package com.example.crypto_trading.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CryptoPairValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCryptoPair {

	String message() default "Invalid crypto pair. Allowed values are BTCUSDT or ETHUSDT.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
