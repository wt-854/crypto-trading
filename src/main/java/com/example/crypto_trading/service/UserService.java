package com.example.crypto_trading.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
