package com.example.hack2v2.model.entities;

import jakarta.persistence.*;

@Entity
public class ModeloIA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private int limitePorUsuario;

    // Constructor vacío requerido por JPA
    public ModeloIA() {}

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLimitePorUsuario() {
        return limitePorUsuario;
    }

    public void setLimitePorUsuario(int limitePorUsuario) {
        this.limitePorUsuario = limitePorUsuario;
    }
}
