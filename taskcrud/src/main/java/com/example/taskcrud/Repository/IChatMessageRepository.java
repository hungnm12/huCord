package com.example.taskcrud.Repository;

import com.example.taskcrud.entity.appuser.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
