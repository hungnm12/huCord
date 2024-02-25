package com.example.taskcrud.service;

import com.example.taskcrud.Repository.IChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChannelService {
    @Autowired
    private IChannelRepository channelRepository;


}
