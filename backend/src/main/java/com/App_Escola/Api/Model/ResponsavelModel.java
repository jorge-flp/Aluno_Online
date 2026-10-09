package com.App_Escola.Api.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "responsavel")
public class ResponsavelModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_responsavel")
    private Integer idResponsavel;

    @NotBlank(message = "O nome do responsável é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @CPF(message = "CPF inválido")
    @Column(unique = true, length = 14)
    private String cpf;

    @Email(message = "E-mail inválido")
    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String telefone;
}