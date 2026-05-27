package mx.unam.fesaragon.frontend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistroHorasDto {
    private Long id;
    private AlumnoDto alumno;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private Double horasTotales;
    private String modalidad;
    private String estado;
    private String observaciones;
    private LocalDateTime fechaCreacion;
}
