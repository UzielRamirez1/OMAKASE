package mx.unam.fesaragon.servicio.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RegistroHoraRequest {
    @NotNull
    private Long alumnoId;
    @NotNull
    private String modalidad; // MODALIDAD_1 o MODALIDAD_2
    @NotNull
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida; // solo para MODALIDAD_1
    private String observaciones;
}
