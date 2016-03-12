package com.bap.erp.modelo.enums;

public enum EnumTipoMovimiento {
    DEBE("DEB"),//STXD
    HABER("HAB"),//STXH 
    AMBOS("AMB"),//TXDH
    NINGUNO("NIG");//TXNI
    private String codigo;
    
    private EnumTipoMovimiento(String codigo){
        this.codigo = codigo;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
