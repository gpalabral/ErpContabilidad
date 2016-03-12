
package com.bap.erp.modelo.enums;

public enum EnumTieneAuxiliar {

    NO("N"),
    SI("S");
  
    private String codigo;

    private EnumTieneAuxiliar(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
