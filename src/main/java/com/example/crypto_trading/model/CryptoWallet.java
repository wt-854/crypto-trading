package com.example.crypto_trading.model;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;
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
@Embeddable
public class CryptoWallet {

	@NotNull(message = "USDT Balance must not be empty")
	@DecimalMin(value = "0.0", inclusive = true, message = "USDT Balance cannot be negative")
	private BigDecimal usdtBalance = BigDecimal.ZERO;

	@NotNull(message = "BTCUSDT Balance must not be empty")
	@DecimalMin(value = "0.0", inclusive = true, message = "BTCUSDT Balance cannot be negative")
	private BigDecimal btcusdtBalance = BigDecimal.ZERO;

	@NotNull(message = "ETHUSDT Balance must not be empty")
	@DecimalMin(value = "0.0", inclusive = true, message = "ETHUSDT Balance cannot be negative")
	private BigDecimal ethusdtBalance = BigDecimal.ZERO;

}
