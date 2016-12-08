package com.wjelogistics.edi.esb.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import com.wjelogistics.edi.esb.util.ObjectMapperFactory;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import java.util.Map;

/*  在service flow的末尾处发送AMQP前执行，
    将真实payload以及服务配置信息重新用JSON序列化以通过AMQP传输 */
public class AfterService implements Callable {
    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {
        MuleMessage message = eventContext.getMessage();

        /* 恢复在BeforeService里暂存的wujie.config */
        String configJson = message.getProperty("wujie.config", PropertyScope.INVOCATION);
        String seq = message.getProperty("WUJIE_PROCESS_SEQ", PropertyScope.SESSION);

        /* configuratorFlow里 WUJIE_PROCESS_SEQ 为null, 需要从wujie.config里取 */
        if(seq == null) {
            seq = "configurator," + ((Map<String, Object>)ObjectMapperFactory.get().readValue(configJson,
                    new TypeReference<Map<String, Object>>(){})).get("seq");
        }

        /* 设置余下须执行的服务seq */
        String remainingSeq = seq.contains(",") ? seq.split(",", 2)[1] : "";
        message.setProperty("WUJIE_PROCESS_SEQ", remainingSeq, PropertyScope.SESSION);

        /* 设置下一个服务的队列名 */
        if (!remainingSeq.isEmpty()) {
            message.setProperty("nextQueue", remainingSeq.split(",", 2)[0].split(":")[0] + ".inbox", PropertyScope.INVOCATION);
        }

        Map<String, Object> payloadMap = Maps.newHashMap();
        payloadMap.put("wujie.payload", message.getPayload());
        payloadMap.put("wujie.config", configJson);

        return ObjectMapperFactory.get().writeValueAsString(payloadMap);
    }
}
