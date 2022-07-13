package com.example.ejercicio23.Procesos;

import java.io.Serializable;

public class Fotos implements Serializable {
    private Integer id;
    private String descripcion;
    private String pathImage;
    private byte[] imagen;


    public Fotos()
    {
        // Constructor Vacio
    }

    public Fotos(Integer id, String descripcion, String pathImage, byte[] imagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.pathImage = pathImage;
        this.imagen = imagen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
