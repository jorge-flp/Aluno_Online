package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.AvaliacaoModel;
import com.App_Escola.Api.Repository.AvaliacaoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class AvaliacaoService {

    private AvaliacaoRepository avaliacaoRepository;

    public List<AvaliacaoModel> listarTodas() {
        return avaliacaoRepository.findAll();
    }

    public Optional<AvaliacaoModel> buscarPorId(Integer id) {
        return avaliacaoRepository.findById(id);
    }

    public AvaliacaoModel salvar(AvaliacaoModel avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public AvaliacaoModel atualizar(Integer id, AvaliacaoModel dados) {
        Optional<AvaliacaoModel> avaliacaoOpt = avaliacaoRepository.findById(id);

        if (avaliacaoOpt.isPresent()) {
            AvaliacaoModel avaliacaoExistente = avaliacaoOpt.get();
            avaliacaoExistente.setTitulo(dados.getTitulo());
            avaliacaoExistente.setDescricao(dados.getDescricao());
            avaliacaoExistente.setTipo(dados.getTipo());
            avaliacaoExistente.setData(dados.getData());
            avaliacaoExistente.setPeso(dados.getPeso());
            avaliacaoExistente.setDisciplina(dados.getDisciplina());

            return avaliacaoRepository.save(avaliacaoExistente);
        }

        return null;
    }

    public void deletar(Integer id) {
        avaliacaoRepository.deleteById(id);
    }
}