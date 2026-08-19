package com.App_Escola.Api.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "turma")
public class TurmaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_turma;

    private String nome;
    private String ano_serie;

    @ManyToOne
    @JoinColumn(name = "escola_inep", nullable = false)
    private EscolaModel escola;

    public TurmaModel() {
    }

    // Getters e Setters
    public Integer getId_turma() {
        return getId_turma();
    }

    public void setId_turma(Integer id_turma) {
        this.id_turma = id_turma;
    }

    public String getAno_serie() {
        return getAno_serie();
    }

    public void setAno_serie(String ano_serie) {
        this.ano_serie = ano_serie;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EscolaModel getEscola() {
        return escola;
    }

    public void setEscola(EscolaModel escola) {
        this.escola = escola;
    }
}
