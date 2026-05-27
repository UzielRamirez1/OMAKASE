package mx.unam.fesaragon.servicio.service;

import mx.unam.fesaragon.servicio.dto.ProfesorRequest;
import mx.unam.fesaragon.servicio.entity.Profesor;
import mx.unam.fesaragon.servicio.entity.Usuario;
import mx.unam.fesaragon.servicio.repository.ProfesorRepository;
import mx.unam.fesaragon.servicio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfesorService {

    @Autowired private ProfesorRepository profesorRepo;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    public List<Profesor> findAll() {
        return profesorRepo.findByActivoTrue();
    }

    public Profesor findById(Long id) {
        return profesorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado: " + id));
    }

    public Profesor findByUsername(String username) {
        return profesorRepo.findByUsuarioUsername(username)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado: " + username));
    }

    @Transactional
    public Profesor create(ProfesorRequest req) {
        if (usuarioRepo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("El username ya está en uso: " + req.getUsername());
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(req.getUsername());
        usuario.setPassword(passwordEncoder.encode(req.getPassword()));
        usuario.setRol(Usuario.Rol.PROFESOR);
        usuario.setActivo(true);
        usuarioRepo.save(usuario);

        Profesor profesor = new Profesor();
        profesor.setUsuario(usuario);
        profesor.setNombre(req.getNombre());
        profesor.setApellidoPaterno(req.getApellidoPaterno());
        profesor.setApellidoMaterno(req.getApellidoMaterno());
        profesor.setEmail(req.getEmail());
        profesor.setNumeroEmpleado(req.getNumeroEmpleado());
        profesor.setActivo(true);
        return profesorRepo.save(profesor);
    }
}
