package com.example.crypto_trading.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.crypto_trading.dto.BinancePriceResponse;
import com.example.crypto_trading.dto.HuobiPriceResponse;
import com.example.crypto_trading.dto.HuobiPriceResponse.HuobiTicker;
import com.example.crypto_trading.model.Price;

@Service
public class PriceScheduler {

	@Autowired
	private PriceService priceService;

	@Autowired
	private RestTemplate restTemplate;

	@Scheduled(fixedRate = 10000) // 10 seconds
	public void fetchAndStorePrices() {
		// Fetch prices from Binance
		String binanceUrl = "https://api.binance.com/api/v3/ticker/bookTicker";
		ResponseEntity<BinancePriceResponse[]> binanceResponse = restTemplate.getForEntity(binanceUrl,
				BinancePriceResponse[].class);
		BinancePriceResponse[] binancePrices = binanceResponse.getBody();

		// Fetch prices from Huobi
		String huobiUrl = "https://api.huobi.pro/market/tickers";
		ResponseEntity<HuobiPriceResponse> huobiResponse = restTemplate.getForEntity(huobiUrl,
				HuobiPriceResponse.class);
		List<HuobiTicker> huobiPrices = huobiResponse.getBody().getData();

		// Store the best prices
		for (String cryptoPair : Arrays.asList("BTCUSDT", "ETHUSDT")) {
			BigDecimal bestBidPrice = BigDecimal.ZERO;
			BigDecimal bestAskPrice = BigDecimal.ZERO;

			for (BinancePriceResponse binancePrice : binancePrices) {
				if (binancePrice.getSymbol().equals(cryptoPair)) {
					bestBidPrice = binancePrice.getBidPrice();
					bestAskPrice = binancePrice.getAskPrice();
					break;
				}
			}

			for (HuobiTicker huobiPrice : huobiPrices) {
				if (huobiPrice.getSymbol().equals(cryptoPair)) {
					if (huobiPrice.getBid().compareTo(bestBidPrice) > 0) {
						bestBidPrice = huobiPrice.getBid();
					}
					if (huobiPrice.getAsk().compareTo(bestAskPrice) < 0) {
						bestAskPrice = huobiPrice.getAsk();
					}
					break;
				}
			}

			Price price = new Price();
			price.setCryptoPair(cryptoPair);
			price.setBidPrice(bestBidPrice);
			price.setAskPrice(bestAskPrice);
			price.setTimestamp(LocalDateTime.now());
			priceService.savePrice(price);
		}
	}

}
