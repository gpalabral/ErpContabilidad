package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import com.bap.erp.modelo.entidades.cnf.ParGruposNivel;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "CNT_MASCARA",schema="CNT")
public class CntMascara implements Serializable {
    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascara",nullable=false)
    private Long idMascara;
    @Column(name = "tamanio_nivel",nullable=false)
    private int tamanioNivel;
    @Column(name = "mascara",length=50,nullable=false)
    private String mascara;
    @ManyToOne(optional = false)
    @JoinColumn(name = "grupo_nivel", referencedColumnName = "codigo")
    private ParGruposNivel grupoNivel;
    @Column(name = "fecha_alta",nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "usuario_alta",length=50,nullable=false)
    private String usuarioAlta;
    @Column(name = "fecha_baja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Column(name = "usuario_baja", length = 50)
    private String usuarioBaja;

    public CntMascara() {
    }

    public CntMascara(Long idMascara) {
        this.idMascara = idMascara;
    }

    public CntMascara(Long idMascara, int tamanioNivel, String mascara, ParGruposNivel grupoNivel, Date fechaAlta, String usuarioAlta) {
        this.idMascara = idMascara;
        this.tamanioNivel = tamanioNivel;
        this.mascara = mascara;
        this.grupoNivel = grupoNivel;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdMascara() {
        return idMascara;
    }

    public void setIdMascara(Long idMascara) {
        this.idMascara = idMascara;
    }

    public int getTamanioNivel() {
        return tamanioNivel;
    }

    public void setTamanioNivel(int tamanioNivel) {
        this.tamanioNivel = tamanioNivel;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public ParGruposNivel getGrupoNivel() {
        return grupoNivel;
    }

    public void setGrupoNivel(ParGruposNivel grupoNivel) {
        this.grupoNivel = grupoNivel;
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
        hash += (idMascara != null ? idMascara.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntMascara)) {
            return false;
        }
        CntMascara other = (CntMascara) object;
        if ((this.idMascara == null && other.idMascara != null) || (this.idMascara != null && !this.idMascara.equals(other.idMascara))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntMascara[ idMascara=" + idMascara + " ]";
    }

    }
