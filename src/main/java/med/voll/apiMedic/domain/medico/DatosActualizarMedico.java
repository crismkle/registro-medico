package med.voll.apiMedic.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.apiMedic.domain.direccion.DatosDireccion;

public record DatosActualizarMedico(
        @NotNull Long id,
        String nombre,
        String documento,
        DatosDireccion direccion
) {
}
