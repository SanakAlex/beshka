package com.creato.beshka.persistence.dao;

import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.persistence.entities.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat, Long> {
//    List<Chat> findAllByMembersIn(User user);
    @Query(
            value = ("select new com.creato.beshka.converters.dto.ChatDto(c.chatId, c.chatTitle, m) " +
                    "from Chat c " +
                    "left outer join c.messages m " +
                    "where (m.createdAt = (select max(m.createdAt) from m where m.chat = c)) " +
                    "order by m.read, ?#{#pageable}"),
            countQuery = "select count(c) from Chat c"
    )
    Page<ChatDto> getChatDtosWithLastMessage(Pageable pageable);
}
