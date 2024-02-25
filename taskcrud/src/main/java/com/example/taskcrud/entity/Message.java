package com.example.taskcrud.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_message")
    private Long idMessage;
}
