package com.hostmdy.chat_hub.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hostmdy.chat_hub.domain.Account;
import com.hostmdy.chat_hub.domain.ChatRoom;
import com.hostmdy.chat_hub.repository.AccountRepository;
import com.hostmdy.chat_hub.repository.ChatRoomRepository;
import com.hostmdy.chat_hub.service.ChatRoomService;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{
	
	private final AccountRepository accountRepository;
	private final ChatRoomRepository chatRoomRepository;

	public ChatRoomServiceImpl(AccountRepository accountRepository, ChatRoomRepository chatRoomRepository) {
		super();
		this.accountRepository = accountRepository;
		this.chatRoomRepository = chatRoomRepository;
	}

	@Override
	public ChatRoom saveChatRoom(ChatRoom chatRoom) {
		// TODO Auto-generated method stub
		return chatRoomRepository.save(chatRoom);
	}

	@Override
	public List<ChatRoom> getAllChatRoomsByUserId(Long userId) {
		// TODO Auto-generated method stub
		Optional<Account> accountOpt = accountRepository.findById(userId);
		if(accountOpt.isEmpty()) {
			return new ArrayList<>();
		}
		
		
		return accountOpt.get().getChatRoomList();
	}

	@Override
	public Optional<ChatRoom> getChatRoomByChatRoomId(Long chatRoomId) {
		// TODO Auto-generated method stub
		return chatRoomRepository.findById(chatRoomId);
	}

	@Override
	public void deleteChatRoom(Long id) {
		// TODO Auto-generated method stub
		
	}

}
