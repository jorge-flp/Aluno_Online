package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.App_Escola.Api.Model.CursoModel;

public interface CursoRepository extends JpaRepository<CursoModel, Integer> {

}