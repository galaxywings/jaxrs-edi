package com.wjelogistics.edi.esb.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import com.wjelogistics.edi.esb.model.entity.VesselInfo;
import com.wjelogistics.edi.esb.util.ObjectMapperFactory;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;

import java.util.Map;

/**
 * Created by wangwy on 2017/3/20.
 */
public class ConsumeNbportMessage implements org.mule.api.lifecycle.Callable {

    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {

        MuleMessage message = eventContext.getMessage();
        Map<String, Object> payloadMap = ObjectMapperFactory.get().readValue(message.getPayloadAsBytes(),
                new TypeReference<Map<String, Object>>() {});

        String val = (String) payloadMap.get("message");

        Gson gson = new Gson();
        VesselInfo vesselInfo = gson.fromJson(val,VesselInfo.class);
        System.out.println(vesselInfo.getVoyage());
        return null;
    }
}