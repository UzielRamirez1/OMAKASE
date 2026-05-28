package mx.unam.fesaragon.servicio.service;

import mx.unam.fesaragon.servicio.dto.ProgramaRequest;
import mx.unam.fesaragon.servicio.entity.Programa;
import mx.unam.fesaragon.servicio.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepo;

    public List<Programa> findAll() {
        return programaRepo.findAll();
    }

    public Programa findById(Long id) {
        return programaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Programa no encontrado: " + id));
    }

    public Programa create(ProgramaRequest req) {
        Programa p = new Programa();
        p.setNombre(req.getNombre());
        p.setDescripcion(req.getDescripcion());
        p.setHorasRequeridas(req.getHorasRequeridas());
        p.setFechaInicio(req.getFechaInicio());
        p.setFechaFin(req.getFechaFin());
        p.setEstado(req.getEstado() != null
                ? Programa.Estado.valueOf(req.getEstado())
                : Programa.Estado.ACTIVO);
        return programaRepo.save(p);
    }

    public Programa update(Long id, ProgramaRequest req) {
        Programa p = findById(id);
        p.setNombre(req.getNombre());
        p.setDescripcion(req.getDescripcion());
        p.setHorasRequeridas(req.getHorasRequeridas());
        p.setFechaInicio(req.getFechaInicio());
        p.setFechaFin(req.getFechaFin());
        if (req.getEstado() != null) {
            p.setEstado(Programa.Estado.valueOf(req.getEstado()));
        }
        return programaRepo.save(p);
    }

    public void delete(Long id) {
        programaRepo.deleteById(id);
    }
}
