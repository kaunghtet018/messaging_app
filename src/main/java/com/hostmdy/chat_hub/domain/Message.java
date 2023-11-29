package com.hostmdy.chat_hub.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long senderId;
	private boolean isRead;
	private String type;
	
	@Column(length = 1000)
	private String content;
	
	@Lob
	private byte[] fileContent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private ChatRoom chatRoom;
	
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	
	public Message() {
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

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public ChatRoom getChatRoom() {
		return chatRoom;
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
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

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", senderId=" + senderId + ", isRead=" + isRead + ", type=" + type + ", content="
				+ content + ", fileContent=" + Arrays.toString(fileContent) + ", createAt=" + createAt + ", updateAt="
				+ updateAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(fileContent);
		result = prime * result + Objects.hash(content, createAt, id, isRead, senderId, type, updateAt);
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
		Message other = (Message) obj;
		return Objects.equals(content, other.content) && Objects.equals(createAt, other.createAt)
				&& Arrays.equals(fileContent, other.fileContent) && Objects.equals(id, other.id)
				&& isRead == other.isRead && Objects.equals(senderId, other.senderId)
				&& Objects.equals(type, other.type) && Objects.equals(updateAt, other.updateAt);
	}

	
	
	
}
