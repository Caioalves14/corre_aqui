package com.example.caio.domain.dto.ofertas;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaGetDto {

    private UUID id;
    private String nomeProduto;
    private String imagem;
    private Double preco;
}