package com.App_Escola.Api.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "aluno_email")
public class AlunoEmailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_email;

    private String email;

    @ManyToOne
    @JoinColumn(name = "aluno_matricula", nullable = false)
    private AlunoModel aluno;

    public AlunoEmailModel() {
    }

    // Getters e Setters
    public Integer getId_email() {
        return id_email;
    }

    public void setId_email(Integer id_email) {
        this.id_email = id_email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }
}