package med.voll.apiMedic.domain.medico;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest        // Le indica que trabajamos con BD
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // Le digo que la BD es externa y que no va a reemplazar la BD que estaba utilizando
@ActiveProfiles("test")     // Le indica el perfil que va a utilizar
class MedicoRepositoryTest {

    @Test
    void seleccionarMedicoConEspecialidadEnFecha() {
    }
}