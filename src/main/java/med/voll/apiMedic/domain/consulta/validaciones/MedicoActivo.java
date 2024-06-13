package med.voll.apiMedic.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.apiMedic.domain.consulta.DatosAgendarConsulta;
import med.voll.apiMedic.domain.medico.MedicoRepository;

public class MedicoActivo {

    private MedicoRepository repository;

    public void validar(DatosAgendarConsulta datos){

        if (datos.idMedico() == null){
            return;
        }

        var medicoActivo = repository.findActivoById(datos.idPaciente());

        if (!medicoActivo){
            throw new ValidationException("No se permite agendar citas con médicos inactivos");
        }
    }

}
