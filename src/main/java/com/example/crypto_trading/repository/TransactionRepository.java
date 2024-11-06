package com.example.crypto_trading.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crypto_trading.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByUserId(Long userId);

}
