package mx.unam.fesaragon.frontend.dto;

import lombok.Data;

@Data
public class AlumnoRequest {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numeroCuenta;
    private String email;
    private Long programaId;
    private String username;
    private String password;
}
