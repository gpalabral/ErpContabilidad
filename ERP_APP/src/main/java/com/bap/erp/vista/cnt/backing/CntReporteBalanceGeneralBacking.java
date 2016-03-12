package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.pojo.PojoCntEntidadBGyEERR;
import com.bap.erp.modelo.servicios.cnt.*;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.bap.erp.vista.utils.ReportManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "cntReporteBalanceGeneralBacking")
@ViewScoped
public class CntReporteBalanceGeneralBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntMascarasService}")
    private CntMascarasService cntMascarasService;
    private List<ParTipoComprobante> listaTipoComprobante;
    private ParTipoComprobante parTipoComprobante = new ParTipoComprobante();
    private String opcionFiltroImpresion = "PER";
    private Boolean opcionImpresion = true;
    private Boolean activaFecha = true;
    private Boolean incluyeCeros = true;
    private int nivel;
    private List<PojoCntEntidadBGyEERR> listaResultadoConsulta = new ArrayList<PojoCntEntidadBGyEERR>();
    private CntDetalleComprobante cntDetalleComprobanteElegido;
    private Date fechaHasta = new Date();
    //Codigo Henrry Guzman
    private BigDecimal montoTotalBolivianos = new BigDecimal("0.00");
    private BigDecimal montoTotalDolares = new BigDecimal("0.00");
    SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");

    public CntReporteBalanceGeneralBacking() {
    }

    @PostConstruct
    void initCntReporteBalanceGeneralBacking() {
        nivel = cntMascarasService.obtieneMascarasPorGrrupoNivel(EnumGruposNivel.PLAN_CUENTAS.getCodigo()).getTamanioNivel();
    }

    public BigDecimal devuelvePositivo(BigDecimal monto) {
        if (monto.compareTo(BigDecimal.ONE) == -1) {
            monto = monto.negate();
        }
        return monto;
    }

    public String cancelaImpresionDeComprobantes() {
        limpiarVariablesSession();
        return "comprobantesList";
    }

    public List<PojoCntEntidadBGyEERR> listaDetallesComprobantesPorConsulta() throws Exception {
        Date fechaInicial = new Date();
        if (fechaHasta != null) {
            Date fechaInicial2 = new Date();
            listaResultadoConsulta = cntDetalleComprobanteService.listPlanCuentaParaBGyEERR(fechaHasta, "BG");
            Date fechaFinal2 = new Date();
            Long tipo2 = (fechaFinal2.getTime() - fechaInicial2.getTime()) / 1000;
            montoTotalBolivianos = listaResultadoConsulta.get(0).getMontoMonedaUno();
            montoTotalDolares = listaResultadoConsulta.get(0).getMontoMonedaDos();
            listaResultadoConsulta.remove(0);
        } else {
            MessageUtils.addErrorMessage("Es necesario llenar todos los campos para realizar la búsqueda");
        }
        Date fechaFinal = new Date();
        Long tipo = (fechaFinal.getTime() - fechaInicial.getTime()) / 1000;

        return listaResultadoConsulta;
    }

    public void activaFechaFiltro() {
        if (opcionFiltroImpresion.equals("PER")) {
            activaFecha = true;

        } else {
            activaFecha = false;
        }
    }

    public void activaNivel() {
//        try {
//            listaResultadoConsulta = cntDetalleComprobanteService.listPlanCuentaParaBGyEERR(fechaHasta, "BG", new BigDecimal("6.87"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        sw=false;
    }

    public void activaCeros(ValueChangeEvent e) throws Exception {
        listaResultadoConsulta = cntDetalleComprobanteService.listPlanCuentaParaBGyEERR(fechaHasta, "BG");
    }

    public List<CntNivel> listaNivelesComprobantes() throws Exception {
        return cntDetalleComprobanteService.listaDeNiveles();
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

    public CntComprobantesService getCntComprobantesService() {
        return cntComprobantesService;
    }

    public void setCntComprobantesService(CntComprobantesService cntComprobantesService) {
        this.cntComprobantesService = cntComprobantesService;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public List<ParTipoComprobante> getListaTipoComprobante() {
        return listaTipoComprobante;
    }

    public void setListaTipoComprobante(List<ParTipoComprobante> listaTipoComprobante) {
        this.listaTipoComprobante = listaTipoComprobante;
    }

    public Boolean getOpcionImpresion() {
        return opcionImpresion;
    }

    public void setOpcionImpresion(Boolean opcionImpresion) {
        this.opcionImpresion = opcionImpresion;
    }

    public ParTipoComprobante getParTipoComprobante() {
        return parTipoComprobante;
    }

    public void setParTipoComprobante(ParTipoComprobante parTipoComprobante) {
        this.parTipoComprobante = parTipoComprobante;
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public CntDetalleComprobante getCntDetalleComprobanteElegido() {
        return cntDetalleComprobanteElegido;
    }

    public void setCntDetalleComprobanteElegido(CntDetalleComprobante cntDetalleComprobanteElegido) {
        this.cntDetalleComprobanteElegido = cntDetalleComprobanteElegido;
    }

    public Boolean getActivaFechaFiltro() {
        return activaFecha;
    }

    public void setActivaFechaFiltro(Boolean activaFechaFiltro) {
        this.activaFecha = activaFechaFiltro;
    }

    public String getOpcionFiltroImpresion() {
        return opcionFiltroImpresion;
    }

    public void setOpcionFiltroImpresion(String opcionFiltroImpresion) {
        this.opcionFiltroImpresion = opcionFiltroImpresion;
    }

    public Boolean getActivaFecha() {
        return activaFecha;
    }

    public void setActivaFecha(Boolean activaFecha) {
        this.activaFecha = activaFecha;
    }

    public Boolean getIncluyeCeros() {
        return incluyeCeros;
    }

    public void setIncluyeCeros(Boolean incluyeCeros) {
        this.incluyeCeros = incluyeCeros;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public List<PojoCntEntidadBGyEERR> getListaResultadoConsulta() {
        return listaResultadoConsulta;
    }

    public void setListaResultadoConsulta(List<PojoCntEntidadBGyEERR> listaResultadoConsulta) {
        this.listaResultadoConsulta = listaResultadoConsulta;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public BigDecimal getMontoTotalBolivianos() {
        return montoTotalBolivianos;
    }

    public void setMontoTotalBolivianos(BigDecimal montoTotalBolivianos) {
        this.montoTotalBolivianos = montoTotalBolivianos;
    }

    public BigDecimal getMontoTotalDolares() {
        return montoTotalDolares;
    }

    public void setMontoTotalDolares(BigDecimal montoTotalDolares) {
        this.montoTotalDolares = montoTotalDolares;
    }

    public CntMascarasService getCntMascarasService() {
        return cntMascarasService;
    }

    public void setCntMascarasService(CntMascarasService cntMascarasService) {
        this.cntMascarasService = cntMascarasService;
    }

    private Boolean sw = true;

    public Boolean activaDatoParaVisualizar(PojoCntEntidadBGyEERR pojoCntEntidadBGyEERR) {
        return nivel >= pojoCntEntidadBGyEERR.getIdEntidad().getNivel();
    }

    public Boolean getSw() {
        return sw;
    }

    public void setSw(Boolean sw) {
        this.sw = sw;
    }

    /*  METODO PARA REPORTE  BALANCE GENERAL  20/04/2015 Jacqueline Carvajal */
    public String reporteBalanceGeneral_action() {
        try {
            Map parameters = new HashMap();//mapear
            parameters.put("fechaHasta", fechaHasta);

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);

            report.drawReport(FacesContext.getCurrentInstance(), listaResultadoConsulta, "reporte_balanceGeneral");//nombre jasper

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

        }
