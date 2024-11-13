package com.example.caio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "loja")
public class Loja {
    private String nome;
    @Id
    private double cnpj;
    private String email;
    private String senha;

}
