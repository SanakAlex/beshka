package com.creato.beshka.persistence.dao;

import com.creato.beshka.persistence.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChat_ChatId(Long chatId);
    Message findByChat_ChatId(Long chId);
    Message findByChat_ChatIdOrderByCreatedAtDesc(Long chatId);
    @Query("select count(m) from Message m where m.chat.chatId = :chatId and m.read = false")
    int getUnreadCount(@Param("chatId") Long chatId);
}
