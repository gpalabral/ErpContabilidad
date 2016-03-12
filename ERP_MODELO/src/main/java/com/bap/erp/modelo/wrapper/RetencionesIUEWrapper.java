package com.bap.erp.modelo.wrapper;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;

public class RetencionesIUEWrapper {
    
    private Float porcentajeRetencionesBienes;
    private Float porcentajeRetencionesServicios;
    private CntEntidad cuentaIUERetencionesServicios;
    private CntEntidad cuentaIUERetencionesBienes ;
    private CntEntidad cuentaITRetenciones;
    private CntEntidad cuentaRetencionesRCIVA;
    private CntEntidad cuentaRetencionesExterior;
    private Float retencionIUEITSectorIndustrialExporta;
    private CntEntidad cuentaRetencionIUEITSectorIndustrial;
    
    public RetencionesIUEWrapper() {
        this.porcentajeRetencionesBienes = new Float("0.0");
        this.porcentajeRetencionesServicios = new Float("0.0");
        this.cuentaIUERetencionesServicios = new CntEntidad();
        this.cuentaIUERetencionesBienes = new CntEntidad();
        this.cuentaITRetenciones = new CntEntidad();
        this.cuentaRetencionesRCIVA = new CntEntidad();
        this.cuentaRetencionesExterior = new CntEntidad();
        this.retencionIUEITSectorIndustrialExporta = new Float("0.0");
        this.cuentaRetencionIUEITSectorIndustrial = new CntEntidad();
    }
    
    public CntEntidad getCuentaITRetenciones() {
        return cuentaITRetenciones;
    }
    
    public void setCuentaITRetenciones(CntEntidad cuentaITRetenciones) {
        this.cuentaITRetenciones = cuentaITRetenciones;
    }
    
    public CntEntidad getCuentaIUERetencionesBienes() {
        return cuentaIUERetencionesBienes;
    }
    
    public void setCuentaIUERetencionesBienes(CntEntidad cuentaIUERetencionesBienes) {
        this.cuentaIUERetencionesBienes = cuentaIUERetencionesBienes;
    }
    
    public CntEntidad getCuentaIUERetencionesServicios() {
        return cuentaIUERetencionesServicios;
    }
    
    public void setCuentaIUERetencionesServicios(CntEntidad cuentaIUERetencionesServicios) {
        this.cuentaIUERetencionesServicios = cuentaIUERetencionesServicios;
    }
    
    public CntEntidad getCuentaRetencionIUEITSectorIndustrial() {
        return cuentaRetencionIUEITSectorIndustrial;
    }
    
    public void setCuentaRetencionIUEITSectorIndustrial(CntEntidad cuentaRetencionIUEITSectorIndustrial) {
        this.cuentaRetencionIUEITSectorIndustrial = cuentaRetencionIUEITSectorIndustrial;
    }
    
    public CntEntidad getCuentaRetencionesExterior() {
        return cuentaRetencionesExterior;
    }
    
    public void setCuentaRetencionesExterior(CntEntidad cuentaRetencionesExterior) {
        this.cuentaRetencionesExterior = cuentaRetencionesExterior;
    }
    
    public CntEntidad getCuentaRetencionesRCIVA() {
        return cuentaRetencionesRCIVA;
    }
    
    public void setCuentaRetencionesRCIVA(CntEntidad cuentaRetencionesRCIVA) {
        this.cuentaRetencionesRCIVA = cuentaRetencionesRCIVA;
    }
    
    public Float getPorcentajeRetencionesBienes() {
        return porcentajeRetencionesBienes;
    }
    
    public void setPorcentajeRetencionesBienes(Float porcentajeRetencionesBienes) {
        this.porcentajeRetencionesBienes = porcentajeRetencionesBienes;
    }
    
    public Float getPorcentajeRetencionesServicios() {
        return porcentajeRetencionesServicios;
    }
    
    public void setPorcentajeRetencionesServicios(Float porcentajeRetencionesServicios) {
        this.porcentajeRetencionesServicios = porcentajeRetencionesServicios;
    }
    
    public Float getRetencionIUEITSectorIndustrialExporta() {
        return retencionIUEITSectorIndustrialExporta;
    }
    
    public void setRetencionIUEITSectorIndustrialExporta(Float retencionIUEITSectorIndustrialExporta) {
        this.retencionIUEITSectorIndustrialExporta = retencionIUEITSectorIndustrialExporta;
    }
}
