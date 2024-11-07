package com.example.crypto_trading.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crypto_trading.dto.TransactionRequest;
import com.example.crypto_trading.exceptions.InsufficientBalanceException;
import com.example.crypto_trading.exceptions.UserNotFoundException;
import com.example.crypto_trading.model.Transaction;
import com.example.crypto_trading.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public Transaction createTransaction(@RequestBody @Valid TransactionRequest transactionRequest)
			throws UserNotFoundException, InsufficientBalanceException {
		return transactionService.createTransaction(transactionRequest);
	}

	@GetMapping("/{userId}")
	public Page<Transaction> getTransactions(@PathVariable Long userId,
			@RequestParam(value = "startDate",
					required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
			@RequestParam(value = "endDate",
					required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
			@RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

		return transactionService.getTransactionsByUserId(userId, startDate, endDate, sortOrder, page, pageSize);
	}

}
