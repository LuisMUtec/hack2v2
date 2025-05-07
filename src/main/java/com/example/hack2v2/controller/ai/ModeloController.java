package com.example.hack2v2.controller.ai;

import com.example.hack2v2.dto.request.ChatRequest;
import com.example.hack2v2.dto.response.ChatResponse;
import com.example.hack2v2.integration.github.GitHubModelsClient;
import com.example.hack2v2.model.entities.ModeloIA;
import com.example.hack2v2.model.entities.Solicitud;
import com.example.hack2v2.model.entities.Usuario;
import com.example.hack2v2.service.ai.IAModelService;
import com.example.hack2v2.service.SolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
public class ModeloController {

    private final GitHubModelsClient modelsClient;
    private final SolicitudService solicitudService;
    private final IAModelService modeloIAService;

    public ModeloController(GitHubModelsClient modelsClient,
                            SolicitudService SolicitudService,
                            IAModelService IAModelService) {
        this.modelsClient = modelsClient;
        this.solicitudService = SolicitudService;
        this.modeloIAService = IAModelService;
    }

    private boolean excedeLimite(Usuario usuario, ModeloIA modelo) {
        Integer tokensConsumidos = solicitudService.totalTokensPorUsuarioYModelo(usuario, modelo);
        int limite = modelo.getLimitePorUsuario(); // Supuesto campo definido en ModeloIA o derivado de Restricción
        return tokensConsumidos != null && tokensConsumidos >= limite;
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
        ModeloIA modelo = modeloIAService.obtenerPorNombre("openai/gpt-4");
        if (excedeLimite(usuario, modelo)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ChatResponse("Límite de uso alcanzado para el modelo."));
        }

        try {
            String respuestaRaw = modelsClient.enviarPromptChat(request.getPrompt());

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

    @PostMapping("/completion")
    public ResponseEntity<ChatResponse> completion(@RequestBody ChatRequest request,
                                                   @AuthenticationPrincipal Usuario usuario) {
        ModeloIA modelo = modeloIAService.obtenerPorNombre("openai/text-davinci-003");
        if (excedeLimite(usuario, modelo)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ChatResponse("Límite de uso alcanzado para el modelo."));
        }

        try {
            String respuestaRaw = modelsClient.enviarPromptCompletion(request.getPrompt());

            Solicitud solicitud = new Solicitud();
            solicitud.setUsuario(usuario);
            solicitud.setTipo("completion");
            solicitud.setConsulta(request.getPrompt());
            solicitud.setRespuesta(respuestaRaw);
            solicitud.setExitoso(true);
            solicitud.setTokensConsumidos(0);
            solicitud.setModelo(modelo);

            solicitudService.guardar(solicitud);

            return ResponseEntity.ok(new ChatResponse(respuestaRaw));
        } catch (Exception e) {
            Solicitud solicitud = new Solicitud();
            solicitud.setUsuario(usuario);
            solicitud.setTipo("completion");
            solicitud.setConsulta(request.getPrompt());
            solicitud.setExitoso(false);
            solicitud.setMensajeError(e.getMessage());
            solicitud.setModelo(modelo);

            solicitudService.guardar(solicitud);

            return ResponseEntity.internalServerError()
                    .body(new ChatResponse("Error al generar respuesta: " + e.getMessage()));
        }
    }

    @PostMapping("/multimodal")
    public ResponseEntity<ChatResponse> multimodal(@RequestBody ChatRequest request,
                                                   @AuthenticationPrincipal Usuario usuario) {
        ModeloIA modelo = modeloIAService.obtenerPorNombre("deepseek/deepseek-vl");
        if (excedeLimite(usuario, modelo)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ChatResponse("Límite de uso alcanzado para el modelo."));
        }

        try {
            String respuestaRaw = modelsClient.enviarPromptMultimodal(request.getPrompt());

            Solicitud solicitud = new Solicitud();
            solicitud.setUsuario(usuario);
            solicitud.setTipo("multimodal");
            solicitud.setConsulta(request.getPrompt());
            solicitud.setRespuesta(respuestaRaw);
            solicitud.setExitoso(true);
            solicitud.setTokensConsumidos(0);
            solicitud.setModelo(modelo);

            solicitudService.guardar(solicitud);

            return ResponseEntity.ok(new ChatResponse(respuestaRaw));
        } catch (Exception e) {
            Solicitud solicitud = new Solicitud();
            solicitud.setUsuario(usuario);
            solicitud.setTipo("multimodal");
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
