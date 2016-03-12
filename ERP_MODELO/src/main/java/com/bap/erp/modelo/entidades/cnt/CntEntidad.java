package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import com.bap.erp.modelo.entidades.cnf.ParAjustes;
import com.bap.erp.modelo.entidades.cnf.ParTiposMovimiento;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "CNT_ENTIDAD", schema = "CNT")
public class CntEntidad implements Serializable, Cloneable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entidad", nullable = false)
    private Long idEntidad;
    @Column(name = "id_entidad_padre")
    private Long idEntidadPadre;
    @ManyToOne
    @JoinColumn(name = "ajuste", referencedColumnName = "codigo")
    private ParAjustes parAjustes;
    @ManyToOne
    @JoinColumn(name = "tipo_movimiento", referencedColumnName = "codigo")
    private ParTiposMovimiento parTiposMovimiento;
    @JoinColumn(name = "id_mascara", referencedColumnName = "id_mascara")
    @ManyToOne(optional = false)
    private CntMascara cntMascara;
    @Basic(optional = false)
    @Column(name = "mascara_generada")
    private String mascaraGenerada;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "nivel", nullable = false)
    private int nivel;
    @Column(name = "tipo", length = 5, nullable = false)
    private String tipo;
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
    @Column(name = "tiene_centro_costo")
    private String tieneCentroCosto;
    @Column(name = "tiene_auxiliar")
    private String tieneAuxiliar;
    @Column(name = "habilita_centro_costo")
    private String habilitaCentroCosto;
    @Column(name = "parametros_individuales", length = 1, nullable = true)
    private String parametrosIndividuales;

    public CntEntidad() {
    }

    public CntEntidad(Long idObjeto) {
        this.idEntidad = idObjeto;
    }

    public CntEntidad(Long idObjeto, String mascaraGenerada, String descripcion, int nivel, String tipo, Date fechaAlta, String usuarioAlta) {
        this.idEntidad = idObjeto;
        this.mascaraGenerada = mascaraGenerada;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.tipo = tipo;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public Long getIdEntidadPadre() {
        return idEntidadPadre;
    }

    public void setIdEntidadPadre(Long idEntidadPadre) {
        this.idEntidadPadre = idEntidadPadre;
    }

    public CntMascara getCntMascara() {
        return cntMascara;
    }

    public void setCntMascara(CntMascara cntMascaras) {
        this.cntMascara = cntMascaras;
    }

    public ParAjustes getParAjustes() {
        return parAjustes;
    }

    public void setParAjustes(ParAjustes parAjustes) {
        this.parAjustes = parAjustes;
    }

    public ParTiposMovimiento getParTiposMovimiento() {
        return parTiposMovimiento;
    }

    public void setParTiposMovimiento(ParTiposMovimiento parTiposMovimiento) {
        this.parTiposMovimiento = parTiposMovimiento;
    }

    public String getMascaraGenerada() {
        return mascaraGenerada;
    }

    public void setMascaraGenerada(String mascaraGenerada) {
        this.mascaraGenerada = mascaraGenerada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        hash += (idEntidad != null ? idEntidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntEntidad)) {
            return false;
        }
        CntEntidad other = (CntEntidad) object;
        if ((this.idEntidad == null && other.idEntidad != null) || (this.idEntidad != null && !this.idEntidad.equals(other.idEntidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntEntidad[ idObjeto=" + idEntidad + " ]";
    }

    public String getTieneCentroCosto() {
        return tieneCentroCosto;
    }

    public void setTieneCentroCosto(String tieneCentroCosto) {
        this.tieneCentroCosto = tieneCentroCosto;
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

    public String getTieneAuxiliar() {
        return tieneAuxiliar;
    }

    public void setTieneAuxiliar(String tieneAuxiliar) {
        this.tieneAuxiliar = tieneAuxiliar;
    }

    public String getHabilitaCentroCosto() {
        return habilitaCentroCosto;
    }

    public void setHabilitaCentroCosto(String habilitaCentroCosto) {
        this.habilitaCentroCosto = habilitaCentroCosto;
    }

    public String getParametrosIndividuales() {
        return parametrosIndividuales;
    }

    public void setParametrosIndividuales(String parametrosIndividuales) {
        this.parametrosIndividuales = parametrosIndividuales;
    }
    
}
