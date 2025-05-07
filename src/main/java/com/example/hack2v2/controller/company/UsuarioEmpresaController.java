package com.example.hack2v2.controller.company;

import com.example.hack2v2.dto.request.UsuarioRequest;
import com.example.hack2v2.dto.response.UsuarioResponse;
import com.example.hack2v2.dto.response.ConsumoResponse;
import com.example.hack2v2.service.company.UsuarioEmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company/users")
@PreAuthorize("hasRole('COMPANY_ADMIN')")
public class UsuarioEmpresaController {

    private final UsuarioEmpresaService usuarioService;

    public UsuarioEmpresaController(UsuarioEmpresaService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // 1. POST /api/company/users
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse createUser(
            @Valid @RequestBody UsuarioRequest request
    ) {
        return usuarioService.crear(request);
    }

    // 2. GET /api/company/users
    @GetMapping
    public List<UsuarioResponse> listUsers() {
        return usuarioService.listarTodos();
    }

    // 3. GET /api/company/users/{id}
    @GetMapping("/{id}")
    public UsuarioResponse getUser(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }

    // 4. PUT /api/company/users/{id}
    @PutMapping("/{id}")
    public UsuarioResponse updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request
    ) {
        return usuarioService.actualizar(id, request);
    }

    // 5. GET /api/company/users/{id}/consumption
    @GetMapping("/{id}/consumption")
    public List<ConsumoResponse> userConsumption(@PathVariable Long id) {
        return usuarioService.consumoUsuario(id);
    }
}
