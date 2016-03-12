package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnf.ParRecetasDistribucionCentroCosto;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.servicios.cnt.CntConfiguracionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntDistribucionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "cntAsignacionCentrosDeCostoBacking")
@ViewScoped
public class CntAsignacionCentrosDeCostoBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntDistribucionCentroCostoService}")
    private CntDistribucionCentroCostoService cntDistribucionCentroCostoService;
    private List<CntEntidad> listaCentrosDeCosto;
    private List<CntEntidad> listaCentrosDeCostoElegidaParaQuitarse = new ArrayList<CntEntidad>();
    private List<CntConfiguracionCentrocosto> listaCentrosDeCostoElegidaParaQuitarseAux = new ArrayList<CntConfiguracionCentrocosto>();
    private CntEntidad selectedEntidad;
    private ParRecetasDistribucionCentroCosto recetasDistribucionCentroCosto;
//    desde aqui codigo Jonas para guardar Configuracion de Centro de Costos por Definicion
    @ManagedProperty(value = "#{cntConfiguracionCentroCostoService}")
    private CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService;
//    hasta aqui codigo Jonas para guardar Configuracion de Centro de Costos por Definicion
    private List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto;
    private CntConfiguracionCentrocosto configuracionCentrocosto = new CntConfiguracionCentrocosto();
    private BigDecimal sumaPorsentajeTotal = new BigDecimal("0.00");
    private BigDecimal disponibilidad = new BigDecimal("100.00");
    private Boolean modificaReceta;
    private Boolean modificaRecetaHabilita = true;
    private Boolean validaNombreReceta;
    private Boolean validaBotonGuardar = true;

    public CntAsignacionCentrosDeCostoBacking() {
    }

    @PostConstruct
    void initCntAsignacionCentrosDeCostoBacking() {
        
        try {
            if (getFromSessionCodigoParRecetasDistribucionCC() != null) {
                recetasDistribucionCentroCosto = (ParRecetasDistribucionCentroCosto) parParametricasService.find(ParRecetasDistribucionCentroCosto.class, getFromSessionCodigoParRecetasDistribucionCC());
                listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
                listaCentrosDeCosto = new ArrayList<CntEntidad>();
                listaCntConfiguracionCentrocosto = cntConfiguracionCentroCostoService.cargaListadoConfiguracionParaRecetasDesdeUnPlanCuentas(recetasDistribucionCentroCosto);
                listaCentrosDeCosto = cntEntidadesService.filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(listaCntConfiguracionCentrocosto);
                sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
                disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
                modificaReceta = true;
            } else {
                recetasDistribucionCentroCosto = new ParRecetasDistribucionCentroCosto();
                recetasDistribucionCentroCosto.setCodigo(parParametricasService.ultimoRegistroRecetaParValor().toString());
                recetasDistribucionCentroCosto.setDescripcion("");
                listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
                listaCentrosDeCosto = new ArrayList<CntEntidad>();
                listaCentrosDeCosto = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
                modificaReceta = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<CntEntidad> listaCentrosDeCostoInicial() {
        try {
            listaCentrosDeCosto = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.CENTROS_COSTOS.getCodigo());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCentrosDeCosto;
    }

    public void enviaRegistroSiguienteLista() {
        try {
            if (!listaCentrosDeCostoElegidaParaQuitarse.isEmpty() && listaCentrosDeCostoElegidaParaQuitarse.size() == 1) {
                if (cntEntidadesService.verfificaExistenciaDeCentroDeCostoAntesDeAdicionar(listaCentrosDeCostoElegidaParaQuitarse.get(0), listaCntConfiguracionCentrocosto)) {
                    if (listaCentrosDeCostoElegidaParaQuitarse.get(0).getNivel() == 1) {
                        CntConfiguracionCentrocosto cntConfiguracionCentrocosto = (CntConfiguracionCentrocosto) configuracionCentrocosto.clone();
                        cntConfiguracionCentrocosto.setIdCentroCosto(listaCentrosDeCostoElegidaParaQuitarse.get(0));
                        listaCntConfiguracionCentrocosto.add(cntConfiguracionCentrocosto);
                    }
                    listaCentrosDeCosto = cntEntidadesService.filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(listaCntConfiguracionCentrocosto);
                }
            }
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviaTodosLosRegistroSiguienteLista() {
        try {
            listaCentrosDeCosto = cntEntidadesService.cntEntidadesParaCentrosDeCostoSoloNivelUnoFiltraList();
            listaCntConfiguracionCentrocosto = cntEntidadesService.llevaTodosLosCentrosDeCostoNivelUnoASiguienteListaConfiguracion();
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviaTodosLosRegistroListaOriginalLista() {
        try {
            listaCentrosDeCosto = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
            listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quitaRegistroSiguienteLista() {
        try {
            if (!listaCentrosDeCostoElegidaParaQuitarseAux.isEmpty() && listaCentrosDeCostoElegidaParaQuitarseAux.size() == 1) {
                listaCntConfiguracionCentrocosto.remove(listaCentrosDeCostoElegidaParaQuitarseAux.get(0));
                listaCentrosDeCosto = cntEntidadesService.filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(listaCntConfiguracionCentrocosto);
            }
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validaNombreReceta(ValueChangeEvent e) {
        if (!recetasDistribucionCentroCosto.getDescripcion().equals("")) {
            if (parParametricasService.validaNombreRepetido(recetasDistribucionCentroCosto)) {
                validaBotonGuardar = true;
                modificaRecetaHabilita = true;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje Error:", "El nombre ya existe"));
            } else {
                validaBotonGuardar = false;
                modificaRecetaHabilita = false;
            }
        } else {
            validaBotonGuardar = true;
            modificaRecetaHabilita = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje Error:", "Se debe llenar el campo de  descripci&oacute;n"));
        }
    }

    public void obtieneObjetoPlanCuentas(ValueChangeEvent e) {
    }

    public String insertaPuntosAutomatio(CntEntidad cntEntidad) {
        return cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad);
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public List<CntEntidad> getListaCentrosDeCosto() {
        return listaCentrosDeCosto;
    }

    public void setListaCentrosDeCosto(List<CntEntidad> listaCentrosDeCosto) {
        this.listaCentrosDeCosto = listaCentrosDeCosto;
    }

    public List<CntEntidad> getListaCentrosDeCostoElegidaParaQuitarse() {
        return listaCentrosDeCostoElegidaParaQuitarse;
    }

    public void setListaCentrosDeCostoElegidaParaQuitarse(List<CntEntidad> listaCentrosDeCostoElegidaParaQuitarse) {
        this.listaCentrosDeCostoElegidaParaQuitarse = listaCentrosDeCostoElegidaParaQuitarse;
    }

    public List<CntConfiguracionCentrocosto> getListaCentrosDeCostoElegidaParaQuitarseAux() {
        return listaCentrosDeCostoElegidaParaQuitarseAux;
    }

    public void setListaCentrosDeCostoElegidaParaQuitarseAux(List<CntConfiguracionCentrocosto> listaCentrosDeCostoElegidaParaQuitarseAux) {
        this.listaCentrosDeCostoElegidaParaQuitarseAux = listaCentrosDeCostoElegidaParaQuitarseAux;
    }

    public CntEntidad getSelectedEntidad() {
        return selectedEntidad;
    }

    public void setSelectedEntidad(CntEntidad selectedEntidad) {
        this.selectedEntidad = selectedEntidad;
    }

    public void onEdit(RowEditEvent event) {
        try {
            if (!cntEntidadesService.validaSumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto)) {
                ((CntConfiguracionCentrocosto) event.getObject()).setPorcentaje(null);
//                MessageUtils.addErrorMessage("El valor del porcentaje sobrepasa el 100%.");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje Error:", "El porcentaje del codigo " + ((CntConfiguracionCentrocosto) event.getObject()).getIdCentroCosto().getMascaraGenerada() + " sobrepasa el 100%."));
            }
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Cancelled", ((CntConfiguracionCentrocosto) event.getObject()).getIdCentroCosto().getDescripcion());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public ParRecetasDistribucionCentroCosto getRecetasDistribucionCentroCosto() {
        return recetasDistribucionCentroCosto;
    }

    public void setRecetasDistribucionCentroCosto(ParRecetasDistribucionCentroCosto recetasDistribucionCentroCosto) {
        this.recetasDistribucionCentroCosto = recetasDistribucionCentroCosto;
    }

//  Desde aqui el codigo Jonas para guardar la Configuracion de Centro de Costos por Definicion
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

    public String guardarCntConfiguracionCentroCostoAlternativa() throws Exception {
        if (!listaCntConfiguracionCentrocosto.isEmpty()) {
            if (cntDistribucionCentroCostoService.verificaValoresListaCentroCostos(listaCntConfiguracionCentrocosto)) {
                cntConfiguracionCentroCostoService.guardaCntConfiguracionCentroCostoAlternativa(listaCntConfiguracionCentrocosto, getLoginSession(), recetasDistribucionCentroCosto);
                setInSessiontCodigoParRecetasDistribucionCC(null);
                return "listaDistribucionDeCentrosDeCosto";
            } else {
                MessageUtils.addErrorMessage("No se asigno correctamente los porcentajes a las cuentas");
            }
        }
        MessageUtils.addErrorMessage("No se asigno correctamente los Centros de Costo");
        return null;
    }

    public String cancelaCntConfiguracionCentroCostoAlternativa() {
        setInSessiontCodigoParRecetasDistribucionCC(null);
        return "listaDistribucionDeCentrosDeCosto";
    }

    public CntConfiguracionCentroCostoService getCntConfiguracionCentroCostoService() {
        return cntConfiguracionCentroCostoService;
    }

    public void setCntConfiguracionCentroCostoService(CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService) {
        this.cntConfiguracionCentroCostoService = cntConfiguracionCentroCostoService;
    }

//  Hasta aqui el codigo Jonas para guardar la Configuracion de Centro de Costos por Definicion
    public List<CntConfiguracionCentrocosto> getListaCntConfiguracionCentrocosto() {
        return listaCntConfiguracionCentrocosto;
    }

    public void setListaCntConfiguracionCentrocosto(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto) {
        this.listaCntConfiguracionCentrocosto = listaCntConfiguracionCentrocosto;
    }

    public CntConfiguracionCentrocosto getConfiguracionCentrocosto() {
        return configuracionCentrocosto;
    }

    public void setConfiguracionCentrocosto(CntConfiguracionCentrocosto configuracionCentrocosto) {
        this.configuracionCentrocosto = configuracionCentrocosto;
    }

    public BigDecimal getSumaPorsentajeTotal() {
        return sumaPorsentajeTotal;
    }

    public void setSumaPorsentajeTotal(BigDecimal sumaPorsentajeTotal) {
        this.sumaPorsentajeTotal = sumaPorsentajeTotal;
    }

    public BigDecimal getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(BigDecimal disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String modificaCntConfiguracionCentroCostoPorAlternativa() throws Exception {        
        if (cntEntidadesService.validaSumaTotalProcentajeAlCienPorcientoParaModificar(listaCntConfiguracionCentrocosto)) {
            if (!listaCntConfiguracionCentrocosto.isEmpty()) {
//                setMergeValues(recetasDistribucionCentroCosto);
                cntConfiguracionCentroCostoService.modificarCntConfiguracionCentroCostoAlternativa(listaCntConfiguracionCentrocosto, getLoginSession(), recetasDistribucionCentroCosto);
                return "listaDistribucionDeCentrosDeCosto";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje Error:", "La suma total de porcentajes no cumple 100%, no se puede modificar."));
        }
        return null;
    }

    public String irListaDistribucionDeCentrosDeCosto() {
        setInSessiontCodigoParRecetasDistribucionCC(null);
        return "listaDistribucionDeCentrosDeCosto";
    }

    public Boolean getModificaReceta() {
        return modificaReceta;
    }

    public void setModificaReceta(Boolean modificaReceta) {
        this.modificaReceta = modificaReceta;
    }

    public Boolean getValidaNombreReceta() {
        return validaNombreReceta;
    }

    public void setValidaNombreReceta(Boolean validaNombreReceta) {
        this.validaNombreReceta = validaNombreReceta;
    }

    public Boolean getValidaBotonGuardar() {
        return validaBotonGuardar;
    }

    public void setValidaBotonGuardar(Boolean validaBotonGuardar) {
        this.validaBotonGuardar = validaBotonGuardar;
    }

    public CntDistribucionCentroCostoService getCntDistribucionCentroCostoService() {
        return cntDistribucionCentroCostoService;
    }

    public void setCntDistribucionCentroCostoService(CntDistribucionCentroCostoService cntDistribucionCentroCostoService) {
        this.cntDistribucionCentroCostoService = cntDistribucionCentroCostoService;
    }

    public Boolean getModificaRecetaHabilita() {
        return modificaRecetaHabilita;
    }

    public void setModificaRecetaHabilita(Boolean modificaRecetaHabilita) {
        this.modificaRecetaHabilita = modificaRecetaHabilita;
    }
}
