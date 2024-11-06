package com.example.crypto_trading.dto;

import java.math.BigDecimal;

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
public class UserWalletResponse {

	private String username;

	private BigDecimal usdtBalance;

	private BigDecimal btcusdtBalance;

	private BigDecimal ethusdtBalance;

}
