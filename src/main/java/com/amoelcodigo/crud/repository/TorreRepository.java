package com.amoelcodigo.crud.repository;

import com.amoelcodigo.crud.entity.Torre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Notación para indicar que es un repositorio
@Repository
public interface TorreRepository  extends JpaRepository<Torre, Integer> {
        // Con @Repository le indico los metodos principales select, create, update, delete

    //Convención sobre convicción
    //CrudRepository permite realizar busquedas por campo según la entidad
    Optional<Torre> findByNombreTorre(String nombreTorre);

    boolean existsByNombreTorre(String nombreTorre);

}