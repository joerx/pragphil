package io.yodo.pragphil.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { WebMvcConfig.class, WebSecurityConfig.class, ApiSecurityConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        FrameworkServlet f = super.createDispatcherServlet(servletAppContext);
        if (f instanceof DispatcherServlet) {
            ((DispatcherServlet) f).setThrowExceptionIfNoHandlerFound(true);
        }
        return f;
    }
}
