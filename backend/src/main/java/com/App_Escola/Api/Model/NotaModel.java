package com.App_Escola.Api.Model;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "nota")
public class NotaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_nota;

    private BigDecimal n_parcial;
    private BigDecimal n_global;
    private String n_qualitativa;

    @ManyToOne
    @JoinColumn(name = "id_boletim", nullable = false)
    private BoletimModel boletim;

    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    private DisciplinaModel disciplina;

    public NotaModel() {
    }

    // Getters e Setters
    public Integer getId_nota() {
        return id_nota;
    }

    public void setId_nota(Integer id_nota) {
        this.id_nota = id_nota;
    }

    public BigDecimal getN_parcial() {
        return n_parcial;
    }

    public void setN_parcial(BigDecimal n_parcial) {
        this.n_parcial = n_parcial;
    }

    public BigDecimal getN_global() {
        return n_global;
    }

    public void setN_global(BigDecimal n_global) {
        this.n_global = n_global;
    }

    public String getN_qualitativa() {
        return n_qualitativa;
    }

    public void setN_qualitativa(String n_qualitativa) {
        this.n_qualitativa = n_qualitativa;
    }

    public BoletimModel getBoletim() {
        return boletim;
    }

    public void setBoletim(BoletimModel boletim) {
        this.boletim = boletim;
    }

    public DisciplinaModel getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaModel disciplina) {
        this.disciplina = disciplina;
    }
}