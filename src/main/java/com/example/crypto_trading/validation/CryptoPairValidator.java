package com.example.crypto_trading.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CryptoPairValidator implements ConstraintValidator<ValidCryptoPair, String> {

	private static final List<String> ALLOWED_CRYPTO_PAIRS = Arrays.asList("BTCUSDT", "ETHUSDT");

	@Override
	public boolean isValid(String cryptoPair, ConstraintValidatorContext context) {
		// Check if cryptoPair is null or not in the allowed list
		return cryptoPair != null && ALLOWED_CRYPTO_PAIRS.contains(cryptoPair);
	}

}
