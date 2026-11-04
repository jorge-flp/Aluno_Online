package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.UsuarioModel;
import com.App_Escola.Api.Model.UsuarioModel.PerfilUsuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository
        extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPerfilAndPerfilId(
            PerfilUsuario perfil,
            Long perfilId
    );
}