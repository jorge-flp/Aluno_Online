package com.App_Escola.Api.DTO;

import com.App_Escola.Api.Model.UsuarioModel.PerfilUsuario;

import jakarta.validation.constraints.*;

public record CadastroUsuarioRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 100)
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 255)
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 12, max = 72,
              message = "A senha deve ter entre 12 e 72 caracteres")
        String senha,

        @NotNull(message = "O perfil é obrigatório")
        PerfilUsuario perfil,

        @NotNull(message = "O ID do perfil é obrigatório")
        @Positive(message = "O ID deve ser positivo")
        Long perfilId

) {}