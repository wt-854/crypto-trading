package com.example.crypto_trading.dto;

import java.math.BigDecimal;

import com.example.crypto_trading.enums.TransactionType;
import com.example.crypto_trading.validation.ValidCryptoPair;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionRequest {

	@NotNull(message = "User ID must not be empty")
	private Long userId;

	@NotNull
	@ValidCryptoPair
	private String cryptoPair;

	@NotNull(message = "Amount must not be empty")
	@Positive(message = "Amount must be positive")
	private BigDecimal amount;

	@NotNull(message = "TransactionType must not be empty")
	private TransactionType transactionType; // "BUY" or "SELL"

}
