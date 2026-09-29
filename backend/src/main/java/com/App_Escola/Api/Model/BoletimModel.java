package com.App_Escola.Api.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "boletim",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "aluno_matricula",
                                "ano_letivo"
                        }
                )
        }
)
public class BoletimModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boletim")
    private Integer idBoletim;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "aluno_matricula",
            referencedColumnName = "matricula",
            nullable = false
    )
    private AlunoModel aluno;

    @Column(
            name = "ano_letivo",
            nullable = false
    )
    private Integer anoLetivo;

    @Column(
            name = "media_geral"
    )
    private Double mediaGeral;

    @Column(
            length = 20
    )
    private String conceito;

    @Column(
            length = 255
    )
    private String feedback;

    @Column(
            name = "data_geracao"
    )
    private LocalDate dataGeracao;
}