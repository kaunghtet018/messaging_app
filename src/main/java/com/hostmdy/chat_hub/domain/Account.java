package com.hostmdy.chat_hub.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String firstName;
	private String lastName;
	private String nickName;
	private String email;
	private String password;
	private String role;
	
	@Lob
	private byte[] profile;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		    name = "account_chatroom",
		    joinColumns = @JoinColumn(name = "account_id"),
		    inverseJoinColumns = @JoinColumn(name = "chatroom_id")
		)
	private List<ChatRoom> chatRoomList = new ArrayList<>();

	
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	
	public Account() {
		super();
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public byte[] getProfile() {
		return profile;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
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

	public List<ChatRoom> getChatRoomList() {
		return chatRoomList;
	}

	public void setChatRoomList(List<ChatRoom> chatRoomList) {
		this.chatRoomList = chatRoomList;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nickName="
				+ nickName + ", email=" + email + ", password=" + password + ", role=" + role + ", profile="
				+ Arrays.toString(profile) + ", createAt=" + createAt + ", updateAt=" + updateAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(profile);
		result = prime * result
				+ Objects.hash(createAt, email, firstName, id, lastName, nickName, password, role, updateAt);
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
		Account other = (Account) obj;
		return Objects.equals(createAt, other.createAt) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(nickName, other.nickName)
				&& Objects.equals(password, other.password) && Arrays.equals(profile, other.profile)
				&& Objects.equals(role, other.role) && Objects.equals(updateAt, other.updateAt);
	}

	
	

}
