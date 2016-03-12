/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.apache.log4j.Logger;

@ManagedBean(name = "cntAuxiliaresBacking")
@ViewScoped
public class CntAuxiliaresBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntAuxiliaresBacking.class);
    @ManagedProperty(value = "#{cntAuxiliaresService}")
    private CntAuxiliaresService cntAuxiliaresService;
    private CntAuxiliar cntAuxiliares;
    private List<CntAuxiliar> listaAuxiliares;
    private List<CntAuxiliar> listAuxiliar;
    private boolean permissionToRegister;
    private CntAuxiliar selectedAuxiliar = new CntAuxiliar();
    private CntAuxiliar seleccionaAuxiliarModificar;
    private List<CntAuxiliar> filteredCntAuxiliar;
    private Boolean botonModifica = false;

    public CntAuxiliaresBacking() {
    }

    @PostConstruct
    public void initCntAuxiliarBacking() {
        if (super.getFromSessionIdEntidad() != null) {
            selectedAuxiliar = (CntAuxiliar) cntAuxiliaresService.find(CntAuxiliar.class, super.getFromSessionIdEntidad());
            botonModifica = true;
            super.setInSessionIdEntidad(null);
        } else {
            selectedAuxiliar = new CntAuxiliar();
            String a = cntAuxiliaresService.ultimo_numero_auxiliar();
            Long numero = Long.parseLong(a, 10);
            selectedAuxiliar.setNumero(numero);
            selectedAuxiliar.setFechaAlta(new Date());
        }

    }

    public List<CntAuxiliar> listasAuxiliares() throws Exception {
        if (listaAuxiliares == null || listaAuxiliares.isEmpty()) {
            listaAuxiliares = cntAuxiliaresService.listaCntAuxiliares();
        }
        return listaAuxiliares;
    }

    public String saveCntAuxiliares() {
        try {
            if (selectedAuxiliar.getSigla().length() <= 4) {
                if (cntAuxiliaresService.verificaExistenciaDeSigla(selectedAuxiliar.getSigla()).equals("OK")) {
                    if (cntAuxiliaresService.verificaExistenciaDeNombre(selectedAuxiliar.getNombre()).equals("OK")) {
                        super.setPersistValues(selectedAuxiliar);
                        cntAuxiliaresService.persistCntAuxiliares(selectedAuxiliar);
                        // MessageUtils.addInfoMessage("Se guardo el Auxiliar Correctamente.");
                    } else {
                        MessageUtils.addErrorMessage(cntAuxiliaresService.verificaExistenciaDeNombre(selectedAuxiliar.getNombre()));
                        return null;
                    }
                } else {
                    MessageUtils.addErrorMessage(cntAuxiliaresService.verificaExistenciaDeSigla(selectedAuxiliar.getSigla()));
                    return null;
                }
            } else {
                selectedAuxiliar.setSigla("");
                MessageUtils.addErrorMessage("La sigla es mayor a cuatro digitos.");
                return null;
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error:", e.getMessage()));
            return null;
        }
        return "auxiliares";
    }

    public String editCntAuxiliares() {
        try {
            if (cntAuxiliaresService.verificaValoresForm(selectedAuxiliar).equals("OK")) {
                super.setMergeValues(selectedAuxiliar);
                cntAuxiliaresService.mergeCntAuxiliares(selectedAuxiliar);
//                MessageUtils.addInfoMessage("Se Modifico el Registro Correctamente.");
            } else {
                MessageUtils.addErrorMessage(cntAuxiliaresService.verificaValoresForm(selectedAuxiliar));
                return null;
            }
        } catch (Exception e) {
            MessageUtils.addErrorMessage("Error:" + e.getMessage());
            return null;
        }
        resetSessionVariables();
        return "auxiliares";
    }

    public String removeCntAuxiliares() {
        if (selectedAuxiliar != null) {
            if (selectedAuxiliar.getIdAuxiliar() != null) {
                if (!cntAuxiliaresService.verificaAuxiliarDetalleCmmprobante(cntAuxiliares)) {
                    try {
                        super.setRemoveValues(selectedAuxiliar);
                        cntAuxiliaresService.removeCntAuxiliares(selectedAuxiliar);
                        MessageUtils.addInfoMessage("Se elimino el Auxiliar Correctamente.");
                    } catch (Exception e) {
                        MessageUtils.addErrorMessage("Error:" + e.getMessage());
                        return null;
                    }

                } else {
                    MessageUtils.addErrorMessage("No se puede Eliminar. El auxiliar esta asignado a un Detalle Comprobante");
                }
            } else {
                MessageUtils.addErrorMessage("No selecciono ningun Auxiliar para eliminar");
            }
        } else {
            MessageUtils.addErrorMessage("No selecciono ningun Auxiliar para eliminar");
        }
        return "auxiliares";
    }

    public boolean isEditable() {
        if (cntAuxiliares == null) {
            return false;
        } else if (cntAuxiliares.getIdAuxiliar() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setListaTipoMenu(List<SelectItem> listaTipoMenu) {
        this.setListaTipoMenu(listaTipoMenu);
    }

    public String irFormAuxiliares() {
        super.setInSessionIdEntidad(null);
        return "auxiliaresForm";
    }

    public String irFormAuxiliaresModificar() {
        if (selectedAuxiliar != null) {
            setInSessionIdEntidad(selectedAuxiliar.getIdAuxiliar());
            return "auxiliaresForm";

        } else {
            MessageUtils.addErrorMessage("No selecciono ningun Auxiliar para modificar");
            return "auxiliares";
        }
    }

    public String actionReturnAuxiliares() {
        selectedAuxiliar = new CntAuxiliar();
        return "auxiliares";
    }

    public void setSelectRowCntAuxiliares(CntAuxiliar value) {
        super.setInSessionIdEntidad(value.getIdAuxiliar());
    }

    public void cierrraDialogo(ValueChangeEvent e) {
        resetSessionVariables();
        selectedAuxiliar = new CntAuxiliar();

    }

    public CntAuxiliar getCntAuxiliares() {
        return cntAuxiliares;
    }

    public void setCntAuxiliares(CntAuxiliar cntAuxiliares) {
        this.cntAuxiliares = cntAuxiliares;
    }

    public CntAuxiliaresService getCntAuxiliaresService() {
        return cntAuxiliaresService;
    }

    public void setCntAuxiliaresService(CntAuxiliaresService cntAuxiliaresService) {
        this.cntAuxiliaresService = cntAuxiliaresService;
    }

    public List<CntAuxiliar> getFilteredCntAuxiliar() {
        return filteredCntAuxiliar;
    }

    public void setFilteredCntAuxiliar(List<CntAuxiliar> filteredCntAuxiliar) {
        this.filteredCntAuxiliar = filteredCntAuxiliar;
    }

    public List<CntAuxiliar> getListAuxiliar() {
        return listAuxiliar;
    }

    public void setListAuxiliar(List<CntAuxiliar> listAuxiliar) {
        this.listAuxiliar = listAuxiliar;
    }

    public List<CntAuxiliar> getListaAuxiliares() {
        return listaAuxiliares;
    }

    public void setListaAuxiliares(List<CntAuxiliar> listaAuxiliares) {
        this.listaAuxiliares = listaAuxiliares;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        CntAuxiliaresBacking.log = log;
    }

    public boolean isPermissionToRegister() {
        return permissionToRegister;
    }

    public void setPermissionToRegister(boolean permissionToRegister) {
        this.permissionToRegister = permissionToRegister;
    }

    public CntAuxiliar getSelectedAuxiliar() {
        return selectedAuxiliar;
    }

    public void setSelectedAuxiliar(CntAuxiliar selectedAuxiliar) {
        this.selectedAuxiliar = selectedAuxiliar;
//        setInSessionIdEntidad(selectedAuxiliar.getIdAuxiliar());
    }

    public void obtieneObjetoAuxiliar(ValueChangeEvent e) {
        if (selectedAuxiliar != null) {
            cntAuxiliares = selectedAuxiliar;
            setInSessionIdEntidad(selectedAuxiliar.getIdAuxiliar());

        }
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

    public Boolean getBotonModifica() {
        return botonModifica;
    }

    public void setBotonModifica(Boolean botonModifica) {
        this.botonModifica = botonModifica;
    }

    public CntAuxiliar getSeleccionaAuxiliarModificar() {
        return seleccionaAuxiliarModificar;
    }

    public void setSeleccionaAuxiliarModificar(CntAuxiliar seleccionaAuxiliarModificar) {
        this.seleccionaAuxiliarModificar = seleccionaAuxiliarModificar;
    }

    public String insertaTituloFormulario() {
        if (isEditable()) {
            return "Modificar / Eliminar Auxiliar";
        } else {
            return "Adicionar Auxiliar";
        }
    }
}
