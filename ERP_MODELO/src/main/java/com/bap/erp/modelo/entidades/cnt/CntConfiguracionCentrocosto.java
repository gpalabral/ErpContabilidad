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
 * @author Administrador
 */
@Entity
@Table(name = "CNT_CONFIGURACION_CENTROCOSTO", schema="CNT")
 
public class CntConfiguracionCentrocosto implements Serializable, Cloneable{
   private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_configuracion_centrocosto")
    private Long idConfiguracionCentrocosto;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "porcentaje")
    private BigDecimal porcentaje;
    @Basic(optional = false)
    @Column(name = "usuario_alta")
    private String usuarioAlta;
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
    private CntEntidad idCentroCosto;
    
    @Column(name = "id_plan_cuenta")
    private Long idPlanCuenta;
    
    @Column(name = "codigo_distribucion_centro_costo")
    private Long codigoDistribucionCentroCosto;

    public CntConfiguracionCentrocosto() {
    }

    public CntConfiguracionCentrocosto(Long idConfiguracionCentrocosto) {
        this.idConfiguracionCentrocosto = idConfiguracionCentrocosto;
    }

    public CntConfiguracionCentrocosto(Long idConfiguracionCentrocosto, String tipo, BigDecimal porcentaje, String usuarioAlta) {
        this.idConfiguracionCentrocosto = idConfiguracionCentrocosto;
        this.tipo = tipo;
        this.porcentaje = porcentaje;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdConfiguracionCentrocosto() {
        return idConfiguracionCentrocosto;
    }

    public void setIdConfiguracionCentrocosto(Long idConfiguracionCentrocosto) {
        this.idConfiguracionCentrocosto = idConfiguracionCentrocosto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
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

    public CntEntidad getIdCentroCosto() {
        return idCentroCosto;
    }

    public void setIdCentroCosto(CntEntidad idCentroCosto) {
        this.idCentroCosto = idCentroCosto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracionCentrocosto != null ? idConfiguracionCentrocosto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntConfiguracionCentrocosto)) {
            return false;
        }
        CntConfiguracionCentrocosto other = (CntConfiguracionCentrocosto) object;
        if ((this.idConfiguracionCentrocosto == null && other.idConfiguracionCentrocosto != null) || (this.idConfiguracionCentrocosto != null && !this.idConfiguracionCentrocosto.equals(other.idConfiguracionCentrocosto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto[ idConfiguracionCentrocosto=" + idConfiguracionCentrocosto + " ]";
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

    public Long getIdPlanCuenta() {
        return idPlanCuenta;
    }

    public void setIdPlanCuenta(Long idPlanCuenta) {
        this.idPlanCuenta = idPlanCuenta;
    }    

    public Long getCodigoDistribucionCentroCosto() {
        return codigoDistribucionCentroCosto;
    }

    public void setCodigoDistribucionCentroCosto(Long codigoDistribucionCentroCosto) {
        this.codigoDistribucionCentroCosto = codigoDistribucionCentroCosto;
    }    
}
