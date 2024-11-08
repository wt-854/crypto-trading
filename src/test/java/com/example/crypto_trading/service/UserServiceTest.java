package com.example.crypto_trading.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.crypto_trading.exceptions.InsufficientBalanceException;
import com.example.crypto_trading.exceptions.UserNotFoundException;
import com.example.crypto_trading.model.CryptoWallet;
import com.example.crypto_trading.model.User;
import com.example.crypto_trading.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testBuyCryptoValidPurchase() throws UserNotFoundException, InsufficientBalanceException {
		// Mock data
		Long userId = 1L;
		String cryptoPair = "BTCUSDT";
		BigDecimal usdtAmount = new BigDecimal("1000.00");
		BigDecimal cryptoAmount = new BigDecimal("0.1");

		User user = new User();
		user.setId(userId);
		user.setCryptoWallet(new CryptoWallet());
		user.getCryptoWallet().setUsdtBalance(new BigDecimal("5000.00"));
		user.getCryptoWallet().setBtcusdtBalance(BigDecimal.ZERO);

		when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);

		User updatedUser = userService.buyCrypto(userId, cryptoPair, usdtAmount, cryptoAmount);

		assertEquals(new BigDecimal("4000.00"), updatedUser.getCryptoWallet().getUsdtBalance());
		assertEquals(new BigDecimal("0.1"), updatedUser.getCryptoWallet().getBtcusdtBalance());
		verify(userRepository, times(1)).save(user);
	}

	@Test
	public void testBuyCryptoInsufficientBalance() {
		// Mock data
		Long userId = 1L;
		String cryptoPair = "BTCUSDT";
		BigDecimal usdtAmount = new BigDecimal("1000.00");
		BigDecimal cryptoAmount = new BigDecimal("0.1");

		User user = new User();
		user.setId(userId);
		user.setCryptoWallet(new CryptoWallet());
		user.getCryptoWallet().setUsdtBalance(new BigDecimal("500.00"));

		when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

		InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
			userService.buyCrypto(userId, cryptoPair, usdtAmount, cryptoAmount);
		});

		assertEquals("Insufficient USDT balance", exception.getMessage());
	}

	@Test
	public void testBuyCryptoInvalidCryptoAmount() {
		// Mock data
		Long userId = 1L;
		String cryptoPair = "BTCUSDT";
		BigDecimal usdtAmount = new BigDecimal("1000.00");
		BigDecimal cryptoAmount = BigDecimal.ZERO;

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.buyCrypto(userId, cryptoPair, usdtAmount, cryptoAmount);
		});

		assertEquals("Invalid crypto amount purchased", exception.getMessage());
	}

	@Test
	public void testSellCryptoValidSale() throws UserNotFoundException, InsufficientBalanceException {
		// Mock data
		Long userId = 1L;
		String cryptoPair = "BTCUSDT";
		BigDecimal usdtAmount = new BigDecimal("1000.00");
		BigDecimal cryptoAmount = new BigDecimal("0.1");

		User user = new User();
		user.setId(userId);
		user.setCryptoWallet(new CryptoWallet());
		user.getCryptoWallet().setUsdtBalance(new BigDecimal("5000.00"));
		user.getCryptoWallet().setBtcusdtBalance(new BigDecimal("1.0"));

		when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);

		User updatedUser = userService.sellCrypto(userId, cryptoPair, usdtAmount, cryptoAmount);

		assertEquals(new BigDecimal("6000.00"), updatedUser.getCryptoWallet().getUsdtBalance());
		assertEquals(new BigDecimal("0.9"), updatedUser.getCryptoWallet().getBtcusdtBalance());
		verify(userRepository, times(1)).save(user);
	}

	@Test
	public void testSellCryptoInsufficientCryptoBalance() {
		// Mock data
		Long userId = 1L;
		String cryptoPair = "BTCUSDT";
		BigDecimal usdtAmount = new BigDecimal("1000.00");
		BigDecimal cryptoAmount = new BigDecimal("1.1");

		User user = new User();
		user.setId(userId);
		user.setCryptoWallet(new CryptoWallet());
		user.getCryptoWallet().setUsdtBalance(new BigDecimal("5000.00"));
		user.getCryptoWallet().setBtcusdtBalance(new BigDecimal("1.0"));

		when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

		InsufficientBalanceException exception = assertThrows(InsufficientBalanceException.class, () -> {
			userService.sellCrypto(userId, cryptoPair, usdtAmount, cryptoAmount);
		});

		assertEquals("Insufficient BTCUSDT balance", exception.getMessage());
	}

	@Test
	public void testSellCryptoInvalidCryptoAmount() {
		// Mock data
		Long userId = 1L;
		String cryptoPair = "BTCUSDT";
		BigDecimal usdtAmount = new BigDecimal("1000.00");
		BigDecimal cryptoAmount = BigDecimal.ZERO;

		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			userService.sellCrypto(userId, cryptoPair, usdtAmount, cryptoAmount);
		});

		assertEquals("Invalid crypto amount sold", exception.getMessage());
	}

}
