package med.voll.apiMedic.controller;

import med.voll.apiMedic.domain.consulta.AgendaDeConsultaService;
import med.voll.apiMedic.domain.consulta.DatosAgendarConsulta;
import med.voll.apiMedic.domain.consulta.DatosDetalleConsulta;
import med.voll.apiMedic.domain.medico.Especialidad;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc   // Realiza una simulación de la petición de este componente, evitando los demás componentes como el repositorio y los servicios de validaciones
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;    // Para realizar una simulación de una petición para este controlador

    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;   // Transforma un objeto de tipo Java y lo convierte en Json

    @Autowired
    private JacksonTester<DatosDetalleConsulta> detalleConsultaJacksonTester;   // Transfoma del Json a Java

    @MockBean
    private AgendaDeConsultaService agendaDeConsultaService;

    @Test
    @DisplayName("Debería retornar estado HTTP 400 cuando los datos ingresados sean inválidos.")
    @WithMockUser   // Para simular el token
    void agendarEscenario1() throws Exception {
        // Given // When
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());


        // -----------------------------------
        // Otra forma generando datos inválidos
//        var response = mvc.perform(post("/consultas")   // Realiza una solicitud POST al endpoint "/consultas".
//                        .contentType(MediaType.APPLICATION_JSON)            // Establece el tipo de contenido de la solicitud como JSON.
//                        .content("{\"pacienteId\": \"invalid_id\", \"medicoId\": \"invalid_id\", \"fecha\": \"2023-04-15\"}")       // Establece el cuerpo de la solicitud con datos inválidos.
//                        .with(csrf()))                                      // Agrega el token CSRF (Cross-Site Request Forgery) a la solicitud, si es necesario.
//                .andExpect(status().isBadRequest());                        // Verifica que la respuesta tenga un código de estado 400 (Bad Request), lo que indica que los datos enviados son inválidos.
    }


    @Test
    @DisplayName("Debería retornar estado HTTP 200 cuando los datos ingresados son válidos.")
    @WithMockUser
    void agendarEscenario2() throws Exception {
        // Given
        var fecha = LocalDateTime.now().plusHours(1);
        var especialidad = Especialidad.ORTOPEDIA;
        var datos = new DatosDetalleConsulta(null, 2l, 14l, fecha);

        // When

        when(agendaDeConsultaService.agendar(any())).thenReturn(datos);

        var response = mvc.perform(post("/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(agendarConsultaJacksonTester.write(new DatosAgendarConsulta(2l, 14l, fecha, especialidad)).getJson())
        ).andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = detalleConsultaJacksonTester.write(datos).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }
}