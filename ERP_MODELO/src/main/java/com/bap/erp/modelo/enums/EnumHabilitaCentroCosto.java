
package com.bap.erp.modelo.enums;

public enum EnumHabilitaCentroCosto {

    NO("N"),
    SI("S");
  
    private String codigo;

    private EnumHabilitaCentroCosto(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
