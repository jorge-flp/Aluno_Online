package com.App_Escola.Api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "aluno_responsavel",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"aluno_matricula", "responsavel_id"}
                )
        }
)
public class AlunoResponsavelModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno_responsavel")
    private Integer idAlunoResponsavel;

        @NotBlank(message = "O parentesco é obrigatório")
        @Column(name = "parentesco", nullable = false, length = 50)
    private String parentesco;

        @NotNull(message = "O aluno é obrigatório")
    @ManyToOne
    @JoinColumn(
            name = "aluno_matricula",
            referencedColumnName = "matricula",
            nullable = false
    )
    private AlunoModel aluno;

        @NotNull(message = "O responsável é obrigatório")
    @ManyToOne
    @JoinColumn(
            name = "responsavel_id",
            referencedColumnName = "id_responsavel",
            nullable = false
    )
    private ResponsavelModel responsavel;
}