/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.pojo;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import java.math.BigDecimal;

/**
 *
 * @author JONATHAN
 */
public class PojoCntEntidadBGyEERR implements Cloneable {

    CntEntidad idEntidad;
    BigDecimal montoMonedaUno;
    BigDecimal montoMonedaDos;
    String descripcion;
    BigDecimal nivel1bs = new BigDecimal(0.00);
    BigDecimal nivel2bs = new BigDecimal(0.00);
    BigDecimal nivel3bs = new BigDecimal(0.00);
    BigDecimal nivel4bs = new BigDecimal(0.00);
    BigDecimal nivel5bs = new BigDecimal(0.00);
    BigDecimal nivel6bs = new BigDecimal(0.00);
    BigDecimal nivel1sus = new BigDecimal(0.00);
    BigDecimal nivel2sus = new BigDecimal(0.00);
    BigDecimal nivel3sus = new BigDecimal(0.00);
    BigDecimal nivel4sus = new BigDecimal(0.00);
    BigDecimal nivel5sus = new BigDecimal(0.00);
    BigDecimal nivel6sus = new BigDecimal(0.00);
    int valorIndex; //para guardar el indice del listado
    
    public CntEntidad getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(CntEntidad idEntidad) {
        this.idEntidad = idEntidad;
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

    public BigDecimal getMontoMonedaUno() {
        return montoMonedaUno;
    }

    public void setMontoMonedaUno(BigDecimal montoMonedaUno) {
        this.montoMonedaUno = montoMonedaUno;
    }

    public BigDecimal getMontoMonedaDos() {
        return montoMonedaDos;
    }

    public void setMontoMonedaDos(BigDecimal montoMonedaDos) {
        this.montoMonedaDos = montoMonedaDos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getNivel1bs() {
        return nivel1bs;
    }

    public void setNivel1bs(BigDecimal nivel1bs) {
        this.nivel1bs = nivel1bs;
    }

    public BigDecimal getNivel2bs() {
        return nivel2bs;
    }

    public void setNivel2bs(BigDecimal nivel2bs) {
        this.nivel2bs = nivel2bs;
    }

    public BigDecimal getNivel3bs() {
        return nivel3bs;
    }

    public void setNivel3bs(BigDecimal nivel3bs) {
        this.nivel3bs = nivel3bs;
    }

    public BigDecimal getNivel4bs() {
        return nivel4bs;
    }

    public void setNivel4bs(BigDecimal nivel4bs) {
        this.nivel4bs = nivel4bs;
    }

    public BigDecimal getNivel5bs() {
        return nivel5bs;
    }

    public void setNivel5bs(BigDecimal nivel5bs) {
        this.nivel5bs = nivel5bs;
    }

    public BigDecimal getNivel6bs() {
        return nivel6bs;
    }

    public void setNivel6bs(BigDecimal nivel6bs) {
        this.nivel6bs = nivel6bs;
    }

    public BigDecimal getNivel1sus() {
        return nivel1sus;
    }

    public void setNivel1sus(BigDecimal nivel1sus) {
        this.nivel1sus = nivel1sus;
    }

    public BigDecimal getNivel2sus() {
        return nivel2sus;
    }

    public void setNivel2sus(BigDecimal nivel2sus) {
        this.nivel2sus = nivel2sus;
    }

    public BigDecimal getNivel3sus() {
        return nivel3sus;
    }

    public void setNivel3sus(BigDecimal nivel3sus) {
        this.nivel3sus = nivel3sus;
    }

    public BigDecimal getNivel4sus() {
        return nivel4sus;
    }

    public void setNivel4sus(BigDecimal nivel4sus) {
        this.nivel4sus = nivel4sus;
    }

    public BigDecimal getNivel5sus() {
        return nivel5sus;
    }

    public void setNivel5sus(BigDecimal nivel5sus) {
        this.nivel5sus = nivel5sus;
    }

    public BigDecimal getNivel6sus() {
        return nivel6sus;
    }

    public void setNivel6sus(BigDecimal nivel6sus) {
        this.nivel6sus = nivel6sus;
    }

    public int getValorIndex() {
        return valorIndex;
    }

    public void setValorIndex(int valorIndex) {
        this.valorIndex = valorIndex;
    }
}
