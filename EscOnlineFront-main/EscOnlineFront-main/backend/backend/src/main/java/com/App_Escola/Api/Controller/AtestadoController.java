package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.Atestado;
import com.App_Escola.Api.Repository.AtestadoRepository;
import com.App_Escola.Api.Service.SupabaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/Atestados")
public class AtestadoController {

    @Autowired
    private AtestadoRepository atestadoRepository;

    @Autowired
    private SupabaseStorageService supabaseStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("arquivo") MultipartFile arquivo) {
        if (arquivo.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo vazio.");
        }

        String nomeArquivo = arquivo.getOriginalFilename();
        if (nomeArquivo == null || !nomeArquivo.toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Apenas arquivos PDF são permitidos.");
        }

        try {
            // 1. Faz o upload para o Supabase Storage e pega a URL pública
            String fileUrl = supabaseStorageService.uploadFile(arquivo);

            // 2. Salva o registro (nome e link) no PostgreSQL da Aiven
            Atestado atestado = new Atestado(nomeArquivo, fileUrl);
            atestadoRepository.save(atestado);

            return ResponseEntity.ok("Upload realizado com sucesso! Arquivo enviado ao Supabase e link salvo na Aiven.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao enviar o arquivo: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Atestado>> listarArquivos() {
        return ResponseEntity.ok(atestadoRepository.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarArquivo(@PathVariable Long id) {
        Atestado atestado = atestadoRepository.findById(id).orElse(null);

        if (atestado == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            // 1. Remove o PDF do bucket do Supabase
            supabaseStorageService.deleteFile(atestado.getUrlArquivo());

            // 2. Deleta o registro do banco de dados da Aiven
            atestadoRepository.delete(atestado);

            return ResponseEntity.ok("Atestado deletado com sucesso do Supabase e da Aiven!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao deletar o atestado: " + e.getMessage());
        }
    }
}