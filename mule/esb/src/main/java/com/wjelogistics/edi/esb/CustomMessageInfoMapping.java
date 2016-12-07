package com.wjelogistics.edi.esb;

import org.mule.api.MuleMessage;
import org.mule.api.routing.MessageInfoMapping;

public class CustomMessageInfoMapping implements MessageInfoMapping {

    public String getCorrelationId(MuleMessage message)
    {
        String id= message.getCorrelationId();
        if (id == null)
        {
            id = getMessageId(message);
        }
        return id;
    }

    public String getMessageId(MuleMessage message)
    {
        return message.getUniqueId();
    }
}
