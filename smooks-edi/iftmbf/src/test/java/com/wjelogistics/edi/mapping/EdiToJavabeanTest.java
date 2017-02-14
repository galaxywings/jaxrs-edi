package com.wjelogistics.edi.mapping;


import org.junit.Test;
import org.milyn.Smooks;
import org.milyn.io.StreamUtils;
import org.milyn.payload.StringResult;
import org.milyn.smooks.edi.EDIReaderConfigurator;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

public class EdiToJavabeanTest {

     @Test
    public void testMapping() throws Exception {
        Smooks smooks = new Smooks();
        smooks.setReaderConfig(new EDIReaderConfigurator("src/main/resources/edi-model.xml"));
        StringResult result = new StringResult();
        smooks.filterSource(new StreamSource(new ByteArrayInputStream(
                StreamUtils.readStream(new FileInputStream("samples/IFTMBF.091049545_MADISON MAERSK706W.txt.B721400358.txt")))), result);
        System.out.println(result.getResult());
    }

}
