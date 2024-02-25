package com.example.taskcrud.Repository;

import com.example.taskcrud.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChannelRepository extends JpaRepository<Channel, Long> {
}
