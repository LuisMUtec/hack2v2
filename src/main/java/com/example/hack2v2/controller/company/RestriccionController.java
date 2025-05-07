package com.example.hack2v2.controller.company;

import com.example.hack2v2.dto.request.RestriccionRequest;
import com.example.hack2v2.dto.response.RestriccionResponse;
import com.example.hack2v2.service.company.RestriccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints para que el ADMIN_EMPRESA gestione las restricciones de su compañía.
 *  • POST   /api/company/restrictions
 *  • GET    /api/company/restrictions
 *  • PUT    /api/company/restrictions/{id}
 *  • DELETE /api/company/restrictions/{id}
 */
@RestController
@RequestMapping("/api/company/restrictions")
@PreAuthorize("hasRole('ADMIN_EMPRESA')")
public class RestriccionController {

    private final RestriccionService restriccionService;

    public RestriccionController(RestriccionService restriccionService) {
        this.restriccionService = restriccionService;
    }

    /*──────────────────── 1. Crear restricción ────────────────────*/
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestriccionResponse createRestriction(@Valid @RequestBody RestriccionRequest request) {
        return restriccionService.crear(request);
    }

    /*──────────────────── 2. Listar restricciones ─────────────────*/
    @GetMapping
    public List<RestriccionResponse> listRestrictions() {
        return restriccionService.listarTodas();
    }

    /*──────────────────── 3. Actualizar restricción ───────────────*/
    @PutMapping("/{id}")
    public RestriccionResponse updateRestriction(@PathVariable Long id,
                                                 @Valid @RequestBody RestriccionRequest request) {
        return restriccionService.actualizar(id, request);
    }

    /*──────────────────── 4. Eliminar restricción ─────────────────*/
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRestriction(@PathVariable Long id) {
        restriccionService.eliminar(id);
    }
}