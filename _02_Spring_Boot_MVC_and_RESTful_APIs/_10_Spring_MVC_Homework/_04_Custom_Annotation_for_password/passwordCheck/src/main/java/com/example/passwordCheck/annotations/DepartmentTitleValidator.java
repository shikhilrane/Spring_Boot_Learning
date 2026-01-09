package com.example.passwordCheck.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class DepartmentTitleValidator implements ConstraintValidator<DepartmentTitleAnnotation, String> {
    @Override
    public boolean isValid(String inputTitle, ConstraintValidatorContext constraintValidatorContext) {
        if (inputTitle == null) return false;
        List<String> list = Arrays.asList("Administrator", "Management", "Finance");
        return list.contains(inputTitle);
    }
}
