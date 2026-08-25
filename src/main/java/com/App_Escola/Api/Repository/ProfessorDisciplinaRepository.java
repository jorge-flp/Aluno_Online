package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.ProfessorDisciplinaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorDisciplinaRepository extends JpaRepository<ProfessorDisciplinaRepository, Integer> {
}