package com.example.taskcrud.Controller;

import com.example.taskcrud.Repository.IChatMassegeRepository;
import com.example.taskcrud.entity.ChatMessage;
import com.example.taskcrud.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/chat")
public class ChatController {

    private  SimpMessagingTemplate messagingTemplate;
    private  ChatService chatService;
    private IChatMassegeRepository chatMassegeRepository;


    @MessageMapping("/chat.sendMessage/{channelId}")
    public void sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable Long channelId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = authentication.getName();
        chatService.save(chatMessage, channelId);
        messagingTemplate.convertAndSend("/topic/channel/" + channelId, chatMessage);
    }

    @MessageMapping("/chat.getChatMessages/{channelId}")
    public void getChatMessages(@DestinationVariable Long channelId) {
        messagingTemplate.convertAndSend("/topic/channel/" + channelId, chatService.getChatMessages(channelId));
    }
}
