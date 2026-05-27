package mx.unam.fesaragon.frontend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfesorDto {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String numeroEmpleado;
    private boolean activo;
    private UsuarioDto usuario;

    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno +
               (apellidoMaterno != null ? " " + apellidoMaterno : "");
    }
}
