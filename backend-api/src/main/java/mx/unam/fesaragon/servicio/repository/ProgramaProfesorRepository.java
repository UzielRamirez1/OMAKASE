package mx.unam.fesaragon.servicio.repository;

import mx.unam.fesaragon.servicio.entity.ProgramaProfesor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgramaProfesorRepository extends JpaRepository<ProgramaProfesor, Long> {
    List<ProgramaProfesor> findByProfesorId(Long profesorId);
    List<ProgramaProfesor> findByProgramaId(Long programaId);
    boolean existsByProgramaIdAndProfesorId(Long programaId, Long profesorId);
}
