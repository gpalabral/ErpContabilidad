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

@Entity
@Table(name = "CNT_TIPO_CAMBIO", schema = "CNT")
@NamedQueries({
    @NamedQuery(name = "CntTipoCambio.findAll", query = "SELECT c FROM CntTipoCambio c")})
public class CntTipoCambio implements Serializable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_cambio", nullable = false)
    private Long idTipoCambio;
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tipo_cambio", nullable = false)
    private BigDecimal tipoCambio;
    @Column(name = "tipo_ufv", nullable = false)
    private BigDecimal tipoUfv;
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

    public CntTipoCambio() {
    }

    public CntTipoCambio(Long idTipoCambio) {
        this.idTipoCambio = idTipoCambio;
    }

    public CntTipoCambio(Long idTipoCambio, Date fecha, BigDecimal tipoCambio, BigDecimal tipoUfv, Date fechaAlta, String usuarioAlta) {
        this.idTipoCambio = idTipoCambio;
        this.fecha = fecha;
        this.tipoCambio = tipoCambio;
        this.tipoUfv = tipoUfv;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdTipoCambio() {
        return idTipoCambio;
    }

    public void setIdTipoCambio(Long idTipoCambio) {
        this.idTipoCambio = idTipoCambio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public BigDecimal getTipoUfv() {
        return tipoUfv;
    }

    public void setTipoUfv(BigDecimal tipoUfv) {
        this.tipoUfv = tipoUfv;
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

    public String getUsuarioBaja() {
        return usuarioBaja;
    }

    public void setUsuarioBaja(String usuarioBaja) {
        this.usuarioBaja = usuarioBaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCambio != null ? idTipoCambio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntTipoCambio)) {
            return false;
        }
        CntTipoCambio other = (CntTipoCambio) object;
        if ((this.idTipoCambio == null && other.idTipoCambio != null) || (this.idTipoCambio != null && !this.idTipoCambio.equals(other.idTipoCambio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntTipoCambio[ idTipoCambio=" + idTipoCambio + " ]";
    }
}
