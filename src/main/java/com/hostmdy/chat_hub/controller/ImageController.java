package com.hostmdy.chat_hub.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hostmdy.chat_hub.domain.Account;
import com.hostmdy.chat_hub.domain.ChatRoom;
import com.hostmdy.chat_hub.domain.Message;
import com.hostmdy.chat_hub.service.AccountService;
import com.hostmdy.chat_hub.service.ChatRoomService;
import com.hostmdy.chat_hub.service.MessageService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/image")
public class ImageController {
	
	private final ChatRoomService chatRoomService;
	private final AccountService accountService;
	private final MessageService messageService;

	public ImageController(ChatRoomService chatRoomService, AccountService accountService, MessageService messageService) {
		super();
		this.chatRoomService = chatRoomService;
		this.accountService = accountService;
		this.messageService = messageService;
	}
	
	
	@GetMapping("/chatroom/{chatRoomId}/show")
	public void showProfileImage(@PathVariable Long chatRoomId,HttpServletResponse response) {
		Optional<ChatRoom> chatRoomOpt = chatRoomService.getChatRoomByChatRoomId(chatRoomId);
		if(chatRoomOpt.isEmpty()) {
			throw new NullPointerException("ChatRoom is Null");
		}
		
		ChatRoom chatRoom = chatRoomOpt.get();
		
		byte[] imageData = chatRoom.getChatProfile();
		InputStream inputStream = new ByteArrayInputStream(imageData);
		response.setContentType("image/jpeg");
		try {
			IOUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@GetMapping("/message/{messageId}/show")
	public void showMessageImage(@PathVariable Long messageId,HttpServletResponse response) {
		Optional<Message> messageOpt = messageService.getMessageByMessageId(messageId);
		if(messageOpt.isEmpty()) {
			throw new NullPointerException("message is Null");
		}
		
		Message message = messageOpt.get();
		
		byte[] imageData = message.getFileContent();
		InputStream inputStream = new ByteArrayInputStream(imageData);
		response.setContentType("image/jpeg");
		try {
			IOUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@GetMapping("/account/{accountId}/show")
	public void showAccountProfileImage(@PathVariable Long accountId,HttpServletResponse response) {
		Optional<Account> accountOpt = accountService.getAccountById(accountId);
		if(accountOpt.isEmpty()) {
			throw new NullPointerException("Account is Null");
		}
		
		Account account = accountOpt.get();
		
		byte[] imageData = account.getProfile();
		if(imageData==null) {
			return;
		}
		
		InputStream inputStream = new ByteArrayInputStream(imageData);
		response.setContentType("image/jpeg");
		try {
			IOUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@PostMapping("/account/{accountId}/upload")
	public String uploadProfileImage(@PathVariable Long accountId, @RequestParam MultipartFile imagefile) throws IOException {
		Optional<Account> accountOpt = accountService.getAccountById(accountId);
		if(accountOpt.isEmpty()) {
			throw new NullPointerException("account is null");
		}
		Account oldAccount = accountOpt.get();
		if(!imagefile.isEmpty()) {
			oldAccount.setProfile(imagefile.getBytes());
		}
		accountService.saveUserAccount(oldAccount);
		return "redirect:/account/"+accountId+"/profile";
	}
	
}
