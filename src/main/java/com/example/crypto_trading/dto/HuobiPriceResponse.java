package com.example.crypto_trading.dto;

import java.math.BigDecimal;
import java.util.List;

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
public class HuobiPriceResponse {

	/*
	 * Sample response { "data": [ { "symbol": "btcusdt", "open": 69610.18, "high":
	 * 75380.0, "low": 68739.64, "close": 74559.73, "amount": 8136.519549164991, "vol":
	 * 5.783682396050508E8, "count": 9964881, "bid": 74559.6, "bidSize": 0.571046, "ask":
	 * 74559.61, "askSize": 0.00202 }, { "symbol": "ethusdt", "open": 2441.18, "high":
	 * 2640.73, "low": 2401.76, "close": 2591.66, "amount": 51573.496896842626, "vol":
	 * 1.291459417529854E8, "count": 84730, "bid": 2592.22, "bidSize": 0.5342, "ask":
	 * 2592.23, "askSize": 5.7749 } ] }
	 */

	private List<HuobiTicker> data;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class HuobiTicker {

		private String symbol;

		private BigDecimal bid;

		private BigDecimal ask;

	}

}
