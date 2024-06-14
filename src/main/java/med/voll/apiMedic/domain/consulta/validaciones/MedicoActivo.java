package med.voll.apiMedic.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.apiMedic.domain.consulta.DatosAgendarConsulta;
import med.voll.apiMedic.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service        // Es un servicio pero también puede ser un compoent
public class MedicoActivo implements ValidadorDeConsultas {

    @Autowired
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
