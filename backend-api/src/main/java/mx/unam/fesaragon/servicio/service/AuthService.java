package mx.unam.fesaragon.servicio.service;

import mx.unam.fesaragon.servicio.dto.LoginRequest;
import mx.unam.fesaragon.servicio.dto.LoginResponse;
import mx.unam.fesaragon.servicio.entity.Alumno;
import mx.unam.fesaragon.servicio.entity.Profesor;
import mx.unam.fesaragon.servicio.entity.Usuario;
import mx.unam.fesaragon.servicio.repository.AlumnoRepository;
import mx.unam.fesaragon.servicio.repository.ProfesorRepository;
import mx.unam.fesaragon.servicio.repository.UsuarioRepository;
import mx.unam.fesaragon.servicio.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider tokenProvider;
    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private ProfesorRepository profesorRepo;
    @Autowired private AlumnoRepository alumnoRepo;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = tokenProvider.generateToken(authentication);
        Usuario usuario = usuarioRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String nombreCompleto = resolveNombreCompleto(usuario);
        Long entityId = resolveEntityId(usuario);

        return new LoginResponse(token, usuario.getUsername(), usuario.getRol().name(), entityId, nombreCompleto);
    }

    private String resolveNombreCompleto(Usuario usuario) {
        return switch (usuario.getRol()) {
            case ADMIN -> "Administrador";
            case PROFESOR -> profesorRepo.findByUsuario(usuario)
                    .map(Profesor::getNombreCompleto).orElse(usuario.getUsername());
            case ALUMNO -> alumnoRepo.findByUsuario(usuario)
                    .map(Alumno::getNombreCompleto).orElse(usuario.getUsername());
        };
    }

    private Long resolveEntityId(Usuario usuario) {
        return switch (usuario.getRol()) {
            case ADMIN -> usuario.getId();
            case PROFESOR -> profesorRepo.findByUsuario(usuario)
                    .map(Profesor::getId).orElse(usuario.getId());
            case ALUMNO -> alumnoRepo.findByUsuario(usuario)
                    .map(Alumno::getId).orElse(usuario.getId());
        };
    }
}
