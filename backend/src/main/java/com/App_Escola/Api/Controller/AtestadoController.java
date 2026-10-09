package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.Atestado;
import com.App_Escola.Api.Repository.AtestadoRepository;
import com.App_Escola.Api.Service.SupabaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/Atestados")
public class AtestadoController {

    private static final Map<String, String> MIME_TYPES = Map.of(
            ".pdf", "application/pdf",
            ".jpg", "image/jpeg",
            ".jpeg", "image/jpeg",
            ".png", "image/png",
            ".webp", "image/webp"
    );

    @Autowired
    private AtestadoRepository atestadoRepository;

    @Autowired
    private SupabaseStorageService supabaseStorageService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("arquivo") MultipartFile arquivo,
            @RequestParam("matricula") Integer matricula,
            @RequestParam(value = "professorId", required = false) Long professorId) {
        if (arquivo.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo vazio.");
        }
        if (matricula == null || matricula <= 0) {
            return ResponseEntity.badRequest().body("Informe uma matrícula válida.");
        }

        String nomeArquivo = arquivo.getOriginalFilename();
        String nomeNormalizado = nomeArquivo == null ? "" : nomeArquivo.toLowerCase(Locale.ROOT);
        String extensao = nomeNormalizado.lastIndexOf('.') >= 0
                ? nomeNormalizado.substring(nomeNormalizado.lastIndexOf('.'))
                : "";
        String contentType = MIME_TYPES.get(extensao);
        if (contentType == null) {
            return ResponseEntity.badRequest().body("Envie um PDF, JPG, PNG ou WEBP.");
        }
        String contentTypeEnviado = arquivo.getContentType();
        if (contentTypeEnviado != null
                && !contentType.equalsIgnoreCase(contentTypeEnviado)
                && !"application/octet-stream".equalsIgnoreCase(contentTypeEnviado)) {
            return ResponseEntity.badRequest().body("O tipo do arquivo não corresponde à extensão.");
        }

        try {
            String fileUrl = supabaseStorageService.uploadFile(arquivo, contentType);

            Atestado atestado = new Atestado(nomeArquivo, fileUrl);
            atestado.setMatricula(matricula);
            atestado.setProfessorId(professorId);

            return ResponseEntity.ok(atestadoRepository.save(atestado));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao enviar o arquivo: " + e.getMessage());
        }
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<Atestado>> listarArquivos() {
        return ResponseEntity.ok(atestadoRepository.findAll());
    }

    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<List<Atestado>> listarPorAluno(@PathVariable Integer matricula) {
        return ResponseEntity.ok(atestadoRepository.findByMatriculaOrderByIdDesc(matricula));
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<Atestado>> listarPorProfessor(@PathVariable Long professorId) {
        return ResponseEntity.ok(atestadoRepository.findByProfessorIdOrderByIdDesc(professorId));
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