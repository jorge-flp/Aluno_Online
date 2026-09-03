package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.JustificativaFaltaModel;
import com.App_Escola.Api.Repository.JustificativaFaltaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JustificativaFaltaService {

    private static JustificativaFaltaRepository repository;

    public JustificativaFaltaService(JustificativaFaltaRepository repository) {
        JustificativaFaltaService.repository = repository;
    }

    public static List<JustificativaFaltaModel> listarTodas() {
        return repository.findAll();
    }

    public static JustificativaFaltaModel buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Justificativa não encontrada"));
    }

    public JustificativaFaltaModel salvar(JustificativaFaltaModel justificativa) {
        return repository.save(justificativa);
    }

    public JustificativaFaltaModel atualizar(
            Integer id,
            JustificativaFaltaModel justificativa) {

        JustificativaFaltaModel existente = buscarPorId(id);

        return repository.save(existente);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Justificativa não encontrada");
        }

        repository.deleteById(id);
    }
}