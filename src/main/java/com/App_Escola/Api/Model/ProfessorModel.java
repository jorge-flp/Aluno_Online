package com.App_Escola.Api.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "professor")
public class ProfessorModel {

    @Id
    private Integer id_professor;

    private String nome;
    private String email;
    private String data_nascimento;

    @ManyToOne
    @JoinColumn(name = "escola_inep", nullable = false)
    private EscolaModel escola;

    public ProfessorModel() {
    }

    // Getters e Setters
    public Integer getId_professor() {
        return id_professor;
    }

    public void setId_professor(Integer id_professor) {
        this.id_professor = id_professor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public EscolaModel getEscola() {
        return escola;
    }

    public void setEscola(EscolaModel escola) {
        this.escola = escola;
    }
}