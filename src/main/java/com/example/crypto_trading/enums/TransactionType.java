package com.example.crypto_trading.enums;

import com.example.crypto_trading.exceptions.InvalidTransactionTypeException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum TransactionType {

	BUY, SELL;

	@JsonCreator
	public static TransactionType fromString(String value) throws InvalidTransactionTypeException {
		try {
			return TransactionType.valueOf(value.toUpperCase());
		}
		catch (IllegalArgumentException ex) {
			throw new InvalidTransactionTypeException(
					"Invalid transaction type: " + value + ". Valid values are BUY, SELL.");
		}
	}

}
