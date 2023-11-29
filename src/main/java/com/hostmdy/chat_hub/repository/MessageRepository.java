package com.hostmdy.chat_hub.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.chat_hub.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Long>{

}
