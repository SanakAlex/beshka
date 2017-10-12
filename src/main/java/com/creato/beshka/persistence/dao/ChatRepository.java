package com.creato.beshka.persistence.dao;

import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.persistence.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
//    List<Chat> findAllByMembersIn(User user);
    @Query("select new com.creato.beshka.converters.dto.ChatDto" +
            "(c.chatId, c.chatTitle, m)" +
//            "from User u " +
            "from Chat c " +
            "left outer join c.messages m " +
            "where (m.createdAt = (select max(m.createdAt) from m where m.chat = c))" +
            "order by m.read")
    List<ChatDto> getChatDtosWithLastMessage();
}
