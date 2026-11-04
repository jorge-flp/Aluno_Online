package com.App_Escola.Api.Service;

import com.App_Escola.Api.DTO.LoginRequest;
import com.App_Escola.Api.DTO.LoginResponse;
import com.App_Escola.Api.Model.UsuarioModel;
import com.App_Escola.Api.Repository.UsuarioRepository;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        UsuarioModel usuario = usuarioRepository
                .findByEmail(request.email())
                .orElseThrow(() ->
                        new BadCredentialsException("Credenciais inválidas")
                );

        if (!passwordEncoder.matches(
                request.senha(),
                usuario.getSenha()
        )) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponse(
                token,
                "Bearer",
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil().name()
        );
    }
}