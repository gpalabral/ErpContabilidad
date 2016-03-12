package com.bap.erp.modelo.pojo;


public class Entidad{    
    
    private Long idEntidad;
    private Long idEntidadPadre;
    private String descripcion;
    private String mascaraGenerada;
    private int nivel;

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public Long getIdEntidadPadre() {
        return idEntidadPadre;
    }

    public void setIdEntidadPadre(Long idEntidadPadre) {
        this.idEntidadPadre = idEntidadPadre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMascaraGenerada() {
        return mascaraGenerada;
    }

    public void setMascaraGenerada(String mascaraGenerada) {
        this.mascaraGenerada = mascaraGenerada;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    
}
