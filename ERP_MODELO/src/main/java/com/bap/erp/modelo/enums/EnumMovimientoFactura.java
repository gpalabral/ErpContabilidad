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
public enum EnumMovimientoFactura {

    FACTURA_COMPRA("COMP"),
    FACTURA_VENTA("VENT");
  
    private String codigo;

    private EnumMovimientoFactura(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
