package com.example.hack2v2.dto.response;

import lombok.Data;

@Data
public class ChatResponse {
    private String respuesta;

    public ChatResponse(String respuesta) {
        this.respuesta = respuesta;
    }
}
