package com.github.devmix.sample.undertow.microservice.core;

import com.github.devmix.sample.undertow.microservice.core.scopes.CDIHttpRequestListener;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.jboss.weld.environment.se.events.ContainerShutdown;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletException;

import static io.undertow.Handlers.redirect;
import static io.undertow.servlet.Servlets.listener;
import static io.undertow.servlet.Servlets.servlet;

/**
 * @author Sergey Grachev
 */
@ApplicationScoped
@SuppressWarnings({"CdiUnproxyableBeanTypesInspection", "UnusedParameters", "unused"})
public final class Bootstrap {

    @Inject
    private ServerConfiguration cfg;

    private Undertow server;

    private void start(@Observes final ContainerInitialized event) throws ServletException {
        final DeploymentManager manager = Servlets.defaultContainer()
                .addDeployment(createDeployment());

        manager.deploy();

        (server = createServer(manager)).start();
    }

    private void stop(@Observes final ContainerShutdown event) {
        server.stop();
    }

    private Undertow createServer(final DeploymentManager manager) throws ServletException {
        final PathHandler path = Handlers.path(redirect(cfg.getContextPath()))
                .addPrefixPath(cfg.getContextPath(), manager.start());

        return Undertow.builder()
                .addHttpListener(cfg.getPort(), cfg.getHost())
                .setHandler(path)
                .build();
    }

    private DeploymentInfo createDeployment() {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        return Servlets.deployment()
                .setClassLoader(classLoader)
                .setContextPath(cfg.getContextPath())
                .setDeploymentName(cfg.getDeploymentName())
                .setResourceManager(new ClassPathResourceManager(classLoader))
                .addListeners(listener(CDIHttpRequestListener.class))
                .addServlets(servlet("jerseyServlet", ServletContainer.class)
                        .setLoadOnStartup(1)
                        .addInitParam("javax.ws.rs.Application", JaxRsApplication.class.getName())
                        .addMapping("/api/*"));
    }
}
