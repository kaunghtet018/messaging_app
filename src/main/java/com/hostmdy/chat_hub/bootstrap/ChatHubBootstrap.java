package com.hostmdy.chat_hub.bootstrap;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.hostmdy.chat_hub.crypto.PasswordEncoder;
import com.hostmdy.chat_hub.domain.Account;
import com.hostmdy.chat_hub.domain.ChatRoom;
import com.hostmdy.chat_hub.service.AccountService;
import com.hostmdy.chat_hub.service.ChatRoomService;

@Component
public class ChatHubBootstrap implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	ChatRoomService chatRoomService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		String commonRes = "C:/Users/ZarMaNi/Desktop/projecttest/photos/";
		addAccount("Kaung", "Htet", "Kaung Htet", "kaung@gmail.com", "1234", commonRes+"image6.jpg");
		addAccount("Su", "Su", "SuSu", "susu@gmail.com", "1234", "C:/Users/ZarMaNi/Desktop/projecttest/sendphotos/9cf6d4c1-6642-4b4f-b2fc-fb15ce6c8230.jpg");
		
		addAccount("Aung", "Aung", "aungGyi123", "aung@gmail.com", "1234", "C:/Users/ZarMaNi/Desktop/images/batman.jpg");
		
		addAccount("La Won", "Lay", "La Won Lay", "lawon@gmail.com", "1234", "C:/Users/ZarMaNi/Desktop/images/download (1).jpg");
		
		addAccount("Hsu Lae", "Mon", "Hsu Lae", "hsulae@gmail.com", "1234", commonRes+"image7.jpg");
		
		
	}
	
	private void addAccount(String firstName,String lastName,String nickName, String email, String password, String path) {
		Account account = new Account();
		account.setFirstName(firstName);
		account.setLastName(lastName);
		account.setNickName(nickName);
		account.setEmail(email);
		try {
			account.setPassword(PasswordEncoder.encode(password));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		account.setProfile(getByteArrayFromImage(path));

		accountService.saveUserAccount(account);
	}
	
	private byte[] getByteArrayFromImage(String imagePath) {
		byte[] imageData = null;
		
		try {
			byte[] buffer = new byte[4096];
			int l = 0;
			FileInputStream inputStream = new FileInputStream(new File(imagePath));
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			while((l=bufferedInputStream.read(buffer))!=-1) {
				arrayOutputStream.write(buffer, 0, l);
			}
			imageData = arrayOutputStream.toByteArray();
			bufferedInputStream.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return imageData;
	}
	
	
	
}
