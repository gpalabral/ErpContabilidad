/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.Erp;
import com.bap.erp.modelo.entidades.cnf.ParParametrosAutorizacion;
import com.bap.erp.modelo.entidades.cnf.ParSucursal;
import com.bap.erp.modelo.entidades.cnf.ParTipoFacturacion;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "CNT_FACTURACION", schema = "CNT")
public class CntFacturacion implements Serializable, Cloneable {

    private static final long serialVersionUID = Erp.serialVersionIdErp;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facturacion", nullable = false)
    private Long idFacturacion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation    
    @Column(name = "monto", nullable = false)
    private BigDecimal monto;
    @Column(name = "excento", nullable = false)
    private BigDecimal excento;
    @Column(name = "ice", nullable = false)
    private BigDecimal ice;
    @Column(name = "iva", nullable = false)
    private BigDecimal iva;
    @Column(name = "monto_seg_moneda", nullable = false)
    private BigDecimal montoSegMoneda;
    @Column(name = "excento_seg_moneda", nullable = false)
    private BigDecimal excentoSegMoneda;
    @Column(name = "ice_seg_moneda", nullable = false)
    private BigDecimal iceSegMoneda;
    @Column(name = "iva_seg_moneda", nullable = false)
    private BigDecimal ivaSegMoneda;
    @Column(name = "credito_fiscal_transitorio", nullable = false)
    private String creditoFiscalTransitorio;
    @Column(name = "fecha_factura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFactura;
    @Column(name = "nro_inicial")
    private Long nroInicial;
    @Column(name = "nro_final")
    private Long nroFinal;
    @ManyToOne
    @JoinColumn(name = "tipo_facturacion", referencedColumnName = "codigo")
    private ParTipoFacturacion parTipoFacturacion;
    @ManyToOne
    @JoinColumn(name = "parametros_autorizacion", referencedColumnName = "codigo")
    private ParParametrosAutorizacion parParametrosAutorizacion;
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
    @Column(name = "estado", nullable = false)
    private String estado;
    @Column(name = "login_usuario", nullable = false)
    private String loginUsuario;
    @Column(name = "nro_autorizacion")
    private String nroAutorizacion;
    @Column(name = "movimiento", nullable = false)
    private String movimiento;
    @Column(name = "codigo_control")
    private String codigoControl;
    @Column(name = "fecha_reg_transitorio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegTransitorio;
    @Column(name = "sucursal")
    private String sucursal;
    @Column(name = "id_antecesor")
    private Long idAntecesor;
    //nuevos atributos para cuentas por pagar CPP
    @Column(name = "id_proveedor_cliente")
    private Long idProveedorCliente;        
    @Column(name = "pagoIT")
    private String pagoIT;
    @Column(name = "id_forma_pago_cobro")
    private Long idFormaPagoCobro;        
    @Column(name = "id_banco")
    private Long idBanco;        
    @Column(name = "id_dosificacion")
    private Long idDosificacion;        
    @Column(name = "nit")
    private Long nit;
    @Column(name = "razon_social")
    private String razonSocial;    
    
    

    public CntFacturacion() {
        this.monto = new BigDecimal("0.00");
        this.excento = new BigDecimal("0.00");
        this.ice = new BigDecimal("0.00");
        this.iva = new BigDecimal("0.00");
        this.creditoFiscalTransitorio = "N";        
        this.parParametrosAutorizacion = new ParParametrosAutorizacion();
        this.parTipoFacturacion = new ParTipoFacturacion();
    }

    public CntFacturacion(Long idFacturacion) {
        this.idFacturacion = idFacturacion;
    }

    public CntFacturacion(Long idFacturacion, BigDecimal monto, BigDecimal excento, BigDecimal ice, BigDecimal iva, String creditoFiscalTransitorio, long nroInicial, Date fechaAlta, String usuarioAlta) {
        this.idFacturacion = idFacturacion;
        this.monto = monto;
        this.excento = excento;
        this.ice = ice;
        this.iva = iva;
        this.creditoFiscalTransitorio = creditoFiscalTransitorio;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
    }

    public Long getIdFacturacion() {
        return idFacturacion;
    }

    public void setIdFacturacion(Long idFacturacion) {
        this.idFacturacion = idFacturacion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getExcento() {
        return excento;
    }

    public void setExcento(BigDecimal excento) {
        this.excento = excento;
    }

    public BigDecimal getIce() {
        return ice;
    }

    public void setIce(BigDecimal ice) {
        this.ice = ice;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public String getCreditoFiscalTransitorio() {
        return creditoFiscalTransitorio;
    }

    public void setCreditoFiscalTransitorio(String creditoFiscalTransitorio) {
        this.creditoFiscalTransitorio = creditoFiscalTransitorio;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Long getNroInicial() {
        return nroInicial;
    }

    public void setNroInicial(Long nroInicial) {
        this.nroInicial = nroInicial;
    }

    public Long getNroFinal() {
        return nroFinal;
    }

    public void setNroFinal(Long nroFinal) {
        this.nroFinal = nroFinal;
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
        hash += (idFacturacion != null ? idFacturacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CntFacturacion)) {
            return false;
        }
        CntFacturacion other = (CntFacturacion) object;
        if ((this.idFacturacion == null && other.idFacturacion != null) || (this.idFacturacion != null && !this.idFacturacion.equals(other.idFacturacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bap.erp.modelo.entidades.cnt.CntFacturacion[ idFacturacion=" + idFacturacion + " ]";
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ParParametrosAutorizacion getParParametrosAutorizacion() {
        return parParametrosAutorizacion;
    }

    public void setParParametrosAutorizacion(ParParametrosAutorizacion parParametrosAutorizacion) {
        this.parParametrosAutorizacion = parParametrosAutorizacion;
    }

    public ParTipoFacturacion getParTipoFacturacion() {
        return parTipoFacturacion;
    }

    public void setParTipoFacturacion(ParTipoFacturacion parTipoFacturacion) {
        this.parTipoFacturacion = parTipoFacturacion;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public BigDecimal getExcentoSegMoneda() {
        return excentoSegMoneda;
    }

    public void setExcentoSegMoneda(BigDecimal excentoSegMoneda) {
        this.excentoSegMoneda = excentoSegMoneda;
    }

    public BigDecimal getIceSegMoneda() {
        return iceSegMoneda;
    }

    public void setIceSegMoneda(BigDecimal iceSegMoneda) {
        this.iceSegMoneda = iceSegMoneda;
    }

    public BigDecimal getIvaSegMoneda() {
        return ivaSegMoneda;
    }

    public void setIvaSegMoneda(BigDecimal ivaSegMoneda) {
        this.ivaSegMoneda = ivaSegMoneda;
    }

    public BigDecimal getMontoSegMoneda() {
        return montoSegMoneda;
    }

    public void setMontoSegMoneda(BigDecimal montoSegMoneda) {
        this.montoSegMoneda = montoSegMoneda;
    }

    public String getCodigoControl() {
        return codigoControl;
    }

    public void setCodigoControl(String codigoControl) {
        this.codigoControl = codigoControl;
    }

    public Date getFechaRegTransitorio() {
        return fechaRegTransitorio;
    }

    public void setFechaRegTransitorio(Date fechaRegTransitorio) {
        this.fechaRegTransitorio = fechaRegTransitorio;
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

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Long getIdAntecesor() {
        return idAntecesor;
    }

    public void setIdAntecesor(Long idAntecesor) {
        this.idAntecesor = idAntecesor;
    }

    public String getNroAutorizacion() {
        return nroAutorizacion;
    }

    public void setNroAutorizacion(String nroAutorizacion) {
        this.nroAutorizacion = nroAutorizacion;
    }

    public Long getIdProveedorCliente() {
        return idProveedorCliente;
    }

    public void setIdProveedorCliente(Long idProveedorCliente) {
        this.idProveedorCliente = idProveedorCliente;
    }

    public String getPagoIT() {
        return pagoIT;
    }

    public void setPagoIT(String pagoIT) {
        this.pagoIT = pagoIT;
    }

    public Long getIdFormaPagoCobro() {
        return idFormaPagoCobro;
    }

    public void setIdFormaPagoCobro(Long idFormaPagoCobro) {
        this.idFormaPagoCobro = idFormaPagoCobro;
    }

    public Long getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Long idBanco) {
        this.idBanco = idBanco;
    }

    public Long getIdDosificacion() {
        return idDosificacion;
    }

    public void setIdDosificacion(Long idDosificacion) {
        this.idDosificacion = idDosificacion;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
        
    
}
