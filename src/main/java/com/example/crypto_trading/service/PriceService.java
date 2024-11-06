package com.example.crypto_trading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crypto_trading.model.Price;
import com.example.crypto_trading.repository.PriceRepository;

@Service
public class PriceService {

	@Autowired
	private PriceRepository priceRepository;

	public Price getLatestPriceByCryptoPair(String cryptoPair) {
		return priceRepository.findFirstByCryptoPairOrderByTimestampDesc(cryptoPair);
	}

	public void savePrice(Price price) {
		priceRepository.save(price);
	}

}
