package com.example.caio.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponseDTO {
    
    @NotBlank
    private String token;

    @NotBlank
    private String refreshToken;
}