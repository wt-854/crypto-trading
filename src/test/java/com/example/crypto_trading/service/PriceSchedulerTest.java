package com.example.crypto_trading.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.crypto_trading.dto.BinancePriceResponse;
import com.example.crypto_trading.dto.HuobiPriceResponse;
import com.example.crypto_trading.dto.HuobiPriceResponse.HuobiTicker;
import com.example.crypto_trading.model.Price;

@SpringBootTest
public class PriceSchedulerTest {

	@Mock
	private PriceService priceService;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private PriceScheduler priceScheduler;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFetchAndStorePrices() {
		// Mock Binance response
		BinancePriceResponse binancePriceResponse1 = new BinancePriceResponse();
		binancePriceResponse1.setSymbol("BTCUSDT");
		binancePriceResponse1.setBidPrice(new BigDecimal("50000.00"));
		binancePriceResponse1.setAskPrice(new BigDecimal("50001.00"));

		BinancePriceResponse binancePriceResponse2 = new BinancePriceResponse();
		binancePriceResponse2.setSymbol("ETHUSDT");
		binancePriceResponse2.setBidPrice(new BigDecimal("3000.00"));
		binancePriceResponse2.setAskPrice(new BigDecimal("3001.00"));

		BinancePriceResponse[] binancePrices = { binancePriceResponse1, binancePriceResponse2 };
		ResponseEntity<BinancePriceResponse[]> binanceResponse = new ResponseEntity<>(binancePrices, HttpStatus.OK);

		// Mock Huobi response
		HuobiTicker huobiTicker1 = new HuobiTicker();
		huobiTicker1.setSymbol("BTCUSDT");
		huobiTicker1.setBid(new BigDecimal("50002.00"));
		huobiTicker1.setAsk(new BigDecimal("49999.00"));

		HuobiTicker huobiTicker2 = new HuobiTicker();
		huobiTicker2.setSymbol("ETHUSDT");
		huobiTicker2.setBid(new BigDecimal("3002.00"));
		huobiTicker2.setAsk(new BigDecimal("2999.00"));

		List<HuobiTicker> huobiTickers = Arrays.asList(huobiTicker1, huobiTicker2);
		HuobiPriceResponse huobiPriceResponse = new HuobiPriceResponse();
		huobiPriceResponse.setData(huobiTickers);
		ResponseEntity<HuobiPriceResponse> huobiResponse = new ResponseEntity<>(huobiPriceResponse, HttpStatus.OK);

		// Mock RestTemplate responses
		when(restTemplate.getForEntity("https://api.binance.com/api/v3/ticker/bookTicker",
				BinancePriceResponse[].class))
			.thenReturn(binanceResponse);
		when(restTemplate.getForEntity("https://api.huobi.pro/market/tickers", HuobiPriceResponse.class))
			.thenReturn(huobiResponse);

		// Call the method to be tested
		priceScheduler.fetchAndStorePrices();

		// Capture the arguments passed to savePrice
		ArgumentCaptor<Price> priceCaptor = ArgumentCaptor.forClass(Price.class);
		verify(priceService, times(2)).savePrice(priceCaptor.capture());

		List<Price> capturedPrices = priceCaptor.getAllValues();

		// Verify the properties of the captured Price objects
		for (Price price : capturedPrices) {
			if (price.getCryptoPair().equals("BTCUSDT")) {
				// highest: binance 50000.00 vs huobi 50002.00
				assertEquals(new BigDecimal("50002.00"), price.getBidPrice());
				// lowest: binance 50001.00 vs huobi 49999.00
				assertEquals(new BigDecimal("49999.00"), price.getAskPrice());
			}
			else if (price.getCryptoPair().equals("ETHUSDT")) {
				// highest: binance 3000.00 vs huobi 3002.00
				assertEquals(new BigDecimal("3002.00"), price.getBidPrice());
				// lowest: binance 3001.00 vs huobi 2999.00
				assertEquals(new BigDecimal("2999.00"), price.getAskPrice());
			}
		}
	}

}
