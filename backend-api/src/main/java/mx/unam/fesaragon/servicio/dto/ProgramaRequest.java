package mx.unam.fesaragon.servicio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProgramaRequest {
    @NotBlank
    private String nombre;
    private String descripcion;
    @NotNull
    private Integer horasRequeridas;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
}
