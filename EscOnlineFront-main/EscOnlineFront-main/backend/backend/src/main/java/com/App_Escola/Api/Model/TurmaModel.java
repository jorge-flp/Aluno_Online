package com.App_Escola.Api.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "turma")
public class TurmaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turma")
    private Integer idTurma;

    @Column(name = "ano_serie", nullable = false, length = 50)
    private String anoSerie;

    @Column(nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "turma")
    @JsonIgnore
    private List<AlunoModel> alunos;
}