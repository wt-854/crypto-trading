package com.example.crypto_trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableScheduling
public class CryptoTradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoTradingApplication.class, args);
	}

}
