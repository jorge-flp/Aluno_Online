package com.App_Escola.Api.DTO;

public record LoginRequest(
        String email,
        String senha
) {}