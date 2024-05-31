package med.voll.apiMedic.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.apiMedic.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRpository;

    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
        medicoRpository.save(new Medico(datosRegistroMedico));
    }

    // Devuelve los datos de una sola vez
//    @GetMapping
//    public List<DatosListadoMedico> listadoMedicos(){
//        return medicoRpository.findAll().stream()
//                .map(DatosListadoMedico::new)    // Con cada elemento crea un nuevo DatosListadoMedico, necesita un constructor (no me toma el Lombok, por eso creo los getters)
//                .toList();
//    }

    // Ahora con paginación
    @GetMapping
    public Page<DatosListadoMedico> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {
        return medicoRpository.findAll(paginacion)
                .map(DatosListadoMedico::new);    // Con cada elemento crea un nuevo DatosListadoMedico, necesita un constructor (no me toma el Lombok, por eso creo los getters)
    }

    @PutMapping
    @Transactional          // Hibernate mapea que cuando termine el método la transacción se va a liberar
    public void actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){  //Valida que el id no llegue null
        Medico medico = medicoRpository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
    }

    

}
