/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CNT_COMPROBANTE", schema="CNT")
public class CntComprobante implements Serializable, Cloneable{
    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id_comprobante", nullable=false)
    private Long idComprobante;    
    @Column(name = "numero",nullable=false)
    private Long numero;
    @Column(name = "periodo", nullable=false)
    private String periodo;       
    @ManyToOne
    @JoinColumn(name = "tipo_comprobante", referencedColumnName = "codigo")
    private ParTipoComprobante parTipoComprobante;                  
    @Column(name = "descripcion", nullable=false)
    private String descripcion;
    @Column(name = "fecha", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;       
//    @Column(name = "total_comprobantes", nullable=false)
    @Column(name = "total_comprobantes")
    private BigDecimal totalComprobantes;    
//    @Column(name = "total_comprobantes_seg_moneda", nullable=false)
    @Column(name = "total_comprobantes_seg_moneda")
    private BigDecimal totalComprobantesSegMoneda;    
    @Column(name = "fecha_alta", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;    
    @Column(name = "usuario_alta", nullable=false)
    private String usuarioAlta;
    @Column(name = "glosa_comprobante")
    private String glosaComprobante;
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
    
 
    @Column(name = "login_usuario", nullable=false)
    private String loginUsuario;
    
    
    @Column(name = "estado", nullable=false)
    private String estado;

    @Column(name = "tipo_moneda", nullable=false)    
    private String tipoMoneda;    
    
    @Column(name = "tipo_cambio", nullable=false)    
    private BigDecimal tipoCambio;    
    
    @Column(name = "modulo", nullable=false)
    private String modulo;
    
    @Column(name = "motivo_anulacion")
    private String motivoAnulacion;
    
    @Column(name = "id_antecesor")
    private Long idAntecesor;
    
    public CntComprobante() {
    }

    public CntComprobante(Long idComprobante) {
        this.idComprobante = idComprobante;
    }

    public CntComprobante(Long idComprobante, String periodo, String tipoTransaccion, String descripcion, BigDecimal totalComprobantes, Date fechaAlta, String usuarioAlta) {
        this.idComprobante = idComprobante;
        this.periodo = periodo;        
        this.descripcion = descripcion;
        this.totalComprobantes = totalComprobantes;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Long idComprobante) {
        this.idComprobante = idComprobante;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getTotalComprobantes() {
        return totalComprobantes;
    }

    public void setTotalComprobantes(BigDecimal totalComprobantes) {
        this.totalComprobantes = totalComprobantes;
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
        hash += (idComprobante != null ? idComprobante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntComprobante)) {
            return false;
        }
        CntComprobante other = (CntComprobante) object;
        if ((this.idComprobante == null && other.idComprobante != null) || (this.idComprobante != null && !this.idComprobante.equals(other.idComprobante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntComprobante[ idComprobante=" + idComprobante + " ]";
    }

    public ParTipoComprobante getParTipoComprobante() {
        return parTipoComprobante;
    }

    public void setParTipoComprobante(ParTipoComprobante parTipoComprobante) {
        this.parTipoComprobante = parTipoComprobante;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public BigDecimal getTotalComprobantesSegMoneda() {
        return totalComprobantesSegMoneda;
    }

    public void setTotalComprobantesSegMoneda(BigDecimal totalComprobantesSegMoneda) {
        this.totalComprobantesSegMoneda = totalComprobantesSegMoneda;
    }

    public String getGlosaComprobante() {
        return glosaComprobante;
    }

    public void setGlosaComprobante(String glosaComprobante) {
        this.glosaComprobante = glosaComprobante;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getMotivoAnulacion() {
        return motivoAnulacion;
    }

    public void setMotivoAnulacion(String motivoAnulacion) {
        this.motivoAnulacion = motivoAnulacion;
    }

    public Long getIdAntecesor() {
        return idAntecesor;
    }

    public void setIdAntecesor(Long idAntecesor) {
        this.idAntecesor = idAntecesor;
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
}
