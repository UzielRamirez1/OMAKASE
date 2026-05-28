package mx.unam.fesaragon.servicio.controller;

import jakarta.validation.Valid;
import mx.unam.fesaragon.servicio.dto.RegistroHoraRequest;
import mx.unam.fesaragon.servicio.entity.RegistroHoras;
import mx.unam.fesaragon.servicio.service.HoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/horas")
public class HoraController {

    @Autowired
    private HoraService horaService;

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<RegistroHoras>> getByAlumno(@PathVariable Long alumnoId) {
        return ResponseEntity.ok(horaService.findByAlumno(alumnoId));
    }

    @PostMapping("/registro-completo")
    public ResponseEntity<RegistroHoras> registroCompleto(@Valid @RequestBody RegistroHoraRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horaService.registroCompleto(request));
    }

    @PostMapping("/entrada")
    public ResponseEntity<RegistroHoras> registrarEntrada(@Valid @RequestBody RegistroHoraRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horaService.registrarEntrada(request));
    }

    @PostMapping("/salida")
    public ResponseEntity<RegistroHoras> registrarSalida(@RequestBody Map<String, Object> body) {
        Long alumnoId = Long.valueOf(body.get("alumnoId").toString());
        LocalDateTime horaSalida = LocalDateTime.parse(body.get("horaSalida").toString());
        String observaciones = body.containsKey("observaciones") ? body.get("observaciones").toString() : null;
        return ResponseEntity.ok(horaService.registrarSalida(alumnoId, horaSalida, observaciones));
    }
}
