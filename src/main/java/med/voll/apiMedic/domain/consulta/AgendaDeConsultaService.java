package med.voll.apiMedic.domain.consulta;

import med.voll.apiMedic.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.apiMedic.domain.medico.Medico;
import med.voll.apiMedic.domain.medico.MedicoRepository;
import med.voll.apiMedic.domain.paciente.PacienteRepository;
import med.voll.apiMedic.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){

        if (!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }

        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("Este id para el medico no fue encontrado");
        }

        validadores.forEach(v->v.validar(datos));       // Envía datos a cada uno de los validadores

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var medico = seleccionarMedico(datos);

        if (medico==null){
            throw new ValidacionDeIntegridad("No existen médicos disponibles para este horario y especialidad");
        }

        var consulta = new Consulta(null, medico, paciente, datos.fecha());

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);

    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {

        if (datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }

        if (datos.especialidad() == null){
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el médico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnData(datos.especialidad(), datos.fecha());
    }
}
