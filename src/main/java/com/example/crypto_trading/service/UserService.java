package com.example.crypto_trading.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crypto_trading.exceptions.InsufficientBalanceException;
import com.example.crypto_trading.exceptions.UserNotFoundException;
import com.example.crypto_trading.model.User;
import com.example.crypto_trading.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserById(Long userId) throws UserNotFoundException {
		return userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

	}

	public User buyCrypto(Long userId, String cryptoPair, BigDecimal usdtAmount, BigDecimal cryptoAmount)
			throws UserNotFoundException, InsufficientBalanceException {
		if (cryptoAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Invalid crypto amount purchased");
		}
		User user = getUserById(userId);
		if (user.getUsdtBalance().compareTo(usdtAmount) < 0) {
			throw new InsufficientBalanceException("Insufficient USDT balance");
		}
		user.setUsdtBalance(user.getUsdtBalance().subtract(usdtAmount));
		if (cryptoPair.equals("BTCUSDT")) {
			user.setBtcusdtBalance(user.getBtcusdtBalance().add(cryptoAmount));
		}
		else if (cryptoPair.equals("ETHUSDT")) {
			user.setEthusdtBalance(user.getEthusdtBalance().add(cryptoAmount));
		}

		return userRepository.save(user);
	}

}
