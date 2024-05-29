package med.voll.apiMedic.controller;

import med.voll.apiMedic.medico.DatosRegistroMedico;
import med.voll.apiMedic.medico.Medico;
import med.voll.apiMedic.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRpository;

    @PostMapping
    public void registrarMedico(@RequestBody DatosRegistroMedico datosRegistroMedico){
        medicoRpository.save(new Medico(datosRegistroMedico));
    }
}
