package com.cinema.theatrepro.User.repo;

import com.cinema.theatrepro.User.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
