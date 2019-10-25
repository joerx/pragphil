package io.yodo.pragphil.core.tracing;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile({"staging", "devel"})
public class ExecutionTimeTracingAspect {

    private final ExecutionTimeTracer tracer;

    public ExecutionTimeTracingAspect(ExecutionTimeTracer tracer) {
        this.tracer = tracer;
    }

    @Around("Pointcuts.webController() && !Pointcuts.noTrace()")
    public Object traceController(ProceedingJoinPoint pjp) throws Throwable {
        return tracer.trace(pjp);
    }

    @Around("Pointcuts.apiEndpoint() && !Pointcuts.noTrace()")
    public Object traceEndpoint(ProceedingJoinPoint pjp) throws Throwable {
        return tracer.trace(pjp);
    }
}
