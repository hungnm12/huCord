package com.example.taskcrud.security.config;

import com.example.taskcrud.service.AuthenticationService;
import com.example.taskcrud.Repository.IAppUserRepository;
import com.example.taskcrud.entity.appuser.AppUser;
import com.example.taskcrud.entity.chat.ChatMessage;
import com.example.taskcrud.entity.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {
    private  SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private AuthenticationService service;
    @Autowired
    private IAppUserRepository repository;
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconnected: {}", username);
            AppUser user = repository.findByEmail(username).orElse(null);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
