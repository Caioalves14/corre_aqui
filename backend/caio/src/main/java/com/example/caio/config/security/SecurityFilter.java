package com.example.caio.config.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.caio.repository.IUserRepository;
import com.example.caio.service.auth.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    

    private final TokenService tokenService;

    private final IUserRepository userRepository;


   @Override
   protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
           throws ServletException, IOException {
       String token = this.recoveryToken(request);

       if (token != null) {
            String login = tokenService.validateToken(token);
            UserDetails user = userRepository.findByLogin(login);

            if (tokenService.isValid(token, user, false)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
       }

       filterChain.doFilter(request, response);
   }


   private String recoveryToken(HttpServletRequest httpServletRequest) {
        String authReader = httpServletRequest.getHeader("Authorization");
        if (authReader == null) {
            return null;
        }

        return authReader.replace("Bearer ","");
   }
}