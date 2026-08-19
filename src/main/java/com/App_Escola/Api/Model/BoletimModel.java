package com.App_Escola.Api.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "boletim")
public class BoletimModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_boletim;

    private Integer ano_letivo;
    private String bimestre;

    @OneToOne
    @JoinColumn(name = "aluno_matricula", nullable = false, unique = true)
    private AlunoModel aluno;

    public BoletimModel() {
    }

    // Getters e Setters
    public Integer getId_boletim() {
        return id_boletim;
    }

    public void setId_boletim(Integer id_boletim) {
        this.id_boletim = id_boletim;
    }

    public Integer getAno_letivo() {
        return ano_letivo;
    }

    public void setAno_letivo(Integer ano_letivo) {
        this.ano_letivo = ano_letivo;
    }

    public String getBimestre() {
        return bimestre;
    }

    public void setBimestre(String bimestre) {
        this.bimestre = bimestre;
    }

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }
}