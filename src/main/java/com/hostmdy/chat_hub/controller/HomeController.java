package com.hostmdy.chat_hub.controller;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hostmdy.chat_hub.domain.Account;
import com.hostmdy.chat_hub.domain.ChatRoom;
import com.hostmdy.chat_hub.domain.Message;
import com.hostmdy.chat_hub.service.AccountService;
import com.hostmdy.chat_hub.service.ChatRoomService;
import com.hostmdy.chat_hub.tempory_bean.ChatDisplay;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	private final AccountService accountService;
	private final ChatRoomService chatRoomService;

	public HomeController(ChatRoomService chatRoomService, AccountService accountService) {
		this.accountService = accountService;
		this.chatRoomService = chatRoomService;
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping({"/","/index"})
	public String showHomePage(Model model,HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		Long userId = (Long) session.getAttribute("userId");
		if(userId==null) {
			
			throw new NullPointerException("user is null");
		}
		Optional<Account> userOpt = accountService.getAccountById(userId);
		if(userOpt.isEmpty()) {
			throw new NullPointerException("user is null");
		}
		Account user = userOpt.get();
		List<ChatDisplay> chatInfoTempList = new ArrayList<>();
		for (ChatRoom chatRoom : user.getChatRoomList()) {
		    List<Account> accountList = chatRoom.getAccountList();
		    ChatDisplay chatDisplay = new ChatDisplay();
		    for(Account account:accountList) {
		    	if(!account.equals(user)) {
		    		chatDisplay.setId(chatRoom.getId());
		    		chatDisplay.setImageAccountId(account.getId());
		    		chatDisplay.setProfileNull(account.getProfile()==null);
		    		chatDisplay.setChatName(account.getNickName());
		    		List<Message> messageList = chatRoom.getMessageList();
		    		String lastMessage = "Start talking with "+account.getNickName();
		    		int len = messageList.size();
		    		if(len!=0) {
		    			Message message = messageList.get(len-1);
		    			if(message.getType().equals("text")) {
			    			lastMessage = ((message.getSenderId()!=account.getId())?"You":account.getNickName())+" : "+message.getContent();	
		    			}
		    			else if(message.getType().equals("image")){
		    				lastMessage = ((message.getSenderId()!=account.getId())?"You":account.getNickName())+" : sent an Image";
		    			}else {
		    				lastMessage = ((message.getSenderId()!=account.getId())?"You":account.getNickName())+" : sent a Video";
		    			}
		    		}
		    		int count = 0;
		    		for(Message message:messageList) {
		    			if(!message.isRead() && message.getSenderId()!=userId) {
		    				count++;
		    			}
		    		}
		    		chatDisplay.setNewMessages(count);
		    		chatDisplay.setLastMessage((lastMessage.length()>30 && len!=0)?lastMessage.substring(0, 30)+"...":lastMessage);
		    	}
		    }
		    chatInfoTempList.add(chatDisplay);
		}

		
		model.addAttribute("chatRoomList",chatInfoTempList);
		return "index";
	}
	
	@PostMapping("/searchpeople")
	public String showSearchedPeople(@RequestParam String searchPeople,Model model ){
		List<Account> accountList = accountService.getAccountByNickName(searchPeople);
		model.addAttribute("searchedPeopleList", accountList);
		return "search";
	}
	

	
}
