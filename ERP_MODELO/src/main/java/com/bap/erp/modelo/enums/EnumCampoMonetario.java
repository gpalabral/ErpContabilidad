/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Administrador
 */
public enum EnumCampoMonetario {

    DEBE("debe"),
    HABER("haber"),
    DEBE_DOLAR("debeDolar"),    
    HABER_DOLAR("haberDolar"),
    DEUDOR("deudor"),
    ACREEDOR("acreedor"),
    DEUDOR_DOLAR("deudorDolar"),    
    ACREEDOR_DOLAR("acreedorDolar");
    
    private String codigo;

    private EnumCampoMonetario(String codigo) {
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
