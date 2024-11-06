package com.example.crypto_trading.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crypto_trading.dto.TransactionRequest;
import com.example.crypto_trading.enums.TransactionType;
import com.example.crypto_trading.exceptions.InsufficientBalanceException;
import com.example.crypto_trading.exceptions.UserNotFoundException;
import com.example.crypto_trading.model.Price;
import com.example.crypto_trading.model.Transaction;
import com.example.crypto_trading.repository.TransactionRepository;

import jakarta.transaction.InvalidTransactionException;

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

	public Transaction createTransaction(TransactionRequest transactionRequest)
			throws UserNotFoundException, InsufficientBalanceException, InvalidTransactionException {

		System.out.println("inside createTransaction");
		Price price = priceService.getLatestPriceByCryptoPair(transactionRequest.getCryptoPair());
		System.out.println("price: " + price.toString());
		// Update User Balance
		if (transactionRequest.getTransactionType().equals(TransactionType.BUY)) {
			userService.buyCrypto(transactionRequest.getUserId(), transactionRequest.getCryptoPair(),
					transactionRequest.getAmount().multiply(price.getAskPrice()), transactionRequest.getAmount());

		}
		else if (transactionRequest.getTransactionType().equals(TransactionType.SELL)) {
			throw new InvalidTransactionException();
		}
		else {
			throw new InvalidTransactionException();
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
