/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.enums;

/**
 *
 * @author Miriam
 */
public enum EnumAjuste {
    	SIN_AJUSTE("SAJU"),
	AJUSTE_RESULTADO_POR_EXPOSICION_INFLACION("AREI"),
	AJUSTE_INFLACION_Y_TENENCIA_DE_BIENES("AITB"),
	DIFERENCIA_DE_CAMBIO("DCAM"),
	CORRECCION_MONETARIA("ADCA"),
	AJUSTE_DE_CAPITAL("ACAP"),
	AJUSTE_GLOBAL_DEL_PATRIMONIO("AGPA"),
	AJUSTE_RESERVA_PATRIMONIAL("ARPA");
    
    private String codigo;
    
    private EnumAjuste(String codigo){
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
