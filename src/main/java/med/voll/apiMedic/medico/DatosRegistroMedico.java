package med.voll.apiMedic.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.apiMedic.direccion.DatosDireccion;

public record DatosRegistroMedico(

        @NotBlank       // NotBlank valida que no esté en blanco y además que no sea nulo
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefono,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")   // Que sea de 4 a 6 digitos
        String documento,
        @NotNull
        Especialidad especialidad,
        @NotNull                        // Not null porque es un objeto
        @Valid
        DatosDireccion direccion) {

}
