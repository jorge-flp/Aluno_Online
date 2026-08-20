package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.App_Escola.Api.Model.FrequenciaModel;

@Repository
public interface FrequenciaRepository extends JpaRepository<FrequenciaModel, Integer> {
}