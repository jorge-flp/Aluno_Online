package com.App_Escola.Api.Model;

import jakarta.persistence.*;
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

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "professor_id",
            referencedColumnName = "id_professor",
            nullable = false
    )
    private ProfessorModel professor;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "turma_id",
            referencedColumnName = "id_turma",
            nullable = false
    )
    private TurmaModel turma; 

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "disciplina_id",
            referencedColumnName = "id_disciplina",
            nullable = false
    )
    private DisciplinaModel disciplina;
}