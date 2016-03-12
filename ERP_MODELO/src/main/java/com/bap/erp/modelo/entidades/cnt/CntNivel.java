package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import com.bap.erp.modelo.entidades.cnf.ParTiposDatoNivel;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "CNT_NIVEL", schema = "CNT")
public class CntNivel implements Serializable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nivel", nullable = false)
    private Long idNivel;
    @JoinColumn(name = "id_mascara", referencedColumnName = "id_mascara")
    @ManyToOne(optional = false)
    private CntMascara cntMascaras;
    @Column(name = "nivel", nullable = false)
    private int nivel;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "tipo_nivel", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private ParTiposDatoNivel tipoNivel;
    @Column(name = "tamanio", nullable = false)
    private int tamanio;
    @Column(name = "fecha_alta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "usuario_alta", nullable = false)
    private String usuarioAlta;
    @Column(name = "tipo_movimiento", nullable = false)
    private String tipoMovimiento;

    public CntNivel() {
    }

    public CntNivel(Long idNivel) {
        this.idNivel = idNivel;
    }

    public CntNivel(Long idNivel, CntMascara cntMascaras, int nivel, String descripcion, ParTiposDatoNivel tipoNivel, int tamanio, Date fechaAlta, String usuarioAlta) {
        this.idNivel = idNivel;
        this.cntMascaras = cntMascaras;
        this.nivel = nivel;
        this.descripcion = descripcion;
        this.tipoNivel = tipoNivel;
        this.tamanio = tamanio;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(Long idNivel) {
        this.idNivel = idNivel;
    }

    public CntMascara getCntMascara() {
        return cntMascaras;
    }

    public void setCntMascara(CntMascara cntMascaras) {
        this.cntMascaras = cntMascaras;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ParTiposDatoNivel getTipoNivel() {
        return tipoNivel;
    }

    public void setTipoNivel(ParTiposDatoNivel tipoNivel) {
        this.tipoNivel = tipoNivel;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNivel != null ? idNivel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntNivel)) {
            return false;
        }
        CntNivel other = (CntNivel) object;
        if ((this.idNivel == null && other.idNivel != null) || (this.idNivel != null && !this.idNivel.equals(other.idNivel))) {
            return false;
        }
        return true;
    }

    public CntMascara getCntMascaras() {
        return cntMascaras;
    }

    public void setCntMascaras(CntMascara cntMascaras) {
        this.cntMascaras = cntMascaras;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntNivel[ idNivel=" + idNivel + " ]";
    }
}
