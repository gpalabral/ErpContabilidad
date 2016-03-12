package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnf.ParSucursal;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.enums.EnumEstado;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.enums.EnumMovimientoFactura;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
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

@ManagedBean(name = "cntReporteLibroDeVentasBacking")
@ViewScoped
public class CntReporteLibroDeVentasBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntFacturacionService}")
    private CntFacturacionService cntFacturacionService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;

    private Date fechaHasta = new Date();
    //Codigo Henrry Guzman
    private BigDecimal montoTotalBolivianos = new BigDecimal("0.00");
    private BigDecimal montoTotalDolares = new BigDecimal("0.00");
    SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");

    private int peridosList[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private int peridoParaBusqueda;
    private int anio;
    private BigDecimal descuento = new BigDecimal("0");
    List<CntFacturacion> listaReporteLibroDeVentas = new ArrayList<CntFacturacion>();
    private CntFacturacion selectedCntFacturacion;
    private CntFacturacion selected;
    private String cpbte;
    private CntFacturacion cntFacturacion;
    private Date fechaActual;
    private List<ParSucursal> listaDeSucursales = new ArrayList<ParSucursal>();
    private Boolean activaCodigoControl = true;
    private String numeroAutorizacion = "";

    public CntReporteLibroDeVentasBacking() {
    }

    @PostConstruct
    void initCntReporteLibroDeVentasBacking() {
        Calendar fecha = new GregorianCalendar();
        anio = fecha.get(Calendar.YEAR);
        descuento = new BigDecimal("0");
        System.out.println("......." + getFromSessionIdEntidadFacturacion());
        try {
            peridoParaBusqueda = super.getFromSessionNivel();
            if (peridoParaBusqueda > 0 && super.getFromSessionIdEntidadFacturacion() != null) {
                selectedCntFacturacion = cntFacturacionService.find(CntFacturacion.class, super.getFromSessionIdEntidadFacturacion());
                selected = selectedCntFacturacion;
                System.out.println("....periodo " + peridoParaBusqueda + "..anio " + anio);
                listaReporteLibroDeVentas = cntFacturacionService.listaReporteCompraVenta("VENT", peridoParaBusqueda, anio);
                System.out.println("...lista es " + listaReporteLibroDeVentas.size());

            }
            if (super.getFromSessionIdEntidadFacturacion() == null) {
                System.out.println("...anulando factura.  ....");
                cntFacturacion = new CntFacturacion();
                fechaActual = new Date();

            }
            super.setInSessionNivel(0);
            super.setInSessionIdEntidadFacturacion(null);
        } catch (Exception e) {
        }

    }

    //<editor-fold defaultstate="collapsed" desc="Metodos facturacion ">
    public String cancelaImpresionDeComprobantes() {
        limpiarVariablesSession();
        return "comprobantesList";
    }

    public List<CntFacturacion> reporteLibroDeVentas() {
        try {
            listaReporteLibroDeVentas = cntFacturacionService.listaReporteCompraVenta("VENT", peridoParaBusqueda, anio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaReporteLibroDeVentas;
    }
  
    public List<CntFacturacion> reporteLibroDeVentasMigradas() {
        try {
            listaReporteLibroDeVentas = cntFacturacionService.listaReporteFacturaVentaMigrado("VENT", peridoParaBusqueda, anio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaReporteLibroDeVentas;
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

    public List<CntFacturacion> getListaReporteLibroDeVentas() {
        return listaReporteLibroDeVentas;
    }

    public void setListaReporteLibroDeVentas(List<CntFacturacion> listaReporteLibroDeVentas) {
        this.listaReporteLibroDeVentas = listaReporteLibroDeVentas;
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

    /*  METODO PARA REPORTE  LIBRO DE VENTAS  21/04/2015 Jacqueline Carvajal */
    public String reporteLibroVentas_action() {
        try {
            Map parameters = new HashMap();//mapear
            parameters.put("peridoParaBusqueda", peridoParaBusqueda);
            parameters.put("anio", anio);

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);

            report.drawReport(FacesContext.getCurrentInstance(), cntFacturacionService.listaReporteCompraVenta("VENT", peridoParaBusqueda, anio), "reporte_libroVentas");//nombre jasper

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*metodos para seleccionar registro y modificar datos de factura*/
    public void obtieneObjeto(ValueChangeEvent event) {
        selected = selectedCntFacturacion;
    }

    public String verFacturacion_action() {
        if (selected.getIdFacturacion() != null) {
            super.setInSessionIdEntidadFacturacion(selected.getIdFacturacion());
            super.setInSessionNivel(peridoParaBusqueda);
            return "facturaVenta";
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

    public String obtieneComprobante(CntFacturacion cntFacturacion) {
        try {
            cpbte = null;
            if (cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion) != null) {
                cpbte = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion).getCntComprobante().getNumero() + cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion).getCntComprobante().getParTipoComprobante().getCodigo();
            }
            return cpbte;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public String getCpbte() {
        return cpbte;
    }

    public void setCpbte(String cpbte) {
        this.cpbte = cpbte;
    }
//</editor-fold>

    public String anularFactura_action() {  // en realidad registra factura anulada
        System.out.println("....ir a facturaAnulada ....");
        return "facturaAnulada";
    }

    public String guardarFacturaAnulada_action() {  // en realidad registra factura anulada
        System.out.println("....registrando factura anulada ....");
        System.out.println("....numero fact  ...." + cntFacturacion.getNroInicial());
        System.out.println("....registrando factur nro autorizacion : " + cntFacturacion.getNroAutorizacion());
        System.out.println("....registrando factur nro autorizacion : " + cntFacturacion.getNroAutorizacion());
        System.out.println("....registrando factur sucursal : " + cntFacturacion.getSucursal());
        System.out.println("....registrando factur fecha : " + cntFacturacion.getFechaFactura());
        try {
            System.out.println("...valida es : " + cntFacturacionService.validaNumeroFactura(cntFacturacion));
            if (!cntFacturacionService.validaNumeroFactura(cntFacturacion)) {
                cntFacturacion.setMovimiento(EnumMovimientoFactura.FACTURA_VENTA.getCodigo());

                cntFacturacion.setMonto(BigDecimal.ZERO);
                cntFacturacion.setExcento(BigDecimal.ZERO);
                cntFacturacion.setIva(BigDecimal.ZERO);
                cntFacturacion.setNit(0L);
                cntFacturacion.setCreditoFiscalTransitorio("N");
//                cntFacturacion.setFechaFactura(cntFacturacion.getFechaFactura());
//                cntFacturacion.setNroInicial(cntFacturacion.getNroInicial());
                cntFacturacion.setNroFinal(cntFacturacion.getNroInicial());

                cntFacturacion.setRazonSocial("ANULADO");
                cntFacturacion.setNroAutorizacion(numeroAutorizacion);
                cntFacturacion.setLoginUsuario(getFromSessionNombreUsuario());
                cntFacturacion.setFechaAlta(new Date());
                cntFacturacion.setUsuarioAlta(getFromSessionNombreUsuario());
                cntFacturacion.setParParametrosAutorizacion(null);
                cntFacturacion.setParTipoFacturacion(null);
                cntFacturacion.setMontoSegMoneda(BigDecimal.ZERO);
                cntFacturacion.setExcentoSegMoneda(BigDecimal.ZERO);
                cntFacturacion.setIceSegMoneda(BigDecimal.ZERO);
                cntFacturacion.setIvaSegMoneda(BigDecimal.ZERO);

                cntFacturacion.setEstado(EnumEstado.ANULADO.getCodigo());

                cntFacturacionService.persistCntFacturacion(cntFacturacion);

                System.out.println("...registro correcto ...");

            } else {
                System.out.println("...existe factura no se puede guardar");
                MessageUtils.addInfoMessage("...existe factura no se puede guardar ..... " + cntFacturacion.getNroInicial());
            }
        } catch (Exception e) {
        }

        return null;
    }

    public void activaCodigoControlVenta(ValueChangeEvent e) {
        cntFacturacion.setNroAutorizacion(numeroAutorizacion);
        activaCodigoControl = cntFacturacionService.activaCodigoDeControl(cntFacturacion);
    }

    public List<ParSucursal> listaTodasLasSucursales() {
        if (listaDeSucursales.isEmpty()) {
            listaDeSucursales = parParametricasService.listaTodasLasSucursal();
        }
        return listaDeSucursales;
    }

    public CntFacturacion getCntFacturacion() {
        return cntFacturacion;
    }

    public void setCntFacturacion(CntFacturacion cntFacturacion) {
        this.cntFacturacion = cntFacturacion;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<ParSucursal> getListaDeSucursales() {
        return listaDeSucursales;
    }

    public void setListaDeSucursales(List<ParSucursal> listaDeSucursales) {
        this.listaDeSucursales = listaDeSucursales;
    }

    public Boolean getActivaCodigoControl() {
        return activaCodigoControl;
    }

    public void setActivaCodigoControl(Boolean activaCodigoControl) {
        this.activaCodigoControl = activaCodigoControl;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public void postProcessXLS(Object document) {
        System.out.println("....exportar....................................");

        System.out.println("....object ...:  " + document);

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

        for (int i = 0; i < 14; i++) {  //CANTIDAD DE COLUMNAS DE DATATABLE
            System.out.println("...sheet es  : " + sheet);
            System.out.println("...sheet es  : " + sheet.toString());
            sheet.autoSizeColumn(i);
        }

        int j = 0;
        for (Row row : sheet) {
            System.out.println("...row : " + row + " .... ");
            for (Cell cell : row) {
                System.out.println("...cell : " + cell + " .... ");
                if (j == 0) {
                    System.out.println("...j : " + j + " .... ");
                    cell.setCellValue(cell.getStringCellValue().toUpperCase());
                    cell.setCellStyle(style);
                }
            }
            j++;
        }
    }

}
