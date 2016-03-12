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
import java.util.Collection;
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

@ManagedBean(name = "cntReporte_EERRBacking")
@ViewScoped
public class CntReporte_EERRBacking extends AbstractManagedBean implements Serializable {

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
    private List<PojoCntEntidadBGyEERR> listaEERR = new ArrayList<PojoCntEntidadBGyEERR>();
    private List<PojoCntEntidadBGyEERR> listaReporteEERR = new ArrayList<PojoCntEntidadBGyEERR>();
    private CntDetalleComprobante cntDetalleComprobanteElegido;
    private PojoCntEntidadBGyEERR selectedPojoCntEntidadBGyEERR;

    private Date fechaDesde = new Date();
    private Date fechaHasta = new Date();
    private String moneda = "BOL";
    private String nivel_aux = "1";
    private CntEntidad selected = new CntEntidad();
    private BigDecimal montoTotalBolivianos = new BigDecimal("0.00");
    private BigDecimal montoTotalDolares = new BigDecimal("0.00");
    BigDecimal sumaEgreso = new BigDecimal(0.00);
    BigDecimal sumaIngreso = new BigDecimal(0.00);
    BigDecimal resultadoBG = new BigDecimal(0.00);
    BigDecimal sumaEgresoSus = new BigDecimal(0.00);
    BigDecimal sumaIngresoSus = new BigDecimal(0.00);
    BigDecimal resultadoBGSus = new BigDecimal(0.00);
    private Boolean habilitaUoP = false;
    private String utilOperdida;
    private CntEntidad cntEntidad = new CntEntidad();
    SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
    private String fechaPracticadoA;

    @PostConstruct
    void initCntReporte_EERRBacking() {
        try {
            if (super.getFromSessionIdEntidadEERRR() != null) {
                cntEntidad = cntEntidadesService.find(CntEntidad.class, super.getFromSessionIdEntidadEERRR());
                fechaDesde = super.getFromSessionFechaInicio();
                fechaHasta = super.getFromSessionFechaFin();
                incluyeCeros = super.getFromSessionCeros();
                listaEERR = cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, super.getFromSessionNivel(), super.getFromSessionCeros(), "EERR");
                montoTotalBolivianos = listaEERR.get(0).getMontoMonedaUno();
                montoTotalDolares = listaEERR.get(0).getMontoMonedaDos();
                fechaPracticadoA = formateador.format(fechaHasta);
                devuelvePositivo();
                devuelvePositivoSus();
                PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
                entidad = obtieneResultados();
                listaEERR.add(entidad);
                selectedPojoCntEntidadBGyEERR = listaEERR.get(getFromSessionValorIndice() - 1);
                selected = selectedPojoCntEntidadBGyEERR.getIdEntidad();
                setInSessionIdEntidadEERRR(null);

            }
        } catch (Exception e) {
        }
    }

    public PojoCntEntidadBGyEERR obtieneResultados() {
        PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
        if (resultadoBG.compareTo(new BigDecimal(0.00)) == - 1) {
            entidad.setDescripcion("RESULTADO :  PERDIDA");
        } else {
            if (resultadoBG.compareTo(new BigDecimal(0.00)) == 1) {
                entidad.setDescripcion("RESULTADO  : UTILIDAD");
            }
        }
        System.out.println("..descripcion es : " + entidad.getDescripcion());
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
    public String reporteEERR_action() {
        String tipoReporte = "EERR";
        try {
            Map parameters = new HashMap();//mapear
            parameters.put("fechaDesde", fechaDesde);
            parameters.put("fechaHasta", fechaHasta);
            parameters.put("moneda", moneda);
            parameters.put("tipoReporte", tipoReporte);
            parameters.put("sumaEgreso", sumaEgreso);
            parameters.put("sumaEgresoSus", sumaEgresoSus);
            parameters.put("sumaIngreso", sumaIngreso);
            parameters.put("sumaIngresoSus", sumaIngresoSus);
            parameters.put("resultadoBG", resultadoBG);
            parameters.put("resultadoBGSus", resultadoBGSus);

            System.out.println("...parameters  ..: " + parameters);

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);
//            report.drawReport(FacesContext.getCurrentInstance(), cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "EERR"), "reporte_EERR");//nombre jasper
            report.drawReport(FacesContext.getCurrentInstance(), getListaEERR(), "reporte_EERR");//nombre jasper
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generaLista() throws Exception {
        if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
            listaEERR = cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "EERR");
            montoTotalBolivianos = listaEERR.get(0).getMontoMonedaUno();
            montoTotalDolares = listaEERR.get(0).getMontoMonedaDos();
            devuelvePositivo();
            devuelvePositivoSus();
            PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
            entidad = obtieneResultados();
            listaEERR.add(entidad);
            fechaPracticadoA = formateador.format(fechaHasta);
        } else {

            MessageUtils.addErrorMessage("...Periodo no permitido fecha fuera de trabajo ..!!! ");
        }
        return "";
    }

    public void activaCeros_c(ValueChangeEvent e) throws Exception {
        if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
            listaEERR = cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "EERR");
            fechaPracticadoA = formateador.format(fechaHasta);
            PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
            entidad = obtieneResultados();
            listaEERR.add(entidad);
        } else {
            MessageUtils.addErrorMessage("...Periodo no permitido fecha fuera de trabajo ..!!! ");
        }
    }

    public void activaNivel_c() {
        try {
            if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
                listaEERR = cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "EERR");
                fechaPracticadoA = formateador.format(fechaHasta);
                PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
                entidad = obtieneResultados();
                listaEERR.add(entidad);
            } else {
                MessageUtils.addErrorMessage("...Periodo no permitido fecha fuera de trabajo ..!!! ");
            }
        } catch (Exception e) {
        }
    }

    public void devuelvePositivo() {
        BigDecimal[] resu = new BigDecimal[2];
        resu = cntDetalleComprobanteService.obtieneMontoTotalEERR(fechaDesde, fechaHasta, "EERR");
        sumaEgreso = resu[0];
        if (resu[1].compareTo(BigDecimal.ONE) == -1) {
            sumaIngreso = resu[1].negate();
        } else {
            sumaIngreso = resu[1];
        }
        resultadoBG = sumaIngreso.subtract(sumaEgreso);

    }

    public void devuelvePositivoSus() {
        BigDecimal[] resu = new BigDecimal[2];
        resu = cntDetalleComprobanteService.obtieneMontoTotalSusEERR(fechaDesde, fechaHasta, "EERR");
        sumaEgresoSus = resu[0];
        if (resu[1].compareTo(BigDecimal.ONE) == -1) {
            sumaIngresoSus = resu[1].negate();
        } else {
            sumaIngresoSus = resu[1];
        }
        resultadoBGSus = sumaIngresoSus.subtract(sumaEgresoSus);
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

    public List<PojoCntEntidadBGyEERR> getListaEERR() {
        return listaEERR;
    }

    public void setListaEERR(List<PojoCntEntidadBGyEERR> listaEERR) {
        this.listaEERR = listaEERR;
    }

//    public List<PojoCntEntidadBGyEERR> getListaEERR() {
////        try {
////            if (listaEERR.isEmpty() || listaEERR != null) {
////                if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
////                    listaEERR = cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "EERR");
////                    PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
////                    entidad = obtieneResultados();
////                    listaEERR.add(entidad);
////                }
////            }
////            System.out.println("...lista reporte retornando es : " + listaReporteEERR.size());
//            return listaEERR;
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return Collections.EMPTY_LIST;
//
//    }
//
//    public void setListaEERR(List<PojoCntEntidadBGyEERR> listaEERR) {
//        this.listaEERR = listaEERR;
//    }
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
        System.out.println("...event :..." + event + "...;;;;" + selectedPojoCntEntidadBGyEERR);
        selected = selectedPojoCntEntidadBGyEERR.getIdEntidad();
        System.out.println("...select es -----:" + selected);
        setInSessionValorIndice(selectedPojoCntEntidadBGyEERR.getValorIndex());
    }

    public String verMayorCntaElegida() {
        System.out.println("....en ver mayor : select  " + selected + "..fecha desde : " + fechaDesde + "..hasta ::::" + fechaHasta);
        if (selected.getIdEntidad() != null) {
            setInSessionIdEntidadEERRR(selected.getIdEntidad());
            setInSessionFechaInicio(fechaDesde);
            setInSessionFechaFin(fechaHasta);
            setInSessionNivel(Integer.parseInt(nivel_aux));
            setInSessionCeros(incluyeCeros);
            setInSessionTipoReporte("EERR");
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

    public BigDecimal getResultadoBGSus() {
        return resultadoBGSus;
    }

    public void setResultadoBGSus(BigDecimal resultadoBGSus) {
        this.resultadoBGSus = resultadoBGSus;
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

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public List<PojoCntEntidadBGyEERR> getListaReporteEERR() {
        try {
            if (listaReporteEERR.isEmpty() || listaReporteEERR != null) {
                if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
                    listaReporteEERR = cntDetalleComprobanteService.listaEERRctrlCeros(fechaDesde, fechaHasta, Integer.parseInt(nivel_aux), incluyeCeros, "EERR");
                    PojoCntEntidadBGyEERR entidad = new PojoCntEntidadBGyEERR();
                    entidad = obtieneResultados();
                    listaReporteEERR.add(entidad);
                }
            }
            return listaReporteEERR;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;

    }

    public void setListaReporteEERR(List<PojoCntEntidadBGyEERR> listaReporteEERR) {
        this.listaReporteEERR = listaReporteEERR;
    }

    //método para exporta a excel Jacqueline Carvajal 
    public void postProcessXLS(Object document) {
        System.out.println("...expo");
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

        for (int i = 0; i < 10; i++) {  //CANTIDAD DE COLUMNAS DE DATATABLE
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
