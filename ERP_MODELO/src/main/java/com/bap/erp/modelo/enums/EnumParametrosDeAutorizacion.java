
package com.bap.erp.modelo.enums;


public enum EnumParametrosDeAutorizacion {

    FACTURA("FACT"),
    ALQUILER("ALQU"),
    BSP("BSP"),
    POLIZA_DE_IMPORTACION("POLI");
    
    private String codigo;

    private EnumParametrosDeAutorizacion(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
