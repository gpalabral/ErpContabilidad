package com.bap.erp.modelo.wrapper;

public class VariosWrapper {

    private Boolean ejecutaPresupuestoFiscal;
    private Boolean centroDeCostos;
    private Boolean cuentasCoorporativas;
    private Integer longitud;
    private Boolean nuevoSistemaFacturacion;
    private Boolean facturacionComputarizada;
    private Boolean codigoControl;
    private Boolean proyectos;
    private Boolean auxiliares;

    public VariosWrapper() {
        this.ejecutaPresupuestoFiscal = false;
        this.centroDeCostos = false;
        this.cuentasCoorporativas = false;
        this.longitud = 0;
        this.nuevoSistemaFacturacion = false;
        this.facturacionComputarizada = false;
        this.codigoControl = false;
        this.proyectos = false;
        this.auxiliares = false;
    }

    public Boolean getCentroDeCostos() {
        return centroDeCostos;
    }

    public void setCentroDeCostos(Boolean centroDeCostos) {
        this.centroDeCostos = centroDeCostos;
    }

    public Boolean getCodigoControl() {
        return codigoControl;
    }

    public void setCodigoControl(Boolean codigoControl) {
        this.codigoControl = codigoControl;
    }

    public Boolean getCuentasCoorporativas() {
        return cuentasCoorporativas;
    }

    public void setCuentasCoorporativas(Boolean cuentasCoorporativas) {
        this.cuentasCoorporativas = cuentasCoorporativas;
    }

    public Boolean getEjecutaPresupuestoFiscal() {
        return ejecutaPresupuestoFiscal;
    }

    public void setEjecutaPresupuestoFiscal(Boolean ejecutaPresupuestoFiscal) {
        this.ejecutaPresupuestoFiscal = ejecutaPresupuestoFiscal;
    }

    public Boolean getFacturacionComputarizada() {
        return facturacionComputarizada;
    }

    public void setFacturacionComputarizada(Boolean facturacionComputarizada) {
        this.facturacionComputarizada = facturacionComputarizada;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public Boolean getNuevoSistemaFacturacion() {
        return nuevoSistemaFacturacion;
    }

    public void setNuevoSistemaFacturacion(Boolean nuevoSistemaFacturacion) {
        this.nuevoSistemaFacturacion = nuevoSistemaFacturacion;
    }

    public Boolean getAuxiliares() {
        return auxiliares;
    }

    public void setAuxiliares(Boolean auxiliares) {
        this.auxiliares = auxiliares;
    }

    public Boolean getProyectos() {
        return proyectos;
    }

    public void setProyectos(Boolean proyectos) {
        this.proyectos = proyectos;
    }
}
