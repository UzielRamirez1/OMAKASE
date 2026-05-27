package mx.unam.fesaragon.servicio.repository;

import mx.unam.fesaragon.servicio.entity.Profesor;
import mx.unam.fesaragon.servicio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Optional<Profesor> findByUsuario(Usuario usuario);
    Optional<Profesor> findByUsuarioUsername(String username);
    List<Profesor> findByActivoTrue();
}
