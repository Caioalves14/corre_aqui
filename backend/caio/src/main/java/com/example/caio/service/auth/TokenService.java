package com.example.caio.service.auth;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.caio.model.Token;
import com.example.caio.model.User;
import com.example.caio.repository.ITokenRepository;


@Service
public class TokenService {

    @Value("${api.security.secretkey}")
    private String secretKey;

    @Value("${api.security.expiration}")
    private Integer expirationToken;

    @Value("${api.security.refresh-token.expiration}")
    private Integer expirationRefreshToken;

    @Autowired
    private ITokenRepository tokenRepository;

    public String generateToken(User user, Integer expiration) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                            .withIssuer("api-security")
                            .withSubject(user.getUsername())
                            .withIssuedAt(new Date(System.currentTimeMillis()))
                            .withExpiresAt(generateExpirationDate(expiration))
                            .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token" + e.getMessage());
        }
    }
    
    public void saveUserToken(String accessToken, String refreshToken, User user) {
        tokenRepository.save(
          Token.builder()
              .id(UUID.randomUUID())
              .accessToken(accessToken)
              .refreshToken(refreshToken)
              .loggedOut(false)
              .user(user)
              .build()
      );
    }

    public void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUserId(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }


    public boolean isValid(String token, UserDetails user, boolean isRefreshToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                                      .withIssuer("api-security")
                                      .build();
            DecodedJWT jwt = verifier.verify(token);

            String username = jwt.getSubject();
            Date expiration = jwt.getExpiresAt();

            boolean validToken = tokenRepository
            .findByToken(token, isRefreshToken)
            .map(t -> !t.isLoggedOut())
            .orElse(false);


            return username.equals(user.getUsername()) && !isTokenExpired(expiration) && validToken;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }


    public String generateAccessToken(User user) {
        return generateToken(user, expirationToken);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, expirationRefreshToken );
    }


    public String validateToken(String token) throws JWTVerificationException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("api-security")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
           throw new JWTVerificationException("Invalid token: " + e.getMessage());
        }
    }


    private Instant generateExpirationDate(Integer expiration) {
        return LocalDateTime.now().plusHours(expiration).toInstant(ZoneOffset.UTC);
    }
}