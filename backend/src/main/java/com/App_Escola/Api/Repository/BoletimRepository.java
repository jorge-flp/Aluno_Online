package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.BoletimModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoletimRepository
        extends JpaRepository<BoletimModel, Integer> {

    List<BoletimModel> findByAluno_Matricula(
            Integer matricula
    );

    Optional<BoletimModel>
    findByAluno_MatriculaAndAnoLetivo(
            Integer matricula,
            Integer anoLetivo
    );
}