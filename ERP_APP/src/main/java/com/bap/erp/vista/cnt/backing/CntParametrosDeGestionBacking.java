package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.enums.EnumParametrosDeGestion_ComprasAndVentas;
import com.bap.erp.modelo.enums.EnumParametrosDeGestion_CuentasDeAjuste;
import com.bap.erp.modelo.enums.EnumParametrosDeGestion_RetencionesIU;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.wrapper.ComprasYVentasWrapper;
import com.bap.erp.modelo.wrapper.CuentasDeAjusteWrapper;
import com.bap.erp.modelo.wrapper.RetencionesIUEWrapper;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.apache.log4j.Logger;
import org.primefaces.event.FlowEvent;

@ManagedBean(name = "cntParametrosDeGestionBacking")
@ViewScoped
public class CntParametrosDeGestionBacking extends AbstractManagedBean implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="INYECCIONES">    
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="VARIABLES">
    private static Logger log = Logger.getLogger(CntParametrosDeGestionBacking.class);
    private ComprasYVentasWrapper comprasYVentasWrapper = new ComprasYVentasWrapper();
    private CuentasDeAjusteWrapper cuentasDeAjusteWrapper = new CuentasDeAjusteWrapper();
    private RetencionesIUEWrapper retencionesIUEWrapper = new RetencionesIUEWrapper();
    private CntEntidad cuentaDebitoFiscalValor;
    private CntEntidad cuentaCreditoFiscalValor;
    private CntEntidad cuentaCreditoFiscalNoDeducibleValor;
    private CntEntidad cuentaCreditoFiscalTransitorioValor;
    private CntEntidad cuentaImpuestosTransaccionesValor;
    private CntEntidad cuentaGastosImpuestosTransaccionesValor;
    private CntEntidad cuentaDebitoFiscalTransitorioValor;
    private CntEntidad cuentaInflacionResultadoExposicionInflacionValor;
    private CntEntidad cuentaAjusteCorreccionMonetariaValor;
    private CntEntidad cuentaDiferenciaCambioValor;
    private CntEntidad cuentaAjusteCapitalValor;
    private CntEntidad cuentaAjusteReservasPatrimonialesValor;
    private CntEntidad cuentaRetencionServicioValor;
    private CntEntidad cuentaRetencionBienesValor;
    private CntEntidad cuentaRetencionValor;
    private CntEntidad cuentaRetencionRC_IVAValor;
    private CntEntidad cuentaRetencionExteriorValor;
    private CntEntidad cuentaRetencionSectorIndustrialValor;
    private String tipoCuentaSelecionada;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="METODOS">
    public CntParametrosDeGestionBacking() {
    }

    @PostConstruct
    public void initCntParametrosDeGestionBacking() {
        comprasYVentasWrapper = parParametricasService.factoryComprasYVentas();
        cuentasDeAjusteWrapper = parParametricasService.factoryCuentasDeAjuste();
        retencionesIUEWrapper = parParametricasService.factoryRetencionesIUE();
        activaBotonSeleccion = true;
        cuentaDebitoFiscalValor = new CntEntidad();
        cuentaCreditoFiscalValor = new CntEntidad();
        cuentaCreditoFiscalNoDeducibleValor = new CntEntidad();
        cuentaCreditoFiscalTransitorioValor = new CntEntidad();
        cuentaImpuestosTransaccionesValor = new CntEntidad();
        cuentaGastosImpuestosTransaccionesValor = new CntEntidad();
        cuentaDebitoFiscalTransitorioValor = new CntEntidad();

        cuentaInflacionResultadoExposicionInflacionValor = new CntEntidad();
        cuentaAjusteCorreccionMonetariaValor = new CntEntidad();
        cuentaDiferenciaCambioValor = new CntEntidad();
        cuentaAjusteCapitalValor = new CntEntidad();
        cuentaAjusteReservasPatrimonialesValor = new CntEntidad();

        cuentaRetencionServicioValor = new CntEntidad();
        cuentaRetencionBienesValor = new CntEntidad();
        cuentaRetencionValor = new CntEntidad();
        cuentaRetencionRC_IVAValor = new CntEntidad();
        cuentaRetencionExteriorValor = new CntEntidad();
        cuentaRetencionSectorIndustrialValor = new CntEntidad();
        cargaValoresDesdeLaBaseDeDatosGeneral();
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public String modificaParametrosDeGestion() {
        try {
            if (verificaSiSeLlenaronTodasLasCuentas()) {
                cargaValoresGeneral();
                parParametricasService.modificaParametrosDeGestion(comprasYVentasWrapper, cuentasDeAjusteWrapper, retencionesIUEWrapper, getLoginSession());
                MessageUtils.addInfoMessage("La modificaci\u00f3n de los par\u00e1metros de gesti\u00f3n se realiz\u00f3 de  manera exitosa.");
                return "paginaEnBlanco";
            } else {
                MessageUtils.addInfoMessage("Debe llenar todas las cuentas");
                return "parametrosGestion";
            }
        } catch (Exception e) {
        }
        return null;
    }

    public String cancelar() {
        return "paginaEnBlanco";
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GETTERS Y SETTERS">
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
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Codigo aumentado por Henrry Guzman 24/11/2014">
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    private List<CntEntidad> cntEntidadList;
    //<editor-fold defaultstate="collapsed" desc="Autor: Henrry Fecha: 24 Junio 2013">
    private List<CntEntidad> cntEntidadHermanosList = new ArrayList<CntEntidad>();
    private List<CntEntidad> cntEntidadHijosList = new ArrayList<CntEntidad>();
    private CntEntidad selectedPlanCuentasHermanos;
    private CntEntidad selectedPlanCuentasHijos;
    private CntEntidad selected = new CntEntidad();
    private Boolean activaBotonSeleccion;
    private String valor;
    private List<CntEntidad> filteredcntEntidad;
    private Boolean eleccionTodos = false;
    private String color = null;
    private List<CntEntidad> listaMomentanea = new ArrayList<CntEntidad>();

    public String deletecntEntidad_action() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminar:", "A un esta en construccion ..Gracias por su comprension!!"));
        return null;
    }

    public List<CntEntidad> cntObjetosPorGrupoNivelList() {
        try {
            cntEntidadList = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
        } catch (Exception e) {
        }
        return cntEntidadList;
    }

    public List<CntEntidad> cntObejetosPorGrupoNivelComponenteList(Boolean activaRetenciones) {
        try {
            if (cntEntidadList == null) {
                if (activaRetenciones) {
                    cntEntidadList = cntEntidadesService.listaCuentasParaRetencionesAndGrossingUp();
                } else {
                    cntEntidadList = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
                }
            }
        } catch (Exception e) {
        }
        return cntEntidadList;
    }

    ////*****************************************
    public List<CntEntidad> getcntEntidadList() {
        return cntEntidadList;
    }

    public void setcntEntidadList(List<CntEntidad> cntEntidadList) {
        this.cntEntidadList = cntEntidadList;
    }

    public CntEntidadesService getcntEntidadesService() {
        return cntEntidadesService;
    }

    public void setcntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    //**********Para elegir cuentas de ajuste**********//
    public String seleccionar() {
        if (selected.getIdEntidad() != null) {
            if (selected.getNivel() == 1) {
                setInSessionIdEntidadSeleccion(selected.getIdEntidad());
                adicionaValorPorBoton(tipoCuentaSelecionada, selected);
                return null;
            } else {
                MessageUtils.addErrorMessage("La cuenta debe ser de Ãºltimo nivel.");
            }
        } else {
            MessageUtils.addErrorMessage("No selecciono ninguna cuenta.");
        }
        return null;
    }

    public void adicionaValorPorBoton(String tipoCuentaSelecionada, CntEntidad cntEntidad) {
        if (tipoCuentaSelecionada.equals("CDBF")) {
            cuentaDebitoFiscalValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CCRF")) {
            cuentaCreditoFiscalValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CCFD")) {
            cuentaCreditoFiscalNoDeducibleValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CCFT")) {
            cuentaCreditoFiscalTransitorioValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CITP")) {
            cuentaImpuestosTransaccionesValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CITG")) {
            cuentaGastosImpuestosTransaccionesValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CDFT")) {
            cuentaDebitoFiscalTransitorioValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("AREI")) {
            cuentaInflacionResultadoExposicionInflacionValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("ACM3")) {
            cuentaAjusteCorreccionMonetariaValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("DCA3")) {
            cuentaDiferenciaCambioValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("ACAP")) {
            cuentaAjusteCapitalValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("AJRP")) {
            cuentaAjusteReservasPatrimonialesValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CTRB")) {
            cuentaRetencionBienesValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CTRS")) {
            cuentaRetencionServicioValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CRIT")) {
            cuentaRetencionValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CRCI")) {
            cuentaRetencionRC_IVAValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CRAE")) {
            cuentaRetencionExteriorValor = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("CRSI")) {
            cuentaRetencionSectorIndustrialValor = cntEntidad;
        }
    }

    public void cargaValoresGeneral() {
        comprasYVentasWrapper.setDebitoFiscal(cuentaDebitoFiscalValor);
        comprasYVentasWrapper.setCreditoFiscal(cuentaCreditoFiscalValor);
        comprasYVentasWrapper.setCreditoFiscalNoDeducible(cuentaCreditoFiscalNoDeducibleValor);
        comprasYVentasWrapper.setCreditoFiscalTransitorio(cuentaCreditoFiscalTransitorioValor);
        comprasYVentasWrapper.setImpuestoTransaccion(cuentaImpuestosTransaccionesValor);
        comprasYVentasWrapper.setGastoImpuestoTransaccion(cuentaGastosImpuestosTransaccionesValor);
        comprasYVentasWrapper.setDebitoFiscalTransitorio(cuentaDebitoFiscalTransitorioValor);

        cuentasDeAjusteWrapper.setInflacionResulPorExposicionInflacion(cuentaInflacionResultadoExposicionInflacionValor);
        cuentasDeAjusteWrapper.setAjusteCorreccionMonetaria(cuentaAjusteCorreccionMonetariaValor);
        cuentasDeAjusteWrapper.setDiferenciaCambio(cuentaDiferenciaCambioValor);
        cuentasDeAjusteWrapper.setAjusteCapital(cuentaAjusteCapitalValor);
        cuentasDeAjusteWrapper.setAjusteReservasPatrimoniales(cuentaAjusteReservasPatrimonialesValor);

        retencionesIUEWrapper.setCuentaIUERetencionesServicios(cuentaRetencionServicioValor);
        retencionesIUEWrapper.setCuentaIUERetencionesBienes(cuentaRetencionBienesValor);
        retencionesIUEWrapper.setCuentaITRetenciones(cuentaRetencionValor);
        retencionesIUEWrapper.setCuentaRetencionesRCIVA(cuentaRetencionRC_IVAValor);
        retencionesIUEWrapper.setCuentaRetencionesExterior(cuentaRetencionExteriorValor);
        retencionesIUEWrapper.setCuentaRetencionIUEITSectorIndustrial(cuentaRetencionSectorIndustrialValor);
    }

    public void cargaValoresDesdeLaBaseDeDatosGeneral() {
        cuentaDebitoFiscalValor = comprasYVentasWrapper.getDebitoFiscal();
        cuentaCreditoFiscalValor = comprasYVentasWrapper.getCreditoFiscal();
        cuentaCreditoFiscalNoDeducibleValor = comprasYVentasWrapper.getCreditoFiscalNoDeducible();
        cuentaCreditoFiscalTransitorioValor = comprasYVentasWrapper.getCreditoFiscalTransitorio();
        cuentaImpuestosTransaccionesValor = comprasYVentasWrapper.getImpuestoTransaccion();
        cuentaGastosImpuestosTransaccionesValor = comprasYVentasWrapper.getGastoImpuestoTransaccion();
        cuentaDebitoFiscalTransitorioValor = comprasYVentasWrapper.getDebitoFiscalTransitorio();

        cuentaInflacionResultadoExposicionInflacionValor = cuentasDeAjusteWrapper.getInflacionResulPorExposicionInflacion();
        cuentaAjusteCorreccionMonetariaValor = cuentasDeAjusteWrapper.getAjusteCorreccionMonetaria();
        cuentaDiferenciaCambioValor = cuentasDeAjusteWrapper.getDiferenciaCambio();
        cuentaAjusteCapitalValor = cuentasDeAjusteWrapper.getAjusteCapital();
        cuentaAjusteReservasPatrimonialesValor = cuentasDeAjusteWrapper.getAjusteReservasPatrimoniales();

        cuentaRetencionServicioValor = retencionesIUEWrapper.getCuentaIUERetencionesServicios();
        cuentaRetencionBienesValor = retencionesIUEWrapper.getCuentaIUERetencionesBienes();
        cuentaRetencionValor = retencionesIUEWrapper.getCuentaITRetenciones();
        cuentaRetencionRC_IVAValor = retencionesIUEWrapper.getCuentaRetencionesRCIVA();
        cuentaRetencionExteriorValor = retencionesIUEWrapper.getCuentaRetencionesExterior();
        cuentaRetencionSectorIndustrialValor = retencionesIUEWrapper.getCuentaRetencionIUEITSectorIndustrial();
    }

    public Boolean verificaSiSeLlenaronTodasLasCuentas() {
        return !(cuentaDebitoFiscalValor.getDescripcion().equals("")
                || cuentaCreditoFiscalValor.getDescripcion().equals("")
                || cuentaCreditoFiscalNoDeducibleValor.getDescripcion().equals("")
                || cuentaCreditoFiscalTransitorioValor.getDescripcion().equals("")
                || cuentaImpuestosTransaccionesValor.getDescripcion().equals("")
                || cuentaGastosImpuestosTransaccionesValor.getDescripcion().equals("")
                || cuentaDebitoFiscalTransitorioValor.getDescripcion().equals("")
                || cuentaInflacionResultadoExposicionInflacionValor.getDescripcion().equals("")
                || cuentaAjusteCorreccionMonetariaValor.getDescripcion().equals("")
                || cuentaDiferenciaCambioValor.getDescripcion().equals("")
                || cuentaAjusteCapitalValor.getDescripcion().equals("")
                || cuentaAjusteReservasPatrimonialesValor.getDescripcion().equals("")
                || cuentaRetencionServicioValor.getDescripcion().equals("")
                || cuentaRetencionBienesValor.getDescripcion().equals("")
                || cuentaRetencionValor.getDescripcion().equals("")
                || cuentaRetencionRC_IVAValor.getDescripcion().equals("")
                || cuentaRetencionExteriorValor.getDescripcion().equals("")
                || cuentaRetencionSectorIndustrialValor.getDescripcion().equals(""));
    }

    //<editor-fold defaultstate="collapsed" desc="Autor: Henrry Guzman Fecha:24 Junio 2013">
    public String adicionar1() {
        if (selected.getIdEntidad() != null) {
            setInSessionIdEntidad(selected.getIdEntidad());
            setInSessionIdEntidad2(null);
            return "planCuentasAdicionar";
        } else {
            MessageUtils.addErrorMessage("Debe seleccionar una cuenta.");
        }
        return null;
    }

    public String adicionarCentroDeCosto() {
        if (selected != null) {
            setInSessionIdCentroDeCosto(selected.getIdEntidad());
            return "centroDeCostoAdicionar";
        }
        return null;
    }

    public String modificarCentroDeCosto() {
        if (selected != null) {
            setInSessionIdCentroDeCosto(selected.getIdEntidad());
            return "centroDeCostoModificar";
        }
        return null;
    }

    public String removeCntCentrosCosto_action() throws Exception {
        if (selected != null) {
            super.setRemoveValues(selected);
            String[] respuesta = cntEntidadesService.verificaYEliminaCentrosDeCosto(selected);
            if (respuesta[0].equals("I")) {
                MessageUtils.addInfoMessage(respuesta[1]);
            } else {
                MessageUtils.addErrorMessage(respuesta[1]);
            }
            return "planCuentas";
        } else {
            MessageUtils.addErrorMessage("Debe seleccionar un centro de costo para eliminar.");
        }
        return null;
    }

    public void cargaParametorsAutomaticosMigracionBaseNueva() throws Exception {
        cntEntidadesService.persistMasivoCuentasNivelDosAndUnoBaseMigrada(getLoginSession());
        MessageUtils.addInfoMessage("Se cargo de manera automatica los parametros automaticos de todas las cuentas con exito.");
    }

    public String modificar1() {
        if (selected.getIdEntidad() != null) {
            setInSessionIdEntidad2(selected.getIdEntidad());
            setInSessionIdEntidad(null);
            setInSessionIntAuxiliar(selected.getNivel()); //Nivel cuenta para modificar
            return "planCuentasModificar";
        } else {
            MessageUtils.addErrorMessage("Debe seleccionar una cuenta.");
            return null;
        }
    }

    public String cancelarAsignacionCentroDeCosto() {
        //        setInSessionIdEntidad(null);
        return "planCuentasAdicionar";
    }

    //// Codigo nurvo///////
    //<editor-fold defaultstate="collapsed" desc="Autor: Henrry Guzman Fecha:24 Junio 2013">
    public List<CntEntidad> getcntEntidadHermanosList() {
        return cntEntidadHermanosList;
    }

    public void setcntEntidadHermanosList(List<CntEntidad> cntEntidadHermanosList) {
        this.cntEntidadHermanosList = cntEntidadHermanosList;
    }

    public List<CntEntidad> getcntEntidadHijosList() {
        return cntEntidadHijosList;
    }

    public void setcntEntidadHijosList(List<CntEntidad> cntEntidadHijosList) {
        this.cntEntidadHijosList = cntEntidadHijosList;
    }

    public List<CntEntidad> cntEntidadListaHermanos() {
        try {
            if (selected != null) {
                cntEntidadHermanosList = cntEntidadesService.obtieneHermanosPorCntEntidad(selected);
            }
        } catch (Exception e) {
        }
        return cntEntidadHermanosList;
    }

    public void planCuentasGeneral(CntEntidad cntEntidad) {
        selected = cntEntidad;
    }

    public List<CntEntidad> cntEntidadListaHijos() {
        try {
            if (selected != null) {
                cntEntidadHijosList = cntEntidadesService.obtieneHijosPorCntObjetos(selected);
            }
        } catch (Exception e) {
        }
        return cntEntidadHijosList;
    }

    public CntEntidad getSelectedPlanCuentasHermanos() {
        return selectedPlanCuentasHermanos;
    }

    public void setSelectedPlanCuentasHermanos(CntEntidad selectedPlanCuentasHermanos) {
        this.selectedPlanCuentasHermanos = selectedPlanCuentasHermanos;
    }

    public CntEntidad getSelectedPlanCuentasHijos() {
        return selectedPlanCuentasHijos;
    }

    public void setSelectedPlanCuentasHijos(CntEntidad selectedPlanCuentasHijos) {
        this.selectedPlanCuentasHijos = selectedPlanCuentasHijos;
    }

    public void obtieneObjetoPlanCuentas(ValueChangeEvent e) {
        activaBotonSeleccion = selected.getNivel() != 1;
        //        setInSessionIdEntidad3(selected.getIdEntidad()); //Borrar solo para la prueba de don Javi
        //        cntEntidadListaHermanos();
    }

    public void obtieneObjetoPlanCuentasHermano(ValueChangeEvent e) {
        selected = selectedPlanCuentasHermanos;
        //        setInSessionIdEntidad3(selected.getIdEntidad());//Borrar solo para la prueba de don Javi
    }

    public void obtieneObjetoPlanCuentasHijo(ValueChangeEvent e) {
        selectedPlanCuentasHermanos = selectedPlanCuentasHijos;
        selected = selectedPlanCuentasHijos;
        //        setInSessionIdEntidad3(selected.getIdEntidad());//Borrar solo para la prueba de don Javi
    }

    public CntEntidad getSelected() {
        return selected;
    }

    public void setSelected(CntEntidad selected) {
        this.selected = selected;
    }

    //</editor-fold>
    public String insertaPuntosAutomatio(CntEntidad cntEntidad) {
        return cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad);
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

    //Metodo par limpiar el filtrado y volver a mostrar la lista original con el buscador vacio.
    //Autor:Henrry Guzman
    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public List<CntEntidad> getCntEntidadList() {
        return cntEntidadList;
    }

    public void setCntEntidadList(List<CntEntidad> cntEntidadList) {
        this.cntEntidadList = cntEntidadList;
    }

    public List<CntEntidad> getCntEntidadHermanosList() {
        return cntEntidadHermanosList;
    }

    public void setCntEntidadHermanosList(List<CntEntidad> cntEntidadHermanosList) {
        this.cntEntidadHermanosList = cntEntidadHermanosList;
    }

    public List<CntEntidad> getCntEntidadHijosList() {
        return cntEntidadHijosList;
    }

    public void setCntEntidadHijosList(List<CntEntidad> cntEntidadHijosList) {
        this.cntEntidadHijosList = cntEntidadHijosList;
    }

    public Boolean getActivaBotonSeleccion() {
        return activaBotonSeleccion;
    }

    public void setActivaBotonSeleccion(Boolean activaBotonSeleccion) {
        this.activaBotonSeleccion = activaBotonSeleccion;
    }

    public CntEntidad getCuentaDebitoFiscalValor() {
        return cuentaDebitoFiscalValor;
    }

    public void setCuentaDebitoFiscalValor(CntEntidad cuentaDebitoFiscalValor) {
        this.cuentaDebitoFiscalValor = cuentaDebitoFiscalValor;
    }

    public CntEntidad getCuentaCreditoFiscalValor() {
        return cuentaCreditoFiscalValor;
    }

    public void setCuentaCreditoFiscalValor(CntEntidad cuentaCreditoFiscalValor) {
        this.cuentaCreditoFiscalValor = cuentaCreditoFiscalValor;
    }

    public CntEntidad getCuentaCreditoFiscalNoDeducibleValor() {
        return cuentaCreditoFiscalNoDeducibleValor;
    }

    public void setCuentaCreditoFiscalNoDeducibleValor(CntEntidad cuentaCreditoFiscalNoDeducibleValor) {
        this.cuentaCreditoFiscalNoDeducibleValor = cuentaCreditoFiscalNoDeducibleValor;
    }

    public CntEntidad getCuentaCreditoFiscalTransitorioValor() {
        return cuentaCreditoFiscalTransitorioValor;
    }

    public void setCuentaCreditoFiscalTransitorioValor(CntEntidad cuentaCreditoFiscalTransitorioValor) {
        this.cuentaCreditoFiscalTransitorioValor = cuentaCreditoFiscalTransitorioValor;
    }

    public CntEntidad getCuentaImpuestosTransaccionesValor() {
        return cuentaImpuestosTransaccionesValor;
    }

    public void setCuentaImpuestosTransaccionesValor(CntEntidad cuentaImpuestosTransaccionesValor) {
        this.cuentaImpuestosTransaccionesValor = cuentaImpuestosTransaccionesValor;
    }

    public CntEntidad getCuentaGastosImpuestosTransaccionesValor() {
        return cuentaGastosImpuestosTransaccionesValor;
    }

    public void setCuentaGastosImpuestosTransaccionesValor(CntEntidad cuentaGastosImpuestosTransaccionesValor) {
        this.cuentaGastosImpuestosTransaccionesValor = cuentaGastosImpuestosTransaccionesValor;
    }

    public CntEntidad getCuentaDebitoFiscalTransitorioValor() {
        return cuentaDebitoFiscalTransitorioValor;
    }

    public void setCuentaDebitoFiscalTransitorioValor(CntEntidad cuentaDebitoFiscalTransitorioValor) {
        this.cuentaDebitoFiscalTransitorioValor = cuentaDebitoFiscalTransitorioValor;
    }

    public String getTipoCuentaSelecionada() {
        return tipoCuentaSelecionada;
    }

    public void setTipoCuentaSelecionada(String tipoCuentaSelecionada) {
        this.tipoCuentaSelecionada = tipoCuentaSelecionada;
    }

    public void cargaValorCuentaDebitoFiscal(ValueChangeEvent e) {
            tipoCuentaSelecionada = EnumParametrosDeGestion_ComprasAndVentas.CUENTA_DE_DEBITO_FISCAL.getCodigo();
    }

    public void cargaValorCuentaCreditoFiscal(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_ComprasAndVentas.CUENTA_DE_CREDITO_FISCAL.getCodigo();
    }

    public void cargaValorCuentaCreditoFiscalNoDeducible(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_ComprasAndVentas.CUENTA_DE_CREDITO_FISCAL_NO_DEDUCIBLE.getCodigo();
    }

    public void cargaValorCuentaCreditoFiscalTransitorio(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_ComprasAndVentas.CUENTA_DE_CREDITO_FISCAL_TRANSITORIO.getCodigo();
    }

    public void cargaValorCuentaImpuestosTransacciones(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_ComprasAndVentas.CUENTA_IMPUESTOS_A_LAS_TRANSACCIONES.getCodigo();
    }

    public void cargaValorCuentaGastosImpuestosTransacciones(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_ComprasAndVentas.CUENTA_GASTO_IMPUESTOS_A_LAS_TRANSACCIONES.getCodigo();
    }

    public void cargaValorCuentaDebitoFiscalTransitorio(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_ComprasAndVentas.CUENTA_DEBITO_FISCAL_TRANSITORIO.getCodigo();
    }

    public void cargaValorCuentaInflacionResultadoExposicionInflacion(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_CuentasDeAjuste.INFLACION_RESUL_P_EXPOSICION_A_LA_INFLACION.getCodigo();
    }

    public void cargaValorCuentaAjusteCorreccionMonetaria(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_CuentasDeAjuste.AJUSTE_POR_CORRECCION_MONETARIA.getCodigo();
    }

    public void cargaValorCuentaDiferenciaCambio(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_CuentasDeAjuste.DIFERENCIA_DE_CAMBIO.getCodigo();
    }

    public void cargaValorCuentaAjusteCapital(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_CuentasDeAjuste.AJUSTE_DE_CAPITAL.getCodigo();
    }

    public void cargaValorCuentaAjusteReservasPatrimoniales(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_CuentasDeAjuste.AJUSTE_DE_RESERVAS_PATRIMONIALES.getCodigo();
    }

    public void cargaValorCuentaRetencionServicio(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_RetencionesIU.CUENTA_IUE_RETENCIONES_POR_SERVICIOS.getCodigo();
    }

    public void cargaValorCuentaRetencionBienes(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_RetencionesIU.CUENTA_IUE_RETENCIONES_POR_BIENES.getCodigo();
    }

    public void cargaValorCuentaRetencion(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_RetencionesIU.CUENTA_DE_IT_DE_RETENCIONES.getCodigo();
    }

    public void cargaValorCuentaRetencionRC_IVA(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_RetencionesIU.CUENTA_RETENCION_RC_IVA.getCodigo();
    }

    public void cargaValorCuentaRetencionExterior(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_RetencionesIU.CUENTA_RETENCIONES_AL_EXTERIOR.getCodigo();
    }

    public void cargaValorCuentaRetencionSectorIndustrial(ValueChangeEvent e) {
        tipoCuentaSelecionada = EnumParametrosDeGestion_RetencionesIU.CUENTA_RETENCION_IUE_IT_SECTOR_INDUSTRIAL.getCodigo();
    }

    public CntEntidad getCuentaInflacionResultadoExposicionInflacionValor() {
        return cuentaInflacionResultadoExposicionInflacionValor;
    }

    public void setCuentaInflacionResultadoExposicionInflacionValor(CntEntidad cuentaInflacionResultadoExposicionInflacionValor) {
        this.cuentaInflacionResultadoExposicionInflacionValor = cuentaInflacionResultadoExposicionInflacionValor;
    }

    public CntEntidad getCuentaAjusteCorreccionMonetariaValor() {
        return cuentaAjusteCorreccionMonetariaValor;
    }

    public void setCuentaAjusteCorreccionMonetariaValor(CntEntidad cuentaAjusteCorreccionMonetariaValor) {
        this.cuentaAjusteCorreccionMonetariaValor = cuentaAjusteCorreccionMonetariaValor;
    }

    public CntEntidad getCuentaDiferenciaCambioValor() {
        return cuentaDiferenciaCambioValor;
    }

    public void setCuentaDiferenciaCambioValor(CntEntidad cuentaDiferenciaCambioValor) {
        this.cuentaDiferenciaCambioValor = cuentaDiferenciaCambioValor;
    }

    public CntEntidad getCuentaAjusteCapitalValor() {
        return cuentaAjusteCapitalValor;
    }

    public void setCuentaAjusteCapitalValor(CntEntidad cuentaAjusteCapitalValor) {
        this.cuentaAjusteCapitalValor = cuentaAjusteCapitalValor;
    }

    public CntEntidad getCuentaAjusteReservasPatrimonialesValor() {
        return cuentaAjusteReservasPatrimonialesValor;
    }

    public void setCuentaAjusteReservasPatrimonialesValor(CntEntidad cuentaAjusteReservasPatrimonialesValor) {
        this.cuentaAjusteReservasPatrimonialesValor = cuentaAjusteReservasPatrimonialesValor;
    }

    public CntEntidad getCuentaRetencionServicioValor() {
        return cuentaRetencionServicioValor;
    }

    public void setCuentaRetencionServicioValor(CntEntidad cuentaRetencionServicioValor) {
        this.cuentaRetencionServicioValor = cuentaRetencionServicioValor;
    }

    public CntEntidad getCuentaRetencionBienesValor() {
        return cuentaRetencionBienesValor;
    }

    public void setCuentaRetencionBienesValor(CntEntidad cuentaRetencionBienesValor) {
        this.cuentaRetencionBienesValor = cuentaRetencionBienesValor;
    }

    public CntEntidad getCuentaRetencionValor() {
        return cuentaRetencionValor;
    }

    public void setCuentaRetencionValor(CntEntidad cuentaRetencionValor) {
        this.cuentaRetencionValor = cuentaRetencionValor;
    }

    public CntEntidad getCuentaRetencionRC_IVAValor() {
        return cuentaRetencionRC_IVAValor;
    }

    public void setCuentaRetencionRC_IVAValor(CntEntidad cuentaRetencionRC_IVAValor) {
        this.cuentaRetencionRC_IVAValor = cuentaRetencionRC_IVAValor;
    }

    public CntEntidad getCuentaRetencionExteriorValor() {
        return cuentaRetencionExteriorValor;
    }

    public void setCuentaRetencionExteriorValor(CntEntidad cuentaRetencionExteriorValor) {
        this.cuentaRetencionExteriorValor = cuentaRetencionExteriorValor;
    }

    public CntEntidad getCuentaRetencionSectorIndustrialValor() {
        return cuentaRetencionSectorIndustrialValor;
    }

    public void setCuentaRetencionSectorIndustrialValor(CntEntidad cuentaRetencionSectorIndustrialValor) {
        this.cuentaRetencionSectorIndustrialValor = cuentaRetencionSectorIndustrialValor;
    }
    //</editor-fold>

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public List<CntEntidad> getFilteredcntEntidad() {
        return filteredcntEntidad;
    }

    public void setFilteredcntEntidad(List<CntEntidad> filteredcntEntidad) {
        this.filteredcntEntidad = filteredcntEntidad;
    }

    public Boolean getEleccionTodos() {
        return eleccionTodos;
    }

    public void setEleccionTodos(Boolean eleccionTodos) {
        this.eleccionTodos = eleccionTodos;
    }
    
    public void listaTodo() {
        if (eleccionTodos) {
            color = "para-elegidas";
            listaMomentanea = filteredcntEntidad;
            filteredcntEntidad = null;
        } else {
            filteredcntEntidad = listaMomentanea;
            listaMomentanea = null;
            color = "para-filas";
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<CntEntidad> getListaMomentanea() {
        return listaMomentanea;
    }

    public void setListaMomentanea(List<CntEntidad> listaMomentanea) {
        this.listaMomentanea = listaMomentanea;
    }
    
    


}
