package com.example.caio.domain.dto.ofertas;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaPostDto {

    private UUID id;
    @JsonProperty("nomeProduto") 
    private String nomeProduto;
    @JsonProperty("imagem")
    private String imagem;

    private Double preco;
}
