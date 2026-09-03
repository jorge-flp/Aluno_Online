package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.SecretarioModel;
import com.App_Escola.Api.Repository.SecretarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecretarioService {

    private final SecretarioRepository repository;

    public SecretarioService(SecretarioRepository repository) {
        this.repository = repository;
    }

    public List<SecretarioModel> listarTodos() {
        return repository.findAll();
    }

    public SecretarioModel buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Secretário não encontrado"));
    }

    public SecretarioModel salvar(SecretarioModel secretario) {
        return repository.save(secretario);
    }

    public SecretarioModel atualizar(Integer id, SecretarioModel secretario) {
        SecretarioModel existente = buscarPorId(id);

        existente.setNome(secretario.getNome());
        existente.setEmail(secretario.getEmail());
        existente.setTelefone(secretario.getTelefone());

        return repository.save(existente);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Secretário não encontrado");
        }

        repository.deleteById(id);
    }
}