package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.TurmaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends JpaRepository<TurmaModel, Integer> {
}