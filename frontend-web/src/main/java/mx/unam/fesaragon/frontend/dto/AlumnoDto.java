package mx.unam.fesaragon.frontend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlumnoDto {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numeroCuenta;
    private String email;
    private Double horasAcumuladas;
    private boolean activo;
    private ProgramaDto programa;
    private UsuarioDto usuario;

    public String getNombreCompleto() {
        return nombre + " " + apellidoPaterno +
               (apellidoMaterno != null ? " " + apellidoMaterno : "");
    }
}
