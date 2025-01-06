package com.example.caio.domain.dto.lojas;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LojaGetDto {
    private UUID id;
    private String nome;
    private String cnpj;
    private String email;
    private String senha;
}
