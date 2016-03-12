/*,
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Jonas
 */
public enum EnumContextosParaVistas {

    RECETA("RECETA");
  
    private String codigo;

    private EnumContextosParaVistas(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
