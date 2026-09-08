package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.JustificativaFaltaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JustificativaFaltaRepository
        extends JpaRepository<JustificativaFaltaModel, Integer> {
}