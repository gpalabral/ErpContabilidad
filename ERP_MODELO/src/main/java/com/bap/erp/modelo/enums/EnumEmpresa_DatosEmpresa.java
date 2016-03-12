package com.bap.erp.modelo.enums;

public enum EnumEmpresa_DatosEmpresa {

    RAZON_SOCIAL("RSOC"),
    SUBTITULO("SUBT"),
    GESTION_CONTABLE("GEST"),
    NUMERO_DE_IDENTIFICACIÓN_TRIBUTARIA("NIT"),
    AUTORIZACION("AUTO"),
    DIRECCIÓN("DIR");
    private String codigo;

    private EnumEmpresa_DatosEmpresa(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
