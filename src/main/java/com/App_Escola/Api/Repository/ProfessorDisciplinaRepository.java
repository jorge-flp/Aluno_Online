package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.App_Escola.Api.Model.ProfessorDisciplinaModel;

public interface ProfessorDisciplinaRepository
        extends JpaRepository<ProfessorDisciplinaModel, Integer> {
}