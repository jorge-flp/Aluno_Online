package com.App_Escola.Api.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "O ano/série é obrigatório")
    @Size(max = 50, message = "O ano/série deve ter no máximo 50 caracteres")
    @Column(name = "ano_serie", nullable = false, length = 50)
    private String anoSerie;

    @NotBlank(message = "O nome da turma é obrigatório")
    @Size(max = 100, message = "O nome da turma deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @OneToMany(mappedBy = "turma")
    @JsonIgnore
    private List<AlunoModel> alunos;
}