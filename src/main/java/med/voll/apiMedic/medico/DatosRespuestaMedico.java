package med.voll.apiMedic.medico;

import med.voll.apiMedic.direccion.DatosDireccion;

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
