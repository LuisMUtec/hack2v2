package com.example.hack2v2.controller.admin;

import com.example.hack2v2.dto.request.EmpresaCreacionRequest;
import com.example.hack2v2.dto.response.EmpresaResponse;
import com.example.hack2v2.dto.response.ConsumoResponse;
import com.example.hack2v2.service.admin.EmpresaAdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/companies")
@PreAuthorize("hasRole('SPARKY_ADMIN')")
public class EmpresaController {

    private final EmpresaAdminService empresaService;

    public EmpresaController(EmpresaAdminService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpresaResponse createCompany(@Valid @RequestBody EmpresaCreacionRequest request) {
        return empresaService.crear(request);
    }

    @GetMapping
    public List<EmpresaResponse> listCompanies() {
        return empresaService.listarTodas();
    }

    /*──────────────────── 3. Detalle empresa ───────────────────*/
    @GetMapping("/{id}")
    public EmpresaResponse getCompany(@PathVariable Long id) {
        return empresaService.obtener(id);
    }

    /*──────────────────── 4. Actualizar empresa ────────────────*/
    @PutMapping("/{id}")
    public EmpresaResponse updateCompany(@PathVariable Long id,
                                         @Valid @RequestBody EmpresaCreacionRequest request) {
        return empresaService.actualizar(id, request);
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void toggleCompanyStatus(@PathVariable Long id) {
        empresaService.cambiarEstado(id);
    }

    /*──────────────────── 6. Consumo por modelo ────────────────*/
    @GetMapping("/{id}/consumption")
    public List<ConsumoResponse> companyConsumption(@PathVariable Long id) {
        return empresaService.consumoEmpresa(id);
    }
}
