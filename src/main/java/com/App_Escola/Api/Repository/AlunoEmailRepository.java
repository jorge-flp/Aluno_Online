package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.AlunoEmailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoEmailRepository extends JpaRepository<AlunoEmailModel, Integer> {
}