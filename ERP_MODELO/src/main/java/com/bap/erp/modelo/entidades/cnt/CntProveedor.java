/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CNT_PROVEEDOR", schema = "CNT")
public class CntProveedor implements Serializable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_proveedor", nullable = false)
    private Long idProveedor;
    @Column(name = "nit", nullable=false)
    private Long nit;
    @Column(name = "razon_social", nullable = false)
    private String razonSocial;    
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
    @Column(name = "nro_autorizacion", nullable=false)
    private String autorizacion;

    public CntProveedor() {
    }

    public CntProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;        
    }

    public CntProveedor(Long idProveedor, Long nit, String razonSocial, String codigoControl, Date fechaAlta, String usuarioAlta) {
        this.idProveedor = idProveedor;
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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
        hash += (idProveedor != null ? idProveedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntProveedor)) {
            return false;
        }
        CntProveedor other = (CntProveedor) object;
        if ((this.idProveedor == null && other.idProveedor != null) || (this.idProveedor != null && !this.idProveedor.equals(other.idProveedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntProveedor[ idProveedor=" + idProveedor + " ]";
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }   
           
}
