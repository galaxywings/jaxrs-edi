package com.wjelogistics.edi.esb.component;

import com.google.common.collect.Maps;
import com.wjelogistics.edi.esb.annotation.InvokeFromFlow;
import com.wjelogistics.edi.esb.util.ObjectMapperFactory;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.annotations.expressions.Expr;
import org.mule.api.annotations.param.Payload;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

import java.util.Map;

public class MessageHelper{

    @InvokeFromFlow
    public String toPayloadAndException(@Payload byte[] payload,
                    @Expr("#[org.mule.util.ExceptionUtils.getFullStackTrace(exception)]") String fullStackTrace)
            throws Exception {
        Map map = Maps.newHashMap();
        map.put("payload", payload);
        map.put("exception", fullStackTrace);
        return ObjectMapperFactory.get().writeValueAsString(map);
    }

}
