/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntFacturacionService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.ws.pojo.DetalleComprobante;
import com.iknow.utils.ObjectUtils;
import java.io.FileNotFoundException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.UploadedFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.glassfish.jersey.media.multipart.FormDataParam;

//import org.apache.log4j.Logger;
/**
 *
 * @author Javi
 */
@ManagedBean(name = "cntCargaFacturasComprasVentasBacking")
@ViewScoped
public class CntCargaFacturasComprasVentasBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;

    @ManagedProperty(value = "#{cntFacturacionService}")
    private CntFacturacionService cntFacturacionService;

    private CntComprobante cntComprobante;
    private CntDetalleComprobante cntDetalleComprobante;
    private CntFacturacion cntFacturacion;

    public CntCargaFacturasComprasVentasBacking() {
    }

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

//<editor-fold defaultstate="collapsed" desc="migrar facturas compras">
    public String upload() throws FileNotFoundException {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            readExcelFile();
        } else {
            FacesMessage message = new FacesMessage(" Archivo es ", file + " debe cargar un archivo a importar...!!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        return null;
    }

    private void readExcelFile() throws FileNotFoundException {
        System.out.println(".....en readExcel File      ");
        List cellDataList = new ArrayList(); // CREA UNA NUEVA INSTANCIA PARA cellDataList
        try {
            POIFSFileSystem fsFileSystem = new POIFSFileSystem(file.getInputstream()); // crea una nueva instancia para POIFSFileSystem class
            HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem); //* Create a new instance for HSSFWorkBook Class
            HSSFSheet hssfSheet = workBook.getSheetAt(0);
            /**
             * Iterate the rows and cells of the spreadsheet to get all the
             * datas. //Iterar las filas y las celdas de la hoja de cálculo para
             * obtener todos los datos
             */
            Iterator rowIterator = hssfSheet.rowIterator();
            while (rowIterator.hasNext()) {
                HSSFRow hssfRow = (HSSFRow) rowIterator.next();
                Iterator iterator = hssfRow.cellIterator();
                List cellTempList = new ArrayList();
                while (iterator.hasNext()) {
                    HSSFCell hssfCell = (HSSFCell) iterator.next();
                    cellTempList.add(hssfCell);
                }
                cellDataList.add(cellTempList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * Call the printToConsole method to print the cell data in the console.
         * //Llamar al método printToConsole para imprimir los datos de la celda
         * en la consola.
         */
        printToConsole(cellDataList);
    }

    private void printToConsole(List cellDataList) {
        System.out.println("...cell data lista esn printoconsole es   -----  : " + cellDataList.size());
        List<DetalleComprobante> listaPojo = new ArrayList<DetalleComprobante>();
        DetalleComprobante pojo;

        for (int i = 0; i < cellDataList.size(); i++) {
            pojo = new DetalleComprobante();
            List cellTempList = (List) cellDataList.get(i);
            cntFacturacion = new CntFacturacion();

            cntComprobante = new CntComprobante();
            cntDetalleComprobante = new CntDetalleComprobante();

            for (int j = 0; j < cellTempList.size(); j++) {

                HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
                String stringCellValue = hssfCell.toString();

                if (!stringCellValue.isEmpty()) {
                    switch (j) {
                        case 0:
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd1 = new BigDecimal(stringCellValue.trim());
                                pojo.setTipo(bd1);
                            }
                            break;
                        case 1:
                            if (!stringCellValue.trim().equals("null")) {
                                Date fechafac = new Date(stringCellValue.trim());
                                pojo.setFechafactura(fechafac);
                            }
                            break;
                        case 2:
                            pojo.setNrofactura(new Double((stringCellValue.trim())).longValue());
                            break;
                        case 3:
                            pojo.setNit(new Double((stringCellValue.trim())).longValue());
                            break;

                        case 4:
                            pojo.setRazon(stringCellValue.trim());
                            break;
                        case 5:
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd4 = new BigDecimal(stringCellValue.trim());
                                pojo.setMonto(bd4);
                            }
                            break;
                        case 6:
                            System.out.println("...nrocpbtes es:::::: " + stringCellValue.trim());
//                            pojo.setNroCpbte(stringCellValue.trim());
                            pojo.setNroComprobante(new Double((stringCellValue.trim())).longValue());
                            break;

                        case 7:
                            pojo.setTipoComprobante(stringCellValue.trim());
                            break;
                        case 8:
                            pojo.setNorden(stringCellValue.trim());

                            break;
                        case 9:
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd6 = new BigDecimal(stringCellValue.trim());
                                pojo.setIce(bd6);
                            }
                            break;
                        case 10:
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd7 = new BigDecimal(stringCellValue.trim());
                                pojo.setExento(bd7);
                            }

                            break;
                        case 11:
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd8 = new BigDecimal(stringCellValue.trim());
                                pojo.setIva(bd8);
                            }
                            break;

                        case 12:
                            pojo.setCodSubcuenta(stringCellValue.trim());
                            break;
                        case 13:
                            pojo.setNopoliza(stringCellValue.trim());
                            break;
                        case 14:
                            pojo.setCodcontrol(stringCellValue.trim());
                            break;
                        case 15:
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd9 = new BigDecimal(stringCellValue.trim());
                                pojo.setNeto(bd9);
                            }
                            break;
                        case 16:
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd10 = new BigDecimal(stringCellValue.trim());
                                pojo.setGasto(bd10);
                            }
                            break;
                    }
                }
            }
            System.out.println("...antes try ---------:   ");
            try {
                System.out.println("...nro cpbte ===> " + pojo.getNroComprobante());
                cntComprobante = cntComprobantesService.getObtieneCpbtePorNumeroCpbte(pojo.getNroComprobante(), pojo.getTipoComprobante());
                System.out.println("..en try cpbte es antes if ------------- : " + cntComprobante);

                if (cntComprobante != null) {
                    System.out.println("...por distinto de null...  : " + cntComprobante.getNumero() + "..tipo c " + cntComprobante.getParTipoComprobante().getCodigo());
                    cntDetalleComprobante = cntDetalleComprobanteService.getObtieneDetalleConFacturaNullXCpbte(cntComprobante);

                    System.out.println("..detalle es :::  : " + cntDetalleComprobante);
                    cntFacturacion.setMonto(pojo.getMonto());
                    cntFacturacion.setExcento(pojo.getExento());
                    cntFacturacion.setIva(pojo.getIva());
                    cntFacturacion.setCreditoFiscalTransitorio("N");
                    cntFacturacion.setFechaFactura(pojo.getFechafactura());
                    cntFacturacion.setNroInicial(pojo.getNrofactura());
                    cntFacturacion.setNroFinal(pojo.getNrofactura());
                    cntFacturacion.setFechaAlta(new Date());
                    cntFacturacion.setUsuarioAlta("SYS");
                    cntFacturacion.setLoginUsuario("SYS");
                    cntFacturacion.setNit(pojo.getNit());
                    cntFacturacion.setRazonSocial(pojo.getRazon());
                    cntFacturacion.setNroAutorizacion(pojo.getNorden());
                    cntFacturacion.setSucursal("SUCURSAL PRINCIPAL");
                    cntFacturacion.setMovimiento("COMP");
                    cntFacturacion.setMontoSegMoneda(BigDecimal.ZERO);
                    cntFacturacion.setExcentoSegMoneda(BigDecimal.ZERO);
                    cntFacturacion.setIvaSegMoneda(BigDecimal.ZERO);
                    cntFacturacion.setCodigoControl(pojo.getCodcontrol());
                    cntFacturacion.setEstado("CONF");
                    cntFacturacion.setParParametrosAutorizacion(null);
                    cntFacturacion.setParTipoFacturacion(null);

                    ObjectUtils.printObjectState(cntFacturacion);

                    cntFacturacionService.persistCntFacturacion(cntFacturacion);
                    System.out.println("...se registo factura con codigo  ::: " + cntFacturacion);

                    cntDetalleComprobante.setCntFacturacion(cntFacturacion);
                    ObjectUtils.printObjectState(cntDetalleComprobante);
                    cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
                }

                FacesMessage msg2 = new FacesMessage("se registro correctamente..-- ");
                FacesContext.getCurrentInstance().addMessage(null, msg2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } // cierra segundo for
    }

//</editor-fold>
    public String upload2() throws FileNotFoundException {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            readExcelFile2();
        } else {
            FacesMessage message = new FacesMessage(" Archivo es ", file + " debe cargar un archivo a importar...!!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        return null;
    }

    private void readExcelFile2() throws FileNotFoundException {
        System.out.println(".....en readExcel File      ");
        List cellDataList = new ArrayList(); // CREA UNA NUEVA INSTANCIA PARA cellDataList
        try {
            POIFSFileSystem fsFileSystem = new POIFSFileSystem(file.getInputstream()); // crea una nueva instancia para POIFSFileSystem class
            HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem); //* Create a new instance for HSSFWorkBook Class
            HSSFSheet hssfSheet = workBook.getSheetAt(0);
            /**
             * Iterate the rows and cells of the spreadsheet to get all the
             * datas. //Iterar las filas y las celdas de la hoja de cálculo para
             * obtener todos los datos
             */
            Iterator rowIterator = hssfSheet.rowIterator();
            while (rowIterator.hasNext()) {
                HSSFRow hssfRow = (HSSFRow) rowIterator.next();
                Iterator iterator = hssfRow.cellIterator();
                List cellTempList = new ArrayList();
                while (iterator.hasNext()) {
                    HSSFCell hssfCell = (HSSFCell) iterator.next();
                    cellTempList.add(hssfCell);
                }
                cellDataList.add(cellTempList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * Call the printToConsole method to print the cell data in the console.
         * //Llamar al método printToConsole para imprimir los datos de la celda
         * en la consola.
         */
        printToConsole2(cellDataList);
    }

    private void printToConsole2(List cellDataList) {
        System.out.println("...cell data lista esn printoconsole es   -ventas----  : " + cellDataList.size());
        List<DetalleComprobante> listaPojo = new ArrayList<DetalleComprobante>();
        DetalleComprobante pojo;

        for (int i = 0; i < cellDataList.size(); i++) {
            pojo = new DetalleComprobante();
            List cellTempList = (List) cellDataList.get(i);
            cntFacturacion = new CntFacturacion();

            cntComprobante = new CntComprobante();
            cntDetalleComprobante = new CntDetalleComprobante();

            for (int j = 0; j < cellTempList.size(); j++) {

                HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
                String stringCellValue = hssfCell.toString();

                if (!stringCellValue.isEmpty()) {
                    switch (j) {

                        case 0:
                            System.out.println(".....fecha  "+stringCellValue.trim());
                            if (!stringCellValue.trim().equals("null")) {
                                Date fechafac = new Date(stringCellValue.trim());
                                pojo.setFechafactura(fechafac);
                            }
                            break;
                        case 1:
                            System.out.println("....nro fac  "+stringCellValue.trim());
                            pojo.setNrofactura(new Double((stringCellValue.trim())).longValue());
                            break;
                        case 2:
                            System.out.println("...nit ::  "+stringCellValue.trim());
                            pojo.setNit(new Double((stringCellValue.trim())).longValue());
                            break;

                        case 3:
                            System.out.println("....razon:::  "+stringCellValue.trim());
                            pojo.setRazon(stringCellValue.trim());
                            break;
                        case 4:
                            System.out.println(".....monto  "+stringCellValue.trim());
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd4 = new BigDecimal(stringCellValue.trim());
                                pojo.setMonto(bd4);
                            }
                            break;

                        case 5:
                            System.out.println(".....iva  "+stringCellValue.trim());
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd8 = new BigDecimal(stringCellValue.trim());
                                pojo.setIva(bd8);
                            }
//                           
                            break;
                        case 6:
                            System.out.println("...valida :::  "+stringCellValue.trim());
                            pojo.setValida(stringCellValue.trim());
                            break;
                        case 7:
                            System.out.println(".nro orden  "+stringCellValue.trim());
                            pojo.setNorden(stringCellValue.trim());
                            break;
                        case 8:
                            System.out.println(". cod cotrol  "+stringCellValue.trim());
                            pojo.setCodcontrol(stringCellValue.trim());
                            break;
                        case 9:
                            System.out.println(".ice ::::"+stringCellValue.trim());
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd6 = new BigDecimal(stringCellValue.trim());
                                pojo.setIce(bd6);
                            }
                            break;
                        case 10:
                            System.out.println("..excento :::  "+stringCellValue.trim());
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd7 = new BigDecimal(stringCellValue.trim());
                                pojo.setExento(bd7);
                            }

                            break;
                        case 11:
                            System.out.println("...neto   "+stringCellValue.trim());
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd7 = new BigDecimal(stringCellValue.trim());
                                pojo.setNeto(bd7);
                            }
                            break;
                    }
                }
            }

            System.out.println("...antes try ---------:   ");
            try {

                System.out.println(".registrando     .....detalle es :::  : ");

                cntFacturacion.setFechaFactura(pojo.getFechafactura());
                cntFacturacion.setNroInicial(pojo.getNrofactura());
                cntFacturacion.setNroFinal(pojo.getNrofactura());
                cntFacturacion.setNit(pojo.getNit());
                cntFacturacion.setRazonSocial(pojo.getRazon());
                cntFacturacion.setMonto(pojo.getMonto());
                cntFacturacion.setIva(pojo.getIva());

                if (pojo.getValida().equals("V")) {
                    cntFacturacion.setEstado("CONF");
                } else {
                    if (pojo.getValida().equals("A")) {
                    cntFacturacion.setEstado("ANUL");
                    }
                }
                cntFacturacion.setNroAutorizacion(pojo.getNorden());
                cntFacturacion.setCodigoControl(pojo.getCodcontrol());
                cntFacturacion.setIce(pojo.getIce());
                cntFacturacion.setExcento(pojo.getExento());

                cntFacturacion.setCreditoFiscalTransitorio("N");
                cntFacturacion.setFechaAlta(new Date());
                cntFacturacion.setUsuarioAlta("SYS");
                cntFacturacion.setLoginUsuario("SYS");
                cntFacturacion.setSucursal("SUCURSAL PRINCIPAL");
                cntFacturacion.setMovimiento("VENT");
                cntFacturacion.setMontoSegMoneda(BigDecimal.ZERO);
                cntFacturacion.setExcentoSegMoneda(BigDecimal.ZERO);
                cntFacturacion.setIvaSegMoneda(BigDecimal.ZERO);
                cntFacturacion.setParParametrosAutorizacion(null);
                cntFacturacion.setParTipoFacturacion(null);

                ObjectUtils.printObjectState(cntFacturacion);

                cntFacturacionService.persistCntFacturacion(cntFacturacion);
                System.out.println("...se registo factura con codigo  ::: " + cntFacturacion);

                FacesMessage msg2 = new FacesMessage("se registro correctamente..-factura venta- ");
                FacesContext.getCurrentInstance().addMessage(null, msg2);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } // cierra segundo for
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public CntComprobantesService getCntComprobantesService() {
        return cntComprobantesService;
    }

    public void setCntComprobantesService(CntComprobantesService cntComprobantesService) {
        this.cntComprobantesService = cntComprobantesService;
    }

    public CntFacturacionService getCntFacturacionService() {
        return cntFacturacionService;
    }

    public void setCntFacturacionService(CntFacturacionService cntFacturacionService) {
        this.cntFacturacionService = cntFacturacionService;
    }

    public CntComprobante getCntComprobante() {
        return cntComprobante;
    }

    public void setCntComprobante(CntComprobante cntComprobante) {
        this.cntComprobante = cntComprobante;
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

}
