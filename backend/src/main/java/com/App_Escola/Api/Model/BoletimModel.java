package com.App_Escola.Api.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoletimModel {

    private Integer matricula;
    private String nome;
    private String turma;
    private Double mediaGeral;
    private Double frequencia;
    private String situacao;
    private String feedback;
}