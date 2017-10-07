package com.creato.beshka.persistence.dao;

import com.creato.beshka.persistence.entities.Chat;
import com.creato.beshka.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
//    List<Chat> findAllByMembersIn(User user);
}
