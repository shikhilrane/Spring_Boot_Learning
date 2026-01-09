package com.inputValidation.inputValidationCustomAnnotation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(
        validatedBy = {EmployeeRoleValidator.class}
)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface EmployeeRoleValidationAnnotation {
    String message() default "Role of Employee can either be ADMIN or USER";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
