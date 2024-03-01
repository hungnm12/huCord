package com.example.taskcrud.Repository;

import com.example.taskcrud.entity.AppUser;
import com.example.taskcrud.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface IChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query(value = "select c.* from chat_message c join user on c.sender_id = u.id where c.sender_id = ?1", nativeQuery = true)
    ArrayList<ChatMessage> GetAllMessagesOfUsers(Long id);
    @Query(value = "select c.* from chat_message c inner join channel ch on c.id = ch.channel_id where ch.name =?1", nativeQuery = true)
    ArrayList<ChatMessage> getMessagesOfChannel(String name);
}
