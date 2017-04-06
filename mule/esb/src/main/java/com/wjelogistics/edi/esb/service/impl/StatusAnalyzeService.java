package com.wjelogistics.edi.esb.service.impl;

import com.google.gson.Gson;
import com.wjelogistics.edi.esb.model.dao.CtnInfoMapper;
import com.wjelogistics.edi.esb.model.dao.CtnStatusMapper;
import com.wjelogistics.edi.esb.model.dao.VesselInfoMapper;
import com.wjelogistics.edi.esb.model.entity.CtnInfo;
import com.wjelogistics.edi.esb.model.entity.CtnStatus;
import com.wjelogistics.edi.esb.model.entity.VesselInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by wangwy on 2017/3/28.
 */
public class StatusAnalyzeService {

    private  String result;


    public StatusAnalyzeService(String result){
          this.result = result;
    }

    public String analyze() throws IOException {
        Gson gson = new Gson();
        VesselInfo vesselInfo = gson.fromJson(result,VesselInfo.class);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        try {
            VesselInfoMapper vesselInfoMapper = session.getMapper(VesselInfoMapper.class);
            CtnInfoMapper ctnInfoMapper = session.getMapper(CtnInfoMapper.class);
            CtnStatusMapper ctnStatusMapper = session.getMapper(CtnStatusMapper.class);

            String vessle_id = UUID.randomUUID().toString();

            for (int i = 0; i < vesselInfo.getCtn_no_list().size(); i++) {
                CtnInfo ctnInfo = vesselInfo.getCtn_no_list().get(i);
                String ctnInfo_id = UUID.randomUUID().toString();
                for (int j = 0; j < ctnInfo.getStatus_list().size(); j++) {
                    CtnStatus ctnStatus = ctnInfo.getStatus_list().get(j);
                    String status_id = UUID.randomUUID().toString();
                    ctnStatus.setId(status_id);
                    ctnStatus.setCtnInfoId(ctnInfo_id);
                    ctnStatus.setVesselInfoId(vessle_id);
                    ctnStatusMapper.insert(ctnStatus);
                }
                ctnInfo.setId(ctnInfo_id);
                ctnInfo.setVesselInfoId(vessle_id);
                ctnInfoMapper.insert(ctnInfo);
            }

            vesselInfo.setId(vessle_id);
            vesselInfoMapper.insert(vesselInfo);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return "1";
    }
}
