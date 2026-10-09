package com.App_Escola.Api.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "atestados")
public class Atestado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @Column(name = "url_arquivo", length = 1000)
    private String urlArquivo;

    @Column(name = "matricula")
    private Integer matricula;

    @Column(name = "professor_id")
    private Long professorId;

    public Atestado() {}

    public Atestado(String nomeArquivo, String urlArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.urlArquivo = urlArquivo;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeArquivo() { return nomeArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }

    public String getUrlArquivo() { return urlArquivo; }
    public void setUrlArquivo(String urlArquivo) { this.urlArquivo = urlArquivo; }

    public Integer getMatricula() { return matricula; }
    public void setMatricula(Integer matricula) { this.matricula = matricula; }

    public Long getProfessorId() { return professorId; }
    public void setProfessorId(Long professorId) { this.professorId = professorId; }
}