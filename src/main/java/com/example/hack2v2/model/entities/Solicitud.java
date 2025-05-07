package com.example.hack2v2.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "modelo_id", nullable = false)
    private ModeloIA modelo;
    
    @Column(nullable = false)
    private LocalDateTime fechaHora;
    
    private Integer tokensConsumidos;
    
    private String tipoSolicitud; // "CHAT", "COMPLETION", "MULTIMODAL"
    
    @Column(length = 1000)
    private String prompt;
    
    @Column(length = 5000)
    private String respuesta;
    
    @PrePersist
    protected void onCreate() {
        this.fechaHora = LocalDateTime.now();
    }
}