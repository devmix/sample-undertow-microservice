package com.github.devmix.sample.undertow.microservice;

import com.github.devmix.sample.undertow.microservice.core.ServerConfiguration;
import com.github.devmix.sample.undertow.microservice.dto.SampleData;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * @author Sergey Grachev
 */
public final class Resources {

    @Produces
    public SampleData data() {
        return new SampleData("Hello", "World");
    }

    @Produces
    @ApplicationScoped
    public ServerConfiguration configuration() {
        return new ServerConfiguration("localhost", 9090, "/");
    }
}
