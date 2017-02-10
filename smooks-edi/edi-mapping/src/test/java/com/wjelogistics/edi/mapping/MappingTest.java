package com.wjelogistics.edi.mapping;

import com.wjelogistics.edi.mapping.focus.FocusBooking;
import com.wjelogistics.edi.mapping.focus.FocusBookingFactory;
import com.wjelogistics.edi.mapping.iftmbf.Iftmbf;
import com.wjelogistics.edi.mapping.iftmbf.IftmbfFactory;
import groovy.lang.GroovyShell;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MappingTest {

    private FocusBookingFactory focusBookingFactory = FocusBookingFactory.getInstance();
    private IftmbfFactory iftmbfFactory = IftmbfFactory.getInstance();
    private GroovyShell shell = new GroovyShell();

    public MappingTest() throws IOException, SAXException {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testMapping() throws Exception {
        FocusBooking focusBooking = focusBookingFactory.fromEDI(new FileReader("resources/input.edi"));
        Iftmbf iftmbf = new Iftmbf();

        shell.setProperty("src", focusBooking);
        shell.setProperty("dest", iftmbf);

        Iftmbf result = (Iftmbf) shell.evaluate(new File("resources/mapping.groovy"));
        assertEquals(result.getHeadRecord().getFileFunction(), "3");
        assertEquals(result.getHeadRecord().getSenderCode(), "132276869");
    }
}
