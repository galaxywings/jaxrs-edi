package com.wjelogistics.edi.esb.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.10
 * 2017-03-13T10:33:27.862+08:00
 * Generated source version: 3.1.10
 * 
 */
@WebServiceClient(name = "EportWebServiceService", 
                  wsdlLocation = "http://www.yibutong.com.cn/eport-dataservices/EportWebServicePort?wsdl",
                  targetNamespace = "http://eportws.nbport.com/") 
public class EportWebServiceService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://eportws.nbport.com/", "EportWebServiceService");
    public final static QName EportWebServicePort = new QName("http://eportws.nbport.com/", "EportWebServicePort");
    static {
        URL url = null;
        try {
            url = new URL("http://www.yibutong.com.cn/eport-dataservices/EportWebServicePort?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(EportWebServiceService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://www.yibutong.com.cn/eport-dataservices/EportWebServicePort?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public EportWebServiceService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public EportWebServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EportWebServiceService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public EportWebServiceService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public EportWebServiceService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public EportWebServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns EportWebServiceDelegate
     */
    @WebEndpoint(name = "EportWebServicePort")
    public EportWebServiceDelegate getEportWebServicePort() {
        return super.getPort(EportWebServicePort, EportWebServiceDelegate.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EportWebServiceDelegate
     */
    @WebEndpoint(name = "EportWebServicePort")
    public EportWebServiceDelegate getEportWebServicePort(WebServiceFeature... features) {
        return super.getPort(EportWebServicePort, EportWebServiceDelegate.class, features);
    }

}