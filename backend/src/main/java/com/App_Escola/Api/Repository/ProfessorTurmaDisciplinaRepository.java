package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.ProfessorTurmaDisciplinaModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessorTurmaDisciplinaRepository
        extends JpaRepository<ProfessorTurmaDisciplinaModel, Integer> {

    List<ProfessorTurmaDisciplinaModel>
    findByProfessor_IdProfessor(Integer idProfessor);

    List<ProfessorTurmaDisciplinaModel>
    findByTurma_IdTurma(Integer idTurma);

    List<ProfessorTurmaDisciplinaModel>
    findByDisciplina_IdDisciplina(Integer idDisciplina);

    boolean existsByProfessor_IdProfessorAndTurma_IdTurmaAndDisciplina_IdDisciplina(
            Integer idProfessor,
            Integer idTurma,
            Integer idDisciplina
    );
}