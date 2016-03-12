/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumEstado;
import com.bap.erp.modelo.enums.EnumTipoComprobante;
import com.bap.erp.modelo.enums.EnumTipoModulo;
import com.bap.erp.modelo.enums.EnumTipoMoneda;
import com.bap.erp.modelo.enums.EnumTransaccionRealizada;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.bap.erp.ws.pojo.DetalleComprobante;
import com.iknow.utils.ObjectUtils;
import java.io.FileNotFoundException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.UploadedFile;

import java.io.IOException;
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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.log4j.Logger;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "cntCargaComprobantesBacking")
@ViewScoped
public class CntCargaComprobantesBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;
    private List<CntEntidad> listaEntidades;

    private CntComprobante cntComprobante;
    private CntDetalleComprobante cntDetalleComprobante;
    Long posicion;

    public CntCargaComprobantesBacking() {
    }

    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public int lookForRowWithValue(Sheet sheet, String term) {       //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet.iterator();
        boolean found = false;
        Cell cell = null;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().equals(term)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        if (cell != null) {
            return cell.getRowIndex();
        } else {
            return -1;
        }

    }

    public String upload() throws FileNotFoundException {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
//            System.out.println(".....session  :" + getSessionManagedBean().getAdmUsuarios().getLogin());
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
            cntComprobante = new CntComprobante();
            cntDetalleComprobante = new CntDetalleComprobante();

            for (int j = 0; j < cellTempList.size(); j++) {

                HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
                String stringCellValue = hssfCell.toString();

                if (!stringCellValue.isEmpty()) {
                    switch (j) {
                        case 0:
                            System.out.println("...nro cuenta es  " + stringCellValue.trim());
                            pojo.setNroComprobante(new Double((stringCellValue.trim())).longValue());
                            break;
                        case 1:
                            System.out.println("...tipo comprobante : " + stringCellValue.trim());
                            pojo.setTipoComprobante(stringCellValue.trim());
                            break;
                        case 2:
                            System.out.println("...periodo : " + stringCellValue.trim());
                            pojo.setPeriodo(stringCellValue.trim());
                            break;
                        case 3:
                            pojo.setNroCuenta(stringCellValue.trim());
                            break;

                        case 4:
                            System.out.println("...fecha nac : " + stringCellValue.trim());
                            if (!stringCellValue.trim().equals("null")) {
                                Date fechaNac = new Date(stringCellValue.trim());
                                pojo.setFecha(fechaNac);
                            }
                            break;
                        case 5:
                            System.out.println("...tipo de cambio es : " + stringCellValue.trim());
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd1 = new BigDecimal(stringCellValue.trim());
                                pojo.setTpoCambio(bd1);
                            }
                            break;
                        case 6:
                            System.out.println("...debe : " + stringCellValue.trim());
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd2 = new BigDecimal(stringCellValue.trim());
                                pojo.setDebeBs(bd2);
                            }
                            break;

                        case 7:
                            System.out.println("...haber : " + stringCellValue.trim());
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd3 = new BigDecimal(stringCellValue.trim());
                                pojo.setHaberBs(bd3);
                            }
                            break;
                        case 8:
                            System.out.println("...debe es :::: "+stringCellValue.trim());
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd3 = new BigDecimal(stringCellValue.trim());
                                pojo.setDebeSus(bd3);
                            }
                            break;
                        case 9:
                            if (!stringCellValue.equals("null")) {
                                BigDecimal bd4 = new BigDecimal(stringCellValue.trim());
                                pojo.setHaberSus(bd4);
                            }
                            break;
                        case 10:
                            pojo.setGlosaComprobante(stringCellValue.trim());
                            pojo.setGlosaDatalle(stringCellValue.trim());

                            break;
                        case 11:
                            pojo.setTipoRegistro(new Double((stringCellValue.trim())).longValue());
                            break;
                        case 12:
                            pojo.setNroCheque(stringCellValue.trim());
                            break;
                        case 13:
                            pojo.setCtregi(new Double(stringCellValue.trim()).longValue());
                            break;

                    }
                }
            }
            System.out.println("...antes try ---------:   ");
            try {
                System.out.println("....nro cpbte es : " + pojo.getNroComprobante() + "....tipo es : " + pojo.getTipoComprobante());
                cntComprobante = cntComprobantesService.getObtieneCpbtePorNumeroCpbte(pojo.getNroComprobante(), pojo.getTipoComprobante());
                System.out.println("..en try cpbte es antes if ------------- : " + cntComprobante);

                if (cntComprobante == null) {
                    System.out.println("...por null  ...");
                    cntComprobante = generaCntComprobante(pojo, new Date());
                    System.out.println("...cntcomprobante es ...... " + cntComprobante);
                    System.out.println("...cntcomprobante es ...****************************************************... ");
                    ObjectUtils.printObjectState(cntComprobante);
                    cntComprobantesService.persist(cntComprobante);

                    System.out.println("...se registro comprobante nro  : " + cntComprobante.getNumero() + "..tipo c " + cntComprobante.getParTipoComprobante().getCodigo());

                    CntEntidad entidad = getCntEntidadesService().cntEntidadPorMascara(pojo.getNroCuenta());
                    System.out.println("....entidad es  :" + entidad);
                    posicion = 1L;
                    cntDetalleComprobante = generaCntDetalleComprobante(pojo, cntComprobante, posicion, entidad, new Date());
                    System.out.println(".......................................................");
                    ObjectUtils.printObjectState(cntDetalleComprobante);
                    cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);

                } else {
                    if (cntComprobante != null) {

                        System.out.println("...por distinto null.cpbte." + cntComprobante);
                        System.out.println("...por distinto de null...  : " + cntComprobante.getNumero() + "..tipo c " + cntComprobante.getParTipoComprobante().getCodigo());
                        posicion = posicion + 1;
                        System.out.println("..nro cuenta  : " + pojo.getNroCuenta());
                        CntEntidad entidad = getCntEntidadesService().cntEntidadPorMascara(pojo.getNroCuenta());
                        System.out.println("....entidad es  :" + entidad);
                        cntDetalleComprobante = generaCntDetalleComprobante(pojo, cntComprobante, posicion, entidad, new Date());
                        System.out.println(".......................................................");
                        ObjectUtils.printObjectState(cntDetalleComprobante);
                        cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
                    }
                }

                FacesMessage msg2 = new FacesMessage("se registro correctamente..-- ");
                FacesContext.getCurrentInstance().addMessage(null, msg2);
                System.out.println("...registro correcto dtallecomprobantes   ...");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } // cierra segundo for
    }

//***********************************************************************************************************************************
//    public void upload_Excel() {
//        if (!file.getFileName().isEmpty() || !file.getFileName().equals("")) {
//            String extension = file.getFileName().substring(file.getFileName().indexOf("."));
//            try {
//                if (file != null && (extension.equals(".xlsx") || extension.equals(".xls"))) {
//                    List<DetalleComprobante> list = new ArrayList<DetalleComprobante>();
//                    try {
//                        Workbook workbook = WorkbookFactory.create(file.getInputstream());
//                        Sheet sheet = workbook.getSheetAt(0);
//                        int startingRow = 1;
//                        int endingRow = lookForRowWithValue(sheet, "TOTAL VALUE") - 1;
//                        sheet = workbook.getSheetAt(0);
//                        DetalleComprobante detalleComprobante = null;
//                        while (startingRow <= endingRow + 1) {
//                            Row row = sheet.getRow(startingRow);
//                            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
//                            Iterator<Cell> cellIterator = row.cellIterator();
//                            detalleComprobante = new DetalleComprobante();
//                            while (cellIterator.hasNext()) {
//                                Cell cell = cellIterator.next();
//                                switch (cell.getCellType()) {
//                                    case Cell.CELL_TYPE_NUMERIC:
//                                        if (cell.getColumnIndex() == 0) {
//                                            System.out.println(".....nro comprobante ... : " + cell.getNumericCellValue());
//                                            detalleComprobante.setNroComprobante(new Double(cell.getNumericCellValue()).longValue());
//                                        }
//                                        if (cell.getColumnIndex() == 2) {
//                                            detalleComprobante.setPeriodo(String.valueOf(new Double(cell.getNumericCellValue()).longValue()));
//                                        }
//                                        if (cell.getColumnIndex() == 5) {
//                                            detalleComprobante.setTpoCambio(BigDecimal.valueOf(new Double(cell.getNumericCellValue()).floatValue()));
//                                        }
//                                        if (cell.getColumnIndex() == 6) {
//                                            detalleComprobante.setDebeBs(BigDecimal.valueOf(new Double(cell.getNumericCellValue()).floatValue()));
//                                        }
//                                        if (cell.getColumnIndex() == 7) {
//                                            detalleComprobante.setHaberBs(BigDecimal.valueOf(new Double(cell.getNumericCellValue()).floatValue()));
//                                        }
//                                        if (cell.getColumnIndex() == 8) {
//                                            detalleComprobante.setDebeSus(BigDecimal.valueOf(new Double(cell.getNumericCellValue()).floatValue()));
//                                        }
//                                        if (cell.getColumnIndex() == 9) {
//                                            detalleComprobante.setHaberSus(BigDecimal.valueOf(new Double(cell.getNumericCellValue()).floatValue()));
//                                        }
//                                        if (cell.getColumnIndex() == 11) {
//                                            detalleComprobante.setTipoRegistro(new Double(cell.getNumericCellValue()).longValue());
//                                        }
//                                        if (cell.getColumnIndex() == 12) {
//                                            detalleComprobante.setNroCheque(String.valueOf(new Double(cell.getNumericCellValue()).longValue()));
//                                        }
//                                        break;
//                                    case Cell.CELL_TYPE_STRING:
//                                        System.out.println("...string :" + cell.getStringCellValue());
//                                        if (cell.getColumnIndex() == 1) {
//                                            detalleComprobante.setTipoComprobante(cell.getStringCellValue());
//                                        }
//                                        if (cell.getColumnIndex() == 3) {
//                                            detalleComprobante.setNroCuenta(cell.getStringCellValue());
//                                        }
//                                        if (cell.getColumnIndex() == 4) {
//                                            detalleComprobante.setFecha(new Date(cell.getStringCellValue()));
//                                        }
//                                        if (cell.getColumnIndex() == 10) {
//                                            detalleComprobante.setGlosaDatalle(cell.getStringCellValue());
//                                            detalleComprobante.setGlosaComprobante(cell.getStringCellValue());
//                                        }
//                                        break;
//                                }
//                            }
//                            list.add(detalleComprobante);
//                            startingRow++;
//                        }
//                        DetalleComprobante detalleComprobanteAux = new DetalleComprobante();
//                        CntComprobante cntComprobante = new CntComprobante();
//                        CntDetalleComprobante cntDetalleComprobante = new CntDetalleComprobante();
//                        List<CntDetalleComprobante> listDetalleComprobante = new ArrayList<CntDetalleComprobante>();
//                        Long posicion = 1L;
//                        Boolean sw = false;
//                        Boolean swp = false;
//                        Date fechaActual = new Date();
//                        for (DetalleComprobante ce : list) {
//                            if (ce.getNroComprobante().equals(detalleComprobanteAux.getNroComprobante()) && ce.getPeriodo().equals(detalleComprobanteAux.getPeriodo()) && ce.getTipoComprobante().equals(detalleComprobanteAux.getTipoComprobante())) {
//                                posicion++;
//                                sw = false;
//                                System.out.println("....mascara ...:  " + ce.getNroCuenta());
//                                CntEntidad entidad = getCntEntidadesService().cntEntidadPorMascara(ce.getNroCuenta());
//                                System.out.println("....entidad por mascara ...." + entidad);
//                                cntDetalleComprobante = generaCntDetalleComprobante(ce, cntComprobante, posicion, entidad, fechaActual);
//                                listDetalleComprobante.add(cntDetalleComprobante);
//                            } else {
//                                sw = true;
//                                if (sw && swp) {
//                                    getCntDetalleComprobanteService().guardaCntDetalleComprobante(cntComprobante, listDetalleComprobante);
//                                } else {
//                                    swp = true;
//                                }
//                                listDetalleComprobante.clear();
//                                posicion = 1L;
//                                cntComprobante = generaCntComprobante(ce, fechaActual);
//                                CntEntidad entidad = getCntEntidadesService().cntEntidadPorMascara(ce.getNroCuenta());
//                                System.out.println("....entidad es  :" + ce.getNroCuenta());
//                                cntDetalleComprobante = generaCntDetalleComprobante(ce, cntComprobante, posicion, entidad, fechaActual);
//                                listDetalleComprobante.add(cntDetalleComprobante);
//                            }
//                            detalleComprobanteAux = ce;
//                        }
//                        getCntDetalleComprobanteService().guardaCntDetalleComprobante(cntComprobante, listDetalleComprobante);
//                        MessageUtils.addInfoMessage("Se registro todos los registro del Documento Excel exitosamente");
//                    } catch (IOException ioe) {
//                        ioe.printStackTrace();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        MessageUtils.addErrorMessage("Ocurrio un error al registrar el Documento Excel.");
//                    }
//                } else {
//                    MessageUtils.addErrorMessage("Compruebe que el archivo es un documento de Excel o Descargue el formato de Archivo Excel de esta pagina");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            MessageUtils.addErrorMessage("No selecciono ningun archivo excel.");
//        }
//    }
    public CntComprobante generaCntComprobante(DetalleComprobante ce, Date fechaActual) {
        System.out.println("...en genera es  " + ce);
        CntComprobante cntComprobante = new CntComprobante();
        cntComprobante.setIdAntecesor(null);
        cntComprobante.setGlosaComprobante(ce.getGlosaComprobante());
        cntComprobante.setTipoCambio(ce.getTpoCambio());
        cntComprobante.setTipoMoneda(EnumTipoMoneda.AMBOS.getCodigo());
        cntComprobante.setNumero(ce.getNroComprobante());
        cntComprobante.setPeriodo(ce.getPeriodo());
        if ("E".equals(ce.getTipoComprobante())) {
            ParTipoComprobante tipo = (ParTipoComprobante) getParParametricasService().find(ParTipoComprobante.class, EnumTipoComprobante.EGRESO.getCodigo());
            cntComprobante.setParTipoComprobante(tipo);
        }
        if ("I".equals(ce.getTipoComprobante())) {
            ParTipoComprobante tipo = (ParTipoComprobante) getParParametricasService().find(ParTipoComprobante.class, EnumTipoComprobante.INGRESO.getCodigo());
            cntComprobante.setParTipoComprobante(tipo);
        }
        if ("T".equals(ce.getTipoComprobante())) {
            ParTipoComprobante tipo = (ParTipoComprobante) getParParametricasService().find(ParTipoComprobante.class, EnumTipoComprobante.TRASPASO.getCodigo());
            cntComprobante.setParTipoComprobante(tipo);
        }
        cntComprobante.setDescripcion(ce.getGlosaComprobante());
        cntComprobante.setFecha(ce.getFecha());
        cntComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
        cntComprobante.setModulo(EnumTipoModulo.CONTABILIDAD.getCodigo());
        cntComprobante.setMotivoAnulacion(null);
        cntComprobante.setFechaAlta(fechaActual);
        cntComprobante.setLoginUsuario(getLoginSession());
        cntComprobante.setUsuarioAlta(getLoginSession());
        System.out.println("....COMP:....... " + cntComprobante.getNumero());

        return cntComprobante;
    }

    public CntDetalleComprobante generaCntDetalleComprobante(DetalleComprobante ce, CntComprobante cntComprobante, Long posicion, CntEntidad cntEntidad, Date fechaActual) {
        CntDetalleComprobante cntDetalleComprobante = new CntDetalleComprobante();
        cntDetalleComprobante.setCntComprobante(cntComprobante);
        cntDetalleComprobante.setCntEntidad(cntEntidad);
        if (ce.getTipoRegistro() == 7) {
            cntDetalleComprobante.setIdPadre(ce.getTipoRegistro());
        } else {
            cntDetalleComprobante.setIdPadre(null);
        }
        cntDetalleComprobante.setIdAntecesor(null);
        cntDetalleComprobante.setIdAuxiliar(null);
        cntDetalleComprobante.setIdProyecto(null);
        cntDetalleComprobante.setPosicion(posicion);
        cntDetalleComprobante.setPosicionAnterior(null);
        cntDetalleComprobante.setDebe(ce.getDebeBs());
        cntDetalleComprobante.setHaber(ce.getHaberBs());
        cntDetalleComprobante.setDebeDolar(ce.getDebeSus());
        cntDetalleComprobante.setHaberDolar(ce.getHaberSus());
        cntDetalleComprobante.setGlosa(ce.getGlosaComprobante());
        cntDetalleComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
        if (ce.getTipoRegistro() == 0) {
            cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.NINGUNO.getCodigo());
        }
        if (ce.getTipoRegistro() == 1) {
            cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo());
        }
        if (ce.getTipoRegistro() == 2) {
            cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo());
        }
        if (ce.getTipoRegistro() == 21) {
            cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.CREDITO_FISCAL_TRANSITORIO.getCodigo());
        }
        if (ce.getTipoRegistro() == 22) {
            cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.REGULARIZADO.getCodigo());
        }
        if (ce.getTipoRegistro() == 23) {
            cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.PROCESO.getCodigo());
        }
        cntDetalleComprobante.setNumeroCheque(ce.getNroCheque());
        cntDetalleComprobante.setFechaAlta(fechaActual);
        cntDetalleComprobante.setLoginUsuario(getLoginSession());
        cntDetalleComprobante.setUsuarioAlta(getLoginSession());
//        System.out.println("DETALLECOMP: " + cntDetalleComprobante.getCntComprobante().getNumero());
        System.out.println("....detalle comprobante generado ...: " + cntDetalleComprobante);
        return cntDetalleComprobante;
    }

    public String saveCombrobantes(CntComprobante cntComprobante, List<CntDetalleComprobante> listCntDetalleComprobante) {
        try {
            getCntDetalleComprobanteService().guardaCntDetalleComprobante(cntComprobante, listCntDetalleComprobante);
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtils.addErrorMessage("Ocurrio un error al registrar el Documento Excel.");
        }
        return null;
    }

    /**
     * @return the cntEntidadesService
     */
    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    /**
     * @param cntEntidadesService the cntEntidadesService to set
     */
    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    /**
     * @return the parParametricasService
     */
    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    /**
     * @param parParametricasService the parParametricasService to set
     */
    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    /**
     * @return the cntDetalleComprobanteService
     */
    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    /**
     * @param cntDetalleComprobanteService the cntDetalleComprobanteService to
     * set
     */
    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    /**
     * @return the cntComprobantesService
     */
    public CntComprobantesService getCntComprobantesService() {
        return cntComprobantesService;
    }

    /**
     * @param cntComprobantesService the cntComprobantesService to set
     */
    public void setCntComprobantesService(CntComprobantesService cntComprobantesService) {
        this.cntComprobantesService = cntComprobantesService;
    }

    public void upload_Excel2() {
        System.out.println(".UPLOAD 2..archivo es : " + file.getFileName());
        if (!file.getFileName().isEmpty() || !file.getFileName().equals("")) {
            String extension = file.getFileName().substring(file.getFileName().indexOf("."));
            try {
                if (file != null && (extension.equals(".xlsx") || extension.equals(".xls"))) {
                    List<DetalleComprobante> list = new ArrayList<DetalleComprobante>();
                    try {
                        Workbook workbook = WorkbookFactory.create(file.getInputstream());
                        Sheet sheet = workbook.getSheetAt(0);
                        int startingRow = 1;
                        int endingRow = lookForRowWithValue(sheet, "TOTAL VALUE") - 1;
                        sheet = workbook.getSheetAt(0);
                        DetalleComprobante detalleComprobante = null;
                        while (startingRow <= endingRow + 1) {
                            Row row = sheet.getRow(startingRow);
                            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                            Iterator<Cell> cellIterator = row.cellIterator();
                            detalleComprobante = new DetalleComprobante();
                            while (cellIterator.hasNext()) {
                                Cell cell = cellIterator.next();
                                switch (cell.getCellType()) {
                                    case Cell.CELL_TYPE_STRING:
                                        if (cell.getColumnIndex() == 3) {
                                            detalleComprobante.setNroCuenta(cell.getStringCellValue());
                                        }

                                        break;
                                }
                            }
                            list.add(detalleComprobante);
                            startingRow++;
                        }
                        DetalleComprobante detalleComprobanteAux = new DetalleComprobante();
                        CntDetalleComprobante cntDetalleComprobante = new CntDetalleComprobante();
                        List<DetalleComprobante> listEntidad = new ArrayList<DetalleComprobante>();
                        Long posicion = 1L;
                        Boolean sw = false;
                        Boolean swp = false;
                        Date fechaActual = new Date();
                        for (DetalleComprobante ce : list) {

                            System.out.println("....mascara ...:  " + ce.getNroCuenta());
                            CntEntidad entidad = getCntEntidadesService().cntEntidadPorMascara(ce.getNroCuenta());
                            System.out.println("....entidad por mascara ...." + entidad);
                            if (entidad == null) {
                                detalleComprobanteAux = new DetalleComprobante();
                                System.out.println("...entidad es null " + entidad);
                                detalleComprobanteAux.setNroCuenta(ce.getNroCuenta());
                            }
                            listEntidad.add(detalleComprobanteAux);

                        }
                        System.out.println("..cuentas que faltna  : " + listEntidad.size());
                        for (DetalleComprobante lf : listEntidad) {
                            System.out.println("...cuenta falta : " + lf.getNroCuenta());
                        }

                        MessageUtils.addInfoMessage("Estos son las cantidad de cuentas que faltna  : " + listEntidad.size());
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                        MessageUtils.addErrorMessage("Ocurrio un error al registrar el Documento Excel.");
                    }
                } else {
                    MessageUtils.addErrorMessage("Compruebe que el archivo es un documento de Excel o Descargue el formato de Archivo Excel de esta pagina");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MessageUtils.addErrorMessage("No selecciono ningun archivo excel.");
        }
    }

    public List<CntEntidad> getListaEntidades() {
        return listaEntidades;
    }

    public void setListaEntidades(List<CntEntidad> listaEntidades) {
        this.listaEntidades = listaEntidades;
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

}
