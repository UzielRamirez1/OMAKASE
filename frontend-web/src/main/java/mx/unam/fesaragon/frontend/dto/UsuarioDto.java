package mx.unam.fesaragon.frontend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioDto {
    private Long id;
    private String username;
    private String rol;
    private boolean activo;
}
