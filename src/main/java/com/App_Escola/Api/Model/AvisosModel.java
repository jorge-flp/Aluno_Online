package com.App_Escola.Api.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "avisos")
public class AvisosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aviso")
    private Integer id_aviso;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDateTime data_publicacao;

    @Column(name = "data_expiracao")
    private LocalDateTime data_expiracao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "escola_inep", nullable = false)
    private EscolaModel escola;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    private TurmaModel turma;

    public AvisosModel() {
    }

    public Integer getId_aviso() {
        return id_aviso;
    }

    public void setId_aviso(Integer id_aviso) {
        this.id_aviso = id_aviso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getData_publicacao() {
        return data_publicacao;
    }

    public void setData_publicacao(LocalDateTime data_publicacao) {
        this.data_publicacao = data_publicacao;
    }

    public LocalDateTime getData_expiracao() {
        return data_expiracao;
    }

    public void setData_expiracao(LocalDateTime data_expiracao) {
        this.data_expiracao = data_expiracao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public EscolaModel getEscola() {
        return escola;
    }

    public void setEscola(EscolaModel escola) {
        this.escola = escola;
    }

    public TurmaModel getTurma() {
        return turma;
    }

    public void setTurma(TurmaModel turma) {
        this.turma = turma;
    }
}