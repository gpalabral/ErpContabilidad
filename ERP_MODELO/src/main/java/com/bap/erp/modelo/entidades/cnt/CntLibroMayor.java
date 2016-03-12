/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CNT_LIBRO_MAYOR", schema = "CNT")
public class CntLibroMayor implements Serializable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @JoinColumn(name = "id_detalle_comprobante", referencedColumnName = "id_detalle_comprobante")
    @ManyToOne(optional = false)
    private CntDetalleComprobante idDetalleComprobante;

//    @Id
//    @Column(name = "id_detalle_comprobante", nullable = false)
//    private long idDetalleComprobante;
    @Basic(optional = false)
    @Column(name = "Id_entidad")
    private long identidad;
    @Basic(optional = false)
    @Column(name = "mascara_generada")
    private String mascaraGenerada;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "GLOSA")
    private String glosa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "debe")
    private BigDecimal debe;
    @Column(name = "haber")
    private BigDecimal haber;
    @Basic(optional = false)
    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "saldo_dolar")
    private BigDecimal saldoDolar; // aumentado para mostrar saldos en dolar Jacqueline 26/05/2015

    @Column(name = "numero")
    private String numero;

    @Column(name = "tipo_comprobante")
    private String tipoComprobante;
///AUMENTADO PARA MOSTRAR EN REPORTE MAYORES debe y haber en dolar mas num cheque 02/12/2014
    @Column(name = "debe_dolar")
    private BigDecimal debeDolar;
    @Column(name = "haber_dolar")
    private BigDecimal haberDolar;
    @Column(name = "numero_cheque")
    private String numeroCheque;
    @Column(name = "tipo_cambio")
    private BigDecimal tipoCambio;

    public CntLibroMayor() {
    }

    public CntDetalleComprobante getIdDetalleComprobante() {
        return idDetalleComprobante;
    }

    public void setIdDetalleComprobante(CntDetalleComprobante idDetalleComprobante) {
        this.idDetalleComprobante = idDetalleComprobante;
    }

    public long getIdentidad() {
        return identidad;
    }

    public void setIdentidad(long identidad) {
        this.identidad = identidad;
    }

    public String getMascaraGenerada() {
        return mascaraGenerada;
    }

    public void setMascaraGenerada(String mascaraGenerada) {
        this.mascaraGenerada = mascaraGenerada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the tipoComprobante
     */
    public String getTipoComprobante() {
        return tipoComprobante;
    }

    /**
     * @param tipoComprobante the tipoComprobante to set
     */
    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public BigDecimal getDebeDolar() {
        return debeDolar;
    }

    public void setDebeDolar(BigDecimal debeDolar) {
        this.debeDolar = debeDolar;
    }

    public BigDecimal getHaberDolar() {
        return haberDolar;
    }

    public void setHaberDolar(BigDecimal haberDolar) {
        this.haberDolar = haberDolar;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public BigDecimal getSaldoDolar() {
        return saldoDolar;
    }

    public void setSaldoDolar(BigDecimal saldoDolar) {
        this.saldoDolar = saldoDolar;
    }
}
