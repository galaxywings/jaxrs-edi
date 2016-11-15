package com.wjelogistics.edi.esb.interceptor;

import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.interceptor.AbstractEnvelopeInterceptor;
import org.mule.management.stats.ProcessingTime;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.util.List;

public class MysqlLoggingInterceptor extends AbstractEnvelopeInterceptor {

    @Inject
    public MuleContext muleContext;

    @Inject
    JdbcTemplate jdbcTemplate;

    @Override
    public MuleEvent before(MuleEvent event) throws MuleException {
        List<Integer> result = jdbcTemplate.queryForList("select id from entities", Integer.class);
        System.out.println(result);
        return event;
    }

    @Override
    public MuleEvent after(MuleEvent event) throws MuleException {
        return event;
    }

    @Override
    public MuleEvent last(MuleEvent event, ProcessingTime time, long startTime, boolean exceptionWasThrown) throws MuleException {
        return event;
    }
}
