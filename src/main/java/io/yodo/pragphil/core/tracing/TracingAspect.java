package io.yodo.pragphil.core.tracing;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile({"staging", "devel"})
public class TracingAspect {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Around("Pointcuts.webController() && !Pointcuts.noTrace()")
    public Object traceController(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            return pjp.proceed(pjp.getArgs());
        } finally {
            long delta = System.currentTimeMillis() - start;
            String sig = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName() + "(..)";

            log.trace("execution of " + sig + " took " + delta + "ms");
        }
    }
}
