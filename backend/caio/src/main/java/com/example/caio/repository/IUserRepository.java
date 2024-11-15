package com.example.caio.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.example.caio.model.User;
@Repository
public interface IUserRepository extends JpaRepository<User,UUID> {

    UserDetails findByLogin(String login);
    boolean existsByLogin(String login);
}
