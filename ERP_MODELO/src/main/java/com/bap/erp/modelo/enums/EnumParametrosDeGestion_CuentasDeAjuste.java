package com.bap.erp.modelo.enums;

public enum EnumParametrosDeGestion_CuentasDeAjuste {

    	INFLACION_RESUL_P_EXPOSICION_A_LA_INFLACION("AREI"),
	AJUSTE_POR_CORRECCION_MONETARIA("ACM3"),
	DIFERENCIA_DE_CAMBIO("DCA3"),
	AJUSTE_DE_CAPITAL("ACAP"),
	AJUSTE_DE_RESERVAS_PATRIMONIALES("AJRP");
    
    private String codigo;

    private EnumParametrosDeGestion_CuentasDeAjuste(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
