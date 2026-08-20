package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.AtividadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends JpaRepository<AtividadeModel, Integer> {
}