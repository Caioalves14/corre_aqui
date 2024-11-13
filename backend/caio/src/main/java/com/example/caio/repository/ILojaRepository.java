package com.example.caio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.caio.model.Loja;

@Repository
public interface ILojaRepository extends JpaRepository<Loja, Double> {

    boolean existsByCnpj(Double cnpj);

    boolean existsByEmail(String email);

}
