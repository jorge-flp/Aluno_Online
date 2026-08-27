package com.App_Escola.Api.Model;

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
@Table(name = "aluno_responsavel", uniqueConstraints = {
        @UniqueConstraint(name = "uk_aluno_responsavel", columnNames = { "aluno_matricula", "id_responsavel" })
})
public class AlunoResponsavelModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno_responsavel")
    private Integer id_aluno_responsavel;

    @Column(length = 50)
    private String parentesco;

    @Column(name = "responsavel_principal", nullable = false)
    private Boolean responsavel_principal = false;

    @ManyToOne
    @JoinColumn(name = "aluno_matricula", nullable = false)
    private AlunoModel aluno;

    @ManyToOne
    @JoinColumn(name = "id_responsavel", nullable = false)
    private ResponsavelModel responsavel;

    public AlunoResponsavelModel() {
    }

    public Integer getId_aluno_responsavel() {
        return id_aluno_responsavel;
    }

    public void setId_aluno_responsavel(Integer id_aluno_responsavel) {
        this.id_aluno_responsavel = id_aluno_responsavel;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Boolean getResponsavel_principal() {
        return responsavel_principal;
    }

    public void setResponsavel_principal(Boolean responsavel_principal) {
        this.responsavel_principal = responsavel_principal;
    }

    public AlunoModel getAluno() {
        return aluno;
    }

    public void setAluno(AlunoModel aluno) {
        this.aluno = aluno;
    }

    public ResponsavelModel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ResponsavelModel responsavel) {
        this.responsavel = responsavel;
    }
}