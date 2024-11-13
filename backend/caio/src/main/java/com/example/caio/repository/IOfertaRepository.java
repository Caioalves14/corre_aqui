package com.example.caio.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.caio.model.Oferta;


@Repository
public interface IOfertaRepository extends JpaRepository<Oferta, UUID>{
    @Query("SELECT u FROM Oferta u WHERE u.nomeProduto = :nomeProduto")
    Optional<Oferta> findOfferByName(@Param("nomeProduto")String nomeProduto);
}
