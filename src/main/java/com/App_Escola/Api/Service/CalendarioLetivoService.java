package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.App_Escola.Api.Model.CalendarioLetivoModel;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CalendarioLetivoService {

    private CalendarioLetivoModel calendarioLetivoRepository;

    public List<CalendarioLetivoModel> listarTodos() {
        return calendarioLetivoRepository.findAll();
    }

    public Optional<CalendarioLetivoModel> buscarPorId(Integer id) {
        return calendarioLetivoRepository.findById(id);
    }

    public CalendarioLetivoModel salvar(CalendarioLetivoModel calendario) {
        return calendarioLetivoRepository.save(calendario);
    }

    public void deletar(Integer id) {
        calendarioLetivoRepository.deleteById(id);
    }
}