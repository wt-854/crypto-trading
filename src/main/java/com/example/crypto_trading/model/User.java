package com.example.crypto_trading.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "crypto_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	@NotNull(message = "USDT Balance must not be empty")
	@DecimalMin(value = "0.0", inclusive = true, message = "USDT Balance cannot be negative")
	private BigDecimal usdtBalance = new BigDecimal("50000.00");

	@NotNull(message = "BTCUSDT Balance must not be empty")
	@DecimalMin(value = "0.0", inclusive = true, message = "BTCUSDT Balance cannot be negative")
	private BigDecimal btcusdtBalance = BigDecimal.ZERO;

	@NotNull(message = "ETHUSDT Balance must not be empty")
	@DecimalMin(value = "0.0", inclusive = true, message = "ETHUSDT Balance cannot be negative")
	private BigDecimal ethusdtBalance = BigDecimal.ZERO;

}
