package com.example.crypto_trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crypto_trading.model.Price;
import com.example.crypto_trading.service.PriceService;

@RestController
@RequestMapping("/prices")
public class PriceController {

	@Autowired
	private PriceService priceService;

	@GetMapping("/{cryptoPair}")
	public Price getLatestPrice(@PathVariable String cryptoPair) {
		return priceService.getLatestPriceByCryptoPair(cryptoPair);
	}

}
