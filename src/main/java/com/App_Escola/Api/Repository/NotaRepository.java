package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.App_Escola.Api.Model.NotaModel;

@Repository
public interface NotaRepository extends JpaRepository<NotaModel, Integer> {
}