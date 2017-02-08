package com.wjelogistics.edi.esb.transformer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.wjelogistics.edi.esb.util.ObjectMapperFactory;
import com.wjelogistics.edi.mapping.focus.FocusBookingFactory;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

public class EdiToJavaTransformer extends AbstractMessageTransformer {

    FocusBookingFactory focusBookingFactory = FocusBookingFactory.getInstance();


    public EdiToJavaTransformer() throws IOException, SAXException {
    }

    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
        try {
            return ObjectMapperFactory.get().writeValueAsString(focusBookingFactory.fromEDI(new StringReader(new String((byte[])message.getPayload()))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
