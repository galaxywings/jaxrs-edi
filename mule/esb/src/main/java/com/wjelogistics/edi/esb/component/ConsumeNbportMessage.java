package com.wjelogistics.edi.esb.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.wjelogistics.edi.esb.model.dao.CtnInfoMapper;
import com.wjelogistics.edi.esb.model.dao.CtnStatusMapper;
import com.wjelogistics.edi.esb.model.dao.VesselInfoMapper;
import com.wjelogistics.edi.esb.model.entity.CtnInfo;
import com.wjelogistics.edi.esb.model.entity.CtnStatus;
import com.wjelogistics.edi.esb.model.entity.VesselInfo;
import com.wjelogistics.edi.esb.service.impl.DatAnalyzeService;
import com.wjelogistics.edi.esb.service.impl.StatusAnalyzeService;
import com.wjelogistics.edi.esb.util.ObjectMapperFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;

import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

/**
 * Created by wangwy on 2017/3/20.
 */
public class ConsumeNbportMessage implements org.mule.api.lifecycle.Callable {

    public static String QUERY_LOGISTICS = "QueryLogisticsStatus";
    public static String QUERY_DAT = "QueryDatResult";

    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {



        MuleMessage message = eventContext.getMessage();
        Map<String, Object> payloadMap = ObjectMapperFactory.get().readValue(message.getPayloadAsBytes(),
                new TypeReference<Map<String, Object>>() {});

        Map<String,Object> msgMap = (Map<String, Object>) payloadMap.get("message");
        String serviceName = (String) msgMap.get("serviceName");
        String ServiceReturn = (String) msgMap.get("serviceReturn");

        if(serviceName.equals(QUERY_LOGISTICS)){
            StatusAnalyzeService statusAnalyzeService = new StatusAnalyzeService(ServiceReturn);
            String result = statusAnalyzeService.analyze();
            System.out.println(result);
        }else if(serviceName.equals(QUERY_LOGISTICS)){
            DatAnalyzeService datAnalyzeService = new DatAnalyzeService(ServiceReturn);
            String result = datAnalyzeService.analyze();
            System.out.println(result);
        }

//        Gson gson = new Gson();
//        VesselInfo vesselInfo = gson.fromJson(val,VesselInfo.class);
//
//        String resource = "mybatis-config.xml";
//        InputStream inputStream = Resources.getResourceAsStream(resource);
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        SqlSession session = sqlSessionFactory.openSession();
//
//        try {
//
//            VesselInfoMapper vesselInfoMapper = session.getMapper(VesselInfoMapper.class);
//            CtnInfoMapper ctnInfoMapper = session.getMapper(CtnInfoMapper.class);
//            CtnStatusMapper ctnStatusMapper = session.getMapper(CtnStatusMapper.class);
//
//            String vessle_id = UUID.randomUUID().toString();
//
//            for (int i = 0; i < vesselInfo.getCtn_no_list().size(); i++) {
//                CtnInfo ctnInfo = vesselInfo.getCtn_no_list().get(i);
//                String ctnInfo_id = UUID.randomUUID().toString();
//                for (int j = 0; j < ctnInfo.getStatus_list().size(); j++) {
//                    CtnStatus ctnStatus = ctnInfo.getStatus_list().get(j);
//                    String status_id = UUID.randomUUID().toString();
//                    ctnStatus.setId(status_id);
//                    ctnStatus.setCtnInfoId(ctnInfo_id);
//                    ctnStatus.setVesselInfoId(vessle_id);
//                    ctnStatusMapper.insert(ctnStatus);
//                }
//                ctnInfo.setId(ctnInfo_id);
//                ctnInfo.setVesselInfoId(vessle_id);
//                ctnInfoMapper.insert(ctnInfo);
//            }
//
//            vesselInfo.setId(vessle_id);
//            vesselInfoMapper.insert(vesselInfo);
//            session.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            session.close();
//        }

        return null;
    }
}
