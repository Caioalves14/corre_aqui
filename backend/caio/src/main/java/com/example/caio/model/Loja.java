package com.example.caio.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "loja")
public class Loja {
    @Id
    private UUID id;
    private String nome;
    private double cnpj;
    private String email;
    private String senha;

}
