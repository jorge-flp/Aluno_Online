package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.ProfessorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository
        extends JpaRepository<ProfessorModel, Integer> {
}