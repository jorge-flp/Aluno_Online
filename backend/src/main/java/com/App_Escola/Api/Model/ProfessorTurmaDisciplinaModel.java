package com.App_Escola.Api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "professor_turma_disciplina",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "professor_id",
                                "turma_id",
                                "disciplina_id"
                        }
                )
        }
)
public class ProfessorTurmaDisciplinaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor_turma_disciplina")
    private Integer idProfessorTurmaDisciplina;

        @NotNull(message = "O professor é obrigatório")
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "professor_id",
            referencedColumnName = "id_professor",
            nullable = false
    )
    private ProfessorModel professor;

        @NotNull(message = "A turma é obrigatória")
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "turma_id",
            referencedColumnName = "id_turma",
            nullable = false
    )
    private TurmaModel turma; 

        @NotNull(message = "A disciplina é obrigatória")
    @ManyToOne(optional = false)
    @JoinColumn(
            name = "disciplina_id",
            referencedColumnName = "id_disciplina",
            nullable = false
    )
    private DisciplinaModel disciplina;
}