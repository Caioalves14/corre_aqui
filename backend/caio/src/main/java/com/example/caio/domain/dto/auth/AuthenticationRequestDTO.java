package com.example.caio.domain.dto.auth;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    
    @NotBlank
    public String login;

    @NotBlank
    public String password;
}