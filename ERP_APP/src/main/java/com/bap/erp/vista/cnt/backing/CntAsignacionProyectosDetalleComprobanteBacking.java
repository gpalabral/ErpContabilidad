/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntProyectoService;
import com.bap.erp.vista.common.AbstractManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

@ManagedBean(name = "cntAsignacionProyectosDetalleComprobanteBacking")
@ViewScoped
public class CntAsignacionProyectosDetalleComprobanteBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntAsignacionProyectosDetalleComprobanteBacking.class);
    @ManagedProperty(value = "#{cntProyectoService}")
    private CntProyectoService cntProyectoService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    List<CntProyecto> listaDeProyectos = new ArrayList<CntProyecto>();
    CntDetalleComprobante cntDetalleComprobante;
    CntProyecto cntProyectoElegido;

    public CntAsignacionProyectosDetalleComprobanteBacking() {
    }

    @PostConstruct
    public void initCntProyectoBacking()  {
        
        try {
         if (getFromSessionIdDetalleComprobante() != null) {
             cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
             listaDeProyectos = cntProyectoService.listaCntProyectosOrdenados();
             if (cntDetalleComprobante.getIdProyecto() != null) {
                 cntProyectoElegido = cntProyectoService.find(CntProyecto.class, cntDetalleComprobante.getIdProyecto());
             }
         } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    public String limpiaEleccion() {
        cntProyectoElegido = null;
        return null;
    }

    public String guardaAsignacionDeProyecto() throws Exception {
        if (cntProyectoElegido != null) {
            cntDetalleComprobanteService.asignaProyectoADetalleComprobante(cntDetalleComprobante, cntProyectoElegido);
        }
       
        setInSessionIdDetalleComprobante(null);
        setInSessionIdComprobante(cntDetalleComprobante.getCntComprobante().getIdComprobante());
        return "formularioComprobante";
    }


    public String insertaPuntosAutomatio(CntProyecto cntProyecto) {
        return cntProyectoService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntProyecto);
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public List<CntProyecto> getListaDeProyectos() {
        return listaDeProyectos;
    }

    public void setListaDeProyectos(List<CntProyecto> listaDeProyectos) {
        this.listaDeProyectos = listaDeProyectos;
    }

    public CntProyectoService getCntProyectoService() {
        return cntProyectoService;
    }

    public void setCntProyectoService(CntProyectoService cntProyectoService) {
        this.cntProyectoService = cntProyectoService;
    }

    public CntDetalleComprobante getCntDetalleComprobante() {
        return cntDetalleComprobante;
    }

    public void setCntDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        this.cntDetalleComprobante = cntDetalleComprobante;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        CntAsignacionProyectosDetalleComprobanteBacking.log = log;
    }

    public CntProyecto getCntProyectoElegido() {
        return cntProyectoElegido;
    }

    public void setCntProyectoElegido(CntProyecto cntProyectoElegido) {
        this.cntProyectoElegido = cntProyectoElegido;
    }
}
