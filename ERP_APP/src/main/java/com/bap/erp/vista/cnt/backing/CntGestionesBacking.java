/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntGestion;
import com.bap.erp.modelo.entidades.cnf.ParDatosEmpresa;
import com.bap.erp.modelo.servicios.cnt.CntGestionesService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;



@ManagedBean(name = "cntGestionesBacking")
@ViewScoped
public class CntGestionesBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntGestionesBacking.class);
    
    @ManagedProperty(value = "#{cntGestionesService}")
    private CntGestionesService cntGestionesService;    
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;    
    private List<CntGestion> listaGestiones;
    private CntGestion cntGestionesSeleccionado;
    private List<CntGestion> listaGestionesFiltrada;      
    private CntGestion cntGestiones;
    
    public CntGestionesBacking() {
    }

    @PostConstruct
    public void initCntGestionesBacking() {        
        
    }    
    
    public List<CntGestion> listaTodasLasGestiones(){
        if (listaGestiones == null || listaGestiones.isEmpty()){
            listaGestiones = cntGestionesService.listaCrmGestiones();
        }
        return listaGestiones;
    }
        
    public void DatosEmpresa(){        
        List<ParDatosEmpresa> listaDatosEmpresa = parParametricasService.listaDatosDeEmpresa();     
    }
    
    public String irGestionesForm(){
       return "gestionesForm";
    }
    
    public boolean esEditable() {
        if (cntGestiones == null) {
            return false;
        } else if (cntGestiones.getIdGestion() == null) {
            return false;
        } else {
            return true;
        }
    }
        
    public CntGestionesService getCntGestionesService() {
        return cntGestionesService;
    }

    public void setCntGestionesService(CntGestionesService cntGestionesService) {
        this.cntGestionesService = cntGestionesService;
    }

    public List<CntGestion> getListaGestiones() {
        return listaGestiones;
    }

    public void setListaGestiones(List<CntGestion> listaGestiones) {
        this.listaGestiones = listaGestiones;
    }
    
    public List<CntGestion> getListaGestionesFiltrada() {
        return listaGestionesFiltrada;
    }

    public void setListaGestionesFiltrada(List<CntGestion> listaGestionesFiltrada) {
        this.listaGestionesFiltrada = listaGestionesFiltrada;
    }

    public CntGestion getCntGestionesSeleccionado() {
        return cntGestionesSeleccionado;
    }

    public void setCntGestionesSeleccionado(CntGestion cntGestionesSeleccionado) {
        this.cntGestionesSeleccionado = cntGestionesSeleccionado;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public CntGestion getCntGestiones() {
        return cntGestiones;
    }

    public void setCntGestiones(CntGestion cntGestiones) {
        this.cntGestiones = cntGestiones;
    }       
}
