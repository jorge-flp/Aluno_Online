package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.EscolaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscolaRepository extends JpaRepository<EscolaModel, Integer> {
}