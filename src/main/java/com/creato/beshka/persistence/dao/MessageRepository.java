package com.creato.beshka.persistence.dao;

import com.creato.beshka.persistence.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findAllByChat_ChatId(Long chatId);

}
