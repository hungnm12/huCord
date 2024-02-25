package com.example.taskcrud.entity.appuser;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessage {
  private String sender;
  private String content;
}
