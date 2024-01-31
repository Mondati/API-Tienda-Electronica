package com.tiendaelectrodomesticos.products.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidateInteger implements IValidate<Integer> {
    private static final Integer MIN_DIGITS = 10000;
    private static final Integer MAX_DIGITS = 99999;

    @Override
    public void validate(Integer value) {
        if (value == null || value < MIN_DIGITS || value > MAX_DIGITS) {
            throw new IllegalArgumentException("El código debe ser un número entero de 5 dígitos, sin letras ni espacios.");
        }
    }
}

