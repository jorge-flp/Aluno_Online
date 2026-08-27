package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.App_Escola.Api.Model.TurmaModel;

public interface TurmaRepository extends JpaRepository<TurmaModel, Integer> {
}