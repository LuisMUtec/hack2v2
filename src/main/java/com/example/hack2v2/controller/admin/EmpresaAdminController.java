package com.example.hack2v2.controller.admin;

import com.example.hack2v2.dto.request.EmpresaCreacionRequest;
import com.example.hack2v2.dto.response.EmpresaResponse;
import com.example.hack2v2.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/companies")
@PreAuthorize("hasRole('SPARKY_ADMIN')")
public class EmpresaAdminController {

    @Autowired
    private final EmpresaService empresaService;

    /**
     * Crea una nueva empresa con su administrador principal.
     */
    @PostMapping
    public ResponseEntity<EmpresaResponse> crearEmpresa(
            @Valid @RequestBody EmpresaCreacionRequest dto) {
        EmpresaResponse respuesta = empresaService.crearEmpresa(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }

    /**
     * Lista todas las empresas registradas.
     */
    @GetMapping
    public ResponseEntity<List<EmpresaResponse>> listarEmpresas() {
        List<EmpresaResponse> lista = empresaService.listarEmpresas();
        return ResponseEntity.ok(lista);
    }

    /**
     * Obtiene los datos de una empresa por su ID.
     */
}
