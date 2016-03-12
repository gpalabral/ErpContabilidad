package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.servicios.cnt.*;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.bap.erp.vista.utils.ReportManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

@ManagedBean(name = "cntReporteLibroDeCompraBacking")
@ViewScoped
public class CntReporteLibroDeCompraBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntFacturacionService}")
    private CntFacturacionService cntFacturacionService;

    private Date fechaHasta = new Date();
    //Codigo Henrry Guzman
    private BigDecimal montoTotalBolivianos = new BigDecimal("0.00");
    private BigDecimal montoTotalDolares = new BigDecimal("0.00");
    SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");

    private int peridosList[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private int peridoParaBusqueda;
    private int anio;
    private BigDecimal descuento = new BigDecimal("0");
    List<CntFacturacion> listaReporteLibroDeCompra = new ArrayList<CntFacturacion>();
    private CntFacturacion selectedCntFacturacion;
    private CntFacturacion selected;

    public CntReporteLibroDeCompraBacking() {
    }

    @PostConstruct
    void initCntReporteLibroDeCompraBacking() {
        Calendar fecha = new GregorianCalendar();
        anio = fecha.get(Calendar.YEAR);
        descuento = new BigDecimal("0");
        try {
            peridoParaBusqueda = super.getFromSessionNivel();
            if (peridoParaBusqueda > 0 && super.getFromSessionIdEntidadFacturacion() != null) {
                selectedCntFacturacion = cntFacturacionService.find(CntFacturacion.class, super.getFromSessionIdEntidadFacturacion());
                selected = selectedCntFacturacion;
                listaReporteLibroDeCompra = cntFacturacionService.listaReporteCompraVenta("COMP", peridoParaBusqueda, anio);
            }
            super.setInSessionNivel(0);
            super.setInSessionIdEntidadFacturacion(null);
        } catch (Exception e) {
        }

    }

    public String cancelaImpresionDeComprobantes() {
        limpiarVariablesSession();
        return "comprobantesList";
    }

    public List<CntFacturacion> reporteLibroDeCompra() {
        try {
            listaReporteLibroDeCompra = cntFacturacionService.listaReporteCompraVenta("COMP", peridoParaBusqueda, anio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaReporteLibroDeCompra;
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

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
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

    public int[] getPeridosList() {
        return peridosList;
    }

    public void setPeridosList(int[] peridosList) {
        this.peridosList = peridosList;
    }

    public int getPeridoParaBusqueda() {
        return peridoParaBusqueda;
    }

    public void setPeridoParaBusqueda(int peridoParaBusqueda) {
        this.peridoParaBusqueda = peridoParaBusqueda;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public CntFacturacionService getCntFacturacionService() {
        return cntFacturacionService;
    }

    public void setCntFacturacionService(CntFacturacionService cntFacturacionService) {
        this.cntFacturacionService = cntFacturacionService;
    }

    public List<CntFacturacion> getListaReporteLibroDeCompra() {
        return listaReporteLibroDeCompra;
    }

    public void setListaReporteLibroDeCompra(List<CntFacturacion> listaReporteLibroDeCompra) {
        this.listaReporteLibroDeCompra = listaReporteLibroDeCompra;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal obtieneImporteNeto(BigDecimal A, BigDecimal B, BigDecimal C, BigDecimal D) {
        BigDecimal resultado = A.subtract(B.add(D.add(C)));
        return resultado;
    }

    /*  METODO PARA REPORTE  LIBRO DE VENTAS  22/04/2015 Jacqueline Carvajal */
    public String reporteLibroCompras_action() {
        try {
            Map parameters = new HashMap();//mapear
            parameters.put("peridoParaBusqueda", peridoParaBusqueda);
            parameters.put("anio", anio);

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);

            report.drawReport(FacesContext.getCurrentInstance(), cntFacturacionService.listaReporteCompraVenta("COMP", peridoParaBusqueda, anio), "reporte_libroCompras");//nombre jasper

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void obtieneObjeto(ValueChangeEvent event) {
        selected = selectedCntFacturacion;
    }

    public String verFacturacion_action() {
        if (selected.getIdFacturacion() != null) {
            super.setInSessionIdEntidadFacturacion(selected.getIdFacturacion());
            super.setInSessionNivel(peridoParaBusqueda);
            return "facturaCompra";
        } else {
            MessageUtils.addErrorMessage("Debe Seleccionar un registro para modificar ...");
            return null;
        }
    }

    public CntFacturacion getSelectedCntFacturacion() {
        return selectedCntFacturacion;
    }

    public void setSelectedCntFacturacion(CntFacturacion selectedCntFacturacion) {
        this.selectedCntFacturacion = selectedCntFacturacion;
    }

    public CntFacturacion getSelected() {
        return selected;
    }

    public void setSelected(CntFacturacion selected) {
        this.selected = selected;
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

        for (int i = 0; i < 13; i++) {  //CANTIDAD DE COLUMNAS DE DATATABLE
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
