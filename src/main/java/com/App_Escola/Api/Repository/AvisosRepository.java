package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.AvisosRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisosRepository extends JpaRepository<AvisosRepository, Integer> {
}