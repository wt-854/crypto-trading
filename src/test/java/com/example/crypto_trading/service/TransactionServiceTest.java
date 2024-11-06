package com.example.crypto_trading.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.crypto_trading.dto.TransactionRequest;
import com.example.crypto_trading.enums.TransactionType;
import com.example.crypto_trading.exceptions.InsufficientBalanceException;
import com.example.crypto_trading.exceptions.UserNotFoundException;
import com.example.crypto_trading.model.Price;
import com.example.crypto_trading.model.Transaction;
import com.example.crypto_trading.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceTest {

	@Mock
	private UserService userService;

	@Mock
	private PriceService priceService;

	@Mock
	private TransactionRepository transactionRepository;

	@InjectMocks
	private TransactionService transactionService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@Transactional
	public void testCreateTransactionValidBuy() throws UserNotFoundException, InsufficientBalanceException {
		// Mock data
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setUserId(1L);
		transactionRequest.setCryptoPair("BTCUSDT");
		transactionRequest.setAmount(new BigDecimal("0.1"));
		transactionRequest.setTransactionType(TransactionType.BUY);

		Price price = new Price();
		price.setAskPrice(new BigDecimal("50000.00"));

		Transaction transaction = new Transaction();
		transaction.setUserId(1L);
		transaction.setAmount(new BigDecimal("0.1"));
		transaction.setCryptoPair("BTCUSDT");
		transaction.setTransactionType(TransactionType.BUY);
		transaction.setPrice(new BigDecimal("50000.00"));
		transaction.setTimestamp(LocalDateTime.now());

		// Mock the service methods
		when(priceService.getLatestPriceByCryptoPair("BTCUSDT")).thenReturn(price);
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		// Call the service method
		Transaction createdTransaction = transactionService.createTransaction(transactionRequest);

		// Verify the result
		assertEquals(transactionRequest.getUserId(), createdTransaction.getUserId());
		assertEquals(transactionRequest.getAmount(), createdTransaction.getAmount());
		assertEquals(transactionRequest.getCryptoPair(), createdTransaction.getCryptoPair());
		assertEquals(transactionRequest.getTransactionType(), createdTransaction.getTransactionType());
		assertEquals(price.getAskPrice(), createdTransaction.getPrice());
		verify(userService, times(1)).buyCrypto(eq(1L), eq("BTCUSDT"), any(BigDecimal.class),
				eq(new BigDecimal("0.1")));
		verify(transactionRepository, times(1)).save(any(Transaction.class));
	}

	@Test
	@Transactional
	public void testCreateTransactionValidSell() throws UserNotFoundException, InsufficientBalanceException {
		// Mock data
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setUserId(1L);
		transactionRequest.setCryptoPair("BTCUSDT");
		transactionRequest.setAmount(new BigDecimal("0.1"));
		transactionRequest.setTransactionType(TransactionType.SELL);

		Price price = new Price();
		price.setBidPrice(new BigDecimal("50000.00"));

		Transaction transaction = new Transaction();
		transaction.setUserId(1L);
		transaction.setAmount(new BigDecimal("0.1"));
		transaction.setCryptoPair("BTCUSDT");
		transaction.setTransactionType(TransactionType.SELL);
		transaction.setPrice(new BigDecimal("50000.00"));
		transaction.setTimestamp(LocalDateTime.now());

		// Mock the service methods
		when(priceService.getLatestPriceByCryptoPair("BTCUSDT")).thenReturn(price);
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		// Call the service method
		Transaction createdTransaction = transactionService.createTransaction(transactionRequest);

		// Verify the result
		assertEquals(transactionRequest.getUserId(), createdTransaction.getUserId());
		assertEquals(transactionRequest.getAmount(), createdTransaction.getAmount());
		assertEquals(transactionRequest.getCryptoPair(), createdTransaction.getCryptoPair());
		assertEquals(transactionRequest.getTransactionType(), createdTransaction.getTransactionType());
		assertEquals(price.getBidPrice(), createdTransaction.getPrice());
		verify(userService, times(1)).sellCrypto(eq(1L), eq("BTCUSDT"), any(BigDecimal.class),
				eq(new BigDecimal("0.1")));
		verify(transactionRepository, times(1)).save(any(Transaction.class));
	}

	@Test
	@Transactional
	public void testCreateTransactionInsufficientBalance() throws UserNotFoundException, InsufficientBalanceException {
		// Mock data
		TransactionRequest transactionRequest = new TransactionRequest();
		transactionRequest.setUserId(1L);
		transactionRequest.setCryptoPair("BTCUSDT");
		transactionRequest.setAmount(new BigDecimal("0.1"));
		transactionRequest.setTransactionType(TransactionType.BUY);

		Price price = new Price();
		price.setAskPrice(new BigDecimal("50000.00"));

		// Mock the service methods to throw InsufficientBalanceException
		when(priceService.getLatestPriceByCryptoPair("BTCUSDT")).thenReturn(price);
		doThrow(new InsufficientBalanceException("Insufficient USDT balance")).when(userService)
			.buyCrypto(eq(1L), eq("BTCUSDT"), any(BigDecimal.class), eq(new BigDecimal("0.1")));

		// Call the service method and expect an exception
		InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
			transactionService.createTransaction(transactionRequest);
		});

		// Verify the exception message
		assertEquals("Insufficient USDT balance", exception.getMessage());
		verify(transactionRepository, never()).save(any(Transaction.class));
	}

}