package com.bap.erp.modelo.wrapper;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;

public class CuentasDeAjusteWrapper {
    private CntEntidad inflacionResulPorExposicionInflacion;
    private CntEntidad ajusteCorreccionMonetaria;
    private CntEntidad diferenciaCambio;
    private CntEntidad ajusteCapital;
    private CntEntidad ajusteReservasPatrimoniales;

    public CuentasDeAjusteWrapper() {
        this.inflacionResulPorExposicionInflacion = new CntEntidad();
        this.ajusteCorreccionMonetaria = new CntEntidad();
        this.diferenciaCambio = new CntEntidad();
        this.ajusteCapital = new CntEntidad();
        this.ajusteReservasPatrimoniales = new CntEntidad();
    }

    public CntEntidad getAjusteCapital() {
        return ajusteCapital;
    }

    public void setAjusteCapital(CntEntidad ajusteCapital) {
        this.ajusteCapital = ajusteCapital;
    }

    public CntEntidad getAjusteCorreccionMonetaria() {
        return ajusteCorreccionMonetaria;
    }

    public void setAjusteCorreccionMonetaria(CntEntidad ajusteCorreccionMonetaria) {
        this.ajusteCorreccionMonetaria = ajusteCorreccionMonetaria;
    }

    public CntEntidad getAjusteReservasPatrimoniales() {
        return ajusteReservasPatrimoniales;
    }

    public void setAjusteReservasPatrimoniales(CntEntidad ajusteReservasPatrimoniales) {
        this.ajusteReservasPatrimoniales = ajusteReservasPatrimoniales;
    }

    public CntEntidad getDiferenciaCambio() {
        return diferenciaCambio;
    }

    public void setDiferenciaCambio(CntEntidad diferenciaCambio) {
        this.diferenciaCambio = diferenciaCambio;
    }

    public CntEntidad getInflacionResulPorExposicionInflacion() {
        return inflacionResulPorExposicionInflacion;
    }

    public void setInflacionResulPorExposicionInflacion(CntEntidad inflacionResulPorExposicionInflacion) {
        this.inflacionResulPorExposicionInflacion = inflacionResulPorExposicionInflacion;
    }
}
