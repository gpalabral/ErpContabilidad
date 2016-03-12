package com.bap.erp.vista.cnt.backing;

import com.bap.comp.CompPlanEntidadesBacking;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumParametrosDeGestion_ComprasAndVentas;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.wrapper.*;
import com.bap.erp.vista.common.AbstractManagedBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.apache.log4j.Logger;
import org.primefaces.event.TabChangeEvent;

@ManagedBean(name = "cntGeneralesBacking")
@ViewScoped
public class CntGeneralesBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntGeneralesBacking.class);
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    DatosEmpresaWrapper datosEmpresaWrapper = new DatosEmpresaWrapper();
    VariosWrapper variosWrapper = new VariosWrapper();
    GestionContableWrapper gestionContableWrapper = new GestionContableWrapper();
    ComprasYVentasWrapper comprasYVentasWrapper;
    CuentasDeAjusteWrapper cuentasDeAjusteWrapper = new CuentasDeAjusteWrapper();
    RetencionesIUEWrapper retencionesIUEWrapper = new RetencionesIUEWrapper();
    @ManagedProperty(value = "#{planEntBacking}")
    private CompPlanEntidadesBacking planEntidadesBacking;
    private Boolean bloqueaUsuario = true;
    private CntEntidad ctaDebitoFiscal;
    private CntEntidad ctaCreditoFiscal;
    private Boolean activaAjustesREI = false;
    private Boolean activaLongitud = false;
    private String facturaComputarizada;

    public CntGeneralesBacking() {
    }

    @PostConstruct
    public void initCntGestionesBacking() {
        datosEmpresaWrapper = parParametricasService.factoryEmpresa();
        variosWrapper = parParametricasService.factoryVarios();
        gestionContableWrapper = parParametricasService.factoryGestionContable();
        comprasYVentasWrapper = parParametricasService.factoryComprasYVentas();
        cuentasDeAjusteWrapper = parParametricasService.factoryCuentasDeAjuste();
        retencionesIUEWrapper = parParametricasService.factoryRetencionesIUE();
        inicializaDatos();
        
//        if(getFrom EnumParametrosDeGestion_ComprasAndVentas.CUENTA_DE_DEBITO_FISCAL.getCodigo()){
//            
//        }
    }

    public void inicializaDatos() {
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

    public ComprasYVentasWrapper getComprasYVentasWrapper() {
        return comprasYVentasWrapper;
    }

    public void setComprasYVentasWrapper(ComprasYVentasWrapper comprasYVentasWrapper) {
        this.comprasYVentasWrapper = comprasYVentasWrapper;
    }

    public CuentasDeAjusteWrapper getCuentasDeAjusteWrapper() {
        return cuentasDeAjusteWrapper;
    }

    public void setCuentasDeAjusteWrapper(CuentasDeAjusteWrapper cuentasDeAjusteWrapper) {
        this.cuentasDeAjusteWrapper = cuentasDeAjusteWrapper;
    }

    public RetencionesIUEWrapper getRetencionesIUEWrapper() {
        return retencionesIUEWrapper;
    }

    public void setRetencionesIUEWrapper(RetencionesIUEWrapper retencionesIUEWrapper) {
        this.retencionesIUEWrapper = retencionesIUEWrapper;
    }

    public String convierteFechaSoloAnio(GestionContableWrapper contableWrapper) {
        if (contableWrapper.getPeriodoActual() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
            return formatter.format(contableWrapper.getPeriodoActual());
        }
        return "Sin Periodo";
    }

    public String modificaDatosEmpresa() {
        try {
            parParametricasService.modificarDatosEmpresa(datosEmpresaWrapper, gestionContableWrapper, variosWrapper, getLoginSession());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String modificaParametrosDeGestion() {
        try {
            parParametricasService.modificaParametrosDeGestion(comprasYVentasWrapper, cuentasDeAjusteWrapper, retencionesIUEWrapper, "Jonas");
        } catch (Exception e) {
        }
        return null;
    }

    public void onTabChange(TabChangeEvent event) {
//        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());  
//        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }

    public void seleccionarCuenta_listener(ActionEvent event) {        
    }

    /**
     * @return the planEntidadesBacking
     */
    public CompPlanEntidadesBacking getPlanEntidadesBacking() {
        return planEntidadesBacking;
    }

    /**
     * @param planEntidadesBacking the planEntidadesBacking to set
     */
    public void setPlanEntidadesBacking(CompPlanEntidadesBacking planEntidadesBacking) {
        this.planEntidadesBacking = planEntidadesBacking;
    }

    public Boolean getBloqueaUsuario() {
        return bloqueaUsuario;
    }

    public void setBloqueaUsuario(Boolean bloqueaUsuario) {
        this.bloqueaUsuario = bloqueaUsuario;
    }

    public String metodo_action() {        
        if (ctaDebitoFiscal != null) {            
        }
        return null;
    }

    /**
     * @return the ctaDebitoFiscal
     */
    public CntEntidad getCtaDebitoFiscal() {
        return ctaDebitoFiscal;
    }

    /**
     * @param ctaDebitoFiscal the ctaDebitoFiscal to set
     */
    public void setCtaDebitoFiscal(CntEntidad ctaDebitoFiscal) {
        this.ctaDebitoFiscal = ctaDebitoFiscal;
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

    public void activaLongitud(ValueChangeEvent e) {
        if (variosWrapper.getCuentasCoorporativas()) {
            activaLongitud = true;
        } else {
            activaLongitud = false;
        }
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

    /**
     * @return the ctaCreditoFiscal
     */
    public CntEntidad getCtaCreditoFiscal() {
        return ctaCreditoFiscal;
    }

    /**
     * @param ctaCreditoFiscal the ctaCreditoFiscal to set
     */
    public void setCtaCreditoFiscal(CntEntidad ctaCreditoFiscal) {
        this.ctaCreditoFiscal = ctaCreditoFiscal;
    }

    public void mostrarEntidadSel_action(ActionEvent actionEvent) {
        if (comprasYVentasWrapper.getDebitoFiscal() != null) {              
        }
    }
}
