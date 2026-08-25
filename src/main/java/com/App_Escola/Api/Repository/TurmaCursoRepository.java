package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.TurmaCursoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaCursoRepository extends JpaRepository<TurmaCursoRepository, Integer> {
}