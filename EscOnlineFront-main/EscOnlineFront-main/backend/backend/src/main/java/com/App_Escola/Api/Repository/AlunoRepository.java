package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.AlunoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository
        extends JpaRepository<AlunoModel, Integer> {
}