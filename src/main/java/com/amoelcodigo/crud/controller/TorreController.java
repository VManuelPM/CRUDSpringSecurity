package com.amoelcodigo.crud.controller;

import com.amoelcodigo.crud.dto.Mensaje;
import com.amoelcodigo.crud.dto.TorreDto;
import com.amoelcodigo.crud.entity.Torre;
import com.amoelcodigo.crud.service.TorreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/torre")
@CrossOrigin(origins = "*")
public class TorreController {

    @Autowired
    TorreService torreService;

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

    @DeleteMapping("/borrarTorre/{idTorre}")
    public ResponseEntity<?> borrarTorre(@PathVariable("idTorre") int idTorre){
        if (!torreService.existsByIdTorre(idTorre))
            return new ResponseEntity(new Mensaje("No existe la torre"), HttpStatus.NOT_FOUND);
        torreService.deleteTorre(idTorre);
        return new ResponseEntity(new Mensaje("Torre eliminada"), HttpStatus.OK);
    }

}
