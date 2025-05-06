package com.example.hack2v2.model.entities;

import com.example.hack2v2.model.enums.TipoModeloEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un modelo de Inteligencia Artificial disponible en la plataforma.
 * Incluye información sobre el tipo de modelo, proveedor, costos y especificaciones técnicas.
 */
@Entity
@Table(name = "modelos_ia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModeloIA {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Nombre del modelo de IA (ej. GPT-4, Claude-2, Llama-3)
     */
    @Column(nullable = false)
    private String nombre;
    
    /**
     * Proveedor del modelo (ej. OpenAI, Anthropic, Meta)
     */
    @Column(nullable = false)
    private String proveedor;
    
    /**
     * Tipo de modelo según su funcionalidad principal
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoModeloEnum tipo;
    
    /**
     * Descripción del modelo, capacidades y casos de uso recomendados
     */
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    /**
     * Versión específica del modelo
     */
    private String version;
    
    /**
     * URL del endpoint para realizar solicitudes a la API
     */
    private String endpoint;
    
    /**
     * Costo por cada 1000 tokens de entrada
     */
    @Column(precision = 10, scale = 6)
    private BigDecimal costoPorMilTokensEntrada;
    
    /**
     * Costo por cada 1000 tokens de salida
     */
    @Column(precision = 10, scale = 6)
    private BigDecimal costoPorMilTokensSalida;
    
    /**
     * Límite máximo de tokens por solicitud
     */
    private Integer maxTokensPorSolicitud;
    
    /**
     * Fecha en que se agregó el modelo al sistema
     */
    private LocalDateTime fechaCreacion;
    
    /**
     * Fecha de la última actualización de la información del modelo
     */
    private LocalDateTime fechaActualizacion;
    
    /**
     * Indica si el modelo está disponible para uso
     */
    private boolean activo = true;
    
    /**
     * Lista de restricciones asociadas a este modelo
     */
    @OneToMany(mappedBy = "modelo")
    private List<Restriccion> restricciones = new ArrayList<>();
    
    /**
     * Lista de solicitudes realizadas a este modelo
     */
    @OneToMany(mappedBy = "modelo")
    private List<Solicitud> solicitudes = new ArrayList<>();
    
    /**
     * Indica si el modelo admite entrada de imágenes
     */
    private boolean soportaImagenes;
    
    /**
     * Indica si el modelo admite entrada de audio
     */
    private boolean soportaAudio;
    
    /**
     * Métodos específicos de autenticación o parámetros requeridos por el proveedor
     */
    private String parametrosEspeciales;
    
    /**
     * Inicializa fechas al crear una nueva entidad
     */
    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    /**
     * Actualiza la fecha de modificación al guardar cambios
     */
    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    /**
     * Calcula el costo estimado basado en tokens de entrada y salida
     * 
     * @param tokensEntrada número de tokens de entrada
     * @param tokensSalida número de tokens de salida
     * @return costo estimado de la operación
     */
    public BigDecimal calcularCostoEstimado(int tokensEntrada, int tokensSalida) {
        BigDecimal costoEntrada = this.costoPorMilTokensEntrada
                .multiply(BigDecimal.valueOf(tokensEntrada))
                .divide(BigDecimal.valueOf(1000));
        
        BigDecimal costoSalida = this.costoPorMilTokensSalida
                .multiply(BigDecimal.valueOf(tokensSalida))
                .divide(BigDecimal.valueOf(1000));
        
        return costoEntrada.add(costoSalida);
    }
    
    /**
     * Determina si el modelo es adecuado para una tarea multimodal
     * 
     * @return true si el modelo soporta entrada multimodal
     */
    public boolean esMultimodal() {
        return this.soportaImagenes || this.soportaAudio;
    }
    
    /**
     * Verifica si el modelo pertenece a un proveedor específico
     * 
     * @param nombreProveedor el nombre del proveedor a verificar
     * @return true si el modelo pertenece al proveedor especificado
     */
    public boolean esDeProveedor(String nombreProveedor) {
        return this.proveedor.equalsIgnoreCase(nombreProveedor);
    }
}