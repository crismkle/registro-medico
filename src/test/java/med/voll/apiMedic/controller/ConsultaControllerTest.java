package med.voll.apiMedic.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc   // Realiza una simulación de la petición de este componente, evitando los demás componentes como el repositorio y los servicios de validaciones
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;    // Para realizar una simulación de una petición para este controlador

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
}