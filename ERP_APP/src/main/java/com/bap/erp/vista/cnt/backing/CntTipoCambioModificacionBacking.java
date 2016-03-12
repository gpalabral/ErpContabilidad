package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

@ManagedBean(name = "cntTipoCambioModificacionBacking")
@ViewScoped
public class CntTipoCambioModificacionBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntTipoCambioModificacionBacking.class);
    @ManagedProperty(value = "#{cntTipoCambioService}")
    private CntTipoCambioService cntTipoCambioService;
    private CntTipoCambio cntTipoCambio;
    private String tipoCambioAux;
    private String tipoUfvAux;

    public CntTipoCambioModificacionBacking() {
    }

    @PostConstruct
    public void initCntTipoCambioBacking() {
        if (super.getFromSessionIdEntidad() != null) {
            cntTipoCambio = (CntTipoCambio) cntTipoCambioService.find(CntTipoCambio.class, super.getFromSessionIdEntidad());
            tipoCambioAux = cntTipoCambio.getTipoCambio().toString();
            tipoUfvAux = cntTipoCambio.getTipoUfv().toString();
        }
    }

    public String modificarUFV() {
        try {
            if (cntTipoCambioService.verificaValoresForm(cntTipoCambio).equals("OK")) {
                cntTipoCambio.setTipoCambio(new BigDecimal(tipoCambioAux));
                cntTipoCambio.setTipoUfv(new BigDecimal(tipoUfvAux));
                super.setMergeValues(cntTipoCambio);
                cntTipoCambioService.mergeCntTipoCambio(cntTipoCambio);
            } else {
                MessageUtils.addErrorMessage(cntTipoCambioService.verificaValoresForm(cntTipoCambio));
                return null;
            }
        } catch (Exception e) {
            MessageUtils.addErrorMessage(e.getMessage());
            return null;
        }
        return "tipoCambioList";
    }

    public String volverListaTipoCambio() {
        resetSessionVariables();
        return "tipoCambioList";
    }

    public String irFormTipoCambio() {
        if (cntTipoCambioService.ultimoDiaRegistrado() >= 1) {
            if (cntTipoCambioService.ultimaFechaRegistroCntTipoCambio() != null) {
                cntTipoCambio = cntTipoCambioService.ultimaFechaRegistroCntTipoCambio();
                super.setInSessionIdEntidad(cntTipoCambio.getIdTipoCambio());
            } else {
                super.setInSessionIdEntidad(null);
            }
        } else {
            super.setInSessionIdEntidad(null);
        }
        return "tipoCambioForm";
    }

    public String irFormTipoCambioModificar() {
        return "tipoCambioForm";
    }

    public String actionReturnTipoCambio() {
        super.setInSessionIdEntidad(null);
        return "tipoCambioList";
    }

    public void setSelectRowCntTipoCambio(CntTipoCambio value) {
        super.setInSessionIdEntidad(value.getIdTipoCambio());

    }

    public CntTipoCambio getCntTipoCambio() {
        return cntTipoCambio;
    }

    public void setCntTipoCambio(CntTipoCambio cntTipoCambio) {
        this.cntTipoCambio = cntTipoCambio;
    }

    public CntTipoCambioService getCntTipoCambioService() {
        return cntTipoCambioService;
    }

    public void setCntTipoCambioService(CntTipoCambioService cntTipoCambioService) {
        this.cntTipoCambioService = cntTipoCambioService;
    }

    
    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        CntTipoCambioModificacionBacking.log = log;
    }

    public String getTipoCambioAux() {
        return tipoCambioAux;
    }

    public void setTipoCambioAux(String tipoCambioAux) {
        this.tipoCambioAux = tipoCambioAux;
    }

    public String getTipoUfvAux() {
        return tipoUfvAux;
    }

    public void setTipoUfvAux(String tipoUfvAux) {
        this.tipoUfvAux = tipoUfvAux;
    }

    public String iconoGuardar() {
        return EnumIconosPrimeFaces.GUARDAR.getCodigo();
    }

    public String iconoModificar() {
        return EnumIconosPrimeFaces.MODIFICAR.getCodigo();
    }

    public String iconoEliminar() {
        return EnumIconosPrimeFaces.ELIMINAR.getCodigo();
    }

    public String iconoAtras() {
        return EnumIconosPrimeFaces.ATRAS.getCodigo();
    }

    public String iconoIrOtraVentana() {
        return EnumIconosPrimeFaces.IR_OTRA_VENTANA.getCodigo();
    }

}
