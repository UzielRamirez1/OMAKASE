package mx.unam.fesaragon.servicio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlumnoRequest {
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numeroCuenta;
    private String email;
    @NotNull
    private Long programaId;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
