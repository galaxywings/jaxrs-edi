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

/**
 * Created by wangwy on 2017/3/28.
 */
public class NbportDatService implements Callable {
    private static final QName SERVICE_NAME = new QName("http://eportws.nbport.com/", "EportWebServiceService");
    @Override
    public Object onCall(MuleEventContext eventContext) throws Exception {
        MuleMessage message = eventContext.getMessage();

        URL wsdlURL = EportWebServiceService.WSDL_LOCATION;

        EportWebServiceService ss = new EportWebServiceService(wsdlURL, SERVICE_NAME);
        EportWebServiceDelegate port = ss.getEportWebServicePort();


        System.out.println("Invoking callService...");
        String usr = "WS_WJDS";
        String pwd  = "64a7909b3b3abf538f7fe1f746aa155660d6bfa457b53da44495609845642823";//SHA-256
        String serviceID = "QueryDatResult";
        String applyData = "{bl_no:'ANBJKBV6733026',ctn_no:'DRYU2022871'}";

        String _callService__return = port.callService(usr, pwd, serviceID, applyData);

        Map<String, Object> payloadMap = Maps.newHashMap();
        Map<String, Object> messageMap = Maps.newHashMap();
        messageMap.put("serviceName",serviceID);
        messageMap.put("serviceReturn",_callService__return);
        payloadMap.put("message",messageMap);

        return ObjectMapperFactory.get().writeValueAsString(payloadMap);
    }
}
