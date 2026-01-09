package com.example.primeNumber.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(
        validatedBy = {DepartmentTitleValidator.class}
)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface DepartmentTitleAnnotation {
    String message() default "Role of Employee can either be Administrator, Management or Finance";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
