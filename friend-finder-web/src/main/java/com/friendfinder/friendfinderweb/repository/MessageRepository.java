package com.friendfinder.friendfinderweb.repository;

import com.friendfinder.friendfinderweb.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllBySender_Id(int id);

    List<Message> findAllByReceiver_Id(int id);

    List<Message> findAllBySender_IdAndReceiver_Id(int firstId, int secondId);

    List<Message> findAllByChatId(int id);
}