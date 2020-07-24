package com.amoelcodigo.crud.security.repository;

import com.amoelcodigo.crud.security.entity.Rol;
import com.amoelcodigo.crud.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
