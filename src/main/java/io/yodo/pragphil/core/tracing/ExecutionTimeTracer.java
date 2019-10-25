package io.yodo.pragphil.core.tracing;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Tracer to trace execution time of methods via the default logging facility set up for the application.
 */
@Component
public class ExecutionTimeTracer {

    private final Logger log = LoggerFactory.getLogger("io.yodo.pragphil.Tracing");

    Object trace(ProceedingJoinPoint pjp) throws Throwable {
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
