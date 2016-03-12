package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import com.bap.erp.modelo.entidades.cnf.ParEstadoProyecto;
import com.bap.erp.modelo.servicios.cnt.CntProyectoService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

@ManagedBean(name = "cntProyectosModificacionBacking")
@ViewScoped
public class CntProyectosModificacionBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntProyectosModificacionBacking.class);
    @ManagedProperty(value = "#{cntProyectoService}")
    private CntProyectoService cntProyectoService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    private CntProyecto cntProyecto;
    private List<ParEstadoProyecto> listaDeEstadosDeProyecto = new ArrayList<ParEstadoProyecto>();

    public CntProyectosModificacionBacking() {
    }

    @PostConstruct
    public void initCntProyectosBacking() {
        cntProyecto = cntProyectoService.find(CntProyecto.class, getFromSessionIdProyecto());
    }

    public String guardaProyecto() {
        try {
            if (!cntProyectoService.datosRepetidosModificacion(cntProyecto).equals("")) {
                MessageUtils.addErrorMessage(cntProyectoService.datosRepetidosModificacion(cntProyecto));
                return null;
            }
            super.setMergeValues(cntProyecto);
            cntProyectoService.mergeCntProyecto(cntProyecto);
            MessageUtils.addInfoMessage("Se Modificó el Proyecto Correctamente.");
        } catch (Exception e) {
            MessageUtils.addErrorMessage("No se pudo modificar el Proyecto");
            return null;
        }
        return "proyectos";
    }

    public String cancelaGuardaProyecto() {
        limpiarVariablesSession();
        return "proyectos";
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        CntProyectosModificacionBacking.log = log;
    }

    public CntProyectoService getCntProyectoService() {
        return cntProyectoService;
    }

    public void setCntProyectoService(CntProyectoService cntProyectoService) {
        this.cntProyectoService = cntProyectoService;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public CntProyecto getCntProyecto() {
        return cntProyecto;
    }

    public void setCntProyecto(CntProyecto cntProyecto) {
        this.cntProyecto = cntProyecto;
    }

    public List<ParEstadoProyecto> getListaDeEstadosDeProyecto() {
        if (listaDeEstadosDeProyecto.isEmpty()) {
            listaDeEstadosDeProyecto = parParametricasService.listaEstadosDeProyectos();
        }
        return listaDeEstadosDeProyecto;
    }

    public void setListaDeEstadosDeProyecto(List<ParEstadoProyecto> listaDeEstadosDeProyecto) {
        this.listaDeEstadosDeProyecto = listaDeEstadosDeProyecto;
    }
}
