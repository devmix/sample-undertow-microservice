package com.github.devmix.sample.undertow.microservice.core.scopes;

import org.jboss.weld.context.bound.BoundRequestContext;
import org.jboss.weld.context.bound.BoundSessionContext;

import javax.enterprise.inject.spi.CDI;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CDIHttpRequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(final ServletRequestEvent event) {
        final ServletRequest request = event.getServletRequest();

        // create request context for current thread
        final BoundRequestContext requestContext = CDI.current().select(BoundRequestContext.class).get();
        final Map<String, Object> requestMap = new HashMap<String, Object>();
        requestContext.associate(requestMap);
        requestContext.activate();
        request.setAttribute("cdiRequestContext", requestContext);
        request.setAttribute("cdiRequestMap", requestMap);

        // restore session content for current thread
        final HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            final BoundSessionContext ctx = (BoundSessionContext) session.getAttribute("cdiSessionContext");
            if (ctx != null && !ctx.isActive()) {
                ctx.associate((Map<String, Object>) session.getAttribute("cdiSessionMap"));
                ctx.activate();
            }
        }
    }

    @Override
    public void requestDestroyed(final ServletRequestEvent event) {
        final ServletRequest request = event.getServletRequest();

        final BoundRequestContext ctx = (BoundRequestContext) request.getAttribute("cdiRequestContext");
        if (ctx != null) {
            final Map<String, Object> data = (Map<String, Object>) request.getAttribute("cdiRequestMap");
            ctx.invalidate();
            ctx.deactivate();
            ctx.dissociate(data);
            request.setAttribute("cdiRequestContext", null);
            request.setAttribute("cdiRequestMap", null);
        }
    }
}