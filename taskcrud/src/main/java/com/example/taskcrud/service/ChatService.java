package com.example.taskcrud.service;

import com.example.taskcrud.Repository.IAppUserRepository;
import com.example.taskcrud.Repository.IChannelRepository;
import com.example.taskcrud.Repository.IChatMassegeRepository;
import com.example.taskcrud.entity.Channel;
import com.example.taskcrud.entity.ChatMessage;
import com.example.taskcrud.entity.appuser.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    private final IChatMassegeRepository chatMessageRepository;
    private final IAppUserRepository userRepository;
    private final IChannelRepository channelRepository;

    public ChatService(IChatMassegeRepository chatMessageRepository, IAppUserRepository userRepository, IChannelRepository channelRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }
    public void save(ChatMessage chatMessage, Long channelId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String senderUsername = authentication.getName();
        AppUser sender = userRepository.findByEmail(senderUsername);

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid channel ID"));

        chatMessage.setSenderId(sender.getId());
        chatMessage.setChannel(channel);
        chatMessage.setSentAt(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatMessages(Long channelId) {
        return chatMessageRepository.findAllByChannelIdOrderBySentAt(channelId);
    }
}
