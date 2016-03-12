package com.bap.erp.modelo.entidades.cnf;


import java.io.Serializable;

public class ParCuentasGeneralesNivel implements Serializable {

    private ParCuentasGenerales cuentasGenerales;
    private int nivel;

    public ParCuentasGeneralesNivel() {
    }

    public ParCuentasGenerales getCuentasGenerales() {
        return cuentasGenerales;
    }

    public void setCuentasGenerales(ParCuentasGenerales cuentasGenerales) {
        this.cuentasGenerales = cuentasGenerales;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
