package com.example.taskcrud.Controller;

import com.example.taskcrud.Repository.IChannelRepository;
import com.example.taskcrud.entity.Channel;
import com.example.taskcrud.entity.File;
import com.example.taskcrud.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/channel")
public class ChannelController {
    @Autowired
    private ChannelService channelService;
    @Autowired
    private IChannelRepository channelRepository;

    @GetMapping("/getChannel")
    public ResponseEntity<List<Channel>> getAllChannel(){
        List<Channel> channels = channelService.toList();
        return ResponseEntity.ok(channels);
    }
    @PostMapping("/create")
    public ResponseEntity<Channel> create(@RequestParam(name = "name")String name){
        Channel channel =channelService.createChannel(name);
        return ResponseEntity.ok(channel);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Channel> delete(Long id) {
        Channel channel = channelRepository.findById(id).orElse(null);
        if (channel.getId() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } else {
            channelService.delete(channel);
            return ResponseEntity.ok(channel);
        }
    }
    @GetMapping("/getChannelOfUser")
    public ResponseEntity<List<Channel>> GetUsersInChannel(Long id){
        List<Channel> channels = channelService.getChannelsOfUser(id);
        return ResponseEntity.ok(channels);
    }

}
