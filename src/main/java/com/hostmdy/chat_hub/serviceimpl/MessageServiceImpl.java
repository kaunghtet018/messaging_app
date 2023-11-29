package com.hostmdy.chat_hub.serviceimpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.chat_hub.domain.Message;
import com.hostmdy.chat_hub.repository.MessageRepository;
import com.hostmdy.chat_hub.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	
	private final MessageRepository messageRepository;
	
	

	public MessageServiceImpl(MessageRepository messageRepository) {
		super();
		this.messageRepository = messageRepository;
	}

	@Override
	public Message saveMessage(Message message) {
		// TODO Auto-generated method stub
		return messageRepository.save(message);
	}

	@Override
	public Optional<Message> getMessageByMessageId(Long id) {
		// TODO Auto-generated method stub
		return messageRepository.findById(id);
	}

	@Override
	public void deleteMessage(Long id) {
		// TODO Auto-generated method stub

	}

}
