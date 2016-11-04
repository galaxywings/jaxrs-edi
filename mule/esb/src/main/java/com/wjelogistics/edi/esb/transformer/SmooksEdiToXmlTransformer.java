package com.wjelogistics.edi.esb.transformer;

import org.milyn.Smooks;
import org.milyn.payload.StringResult;
import org.milyn.smooks.edi.EDIReaderConfigurator;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

public class SmooksEdiToXmlTransformer extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		Smooks smooks = new Smooks();
		StringResult result = new StringResult();
		try {
			byte[] bytes = message.getInvocationProperty("mapping_file");
			smooks.setReaderConfig(new EDIReaderConfigurator(new String(bytes)));

			smooks.filterSource(new StreamSource(new ByteArrayInputStream(message.getPayloadAsBytes())), result);
		} catch (Exception e) {
			throw new TransformerException(this, e);
		}

		return result.getResult();
	}
}
