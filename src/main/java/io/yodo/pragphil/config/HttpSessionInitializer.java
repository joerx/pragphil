package io.yodo.pragphil.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

import javax.servlet.annotation.WebListener;

@WebListener
public class HttpSessionInitializer extends AbstractHttpSessionApplicationInitializer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public HttpSessionInitializer() {
        super(CoreConfig.class);
        log.debug("Initialized http session config");
    }
}
