package com.example.hack2v2.controller.company;

import com.example.hack2v2.dto.request.LimiteRequest;
import com.example.hack2v2.dto.response.LimiteResponse;
import com.example.hack2v2.service.company.LimiteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company/users/{userId}/limits")
@PreAuthorize("hasRole('COMPANY_ADMIN')")
public class LimiteController {

    private final LimiteService limiteService;

    public LimiteController(LimiteService limiteService) {
        this.limiteService = limiteService;
    }

    // POST /api/company/users/{userId}/limits
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LimiteResponse createLimit(
            @PathVariable Long userId,
            @Valid @RequestBody LimiteRequest request
    ) {
        return limiteService.asignarLimite(userId, request, null);
    }
}
