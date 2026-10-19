package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.AlunoResponsavelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoResponsavelRepository
        extends JpaRepository<AlunoResponsavelModel, Integer> {

    Optional<AlunoResponsavelModel> findByAluno_MatriculaAndResponsavel_IdResponsavel(
            Integer matricula,
            Integer idResponsavel
    );
}