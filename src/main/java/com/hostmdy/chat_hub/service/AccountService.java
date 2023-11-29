package com.hostmdy.chat_hub.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.chat_hub.domain.Account;

public interface AccountService {
	
	Account saveUserAccount(Account account);
	
	List<Account> getAllAccounts();
	
	Optional<Account> getAccountById(Long id);
	
	Optional<Account> getAccountByEmail(String email);
	
	List<Account> getAccountByNickName(String nickName);
	
	void deleteAccount(Long id);
	
}
