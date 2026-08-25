package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.AvisosModel;
import com.App_Escola.Api.Repository.AvisosRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class AvisosService {

    private AvisosRepository avisoRepository;

    public List<AvisosModel> listarTodas() {
        return avisoRepository.findAll();
    }

    public Optional<AvisosModel> buscarPorId(Integer id) {
        return avisoRepository.findById(id);
    }

    public AvisosModel salvar(AvisosModel aviso) {
        return avisoRepository.save(aviso);
    }

    public AvisosModel atualizar(Integer id, AvisosModel dados) {
        Optional<AvisosModel> avisoOpt = avisoRepository.findById(id);

        if (avisoOpt.isPresent()) {
            AvisosModel avisoExistente = avisoOpt.get();
            avisoExistente.setTitulo(dados.getTitulo());
            avisoExistente.setMensagem(dados.getMensagem());
            avisoExistente.setData(dados.getData());
            avisoExistente.setEscola(dados.getEscola());

            return avisoRepository.save(avisoExistente);
        }

        return null;
    }

    public void deletar(Integer id) {
        avisoRepository.deleteById(id);
    }
}