package mx.unam.fesaragon.frontend.dto;

import lombok.Data;

@Data
public class ProfesorRequest {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String numeroEmpleado;
    private String username;
    private String password;
}
