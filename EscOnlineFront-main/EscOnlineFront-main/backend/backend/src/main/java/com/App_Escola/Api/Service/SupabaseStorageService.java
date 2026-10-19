package com.App_Escola.Api.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.UUID;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile arquivo) throws IOException {
        String originalName = arquivo.getOriginalFilename() != null ? arquivo.getOriginalFilename() : "arquivo.pdf";
        
        // Remove acentos e caracteres especiais para evitar erro de InvalidKey no Supabase
        String cleanName = Normalizer.normalize(originalName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("\\s+", "_");

        String fileName = UUID.randomUUID().toString() + "_" + cleanName;
        String uploadUrl = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseKey);
        headers.setContentType(MediaType.valueOf(arquivo.getContentType() != null ? arquivo.getContentType() : "application/pdf"));

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(arquivo.getBytes(), headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // Retorna o link público do arquivo no Supabase
                return supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + fileName;
            } else {
                throw new RuntimeException("Erro ao enviar para o Supabase: " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha na comunicação com o Supabase Storage: " + e.getMessage(), e);
        }
    }

    public void deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) return;

        try {
            String marker = "/public/" + bucketName + "/";
            int index = fileUrl.indexOf(marker);
            if (index == -1) return;

            String fileName = fileUrl.substring(index + marker.length());
            String deleteUrl = supabaseUrl + "/storage/v1/object/" + bucketName + "/" + fileName;

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + supabaseKey);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            restTemplate.exchange(deleteUrl, HttpMethod.DELETE, requestEntity, String.class);

        } catch (Exception e) {
            System.err.println("Erro ao deletar arquivo do Supabase Storage: " + e.getMessage());
        }
    }
}