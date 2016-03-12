package com.bap.erp.modelo.pojo;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico;

public class CntEntidadPojo implements Cloneable{

    private CntEntidad cntEntidad;
    private CntParametroAutomatico cntParametroAutomatico;

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    public CntParametroAutomatico getCntParametroAutomatico() {
        return cntParametroAutomatico;
    }

    public void setCntParametroAutomatico(CntParametroAutomatico cntParametroAutomatico) {
        this.cntParametroAutomatico = cntParametroAutomatico;
    }

    @Override
    public Object clone() {
        Object obj = null;
        try {
            obj = super.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return obj;
    }
}
