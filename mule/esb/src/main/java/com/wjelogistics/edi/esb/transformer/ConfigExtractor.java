package com.wjelogistics.edi.esb.transformer;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigExtractor extends AbstractMessageTransformer {

    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
        Map<String,String> map = (Map<String, String>) message.getPayload();
        List<String> configs = new ArrayList<String>();
        configs.add(map.get("config"));

        return configs;
    }
}