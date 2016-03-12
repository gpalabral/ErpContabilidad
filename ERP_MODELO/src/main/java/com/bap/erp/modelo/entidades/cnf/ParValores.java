/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.entidades.cnf;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author HENRRY
 */
@Entity
@Table(name = "PAR_VALORES", schema="CNF")
@NamedQueries({
    @NamedQuery(name = "ParValores.findAll", query = "SELECT p FROM ParValores p")})
public class ParValores implements Serializable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valores", nullable = false)
    private Long idValores;
    @Column(name = "codigo", length = 5, nullable = false)
    private String codigo;
    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;
    @Column(name = "contexto", length = 30, nullable = false)
    private String contexto;
    @Column(name = "grupo_parametro", length = 5, nullable = false)
    private String grupoParametro;
    @Column(name = "fecha_alta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "usuario_alta", length = 50, nullable = false)
    private String usuarioAlta;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "usuario_modificacion", length = 50)
    private String usuarioModificacion;
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Column(name = "usuario_baja", length = 50)
    private String usuarioBaja;

    public ParValores() {
    }

    public ParValores(Long idValores) {
        this.idValores = idValores;
    }

    public ParValores(Long idValores, String codigo, String descripcion, String contexto, String grupoParametro, Date fechaAlta, String usuarioAlta) {
        this.idValores = idValores;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.contexto = contexto;
        this.grupoParametro = grupoParametro;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdValores() {
        return idValores;
    }

    public void setIdValores(Long idValores) {
        this.idValores = idValores;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContexto() {
        return contexto;
    }

    public void setContexto(String contexto) {
        this.contexto = contexto;
    }

    public String getGrupoParametro() {
        return grupoParametro;
    }

    public void setGrupoParametro(String grupoParametro) {
        this.grupoParametro = grupoParametro;
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
        hash += (idValores != null ? idValores.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParValores)) {
            return false;
        }
        ParValores other = (ParValores) object;
        if ((this.idValores == null && other.idValores != null) || (this.idValores != null && !this.idValores.equals(other.idValores))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.ParValores[ idValores=" + idValores + " ]";
    }
}
