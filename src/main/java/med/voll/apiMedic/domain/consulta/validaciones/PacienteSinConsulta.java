package med.voll.apiMedic.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.apiMedic.domain.consulta.ConsultaRepository;
import med.voll.apiMedic.domain.consulta.DatosAgendarConsulta;

public class PacienteSinConsulta {

    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datos){

        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario = datos.fecha().withHour(18);

        var pacienteConConsulta = repository.existsByPacienteIdAndDataBetween(datos.idPaciente(), primerHorario, ultimoHorario);

        if (pacienteConConsulta){
            throw new ValidationException("El paciente ya tiene una consulta para ese día");
        }
    }

}