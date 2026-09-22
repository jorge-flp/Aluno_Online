package com.App_Escola.Api.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "nota_escrita")
public class NotaEscritaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "aluno_matricula", nullable = false)
    private AlunoModel aluno;

    @Column(name = "professor_id", nullable = false)
    private Long professorId;

    public NotaEscritaModel() {
    }

    public Long getId() {
        return id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }
}