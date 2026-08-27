package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.App_Escola.Api.Model.ResponsavelModel;

public interface ResponsavelRepository extends JpaRepository<ResponsavelModel, Integer> {
}