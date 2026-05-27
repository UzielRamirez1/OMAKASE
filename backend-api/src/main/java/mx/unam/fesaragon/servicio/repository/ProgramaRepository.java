package mx.unam.fesaragon.servicio.repository;

import mx.unam.fesaragon.servicio.entity.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgramaRepository extends JpaRepository<Programa, Long> {
    List<Programa> findByEstado(Programa.Estado estado);
}
