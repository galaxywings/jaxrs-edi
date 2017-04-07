package com.wjelogistics.edi.esb.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.wjelogistics.edi.esb.model.dao.CtnInfoMapper;
import com.wjelogistics.edi.esb.model.dao.CtnStatusMapper;
import com.wjelogistics.edi.esb.model.dao.DatResultMapper;
import com.wjelogistics.edi.esb.model.dao.VesselInfoMapper;
import com.wjelogistics.edi.esb.model.entity.CtnInfo;
import com.wjelogistics.edi.esb.model.entity.CtnStatus;
import com.wjelogistics.edi.esb.model.entity.DatResult;
import com.wjelogistics.edi.esb.model.entity.VesselInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangwy on 2017/3/28.
 */
public class DatAnalyzeService {
    private  String result;


    public DatAnalyzeService(String result){
        this.result = result;
    }

    public String analyze () throws Exception{
        Gson gson = new Gson();
        List<DatResult> datResultList = gson.fromJson(result,new TypeToken<List<DatResult>>(){}.getType());

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        try {
            DatResultMapper datResultMapper = session.getMapper(DatResultMapper.class);
            for (DatResult datResult:datResultList) {
                String id = UUID.randomUUID().toString();
                datResult.setId(id);
                datResultMapper.insert(datResult);
            }
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return "1";
    }
}
