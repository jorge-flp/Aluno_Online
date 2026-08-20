package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Repository.AlunoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class AlunoService {

    private AlunoRepository alunoRepository;

    public List<AlunoModel> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<AlunoModel> buscarPorMatricula(Integer matricula) {
        return alunoRepository.findByMatricula(matricula);
    }

    public AlunoModel salvar(AlunoModel aluno) {
        // Validar cpf ou regras do aluno no futuro
        return alunoRepository.save(aluno);
    }

    public void deletar(Integer matricula) {
        alunoRepository.deleteById(matricula);
    }

}
