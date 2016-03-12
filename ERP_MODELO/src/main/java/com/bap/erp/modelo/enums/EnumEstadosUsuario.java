package com.bap.erp.modelo.enums;

public enum EnumEstadosUsuario {
    VIGENTE("VIG"),
    NO_VIGENTE("NVI"),
    AUTORIZADO("AUT"),
    PENDIENTE("PEN"),
    BLOQUEADO("BLQ"),
    EN_PROCESO_DESBLOQUEO("EPD");
    
    private String estado;
    
    private EnumEstadosUsuario(String estado)
    {   this.estado=estado;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }
    
}
