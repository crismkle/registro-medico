package med.voll.apiMedic.domain.medico;

import med.voll.apiMedic.domain.consulta.Consulta;
import med.voll.apiMedic.domain.direccion.DatosDireccion;
import med.voll.apiMedic.domain.direccion.Direccion;
import med.voll.apiMedic.domain.paciente.DatosRegistroPaciente;
import med.voll.apiMedic.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest        // Le indica que trabajamos con BD
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // Le digo que la BD es externa y que no va a reemplazar la BD que estaba utilizando
@ActiveProfiles("test")     // Le indica el perfil que va a utilizar
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Debería retornar nulo cuando el médico se encuentre en consulta con otro paciente en ese horario.")
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {  // Caso médico ocupado con otro paciente

        // Given
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = registrarMedico("José", "jose@mail.com", "123777", Especialidad.CARDIOLOGIA);
//        Medico medicoGuardado = medicoRepository.findById(medico.getId()).orElse(null);
//        System.out.println(medicoGuardado.getId() + medicoGuardado.getNombre());

        var paciente = registrarPaciente("Antonio", "as@gmail.com", "773534");
        registrarConsulta(medico, paciente, proximoLunes10H);

        // When
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnData(Especialidad.CARDIOLOGIA, proximoLunes10H);

        // Then
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Debería retornar un médico cuando realice la consulta en la base de datos para ese horario.")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {  // Caso de médico disponible

        // Given
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = registrarMedico("José", "jose@mail.com", "123777", Especialidad.CARDIOLOGIA);
//        Medico medicoGuardado = medicoRepository.findById(medico.getId()).orElse(null);
//        System.out.println(medicoGuardado.getId() + medicoGuardado.getNombre());

        // When
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnData(Especialidad.CARDIOLOGIA, proximoLunes10H);

        // Then
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        em.persist(new Consulta(medico, paciente, fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad) {
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento) {
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
        return new DatosRegistroMedico(
                nombre,
                email,
                "61999999999",
                documento,
                especialidad,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                email,
                "6199888999",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                " loca",
                "azul",
                "acapulpo",
                "321",
                "12"
        );
    }

}