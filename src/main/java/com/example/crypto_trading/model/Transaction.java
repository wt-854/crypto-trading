package com.example.crypto_trading.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.crypto_trading.enums.TransactionType;
import com.example.crypto_trading.validation.ValidCryptoPair;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "User ID must not be empty")
	private Long userId;

	@NotNull
	@ValidCryptoPair
	private String cryptoPair;

	@NotNull(message = "Amount must not be empty")
	@Positive(message = "Amount must be positive")
	private BigDecimal amount;

	@NotNull(message = "Price must not be empty")
	@Positive(message = "Price must be positive")
	private BigDecimal price;

	@NotNull(message = "TransactionType must not be empty")
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType; // "BUY" or "SELL"

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
	private LocalDateTime timestamp;

}
