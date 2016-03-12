package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import com.bap.erp.modelo.enums.EnumAjuste;
import com.bap.erp.modelo.enums.EnumConfirmacion;
import com.bap.erp.modelo.enums.EnumEmpresa_Varios;
import com.bap.erp.modelo.enums.EnumEstado;
import com.bap.erp.modelo.enums.EnumFacturaCompraCombustible;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.enums.EnumParametroAutomatico;
import com.bap.erp.modelo.enums.EnumRetencionGrossing;
import com.bap.erp.modelo.enums.EnumTipoCalculoAutomatico;
import com.bap.erp.modelo.enums.EnumTipoComprobante;
import com.bap.erp.modelo.enums.EnumTipoMoneda;
import com.bap.erp.modelo.enums.EnumTipoMovimiento;
import com.bap.erp.modelo.enums.EnumTipoRetencion;
import com.bap.erp.modelo.enums.EnumTransaccionRealizada;
import com.bap.erp.modelo.servicios.cnt.*;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "cntFormularioComprobanteBacking")
@ViewScoped
public class CntFormularioComprobanteBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;
    @ManagedProperty(value = "#{cntTipoCambioService}")
    private CntTipoCambioService cntTipoCambioService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntParametroAutomaticoService}")
    private CntParametroAutomaticoService cntParametroAutomaticoService;
    @ManagedProperty(value = "#{cntProyectoService}")
    private CntProyectoService cntProyectoService;
    @ManagedProperty(value = "#{cntAuxiliaresPorCuentaService}")
    private CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService;
    private CntComprobante cntComprobante;
    private String tituloTipoComprobante;
    private String tituloTipoMoneda;
    private String tipoMoneda;
    private BigDecimal convierte;
    private BigDecimal sumaTotalDebe;
    private BigDecimal sumaTotalHaber;
    private List<CntDetalleComprobante> cntDetalleComprobantesList = new ArrayList<CntDetalleComprobante>();
    private List<CntDetalleComprobante> listaCntDetalleComprobanteElegidaParaQuitarse = new ArrayList<CntDetalleComprobante>();
    private Boolean muestraMensajeSiExistePendientes;
    private Date fechaActual;
    private List<ParTipoComprobante> iiposComprobantesList;
    private CntDetalleComprobante cntDetalleComprobante;
    private CntEntidad cntEntidad;
    private CntEntidad cntEntidadSelecion;
    private Boolean activaBotonGuardar;
    private Boolean swDebe;
    private Boolean swHaber;
    private Boolean pulsoSwDebe;
    private Boolean pulsoSwHaber;
    private Boolean activaFacturaDeVenta;
    private Boolean activaSinFactura;
    private Boolean activaRetencion;
    private String nombreRetencion;
    private Boolean activaRetencionGrossingUp;
    private String nombreRetencionGrossingUp;
    private Boolean activaCreditoFiscalNoDeducible;
    private Boolean activaAjuste;
    private String nombreAjuste;
    private Boolean activaPanelNuevoDetalleComprobante;
    private Boolean activaFacturaDeCompraSinCombustible;
    private Boolean activaFacturaDeCompraConCombustible;
    private CntDetalleComprobante selecDetalleComprobanteParaInsertar;
    private Boolean activaBotonInsertar;
    private Boolean swActivaRetenciones;
    private String tipoRetencion;
    private Boolean activaBotonParaCuentasNinguno;
    private Boolean activaBotonModificar;
    private BigDecimal diferencia;
    private String remitente;
    private int numeroMovimientos;
    private Boolean swModificaDetalleComprobante;
    private Boolean activaBotonModificarOpciones;
    private Boolean activaPanelMotivoAnulacion;
    private String colorEstado;
    private Boolean desahabilitaBotonNuevo = false;
    private int tipodemonedas;
    private Boolean activaModificacion = false;
    private Boolean activaControlGlosa = true;
    private Long inicioImpresion;
    private Long finImpresion;
    private String periodo = " ";
    private String anio = " ";
    private String tipo;
    private String moneda = "BOL";
    private Boolean opcionImpresion = true;
    private String nombreVentanaDetalleComprobante;
    private Boolean cambiaTipoDeMonedaMontos = true;
    private BigDecimal sumaTotalDebeBoliviano;
    private BigDecimal sumaTotalHaberBoliviano;
    private BigDecimal sumaTotalDebeDolar;
    private BigDecimal sumaTotalHaberDolar;
    private Boolean activaDesactivaBotonesDetalle;
    private CntFacturacion cntFacturacionNumero;

    private CntEntidad entidadModificaDetalle;

    public CntFormularioComprobanteBacking() {
    }

    @PostConstruct
    void initCntFormularioComprobanteBacking() {
        try {
            activaDesactivaBotonesDetalle = getFromSessionActivaDesactivaBotonesDetalle();

            fechaActual = cntTipoCambioService.ultimoCntTipoCambioRegistrado().getFecha();
            setInSessionIdEntidad3(null);
            if (super.getFromSessionIdComprobante() != null) {
                cntComprobante = cntComprobantesService.find(CntComprobante.class, super.getFromSessionIdComprobante());
                fechaActual = cntComprobante.getFecha();
                setInSessionIdComprobante(null);
                inicializaDatos();
                remitente = cntDetalleComprobanteService.encuentraTipoComprobante(cntComprobante);
                activaPanelMotivoAnulacion = cntComprobante.getEstado().equals(EnumEstado.PROCESO_ANULACION.getCodigo());
                if (cntComprobante.getIdComprobante() != null && cntComprobantesService.verificaSiSeCreaOModifica(cntComprobante)) {
                    activaModificacion = !activaPanelMotivoAnulacion;
                } else {
                    cntComprobante.setTipoMoneda(EnumTipoMoneda.AMBOS.getCodigo());
                }
                if (!cntComprobantesService.verificaDetalleComprobantePendientesPorComprobante(cntComprobante)) {
                    actualizaComprobante();
                }
            }
            if (getFromSessionIdDetalleComprobante() != null) {
                cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
                cntEntidad = cntEntidadesService.find(CntEntidad.class, cntDetalleComprobante.getCntEntidad().getIdEntidad());
                CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
                swDebe = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.DEBE.getCodigo());
                swHaber = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.HABER.getCodigo());
            } else {
                cntDetalleComprobante = new CntDetalleComprobante();
            }
            cntEntidad = new CntEntidad();
            cntEntidadSelecion = new CntEntidad();
            activaPanelNuevoDetalleComprobante = true;
            activaBotonGuardar = true;
            activaBotonInsertar = true;
            swActivaRetenciones = false;
            tipoRetencion = "";
            activaBotonParaCuentasNinguno = true;
            activaBotonModificar = true;
            swModificaDetalleComprobante = false;
            activaBotonModificarOpciones = true;
            if (cntComprobante != null) {
                if (cntComprobante.getGlosaComprobante() == null || cntComprobante.getGlosaComprobante().equals("")) {
                    if (!cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(cntComprobante).isEmpty()) {
                        cntComprobante.setGlosaComprobante(cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(cntComprobante).get(0).getGlosa());
                    }
                }
            }
            if (getFromSessionIdEntidad() != null) {
                cntEntidadSelecion = cntEntidadesService.find(CntEntidad.class, getFromSessionIdEntidad());
                cntEntidad = cntEntidadSelecion;
//                if (getFromSessionIdEntidad3() != null) {
//                    entidadModificaDetalle = cntEntidadesService.find(CntEntidad.class, getFromSessionIdEntidad3());
//                    cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
//                    cntDetalleComprobante.setCntEntidad(entidadModificaDetalle);
//                    setInSessionIdEntidad3(null);
//                    setInSessionIdDetalleComprobante(null);
//                }                
                org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoDetalle').show()");

//                if (getFromSessionTipoRetencionOGrossing() != null) {
//                    swActivaRetenciones=true;
//                    tipoRetencion=getFromSessionTipoRetencionOGrossing();
//                    activaRetencionesProcesos();
//                }else{
//                    org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoDetalle').show()");
//                }
                super.setInSessionIdEntidad(null);
            }
            if (getFromSessionIdEntidadModificacionDetalle() != null) {
                entidadModificaDetalle = cntEntidadesService.find(CntEntidad.class, getFromSessionIdEntidadModificacionDetalle());
                cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
                cntDetalleComprobante.setCntEntidad(entidadModificaDetalle);
                cntDetalleComprobante = cntDetalleComprobanteService.obtieneMontoOriginal(cntDetalleComprobante);
                cntEntidad = cntEntidadSelecion = entidadModificaDetalle;

                swModificaDetalleComprobante = true;
                setInSessionIdEntidadModificacionDetalle(null);
                setInSessionIdDetalleComprobante(null);
                cntDetalleComprobante.setCntFacturacion(null);
                CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntDetalleComprobante.getCntEntidad());
                cntEntidadSelecion = cntDetalleComprobante.getCntEntidad();
                cntEntidad = cntDetalleComprobante.getCntEntidad();
                swDebe = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.DEBE.getCodigo());
                swHaber = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.HABER.getCodigo());
                activaBotonGuardar = true;
                activaBotonParaCuentasNinguno = true;
//                activaBotonModificar = true;
                swModificaDetalleComprobante = true;
                activaControlGlosa = false;
                org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoDetalle').show()");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void actualizaDatosDebeAndHaber() {
//        cntEntidadSelecion = cntEntidad;
        if (cntEntidadSelecion.getIdEntidad() != null) {
            CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidadSelecion);
            swDebe = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.DEBE.getCodigo());
            swHaber = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.HABER.getCodigo());
        } else {
            MessageUtils.addErrorMessage("Debe seleccionar una cuenta para poder llenar la glosa.");
        }
    }

    public void actualizaDatosEntidad() {
        if (cntEntidad.getNivel() == 1) {
            cntEntidadSelecion = cntEntidad;
            activaControlGlosa = true;
            setInSessionActivaDesactivaBotonesDetalle(false);
            activaDesactivaBotonesDetalle = getFromSessionActivaDesactivaBotonesDetalle();
        } else {
            MessageUtils.addErrorMessage("Seleccione una cuenta de ultimo nivel para realizar el movimiento");
        }
    }

    public String activaRetencionesProcesos() {
        if (swActivaRetenciones) {
            try {
                setInSessionActivaDesactivaBotonesDetalle(false);
                activaDesactivaBotonesDetalle = getFromSessionActivaDesactivaBotonesDetalle();
                if (cntDetalleComprobante.getIdDetalleComprobante() == null) {
                    cntDetalleComprobante = cntDetalleComprobanteService.guardaRetencionGrossing(cntDetalleComprobante, cntEntidad, tipoRetencion);
                } else {
                    setMergeValues(cntDetalleComprobante);
                    cntDetalleComprobante = cntDetalleComprobanteService.guardaRetencionGrossingModifica(cntDetalleComprobante, cntEntidad, tipoRetencion);
                }
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.CENTROS_DE_COSTO.getCodigo()).getValor()).equals("1")) {
                    if (cntEntidadesService.verificaEntidadCentroCosto(cntDetalleComprobante.getCntEntidad())) {
                        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                        return "distribucionDeCentroDeCosto";
                    }
                }
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.AUXILIAR.getCodigo()).getValor()).equals("1")) {
                    if (cntDetalleComprobante.getCntEntidad().getTieneAuxiliar().equals(EnumConfirmacion.SI.getCodigo())) {
                        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                        return "asignacionDeAuxiliaresADetalleComprobante";
                    }
                }
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.PROYECTO.getCodigo()).getValor()).equals("1")) {
                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                    return "asignacionDeProyectosADetalleComprobante";
                } else {
                    reiniciaValoresPanelNuevoDetalleComprobante();
                    return "formularioComprobante";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            actualizaDatosEntidad();
//            actualizaDatosDebeAndHaber();
        }
        return null;
    }

    public void activaSwRetencionProcesos() {
        swActivaRetenciones = true;
    }

    public void inicializaDatos() throws Exception {
//        if (super.getFromSessionIdComprobanteModifica() == null) {
//            cntComprobante.setNumero(cntComprobantesService.ultimoNumeroComprobante(cntComprobante));
//        }
        if (cntComprobante.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
            if (cntComprobante.getDescripcion().isEmpty()) {
                cntComprobante.setNumero(cntComprobantesService.ultimoNumeroComprobante(cntComprobante));
            }
//            cntComprobante.setNumero(0L);
        }
        tituloTipoComprobante = cntComprobante.getParTipoComprobante().getDescripcion();
        tipoMoneda = EnumTipoMoneda.BOLIVIANOS.getCodigo();
        tituloTipoMoneda = "BOLIVIANOS";
        convierte = new BigDecimal("1");
        sumaTotalDebe = cntDetalleComprobanteService.sumaDebeComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante));
        sumaTotalHaber = cntDetalleComprobanteService.sumaHaberComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante));
        sumaTotalDebeBoliviano = cntDetalleComprobanteService.sumaDebeBolivianoDolarComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante), EnumTipoMoneda.BOLIVIANOS.getCodigo());
        sumaTotalHaberBoliviano = cntDetalleComprobanteService.sumaHaberBolivianoDolarComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante), EnumTipoMoneda.BOLIVIANOS.getCodigo());
        diferencia = sumaTotalDebe.subtract(sumaTotalHaber);
        if (diferencia.signum() == -1) {
            diferencia = diferencia.multiply(new BigDecimal("-1"));
        }
        if (cntComprobante.getEstado().equals(EnumEstado.REVERTIDO.getCodigo())) {
            desahabilitaBotonNuevo = true;
        } else {
            desahabilitaBotonNuevo = false;
        }
        cntDetalleComprobantesList = cntDetalleComprobanteService.ordenaSegunPosicion(cntComprobante);
        numeroMovimientos = cntDetalleComprobantesList.size();
        if (numeroMovimientos > 0) {
            nombreVentanaDetalleComprobante = "MODIFICACIÓN";
        } else {
            nombreVentanaDetalleComprobante = "ADICIÓN";
        }
    }

    public void actualizaTituloTipoComprobante(ValueChangeEvent e) throws Exception {
        cntComprobante.setNumero(cntComprobantesService.ultimoNumeroComprobante(cntComprobante));
        if (cntComprobante.getParTipoComprobante().getCodigo().equals(EnumTipoComprobante.EGRESO.getCodigo())) {
            tituloTipoComprobante = "EGRESO";
        } else {
            if (cntComprobante.getParTipoComprobante().getCodigo().equals(EnumTipoComprobante.INGRESO.getCodigo())) {
                tituloTipoComprobante = "INGRESO";
            } else {
                if (cntComprobante.getParTipoComprobante().getCodigo().equals(EnumTipoComprobante.TRASPASO.getCodigo())) {
                    tituloTipoComprobante = "TRASPASO";
                }
            }
        }
    }

    public void devuelveTipoCambioPorFecha(ValueChangeEvent e) {
        if (cntTipoCambioService.devuelveCntTipoDeCambio(cntComprobante.getFecha()) != null) {
            cntComprobante.setTipoCambio(cntTipoCambioService.devuelveCntTipoDeCambio(cntComprobante.getFecha()).getTipoCambio());
        } else {
            cntComprobante.setTipoCambio(new BigDecimal("0.0"));
        }
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        actualizaValorAndConvierteMoneda();

    }

    public List<CntEntidad> completaDescripcionCuenta(String descripcion) {
        List<CntEntidad> listaDeSugerencias;
        listaDeSugerencias = cntEntidadesService.listaCuentasDeUltimoNivelPorDescripcion(descripcion);
        return listaDeSugerencias;
    }

    public void actualizaTituloTipoMoneda(ValueChangeEvent e) throws Exception {
        actualizaValorAndConvierteMoneda();

    }

    public void actualizaValorAndConvierteMoneda() {
        if (cntComprobante.getTipoMoneda().equals(EnumTipoMoneda.BOLIVIANOS.getCodigo())) {
            tituloTipoMoneda = "BOLIVIANOS";
            convierte = new BigDecimal("1");
        } else {
            if (cntComprobante.getTipoMoneda().equals(EnumTipoMoneda.DOLAR.getCodigo())) {
                tituloTipoMoneda = "DOLLAR";
                if (cntComprobante.getTipoCambio() != null) {
                    convierte = cntComprobante.getTipoCambio();
                }
            }
        }
    }

    public List<CntDetalleComprobante> listaDeCuentasPorComprobante() {
        try {

            if (cntDetalleComprobantesList.isEmpty() || cntDetalleComprobantesList == null) {
                cntDetalleComprobantesList = cntDetalleComprobanteService.ordenaSegunPosicion(cntComprobante);
            }
            return cntDetalleComprobantesList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    public void sumaDebeHaberComprobante() {
        sumaTotalDebe = cntDetalleComprobanteService.sumaDebeComprobante(cntDetalleComprobantesList);
        sumaTotalHaber = cntDetalleComprobanteService.sumaHaberComprobante(cntDetalleComprobantesList);
    }

    public void modificarCuentaDeComprobante() {
        try {
            cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.obtieneIdPadreDeUnDetalleComprobante(listaCntDetalleComprobanteElegidaParaQuitarse).clone();
            cntDetalleComprobante = cntDetalleComprobanteService.obtieneMontoOriginal(cntDetalleComprobante);
            cntDetalleComprobante.setCntFacturacion(null);
            CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(selecDetalleComprobanteParaInsertar.getCntEntidad());
            cntEntidadSelecion = selecDetalleComprobanteParaInsertar.getCntEntidad();
            cntEntidad = selecDetalleComprobanteParaInsertar.getCntEntidad();
            swDebe = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.DEBE.getCodigo());
            swHaber = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.HABER.getCodigo());
            activaBotonGuardar = true;
            activaBotonParaCuentasNinguno = true;
            activaBotonModificar = true;
            swModificaDetalleComprobante = true;
            activaControlGlosa = false;
            org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoDetalle').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quitaCuentaDeComprobante() throws Exception {
        if (listaCntDetalleComprobanteElegidaParaQuitarse != null) {
//            cntDetalleComprobante = new CntDetalleComprobante();
            reiniciaValoresPanelNuevoDetalleComprobante();
            setRemoveValues(cntComprobante);
            cntDetalleComprobanteService.quitarCntDetalleComprobantes(listaCntDetalleComprobanteElegidaParaQuitarse, cntComprobante);
            cntDetalleComprobantesList = cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante);
            muestraMensajeSiExistePendientes = cntDetalleComprobanteService.verificaExistenciaDePendientes(cntComprobante);
            sumaDebeHaberComprobante();
            sumaTotalDebeBoliviano = cntDetalleComprobanteService.sumaDebeBolivianoDolarComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante), EnumTipoMoneda.BOLIVIANOS.getCodigo());
            sumaTotalHaberBoliviano = cntDetalleComprobanteService.sumaHaberBolivianoDolarComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante), EnumTipoMoneda.BOLIVIANOS.getCodigo());
            sumaTotalDebeDolar = cntDetalleComprobanteService.sumaDebeBolivianoDolarComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante), EnumTipoMoneda.DOLAR.getCodigo());
            sumaTotalHaberDolar = cntDetalleComprobanteService.sumaHaberBolivianoDolarComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante), EnumTipoMoneda.DOLAR.getCodigo());
        } else {
            MessageUtils.addInfoMessage("No selecciono ninguna");
        }
    }

    public String guardaComprobanteAndListaDetalleComprobante() throws Exception {
        if (!cntDetalleComprobantesList.isEmpty()) {
            try {
                if (sumaTotalDebe.equals(sumaTotalHaber)) {
                    try {
                        cntComprobante.setTotalComprobantes(cntDetalleComprobanteService.sumaDebeComprobante(cntDetalleComprobantesList));
                        cntComprobante.setTotalComprobantesSegMoneda(cntDetalleComprobanteService.convierteBolivianosDolar(cntComprobante.getTotalComprobantes(), cntComprobante.getTipoCambio()));
                        cntComprobante.setPeriodo(cntComprobantesService.obtienePeriodoPorFecha(cntComprobante.getFecha()));
//                        if (cntComprobante.getNumero().equals(cntComprobantesService.obtieneUltimoNumeroComprobante(cntComprobante))) {
//                            MessageUtils.addInfoMessage("El número de Comprobante ya fue usado por otro usuario, este comprobante se creara con el número:" + cntComprobantesService.ultimoNumeroComprobante(cntComprobante));
//                            cntComprobante.setNumero(cntComprobantesService.ultimoNumeroComprobante(cntComprobante));
//                        }
                        if (!cntComprobante.getEstado().equals(EnumEstado.MODIFICANDO.getCodigo()) && !cntComprobante.getEstado().equals(EnumEstado.ANULADO.getCodigo())) {
                            cntComprobante.setNumero(cntComprobantesService.ultimoNumeroComprobante(cntComprobante));
                        }
                        setPersistValues(cntComprobante);
                        cntComprobantesService.mergeCntComprobanteYCntDetalleComprobanteConfirmado(cntComprobante, cntDetalleComprobantesList, getLoginSession());
                        setInSessionIdComprobante(cntComprobante.getIdComprobante());
                        setInSessionIdDetalleComprobante(null);
                        MessageUtils.addInfoMessage("El numero de Comprobante con el que se guardo es el " + cntComprobante.getNumero() + " con un tipo de comprobante " + cntComprobante.getParTipoComprobante().getDescripcion());
                        return "imprimeComprobante";
                    } catch (Exception e) {
                        MessageUtils.addErrorMessage("No se puedo Grabar, consulte al proveedor del Sistema, gracias.");
                        e.printStackTrace();
                    }
                } else {
                    MessageUtils.addErrorMessage("No se puede Grabar, él debe y el haber no son iguales, verifique por favor.");
                }
            } catch (Exception e) {
                MessageUtils.addErrorMessage("No se puedo Grabar, consulte al proveedor del Sistema, gracias.");
                e.printStackTrace();
            }
        } else {
            MessageUtils.addErrorMessage("El tipo de cambio esta vacio.");
        }
        return null;
    }

    public String cancelaGuardadoComprobante() {
        setInSessionIdComprobante(cntComprobante.getIdComprobante());
        return "detalleComprobante";
    }

    public Boolean verificaTipoCambioPorFecha() {
        if (cntTipoCambioService.devuelveCntTipoDeCambio(cntComprobante.getFecha()) != null) {
            return true;
        }
        return false;
    }

    public String irComprobante() {
        setInSessionIdComprobante(null);
        setInSessionIdDetalleComprobante(null);
        try {
            cntDetalleComprobanteService.removePendientesDetalleComprobanteCancelar(cntComprobante);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "comprobantesList";
    }

    public String irComprobanteCancelandoAnulacion() {
        setInSessionIdComprobante(null);
        setInSessionIdDetalleComprobante(null);
        cntComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
        setMergeValues(cntComprobante);
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "comprobantesList";
    }

    public Boolean cambiaColor(String estado) {
        if (estado.equals(EnumEstado.PENDIENTE.getCodigo())) {
            return true;
        }
        return false;
    }

    public Boolean cambiaColorPendientes(String estado) {
        if (estado.equals(EnumEstado.PENDIENTE.getCodigo())) {
            return true;
        }
        return false;
    }

    public Boolean cambiaColorModificados(String estado) {
        if (estado.equals(EnumEstado.MODIFICANDO.getCodigo())) {
            return true;
        }
        return false;
    }

    public Boolean cambiaColorProcesoAnulado(String estado) {
        if (estado.equals(EnumEstado.PROCESO_ANULACION.getCodigo())) {
            return true;
        }
        return false;
    }

    public Boolean cambiaColorAnulado(String estado) {
        if (estado.equals(EnumEstado.ANULADO.getCodigo())) {
            return true;
        }
        return false;
    }

    public String cambiaColorEstadoCC(String estado) {
        if (estado.equals(EnumEstado.CONFIRMADO.getCodigo())) {
            colorEstado = "#009999";
        } else {
            if (estado.equals(EnumEstado.ANULADO.getCodigo())) {
                colorEstado = "#D0332E";
            } else {
                colorEstado = "#E1E463";
            }
        }
        return colorEstado;
    }

    public String adicionaDetalleComprobanteConEstado() {
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
            if (!swModificaDetalleComprobante) {
                cntDetalleComprobante.setLoginUsuario(getLoginSession());
                cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
                cntDetalleComprobante.setCntComprobante(cntComprobante);
                if (cntDetalleComprobante.getDebe() != null) {
                    cntDetalleComprobante.setDebeDolar((cntDetalleComprobante.getDebe().divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                if (cntDetalleComprobante.getHaber() != null) {
                    cntDetalleComprobante.setHaberDolar((cntDetalleComprobante.getHaber().divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                cntDetalleComprobante.setCntEntidad(cntEntidad);
                CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
                cntDetalleComprobante.setTransaccionRealizada(pa.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.NINGUNO.getCodigo()) ? EnumTransaccionRealizada.NINGUNO.getCodigo() : EnumTransaccionRealizada.SIN_FACTURA.getCodigo());
                cntDetalleComprobanteService.obtienePosicionDetalleComprobantePorComprobante(cntDetalleComprobante);
                setPersistValues(cntDetalleComprobante);
                System.out.println("en backing---- " + cntDetalleComprobante.getIdDetalleComprobante());
                System.out.println("en backing---- " + cntDetalleComprobante.getCntFacturacion());
                cntDetalleComprobante = cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.CENTROS_DE_COSTO.getCodigo()).getValor()).equals("1")) {
                    if (!cntEntidadesService.verificaEntidadCentroCosto(cntDetalleComprobante.getCntEntidad())) {
                        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                        return "distribucionDeCentroDeCosto";
                    }
                }
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.AUXILIAR.getCodigo()).getValor()).equals("1")) {
                    if (cntDetalleComprobante.getCntEntidad().getTieneAuxiliar().equals(EnumConfirmacion.SI.getCodigo())) {
                        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                        if (cntAuxiliaresPorCuentaService.verificaRelacionCntEntidadConCntAuxiliarPorCuenta(cntDetalleComprobante.getCntEntidad())) {
                            return "asignacionDeAuxiliaresADetalleComprobante";
                        }
                    }
                }
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.PROYECTO.getCodigo()).getValor()).equals("1")) {
                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                    return "asignacionDeProyectosADetalleComprobante";
                }
                setInSessionIdEntidad(null);
                setInSessionIdDetalleComprobante(null);
                setInSessionIdComprobante(cntDetalleComprobante.getCntComprobante().getIdComprobante());
                return "detalleComprobante";
            } else {
                System.out.println("por else de modificacion---");
                setMergeValues(cntDetalleComprobante);
                if (cntDetalleComprobante.getDebe() != null) {
                    cntDetalleComprobante.setDebeDolar((cntDetalleComprobante.getDebe().divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    cntDetalleComprobante.setHaber(null);
                    cntDetalleComprobante.setHaberDolar(null);
                }
                if (cntDetalleComprobante.getHaber() != null) {
                    cntDetalleComprobante.setHaberDolar((cntDetalleComprobante.getHaber().divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    cntDetalleComprobante.setDebe(null);
                    cntDetalleComprobante.setDebeDolar(null);
                }
                System.out.println("el detalleComprobante antes----" + cntDetalleComprobante.getIdDetalleComprobante());
                System.out.println("el factura antes----" + cntDetalleComprobante.getCntFacturacion());
                cntDetalleComprobante = cntDetalleComprobanteService.persistCntDetalleComprobantesModifica(cntDetalleComprobante);
//                if (cntEntidadesService.verificaEntidadCentroCosto(cntDetalleComprobante.getCntEntidad())) {
//                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
//                    return "distribucionDeCentroDeCosto";
//                }
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.CENTROS_DE_COSTO.getCodigo()).getValor()).equals("1")) {
                    if (!cntEntidadesService.verificaEntidadCentroCosto(cntDetalleComprobante.getCntEntidad())) {
                        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                        return "distribucionDeCentroDeCosto";
                    }
                }

                if (cntDetalleComprobante.getCntEntidad().getTieneAuxiliar().equals(EnumConfirmacion.SI.getCodigo())) {
                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                    return "asignacionDeAuxiliaresADetalleComprobante";
                }

                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.PROYECTO.getCodigo()).getValor()).equals("1")) {
                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                    return "asignacionDeProyectosADetalleComprobante";
                }
//                reiniciaValoresPanelNuevoDetalleComprobante();
                setInSessionIdEntidad(null);
                setInSessionIdDetalleComprobante(null);
                setInSessionIdComprobante(cntDetalleComprobante.getCntComprobante().getIdComprobante());
                reiniciaValoresPanelNuevoDetalleComprobante();
                return "detalleComprobante";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "detalleComprobante";
    }

    public void bloqueaOpcionDebe(ValueChangeEvent e) throws Exception {
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaFacturaCompra();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaFacturaVenta();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaSinFactura();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaRetenciones();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaGrossingUp();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaCreditoFiscalNoDeducible();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaAjuste();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaBotonParaCuentasParametrizadasConNinguno();
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pa.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.NINGUNO.getCodigo())) {
            if (cntDetalleComprobante.getDebe() != null) {
                swHaber = false;
                activaBotonGuardar = true;
                cntDetalleComprobante.setHaber(null);
            } else {
                swHaber = true;
                activaBotonGuardar = true;
                cntDetalleComprobante.setHaber(null);
            }
        } else {
            if (cntDetalleComprobante.getDebe() != null) {
                swHaber = false;
                if (!swModificaDetalleComprobante) {
                    activaBotonGuardar = false;
                } else {
                    activaBotonModificarOpciones = false;
                }
                activaBotonParaCuentasNinguno = true;
                cntDetalleComprobante.setHaber(null);
            } else {
                swHaber = true;
                activaBotonGuardar = true;
                activaBotonModificarOpciones = true;
                activaBotonParaCuentasNinguno = true;
                cntDetalleComprobante.setHaber(null);
            }
        }
        swDebe = true;

    }

    public void bloqueaOpcionHaber(ValueChangeEvent e) throws Exception {
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaFacturaCompra();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaFacturaVenta();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaSinFactura();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaRetenciones();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaGrossingUp();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaCreditoFiscalNoDeducible();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaAjuste();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaBotonParaCuentasParametrizadasConNinguno();
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pa.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.NINGUNO.getCodigo())) {
            if (cntDetalleComprobante.getHaber() != null) {
                swDebe = false;
                activaBotonGuardar = true;
                cntDetalleComprobante.setDebe(null);
            } else {
                swDebe = true;
                activaBotonGuardar = true;
                cntDetalleComprobante.setDebe(null);
            }
        } else {
            if (cntDetalleComprobante.getHaber() != null) {
                swDebe = false;
                if (!swModificaDetalleComprobante) {
                    activaBotonGuardar = false;
                } else {
                    activaBotonModificarOpciones = false;
                }
                activaBotonParaCuentasNinguno = true;
                cntDetalleComprobante.setDebe(null);
            } else {
                swDebe = true;
                activaBotonGuardar = true;
                activaBotonModificarOpciones = true;
                activaBotonParaCuentasNinguno = true;
                cntDetalleComprobante.setDebe(null);
            }
        }
        swHaber = true;

    }

    public void obtieneOpcionDebe() throws Exception {
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaFacturaCompra();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaFacturaVenta();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaSinFactura();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaRetenciones();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaGrossingUp();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaCreditoFiscalNoDeducible();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaAjuste();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaBotonParaCuentasParametrizadasConNinguno();
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pa.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.NINGUNO.getCodigo())) {
            if (cntDetalleComprobante.getDebe() != null) {
                activaBotonGuardar = true;
                cntDetalleComprobante.setHaber(null);
            } else {
                activaBotonGuardar = true;
                cntDetalleComprobante.setHaber(null);
            }
        } else {
            if (cntDetalleComprobante.getDebe() != null) {
                if (!swModificaDetalleComprobante) {
                    activaBotonGuardar = false;
                } else {
                    activaBotonModificarOpciones = false;
                }
                activaBotonParaCuentasNinguno = true;
                cntDetalleComprobante.setHaber(null);
            } else {
                activaBotonGuardar = true;
                activaBotonParaCuentasNinguno = true;
                cntDetalleComprobante.setHaber(null);
            }
        }
    }

    public void obtieneOpcionHaber() throws Exception {
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaFacturaCompra();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaFacturaVenta();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaSinFactura();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaRetenciones();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaGrossingUp();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaCreditoFiscalNoDeducible();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaAjuste();
        pulsoSwDebe = true;
        pulsoSwHaber = true;
        activaBotonParaCuentasParametrizadasConNinguno();
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pa.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.NINGUNO.getCodigo())) {
            if (cntDetalleComprobante.getHaber() != null) {
                activaBotonGuardar = true;
                cntDetalleComprobante.setDebe(null);
            } else {
                activaBotonGuardar = true;
                cntDetalleComprobante.setDebe(null);
            }
        } else {
            if (cntDetalleComprobante.getHaber() != null) {
                if (!swModificaDetalleComprobante) {
                    activaBotonGuardar = false;
                } else {
                    activaBotonModificarOpciones = false;
                }
                activaBotonParaCuentasNinguno = true;
                cntDetalleComprobante.setDebe(null);
            } else {
                activaBotonGuardar = true;
                activaBotonParaCuentasNinguno = true;
                cntDetalleComprobante.setDebe(null);
            }
        }
    }

    public void activaBotonModificarAlPrecionarEnGlosaParaModificar(ValueChangeEvent e) throws Exception {
        if (!activaControlGlosa) {
            if (cntDetalleComprobante.getDebe() != null) {
                bloqueaOpcionDebe(e);
            } else {
                if (cntDetalleComprobante.getHaber() != null) {
                    bloqueaOpcionHaber(e);
                }
            }
        } else {
            if (!cntDetalleComprobante.getGlosa().isEmpty()) {
                actualizaDatosDebeAndHaber();
            } else {
                swDebe = false;
                swHaber = false;
                activaBotonGuardar = true;
                cntDetalleComprobante.setDebe(null);
                cntDetalleComprobante.setHaber(null);
            }
        }
    }

    public void activaFacturaCompra() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getFacturaCompra().equals(EnumFacturaCompraCombustible.NINGUNO.getCodigo())) {
                activaFacturaDeCompraSinCombustible = false;
                activaFacturaDeCompraConCombustible = false;
            } else if (pa.getFacturaCompra().equals(EnumFacturaCompraCombustible.SIN_COMBUSTIBLE.getCodigo())) {
                activaFacturaDeCompraSinCombustible = true;
            } else if (pa.getFacturaCompra().equals(EnumFacturaCompraCombustible.CON_COMBUSTIBLE.getCodigo())) {
                activaFacturaDeCompraConCombustible = true;
            }
        } else {
            if (pulsoSwHaber) {
                activaFacturaDeCompraSinCombustible = false;
                activaFacturaDeCompraConCombustible = false;
            }
        }
    }

    public void activaCreditoFiscalNoDeducible() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getCreditoFiscalNoDeducible().equals(EnumParametroAutomatico.DEBE.getCodigo())) {
                activaCreditoFiscalNoDeducible = true;
            } else {
                activaCreditoFiscalNoDeducible = false;
            }
        } else {
            if (pulsoSwHaber) {
                activaCreditoFiscalNoDeducible = false;
            }
        }
    }

    public void activaFacturaVenta() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getFacturaVenta().equals(EnumParametroAutomatico.DEBE.getCodigo())) {
                activaFacturaDeVenta = false;
            }
        } else {
            if (pulsoSwHaber) {
                if (pa.getFacturaVenta().equals(EnumParametroAutomatico.HABER.getCodigo())) {
                    activaFacturaDeVenta = true;
                } else {
                    activaFacturaDeVenta = false;
                }
            }
        }
    }

    public void activaSinFactura() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getSinFactura().equals(EnumParametroAutomatico.NINGUNO.getCodigo())) {
                activaSinFactura = false;
            } else {
                activaSinFactura = true;
            }
        } else {
            if (pulsoSwHaber) {
                if (pa.getSinFactura().equals(EnumParametroAutomatico.NINGUNO.getCodigo())) {
                    activaSinFactura = false;
                } else {
                    activaSinFactura = true;
                }
            }
        }
    }

    public void activaRetenciones() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getParTipoRetencion().getCodigo().equals(EnumTipoRetencion.SIN_RETENCION.getCodigo())) {
                activaRetencion = false;
            } else {
                activaRetencion = true;
                nombreRetencion = pa.getParTipoRetencion().getDescripcion();
            }
        } else {
            if (pulsoSwHaber) {
                activaRetencion = false;
            }
        }
    }

    public void activaGrossingUp() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getParTipoRetencionGrossingUp().getCodigo().equals(EnumTipoRetencion.SIN_RETENCION.getCodigo())) {
                activaRetencionGrossingUp = false;
            } else {
                activaRetencionGrossingUp = true;
                nombreRetencionGrossingUp = pa.getParTipoRetencionGrossingUp().getDescripcion();
            }
        } else {
            if (pulsoSwHaber) {
                activaRetencionGrossingUp = false;
            }
        }
    }

    public void activaAjuste() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getParAjustes().getCodigo().equals(EnumAjuste.SIN_AJUSTE.getCodigo())) {
                activaAjuste = false;
            } else {
                activaAjuste = true;
                nombreAjuste = pa.getParAjustes().getDescripcion();
            }
        } else {
            if (pulsoSwHaber) {
                if (pa.getParAjustes().getCodigo().equals(EnumAjuste.SIN_AJUSTE.getCodigo())) {
                    activaAjuste = false;
                } else {
                    activaAjuste = true;
                    nombreAjuste = pa.getParAjustes().getDescripcion();
                }
            }
        }
    }

    public void activaBotonParaCuentasParametrizadasConNinguno() {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.NINGUNO.getCodigo())) {
                activaBotonParaCuentasNinguno = false;
                activaBotonGuardar = true;
            } else {
                activaBotonParaCuentasNinguno = true;
            }
        } else {
            if (pulsoSwHaber) {
                if (pa.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.NINGUNO.getCodigo())) {
                    activaBotonParaCuentasNinguno = false;
                    activaBotonGuardar = true;
                } else {
                    activaBotonParaCuentasNinguno = true;
                }
            }
        }
    }

    public String irAjustes() {
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
            CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);

            if (pa.getParAjustes().getCodigo().equals(EnumAjuste.AJUSTE_RESULTADO_POR_EXPOSICION_INFLACION.getCodigo())) {
                return "";
            } else {
                if (pa.getParAjustes().getCodigo().equals(EnumAjuste.AJUSTE_INFLACION_Y_TENENCIA_DE_BIENES.getCodigo())) {
                    return "";
                } else {
                    if (pa.getParAjustes().getCodigo().equals(EnumAjuste.DIFERENCIA_DE_CAMBIO.getCodigo())) {
                        return "";
                    } else {
                        if (pa.getParAjustes().getCodigo().equals(EnumAjuste.CORRECCION_MONETARIA.getCodigo())) {
                            return "";
                        } else {
                            if (pa.getParAjustes().getCodigo().equals(EnumAjuste.AJUSTE_DE_CAPITAL.getCodigo())) {
                                return "";
                            } else {
                                if (pa.getParAjustes().getCodigo().equals(EnumAjuste.AJUSTE_GLOBAL_DEL_PATRIMONIO.getCodigo())) {
                                    return "";
                                } else {
                                    if (pa.getParAjustes().getCodigo().equals(EnumAjuste.AJUSTE_RESERVA_PATRIMONIAL.getCodigo())) {
                                        return "";
                                    } else {
                                        return null;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String irFacturaCompra() {
        return "facturaCompra";
    }

    public String irCreditoFiscalNoDeducible() {
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
            if (!swModificaDetalleComprobante) {
                setPersistValues(cntDetalleComprobante);
                cntDetalleComprobante.setLoginUsuario(getLoginSession());
                cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
                cntDetalleComprobante.setCntComprobante(cntComprobante);
                cntDetalleComprobante.setCntEntidad(cntEntidad);
                cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.CREDITO_FISCAL_NO_DEDUCIBLE.getCodigo());
                cntDetalleComprobanteService.obtienePosicionDetalleComprobantePorComprobante(cntDetalleComprobante);
                cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
                cntDetalleComprobanteService.guardaCreditoFiscalNoDeducible(cntDetalleComprobante);
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.CENTROS_DE_COSTO.getCodigo()).getValor()).equals("1")) {
                    if (!cntEntidadesService.verificaEntidadCentroCosto(cntDetalleComprobante.getCntEntidad())) {
                        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                        return "distribucionDeCentroDeCosto";
                    }
                }
                //aumentado para verificasr si una cuenta tiene auxiliares y proyectos
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.AUXILIAR.getCodigo()).getValor()).equals("1")) {
                    if (cntDetalleComprobante.getCntEntidad().getTieneAuxiliar().equals(EnumConfirmacion.SI.getCodigo())) {
                        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                        if (cntAuxiliaresPorCuentaService.verificaRelacionCntEntidadConCntAuxiliarPorCuenta(cntDetalleComprobante.getCntEntidad())) {
                            return "asignacionDeAuxiliaresADetalleComprobante";
                        }
                    }
                }
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.PROYECTO.getCodigo()).getValor()).equals("1")) {
                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                    return "asignacionDeProyectosADetalleComprobante";
                }
                setInSessionIdComprobante(cntDetalleComprobante.getCntComprobante().getIdComprobante());
                return "detalleComprobante";
            } else {
                setMergeValues(cntDetalleComprobante);

                cntDetalleComprobante = cntDetalleComprobanteService.persistCntDetalleComprobantesModificaCreditoFiscalNoDeducible(cntDetalleComprobante);
                if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.CENTROS_DE_COSTO.getCodigo()).getValor()).equals("1")) {
                    if (!cntEntidadesService.verificaEntidadCentroCosto(cntDetalleComprobante.getCntEntidad())) {
                        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                        return "distribucionDeCentroDeCosto";
                    }
                }
                reiniciaValoresPanelNuevoDetalleComprobante();
                return "detalleComprobante";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String irPlanCuentas() throws Exception {
        cntComprobantesService.mergeCntComprobantes(cntComprobante);
        setInSessionIdComprobante(cntComprobante.getIdComprobante());
        return "planCuentas";
    }

    public String irFacturaVenta() {
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
            cntDetalleComprobante.setLoginUsuario(getLoginSession());
            cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntDetalleComprobante.setCntComprobante(cntComprobante);
            cntDetalleComprobante.setCntEntidad(cntEntidadSelecion);
            cntDetalleComprobanteService.obtienePosicionDetalleComprobantePorComprobante(cntDetalleComprobante);
            setPersistValues(cntDetalleComprobante);
            cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
            return "facturaVenta";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String registroDetalleComprobanteParaRetenciones() {
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
            swActivaRetenciones = true;
            cntDetalleComprobante.setLoginUsuario(getLoginSession());
            cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntDetalleComprobante.setCntComprobante(cntComprobante);
            cntDetalleComprobante.setCntEntidad(cntEntidadSelecion);
            cntDetalleComprobanteService.obtienePosicionDetalleComprobantePorComprobante(cntDetalleComprobante);
            setPersistValues(cntDetalleComprobante);
            cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            tipoRetencion = EnumRetencionGrossing.RETENCION.getCodigo();
            setInSessionTipoRetencionOGrossing(tipoRetencion);
            setInSessionIdComprobante(cntComprobante.getIdComprobante());
            setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
            return "planCuentas";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String modificaRegistroDetalleComprobanteParaRetenciones() {//aumentado para la modificacion de retenciones
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
            swActivaRetenciones = true;
            cntDetalleComprobante.setLoginUsuario(getLoginSession());
//        cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntDetalleComprobante.setCntEntidad(cntEntidadSelecion);
            cntDetalleComprobante.setFechaBaja(new Date());
            cntDetalleComprobante.setUsuarioBaja(getLoginSession());
            setMergeValues(cntDetalleComprobante);
            cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
            tipoRetencion = EnumRetencionGrossing.RETENCION.getCodigo();
            setInSessionTipoRetencionOGrossing(tipoRetencion);
            setInSessionIdComprobante(cntComprobante.getIdComprobante());
            setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
            return "planCuentas";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String registroDetalleComprobanteParaGrossing() {
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
            swActivaRetenciones = true;
            cntDetalleComprobante.setLoginUsuario(getLoginSession());
            cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntDetalleComprobante.setCntComprobante(cntComprobante);
            cntDetalleComprobante.setCntEntidad(cntEntidadSelecion);
            cntDetalleComprobanteService.obtienePosicionDetalleComprobantePorComprobante(cntDetalleComprobante);
            setPersistValues(cntDetalleComprobante);
            cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            tipoRetencion = EnumRetencionGrossing.GROSSING.getCodigo();
            setInSessionTipoRetencionOGrossing(tipoRetencion);
            setInSessionIdComprobante(cntComprobante.getIdComprobante());
            setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
            return "planCuentas";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String modificaRegistroDetalleComprobanteParaGrossing() {
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
            swActivaRetenciones = true;
            cntDetalleComprobante.setLoginUsuario(getLoginSession());
//        cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntDetalleComprobante.setCntEntidad(cntEntidadSelecion);
            cntDetalleComprobante.setFechaBaja(new Date());
            cntDetalleComprobante.setUsuarioBaja(getLoginSession());
            setMergeValues(cntDetalleComprobante);
            cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
            tipoRetencion = EnumRetencionGrossing.GROSSING.getCodigo();
            setInSessionIdComprobante(cntComprobante.getIdComprobante());
            setInSessionTipoRetencionOGrossing(tipoRetencion);
            setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
            return "planCuentas";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String adicionaDetalleComprobanteFacturaVenta() {
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
            cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo());
            cntDetalleComprobante.setLoginUsuario(getLoginSession());
            cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntDetalleComprobante.setCntComprobante(cntComprobante);
            cntDetalleComprobante.setCntEntidad(cntEntidadSelecion);
            cntDetalleComprobanteService.obtienePosicionDetalleComprobantePorComprobante(cntDetalleComprobante);
            setPersistValues(cntDetalleComprobante);
            cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            super.setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
            return "facturaCompra";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String modificaDetalleComprobanteFacturaDeCompra() {
        try {
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
            cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo());
            cntDetalleComprobante.setLoginUsuario(getLoginSession());
            cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntDetalleComprobante.setIdAntecesor(cntDetalleComprobante.getIdDetalleComprobante());
            cntDetalleComprobante.setIdDetalleComprobante(null);
            setPersistValues(cntDetalleComprobante);
            cntDetalleComprobante = cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            super.setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
            return "facturaCompra";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ParTipoComprobante> listaTiposComprobantes() {
        return parParametricasService.listaTiposComprobantes();
    }

    public boolean isEditable() {
        if (cntDetalleComprobante == null) {
            return false;
        } else if (cntDetalleComprobante.getIdDetalleComprobante() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void reiniciaValoresPanelNuevoDetalleComprobante() {
        cntEntidad = new CntEntidad();
        cntDetalleComprobante = new CntDetalleComprobante();
        activaBotonInsertar = true;
    }

    public void modificaDetalleComprobante() {
    }

    public void selecDetalleComprobanteParaInsertarEvento(ValueChangeEvent e) {
        if (listaCntDetalleComprobanteElegidaParaQuitarse != null) {
            if (cntDetalleComprobanteService.obtieneIdPadreDeUnDetalleComprobante(listaCntDetalleComprobanteElegidaParaQuitarse) != null) {
                activaBotonInsertar = false;
                selecDetalleComprobanteParaInsertar = cntDetalleComprobanteService.obtieneIdPadreDeUnDetalleComprobante(listaCntDetalleComprobanteElegidaParaQuitarse);
                listaCntDetalleComprobanteElegidaParaQuitarse = cntDetalleComprobanteService.obtieneHijosTambienPadresDeUnDetalleComprobante(selecDetalleComprobanteParaInsertar);
                activaBotonModificar = false;
            } else {
                MessageUtils.addInfoMessage("Selecciono más de una cuenta principal, seleccione solo una cuenta principal gracias.");
                activaBotonInsertar = true;
            }
        } else {
            activaBotonInsertar = true;
        }
    }

    public String irGlosaComprobante() {
        try {
            if (sumaTotalDebe.compareTo(BigDecimal.ZERO) == 0 && sumaTotalHaber.compareTo(BigDecimal.ZERO) == 0) {
                MessageUtils.addErrorMessage("No se puede Grabar, el comprobante no puede tener un monto igual a cero");
            } else {
                if (!parParametricasService.verificaPeriodoYAnioActual(cntComprobante.getFecha())) {
                    MessageUtils.addErrorMessage("No se puede Grabar, la fecha no se encuentra en el Periodo Habilitado");
                } else if (compruebaSiElPeriodoEstaHabilitado()) {
                    if (!cntDetalleComprobantesList.isEmpty()) {
                        if (sumaTotalDebe.equals(sumaTotalHaber)) {
                            if (cntComprobante.getTipoCambio().compareTo(BigDecimal.ZERO) != 0) {
                                if (!cntComprobantesService.existenFacturasIncompletas(cntComprobante)) {
                                    if (cntComprobante.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                                        if (cntComprobante.getNumero().equals(new Long("0"))) {
                                            cntComprobante.setNumero(0L);
                                        }
//                                cntComprobante.setPeriodo("");
                                    }
                                    cntComprobante.setPeriodo(cntComprobantesService.obtienePeriodoPorFecha(cntComprobante.getFecha()));
                                    cntComprobantesService.mergeCntComprobantes(cntComprobante);
                                    setInSessionIdComprobante(cntComprobante.getIdComprobante());
                                    return "guardarComprobante";
                                } else {
                                    MessageUtils.addErrorMessage("No se puede Grabar, no asigno fecha o numero de factura a las facturas existentes");
                                }
                            } else {
                                MessageUtils.addErrorMessage("No se puede Grabar, debe asignar un tipo de cambio diferente a cero.");
                            }
                        } else {
                            MessageUtils.addErrorMessage("No se puede Grabar, él debe y el haber no son iguales, verifique por favor.");
                        }
                    } else {
                        MessageUtils.addErrorMessage("No existe registros en Detalle comprobante..");
                    }
                } else {
                    MessageUtils.addErrorMessage("La fecha no se encuentra en el Periodo o este no esta habilitado para su registro");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public Boolean compruebaSiElPeriodoEstaHabilitado() {
        //            Comentado hasta habilitar la opcion de Plazo de Registro de Comprobantes en la parametrizacion de Datos de Empresa
//        return cntComprobantesService.compruebaSiElPeriodoEstaHabilitado(cntComprobante);
        return true;
    }

    public String modificarComprobanteTotal() {
        try {
            cntComprobantesService.mergeCntComprobanteAndCntDetalleComprobanteAndFacturaAndDistribucionTotal(cntComprobante, getLoginSession(), new Date());
            return "comprobantesList";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String modificaEstadoAnuladoComprobanteDetalleFacturacion() {
        try {
//            cntComprobantesService.mergeAnuladoComprobanteDetalleFacturacion(cntComprobante, getLoginSession());
            if (!cntComprobante.getMotivoAnulacion().isEmpty()) {
                setMergeValues(cntComprobante);
                cntDetalleComprobanteService.cambiaEstadoAnuladoTodoElComprobanteMasLasTablasDependientes(cntComprobante);
                return "comprobantesList";
            } else {
                MessageUtils.addErrorMessage("Debe de llenar el campo Motivo de Anulacion antes de anular.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String modificaFacturaVenta() throws Exception {
        cntComprobantesService.mergeCntComprobantes(cntComprobante);
        cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo());
        cntDetalleComprobante.setLoginUsuario(getLoginSession());
        cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntDetalleComprobante.setIdAntecesor(cntDetalleComprobante.getIdDetalleComprobante());
        cntDetalleComprobante.setIdDetalleComprobante(null);
        setPersistValues(cntDetalleComprobante);
        try {
            cntDetalleComprobante = cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            super.setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
            return "facturaVenta";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public Boolean tieneFactura(CntDetalleComprobante cntDetalleComprobante) {
        return cntDetalleComprobanteService.verificaDetalleComprobanteTieneFactura(cntDetalleComprobante);
    }

    public String tipoDeTransaccion(CntDetalleComprobante cntDetalleComprobante) {
        return cntDetalleComprobanteService.tipoDeTransaccionPorDetalleComprobante(cntDetalleComprobante);
    }

    public String limpiaCuadrosDeTexto() throws Exception {
        setInSessionActivaDesactivaBotonesDetalle(true);
        cntComprobantesService.mergeCntComprobantes(cntComprobante);
        activaDesactivaBotonesDetalle = getFromSessionActivaDesactivaBotonesDetalle();
        return "formularioComprobante";
    }

    public String cambiaFormatoMoneda(BigDecimal valor) {
        return cntDetalleComprobanteService.cambiaFormatoMoneda(valor);
    }

    public void generaNuevaPosicionAlPrecionarBotonInsertaArriba() {
        cntDetalleComprobante.setPosicion(selecDetalleComprobanteParaInsertar.getPosicion());
    }

    public void generaNuevaPosicionAlPrecionarBotonInsertaAbajo() {
        cntDetalleComprobante.setPosicion(selecDetalleComprobanteParaInsertar.getPosicion() + 1);
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

    public String convierteFechaSoloAnioComprobante(CntComprobante cntComprobante) {
        if (cntComprobante.getFechaAlta() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            return formatter.format(cntComprobante.getFechaAlta());
        }
        return "Sin Periodo";
    }

    public String buscaNumeroFactura(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        if (cntDetalleComprobanteService.buscaNumeroFactura(cntDetalleComprobante) != null) {
            cntFacturacionNumero = cntDetalleComprobanteService.buscaNumeroFactura(cntDetalleComprobante);
            return cntFacturacionNumero.getNroInicial().toString();
        } else {
            return null;
        }
    }

//    public String buscaProyecto(CntDetalleComprobante cntDetalleComprobante) throws Exception {
//        if (((CntProyecto) cntProyectoService.find(CntProyecto.class, cntDetalleComprobante.getIdProyecto())) != null) {
//            return ((CntProyecto) cntProyectoService.find(CntProyecto.class, cntDetalleComprobante.getIdProyecto())).getMascara();
//        } else {
//            return null;
//        }
//    }
    //Modificado por Jonathan y actualizado por Jacqueline 18/11/2014
    public String buscaProyecto(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        if (cntDetalleComprobante.getIdProyecto() != null && ((CntProyecto) cntProyectoService.find(CntProyecto.class, cntDetalleComprobante.getIdProyecto())) != null) {
            return ((CntProyecto) cntProyectoService.find(CntProyecto.class, cntDetalleComprobante.getIdProyecto())).getMascara();
        }
        return "";
    }

    public String navegaAPlanDeCuentas() {
        setInSessionIdDetalleComprobante(null);
        setInSessionIdComprobante(null);
        setInSessionIdEntidad(null);
        setInSessionTipoRetencionOGrossing("");

        setInSessionIdComprobante(cntComprobante.getIdComprobante());
        return "planCuentas";
    }

    public CntFacturacion getCntFacturacionNumero() {
        return cntFacturacionNumero;
    }

    public void setCntFacturacionNumero(CntFacturacion cntFacturacionNumero) {
        this.cntFacturacionNumero = cntFacturacionNumero;
    }

    public CntProyectoService getCntProyectoService() {
        return cntProyectoService;
    }

    public void setCntProyectoService(CntProyectoService cntProyectoService) {
        this.cntProyectoService = cntProyectoService;
    }

    public CntComprobante getCntComprobante() {
        return cntComprobante;
    }

    public void setCntComprobante(CntComprobante cntComprobante) {
        this.cntComprobante = cntComprobante;
    }

    public CntComprobantesService getCntComprobantesService() {
        return cntComprobantesService;
    }

    public void setCntComprobantesService(CntComprobantesService cntComprobantesService) {
        this.cntComprobantesService = cntComprobantesService;
    }

    public String getTituloTipoComprobante() {
        return tituloTipoComprobante;
    }

    public void setTituloTipoComprobante(String tituloTipoComprobante) {
        this.tituloTipoComprobante = tituloTipoComprobante;
    }

    public String getTituloTipoMoneda() {
        return tituloTipoMoneda;
    }

    public void setTituloTipoMoneda(String tituloTipoMoneda) {
        this.tituloTipoMoneda = tituloTipoMoneda;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public CntTipoCambioService getCntTipoCambioService() {
        return cntTipoCambioService;
    }

    public void setCntTipoCambioService(CntTipoCambioService cntTipoCambioService) {
        this.cntTipoCambioService = cntTipoCambioService;
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public List<CntDetalleComprobante> getCntDetalleComprobantesList() {
        return cntDetalleComprobantesList;
    }

    public void setCntDetalleComprobantesList(List<CntDetalleComprobante> cntDetalleComprobantesList) {
        this.cntDetalleComprobantesList = cntDetalleComprobantesList;
    }

    public List<CntDetalleComprobante> getListaCntDetalleComprobanteElegidaParaQuitarse() {
        return listaCntDetalleComprobanteElegidaParaQuitarse;
    }

    public void setListaCntDetalleComprobanteElegidaParaQuitarse(List<CntDetalleComprobante> listaCntDetalleComprobanteElegidaParaQuitarse) {
        this.listaCntDetalleComprobanteElegidaParaQuitarse = listaCntDetalleComprobanteElegidaParaQuitarse;
    }

    public BigDecimal getConvierte() {
        return convierte;
    }

    public void setConvierte(BigDecimal convierte) {
        this.convierte = convierte;
    }

    public BigDecimal getSumaTotalDebe() {
        return sumaTotalDebe;
    }

    public void setSumaTotalDebe(BigDecimal sumaTotalDebe) {
        this.sumaTotalDebe = sumaTotalDebe;
    }

    public BigDecimal getSumaTotalHaber() {
        return sumaTotalHaber;
    }

    public void setSumaTotalHaber(BigDecimal sumaTotalHaber) {
        this.sumaTotalHaber = sumaTotalHaber;
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public Boolean getMuestraMensajeSiExistePendientes() {
        return muestraMensajeSiExistePendientes;
    }

    public void setMuestraMensajeSiExistePendientes(Boolean muestraMensajeSiExistePendientes) {
        this.muestraMensajeSiExistePendientes = muestraMensajeSiExistePendientes;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public List<ParTipoComprobante> getIiposComprobantesList() {
        return iiposComprobantesList;
    }

    public void setIiposComprobantesList(List<ParTipoComprobante> iiposComprobantesList) {
        this.iiposComprobantesList = iiposComprobantesList;
    }

    public CntDetalleComprobante getCntDetalleComprobante() {
        return cntDetalleComprobante;
    }

    public void setCntDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        this.cntDetalleComprobante = cntDetalleComprobante;
    }

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    public CntParametroAutomaticoService getCntParametroAutomaticoService() {
        return cntParametroAutomaticoService;
    }

    public void setCntParametroAutomaticoService(CntParametroAutomaticoService cntParametroAutomaticoService) {
        this.cntParametroAutomaticoService = cntParametroAutomaticoService;
    }

    public Boolean getSwDebe() {
        return swDebe;
    }

    public void setSwDebe(Boolean swDebe) {
        this.swDebe = swDebe;
    }

    public Boolean getSwHaber() {
        return swHaber;
    }

    public void setSwHaber(Boolean swHaber) {
        this.swHaber = swHaber;
    }

    public Boolean getPulsoSwDebe() {
        return pulsoSwDebe;
    }

    public void setPulsoSwDebe(Boolean pulsoSwDebe) {
        this.pulsoSwDebe = pulsoSwDebe;
    }

    public Boolean getPulsoSwHaber() {
        return pulsoSwHaber;
    }

    public void setPulsoSwHaber(Boolean pulsoSwHaber) {
        this.pulsoSwHaber = pulsoSwHaber;
    }

    public Boolean getActivaFacturaDeVenta() {
        return activaFacturaDeVenta;
    }

    public void setActivaFacturaDeVenta(Boolean activaFacturaDeVenta) {
        this.activaFacturaDeVenta = activaFacturaDeVenta;
    }

    public Boolean getActivaSinFactura() {
        return activaSinFactura;
    }

    public void setActivaSinFactura(Boolean activaSinFactura) {
        this.activaSinFactura = activaSinFactura;
    }

    public Boolean getActivaRetencion() {
        return activaRetencion;
    }

    public void setActivaRetencion(Boolean activaRetencion) {
        this.activaRetencion = activaRetencion;
    }

    public String getNombreRetencion() {
        return nombreRetencion;
    }

    public void setNombreRetencion(String nombreRetencion) {
        this.nombreRetencion = nombreRetencion;
    }

    public Boolean getActivaRetencionGrossingUp() {
        return activaRetencionGrossingUp;
    }

    public void setActivaRetencionGrossingUp(Boolean activaRetencionGrossingUp) {
        this.activaRetencionGrossingUp = activaRetencionGrossingUp;
    }

    public String getNombreRetencionGrossingUp() {
        return nombreRetencionGrossingUp;
    }

    public void setNombreRetencionGrossingUp(String nombreRetencionGrossingUp) {
        this.nombreRetencionGrossingUp = nombreRetencionGrossingUp;
    }

    public Boolean getActivaCreditoFiscalNoDeducible() {
        return activaCreditoFiscalNoDeducible;
    }

    public void setActivaCreditoFiscalNoDeducible(Boolean activaCreditoFiscalNoDeducible) {
        this.activaCreditoFiscalNoDeducible = activaCreditoFiscalNoDeducible;
    }

    public Boolean getActivaAjuste() {
        return activaAjuste;
    }

    public void setActivaAjuste(Boolean activaAjuste) {
        this.activaAjuste = activaAjuste;
    }

    public Boolean getActivaBotonGuardar() {
        return activaBotonGuardar;
    }

    public void setActivaBotonGuardar(Boolean activaBotonGuardar) {
        this.activaBotonGuardar = activaBotonGuardar;
    }

    public String getNombreAjuste() {
        return nombreAjuste;
    }

    public void setNombreAjuste(String nombreAjuste) {
        this.nombreAjuste = nombreAjuste;
    }

    public Boolean getActivaPanelNuevoDetalleComprobante() {
        return activaPanelNuevoDetalleComprobante;
    }

    public void setActivaPanelNuevoDetalleComprobante(Boolean activaPanelNuevoDetalleComprobante) {
        this.activaPanelNuevoDetalleComprobante = activaPanelNuevoDetalleComprobante;
    }

    public Boolean getActivaFacturaDeCompraSinCombustible() {
        return activaFacturaDeCompraSinCombustible;
    }

    public void setActivaFacturaDeCompraSinCombustible(Boolean activaFacturaDeCompraSinCombustible) {
        this.activaFacturaDeCompraSinCombustible = activaFacturaDeCompraSinCombustible;
    }

    public Boolean getActivaFacturaDeCompraConCombustible() {
        return activaFacturaDeCompraConCombustible;
    }

    public void setActivaFacturaDeCompraConCombustible(Boolean activaFacturaDeCompraConCombustible) {
        this.activaFacturaDeCompraConCombustible = activaFacturaDeCompraConCombustible;
    }

    public CntDetalleComprobante getSelecDetalleComprobanteParaInsertar() {
        return selecDetalleComprobanteParaInsertar;
    }

    public void setSelecDetalleComprobanteParaInsertar(CntDetalleComprobante selecDetalleComprobanteParaInsertar) {
        this.selecDetalleComprobanteParaInsertar = selecDetalleComprobanteParaInsertar;
    }

    public Boolean getActivaBotonInsertar() {
        return activaBotonInsertar;
    }

    public void setActivaBotonInsertar(Boolean activaBotonInsertar) {
        this.activaBotonInsertar = activaBotonInsertar;
    }

    public Boolean getSwActivaRetenciones() {
        return swActivaRetenciones;
    }

    public void setSwActivaRetenciones(Boolean swActivaRetenciones) {
        this.swActivaRetenciones = swActivaRetenciones;
    }

    public CntEntidad getCntEntidadSelecion() {
        return cntEntidadSelecion;
    }

    public void setCntEntidadSelecion(CntEntidad cntEntidadSelecion) {
        this.cntEntidadSelecion = cntEntidadSelecion;
    }

    public Boolean getActivaBotonParaCuentasNinguno() {
        return activaBotonParaCuentasNinguno;
    }

    public void setActivaBotonParaCuentasNinguno(Boolean activaBotonParaCuentasNinguno) {
        this.activaBotonParaCuentasNinguno = activaBotonParaCuentasNinguno;
    }

    public Boolean getActivaBotonModificar() {
        return activaBotonModificar;
    }

    public void setActivaBotonModificar(Boolean activaBotonModificar) {
        this.activaBotonModificar = activaBotonModificar;
    }

    public BigDecimal getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(BigDecimal diferencia) {
        this.diferencia = diferencia;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public int getNumeroMovimientos() {
        return numeroMovimientos;
    }

    public void setNumeroMovimientos(int numeroMovimientos) {
        this.numeroMovimientos = numeroMovimientos;
    }

    public Boolean getSwModificaDetalleComprobante() {
        return swModificaDetalleComprobante;
    }

    public void setSwModificaDetalleComprobante(Boolean swModificaDetalleComprobante) {
        this.swModificaDetalleComprobante = swModificaDetalleComprobante;
    }

    public Boolean getActivaBotonModificarOpciones() {
        return activaBotonModificarOpciones;
    }

    public void setActivaBotonModificarOpciones(Boolean activaBotonModificarOpciones) {
        this.activaBotonModificarOpciones = activaBotonModificarOpciones;
    }

    public Boolean getActivaPanelMotivoAnulacion() {
        return activaPanelMotivoAnulacion;
    }

    public void setActivaPanelMotivoAnulacion(Boolean activaPanelMotivoAnulacion) {
        this.activaPanelMotivoAnulacion = activaPanelMotivoAnulacion;
    }

    public Boolean getDesahabilitaBotonNuevo() {
        return desahabilitaBotonNuevo;
    }

    public void setDesahabilitaBotonNuevo(Boolean desahabilitaBotonNuevo) {
        this.desahabilitaBotonNuevo = desahabilitaBotonNuevo;
    }

    public String getColorEstado() {
        return colorEstado;
    }

    public void setColorEstado(String colorEstado) {
        this.colorEstado = colorEstado;
    }

    public String getTipoRetencion() {
        return tipoRetencion;
    }

    public void setTipoRetencion(String tipoRetencion) {
        this.tipoRetencion = tipoRetencion;
    }

    public int getTipodemonedas() {
        return tipodemonedas;
    }

    public void setTipodemonedas(int tipodemonedas) {
        this.tipodemonedas = tipodemonedas;
    }

    public Boolean getActivaModificacion() {
        return activaModificacion;
    }

    public void setActivaModificacion(Boolean activaModificacion) {
        this.activaModificacion = activaModificacion;
    }

    public Boolean getActivaControlGlosa() {
        return activaControlGlosa;
    }

    public void setActivaControlGlosa(Boolean activaControlGlosa) {
        this.activaControlGlosa = activaControlGlosa;
    }

    public Long getInicioImpresion() {
        return inicioImpresion;
    }

    public void setInicioImpresion(Long inicioImpresion) {
        this.inicioImpresion = inicioImpresion;
    }

    public Long getFinImpresion() {
        return finImpresion;
    }

    public void setFinImpresion(Long finImpresion) {
        this.finImpresion = finImpresion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Boolean getOpcionImpresion() {
        return opcionImpresion;
    }

    public void setOpcionImpresion(Boolean opcionImpresion) {
        this.opcionImpresion = opcionImpresion;
    }

    public String getNombreVentanaDetalleComprobante() {
        return nombreVentanaDetalleComprobante;
    }

    public void setNombreVentanaDetalleComprobante(String nombreVentanaDetalleComprobante) {
        this.nombreVentanaDetalleComprobante = nombreVentanaDetalleComprobante;
    }

    public void cambiaTipoDeCambio(ValueChangeEvent e) {
        try {

            if (cntComprobante.getTipoMoneda().equals(EnumTipoMoneda.BOLIVIANOS.getCodigo())) {
                cambiaTipoDeMonedaMontos = true;
                sumaTotalDebeBoliviano = cntDetalleComprobanteService.sumaDebeBolivianoDolarComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante), EnumTipoMoneda.BOLIVIANOS.getCodigo());
                sumaTotalHaberBoliviano = cntDetalleComprobanteService.sumaHaberBolivianoDolarComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante), EnumTipoMoneda.BOLIVIANOS.getCodigo());
                cntDetalleComprobantesList = cntDetalleComprobanteService.ordenaSegunPosicion(cntComprobante);
            } else {
                cambiaTipoDeMonedaMontos = false;
                sumaTotalDebeDolar = cntDetalleComprobanteService.sumaDebeBolivianoDolarComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante), EnumTipoMoneda.DOLAR.getCodigo());
                sumaTotalHaberDolar = cntDetalleComprobanteService.sumaHaberBolivianoDolarComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante), EnumTipoMoneda.DOLAR.getCodigo());
                cntDetalleComprobantesList = cntDetalleComprobanteService.ordenaSegunPosicionDolar(cntComprobante);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Boolean getCambiaTipoDeMonedaMontos() {
        return cambiaTipoDeMonedaMontos;
    }

    public void setCambiaTipoDeMonedaMontos(Boolean cambiaTipoDeMonedaMontos) {
        this.cambiaTipoDeMonedaMontos = cambiaTipoDeMonedaMontos;
    }

    public BigDecimal getSumaTotalDebeBoliviano() {
        return sumaTotalDebeBoliviano;
    }

    public void setSumaTotalDebeBoliviano(BigDecimal sumaTotalDebeBoliviano) {
        this.sumaTotalDebeBoliviano = sumaTotalDebeBoliviano;
    }

    public BigDecimal getSumaTotalHaberBoliviano() {
        return sumaTotalHaberBoliviano;
    }

    public void setSumaTotalHaberBoliviano(BigDecimal sumaTotalHaberBoliviano) {
        this.sumaTotalHaberBoliviano = sumaTotalHaberBoliviano;
    }

    public BigDecimal getSumaTotalDebeDolar() {
        return sumaTotalDebeDolar;
    }

    public void setSumaTotalDebeDolar(BigDecimal sumaTotalDebeDolar) {
        this.sumaTotalDebeDolar = sumaTotalDebeDolar;
    }

    public BigDecimal getSumaTotalHaberDolar() {
        return sumaTotalHaberDolar;
    }

    public void setSumaTotalHaberDolar(BigDecimal sumaTotalHaberDolar) {
        this.sumaTotalHaberDolar = sumaTotalHaberDolar;
    }

    public void actualizaComprobante() {
        try {
            cntComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void botonCancelarParaComponente() {
        setInSessionActivaDesactivaBotonesDetalle(false);
        activaDesactivaBotonesDetalle = getFromSessionActivaDesactivaBotonesDetalle();
    }

    public Boolean getActivaDesactivaBotonesDetalle() {
        return activaDesactivaBotonesDetalle;
    }

    public void setActivaDesactivaBotonesDetalle(Boolean activaDesactivaBotonesDetalle) {
        this.activaDesactivaBotonesDetalle = activaDesactivaBotonesDetalle;
    }

    public void generaNumeroPorComboTipoComprobante(ValueChangeEvent e) {
        try {
            cntComprobante.setNumero(cntComprobantesService.ultimoNumeroComprobante(cntComprobante));
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }

    public void guardaLogoTipoSellecionadoPrincipal() {
//        listaLogotipos = smLogotipoService.guardaImagenPrincipalEnLaListaDeImagenes(listaLogotipos, logotipoObjetoSelecciona);
    }

    public String cambiarEntidad() {
        try {
            if (cntDetalleComprobante.getIdDetalleComprobante() != null) {
                setMergeValues(cntComprobante);
                cntComprobantesService.mergeCntComprobantes(cntComprobante);
                setInSessionIdComprobante(cntComprobante.getIdComprobante());
                setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                setInSessionIdEntidad3(cntDetalleComprobante.getCntEntidad().getIdEntidad());
                return "planCuentas";
            } else {
                setInSessionIdDetalleComprobante(null);
                setInSessionIdComprobante(null);
                setInSessionIdEntidad3(null);
                setInSessionTipoRetencionOGrossing("");
                setInSessionIdComprobante(cntComprobante.getIdComprobante());
                return "planCuentas";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean activaBotonCambiaCuenta(CntDetalleComprobante detalleComprobante) {
        return detalleComprobante.getIdDetalleComprobante() != null;
    }

    public CntEntidad getEntidadModificaDetalle() {
        return entidadModificaDetalle;
    }

    public void setEntidadModificaDetalle(CntEntidad entidadModificaDetalle) {
        this.entidadModificaDetalle = entidadModificaDetalle;
    }

    public String irComprobanteDesdeAnulacion() {
        setInSessionIdComprobante(null);
        setInSessionIdDetalleComprobante(null);
        return "comprobantesList";
    }

    public CntAuxiliaresPorCuentaService getCntAuxiliaresPorCuentaService() {
        return cntAuxiliaresPorCuentaService;
    }

    public void setCntAuxiliaresPorCuentaService(CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService) {
        this.cntAuxiliaresPorCuentaService = cntAuxiliaresPorCuentaService;
    }

}
