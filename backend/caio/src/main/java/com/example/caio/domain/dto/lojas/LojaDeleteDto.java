package com.example.caio.domain.dto.lojas;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LojaDeleteDto {
private UUID id;
    private String cnpj;
}
