package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.servicios.cnt.CntCentrosCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "cntCentrosDeCostoModificarBacking")
@ViewScoped
public class CntCentrosDeCostoModificarBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntCentrosCostoService}")
    private CntCentrosCostoService cntCentrosCostoService;
    private CntEntidad centrosCostoNuevo;

    public CntCentrosDeCostoModificarBacking() {
    }

    @PostConstruct
    void initCntCentrosDeCostoModificarBacking() {
        if (getFromSessionIdCentroDeCosto() != null) {
            centrosCostoNuevo = (CntEntidad) cntCentrosCostoService.find(CntEntidad.class, getFromSessionIdCentroDeCosto());
        }else{
            centrosCostoNuevo=new CntEntidad();
        }
    }

    public String mergeCntCentrosCosto_action() {
        if (centrosCostoNuevo != null) {
            setMergeValues(centrosCostoNuevo);
            try {
                cntCentrosCostoService.mergeCntCentroCostoNivelAndSubNivel(centrosCostoNuevo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "centrosDeCosto";
        }else{
              MessageUtils.addErrorMessage("Debe seleccionar un centro de costo para modificar.");
            return null;
        }
    }

    public String cancelarCntCentrosCosto_action() {
        setInSessionIdCentroDeCosto(null);
        return "centrosDeCosto";
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

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public CntEntidad getCentrosCostoNuevo() {
        return centrosCostoNuevo;
    }

    public void setCentrosCostoNuevo(CntEntidad centrosCostoNuevo) {
        this.centrosCostoNuevo = centrosCostoNuevo;
    }

    public CntCentrosCostoService getCntCentrosCostoService() {
        return cntCentrosCostoService;
    }

    public void setCntCentrosCostoService(CntCentrosCostoService cntCentrosCostoService) {
        this.cntCentrosCostoService = cntCentrosCostoService;
    }
}
