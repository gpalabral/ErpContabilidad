/*,
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Administrador
 */
public enum EnumTieneCentroDeCosto {

    NO("N"),
    SI("S");
  
    private String codigo;

    private EnumTieneCentroDeCosto(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
