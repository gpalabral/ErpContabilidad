/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Miriam
 */
public enum EnumTipoConfiguracionDeCentroDeCosto {
    DEFINICION("DEFI"),
    LIBRE("LIBR"),
    ALTERNATIVA("ALTR");       
    
    private String codigo;
    
    private EnumTipoConfiguracionDeCentroDeCosto(String codigo){
        this.codigo = codigo;
    }
    
    
    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
