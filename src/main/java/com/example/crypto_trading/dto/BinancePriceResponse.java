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
public class BinancePriceResponse {

	/*
	 * Sample response [ { "symbol": "ETHUSDT", "bidPrice": "2589.60000000", "bidQty":
	 * "16.78240000", "askPrice": "2589.61000000", "askQty": "39.64290000" }, { "symbol":
	 * "BTCUSDT", "bidPrice": "74508.83000000", "bidQty": "1.18818000", "askPrice":
	 * "74508.84000000", "askQty": "3.97432000" } ]
	 */

	private String symbol;

	private BigDecimal bidPrice;

	private BigDecimal askPrice;

}
