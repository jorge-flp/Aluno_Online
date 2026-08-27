package com.App_Escola.Api.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "turma_curso", uniqueConstraints = {
        @UniqueConstraint(name = "uk_turma_curso", columnNames = { "id_turma", "id_curso" })
})
public class TurmaCursoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma_curso")
    private Integer id_turma_curso;

    private LocalDate data_inicio;

    private LocalDate data_fim;

    @ManyToOne
    @JoinColumn(name = "id_turma", nullable = false)
    private TurmaModel turma;

    @ManyToOne
    @JoinColumn(name = "id_curso", nullable = false)
    private CursoModel curso;

    public TurmaCursoModel() {
    }

    public Integer getId_turma_curso() {
        return id_turma_curso;
    }

    public void setId_turma_curso(Integer id_turma_curso) {
        this.id_turma_curso = id_turma_curso;
    }

    public LocalDate getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(LocalDate data_inicio) {
        this.data_inicio = data_inicio;
    }

    public LocalDate getData_fim() {
        return data_fim;
    }

    public void setData_fim(LocalDate data_fim) {
        this.data_fim = data_fim;
    }

    public TurmaModel getTurma() {
        return turma;
    }

    public void setTurma(TurmaModel turma) {
        this.turma = turma;
    }

    public CursoModel getCurso() {
        return curso;
    }

    public void setCurso(CursoModel curso) {
        this.curso = curso;
    }
}