/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Jonas
 */
public enum EnumTipoCalculoAutomatico {
    COMPRA("COMP"),
    VENTA("VENT"),
    NINGUNO("NING");
    
    private String codigo;
    
    private EnumTipoCalculoAutomatico(String codigo){
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
