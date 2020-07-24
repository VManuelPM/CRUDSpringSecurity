package com.amoelcodigo.crud.controller;

import com.amoelcodigo.crud.dto.Mensaje;
import com.amoelcodigo.crud.dto.TorreDto;
import com.amoelcodigo.crud.entity.Torre;
import com.amoelcodigo.crud.service.TorreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Notación para indicar que es un controlador de tipo Rest
@RestController
//Notación para indicar el contexto de nuestros endpoint es decir /torre/nombreServicio
@RequestMapping("/torre")
//URL que permitimos que consuman nuestras APIS
//En caso de querer permitir todos los origentes ponemos en lugar de la URL un *
@CrossOrigin(origins = "http://localhost:4200")
public class TorreController {

    /*El nombre de las torres es unico,
    en la creación y actualizacón se hace la validación*/

    //Inyección de dependencias
    @Autowired
    TorreService torreService;

    //Se le indica el tipo de petición asi como el nombre del servicio
    @GetMapping("/listaTorre")
    public ResponseEntity<List<Torre>> listaTorres(){

        List<Torre> torres = torreService.listaTorre();
        return new ResponseEntity<List<Torre>>(torres, HttpStatus.OK);
    }

    @GetMapping("/detalleTorre/{idTorre}")
    public ResponseEntity<Torre> torreById(@PathVariable("idTorre") int idTorre){

        if (!torreService.existsByIdTorre(idTorre))
            return new ResponseEntity(new Mensaje("No existe la torre"), HttpStatus.NOT_FOUND);

        Torre torre = torreService.getTorre(idTorre).get();
        return new ResponseEntity(torre, HttpStatus.OK);
    }

    @GetMapping("/detalleNombre/{nombreTorre}")
    public ResponseEntity<Torre> torreByNombre(@PathVariable("nombreTorre") String nombreTorre){

        if (!torreService.existsByNombreTorre(nombreTorre))
            return new ResponseEntity(new Mensaje("No existe la torre"), HttpStatus.NOT_FOUND);

        Torre torre = torreService.getByNombreTorre(nombreTorre).get();
        return new ResponseEntity(torre, HttpStatus.OK);
    }

    //Con el ? le decimos que no devulve ningún tipo de dato
    //El body va a ser un JSON
    //Aqui se usa el apache commons lang
    // El import de StringUtils es import org.apache.commons.lang3.StringUtils;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/crearTorre")
    public ResponseEntity<?> creaTorre(@RequestBody TorreDto torreDto){

        if(StringUtils.isBlank(torreDto.getNombreTorre()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        if(torreDto.getCantidadAptos()<0 || (Integer) torreDto.getCantidadAptos() == null)
            return new ResponseEntity(new Mensaje("La cantidad de aptos debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        if(torreService.existsByNombreTorre(torreDto.getNombreTorre()))
            return new ResponseEntity(new Mensaje("Ya existe una torre con ese nombre"), HttpStatus.BAD_REQUEST);

        Torre torre = new Torre(torreDto.getNombreTorre(), torreDto.getCantidadAptos());
        torreService.saveTorre(torre);
        return new ResponseEntity(new Mensaje("Torre creada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizarTorre/{idTorre}")
    public ResponseEntity<?> actualizarTorre(@PathVariable("idTorre") int idTorre, @RequestBody TorreDto torreDto){

        if (!torreService.existsByIdTorre(idTorre))
        return new ResponseEntity(new Mensaje("No existe la torre"), HttpStatus.NOT_FOUND);

        if (torreService.existsByNombreTorre(torreDto.getNombreTorre())
                && torreService.getByNombreTorre(torreDto.getNombreTorre()).get().getIdTorre() != idTorre)
            return new ResponseEntity(new Mensaje("El nombre de la torre ya existe"), HttpStatus.NOT_FOUND);

        if(StringUtils.isBlank(torreDto.getNombreTorre()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);

        if(torreDto.getCantidadAptos()<0 || (Integer) torreDto.getCantidadAptos() == null)
            return new ResponseEntity(new Mensaje("La cantidad de aptos debe ser mayor a 0"), HttpStatus.BAD_REQUEST);

        Torre torre = torreService.getTorre(idTorre).get();
        torre.setNombreTorre(torreDto.getNombreTorre());
        torre.setCantidadAptos(torreDto.getCantidadAptos());
        torreService.saveTorre(torre);
        return new ResponseEntity(new Mensaje("Torre actualizada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/borrarTorre/{idTorre}")
    public ResponseEntity<?> borrarTorre(@PathVariable("idTorre") int idTorre){
        if (!torreService.existsByIdTorre(idTorre))
            return new ResponseEntity(new Mensaje("No existe la torre"), HttpStatus.NOT_FOUND);
        torreService.deleteTorre(idTorre);
        return new ResponseEntity(new Mensaje("Torre eliminada"), HttpStatus.OK);
    }

}
