package mx.unam.fesaragon.servicio.service;

import mx.unam.fesaragon.servicio.dto.AlumnoRequest;
import mx.unam.fesaragon.servicio.entity.Alumno;
import mx.unam.fesaragon.servicio.entity.Programa;
import mx.unam.fesaragon.servicio.entity.Usuario;
import mx.unam.fesaragon.servicio.repository.AlumnoRepository;
import mx.unam.fesaragon.servicio.repository.ProgramaRepository;
import mx.unam.fesaragon.servicio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnoService {

    @Autowired private AlumnoRepository alumnoRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private ProgramaRepository programaRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    public List<Alumno> findAll() {
        return alumnoRepo.findByActivoTrue();
    }

    public Alumno findById(Long id) {
        return alumnoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado: " + id));
    }

    public Alumno findByUsername(String username) {
        return alumnoRepo.findByUsuarioUsername(username)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado: " + username));
    }

    public List<Alumno> findByPrograma(Long programaId) {
        return alumnoRepo.findByProgramaId(programaId);
    }

    @Transactional
    public Alumno create(AlumnoRequest req) {
        if (usuarioRepo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("El username ya está en uso: " + req.getUsername());
        }
        Programa programa = programaRepo.findById(req.getProgramaId())
                .orElseThrow(() -> new RuntimeException("Programa no encontrado: " + req.getProgramaId()));

        Usuario usuario = new Usuario();
        usuario.setUsername(req.getUsername());
        usuario.setPassword(passwordEncoder.encode(req.getPassword()));
        usuario.setRol(Usuario.Rol.ALUMNO);
        usuario.setActivo(true);
        usuarioRepo.save(usuario);

        Alumno alumno = new Alumno();
        alumno.setUsuario(usuario);
        alumno.setPrograma(programa);
        alumno.setNombre(req.getNombre());
        alumno.setApellidoPaterno(req.getApellidoPaterno());
        alumno.setApellidoMaterno(req.getApellidoMaterno());
        alumno.setNumeroCuenta(req.getNumeroCuenta());
        alumno.setEmail(req.getEmail());
        alumno.setHorasAcumuladas(0.0);
        alumno.setActivo(true);
        return alumnoRepo.save(alumno);
    }
}
