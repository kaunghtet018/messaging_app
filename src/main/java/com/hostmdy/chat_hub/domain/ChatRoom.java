package com.hostmdy.chat_hub.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class ChatRoom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String chatName;
	
	@Lob
	private byte[] chatProfile;
	private boolean isBlocked;
	private Long blockerId;
	
	@ManyToMany(mappedBy = "chatRoomList",fetch = FetchType.EAGER)
	private List<Account> accountList = new ArrayList<>();
	@OneToMany(mappedBy = "chatRoom",fetch = FetchType.EAGER)
	private List<Message> messageList = new ArrayList<>();
	
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	
	public ChatRoom() {
		// TODO Auto-generated constructor stub
	}
	
	@PrePersist
	void onCreate() {
		createAt = LocalDateTime.now();
	}
	
	@PreUpdate
	void onUpdate () {
		updateAt = LocalDateTime.now();
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



	public byte[] getChatProfile() {
		return chatProfile;
	}



	public void setChatProfile(byte[] chatProfile) {
		this.chatProfile = chatProfile;
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



	public List<Account> getAccountList() {
		return accountList;
	}



	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}



	public List<Message> getMessageList() {
		return messageList;
	}



	public void setMessageList(List<Message> messageList) {
		this.messageList = messageList;
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



	@Override
	public String toString() {
		return "ChatRoom [id=" + id + ", chatName=" + chatName + ", chatProfile=" + Arrays.toString(chatProfile)
				+ ", isBlocked=" + isBlocked + ", blockerId=" + blockerId + ", createAt=" + createAt + ", updateAt="
				+ updateAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(chatProfile);
		result = prime * result + Objects.hash(blockerId, chatName, createAt, id, isBlocked, updateAt);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatRoom other = (ChatRoom) obj;
		return Objects.equals(blockerId, other.blockerId) && Objects.equals(chatName, other.chatName)
				&& Arrays.equals(chatProfile, other.chatProfile) && Objects.equals(createAt, other.createAt)
				&& Objects.equals(id, other.id) && isBlocked == other.isBlocked
				&& Objects.equals(updateAt, other.updateAt);
	}

	

	
	

}
