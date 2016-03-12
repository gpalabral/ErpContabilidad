package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "CNT_GESTION", schema="CNT")
public class CntGestion implements Serializable {
    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gestion", nullable=false)
    private Long idGestion;
    
    @Column(name = "nombre_gestion", length = 50, nullable=false)
    private String nombreGestion;  
    
    @Column(name = "periodo_inicial", length = 50, nullable = false)
    private String periodoInicial;
    
    @Column(name = "periodo_final", length = 50, nullable=false)
    private String periodoFinal;
    
    @Column(name = "estado_gestion", length = 5, nullable=false)
    private String estadoGestion;    
    
    @Column(name = "fecha_alta", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;    
    
    @Column(name = "usuario_alta", length = 50, nullable=false)
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

    public CntGestion() {
    }

    public CntGestion(Long idGestion) {
        this.idGestion = idGestion;
    }

    public CntGestion(Long idGestion, String nombreGestion, String periodoInicial, String periodoFinal, String estadoGestion, Date fechaAlta, String usuarioAlta) {
        this.idGestion = idGestion;
        this.nombreGestion = nombreGestion;
        this.periodoInicial = periodoInicial;
        this.periodoFinal = periodoFinal;
        this.estadoGestion = estadoGestion;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdGestion() {
        return idGestion;
    }

    public void setIdGestion(Long idGestion) {
        this.idGestion = idGestion;
    }

    public String getNombreGestion() {
        return nombreGestion;
    }

    public void setNombreGestion(String nombreGestion) {
        this.nombreGestion = nombreGestion;
    }

    public String getPeriodoInicial() {
        return periodoInicial;
    }

    public void setPeriodoInicial(String periodoInicial) {
        this.periodoInicial = periodoInicial;
    }

    public String getPeriodoFinal() {
        return periodoFinal;
    }

    public void setPeriodoFinal(String periodoFinal) {
        this.periodoFinal = periodoFinal;
    }

    public String getEstadoGestion() {
        return estadoGestion;
    }

    public void setEstadoGestion(String estadoGestion) {
        this.estadoGestion = estadoGestion;
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
        hash += (idGestion != null ? idGestion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntGestion)) {
            return false;
        }
        CntGestion other = (CntGestion) object;
        if ((this.idGestion == null && other.idGestion != null) || (this.idGestion != null && !this.idGestion.equals(other.idGestion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntGestion[ idGestion=" + idGestion + " ]";
    }
    
}
