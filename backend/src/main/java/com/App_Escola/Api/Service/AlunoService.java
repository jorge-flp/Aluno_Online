package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Repository.AlunoRepository;
import com.App_Escola.Api.Repository.TurmaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;

    public List<AlunoModel> listarTodos() {
        return alunoRepository.findAll();
    }

    public Optional<AlunoModel> buscarPorMatricula(Integer matricula) {
        return alunoRepository.findById(matricula);
    }

    public AlunoModel salvar(AlunoModel aluno) {

        if (aluno.getTurmaId() == null) {
            throw new RuntimeException("O ID da turma é obrigatório");
        }

        turmaRepository.findById(aluno.getTurmaId())
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        return alunoRepository.save(aluno);
    }

    public void deletar(Integer matricula) {
        alunoRepository.deleteById(matricula);
    }
}