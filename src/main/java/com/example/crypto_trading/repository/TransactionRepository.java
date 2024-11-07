package com.example.crypto_trading.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crypto_trading.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	Page<Transaction> findByUserId(Long userId, Pageable pageable);

	Page<Transaction> findByUserIdAndTimestampBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate,
			Pageable pageable);

}
