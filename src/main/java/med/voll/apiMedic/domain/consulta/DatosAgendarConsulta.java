package med.voll.apiMedic.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.apiMedic.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record DatosAgendarConsulta(
        //Long id,
        @NotNull
        Long idPaciente,
        Long idMedico,
        @NotNull
        @Future     // Posterior a la fecha actual
        LocalDateTime fecha,
        Especialidad especialidad
) {
}
