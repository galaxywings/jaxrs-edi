package com.wjelogistics.edi.esb;

import org.milyn.SmooksException;
import org.milyn.cdr.annotation.ConfigParam;
import org.milyn.container.ExecutionContext;
import org.milyn.delivery.sax.SAXElement;
import org.milyn.delivery.sax.SAXVisitAfter;
import org.milyn.delivery.sax.SAXVisitBefore;

import java.io.IOException;

/**
 * Created by kymair on 03/02/2017.
 */
public class EchoMessageVisitor implements SAXVisitAfter, SAXVisitBefore {

    @ConfigParam
    private  String message;

    @Override
    public void visitAfter(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
        System.out.println("visit after message: " + message);
    }

    @Override
    public void visitBefore(SAXElement element, ExecutionContext executionContext) throws SmooksException, IOException {
        System.out.println("visit before message: " + message);
    }
}
