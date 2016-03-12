/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnf.ParCuentasGenerales;
import com.bap.erp.modelo.entidades.cnf.ParGruposNivel;
import com.bap.erp.modelo.entidades.cnf.ParTiposDatoNivel;
import com.bap.erp.modelo.entidades.cnt.CntDefinicionCuentasMigracion;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumHabilitaCentroCosto;
import com.bap.erp.modelo.enums.EnumTieneAuxiliar;
import com.bap.erp.modelo.enums.EnumTieneCentroDeCosto;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import com.bap.erp.modelo.servicios.cnt.CntMigracionExcelService;
import com.bap.erp.modelo.servicios.cnt.CntNivelesService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
//import org.apache.log4j.Logger;

/**
 *
 * @author Jonas
 */
@ManagedBean(name = "cntCargaPlanDeCuentasBacking")
@ViewScoped
public class CntCargaPlanDeCuentasBacking extends AbstractManagedBean implements Serializable {

    private CntEntidad excelRowInicioCntEntidad;
    private CntEntidad excelRowCntEntidad;
    private CntNivel excelRowCntNivel;
    private List<CntEntidad> excelRowCntEntidadList = new ArrayList<CntEntidad>();
    private List<CntNivel> excelRowCntNivelList = new ArrayList<CntNivel>();
    /*Inicio Codigo Jonathan para Definicion de Cuentas*/
    private List<CntEntidad> listaSuperiores = new ArrayList<CntEntidad>();
    private List<CntDefinicionCuentasMigracion> listaSuperioresPadres = new ArrayList<CntDefinicionCuentasMigracion>();
    private List<CntDefinicionCuentasMigracion> listaDefinicionCuentasMigracion = new ArrayList<CntDefinicionCuentasMigracion>();
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntNivelesService}")
    private CntNivelesService cntNivelesService;
    @ManagedProperty(value = "#{cntMascarasService}")
    private CntMascarasService cntMascarasService;
    @ManagedProperty(value = "#{cntMigracionExcelService}")
    private CntMigracionExcelService cntMigracionExcelService;
    private List<ParCuentasGenerales> parCuentasGeneralesList;
    private List<ParCuentasGenerales> listaCuentasGenerales = new ArrayList<ParCuentasGenerales>();
    /*Inicio Codigo Jonathan para Definicion de Cuentas*/
    CntMascara cntMascaras;
    int tamanioNivel = 0;
    String mascaraPrimeraColumna = "";
    private List<CntNivel> listaCntNivelesMigracion = new ArrayList<CntNivel>();
    private Boolean activaBotonContinuarNiveles = false;
    private Boolean activaBotonContinuarCuentas = false;

    public CntCargaPlanDeCuentasBacking() {
    }

    @PostConstruct
    public void initCntCargaPlanDeCuentasBacking() {
        listaCuentasGenerales = parParametricasService.listaCuentasGeneralesParametricas();
//        listaDefinicionCuentasMigracion = parParametricasService.listaDefinicionCuentas(listaSuperiores);

        //Este codigo solo sirve de prueba hasta que se logre unir el codigo que carga el excel
        //for (int i = 0; i < 5; i++) {
        // CntEntidad entidadSuperior = new CntEntidad();
        //entidadSuperior.setMascaraGenerada(i + "-0-00-000");
        //entidadSuperior.setDescripcion(i == 1 ? "ACTIVO" : i == 2 ? "PASIVO BAP" : i == 3 ? "PATRIMONIO BAP" : i == 4 ? "EGRESOS" : "INGRESOS");
        // listaSuperiores.add(entidadSuperior);
        //}
//        for (int i = 5; i >= 1; i--) {
//            CntNivel nivel = new CntNivel();
//            nivel.setNivel(i);
//            nivel.setTamanio(i == 1 ? 1 : i == 2 ? 1 : i == 3 ? 2 : 3);
//           listaCntNivelesMigracion.add(nivel);
//        }
        //FIN del codigo
        activaBotonContinuarNiveles = verificaDescripcionListaNivelesMigracion();
    }

    public Boolean verificaDescripcionListaNivelesMigracion() {
        Boolean sw = true;
        for (CntNivel cntNivel : listaCntNivelesMigracion) {
            if (cntNivel.getDescripcion() != null) {
                if (cntNivel.getDescripcion().isEmpty()) {
                    sw = false;
                }
            } else {
                sw = false;
            }

        }
        return sw;
    }

    public Boolean verificaTipoCuentaMigracion() {
        Boolean sw = true;
        for (CntEntidad cntEntidad : listaSuperiores) {
            if (cntEntidad.getDescripcion() == null) {
                sw = false;
            }

        }
        return sw;
    }

    public void downloadExcelFile_action() {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = (String) servletContext.getRealPath("/WEB-INF/files/CNT_ENTIDAD.xlsx");
            File ficheroXLS = new File(realPath);
            FacesContext ctx = FacesContext.getCurrentInstance();
            FileInputStream fis = new FileInputStream(ficheroXLS);
            byte[] bytes = new byte[1000];
            int read = 0;
            if (!ctx.getResponseComplete()) {
                String fileName = ficheroXLS.getName();
                String contentType = "application/vnd.ms-excel";
                HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                ServletOutputStream out = response.getOutputStream();
                while ((read = fis.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                ctx.responseComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload_action() {
        Row row, rowInicio, rowLimite;
        int contador = 4;
        int limiteRecorridoCntEntidad = 0;
        int limiteRecorridoCntNiveles = 0;
        boolean entra = true;
        String mascaraGeneradaAux = "";
        String descripcionAux = "";
        Date fechaActual = new Date();
        excelRowCntEntidadList.clear();
        excelRowCntNivelList.clear();
        listaSuperiores.clear();
        listaSuperioresPadres.clear();
        System.out.println("...file es -- :" + file.getFileName());
        if (!file.getFileName().isEmpty() || !file.getFileName().equals("")) {
            String extension = file.getFileName().substring(file.getFileName().indexOf("."));
            try {
                if (file != null && (extension.equals(".xlsx") || extension.equals(".xls"))) {
                    Workbook wb = new XSSFWorkbook(file.getInputstream());
                    Sheet sheet = wb.getSheetAt(0);
                    rowLimite = sheet.getRow(1);
                    limiteRecorridoCntEntidad = (int) rowLimite.getCell(1).getNumericCellValue();
                    rowInicio = sheet.getRow(contador);
                    excelRowInicioCntEntidad = new CntEntidad();
                    excelRowInicioCntEntidad.setIdEntidadPadre(new Long(0));
                    excelRowInicioCntEntidad.setMascaraGenerada(rowInicio.getCell(0).getStringCellValue());
                    excelRowInicioCntEntidad.setDescripcion(rowInicio.getCell(1).getStringCellValue());
                    excelRowInicioCntEntidad.setNivel(cntEntidadesService.generaNivelPorMascara(excelRowInicioCntEntidad.getMascaraGenerada()));
                    excelRowInicioCntEntidad.setTipo(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
                    excelRowInicioCntEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
                    excelRowInicioCntEntidad.setTieneAuxiliar(EnumTieneAuxiliar.NO.getCodigo());
                    excelRowInicioCntEntidad.setHabilitaCentroCosto(EnumHabilitaCentroCosto.NO.getCodigo());
                    excelRowInicioCntEntidad.setFechaAlta(fechaActual);
                    excelRowInicioCntEntidad.setUsuarioAlta(getLoginSession());
                    tamanioNivel = excelRowInicioCntEntidad.getMascaraGenerada().split("-").length;
                    mascaraPrimeraColumna = excelRowInicioCntEntidad.getMascaraGenerada();
                    mascaraGeneradaAux = excelRowInicioCntEntidad.getMascaraGenerada();
                    descripcionAux = excelRowInicioCntEntidad.getDescripcion();
                    excelRowCntEntidadList.add(excelRowInicioCntEntidad);
                    if (cntEntidadesService.generaNivelPorMascara(excelRowInicioCntEntidad.getMascaraGenerada()) == tamanioNivel) {
                        listaSuperiores.add(excelRowInicioCntEntidad);
                        CntDefinicionCuentasMigracion cntDefinicionCuentasMigracion = new CntDefinicionCuentasMigracion();
                        cntDefinicionCuentasMigracion.setCntEntidad(excelRowInicioCntEntidad);
                        listaSuperioresPadres.add(cntDefinicionCuentasMigracion);
                    }
                    contador++;
                    while (entra) {
                        try {
                            row = sheet.getRow(contador);
                            System.out.println("...row es : " + row);
                            excelRowCntEntidad = new CntEntidad();
                            excelRowCntEntidad.setIdEntidadPadre(new Long(0));
                            excelRowCntEntidad.setMascaraGenerada(row.getCell(0).getStringCellValue());
                            excelRowCntEntidad.setDescripcion(row.getCell(1).getStringCellValue());
                            excelRowCntEntidad.setNivel(cntEntidadesService.generaNivelPorMascara(excelRowCntEntidad.getMascaraGenerada()));
                            excelRowCntEntidad.setTipo(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
                            excelRowCntEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
                            excelRowCntEntidad.setTieneAuxiliar(EnumTieneAuxiliar.NO.getCodigo());
                            excelRowCntEntidad.setHabilitaCentroCosto(EnumHabilitaCentroCosto.NO.getCodigo());
                            excelRowCntEntidad.setFechaAlta(fechaActual);
                            excelRowCntEntidad.setUsuarioAlta(getLoginSession());

                            if (cntEntidadesService.generaNivelPorMascara(excelRowCntEntidad.getMascaraGenerada()) == tamanioNivel) {
                                listaSuperiores.add(excelRowCntEntidad);
                                CntDefinicionCuentasMigracion cntDefinicionCuentasMigracion = new CntDefinicionCuentasMigracion();
                                cntDefinicionCuentasMigracion.setCntEntidad(excelRowCntEntidad);
                                listaSuperioresPadres.add(cntDefinicionCuentasMigracion);
                            }
                            if (!mascaraGeneradaAux.equals(excelRowCntEntidad.getMascaraGenerada())) {
                                excelRowCntEntidadList.add(excelRowCntEntidad);
                                mascaraGeneradaAux = excelRowCntEntidad.getMascaraGenerada();
                                descripcionAux = excelRowCntEntidad.getDescripcion();
                            }
                            contador++;
                            if (contador == limiteRecorridoCntEntidad + 4) {
                                entra = false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        try {
//                            row = sheet.getRow(contador);
//                            System.out.println("...row es : "+row);
//                            excelRowCntEntidad = new CntEntidad();
//                            excelRowCntEntidad.setIdEntidadPadre(new Long(0));
//                            excelRowCntEntidad.setMascaraGenerada(row.getCell(0).getStringCellValue());
//                            excelRowCntEntidad.setDescripcion(row.getCell(1).getStringCellValue());
//                            excelRowCntEntidad.setNivel(cntEntidadesService.generaNivelPorMascara(excelRowCntEntidad.getMascaraGenerada()));
//                            excelRowCntEntidad.setTipo(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
//                            excelRowCntEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
//                            excelRowCntEntidad.setTieneAuxiliar(EnumTieneAuxiliar.NO.getCodigo());
//                            excelRowCntEntidad.setHabilitaCentroCosto(EnumHabilitaCentroCosto.NO.getCodigo());
//                            excelRowCntEntidad.setFechaAlta(fechaActual);
//                            excelRowCntEntidad.setUsuarioAlta(getLoginSession());
//                            if (cntEntidadesService.generaNivelPorMascara(excelRowCntEntidad.getMascaraGenerada()) == tamanioNivel) {
//                                listaSuperiores.add(excelRowCntEntidad);
//                                CntDefinicionCuentasMigracion cntDefinicionCuentasMigracion = new CntDefinicionCuentasMigracion();
//                                cntDefinicionCuentasMigracion.setCntEntidad(excelRowCntEntidad);
//                                listaSuperioresPadres.add(cntDefinicionCuentasMigracion);
//                            }
//                            if (!mascaraGeneradaAux.equals(excelRowCntEntidad.getMascaraGenerada())) {
//                                excelRowCntEntidadList.add(excelRowCntEntidad);
//                                mascaraGeneradaAux = excelRowCntEntidad.getMascaraGenerada();
//                                descripcionAux = excelRowCntEntidad.getDescripcion();
//                            }
//                            contador++;
//                            if (contador == limiteRecorridoCntEntidad + 4) {
//                                entra = false;
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }
                    for (CntEntidad ce : excelRowCntEntidadList) {
                        System.out.println("Cuenta:" + ce.getMascaraGenerada() + " Descripcion:" + ce.getDescripcion() + " Nivel:" + ce.getNivel());
                    }
                    System.out.println("LISTA PLAN CUENTAS SOLO PADRES");
                    for (CntEntidad cp : listaSuperiores) {
                        System.out.println("Cuenta:" + cp.getMascaraGenerada() + " Descripcion:" + cp.getDescripcion());
                    }
                    String mascaraGen;
                    mascaraGen = cntEntidadesService.generaMascaraConElRegistroDelExcel(rowInicio.getCell(0).getStringCellValue());
                    List<CntNivel> cntNivel = cntMigracionExcelService.generaNivelConElRegistroDelExcel(mascaraGen);
                    excelRowCntNivelList = cntMigracionExcelService.generaNivelConElRegistroDelExcel(mascaraGen);
                    System.out.println("LISTA NIVELES");
                    for (CntNivel cn : excelRowCntNivelList) {
                        System.out.println("Nivel:" + cn.getNivel() + " Tamanio:" + cn.getTamanio());
                    }
                    listaCntNivelesMigracion = cntMigracionExcelService.generaNivelConElRegistroDelExcel(mascaraGen);
                    activaBotonContinuarNiveles = verificaDescripcionListaNivelesMigracion();
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

    public void controlaCuenta(ValueChangeEvent event) {
        listaCuentasGenerales = parParametricasService.listaCuentasNoElegidas(listaDefinicionCuentasMigracion);
    }

    public String saveCntMascarCntNivelesCntPlancuentasCntParametroAutomatico() {
        cntMascaras = new CntMascara();
        cntMascaras.setTamanioNivel(tamanioNivel);
        cntMascaras.setMascara(cntEntidadesService.generaMascaraConElRegistroDelExcel(mascaraPrimeraColumna));
        super.setPersistValues(cntMascaras);
        try {
            ParGruposNivel parGruposNivel = (ParGruposNivel) parParametricasService.find(ParGruposNivel.class, EnumGruposNivel.PLAN_CUENTAS.getCodigo());
            cntMascaras.setGrupoNivel(parGruposNivel);
            if (cntMascarasService.verificaExistenciaDeMascara(parGruposNivel.getCodigo())) {
                cntEntidadesService.saveCntMascarCntNivelesCntPlancuentasCntParametroAutomatico(cntMascaras, excelRowCntNivelList, excelRowCntEntidadList, getLoginSession());
                MessageUtils.addInfoMessage("Se registro todos los registro del Documento Excel exitosamente");
            } else {
                MessageUtils.addErrorMessage("Ya se registro la mascara para Plan de Cuentas.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtils.addErrorMessage("Ocurrio un error al registrar el Documento Excel.");
        }
        return null;
    }

    public String guardaCntMascarCntNivelesCntPlancuentasCntParametroAutomatico() {
        cntMascaras = new CntMascara();
        cntMascaras.setTamanioNivel(tamanioNivel);
        cntMascaras.setMascara(cntEntidadesService.generaMascaraConElRegistroDelExcel(mascaraPrimeraColumna));
        super.setPersistValues(cntMascaras);
        try {
            ParGruposNivel parGruposNivel = (ParGruposNivel) parParametricasService.find(ParGruposNivel.class, EnumGruposNivel.PLAN_CUENTAS.getCodigo());
            cntMascaras.setGrupoNivel(parGruposNivel);
            if (cntMascarasService.verificaExistenciaDeMascara(parGruposNivel.getCodigo())) {
                cntMigracionExcelService.guardarCntMascarCntNivelesCntPlancuentasCntParametroAutomatico(cntMascaras, listaCntNivelesMigracion, excelRowCntEntidadList, listaSuperioresPadres);
                MessageUtils.addInfoMessage("Se registro todos los registro del Documento Excel exitosamente");
            } else {
                MessageUtils.addErrorMessage("Ya se registro la mascara para Plan de Cuentas.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtils.addErrorMessage("Ocurrio un error al registrar el Documento Excel.");
        }
        return null;
    }

    public void saveByExcelCntTiposCambio_action(List<CntTipoCambio> listaTipoCambio) {
//        List<CntTiposCambio> listaBD = cntTiposCambioService.dataForUpdatingList(listaTipoCambio.get(0).getFecha(), listaTipoCambio.get(listaTipoCambio.size() - 1).getFecha(), getIdGestionSession());
//        if (listaBD.size() == listaTipoCambio.size()) {
//            try {
//                for (int i = 0; i < listaBD.size(); i++) {
//                    listaBD.get(i).setTipoCambio(listaTipoCambio.get(i).getTipoCambio());
//                    listaBD.get(i).setTipoUfv(listaTipoCambio.get(i).getTipoUfv());
//                    super.setMergeValues(listaBD.get(i));
//                    cntTiposCambioService.mergeCntTiposCambio(listaBD.get(i));
//                }
//            } catch (Exception e) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e.getMessage()));
//            }
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", "Revise que los datos a guardar sean los correctos"));
//        }
    }

    public CntEntidad getExcelRowInicioCntEntidad() {
        return excelRowInicioCntEntidad;
    }

    public void setExcelRowInicioCntEntidad(CntEntidad excelRowInicioCntEntidad) {
        this.excelRowInicioCntEntidad = excelRowInicioCntEntidad;
    }

    public CntEntidad getExcelRowCntEntidad() {
        return excelRowCntEntidad;
    }

    public void setExcelRowCntEntidad(CntEntidad excelRowCntEntidad) {
        this.excelRowCntEntidad = excelRowCntEntidad;
    }

    public List<CntEntidad> getExcelRowCntEntidadList() {
        return excelRowCntEntidadList;
    }

    public void setExcelRowCntEntidadList(List<CntEntidad> excelRowCntEntidadList) {
        this.excelRowCntEntidadList = excelRowCntEntidadList;
    }

    public List<CntNivel> getExcelRowCntNivelList() {
        return excelRowCntNivelList;
    }

    public void setExcelRowCntNivelList(List<CntNivel> excelRowCntNivelList) {
        this.excelRowCntNivelList = excelRowCntNivelList;
    }

    public List<CntEntidad> getListaSuperiores() {
        return listaSuperiores;
    }

    public void setListaSuperiores(List<CntEntidad> listaSuperiores) {
        this.listaSuperiores = listaSuperiores;
    }

    public List<CntEntidad> getExcelRowList() {
        return excelRowCntEntidadList;
    }

    public void setExcelRowList(List<CntEntidad> excelRowCntEntidadList) {
        this.excelRowCntEntidadList = excelRowCntEntidadList;
    }

    /*Codigo Jonas migracion de datos*/
    public String generaDigitoMascaraGenerada(String mascaraGenerada) {
        return mascaraGenerada.substring(0, 1);
    }

    public void cierraDialogo(ValueChangeEvent e) {
        resetSessionVariables();
    }

    public List<CntDefinicionCuentasMigracion> conMetodo() {
        if (listaDefinicionCuentasMigracion.isEmpty()) {
            listaDefinicionCuentasMigracion = parParametricasService.listaDefinicionCuentas(listaSuperiores);
        }
        return listaDefinicionCuentasMigracion;
    }

    public List<CntDefinicionCuentasMigracion> getListaDefinicionCuentasMigracion() {
        if (listaDefinicionCuentasMigracion.isEmpty()) {
            listaDefinicionCuentasMigracion = parParametricasService.listaDefinicionCuentas(listaSuperiores);
        }
        return listaDefinicionCuentasMigracion;
    }

    public String guardarDefinicionCuentas() {
        if (parParametricasService.validaListaDefinicionCuentas(listaDefinicionCuentasMigracion)) {
            if (parParametricasService.validaCuentasRepetidas(listaDefinicionCuentasMigracion)) {
                parParametricasService.guardaDefinicionDeCuentasMigracion(listaDefinicionCuentasMigracion);
                MessageUtils.addInfoMessage("Se Migro correctamente los datos");
                guardaCntMascarCntNivelesCntPlancuentasCntParametroAutomatico();
                return "definicionCuentas";
            }
            MessageUtils.addErrorMessage("No se puede asignar una cuenta mas de una vez");
            return null;
        }
        MessageUtils.addErrorMessage("Asegurese de asignar todas las cuentas");
        return null;
    }

    public void setListaDefinicionCuentasMigracion(List<CntDefinicionCuentasMigracion> listaDefinicionCuentasMigracion) {
        this.listaDefinicionCuentasMigracion = listaDefinicionCuentasMigracion;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public List<ParCuentasGenerales> getListaCuentasGenerales() {
        return listaCuentasGenerales;
    }

    public void setParCuentasGeneralesList(List<ParCuentasGenerales> parCuentasGeneralesList) {
        this.parCuentasGeneralesList = parCuentasGeneralesList;
    }
    /*Fin codigo Jonas migracion de datos*/

    public CntNivel getExcelRowCntNivel() {
        return excelRowCntNivel;
    }

    public void setExcelRowCntNivel(CntNivel excelRowCntNivel) {
        this.excelRowCntNivel = excelRowCntNivel;
    }

    public CntNivelesService getCntNivelesService() {
        return cntNivelesService;
    }

    public void setCntNivelesService(CntNivelesService cntNivelesService) {
        this.cntNivelesService = cntNivelesService;
    }

    public CntMascarasService getCntMascarasService() {
        return cntMascarasService;
    }

    public void setCntMascarasService(CntMascarasService cntMascarasService) {
        this.cntMascarasService = cntMascarasService;
    }

    public CntMascara getCntMascaras() {
        return cntMascaras;
    }

    public void setCntMascaras(CntMascara cntMascaras) {
        this.cntMascaras = cntMascaras;
    }

    public int getTamanioNivel() {
        return tamanioNivel;
    }

    public void setTamanioNivel(int tamanioNivel) {
        this.tamanioNivel = tamanioNivel;
    }

    public String getMascaraPrimeraColumna() {
        return mascaraPrimeraColumna;
    }

    public void setMascaraPrimeraColumna(String mascaraPrimeraColumna) {
        this.mascaraPrimeraColumna = mascaraPrimeraColumna;
    }

//    public void genraNiveles() {
//        migracionCntNivelesList = new ArrayList<CntNivel>();
//        nivel = 0;
//        numerosNiveles = 0;
//        for (int i = 1; i <= numeroObjetosNivel; i++) {
//            nivel++;
//            cntNiveles.setNivel(nivel);
//            cntNiveles.setTamanio(1);
//            cntNiveles.setDescripcion("");
//            ParTiposDatoNivel parTiposDatoNivel = (ParTiposDatoNivel) parParametricasService.find(ParTiposDatoNivel.class, "N");
//            cntNiveles.setTipoNivel(parTiposDatoNivel);
//            super.setPersistValues(cntNiveles);
//            auxCntNivelesList.add(cntNiveles);
//            if (gruposNivelCombo.equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo())) {
//                generaMascaraAutomatico();
//            } else {
//                swActivaOpciones = true;
//                generaMascaraAutomaticoCentros();
//            }
//            cntNiveles = new CntNivel();
//            cntNiveles.setTamanio(1);
//            numerosNiveles++;
//        }
//    }
    public List<CntNivel> getListaCntNivelesMigracion() {
        return listaCntNivelesMigracion;
    }

    public void setListaCntNivelesMigracion(List<CntNivel> listaCntNivelesMigracion) {
        this.listaCntNivelesMigracion = listaCntNivelesMigracion;
    }

    public void onRowEdit(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        activaBotonContinuarNiveles = verificaDescripcionListaNivelesMigracion();
//        Object oldValue = event.getOldValue();
//        Object newValue = event.getNewValue();
//         
//        if(newValue != null && !newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
    }

    public Boolean getActivaBotonContinuarNiveles() {
        return activaBotonContinuarNiveles;
    }

    public void setActivaBotonContinuarNiveles(Boolean activaBotonContinuarNiveles) {
        this.activaBotonContinuarNiveles = activaBotonContinuarNiveles;
    }

    public Boolean getActivaBotonContinuarCuentas() {
        return activaBotonContinuarCuentas;
    }

    public void setActivaBotonContinuarCuentas(Boolean activaBotonContinuarCuentas) {
        this.activaBotonContinuarCuentas = activaBotonContinuarCuentas;
    }

    private String generaNivelPorMascara(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public CntMigracionExcelService getCntMigracionExcelService() {
        return cntMigracionExcelService;
    }

    public void setCntMigracionExcelService(CntMigracionExcelService cntMigracionExcelService) {
        this.cntMigracionExcelService = cntMigracionExcelService;
    }

    public List<CntDefinicionCuentasMigracion> getListaSuperioresPadres() {
        return listaSuperioresPadres;
    }

    public void setListaSuperioresPadres(List<CntDefinicionCuentasMigracion> listaSuperioresPadres) {
        this.listaSuperioresPadres = listaSuperioresPadres;
    }

}
