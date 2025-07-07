package com.example.hack2v2.dto.response;

public class ChatResponse {
    private String respuesta;

    public ChatResponse(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
