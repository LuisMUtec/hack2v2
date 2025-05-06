package com.example.hack2v2.controller;

import com.example.hack2v2.external.githubmodels.GitHubModelsClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class ModeloController {

    private final GitHubModelsClient modelsClient;

    public ModeloController(GitHubModelsClient modelsClient) {
        this.modelsClient = modelsClient;
    }

    @GetMapping("/models")
    public ResponseEntity<String> getModels() {
        try {
            String response = modelsClient.getAvailableModels();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener modelos: " + e.getMessage());
        }
    }
}
