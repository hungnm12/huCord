package com.example.taskcrud.Controller;

import com.example.taskcrud.Repository.IChatMessageRepository;
import com.example.taskcrud.entity.AppUser;
import com.example.taskcrud.entity.ChatMessage;
import com.example.taskcrud.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private IChatMessageRepository messageRepository;
    @Autowired
    MessageService messageService;
    @MessageMapping("api/chat")
    public void sendMessage(@Payload ChatMessage message, Principal principal) {



        // Tạo message mới
        ChatMessage newMessage = new ChatMessage();
        newMessage.setSender(message.getSender());
        newMessage.setContent(message.getContent());
        newMessage.setChannel(message.getChannel());
        messageRepository.save(newMessage);

        // Gửi message đến tất cả người dùng
        simpMessagingTemplate.convertAndSend("/topic/chat", newMessage);
    }

    @GetMapping("/api/chat/getMessagesFromUser")
    public ResponseEntity<List<ChatMessage>> GetMessagesFromUser(@RequestParam("token") String token) {
       try {
           List<ChatMessage> chatMessages=messageService.GetMessagesFromUser(token);
           return ResponseEntity.ok(chatMessages);
       }
       catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }
    @GetMapping("api/chat/MsgInChannel")
    public ResponseEntity<List<ChatMessage>> getMessagesOfChannel(@RequestParam("name") String name) {
        try {
            List<ChatMessage> chatMessages = messageService.getMessagesOfChannel(name);
            return ResponseEntity.ok(chatMessages);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
