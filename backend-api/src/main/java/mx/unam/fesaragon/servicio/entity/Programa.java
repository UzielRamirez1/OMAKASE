package mx.unam.fesaragon.servicio.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "programas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Programa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.ACTIVO;

    @Column(name = "horas_requeridas", nullable = false)
    private Integer horasRequeridas = 480;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "programa", fetch = FetchType.LAZY)
    private List<Alumno> alumnos;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "programa", fetch = FetchType.LAZY)
    private List<ProgramaProfesor> programaProfesores;

    public enum Estado {
        ACTIVO, INACTIVO, CONCLUIDO
    }
}
