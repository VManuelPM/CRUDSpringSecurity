package com.amoelcodigo.crud.entity;

import javax.persistence.*;

@Entity
@Table(name = "torre")
public class Torre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTorre;
    private String nombreTorre;
    private int cantidadAptos;

    public Torre() {
    }

    public Torre(String nombreTorre, int cantidadAptos) {
        this.nombreTorre = nombreTorre;
        this.cantidadAptos = cantidadAptos;
    }

    public int getIdTorre() {
        return idTorre;
    }

    public void setIdTorre(int idTorre) {
        this.idTorre = idTorre;
    }

    public String getNombreTorre() {
        return nombreTorre;
    }

    public void setNombreTorre(String nombreTorre) {
        this.nombreTorre = nombreTorre;
    }

    public int getCantidadAptos() {
        return cantidadAptos;
    }

    public void setCantidadAptos(int cantidadAptos) {
        this.cantidadAptos = cantidadAptos;
    }
}
