package com.creato.beshka.persistence.dao;

import com.creato.beshka.persistence.entities.Chat;
import com.creato.beshka.persistence.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(
            value = "select c from User u " +
                    "left outer join u.chats c " +
                    "left outer join c.messages m " +
                    "where u = :user " +
                    "group by c.chatId " +
                    "order by max(m.createdAt) desc",
//                        "and (m.createdAt = (select max(m.createdAt) from m where m.chat = c)) " +
            countQuery = "select count(c) from User u left outer join u.chats c where u = :user"
    )
    Page<Chat> getChatWithLastMessage(Pageable pageable, @Param("user")User currentUser);

    @Query("select c from User u left outer join u.chats c where u = :currentUser and :secondUser member c.members")
    Chat findChatWithUserIn(@Param("currentUser") User currentUser, @Param("secondUser") User secondUser);
}
