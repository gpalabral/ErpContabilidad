/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.pojo.CntEntidadPojo;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.vista.common.AbstractManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

@ManagedBean(name = "cntPlanCuentasParametrizadoBacking")
@ViewScoped
public class CntPlanCuentasParametrizadoBacking extends AbstractManagedBean implements Serializable {
    
    private static Logger log = Logger.getLogger(CntPlanCuentasParametrizadoBacking.class);
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;    
    public List<CntEntidadPojo> listaCuentas = new ArrayList<CntEntidadPojo>();
    public String descripcion;
        
    public CntPlanCuentasParametrizadoBacking() {
    }

    @PostConstruct
    public void initCntPlanCuentasParametrizadoBacking() {    
        if(listaCuentas.isEmpty()){
            listaCuentas = cntEntidadesService.listaCuentasConParametrosAutomaticos();
        }
    }

      
    public String insertaPuntosAutomatio(CntEntidad cntEntidad) {
        return cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad);
    }
    
    public void buscarPorDescripcion(){        
        listaCuentas = cntEntidadesService.listaCuentasConParametrosAutomaticosPorDescripcion(descripcion);
    }
    
    public String salir(){
        return "paginaEnBlanco";
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        CntPlanCuentasParametrizadoBacking.log = log;
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public List<CntEntidadPojo> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(List<CntEntidadPojo> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
