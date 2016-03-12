package com.bap.erp.modelo.enums;

public enum EnumParametroAutomatico {

    DEBE("DEB"),
    HABER("HAB"),
    AMBOS("AMB"),
    NINGUNO("NING");
    
    private String codigo;

    private EnumParametroAutomatico(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
