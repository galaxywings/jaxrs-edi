package com.wjelogistics.edi.esb.component;

import com.google.common.collect.Maps;
import com.wjelogistics.edi.esb.jaxws.EportWebServiceDelegate;
import com.wjelogistics.edi.esb.jaxws.EportWebServiceService;
import com.wjelogistics.edi.esb.util.ObjectMapperFactory;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.Map;

/*  在service flow的末尾处发送AMQP前执行，
    将真实payload以及服务配置信息重新用JSON序列化以通过AMQP传输 */
public class MessageAnalyzeService implements Callable {

    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {
        MuleMessage message = eventContext.getMessage();

        Map<String, Object> payloadMap = Maps.newHashMap();
        payloadMap.put("test","test");


        return ObjectMapperFactory.get().writeValueAsString(payloadMap);
    }
}
