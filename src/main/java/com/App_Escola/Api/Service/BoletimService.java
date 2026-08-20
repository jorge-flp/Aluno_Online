package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.App_Escola.Api.Model.BoletimModel;
import com.App_Escola.Api.Repository.BoletimRepository;

@Service
public class BoletimService {

    private BoletimRepository boletimRepository;

    public List<BoletimModel> listarTodos() {
        return boletimRepository.findAll();
    }

    public Optional<BoletimModel> buscarPorId(Integer id) {
        return boletimRepository.findById(id);
    }

    public BoletimModel salvar(BoletimModel boletim) {
        return boletimRepository.save(boletim);
    }

    public void deletar(Integer id) {
        boletimRepository.deleteById(id);
    }
}