package com.example.crypto_trading.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crypto_trading.dto.TransactionRequest;
import com.example.crypto_trading.enums.TransactionType;
import com.example.crypto_trading.exceptions.InsufficientBalanceException;
import com.example.crypto_trading.exceptions.UserNotFoundException;
import com.example.crypto_trading.model.Price;
import com.example.crypto_trading.model.Transaction;
import com.example.crypto_trading.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private PriceService priceService;

	public Page<Transaction> getTransactionsByUserId(Long userId, LocalDateTime startDate, LocalDateTime endDate,
			String sortOrder, int page, int pageSize) {

		Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(Sort.Order.desc("timestamp"))
				: Sort.by(Sort.Order.asc("timestamp"));

		Pageable pageable = PageRequest.of(page, pageSize, sort);

		// If no date range, filter by userId
		if (startDate == null && endDate == null) {
			return transactionRepository.findByUserId(userId, pageable);
		}

		return transactionRepository.findByUserIdAndTimestampBetween(userId, startDate, endDate, pageable);
	}

	@Transactional
	public Transaction createTransaction(TransactionRequest transactionRequest)
			throws UserNotFoundException, InsufficientBalanceException {
		Price price = priceService.getLatestPriceByCryptoPair(transactionRequest.getCryptoPair());
		// Update User Balance
		if (transactionRequest.getTransactionType().equals(TransactionType.BUY)) {
			userService.buyCrypto(transactionRequest.getUserId(), transactionRequest.getCryptoPair(),
					transactionRequest.getAmount().multiply(price.getAskPrice()), transactionRequest.getAmount());

		}
		else if (transactionRequest.getTransactionType().equals(TransactionType.SELL)) {
			userService.sellCrypto(transactionRequest.getUserId(), transactionRequest.getCryptoPair(),
					transactionRequest.getAmount().multiply(price.getBidPrice()), transactionRequest.getAmount());
		}

		// Add new transaction
		Transaction transaction = new Transaction();
		transaction.setUserId(transactionRequest.getUserId());
		transaction.setAmount(transactionRequest.getAmount());
		transaction.setCryptoPair(transactionRequest.getCryptoPair());
		transaction.setTransactionType(transactionRequest.getTransactionType());
		transaction.setPrice(price.getAskPrice());
		transaction.setTimestamp(LocalDateTime.now());

		return transactionRepository.save(transaction);
	}

}
