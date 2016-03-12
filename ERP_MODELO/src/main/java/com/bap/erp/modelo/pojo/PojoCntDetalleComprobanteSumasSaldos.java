/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.pojo;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import java.math.BigDecimal;

/**
 *
 * @author HENRRY
 */
public class PojoCntDetalleComprobanteSumasSaldos implements Cloneable {

    CntEntidad idEntidad;
    BigDecimal SumaDebe;
    BigDecimal SumaHaber;
    BigDecimal Deudor;
    BigDecimal Acreedor;
    BigDecimal SumaDebeDolar;
    BigDecimal SumaHaberDolar;
    BigDecimal DeudorDolar;
    BigDecimal AcreedorDolar;
    int valorIndex; //para guardar el indice del listado
    private String descripcion;

    public CntEntidad getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(CntEntidad idEntidad) {
        this.idEntidad = idEntidad;
    }

    public BigDecimal getSumaDebe() {
        return SumaDebe;
    }

    public void setSumaDebe(BigDecimal SumaDebe) {
        this.SumaDebe = SumaDebe;
    }

    public BigDecimal getSumaHaber() {
        return SumaHaber;
    }

    public void setSumaHaber(BigDecimal SumaHaber) {
        this.SumaHaber = SumaHaber;
    }

    public BigDecimal getDeudor() {
        return Deudor;
    }

    public void setDeudor(BigDecimal Deudor) {
        this.Deudor = Deudor;
    }

    public BigDecimal getAcreedor() {
        return Acreedor;
    }

    public void setAcreedor(BigDecimal Acreedor) {
        this.Acreedor = Acreedor;
    }

    public BigDecimal getSumaDebeDolar() {
        return SumaDebeDolar;
    }

    public void setSumaDebeDolar(BigDecimal SumaDebeDolar) {
        this.SumaDebeDolar = SumaDebeDolar;
    }

    public BigDecimal getSumaHaberDolar() {
        return SumaHaberDolar;
    }

    public void setSumaHaberDolar(BigDecimal SumaHaberDolar) {
        this.SumaHaberDolar = SumaHaberDolar;
    }

    public BigDecimal getDeudorDolar() {
        return DeudorDolar;
    }

    public void setDeudorDolar(BigDecimal DeudorDolar) {
        this.DeudorDolar = DeudorDolar;
    }

    public BigDecimal getAcreedorDolar() {
        return AcreedorDolar;
    }

    public void setAcreedorDolar(BigDecimal AcreedorDolar) {
        this.AcreedorDolar = AcreedorDolar;
    }

    public int getValorIndex() {
        return valorIndex;
    }

    public void setValorIndex(int valorIndex) {
        this.valorIndex = valorIndex;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
