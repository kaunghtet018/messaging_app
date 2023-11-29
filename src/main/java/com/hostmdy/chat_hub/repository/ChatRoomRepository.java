package com.hostmdy.chat_hub.repository;

import org.springframework.data.repository.CrudRepository;

import com.hostmdy.chat_hub.domain.ChatRoom;

public interface ChatRoomRepository  extends CrudRepository<ChatRoom, Long>{

}
