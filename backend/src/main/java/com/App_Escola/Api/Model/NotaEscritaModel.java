package com.App_Escola.Api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "nota_escrita")
public class NotaEscritaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotBlank(message = "O feedback é obrigatório")
    private String feedback;

    @ManyToOne
    @NotNull(message = "O aluno é obrigatório")
    @JoinColumn(name = "aluno_matricula", nullable = false)
    private AlunoModel aluno;

    @Column(name = "professor_id", nullable = false)
    @NotNull(message = "O ID do professor é obrigatório")
    @Positive(message = "O ID do professor deve ser positivo")
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