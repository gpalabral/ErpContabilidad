package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
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
import java.util.Collections;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.annotations.common.util.StringHelper;

@ManagedBean(name = "cntReporteBalanceGralBacking")
@ViewScoped
public class CntReporteBalanceGralBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntMascarasService}")
    private CntMascarasService cntMascarasService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    private List<ParTipoComprobante> listaTipoComprobante;
    private ParTipoComprobante parTipoComprobante = new ParTipoComprobante();
    private String opcionFiltroImpresion = "FEC";
    private Boolean activaFecha = false;
    private Boolean incluyeCeros = false;
    private int nivel;
    private List<PojoCntEntidadBGyEERR> listaBalanceGeneral = new ArrayList<PojoCntEntidadBGyEERR>();
    private List<PojoCntEntidadBGyEERR> listaReporteBalanceGeneral = new ArrayList<PojoCntEntidadBGyEERR>();
    private CntDetalleComprobante cntDetalleComprobanteElegido;
    private PojoCntEntidadBGyEERR selectedPojoCntEntidadBGyEERR;

    private Date fechaHasta = new Date();
    private String moneda = "BOL";
    private String nivel_aux = "1";
    private CntEntidad selected = new CntEntidad();
    private BigDecimal montoTotalBolivianos = new BigDecimal("0.00");
    private BigDecimal montoTotalDolares = new BigDecimal("0.00");
    BigDecimal sumaAct = new BigDecimal(0.00);
    BigDecimal sumaPas = new BigDecimal(0.00);
    BigDecimal sumaPatri = new BigDecimal(0.00);
    BigDecimal resultadoBG = new BigDecimal(0.00);
    BigDecimal sumaActSus = new BigDecimal(0.00);
    BigDecimal sumaPasSus = new BigDecimal(0.00);
    BigDecimal sumaPatriSus = new BigDecimal(0.00);
    BigDecimal resultadoBGSus = new BigDecimal(0.00);
    private Boolean habilitaUoP = false;
    private String utilOperdida;
    private CntEntidad cntEntidad = new CntEntidad();
    SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
    private String fechaPracticadoA;
    //VARIABLES PARA AUMENTAR RESULTADO EERR
    private List<PojoCntEntidadBGyEERR> listaEERR = new ArrayList<PojoCntEntidadBGyEERR>();
//    private Date fechaDesde = new Date("31/12/2014");
    Date fechaDesde = new Date("01/01/2015");
    BigDecimal sumaEgreso = new BigDecimal(0.00);
    BigDecimal sumaIngreso = new BigDecimal(0.00);
    BigDecimal resultadoBGEERR = new BigDecimal(0.00);
    BigDecimal sumaEgresoSus = new BigDecimal(0.00);
    BigDecimal sumaIngresoSus = new BigDecimal(0.00);
    BigDecimal resultadoBGSusEERR = new BigDecimal(0.00);
    private String resultadoPerdida;
    private String resultadoUtilidad;
    private String resultadoPerdidabg;
    private String resultadoUtilidadbg;

    @PostConstruct
    void initCntReporteBalanceGeneralBacking() {
        try {
            if (super.getFromSessionIdEntidadBG() != null) {
                cntEntidad = cntEntidadesService.find(CntEntidad.class, super.getFromSessionIdEntidadBG());
                fechaHasta = super.getFromSessionFechaFin();
                incluyeCeros = super.getFromSessionCeros();
                moneda = super.getFromSessionTipoMoneda();
                System.out.println("...en init fecha desde es : " + fechaDesde);
                listaBalanceGeneral = cntDetalleComprobanteService.listaPlanCuentasParaBalanceGeneralCeros(fechaHasta, super.getFromSessionNivel(), incluyeCeros, "BG");
                listaEERR = cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "EERR");
                System.out.println(" lista err es : " + listaEERR.size());
                montoTotalBolivianos = listaBalanceGeneral.get(0).getMontoMonedaUno();
                montoTotalDolares = listaBalanceGeneral.get(0).getMontoMonedaDos();
                devuelvePositivo();
                devuelvePositivoSus();
                calculaResultadoBG();
                calculaResultadoBGSus();
                PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
                entidad = obtieneResultados();
                listaBalanceGeneral.add(entidad);
                fechaPracticadoA = formateador.format(fechaHasta);
                System.out.println("...valor indice es ...: " + getFromSessionValorIndice());
                selectedPojoCntEntidadBGyEERR = listaBalanceGeneral.get(getFromSessionValorIndice() - 1);
                selected = selectedPojoCntEntidadBGyEERR.getIdEntidad();
                obtieneResultadosEERR();

                setInSessionTipoMoneda(null);
                setInSessionIdEntidadBG(null);

            }
        } catch (Exception e) {
        }

    }

    public PojoCntEntidadBGyEERR obtieneResultados() {
        PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
        if (resultadoBG.compareTo(new BigDecimal(0.00)) == - 1) {
            entidad.setDescripcion("RESULTADO :  PERDIDA");
            resultadoPerdidabg = "TOTAL RESULTADO :  PERDIDA";
        } else {
            if (resultadoBG.compareTo(new BigDecimal(0.00)) == 1) {
                entidad.setDescripcion("RESULTADO  : UTILIDAD");
                resultadoUtilidadbg = "TOTAL RESULTADO :  UTILIDAD";
            }
        }
        entidad.setNivel1bs(resultadoBG);
        entidad.setNivel2bs(resultadoBG);
        entidad.setNivel3bs(resultadoBG);
        entidad.setNivel4bs(resultadoBG);
        entidad.setNivel5bs(resultadoBG);
        entidad.setNivel6bs(resultadoBG);
        entidad.setMontoMonedaUno(resultadoBG);
        entidad.setNivel1sus(resultadoBGSus);
        entidad.setNivel2sus(resultadoBGSus);
        entidad.setNivel3sus(resultadoBGSus);
        entidad.setNivel4sus(resultadoBGSus);
        entidad.setNivel5sus(resultadoBGSus);
        entidad.setNivel6sus(resultadoBGSus);
        entidad.setMontoMonedaDos(resultadoBGSus);
        return entidad;
    }

    public void activaFechaFiltro() {
        if (opcionFiltroImpresion.equals("PER")) {
            activaFecha = true;

        } else {
            activaFecha = false;
        }
    }

    public List<CntNivel> listaNivelesComprobantes() throws Exception {
        return cntDetalleComprobanteService.listaDeNiveles();
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public CntMascarasService getCntMascarasService() {
        return cntMascarasService;
    }

    public void setCntMascarasService(CntMascarasService cntMascarasService) {
        this.cntMascarasService = cntMascarasService;
    }

    /*  METODO PARA REPORTE  BALANCE GENERAL  20/04/2015 Jacqueline Carvajal */
    public String reporteBalanceGeneral_action() {
        String tipoReporte = "BG";
        obtieneResultadosEERR();

        try {
            Map parameters = new HashMap();//mapear
            parameters.put("fechaHasta", fechaHasta);
            parameters.put("moneda", moneda);
            parameters.put("tipoReporte", tipoReporte);
            parameters.put("sumaAct", sumaAct);
            parameters.put("sumaPas", sumaPas);
            parameters.put("sumaPatri", sumaPatri);
            parameters.put("resultadoBG", resultadoBG);
            parameters.put("sumaActSus", sumaActSus);
            parameters.put("sumaPasSus", sumaPasSus);
            parameters.put("sumaPatriSus", sumaPatriSus);
            parameters.put("resultadoBGSus", resultadoBGSus);
            parameters.put("resultadoBGEERR", resultadoBGEERR);
            parameters.put("resultadoBGSusEERR", resultadoBGSusEERR);
            parameters.put("resultadoPerdida", resultadoPerdida);
            parameters.put("resultadoUtilidad", resultadoUtilidad);
            parameters.put("resultadoPerdidabg", resultadoPerdidabg);
            parameters.put("resultadoUtilidadbg", resultadoUtilidadbg);

            System.out.println("...parameters  ..: " + parameters);

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);

//            report.drawReport(FacesContext.getCurrentInstance(), cntDetalleComprobanteService.listaPlanCuentasParaBalanceGeneralCeros(fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "BG"), "reporte_balanceGeneral");//nombre jasper
            System.out.println("...lista bg : " + listaBalanceGeneral.size());
            report.drawReport(FacesContext.getCurrentInstance(), listaBalanceGeneral, "reporte_balanceGeneral");//nombre jasper

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generaLista() throws Exception {
        System.out.println("...periodo::  " + nivel_aux);
        if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
            listaBalanceGeneral = cntDetalleComprobanteService.listaPlanCuentasParaBalanceGeneralCeros(fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "BG");
//            listaEERR = cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "EERR");
//            System.out.println("...lista err es : " + listaEERR.size());
            montoTotalBolivianos = listaBalanceGeneral.get(0).getMontoMonedaUno();
            montoTotalDolares = listaBalanceGeneral.get(0).getMontoMonedaDos();
            devuelvePositivo();
            devuelvePositivoSus();
            calculaResultadoBG();
            calculaResultadoBGSus();
            PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
            entidad = obtieneResultados();
            listaBalanceGeneral.add(entidad);
            fechaPracticadoA = formateador.format(fechaHasta);
//            devuelvePositivoEERR();
//            devuelvePositivoSusEERR();
//            obtieneResultadosEERR();

        } else {

            MessageUtils.addErrorMessage("...Periodo no permitido fecha fuera de trabajo ..!!! ");
        }
        return "";
    }

    public void activaCeros_c(ValueChangeEvent e) throws Exception {
        System.out.println("..actibva ceros.fecha es : " + fechaDesde);
        if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
            listaBalanceGeneral = cntDetalleComprobanteService.listaPlanCuentasParaBalanceGeneralCeros(fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "BG");
            listaEERR = cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "EERR");
            fechaPracticadoA = formateador.format(fechaHasta);
            PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
            entidad = obtieneResultados();
            obtieneResultadosEERR();
            listaBalanceGeneral.add(entidad);
        } else {
            MessageUtils.addErrorMessage("...Periodo no permitido fecha fuera de trabajo ..!!! ");
        }
    }

    public void activaNivel_c() {
        System.out.println(".activa nivel..fecha es : " + fechaDesde);
        try {
            if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
                listaBalanceGeneral = cntDetalleComprobanteService.listaPlanCuentasParaBalanceGeneralCeros(fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "BG");
                listaEERR = cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "EERR");
                fechaPracticadoA = formateador.format(fechaHasta);
                PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
                entidad = obtieneResultados();
                obtieneResultadosEERR();
                listaBalanceGeneral.add(entidad);
            } else {
                MessageUtils.addErrorMessage("...Periodo no permitido fecha fuera de trabajo ..!!! ");
            }
        } catch (Exception e) {
        }
    }

    public void devuelvePositivo() {
        BigDecimal[] resu = new BigDecimal[3];
        resu = cntDetalleComprobanteService.obtieneMontoTotal(fechaHasta, "BG");
        sumaAct = resu[0];
        if (resu[1].compareTo(BigDecimal.ONE) == -1) {
            sumaPas = resu[1].negate();
        } else {
            sumaPas = resu[1];
        }
        if (resu[2].compareTo(BigDecimal.ONE) == -1) {
            sumaPatri = resu[2].negate();
        } else {
            sumaPatri = resu[2];
        }
    }

    public void calculaResultadoBG() {
        resultadoBG = sumaAct.subtract(sumaPas.add(sumaPatri));
    }

    public void devuelvePositivoSus() {
        BigDecimal[] resu = new BigDecimal[3];
        resu = cntDetalleComprobanteService.obtieneMontoTotalSus(fechaHasta, "BG");

        sumaActSus = resu[0];
        if (resu[1].compareTo(BigDecimal.ONE) == -1) {
            sumaPasSus = resu[1].negate();
        } else {
            sumaPasSus = resu[1];
        }
        if (resu[2].compareTo(BigDecimal.ONE) == -1) {
            sumaPatriSus = resu[2].negate();
        } else {
            sumaPatriSus = resu[2];
        }
    }

    public void calculaResultadoBGSus() {
        resultadoBGSus = sumaActSus.subtract(sumaPasSus.add(sumaPatriSus));
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getNivel_aux() {
        return nivel_aux;
    }

    public void setNivel_aux(String nivel_aux) {
        this.nivel_aux = nivel_aux;
    }

    public List<PojoCntEntidadBGyEERR> getListaBalanceGeneral() {
        try {
            if (listaBalanceGeneral.isEmpty() || listaBalanceGeneral != null) {
                if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
                    listaBalanceGeneral = cntDetalleComprobanteService.listaPlanCuentasParaBalanceGeneralCeros(fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "BG");
                    PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
                    entidad = obtieneResultados();
                    listaBalanceGeneral.add(entidad);
                }

            }
            System.out.println("...retornnado es : " + listaBalanceGeneral.size());
            return listaBalanceGeneral;
        } catch (Exception e) {
        }
        return Collections.EMPTY_LIST;

    }

    public void setListaBalanceGeneral(List<PojoCntEntidadBGyEERR> listaBalanceGeneral) {
        this.listaBalanceGeneral = listaBalanceGeneral;
    }

    public CntEntidad getSelected() {
        return selected;
    }

    public void setSelected(CntEntidad selected) {
        this.selected = selected;
    }

    public PojoCntEntidadBGyEERR getSelectedPojoCntEntidadBGyEERR() {
        return selectedPojoCntEntidadBGyEERR;
    }

    public void setSelectedPojoCntEntidadBGyEERR(PojoCntEntidadBGyEERR selectedPojoCntEntidadBGyEERR) {
        this.selectedPojoCntEntidadBGyEERR = selectedPojoCntEntidadBGyEERR;
    }

    public void obtieneObjeto(ValueChangeEvent event) {
        selected = selectedPojoCntEntidadBGyEERR.getIdEntidad();
        setInSessionValorIndice(selectedPojoCntEntidadBGyEERR.getValorIndex());
    }

    public String verMayorCntaElegida() {
        if (selected.getIdEntidad() != null) {
            setInSessionIdEntidadBG(selected.getIdEntidad());
            setInSessionTipoReporte("BG");
            setInSessionFechaFin(fechaHasta);
            setInSessionNivel(Integer.parseInt(nivel_aux));
            setInSessionCeros(incluyeCeros);
            setInSessionTipoMoneda(moneda);

            return "libro_Mayor";
        } else {
            MessageUtils.addErrorMessage("Debe Seleccionar una Cuenta Último nivel para ver su Mayor ...");
            return null;
        }
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

    public BigDecimal getSumaAct() {
        return sumaAct;
    }

    public void setSumaAct(BigDecimal sumaAct) {
        this.sumaAct = sumaAct;
    }

    public BigDecimal getSumaPas() {
        return sumaPas;
    }

    public void setSumaPas(BigDecimal sumaPas) {
        this.sumaPas = sumaPas;
    }

    public BigDecimal getSumaPatri() {
        return sumaPatri;
    }

    public void setSumaPatri(BigDecimal sumaPatri) {
        this.sumaPatri = sumaPatri;
    }

    public BigDecimal getResultadoBG() {
        return resultadoBG;
    }

    public void setResultadoBG(BigDecimal resultadoBG) {
        this.resultadoBG = resultadoBG;
    }

    public Boolean getHabilitaUoP() {
        return habilitaUoP;
    }

    public void setHabilitaUoP(Boolean habilitaUoP) {
        this.habilitaUoP = habilitaUoP;
    }

    public String getUtilOperdida() {
        return utilOperdida;
    }

    public void setUtilOperdida(String utilOperdida) {
        this.utilOperdida = utilOperdida;
    }

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    public String getFechaPracticadoA() {
        return fechaPracticadoA;
    }

    public void setFechaPracticadoA(String fechaPracticadoA) {
        this.fechaPracticadoA = fechaPracticadoA;
    }

    public BigDecimal getSumaActSus() {
        return sumaActSus;
    }

    public void setSumaActSus(BigDecimal sumaActSus) {
        this.sumaActSus = sumaActSus;
    }

    public BigDecimal getSumaPasSus() {
        return sumaPasSus;
    }

    public void setSumaPasSus(BigDecimal sumaPasSus) {
        this.sumaPasSus = sumaPasSus;
    }

    public BigDecimal getSumaPatriSus() {
        return sumaPatriSus;
    }

    public void setSumaPatriSus(BigDecimal sumaPatriSus) {
        this.sumaPatriSus = sumaPatriSus;
    }

    public BigDecimal getResultadoBGSus() {
        return resultadoBGSus;
    }

    public void setResultadoBGSus(BigDecimal resultadoBGSus) {
        this.resultadoBGSus = resultadoBGSus;
    }

    public List<PojoCntEntidadBGyEERR> getListaReporteBalanceGeneral() {
        try {
            if (listaReporteBalanceGeneral.isEmpty() || listaReporteBalanceGeneral != null) {
                if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
                    listaReporteBalanceGeneral = cntDetalleComprobanteService.listaPlanCuentasParaBalanceGeneralCeros(fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "BG");
                    PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
                    entidad = obtieneResultados();
                    obtieneResultadosEERR();
                    listaReporteBalanceGeneral.add(entidad);
                }

            }
            return listaReporteBalanceGeneral;
        } catch (Exception e) {
        }
        return Collections.EMPTY_LIST;

    }

    public void setListaReporteBalanceGeneral(List<PojoCntEntidadBGyEERR> listaReporteBalanceGeneral) {
        this.listaReporteBalanceGeneral = listaReporteBalanceGeneral;
    }

    public List<PojoCntEntidadBGyEERR> getListaEERR() {
        return listaEERR;
    }

    public void setListaEERR(List<PojoCntEntidadBGyEERR> listaEERR) {
        this.listaEERR = listaEERR;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public void devuelvePositivoEERR() {
        BigDecimal[] resu = new BigDecimal[2];
        resu = cntDetalleComprobanteService.obtieneMontoTotalEERR(fechaDesde, fechaHasta, "EERR");
        sumaEgreso = resu[0];
        if (resu[1].compareTo(BigDecimal.ONE) == -1) {
            sumaIngreso = resu[1].negate();
        } else {
            sumaIngreso = resu[1];
        }
        resultadoBGEERR = sumaIngreso.subtract(sumaEgreso);

    }

    public void devuelvePositivoSusEERR() {
        BigDecimal[] resu = new BigDecimal[2];
        resu = cntDetalleComprobanteService.obtieneMontoTotalSusEERR(fechaDesde, fechaHasta, "EERR");
        sumaEgresoSus = resu[0];
        if (resu[1].compareTo(BigDecimal.ONE) == -1) {
            sumaIngresoSus = resu[1].negate();
        } else {
            sumaIngresoSus = resu[1];
        }
        resultadoBGSusEERR = sumaIngresoSus.subtract(sumaEgresoSus);
    }

    public String obtieneResultadosEERR() {
        System.out.println("...resultado es : " + resultadoBGEERR);
        if (resultadoBGEERR.compareTo(new BigDecimal(0.00)) == - 1) {
            resultadoPerdida = ("RESULTADO PERIODO: PERDIDA :");
        } else {
            if (resultadoBGEERR.compareTo(new BigDecimal(0.00)) == 1) {
                resultadoPerdida = ("RESULTADO PERIODO: UTILIDAD :");
            }
        }

        return null;
    }

    public BigDecimal getSumaEgreso() {
        return sumaEgreso;
    }

    public void setSumaEgreso(BigDecimal sumaEgreso) {
        this.sumaEgreso = sumaEgreso;
    }

    public BigDecimal getSumaIngreso() {
        return sumaIngreso;
    }

    public void setSumaIngreso(BigDecimal sumaIngreso) {
        this.sumaIngreso = sumaIngreso;
    }

    public BigDecimal getResultadoBGEERR() {
        return resultadoBGEERR;
    }

    public void setResultadoBGEERR(BigDecimal resultadoBGEERR) {
        this.resultadoBGEERR = resultadoBGEERR;
    }

    public BigDecimal getSumaEgresoSus() {
        return sumaEgresoSus;
    }

    public void setSumaEgresoSus(BigDecimal sumaEgresoSus) {
        this.sumaEgresoSus = sumaEgresoSus;
    }

    public BigDecimal getSumaIngresoSus() {
        return sumaIngresoSus;
    }

    public void setSumaIngresoSus(BigDecimal sumaIngresoSus) {
        this.sumaIngresoSus = sumaIngresoSus;
    }

    public BigDecimal getResultadoBGSusEERR() {
        return resultadoBGSusEERR;
    }

    public void setResultadoBGSusEERR(BigDecimal resultadoBGSusEERR) {
        this.resultadoBGSusEERR = resultadoBGSusEERR;
    }

    public String getResultadoPerdida() {
        return resultadoPerdida;
    }

    public void setResultadoPerdida(String resultadoPerdida) {
        this.resultadoPerdida = resultadoPerdida;
    }

    public String getResultadoUtilidad() {
        return resultadoUtilidad;
    }

    public void setResultadoUtilidad(String resultadoUtilidad) {
        this.resultadoUtilidad = resultadoUtilidad;
    }

    public String getResultadoPerdidabg() {
        return resultadoPerdidabg;
    }

    public void setResultadoPerdidabg(String resultadoPerdidabg) {
        this.resultadoPerdidabg = resultadoPerdidabg;
    }

    public String getResultadoUtilidadbg() {
        return resultadoUtilidadbg;
    }

    public void setResultadoUtilidadbg(String resultadoUtilidadbg) {
        this.resultadoUtilidadbg = resultadoUtilidadbg;
    }

    public void postProcessXLS(Object document) {
        System.out.println("....exportar....................................");

        HSSFWorkbook wb = (HSSFWorkbook) document;

        Font font = wb.createFont();

        font.setFontHeightInPoints((short) 10);

        font.setFontName("Arial");

        font.setColor(IndexedColors.WHITE.getIndex());

        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        HSSFSheet sheet = wb.getSheetAt(0);

        CellStyle style = wb.createCellStyle();

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_CENTER);

        style.setVerticalAlignment(CellStyle.VERTICAL_JUSTIFY);

        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());

        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < 7; i++) {  //CANTIDAD DE COLUMNAS DE DATATABLE
            sheet.autoSizeColumn(i);
        }

        int j = 0;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (j == 0) {
                    cell.setCellValue(cell.getStringCellValue().toUpperCase());
                    cell.setCellStyle(style);
                }
            }
            j++;
        }
    }
}
