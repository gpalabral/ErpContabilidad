package com.bap.comp;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "planEntBacking")
@ViewScoped
public class CompPlanEntidadesBacking implements Serializable {

    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    private List<CntEntidad> entidadesList;
    private List<CntEntidad> filteredCntEntidades;
    private List<CntEntidad> cntHermanosList;
    private List<CntEntidad> cntHijosList;
    private CntEntidad selected;
    private String grupoNivel;
    private Boolean eleccionTodos = false;
    private String valor;
    private String color = null;
    private CntEntidad entidadSel;
    private CntEntidad selectedObjetosHermanos;
    private CntEntidad selectedObjetosHijos;
    private List<CntEntidad> listaMomentanea = new ArrayList<CntEntidad>();

    public CompPlanEntidadesBacking() {
    }

    @PostConstruct
    public void initCntAuxiliarBacking() {
        //Asignacion temporal, deberia venir de un combo, o  pasarlo como parametro
        grupoNivel = "PCTA";
    }

    /**
     * @return the entidadesList
     */
    public List<CntEntidad> getEntidadesList() {

        try {
            if (grupoNivel == null) {
                MessageUtils.addErrorMessage("Se debe definir un grupo para recuperar entidades.");
            }
            if (entidadesList == null) {
                entidadesList = cntEntidadesService.cntEntidadesPorGrupoNivelList(grupoNivel);
            }
        } catch (Exception e) {
            MessageUtils.addErrorMessage("Error:" + e.getMessage());
        }

        return entidadesList;
    }

    /**
     * @param entidadesList the entidadesList to set
     */
    public void setEntidadesList(List<CntEntidad> entidadesList) {
        this.setEntidadesList(entidadesList);
    }

    /**
     * @return the cntEntidadesService
     */
    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    /**
     * @param cntEntidadesService the cntEntidadesService to set
     */
    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public void activaOpciones(ValueChangeEvent event) {

        try {
            setEntidadesList(getCntEntidadesService().cntEntidadesPorGrupoNivelList(grupoNivel));
            setCntHermanosList(new ArrayList<CntEntidad>());
            setCntHijosList(new ArrayList<CntEntidad>());
            setSelected(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getGrupoNivel() {
        return grupoNivel;
    }

    public void setGrupoNivel(String grupoNivel) {
        this.grupoNivel = grupoNivel;
    }

    /**
     * @return the cntHermanosList
     */
    public List<CntEntidad> getCntHermanosList() {
        return cntHermanosList;
    }

    /**
     * @param cntHermanosList the cntHermanosList to set
     */
    public void setCntHermanosList(List<CntEntidad> cntHermanosList) {
        this.cntHermanosList = cntHermanosList;
    }

    /**
     * @return the cntHijosList
     */
    public List<CntEntidad> getCntHijosList() {
        return cntHijosList;
    }

    /**
     * @param cntHijosList the cntHijosList to set
     */
    public void setCntHijosList(List<CntEntidad> cntHijosList) {
        this.cntHijosList = cntHijosList;
    }

    /**
     * @return the filteredCntEntidades
     */
    public List<CntEntidad> getFilteredCntEntidades() {
        return filteredCntEntidades;
    }

    /**
     * @param filteredCntEntidades the filteredCntEntidades to set
     */
    public void setFilteredCntEntidades(List<CntEntidad> filteredCntEntidades) {
        this.filteredCntEntidades = filteredCntEntidades;
    }

    public void listaTodo() {
        if (getEleccionTodos()) {
            color = "para-elegidas";
            listaMomentanea = filteredCntEntidades;
            filteredCntEntidades = null;
        } else {
            filteredCntEntidades = listaMomentanea;
            listaMomentanea = null;
            color = "para-filas";
        }
    }

    public List<CntEntidad> cntObjetosListaHermanos() {
        try {
            if (getSelected() != null) {
                cntHermanosList = getCntEntidadesService().obtieneHermanosPorCntEntidad(getSelected());
            }
        } catch (Exception e) {
        }
        return cntHermanosList;
    }

    public void planCuentasGeneral(CntEntidad CntObjetos) {
        setSelected(CntObjetos);
    }

    public List<CntEntidad> cntObjetosListaHijos() {
        try {
            if (getSelected() != null) {
                cntHijosList = getCntEntidadesService().obtieneHijosPorCntObjetos(getSelected());
            }
        } catch (Exception e) {
        }
        return cntHijosList;
    }

    public void obtieneObjeto(ValueChangeEvent e) {
        setSelected(entidadSel);
    }

    public void obtieneObjetoHermano(ValueChangeEvent e) {
        entidadSel = selectedObjetosHermanos;
        setSelected(selectedObjetosHermanos);
    }

    public void obtieneObjetoPlanCuentasHijo(ValueChangeEvent e) {
        entidadSel = selectedObjetosHijos;
        selectedObjetosHermanos = selectedObjetosHijos;
        setSelected(selectedObjetosHijos);
    }

    /**
     * @return the selected
     */
    public CntEntidad getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(CntEntidad selected) {
        this.selected = selected;
    }

    /**
     * @return the eleccionTodos
     */
    public Boolean getEleccionTodos() {
        return eleccionTodos;
    }

    /**
     * @param eleccionTodos the eleccionTodos to set
     */
    public void setEleccionTodos(Boolean eleccionTodos) {
        this.eleccionTodos = eleccionTodos;
    }

    //// Codigo nurvo///////
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
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

    /**
     * @return the entidadSel
     */
    public CntEntidad getEntidadSel() {
        return entidadSel;
    }

    /**
     * @param entidadSel the entidadSel to set
     */
    public void setEntidadSel(CntEntidad entidadSel) {
        this.entidadSel = entidadSel;
    }

    /*
     * Metodos añadidos por Jonas Broooowwww
     */
    public List<CntEntidad> cntEntidadListaHermanos() {
        try {
            if (selected != null) {
                cntHermanosList = cntEntidadesService.obtieneHermanosPorCntEntidad(selected);
            }
        } catch (Exception e) {
        }
        return cntHermanosList;
    }
    
    public void mostrarEntidadSel_action(ActionEvent actionEvent)
    {           
    }
}
