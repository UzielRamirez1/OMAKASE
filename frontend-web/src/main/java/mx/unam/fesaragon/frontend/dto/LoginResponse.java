package mx.unam.fesaragon.frontend.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String username;
    private String rol;
    private Long id;
    private String nombreCompleto;
}
