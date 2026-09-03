package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.App_Escola.Api.Model.DisciplinaModel;

@Repository
public interface DisciplinaRepository extends JpaRepository<DisciplinaModel, Integer> {
}