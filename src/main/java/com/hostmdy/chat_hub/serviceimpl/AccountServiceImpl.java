package com.hostmdy.chat_hub.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.chat_hub.domain.Account;
import com.hostmdy.chat_hub.repository.AccountRepository;
import com.hostmdy.chat_hub.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	
	private final AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}

	@Override
	public Account saveUserAccount(Account account) {
		// TODO Auto-generated method stub
		return accountRepository.save(account);
	}

	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return (List<Account>) accountRepository.findAll();
	}

	@Override
	public Optional<Account> getAccountById(Long id) {
		// TODO Auto-generated method stub
		return accountRepository.findById(id);
	}

	@Override
	public Optional<Account> getAccountByEmail(String email) {
		// TODO Auto-generated method stub
		return accountRepository.findByEmail(email);
	}

	@Override
	public void deleteAccount(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Account> getAccountByNickName(String nickName) {
		// TODO Auto-generated method stub
		return accountRepository.findByNickNameContainingIgnoreCase(nickName);
	}

}
