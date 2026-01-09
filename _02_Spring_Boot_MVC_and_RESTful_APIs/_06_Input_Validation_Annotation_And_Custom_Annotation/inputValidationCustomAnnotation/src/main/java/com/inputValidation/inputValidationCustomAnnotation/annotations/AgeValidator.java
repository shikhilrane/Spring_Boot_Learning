package com.inputValidation.inputValidationCustomAnnotation.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<AgeValidationAnnotation, Integer> {

    @Override
    public boolean isValid(Integer age, ConstraintValidatorContext constraintValidatorContext) {
        if ((age>=18)&&(age<80)) return true;
        return false;
    }
}
