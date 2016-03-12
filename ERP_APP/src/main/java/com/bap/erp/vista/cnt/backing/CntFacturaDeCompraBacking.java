package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.entidades.cnf.ParParametrosAutorizacion;
import com.bap.erp.modelo.entidades.cnf.ParSucursal;
import com.bap.erp.modelo.entidades.cnf.ParTipoFacturacion;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntProveedor;
import com.bap.erp.modelo.enums.EnumConfirmacion;
import com.bap.erp.modelo.enums.EnumEmpresa_Varios;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.enums.EnumParametrosDeAutorizacion;
import com.bap.erp.modelo.enums.EnumTransaccionRealizada;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntFacturacionService;
import com.bap.erp.modelo.servicios.cnt.CntProveedorService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresPorCuentaService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "cntFacturaDeCompraBacking")
@ViewScoped
public class CntFacturaDeCompraBacking extends AbstractManagedBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="VARIABLES">
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntFacturacionService}")
    private CntFacturacionService cntFacturacionService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntProveedorService}")
    private CntProveedorService cntProveedorService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    private String sucursalCodigo;
    private String creditoFiscalTransitorio;
    private String vendeCcombustible;
    private String tipoDeFactura;
    private String parametroAutorizacion;
    private CntDetalleComprobante cntDetalleComprobante;
    private Date diaFactura;
    private Boolean activaNumeroFactura = false;
    private Boolean activaSinCredito = true;
    private String listaPrueba[] = {"henrry", "renan", "hola"};
    private CntFacturacion cntFacturacion = new CntFacturacion();
    private List<ParSucursal> listaDeSucursales = new ArrayList<ParSucursal>();
    private BigDecimal tipoCambio;
    private BigDecimal montoDolares;
    private List<ParTipoFacturacion> listaDeTiposDeFactura = new ArrayList<ParTipoFacturacion>();
    private BigDecimal excento = new BigDecimal(BigInteger.ZERO);
    private BigDecimal ice = new BigDecimal(BigInteger.ZERO);
    private Boolean activaCodigoControl = true;
    private Boolean habilitaRazonSocial = false;
    private String nitEsObligatorio = null;
    private String razonSocialEsObligatorio = null;
    private String autorizacionEsObligatorio = null;
    private String codigoControlEsObligatorio = null;
    private String numeroFacturaEsObligatorio = null;
    private String mensajeCamposObligatorios = null;
    private String tipoAccion = "GUARDAR";
    private Long nit = 0L;
    private String razonSocial = "";
    private String numeroAutorizacion = "";
    private Boolean activaModificarFactura = false;
    private CntEntidad cntEntidad = null;

    //</editor-fold>
    @ManagedProperty(value = "#{cntAuxiliaresPorCuentaService}")
    private CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="METODOS">
    public CntFacturaDeCompraBacking() {
    }

    @PostConstruct
    public void initCntFacturaDeCompraBacking() {
        try {
            CntProveedor cntProveedor = new CntProveedor();
            if (getFromSessionIdDetalleComprobante() != null) {
                cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
                if (cntDetalleComprobante.getIdAntecesor() != null) {
                    if ((CntFacturacion) cntFacturacionService.obtieneFacturaPorDetalleComprobanteParaDelete((CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor())) != null) {
                        cntFacturacion = (CntFacturacion) cntFacturacionService.obtieneFacturaPorDetalleComprobanteParaDelete((CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor())).clone();
                        if (cntFacturacion.getCreditoFiscalTransitorio().equals("S")) {
                            activaSinCredito = false;
                        } else {
                            if (cntFacturacion.getIdProveedorCliente() != null) {
                                if (cntFacturacion.getMovimiento().equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo()) && cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo())) {
                                    ParTipoFacturacion parTipoFacturacion = (ParTipoFacturacion) parParametricasService.find(ParTipoFacturacion.class, "MINT");
                                    cntFacturacion.setParTipoFacturacion(parTipoFacturacion);
                                    cntFacturacion.setIdProveedorCliente(null);
                                } else {
                                    cntProveedor = cntProveedorService.find(CntProveedor.class, cntFacturacion.getIdProveedorCliente());
                                    nit = cntProveedor.getNit();
                                    razonSocial = cntProveedor.getRazonSocial();
                                    numeroAutorizacion = cntProveedor.getAutorizacion();
                                }
                            }
                        }
                    }
                    tipoAccion = "MODIFICAR";
                } else {
                    System.out.println("entra por idAntecesor nulo");
                    cntEntidad = cntDetalleComprobante.getCntEntidad();
                    cntFacturacion = new CntFacturacion();
                }
                cargaDatosFacturacion();
                setInSessionIdDetalleComprobante(null);
            } else {
                cntFacturacion = new CntFacturacion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (super.getFromSessionIdEntidadFacturacion() != null) {
            try {
                cntFacturacion = cntFacturacionService.find(CntFacturacion.class, super.getFromSessionIdEntidadFacturacion());
                nit = cntFacturacion.getNit();
                razonSocial = cntFacturacion.getRazonSocial();
                numeroAutorizacion = cntFacturacion.getNroAutorizacion();
                cntDetalleComprobante = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion);
                activaModificarFactura = true;
                super.setInSessionIdEntidadFacturacion(null);
            } catch (Exception e) {
            }

        }
    }

    public Date fechaMaxima() {
        return cntDetalleComprobante.getCntComprobante().getFecha();
    }

    public Boolean verificaTipoDeFactura() {
        if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo())) {
            return true;
        } else {
            if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo())) {
                return true;
            } else {
                if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.CREDITO_FISCAL_NO_DEDUCIBLE.getCodigo())) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    public String guardaFacturaDeCompra() {
        System.out.println("el numero de auto...................." + numeroAutorizacion);
        if (cntFacturacion.getCreditoFiscalTransitorio().equals("N")) {
            String[] datosValidados = cntFacturacionService.validaDatosDeFacturaDeCompra(cntFacturacion, nit, razonSocial, numeroAutorizacion);
            nitEsObligatorio = datosValidados[0];
            razonSocialEsObligatorio = datosValidados[1];
            autorizacionEsObligatorio = datosValidados[2];
            codigoControlEsObligatorio = datosValidados[3];
            numeroFacturaEsObligatorio = datosValidados[4];
            mensajeCamposObligatorios = datosValidados[5];
        } else {
            mensajeCamposObligatorios = "";
        }
        if (mensajeCamposObligatorios.equals("")) {
            try {
                if (cntFacturacion.getCodigoControl() == null) {
                    cntFacturacion.setCodigoControl("");
                }
                if (cntFacturacionService.validaFormatoCodigoControl(cntFacturacion.getCodigoControl())) {
                    cntFacturacion.setLoginUsuario(getLoginSession());
                    setPersistValues(cntFacturacion);
//                  inicio modificado para CPP
//                    cntDetalleComprobante.setCntFacturacion(cntFacturacion);
//                    cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
//                  fin modificado para CPP      
                    cntFacturacionService.guardaFacturaDeCompra(cntFacturacion, nit, razonSocial, numeroAutorizacion, cntDetalleComprobante.getIdDetalleComprobante());
                    if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.CENTROS_DE_COSTO.getCodigo()).getValor()).equals("1")) {
//                        if (cntEntidadesService.verificaEntidadCentroCosto(cntFacturacion.getCntDetalleComprobante().getCntEntidad())) {     
                        System.out.println("el detalle es::: " + cntDetalleComprobante.getCntEntidad());
//                        if (cntEntidadesService.verificaEntidadCentroCosto(cntDetalleComprobante.getCntEntidad())) {
                        if (cntDetalleComprobante.getCntEntidad().getHabilitaCentroCosto().equals("S")) {
                            setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                            return "distribucionDeCentroDeCosto";
                        }
                    }
                    if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.AUXILIAR.getCodigo()).getValor()).equals("1")) {
//                        if (cntFacturacion.getCntDetalleComprobante().getCntEntidad().getTieneAuxiliar().equals(EnumConfirmacion.SI.getCodigo())) {
                        if (cntDetalleComprobante.getCntEntidad().getTieneAuxiliar().equals(EnumConfirmacion.SI.getCodigo())) {
//                            setInSessionIdDetalleComprobante(cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
                            setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                            if (cntAuxiliaresPorCuentaService.verificaRelacionCntEntidadConCntAuxiliarPorCuenta(cntDetalleComprobante.getCntEntidad())) {
                                return "asignacionDeAuxiliaresADetalleComprobante";
                            }
                        }
                    }
                    if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.PROYECTO.getCodigo()).getValor()).equals("1")) {
//                        setInSessionIdDetalleComprobante(cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
                        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                        return "asignacionDeProyectosADetalleComprobante";
                    }
                    setInSessionIdEntidad(null);
                    setInSessionIdDetalleComprobante(null);
//                    setInSessionIdComprobante(cntFacturacion.getCntDetalleComprobante().getCntComprobante().getIdComprobante());
                    setInSessionIdComprobante(cntDetalleComprobante.getCntComprobante().getIdComprobante());

                    return "detalleComprobante";

                } else {
                    MessageUtils.addInfoMessage("El Codigo de Control no tiene el formato apropiado");
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MessageUtils.addErrorMessage("Los campos " + mensajeCamposObligatorios + "son obligatorios");
            return null;
        }
        return null;
    }

    public void activaNumeroFacturaAction(ValueChangeEvent e) {
        if (EnumParametrosDeAutorizacion.POLIZA_DE_IMPORTACION.getCodigo().equals(cntFacturacion.getParParametrosAutorizacion().getCodigo())) {
            activaNumeroFactura = true;
            cntFacturacion.setNroAutorizacion("3");
        } else {
            activaNumeroFactura = false;
        }
        if (EnumParametrosDeAutorizacion.ALQUILER.getCodigo().equals(cntFacturacion.getParParametrosAutorizacion().getCodigo())) {
            cntFacturacion.setNroAutorizacion("2");
        }
        if (EnumParametrosDeAutorizacion.BSP.getCodigo().equals(cntFacturacion.getParParametrosAutorizacion().getCodigo())) {
            cntFacturacion.setNroAutorizacion("1");
        }
        if (EnumParametrosDeAutorizacion.FACTURA.getCodigo().equals(cntFacturacion.getParParametrosAutorizacion().getCodigo())) {
            cntFacturacion.setNroAutorizacion("");
            buscarProveedor(e);
        }

    }

    public void activaIva(ValueChangeEvent e) throws Exception {
        cntFacturacion = cntFacturacionService.obtieneIVA(cntFacturacion, excento, ice, null, null);
        excento = cntFacturacion.getExcento().setScale(2, BigDecimal.ROUND_HALF_UP);
        ice = cntFacturacion.getIce().setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void activaSinCreditoFiscal(ValueChangeEvent e) {
        if (cntFacturacion.getCreditoFiscalTransitorio().equals("S")) {
            activaSinCredito = false;
        } else {
            activaSinCredito = true;
        }
    }

    public void activaCodigoControl(ValueChangeEvent e) {
        cntFacturacion.setNroAutorizacion(numeroAutorizacion);
        activaCodigoControl = cntFacturacionService.activaCodigoDeControl(cntFacturacion);
    }

    public void formatoCodigoControl(ValueChangeEvent e) {
        cntFacturacion.setCodigoControl(cntFacturacionService.completaFormatoCodigoControl(cntFacturacion.getCodigoControl()));
    }

    public void cargaDatosFacturacion() throws Exception {
        ParParametrosAutorizacion parametrosAutorizacion = (ParParametrosAutorizacion) parParametricasService.find(ParParametrosAutorizacion.class, EnumParametrosDeAutorizacion.FACTURA.getCodigo());
//        cntFacturacion.setCntDetalleComprobante(cntDetalleComprobante);
        cntFacturacion.setMonto(cntDetalleComprobante.getDebe() == null ? cntDetalleComprobante.getHaber() : cntDetalleComprobante.getDebe());
        cntFacturacion.setParParametrosAutorizacion(parametrosAutorizacion);
        tipoCambio = cntDetalleComprobante.getCntComprobante().getTipoCambio();
        excento = cntFacturacion.getExcento();
        ice = cntFacturacion.getIce();
        if (cntFacturacion.getFechaFactura() == null) {
            cntFacturacion.setFechaFactura(new Date());
        }                
        cntFacturacion = cntFacturacionService.obtieneIVA(cntFacturacion, excento, ice, cntEntidad, null);
    }

    public List<ParSucursal> listaTodasLasSucursales() {
        if (listaDeSucursales.isEmpty()) {
            listaDeSucursales = parParametricasService.listaTodasLasSucursal();
        }
        return listaDeSucursales;
    }

    public List<ParTipoFacturacion> listaTodosLosTiposDeFacturacion() {
        if (listaDeTiposDeFactura.isEmpty()) {
            listaDeTiposDeFactura = parParametricasService.listaLosTiposDeFactura();
        }
        return listaDeTiposDeFactura;
    }

//    public void buscarProveedor(ValueChangeEvent e) {
//        if (cntFacturacion.getNit() != null) {
//            if (cntFacturacion.getNit() > 999999) {
//                if (cntProveedorService.generaProveedorPorNit(cntFacturacion.getNit()) != null) {
//                    cntFacturacion.setRazonSocial(cntProveedorService.generaProveedorPorNit(cntFacturacion.getNit()).getRazonSocial());
//                    cntFacturacion.setNroAutorizacion(cntProveedorService.generaProveedorPorNit(cntFacturacion.getNit()).getAutorizacion());                    
//                    habilitaRazonSocial = true;
//                } else {
//                    habilitaRazonSocial = false;
//                    cntFacturacion.setRazonSocial("");
//                    cntFacturacion.setNroAutorizacion("");                    
//                }
//            } else {
//                habilitaRazonSocial = false;                
//                cntFacturacion.setRazonSocial("");
//                cntFacturacion.setNroAutorizacion("");
//            }
//        }
//        activaCodigoControl = cntFacturacionService.activaCodigoDeControl(cntFacturacion);
//    }
    public void buscarProveedor(ValueChangeEvent e) {
        CntProveedor cntProveedor = new CntProveedor();
        if (nit != null && nit != 0L) {
            if (nit > 999999) {
                cntProveedor = cntProveedorService.generaProveedorPorNit(nit);
                if (cntProveedor != null) {
                    cntFacturacion.setIdProveedorCliente(cntProveedor.getIdProveedor());
                    numeroAutorizacion = cntProveedor.getAutorizacion();
                    razonSocial = cntProveedor.getRazonSocial();
                    habilitaRazonSocial = true;
                } else {
                    habilitaRazonSocial = false;
                    cntFacturacion.setIdProveedorCliente(null);
                    cntFacturacion.setNroAutorizacion("");
                    numeroAutorizacion = "";
                    razonSocial = "";
                }
            } else {
                habilitaRazonSocial = false;
                cntFacturacion.setIdProveedorCliente(null);
                cntFacturacion.setNroAutorizacion("");
                numeroAutorizacion = "";
                razonSocial = "";
            }
        }
        activaCodigoControl = cntFacturacionService.activaCodigoDeControl(cntFacturacion);
    }

    public BigDecimal divideDosBigDecimal(BigDecimal dividendo, BigDecimal divisor) {
        return cntFacturacionService.divideDosBigDecimal(dividendo, divisor);
    }

    public String cancelaFacturaDeCompra() throws Exception {
//        cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntFacturacion.getCntDetalleComprobante());
//        setInSessionIdComprobante(cntFacturacion.getCntDetalleComprobante().getCntComprobante().getIdComprobante());
        setInSessionIdComprobante(cntDetalleComprobante.getCntComprobante().getIdComprobante());
        cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
        return "detalleComprobante";
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET y SET">
    public String getSucursalCodigo() {
        return sucursalCodigo;
    }

    public void setSucursalCodigo(String sucursalCodigo) {
        this.sucursalCodigo = sucursalCodigo;
    }

    public String getCreditoFiscalTransitorio() {
        return creditoFiscalTransitorio;
    }

    public void setCreditoFiscalTransitorio(String creditoFiscalTransitorio) {
        this.creditoFiscalTransitorio = creditoFiscalTransitorio;
    }

    public String getVendeCcombustible() {
        return vendeCcombustible;
    }

    public void setVendeCcombustible(String vendeCcombustible) {
        this.vendeCcombustible = vendeCcombustible;
    }

    public String getTipoDeFactura() {
        return tipoDeFactura;
    }

    public void setTipoDeFactura(String tipoDeFactura) {
        this.tipoDeFactura = tipoDeFactura;
    }

    public String getParametroAutorizacion() {
        return parametroAutorizacion;
    }

    public void setParametroAutorizacion(String parametroAutorizacion) {
        this.parametroAutorizacion = parametroAutorizacion;
    }

    public Date getDiaFactura() {
        return diaFactura;
    }

    public void setDiaFactura(Date diaFactura) {
        this.diaFactura = diaFactura;
    }

    public Boolean getActivaNumeroFactura() {
        return activaNumeroFactura;
    }

    public void setActivaNumeroFactura(Boolean activaNumeroFactura) {
        this.activaNumeroFactura = activaNumeroFactura;
    }

    public CntFacturacion getCntFacturacion() {
        return cntFacturacion;
    }

    public void setCntFacturacion(CntFacturacion cntFacturacion) {
        this.cntFacturacion = cntFacturacion;
    }

    public List<ParSucursal> getListaDeSucursales() {
        return listaDeSucursales;
    }

    public void setListaDeSucursales(List<ParSucursal> listaDeSucursales) {
        this.listaDeSucursales = listaDeSucursales;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public List<ParTipoFacturacion> getListaDeTiposDeFactura() {
        return listaDeTiposDeFactura;
    }

    public void setListaDeTiposDeFactura(List<ParTipoFacturacion> listaDeTiposDeFactura) {
        this.listaDeTiposDeFactura = listaDeTiposDeFactura;
    }

    public CntDetalleComprobante getCntDetalleComprobante() {
        return cntDetalleComprobante;
    }

    public void setCntDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        this.cntDetalleComprobante = cntDetalleComprobante;
    }

    public BigDecimal getMontoDolares() {
        return montoDolares;
    }

    public void setMontoDolares(BigDecimal montoDolares) {
        this.montoDolares = montoDolares;
    }

    public BigDecimal getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(BigDecimal tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public CntProveedorService getCntProveedorService() {
        return cntProveedorService;
    }

    public void setCntProveedorService(CntProveedorService cntProveedorService) {
        this.cntProveedorService = cntProveedorService;
    }

    public Boolean getActivaSinCredito() {
        return activaSinCredito;
    }

    public void setActivaSinCredito(Boolean activaSinCredito) {
        this.activaSinCredito = activaSinCredito;
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public String[] getListaPrueba() {
        return listaPrueba;
    }

    public void setListaPrueba(String[] listaPrueba) {
        this.listaPrueba = listaPrueba;
    }

    public CntFacturacionService getCntFacturacionService() {
        return cntFacturacionService;
    }

    public void setCntFacturacionService(CntFacturacionService cntFacturacionService) {
        this.cntFacturacionService = cntFacturacionService;
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

    public Boolean getActivaCodigoControl() {
        return activaCodigoControl;
    }

    public void setActivaCodigoControl(Boolean activaCodigoControl) {
        this.activaCodigoControl = activaCodigoControl;
    }

    public Boolean getHabilitaRazonSocial() {
        return habilitaRazonSocial;
    }

    public void setHabilitaRazonSocial(Boolean habilitaRazonSocial) {
        this.habilitaRazonSocial = habilitaRazonSocial;
    }

    public String getAutorizacionEsObligatorio() {
        return autorizacionEsObligatorio;
    }

    public void setAutorizacionEsObligatorio(String autorizacionEsObligatorio) {
        this.autorizacionEsObligatorio = autorizacionEsObligatorio;
    }

    public String getNitEsObligatorio() {
        return nitEsObligatorio;
    }

    public void setNitEsObligatorio(String nitEsObligatorio) {
        this.nitEsObligatorio = nitEsObligatorio;
    }

    public String getCodigoControlEsObligatorio() {
        return codigoControlEsObligatorio;
    }

    public void setCodigoControlEsObligatorio(String codigoControlEsObligatorio) {
        this.codigoControlEsObligatorio = codigoControlEsObligatorio;
    }

    public String getNumeroFacturaEsObligatorio() {
        return numeroFacturaEsObligatorio;
    }

    public void setNumeroFacturaEsObligatorio(String numeroFacturaEsObligatorio) {
        this.numeroFacturaEsObligatorio = numeroFacturaEsObligatorio;
    }

    public String getRazonSocialEsObligatorio() {
        return razonSocialEsObligatorio;
    }

    public void setRazonSocialEsObligatorio(String razonSocialEsObligatorio) {
        this.razonSocialEsObligatorio = razonSocialEsObligatorio;
    }

    public String getMensajeCamposObligatorios() {
        return mensajeCamposObligatorios;
    }

    public void setMensajeCamposObligatorios(String mensajeCamposObligatorios) {
        this.mensajeCamposObligatorios = mensajeCamposObligatorios;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(String tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public Long getNit() {
        return nit;
    }

    public void setNit(Long nit) {
        this.nit = nit;
    }

    //</editor-fold>
    public CntAuxiliaresPorCuentaService getCntAuxiliaresPorCuentaService() {
        return cntAuxiliaresPorCuentaService;
    }

    public void setCntAuxiliaresPorCuentaService(CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService) {
        this.cntAuxiliaresPorCuentaService = cntAuxiliaresPorCuentaService;
    }

    public Boolean getActivaModificarFactura() {
        return activaModificarFactura;
    }

    public void setActivaModificarFactura(Boolean activaModificarFactura) {
        this.activaModificarFactura = activaModificarFactura;
    }

    public String modificaFactura_Action() {
        cntFacturacion.setNit(nit);
        cntFacturacion.setRazonSocial(razonSocial);
        cntFacturacion.setNroAutorizacion(numeroAutorizacion);
        setPersistValues(cntFacturacion);
        cntFacturacionService.merge(cntFacturacion);
        return "reporteLibroCompras";
    }

    public String vuelveLibrodeVenta_Action() {
        return "reporteLibroCompras";
    }

    public String iconoRegistra() {
        return EnumIconosPrimeFaces.GUARDAR.getCodigo();
    }

    public String iconoAtras() {
        return EnumIconosPrimeFaces.ATRAS.getCodigo();
    }

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    
}
