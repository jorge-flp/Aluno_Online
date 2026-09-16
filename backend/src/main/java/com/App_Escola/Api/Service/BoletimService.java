package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.BoletimConceitoModel;
import com.App_Escola.Api.Model.BoletimNotasModel;
import com.App_Escola.Api.Model.DisciplinaBoletimModel;
import com.App_Escola.Api.Model.NotaModel;

import com.App_Escola.Api.Repository.AlunoRepository;
import com.App_Escola.Api.Repository.NotaRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoletimService {

    private final AlunoRepository alunoRepository;
    private final NotaRepository notaRepository;

    public BoletimService(
            AlunoRepository alunoRepository,
            NotaRepository notaRepository
    ) {
        this.alunoRepository = alunoRepository;
        this.notaRepository = notaRepository;
    }

    // =========================================================
    // BOLETIM DE NOTAS
    // =========================================================

    public BoletimNotasModel buscarNotas(Integer matricula) {

        // 1. Busca o aluno
        AlunoModel aluno = alunoRepository.findById(matricula)
                .orElseThrow(() ->
                        new RuntimeException("Aluno não encontrado")
                );

        // 2. Busca todas as notas desse aluno
        List<NotaModel> notas =
                notaRepository.findByAluno_Matricula(matricula);

        // 3. Agrupa as notas por disciplina
        Map<String, List<Double>> notasPorDisciplina =
                new LinkedHashMap<>();

        for (NotaModel nota : notas) {

            String nomeDisciplina =
                    nota.getDisciplina().getNome();

            notasPorDisciplina
                    .computeIfAbsent(
                            nomeDisciplina,
                            chave -> new ArrayList<>()
                    )
                    .add(nota.getValor());
        }

        // 4. Monta as disciplinas do boletim
        List<DisciplinaBoletimModel> disciplinas =
                new ArrayList<>();

        for (Map.Entry<String, List<Double>> entry
                : notasPorDisciplina.entrySet()) {

            String nomeDisciplina = entry.getKey();

            List<Double> valores = entry.getValue();

            double mediaDisciplina = valores.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            DisciplinaBoletimModel disciplinaBoletim =
                    new DisciplinaBoletimModel();

            disciplinaBoletim.setDisciplina(nomeDisciplina);
            disciplinaBoletim.setNotas(valores);
            disciplinaBoletim.setMedia(
                    arredondar(mediaDisciplina)
            );

            disciplinas.add(disciplinaBoletim);
        }

        // 5. Calcula a média geral
        double mediaGeral = disciplinas.stream()
                .mapToDouble(
                        DisciplinaBoletimModel::getMedia
                )
                .average()
                .orElse(0.0);

        // 6. Descobre a turma do aluno
        String turma = null;

        if (aluno.getTurma() != null) {
            turma = aluno.getTurma().getNome();
        }

        // 7. Monta o boletim
        BoletimNotasModel boletim =
                new BoletimNotasModel();

        boletim.setMatricula(
                aluno.getMatricula()
        );

        boletim.setNome(
                aluno.getNome()
        );

        boletim.setTurma(
                turma
        );

        boletim.setDisciplinas(
                disciplinas
        );

        boletim.setMediaGeral(
                arredondar(mediaGeral)
        );

        return boletim;
    }

    // =========================================================
    // BOLETIM POR CONCEITO
    // =========================================================

    public BoletimConceitoModel buscarConceito(
            Integer matricula
    ) {

        BoletimNotasModel boletimNotas =
                buscarNotas(matricula);

        BoletimConceitoModel conceitoModel =
                new BoletimConceitoModel();

        conceitoModel.setMatricula(
                boletimNotas.getMatricula()
        );

        conceitoModel.setNome(
                boletimNotas.getNome()
        );

        conceitoModel.setMediaGeral(
                boletimNotas.getMediaGeral()
        );

        // Aluno ainda sem notas
        if (boletimNotas.getDisciplinas() == null ||
                boletimNotas.getDisciplinas().isEmpty()) {

            conceitoModel.setConceito(
                    "SEM AVALIAÇÃO"
            );

            conceitoModel.setFeedback(
                    "O aluno ainda não possui notas cadastradas."
            );

            return conceitoModel;
        }

        String conceito =
                calcularConceito(
                        boletimNotas.getMediaGeral()
                );

        conceitoModel.setConceito(conceito);

        conceitoModel.setFeedback(
                gerarFeedback(conceito)
        );

        return conceitoModel;
    }

    // =========================================================
    // CÁLCULO DO CONCEITO
    // =========================================================

    private String calcularConceito(double media) {

        if (media >= 9.0) {
            return "A";
        }

        if (media >= 7.0) {
            return "B";
        }

        if (media >= 5.0) {
            return "C";
        }

        return "D";
    }

    // =========================================================
    // FEEDBACK
    // =========================================================

    private String gerarFeedback(String conceito) {

        return switch (conceito) {

            case "A" ->
                    "Excelente desempenho acadêmico.";

            case "B" ->
                    "Bom desempenho acadêmico.";

            case "C" ->
                    "Desempenho regular. É recomendável reforçar os estudos.";

            case "D" ->
                    "O aluno necessita de maior acompanhamento acadêmico.";

            default ->
                    "Aluno ainda não avaliado.";
        };
    }

    // =========================================================
    // ARREDONDAMENTO
    // =========================================================

    private double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}