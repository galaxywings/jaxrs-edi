package com.wjelogistics.edi.esb.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wjelogistics.edi.esb.util.ObjectMapperFactory;
import org.apache.commons.codec.binary.Base64;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import java.util.List;
import java.util.Map;


public class BeforeService implements Callable {

    /* MuleMessage暂时无法通过注解方式获取，需要实现Callable, 以从MuleEventContext获得 */
    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {
        MuleMessage message = eventContext.getMessage();
        String seq = message.getProperty("WUJIE_PROCESS_SEQ", PropertyScope.SESSION);

        int currentSeq = Integer.parseInt(seq.split(",")[0].split(":")[1]);

        /* 设置余下须执行的服务seq */
        String remainingSeq = seq.contains(",") ? seq.split(",", 2)[1] : "";
        message.setProperty("WUJIE_PROCESS_SEQ", remainingSeq, PropertyScope.SESSION);

        /* 设置下一个服务的队列名 */
        if (!remainingSeq.isEmpty()) {
            message.setProperty("nextQueue", remainingSeq.split(",", 2)[0].split(":")[0] + ".inbox", PropertyScope.INVOCATION);
        }

        /* 保存原始payload, 发生异常时读取 */
        message.setProperty("originalPayload", message.getPayload(), PropertyScope.INVOCATION);

        Map<String, Object> payloadMap = ObjectMapperFactory.get().readValue(message.getPayloadAsBytes(),
                new TypeReference<Map<String, Object>>() {
                });

        String configJson = (String) payloadMap.get("wujie.config");
        Map<String, Object> configMap = ObjectMapperFactory.get()
                .readValue(configJson, new TypeReference<Map<String, Object>>() {
                });

        Map<String, Object> propsMap = (Map<String, Object>) ((List) configMap.get("params")).get(currentSeq);

        propsMap.forEach((k, v) -> message.setProperty(k, v, PropertyScope.INVOCATION));

        message.setProperty("wujie.config", configJson, PropertyScope.INVOCATION);

        return Base64.isBase64((String) payloadMap.get("wujie.payload")) ?
                ObjectMapperFactory.get().convertValue(payloadMap.get("wujie.payload"), byte[].class)
                : payloadMap.get("wujie.payload");
    }
}
