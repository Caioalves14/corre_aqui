package com.example.caio.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.caio.model.Loja;

@Repository
public interface ILojaRepository extends JpaRepository<Loja, UUID> {

    boolean existsByCnpj(String cnpj);

    boolean existsByEmail(String email);

}
