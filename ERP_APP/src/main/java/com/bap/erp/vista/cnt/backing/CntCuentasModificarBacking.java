package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnf.ParAjustes;
import com.bap.erp.modelo.entidades.cnf.ParTipoRetencion;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico;
import com.bap.erp.modelo.enums.EnumAjuste;
import com.bap.erp.modelo.enums.EnumConfirmacion;
import com.bap.erp.modelo.enums.EnumEmpresa_Varios;
import com.bap.erp.modelo.enums.EnumFacturaCompraCombustible;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumParametroAutomatico;
import com.bap.erp.modelo.enums.EnumTieneCentroDeCosto;
import com.bap.erp.modelo.enums.EnumTipoAjuste;
import com.bap.erp.modelo.enums.EnumTipoCalculoAutomatico;
import com.bap.erp.modelo.enums.EnumTipoMovimiento;
import com.bap.erp.modelo.enums.EnumTipoRetencion;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresPorCuentaService;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import com.bap.erp.modelo.servicios.cnt.CntConfiguracionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntParametroAutomaticoService;
import com.bap.erp.modelo.wrapper.GestionContableWrapper;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "cntCuentasModificarBacking")
@ViewScoped
public class CntCuentasModificarBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntParametroAutomaticoService}")
    private CntParametroAutomaticoService cntParametroAutomaticoService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntConfiguracionCentroCostoService}")
    private CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService;
    @ManagedProperty(value = "#{cntAuxiliaresService}")
    private CntAuxiliaresService cntAuxiliaresService;
    @ManagedProperty(value = "#{cntAuxiliaresPorCuentaService}")
    private CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService;
    private List<CntEntidad> listPadres;
    private CntEntidad cntEntidad;
    private int numeroEspaciador = 10;
    private CntEntidad selectedEntidad;
    private CntParametroAutomatico cntParametroAutomaticoDeNivel2;
    private List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
    private List<CntEntidad> listaCentrosDeCosto;
    private BigDecimal sumaPorsentajeTotal = new BigDecimal("0.00");
    private BigDecimal disponibilidad = new BigDecimal("100.00");
    private Boolean activaBotonModificacionAsignacion = false;
    private Boolean esDefinicion = false;
    private Boolean tieneCentroDeCostoNivel4;
    private Boolean tieneCCMasivo;
    private Boolean botonCentroDeCostoHabilita;
    private Boolean botonIrACentroDeCosto = true;
    private Boolean centroDeCostoHabilita;
    private List<ParAjustes> parAjustesList = new ArrayList<ParAjustes>();
    private List<CntAuxiliar> selectAuxiliarParaAsignacionModificacion = new ArrayList<CntAuxiliar>();
    private Boolean swParAutomatico = false;
    private Boolean swActivaBoton = false;
    private String tipoCalculoAutomatico;
    private Boolean botonFacturaVentaHabilita;
    private Boolean botonFacturaCompraHabilita;
    private Boolean botonCreditoFiscalNoDeducibleHabilita;
    private Boolean botonRetencionesHabilita;
    private Boolean botonGrossinUpHabilita;
    private Boolean botonAjusteHabilita;
    private Boolean comboRetencionHabilita;
    private Boolean comboGrossinUpHabilita;
    private Boolean botonSinFacturaHabilita;
    private Boolean facDeCompraHabilita;
    private Boolean combustibleHabilita;
    private String combustible;
    private String modificarSubnivelesIndividualesMasivamente = "N";
    private Boolean facDeVentaHabilita;
    private Boolean noDeducibleHabilita;
    private String sinFactura = "NING";
    private Boolean sinfacturaHabilita;
    private Boolean sinfacturaDebe = false;
    private Boolean sinfacturaHaber = false;
    private Boolean retencionHabilita;
    private Boolean grossingUpActivar;
    private String retencion = "N";
    private String grossingUp = "SRET";
    private Boolean grossingHabilita;
    private String tipoMovimiento;
    private String ajuste = "SAJU";
    private Boolean botonIrAAuxiliar = true;
    private Boolean habilitaDialogoAuxiliar = true;
    private Boolean botonAuxiliarHabilita;
    private Boolean auxiliarHabilita = false;
    private Boolean desactivaRadioButtonSubNivel = true;
    private List<ParTipoRetencion> parRetencionesList = new ArrayList<ParTipoRetencion>();
    private String descripcion = "";
    private String mascaraNuevoOpcion = "";
    private String mascaraNivelPosicionUno = "";
    private String nivelIn;
    private String mascaraNivelPosicionDos = "";
    private String mascaraSubNivelPosicionUno = "";
    private String subNivelIn;
    private String mascaraSubNivelPosicionDos = "";
    private Boolean botonIrAsignacionAuxiliar = true;
    private String facDeCompra = "NING";
    private String facDeVenta = "NING";
    private CntParametroAutomatico cntParametroAutomatico;
    private String noDeducible = "NING";
    private List<CntEntidad> listaCentrosDeCostoElegidaParaQuitarse = new ArrayList<CntEntidad>();
    private CntConfiguracionCentrocosto configuracionCentrocosto = new CntConfiguracionCentrocosto();
    private List<CntConfiguracionCentrocosto> listaCentrosDeCostoElegidaParaQuitarseAux = new ArrayList<CntConfiguracionCentrocosto>();
    private List<CntAuxiliar> listaSelecionadosParaAsignarSelec = new ArrayList<CntAuxiliar>();
    private List<CntAuxiliar> selectAuxiliarParaAsignacion = new ArrayList<CntAuxiliar>();
    private Boolean ocultaPorNivel2 = true;

    public CntCuentasModificarBacking() {
    }

    @PostConstruct
    void initCntCuentasModificarBacking() {

        try {
            if (super.getFromSessionIdEntidad2() != null) {
                selectedEntidad = (CntEntidad) getCntEntidadesService().find(CntEntidad.class, super.getFromSessionIdEntidad2());
                cntParametroAutomaticoDeNivel2 = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(selectedEntidad);
                cargaDatosInicialesSoloParaModificacionDeCuenta();
                if (cntParametroAutomaticoDeNivel2 != null) {
                    cargaDatosDeParametrosAutomaticosDeNivel2();
                }
                verificaSiCuentaTieneAuxiliar(selectedEntidad);
                if (selectedEntidad.getHabilitaCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo())) {
                    listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
                    listaCentrosDeCosto = new ArrayList<CntEntidad>();
                    listaCntConfiguracionCentrocosto = cntEntidadesService.cargaListadoConfiguracionDesdeUnPlanCuentas(selectedEntidad);
                    listaCentrosDeCosto = cntEntidadesService.filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(listaCntConfiguracionCentrocosto);
                    sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
                    disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
                    activaBotonModificacionAsignacion = true;
                    esDefinicion = false;
                }
                if (selectedEntidad.getNivel() == 3) {
                    tieneCentroDeCostoNivel4 = parParametricasService.estaHabilitadoCCenDatosDeEmpresa();
//                 tieneCentroDeCostoNivel4 = true;
                    tieneCCMasivo = selectedEntidad.getHabilitaCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                }
                if (selectedEntidad.getNivel() == 2) {
                    ocultaPorNivel2 = false;
                }
                if (selectedEntidad.getNivel() == 1) {
                    botonCentroDeCostoHabilita = !selectedEntidad.getHabilitaCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                    centroDeCostoHabilita = selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                    botonIrACentroDeCosto = !selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                }
                if (selectedEntidad.getNivel() > 3) {
                    centroDeCostoHabilita = selectedEntidad.getHabilitaCentroCosto().equals(EnumTieneCentroDeCosto.NO.getCodigo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<CntEntidad> listadoDePadres() throws Exception {
        if (listPadres == null || listPadres.isEmpty()) {
            listPadres = cntEntidadesService.listaSuperiorGrupoDeObjetoSeleccionado(cntEntidad);
        }
        return listPadres;
    }

    public String muestraDescripcion(int value) {
        String descripcion = "";
        CntNivel cntNivel = cntEntidadesService.obtienedescripcionNivel(value);
        descripcion = cntNivel.getDescripcion();
        return descripcion;
    }

    public void cargaDatosInicialesSoloParaModificacionDeCuenta() throws Exception {
        GestionContableWrapper gestionContableWrapper = parParametricasService.factoryGestionContable();
        gestionContableWrapper.getNormaContable3();
        parAjustesList = parParametricasService.listaTiposDeAjuste(obtieneEnumTipoAjuste(gestionContableWrapper.getNormaContable3()));
        selectAuxiliarParaAsignacionModificacion = cntEntidadesService.listaDeAuxiliaresPorEntidad(selectedEntidad);
        switch (selectedEntidad.getNivel()) {
            case 1:
                swParAutomatico = true;
                numeroEspaciador = 200;
                break;
            case 2:
                swParAutomatico = true;
                swActivaBoton = true;
                numeroEspaciador = 200;
                break;
            case 3:
                swParAutomatico = false;
                numeroEspaciador = 1;
                break;
            default:
                break;
        }

        cntEntidad = (CntEntidad) getCntEntidadesService().find(CntEntidad.class, selectedEntidad.getIdEntidad());
    }

    public void cargaDatosDeParametrosAutomaticosDeNivel2() {
        tipoCalculoAutomatico = cntParametroAutomaticoDeNivel2.getTipoCalculoAutomatico();
        if (cntParametroAutomaticoDeNivel2.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.COMPRA.getCodigo())) {
            botonFacturaVentaHabilita = true;
        } else if (cntParametroAutomaticoDeNivel2.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.VENTA.getCodigo())) {
            botonFacturaCompraHabilita = true;
            botonCreditoFiscalNoDeducibleHabilita = true;
            botonRetencionesHabilita = true;
            botonGrossinUpHabilita = true;
            botonAjusteHabilita = true;
            comboRetencionHabilita = true;
            comboGrossinUpHabilita = true;
        } else if (cntParametroAutomaticoDeNivel2.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.NINGUNO.getCodigo())) {
            botonFacturaCompraHabilita = true;
            botonCreditoFiscalNoDeducibleHabilita = true;
            botonFacturaVentaHabilita = true;
            botonSinFacturaHabilita = true;
            botonRetencionesHabilita = true;
            botonGrossinUpHabilita = true;
            botonAjusteHabilita = true;
            comboRetencionHabilita = true;
            comboGrossinUpHabilita = true;
        }

        if (cntParametroAutomaticoDeNivel2.getFacturaCompra().equals(EnumFacturaCompraCombustible.SIN_COMBUSTIBLE.getCodigo())) {
            facDeCompraHabilita = true;
            combustibleHabilita = true;
            combustible = "N";
        } else {
            if (cntParametroAutomaticoDeNivel2.getFacturaCompra().equals(EnumFacturaCompraCombustible.CON_COMBUSTIBLE.getCodigo())) {
                facDeCompraHabilita = true;
                combustibleHabilita = true;
                combustible = "S";
            } else {
                facDeCompraHabilita = false;
                combustibleHabilita = false;
                combustible = null;
            }
        }

        if (!cntParametroAutomaticoDeNivel2.getFacturaVenta().equals(EnumParametroAutomatico.NINGUNO.getCodigo())) {
            facDeVentaHabilita = true;
        } else {
            facDeVentaHabilita = false;
        }

        if (!cntParametroAutomaticoDeNivel2.getCreditoFiscalNoDeducible().equals(EnumParametroAutomatico.NINGUNO.getCodigo())) {
            noDeducibleHabilita = true;
        } else {
            noDeducibleHabilita = false;
        }

        sinFactura = cntParametroAutomaticoDeNivel2.getSinFactura();
        if (sinFactura.equals(EnumParametroAutomatico.NINGUNO.getCodigo())) {
            sinfacturaHabilita = false;
            sinfacturaDebe = false;
            sinfacturaHaber = false;
        }
        if (sinFactura.equals(EnumParametroAutomatico.AMBOS.getCodigo())) {
            sinfacturaHabilita = true;
            sinfacturaDebe = true;
            sinfacturaHaber = true;
        }
        if (sinFactura.equals(EnumParametroAutomatico.DEBE.getCodigo())) {
            sinfacturaHabilita = true;
            sinfacturaDebe = true;
            sinfacturaHaber = false;
        }
        if (sinFactura.equals(EnumParametroAutomatico.HABER.getCodigo())) {
            sinfacturaHabilita = true;
            sinfacturaDebe = false;
            sinfacturaHaber = true;
        }

        if (!cntParametroAutomaticoDeNivel2.getParTipoRetencion().getCodigo().equals(EnumConfirmacion.NO.getCodigo())) {
            retencionHabilita = true;
            grossingUpActivar = true;
        }

        retencion = cntParametroAutomaticoDeNivel2.getParTipoRetencion().getCodigo();
        if (!cntParametroAutomaticoDeNivel2.getParTipoRetencion().getCodigo().equals(EnumTipoRetencion.SIN_RETENCION.getCodigo())) {
            retencionHabilita = true;
            comboRetencionHabilita = false;
        } else {
            retencionHabilita = false;
            comboRetencionHabilita = true;
        }

        grossingUp = cntParametroAutomaticoDeNivel2.getParTipoRetencionGrossingUp().getCodigo();
        if (!cntParametroAutomaticoDeNivel2.getParTipoRetencionGrossingUp().getCodigo().equals(EnumTipoRetencion.SIN_RETENCION.getCodigo())) {
            grossingHabilita = true;
            comboGrossinUpHabilita = false;
        } else {
            grossingHabilita = false;
            comboGrossinUpHabilita = true;
        }
        tipoMovimiento = cntParametroAutomaticoDeNivel2.getTipoMovimiento();
        ajuste = cntParametroAutomaticoDeNivel2.getParAjustes().getCodigo();
    }

    public void verificaSiCuentaTieneAuxiliar(CntEntidad cuenta) throws Exception {
        if ((parParametricasService.findParValorContextoVarios(EnumEmpresa_Varios.AUXILIAR.getCodigo()).getValor()).equals("1")) {
            if (cuenta.getTieneAuxiliar().equals(EnumConfirmacion.SI.getCodigo())) {
                botonIrAAuxiliar = true;
                habilitaDialogoAuxiliar = false;
                botonAuxiliarHabilita = false;
                if (cntEntidadesService.verificaEntidadTieneAuxiliarAsignado(cuenta) == true) {
                    auxiliarHabilita = true;
                    botonIrAAuxiliar = false;
                } else {
                    auxiliarHabilita = false;
                    botonIrAAuxiliar = true;
                }
            }
        } else {
            botonAuxiliarHabilita = true;
            auxiliarHabilita = true;
        }

    }

    public String obtieneEnumTipoAjuste(String tipoAjuste) {
        if (tipoAjuste.equals("ANTERIOR")) {
            return EnumTipoAjuste.ANTERIOR.getCodigo();
        }
        return EnumTipoAjuste.ACTUAL.getCodigo();
    }

    public void verificaExistenciaDeCentrosDeCosto(ValueChangeEvent e) throws Exception {
        if (!cntEntidadesService.verificaSiEnUnRangoDeCuentasTieneAsignadosCentrosDeCosto(selectedEntidad)) {
            MessageUtils.addErrorMessage("Una de las cuentas ya tiene asignado Centros de Costo no puede cambiar su valor.");
        }
    }

    public void activaCalculoAutomatico(ValueChangeEvent e) {
        if (tipoCalculoAutomatico.equals(EnumTipoCalculoAutomatico.COMPRA.getCodigo())) {
            inicializaDatosPorDefectoParametrizacionautomatica();
            botonFacturaCompraHabilita = false;
            combustibleHabilita = false;
            botonCreditoFiscalNoDeducibleHabilita = false;
            botonFacturaVentaHabilita = true;
            botonSinFacturaHabilita = false;
            botonRetencionesHabilita = false;
            botonGrossinUpHabilita = false;
            botonAjusteHabilita = false;
            comboRetencionHabilita = true;
            comboGrossinUpHabilita = true;
        } else {
            if (tipoCalculoAutomatico.equals(EnumTipoCalculoAutomatico.VENTA.getCodigo())) {
                inicializaDatosPorDefectoParametrizacionautomatica();
                botonFacturaCompraHabilita = true;
                combustibleHabilita = false;
                botonCreditoFiscalNoDeducibleHabilita = true;
                botonFacturaVentaHabilita = false;
                botonSinFacturaHabilita = false;
                botonRetencionesHabilita = true;
                botonGrossinUpHabilita = true;
                botonAjusteHabilita = true;
                comboRetencionHabilita = true;
                comboGrossinUpHabilita = true;
            } else {
                inicializaDatosPorDefectoParametrizacionautomatica();
                botonFacturaCompraHabilita = true;
                combustibleHabilita = false;
                botonCreditoFiscalNoDeducibleHabilita = true;
                botonFacturaVentaHabilita = true;
                botonSinFacturaHabilita = true;
                botonRetencionesHabilita = true;
                botonGrossinUpHabilita = true;
                botonAjusteHabilita = true;
                comboRetencionHabilita = true;
                comboGrossinUpHabilita = true;
            }
        }
    }

    public void inicializaDatosPorDefectoParametrizacionautomatica() {
        ParTipoRetencion parTipoRetencionGrossing = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo());
        ParTipoRetencion parTipoRetencion = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo());
        ParAjustes parAjustes = (ParAjustes) parParametricasService.find(ParAjustes.class, EnumAjuste.SIN_AJUSTE.getCodigo());
        tipoMovimiento = EnumTipoMovimiento.AMBOS.getCodigo();
        facDeCompraHabilita = false;
        combustible = null;
        noDeducibleHabilita = false;
        facDeVentaHabilita = false;
        sinfacturaHabilita = false;
        sinfacturaDebe = null;
        sinfacturaHaber = null;
        retencionHabilita = false;
        retencion = parTipoRetencion.getCodigo();
        grossingHabilita = false;
        grossingUp = parTipoRetencionGrossing.getCodigo();
        ajuste = parAjustes.getCodigo();
    }

    public void activaCombustible(ValueChangeEvent e) {
        if (facDeCompraHabilita) {
            combustibleHabilita = true;
            combustible = "N";
        } else {
            combustibleHabilita = false;
            combustible = null;
        }
    }

    public void activaCreditoFiscal(ValueChangeEvent e) {
        if (noDeducibleHabilita) {
            noDeducibleHabilita = true;

        } else {
            noDeducibleHabilita = false;
        }
    }

    public void activaSinFactura(ValueChangeEvent e) {
        if (sinfacturaHabilita) {
            sinfacturaHabilita = true;
            sinfacturaDebe = true;

        } else {
            sinfacturaHabilita = false;
            sinfacturaDebe = null;
            sinfacturaHaber = null;
        }
    }

    public void activaRetencion(ValueChangeEvent e) {
        if (retencionHabilita) {
            comboRetencionHabilita = false;
            botonRetencionesHabilita = true;

        } else {
            ParTipoRetencion parTipoRetencion = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo());
            comboRetencionHabilita = false;
            botonRetencionesHabilita = false;
            retencion = parTipoRetencion.getCodigo();
        }
    }

    public List<ParTipoRetencion> listadoDeRetencion() throws Exception {
        if (parRetencionesList == null || parRetencionesList.isEmpty()) {
            parRetencionesList = parParametricasService.listaTipoRetencion();
        }
        return parRetencionesList;
    }

    public void activaGrossing(ValueChangeEvent e) {
        if (grossingHabilita) {
            comboGrossinUpHabilita = false;
        } else {
            ParTipoRetencion parTipoRetencionGrossingUp = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo());
            comboGrossinUpHabilita = true;
            grossingUp = parTipoRetencionGrossingUp.getCodigo();
        }
    }

    public void activaCentroDeCosto(ValueChangeEvent e) {
        if (centroDeCostoHabilita) {
            botonIrACentroDeCosto = false;
        } else {
            botonIrACentroDeCosto = true;
        }
    }

    public void inicializaValoresAsignacionCentrosDeCosto() throws Exception {
        cntEntidad = new CntEntidad();
        cntEntidad.setDescripcion(descripcion);
        if (mascaraNuevoOpcion.equals("N")) {
            cntEntidad.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos));
        } else {
            cntEntidad.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos));
        }
    }

    public void activaAuxiliar(ValueChangeEvent e) {
        if (auxiliarHabilita) {
            botonIrAAuxiliar = false;
            botonIrAsignacionAuxiliar = false;
        } else {
            botonIrAAuxiliar = true;
            botonIrAsignacionAuxiliar = true;
        }
    }

    public String concatenaNuevaMascaraDatosVista(String mascaraParteUno, String cajaDetexto, String mascaraParteDos) {
        return mascaraParteUno + cajaDetexto + mascaraParteDos;
    }

    public void inicializaValoresAsignacionAuxiliares() throws Exception {
    }

    public String mergeCntEntidadNivelDos_action() throws Exception {

        cntParametroAutomaticoDeNivel2 = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(selectedEntidad);
        if (cntParametroAutomaticoDeNivel2 != null) {
            ParAjustes parAjustes = (ParAjustes) parParametricasService.find(ParAjustes.class, ajuste);
            ParTipoRetencion parTipoRetencion = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, retencion);
            ParTipoRetencion parTipoRetencionGrossing = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, grossingUp);
            cntParametroAutomaticoDeNivel2.setCntEntidad(selectedEntidad);
            if (facDeCompraHabilita == true) {
                cntParametroAutomaticoDeNivel2.setFacturaCompra(facDeCompra);
            } else {
                cntParametroAutomaticoDeNivel2.setFacturaCompra(EnumParametroAutomatico.NINGUNO.getCodigo());
            }
            if (facDeVentaHabilita == true) {
                cntParametroAutomaticoDeNivel2.setFacturaVenta(facDeVenta);
            } else {
                cntParametroAutomaticoDeNivel2.setFacturaVenta(EnumParametroAutomatico.NINGUNO.getCodigo());
            }
            if (noDeducibleHabilita == true) {
                cntParametroAutomaticoDeNivel2.setCreditoFiscalNoDeducible(noDeducible);
            } else {
                cntParametroAutomaticoDeNivel2.setCreditoFiscalNoDeducible(EnumParametroAutomatico.NINGUNO.getCodigo());
            }
            if (sinfacturaHabilita == true) {
                if (sinfacturaDebe == true && sinfacturaHaber == true) {
                    cntParametroAutomaticoDeNivel2.setSinFactura(EnumParametroAutomatico.AMBOS.getCodigo());

                } else {
                    if (sinfacturaDebe == true) {
                        cntParametroAutomaticoDeNivel2.setSinFactura(EnumParametroAutomatico.DEBE.getCodigo());
                    }
                    if (sinfacturaHaber == true) {
                        cntParametroAutomaticoDeNivel2.setSinFactura(EnumParametroAutomatico.HABER.getCodigo());
                    }
                }
            } else {
                cntParametroAutomaticoDeNivel2.setSinFactura(EnumParametroAutomatico.NINGUNO.getCodigo());
            }
            if (retencionHabilita == true) {
                cntParametroAutomaticoDeNivel2.setParTipoRetencion(parTipoRetencion);
            } else {
                cntParametroAutomaticoDeNivel2.setParTipoRetencion((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo()));
            }
            if (grossingHabilita == true) {
                cntParametroAutomaticoDeNivel2.setParTipoRetencionGrossingUp(parTipoRetencionGrossing);
            } else {
                cntParametroAutomaticoDeNivel2.setParTipoRetencionGrossingUp((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo()));
            }
            cntParametroAutomaticoDeNivel2.setTipoMovimiento(tipoMovimiento);
            cntParametroAutomaticoDeNivel2.setParAjustes(parAjustes);
            //aumentado para que guarde el usuario logueado
            setMergeValues(cntParametroAutomaticoDeNivel2);
//            cntParametroAutomaticoDeNivel2.setUsuarioModificacion("TEST");
//            cntParametroAutomaticoDeNivel2.setFechaModificacion(new Date());
        }
        cntEntidadesService.mergeCntEntidadAndCntParametroAutomatico(selectedEntidad, cntParametroAutomaticoDeNivel2);
        return "planCuentas";
    }
//metodo de modificacion
    public String mergeCntEntidad_action() {
        try {
            cntParametroAutomaticoDeNivel2 = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(selectedEntidad);
            super.setMergeValues(selectedEntidad);
            if (cntParametroAutomaticoDeNivel2 != null) {
                cntParametroAutomaticoDeNivel2.setTipoCalculoAutomatico(tipoCalculoAutomatico);
                ParAjustes parAjustes = (ParAjustes) parParametricasService.find(ParAjustes.class, ajuste);
                ParTipoRetencion parTipoRetencion = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, retencion);
                ParTipoRetencion parTipoRetencionGrossing = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, grossingUp);
                cntParametroAutomaticoDeNivel2.setCntEntidad(selectedEntidad);
                if (facDeCompraHabilita == true) {
                    if (combustible.equals(EnumConfirmacion.NO.getCodigo())) {
                        cntParametroAutomaticoDeNivel2.setFacturaCompra(EnumFacturaCompraCombustible.SIN_COMBUSTIBLE.getCodigo());
                    } else {
                        if (combustible.equals(EnumConfirmacion.SI.getCodigo())) {
                            cntParametroAutomaticoDeNivel2.setFacturaCompra(EnumFacturaCompraCombustible.CON_COMBUSTIBLE.getCodigo());
                        } else {
                            cntParametroAutomaticoDeNivel2.setFacturaCompra(EnumFacturaCompraCombustible.NINGUNO.getCodigo());
                        }
                    }
                } else {
                    cntParametroAutomaticoDeNivel2.setFacturaCompra(EnumFacturaCompraCombustible.NINGUNO.getCodigo());
                }
                if (facDeVentaHabilita == true) {
                    cntParametroAutomaticoDeNivel2.setFacturaVenta(EnumParametroAutomatico.HABER.getCodigo());
                } else {
                    cntParametroAutomaticoDeNivel2.setFacturaVenta(EnumParametroAutomatico.NINGUNO.getCodigo());
                }
                if (noDeducibleHabilita == true) {
                    cntParametroAutomaticoDeNivel2.setCreditoFiscalNoDeducible(EnumParametroAutomatico.DEBE.getCodigo());
                } else {
                    cntParametroAutomaticoDeNivel2.setCreditoFiscalNoDeducible(EnumParametroAutomatico.NINGUNO.getCodigo());
                }

                if (sinfacturaHabilita == true) {
                    if (sinfacturaDebe == true && sinfacturaHaber == true) {
                        cntParametroAutomaticoDeNivel2.setSinFactura(EnumParametroAutomatico.AMBOS.getCodigo());
                    } else {
                        if (sinfacturaDebe == true) {
                            cntParametroAutomaticoDeNivel2.setSinFactura(EnumParametroAutomatico.DEBE.getCodigo());
                        }
                        if (sinfacturaHaber == true) {
                            cntParametroAutomaticoDeNivel2.setSinFactura(EnumParametroAutomatico.HABER.getCodigo());
                        }
                    }
                } else {
                    cntParametroAutomaticoDeNivel2.setSinFactura(EnumParametroAutomatico.NINGUNO.getCodigo());
                }

                if (retencionHabilita == true) {
                    cntParametroAutomaticoDeNivel2.setParTipoRetencion(parTipoRetencion);
                } else {
                    cntParametroAutomaticoDeNivel2.setParTipoRetencion((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo()));
                }
                if (grossingHabilita == true) {
                    cntParametroAutomaticoDeNivel2.setParTipoRetencionGrossingUp(parTipoRetencionGrossing);
                } else {
                    cntParametroAutomaticoDeNivel2.setParTipoRetencionGrossingUp((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo()));
                }
                cntParametroAutomaticoDeNivel2.setTipoMovimiento(tipoMovimiento);
                cntParametroAutomaticoDeNivel2.setParAjustes(parAjustes);
                setMergeValues(cntParametroAutomaticoDeNivel2);
            }
            if (selectedEntidad.getNivel() == 3) {
                //Permite verificar la existencia de Cuentas que tengan asignados CC, antes de modificar de forma masiva, 
                //a todas las cuentas dependientes a esta. NOTA: esto es cuando se selecciona una cuenta de nivel 3.
                if (cntEntidadesService.verificaSiEnUnRangoDeCuentasTieneAsignadosCentrosDeCosto(selectedEntidad)) {
                    selectedEntidad.setHabilitaCentroCosto(tieneCCMasivo ? "S" : "N");
                    selectedEntidad.setUsuarioModificacion(getLoginSession());
                    selectedEntidad.setFechaModificacion(new Date());
                    cntEntidadesService.modificaMasivoPlanDeCuentasSoloCampoTieneCentrosDeCosto(selectedEntidad);
                }
            } else {
//                selectedEntidad.setTieneCentroCosto(centroDeCostoHabilita ? EnumTieneCentroDeCosto.SI.getCodigo() : EnumTieneCentroDeCosto.NO.getCodigo());
                if (selectedEntidad.getNivel() == 2) {
                    if (selectedEntidad.getTieneCentroCosto().equals("S")) {
                        centroDeCostoHabilita = true;
                    } else {
                        centroDeCostoHabilita = false;
                    }
                }
                if (centroDeCostoHabilita == true) {
                    selectedEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.SI.getCodigo());
                } else {
                    selectedEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
                }
                if (selectedEntidad.getNivel() == 1) {
                    selectedEntidad.setParametrosIndividuales(EnumConfirmacion.SI.getCodigo());
                }
            }
            //Codigo de prueba se activo por henrry esta en verificacion
            System.out.println("el centroDeCostoHabilita--- "+centroDeCostoHabilita);
            if (centroDeCostoHabilita != null && centroDeCostoHabilita == true) {
                selectedEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.SI.getCodigo());
            } else {
                selectedEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
            }
            //FIN Codigo de prueba se activo por henrry esta en verificacion
            selectedEntidad.setTieneAuxiliar(auxiliarHabilita == true ? "S" : "N");
            cntEntidadesService.mergeCntEntidadAndCntParametroAutomatico(selectedEntidad, cntParametroAutomaticoDeNivel2);
            //aumentado para modificar auxiliares           
            super.setMergeValues(selectedEntidad);
            cntEntidadesService.mergeCntObjetosConAsignacionPorDefinicionConAsignacionAuxiliares(selectedEntidad, mascaraNuevoOpcion, cntParametroAutomatico, selectAuxiliarParaAsignacionModificacion, listaCntConfiguracionCentrocosto);
            if (selectedEntidad.getNivel() == 2) {
                if (modificarSubnivelesIndividualesMasivamente.equals("S")) {
                    cntParametroAutomaticoService.grabarParametrosHijosMasivoCondicionIndividual(selectedEntidad, getSessionManagedBean().getLogin(), modificarSubnivelesIndividualesMasivamente);
                    MessageUtils.addInfoMessage("Se modificaron todas las cuentas dependientes");
                } else {
                    cntParametroAutomaticoService.grabarParametrosHijosMasivo(selectedEntidad, getSessionManagedBean().getLogin());
                    MessageUtils.addInfoMessage("Se modificaron todas las cuentas no individuales");
                }
            }

            return "planCuentas";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void enviaTodosLosRegistroSiguienteLista() {
        try {
            listaCentrosDeCosto = cntEntidadesService.cntEntidadesParaCentrosDeCostoSoloNivelUnoFiltraList();
            listaCntConfiguracionCentrocosto = cntEntidadesService.llevaTodosLosCentrosDeCostoNivelUnoASiguienteListaConfiguracion();
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String cierrraDialogo(ValueChangeEvent e) {
        return "planCuentasModificar";
    }

    public String insertaPuntosAutomatio(CntEntidad cntEntidad) {
        return cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad);
    }

    public void enviaRegistroSiguienteLista() {
        try {
            if (!listaCentrosDeCostoElegidaParaQuitarse.isEmpty() && listaCentrosDeCostoElegidaParaQuitarse.size() == 1) {
                if (cntEntidadesService.verfificaExistenciaDeCentroDeCostoAntesDeAdicionar(listaCentrosDeCostoElegidaParaQuitarse.get(0), listaCntConfiguracionCentrocosto)) {
                    if (listaCentrosDeCostoElegidaParaQuitarse.get(0).getNivel() == 1) {
                        CntConfiguracionCentrocosto cntConfiguracionCentrocosto = (CntConfiguracionCentrocosto) configuracionCentrocosto.clone();
                        cntConfiguracionCentrocosto.setIdCentroCosto(listaCentrosDeCostoElegidaParaQuitarse.get(0));
                        listaCntConfiguracionCentrocosto.add(cntConfiguracionCentrocosto);
                    }
                    listaCentrosDeCosto = cntEntidadesService.filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(listaCntConfiguracionCentrocosto);
                }
            }
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quitaRegistroSiguienteLista() {
        try {
            if (!listaCentrosDeCostoElegidaParaQuitarseAux.isEmpty() && listaCentrosDeCostoElegidaParaQuitarseAux.size() == 1) {
                listaCntConfiguracionCentrocosto.remove(listaCentrosDeCostoElegidaParaQuitarseAux.get(0));
                listaCentrosDeCosto = cntEntidadesService.filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(listaCntConfiguracionCentrocosto);
            }
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviaTodosLosRegistroListaOriginalLista() {
        try {
            listaCentrosDeCosto = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
            listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEdit(RowEditEvent event) {
        try {
            if (!cntEntidadesService.validaSumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto)) {
                ((CntConfiguracionCentrocosto) event.getObject()).setPorcentaje(null);
//                MessageUtils.addErrorMessage("El valor del porcentaje sobrepasa el 100%.");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje Error:", "El porcentaje del codigo " + ((CntConfiguracionCentrocosto) event.getObject()).getIdCentroCosto().getMascaraGenerada() + " sobrepasa el 100%."));
            }
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Car Cancelled", ((CntConfiguracionCentrocosto) event.getObject()).getIdCentroCosto().getDescripcion());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String guardarCntConfiguracionCentroCostoPorDefinicion() throws Exception {
        if (!listaCntConfiguracionCentrocosto.isEmpty()) {
            cntConfiguracionCentroCostoService.guardaCntConfiguracionCentroCostoDefinicion(listaCntConfiguracionCentrocosto, getLoginSession(), cntEntidad);
            setInSessionIdEntidad2(cntEntidad.getIdEntidad());
            return "planCuentasModificar";
        }
        MessageUtils.addErrorMessage("No se asigno correctamente los Centros de Costo");
        return null;
    }

    public void guardarCntConfiguracionCentroCostoPorDefinicionAccion() {
        try {
            if (listaCntConfiguracionCentrocosto.isEmpty()) {
//                cntConfiguracionCentroCostoService.guardaCntConfiguracionCentroCostoDefinicion(listaCntConfiguracionCentrocosto, getLoginSession(), cntEntidad);
//                setInSessionIdEntidad2(cntEntidad.getIdEntidad());
                MessageUtils.addErrorMessage("No se asigno correctamente los Centros de Costo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void modificaCntConfiguracionCentroCostoPorDefinicion() throws Exception {
        if (cntEntidadesService.validaSumaTotalProcentajeAlCienPorcientoParaModificar(listaCntConfiguracionCentrocosto)) {
            if (!listaCntConfiguracionCentrocosto.isEmpty()) {
                cntConfiguracionCentroCostoService.modificarCntConfiguracionCentroCostoDefinicion(listaCntConfiguracionCentrocosto, getLoginSession(), cntEntidad);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje Error:", "La suma total de porcentajes no cumple 100%, no se puede modificar."));
        }

    }

    public void cancelarCntConfiguracionCentroCostoPorDefinicionModificacion() throws Exception {
        if (cntEntidadesService.verificaEntidadCentroCosto(selectedEntidad)) {
            listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
            listaCentrosDeCosto = new ArrayList<CntEntidad>();
            listaCntConfiguracionCentrocosto = cntEntidadesService.cargaListadoConfiguracionDesdeUnPlanCuentas(selectedEntidad);
            listaCentrosDeCosto = cntEntidadesService.filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(listaCntConfiguracionCentrocosto);
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
            activaBotonModificacionAsignacion = true;
            esDefinicion = false;
        }
    }

    public String cancelaCntConfiguracionCentroCostoPorDefinicion() {
        setInSessionIdEntidad2(cntEntidad.getIdEntidad());
        return "planCuentasModificar";
    }

    public Boolean bloquearAuxiliaresRegistradosEnDetalleComprobante(Long IdAuxiliar, CntEntidad seleCntEntidad) throws Exception {
        return cntAuxiliaresPorCuentaService.comparaAuxiliarDetalleComprobanteEntidad(IdAuxiliar, seleCntEntidad);
    }

    public String cancelar() {
        return "planCuentas";
    }

    public void limpiaListaDeAuxiliaresSeleccionados() {
        selectAuxiliarParaAsignacion = new ArrayList<CntAuxiliar>();
        if (!selectAuxiliarParaAsignacionModificacion.isEmpty()) {
            botonIrAAuxiliar = false;
            botonIrAsignacionAuxiliar = false;

        } else {
            botonIrAAuxiliar = true;
            botonIrAsignacionAuxiliar = true;
            auxiliarHabilita = false;
        }

    }

    public List<CntAuxiliar> listaAuxiliaresPorAsignar() throws Exception {
        return listaSelecionadosParaAsignarSelec = cntAuxiliaresService.listaCntAuxiliares();
    }

    public void validaListaVacia() {
        if (!selectAuxiliarParaAsignacionModificacion.isEmpty()) {
            botonIrAAuxiliar = false;
            botonIrAsignacionAuxiliar = false;

        } else {
            botonIrAAuxiliar = true;
            botonIrAsignacionAuxiliar = true;
            auxiliarHabilita = false;
        }
    }

    public void activaFacturaDeCompra(ValueChangeEvent e) {
        if (facDeCompraHabilita) {
            facDeCompraHabilita = true;

        } else {
            facDeCompraHabilita = false;
        }
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public List<CntEntidad> getListPadres() {
        return listPadres;
    }

    public void setListPadres(List<CntEntidad> listPadres) {
        this.listPadres = listPadres;
    }

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    public int getNumeroEspaciador() {
        return numeroEspaciador;
    }

    public void setNumeroEspaciador(int numeroEspaciador) {
        this.numeroEspaciador = numeroEspaciador;
    }

    public CntEntidad getSelectedEntidad() {
        return selectedEntidad;
    }

    public void setSelectedEntidad(CntEntidad selectedEntidad) {
        this.selectedEntidad = selectedEntidad;
    }

    public CntParametroAutomatico getCntParametroAutomaticoDeNivel2() {
        return cntParametroAutomaticoDeNivel2;
    }

    public void setCntParametroAutomaticoDeNivel2(CntParametroAutomatico cntParametroAutomaticoDeNivel2) {
        this.cntParametroAutomaticoDeNivel2 = cntParametroAutomaticoDeNivel2;
    }

    public CntParametroAutomaticoService getCntParametroAutomaticoService() {
        return cntParametroAutomaticoService;
    }

    public void setCntParametroAutomaticoService(CntParametroAutomaticoService cntParametroAutomaticoService) {
        this.cntParametroAutomaticoService = cntParametroAutomaticoService;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public List<CntConfiguracionCentrocosto> getListaCntConfiguracionCentrocosto() {
        return listaCntConfiguracionCentrocosto;
    }

    public void setListaCntConfiguracionCentrocosto(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto) {
        this.listaCntConfiguracionCentrocosto = listaCntConfiguracionCentrocosto;
    }

    public List<CntEntidad> getListaCentrosDeCosto() {
        return listaCentrosDeCosto;
    }

    public void setListaCentrosDeCosto(List<CntEntidad> listaCentrosDeCosto) {
        this.listaCentrosDeCosto = listaCentrosDeCosto;
    }

    public BigDecimal getSumaPorsentajeTotal() {
        return sumaPorsentajeTotal;
    }

    public void setSumaPorsentajeTotal(BigDecimal sumaPorsentajeTotal) {
        this.sumaPorsentajeTotal = sumaPorsentajeTotal;
    }

    public BigDecimal getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(BigDecimal disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Boolean getActivaBotonModificacionAsignacion() {
        return activaBotonModificacionAsignacion;
    }

    public void setActivaBotonModificacionAsignacion(Boolean activaBotonModificacionAsignacion) {
        this.activaBotonModificacionAsignacion = activaBotonModificacionAsignacion;
    }

    public Boolean getEsDefinicion() {
        return esDefinicion;
    }

    public void setEsDefinicion(Boolean esDefinicion) {
        this.esDefinicion = esDefinicion;
    }

    public Boolean getTieneCentroDeCostoNivel4() {
        return tieneCentroDeCostoNivel4;
    }

    public void setTieneCentroDeCostoNivel4(Boolean tieneCentroDeCostoNivel4) {
        this.tieneCentroDeCostoNivel4 = tieneCentroDeCostoNivel4;
    }

    public Boolean getTieneCCMasivo() {
        return tieneCCMasivo;
    }

    public void setTieneCCMasivo(Boolean tieneCCMasivo) {
        this.tieneCCMasivo = tieneCCMasivo;
    }

    public Boolean getBotonCentroDeCostoHabilita() {
        return botonCentroDeCostoHabilita;
    }

    public void setBotonCentroDeCostoHabilita(Boolean botonCentroDeCostoHabilita) {
        this.botonCentroDeCostoHabilita = botonCentroDeCostoHabilita;
    }

    public Boolean getBotonIrACentroDeCosto() {
        return botonIrACentroDeCosto;
    }

    public void setBotonIrACentroDeCosto(Boolean botonIrACentroDeCosto) {
        this.botonIrACentroDeCosto = botonIrACentroDeCosto;
    }

    public Boolean getCentroDeCostoHabilita() {
        return centroDeCostoHabilita;
    }

    public void setCentroDeCostoHabilita(Boolean centroDeCostoHabilita) {
        this.centroDeCostoHabilita = centroDeCostoHabilita;
    }

    public List<ParAjustes> getParAjustesList() {
        return parAjustesList;
    }

    public void setParAjustesList(List<ParAjustes> parAjustesList) {
        this.parAjustesList = parAjustesList;
    }

    public List<CntAuxiliar> getSelectAuxiliarParaAsignacionModificacion() {
        return selectAuxiliarParaAsignacionModificacion;
    }

    public void setSelectAuxiliarParaAsignacionModificacion(List<CntAuxiliar> selectAuxiliarParaAsignacionModificacion) {
        this.selectAuxiliarParaAsignacionModificacion = selectAuxiliarParaAsignacionModificacion;
    }

    public Boolean getSwParAutomatico() {
        return swParAutomatico;
    }

    public void setSwParAutomatico(Boolean swParAutomatico) {
        this.swParAutomatico = swParAutomatico;
    }

    public Boolean getSwActivaBoton() {
        return swActivaBoton;
    }

    public void setSwActivaBoton(Boolean swActivaBoton) {
        this.swActivaBoton = swActivaBoton;
    }

    public String getTipoCalculoAutomatico() {
        return tipoCalculoAutomatico;
    }

    public void setTipoCalculoAutomatico(String tipoCalculoAutomatico) {
        this.tipoCalculoAutomatico = tipoCalculoAutomatico;
    }

    public Boolean getBotonFacturaVentaHabilita() {
        return botonFacturaVentaHabilita;
    }

    public void setBotonFacturaVentaHabilita(Boolean botonFacturaVentaHabilita) {
        this.botonFacturaVentaHabilita = botonFacturaVentaHabilita;
    }

    public Boolean getBotonFacturaCompraHabilita() {
        return botonFacturaCompraHabilita;
    }

    public void setBotonFacturaCompraHabilita(Boolean botonFacturaCompraHabilita) {
        this.botonFacturaCompraHabilita = botonFacturaCompraHabilita;
    }

    public Boolean getBotonCreditoFiscalNoDeducibleHabilita() {
        return botonCreditoFiscalNoDeducibleHabilita;
    }

    public void setBotonCreditoFiscalNoDeducibleHabilita(Boolean botonCreditoFiscalNoDeducibleHabilita) {
        this.botonCreditoFiscalNoDeducibleHabilita = botonCreditoFiscalNoDeducibleHabilita;
    }

    public Boolean getBotonRetencionesHabilita() {
        return botonRetencionesHabilita;
    }

    public void setBotonRetencionesHabilita(Boolean botonRetencionesHabilita) {
        this.botonRetencionesHabilita = botonRetencionesHabilita;
    }

    public Boolean getBotonGrossinUpHabilita() {
        return botonGrossinUpHabilita;
    }

    public void setBotonGrossinUpHabilita(Boolean botonGrossinUpHabilita) {
        this.botonGrossinUpHabilita = botonGrossinUpHabilita;
    }

    public Boolean getBotonAjusteHabilita() {
        return botonAjusteHabilita;
    }

    public void setBotonAjusteHabilita(Boolean botonAjusteHabilita) {
        this.botonAjusteHabilita = botonAjusteHabilita;
    }

    public Boolean getComboRetencionHabilita() {
        return comboRetencionHabilita;
    }

    public void setComboRetencionHabilita(Boolean comboRetencionHabilita) {
        this.comboRetencionHabilita = comboRetencionHabilita;
    }

    public Boolean getComboGrossinUpHabilita() {
        return comboGrossinUpHabilita;
    }

    public void setComboGrossinUpHabilita(Boolean comboGrossinUpHabilita) {
        this.comboGrossinUpHabilita = comboGrossinUpHabilita;
    }

    public Boolean getBotonSinFacturaHabilita() {
        return botonSinFacturaHabilita;
    }

    public void setBotonSinFacturaHabilita(Boolean botonSinFacturaHabilita) {
        this.botonSinFacturaHabilita = botonSinFacturaHabilita;
    }

    public Boolean getFacDeCompraHabilita() {
        return facDeCompraHabilita;
    }

    public void setFacDeCompraHabilita(Boolean facDeCompraHabilita) {
        this.facDeCompraHabilita = facDeCompraHabilita;
    }

    public Boolean getCombustibleHabilita() {
        return combustibleHabilita;
    }

    public void setCombustibleHabilita(Boolean combustibleHabilita) {
        this.combustibleHabilita = combustibleHabilita;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public Boolean getFacDeVentaHabilita() {
        return facDeVentaHabilita;
    }

    public void setFacDeVentaHabilita(Boolean facDeVentaHabilita) {
        this.facDeVentaHabilita = facDeVentaHabilita;
    }

    public Boolean getNoDeducibleHabilita() {
        return noDeducibleHabilita;
    }

    public void setNoDeducibleHabilita(Boolean noDeducibleHabilita) {
        this.noDeducibleHabilita = noDeducibleHabilita;
    }

    public String getSinFactura() {
        return sinFactura;
    }

    public void setSinFactura(String sinFactura) {
        this.sinFactura = sinFactura;
    }

    public Boolean getSinfacturaHabilita() {
        return sinfacturaHabilita;
    }

    public void setSinfacturaHabilita(Boolean sinfacturaHabilita) {
        this.sinfacturaHabilita = sinfacturaHabilita;
    }

    public Boolean getSinfacturaDebe() {
        return sinfacturaDebe;
    }

    public void setSinfacturaDebe(Boolean sinfacturaDebe) {
        this.sinfacturaDebe = sinfacturaDebe;
    }

    public Boolean getSinfacturaHaber() {
        return sinfacturaHaber;
    }

    public void setSinfacturaHaber(Boolean sinfacturaHaber) {
        this.sinfacturaHaber = sinfacturaHaber;
    }

    public Boolean getRetencionHabilita() {
        return retencionHabilita;
    }

    public void setRetencionHabilita(Boolean retencionHabilita) {
        this.retencionHabilita = retencionHabilita;
    }

    public Boolean getGrossingUpActivar() {
        return grossingUpActivar;
    }

    public void setGrossingUpActivar(Boolean grossingUpActivar) {
        this.grossingUpActivar = grossingUpActivar;
    }

    public String getRetencion() {
        return retencion;
    }

    public void setRetencion(String retencion) {
        this.retencion = retencion;
    }

    public String getGrossingUp() {
        return grossingUp;
    }

    public void setGrossingUp(String grossingUp) {
        this.grossingUp = grossingUp;
    }

    public Boolean getGrossingHabilita() {
        return grossingHabilita;
    }

    public void setGrossingHabilita(Boolean grossingHabilita) {
        this.grossingHabilita = grossingHabilita;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getAjuste() {
        return ajuste;
    }

    public void setAjuste(String ajuste) {
        this.ajuste = ajuste;
    }

    public Boolean getBotonIrAAuxiliar() {
        return botonIrAAuxiliar;
    }

    public void setBotonIrAAuxiliar(Boolean botonIrAAuxiliar) {
        this.botonIrAAuxiliar = botonIrAAuxiliar;
    }

    public Boolean getHabilitaDialogoAuxiliar() {
        return habilitaDialogoAuxiliar;
    }

    public void setHabilitaDialogoAuxiliar(Boolean habilitaDialogoAuxiliar) {
        this.habilitaDialogoAuxiliar = habilitaDialogoAuxiliar;
    }

    public Boolean getBotonAuxiliarHabilita() {
        return botonAuxiliarHabilita;
    }

    public void setBotonAuxiliarHabilita(Boolean botonAuxiliarHabilita) {
        this.botonAuxiliarHabilita = botonAuxiliarHabilita;
    }

    public Boolean getAuxiliarHabilita() {
        return auxiliarHabilita;
    }

    public void setAuxiliarHabilita(Boolean auxiliarHabilita) {
        this.auxiliarHabilita = auxiliarHabilita;
    }

    public Boolean getDesactivaRadioButtonSubNivel() {
        return desactivaRadioButtonSubNivel;
    }

    public void setDesactivaRadioButtonSubNivel(Boolean desactivaRadioButtonSubNivel) {
        this.desactivaRadioButtonSubNivel = desactivaRadioButtonSubNivel;
    }

    public List<ParTipoRetencion> getParRetencionesList() {
        return parRetencionesList;
    }

    public void setParRetencionesList(List<ParTipoRetencion> parRetencionesList) {
        this.parRetencionesList = parRetencionesList;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMascaraNuevoOpcion() {
        return mascaraNuevoOpcion;
    }

    public void setMascaraNuevoOpcion(String mascaraNuevoOpcion) {
        this.mascaraNuevoOpcion = mascaraNuevoOpcion;
    }

    public String getMascaraNivelPosicionUno() {
        return mascaraNivelPosicionUno;
    }

    public void setMascaraNivelPosicionUno(String mascaraNivelPosicionUno) {
        this.mascaraNivelPosicionUno = mascaraNivelPosicionUno;
    }

    public String getNivelIn() {
        return nivelIn;
    }

    public void setNivelIn(String nivelIn) {
        this.nivelIn = nivelIn;
    }

    public String getMascaraNivelPosicionDos() {
        return mascaraNivelPosicionDos;
    }

    public void setMascaraNivelPosicionDos(String mascaraNivelPosicionDos) {
        this.mascaraNivelPosicionDos = mascaraNivelPosicionDos;
    }

    public String getMascaraSubNivelPosicionUno() {
        return mascaraSubNivelPosicionUno;
    }

    public void setMascaraSubNivelPosicionUno(String mascaraSubNivelPosicionUno) {
        this.mascaraSubNivelPosicionUno = mascaraSubNivelPosicionUno;
    }

    public String getSubNivelIn() {
        return subNivelIn;
    }

    public void setSubNivelIn(String subNivelIn) {
        this.subNivelIn = subNivelIn;
    }

    public String getMascaraSubNivelPosicionDos() {
        return mascaraSubNivelPosicionDos;
    }

    public void setMascaraSubNivelPosicionDos(String mascaraSubNivelPosicionDos) {
        this.mascaraSubNivelPosicionDos = mascaraSubNivelPosicionDos;
    }

    public Boolean getBotonIrAsignacionAuxiliar() {
        return botonIrAsignacionAuxiliar;
    }

    public void setBotonIrAsignacionAuxiliar(Boolean botonIrAsignacionAuxiliar) {
        this.botonIrAsignacionAuxiliar = botonIrAsignacionAuxiliar;
    }

    public String getFacDeCompra() {
        return facDeCompra;
    }

    public void setFacDeCompra(String facDeCompra) {
        this.facDeCompra = facDeCompra;
    }

    public String getFacDeVenta() {
        return facDeVenta;
    }

    public void setFacDeVenta(String facDeVenta) {
        this.facDeVenta = facDeVenta;
    }

    public CntParametroAutomatico getCntParametroAutomatico() {
        return cntParametroAutomatico;
    }

    public void setCntParametroAutomatico(CntParametroAutomatico cntParametroAutomatico) {
        this.cntParametroAutomatico = cntParametroAutomatico;
    }

    public String getNoDeducible() {
        return noDeducible;
    }

    public void setNoDeducible(String noDeducible) {
        this.noDeducible = noDeducible;
    }

    public List<CntEntidad> getListaCentrosDeCostoElegidaParaQuitarse() {
        return listaCentrosDeCostoElegidaParaQuitarse;
    }

    public void setListaCentrosDeCostoElegidaParaQuitarse(List<CntEntidad> listaCentrosDeCostoElegidaParaQuitarse) {
        this.listaCentrosDeCostoElegidaParaQuitarse = listaCentrosDeCostoElegidaParaQuitarse;
    }

    public CntConfiguracionCentrocosto getConfiguracionCentrocosto() {
        return configuracionCentrocosto;
    }

    public void setConfiguracionCentrocosto(CntConfiguracionCentrocosto configuracionCentrocosto) {
        this.configuracionCentrocosto = configuracionCentrocosto;
    }

    public List<CntConfiguracionCentrocosto> getListaCentrosDeCostoElegidaParaQuitarseAux() {
        return listaCentrosDeCostoElegidaParaQuitarseAux;
    }

    public void setListaCentrosDeCostoElegidaParaQuitarseAux(List<CntConfiguracionCentrocosto> listaCentrosDeCostoElegidaParaQuitarseAux) {
        this.listaCentrosDeCostoElegidaParaQuitarseAux = listaCentrosDeCostoElegidaParaQuitarseAux;
    }

    public CntConfiguracionCentroCostoService getCntConfiguracionCentroCostoService() {
        return cntConfiguracionCentroCostoService;
    }

    public void setCntConfiguracionCentroCostoService(CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService) {
        this.cntConfiguracionCentroCostoService = cntConfiguracionCentroCostoService;
    }

    public CntAuxiliaresService getCntAuxiliaresService() {
        return cntAuxiliaresService;
    }

    public void setCntAuxiliaresService(CntAuxiliaresService cntAuxiliaresService) {
        this.cntAuxiliaresService = cntAuxiliaresService;
    }

    public List<CntAuxiliar> getListaSelecionadosParaAsignarSelec() {
        return listaSelecionadosParaAsignarSelec;
    }

    public void setListaSelecionadosParaAsignarSelec(List<CntAuxiliar> listaSelecionadosParaAsignarSelec) {
        this.listaSelecionadosParaAsignarSelec = listaSelecionadosParaAsignarSelec;
    }

    public CntAuxiliaresPorCuentaService getCntAuxiliaresPorCuentaService() {
        return cntAuxiliaresPorCuentaService;
    }

    public void setCntAuxiliaresPorCuentaService(CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService) {
        this.cntAuxiliaresPorCuentaService = cntAuxiliaresPorCuentaService;
    }

    public List<CntAuxiliar> getSelectAuxiliarParaAsignacion() {
        return selectAuxiliarParaAsignacion;
    }

    public void setSelectAuxiliarParaAsignacion(List<CntAuxiliar> selectAuxiliarParaAsignacion) {
        this.selectAuxiliarParaAsignacion = selectAuxiliarParaAsignacion;
    }

    public Boolean getOcultaPorNivel2() {
        return ocultaPorNivel2;
    }

    public void setOcultaPorNivel2(Boolean ocultaPorNivel2) {
        this.ocultaPorNivel2 = ocultaPorNivel2;
    }

    public String getModificarSubnivelesIndividualesMasivamente() {
        return modificarSubnivelesIndividualesMasivamente;
    }

    public void setModificarSubnivelesIndividualesMasivamente(String modificarSubnivelesIndividualesMasivamente) {
        this.modificarSubnivelesIndividualesMasivamente = modificarSubnivelesIndividualesMasivamente;
    }
}
