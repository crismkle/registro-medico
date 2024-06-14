package med.voll.apiMedic.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.apiMedic.domain.consulta.ConsultaRepository;
import med.voll.apiMedic.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datos){

        if (datos.idMedico() == null)
            return;

        var medicoConConsulta = repository.existsByMedicoIdAndData(datos.idMedico(), datos.fecha());

        if (medicoConConsulta){
            throw new ValidationException("Este médico ya tiene una consulta en ese horario");
        }

    }

}
