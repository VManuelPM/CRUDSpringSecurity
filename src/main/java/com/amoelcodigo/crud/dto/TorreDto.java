package com.amoelcodigo.crud.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class TorreDto {

    //Notación para especificar que el campo no puede venir vacio
    @NotBlank
    private String nombreTorre;
    //Notación para indicar que el tamaño minimo debe ser 0
    @Min(0)
    private int cantidadAptos;

    public TorreDto() {
    }

    public TorreDto(String nombreTorre, int cantidadAptos) {
        this.nombreTorre = nombreTorre;
        this.cantidadAptos = cantidadAptos;
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
