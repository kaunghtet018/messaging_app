package com.hostmdy.chat_hub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.chat_hub.domain.Account;


public interface AccountRepository extends CrudRepository<Account, Long> {

	Optional<Account> findByEmail(String email);
	
	List<Account> findByNickNameContainingIgnoreCase(String nickName);
	
}
