package io.yodo.pragphil.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})

public @interface Password {

    int minLength() default 8;

    String message() default "must be longer than {minLength}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
