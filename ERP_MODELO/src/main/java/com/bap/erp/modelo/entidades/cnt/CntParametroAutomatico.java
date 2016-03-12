/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import com.bap.erp.modelo.entidades.cnf.ParAjustes;
import com.bap.erp.modelo.entidades.cnf.ParTipoRetencion;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "CNT_PARAMETRO_AUTOMATICO", schema = "CNT")
public class CntParametroAutomatico implements Serializable, Cloneable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametro_automatico", nullable = false)
    private Long idParametroAutomatico;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_entidad", referencedColumnName = "id_entidad")
    private CntEntidad cntEntidad;
    @Column(name = "tipo_movimiento", nullable = false, length = 5)
    private String tipoMovimiento;
    @Column(name = "factura_compra", nullable = false, length = 5)
    private String facturaCompra;
    @Column(name = "factura_venta", nullable = false, length = 5)
    private String facturaVenta;
    @Column(name = "sin_factura", nullable = false, length = 5)
    private String sinFactura;
    @Column(name = "credito_fiscal_no_deducible", nullable = false, length = 5)
    private String creditoFiscalNoDeducible;
    @ManyToOne
    @JoinColumn(name = "retencion", referencedColumnName = "codigo")
    private ParTipoRetencion parTipoRetencion;
    @ManyToOne
    @JoinColumn(name = "grossing_up", referencedColumnName = "codigo")
    private ParTipoRetencion parTipoRetencionGrossingUp;
    @ManyToOne
    @JoinColumn(name = "tipo_ajuste", referencedColumnName = "codigo")
    private ParAjustes parAjustes;
    @Column(name = "fecha_alta", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "usuario_alta", nullable = false, length = 50)
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
    @Column(name = "tipo_calculo_automatico", nullable = false)
    private String tipoCalculoAutomatico;

    public CntParametroAutomatico() {
    }

    public CntParametroAutomatico(Long idParametroAutomatico) {
        this.idParametroAutomatico = idParametroAutomatico;
    }

    public CntParametroAutomatico(Long idParametroAutomatico, String tipoMovimiento, String facturaCompra, String facturaVenta, String retencion, String grossingUp, Date fechaAlta, String usuarioAlta, String sinFactura) {
        this.idParametroAutomatico = idParametroAutomatico;
        this.tipoMovimiento = tipoMovimiento;
        this.facturaCompra = facturaCompra;
        this.facturaVenta = facturaVenta;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
        this.sinFactura = sinFactura;
    }

    public Long getIdParametroAutomatico() {
        return idParametroAutomatico;
    }

    public void setIdParametroAutomatico(Long idParametroAutomatico) {
        this.idParametroAutomatico = idParametroAutomatico;
    }

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getFacturaCompra() {
        return facturaCompra;
    }

    public void setFacturaCompra(String facturaCompra) {
        this.facturaCompra = facturaCompra;
    }

    public String getFacturaVenta() {
        return facturaVenta;
    }

    public void setFacturaVenta(String facturaVenta) {
        this.facturaVenta = facturaVenta;
    }

    public ParAjustes getParAjustes() {
        return parAjustes;
    }

    public void setParAjustes(ParAjustes parAjustes) {
        this.parAjustes = parAjustes;
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

    public String getSinFactura() {
        return sinFactura;
    }

    public void setSinFactura(String sinFactura) {
        this.sinFactura = sinFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametroAutomatico != null ? idParametroAutomatico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntParametroAutomatico)) {
            return false;
        }
        CntParametroAutomatico other = (CntParametroAutomatico) object;
        if ((this.idParametroAutomatico == null && other.idParametroAutomatico != null) || (this.idParametroAutomatico != null && !this.idParametroAutomatico.equals(other.idParametroAutomatico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico[ idParametroAutomatico=" + idParametroAutomatico + " ]";
    }

    public ParTipoRetencion getParTipoRetencionGrossingUp() {
        return parTipoRetencionGrossingUp;
    }

    public void setParTipoRetencionGrossingUp(ParTipoRetencion parTipoRetencionGrossingUp) {
        this.parTipoRetencionGrossingUp = parTipoRetencionGrossingUp;
    }

    public String getCreditoFiscalNoDeducible() {
        return creditoFiscalNoDeducible;
    }

    public void setCreditoFiscalNoDeducible(String creditoFiscalNoDeducible) {
        this.creditoFiscalNoDeducible = creditoFiscalNoDeducible;
    }

    public String getTipoCalculoAutomatico() {
        return tipoCalculoAutomatico;
    }

    public void setTipoCalculoAutomatico(String tipoCalculoAutomatico) {
        this.tipoCalculoAutomatico = tipoCalculoAutomatico;
    }

    public ParTipoRetencion getParTipoRetencion() {
        return parTipoRetencion;
    }

    public void setParTipoRetencion(ParTipoRetencion parTipoRetencion) {
        this.parTipoRetencion = parTipoRetencion;
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
