package com.bap.erp.modelo.enums;

public enum EnumTipoUsuario {
    ADMINISTRADOR("ADM"),
    CONTADOR("CON");
    
     private String codigo;

    private EnumTipoUsuario(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
}
