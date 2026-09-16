package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.DisciplinaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository
        extends JpaRepository<DisciplinaModel, Integer> {
}