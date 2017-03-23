package com.wjelogistics.edi.esb;

import com.wjelogistics.edi.esb.model.dao.VesselInfoMapper;
import com.wjelogistics.edi.esb.model.entity.VesselInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by wangwy on 2017/3/22.
 */
public class MybatisTest {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        try{
            System.out.println("....open");
            VesselInfoMapper mapper = session.getMapper(VesselInfoMapper.class);
            VesselInfo obj = mapper.selectByPrimaryKey("1");
            System.out.println(obj.getVoyage());
            VesselInfo vessel = new VesselInfo();
            vessel.setId(UUID.randomUUID().toString());
            vessel.setArrivalTime("1111");
            mapper.insert(vessel);
            session.commit();
            System.out.println("inserted");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
    }
}
