package com.hostmdy.chat_hub.tempory_bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.hostmdy.chat_hub.domain.Message;

public class ChatDisplay {
	
	private Long id;
	private String chatName;
	
	private Long imageAccountId;
	private String lastMessage;
	private int newMessages;
	
	private boolean isProfileNull;
	
	private boolean isBlocked;
	private Long blockerId;
	
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	
	public ChatDisplay() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChatName() {
		return chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public Long getImageAccountId() {
		return imageAccountId;
	}

	public void setImageAccountId(Long imageAccountId) {
		this.imageAccountId = imageAccountId;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Long getBlockerId() {
		return blockerId;
	}

	public void setBlockerId(Long blockerId) {
		this.blockerId = blockerId;
	}

	public int getNewMessages() {
		return newMessages;
	}

	public void setNewMessages(int newMessages) {
		this.newMessages = newMessages;
	}

	public boolean isProfileNull() {
		return isProfileNull;
	}

	public void setProfileNull(boolean isProfileNull) {
		this.isProfileNull = isProfileNull;
	}

	
	

}
