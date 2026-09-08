package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.ResponsavelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsavelRepository extends JpaRepository<ResponsavelModel, Integer> {
}