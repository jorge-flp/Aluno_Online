package com.App_Escola.Api.Repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.App_Escola.Api.Model.ProfessorModel;

@Repository
public interface ProfessorRepository extends JpaRepository<ProfessorModel, Integer> {

}