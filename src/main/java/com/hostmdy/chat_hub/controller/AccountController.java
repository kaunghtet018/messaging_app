package com.hostmdy.chat_hub.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hostmdy.chat_hub.crypto.PasswordEncoder;
import com.hostmdy.chat_hub.crypto.PasswordValidator;
import com.hostmdy.chat_hub.domain.Account;
import com.hostmdy.chat_hub.domain.ChatRoom;
import com.hostmdy.chat_hub.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class AccountController {
	
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}
	
	@GetMapping({"/signin","signup"})
	public String showForm(Model model) {
		model.addAttribute("account", new Account());
		return "signinup";
	}
	
	@PostMapping({"/signin"}) 
	public String signin(@ModelAttribute Account account,Model model,HttpSession session){
		String email = account.getEmail();
		Optional<Account> accountOpt = accountService.getAccountByEmail(email);
		if(accountOpt.isPresent()) {
			Account accountCheck = accountOpt.get();
			try {
				if(PasswordValidator.validatePassword(account.getPassword(),accountCheck.getPassword())) {
					session.setAttribute("userId", accountCheck.getId());
					session.setMaxInactiveInterval(3600);
					return "redirect:/index";
				}
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		model.addAttribute("message", "Email or Password is incorrect");
		model.addAttribute("success", false);
		return "signinup";
		
	}
	
	@PostMapping({"/signup"})
	public String signup(@ModelAttribute Account account,Model model) {
		account.setRole("user");
		try {
			account.setPassword(PasswordEncoder.encode(account.getPassword()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Account createdUser = null;
		
		Optional<Account> userOpt = accountService.getAccountByEmail(account.getEmail());
		if(userOpt.isEmpty()) {
			createdUser = accountService.saveUserAccount(account);
		}
		
		if(createdUser!=null) {
			model.addAttribute("message", "Account is created successfully");
			model.addAttribute("success", true);
		}
		else {
			model.addAttribute("message", "Failed to create an account");
			model.addAttribute("success", false);
		}
		return "signinup";
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(0);
		session.invalidate();
		return "redirect:/account/signin";
	}
	
	@GetMapping("/{accountId}/profile")
	public String showProfile(@PathVariable Long accountId,HttpServletRequest request, Model model) {
		Optional<Account> accountOpt = accountService.getAccountById(accountId);
		if(accountOpt.isEmpty()) {
			throw new NullPointerException("user is null");
		}
		Account account = accountOpt.get();
		HttpSession session = request.getSession();
		Long userId = (Long)session.getAttribute("userId");
		if(userId==null) {
			throw new NullPointerException("user is null");
		}
		Optional<Account> userOpt = accountService.getAccountById(userId);
		if(userOpt.isEmpty()) {
			throw new NullPointerException("user is null");
		}
		Account user = userOpt.get();
		boolean isAdded = false;
		for(ChatRoom chatRoom:user.getChatRoomList()) {
			for(Account chatAccount:chatRoom.getAccountList()) {
				if(account.getId()==chatAccount.getId()) {
					isAdded = true;
					break;
				}
			}
			if(isAdded) {
				model.addAttribute("chatId", chatRoom.getId());
				
				break;
			}
		}
		model.addAttribute("isAdded", isAdded);
		model.addAttribute("account", account);
		return "profile";
	}
	
	@PostMapping("/{accountId}/update")
	public String updateAccount(@PathVariable Long accountId,@ModelAttribute Account account) {
		
		Optional<Account> accountOpt = accountService.getAccountById(accountId);
		if(accountOpt.isEmpty()) {
			throw new NullPointerException("Account is null");
		}
		Account oldAccount = accountOpt.get();
		oldAccount.setFirstName(account.getFirstName());
		oldAccount.setLastName(account.getLastName());
		oldAccount.setNickName(account.getNickName());
		accountService.saveUserAccount(oldAccount);
		return "redirect:/account/"+accountId+"/profile";
	}
	
}
