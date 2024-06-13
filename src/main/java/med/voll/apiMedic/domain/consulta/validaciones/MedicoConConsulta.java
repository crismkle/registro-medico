package med.voll.apiMedic.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.apiMedic.domain.consulta.ConsultaRepository;
import med.voll.apiMedic.domain.consulta.DatosAgendarConsulta;

public class MedicoConConsulta {

    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datos){

        if (datos.idMedico() == null)
            return;

        var medicoConConsulta = repository.existByMedicoIdAndData(datos.idMedico(), datos.fecha());

        if (medicoConConsulta){
            throw new ValidationException("Este médico ya tiene una consulta en ese horario");
        }

    }

}
