package mx.unam.fesaragon.servicio.repository;

import mx.unam.fesaragon.servicio.entity.RegistroHoras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RegistroHorasRepository extends JpaRepository<RegistroHoras, Long> {
    List<RegistroHoras> findByAlumnoId(Long alumnoId);
    List<RegistroHoras> findByAlumnoIdOrderByFechaCreacionDesc(Long alumnoId);

    Optional<RegistroHoras> findByAlumnoIdAndEstado(Long alumnoId, RegistroHoras.EstadoRegistro estado);

    @Query("SELECT SUM(r.horasTotales) FROM RegistroHoras r WHERE r.alumno.id = :alumnoId AND r.estado = 'COMPLETADO'")
    Double sumHorasByAlumnoId(@Param("alumnoId") Long alumnoId);

    @Query("SELECT SUM(r.horasTotales) FROM RegistroHoras r WHERE r.alumno.programa.id = :programaId AND r.estado = 'COMPLETADO'")
    Double sumHorasByProgramaId(@Param("programaId") Long programaId);
}
