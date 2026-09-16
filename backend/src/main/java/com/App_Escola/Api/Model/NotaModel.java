package com.App_Escola.Api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "nota")
public class NotaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Integer idNota;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "aluno_matricula",
            referencedColumnName = "matricula",
            nullable = false
    )
    private AlunoModel aluno;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "disciplina_id",
            referencedColumnName = "id_disciplina",
            nullable = false
    )
    private DisciplinaModel disciplina;

    @NotNull(message = "O bimestre é obrigatório")
    @Min(value = 1, message = "O bimestre mínimo é 1")
    @Max(value = 4, message = "O bimestre máximo é 4")
    @Column(nullable = false)
    private Integer bimestre;

    @NotNull(message = "A nota é obrigatória")
    @Min(value = 0, message = "A nota mínima é 0")
    @Max(value = 10, message = "A nota máxima é 10")
    @Column(nullable = false)
    private Double valor;
}