package com.hostmdy.chat_hub.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hostmdy.chat_hub.domain.ChatRoom;
import com.hostmdy.chat_hub.domain.Message;
import com.hostmdy.chat_hub.service.ChatRoomService;
import com.hostmdy.chat_hub.service.MessageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/message")
public class MessageController {

	private final MessageService messageService;
	private final ChatRoomService chatRoomService;
	
	public MessageController(MessageService messageService, ChatRoomService chatRoomService) {
		this.messageService = messageService;
		// TODO Auto-generated constructor stub
		this.chatRoomService = chatRoomService;
	}
	
	@PostMapping("/{chatRoomId}/newtext")
	public String createTextMessage(@PathVariable Long chatRoomId,@RequestParam String content,HttpServletRequest request) {
		
		Message message = new Message();
		Optional<ChatRoom> chatRoomOpt = chatRoomService.getChatRoomByChatRoomId(chatRoomId);
		if(chatRoomOpt.isEmpty()) {
			throw new NullPointerException("chatroom is null.");
		}
		ChatRoom chatRoom = chatRoomOpt.get();
		HttpSession session = request.getSession();
		Long senderId = (Long) session.getAttribute("userId");
		if(senderId==null) {
			throw new NullPointerException("userId is null");
		}
		
		message.setType("text");
		message.setChatRoom(chatRoom);
		message.setContent(content);
		message.setSenderId(senderId);
		message.setRead(false);
		chatRoom.getMessageList().add(message);
		chatRoomService.saveChatRoom(chatRoom);
		if(!chatRoom.isBlocked()) {
			messageService.saveMessage(message);
		}
		
		return "redirect:/chatroom/"+chatRoomId+"/detail";
	}
	
	@PostMapping("/{chatRoomId}/newfile")
	public String createFileMessage(@PathVariable Long chatRoomId,@RequestParam MultipartFile sendfile,HttpServletRequest request) throws IOException {
		
		Message message = new Message();
		Optional<ChatRoom> chatRoomOpt = chatRoomService.getChatRoomByChatRoomId(chatRoomId);
		if(chatRoomOpt.isEmpty()) {
			throw new NullPointerException("chatroom is null.");
		}
		ChatRoom chatRoom = chatRoomOpt.get();
		HttpSession session = request.getSession();
		Long senderId = (Long) session.getAttribute("userId");
		if(senderId==null) {
			throw new NullPointerException("userId is null");
		}
		
		if(!sendfile.isEmpty()) {
			
		
			String originalName = sendfile.getOriginalFilename();
			if(originalName.endsWith(".jpg")||originalName.endsWith(".jpeg")||originalName.endsWith(".png")) {
				message.setType("image");
			}
			if(originalName.endsWith(".mp4")) {
				message.setType("video");
			}
			message.setChatRoom(chatRoom);
			message.setFileContent(sendfile.getBytes());
			message.setSenderId(senderId);
			message.setRead(false);
			chatRoom.getMessageList().add(message);
			chatRoomService.saveChatRoom(chatRoom);
			if(!chatRoom.isBlocked()) {
				messageService.saveMessage(message);
			}
		}
		
		return "redirect:/chatroom/"+chatRoomId+"/detail";
	}
	
}
