package com.example.taskcrud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Data
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String sender;
  private String content;
  @ManyToOne
  @JoinColumn(name = "channel_id")
  private Channel channel;
}
