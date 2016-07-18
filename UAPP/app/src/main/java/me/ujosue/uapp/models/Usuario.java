package me.ujosue.uapp.models;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by HP240 on 15/07/2016.
 */
@SuppressWarnings("serial")
public class Usuario extends SugarRecord implements Serializable {
    private int id_Usuario;
    private String nombre;
    private String correo;
    private String contraseña;
    private String direccion;
    private String id_Rol;

    public Usuario() {
    }

    public Usuario(int id_Usuario, String nombre, String correo, String contraseña, String direccion, String id_Rol) {
        this.id_Usuario = id_Usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.direccion = direccion;
        this.id_Rol = id_Rol;
    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getId_Rol() {
        return id_Rol;
    }

    public void setId_Rol(String id_Rol) {
        this.id_Rol = id_Rol;
    }
}
