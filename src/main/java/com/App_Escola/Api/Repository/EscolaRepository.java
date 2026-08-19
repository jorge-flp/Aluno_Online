package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.App_Escola.Api.Model.EscolaModel;

public interface EscolaRepository extends JpaRepository<EscolaModel, String> {

}
