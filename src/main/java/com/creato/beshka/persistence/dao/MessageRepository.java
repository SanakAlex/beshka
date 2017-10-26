package com.creato.beshka.persistence.dao;

import com.creato.beshka.persistence.entities.Message;
import com.creato.beshka.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select count(m) from Message m where " +
            "m.chat.chatId = :chatId " +
            "and m.read = false " +
            "and m.sender <> :currentUser")
    int getUnreadCount(@Param("chatId") Long chatId, @Param("currentUser") User currentUser);

    @Modifying
    @Query("update Message m set m.read = true where m.chat.chatId = chatId and m.sender = currentUser")
    void readMessagesByChatId(@Param("chatId")Long chatId, @Param("currentUser") User currentUser);

}
