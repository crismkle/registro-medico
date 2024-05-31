package med.voll.apiMedic.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.apiMedic.direccion.DatosDireccion;

public record DatosActualizarMedico(
        @NotNull Long id,
        String nombre,
        String documento,
        DatosDireccion direccion
) {
}
