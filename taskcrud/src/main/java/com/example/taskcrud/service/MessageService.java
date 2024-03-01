package com.example.taskcrud.service;

import com.example.taskcrud.Repository.IAppUserRepository;
import com.example.taskcrud.Repository.IChannelRepository;
import com.example.taskcrud.Repository.IChatMessageRepository;
import com.example.taskcrud.entity.Channel;
import com.example.taskcrud.entity.ChatMessage;
import com.example.taskcrud.security.config.JwtService;
import com.example.taskcrud.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class MessageService implements IService<ChatMessage> {

    @Autowired
    IChatMessageRepository chatMessageRepository;
    @Autowired
    IAppUserRepository userRepository;
    @Autowired
    IChannelRepository channelRepository;
    private final JwtService jwtService;

    public MessageService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public List<ChatMessage> toList() {
        return chatMessageRepository.findAll();
    }

    @Override
    public ChatMessage save(ChatMessage entity) {
        return chatMessageRepository.save(entity);
    }

    @Override
    public void delete(ChatMessage entity) {
        chatMessageRepository.delete(entity);
    }

    @Override
    public ChatMessage findById(Long id) {
        return chatMessageRepository.findById(id).orElse(null);
    }

    public ArrayList<ChatMessage> GetMessagesFromUser(String token) {
        var user = JwtUtils.getUserFromToken(jwtService,userRepository,token);
        ChatMessage message = chatMessageRepository.findById(user.getId()).orElse(null);
        ArrayList<ChatMessage> messages = chatMessageRepository.GetAllMessagesOfUsers(Objects.requireNonNull(message).getId());
        return messages;
    }
    public ArrayList<ChatMessage> getMessagesOfChannel(String name) {
        Channel channel = channelRepository.findByName(name);
        ArrayList<ChatMessage> messages = chatMessageRepository.getMessagesOfChannel(channel.getName());
        return messages;
    }
}
