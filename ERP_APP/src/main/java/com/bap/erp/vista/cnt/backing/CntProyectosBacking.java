package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import com.bap.erp.modelo.servicios.cnt.CntProyectoService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.iknow.utils.ObjectUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

@ManagedBean(name = "cntProyectosBacking")
@ViewScoped
public class CntProyectosBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntProyectosBacking.class);
    @ManagedProperty(value = "#{cntProyectoService}")
    private CntProyectoService cntProyectoService;
    private CntProyecto cntProyecto;
    private List<CntProyecto> listaProyectos;
    private List<CntProyecto> filteredCntProyectos;
    private CntProyecto seleccionCntProyecto = new CntProyecto();

    public CntProyectosBacking() {
    }

    @PostConstruct
    public void initCntProyectosBacking() {
    }

    public List<CntProyecto> listaProyectosOrdenados() throws Exception {
        if (listaProyectos == null || listaProyectos.isEmpty()) {
            listaProyectos = cntProyectoService.listaCntProyectosOrdenados();
        }
        return listaProyectos;
    }

    public String insertaPuntosAutomatio(CntProyecto cntProyecto) {
        return cntProyectoService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntProyecto);
    }

    public boolean isEditable() {
        if (cntProyecto == null) {
            return false;
        } else if (cntProyecto.getIdProyecto() == null) {
            return false;
        } else {
            return true;
        }
    }

    public String insertaTituloFormulario() {
        if (isEditable()) {
            return "Modificar / Eliminar Auxiliar";
        } else {
            return "Adicionar Auxiliar";
        }
    }

    public CntProyectoService getCntProyectoService() {
        return cntProyectoService;
    }

    public void setCntProyectoService(CntProyectoService cntProyectoService) {
        this.cntProyectoService = cntProyectoService;
    }

    public CntProyecto getCntProyecto() {
        return cntProyecto;
    }

    public void setCntProyecto(CntProyecto cntProyecto) {
        this.cntProyecto = cntProyecto;
    }

    public List<CntProyecto> getListaProyectos() {
        return listaProyectos;
    }

    public void setListaProyectos(List<CntProyecto> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    public List<CntProyecto> getFilteredCntProyectos() {
        return filteredCntProyectos;
    }

    public void setFilteredCntProyectos(List<CntProyecto> filteredCntProyectos) {
        this.filteredCntProyectos = filteredCntProyectos;
    }

    public CntProyecto getSeleccionCntProyecto() {
        return seleccionCntProyecto;
    }

    public void setSeleccionCntProyecto(CntProyecto seleccionCntProyecto) {
        this.seleccionCntProyecto = seleccionCntProyecto;
    }

    public String irAdicionProyecto() {
        if (cntProyectoService.verificaExistenciaDeProyectos(listaProyectos, seleccionCntProyecto)) {
            setInSessionIdProyecto(seleccionCntProyecto.getIdProyecto());
            return "proyectosForm";
        } else {
            if (listaProyectos.isEmpty()) {
                return "proyectosForm";
            } else {
                MessageUtils.addErrorMessage("No selecciono ningun Proyecto.");
                return null;
            }
        }
    }

    public String irModificacionProyecto() {
        if (seleccionCntProyecto != null) {
            setInSessionIdProyecto(seleccionCntProyecto.getIdProyecto());
            return "proyectosFormModificacion";
        } else {
            MessageUtils.addErrorMessage("No selecciono ningun Proyecto para Modificar");
        }
        return null;
    }

    public String eliminaProyecto() throws Exception {
        if (seleccionCntProyecto != null) {
            if (seleccionCntProyecto.getNivel() == 2) {
                int sw = 0;
                List<CntProyecto> listaHijos = cntProyectoService.listaHijosProyecto(seleccionCntProyecto);
                for (CntProyecto cntProyecto1 : listaHijos) {
                    if (cntProyectoService.verificaProyectoDetalleComprobante(cntProyecto1)) {
                        sw = 1;
                        break;
                    }
                }
                if (sw == 0) {
                    setRemoveValues(seleccionCntProyecto);                    
                    cntProyectoService.removeProyectosMasHijos(seleccionCntProyecto);
                } else {
                    MessageUtils.addErrorMessage("No se puede eliminar el proyecto seleccionado, tiene dependientes asiganados en un Detalle Comprobante");
                }
            } else {
                if (seleccionCntProyecto.getNivel() == 1) {
                    if (!cntProyectoService.verificaProyectoDetalleComprobante(seleccionCntProyecto)) {
                        try {
                            setRemoveValues(seleccionCntProyecto);
                            cntProyectoService.removeCntProyectos(seleccionCntProyecto);
                         
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        MessageUtils.addErrorMessage("No se puede eliminar el  proyecto seleccionado esta asiganado en un Detalle Comprobante");
                    }
                } else {
                    MessageUtils.addErrorMessage("El proyecto seleccionado debe de ser de ultimo nivel");
                }
            }
               return "proyectos";
        } else {
            MessageUtils.addErrorMessage("No selecciono ningun Proyecto para Modificar");
        }
        return null;
    }
}
