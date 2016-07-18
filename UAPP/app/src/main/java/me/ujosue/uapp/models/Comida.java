package me.ujosue.uapp.models;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by HP240 on 16/07/2016.
 */
@SuppressWarnings("serial")
public class Comida extends SugarRecord implements Serializable {
    private int id_Comida;
    private String nombre;
    private String descripcion;
    private int precio;
    private String fotoUrl;

    public Comida() {
    }

    public Comida(int id_Comida, String nombre, String descripcion, int precio, String fotoUrl) {
        this.id_Comida = id_Comida;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fotoUrl = fotoUrl;
    }

    public int getId_Comida() {
        return id_Comida;
    }

    public void setId_Comida(int id_Comida) {
        this.id_Comida = id_Comida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }
}
