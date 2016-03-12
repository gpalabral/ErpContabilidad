package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumConfirmacion;
import com.bap.erp.modelo.enums.EnumEmpresa_Varios;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresPorCuentaService;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import com.bap.erp.modelo.servicios.cnt.CntParametroAutomaticoService;
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

/**
 *
 * @author Henrry Guzman
 */
@ManagedBean(name = "cntPlanCuentasBacking")
@ViewScoped
public class CntPlanCuentasBacking extends AbstractManagedBean implements Serializable {
    
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntMascarasService}")
    private CntMascarasService cntMascarasService;
    @ManagedProperty(value = "#{cntParametroAutomaticoService}")
    private CntParametroAutomaticoService cntParametroAutomaticoService;
    @ManagedProperty(value = "#{cntAuxiliaresPorCuentaService}")
    private CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;
    private List<CntEntidad> cntEntidadList;
    private CntEntidad selectedPlanCuentas;
    private List<CntEntidad> filteredcntEntidad;
    private String valor;
    private String color = null;
    private Boolean eleccionTodos = false;
    private List<CntEntidad> listaMomentanea = new ArrayList<CntEntidad>();
    private Boolean activarBotonEliminarCuenta;
    //<editor-fold defaultstate="collapsed" desc="Autor: Henrry Fecha: 24 Junio 2013">
    private List<CntEntidad> cntEntidadHermanosList = new ArrayList<CntEntidad>();
    private List<CntEntidad> cntEntidadHijosList = new ArrayList<CntEntidad>();
    private CntEntidad selectedPlanCuentasHermanos;
    private CntEntidad selectedPlanCuentasHijos;
    private List<CntEntidad> filteredcntEntidadHermanos;
    private List<CntEntidad> filteredcntEntidadHijos;
    private CntEntidad selectedPlanCuentasHermanosAux;
    private CntEntidad selectedPlanCuentasHijosAux;
    private CntEntidad selectedPlanCuentasAux;
    private CntEntidad selected = new CntEntidad();
    private String cuentaActivada;
    private String tipoCuenta;
    private Boolean activaBotonesCentrosDeCosto;
    private Boolean activaCargaParametrosAutomaticos;
    //</editor-fold>    
    private CntComprobante cntComprobante;
    ////////////////////

    private CntDetalleComprobante cntDetalleComprobante;
    private String tipoRetencion;
    
    private List<CntEntidad> cntEntidadHermanosCCList = new ArrayList<CntEntidad>();
    private List<CntEntidad> cntEntidadHijosCCList = new ArrayList<CntEntidad>();
    private CntEntidad planCuentaVerificar;
    private Boolean activaBotonComparaEntidad = false;
    private Boolean activaBotonSelecionaEntidad = false;
    
    public CntPlanCuentasBacking() {
    }
    
    @PostConstruct
    void initCntPlanCuentasBacking() {
        try {
            if (super.getFromSessionIdEntidad() != null) {
                selectedPlanCuentas = (CntEntidad) cntEntidadesService.find(CntEntidad.class, super.getFromSessionIdEntidad());
                selected = selectedPlanCuentas;
            }
            if (super.getFromSessionIdEntidad2() != null) {
                selectedPlanCuentas = (CntEntidad) cntEntidadesService.find(CntEntidad.class, super.getFromSessionIdEntidad2());
                selected = selectedPlanCuentas;
            }
            if (super.getFromSessionTipoDeGrupoNivel() != null) {
                tipoCuenta = super.getFromSessionTipoDeGrupoNivel();
                cuentaActivada = super.getFromSessionTipoDeGrupoNivel();
                activaBotonesCentrosDeCosto = !super.getFromSessionTipoDeGrupoNivel().equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
            } else {
                tipoCuenta = EnumGruposNivel.PLAN_CUENTAS.getCodigo();
                cuentaActivada = EnumGruposNivel.PLAN_CUENTAS.getCodigo();
                activaBotonesCentrosDeCosto = false;
            }
            if (getFromSessionIdEntidadParaMayor() != null) {
                selectedPlanCuentas = (CntEntidad) cntEntidadesService.find(CntEntidad.class, super.getFromSessionIdEntidadParaMayor());
                selected = selectedPlanCuentas;
                setInSessionIdEntidadParaMayor(null);
            }
            activaCargaParametrosAutomaticos = cntEntidadesService.verificaEntidadTieneParametrosAutomaticos();
            if (super.getFromSessionIdComprobante() != null) {
                cntComprobante = cntComprobantesService.find(CntComprobante.class, super.getFromSessionIdComprobante());
                
                if (super.getFromSessionIdEntidad3() != null) {
                    planCuentaVerificar = (CntEntidad) cntEntidadesService.find(CntEntidad.class, super.getFromSessionIdEntidad3());
//                    setInSessionIdEntidad3(null);
                    activaBotonComparaEntidad = true;
                    activaBotonSelecionaEntidad = false;
                } else {
                    activaBotonSelecionaEntidad = true;
                }
            } else {
                activaBotonSelecionaEntidad = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public String deletecntEntidad_action() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminar:", "A un esta en construccion ..Gracias por su comprension!!"));
        return null;
    }
    
    public List<CntEntidad> cntObjetosPorGrupoNivelList(Boolean activaRetenciones) {
        try {
            if (cntEntidadList == null) {
                if (activaRetenciones) {
                    cntEntidadList = cntEntidadesService.listaCuentasParaRetencionesAndGrossingUp();
                } else {
                    cntEntidadList = cntEntidadesService.cntEntidadesPorGrupoNivelList(cuentaActivada);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    
    public List<CntEntidad> getFilteredcntEntidad() {
        return filteredcntEntidad;
    }
    
    public void setFilteredcntEntidad(List<CntEntidad> filteredcntEntidad) {
        this.filteredcntEntidad = filteredcntEntidad;
    }
    
    public CntEntidad getSelectedPlanCuentas() {
        return selectedPlanCuentas;
    }
    
    public void setSelectedPlanCuentas(CntEntidad selectedPlanCuentas) {
        this.selectedPlanCuentas = selectedPlanCuentas;
    }

    //**********Para elegir cuentas de ajuste**********//
    public String seleccionar() {
        if (selected.getIdEntidad() != null) {
            if (selected.getNivel() == 1) {
                setInSessionIdEntidadSeleccion(selected.getIdEntidad());
                setInSessionIdComprobante(cntComprobante.getIdComprobante());
                return "formularioComprobante";
            } else {
                MessageUtils.addErrorMessage("La cuenta debe ser de último nivel.");
            }
        } else {
            MessageUtils.addErrorMessage("No selecciono ninguna cuenta.");
        }
        return null;
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
        if (selected != null && cntMascarasService.obtieneMascarasPorGrrupoNivel(EnumGruposNivel.CENTROS_COSTOS.getCodigo()) != null) {
            setInSessionIdCentroDeCosto(selected.getIdEntidad());
            return "centroDeCostoAdicionar";
        } else {
            MessageUtils.addErrorMessage("Debe crear una mascara para los Centros de Costo.");
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
            return "centrosDeCosto";
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
    
    public void eliminar() {
        if (selected.getIdEntidad() != null) {
            setInSessionIdEntidad2(selected.getIdEntidad());
            setInSessionIdEntidad(null);
            setInSessionIntAuxiliar(selected.getNivel()); //Nivel cuenta para modificar
            try {
                if (!cntAuxiliaresPorCuentaService.verificaRelacionCntEntidadConCntAuxiliarPorCuenta(selected) && !cntDetalleComprobanteService.verificaRelacionCntEntidadConCntDetalleComprobante(selected) && !parParametricasService.verificaSiUnaCuentaEstaEnParametrosDeGestion(selected)) {
                    super.setRemoveValues(selected);
                    cntEntidadesService.eliminaCuentaConSuParametrosAutomatico(selected);
                    cntEntidadList = cntEntidadesService.cntEntidadesPorGrupoNivelList(cuentaActivada);
                    MessageUtils.addInfoMessage("Se elimino la cuenta " + selected.getDescripcion() + " exitosamente.");
                } else {
                    MessageUtils.addErrorMessage("La cuenta" + selected.getDescripcion() + " no se puede eliminar .");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else {
            MessageUtils.addErrorMessage("Debe seleccionar una cuenta.");
        }
    }
    
    public String cancelar() {
        setInSessionIdEntidad(null);
        setCntObjetosSession(null);
        return "planCuentas";
    }
    
    public void activaCuenta(ValueChangeEvent e) throws Exception {
        if (tipoCuenta.equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo())) {
            cuentaActivada = EnumGruposNivel.PLAN_CUENTAS.getCodigo();
            setInSessionTipoDeGrupoNivel(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
        } else {
            if (tipoCuenta.equals(EnumGruposNivel.CENTROS_COSTOS.getCodigo())) {
                cuentaActivada = EnumGruposNivel.CENTROS_COSTOS.getCodigo();
                activaBotonesCentrosDeCosto = true;
                setInSessionTipoDeGrupoNivel(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
            }
        }
        cntEntidadList = cntEntidadesService.cntEntidadesPorGrupoNivelList(cuentaActivada);
    }
    
    public String cancelarAsignacionCentroDeCosto() {
//        setInSessionIdEntidad(null);
        return "planCuentasAdicionar";
    }
    
    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    //// Codigo nurvo///////
    public String getValor() {
        return valor;
    }
    
    public void setValor(String valor) {
        this.valor = valor;
    }
    
    public void listaTodo() {
        if (eleccionTodos) {
            color = "para-elegidas";
            listaMomentanea = getFromSessionListaDeCuentasFiltrada();
//            listaMomentanea = filteredcntEntidad;
            setInSessionListaDeCuentasFiltrada(null);
//            filteredcntEntidad = null;
        } else {
            filteredcntEntidad = getFromSessionListaDeCuentasFiltrada();
//            filteredcntEntidad = listaMomentanea;
            setInSessionListaDeCuentasFiltrada(null);
//            listaMomentanea = null;
            color = "para-filas";
        }
    }
    
    public String verMayorCuentaElegida() {
        if (selectedPlanCuentas == null) {
            MessageUtils.addErrorMessage("Debe Seleccionar una Cuenta para ver su Mayor");
            return null;
        } else {
            setInSessionIdEntidadParaMayor(selectedPlanCuentas.getIdEntidad());
            return "libroMayor";
        }
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public Boolean getEleccionTodos() {
        return eleccionTodos;
    }
    
    public void setEleccionTodos(Boolean eleccionTodos) {
        this.eleccionTodos = eleccionTodos;
    }
    
    public List<CntEntidad> getListaMomentanea() {
        return listaMomentanea;
    }
    
    public void setListaMomentanea(List<CntEntidad> listaMomentanea) {
        this.listaMomentanea = listaMomentanea;
    }
    
    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

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
    
    public List<CntEntidad> cntEntidadListaHermanosCC() {
        try {
            if (selectedPlanCuentas != null) {
                cntEntidadHermanosCCList = cntEntidadesService.obtieneHermanosPorCntEntidadCC(selectedPlanCuentas);
            }
        } catch (Exception e) {
        }
        return cntEntidadHermanosCCList;
    }
    
    public List<CntEntidad> cntEntidadListaHijosCC() {
        try {
            if (selectedPlanCuentasHermanos != null) {
                cntEntidadHijosCCList = cntEntidadesService.obtieneHijosPorCntObjetosCC(selectedPlanCuentasHermanos);
            }
        } catch (Exception e) {
        }
        return cntEntidadHijosCCList;
    }
    
    public String seleccionaComprobante() {
        if (selectedPlanCuentas.getIdEntidad() != null) {
            if (selectedPlanCuentas.getNivel() == 1) {
                setInSessionIdComprobante(cntComprobante.getIdComprobante());
                if (getFromSessionTipoRetencionOGrossing().isEmpty() && getFromSessionIdDetalleComprobante() == null) {
                    setInSessionIdEntidad(selectedPlanCuentas.getIdEntidad());
                    return "detalleComprobante";
                } else {
                    cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
                    tipoRetencion = getFromSessionTipoRetencionOGrossing();
                    setInSessionIdDetalleComprobante(null);
                    setInSessionIdEntidad(null);
                    return activaRetencionesProcesos();
                }
            } else {
                MessageUtils.addErrorMessage("La cuenta debe ser de último nivel.");
            }
        } else {
            MessageUtils.addErrorMessage("No selecciono ninguna cuenta.");
        }
        return null;
    }
    
    public String activaRetencionesProcesos() {
        try {
            setInSessionActivaDesactivaBotonesDetalle(false);
            if (cntDetalleComprobante.getIdDetalleComprobante() == null) {
                cntDetalleComprobante = cntDetalleComprobanteService.guardaRetencionGrossing(cntDetalleComprobante, selectedPlanCuentas, tipoRetencion);
            } else {
                setMergeValues(cntDetalleComprobante);
                cntDetalleComprobante = cntDetalleComprobanteService.guardaRetencionGrossingModifica(cntDetalleComprobante, selectedPlanCuentas, tipoRetencion);
            }
            if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.CENTROS_DE_COSTO.getCodigo()).getValor()).equals("1")) {
//                if (cntEntidadesService.verificaEntidadCentroCosto(cntDetalleComprobante.getCntEntidad())) {
//                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
//                    return "distribucionDeCentroDeCosto";
//                }
                if (cntDetalleComprobante.getCntEntidad().getHabilitaCentroCosto().equals("S")) {
                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                    return "distribucionDeCentroDeCosto";
                }
            }
            if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.AUXILIAR.getCodigo()).getValor()).equals("1")) {
                if (cntDetalleComprobante.getCntEntidad().getTieneAuxiliar().equals(EnumConfirmacion.SI.getCodigo())) {
                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                    if (cntAuxiliaresPorCuentaService.verificaRelacionCntEntidadConCntAuxiliarPorCuenta(cntDetalleComprobante.getCntEntidad())) {
                        return "asignacionDeAuxiliaresADetalleComprobante";
                    }
                }
            }
            if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.PROYECTO.getCodigo()).getValor()).equals("1")) {
                setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                return "asignacionDeProyectosADetalleComprobante";
            } else {
                return "detalleComprobante";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public String vuelveAComprobantes() {
        setInSessionIdComprobante(cntComprobante.getIdComprobante());
        setInSessionIdEntidad(null);
        return "detalleComprobante";
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
    
    public List<CntEntidad> getFilteredcntEntidadHermanos() {
        return filteredcntEntidadHermanos;
    }
    
    public void setFilteredcntEntidadHermanos(List<CntEntidad> filteredcntEntidadHermanos) {
        this.filteredcntEntidadHermanos = filteredcntEntidadHermanos;
    }
    
    public List<CntEntidad> getFilteredcntEntidadHijos() {
        return filteredcntEntidadHijos;
    }
    
    public void setFilteredcntEntidadHijos(List<CntEntidad> filteredcntEntidadHijos) {
        this.filteredcntEntidadHijos = filteredcntEntidadHijos;
    }
    
    public void obtieneObjetoPlanCuentas(ValueChangeEvent e) {
        selected = selectedPlanCuentas;
        activarBotonEliminarCuenta = selected.getNivel() == 1;
//        setInSessionIdEntidad3(selected.getIdEntidad()); //Borrar solo para la prueba de don Javi
//        cntEntidadListaHermanos();
    }
    
    public void obtieneObjetoPlanCuentasCC(ValueChangeEvent e) {
        selected = selectedPlanCuentas;
        activarBotonEliminarCuenta = selected.getNivel() == 1;
//        setInSessionIdEntidad3(selected.getIdEntidad()); //Borrar solo para la prueba de don Javi
        cntEntidadListaHermanosCC();
    }
    
    public void obtieneObjetoPlanCuentasHermano(ValueChangeEvent e) {
        selectedPlanCuentas = selectedPlanCuentasHermanos;
        selected = selectedPlanCuentasHermanos;
//        setInSessionIdEntidad3(selected.getIdEntidad());//Borrar solo para la prueba de don Javi
    }
    
    public void obtieneObjetoPlanCuentasHermanoCC(ValueChangeEvent e) {
        selectedPlanCuentas = selectedPlanCuentasHermanos;
        selected = selectedPlanCuentasHermanos;
        cntEntidadListaHijosCC();
    }
    
    public void obtieneObjetoPlanCuentasHijo(ValueChangeEvent e) {
        selectedPlanCuentas = selectedPlanCuentasHijos;
        selectedPlanCuentasHermanos = selectedPlanCuentasHijos;
        selected = selectedPlanCuentasHijos;
//        setInSessionIdEntidad3(selected.getIdEntidad());//Borrar solo para la prueba de don Javi
    }
    
    public CntEntidad getSelectedPlanCuentasHermanosAux() {
        return selectedPlanCuentasHermanosAux;
    }
    
    public void setSelectedPlanCuentasHermanosAux(CntEntidad selectedPlanCuentasHermanosAux) {
        this.selectedPlanCuentasHermanosAux = selectedPlanCuentasHermanosAux;
    }
    
    public CntEntidad getSelectedPlanCuentasAux() {
        return selectedPlanCuentasAux;
    }
    
    public void setSelectedPlanCuentasAux(CntEntidad selectedPlanCuentasAux) {
        this.selectedPlanCuentasAux = selectedPlanCuentasAux;
    }
    
    public CntEntidad getSelectedPlanCuentasHijosAux() {
        return selectedPlanCuentasHijosAux;
    }
    
    public void setSelectedPlanCuentasHijosAux(CntEntidad selectedPlanCuentasHijosAux) {
        this.selectedPlanCuentasHijosAux = selectedPlanCuentasHijosAux;
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
    
    public String getTipoCuenta() {
        return tipoCuenta;
    }
    
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    
    public String getCuentaActivada() {
        return cuentaActivada;
    }
    
    public void setCuentaActivada(String cuentaActivada) {
        this.cuentaActivada = cuentaActivada;
    }
    
    public Boolean getActivaBotonesCentrosDeCosto() {
        return activaBotonesCentrosDeCosto;
    }
    
    public void setActivaBotonesCentrosDeCosto(Boolean activaBotonesCentrosDeCosto) {
        this.activaBotonesCentrosDeCosto = activaBotonesCentrosDeCosto;
    }
    
    public String muestraTituloDeVenta(String tipoDeGrupoNivel) {
        return tipoDeGrupoNivel.equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo()) ? "PLAN DE CUENTAS" : "CENTROS DE COSTO";
    }
    
    public Boolean getActivaCargaParametrosAutomaticos() {
        return activaCargaParametrosAutomaticos;
    }
    
    public void setActivaCargaParametrosAutomaticos(Boolean activaCargaParametrosAutomaticos) {
        this.activaCargaParametrosAutomaticos = activaCargaParametrosAutomaticos;
    }
    
    public String tipoDeCambio() {
        limpiarVariablesSession();
        return "tipoCambioList";
    }
    
    public String comprobantesList() {
        limpiarVariablesSession();
        return "comprobantesList";
    }

    //Metodo par limpiar el filtrado y volver a mostrar la lista original con el buscador vacio.
    //Autor:Henrry Guzman
    public void limpiaFiltradoParaVolverALLamarElComponente(ValueChangeEvent e) throws Exception {
        valor = "";
        filteredcntEntidad = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
    }
    
    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }
    
    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }
    
    public CntMascarasService getCntMascarasService() {
        return cntMascarasService;
    }
    
    public void setCntMascarasService(CntMascarasService cntMascarasService) {
        this.cntMascarasService = cntMascarasService;
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

    //<editor-fold defaultstate="collapsed" desc="Método para ver mayor 03/12/2014">
    //  Para ver mayor de una cuenta seleccionada  Jacqueline Carvajal 03/12/2014
    public String verMayorCntaElegida() {
        if (selected.getIdEntidad() != null) {
            setInSessionIdEntidad2(selected.getIdEntidad());
            return "libro_Mayor";
        } else {
            MessageUtils.addErrorMessage("Debe Seleccionar una Cuenta Último nivel para ver su Mayor ...");
            return null;
        }
    }
    //</editor-fold>

    public CntParametroAutomaticoService getCntParametroAutomaticoService() {
        return cntParametroAutomaticoService;
    }
    
    public void setCntParametroAutomaticoService(CntParametroAutomaticoService cntParametroAutomaticoService) {
        this.cntParametroAutomaticoService = cntParametroAutomaticoService;
    }
    
    public CntAuxiliaresPorCuentaService getCntAuxiliaresPorCuentaService() {
        return cntAuxiliaresPorCuentaService;
    }
    
    public void setCntAuxiliaresPorCuentaService(CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService) {
        this.cntAuxiliaresPorCuentaService = cntAuxiliaresPorCuentaService;
    }
    
    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }
    
    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }
    
    public Boolean getActivarBotonEliminarCuenta() {
        return activarBotonEliminarCuenta;
    }
    
    public void setActivarBotonEliminarCuenta(Boolean activarBotonEliminarCuenta) {
        this.activarBotonEliminarCuenta = activarBotonEliminarCuenta;
    }
    
    public CntComprobantesService getCntComprobantesService() {
        return cntComprobantesService;
    }
    
    public void setCntComprobantesService(CntComprobantesService cntComprobantesService) {
        this.cntComprobantesService = cntComprobantesService;
    }
    
    public CntComprobante getCntComprobante() {
        return cntComprobante;
    }
    
    public void setCntComprobante(CntComprobante cntComprobante) {
        this.cntComprobante = cntComprobante;
    }
    
    public List<CntEntidad> getCntEntidadHermanosCCList() {
        return cntEntidadHermanosCCList;
    }
    
    public void setCntEntidadHermanosCCList(List<CntEntidad> cntEntidadHermanosCCList) {
        this.cntEntidadHermanosCCList = cntEntidadHermanosCCList;
    }
    
    public List<CntEntidad> getCntEntidadHijosCCList() {
        return cntEntidadHijosCCList;
    }
    
    public void setCntEntidadHijosCCList(List<CntEntidad> cntEntidadHijosCCList) {
        this.cntEntidadHijosCCList = cntEntidadHijosCCList;
    }
    
    public Boolean getActivaBotonComparaEntidad() {
        return activaBotonComparaEntidad;
    }
    
    public void setActivaBotonComparaEntidad(Boolean activaBotonComparaEntidad) {
        this.activaBotonComparaEntidad = activaBotonComparaEntidad;
    }
    
    public Boolean getActivaBotonSelecionaEntidad() {
        return activaBotonSelecionaEntidad;
    }
    
    public void setActivaBotonSelecionaEntidad(Boolean activaBotonSelecionaEntidad) {
        this.activaBotonSelecionaEntidad = activaBotonSelecionaEntidad;
    }
    
    public String seleccionaParaCambioDeEntidad() {
        if (selectedPlanCuentas.getIdEntidad() != null) {
            if (selectedPlanCuentas.getNivel() == 1) {
                cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
                if (cntEntidadesService.comparaEntidadConDetalleComprobante(selectedPlanCuentas, cntDetalleComprobante)) {
                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                    setInSessionIdEntidadModificacionDetalle(selectedPlanCuentas.getIdEntidad());
                    setInSessionIdComprobante(cntComprobante.getIdComprobante());
                    return "detalleComprobante";
                } else {
                    org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('confirmacionCambiarCuenta').show()");
                }
            } else {
                MessageUtils.addErrorMessage("La cuenta debe ser de último nivel.");
            }
        } else {
            MessageUtils.addErrorMessage("No selecciono ninguna cuenta.");
        }
        return null;
    }
    
    public String confirmaCambio() {
        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
        setInSessionIdEntidadModificacionDetalle(selectedPlanCuentas.getIdEntidad());
        setInSessionIdComprobante(cntComprobante.getIdComprobante());
        return "detalleComprobante";
    }
    
}
