package com.creato.beshka.persistence.dao;

import com.creato.beshka.converters.dto.UserDto;
import com.creato.beshka.persistence.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Page<User> findAllByActiveIsTrue(Pageable pageable);

    @Query(
            value = ("select new com.creato.beshka.converters.dto.UserDto(u) " +
                "from User u " +
                "left outer join u.chats c " +
                "left outer join c.messages m " +
                "where  (m.createdAt = (select max(m.createdAt) from m where m.chat = c)) " +
                    "and u = :currentUser " +
//                    "and (select messageToCount from m where m.read = false and m.sender != :currentUser)" +
                "order by ?#{#pageable}"),
            countQuery = "select count(c) from Chat c")
    Page<UserDto> findUserDtosWithReadCount(Pageable pageable, @Param("currentUser") User currentUser);
}
