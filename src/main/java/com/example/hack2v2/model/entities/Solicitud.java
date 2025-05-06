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
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String consulta;
    
    @Column(columnDefinition = "TEXT")
    private String respuesta;
    
    private Integer tokensConsumidos;
    
    @Column(nullable = false)
    private LocalDateTime fechaHora;
    
    private String nombreArchivo; // Para solicitudes multimodales
    
    private boolean exitoso;
    
    private String mensajeError;
}