package com.App_Escola.Api.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
@Entity
@Table(name = "Secretario")
public class SecretarioModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String telefone;

}
