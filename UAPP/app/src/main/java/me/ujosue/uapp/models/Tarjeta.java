package me.ujosue.uapp.models;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by HP240 on 17/07/2016.
 */
@SuppressWarnings("serial")
public class Tarjeta extends SugarRecord implements Serializable {
    private int id_Tarjeta;
    private int id_Usuario;
    private int numero;
    private int zip;
    private String titular;

    public Tarjeta() {
    }

    public Tarjeta(int id_Tarjeta, int id_Usuario, int numero, int zip, String titular) {
        this.id_Tarjeta = id_Tarjeta;
        this.id_Usuario = id_Usuario;
        this.numero = numero;
        this.zip = zip;
        this.titular = titular;
    }

    public int getId_Tarjeta() {
        return id_Tarjeta;
    }

    public void setId_Tarjeta(int id_Tarjeta) {
        this.id_Tarjeta = id_Tarjeta;
    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
}
