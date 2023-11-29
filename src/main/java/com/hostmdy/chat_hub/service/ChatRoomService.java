package com.hostmdy.chat_hub.service;

import java.util.List;
import java.util.Optional;

import com.hostmdy.chat_hub.domain.ChatRoom;

public interface ChatRoomService {
	
	ChatRoom saveChatRoom(ChatRoom chatRoom);
	
	List<ChatRoom> getAllChatRoomsByUserId(Long userId);
	
	Optional<ChatRoom> getChatRoomByChatRoomId(Long chatRoomId);
	
	void deleteChatRoom(Long id);

}
