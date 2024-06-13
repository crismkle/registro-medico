package med.voll.apiMedic.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.apiMedic.domain.consulta.DatosAgendarConsulta;
import med.voll.apiMedic.domain.paciente.PacienteRepository;

public class PacienteActivo {


    private PacienteRepository repository;

    public void validar(DatosAgendarConsulta datos){

        if (datos.idPaciente() == null){
            return;
        }

        var pacienteActivo = repository.findActivoById(datos.idPaciente());

        if (!pacienteActivo){
            throw new ValidationException("No se permite agendar citas con pacientes inactivos");
        }
    }

}
