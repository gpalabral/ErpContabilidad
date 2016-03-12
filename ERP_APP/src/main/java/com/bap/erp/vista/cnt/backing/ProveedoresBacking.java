/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.cnt.backing;

import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;


import com.bap.erp.ws.pojo.Proveedores;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
@ManagedBean(name = "proveedoresBacking")
@ViewScoped
public class ProveedoresBacking extends AbstractManagedBean implements Serializable {

    public ProveedoresBacking() {
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

    public void upload_Excel() {
        if (!file.getFileName().isEmpty() || !file.getFileName().equals("")) {
            String extension = file.getFileName().substring(file.getFileName().indexOf("."));
            try {
                if (file != null && (extension.equals(".xlsx") || extension.equals(".xls"))) {
                    List<Proveedores> list = new ArrayList<Proveedores>();
                    try {
                        Workbook workbook = WorkbookFactory.create(file.getInputstream());
                        Sheet sheet = workbook.getSheetAt(0);
                        int startingRow = 14;
                        int endingRow = lookForRowWithValue(sheet, "TOTAL VALUE") - 1;

                        sheet = workbook.getSheetAt(0);
                        Proveedores proveedores = null;
                        while (startingRow <= endingRow + 1) {
                            Row row = sheet.getRow(startingRow);
                            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                            Iterator<Cell> cellIterator = row.cellIterator();
                            proveedores = new Proveedores();
                            while (cellIterator.hasNext()) {
                                Cell cell = cellIterator.next();
                                switch (cell.getCellType()) {
                                    case Cell.CELL_TYPE_NUMERIC:
                                        if (cell.getColumnIndex() == 1) {
                                            proveedores.setNit(new Double(cell.getNumericCellValue()).longValue());
                                            System.out.println("Mensaje 1.- " + new Double(cell.getNumericCellValue()).longValue() + "\t");
                                        }
//                            if (cell.getColumnIndex() == 3) {
//                                lgnNFac = Long.parseLong(String.valueOf(new Double(cell.getNumericCellValue()).longValue()));
//                                srtNFac = String.valueOf(new Double(cell.getNumericCellValue()).longValue());
//                                proveedores.setNroFactura(lgnNFac);
//                            }
//                            if (cell.getColumnIndex() == 4) {
//                                lgnNIT = Long.parseLong(String.valueOf(new Double(cell.getNumericCellValue()).longValue()));
//                                proveedores.setNit(lgnNIT);
//                            }
//                            if (cell.getColumnIndex() == 6) {
//                                fltMonto = new Double(cell.getNumericCellValue()).floatValue();
//                                Proveedores.setMonto(fltMonto);
//                            }
                                        break;
                        case Cell.CELL_TYPE_STRING:
//                            if (cell.getColumnIndex() == 0) {
//                                dteFecha = new Date(cell.getStringCellValue());
//                                proveedores.setFechaFactura(dteFecha);
//                            }
                            if (cell.getColumnIndex() == 0) {
                                proveedores.setTipoTransaccion(cell.getStringCellValue());
                                System.out.println("Mensaje 2.- " + cell.getStringCellValue()+ "\t");
                            }
                            if (cell.getColumnIndex() == 3) {
                                proveedores.setTipoTransaccion(cell.getStringCellValue());
                                System.out.println("Mensaje 3.- " + cell.getStringCellValue()+ "\t");
                            }
                            break;
                                }
                            }

//                System.out.println("CODIGO CONTROL.- " + codCont + "\t");
//                System.out.println("\t");
//                            System.out.println("Mensaje.- " + "\t");

                            list.add(proveedores);
                            startingRow++;
                        }
//            System.out.println("LIST::: " + list);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
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
}
