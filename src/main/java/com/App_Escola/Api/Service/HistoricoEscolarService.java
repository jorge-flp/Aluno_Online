package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.HistoricoEscolarModel;
import com.App_Escola.Api.Repository.HistoricoEscolarRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HistoricoEscolarService {

    private final HistoricoEscolarRepository historicoEscolarRepository;

    public List<HistoricoEscolarModel> listarTodos() {
        return historicoEscolarRepository.findAll();
    }

    public Optional<HistoricoEscolarModel> buscarPorId(Integer id) {
        return historicoEscolarRepository.findById(id);
    }

    public HistoricoEscolarModel salvar(HistoricoEscolarModel historico) {
        return historicoEscolarRepository.save(historico);
    }

    public void deletar(Integer id) {
        historicoEscolarRepository.deleteById(id);
    }
}