package com.bap.erp.modelo.pojo;

import com.bap.erp.modelo.entidades.cnf.ParCuentasGenerales;

public class ParCuentasGeneralesPojo implements Cloneable{

    private ParCuentasGenerales parCuentasGenerales;
    private Boolean seleccionado;

    public ParCuentasGenerales getParCuentasGenerales() {
        return parCuentasGenerales;
    }

    public void setParCuentasGenerales(ParCuentasGenerales parCuentasGenerales) {
        this.parCuentasGenerales = parCuentasGenerales;
    }

    public Boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
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
