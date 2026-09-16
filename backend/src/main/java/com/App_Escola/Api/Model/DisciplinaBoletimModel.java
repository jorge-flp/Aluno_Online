package com.App_Escola.Api.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinaBoletimModel {

    private String disciplina;

    private List<Double> notas;

    private Double media;

}