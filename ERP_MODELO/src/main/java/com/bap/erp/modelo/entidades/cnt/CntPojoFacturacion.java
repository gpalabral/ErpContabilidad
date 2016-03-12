package com.bap.erp.modelo.entidades.cnt;

import java.math.BigDecimal;

/**
 *
 * @author Renan
 */
public class CntPojoFacturacion {
    private CntFacturacion cntFacturacion;
    private BigDecimal totalMonto;
    private BigDecimal totalIce;
    private BigDecimal totalExcento;
    private BigDecimal totalDescuento;
    private BigDecimal totalImporteNeto;
    private BigDecimal totalIva;

    public CntFacturacion getCntFacturacion() {
        return cntFacturacion;
    }

    public void setCntFacturacion(CntFacturacion cntFacturacion) {
        this.cntFacturacion = cntFacturacion;
    }

    public BigDecimal getTotalMonto() {
        return totalMonto;
    }

    public void setTotalMonto(BigDecimal totalMonto) {
        this.totalMonto = totalMonto;
    }

    public BigDecimal getTotalIce() {
        return totalIce;
    }

    public void setTotalIce(BigDecimal totalIce) {
        this.totalIce = totalIce;
    }

    public BigDecimal getTotalExcento() {
        return totalExcento;
    }

    public void setTotalExcento(BigDecimal totalExcento) {
        this.totalExcento = totalExcento;
    }

    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public BigDecimal getTotalImporteNeto() {
        return totalImporteNeto;
    }

    public void setTotalImporteNeto(BigDecimal totalImporteNeto) {
        this.totalImporteNeto = totalImporteNeto;
    }

    public BigDecimal getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
    }

   
    
}
