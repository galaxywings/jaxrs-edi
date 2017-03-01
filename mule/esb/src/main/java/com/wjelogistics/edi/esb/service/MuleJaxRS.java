package com.wjelogistics.edi.esb.service;

import javax.ws.rs.*;

/**
 * Created by wangwy on 2017/2/22.
 */
@Path("/helloworld")
public class MuleJaxRS {

    @GET
    @Produces("text/plain")
    @Path("/{name}")
    public String sayHelloWithUri(@PathParam("name") String name)
    {
        return "Hello " + name;
    }

    @POST
    @Produces("text/json")
    @Path("/{body}")
    public  String handlePost(@PathParam("body") String body){
        System.out.println(body);
        return body;
    }
}
