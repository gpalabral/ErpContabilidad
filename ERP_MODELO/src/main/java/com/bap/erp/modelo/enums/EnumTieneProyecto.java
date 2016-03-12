
package com.bap.erp.modelo.enums;

public enum EnumTieneProyecto {

    NO("N"),
    SI("S");
  
    private String codigo;

    private EnumTieneProyecto(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
