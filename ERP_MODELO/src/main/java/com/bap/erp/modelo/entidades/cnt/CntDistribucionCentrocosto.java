/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Administrador
 */
@Entity
@Table(name = "CNT_DISTRIBUCION_CENTROCOSTO",schema="CNT")
public class CntDistribucionCentrocosto implements Serializable, Cloneable{
  private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distribucion_centrocosto")
    private Long idDistribucionCentrocosto;    
    @Column(name = "id_configuracion_centrocosto")
    private Long idConfiguracionCentrocosto;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "porcentaje")
    private BigDecimal porcentaje;
    @Basic(optional = false)
    @Column(name = "monto")
    private BigDecimal monto;
    @Basic(optional = false)
    @Column(name = "usuario_alta")
    private String usuarioAlta;
    @Basic(optional = false)
    @Column(name = "fecha_alta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "usuario_baja")
    private String usuarioBaja;
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @JoinColumn(name = "id_centro_costo", referencedColumnName = "id_entidad")
    @ManyToOne(optional = false)
    private CntEntidad cntCentroDeCosto;
    @JoinColumn(name = "id_detalle_comprobante", referencedColumnName = "id_detalle_comprobante")
    @ManyToOne(optional = false)
    private CntDetalleComprobante cntDetalleComprobante;

    public CntDistribucionCentrocosto() {
    }

    public CntDistribucionCentrocosto(Long idDistribucionCentrocosto) {
        this.idDistribucionCentrocosto = idDistribucionCentrocosto;
    }

    public CntDistribucionCentrocosto(Long idDistribucionCentrocosto, String estado, BigDecimal porcentaje, BigDecimal monto, String usuarioAlta, Date fechaAlta) {
        this.idDistribucionCentrocosto = idDistribucionCentrocosto;
//        this.idConfiguracionCentrocosto = idConfiguracionCentrocosto;
        this.estado = estado;
        this.porcentaje = porcentaje;
        this.monto = monto;
        this.usuarioAlta = usuarioAlta;
        this.fechaAlta = fechaAlta;
    }

    public Long getIdDistribucionCentrocosto() {
        return idDistribucionCentrocosto;
    }

    public void setIdDistribucionCentrocosto(Long idDistribucionCentrocosto) {
        this.idDistribucionCentrocosto = idDistribucionCentrocosto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(String usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioBaja() {
        return usuarioBaja;
    }

    public void setUsuarioBaja(String usuarioBaja) {
        this.usuarioBaja = usuarioBaja;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public CntEntidad getCntCentroDeCosto() {
        return cntCentroDeCosto;
    }

    public void setCntCentroDeCosto(CntEntidad cntCentroDeCosto) {
        this.cntCentroDeCosto = cntCentroDeCosto;
    }

    public CntDetalleComprobante getCntDetalleComprobante() {
        return cntDetalleComprobante;
    }

    public void setCntDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        this.cntDetalleComprobante = cntDetalleComprobante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDistribucionCentrocosto != null ? idDistribucionCentrocosto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntDistribucionCentrocosto)) {
            return false;
        }
        CntDistribucionCentrocosto other = (CntDistribucionCentrocosto) object;
        if ((this.idDistribucionCentrocosto == null && other.idDistribucionCentrocosto != null) || (this.idDistribucionCentrocosto != null && !this.idDistribucionCentrocosto.equals(other.idDistribucionCentrocosto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntDistribucionCentrocosto[ idDistribucionCentrocosto=" + idDistribucionCentrocosto + " ]";
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

    public Long getIdConfiguracionCentrocosto() {
        return idConfiguracionCentrocosto;
    }

    public void setIdConfiguracionCentrocosto(Long idConfiguracionCentrocosto) {
        this.idConfiguracionCentrocosto = idConfiguracionCentrocosto;
    }
         
}
