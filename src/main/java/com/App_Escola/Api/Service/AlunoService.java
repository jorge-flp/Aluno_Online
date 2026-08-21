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
        // Supondo que getTurmaId() retorne o ID diretamente ou que você ajuste para
        // pegar o ID correto:
        Integer turmaId = aluno.getTurmaId();

        System.out.println("=================================");
        System.out.println("ID DA TURMA RECEBIDO: " + turmaId);
        System.out.println("=================================");

        TurmaModel turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada. ID recebido: " + turmaId));

        // Se o seu model aceita o objeto TurmaModel:
        aluno.setTurmaId(turmaId);

        // OU se aceita apenas o ID:
        // aluno.setTurmaId(turmaId);

        return alunoRepository.save(aluno);
    }

    public void deletar(Integer matricula) {
        alunoRepository.deleteById(matricula);
    }
}