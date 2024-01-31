package com.tiendaelectrodomesticos.products.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidateString implements IValidate<String> {
    @Override
    public void validate(String s) {
        if (s == null || !s.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("La cadena debe contener solo letras");
        }
    }
}
