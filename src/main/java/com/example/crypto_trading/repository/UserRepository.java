package com.example.crypto_trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crypto_trading.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
