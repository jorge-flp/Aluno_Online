package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.App_Escola.Api.Model.HistoricoEscolarModel;

public interface HistoricoEscolarRepository extends JpaRepository<HistoricoEscolarModel, Integer> {
}