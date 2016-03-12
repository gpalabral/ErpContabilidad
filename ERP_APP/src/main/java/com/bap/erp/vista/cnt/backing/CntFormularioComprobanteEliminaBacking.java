package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.enums.EnumEstado;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.enums.EnumTipoComprobante;
import com.bap.erp.modelo.enums.EnumTipoMoneda;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntParametroAutomaticoService;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "cntFormularioComprobanteEliminaBacking")
@ViewScoped
public class CntFormularioComprobanteEliminaBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;
    @ManagedProperty(value = "#{cntTipoCambioService}")
    private CntTipoCambioService cntTipoCambioService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntParametroAutomaticoService}")
    private CntParametroAutomaticoService cntParametroAutomaticoService;
    private CntComprobante cntComprobante;
    private String tituloTipoComprobante;
    private String tituloTipoMoneda;
    private String tipoMoneda;
    private BigDecimal convierte;
    private BigDecimal sumaTotalDebe;
    private BigDecimal sumaTotalHaber;
    private List<CntDetalleComprobante> cntDetalleComprobantesList = new ArrayList<CntDetalleComprobante>();
    private Date fechaActual;
    private List<ParTipoComprobante> iiposComprobantesList;
    private Boolean swDebe;
    private Boolean swHaber;
    private CntDetalleComprobante selecDetalleComprobanteParaInsertar;
    private BigDecimal diferencia;
    private String remitente;
    private String colorEstado;

    public CntFormularioComprobanteEliminaBacking() {
    }

    @PostConstruct
    void initCntFormularioComprobanteEliminaBacking() {
        try {
            fechaActual = new Date();
            if (super.getFromSessionIdComprobante() != null) {
                cntComprobante = cntComprobantesService.find(CntComprobante.class, super.getFromSessionIdComprobante());
                inicializaDatos();
                remitente = cntDetalleComprobanteService.encuentraTipoComprobante(cntComprobante);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inicializaDatos() throws Exception {//esta bien
        cntComprobante.setNumero(cntComprobantesService.ultimoNumeroComprobante(cntComprobante));
        tituloTipoComprobante = cntComprobante.getParTipoComprobante().getDescripcion();
        tipoMoneda = EnumTipoMoneda.BOLIVIANOS.getCodigo();
        tituloTipoMoneda = "BOLIVIANOS";
        convierte = new BigDecimal("1");
        sumaTotalDebe = cntDetalleComprobanteService.sumaDebeComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante));
        sumaTotalHaber = cntDetalleComprobanteService.sumaHaberComprobante(cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante));
        diferencia = sumaTotalDebe.subtract(sumaTotalHaber);
        if (diferencia.signum() == -1) {
            diferencia = diferencia.multiply(new BigDecimal("-1"));
        }
        cntDetalleComprobantesList = cntDetalleComprobanteService.ordenaSegunPosicion(cntComprobante);
    }

    public String eliminaComprobante() {
        try {
            setRemoveValues(cntComprobante);
            cntComprobantesService.removeCntComprobanteCntDetalleComprobanteCntFacturacion(cntComprobante);
            return "comprobantesList";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String irComprobante() {
        setInSessionIdDetalleComprobante(null);
        return "comprobantesList";
    }

    public List<CntEntidad> completaDescripcionCuenta(String descripcion) {
        List<CntEntidad> listaDeSugerencias;
        listaDeSugerencias = cntEntidadesService.listaCuentasDeUltimoNivelPorDescripcion(descripcion);
        return listaDeSugerencias;
    }

    public void actualizaTituloTipoMoneda(ValueChangeEvent e) throws Exception {
        actualizaValorAndConvierteMoneda();

    }

    public void actualizaValorAndConvierteMoneda() {
        if (cntComprobante.getTipoMoneda().equals(EnumTipoMoneda.BOLIVIANOS.getCodigo())) {
            tituloTipoMoneda = "BOLIVIANOS";
            convierte = new BigDecimal("1");
        } else {
            if (cntComprobante.getTipoMoneda().equals(EnumTipoMoneda.DOLAR.getCodigo())) {
                tituloTipoMoneda = "DOLLAR";
                if (cntComprobante.getTipoCambio() != null) {
                    convierte = cntComprobante.getTipoCambio();
                }
            }
        }
    }

    public List<CntDetalleComprobante> listaDeCuentasPorComprobante() {
        try {

            if (cntDetalleComprobantesList.isEmpty() || cntDetalleComprobantesList == null) {
                cntDetalleComprobantesList = cntDetalleComprobanteService.ordenaSegunPosicion(cntComprobante);
            }
            return cntDetalleComprobantesList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    public void sumaDebeHaberComprobante() {
        sumaTotalDebe = cntDetalleComprobanteService.sumaDebeComprobante(cntDetalleComprobantesList);
        sumaTotalHaber = cntDetalleComprobanteService.sumaHaberComprobante(cntDetalleComprobantesList);
    }

    public String cancelaGuardadoComprobante() {
        setInSessionIdComprobante(cntComprobante.getIdComprobante());
        return "formularioComprobante";
    }

    public String cambiaColorEstadoCC(String estado) {
        if (estado.equals(EnumEstado.CONFIRMADO.getCodigo())) {
            colorEstado = "#009999";
        } else {
            if (estado.equals(EnumEstado.ANULADO.getCodigo())) {
                colorEstado = "#D0332E";
            } else {
                colorEstado = "#E1E463";
            }
        }
        return colorEstado;
    }

    public List<ParTipoComprobante> listaTiposComprobantes() {
        return parParametricasService.listaTiposComprobantes();
    }

    public Boolean cambiaColor(String estado) {

        if (estado.equals(EnumEstado.PENDIENTE.getCodigo())) {
            return true;
        }
        return false;

    }

    public String tipoDeTransaccion(CntDetalleComprobante cntDetalleComprobante) {
        return cntDetalleComprobanteService.tipoDeTransaccionPorDetalleComprobante(cntDetalleComprobante);
    }

    public String cambiaFormatoMoneda(BigDecimal valor) {
        return cntDetalleComprobanteService.cambiaFormatoMoneda(valor);
    }

    public String iconoRegistra() {
        return EnumIconosPrimeFaces.GUARDAR.getCodigo();
    }

    public String iconoEdita() {
        return EnumIconosPrimeFaces.MODIFICAR.getCodigo();
    }

    public String iconoElimina() {
        return EnumIconosPrimeFaces.ELIMINAR.getCodigo();
    }

    public String iconoAtras() {
        return EnumIconosPrimeFaces.ATRAS.getCodigo();
    }

    public CntComprobante getCntComprobante() {
        return cntComprobante;
    }

    public void setCntComprobante(CntComprobante cntComprobante) {
        this.cntComprobante = cntComprobante;
    }

    public CntComprobantesService getCntComprobantesService() {
        return cntComprobantesService;
    }

    public void setCntComprobantesService(CntComprobantesService cntComprobantesService) {
        this.cntComprobantesService = cntComprobantesService;
    }

    public String getTituloTipoComprobante() {
        return tituloTipoComprobante;
    }

    public void setTituloTipoComprobante(String tituloTipoComprobante) {
        this.tituloTipoComprobante = tituloTipoComprobante;
    }

    public String getTituloTipoMoneda() {
        return tituloTipoMoneda;
    }

    public void setTituloTipoMoneda(String tituloTipoMoneda) {
        this.tituloTipoMoneda = tituloTipoMoneda;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public CntTipoCambioService getCntTipoCambioService() {
        return cntTipoCambioService;
    }

    public void setCntTipoCambioService(CntTipoCambioService cntTipoCambioService) {
        this.cntTipoCambioService = cntTipoCambioService;
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public List<CntDetalleComprobante> getCntDetalleComprobantesList() {
        return cntDetalleComprobantesList;
    }

    public void setCntDetalleComprobantesList(List<CntDetalleComprobante> cntDetalleComprobantesList) {
        this.cntDetalleComprobantesList = cntDetalleComprobantesList;
    }

    public BigDecimal getConvierte() {
        return convierte;
    }

    public void setConvierte(BigDecimal convierte) {
        this.convierte = convierte;
    }

    public BigDecimal getSumaTotalDebe() {
        return sumaTotalDebe;
    }

    public void setSumaTotalDebe(BigDecimal sumaTotalDebe) {
        this.sumaTotalDebe = sumaTotalDebe;
    }

    public BigDecimal getSumaTotalHaber() {
        return sumaTotalHaber;
    }

    public void setSumaTotalHaber(BigDecimal sumaTotalHaber) {
        this.sumaTotalHaber = sumaTotalHaber;
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public List<ParTipoComprobante> getIiposComprobantesList() {
        return iiposComprobantesList;
    }

    public void setIiposComprobantesList(List<ParTipoComprobante> iiposComprobantesList) {
        this.iiposComprobantesList = iiposComprobantesList;
    }

    public CntParametroAutomaticoService getCntParametroAutomaticoService() {
        return cntParametroAutomaticoService;
    }

    public void setCntParametroAutomaticoService(CntParametroAutomaticoService cntParametroAutomaticoService) {
        this.cntParametroAutomaticoService = cntParametroAutomaticoService;
    }

    public Boolean getSwDebe() {
        return swDebe;
    }

    public void setSwDebe(Boolean swDebe) {
        this.swDebe = swDebe;
    }

    public Boolean getSwHaber() {
        return swHaber;
    }

    public void setSwHaber(Boolean swHaber) {
        this.swHaber = swHaber;
    }

    public CntDetalleComprobante getSelecDetalleComprobanteParaInsertar() {
        return selecDetalleComprobanteParaInsertar;
    }

    public void setSelecDetalleComprobanteParaInsertar(CntDetalleComprobante selecDetalleComprobanteParaInsertar) {
        this.selecDetalleComprobanteParaInsertar = selecDetalleComprobanteParaInsertar;
    }

    public BigDecimal getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(BigDecimal diferencia) {
        this.diferencia = diferencia;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getColorEstado() {
        return colorEstado;
    }

    public void setColorEstado(String colorEstado) {
        this.colorEstado = colorEstado;
    }
}
