package io.yodo.pragphil.core.tracing;

import org.aspectj.lang.annotation.Pointcut;

abstract class Pointcuts {

    @Pointcut("@annotation(io.yodo.pragphil.core.tracing.NoTrace)")
    void noTrace() {}

    @Pointcut("execution(public * io.yodo.pragphil.web.controller.*Controller.*(..))")
    void webController() {}

    @Pointcut("execution(public * io.yodo.pragphil.api.endpoint.*Endpoint.*(..))")
    void apiEndpoint() {}

    @Pointcut("execution(public * io.yodo.pragphil.api.endpoint.*Endpoint.*(..))")
    void restEndpoint() {}
}
