package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.NotaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaRepository extends JpaRepository<NotaModel, Integer> {
}