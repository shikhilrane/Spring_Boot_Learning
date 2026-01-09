package com.example.primeNumber.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrimeNumberValidator implements ConstraintValidator<PrimeNumCheck, Integer> {


    @Override
    public boolean isValid(Integer i, ConstraintValidatorContext constraintValidatorContext) {
        // Prime numbers are greater than 1
        if (i <= 1) {
            return false;
        }

        // 2 is the only even prime number
        if (i == 2) {
            return true;
        }

        // Eliminate even numbers
        if (i % 2 == 0) {
            return false;
        }

        // Check divisibility up to sqrt(i)
        for (int j = 3; j * j <= i; j += 2) {
            if (i % j == 0) {
                return false;
            }
        }

        return true;
    }
}
