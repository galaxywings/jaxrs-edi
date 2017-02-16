package com.wjelogistics.edi.mapping;


import org.junit.Test;
import org.milyn.Smooks;
import org.milyn.payload.StringResult;
import org.milyn.smooks.edi.EDIReaderConfigurator;

import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EdiToJavabeanTest {

    @Test
    public void testMapping() throws Exception {
        Smooks smooks = new Smooks();
        smooks.setReaderConfig(new EDIReaderConfigurator("src/main/resources/edi-model.xml"));
        StringResult result = new StringResult();

        Files.list(Paths.get("samples"))
                .forEach(file -> {
                    try {
                        System.out.println(file.getFileName());
                        smooks.filterSource(new StreamSource(new FileReader("samples/" + file.getFileName())), result);
                        System.out.println(result.getResult());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
    }

}
