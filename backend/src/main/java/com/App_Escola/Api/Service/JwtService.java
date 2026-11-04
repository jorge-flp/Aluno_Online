package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.UsuarioModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;

    @Value("${app.jwt.expiration:3600000}")
    private long expiration;

    public JwtService(
            @Value("${app.jwt.secret}") String secret
    ) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException(
                    "A chave JWT deve possuir pelo menos 32 caracteres."
            );
        }

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String gerarToken(UsuarioModel usuario) {

        Date agora = new Date();
        Date expiracao = new Date(
                agora.getTime() + expiration
        );

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("perfil", usuario.getPerfil().name())
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(secretKey)
                .compact();
    }

    public String validarToken(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}