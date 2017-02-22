
package service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CallService_QNAME = new QName("http://eportws.nbport.com/", "callService");
    private final static QName _CallServiceEx_QNAME = new QName("http://eportws.nbport.com/", "callServiceEx");
    private final static QName _CallServiceExResponse_QNAME = new QName("http://eportws.nbport.com/", "callServiceExResponse");
    private final static QName _CallServiceResponse_QNAME = new QName("http://eportws.nbport.com/", "callServiceResponse");
    private final static QName _MDMSubscribeResponse_QNAME = new QName("http://eportws.nbport.com/", "MDMSubscribeResponse");
    private final static QName _MDMSubscribe_QNAME = new QName("http://eportws.nbport.com/", "MDMSubscribe");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MDMSubscribe }
     * 
     */
    public MDMSubscribe createMDMSubscribe() {
        return new MDMSubscribe();
    }

    /**
     * Create an instance of {@link MDMSubscribeResponse }
     * 
     */
    public MDMSubscribeResponse createMDMSubscribeResponse() {
        return new MDMSubscribeResponse();
    }

    /**
     * Create an instance of {@link CallService }
     * 
     */
    public CallService createCallService() {
        return new CallService();
    }

    /**
     * Create an instance of {@link CallServiceExResponse }
     * 
     */
    public CallServiceExResponse createCallServiceExResponse() {
        return new CallServiceExResponse();
    }

    /**
     * Create an instance of {@link CallServiceResponse }
     * 
     */
    public CallServiceResponse createCallServiceResponse() {
        return new CallServiceResponse();
    }

    /**
     * Create an instance of {@link CallServiceEx }
     * 
     */
    public CallServiceEx createCallServiceEx() {
        return new CallServiceEx();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CallService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eportws.nbport.com/", name = "callService")
    public JAXBElement<CallService> createCallService(CallService value) {
        return new JAXBElement<CallService>(_CallService_QNAME, CallService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CallServiceEx }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eportws.nbport.com/", name = "callServiceEx")
    public JAXBElement<CallServiceEx> createCallServiceEx(CallServiceEx value) {
        return new JAXBElement<CallServiceEx>(_CallServiceEx_QNAME, CallServiceEx.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CallServiceExResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eportws.nbport.com/", name = "callServiceExResponse")
    public JAXBElement<CallServiceExResponse> createCallServiceExResponse(CallServiceExResponse value) {
        return new JAXBElement<CallServiceExResponse>(_CallServiceExResponse_QNAME, CallServiceExResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CallServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eportws.nbport.com/", name = "callServiceResponse")
    public JAXBElement<CallServiceResponse> createCallServiceResponse(CallServiceResponse value) {
        return new JAXBElement<CallServiceResponse>(_CallServiceResponse_QNAME, CallServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MDMSubscribeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eportws.nbport.com/", name = "MDMSubscribeResponse")
    public JAXBElement<MDMSubscribeResponse> createMDMSubscribeResponse(MDMSubscribeResponse value) {
        return new JAXBElement<MDMSubscribeResponse>(_MDMSubscribeResponse_QNAME, MDMSubscribeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MDMSubscribe }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://eportws.nbport.com/", name = "MDMSubscribe")
    public JAXBElement<MDMSubscribe> createMDMSubscribe(MDMSubscribe value) {
        return new JAXBElement<MDMSubscribe>(_MDMSubscribe_QNAME, MDMSubscribe.class, null, value);
    }

}
