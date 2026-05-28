package mx.unam.fesaragon.servicio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfesorRequest {
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String numeroEmpleado;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
