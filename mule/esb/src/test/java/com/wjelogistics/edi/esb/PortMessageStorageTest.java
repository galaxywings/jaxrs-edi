package com.wjelogistics.edi.esb;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * Created by wangwy on 2017/3/23.
 */
public class PortMessageStorageTest {
    public static void main(String[] args) throws IOException {
        String val = "{" +
                "    'arrival_time': '20161102092001'," +
                "    'ctn_no_list': [" +
                "        {" +
                "            'ctn_no': 'FCIU7020858'," +
                "            'ctn_owner': 'COS'," +
                "            'ctn_sealno': '4511282'," +
                "            'ctn_sizetype': '45GP'," +
                "            'ctn_status': 'F'," +
                "            'ctn_weight': '14230.0'," +
                "            'dl_port': 'SADM1'," +
                "            'gate_time': '20161029081600'," +
                "            'status_list': [" +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161102215700'," +
                "                    'status': '01'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '02'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161029081600'," +
                "                    'status': '03'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '04'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '05'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '06'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '07'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161028102657'," +
                "                    'status': '09'" +
                "                }" +
                "            ]," +
                "            'truck_lis': 'ZJB-8W839'," +
                "            'work_terminal': 'BLCT3'" +
                "        }," +
                "        {" +
                "            'ctn_no': 'CSLU6342894'," +
                "            'ctn_owner': 'COS'," +
                "            'ctn_sealno': '4507095'," +
                "            'ctn_sizetype': '45GP'," +
                "            'ctn_status': 'F'," +
                "            'ctn_weight': '14560.0'," +
                "            'dl_port': 'SADM1'," +
                "            'gate_time': '20161029063200'," +
                "            'status_list': [" +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161103014100'," +
                "                    'status': '01'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '02'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161029063200'," +
                "                    'status': '03'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '04'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '05'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '06'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '07'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161029053526'," +
                "                    'status': '09'" +
                "                }" +
                "            ]," +
                "            'truck_lis': 'ZJB-8R199'," +
                "            'work_terminal': 'BLCT3'" +
                "        }," +
                "        {" +
                "            'ctn_no': 'CSLU6337774'," +
                "            'ctn_owner': 'COS'," +
                "            'ctn_sealno': '4506923'," +
                "            'ctn_sizetype': '45GP'," +
                "            'ctn_status': 'F'," +
                "            'ctn_weight': '14560.0'," +
                "            'dl_port': 'SADM1'," +
                "            'gate_time': '20161028140000'," +
                "            'status_list': [" +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161103011000'," +
                "                    'status': '01'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '02'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161028140000'," +
                "                    'status': '03'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '04'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '05'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '06'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '07'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161028134000'," +
                "                    'status': '09'" +
                "                }" +
                "            ]," +
                "            'truck_lis': 'ZJB-8R718'," +
                "            'work_terminal': 'BLCT3'" +
                "        }," +
                "        {" +
                "            'ctn_no': 'CSLU6337809'," +
                "            'ctn_owner': 'COS'," +
                "            'ctn_sealno': '4511178'," +
                "            'ctn_sizetype': '45GP'," +
                "            'ctn_status': 'F'," +
                "            'ctn_weight': '14600.0'," +
                "            'dl_port': 'SADM1'," +
                "            'gate_time': '20161029080200'," +
                "            'status_list': [" +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161103012800'," +
                "                    'status': '01'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '02'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161029080200'," +
                "                    'status': '03'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '04'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '05'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '06'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '07'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161028095601'," +
                "                    'status': '09'" +
                "                }" +
                "            ]," +
                "            'truck_lis': 'ZJB-83A73'," +
                "            'work_terminal': 'BLCT3'" +
                "        }," +
                "        {" +
                "            'ctn_no': 'CSLU6345229'," +
                "            'ctn_owner': 'COS'," +
                "            'ctn_sealno': '4507082'," +
                "            'ctn_sizetype': '45GP'," +
                "            'ctn_status': 'F'," +
                "            'ctn_weight': '14560.0'," +
                "            'dl_port': 'SADM1'," +
                "            'gate_time': '20161028164400'," +
                "            'status_list': [" +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161103004600'," +
                "                    'status': '01'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '02'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161028164400'," +
                "                    'status': '03'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '04'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '05'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '06'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '0'," +
                "                    'feed_back_time': ''," +
                "                    'status': '07'" +
                "                }," +
                "                {" +
                "                    'feed_back_desc': ''," +
                "                    'feed_back_flag1': '1'," +
                "                    'feed_back_flag2': ''," +
                "                    'feed_back_result': '1'," +
                "                    'feed_back_time': '20161028162022'," +
                "                    'status': '09'" +
                "                }" +
                "            ]," +
                "            'truck_lis': 'ZJB-8Y739'," +
                "            'work_terminal': 'BLCT3'" +
                "        }" +
                "    ]," +
                "    'direction': 'E'," +
                "    'sailing_time': '20161103031500'," +
                "    'terminal': ''," +
                "    'vessel_ename': 'COSCO TIANJIN'," +
                "    'vessel_uncode': 'UN9300324'," +
                "    'voyage': '122W'" +
                "}";

        Gson gson =  new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        VesselInfo vesselInfo = gson.fromJson(val,VesselInfo.class);
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
        System.out.println("storage....");
    }
}
