package com.App_Escola.Api.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "boletim")
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class BoletimModel {
    
    @Id 
    @Column 
    private Integer idBoletim;

    @Column 
    private String materia;

    @Column
    private String nota;

    @Column 
    private Double frequencia;

    @Column 
    private Double mediaGeral;

    @Column
    private String situacao;

    @Column 
    private String feedback;

}