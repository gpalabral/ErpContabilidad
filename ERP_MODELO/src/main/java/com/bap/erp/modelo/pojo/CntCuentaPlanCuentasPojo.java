package com.bap.erp.modelo.pojo;

import java.io.Serializable;

public class CntCuentaPlanCuentasPojo implements Serializable{

    private Long idEntidad;
    private String descripcion;
    private String mascara;

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }
    
    
    
}
