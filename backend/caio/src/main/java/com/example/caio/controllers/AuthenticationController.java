package com.example.caio.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.caio.domain.dto.auth.AuthenticationRequestDTO;
import com.example.caio.domain.dto.auth.AuthenticationResponseDTO;
import com.example.caio.domain.dto.user.UserRequestDTO;
import com.example.caio.domain.dto.user.UserResponseDTO;
import com.example.caio.service.auth.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "auth")
public class AuthenticationController {
    
    @Autowired
    @Lazy
    private AuthenticationService authenticationService;


    @Operation(summary = "Register User", responses = {
        @ApiResponse(responseCode = "201", description = "Successfully Registered!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "409", description = "User Already Exists", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserRequestDTO userRequest) throws IOException {
            UserResponseDTO userResponse = authenticationService.register(userRequest);
            return ResponseEntity.ok().body(userResponse);
    }

    @Operation(summary = "Authenticate User", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully Authenticated!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Bad Credentials", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody AuthenticationRequestDTO authRequest) throws IOException, AuthException,UsernameNotFoundException {
            AuthenticationResponseDTO jwtToken = authenticationService.login(authRequest);
            return ResponseEntity.ok(jwtToken);
    }

   @Operation(summary = "Authenticate RefreshToken", responses = {
        @ApiResponse(responseCode = "200", description = "Successfully Authenticated!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponseDTO.class))),
        @ApiResponse(responseCode = "401", description = "Bad Credentials", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDTO> authenticateRefreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws AuthException {
        
        AuthenticationResponseDTO jwtToken = authenticationService.refreshToken(request, response);
        return ResponseEntity.ok(jwtToken);
    }
    
}