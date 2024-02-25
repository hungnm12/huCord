package com.example.taskcrud.Repository;

import com.example.taskcrud.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChatMassegeRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChannelIdOrderBySentAt(Long channelId);
}
