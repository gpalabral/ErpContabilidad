package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.iknow.utils.ObjectUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.apache.log4j.Logger;

@ManagedBean(name = "cntTipoCambioBacking")
@ViewScoped
public class CntTipoCambioBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntTipoCambioBacking.class);
    @ManagedProperty(value = "#{cntTipoCambioService}")
    private CntTipoCambioService cntTipoCambioService;
    private CntTipoCambio cntTipoCambio;
    private Date ultimaFecha;
    private BigDecimal ultimoTipo;
    private BigDecimal ultimoTipoVista;
    private BigDecimal sugerenciaTipoCambio;
    private BigDecimal ultimoTipoUFVVista;
    private Date fechaActual;
    private Long nroDiasRestantes;
    private List<CntTipoCambio> listaTipoCambio;
    private List<CntTipoCambio> listTipoCambio;
    private CntTipoCambio selectedTipoCambio;
    private List<CntTipoCambio> filteredCntTipoCambio;
    private String tipoCambioAux;
    private String tipoUfvAux;
    private BigDecimal tipovalorcambio;
    private CntTipoCambio seleccionaTipoCambio = new CntTipoCambio();
    private BigDecimal tipovalorufv;
    CntTipoCambio cntTipoCambioAux;
    private Boolean activaBotonEditar = true;

    public CntTipoCambioBacking() {
    }

    @PostConstruct
    public void initCntTipoCambioBacking() {
        if (super.getFromSessionIdEntidad() != null) {
            cntTipoCambio = (CntTipoCambio) cntTipoCambioService.find(CntTipoCambio.class, super.getFromSessionIdEntidad());
            tipoCambioAux = cntTipoCambio.getTipoCambio().toString();

//            tipoCambioAux = (cntTipoCambioService.ultimoCntTipoCambio().getTipoCambio()).toString();
            tipoUfvAux = cntTipoCambio.getTipoUfv().toString();
        } else {
            cntTipoCambio = new CntTipoCambio();
            cntTipoCambioService.grabarPrimerFecha();
            if ((cntTipoCambioService.ultimoCntTipoCambio()) != null) {

                tipoCambioAux = (cntTipoCambioService.ultimoCntTipoCambio().getTipoCambio()).toString();
            }
            if (cntTipoCambioService.ultimoCntTipoCambio() == null) {
                cntTipoCambioAux = cntTipoCambioService.ultimoRegistroCntTipoCambio();
            } else {
//                cntTipoCambioAux = cntTipoCambioService.ultimoCntTipoCambio();
                //aumentado para sugerir tipo de cambio
                cntTipoCambioAux = cntTipoCambioService.ultimoRegistroCntTipoCambio();
            }
            cntTipoCambio.setFecha(cntTipoCambioService.generaSiguienteDia(cntTipoCambioAux.getFecha(), 1));
            cntTipoCambioService.grabarFechas();
        }
    }

    public List<CntTipoCambio> listadoTipoCambio() throws Exception {
        if (listaTipoCambio == null || listaTipoCambio.isEmpty()) {
            listaTipoCambio = cntTipoCambioService.listaCntTipoCambio();
        }
        return listaTipoCambio;
    }

    public String saveCntTipoCambio() {
        try {
            if (cntTipoCambioService.verificaValoresForm(cntTipoCambio).equals("OK")) {
                tipovalorcambio = new BigDecimal(tipoCambioAux);
                cntTipoCambio.setTipoCambio(tipovalorcambio);
                tipovalorufv = new BigDecimal(tipoUfvAux);
                cntTipoCambio.setTipoUfv(tipovalorufv);
                super.setPersistValues(cntTipoCambio);
                cntTipoCambioService.persistCntTipoCambio(cntTipoCambio);
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

    public String editCntTipoCambio() {
        try {
            if (cntTipoCambioService.verificaValoresForm(cntTipoCambio).equals("OK")) {
                tipovalorcambio = new BigDecimal(tipoCambioAux);
                cntTipoCambio.setTipoCambio(tipovalorcambio);
                tipovalorufv = new BigDecimal(tipoUfvAux);
                cntTipoCambio.setTipoUfv(tipovalorufv);
                super.setMergeValues(cntTipoCambio);
                cntTipoCambioService.mergeCntTipoCambio(cntTipoCambio);
//                MessageUtils.addInfoMessage("Se modifico Correctamente");
            } else {
                MessageUtils.addErrorMessage(cntTipoCambioService.verificaValoresForm(cntTipoCambio));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error:", e.getMessage()));
            return null;
        }
        resetSessionVariables();
        return "tipoCambioList";
    }

    public String editarSeguirCntTipoCambio() {
        try {
            if (cntTipoCambioService.verificaValoresForm(cntTipoCambio).equals("OK")) {
                tipovalorcambio = new BigDecimal(tipoCambioAux);
                cntTipoCambio.setTipoCambio(tipovalorcambio);
                tipovalorufv = new BigDecimal(tipoUfvAux);
                cntTipoCambio.setTipoUfv(tipovalorufv);
//                cntTipoCambio.setFecha(fechaparamodificar);
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

        if (cntTipoCambioService.ultimoDiaRegistrado() > 1) {
            cntTipoCambio = cntTipoCambioService.ultimaFechaRegistroCntTipoCambio();
            if (cntTipoCambio != null) {
                super.setInSessionIdEntidad(cntTipoCambio.getIdTipoCambio());
                return "tipoCambioForm";
            }
        }
        resetSessionVariables();
        return "tipoCambioList";

    }

    public String removeCntTipoCambio() {
        cntTipoCambio = cntTipoCambioService.find(CntTipoCambio.class, super.getFromSessionIdEntidad());
        try {
            super.setRemoveValues(cntTipoCambio);
            cntTipoCambioService.removeCntTipoCambio(cntTipoCambio);
//          
            MessageUtils.addInfoMessage("Se elimino El Tipo de Cambio Correctamente");
            super.setInSessionIdEntidad(null);
        } catch (Exception e) {
            MessageUtils.addErrorMessage(e.getMessage());
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error:", e.getMessage()));
            return null;
        }
        resetSessionVariables();
        return "tipoCambioList";
    }

    public String modificarUFV() {
        try {
            if (cntTipoCambioService.verificaValoresForm(seleccionaTipoCambio).equals("OK")) {
                super.setMergeValues(seleccionaTipoCambio);
                cntTipoCambioService.mergeCntTipoCambio(seleccionaTipoCambio);
            } else {
                MessageUtils.addErrorMessage(cntTipoCambioService.verificaValoresForm(seleccionaTipoCambio));
                return null;
            }
        } catch (Exception e) {
            MessageUtils.addErrorMessage(e.getMessage());
            return null;
        }
        seleccionaTipoCambio = new CntTipoCambio();
        return "tipoCambioList";
    }

    public void cierrraDialogo(ValueChangeEvent e) {
        resetSessionVariables();
        seleccionaTipoCambio = new CntTipoCambio();

    }

    public boolean isEditable() {
        if (cntTipoCambio == null) {
            return false;
        } else if (cntTipoCambio.getIdTipoCambio() == null) {
            return false;
        } else {
            return true;
        }
    }

    public Date getUltimaFechaRegistro() {
        if (cntTipoCambioService.ultimoCntTipoCambio() != null) {
            ultimaFecha = cntTipoCambioService.ultimoCntTipoCambio().getFecha();
            return ultimaFecha;
        }
        return new Date();
    }

    public void setUltimaFechaRegistro(Date ultimaFecha) {
        this.ultimaFecha = ultimaFecha;
    }

    public BigDecimal getUltimoTipoCambioRegistro() {
        if (cntTipoCambioService.ultimoCntTipoCambio() != null) {
            ultimoTipoVista = cntTipoCambioService.ultimoCntTipoCambio().getTipoCambio();
            return ultimoTipoVista.setScale(2, RoundingMode.HALF_UP);

        }
        return new BigDecimal(0);
    }

    public void setUltimoTipoCambioRegistro() {
        this.ultimoTipoVista = ultimoTipoVista;
    }

    public BigDecimal getUltimoTipoUFVCambioRegistro() {
        if (cntTipoCambioService.ultimoCntTipoCambio() != null) {
            ultimoTipoUFVVista = cntTipoCambioService.ultimoCntTipoCambio().getTipoUfv();
            return ultimoTipoUFVVista.setScale(5, RoundingMode.HALF_UP);
        }
        return new BigDecimal(0);
    }

    public void setUltimoTipoUFVCambioRegistro() {
        this.ultimoTipoUFVVista = ultimoTipoUFVVista;
    }

    public Date getFechaActual() {
        fechaActual = new Date();
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
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

    public List<CntTipoCambio> getFilteredCntTipoCambio() {
        return filteredCntTipoCambio;
    }

    public void setFilteredCntTipoCambio(List<CntTipoCambio> filteredCntTipoCambio) {
        this.filteredCntTipoCambio = filteredCntTipoCambio;
    }

    public List<CntTipoCambio> getListTipoCambio() {
        return listTipoCambio;
    }

    public void setListTipoCambio(List<CntTipoCambio> listTipoCambio) {
        this.listTipoCambio = listTipoCambio;
    }

    public List<CntTipoCambio> getListaTipoCambio() {
        return listaTipoCambio;
    }

    public void setListaTipoCambio(List<CntTipoCambio> listaTipoCambio) {
        this.listaTipoCambio = listaTipoCambio;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        CntTipoCambioBacking.log = log;
    }

    public CntTipoCambio getSelectedTipoCambio() {

        return selectedTipoCambio;
    }

    public void setSelectedTipoCambio(CntTipoCambio selectedTipoCambio) {

        this.selectedTipoCambio = selectedTipoCambio;
        //  tipoCambioAux = selectedTipoCambio.getTipoCambio().toString();
        //  tipoUfvAux = selectedTipoCambio.getTipoUfv().toString();
        //  fechaparamodificar= selectedTipoCambio.getFecha();

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

    public Long getNroDiasRestantes() {
        return nroDiasRestantes;
    }

    public void setNroDiasRestantes(Long nroDiasRestantes) {
        this.nroDiasRestantes = nroDiasRestantes;
    }

    public BigDecimal getTipovalorcambio() {
        return tipovalorcambio;
    }

    public void setTipovalorcambio(BigDecimal tipovalorcambio) {
        this.tipovalorcambio = tipovalorcambio;
    }

    public BigDecimal getTipovalorufv() {
        return tipovalorufv;
    }

    public void setTipovalorufv(BigDecimal tipovalorufv) {
        this.tipovalorufv = tipovalorufv;
    }

    public BigDecimal getUltimoTipo() {
        return ultimoTipo;
    }

    public void setUltimoTipo(BigDecimal ultimoTipo) {
        this.ultimoTipo = ultimoTipo;
    }

    public CntTipoCambio getSeleccionaTipoCambio() {
        return seleccionaTipoCambio;
    }

    public void setSeleccionaTipoCambio(CntTipoCambio seleccionaTipoCambio) {
        this.seleccionaTipoCambio = seleccionaTipoCambio;
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

    public Date getUltimaFecha() {
        return ultimaFecha;
    }

    public void setUltimaFecha(Date ultimaFecha) {
        this.ultimaFecha = ultimaFecha;
    }

    public BigDecimal getUltimoTipoVista() {
        return ultimoTipoVista;
    }

    public void setUltimoTipoVista(BigDecimal ultimoTipoVista) {
        this.ultimoTipoVista = ultimoTipoVista;
    }

    public BigDecimal getSugerenciaTipoCambio() {
        return sugerenciaTipoCambio;
    }

    public void setSugerenciaTipoCambio(BigDecimal sugerenciaTipoCambio) {
        this.sugerenciaTipoCambio = sugerenciaTipoCambio;
    }

    public BigDecimal getUltimoTipoUFVVista() {
        return ultimoTipoUFVVista;
    }

    public void setUltimoTipoUFVVista(BigDecimal ultimoTipoUFVVista) {
        this.ultimoTipoUFVVista = ultimoTipoUFVVista;
    }

    public CntTipoCambio getCntTipoCambioAux() {
        return cntTipoCambioAux;
    }

    public void setCntTipoCambioAux(CntTipoCambio cntTipoCambioAux) {
        this.cntTipoCambioAux = cntTipoCambioAux;
    }

    public String irModificar(CntTipoCambio cntTipoCambio) {
        if (cntTipoCambio.getIdTipoCambio() != null) {
            setInSessionIdEntidad(cntTipoCambio.getIdTipoCambio());
            return "tipoCambioModificacionForm";
        } else {
            MessageUtils.addErrorMessage("Seleccione un registro para editar.");
            return null;
        }
    }

    public void modificarTipoDeCambio(CntTipoCambio cntTipoCambio) {
        System.out.println("tipo cambio:" + cntTipoCambio.getIdTipoCambio());
        System.out.println("ufv:" + cntTipoCambio.getTipoUfv());
        if (cntTipoCambio.getIdTipoCambio() != null) {
//            setInSessionIdEntidad(cntTipoCambio.getIdTipoCambio());
            seleccionaTipoCambio = cntTipoCambio;
            org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('itemDialog').show()");
        } else {
            MessageUtils.addErrorMessage("Seleccione un registro para editar.");
        }
    }

    public Boolean getActivaBotonEditar() {
        return activaBotonEditar;
    }

    public void setActivaBotonEditar(Boolean activaBotonEditar) {
        this.activaBotonEditar = activaBotonEditar;
    }

    public void selecionaTipoCambio(ValueChangeEvent e) {
        System.out.println("SELECION:" + seleccionaTipoCambio.getFechaModificacion());
        activaBotonEditar = seleccionaTipoCambio.getFechaModificacion() == null;

    }

    
}
