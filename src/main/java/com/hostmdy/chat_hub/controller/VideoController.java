package com.hostmdy.chat_hub.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hostmdy.chat_hub.domain.Message;
import com.hostmdy.chat_hub.service.MessageService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/video")
public class VideoController {

	private final MessageService messageService;
	
	public VideoController(MessageService messageService) {
		this.messageService = messageService;
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/message/{messageId}/show")
	public void showMessageVideo(@PathVariable Long messageId,HttpServletResponse response) {
		Optional<Message> messageOpt = messageService.getMessageByMessageId(messageId);
		if(messageOpt.isEmpty()) {
			throw new NullPointerException("message is Null");
		}
		
		Message message = messageOpt.get();
		
		byte[] videoData = message.getFileContent();
		InputStream inputStream = new ByteArrayInputStream(videoData);
		response.setContentType("video/mp4");
		try {
			IOUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
