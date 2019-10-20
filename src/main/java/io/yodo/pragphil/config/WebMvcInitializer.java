package io.yodo.pragphil.config;

import io.yodo.pragphil.api.ApiConfig;
import io.yodo.pragphil.web.WebMvcConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.lang.reflect.AnnotatedType;

public class WebMvcInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        WebApplicationContext rootContext = createContext(CoreConfig.class);
        container.addListener(new ContextLoaderListener(rootContext));

        WebApplicationContext webContext = createContext(WebMvcConfig.class);
        addDispatcherServlet(container, "web", "/", webContext);

        WebApplicationContext apiContext = createContext(ApiConfig.class);
        addDispatcherServlet(container, "api", "/api/*", apiContext);
    }

    private WebApplicationContext createContext(Class<?>... configClasses) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(configClasses);
        return ctx;
    }

    private void addDispatcherServlet(ServletContext container, String name, String path, WebApplicationContext ctx) {
        DispatcherServlet webServlet = new DispatcherServlet(ctx);
        webServlet.setThrowExceptionIfNoHandlerFound(true);

        ServletRegistration.Dynamic webDispatcher = container.addServlet(name, webServlet);
        webDispatcher.setLoadOnStartup(1);
        webDispatcher.addMapping(path);
    }
}
