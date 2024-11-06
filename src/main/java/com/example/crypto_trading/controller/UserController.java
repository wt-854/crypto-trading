package com.example.crypto_trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.crypto_trading.dto.UserWalletResponse;
import com.example.crypto_trading.exceptions.UserNotFoundException;
import com.example.crypto_trading.model.User;
import com.example.crypto_trading.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{userId}/balance")
	public UserWalletResponse getUserBalance(@PathVariable Long userId) throws UserNotFoundException {
		User user = userService.getUserById(userId);
		UserWalletResponse userWallet = new UserWalletResponse(user.getUsername(), user.getUsdtBalance(),
				user.getBtcusdtBalance(), user.getEthusdtBalance());
		return userWallet;
	}

}
