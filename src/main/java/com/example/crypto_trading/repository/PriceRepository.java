package com.example.crypto_trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crypto_trading.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {

	Price findFirstByCryptoPairOrderByTimestampDesc(String cryptoPair);

}
