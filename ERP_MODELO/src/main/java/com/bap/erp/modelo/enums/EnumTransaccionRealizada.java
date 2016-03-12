/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Jonas
 */
public enum EnumTransaccionRealizada {
    FACTURA_COMPRA("COMP"),
    FACTURA_VENTA("VENT"),
    CREDITO_FISCAL_NO_DEDUCIBLE("CFND"),
    SIN_FACTURA("SINF"),
    RETENCIONES("RETE"),
    RETENCION_BIENES("REBI"),
    RETENCION_SERVICIOS("RESE"),
    RETENCION_RC_IVA("RERC"),
    RETENCION_ALQUILERES("REAL"),
    RETENCION_REMESAS_AL_EXTERIOR("RERE"),
    RETENCION_IUE_IT_IND_EXPORTADOR("REIU"),
    GROSSING("GROS"),
    GROSSING_BIENES("GRBI"),
    GROSSING_SERVICIOS("GRSE"),
    GROSSING_RC_IVA("GRRC"),
    GROSSING_ALQUILERES("GRAL"),
    GROSSING_REMESAS_AL_EXTERIOR("GRRE"),
    GROSSING_IUE_IT_IND_EXPORTADOR("GRIU"),
//    Adicionado Javier Chavez
    CREDITO_FISCAL_TRANSITORIO("CFT"),
    REGULARIZADO("REG"),
    PROCESO("PRC"),
//    fin adicion
    NINGUNO("NING");

    private String codigo;
    
    private EnumTransaccionRealizada(String codigo){
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
