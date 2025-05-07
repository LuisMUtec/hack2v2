package com.example.hack2v2.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "limites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Limite {
    
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
    private Integer limiteSolicitudes;
    
    @Column(nullable = false)
    private Integer limiteTokens;
    
    @Column(nullable = false)
    private String periodoReinicio; // "1h", "1d", "7d"
    
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime fechaUltimoReinicio;
    
    private LocalDateTime ultimaModificacion;
    
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaUltimoReinicio = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.ultimaModificacion = LocalDateTime.now();
    }
}