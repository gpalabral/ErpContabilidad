/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Jonas
 */
public enum EnumEstado {
    PENDIENTE("PEND"),
    CONFIRMADO("CONF"),
    MODIFICANDO("MODI"),
    COPIANDO("COPI"),
    PROCESO_ANULACION("PANU"),
    REVERTIDO("REVE"),
    ANULADO("ANUL"),
    QUITADO_CONFIRMADO("QUIC");
    private String codigo;
    
    private EnumEstado(String codigo){
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
