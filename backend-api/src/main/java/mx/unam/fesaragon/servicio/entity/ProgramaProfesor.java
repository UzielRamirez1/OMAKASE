package mx.unam.fesaragon.servicio.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "programa_profesor",
       uniqueConstraints = @UniqueConstraint(columnNames = {"programa_id", "profesor_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaProfesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "programa_id", nullable = false)
    private Programa programa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesor;

    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion = LocalDate.now();
}
