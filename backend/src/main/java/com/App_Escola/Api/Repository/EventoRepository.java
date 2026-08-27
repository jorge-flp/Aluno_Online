package com.App_Escola.Api.Repository;


import org.springframework.stereotype.Repository;

import com.App_Escola.Api.Model.EventoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<EventoModel, Integer> {
}