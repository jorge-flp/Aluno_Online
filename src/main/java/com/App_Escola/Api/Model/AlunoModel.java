package com.App_Escola.Api.Model;

import  jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "aluno")
public class AlunoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matricula;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @Email(message = "O e-mail deve ser válido")
    @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres")
    @Column(unique = true, length = 150)
    private String email;

    @Past(message = "A data de nascimento deve ser anterior à data atual")
    private LocalDate dataNascimento;
}