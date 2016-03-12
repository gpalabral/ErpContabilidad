/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.entidades.cnt;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Jonas
 */
@Entity
@Table(name = "CNT_AUXILIAR_POR_CUENTA", schema = "CNT")
public class CntAuxiliarPorCuenta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auxiliar_por_cuenta", nullable = false)
    private Long idAuxiliarPorCuenta;
    @Column(name = "usuario_alta", nullable = false)
    private String usuarioAlta;
    @Column(name = "fecha_alta", nullable = false)
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
    @JoinColumn(name = "id_entidad", referencedColumnName = "id_entidad")
    @ManyToOne(optional = false)
    private CntEntidad cntEntidad;
    @JoinColumn(name = "id_auxiliar", referencedColumnName = "id_auxiliar")
    @ManyToOne(optional = false)
    private CntAuxiliar cntAuxiliar;

    public CntAuxiliarPorCuenta() {
    }

    public CntAuxiliarPorCuenta(Long idAuxiliarPorCuenta) {
        this.idAuxiliarPorCuenta = idAuxiliarPorCuenta;
    }

    public CntAuxiliarPorCuenta(Long idAuxiliarPorCuenta, String usuarioAlta) {
        this.idAuxiliarPorCuenta = idAuxiliarPorCuenta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdAuxiliarPorCuenta() {
        return idAuxiliarPorCuenta;
    }

    public void setIdAuxiliarPorCuenta(Long idAuxiliarPorCuenta) {
        this.idAuxiliarPorCuenta = idAuxiliarPorCuenta;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuxiliarPorCuenta != null ? idAuxiliarPorCuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntAuxiliarPorCuenta)) {
            return false;
        }
        CntAuxiliarPorCuenta other = (CntAuxiliarPorCuenta) object;
        if ((this.idAuxiliarPorCuenta == null && other.idAuxiliarPorCuenta != null) || (this.idAuxiliarPorCuenta != null && !this.idAuxiliarPorCuenta.equals(other.idAuxiliarPorCuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntAuxiliarPorCuenta[ idAuxiliarPorCuenta=" + idAuxiliarPorCuenta + " ]";
    }

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    public CntAuxiliar getCntAuxiliar() {
        return cntAuxiliar;
    }

    public void setCntAuxiliar(CntAuxiliar cntAuxiliar) {
        this.cntAuxiliar = cntAuxiliar;
    }
       
}
