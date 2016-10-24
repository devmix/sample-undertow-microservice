package com.github.devmix.sample.undertow.microservice.core;

/**
 * @author Sergey Grachev
 */
public class ServerConfiguration {

    private final String host;
    private final int port;
    private final String contextPath;
    private final String deploymentName;

    public ServerConfiguration(final String host, final int port, final String contextPath) {
        this.host = host;
        this.port = port;
        this.contextPath = contextPath;
        this.deploymentName = "SampleUndertowMicroservice";
    }

    public String getContextPath() {
        return contextPath;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public String getDeploymentName() {
        return deploymentName;
    }
}
