package com.cinema.theatrepro.User.repo;

import com.cinema.theatrepro.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUsername(String username);
}
