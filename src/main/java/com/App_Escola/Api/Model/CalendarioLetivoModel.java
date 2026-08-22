package com.App_Escola.Api.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "calendario_letivo")
public class CalendarioLetivoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_calendario;

    private Integer ano;
    private String periodo;

    @OneToOne
    @JoinColumn(name = "escola_inep", nullable = false, unique = true)
    private EscolaModel escola;

    public CalendarioLetivoModel() {
    }

    // Getters e Setters
    public Integer getId_calendario() {
        return id_calendario;
    }

    public void setId_calendario(Integer id_calendario) {
        this.id_calendario = id_calendario;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public EscolaModel getEscola() {
        return escola;
    }

    public void setEscola(EscolaModel escola) {
        this.escola = escola;
    }

}