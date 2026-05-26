package com.ada.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Set;

@ApplicationScoped
public class TokenService {

    public String gerarToken(String email, String role) {
        return Jwt.issuer("https://loja.com")
                .subject(email)
                .groups(Set.of(role))
                .expiresIn(Duration.ofHours(1))
                .sign();
    }
}

