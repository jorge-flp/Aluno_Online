package com.App_Escola.Api.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "disciplina")
public class DisciplinaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_disciplina;

    private String nome;

    private Integer carga_horaria;

    @OneToMany(mappedBy = "disciplina")
    @JsonIgnore
    private List<ProfessorDisciplinaModel> professores = new ArrayList<>();

    public DisciplinaModel() {
    }

    public Integer getId_disciplina() {
        return id_disciplina;
    }

    public void setId_disciplina(Integer id_disciplina) {
        this.id_disciplina = id_disciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(Integer carga_horaria) {
        this.carga_horaria = carga_horaria;
    }

    public List<ProfessorDisciplinaModel> getProfessores() {
        return professores;
    }

    public void setProfessores(List<ProfessorDisciplinaModel> professores) {
        this.professores = professores;
    }
}