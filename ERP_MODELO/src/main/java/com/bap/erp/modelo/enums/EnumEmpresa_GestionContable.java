package com.bap.erp.modelo.enums;

public enum EnumEmpresa_GestionContable {

    CIUDAD("CIUD"),
    INICIO_GESTIÓN_FISCAL("IGFI"),
    PERIODO_ACTUAL("PACT"),
    TIPO_DE_CAMBIO_INICIAL("TCOF"),
    DEFINICION_DE_CUENTAS("DCTA"),
    NORMA_CONTABLE_3("NCON"),
    AJUSTES_REI_SOBRE_LA_MISMA_CUENTA("ARSC"),
    MODO_DE_PROCESO_DE_AJUSTES("MPDA"),
    TIPO_DE_MONEDA("TMON"),
    FECHA_INICIAL_REGISTRO_COMPROBANTE("FIRC"),
    FECHA_FINAL_REGISTRO_COMPROBANTE("FFRC"),
    ANIO_ACTUAL("AACT");
    
    private String codigo;

    private EnumEmpresa_GestionContable(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
