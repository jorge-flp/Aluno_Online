package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.FrequenciaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrequenciaRepository extends JpaRepository<FrequenciaModel, Integer> {
}