package mx.unam.fesaragon.servicio.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_horas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroHoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @Column(name = "hora_entrada", nullable = false)
    private LocalDateTime horaEntrada;

    @Column(name = "hora_salida")
    private LocalDateTime horaSalida;

    @Column(name = "horas_totales")
    private Double horasTotales;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Modalidad modalidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRegistro estado = EstadoRegistro.EN_CURSO;

    @Column(length = 500)
    private String observaciones;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public enum Modalidad {
        MODALIDAD_1, MODALIDAD_2
    }

    public enum EstadoRegistro {
        EN_CURSO, COMPLETADO
    }
}
