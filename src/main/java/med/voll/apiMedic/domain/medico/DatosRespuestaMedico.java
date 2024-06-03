package med.voll.apiMedic.domain.medico;

import med.voll.apiMedic.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        String especialidad,
        DatosDireccion direccion
) {
}
