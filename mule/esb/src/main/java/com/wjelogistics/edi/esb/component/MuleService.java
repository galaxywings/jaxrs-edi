package com.wjelogistics.edi.esb.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.wjelogistics.edi.esb.annotation.InvokeFromFlow;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import groovy.ui.SystemOutputInterceptor;
import org.joda.time.DateTime;
import org.mule.api.MuleContext;
import org.mule.api.annotations.expressions.Expr;
import org.mule.api.annotations.expressions.Lookup;
import org.mule.api.annotations.param.Payload;
import org.mule.util.ExceptionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MuleService {

    @InvokeFromFlow
    public String greet(@Expr(value = "#[message.inboundProperties.'http.query.params'.name]") String params) {
        return "Hello " + params;
    }

    @InvokeFromFlow
    public String executeScript(@Lookup MuleContext context, @Payload String script) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(eval(script));
        } catch (Exception t) {
            Map<String, String> resultMap = Maps.newHashMap();
            resultMap.put("error", ExceptionUtils.getFullStackTrace(t));
            try {
                return mapper.writeValueAsString(resultMap);
            } catch (JsonProcessingException e) {
                return "{\"error\" : \"Error when generating JSON output\"}";
            }
        }
    }

    private String eval(String script, HashMap<String, Object> bindingValues) {
        GroovyShell shell = createShell(bindingValues);
        Object result = shell.evaluate(script);
        return result != null ? result.toString() : "null";
    }

    private Map<String, String> eval(String script) {
        Map<String, String> resultMap = Maps.newHashMap();
        resultMap.put("script", script);
        resultMap.put("startTime", DateTime.now().toString());

        SystemOutputInterceptorClosure outputCollector = new SystemOutputInterceptorClosure(null);
        SystemOutputInterceptor systemOutputInterceptor = new SystemOutputInterceptor(outputCollector);
        systemOutputInterceptor.start();

        try {
            HashMap<String, Object> bindingValues = Maps.newHashMap();
            resultMap.put("result", eval(script, bindingValues));
        } catch (Exception t) {
            resultMap.put("error", t.getMessage());
        } finally {
            systemOutputInterceptor.stop();
            try {
                systemOutputInterceptor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        resultMap.put("output", outputCollector.getStringBuffer().toString().trim());
        resultMap.put("endTime", DateTime.now().toString());
        return resultMap;
    }


    private GroovyShell createShell(HashMap<String, Object> bindingValues) {
        return new GroovyShell(this.getClass().getClassLoader(), new Binding(bindingValues));
    }

    private class SystemOutputInterceptorClosure extends Closure<Object> {

        private static final long serialVersionUID = 1L;
        StringBuffer stringBuffer = new StringBuffer();

        SystemOutputInterceptorClosure(Object owner) {
            super(owner);
        }

        @Override
        public Object call(Object params) {
            stringBuffer.append(params);
            return false;
        }

        @Override
        public Object call(Object... args) {
            stringBuffer.append(Arrays.toString(args));
            return false;
        }

        StringBuffer getStringBuffer() {
            return this.stringBuffer;
        }
    }

}
