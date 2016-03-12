package com.bap.erp.modelo.enums;

public enum EnumParametrosGestion {

    SEGUNDA_MONEDA("SEGM"),
    USA_PROYECTOS("UPRY"),
    USA_CENTROS_COSTO("UCCO"),
    USA_AUXILIARES("UAUX"),
    TIPO_NORMA_AJUSTE("TNAJ"),
    AJUSTE_MISMA_CUENTA("AJMC"),
    MODO_PROCESO_AJUSTE("MPAJ"),
    USA_T_CODE("UTCO"),
    CTA_DEBITO_FISCAL("CDBF"),
    CTA_CREDITO_FISCAL("CCRF"),
    CTA_CREDITO_FISCAL_TRANSITORIO("CCFT"),
    CTA_CREDITO_FISCAL_NO_DEDUCIBLE("CCFD"),
    CTA_IMPUESTO_TRANSACCIONES_GASTO("CITG"),
    CTA_IMPUESTO_TRANSACCIONES_PASIVO("CITP"),
    CTA_DEBITO_FISCAL_TRANSITORIO("CDFT"),
    CTA_RETENCION_SERVICIO("CRSV"),
    CTA_RETENCION_BIENES("CRBI"),
    CTA_RETENCION_RC_IVA("CRCI"),
    CTA_RETENCION_IT("CRIT"),
    CTA_RETENCION_AL_EXTERIOR("CRAE"),
    AJUSTE_ACT_RESULTADO_POR_EXPOSICION_INFLACION("AREI"),
    AJUSTE_ACT_RESULTADO_POR_EXPOSICION_INFLACION_INGRESO("REII"),
    AJUSTE_ACT_RESULTADO_POR_EXPOSICION_INFLACION_EGRESO("REIE"),
    AJUSTE_ACT_POR_CORRECCION_MONETARIA("ACM3"),
    AJUSTE_ACT_DIFERENCIA_CAMBIO("DCA3"),
    AJUSTE_ACT_CAPITAL("ACAP"),
    AJUSTE_ACT_RESERVA_PATRIMONIAL("AJRP"),
    AJUSTE_ANT_INFLACION_TENENCIA_BIENES("AITB"),
    AJUSTE_ANT_CORRECCION_MONETARIA("ACMA"),
    AJUSTE_ANT_DIFERENCIA_CAMBIO_ANTERIOR("DCAA"),
    AJUSTE_ANT_GLOBAL_PATRIMONIO("AGAP");

    
    private String codigo;

    private EnumParametrosGestion(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}