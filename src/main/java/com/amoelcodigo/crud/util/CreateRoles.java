package com.amoelcodigo.crud.util;

import com.amoelcodigo.crud.security.entity.Rol;
import com.amoelcodigo.crud.security.enums.RolNombre;
import com.amoelcodigo.crud.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
/*
@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
        Rol rolUser = new Rol(RolNombre.ROLE_USER);
        rolService.save(rolAdmin);
        rolService.save(rolUser);
    }
} */
