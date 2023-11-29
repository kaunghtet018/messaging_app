package com.hostmdy.chat_hub.service;

import java.util.Optional;

import com.hostmdy.chat_hub.domain.Message;

public interface MessageService {
	
	Message saveMessage(Message message);
	
	Optional<Message> getMessageByMessageId(Long id);
	
	void deleteMessage(Long id);

}
