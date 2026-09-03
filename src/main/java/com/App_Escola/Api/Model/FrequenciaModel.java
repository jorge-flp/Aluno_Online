package com.App_Escola.Api.Model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "frequencias")
public class FrequenciaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate data;

    private Boolean presente; // true para presente, false para falta

    // Relacionamentos comuns em frequência (ajuste conforme os seus outros models)
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private AlunoModel aluno;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private DisciplinaModel disciplina;
}