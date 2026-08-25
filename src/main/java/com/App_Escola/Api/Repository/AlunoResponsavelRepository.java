package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.AlunoResponsavelRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoResponsavelRepository extends JpaRepository<AlunoResponsavelRepository, Integer> {
}