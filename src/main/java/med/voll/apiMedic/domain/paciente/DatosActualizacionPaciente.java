package med.voll.apiMedic.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.apiMedic.domain.direccion.DatosDireccion;

public record DatosActualizacionPaciente(
        @NotNull
        Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion) {
}
