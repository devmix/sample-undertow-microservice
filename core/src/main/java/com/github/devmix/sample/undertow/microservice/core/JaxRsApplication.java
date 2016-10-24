package com.github.devmix.sample.undertow.microservice.core;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @author Sergey Grachev
 */
@ApplicationPath("/api/*")
public final class JaxRsApplication extends ResourceConfig {

    public JaxRsApplication() {
        packages(true, "com.github.devmix.sample.undertow.microservice");
    }
}