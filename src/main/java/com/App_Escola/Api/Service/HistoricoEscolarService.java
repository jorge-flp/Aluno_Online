package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.HistoricoEscolarModel;
import com.App_Escola.Api.Repository.HistoricoEscolarRepository;

import lombok.AllArgsConstructor;

@Service
@AllowNonPortable
public class HistoricoEscolarService {

    private HistoricoEscolarRepository historicoEscolarRepository;

    public List<HistoricoEscolarRepository> listarTodas() {
        return historicoEscolarRepository.findAll();
    }

    public Optional<HistoricoEscolarRepository> buscarPorId(Integer id) {
        return historicoEscolarRepository.findById(id);
    }

    public HistoricoEscolarModel salvar(HistoricoEscolarModel historico) {
        return historicoEscolarRepository.save(historico);
    }

   public HistoricoEscolarRepository atualizar(Integer id, HistoricoEscolarModel dados) {
    Optional<HistoricoEscolarRepository> historicoOpt = historicoEscolarRepository.findById(id);

    if (historicoOpt.isPresent()) {
        HistoricoEscolarRepository historicoExistente = historicoOpt.get();
        historicoExistente.setAnoLetivo(dados.getAno_letivo());
        historicoExistente.setSerie(dados.getSerie());
        historicoExistente.setSituacao(dados.getSituacao());
        historicoExistente.setMedia_final(dados.getMedia_final());
        historicoExistente.setObservacao(dados.getObservacao());
        historicoExistente.setAluno(dados.getAluno());
        historicoExistente.setEscola(dados.getEscola());

        return historicoEscolarRepository.save(historicoExistente);
    }

    return null;
    }

    public void deletar(Integer id) {
        historicoEscolarRepository.deleteById(id);
    }
}