package com.example.hack2v2.controller;

import com.example.hack2v2.dto.request.ChatRequest;
import com.example.hack2v2.dto.response.ChatResponse;
import com.example.hack2v2.external.githubmodels.GitHubModelsClient;
import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.entities.Solicitud;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.service.ModeloIAService;
import com.example.hack2v2.service.SolicitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class ModeloController {

    private final GitHubModelsClient modelsClient;
    private final SolicitudService solicitudService;
    private final ModeloIAService modeloIAService;

    public ModeloController(GitHubModelsClient modelsClient,
                            SolicitudService solicitudService,
                            ModeloIAService modeloIAService) {
        this.modelsClient = modelsClient;
        this.solicitudService = solicitudService;
        this.modeloIAService = modeloIAService;
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

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request,
                                             @AuthenticationPrincipal Usuario usuario) {
        try {
            String respuestaRaw = modelsClient.enviarPromptChat(request.getPrompt());

            ModeloIA modelo = modeloIAService.obtenerPorNombre("openai/gpt-4");

            Solicitud solicitud = new Solicitud();
            solicitud.setUsuario(usuario);
            solicitud.setTipo("chat");
            solicitud.setConsulta(request.getPrompt());
            solicitud.setRespuesta(respuestaRaw);
            solicitud.setExitoso(true);
            solicitud.setTokensConsumidos(0);
            solicitud.setModelo(modelo);

            solicitudService.guardar(solicitud);

            return ResponseEntity.ok(new ChatResponse(respuestaRaw));
        } catch (Exception e) {
            ModeloIA modelo = modeloIAService.obtenerPorNombre("openai/gpt-4");

            Solicitud solicitud = new Solicitud();
            solicitud.setUsuario(usuario);
            solicitud.setTipo("chat");
            solicitud.setConsulta(request.getPrompt());
            solicitud.setExitoso(false);
            solicitud.setMensajeError(e.getMessage());
            solicitud.setModelo(modelo);

            solicitudService.guardar(solicitud);

            return ResponseEntity.internalServerError()
                    .body(new ChatResponse("Error al generar respuesta: " + e.getMessage()));
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<Solicitud>> getHistory(@AuthenticationPrincipal Usuario usuario) {
        List<Solicitud> historial = solicitudService.obtenerPorUsuario(usuario);
        return ResponseEntity.ok(historial);
    }
} 
