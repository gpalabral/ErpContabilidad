
package com.bap.erp.modelo.enums;


public enum EnumDatosEmpresa {

    RAZON_SOCIAL("RSOC"),
    NUMERO_NIT("NNIT"),
    CORREO_ELECTRONICO("CORR");
    
    private String codigo;

    private EnumDatosEmpresa(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
