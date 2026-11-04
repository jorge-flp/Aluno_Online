package com.App_Escola.Api.DTO;

import com.App_Escola.Api.Model.UsuarioModel.PerfilUsuario;

import java.time.LocalDateTime;

public record UsuarioResponse(

        Long id,
        String nome,
        String email,
        PerfilUsuario perfil,
        Long perfilId,
        LocalDateTime criadoEm

) {}