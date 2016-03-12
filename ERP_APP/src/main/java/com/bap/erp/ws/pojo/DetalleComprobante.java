package com.bap.erp.ws.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class DetalleComprobante{

//    private static final long serialVersionUID = Erp.serialVersionIdErp;

    private Long nroComprobante;
    private String tipoComprobante;
    private String periodo;
    private String nroCuenta;
    private Date fecha;
    private BigDecimal tpoCambio;
    private BigDecimal debeBs;
    private BigDecimal haberBs;
    private BigDecimal debeSus;
    private BigDecimal HaberSus;
    private String glosaDatalle;
    private Long tipoRegistro;
    private String nroCheque;
    private String glosaComprobante;
    private Long ctregi;
    // datos para facturas
    private BigDecimal tipo;
    private Date fechafactura;
    private String razon;
    private Long nrofactura;
    private Long nit;
    private BigDecimal monto;
    private String nroCpbte;
    private String norden;
    private BigDecimal ice;
    private BigDecimal exento;
    private BigDecimal iva;
    private String codSubcuenta;
    private String nopoliza;
    private String codcontrol;
    private BigDecimal neto;
    private BigDecimal gasto;
    private String valida;

    public DetalleComprobante() {

    }

    public Long getNroComprobante() {
        return nroComprobante;
    }

    public void setNroComprobante(Long nroComprobante) {
        this.nroComprobante = nroComprobante;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }


    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTpoCambio() {
        return tpoCambio;
    }

    public void setTpoCambio(BigDecimal tpoCambio) {
        this.tpoCambio = tpoCambio;
    }

    public BigDecimal getDebeBs() {
        return debeBs;
    }

    public void setDebeBs(BigDecimal debeBs) {
        this.debeBs = debeBs;
    }

    public BigDecimal getHaberBs() {
        return haberBs;
    }

    public void setHaberBs(BigDecimal haberBs) {
        this.haberBs = haberBs;
    }

    public BigDecimal getDebeSus() {
        return debeSus;
    }

    public void setDebeSus(BigDecimal debeSus) {
        this.debeSus = debeSus;
    }

    public BigDecimal getHaberSus() {
        return HaberSus;
    }

    public void setHaberSus(BigDecimal HaberSus) {
        this.HaberSus = HaberSus;
    }

    public String getGlosaDatalle() {
        return glosaDatalle;
    }

    public void setGlosaDatalle(String glosaDatalle) {
        this.glosaDatalle = glosaDatalle;
    }

    public Long getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(Long tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getNroCheque() {
        return nroCheque;
    }

    public void setNroCheque(String nroCheque) {
        this.nroCheque = nroCheque;
    }

    public String getGlosaComprobante() {
        return glosaComprobante;
    }

    public void setGlosaComprobante(String glosaComprobante) {
        this.glosaComprobante = glosaComprobante;
    }

    public Long getCtregi() {
        return ctregi;
    }

    public void setCtregi(Long ctregi) {
        this.ctregi = ctregi;
    }

    public BigDecimal getTipo() {
        return tipo;
    }

    public void setTipo(BigDecimal tipo) {
        this.tipo = tipo;
    }

    public Date getFechafactura() {
        return fechafactura;
    }

    public void setFechafactura(Date fechafactura) {
        this.fechafactura = fechafactura;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public Long getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(Long nrofactura) {
        this.nrofactura = nrofactura;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getNroCpbte() {
        return nroCpbte;
    }

    public void setNroCpbte(String nroCpbte) {
        this.nroCpbte = nroCpbte;
    }

    public String getNorden() {
        return norden;
    }

    public void setNorden(String norden) {
        this.norden = norden;
    }

    public BigDecimal getIce() {
        return ice;
    }

    public void setIce(BigDecimal ice) {
        this.ice = ice;
    }

    public BigDecimal getExento() {
        return exento;
    }

    public void setExento(BigDecimal exento) {
        this.exento = exento;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public String getCodSubcuenta() {
        return codSubcuenta;
    }

    public void setCodSubcuenta(String codSubcuenta) {
        this.codSubcuenta = codSubcuenta;
    }

    public String getNopoliza() {
        return nopoliza;
    }

    public void setNopoliza(String nopoliza) {
        this.nopoliza = nopoliza;
    }

    public String getCodcontrol() {
        return codcontrol;
    }

    public void setCodcontrol(String codcontrol) {
        this.codcontrol = codcontrol;
    }

    public BigDecimal getNeto() {
        return neto;
    }

    public void setNeto(BigDecimal neto) {
        this.neto = neto;
    }

    public BigDecimal getGasto() {
        return gasto;
    }

    public void setGasto(BigDecimal gasto) {
        this.gasto = gasto;
    }

    public String getValida() {
        return valida;
    }

    public void setValida(String valida) {
        this.valida = valida;
    }
}
