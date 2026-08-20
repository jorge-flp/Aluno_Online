package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.App_Escola.Api.Model.NotaModel;
import com.App_Escola.Api.Repository.NotaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class NotaService {

    private NotaRepository notaRepository;

    public List<NotaModel> listarTodas() {
        return notaRepository.findAll();
    }

    public Optional<NotaModel> buscarPorId(Integer id) {
        return notaRepository.findById(id);
    }

    public NotaModel salvar(NotaModel nota) {
        return notaRepository.save(nota);
    }

    public void deletar(Integer id) {
        notaRepository.deleteById(id);
    }
}