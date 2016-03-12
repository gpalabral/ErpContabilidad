package com.bap.erp.modelo.enums;

public enum EnumParametrosDeGestion_RetencionesIU {

    	PORCENTAJE_DE_RETENCION_EN_BIENES("CRBI"),
	PORCENTAJE_DE_RETENCION_EN_SERVICIOS("CRSV"),
	CUENTA_IUE_RETENCIONES_POR_SERVICIOS("CTRS"),
	CUENTA_IUE_RETENCIONES_POR_BIENES("CTRB"),
	CUENTA_DE_IT_DE_RETENCIONES("CRIT"),
	CUENTA_RETENCION_RC_IVA("CRCI"),
	CUENTA_RETENCIONES_AL_EXTERIOR("CRAE"),
	RETENCION_IUE_IT_SECTOR_INDUSTRIAL_EXPORTA("RSIE"),
	CUENTA_RETENCION_IUE_IT_SECTOR_INDUSTRIAL("CRSI");
    	
    private String codigo;

    private EnumParametrosDeGestion_RetencionesIU(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
