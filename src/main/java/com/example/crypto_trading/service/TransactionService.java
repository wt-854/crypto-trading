package com.example.crypto_trading.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<Transaction> getTransactionsByUserId(Long userId) {
		return transactionRepository.findByUserId(userId);
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
