package com.example.crypto_trading.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crypto_trading.dto.TransactionRequest;
import com.example.crypto_trading.exceptions.InsufficientBalanceException;
import com.example.crypto_trading.exceptions.UserNotFoundException;
import com.example.crypto_trading.model.Transaction;
import com.example.crypto_trading.service.TransactionService;

import jakarta.transaction.InvalidTransactionException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public Transaction createTransaction(@RequestBody @Valid TransactionRequest transactionRequest)
			throws UserNotFoundException, InvalidTransactionException, InsufficientBalanceException {

		System.out.println("POST createTransaction");
		return transactionService.createTransaction(transactionRequest);
	}

	@GetMapping("/{userId}")
	public List<Transaction> getTransactions(@PathVariable Long userId) {
		return transactionService.getTransactionsByUserId(userId);
	}

}
