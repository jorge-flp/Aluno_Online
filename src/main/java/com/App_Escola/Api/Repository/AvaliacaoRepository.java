package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.AvaliacaoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoRepository, Integer> {
}