package com.bap.erp.modelo.wrapper;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import java.util.Date;

public class GestionContableWrapper {

    private String ciudad;
    private Integer inicioGestionFiscal;
    private Integer periodoActual;
    private Float tipoCambioInicial;
    private CntEntidad definicionCuentas;
    private String normaContable3;
    private Boolean ajustesREI;
    private String modoProcesosAjustes;
    private String tipoMoneda;
    private Date fechaLimiteInicial;
    private Date fechaLimiteFinal;
    private Integer anioActual;

    public GestionContableWrapper() {
        this.ciudad = "";
        this.inicioGestionFiscal = 0;
        this.periodoActual = null;
        this.tipoCambioInicial = new Float("0.0");
        this.definicionCuentas = new CntEntidad();
        this.normaContable3 = "";
        this.ajustesREI = false;
        this.modoProcesosAjustes = "";
        this.tipoMoneda = "";
        this.anioActual = 2015;
    }

    public Boolean getAjustesREI() {
        return ajustesREI;
    }

    public void setAjustesREI(Boolean ajustesREI) {
        this.ajustesREI = ajustesREI;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public CntEntidad getDefinicionCuentas() {
        return definicionCuentas;
    }

    public void setDefinicionCuentas(CntEntidad definicionCuentas) {
        this.definicionCuentas = definicionCuentas;
    }

    public Integer getInicioGestionFiscal() {
        return inicioGestionFiscal;
    }

    public void setInicioGestionFiscal(Integer inicioGestionFiscal) {
        this.inicioGestionFiscal = inicioGestionFiscal;
    }

    public String getModoProcesosAjustes() {
        return modoProcesosAjustes;
    }

    public void setModoProcesosAjustes(String modoProcesosAjustes) {
        this.modoProcesosAjustes = modoProcesosAjustes;
    }

    public String getNormaContable3() {
        return normaContable3;
    }

    public void setNormaContable3(String normaContable3) {
        this.normaContable3 = normaContable3;
    }

    public Integer getPeriodoActual() {
        return periodoActual;
    }

    public void setPeriodoActual(Integer periodoActual) {
        this.periodoActual = periodoActual;
    }
   
    public Float getTipoCambioInicial() {
        return tipoCambioInicial;
    }

    public void setTipoCambioInicial(Float tipoCambioInicial) {
        this.tipoCambioInicial = tipoCambioInicial;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public Date getFechaLimiteInicial() {
        return fechaLimiteInicial;
    }

    public void setFechaLimiteInicial(Date fechaLimiteInicial) {
        this.fechaLimiteInicial = fechaLimiteInicial;
    }

    public Date getFechaLimiteFinal() {
        return fechaLimiteFinal;
    }

    public void setFechaLimiteFinal(Date fechaLimiteFinal) {
        this.fechaLimiteFinal = fechaLimiteFinal;
    }

    public Integer getAnioActual() {
        return anioActual;
    }

    public void setAnioActual(Integer anioActual) {
        this.anioActual = anioActual;
    }
    
}
