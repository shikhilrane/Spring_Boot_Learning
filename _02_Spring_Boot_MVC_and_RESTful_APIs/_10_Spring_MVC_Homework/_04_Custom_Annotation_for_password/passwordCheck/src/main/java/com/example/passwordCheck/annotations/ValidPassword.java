package com.example.passwordCheck.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Password must contain uppercase, lowercase, special character and be at least 10 characters long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}