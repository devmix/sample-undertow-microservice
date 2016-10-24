package com.github.devmix.sample.undertow.microservice.endpoints;

import com.github.devmix.sample.undertow.microservice.dto.SampleData;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.ok;

/**
 * @author Sergey Grachev
 */
@Produces("application/json")
@Path("/java")
@RequestScoped
public class SampleEndpoint {

    @Inject
    private SampleData data;

    @GET
    public Response asString() {
        return ok("Hello World!").build();
    }

    @GET
    @Path("json")
    public SampleData asJson() {
        return data;
    }
}
