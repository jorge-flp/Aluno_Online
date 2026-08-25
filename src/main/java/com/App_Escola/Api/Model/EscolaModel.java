package com.App_Escola.Api.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EscolaModel {

    @Id
    @Column(length = 20)
    private String inep;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 200)
    private String endereco;

    @Column(length = 10)
    private String cep;

    @Column(length = 20)
    private String telefone;

    @Column(length = 100)
    private String email;

    public String getInep() {
        return inep;
    }

    public void setInep(String inep) {
        this.inep = inep;
    }
}
