package com.wjelogistics.edi.esb.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /**
     * @return a fully-configured ObjectMapper
     */
    public static ObjectMapper get() {
        return mapper;
    }

    private ObjectMapperFactory() {
        // Utility class
    }
}
