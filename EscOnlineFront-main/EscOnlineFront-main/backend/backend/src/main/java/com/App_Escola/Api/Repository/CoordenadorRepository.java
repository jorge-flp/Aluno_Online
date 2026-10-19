package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.CoordenadorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadorRepository extends JpaRepository<CoordenadorModel, Integer> {
}