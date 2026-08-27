package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.App_Escola.Api.Model.EventoModel;
import com.App_Escola.Api.Repository.EventoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class EventoService {

    private EventoRepository eventoRepository;

    public List<EventoModel> listarTodos() {
        return eventoRepository.findAll();
    }

    public Optional<EventoModel> buscarPorId(Integer id) {
        return eventoRepository.findById(id);
    }

    public EventoModel salvar(EventoModel evento) {
        return eventoRepository.save(evento);
    }

    public void deletar(Integer id) {
        eventoRepository.deleteById(id);
    }
}