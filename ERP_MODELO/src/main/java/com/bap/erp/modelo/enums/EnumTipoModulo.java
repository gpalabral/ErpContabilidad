/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Administrador
 */
public enum EnumTipoModulo {

    CONTABILIDAD("CONT"),
    CUENTAS_POR_PAGAR("CPP"),
    CUENTAS_POR_COBRAR("CPC");
  
    
    private String codigo;

    private EnumTipoModulo(String codigo) {
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
