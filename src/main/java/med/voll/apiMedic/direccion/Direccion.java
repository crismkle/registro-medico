package med.voll.apiMedic.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter             // Lombok crea los getters al compilar
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    private String calle;
    private Integer numero;
    private String complemento;
    private String distrito;
    private String ciudad;

}
