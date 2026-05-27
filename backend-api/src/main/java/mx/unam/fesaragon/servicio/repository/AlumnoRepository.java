package mx.unam.fesaragon.servicio.repository;

import mx.unam.fesaragon.servicio.entity.Alumno;
import mx.unam.fesaragon.servicio.entity.Programa;
import mx.unam.fesaragon.servicio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Optional<Alumno> findByUsuario(Usuario usuario);
    Optional<Alumno> findByUsuarioUsername(String username);
    List<Alumno> findByPrograma(Programa programa);
    List<Alumno> findByProgramaId(Long programaId);
    List<Alumno> findByActivoTrue();
}
