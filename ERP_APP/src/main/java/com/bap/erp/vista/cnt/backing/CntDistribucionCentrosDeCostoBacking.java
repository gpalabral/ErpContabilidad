package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDistribucionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnf.ParRecetasDistribucionCentroCosto;
import com.bap.erp.modelo.enums.EnumConfirmacion;
import com.bap.erp.modelo.enums.EnumEmpresa_Varios;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.servicios.cnt.CntConfiguracionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntDistribucionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "cntDistribucionCentrosDeCostoBacking")
@ViewScoped
public class CntDistribucionCentrosDeCostoBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntConfiguracionCentroCostoService}")
    private CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService;
    @ManagedProperty(value = "#{cntDistribucionCentroCostoService}")
    private CntDistribucionCentroCostoService cntDistribucionCentroCostoService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    private CntDetalleComprobante cntDetalleComprobante;
    private CntDetalleComprobante cntDetalleComprobanteParaVista;
    private List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList = new ArrayList<CntDistribucionCentrocosto>();
    private CntDistribucionCentrocosto cntDistribucionCentrocostoSelect;
    private BigDecimal monto;
    private BigDecimal porcentajeInicial;
    private BigDecimal montoInicial;
    private Boolean swPorcentaje = false;
    private Boolean swMonto = false;
    private BigDecimal variableRestoPorcentaje = new BigDecimal(BigInteger.ZERO);
    private BigDecimal variableResto = new BigDecimal(BigInteger.ZERO);
    private BigDecimal montoTotal = new BigDecimal(BigInteger.ZERO);
    private BigDecimal promedioTotal = new BigDecimal(BigInteger.ZERO);
    private List<ParRecetasDistribucionCentroCosto> listaRecetasDistribucionCentroCostos;
    private List<ParRecetasDistribucionCentroCosto> listaRecetas;
    private Boolean habilitaAlternativa = true;
    private Boolean habilitaDefinicion = false;
    private String codigoReceta = "0";
    private Boolean habilitaEditarPorcentajes = false;
    private String tituloListaTipoDistribucion = "Libre";
    private String variableFocus = "libre";
    private boolean valorDefinicion = false;
    private boolean valorLibre = true;
    private boolean valorAlterna = false;
    private Boolean cargaListaLibre = true;
    private BigDecimal totalRestoPorcentaje = new BigDecimal(BigInteger.ZERO);
    private BigDecimal totalResto = new BigDecimal("100.00");
    private Boolean verificaCentroCostoDefinicion = false;

    public CntDistribucionCentrosDeCostoBacking() {
    }

    @PostConstruct
    void initCntAsignacionCentrosDeCostoBacking() {

        try {
            if (getFromSessionIdDetalleComprobante() != null) {
                cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
                monto = cntDetalleComprobante.getDebe() != null ? cntDetalleComprobante.getDebe() : cntDetalleComprobante.getHaber();
                cntDistribucionCentrocostoList = cntDistribucionCentroCostoService.listaCombinadaCentrosDeCostoConDistribucionCentroCostoList(cntDetalleComprobante, habilitaDefinicion, codigoReceta);
                if (cntDetalleComprobante.getIdAntecesor() != null) {
                    cntDistribucionCentrocostoList = cntDistribucionCentroCostoService.listaDistribucionCCParaModificacion(cntDetalleComprobante, cntDistribucionCentrocostoList);
                    promedioTotal = obtieneSumaPorcentajeTotalDeLista();
                    montoTotal = obtieneSumaMontoTotalDeLista();
                    totalRestoPorcentaje = totalResto.subtract(promedioTotal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String guardaDistribucionCentroDeCosto() throws Exception {

        if (promedioTotal.compareTo(new BigDecimal(100)) == 0) {

            cntDistribucionCentroCostoService.guardaListaDeCntDistribucionCentroCosto(cntDistribucionCentrocostoList, getLoginSession());
            setInSessionIdComprobante(cntDetalleComprobante.getCntComprobante().getIdComprobante());

            if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.AUXILIAR.getCodigo()).getValor()).equals("1")) {

                if (cntDetalleComprobante.getCntEntidad().getTieneAuxiliar().equals(EnumConfirmacion.SI.getCodigo())) {
                    setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                    return "asignacionDeAuxiliaresADetalleComprobante";
                }
            }
            if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.PROYECTO.getCodigo()).getValor()).equals("1")) {

                setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
                return "asignacionDeProyectosADetalleComprobante";
            }

            
            setInSessionIdDetalleComprobante(null);
            return "detalleComprobante";

        }
        MessageUtils.addErrorMessage("No se asignaron correctamente los porcentajes de los Centros de Costo");
        return null;
    }

    public String insertaPuntosAutomatio(CntEntidad cntEntidad) {
        return cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad);
    }

    public Boolean verificaEntidadSiEsNivelUno(CntEntidad cntEntidad) {
        return cntEntidad.getNivel() == 1 ? false : true;
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public void onEdit(RowEditEvent event) {
        if (swPorcentaje) {
            ((CntDistribucionCentrocosto) event.getObject()).setMonto(cntDistribucionCentroCostoService.obtieneMontoDesdeElPorcentaje(monto, ((CntDistribucionCentrocosto) event.getObject()).getPorcentaje()));
            ((CntDistribucionCentrocosto) event.getObject()).setPorcentaje(((CntDistribucionCentrocosto) event.getObject()).getPorcentaje().setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            if (swMonto) {
                ((CntDistribucionCentrocosto) event.getObject()).setPorcentaje(cntDistribucionCentroCostoService.obtienePorcentajeDesdeElMonto(monto, ((CntDistribucionCentrocosto) event.getObject()).getMonto()));
                ((CntDistribucionCentrocosto) event.getObject()).setMonto(((CntDistribucionCentrocosto) event.getObject()).getMonto().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        promedioTotal = obtieneSumaPorcentajeTotalDeLista();
        montoTotal = obtieneSumaMontoTotalDeLista();
        totalRestoPorcentaje = totalResto.subtract(promedioTotal);
    }
//carga la lista con distribucion por definicion

    public void enviaRegistroSiguienteListaDefinicion() throws Exception {
        habilitaDefinicion = true;
        codigoReceta = "0";
        habilitaAlternativa = true;
        habilitaEditarPorcentajes = true;
        tituloListaTipoDistribucion = "Definición";
        variableFocus = "definicion";
        valorDefinicion = true;
        valorAlterna = false;
        valorLibre = false;
        verificaCentroCostoDefinicion = cntEntidadesService.verificaEntidadConCentroCosto(cntDetalleComprobante.getCntEntidad());
        cntDistribucionCentrocostoList = cntDistribucionCentroCostoService.listaCombinadaCentrosDeCostoConDistribucionCentroCostoList(cntDetalleComprobante, habilitaDefinicion, codigoReceta);
        if (!cntEntidadesService.verificaEntidadConCentroCosto(cntDetalleComprobante.getCntEntidad())) {
            habilitaDefinicion = false;
            codigoReceta = "0";
            habilitaEditarPorcentajes = false;
            habilitaAlternativa = true;
            tituloListaTipoDistribucion = "Libre";
            variableFocus = "libre";
            valorDefinicion = false;
            valorAlterna = false;
            valorLibre = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje Error:", "La cuenta " + cntDetalleComprobante.getCntEntidad().getMascaraGenerada() + " no tiene asignado Centro de Costo por Definición"));
        }
        cargaListaLibre = true;
        promedioTotal = obtieneSumaPorcentajeTotalDeLista();
        montoTotal = obtieneSumaMontoTotalDeLista();
        totalRestoPorcentaje = totalResto.subtract(promedioTotal);
    }
//carga la alista con distribucion libre*************************************

    public void enviaRegistroSiguienteLista() throws Exception {
        habilitaDefinicion = false;
        codigoReceta = "0";
        habilitaEditarPorcentajes = false;
        habilitaAlternativa = true;
        tituloListaTipoDistribucion = "Libre";
        variableFocus = "libre";
        valorDefinicion = false;
        valorAlterna = false;
        valorLibre = true;
        if (cntDetalleComprobante.getIdAntecesor() != null) {
            cntDistribucionCentrocostoList = cntDistribucionCentroCostoService.listaDistribucionCCParaModificacion(cntDetalleComprobante, cntDistribucionCentrocostoList);
        } else {
            cntDistribucionCentrocostoList = cntDistribucionCentroCostoService.listaCombinadaCentrosDeCostoConDistribucionCentroCostoList(cntDetalleComprobante, habilitaDefinicion, codigoReceta);
        }
        cargaListaLibre = true;
        promedioTotal = obtieneSumaPorcentajeTotalDeLista();
        montoTotal = obtieneSumaMontoTotalDeLista();
        totalRestoPorcentaje = totalResto.subtract(promedioTotal);
    }

    //activa el combo para la eleccion de recetas**************************************
    public void activaRecetas(ValueChangeEvent e) throws Exception {
        habilitaAlternativa = false;
        habilitaDefinicion = false;
        habilitaEditarPorcentajes = true;
        tituloListaTipoDistribucion = "Alternativa";
        variableFocus = "alternativa";
        valorDefinicion = false;
        valorAlterna = true;
        valorLibre = false;
        listaRecetas = parParametricasService.listaDistribucionCentroCostos();
        if (!listaRecetas.isEmpty()) {
            codigoReceta = listaRecetas.get(0).getCodigo();
        } else {
            codigoReceta = "0";
        }
        cntDistribucionCentrocostoList = cntDistribucionCentroCostoService.listaCombinadaCentrosDeCostoConDistribucionCentroCostoList(cntDetalleComprobante, habilitaDefinicion, codigoReceta);
        cargaListaLibre = true;
        promedioTotal = obtieneSumaPorcentajeTotalDeLista();
        montoTotal = obtieneSumaMontoTotalDeLista();
        totalRestoPorcentaje = totalResto.subtract(promedioTotal);
    }

    public List<ParRecetasDistribucionCentroCosto> listadoRecetas() {
        if (listaRecetasDistribucionCentroCostos == null || listaRecetasDistribucionCentroCostos.isEmpty()) {
            listaRecetasDistribucionCentroCostos = parParametricasService.listaDistribucionCentroCostos();
        }
        return listaRecetasDistribucionCentroCostos;
    }

    //carga la distribucion por recetas
    public void distribucionAlternativa(ValueChangeEvent e) throws Exception {
        habilitaDefinicion = false;
        variableFocus = "alternativa";
        cntDistribucionCentrocostoList = cntDistribucionCentroCostoService.listaCombinadaCentrosDeCostoConDistribucionCentroCostoList(cntDetalleComprobante, habilitaDefinicion, codigoReceta);
        cargaListaLibre = false;
    }

    public void onCancel(RowEditEvent event) {
//        ((CntDistribucionCentrocosto) event.getObject()).setPorcentaje(porcentajeInicial != null ? porcentajeInicial.setScale(2, BigDecimal.ROUND_HALF_UP) : porcentajeInicial);
//        ((CntDistribucionCentrocosto) event.getObject()).setMonto(montoInicial != null ? montoInicial.setScale(2, BigDecimal.ROUND_HALF_UP) : montoInicial);
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public CntDetalleComprobante getCntDetalleComprobante() {
        return cntDetalleComprobante;
    }

    public void setCntDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        this.cntDetalleComprobante = cntDetalleComprobante;
    }

    public CntConfiguracionCentroCostoService getCntConfiguracionCentroCostoService() {
        return cntConfiguracionCentroCostoService;
    }

    public void setCntConfiguracionCentroCostoService(CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService) {
        this.cntConfiguracionCentroCostoService = cntConfiguracionCentroCostoService;
    }

    public List<CntDistribucionCentrocosto> getCntDistribucionCentrocostoList() {
        return cntDistribucionCentrocostoList;
    }

    public void setCntDistribucionCentrocostoList(List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList) {
        this.cntDistribucionCentrocostoList = cntDistribucionCentrocostoList;
    }

    public CntDetalleComprobante getCntDetalleComprobanteParaVista() {
        return cntDetalleComprobanteParaVista;
    }

    public void setCntDetalleComprobanteParaVista(CntDetalleComprobante cntDetalleComprobanteParaVista) {
        this.cntDetalleComprobanteParaVista = cntDetalleComprobanteParaVista;
    }

    public CntDistribucionCentrocosto getCntDistribucionCentrocostoSelect() {
        return cntDistribucionCentrocostoSelect;
    }

    public void setCntDistribucionCentrocostoSelect(CntDistribucionCentrocosto cntDistribucionCentrocostoSelect) {
        this.cntDistribucionCentrocostoSelect = cntDistribucionCentrocostoSelect;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public CntDistribucionCentroCostoService getCntDistribucionCentroCostoService() {
        return cntDistribucionCentroCostoService;
    }

    public void setCntDistribucionCentroCostoService(CntDistribucionCentroCostoService cntDistribucionCentroCostoService) {
        this.cntDistribucionCentroCostoService = cntDistribucionCentroCostoService;
    }

    public void activaPorcentaje(ValueChangeEvent e) throws Exception {
        swPorcentaje = true;
        swMonto = false;
    }

    public void activaMonto(ValueChangeEvent e) throws Exception {
        swMonto = true;
        swPorcentaje = false;
    }

    public Boolean getSwPorcentaje() {
        return swPorcentaje;
    }

    public void setSwPorcentaje(Boolean swPorcentaje) {
        this.swPorcentaje = swPorcentaje;
    }

    public Boolean getSwMonto() {
        return swMonto;
    }

    public void setSwMonto(Boolean swMonto) {
        this.swMonto = swMonto;
    }

    public BigDecimal getPorcentajeInicial() {
        return porcentajeInicial;
    }

    public void setPorcentajeInicial(BigDecimal porcentajeInicial) {
        this.porcentajeInicial = porcentajeInicial;
    }

    public BigDecimal getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(BigDecimal montoInicial) {
        this.montoInicial = montoInicial;
    }

    //Codigo Jonas
    public BigDecimal obtieneSumaMontoTotalDeLista() {
        if (!cntDistribucionCentrocostoList.isEmpty()) {
            montoTotal = cntDistribucionCentroCostoService.obtieneSumaDeMontosListaCntDistribucionCentroCosto(cntDistribucionCentrocostoList);
        }
        return montoTotal;
    }

    public BigDecimal obtieneSumaPorcentajeTotalDeLista() {
        if (!cntDistribucionCentrocostoList.isEmpty()) {
            promedioTotal = cntDistribucionCentroCostoService.obtieneSumaDePorcentajesListaCntDistribucionCentroCosto(cntDistribucionCentrocostoList);
        }
        return promedioTotal;
    }
//codigo Chano

    public BigDecimal obtineRestoMontoTotalDeLista() {
        if (!cntDistribucionCentrocostoList.isEmpty()) {
            variableResto = cntDistribucionCentroCostoService.encuentraRestoDistribucionCentroDeCostos(cntDistribucionCentrocostoList, montoTotal);
        }
        return variableResto;
    }

    public BigDecimal obtineRestoMontoPorcentajeTotalDeLista() {
        if (!cntDistribucionCentrocostoList.isEmpty()) {
            variableRestoPorcentaje = cntDistribucionCentroCostoService.encuentraRestoPorcentajeDistribucionCentroDeCostos(cntDistribucionCentrocostoList, montoTotal);
        }
        return variableRestoPorcentaje;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public BigDecimal getPromedioTotal() {
        return promedioTotal;
    }

    public void setPromedioTotal(BigDecimal promedioTotal) {
        this.promedioTotal = promedioTotal;
    }

    //hasta aqui Codigo Jonas
    public BigDecimal getVariableRestoPorcentaje() {
        return variableRestoPorcentaje;
    }

    public void setVariableRestoPorcentaje(BigDecimal variableRestoPorcentaje) {
        this.variableRestoPorcentaje = variableRestoPorcentaje;
    }

    public BigDecimal getVariableResto() {
        return variableResto;
    }

    public void setVariableResto(BigDecimal variableResto) {
        this.variableResto = variableResto;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public List<ParRecetasDistribucionCentroCosto> getListaRecetasDistribucionCentroCostos() {
        return listaRecetasDistribucionCentroCostos;
    }

    public void setListaRecetasDistribucionCentroCostos(List<ParRecetasDistribucionCentroCosto> listaRecetasDistribucionCentroCostos) {
        this.listaRecetasDistribucionCentroCostos = listaRecetasDistribucionCentroCostos;
    }

    public Boolean getHabilitaAlternativa() {
        return habilitaAlternativa;
    }

    public void setHabilitaAlternativa(Boolean habilitaAlternativa) {
        this.habilitaAlternativa = habilitaAlternativa;
    }

    public Boolean getHabilitaDefinicion() {
        return habilitaDefinicion;
    }

    public void setHabilitaDefinicion(Boolean habilitaDefinicion) {
        this.habilitaDefinicion = habilitaDefinicion;
    }

    public String getCodigoReceta() {
        return codigoReceta;
    }

    public void setCodigoReceta(String codigoReceta) {
        this.codigoReceta = codigoReceta;
    }

    public Boolean getHabilitaEditarPorcentajes() {
        return habilitaEditarPorcentajes;
    }

    public void setHabilitaEditarPorcentajes(Boolean habilitaEditarPorcentajes) {
        this.habilitaEditarPorcentajes = habilitaEditarPorcentajes;
    }

    public String getTituloListaTipoDistribucion() {
        return tituloListaTipoDistribucion;
    }

    public void setTituloListaTipoDistribucion(String tituloListaTipoDistribucion) {
        this.tituloListaTipoDistribucion = tituloListaTipoDistribucion;
    }

    public String getVariableFocus() {
        return variableFocus;
    }

    public void setVariableFocus(String variableFocus) {
        this.variableFocus = variableFocus;
    }

    public boolean isValorDefinicion() {
        return valorDefinicion;
    }

    public void setValorDefinicion(boolean valorDefinicion) {
        this.valorDefinicion = valorDefinicion;
    }

    public boolean isValorLibre() {
        return valorLibre;
    }

    public void setValorLibre(boolean valorLibre) {
        this.valorLibre = valorLibre;
    }

    public boolean isValorAlterna() {
        return valorAlterna;
    }

    public void setValorAlterna(boolean valorAlterna) {
        this.valorAlterna = valorAlterna;
    }

    public Boolean getCargaListaLibre() {
        return cargaListaLibre;
    }

    public void setCargaListaLibre(Boolean cargaListaLibre) {
        this.cargaListaLibre = cargaListaLibre;
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

    public BigDecimal getTotalRestoPorcentaje() {
        return totalRestoPorcentaje;
    }

    public void setTotalRestoPorcentaje(BigDecimal totalRestoPorcentaje) {
        this.totalRestoPorcentaje = totalRestoPorcentaje;
    }

    public BigDecimal getTotalResto() {
        return totalResto;
    }

    public void setTotalResto(BigDecimal totalResto) {
        this.totalResto = totalResto;
    }

    public Boolean getVerificaCentroCostoDefinicion() {
        return verificaCentroCostoDefinicion;
    }

    public void setVerificaCentroCostoDefinicion(Boolean verificaCentroCostoDefinicion) {
        this.verificaCentroCostoDefinicion = verificaCentroCostoDefinicion;
    }

    public List<ParRecetasDistribucionCentroCosto> getListaRecetas() {
        return listaRecetas;
    }

    public void setListaRecetas(List<ParRecetasDistribucionCentroCosto> listaRecetas) {
        this.listaRecetas = listaRecetas;
    }
}
