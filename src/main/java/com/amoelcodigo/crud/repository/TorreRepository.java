package com.amoelcodigo.crud.repository;

import com.amoelcodigo.crud.entity.Torre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TorreRepository  extends JpaRepository<Torre, Integer> {
        // Con @Repository le indico los metodos principales select

    //Convención sobre convicción
    Optional<Torre> findByNombreTorre(String nombreTorre);

    boolean existsByNombreTorre(String nombreTorre);

}
