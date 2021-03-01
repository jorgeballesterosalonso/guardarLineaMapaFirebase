package com.pass.grabarmapas;

import java.util.List;

public class ListaUbicaciones {
    private String nombre;
    private List<Ubicacion> ubicaciones;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<Ubicacion> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    @Override
    public String toString() {
        return "ListaUbicaciones{" +
                "nombre='" + nombre + '\'' +
                ", ubicaciones=" + ubicaciones +
                '}';
    }
}
