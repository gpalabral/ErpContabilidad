package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.entidades.cnt.CntProveedor;
import com.bap.erp.modelo.entidades.cnf.ParParametrosAutorizacion;
import com.bap.erp.modelo.entidades.cnf.ParSucursal;
import com.bap.erp.modelo.entidades.cnf.ParTipoFacturacion;
import com.bap.erp.modelo.enums.EnumConfirmacion;
import com.bap.erp.modelo.enums.EnumEmpresa_Varios;
import com.bap.erp.modelo.enums.EnumEstado;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "cntFacturaDeVentaBacking")
@ViewScoped
public class CntFacturaDeVentaBacking extends AbstractManagedBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="INYECCIONES">
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntFacturacionService}")
    private CntFacturacionService cntFacturacionService;
    @ManagedProperty(value = "#{cntProveedorService}")
    private CntProveedorService cntProveedorService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="VARIABLES">
    private String sucursalCodigo;
    private String parametroAutorizacion;
    private CntDetalleComprobante cntDetalleComprobante;
    private CntFacturacion cntFacturacion;
    private List<ParSucursal> listaDeSucursales = new ArrayList<ParSucursal>();
    private BigDecimal tipoCambio;
    private BigDecimal montoDolares;
    private List<ParTipoFacturacion> listaDeTiposDeFactura = new ArrayList<ParTipoFacturacion>();
    private BigDecimal excento;
    private BigDecimal ice;
    private Boolean activaCodigoControl = true;
    private Boolean activaCampoProveedor = false;
    private String nitEsObligatorio = null;
    private String razonSocialEsObligatorio = null;
    private String autorizacionEsObligatorio = null;
    private String mensajeCamposObligatorios = null;
    private String tipoAccion = "GUARDAR";
    private Long nit;
    private String razonSocial;
    private String numeroAutorizacion = "";
    private Boolean activaModificarFactura = false;
    private boolean habilitaVerFactura = false;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="METODOS">
    @ManagedProperty(value = "#{cntAuxiliaresPorCuentaService}")
    private CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService;

    public CntFacturaDeVentaBacking() {
    }

    @PostConstruct
    public void initCntFacturaDeVentaBacking() {

        try {
            CntProveedor cntProveedor = new CntProveedor();
            if (getFromSessionIdDetalleComprobante() != null) {
                cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
                if (cntDetalleComprobante.getIdAntecesor() != null) {
                    if ((CntFacturacion) cntFacturacionService.obtieneFacturaPorDetalleComprobanteParaDelete((CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor())) != null) {
//                        cntFacturacion = (CntFacturacion) cntFacturacionService.obtieneFacturaPorDetalleComprobanteParaDeleteFacturaVenta((CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor())).clone();
                        cntFacturacion = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor()).getCntFacturacion();
                        tipoAccion = "MODIFICAR";
                        if (cntFacturacion.getIdProveedorCliente() != null) {
                            if (cntFacturacion.getMovimiento().equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo()) && cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo())) {
                                cntFacturacion.setParTipoFacturacion(null);
                                cntFacturacion.setIdProveedorCliente(null);
                            } else {
                                cntProveedor = cntProveedorService.find(CntProveedor.class, cntFacturacion.getIdProveedorCliente());
                                nit = cntProveedor.getNit();
                                razonSocial = cntProveedor.getRazonSocial();
//                                numeroAutorizacion = cntProveedor.getAutorizacion();
                                numeroAutorizacion = cntFacturacion.getNroAutorizacion();
                            }
                        }
                    } else {
                        cntFacturacion = new CntFacturacion();
                    }

                } else {
                    cntFacturacion = new CntFacturacion();
                }
                setInSessionIdDetalleComprobante(null);
                cargaDatosFacturacion();
            }
            if (super.getFromSessionIdEntidadFacturacion() != null) {
                cntFacturacion = cntFacturacionService.find(CntFacturacion.class, super.getFromSessionIdEntidadFacturacion());
                nit = cntFacturacion.getNit();
                razonSocial = cntFacturacion.getRazonSocial();
                numeroAutorizacion = cntFacturacion.getNroAutorizacion();
                activaModificarFactura = true;
                if (cntFacturacion.getEstado().equals(EnumEstado.ANULADO.getCodigo())) {
                    habilitaVerFactura = false;
                }
                if (cntFacturacion.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo())) {
                    habilitaVerFactura = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String guardarFacturaVenta_Accion() {
        cntFacturacion.setNroAutorizacion(numeroAutorizacion);
        String[] datosValidados = cntFacturacionService.validaCamposFacturaVenta(cntFacturacion, nit, razonSocial, numeroAutorizacion);
        nitEsObligatorio = datosValidados[0];
        razonSocialEsObligatorio = datosValidados[1];
        autorizacionEsObligatorio = datosValidados[2];
        mensajeCamposObligatorios = datosValidados[3];
        if (mensajeCamposObligatorios.equals("")) {
            try {
                if (cntFacturacion.getCodigoControl() == null) {
                    cntFacturacion.setCodigoControl("");
                }
                if (cntFacturacionService.validaFormatoCodigoControl(cntFacturacion.getCodigoControl())) {
                    cntFacturacion.setLoginUsuario(getLoginSession());
                    if (cntFacturacion.getIdFacturacion() != null) {
                        setMergeValues(cntFacturacion);
                        cntFacturacionService.guardaFacturaDeVentaModifica(cntFacturacion, nit, razonSocial, numeroAutorizacion, cntDetalleComprobante.getIdDetalleComprobante());
                    } else {
                        setPersistValues(cntFacturacion);
                        cntFacturacionService.guardaFacturaDeVenta(cntFacturacion, nit, razonSocial, numeroAutorizacion, cntDetalleComprobante.getIdDetalleComprobante());
                    }
                    //aumentado para centro de costos 
                    if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.CENTROS_DE_COSTO.getCodigo()).getValor()).equals("1")) {
//                        if (!cntEntidadesService.verificaEntidadCentroCosto(cntFacturacion.getCntDetalleComprobante().getCntEntidad())) {
//                        if (!cntEntidadesService.verificaEntidadCentroCosto(cntDetalleComprobante.getCntEntidad())) {
                        if (cntDetalleComprobante.getCntEntidad().getHabilitaCentroCosto().equals("S")) {
                            setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                            return "distribucionDeCentroDeCosto";
                        }
                    }
                    //aumentado para verificasr si una cuenta tiene auxiliares y proyectos
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
                    } else {
//                        setInSessionIdComprobante(cntFacturacion.getCntDetalleComprobante().getCntComprobante().getIdComprobante());
                        setInSessionIdComprobante(cntDetalleComprobante.getCntComprobante().getIdComprobante());
                        return "detalleComprobante";
                    }
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

    public void cargaDatosFacturacion() throws Exception {
        ParParametrosAutorizacion parametrosAutorizacion = (ParParametrosAutorizacion) parParametricasService.find(ParParametrosAutorizacion.class, EnumParametrosDeAutorizacion.FACTURA.getCodigo());
        cntFacturacion.setMonto(cntDetalleComprobante.getDebe() == null ? cntDetalleComprobante.getHaber() : cntDetalleComprobante.getDebe());
        cntFacturacion.setParParametrosAutorizacion(parametrosAutorizacion);
        tipoCambio = cntDetalleComprobante.getCntComprobante().getTipoCambio();
//        cntFacturacion.setCntDetalleComprobante(cntDetalleComprobante);
        excento = cntFacturacion.getExcento().setScale(2, BigDecimal.ROUND_HALF_UP);
        ice = cntFacturacion.getIce().setScale(2, BigDecimal.ROUND_HALF_UP);
        if (cntFacturacion.getFechaFactura() == null) {
            cntFacturacion.setFechaFactura(new Date());
        }
        if (cntFacturacion.getNroInicial() == null) {
            cntFacturacion.setNroInicial(1L);
            cntFacturacion.setNroFinal(cntFacturacion.getNroInicial());
        }
        cntFacturacion = cntFacturacionService.obtieneIVA(cntFacturacion, excento, ice, null, null);
        cntFacturacion.setLoginUsuario(getLoginSession());
    }

    public void activaCodigoControlVenta(ValueChangeEvent e) {
        cntFacturacion.setNroAutorizacion(numeroAutorizacion);
        activaCodigoControl = cntFacturacionService.activaCodigoDeControl(cntFacturacion);
    }

    public void activaIva(ValueChangeEvent e) throws Exception {
        cntFacturacion = cntFacturacionService.obtieneIVA(cntFacturacion, excento, ice, null, null);
        excento = cntFacturacion.getExcento().setScale(2, BigDecimal.ROUND_HALF_UP);
        ice = cntFacturacion.getIce().setScale(2, BigDecimal.ROUND_HALF_UP);
    }

//    public void buscarProveedor(ValueChangeEvent e) {
//        if (nit != null) {
//            if (nit > 999999) {
//                if (cntProveedorService.generaProveedorPorNit(nit) != null) {
//                    cntFacturacion.setIdProveedorCliente(cntProveedorService.generaProveedorPorNit(nit).getIdProveedor());
//                    activaCampoProveedor = true;
//                } else {
//                    CntProveedor cntProveedor = new CntProveedor();
//                    cntProveedor.setNit(cntProveedor.getNit());
//                    activaCampoProveedor = false;                    
//                }
//            } else {
//                activaCampoProveedor = false;                
//            }
//        }
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
//                    habilitaRazonSocial = true;
                } else {
//                    habilitaRazonSocial = false;
                    cntFacturacion.setIdProveedorCliente(null);
                    cntFacturacion.setNroAutorizacion("");
                    numeroAutorizacion = "";
                    razonSocial = "";
                }
            } else {
//                habilitaRazonSocial = false;
                cntFacturacion.setIdProveedorCliente(null);
                cntFacturacion.setNroAutorizacion("");
                numeroAutorizacion = "";
                razonSocial = "";
            }
        }
        activaCodigoControl = cntFacturacionService.activaCodigoDeControl(cntFacturacion);
    }

    public List<ParSucursal> listaTodasLasSucursales() {
        if (listaDeSucursales.isEmpty()) {
            listaDeSucursales = parParametricasService.listaTodasLasSucursal();
        }
        return listaDeSucursales;
    }

    public void actualizaNumeroAutorizacion(ValueChangeEvent e) {

        if (parParametricasService.encuentraParSucursal(cntFacturacion.getSucursal()) != null) {
            cntFacturacion.setNroAutorizacion((parParametricasService.encuentraParSucursal(cntFacturacion.getSucursal())).getAutorizacion());
            activaCodigoControl = cntFacturacionService.activaCodigoDeControl(cntFacturacion);

        } else {
            cntFacturacion.setNroAutorizacion(" ");
        }
    }

    public void actualizaNumeroFacturaFin(ValueChangeEvent e) {
        cntFacturacion.setNroFinal(cntFacturacion.getNroInicial());
    }

    public void validaNumeroFacturaFin(ValueChangeEvent e) {
        if (cntFacturacion.getNroFinal() < cntFacturacion.getNroInicial()) {
            cntFacturacion.setNroFinal(cntFacturacion.getNroInicial());
        }

    }

    public String cancelaFacturaDeVenta() throws Exception {
        if (cntFacturacion.getIdFacturacion() != null) {
//            cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante()));
            cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()));
        } else {
//            cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante()));
            cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()));
        }
//        setInSessionIdComprobante(cntFacturacion.getCntDetalleComprobante().getCntComprobante().getIdComprobante());
        setInSessionIdComprobante(cntDetalleComprobante.getCntComprobante().getIdComprobante());
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

    public String getParametroAutorizacion() {
        return parametroAutorizacion;
    }

    public void setParametroAutorizacion(String parametroAutorizacion) {
        this.parametroAutorizacion = parametroAutorizacion;
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public CntDetalleComprobante getCntDetalleComprobante() {
        return cntDetalleComprobante;
    }

    public void setCntDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        this.cntDetalleComprobante = cntDetalleComprobante;
    }

    public CntFacturacion getCntFacturacion() {
        return cntFacturacion;
    }

    public void setCntFacturacion(CntFacturacion cntFacturacion) {
        this.cntFacturacion = cntFacturacion;
    }

    public CntFacturacionService getCntFacturacionService() {
        return cntFacturacionService;
    }

    public void setCntFacturacionService(CntFacturacionService cntFacturacionService) {
        this.cntFacturacionService = cntFacturacionService;
    }

    public CntProveedorService getCntProveedorService() {
        return cntProveedorService;
    }

    public void setCntProveedorService(CntProveedorService cntProveedorService) {
        this.cntProveedorService = cntProveedorService;
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

    public List<ParTipoFacturacion> getListaDeTiposDeFactura() {
        return listaDeTiposDeFactura;
    }

    public void setListaDeTiposDeFactura(List<ParTipoFacturacion> listaDeTiposDeFactura) {
        this.listaDeTiposDeFactura = listaDeTiposDeFactura;
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

    public Boolean getActivaCodigoControl() {
        return activaCodigoControl;
    }

    public void setActivaCodigoControl(Boolean activaCodigoControl) {
        this.activaCodigoControl = activaCodigoControl;
    }

    public Boolean getActivaCampoProveedor() {
        return activaCampoProveedor;
    }

    public void setActivaCampoProveedor(Boolean activaCampoProveedor) {
        this.activaCampoProveedor = activaCampoProveedor;
    }

    public String getAutorizacionEsObligatorio() {
        return autorizacionEsObligatorio;
    }

    public void setAutorizacionEsObligatorio(String autorizacionEsObligatorio) {
        this.autorizacionEsObligatorio = autorizacionEsObligatorio;
    }

    public String getMensajeCamposObligatorios() {
        return mensajeCamposObligatorios;
    }

    public void setMensajeCamposObligatorios(String mensajeCamposObligatorios) {
        this.mensajeCamposObligatorios = mensajeCamposObligatorios;
    }

    public String getNitEsObligatorio() {
        return nitEsObligatorio;
    }

    public void setNitEsObligatorio(String nitEsObligatorio) {
        this.nitEsObligatorio = nitEsObligatorio;
    }

    public String getRazonSocialEsObligatorio() {
        return razonSocialEsObligatorio;
    }

    public void setRazonSocialEsObligatorio(String razonSocialEsObligatorio) {
        this.razonSocialEsObligatorio = razonSocialEsObligatorio;
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

    public String iconoRegistra() {
        return EnumIconosPrimeFaces.GUARDAR.getCodigo();
    }

    public String iconoEdita() {
        return EnumIconosPrimeFaces.MODIFICAR.getCodigo();
    }

    public String iconoElimina() {
        return EnumIconosPrimeFaces.ELIMINAR.getCodigo();
    }

    public String iconoAtras() {
        return EnumIconosPrimeFaces.ATRAS.getCodigo();
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

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }
    //</editor-fold>

    public CntAuxiliaresPorCuentaService getCntAuxiliaresPorCuentaService() {
        return cntAuxiliaresPorCuentaService;
    }

    public void setCntAuxiliaresPorCuentaService(CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService) {
        this.cntAuxiliaresPorCuentaService = cntAuxiliaresPorCuentaService;
    }

//<editor-fold defaultstate="collapsed" desc="Metodos para modificar factura Jacqueline Carvajal 12/06/2015">
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
        return "reporteLibroVentas";
    }

    public String vuelveLibrodeVenta_Action() {
        System.out.println("...id fact es volviendo : " + getFromSessionIdEntidadFacturacion());
        return "reporteLibroVentas";
    }
//</editor-fold>

    public boolean isHabilitaVerFactura() {
        return habilitaVerFactura;
    }

    public void setHabilitaVerFactura(boolean habilitaVerFactura) {
        this.habilitaVerFactura = habilitaVerFactura;
    }

}
