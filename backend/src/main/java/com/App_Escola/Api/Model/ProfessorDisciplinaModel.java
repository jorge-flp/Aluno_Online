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
@Table(name = "professor_disciplina", uniqueConstraints = {
        @UniqueConstraint(name = "uk_professor_disciplina", columnNames = { "professor_matricula", "id_disciplina" })
})
public class ProfessorDisciplinaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor_disciplina")
    private Integer id_professor_disciplina;

    @Column(name = "data_inicio")
    private LocalDate data_inicio;

    @Column(name = "data_fim")
    private LocalDate data_fim;

    @ManyToOne
    @JoinColumn(name = "professor_matricula", nullable = false)
    private ProfessorModel professor;

    @ManyToOne
    @JoinColumn(name = "id_disciplina", nullable = false)
    private DisciplinaModel disciplina;

    public ProfessorDisciplinaModel() {
    }

    public Integer getId_professor_disciplina() {
        return id_professor_disciplina;
    }

    public void setId_professor_disciplina(Integer id_professor_disciplina) {
        this.id_professor_disciplina = id_professor_disciplina;
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

    public ProfessorModel getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorModel professor) {
        this.professor = professor;
    }

    public DisciplinaModel getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(DisciplinaModel disciplina) {
        this.disciplina = disciplina;
    }
}