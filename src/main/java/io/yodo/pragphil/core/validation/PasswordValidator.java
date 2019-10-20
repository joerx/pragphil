package io.yodo.pragphil.core.validation;

import io.yodo.pragphil.core.entity.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, User> {

    private static final String TARGET_PROPERTY = "password";

    private int minLength;

    private String message;

    @Override
    public void initialize(Password constraintAnnotation) {
        minLength = constraintAnnotation.minLength();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(User u, ConstraintValidatorContext context) {
        // existing users might not want to update their password
        if (!isNewUser(u) && isEmpty(u.getPassword())) {
            return true;
        }
        // existing users updating their password or new users have to meet password criteria
        if (u.getPassword().length() < minLength) {
            addCustomConstraintViolation(context, message);
            return false;
        }
        return true;
    }

    private static void addCustomConstraintViolation(ConstraintValidatorContext ctx, String msg) {
        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate(msg)
                .addPropertyNode(TARGET_PROPERTY)
                .addConstraintViolation();
    }

    private static boolean isNewUser(User u) {
        return u.getId() == 0;
    }

    private static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
