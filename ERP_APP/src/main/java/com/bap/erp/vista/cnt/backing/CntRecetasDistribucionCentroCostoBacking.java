package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import com.bap.erp.modelo.entidades.cnf.ParRecetasDistribucionCentroCosto;
import com.bap.erp.modelo.entidades.cnf.ParSucursal;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.iknow.utils.ObjectUtils;
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

@ManagedBean(name = "cntRecetasDistribucionCentroCostoBacking")
@ViewScoped
public class CntRecetasDistribucionCentroCostoBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    private List<CntConfiguracionCentrocosto> cntListaConfiguracionPorReceta;
    private ParRecetasDistribucionCentroCosto selectedParValor;
    private ParValor parValor = new ParValor();
    private List<ParRecetasDistribucionCentroCosto> listaRecetasDistribucionCentroCostos;

    public CntRecetasDistribucionCentroCostoBacking() {
    }

    @PostConstruct
    void initcntRecetasDistribucionCentroCostoBacking() {
        if (super.getFromSessionCodigoParRecetasDistribucionCC() != null) {
        } else {
        }
    }

    public List<ParRecetasDistribucionCentroCosto> listadoRecetas() {
        if (listaRecetasDistribucionCentroCostos == null || listaRecetasDistribucionCentroCostos.isEmpty()) {
            listaRecetasDistribucionCentroCostos = parParametricasService.listaDistribucionCentroCostos();
        }
        return listaRecetasDistribucionCentroCostos;
    }

    public String saveDistribucionDeCentrosDeCosto() {
        try {
            if (parParametricasService.verificaValoresFormDistribucion(parValor).equals("OK")) {
                super.setPersistValues(parValor);

                parParametricasService.persistParValor(parValor);
                MessageUtils.addInfoMessage("Se guardo la nueva distribucion correctamente.");
            } else {
                MessageUtils.addErrorMessage(parParametricasService.verificaValoresFormDistribucion(parValor));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error:", e.getMessage()));
            return null;
        }
        return "listaDistribucionDeCentrosDeCosto";
    }

    public List<CntConfiguracionCentrocosto> listaCentroCostoPorReceta() {
        try {
            if (selectedParValor != null) {
                cntListaConfiguracionPorReceta = parParametricasService.listaConfiguracionCentroCostoPorReceta(selectedParValor.getCodigo());
            }
        } catch (Exception e) {
        }
        return cntListaConfiguracionPorReceta;

    }

    public String irFormDistribucion() {
        setInSessiontCodigoParRecetasDistribucionCC(null);
        setInSessiontIdEntidadAsignacionCC(null);
        return "asignacionCentroDeCosto";
    }

    public String eliminaReceta() {
        try {
            if (selectedParValor != null) {
                if (!parParametricasService.verificaRecetaDetalleComprobante(selectedParValor)) {
                    setRemoveValues(selectedParValor);
                    parParametricasService.eliminaRecetaCentroCostos(selectedParValor);
                    return "listaDistribucionDeCentrosDeCosto";
                }else{
                    MessageUtils.addErrorMessage("La Receta esta asignada a un Comprobante, por lo tanto no se puede eliminar.");
                }
            } else {
                MessageUtils.addErrorMessage("No selecciono Receta para eliminar.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public List<CntConfiguracionCentrocosto> getCntListaConfiguracionPorReceta() {
        return cntListaConfiguracionPorReceta;
    }

    public void setCntListaConfiguracionPorReceta(List<CntConfiguracionCentrocosto> cntListaConfiguracionPorReceta) {
        this.cntListaConfiguracionPorReceta = cntListaConfiguracionPorReceta;
    }

    public ParRecetasDistribucionCentroCosto getSelectedParValor() {
        return selectedParValor;
    }

    public void setSelectedParValor(ParRecetasDistribucionCentroCosto selectedParValor) {
        this.selectedParValor = selectedParValor;
    }

    public ParValor getParValor() {
        return parValor;
    }

    public void setParValor(ParValor parValor) {
        this.parValor = parValor;
    }

    public List<ParRecetasDistribucionCentroCosto> getListaRecetasDistribucionCentroCostos() {
        return listaRecetasDistribucionCentroCostos;
    }

    public void setListaRecetasDistribucionCentroCostos(List<ParRecetasDistribucionCentroCosto> listaRecetasDistribucionCentroCostos) {
        this.listaRecetasDistribucionCentroCostos = listaRecetasDistribucionCentroCostos;
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

    public String irFormDistribucionModificacion() {
        if (selectedParValor != null && selectedParValor.getCodigo() != null) {
            setInSessiontCodigoParRecetasDistribucionCC(selectedParValor.getCodigo());
            setInSessiontIdEntidadAsignacionCC(null);
            return "asignacionCentroDeCosto";
        } else {
            MessageUtils.addErrorMessage("Debe seleccionar un elemento de la lista para modificar");
            return null;
        }
    }
}
