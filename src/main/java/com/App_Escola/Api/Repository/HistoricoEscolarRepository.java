package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.EscolaModel;
import com.App_Escola.Api.Model.HistoricoEscolarModel;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoEscolarRepository extends JpaRepository<HistoricoEscolarRepository, Integer> {

    HistoricoEscolarModel save(HistoricoEscolarModel historico);

    void setAnoLetivo(Integer ano_letivo);

    void setEscola(EscolaModel escola);

    void setSerie(String serie);

    void setSituacao(String situacao);

    void setMedia_final(BigDecimal media_final);

    void setObservacao(String observacao);

    void setAluno(AlunoModel aluno);
}