package mx.unam.fesaragon.servicio.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "profesores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido_paterno", nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;

    @Column(length = 150)
    private String email;

    @Column(name = "numero_empleado", length = 20)
    private String numeroEmpleado;

    @Column(nullable = false)
    private boolean activo = true;

    @com.fasterxml.jackson.annotation.JsonIgnore
    @OneToMany(mappedBy = "profesor", fetch = FetchType.LAZY)
    private List<ProgramaProfesor> programaProfesores;

    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno + (apellidoMaterno != null ? " " + apellidoMaterno : "");
    }
}
