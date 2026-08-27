package com.App_Escola.Api.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.App_Escola.Api.Model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {

    Optional<UsuarioModel> findByUsername(String username);

    boolean existsByUsername(String username);
}