package com.example.taskcrud.Controller;

import com.example.taskcrud.Repository.IChatMessageRepository;
import com.example.taskcrud.entity.appuser.AppUser;
import com.example.taskcrud.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private IChatMessageRepository messageRepository;
    @MessageMapping("api/chat")
    public void sendMessage(@Payload ChatMessage message, Principal principal) {
        // Lấy thông tin người gửi từ AppUser
        AppUser sender = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tạo message mới
        ChatMessage newMessage = new ChatMessage();
        newMessage.setSender(sender);
        newMessage.setContent(message.getContent());
        newMessage.setChannel(message.getChannel());
        messageRepository.save(newMessage);

        // Gửi message đến tất cả người dùng
        simpMessagingTemplate.convertAndSend("/topic/chat", newMessage);
    }
}
