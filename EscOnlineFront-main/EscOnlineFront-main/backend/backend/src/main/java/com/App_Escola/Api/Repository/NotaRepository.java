package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.NotaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository
        extends JpaRepository<NotaModel, Integer> {

    List<NotaModel> findByAluno_Matricula(Integer matricula);
}