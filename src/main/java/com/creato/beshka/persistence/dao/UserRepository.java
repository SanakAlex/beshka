package com.creato.beshka.persistence.dao;

import com.creato.beshka.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByActiveIsTrue();
}
