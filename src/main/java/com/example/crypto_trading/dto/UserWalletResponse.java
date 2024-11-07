package com.example.crypto_trading.dto;

import com.example.crypto_trading.model.CryptoWallet;

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
public class UserWalletResponse {

	private String username;

	private CryptoWallet cryptoWallet;

}
