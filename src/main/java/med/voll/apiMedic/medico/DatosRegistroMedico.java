package med.voll.apiMedic.medico;

import med.voll.apiMedic.direccion.DatosDireccion;

public record DatosRegistroMedico(
        String nombre,
        String email,
        String documento,
        Especialidad especialidad,
        DatosDireccion direccion) {
}
