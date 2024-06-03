package med.voll.apiMedic.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.apiMedic.direccion.DatosDireccion;
import med.voll.apiMedic.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
//        return medicoRpository.findAll(paginacion)
//                .map(DatosListadoMedico::new);    // Con cada elemento crea un nuevo DatosListadoMedico, necesita un constructor (no me toma el Lombok, por eso creo los getters)

        return medicoRpository.findByActivoTrue(paginacion)
                .map(DatosListadoMedico::new);          // Filtra los médicos lógicos
    }

    @PutMapping
    @Transactional          // Hibernate mapea que cuando termine el método la transacción se va a liberar
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){  //Valida que el id no llegue null
        Medico medico = medicoRpository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(),medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }

//    DELETE físico de la BD
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarMedico(@PathVariable Long id){
//        Medico medico = medicoRpository.getReferenceById(id);
//        medicoRpository.delete(medico);
//    }


    // DELETE lógico de la BD
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRpository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }


}
