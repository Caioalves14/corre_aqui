package com.example.caio.domain.dto.lojas;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LojaGetDto {

    private String nome;
    @Id
    private double cnpj;
    private String email;
    private String senha;
}
