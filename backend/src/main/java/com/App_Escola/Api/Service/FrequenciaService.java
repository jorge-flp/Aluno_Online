package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;

import org.springframework.stereotype.Service;
import com.App_Escola.Api.Model.FrequenciaModel;
import com.App_Escola.Api.Repository.FrequenciaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class FrequenciaService {

    private FrequenciaRepository frequenciaRepository;

    public List<FrequenciaModel> listarTodas() {
        return frequenciaRepository.findAll();
    }

    public Optional<FrequenciaModel> buscarPorId(Integer id) {
        return frequenciaRepository.findById(id);
    }

    public FrequenciaModel salvar(FrequenciaModel frequencia) {
        return frequenciaRepository.save(frequencia);
    }

    public void deletar(Integer id) {
        frequenciaRepository.deleteById(id);
    }
}