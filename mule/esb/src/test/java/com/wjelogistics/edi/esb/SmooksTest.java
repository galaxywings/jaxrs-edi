package com.wjelogistics.edi.esb;

import org.junit.*;
import org.junit.Test;
import org.milyn.Smooks;
import org.milyn.edi.unedifact.d99b.D99BInterchangeFactory;
import org.milyn.payload.JavaResult;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;

public class SmooksTest {

    @Test
    public void testSax() throws Exception {
        Smooks smooks = new Smooks("samples/smooks-config.xml");
        smooks.filterSource(new StreamSource(new FileInputStream("samples/input-message.xml")),
                new StreamResult(System.out));

    }

    @Test
    public void testVisitor() throws Exception{
        Smooks smooks = new Smooks("samples/smooks-visitor.xml");
        JavaResult result = new JavaResult();
        smooks.filterSource(new StreamSource(new FileInputStream("samples/visitor.xml")), result);
        System.out.println(result.getBean("PUUID"));
    }

}
