package com.example.taskcrud.service;

import com.example.taskcrud.Repository.IChannelRepository;
import com.example.taskcrud.Repository.IFileRepository;
import com.example.taskcrud.entity.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class ChannelService implements IService<Channel>{

    @Autowired
    IChannelRepository channelRepository;
    @Override
    public List<Channel> toList() {
        return channelRepository.findAll();
    }

    @Override
    public Channel save(Channel entity) {
        return channelRepository.save(entity);
    }

    @Override
    public void delete(Channel entity) {
        channelRepository.delete(entity);
    }

    @Override
    public Channel findById(Long id) {
        return channelRepository.findById(id).orElse(null);
    }
}
