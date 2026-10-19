package com.App_Escola.Api.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoletimConceitoModel {

    private Integer matricula;

    private String nome;

    private Double mediaGeral;

    private String conceito;

    private String feedback;
}