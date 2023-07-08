package com.projects.customer_web_app.repository;

import com.projects.customer_web_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByPersonalInfoEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByPersonalInfoEmail(String email);
}