package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.BoletimConceitoModel;
import com.App_Escola.Api.Model.BoletimModel;
import com.App_Escola.Api.Model.BoletimNotasModel;
import com.App_Escola.Api.Model.DisciplinaBoletimModel;
import com.App_Escola.Api.Model.NotaModel;

import com.App_Escola.Api.Repository.AlunoRepository;
import com.App_Escola.Api.Repository.BoletimRepository;
import com.App_Escola.Api.Repository.NotaRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BoletimService {

    private final AlunoRepository alunoRepository;
    private final NotaRepository notaRepository;
    private final BoletimRepository boletimRepository;

    public BoletimService(
            AlunoRepository alunoRepository,
            NotaRepository notaRepository,
            BoletimRepository boletimRepository
    ) {
        this.alunoRepository = alunoRepository;
        this.notaRepository = notaRepository;
        this.boletimRepository = boletimRepository;
    }

    // =========================================================
    // BOLETIM DETALHADO DE NOTAS
    // =========================================================

    public BoletimNotasModel buscarNotas(Integer matricula) {

        AlunoModel aluno = alunoRepository.findById(matricula)
                .orElseThrow(() ->
                        new RuntimeException("Aluno não encontrado")
                );

        List<NotaModel> notas =
                notaRepository.findByAluno_Matricula(matricula);

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

        List<DisciplinaBoletimModel> disciplinas =
                new ArrayList<>();

        for (Map.Entry<String, List<Double>> entry
                : notasPorDisciplina.entrySet()) {

            String nomeDisciplina =
                    entry.getKey();

            List<Double> valores =
                    entry.getValue();

            double mediaDisciplina =
                    valores.stream()
                            .mapToDouble(Double::doubleValue)
                            .average()
                            .orElse(0.0);

            DisciplinaBoletimModel disciplina =
                    new DisciplinaBoletimModel();

            disciplina.setDisciplina(
                    nomeDisciplina
            );

            disciplina.setNotas(
                    valores
            );

            disciplina.setMedia(
                    arredondar(mediaDisciplina)
            );

            disciplinas.add(
                    disciplina
            );
        }

        double mediaGeral =
                disciplinas.stream()
                        .mapToDouble(
                                DisciplinaBoletimModel::getMedia
                        )
                        .average()
                        .orElse(0.0);

        String turma = null;

        if (aluno.getTurma() != null) {
            turma = aluno.getTurma().getNome();
        }

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

        BoletimConceitoModel conceito =
                new BoletimConceitoModel();

        conceito.setMatricula(
                boletimNotas.getMatricula()
        );

        conceito.setNome(
                boletimNotas.getNome()
        );

        conceito.setMediaGeral(
                boletimNotas.getMediaGeral()
        );

        if (boletimNotas.getDisciplinas() == null ||
                boletimNotas.getDisciplinas().isEmpty()) {

            conceito.setConceito(
                    "SEM AVALIAÇÃO"
            );

            conceito.setFeedback(
                    "O aluno ainda não possui notas cadastradas."
            );

            return conceito;
        }

        String valorConceito =
                calcularConceito(
                        boletimNotas.getMediaGeral()
                );

        conceito.setConceito(
                valorConceito
        );

        conceito.setFeedback(
                gerarFeedback(valorConceito)
        );

        return conceito;
    }

    // =========================================================
    // GERA E SALVA BOLETIM NO AIVEN
    // =========================================================

    public BoletimModel gerarBoletim(
            Integer matricula,
            Integer anoLetivo
    ) {

        AlunoModel aluno =
                alunoRepository.findById(matricula)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Aluno não encontrado"
                                )
                        );

        BoletimConceitoModel conceito =
                buscarConceito(matricula);

        Optional<BoletimModel> boletimExistente =
                boletimRepository
                        .findByAluno_MatriculaAndAnoLetivo(
                                matricula,
                                anoLetivo
                        );

        BoletimModel boletim =
                boletimExistente
                        .orElse(
                                new BoletimModel()
                        );

        boletim.setAluno(
                aluno
        );

        boletim.setAnoLetivo(
                anoLetivo
        );

        boletim.setMediaGeral(
                conceito.getMediaGeral()
        );

        boletim.setConceito(
                conceito.getConceito()
        );

        boletim.setFeedback(
                conceito.getFeedback()
        );

        boletim.setDataGeracao(
                LocalDate.now()
        );

        return boletimRepository.save(
                boletim
        );
    }

    // =========================================================
    // LISTA BOLETINS DO ALUNO
    // =========================================================

    public List<BoletimModel> listarBoletinsDoAluno(
            Integer matricula
    ) {

        if (!alunoRepository.existsById(matricula)) {

            throw new RuntimeException(
                    "Aluno não encontrado"
            );
        }

        return boletimRepository
                .findByAluno_Matricula(
                        matricula
                );
    }

    // =========================================================
    // BUSCA BOLETIM POR ANO
    // =========================================================

    public BoletimModel buscarBoletim(
            Integer matricula,
            Integer anoLetivo
    ) {

        return boletimRepository
                .findByAluno_MatriculaAndAnoLetivo(
                        matricula,
                        anoLetivo
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Boletim não encontrado"
                        )
                );
    }

    // =========================================================
    // DELETA BOLETIM
    // =========================================================

    public void deletarBoletim(
            Integer idBoletim
    ) {

        BoletimModel boletim =
                boletimRepository.findById(idBoletim)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Boletim não encontrado"
                                )
                        );

        boletimRepository.delete(
                boletim
        );
    }

    // =========================================================
    // CONCEITO
    // =========================================================

    private String calcularConceito(
            double media
    ) {

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

    private String gerarFeedback(
            String conceito
    ) {

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

    private double arredondar(
            double valor
    ) {

        return Math.round(
                valor * 100.0
        ) / 100.0;
    }
}