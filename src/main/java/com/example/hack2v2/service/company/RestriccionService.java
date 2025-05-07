package com.example.hack2v2.service.company;

import com.example.hack2v2.dto.request.RestriccionRequest;
import com.example.hack2v2.dto.response.RestriccionResponse;
import java.util.List;

public interface RestriccionService {
    RestriccionResponse crear(RestriccionRequest request);
    List<RestriccionResponse> listarTodas();
    RestriccionResponse actualizar(Long id, RestriccionRequest request);
    void eliminar(Long id);
}
