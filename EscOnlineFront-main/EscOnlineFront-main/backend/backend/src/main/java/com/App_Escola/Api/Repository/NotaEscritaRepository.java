package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.NotaEscritaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaEscritaRepository extends JpaRepository<NotaEscritaModel, Long> {

    List<NotaEscritaModel> findByAlunoMatricula(Long matricula);

    List<NotaEscritaModel> findByProfessorId(Long professorId);
}