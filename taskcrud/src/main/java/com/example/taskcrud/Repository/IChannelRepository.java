package com.example.taskcrud.Repository;

import com.example.taskcrud.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface IChannelRepository extends JpaRepository<Channel,Long> {

Optional<Channel> findById(Long id);

Channel findByName(String name);
@Query(value = "select c.* from channel c " +
        "inner join user_channel uc on uc.channel_id = c.id inner join user u on uc.user_id = u.id where u.id =?1", nativeQuery = true )
    ArrayList<Channel> getChannelsById(Long id);

}




