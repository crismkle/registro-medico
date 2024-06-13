package med.voll.apiMedic.controller;


import jakarta.validation.Valid;
import med.voll.apiMedic.domain.consulta.AgendaDeConsultaService;
import med.voll.apiMedic.domain.consulta.DatosAgendarConsulta;
import med.voll.apiMedic.domain.consulta.DatosDetalleConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){

        service.agendar(datos);

        return ResponseEntity.ok(new DatosDetalleConsulta(null, null, null, null));
    }
}
