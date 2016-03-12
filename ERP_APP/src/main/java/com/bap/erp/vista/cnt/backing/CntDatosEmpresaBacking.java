package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntDistribucionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.wrapper.*;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.FlowEvent;

@ManagedBean(name = "cntDatosEmpresaBacking")
@ViewScoped
public class CntDatosEmpresaBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntDistribucionCentroCostoService}")
    private CntDistribucionCentroCostoService cntDistribucionCentroCostoService;
    DatosEmpresaWrapper datosEmpresaWrapper = new DatosEmpresaWrapper();
    VariosWrapper variosWrapper = new VariosWrapper();
    GestionContableWrapper gestionContableWrapper = new GestionContableWrapper();
    private Boolean bloqueaUsuario = true;
    private CntEntidad ctaDebitoFiscal;
    private Boolean activaAjustesREI = false;
    private Boolean activaLongitud = false;
    private Boolean habilitaCentroCostos = false;
    private Boolean habilitaAuxiliar = false;
    private String facturaComputarizada;
    private boolean skip;
    private int gestionFiscalListNumeros[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private int longitudListNumeros[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    private String mesesList[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Nobiembre", "Diciembre"};
    private String mesGestionFiscal;
    private String mesPeriodo;
    private int periodoActualVista;
    private int anioActualVista;

    public CntDatosEmpresaBacking() {
    }

    @PostConstruct
    public void initCntDatosEmpresaBacking() {

        try {
            datosEmpresaWrapper = parParametricasService.factoryEmpresa();
            variosWrapper = parParametricasService.factoryVarios();
            gestionContableWrapper = parParametricasService.factoryGestionContable();
            inicializaDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void inicializaDatos() throws Exception {
        if (gestionContableWrapper.getNormaContable3().equals("ACTUAL")) {
            activaAjustesREI = true;
        } else {
            activaAjustesREI = false;
        }
        if (variosWrapper.getFacturacionComputarizada()) {
            facturaComputarizada = "C/Cod.Ctrl.";
        } else {
            facturaComputarizada = "S/Cod.Ctrl.";
        }
        if (variosWrapper.getCuentasCoorporativas()) {
            activaLongitud = true;
        } else {
            activaLongitud = false;
        }
        System.out.println("entra");
        if (gestionContableWrapper.getPeriodoActual() != null) {
            periodoActualVista = gestionContableWrapper.getPeriodoActual();
            mesPeriodo = mesesList[gestionContableWrapper.getPeriodoActual()-1];
        }
        if (gestionContableWrapper.getAnioActual() != null) {
            anioActualVista = gestionContableWrapper.getAnioActual();
        }
        if (gestionContableWrapper.getInicioGestionFiscal() != 0) {
            mesGestionFiscal = mesesList[gestionContableWrapper.getInicioGestionFiscal() - 1];
        }
        if (cntEntidadesService.verificaAuxiliarEntidades()) {
            habilitaAuxiliar = true;
        }
        if (cntEntidadesService.verificaCentroCostosEntidades() && cntDistribucionCentroCostoService.existeUnComprobanteConCentroDeCosto()) {
            habilitaCentroCostos = true;
        }
        
    }

    public String convierteFechaSoloAnio(GestionContableWrapper contableWrapper) {
//        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (contableWrapper.getPeriodoActual() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            return formatter.format(contableWrapper.getPeriodoActual());
        }
//aumentado para que muestre el año
        java.util.Calendar fecha = java.util.Calendar.getInstance();
        return String.valueOf(fecha.get(java.util.Calendar.YEAR));
//        return "Sin Periodo";

    }

    public String modificaDatosEmpresa() {
            try {
                if (datosEmpresaWrapper.getDireccion().equals("")) {
                    MessageUtils.addErrorMessage("La direccion es obligatoria");
                } else {
                    gestionContableWrapper.setPeriodoActual(periodoActualVista);
                    gestionContableWrapper.setAnioActual(anioActualVista);
                    parParametricasService.modificarDatosEmpresa(datosEmpresaWrapper, gestionContableWrapper, variosWrapper, getLoginSession());
                    return "paginaEnBlanco";
                }
            //            MessageUtils.addInfoMessage("Se modifico Correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void activaAjusteREI(ValueChangeEvent e) {

        if (gestionContableWrapper.getNormaContable3().equals("ACTUAL")) {

            activaAjustesREI = true;
        } else {

            activaAjustesREI = false;
        }
    }

    public void activaFacturacionComputarizada(ValueChangeEvent e) {
        if (variosWrapper.getFacturacionComputarizada()) {
            facturaComputarizada = "C/Cod.Ctrl.";
        } else {
            facturaComputarizada = "S/Cod.Ctrl.";
        }
    }

    public String onFlowProcess(FlowEvent event) {

        if (skip) {
            skip = false;   //reset in case user goes back  
            return "varios";
        } else {
            return event.getNewStep();
        }
    }

    public void activaLongitud(ValueChangeEvent e) {
        if (variosWrapper.getCuentasCoorporativas()) {
            activaLongitud = true;
        } else {
            activaLongitud = false;
        }
    }

    public String cancelar() {
        return "paginaEnBlanco";
    }

    public DatosEmpresaWrapper getDatosEmpresaWrapper() {
        return datosEmpresaWrapper;
    }

    public void setDatosEmpresaWrapper(DatosEmpresaWrapper datosEmpresaWrapper) {
        this.datosEmpresaWrapper = datosEmpresaWrapper;
    }

    public VariosWrapper getVariosWrapper() {
        return variosWrapper;
    }

    public void setVariosWrapper(VariosWrapper variosWrapper) {
        this.variosWrapper = variosWrapper;
    }

    public GestionContableWrapper getGestionContableWrapper() {
        return gestionContableWrapper;
    }

    public void setGestionContableWrapper(GestionContableWrapper gestionContableWrapper) {
        this.gestionContableWrapper = gestionContableWrapper;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public Boolean getBloqueaUsuario() {
        return bloqueaUsuario;
    }

    public void setBloqueaUsuario(Boolean bloqueaUsuario) {
        this.bloqueaUsuario = bloqueaUsuario;
    }

    public CntEntidad getCtaDebitoFiscal() {
        return ctaDebitoFiscal;
    }

    public void setCtaDebitoFiscal(CntEntidad ctaDebitoFiscal) {
        this.ctaDebitoFiscal = ctaDebitoFiscal;
    }

    public Boolean getActivaAjustesREI() {
        return activaAjustesREI;
    }

    public void setActivaAjustesREI(Boolean activaAjustesREI) {
        this.activaAjustesREI = activaAjustesREI;
    }

    public String getFacturaComputarizada() {
        return facturaComputarizada;
    }

    public void setFacturaComputarizada(String facturaComputarizada) {
        this.facturaComputarizada = facturaComputarizada;
    }

    public Boolean getActivaLongitud() {
        return activaLongitud;
    }

    public void setActivaLongitud(Boolean activaLongitud) {
        this.activaLongitud = activaLongitud;
    }

    public void actualizaMesGestionFiscal(ValueChangeEvent e) {
        mesGestionFiscal = mesesList[gestionContableWrapper.getInicioGestionFiscal() - 1];
    }

    public void actualizaMesPeriodo(ValueChangeEvent e) {
        mesPeriodo = mesesList[periodoActualVista - 1];
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public int[] getGestionFiscalListNumeros() {
        return gestionFiscalListNumeros;
    }

    public void setGestionFiscalListNumeros(int[] gestionFiscalListNumeros) {
        this.gestionFiscalListNumeros = gestionFiscalListNumeros;
    }

    public String[] getMesesList() {
        return mesesList;
    }

    public void setMesesList(String[] mesesList) {
        this.mesesList = mesesList;
    }

    public String getMesGestionFiscal() {
        return mesGestionFiscal;
    }

    public void setMesGestionFiscal(String mesGestionFiscal) {
        this.mesGestionFiscal = mesGestionFiscal;
    }

    public int getPeriodoActualVista() {
        return periodoActualVista;
    }

    public void setPeriodoActualVista(int periodoActualVista) {
        this.periodoActualVista = periodoActualVista;
    }

    public String getMesPeriodo() {
        return mesPeriodo;
    }

    public void setMesPeriodo(String mesPeriodo) {
        this.mesPeriodo = mesPeriodo;
    }

    public int[] getLongitudListNumeros() {
        return longitudListNumeros;
    }

    public void setLongitudListNumeros(int[] longitudListNumeros) {
        this.longitudListNumeros = longitudListNumeros;
    }

    public Boolean getHabilitaCentroCostos() {
        return habilitaCentroCostos;
    }

    public void setHabilitaCentroCostos(Boolean habilitaCentroCostos) {
        this.habilitaCentroCostos = habilitaCentroCostos;
    }

    public Boolean getHabilitaAuxiliar() {
        return habilitaAuxiliar;
    }

    public void setHabilitaAuxiliar(Boolean habilitaAuxiliar) {
        this.habilitaAuxiliar = habilitaAuxiliar;
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public CntDistribucionCentroCostoService getCntDistribucionCentroCostoService() {
        return cntDistribucionCentroCostoService;
    }

    public void setCntDistribucionCentroCostoService(CntDistribucionCentroCostoService cntDistribucionCentroCostoService) {
        this.cntDistribucionCentroCostoService = cntDistribucionCentroCostoService;
    }

    public int getAnioActualVista() {
        return anioActualVista;
    }

    public void setAnioActualVista(int anioActualVista) {
        this.anioActualVista = anioActualVista;
    }
    
    
}
