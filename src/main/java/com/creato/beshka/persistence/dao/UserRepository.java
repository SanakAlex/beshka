package com.creato.beshka.persistence.dao;

import com.creato.beshka.persistence.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Page<User> findAllByActiveIsTrue(Pageable pageable);
}
