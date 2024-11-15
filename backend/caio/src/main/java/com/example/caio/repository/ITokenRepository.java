package com.example.caio.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.caio.model.Token;

@Repository
public interface ITokenRepository extends CrudRepository<Token,UUID> {

    List<Token> findAllAccessTokensByUserId(UUID id);

  @Query("SELECT t FROM Token t " +
           "WHERE (CASE WHEN :isRefreshToken = true THEN t.refreshToken ELSE t.accessToken END) = :token")
    Optional<Token> findByToken(@Param("token") String token, @Param("isRefreshToken") boolean isRefreshToken);
    
}