/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.admin;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Henrry
 */
@Entity
@Table(name = "ADM_USUARIO", schema = "ADM")
public class AdmUsuarios implements Serializable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "password", length = 150, nullable = false)
    private String password;
    @Column(name = "estado_usuario", length = 5, nullable = false)
    private String estadoUsuario;
    @Column(name = "tipo_usuario", length = 5, nullable = false)
    private String tipoUsuario;
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

    public AdmUsuarios() {
    }

    public AdmUsuarios(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public AdmUsuarios(Long idUsuario, String login, String estadoUsuario, String tipoUsuario, Date fechaAlta, String usuarioAlta, Date fechaModificacion, String usuarioModificacion) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.estadoUsuario = estadoUsuario;
        this.tipoUsuario = tipoUsuario;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdmUsuarios)) {
            return false;
        }
        AdmUsuarios other = (AdmUsuarios) object;
        return !((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario)));
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.admin.AdmUsuarios[ idUsuario=" + idUsuario + " ]";
    }
    
}
