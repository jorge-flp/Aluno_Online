package com.App_Escola.Api.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoletimNotasModel {

    private Integer matricula;

    private String nome;

    private String turma;

    private List<DisciplinaBoletimModel> disciplinas;

    private Double mediaGeral;
}