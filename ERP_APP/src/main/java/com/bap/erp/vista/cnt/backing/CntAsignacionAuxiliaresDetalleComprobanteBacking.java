/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliarPorCuenta;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.enums.EnumConfirmacion;
import com.bap.erp.modelo.enums.EnumEmpresa_Varios;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

@ManagedBean(name = "cntAsignacionAuxiliaresDetalleComprobanteBacking")
@ViewScoped
public class CntAsignacionAuxiliaresDetalleComprobanteBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntAsignacionAuxiliaresDetalleComprobanteBacking.class);
    @ManagedProperty(value = "#{cntAuxiliaresService}")
    private CntAuxiliaresService cntAuxiliaresService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    private List<CntAuxiliarPorCuenta> listaDeAuxiliaresPorCuenta;
    private CntDetalleComprobante cntDetalleComprobante;
    private CntAuxiliarPorCuenta cntAuxiliarPorCuentaElegido = new CntAuxiliarPorCuenta();

    public CntAsignacionAuxiliaresDetalleComprobanteBacking() {
    }

    @PostConstruct
    public void initCntAuxiliarBacking() {
        if (getFromSessionIdDetalleComprobante() != null) {
            cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
            listaDeAuxiliaresPorCuenta = cntAuxiliaresService.listaCntAuxiliaresPorCuenta(cntDetalleComprobante.getCntEntidad());
            if (cntDetalleComprobante.getIdAuxiliar() != null) {
                cntAuxiliarPorCuentaElegido = cntAuxiliaresService.encuentraAuxiliarPorCuentaPorDetalleYAuxiliar(cntDetalleComprobante, cntAuxiliaresService.find(CntAuxiliar.class, cntDetalleComprobante.getIdAuxiliar()));
            }
        }
    }

    public String guardaAsignacionDeAuxiliar() throws Exception {
        if (cntAuxiliarPorCuentaElegido != null) {
            cntDetalleComprobanteService.asignaAuxiliaresADetalleComprobante(cntDetalleComprobante, cntAuxiliarPorCuentaElegido.getCntAuxiliar());
        } 
        if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.PROYECTO.getCodigo()).getValor()).equals("1")) {
            setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
            return "asignacionDeProyectosADetalleComprobante";
        } 
            setInSessionIdComprobante(cntDetalleComprobante.getCntComprobante().getIdComprobante());
            setInSessionIdDetalleComprobante(null);            
            return "detalleComprobante";
        
    }

    public String limpiaEleccion() {
        cntAuxiliarPorCuentaElegido = null;
        return null;
    }

    public CntAuxiliaresService getCntAuxiliaresService() {
        return cntAuxiliaresService;
    }

    public void setCntAuxiliaresService(CntAuxiliaresService cntAuxiliaresService) {
        this.cntAuxiliaresService = cntAuxiliaresService;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        CntAsignacionAuxiliaresDetalleComprobanteBacking.log = log;
    }

    public CntDetalleComprobante getCntDetalleComprobante() {
        return cntDetalleComprobante;
    }

    public void setCntDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        this.cntDetalleComprobante = cntDetalleComprobante;
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public List<CntAuxiliarPorCuenta> getListaDeAuxiliaresPorCuenta() {
        return listaDeAuxiliaresPorCuenta;
    }

    public void setListaDeAuxiliaresPorCuenta(List<CntAuxiliarPorCuenta> listaDeAuxiliaresPorCuenta) {
        this.listaDeAuxiliaresPorCuenta = listaDeAuxiliaresPorCuenta;
    }

    public CntAuxiliarPorCuenta getCntAuxiliarPorCuentaElegido() {
        return cntAuxiliarPorCuentaElegido;
    }

    public void setCntAuxiliarPorCuentaElegido(CntAuxiliarPorCuenta cntAuxiliarPorCuentaElegido) {
        this.cntAuxiliarPorCuentaElegido = cntAuxiliarPorCuentaElegido;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }
}
