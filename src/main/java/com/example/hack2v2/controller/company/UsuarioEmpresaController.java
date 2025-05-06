package com.example.hack2v2.controller.company;

import com.example.hack2v2.dto.request.LimiteRequest;
import com.example.hack2v2.dto.request.UsuarioRequest;
import com.example.hack2v2.dto.response.ConsumoResponse;
import com.example.hack2v2.dto.response.LimiteResponse;
import com.example.hack2v2.dto.response.UsuarioResponse;
import com.example.hack2v2.service.company.LimiteService;
import com.example.hack2v2.service.company.UsuarioEmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company/users")
@PreAuthorize("hasRole('COMPANY_ADMIN')")
@RequiredArgsConstructor
public class UsuarioEmpresaController {

    private final UsuarioEmpresaService usuarioService;
    private final LimiteService limiteService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        Long empresaId = usuarioService.obtenerEmpresaIdDelUsuarioActual();
        UsuarioResponse usuario = usuarioService.crearUsuario(request, empresaId);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        Long empresaId = usuarioService.obtenerEmpresaIdDelUsuarioActual();
        List<UsuarioResponse> usuarios = usuarioService.listarUsuariosPorEmpresa(empresaId);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable Long id) {
        Long empresaId = usuarioService.obtenerEmpresaIdDelUsuarioActual();
        UsuarioResponse usuario = usuarioService.obtenerUsuario(id, empresaId);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request) {
        Long empresaId = usuarioService.obtenerEmpresaIdDelUsuarioActual();
        UsuarioResponse usuario = usuarioService.actualizarUsuario(id, request, empresaId);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/{id}/limits")
    public ResponseEntity<LimiteResponse> asignarLimite(
            @PathVariable Long id,
            @Valid @RequestBody LimiteRequest request) {
        Long empresaId = usuarioService.obtenerEmpresaIdDelUsuarioActual();
        LimiteResponse limite = limiteService.asignarLimite(id, request, empresaId);
        return new ResponseEntity<>(limite, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/limits")
    public ResponseEntity<List<LimiteResponse>> listarLimitesUsuario(@PathVariable Long id) {
        Long empresaId = usuarioService.obtenerEmpresaIdDelUsuarioActual();
        List<LimiteResponse> limites = limiteService.listarLimitesPorUsuario(id, empresaId);
        return ResponseEntity.ok(limites);
    }

    @GetMapping("/{id}/consumption")
    public ResponseEntity<ConsumoResponse> obtenerConsumoUsuario(@PathVariable Long id) {
        Long empresaId = usuarioService.obtenerEmpresaIdDelUsuarioActual();
        ConsumoResponse consumo = limiteService.obtenerConsumoUsuario(id, empresaId);
        return ResponseEntity.ok(consumo);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> cambiarEstadoUsuario(
            @PathVariable Long id,
            @RequestParam boolean activo) {
        Long empresaId = usuarioService.obtenerEmpresaIdDelUsuarioActual();
        usuarioService.cambiarEstadoUsuario(id, activo, empresaId);
        return ResponseEntity.noContent().build();
    }
}