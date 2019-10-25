package io.yodo.pragphil.core.tracing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for methods that should be ignored by the tracer in a component that would otherwise be targeted by
 * tracing. Useful if for instance we're tracing all public methods in a {@link org.springframework.stereotype.Controller}
 * but we want to skip certain utility methods, like initializers, exception handlers, etc.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NoTrace {
}
