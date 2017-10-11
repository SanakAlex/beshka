package com.creato.beshka.persistence.dao;

import com.creato.beshka.persistence.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select count(m) from Message m where m.chat.chatId = :chatId and m.read = false")
    int getUnreadCount(@Param("chatId") Long chatId);
}
