package com.bap.erp.ws.pojo;

import com.bap.erp.modelo.Erp;

public class Entidad{
    
    private static final long serialVersionUID = Erp.serialVersionIdErp;
    
    private Long idEntidad;
    private Long idEntidadPadre;
    private String descripcion;
    private String mascaraGenerada;
    private int nivel;

    public Entidad(Long idEntidad, Long idEntidadPadre, String descripcion, String mascaraGenerada,int nivel) {
        this.idEntidad = idEntidad;
        this.idEntidadPadre = idEntidadPadre;
        this.descripcion = descripcion;
        this.mascaraGenerada = mascaraGenerada;
        this.nivel=nivel;
    }
    
    

    /**
     * @return the idEntidad
     */
    public Long getIdEntidad() {
        return idEntidad;
    }

    /**
     * @param idEntidad the idEntidad to set
     */
    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    /**
     * @return the idEntidadPadre
     */
    public Long getIdEntidadPadre() {
        return idEntidadPadre;
    }

    /**
     * @param idEntidadPadre the idEntidadPadre to set
     */
    public void setIdEntidadPadre(Long idEntidadPadre) {
        this.idEntidadPadre = idEntidadPadre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the mascaraGenerada
     */
    public String getMascaraGenerada() {
        return mascaraGenerada;
    }

    /**
     * @param mascaraGenerada the mascaraGenerada to set
     */
    public void setMascaraGenerada(String mascaraGenerada) {
        this.mascaraGenerada = mascaraGenerada;
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    
    
}
