package com.hostmdy.chat_hub.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.hostmdy.chat_hub.domain.Account;
import com.hostmdy.chat_hub.domain.ChatRoom;
import com.hostmdy.chat_hub.domain.Message;
import com.hostmdy.chat_hub.service.AccountService;
import com.hostmdy.chat_hub.service.ChatRoomService;
import com.hostmdy.chat_hub.service.MessageService;
import com.hostmdy.chat_hub.tempory_bean.ChatDisplay;
import com.hostmdy.chat_hub.tempory_bean.MessageDisplay;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/chatroom")
public class ChatRoomController {
	
	private final AccountService accountService;
	private final ChatRoomService chatRoomService;
	private final MessageService messageService;
	public ChatRoomController(AccountService accountService, ChatRoomService chatRoomService, MessageService messageService) {
		super();
		this.accountService = accountService;
		this.chatRoomService = chatRoomService;
		this.messageService = messageService;
	}
	
	@GetMapping("/{friendId}/create")
	public String createNewChatRoom(@PathVariable Long friendId,HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		Optional<Account> friendOpt = accountService.getAccountById(friendId);
		Optional<Account> userOpt = accountService.getAccountById(userId);
		
		if(friendOpt.isPresent() && userOpt.isPresent()) {
			Account user = userOpt.get();
			Account friend = friendOpt.get();
			if(user.getId()!=friend.getId()) {
				ChatRoom chatRoom = new ChatRoom();
				chatRoom.getAccountList().add(user);
				chatRoom.getAccountList().add(friend);
				user.getChatRoomList().add(chatRoom);
				friend.getChatRoomList().add(chatRoom);
				chatRoomService.saveChatRoom(chatRoom);
				
				accountService.saveUserAccount(user);
				accountService.saveUserAccount(friend);
			}
			
			
		}
		
		
		return "redirect:/";
	}
	
	@GetMapping("/{chatRoomId}/detail")
	public String showChatRoom(@PathVariable Long chatRoomId, Model model, HttpServletRequest request) {
		
		Optional<ChatRoom> chatRoomOpt = chatRoomService.getChatRoomByChatRoomId(chatRoomId);
		if(chatRoomOpt.isEmpty()) {
			throw new NullPointerException("chatroom is null");
		}
		
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		if(userId==null)
		{
			throw new NullPointerException("userid is null");
		}
		
		ChatRoom chatRoom = chatRoomOpt.get();
		List<Message> messageList = chatRoom.getMessageList();
		ChatDisplay chatDisplay = new ChatDisplay();
		chatDisplay.setId(chatRoom.getId());
		chatDisplay.setBlocked(chatRoom.isBlocked());
		chatDisplay.setBlockerId(chatRoom.getBlockerId());
		for(Account account : chatRoom.getAccountList()) {
			if(account.getId()!=userId) {
				chatDisplay.setImageAccountId(account.getId());
				chatDisplay.setChatName(account.getNickName());
				chatDisplay.setProfileNull(account.getProfile()==null);
			}
		}
		Collections.reverse(messageList);
		List<MessageDisplay> messageDisplayList = new ArrayList<>();
		
		for(Message message : messageList) {
			if(message.getSenderId()!=userId) {
				message.setRead(true);
				messageService.saveMessage(message);
			}
			MessageDisplay messageDisplay = new MessageDisplay();
			messageDisplay.setId(message.getId());
			messageDisplay.setSenderId(message.getSenderId());
			Optional<Account> senderOpt = accountService.getAccountById(message.getSenderId());
			Account sender = senderOpt.get();
			messageDisplay.setSenderName(sender.getNickName());
			messageDisplay.setContent(message.getContent());
			messageDisplay.setType(message.getType());
			LocalDateTime messageCreatedTime = message.getCreateAt();
			
			int hour = messageCreatedTime.getHour();
			if(hour==0) {
				hour=12;
			}
			int minute = messageCreatedTime.getMinute();
			String sendTime = ((hour-12>0)?(hour-12):(hour))+":"+((minute>9)?minute:"0"+minute)+((hour-12>0)?" PM":" AM");
			messageDisplay.setRead(message.isRead());
			messageDisplay.setSendTime(sendTime);
			messageDisplayList.add(messageDisplay);
		}
		model.addAttribute("messageList", messageDisplayList);
		model.addAttribute("chatRoom", chatDisplay);
		model.addAttribute("newMessage", new Message());
		
		return "message";
	}
	
	@GetMapping("/{chatRoomId}/block")
	public String blockChatRoom(@PathVariable Long chatRoomId,@SessionAttribute Long userId) {
		
		Optional<ChatRoom> chatRoomOpt = chatRoomService.getChatRoomByChatRoomId(chatRoomId);
		if(chatRoomOpt.isEmpty()) {
			throw new NullPointerException("chatRoom is null");
		}
		ChatRoom chatRoom = chatRoomOpt.get();
		chatRoom.setBlocked(true);
		chatRoom.setBlockerId(userId);
		chatRoomService.saveChatRoom(chatRoom);
		
		return "redirect:/chatroom/"+chatRoomId+"/detail";
	}
	
	@GetMapping("/{chatRoomId}/unblock")
	public String unblockChatRoom(@PathVariable Long chatRoomId,@SessionAttribute Long userId) {
		
		Optional<ChatRoom> chatRoomOpt = chatRoomService.getChatRoomByChatRoomId(chatRoomId);
		if(chatRoomOpt.isEmpty()) {
			throw new NullPointerException("chatRoom is null");
		}
		ChatRoom chatRoom = chatRoomOpt.get();
		chatRoom.setBlocked(false);
		chatRoom.setBlockerId(null);
		chatRoomService.saveChatRoom(chatRoom);
		
		return "redirect:/chatroom/"+chatRoomId+"/detail";
	}

}
