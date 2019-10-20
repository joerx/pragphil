package io.yodo.pragphil.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "io.yodo.pragphil.web")
public class WebMvcConfig implements WebMvcConfigurer {

//    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Bean
//    public SimpleMappingExceptionResolver errorHandler() {
//        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
//
//        r.setDefaultErrorView("error");
//        r.setExceptionAttribute("ex");
//        r.setWarnLogCategory("io.yodo.pragphil.core.error");
//
//        return r;
//    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setExposedContextBeanNames("userHelper");
        vr.setPrefix("/WEB-INF/view/");
        vr.setSuffix(".jsp");
        registry.viewResolver(vr);
    }

    // resource handlers, to serve static files
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

}

