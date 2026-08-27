package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.App_Escola.Api.Model.TurmaCursoModel;

public interface TurmaCursoRepository extends JpaRepository<TurmaCursoModel, Integer> {
}