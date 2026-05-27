package mx.unam.fesaragon.servicio.service;

import mx.unam.fesaragon.servicio.dto.RegistroHoraRequest;
import mx.unam.fesaragon.servicio.entity.Alumno;
import mx.unam.fesaragon.servicio.entity.RegistroHoras;
import mx.unam.fesaragon.servicio.repository.AlumnoRepository;
import mx.unam.fesaragon.servicio.repository.RegistroHorasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HoraService {

    @Autowired private RegistroHorasRepository registroRepo;
    @Autowired private AlumnoRepository alumnoRepo;

    public List<RegistroHoras> findByAlumno(Long alumnoId) {
        return registroRepo.findByAlumnoIdOrderByFechaCreacionDesc(alumnoId);
    }

    /**
     * MODALIDAD 1: Registra entrada y salida en una sola petición.
     */
    @Transactional
    public RegistroHoras registroCompleto(RegistroHoraRequest req) {
        if (req.getHoraSalida() == null) {
            throw new IllegalArgumentException("Modalidad 1 requiere hora de salida.");
        }
        if (!req.getHoraSalida().isAfter(req.getHoraEntrada())) {
            throw new IllegalArgumentException("La hora de salida debe ser mayor que la hora de entrada.");
        }
        Alumno alumno = getAlumno(req.getAlumnoId());
        validarSinEntradaAbierta(alumno.getId());

        double horas = calcularHoras(req.getHoraEntrada(), req.getHoraSalida());

        RegistroHoras registro = new RegistroHoras();
        registro.setAlumno(alumno);
        registro.setHoraEntrada(req.getHoraEntrada());
        registro.setHoraSalida(req.getHoraSalida());
        registro.setHorasTotales(horas);
        registro.setModalidad(RegistroHoras.Modalidad.MODALIDAD_1);
        registro.setEstado(RegistroHoras.EstadoRegistro.COMPLETADO);
        registro.setObservaciones(req.getObservaciones());
        registro.setFechaCreacion(LocalDateTime.now());
        RegistroHoras saved = registroRepo.save(registro);

        actualizarHorasAlumno(alumno, horas);
        return saved;
    }

    /**
     * MODALIDAD 2 - PASO 1: Solo registra la entrada.
     */
    @Transactional
    public RegistroHoras registrarEntrada(RegistroHoraRequest req) {
        Alumno alumno = getAlumno(req.getAlumnoId());
        validarSinEntradaAbierta(alumno.getId());

        RegistroHoras registro = new RegistroHoras();
        registro.setAlumno(alumno);
        registro.setHoraEntrada(req.getHoraEntrada());
        registro.setModalidad(RegistroHoras.Modalidad.MODALIDAD_2);
        registro.setEstado(RegistroHoras.EstadoRegistro.EN_CURSO);
        registro.setObservaciones(req.getObservaciones());
        registro.setFechaCreacion(LocalDateTime.now());
        return registroRepo.save(registro);
    }

    /**
     * MODALIDAD 2 - PASO 2: Registra la salida cerrando el registro abierto.
     */
    @Transactional
    public RegistroHoras registrarSalida(Long alumnoId, LocalDateTime horaSalida, String observaciones) {
        RegistroHoras registro = registroRepo.findByAlumnoIdAndEstado(alumnoId, RegistroHoras.EstadoRegistro.EN_CURSO)
                .orElseThrow(() -> new IllegalStateException("No hay una entrada abierta para este alumno."));

        if (!horaSalida.isAfter(registro.getHoraEntrada())) {
            throw new IllegalArgumentException("La hora de salida debe ser mayor que la hora de entrada.");
        }

        double horas = calcularHoras(registro.getHoraEntrada(), horaSalida);

        registro.setHoraSalida(horaSalida);
        registro.setHorasTotales(horas);
        registro.setEstado(RegistroHoras.EstadoRegistro.COMPLETADO);
        if (observaciones != null) registro.setObservaciones(observaciones);
        RegistroHoras saved = registroRepo.save(registro);

        Alumno alumno = registro.getAlumno();
        actualizarHorasAlumno(alumno, horas);
        return saved;
    }

    private void validarSinEntradaAbierta(Long alumnoId) {
        registroRepo.findByAlumnoIdAndEstado(alumnoId, RegistroHoras.EstadoRegistro.EN_CURSO)
                .ifPresent(r -> {
                    throw new IllegalStateException("El alumno ya tiene una entrada abierta. Registre la salida primero.");
                });
    }

    private double calcularHoras(LocalDateTime entrada, LocalDateTime salida) {
        long minutos = Duration.between(entrada, salida).toMinutes();
        return Math.round((minutos / 60.0) * 100.0) / 100.0;
    }

    private Alumno getAlumno(Long id) {
        return alumnoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado: " + id));
    }

    private void actualizarHorasAlumno(Alumno alumno, double horas) {
        alumno.setHorasAcumuladas(alumno.getHorasAcumuladas() + horas);
        alumnoRepo.save(alumno);
    }
}
