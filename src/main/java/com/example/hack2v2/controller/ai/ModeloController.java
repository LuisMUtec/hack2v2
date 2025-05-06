package com.example.hack2v2.controller;

import com.example.hack2v2.dto.request.ChatRequest;
import com.example.hack2v2.dto.response.ChatResponse;
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

    // Endpoint para obtener todos los modelos disponibles
    @GetMapping("/models")
    public ResponseEntity<String> getModels() {
        try {
            String response = modelsClient.getAvailableModels();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener modelos: " + e.getMessage());
        }
    }

    // Endpoint para enviar un prompt al modelo tipo chat (ej: openai/gpt-4)
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            String rawResponse = modelsClient.enviarPromptChat(request.getPrompt());
            return ResponseEntity.ok(new ChatResponse(rawResponse));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(new ChatResponse("Error al generar respuesta: " + e.getMessage()));
        }
    }
}
