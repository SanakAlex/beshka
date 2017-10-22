package com.creato.beshka.persistence.dao;

import com.creato.beshka.converters.dto.ChatDto;
import com.creato.beshka.persistence.entities.Chat;
import com.creato.beshka.persistence.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<Chat, Long> {
//    List<Chat> findAllByMembersIn(User user);
//    @Query(
//            value = ("select new com.creato.beshka.converters.dto.ChatDto(c, m) " +
//                    "from Chat c " +
//                    "left outer join c.messages m " +
//                    "where (m.createdAt = (select max(m.createdAt) from m where m.chat = c)) "),
//            countQuery = ("select count(c) from Chat c")
//    )
//    Page<ChatDto> getChatDtosWithLastMessage(Pageable pageable);

    @Query(
            value = ("select c " +
                    "from User u " +
                    "left outer join u.chats c " +
                    "left outer join c.messages m " +
                    "where u = :user " +
//                        "and (m.createdAt = (select max(m.createdAt) from m where m.chat = c)) " +
                    "group by c.chatId " +
                    "order by max(m.createdAt) desc "
                    ),
            countQuery = ("select count(c) from User u left outer join u.chats c where u = :user")
    )
    Page<Chat> getChatWithLastMessage(Pageable pageable, @Param("user")User currentUser);
}
