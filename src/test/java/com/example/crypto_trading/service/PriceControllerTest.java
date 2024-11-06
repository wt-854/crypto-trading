package com.example.crypto_trading.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.crypto_trading.controller.PriceController;
import com.example.crypto_trading.model.Price;

@WebMvcTest(PriceController.class)
public class PriceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PriceService priceService;

	@Test
	public void testGetLatestPriceValidCryptoPair() throws Exception {
		// Mock data
		String cryptoPair = "BTCUSDT";
		Price price = new Price();
		price.setCryptoPair(cryptoPair);
		price.setBidPrice(new BigDecimal("50000.00"));
		price.setAskPrice(new BigDecimal("50001.00"));
		price.setTimestamp(LocalDateTime.now());

		// Mock the service method
		when(priceService.getLatestPriceByCryptoPair(cryptoPair)).thenReturn(price);
		mockMvc.perform(get("/prices/" + cryptoPair)).andExpect(status().isOk());
	}

	@Test
	public void testGetLatestPriceInvalidCryptoPair() throws Exception {
		// Mock data
		String cryptoPair = "ABC";
		Price price = new Price();
		price.setCryptoPair(cryptoPair);
		price.setBidPrice(new BigDecimal("50000.00"));
		price.setAskPrice(new BigDecimal("50001.00"));
		price.setTimestamp(LocalDateTime.now());

		// Mock the service method
		when(priceService.getLatestPriceByCryptoPair(cryptoPair)).thenReturn(price);
		mockMvc.perform(get("/prices/" + cryptoPair)).andExpect(status().isBadRequest());

	}

}
