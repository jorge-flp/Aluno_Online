package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository
        extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByEmail(String email);

    boolean existsByEmail(String email);
}