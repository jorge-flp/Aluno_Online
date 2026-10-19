package com.App_Escola.Api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "aluno",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "cpf")
        }
)
public class AlunoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matricula")
    private Integer matricula;

    @NotBlank(message = "O nome é obrigatório")
    @Size(
            max = 100,
            message = "O nome deve ter no máximo 100 caracteres"
    )
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @CPF(message = "CPF inválido")
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Email(message = "E-mail inválido")
    @Column(unique = true, length = 150)
    private String email;

    @Past(message = "A data de nascimento deve estar no passado")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_turma",
            nullable = false
    )
    private TurmaModel turma;
}