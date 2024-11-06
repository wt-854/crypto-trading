package com.example.crypto_trading.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.crypto_trading.validation.ValidCryptoPair;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ValidCryptoPair
	private String cryptoPair;

	@NotNull(message = "BidPrice must not be empty")
	@Positive(message = "BidPrice must be positive")
	private BigDecimal bidPrice;

	@NotNull(message = "AskPrice must not be empty")
	@Positive(message = "AskPrice must be positive")
	private BigDecimal askPrice;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
	private LocalDateTime timestamp;

}
