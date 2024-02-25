package com.example.taskcrud.entity.chat;

import com.example.taskcrud.entity.Channel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage {
    @Id
    private Long id;
    private MessageType type;
    private String content;
    private String sender;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

}
