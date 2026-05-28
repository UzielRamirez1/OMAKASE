package mx.unam.fesaragon.servicio.config;

import mx.unam.fesaragon.servicio.entity.*;
import mx.unam.fesaragon.servicio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired private UsuarioRepository usuarioRepo;
    @Autowired private ProgramaRepository programaRepo;
    @Autowired private ProfesorRepository profesorRepo;
    @Autowired private AlumnoRepository alumnoRepo;
    @Autowired private ProgramaProfesorRepository ppRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (usuarioRepo.count() > 0) {
            return; // Ya hay datos, no reinsertar
        }

        // ==================== USUARIOS ====================
        Usuario uAdmin = new Usuario();
        uAdmin.setUsername("admin");
        uAdmin.setPassword(passwordEncoder.encode("Admin1234!"));
        uAdmin.setRol(Usuario.Rol.ADMIN);
        uAdmin.setActivo(true);
        usuarioRepo.save(uAdmin);

        Usuario uProf1 = new Usuario();
        uProf1.setUsername("profesor1");
        uProf1.setPassword(passwordEncoder.encode("Prof1234!"));
        uProf1.setRol(Usuario.Rol.PROFESOR);
        uProf1.setActivo(true);
        usuarioRepo.save(uProf1);

        Usuario uProf2 = new Usuario();
        uProf2.setUsername("profesor2");
        uProf2.setPassword(passwordEncoder.encode("Prof1234!"));
        uProf2.setRol(Usuario.Rol.PROFESOR);
        uProf2.setActivo(true);
        usuarioRepo.save(uProf2);

        Usuario uAlum1 = new Usuario();
        uAlum1.setUsername("alumno1");
        uAlum1.setPassword(passwordEncoder.encode("Alum1234!"));
        uAlum1.setRol(Usuario.Rol.ALUMNO);
        uAlum1.setActivo(true);
        usuarioRepo.save(uAlum1);

        Usuario uAlum2 = new Usuario();
        uAlum2.setUsername("alumno2");
        uAlum2.setPassword(passwordEncoder.encode("Alum1234!"));
        uAlum2.setRol(Usuario.Rol.ALUMNO);
        uAlum2.setActivo(true);
        usuarioRepo.save(uAlum2);

        Usuario uAlum3 = new Usuario();
        uAlum3.setUsername("alumno3");
        uAlum3.setPassword(passwordEncoder.encode("Alum1234!"));
        uAlum3.setRol(Usuario.Rol.ALUMNO);
        uAlum3.setActivo(true);
        usuarioRepo.save(uAlum3);

        // ==================== PROGRAMA ====================
        Programa programa = new Programa();
        programa.setNombre("Laboratorio de Macrodatos FES Aragón");
        programa.setDescripcion("Programa de servicio social en el área de Big Data y análisis de datos masivos para la FES Aragón, UNAM.");
        programa.setEstado(Programa.Estado.ACTIVO);
        programa.setHorasRequeridas(480);
        programa.setFechaInicio(LocalDate.of(2024, 2, 1));
        programa.setFechaFin(LocalDate.of(2024, 12, 31));
        programaRepo.save(programa);

        // ==================== PROFESORES ====================
        Profesor prof1 = new Profesor();
        prof1.setUsuario(uProf1);
        prof1.setNombre("Carlos");
        prof1.setApellidoPaterno("García");
        prof1.setApellidoMaterno("López");
        prof1.setEmail("c.garcia@fesaragon.unam.mx");
        prof1.setNumeroEmpleado("EMP001");
        prof1.setActivo(true);
        profesorRepo.save(prof1);

        Profesor prof2 = new Profesor();
        prof2.setUsuario(uProf2);
        prof2.setNombre("María");
        prof2.setApellidoPaterno("Hernández");
        prof2.setApellidoMaterno("Martínez");
        prof2.setEmail("m.hernandez@fesaragon.unam.mx");
        prof2.setNumeroEmpleado("EMP002");
        prof2.setActivo(true);
        profesorRepo.save(prof2);

        // ==================== ALUMNOS ====================
        Alumno alum1 = new Alumno();
        alum1.setUsuario(uAlum1);
        alum1.setPrograma(programa);
        alum1.setNombre("Ana");
        alum1.setApellidoPaterno("Martínez");
        alum1.setApellidoMaterno("Pérez");
        alum1.setNumeroCuenta("316012345");
        alum1.setEmail("ana.martinez@alumno.unam.mx");
        alum1.setHorasAcumuladas(0.0);
        alum1.setActivo(true);
        alumnoRepo.save(alum1);

        Alumno alum2 = new Alumno();
        alum2.setUsuario(uAlum2);
        alum2.setPrograma(programa);
        alum2.setNombre("Juan");
        alum2.setApellidoPaterno("Sánchez");
        alum2.setApellidoMaterno("Ramos");
        alum2.setNumeroCuenta("316023456");
        alum2.setEmail("juan.sanchez@alumno.unam.mx");
        alum2.setHorasAcumuladas(0.0);
        alum2.setActivo(true);
        alumnoRepo.save(alum2);

        Alumno alum3 = new Alumno();
        alum3.setUsuario(uAlum3);
        alum3.setPrograma(programa);
        alum3.setNombre("Laura");
        alum3.setApellidoPaterno("Torres");
        alum3.setApellidoMaterno("Vega");
        alum3.setNumeroCuenta("316034567");
        alum3.setEmail("laura.torres@alumno.unam.mx");
        alum3.setHorasAcumuladas(0.0);
        alum3.setActivo(true);
        alumnoRepo.save(alum3);

        // ==================== PROGRAMA-PROFESOR ====================
        ProgramaProfesor pp1 = new ProgramaProfesor();
        pp1.setPrograma(programa);
        pp1.setProfesor(prof1);
        pp1.setFechaAsignacion(LocalDate.now());
        ppRepo.save(pp1);

        ProgramaProfesor pp2 = new ProgramaProfesor();
        pp2.setPrograma(programa);
        pp2.setProfesor(prof2);
        pp2.setFechaAsignacion(LocalDate.now());
        ppRepo.save(pp2);

        System.out.println("✅ Datos de prueba insertados correctamente.");
    }
}
