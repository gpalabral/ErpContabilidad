package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumCampoMonetario;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.pojo.PojoCntDetalleComprobanteSumasSaldos;
import com.bap.erp.modelo.pojo.PojoCntEntidadBGyEERR;
import com.bap.erp.modelo.servicios.cnt.*;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.bap.erp.vista.utils.ReportManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

@ManagedBean(name = "cntImprimeSumasSadosComprobanteBacking")
@ViewScoped
public class CntImprimeSumasSadosComprobanteBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    private List<ParTipoComprobante> listaTipoComprobante;
    private ParTipoComprobante parTipoComprobante = new ParTipoComprobante();
    private String opcionFiltroImpresion = "PER";
    private String moneda = "BOL";
    private Boolean opcionImpresion = true;
    private Boolean activaFecha = true;
    private Boolean incluyeCeros = true;
    private String nivel = "1";
    private List<PojoCntDetalleComprobanteSumasSaldos> listaResultadoConsulta = new ArrayList<PojoCntDetalleComprobanteSumasSaldos>();
    private CntDetalleComprobante cntDetalleComprobanteElegido;
    private Date fechaHasta = new Date();
    //Codigo Henrry Guzman
    private BigDecimal sumaGeneralDebe = new BigDecimal("0.00");
    private BigDecimal sumaGeneralHaber = new BigDecimal("0.00");
    private BigDecimal sumaGeneralDebeDolar = new BigDecimal("0.00");
    private BigDecimal sumaGeneralHaberDolar = new BigDecimal("0.00");
    private BigDecimal sumaGeneralDeudor = new BigDecimal("0.00");
    private BigDecimal sumaGeneralAcreedor = new BigDecimal("0.00");
    private BigDecimal sumaGeneralDeudorDolar = new BigDecimal("0.00");
    private BigDecimal sumaGeneralAcreedorDolar = new BigDecimal("0.00");
    SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
    NumberFormat formatter = new DecimalFormat("#,#00.00");
    private String sumaGeneralDebes;
    private String sumaGeneralHabers;
    private String sumaGeneralDebeDolars;
    private String sumaGeneralHaberDolars;
    private String sumaGeneralDeudors;
    private String sumaGeneralAcreedors;
    private String sumaGeneralDeudorDolars;
    private String sumaGeneralAcreedorDolars;
    private PojoCntDetalleComprobanteSumasSaldos selectedPojoCntEntidadBGyEERR;
    private CntEntidad selected = new CntEntidad();

    public CntImprimeSumasSadosComprobanteBacking() {
    }

    @PostConstruct
    void initCntImprimeSumasSadosComprobanteBacking() {
        try {
            if (super.getFromSessionIdEntidadSumasySaldos() != null) {
                fechaHasta = super.getFromSessionFechaFin();
                moneda = super.getFromSessionTipoMoneda();
                incluyeCeros = super.getFromSessionCeros();
//                listaResultadoConsulta = cntDetalleComprobanteService.obtieneListaCompletaParaSumasSaldos(fechaHasta, super.getFromSessionNivel(), incluyeCeros);
                listaResultadoConsulta = cntDetalleComprobanteService.obtieneListaFinal(fechaHasta, super.getFromSessionNivel(), incluyeCeros);
                selectedPojoCntEntidadBGyEERR = listaResultadoConsulta.get(getFromSessionValorIndice());
                selected = selectedPojoCntEntidadBGyEERR.getIdEntidad();
            }
        } catch (Exception e) {
        }

    }

    public String cancelaImpresionDeComprobantes() {
        limpiarVariablesSession();
        return "comprobantesList";
    }

    public List<PojoCntDetalleComprobanteSumasSaldos> listaDetallesComprobantesPorConsulta() throws Exception {
        Date fechaInicial = new Date();
        if (fechaHasta != null) {
            if (cntDetalleComprobanteService.verificaPeriodoyGestion(fechaHasta)) {
                Date fechaInicial2 = new Date();
//                listaResultadoConsulta = cntDetalleComprobanteService.obtieneListaCompletaParaSumasSaldos(fechaHasta, Integer.parseInt(nivel), incluyeCeros); 
                listaResultadoConsulta = cntDetalleComprobanteService.obtieneListaFinal(fechaHasta, Integer.parseInt(nivel), incluyeCeros);
                Date fechaFinal2 = new Date();
                Long tipo2 = (fechaFinal2.getTime() - fechaInicial2.getTime()) / 1000;
                sumaGeneralDebe = cntDetalleComprobanteService.obtieneSumasgeneralesporTipoDeColumna(EnumCampoMonetario.DEBE.getCodigo(), fechaHasta);
                sumaGeneralHaber = cntDetalleComprobanteService.obtieneSumasgeneralesporTipoDeColumna(EnumCampoMonetario.HABER.getCodigo(), fechaHasta);
                sumaGeneralDeudor = cntDetalleComprobanteService.obtieneSumasgeneralesporTipoDeColumna(EnumCampoMonetario.DEUDOR.getCodigo(), fechaHasta);
                sumaGeneralAcreedor = cntDetalleComprobanteService.obtieneSumasgeneralesporTipoDeColumna(EnumCampoMonetario.ACREEDOR.getCodigo(), fechaHasta);

                sumaGeneralDebeDolar = cntDetalleComprobanteService.obtieneSumasgeneralesporTipoDeColumna(EnumCampoMonetario.DEBE_DOLAR.getCodigo(), fechaHasta);
                sumaGeneralHaberDolar = cntDetalleComprobanteService.obtieneSumasgeneralesporTipoDeColumna(EnumCampoMonetario.HABER_DOLAR.getCodigo(), fechaHasta);
                sumaGeneralDeudorDolar = cntDetalleComprobanteService.obtieneSumasgeneralesporTipoDeColumna(EnumCampoMonetario.DEUDOR_DOLAR.getCodigo(), fechaHasta);
                sumaGeneralAcreedorDolar = cntDetalleComprobanteService.obtieneSumasgeneralesporTipoDeColumna(EnumCampoMonetario.ACREEDOR_DOLAR.getCodigo(), fechaHasta);

                sumaGeneralDebes = formatter.format(sumaGeneralDebe);
                sumaGeneralHabers = formatter.format(sumaGeneralHaber);
                sumaGeneralDeudors = formatter.format(sumaGeneralDeudor);
                sumaGeneralAcreedors = formatter.format(sumaGeneralAcreedor);

                sumaGeneralDebeDolars = formatter.format(sumaGeneralDebeDolar);
                sumaGeneralHaberDolars = formatter.format(sumaGeneralHaberDolar);
                sumaGeneralDeudorDolars = formatter.format(sumaGeneralDeudorDolar);
                sumaGeneralAcreedorDolars = formatter.format(sumaGeneralAcreedorDolar);
            } else {
                MessageUtils.addErrorMessage("...Periodo no permitido fecha fuera de trabajo ..!!! ");
            }
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
        try {
//            listaResultadoConsulta = cntDetalleComprobanteService.obtieneListaCompletaParaSumasSaldos(fechaHasta, Integer.parseInt(nivel), incluyeCeros);
            listaResultadoConsulta = cntDetalleComprobanteService.obtieneListaFinal(fechaHasta, Integer.parseInt(nivel), incluyeCeros);
        } catch (Exception e) {
        }
    }

    public void activaCeros(ValueChangeEvent e) throws Exception {
//        listaResultadoConsulta = cntDetalleComprobanteService.obtieneListaCompletaParaSumasSaldos(fechaHasta, Integer.parseInt(nivel), incluyeCeros);
        listaResultadoConsulta = cntDetalleComprobanteService.obtieneListaFinal(fechaHasta, Integer.parseInt(nivel), incluyeCeros);
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

    public List<PojoCntDetalleComprobanteSumasSaldos> getListaResultadoConsulta() {
        return listaResultadoConsulta;
    }

    public void setListaResultadoConsulta(List<PojoCntDetalleComprobanteSumasSaldos> listaResultadoConsulta) {
        this.listaResultadoConsulta = listaResultadoConsulta;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public BigDecimal getSumaGeneralDebe() {
        return sumaGeneralDebe;
    }

    public void setSumaGeneralDebe(BigDecimal sumaGeneralDebe) {
        this.sumaGeneralDebe = sumaGeneralDebe;
    }

    public BigDecimal getSumaGeneralHaber() {
        return sumaGeneralHaber;
    }

    public void setSumaGeneralHaber(BigDecimal sumaGeneralHaber) {
        this.sumaGeneralHaber = sumaGeneralHaber;
    }

    public BigDecimal getSumaGeneralDebeDolar() {
        return sumaGeneralDebeDolar;
    }

    public void setSumaGeneralDebeDolar(BigDecimal sumaGeneralDebeDolar) {
        this.sumaGeneralDebeDolar = sumaGeneralDebeDolar;
    }

    public BigDecimal getSumaGeneralHaberDolar() {
        return sumaGeneralHaberDolar;
    }

    public void setSumaGeneralHaberDolar(BigDecimal sumaGeneralHaberDolar) {
        this.sumaGeneralHaberDolar = sumaGeneralHaberDolar;
    }

    public BigDecimal getSumaGeneralDeudor() {
        return sumaGeneralDeudor;
    }

    public void setSumaGeneralDeudor(BigDecimal sumaGeneralDeudor) {
        this.sumaGeneralDeudor = sumaGeneralDeudor;
    }

    public BigDecimal getSumaGeneralAcreedor() {
        return sumaGeneralAcreedor;
    }

    public void setSumaGeneralAcreedor(BigDecimal sumaGeneralAcreedor) {
        this.sumaGeneralAcreedor = sumaGeneralAcreedor;
    }

    public BigDecimal getSumaGeneralDeudorDolar() {
        return sumaGeneralDeudorDolar;
    }

    public void setSumaGeneralDeudorDolar(BigDecimal sumaGeneralDeudorDolar) {
        this.sumaGeneralDeudorDolar = sumaGeneralDeudorDolar;
    }

    public BigDecimal getSumaGeneralAcreedorDolar() {
        return sumaGeneralAcreedorDolar;
    }

    public void setSumaGeneralAcreedorDolar(BigDecimal sumaGeneralAcreedorDolar) {
        this.sumaGeneralAcreedorDolar = sumaGeneralAcreedorDolar;
    }

    public String reporteSumasySaldos_action() {
        String fechaHoy = formateador.format(new Date());
        try {
            Map parameters = new HashMap();//mapear
            parameters.put("moneda", moneda);
            parameters.put("fechaHoy", fechaHoy);
            ReportManager report = new ReportManager();
            report.setReportParam(parameters);
            report.drawReport(FacesContext.getCurrentInstance(), cntDetalleComprobanteService.obtieneListaCompletaParaSumasSaldos(fechaHasta, Integer.parseInt(nivel), incluyeCeros), "reporte_SumasySaldos");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSumaGeneralDebes() {
        return sumaGeneralDebes;
    }

    public void setSumaGeneralDebes(String sumaGeneralDebes) {
        this.sumaGeneralDebes = sumaGeneralDebes;
    }

    public String getSumaGeneralHabers() {
        return sumaGeneralHabers;
    }

    public void setSumaGeneralHabers(String sumaGeneralHabers) {
        this.sumaGeneralHabers = sumaGeneralHabers;
    }

    public String getSumaGeneralDebeDolars() {
        return sumaGeneralDebeDolars;
    }

    public void setSumaGeneralDebeDolars(String sumaGeneralDebeDolars) {
        this.sumaGeneralDebeDolars = sumaGeneralDebeDolars;
    }

    public String getSumaGeneralHaberDolars() {
        return sumaGeneralHaberDolars;
    }

    public void setSumaGeneralHaberDolars(String sumaGeneralHaberDolars) {
        this.sumaGeneralHaberDolars = sumaGeneralHaberDolars;
    }

    public String getSumaGeneralDeudors() {
        return sumaGeneralDeudors;
    }

    public void setSumaGeneralDeudors(String sumaGeneralDeudors) {
        this.sumaGeneralDeudors = sumaGeneralDeudors;
    }

    public String getSumaGeneralAcreedors() {
        return sumaGeneralAcreedors;
    }

    public void setSumaGeneralAcreedors(String sumaGeneralAcreedors) {
        this.sumaGeneralAcreedors = sumaGeneralAcreedors;
    }

    public String getSumaGeneralDeudorDolars() {
        return sumaGeneralDeudorDolars;
    }

    public void setSumaGeneralDeudorDolars(String sumaGeneralDeudorDolars) {
        this.sumaGeneralDeudorDolars = sumaGeneralDeudorDolars;
    }

    public String getSumaGeneralAcreedorDolars() {
        return sumaGeneralAcreedorDolars;
    }

    public void setSumaGeneralAcreedorDolars(String sumaGeneralAcreedorDolars) {
        this.sumaGeneralAcreedorDolars = sumaGeneralAcreedorDolars;
    }

    public void obtieneObjeto(ValueChangeEvent event) {
        selected = selectedPojoCntEntidadBGyEERR.getIdEntidad();
        setInSessionValorIndice(selectedPojoCntEntidadBGyEERR.getValorIndex());
    }

    public CntEntidad getSelected() {
        return selected;
    }

    public void setSelected(CntEntidad selected) {
        this.selected = selected;
    }

    public PojoCntDetalleComprobanteSumasSaldos getSelectedPojoCntEntidadBGyEERR() {
        return selectedPojoCntEntidadBGyEERR;
    }

    public void setSelectedPojoCntEntidadBGyEERR(PojoCntDetalleComprobanteSumasSaldos selectedPojoCntEntidadBGyEERR) {
        this.selectedPojoCntEntidadBGyEERR = selectedPojoCntEntidadBGyEERR;
    }

    public String verMayorCntaElegida() {
        if (selected.getIdEntidad() != null) {
            setInSessionIdEntidadSumasySaldos(selected.getIdEntidad());
            setInSessionFechaFin(fechaHasta);
            setInSessionNivel(Integer.parseInt(nivel));
            setInSessionCeros(incluyeCeros);
            setInSessionTipoMoneda(moneda);
            setInSessionTipoReporte("SS");
            return "libro_Mayor";
        } else {
            MessageUtils.addErrorMessage("Debe Seleccionar una Cuenta Último nivel para ver su Mayor ...");
            return null;
        }
    }
    
     //método para exporta a excel Jacqueline Carvajal 
    public void postProcessXLS(Object document) {
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

        for (int i = 0; i < 5; i++) {  //CANTIDAD DE COLUMNAS DE DATATABLE
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
