package com.App_Escola.Api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.App_Escola.Api.Model.FrequenciaModel;

@Repository
public interface FrequenciaRepository extends JpaRepository<FrequenciaModel, Integer> {
    // Como o seu Service já usa o JpaRepository implícito através do Spring Data,
    // o básico já está pronto. Você pode adicionar consultas personalizadas aqui se precisar no futuro.
}