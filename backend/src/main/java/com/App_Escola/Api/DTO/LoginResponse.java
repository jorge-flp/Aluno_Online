package com.App_Escola.Api.DTO;

public record LoginResponse(
        String token,
        String tipo,
        Long id,
        String nome,
        String email,
        String perfil
) {}