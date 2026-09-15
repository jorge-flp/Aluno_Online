package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.BoletimModel;
import com.App_Escola.Api.Repository.AlunoRepository;
import org.springframework.stereotype.Service;

@Service
public class BoletimService {

    private final AlunoRepository alunoRepository;

    public BoletimService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public BoletimModel buscarBoletimDoAluno(Integer matricula) {

        AlunoModel aluno = alunoRepository.findById(matricula)
                .orElseThrow(() ->
                        new RuntimeException("Aluno não encontrado"));

        BoletimModel boletim = new BoletimModel();

        boletim.setMatricula(aluno.getMatricula());
        boletim.setNome(aluno.getNome());

        if (aluno.getTurma() != null) {
            boletim.setTurma(aluno.getTurma().getNome());
        }

        // Por enquanto, valores de exemplo.
        boletim.setMediaGeral(8.5);
        boletim.setFrequencia(95.0);
        boletim.setSituacao("APROVADO");
        boletim.setFeedback(
                "O aluno apresenta bom desempenho acadêmico e frequência satisfatória."
        );

        return boletim;
    }
}