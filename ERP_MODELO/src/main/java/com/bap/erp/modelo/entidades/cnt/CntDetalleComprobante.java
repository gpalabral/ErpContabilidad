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
@Table(name = "CNT_DETALLE_COMPROBANTE", schema = "CNT")
public class CntDetalleComprobante implements Serializable, Cloneable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_comprobante", nullable = false)
    private Long idDetalleComprobante;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation    
    @Column(name = "debe")
    private BigDecimal debe;
    @Column(name = "haber")
    private BigDecimal haber;
    @Column(name = "glosa", nullable = false)
    private String glosa;
    @Column(name = "fecha_alta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "usuario_alta", nullable = false)
    private String usuarioAlta;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Column(name = "usuario_baja")
    private String usuarioBaja;
    @JoinColumn(name = "id_comprobante", referencedColumnName = "id_comprobante")
    @ManyToOne(optional = false)
    private CntComprobante cntComprobante;
    @JoinColumn(name = "id_entidad", referencedColumnName = "id_entidad")
    @ManyToOne(optional = false)
    private CntEntidad cntEntidad;
    @Column(name = "estado", nullable = false)
    private String estado;
    @Column(name = "login_usuario", nullable = false)
    private String loginUsuario;
    @Column(name = "debe_dolar")
    private BigDecimal debeDolar;
    @Column(name = "haber_dolar")
    private BigDecimal haberDolar;
    @Column(name = "id_padre")
    private Long idPadre;
    @Column(name = "posicion")
    private Long posicion;
    @Column(name = "transaccion_realizada")
    private String transaccionRealizada;
    @Column(name = "numero_cheque")
    private String numeroCheque;
    @Column(name = "id_antecesor")
    private Long idAntecesor;
    @Column(name = "id_auxiliar")
    private Long idAuxiliar;
    @Column(name = "id_proyecto")
    private Long idProyecto;
    @Column(name = "posicion_anterior")
    private Long posicionAnterior;
    @JoinColumn(name = "id_facturacion", referencedColumnName = "id_facturacion")
    @ManyToOne(optional = true)
    private CntFacturacion cntFacturacion;

    public CntDetalleComprobante() {
    }

    public CntDetalleComprobante(Long idDetalleComprobante) {
        this.idDetalleComprobante = idDetalleComprobante;
    }

    public CntDetalleComprobante(Long idDetalleComprobante, BigDecimal debe, BigDecimal haber, String glosa, Date fechaAlta, String usuarioAlta) {
        this.idDetalleComprobante = idDetalleComprobante;
        this.debe = debe;
        this.haber = haber;
        this.glosa = glosa;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdDetalleComprobante() {
        return idDetalleComprobante;
    }

    public void setIdDetalleComprobante(Long idDetalleComprobante) {
        this.idDetalleComprobante = idDetalleComprobante;
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

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(String usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public String getUsuarioBaja() {
        return usuarioBaja;
    }

    public void setUsuarioBaja(String usuarioBaja) {
        this.usuarioBaja = usuarioBaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleComprobante != null ? idDetalleComprobante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntDetalleComprobante)) {
            return false;
        }
        CntDetalleComprobante other = (CntDetalleComprobante) object;
        if ((this.idDetalleComprobante == null && other.idDetalleComprobante != null) || (this.idDetalleComprobante != null && !this.idDetalleComprobante.equals(other.idDetalleComprobante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante[ idDetalleComprobante=" + idDetalleComprobante + " ]";
    }

    public CntComprobante getCntComprobante() {
        return cntComprobante;
    }

    public void setCntComprobante(CntComprobante cntComprobante) {
        this.cntComprobante = cntComprobante;
    }

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
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

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public Long getPosicion() {
        return posicion;
    }

    public void setPosicion(Long posicion) {
        this.posicion = posicion;
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

    public String getTransaccionRealizada() {
        return transaccionRealizada;
    }

    public void setTransaccionRealizada(String transaccionRealizada) {
        this.transaccionRealizada = transaccionRealizada;
    }

    public Long getIdAntecesor() {
        return idAntecesor;
    }

    public void setIdAntecesor(Long idAntecesor) {
        this.idAntecesor = idAntecesor;
    }

    public Long getIdAuxiliar() {
        return idAuxiliar;
    }

    public void setIdAuxiliar(Long idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Long getPosicionAnterior() {
        return posicionAnterior;
    }

    public void setPosicionAnterior(Long posicionAnterior) {
        this.posicionAnterior = posicionAnterior;
    }

    public CntFacturacion getCntFacturacion() {
        return cntFacturacion;
    }

    public void setCntFacturacion(CntFacturacion cntFacturacion) {
        this.cntFacturacion = cntFacturacion;
    }
    
}
