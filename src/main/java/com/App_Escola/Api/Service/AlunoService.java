package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Repository.AlunoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public List<AlunoModel> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<AlunoModel> buscarPorMatricula(Integer id) {
        return alunoRepository.findById(id);
    }

    public AlunoModel salvar(AlunoModel aluno) {
        return alunoRepository.save(aluno);
    }

    public void deletar(Integer id) {
        alunoRepository.deleteById(id);
    }
}