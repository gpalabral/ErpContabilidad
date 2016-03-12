package com.bap.erp.modelo.enums;

/**
 *
 * @author Henrry
 */
public enum EnumIconosPrimeFaces {
    GUARDAR("ui-icon-disk"),
    MODIFICAR("ui-icon-pencil"),
    ELIMINAR("ui-icon-trash"),
    ATRAS("ui-icon-arrowthick-1-w"),
    IR_OTRA_VENTANA("ui-icon-check");
       
    private String codigo;
    
    private EnumIconosPrimeFaces(String codigo){
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
