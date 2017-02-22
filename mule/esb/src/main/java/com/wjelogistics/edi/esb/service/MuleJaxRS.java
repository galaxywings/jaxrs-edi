package com.wjelogistics.edi.esb.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Created by wangwy on 2017/2/22.
 */
public class MuleJaxRS {

    @GET
    @Produces("text/plain")
    @Path("/sayHelloWithUri/{name}")
    public String sayHelloWithUri(@PathParam("name") String name)
    {
        return "Hello " + name;
    }
}
