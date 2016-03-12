package com.bap.erp.modelo.enums;

public enum EnumEmpresa_Varios {

    	EJECUTA_PRESUPUESTO_FISCAL("EPF"),
	CENTROS_DE_COSTO("CCOS"),
	CUENTAS_COORPORATIVAS("CTAC"),
	LONGITUD("LONG"),
	NUEVO_SISTEMA_DE_FACTURACION("NSDF"),
	FACTURACIÓN_COMPUTARIZADA("FCOM"),
	CODIGO_DE_CONTROL("CCOD"),
        AUXILIAR("AUXI"),
        PROYECTO("PROY");
    private String codigo;

    private EnumEmpresa_Varios(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
