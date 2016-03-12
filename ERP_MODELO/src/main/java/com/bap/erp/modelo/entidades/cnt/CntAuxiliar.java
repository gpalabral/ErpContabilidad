package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "CNT_AUXILIAR",schema="CNT")
public class CntAuxiliar implements Serializable {
    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auxiliar",nullable=false)
    private Long idAuxiliar;
    @Column(name = "numero",nullable=false)
    private Long numero;
    @Column(name = "sigla",length=5, nullable=false)
    private String sigla;
    @Column(name = "nombre",nullable=false)
    private String nombre;
    @Column(name = "descripcion",nullable=false)
    private String descripcion;
    @Column(name = "fecha_alta",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "usuario_alta",nullable=false)
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

    public CntAuxiliar() {
    }

    public CntAuxiliar(Long idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

    public CntAuxiliar(Long idAuxiliar, Long numero, String sigla, String nombre, String descripcion, Date fechaAlta, String usuarioAlta) {
        this.idAuxiliar = idAuxiliar;
        this.numero = numero;
        this.sigla = sigla;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdAuxiliar() {
        return idAuxiliar;
    }

    public void setIdAuxiliar(Long idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idAuxiliar != null ? idAuxiliar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntAuxiliar)) {
            return false;
        }
        CntAuxiliar other = (CntAuxiliar) object;
        if ((this.idAuxiliar == null && other.idAuxiliar != null) || (this.idAuxiliar != null && !this.idAuxiliar.equals(other.idAuxiliar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.servicios.cnt.CntAuxiliar[ idAuxiliar=" + idAuxiliar + " ]";
    }
    
}
