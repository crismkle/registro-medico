package med.voll.apiMedic.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.apiMedic.domain.direccion.DatosDireccion;
import med.voll.apiMedic.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRpository;

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRpository.save(new Medico(datosRegistroMedico));

        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(),medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));

        //URI url = "http://localhost:8080/medicos/" + medico.getId();
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);

        // return 201 Created
        // URL donde encontrar al médico
        // GET http://localhost:8080/medicos/xx Por Standart universal debe retornar la url para acceder al medico
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
    public ResponseEntity<Page> listadoMedicos(@PageableDefault(size = 2) Pageable paginacion) {
//        return medicoRpository.findAll(paginacion)
//                .map(DatosListadoMedico::new);    // Con cada elemento crea un nuevo DatosListadoMedico, necesita un constructor (no me toma el Lombok, por eso creo los getters)

        return ResponseEntity.ok(medicoRpository.findByActivoTrue(paginacion)
                .map(DatosListadoMedico::new));          // Filtra los médicos lógicos
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

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedicos(@PathVariable Long id){
        Medico medico = medicoRpository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(),medico.getDocumento(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }


}
