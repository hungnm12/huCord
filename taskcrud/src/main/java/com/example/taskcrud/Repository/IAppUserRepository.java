package com.example.taskcrud.Repository;

import com.example.taskcrud.entity.AppUser;
import com.example.taskcrud.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository

public interface IAppUserRepository extends JpaRepository<AppUser,Integer> {

    AppUser findByEmail (String email);

    @Query(value = "select u.* from user u " +
            "inner join user_channel uc on uc.user_id = u.id " +
            "inner join channel c on uc.channel_id = c.id where c.name = ?1",nativeQuery = true)
    ArrayList<AppUser> findUsersByChannelName(String name);


}

