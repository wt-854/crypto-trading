package com.example.crypto_trading.exceptions;

public class InvalidTransactionTypeException extends Exception {

	public InvalidTransactionTypeException() {
		super("Invalid transaction type. Expecting either BUY or SELL");
	}

}
