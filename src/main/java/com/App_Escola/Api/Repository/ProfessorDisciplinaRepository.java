package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.ProfessorDisciplinaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorDisciplinaRepository extends JpaRepository<ProfessorDisciplinaRepository, Integer> {

    ProfessorDisciplinaModel save(ProfessorDisciplinaModel professorDisciplina);
}