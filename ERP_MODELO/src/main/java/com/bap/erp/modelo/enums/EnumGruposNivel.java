/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Administrador
 */
public enum EnumGruposNivel {

    PROYECTOS("PROY"),
    CENTROS_COSTOS("CCOS"),
    PLAN_CUENTAS("PCTA");
    private String codigo;

    private EnumGruposNivel(String codigo) {
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
