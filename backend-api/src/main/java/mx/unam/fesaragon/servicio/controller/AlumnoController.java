package mx.unam.fesaragon.servicio.controller;

import jakarta.validation.Valid;
import mx.unam.fesaragon.servicio.dto.AlumnoRequest;
import mx.unam.fesaragon.servicio.entity.Alumno;
import mx.unam.fesaragon.servicio.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<Alumno>> getAll() {
        return ResponseEntity.ok(alumnoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> getById(@PathVariable Long id) {
        return ResponseEntity.ok(alumnoService.findById(id));
    }

    @GetMapping("/programa/{programaId}")
    public ResponseEntity<List<Alumno>> getByPrograma(@PathVariable Long programaId) {
        return ResponseEntity.ok(alumnoService.findByPrograma(programaId));
    }

    @PostMapping
    public ResponseEntity<Alumno> create(@Valid @RequestBody AlumnoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.create(request));
    }
}
