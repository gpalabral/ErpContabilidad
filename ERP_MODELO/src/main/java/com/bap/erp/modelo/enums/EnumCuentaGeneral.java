/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Miriam
 */
public enum EnumCuentaGeneral {
    ACTIVO("ACT"),
    PASIVO("PAS"),
    PATRIMONIO("PAT"),
    INGRESO("ING"),
    EGRESO("EGR");       
    private String codigo;
    
    private EnumCuentaGeneral(String codigo){
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
