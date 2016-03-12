package com.bap.erp.modelo.enums;

public enum EnumParametrosDeGestion_ComprasAndVentas {

    PORCENTAJE_IVA("IVA"),
    PORCENTAJE_IT("IT"),
    OBLIGACIÓN_DE_PAGO_IT("OPIT"),
    CUENTA_DE_DEBITO_FISCAL("CDBF"),
    CUENTA_DE_CREDITO_FISCAL("CCRF"),
    CUENTA_DE_CREDITO_FISCAL_NO_DEDUCIBLE("CCFD"),
    CUENTA_DE_CREDITO_FISCAL_TRANSITORIO("CCFT"),
    CUENTA_IMPUESTOS_A_LAS_TRANSACCIONES("CITP"),
    CUENTA_GASTO_IMPUESTOS_A_LAS_TRANSACCIONES("CITG"),
    CUENTA_DEBITO_FISCAL_TRANSITORIO("CDFT");
    private String codigo;

    private EnumParametrosDeGestion_ComprasAndVentas(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
