package com.example.taskcrud.entity;

import com.example.taskcrud.entity.appuser.AppUser;
import com.example.taskcrud.entity.chat.ChatMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Transactional
@Table(name = "channel")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelId;

    private String name;

    @OneToMany(mappedBy = "channel")
    private List<ChatMessage> messages;

    @ManyToMany(mappedBy = "channels")
    private List<AppUser> users;


}
