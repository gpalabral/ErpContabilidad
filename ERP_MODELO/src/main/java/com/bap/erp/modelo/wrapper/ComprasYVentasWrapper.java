package com.bap.erp.modelo.wrapper;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;

public class ComprasYVentasWrapper {

    private Float porcentajeIVA ;
    private Float porcentajeIT;
    private Boolean obligacionPagoIT;
    private CntEntidad debitoFiscal;
    private CntEntidad creditoFiscal;
    private CntEntidad creditoFiscalNoDeducible;
    private CntEntidad creditoFiscalTransitorio;
    private CntEntidad impuestoTransaccion;
    private CntEntidad gastoImpuestoTransaccion;
    private CntEntidad debitoFiscalTransitorio;

    public ComprasYVentasWrapper() {
        this.porcentajeIVA = new Float("0.0");
        this.porcentajeIT = new Float("0.0");
        this.obligacionPagoIT = false;
        this.debitoFiscal = new CntEntidad();
        this.creditoFiscal = new CntEntidad();
        this.creditoFiscalNoDeducible = new CntEntidad();
        this.creditoFiscalTransitorio = new CntEntidad();
        this.impuestoTransaccion = new CntEntidad();
        this.gastoImpuestoTransaccion = new CntEntidad();
        this.debitoFiscalTransitorio = new CntEntidad();
    }

    public Float getPorcentajeIT() {
        return porcentajeIT;
    }

    public void setPorcentajeIT(Float porcentajeIT) {
        this.porcentajeIT = porcentajeIT;
    }

    public Float getPorcentajeIVA() {
        return porcentajeIVA;
    }

    public void setPorcentajeIVA(Float porcentajeIVA) {
        this.porcentajeIVA = porcentajeIVA;
    }

    public CntEntidad getCreditoFiscal() {
        return creditoFiscal;
    }

    public void setCreditoFiscal(CntEntidad creditoFiscal) {
        this.creditoFiscal = creditoFiscal;
    }

    public CntEntidad getCreditoFiscalNoDeducible() {
        return creditoFiscalNoDeducible;
    }

    public void setCreditoFiscalNoDeducible(CntEntidad creditoFiscalNoDeducible) {
        this.creditoFiscalNoDeducible = creditoFiscalNoDeducible;
    }

    public CntEntidad getCreditoFiscalTransitorio() {
        return creditoFiscalTransitorio;
    }

    public void setCreditoFiscalTransitorio(CntEntidad creditoFiscalTransitorio) {
        this.creditoFiscalTransitorio = creditoFiscalTransitorio;
    }

    public CntEntidad getDebitoFiscal() {
        return debitoFiscal;
    }

    public void setDebitoFiscal(CntEntidad debitoFiscal) {
        this.debitoFiscal = debitoFiscal;
    }

    public CntEntidad getDebitoFiscalTransitorio() {
        return debitoFiscalTransitorio;
    }

    public void setDebitoFiscalTransitorio(CntEntidad debitoFiscalTransitorio) {
        this.debitoFiscalTransitorio = debitoFiscalTransitorio;
    }

    public CntEntidad getGastoImpuestoTransaccion() {
        return gastoImpuestoTransaccion;
    }

    public void setGastoImpuestoTransaccion(CntEntidad gastoImpuestoTransaccion) {
        this.gastoImpuestoTransaccion = gastoImpuestoTransaccion;
    }

    public CntEntidad getImpuestoTransaccion() {
        return impuestoTransaccion;
    }

    public void setImpuestoTransaccion(CntEntidad impuestoTransaccion) {
        this.impuestoTransaccion = impuestoTransaccion;
    }

    public Boolean getObligacionPagoIT() {
        return obligacionPagoIT;
    }

    public void setObligacionPagoIT(Boolean obligacionPagoIT) {
        this.obligacionPagoIT = obligacionPagoIT;
    }
}
