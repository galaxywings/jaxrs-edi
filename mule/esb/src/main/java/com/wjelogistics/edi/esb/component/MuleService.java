package com.wjelogistics.edi.esb.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;
import com.wjelogistics.edi.esb.annotation.InvokeFromFlow;
import com.wjelogistics.edi.esb.util.ObjectMapperFactory;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import groovy.ui.SystemOutputInterceptor;
import org.joda.time.DateTime;
import org.mule.api.MuleContext;
import org.mule.api.annotations.expressions.Lookup;
import org.mule.api.annotations.param.Payload;
import org.mule.api.construct.FlowConstruct;
import org.mule.construct.Flow;
import org.mule.util.ExceptionUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MuleService {

    @InvokeFromFlow
    public String getServices(@Lookup MuleContext context) {
        try {
            List<ServiceDef> services = context.getRegistry().lookupFlowConstructs().stream()
                    .filter(flow -> flow.getName().startsWith("service-"))
                    .map(new Function<FlowConstruct, ServiceDef>() {
                        @Override
                        public ServiceDef apply(FlowConstruct flow) {
                            ServiceDef def = new ServiceDef();
                            def.setName(flow.getName());
//
//                            ((Flow)flow).getAnnotations().values().toArray()[2]
                            return new ServiceDef();
                        }
                    }).collect(Collectors.toList());

//                    .map(flow -> ((String)((Flow)flow).getAnnotations().values().toArray()[2]))

            return ObjectMapperFactory.get().writeValueAsString(services);
        } catch (JsonProcessingException e) {
            return "{\"error\" : \"Error when generating JSON output\"}";
        }
    }

    @InvokeFromFlow
    public String executeScript(@Lookup MuleContext context, @Payload String script) {
        try {
            return ObjectMapperFactory.get().writeValueAsString(eval(script));
        } catch (Exception t) {
            Map<String, String> resultMap = Maps.newHashMap();
            resultMap.put("error", ExceptionUtils.getFullStackTrace(t));
            try {
                return ObjectMapperFactory.get().writeValueAsString(resultMap);
            } catch (JsonProcessingException e) {
                return "{\"error\" : \"Error when generating JSON output\"}";
            }
        }
    }

    static class ServiceDef implements Serializable{
        private String name;
        private Map<String, String> params;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
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
