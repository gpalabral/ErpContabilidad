package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico;
import com.bap.erp.modelo.entidades.cnf.ParAjustes;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGenerales;
import com.bap.erp.modelo.entidades.cnf.ParTipoRetencion;
import com.bap.erp.modelo.entidades.cnf.ParTiposMovimiento;
import com.bap.erp.modelo.enums.*;
import com.bap.erp.modelo.pojo.ParCuentasGeneralesPojo;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresPorCuentaService;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import com.bap.erp.modelo.servicios.cnt.CntConfiguracionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntDistribucionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import com.bap.erp.modelo.servicios.cnt.CntParametroAutomaticoService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.wrapper.GestionContableWrapper;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.iknow.utils.ObjectUtils;
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

@ManagedBean(name = "cntCuentasAdicionarModificarBacking")
@ViewScoped
public class CntCuentasAdicionarModificarBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntMascarasService}")
    private CntMascarasService cntMascarasService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntParametroAutomaticoService}")
    private CntParametroAutomaticoService cntParametroAutomaticoService;
    @ManagedProperty(value = "#{cntAuxiliaresService}")
    private CntAuxiliaresService cntAuxiliaresService;
    @ManagedProperty(value = "#{cntAuxiliaresPorCuentaService}")
    private CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService;
    @ManagedProperty(value = "#{cntDistribucionCentroCostoService}")
    private CntDistribucionCentroCostoService cntDistribucionCentroCostoService;
    private CntEntidad cntEntidad;
    private CntEntidad selectedEntidad;
    private List<CntEntidad> filteredCntObjetos;
    private String mascaraNuevo;
    private String mascaraNuevoOpcion = "";
    private String descripcion = "";
    private String tipoMovimiento;
    private boolean activaTipoMovimiento = false;
    private String mascaraNivel;
    private String mascaraSubNivel;
    private int longitud;
    private int longitudNivel;
    private int longitudSubNivel;
    private String nivelIn;
    private String subNivelIn;
    private String mensajeVerifica1;
    private String mensajeVerifica2;
    private CntMascara cntMascaras;
    private boolean activaCentrosCosto = false;
    private boolean activaAsignacionCentrosCosto = false;
    private boolean activaPanelAsigCentroCosto = false;
    private boolean swajuste = false;
    private boolean activaDebeHaber = true;
    private int movimiento = 3;
    private String ajuste = "SAJU";
    private boolean swnivel = false;
    private boolean swsubnivel = false;
    private String color = null;
    private Boolean eleccionTodos = false;
    private List<CntEntidad> listaMomentanea = new ArrayList<CntEntidad>();
    private String gruposNivelCombo = EnumGruposNivel.PLAN_CUENTAS.getCodigo();
    private String facDeCompra = "NING";
    private String facDeVenta = "NING";
    private String retencion = "N";
    private String grossingUp = "SRET";
    private String sinFactura = "NING";
    private String noDeducible = "NING";
    private Boolean facDeCompraHabilita;
    private Boolean facDeVentaHabilita;
    private Boolean noDeducibleHabilita;
    private Boolean sinfacturaHabilita;
    private Boolean retencionHabilita;
    private Boolean grossingUpActivar;
    private Boolean retencionActiva;
    private Boolean grossingHabilita;
    private Boolean swRetencion = false;
    private Boolean sinfacturaDebe = false;
    private Boolean sinfacturaHaber = false;
    private Boolean swActivaBoton = false;
    private List<CntNivel> listaNivel = new ArrayList<CntNivel>();
    private List<ParAjustes> parAjustesList = new ArrayList<ParAjustes>();
    private List<ParTipoRetencion> parRetencionesList = new ArrayList<ParTipoRetencion>();
    private List<CntParametroAutomatico> listParametroAutomatico;
    private List<CntEntidad> listPadres;
    private List<ParCuentasGenerales> listaCuentasGenerales;
    private ParCuentasGenerales parCuentasGenerales;
    private Boolean renderNivel2AndNivel1 = false;
    private CntParametroAutomatico cntParametroAutomatico;
    private CntParametroAutomatico cntParametroAutomaticoDeNivel2;
    private Boolean swParAutomatico = false;
    private Boolean swRadioSubNivel = false;
    //nuevo codigo henrry
    private String mascaraNivelPosicionUno = "";
    private String mascaraNivelPosicionDos = "";
    private String mascaraSubNivelPosicionUno = "";
    private String mascaraSubNivelPosicionDos = "";
    private String nuevaMasacaraNivelVista = "";
    private String nuevaMasacaraSubNivelVista = "";
    private Boolean desactivaRadioButtonSubNivel = true;
    private int numeroEspaciador = 10;
    private int numeroEspaciadorAdicionar = 10;
    private String combustible;
    private Boolean combustibleHabilita;
    private String tipoCalculoAutomatico;
    private Boolean botonFacturaCompraHabilita;
    private Boolean botonCreditoFiscalNoDeducibleHabilita;
    private Boolean botonFacturaVentaHabilita;
    private Boolean botonSinFacturaHabilita;
    private Boolean botonRetencionesHabilita;
    private Boolean botonGrossinUpHabilita;
    private Boolean botonAjusteHabilita;
    private Boolean comboRetencionHabilita;
    private Boolean comboGrossinUpHabilita;
    //fin nuevo codigo henrry
    //desde aqui codigo Jonas    
    private Boolean botonCentroDeCostoHabilita;
    private Boolean botonAuxiliarHabilita;
    private Boolean botonProyectoHabilita;
    private Boolean centroDeCostoHabilita;
    private Boolean auxiliarHabilita = false;
    private Boolean proyectoHabilita;
    private Boolean botonIrACentroDeCosto = true;
    private Boolean botonIrAAuxiliar = true;
    private Boolean botonIrAProyecto = true;
    //hasta aqui codigo Jonas
    //Codigo para la asignacion de centros de costo Por Henrry Guzman.
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    private List<CntEntidad> listaCentrosDeCosto;
    private List<CntEntidad> listaCentrosDeCostoElegidaParaQuitarse = new ArrayList<CntEntidad>();
    private List<CntConfiguracionCentrocosto> listaCentrosDeCostoElegidaParaQuitarseAux = new ArrayList<CntConfiguracionCentrocosto>();
//    desde aqui codigo Jonas para guardar Configuracion de Centro de Costos por Definicion
    @ManagedProperty(value = "#{cntConfiguracionCentroCostoService}")
    private CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService;
    private Boolean esDefinicion = false;
    private Boolean tieneCentroCostoEntidad = false;
    private Boolean activaBotonGuardaPadre = false;
//    hasta aqui codigo Jonas para guardar Configuracion de Centro de Costos por Definicion
    private List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
    private CntConfiguracionCentrocosto configuracionCentrocosto = new CntConfiguracionCentrocosto();
    private BigDecimal sumaPorsentajeTotal = new BigDecimal("0.00");
    private BigDecimal disponibilidad = new BigDecimal("100.00");
    private Boolean actimaBotonModificacionAsignacion = false;
    private CntEntidad cntEntidadDefinicionCentrosCosto = new CntEntidad();
    private Boolean activaBotonGuardar = true;
    private ParCuentasGenerales selectParCuentasGenerales;
    private Boolean muestraDialogo = true;
    private String codigo;
    private Boolean habilitaDialogoCC = true;
    private Boolean habilitaDialogoAuxiliar = true;
    private Boolean habilitaDialogoProyecto = true;
//    private Boolean auxiliarHabilita;
    private Boolean botonIrAsignacionAuxiliar = true;
    private List<CntAuxiliar> selectAuxiliarParaAsignacion = new ArrayList<CntAuxiliar>();
    private List<CntAuxiliar> selectAuxiliarParaAsignacionModificacion = new ArrayList<CntAuxiliar>();
    private List<CntAuxiliar> listaSelecionadosParaAsignarSelec = new ArrayList<CntAuxiliar>();
    private Boolean activaBotonGuardarGeneral;
    private Boolean tieneCCMasivo;
    private Boolean tieneCentroDeCostoNivel3;
    private Boolean tieneCentroDeCostoNivel4;

    //Fin Codigo para asignacion de centros de costo por Henrry Guzman.
    public CntCuentasAdicionarModificarBacking() {
    }

    @PostConstruct
    void initCntCuentasAdicionarModificarBacking() {
        
        try {
            if (super.getFromSessionIdEntidad() != null) {
                cntEntidad = (CntEntidad) getCntEntidadesService().find(CntEntidad.class, super.getFromSessionIdEntidad());
                selectedEntidad = (CntEntidad) getCntEntidadesService().find(CntEntidad.class, super.getFromSessionIdEntidad());
                selectedEntidad.setDescripcion(getCntEntidadesService().eliminaCaracterDescripcion(selectedEntidad));
                cargaDatosInicialesSoloParaAdicionDeCuenta();
                cargaDatosParaNivelAndSubNivel();
                if (selectedEntidad.getNivel() == 3) {
                    tieneCentroDeCostoNivel3 = true;
                    tieneCCMasivo = false;
                }
                if (selectedEntidad.getNivel() == 2) {
                    botonCentroDeCostoHabilita = true;
                    centroDeCostoHabilita = selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                }
                if (selectedEntidad.getNivel() == 1) {
                    botonCentroDeCostoHabilita = selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.NO.getCodigo());
                    centroDeCostoHabilita = selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                    botonIrACentroDeCosto = !selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                }
                listaCentrosDeCosto = new ArrayList<CntEntidad>();
                listaCentrosDeCosto = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
                if (cntEntidadesService.verificaEntidadCentroCosto(selectedEntidad) && selectedEntidad.getNivel() == 1) {
                    listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();

                    // CODIGO MODIFICADO POR HENRRY GUZMAN REVISAR ANTES DE BORRA POR FAVOR...... PREGUNTAR A HENRRY
    //                listaCentrosDeCosto = new ArrayList<CntEntidad>();
    //                listaCentrosDeCosto = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
                    actimaBotonModificacionAsignacion = false;
                    esDefinicion = true;
                    activaBotonGuardaPadre = false;
                    activaBotonGuardarGeneral = true;
                    tieneCentroCostoEntidad = true;
                } else {
                    tieneCentroCostoEntidad = false;
                    activaBotonGuardaPadre = false;
                    activaBotonGuardarGeneral = true;
                }
                if (cntEntidadesService.verificaEntidadPadre(selectedEntidad)) {
                    tieneCentroCostoEntidad = false;
                    activaBotonGuardaPadre = true;
                    activaBotonGuardarGeneral = false;
                }
            }
            if (super.getFromSessionIdEntidad2() != null) {
                selectedEntidad = (CntEntidad) getCntEntidadesService().find(CntEntidad.class, super.getFromSessionIdEntidad2());
                cntParametroAutomaticoDeNivel2 = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(selectedEntidad);
                cargaDatosInicialesSoloParaModificacionDeCuenta();
                if (cntParametroAutomaticoDeNivel2 != null) {
                    cargaDatosDeParametrosAutomaticosDeNivel2();
                }
                verificaSiCuentaTieneAuxiliar(selectedEntidad);
                //INICIO: Carga listas para asignacion de centros de costo por Henrry Guzman
                if (cntEntidadesService.verificaEntidadCentroCosto(selectedEntidad)) {
                    listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
                    listaCentrosDeCosto = new ArrayList<CntEntidad>();
                    listaCntConfiguracionCentrocosto = cntEntidadesService.cargaListadoConfiguracionDesdeUnPlanCuentas(selectedEntidad);
                    listaCentrosDeCosto = cntEntidadesService.filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(listaCntConfiguracionCentrocosto);
                    sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
                    disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
                    actimaBotonModificacionAsignacion = true;
                    esDefinicion = false;
                }

                if (selectedEntidad.getNivel() == 3) {
                    tieneCentroDeCostoNivel4 = true;
                    tieneCCMasivo = selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                }
                if (selectedEntidad.getNivel() == 2) {
                    botonCentroDeCostoHabilita = true;
                    botonIrACentroDeCosto = true;
                    centroDeCostoHabilita = selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                }
                if (selectedEntidad.getNivel() == 1) {
                    botonCentroDeCostoHabilita = !selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                    botonIrACentroDeCosto = !selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                    centroDeCostoHabilita = selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                }
            }
            if (retencion.equals(EnumTipoRetencion.SIN_RETENCION.getCodigo())) {
                swRetencion = false;
                activaDebeHaber = true;
            } else {
                swRetencion = true;
                activaDebeHaber = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
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

    public void cargaDatosInicialesSoloParaModificacionDeCuenta() throws Exception {
        //Obtiene Ajuste Autor: Henrry Guzman
        GestionContableWrapper gestionContableWrapper = parParametricasService.factoryGestionContable();
        gestionContableWrapper.getNormaContable3();
        parAjustesList = parParametricasService.listaTiposDeAjuste(obtieneEnumTipoAjuste(gestionContableWrapper.getNormaContable3()));
        selectAuxiliarParaAsignacionModificacion = cntEntidadesService.listaDeAuxiliaresPorEntidad(selectedEntidad);
        //Obtien Ajuste Fin

        //Activa formulario para automatico modifica  
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

    public void cargaDatosInicialesSoloParaAdicionDeCuenta() throws Exception {
        //Obtiene Ajuste Autor: Henrry Guzman
        GestionContableWrapper gestionContableWrapper = parParametricasService.factoryGestionContable();
        gestionContableWrapper.getNormaContable3();
        parAjustesList = parParametricasService.listaTiposDeAjuste(obtieneEnumTipoAjuste(gestionContableWrapper.getNormaContable3()));
        //Obtien Ajuste Fin
        //Activa formulario para automatico modifica  
        if (selectedEntidad.getNivel() >= 3) {
            numeroEspaciadorAdicionar = 260;
        }

        if (selectedEntidad.getNivel() == 3) {
            swParAutomatico = false;
            numeroEspaciadorAdicionar = 230;
        }
        if (selectedEntidad.getNivel() == 2) {
            swParAutomatico = true;
            swActivaBoton = true;
            numeroEspaciadorAdicionar = 200;
        }
        if (selectedEntidad.getNivel() == 1) {
            swParAutomatico = true;
            numeroEspaciadorAdicionar = 130;
            cntParametroAutomaticoDeNivel2 = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(selectedEntidad);
        }

        mascaraNuevoOpcion = "N";
        cntEntidad = (CntEntidad) getCntEntidadesService().find(CntEntidad.class, selectedEntidad.getIdEntidad());
        mascaraNivel = getCntEntidadesService().generaCodigoNivelesSubAndPadre(selectedEntidad, "N");
        mascaraSubNivel = getCntEntidadesService().generaCodigoNivelesSubAndPadre(selectedEntidad, "S");
        longitudNivel = getCntEntidadesService().controlaLongitudNumero(selectedEntidad, "N");
        longitudSubNivel = getCntEntidadesService().controlaLongitudNumero(selectedEntidad, "S");
    }

    public void cargaDatosParaNivelAndSubNivel() {
        //henrry
        mascaraNivelPosicionUno = cntEntidadesService.obtieneMascaraSeparada(selectedEntidad, "N")[0];
        mascaraNivelPosicionDos = cntEntidadesService.obtieneMascaraSeparada(selectedEntidad, "N")[1];
        longitudNivel = getCntEntidadesService().controlaLongitudNumero(selectedEntidad, "N");
        nivelIn = cntEntidadesService.generaNumeroSiguienteAutomatico(selectedEntidad, "N");
        mascaraNivel = getCntEntidadesService().generaCodigoNivelesSubAndPadre(selectedEntidad, "N");
        mensajeVerifica1 = getCntEntidadesService().verificaExistenciaCodigo(mascaraNivelPosicionUno + nivelIn + mascaraNivelPosicionDos);
        tipoCalculoAutomatico = EnumTipoCalculoAutomatico.NINGUNO.getCodigo();
        botonFacturaCompraHabilita = true;
        botonCreditoFiscalNoDeducibleHabilita = true;
        botonFacturaVentaHabilita = true;
        botonSinFacturaHabilita = true;
        botonRetencionesHabilita = true;
        botonGrossinUpHabilita = true;
        botonAjusteHabilita = true;
        comboRetencionHabilita = true;
        comboGrossinUpHabilita = true;
        if (selectedEntidad.getIdEntidadPadre() == 0) {
            activaBotonGuardar = parParametricasService.verificaExistenciaDeCuentaEnDefiniconDeCuentas(nivelIn);
        }
        if (selectedEntidad.getNivel() == 1) {
            desactivaRadioButtonSubNivel = false;
        } else {
            mascaraSubNivelPosicionUno = cntEntidadesService.obtieneMascaraSeparada(selectedEntidad, "S")[0];
            mascaraSubNivelPosicionDos = cntEntidadesService.obtieneMascaraSeparada(selectedEntidad, "S")[1];
            longitudSubNivel = getCntEntidadesService().controlaLongitudNumero(selectedEntidad, "S");
            subNivelIn = cntEntidadesService.generaNumeroSiguienteAutomatico(selectedEntidad, "S");

            mascaraSubNivel = getCntEntidadesService().generaCodigoNivelesSubAndPadre(selectedEntidad, "S");
            mensajeVerifica2 = getCntEntidadesService().verificaExistenciaCodigo(mascaraSubNivelPosicionUno + subNivelIn + mascaraSubNivelPosicionDos);
        }
        inicializaDatosPorDefectoParametrizacionautomatica();
        //fin henrry
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

    public String saveCntEntidad_action() throws Exception {
        cntEntidad = new CntEntidad();
        cntEntidad.setDescripcion(descripcion);
        cntEntidad.setIdEntidadPadre(super.getFromSessionIdEntidad3());
        if (mascaraNuevoOpcion == null) {
            MessageUtils.addInfoMessage("No se registró, ya que no selecciono ninguna opción");
            return null;
        } else if (mascaraNuevoOpcion.equals("N")) {
            cntEntidad.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos));
        } else {
            cntEntidad.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos));
        }
        cntEntidad.setCntMascara(selectedEntidad.getCntMascara());
        cntEntidad.setNivel(cntEntidadesService.obtieneNiveleCuentaSubAndPadre(selectedEntidad, mascaraNuevoOpcion));
        ParTiposMovimiento parTiposMovimiento = (ParTiposMovimiento) parParametricasService.find(ParTiposMovimiento.class, tipoMovimiento);
        cntEntidad.setParTiposMovimiento(parTiposMovimiento);
        if (selectedEntidad.getIdEntidadPadre() == 0L && mascaraNuevoOpcion.equals("N")) {
            cntEntidad.setIdEntidadPadre(selectedEntidad.getIdEntidad());
        } else {
            if (mascaraNuevoOpcion.equals("N")) {
                cntEntidad.setIdEntidadPadre(selectedEntidad.getIdEntidadPadre());
            } else {
                cntEntidad.setIdEntidadPadre(selectedEntidad.getIdEntidad());
            }
        }
        cntEntidad.setTieneAuxiliar(auxiliarHabilita == true ? "S" : "N");
        if (cntEntidad.getNivel() == 3) {
            cntEntidad.setTieneCentroCosto(tieneCCMasivo ? "S" : "N");
        } else {
            if (cntEntidad.getNivel() == 2) {
                if (mascaraNuevoOpcion.equals("N")) {
                    cntEntidad.setTieneCentroCosto(selectedEntidad.getTieneCentroCosto());
                } else {
                    cntEntidad.setTieneCentroCosto(cntEntidadesService.find(CntEntidad.class, selectedEntidad.getIdEntidad()).getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo()) ? "S" : "N");
                }
            } else {
                if (cntEntidad.getNivel() == 1) {
                    if (cntEntidadesService.find(CntEntidad.class, selectedEntidad.getIdEntidad()).getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo())) {
                        cntEntidad.setTieneCentroCosto(centroDeCostoHabilita ? "S" : "N");
                    } else {
                        cntEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
                    }
                } else {
                    cntEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
                }
            }
        }
        super.setPersistValues(cntEntidad);
        if (cntEntidadesService.verificaExistenciaMascara(cntEntidad)) {
            ParAjustes parAjustes = (ParAjustes) parParametricasService.find(ParAjustes.class, ajuste);
            ParTipoRetencion parTipoRetencion = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, retencion);
            ParTipoRetencion parTipoRetencionGrossing = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, grossingUp);
            cntParametroAutomatico = new CntParametroAutomatico();
            cntParametroAutomatico.setCntEntidad(cntEntidad);
            cntParametroAutomatico.setTipoCalculoAutomatico(tipoCalculoAutomatico);
            if (facDeCompraHabilita == true) {
                if (combustible.equals(EnumConfirmacion.NO.getCodigo())) {
                    cntParametroAutomatico.setFacturaCompra(EnumFacturaCompraCombustible.SIN_COMBUSTIBLE.getCodigo());
                } else {
                    if (combustible.equals(EnumConfirmacion.SI.getCodigo())) {
                        cntParametroAutomatico.setFacturaCompra(EnumFacturaCompraCombustible.CON_COMBUSTIBLE.getCodigo());
                    } else {
                        cntParametroAutomatico.setFacturaCompra(EnumFacturaCompraCombustible.NINGUNO.getCodigo());
                    }
                }
            } else {
                cntParametroAutomatico.setFacturaCompra(EnumFacturaCompraCombustible.NINGUNO.getCodigo());
            }
            if (facDeVentaHabilita == true) {
                cntParametroAutomatico.setFacturaVenta(EnumParametroAutomatico.HABER.getCodigo());
            } else {
                cntParametroAutomatico.setFacturaVenta(EnumParametroAutomatico.NINGUNO.getCodigo());
            }
            if (noDeducibleHabilita == true) {

                cntParametroAutomatico.setCreditoFiscalNoDeducible(EnumParametroAutomatico.DEBE.getCodigo());
            } else {
                cntParametroAutomatico.setCreditoFiscalNoDeducible(EnumParametroAutomatico.NINGUNO.getCodigo());
            }
            if (sinfacturaHabilita == true) {
                if (sinfacturaDebe == true && sinfacturaHaber == true) {
                    cntParametroAutomatico.setSinFactura(EnumParametroAutomatico.AMBOS.getCodigo());

                } else {
                    if (sinfacturaDebe == true) {
                        cntParametroAutomatico.setSinFactura(EnumParametroAutomatico.DEBE.getCodigo());
                    }
                    if (sinfacturaHaber == true) {
                        cntParametroAutomatico.setSinFactura(EnumParametroAutomatico.HABER.getCodigo());
                    }
                }
            } else {
                cntParametroAutomatico.setSinFactura(EnumParametroAutomatico.NINGUNO.getCodigo());
            }

            if (retencionHabilita == true) {
                cntParametroAutomatico.setParTipoRetencion(parTipoRetencion);
            } else {
                cntParametroAutomatico.setParTipoRetencion((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo()));
            }
            if (grossingHabilita == true) {
                cntParametroAutomatico.setParTipoRetencionGrossingUp(parTipoRetencionGrossing);
            } else {
                cntParametroAutomatico.setParTipoRetencionGrossingUp((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo()));
            }
            cntParametroAutomatico.setTipoMovimiento(tipoMovimiento);
            cntParametroAutomatico.setParAjustes(parAjustes);
            setPersistValues(cntParametroAutomatico);
//            cntEntidadesService.persistCntObjetosConAsignacionPorDefinicion(cntEntidad, mascaraNuevoOpcion, cntParametroAutomatico);
            cntEntidadesService.persistCntObjetosConAsignacionPorDefinicionConAsignacionAuxiliares(cntEntidad, mascaraNuevoOpcion, cntParametroAutomatico, selectAuxiliarParaAsignacion, listaCntConfiguracionCentrocosto);

            descripcion = "";
            mascaraNuevoOpcion = "";
            mascaraNuevo = "";
            mascaraNivel = "";
            mascaraSubNivel = "";
            activaTipoMovimiento = false;
            cargaDatosDeParametrosAutomaticosDeNivel2Cereados();
            swRetencion = false;
            return "planCuentas";

        } else {
            MessageUtils.addInfoMessage("La cuenta " + mascaraNuevo + " ya existe");
        }
        return null;
    }

    public List<CntParametroAutomatico> listadoDeHijosDeNivelDos() throws Exception {
        if (listParametroAutomatico == null || listParametroAutomatico.isEmpty()) {
            listParametroAutomatico = cntParametroAutomaticoService.listaDeParametrosAutomaticosDeHijos(cntEntidad);
        }
        return listParametroAutomatico;
    }

    public List<CntEntidad> listadoDePadres() throws Exception {
        if (listPadres == null || listPadres.isEmpty()) {
            listPadres = cntEntidadesService.listaSuperiorGrupoDeObjetoSeleccionado(cntEntidad);
        }
        return listPadres;
    }

    public List<ParTipoRetencion> listadoDeRetencion() throws Exception {
        if (parRetencionesList == null || parRetencionesList.isEmpty()) {
            parRetencionesList = parParametricasService.listaTipoRetencion();
        }
        return parRetencionesList;
    }

    //aumentado para obtener descripcion
    public String muestraDescripcion(int value) {
        String descripcion = "";
        CntNivel cntNivel = cntEntidadesService.obtienedescripcionNivel(value);
        descripcion = cntNivel.getDescripcion();
        return descripcion;
    }

    public String mergeCntEntidad_action() {
        try {
            cntParametroAutomaticoDeNivel2 = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(selectedEntidad);
            if (cntParametroAutomaticoDeNivel2 != null) {
                cntParametroAutomaticoDeNivel2.setTipoCalculoAutomatico(tipoCalculoAutomatico);
                ParAjustes parAjustes = (ParAjustes) parParametricasService.find(ParAjustes.class, ajuste);
                ParTipoRetencion parTipoRetencion = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, retencion);
                ParTipoRetencion parTipoRetencionGrossing = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, grossingUp);

                cntParametroAutomaticoDeNivel2.setCntEntidad(selectedEntidad);
                if (facDeCompraHabilita
                        == true) {
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
                    selectedEntidad.setTieneCentroCosto(tieneCCMasivo ? "S" : "N");
                    selectedEntidad.setUsuarioModificacion(getLoginSession());
                    selectedEntidad.setFechaModificacion(new Date());
                    cntEntidadesService.modificaMasivoPlanDeCuentasSoloCampoTieneCentrosDeCosto(selectedEntidad);
                }
            } else {
//                selectedEntidad.setTieneCentroCosto(centroDeCostoHabilita ? EnumTieneCentroDeCosto.SI.getCodigo() : EnumTieneCentroDeCosto.NO.getCodigo());
                if (centroDeCostoHabilita == true) {
                    selectedEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.SI.getCodigo());
                } else {
                    selectedEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
                }
            }
//            if (centroDeCostoHabilita == true) {
//                selectedEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.SI.getCodigo());
//            } else {
//                selectedEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
//            }
            selectedEntidad.setTieneAuxiliar(auxiliarHabilita == true ? "S" : "N");


            cntEntidadesService.mergeCntEntidadAndCntParametroAutomatico(selectedEntidad, cntParametroAutomaticoDeNivel2);
            //aumentado para modificar auxiliares
            super.setMergeValues(selectedEntidad);
            cntEntidadesService.mergeCntObjetosConAsignacionPorDefinicionConAsignacionAuxiliares(selectedEntidad, mascaraNuevoOpcion, cntParametroAutomatico, selectAuxiliarParaAsignacionModificacion, listaCntConfiguracionCentrocosto);
            if (selectedEntidad.getNivel() == 2) {
                cntParametroAutomaticoService.grabarParametrosHijosMasivo(selectedEntidad, getSessionManagedBean().getLogin());
            }

            return "planCuentas";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
                cntParametroAutomatico.setParTipoRetencion(parTipoRetencion);
            } else {
                cntParametroAutomatico.setParTipoRetencion((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo()));
            }
            if (grossingHabilita == true) {
                cntParametroAutomaticoDeNivel2.setParTipoRetencionGrossingUp(parTipoRetencionGrossing);
            } else {
                cntParametroAutomaticoDeNivel2.setParTipoRetencionGrossingUp((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo()));
            }
            cntParametroAutomaticoDeNivel2.setTipoMovimiento(tipoMovimiento);
            cntParametroAutomaticoDeNivel2.setParAjustes(parAjustes);
            //aumentado para setear el usuario logueado
            setMergeValues(cntParametroAutomaticoDeNivel2);
            
//            cntParametroAutomaticoDeNivel2.setUsuarioModificacion("TEST");
//            cntParametroAutomaticoDeNivel2.setFechaModificacion(new Date());
        }
        cntEntidadesService.mergeCntEntidadAndCntParametroAutomatico(selectedEntidad, cntParametroAutomaticoDeNivel2);
        return "planCuentas";
    }

    public void mergeMasivoDeEntidadesNivel1(CntEntidad cntEntidadNivel2) {
    }

    public String deleteCntObjetos_action() {
        MessageUtils.addInfoMessage("A un está en construcción...Gracias por su comprensión.");
        return null;
    }

    public void generaCuentaNueva(ValueChangeEvent e) {
        if (mascaraNuevoOpcion.equals("N")) {
            mascaraNuevo = getCntEntidadesService().generaCodigoNivelesSubAndPadre(selectedEntidad, "N");
            activaTipoMovimiento = getCntEntidadesService().activaTipoMovimiento(selectedEntidad, mascaraNuevoOpcion, mascaraNuevo);
        } else {
            if (mascaraNuevoOpcion.equals("M")) {
                mascaraNuevo = getCntEntidadesService().generaCodigoNivelesSubAndPadre(selectedEntidad, "M");
            } else {
                mascaraNuevo = getCntEntidadesService().generaCodigoNivelesSubAndPadre(selectedEntidad, "S");
                activaTipoMovimiento = getCntEntidadesService().activaTipoMovimiento(selectedEntidad, mascaraNuevoOpcion, mascaraNuevo);
            }
        }
    }

    public boolean isEditable() {
        if (mascaraNuevoOpcion.equals("N")) {
            return getCntEntidadesService().activaTipoMovimiento(selectedEntidad, mascaraNuevoOpcion, mascaraNuevo);
        } else {
            if (mascaraNuevoOpcion.equals("S")) {
                return getCntEntidadesService().activaTipoMovimiento(selectedEntidad, mascaraNuevoOpcion, mascaraNuevo);
            } else {
                return false;
            }
        }
    }

    public void cerrarDialogo(ValueChangeEvent e) {
        mascaraNuevoOpcion = "";
        mascaraNuevo = "";
        activaTipoMovimiento = false;
        cntEntidad = new CntEntidad();
    }

    public void abrirDialogo(ValueChangeEvent e) {
        mascaraNuevoOpcion = "";
        MessageUtils.addInfoMessage("Registro Seleccionado CUENTA: " + selectedEntidad.getMascaraGenerada() + " DESCRIPCIÓN:" + selectedEntidad.getDescripcion());
        cntEntidad = (CntEntidad) getCntEntidadesService().find(CntEntidad.class, selectedEntidad.getIdEntidad());
        setInSessionIdEntidad2(selectedEntidad.getIdEntidad());
        mascaraNivel = getCntEntidadesService().generaCodigoNivelesSubAndPadre(selectedEntidad, "N");
        mascaraSubNivel = getCntEntidadesService().generaCodigoNivelesSubAndPadre(selectedEntidad, "S");
    }

    public String cierrraDialogo(ValueChangeEvent e) {
        return "planCuentasModificar";
    }

    //aumentado para listar cuentas generales
    public List<ParCuentasGenerales> listaCuentasGeneralesPorAsignar() {
        return parParametricasService.listaCuentasGenerales();
    }

    public List<ParCuentasGeneralesPojo> listaCuentasGeneralesPorAsignarPojo() {
        return parParametricasService.listaCuentasGeneralesPojo();
    }

    public Boolean bloquearAuxiliaresRegistradosEnDetalleComprobante(Long IdAuxiliar, CntEntidad seleCntEntidad) throws Exception {
        return cntAuxiliaresPorCuentaService.comparaAuxiliarDetalleComprobanteEntidad(IdAuxiliar, seleCntEntidad);
    }

    public CntEntidad getCntObjetos() {
        return cntEntidad;
    }

    public void setCntObjetos(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    public List<CntEntidad> getFilteredCntObjetos() {
        return filteredCntObjetos;
    }

    public void setFilteredCntObjetos(List<CntEntidad> filteredCntObjetos) {
        this.filteredCntObjetos = filteredCntObjetos;
    }

    public CntEntidad getselectedEntidad() {
        return selectedEntidad;
    }

    public void setselectedEntidad(CntEntidad selectedEntidad) {
        this.selectedEntidad = selectedEntidad;
    }

    public String getMascaraNuevo() {
        return mascaraNuevo;
    }

    public void setMascaraNuevo(String mascaraNuevo) {
        this.mascaraNuevo = mascaraNuevo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CntMascarasService getCntMascarasService() {
        return cntMascarasService;
    }

    public void setCntMascarasService(CntMascarasService cntMascarasService) {
        this.cntMascarasService = cntMascarasService;
    }

    public String getMascaraNuevoOpcion() {
        return mascaraNuevoOpcion;
    }

    public void setMascaraNuevoOpcion(String mascaraNuevoOpcion) {
        this.mascaraNuevoOpcion = mascaraNuevoOpcion;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public boolean isActivaTipoMovimiento() {
        return activaTipoMovimiento;
    }

    public void setActivaTipoMovimiento(boolean activaTipoMovimiento) {
        this.activaTipoMovimiento = activaTipoMovimiento;
    }

    public String getMascaraNivel() {
        return mascaraNivel;
    }

    public void setMascaraNivel(String mascaraNivel) {
        this.mascaraNivel = mascaraNivel;
    }

    public String getMascaraSubNivel() {
        return mascaraSubNivel;
    }

    public void setMascaraSubNivel(String mascaraSubNivel) {
        this.mascaraSubNivel = mascaraSubNivel;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public int getLongitudNivel() {
        return longitudNivel;
    }

    public void setLongitudNivel(int longitudNivel) {
        this.longitudNivel = longitudNivel;
    }

    public int getLongitudSubNivel() {
        return longitudSubNivel;
    }

    public void setLongitudSubNivel(int longitudSubNivel) {
        this.longitudSubNivel = longitudSubNivel;
    }

    public String getNivelIn() {
        return nivelIn;
    }

    public void setNivelIn(String nivelIn) {
        this.nivelIn = nivelIn;
    }

    public String getSubNivelIn() {
        return subNivelIn;
    }

    public void setSubNivelIn(String subNivelIn) {
        this.subNivelIn = subNivelIn;
    }

    public void generaPruebaNivel(ValueChangeEvent e) {
        if (!nivelIn.equals("") || !nivelIn.isEmpty()) {
            if (esEntero(nivelIn)) {
                if (Integer.parseInt(nivelIn) != 0) {
                    mascaraNivel = getCntEntidadesService().generaCodigoNiveleAndSubNivel(selectedEntidad, "N", Integer.parseInt(nivelIn));
                    if (!cntEntidadesService.verificaExistenciaMascaraNivelAndSubNivel(mascaraNivel)) {
                        MessageUtils.addErrorMessage("El código " + mascaraNivel + " ya existe inserte otro número.");
                    } else {
                        MessageUtils.addInfoMessage("El código " + mascaraNivel + " no existe puede registrar sin problemas.");
                    }
                    mensajeVerifica1 = getCntEntidadesService().verificaExistenciaCodigo(mascaraNivel);
                    if (selectedEntidad.getIdEntidadPadre() == 0) {
                        activaBotonGuardar = parParametricasService.verificaExistenciaDeCuentaEnDefiniconDeCuentas(nivelIn);
                        codigo = concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos);
                    }
                }
            } else {
                MessageUtils.addErrorMessage("El valor no es un número, introduzca un número.");
            }
        } else {
            activaBotonGuardar = parParametricasService.verificaExistenciaDeCuentaEnDefiniconDeCuentas(nivelIn);
        }
    }

    public void generaPruebaSubNivel(ValueChangeEvent e) {
        try {
            if (!subNivelIn.equals("") || !subNivelIn.isEmpty()) {
                if (esEntero(nivelIn)) {
                    if (Integer.parseInt(subNivelIn) != 0) {
                        mascaraSubNivel = getCntEntidadesService().generaCodigoNiveleAndSubNivel(selectedEntidad, "S", Integer.parseInt(subNivelIn));
                        if (!cntEntidadesService.verificaExistenciaMascaraNivelAndSubNivel(mascaraSubNivel)) {
                            MessageUtils.addErrorMessage("El código " + mascaraSubNivel + " ya existe inserte otro número.");
                        } else {
                            MessageUtils.addInfoMessage("El código " + mascaraSubNivel + " no existe puede registrar sin problemas.");
                        }
                        mensajeVerifica2 = getCntEntidadesService().verificaExistenciaCodigo(mascaraSubNivel);
                    }
                }
            } else {
                MessageUtils.addErrorMessage("El valor no es un número, introduzca un número.");
            }
            activaBotonGuardar = false;
        } catch (NumberFormatException ex) {
        }
    }

    public boolean esEntero(String cad) {
        for (int i = 0; i < cad.length(); i++) {
            if (!Character.isDigit(cad.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public void activaCombillo(ValueChangeEvent e) throws Exception {
        swnivel = false;
        swsubnivel = false;
        swajuste = false;
        activaCentrosCosto = true;
        activaAsignacionCentrosCosto = false;
        selectedEntidad.getNivel();

        if (mascaraNuevoOpcion.equals("N")) {
            swnivel = true;
            if (selectedEntidad.getIdEntidadPadre() == 0) {
                activaBotonGuardar = parParametricasService.verificaExistenciaDeCuentaEnDefiniconDeCuentas(nivelIn);
                tieneCentroCostoEntidad = false;
                activaBotonGuardaPadre = true;
                activaBotonGuardarGeneral = false;
            }
        } else {
            swsubnivel = true;
            activaBotonGuardar = false;
            tieneCentroCostoEntidad = false;
            activaBotonGuardaPadre = false;
            activaBotonGuardarGeneral = true;
        }

        if (selectedEntidad.getNivel() == 3 && mascaraNuevoOpcion.equals("N")) {
            swParAutomatico = false;
            swRadioSubNivel = true;
            tieneCentroCostoEntidad = false;
            activaBotonGuardaPadre = false;
            activaBotonGuardarGeneral = true;
        }
        if (selectedEntidad.getNivel() == 3 && mascaraNuevoOpcion.equals("S")) {
            swParAutomatico = true;
            swRadioSubNivel = true;
            tieneCentroCostoEntidad = false;
            activaBotonGuardaPadre = false;
            activaBotonGuardarGeneral = true;
            cargaDatosDeParametrosAutomaticosDeNivel2Cereados();
        }
        if (selectedEntidad.getNivel() == 2 && mascaraNuevoOpcion.equals("N")) {
            swParAutomatico = true;
            swRadioSubNivel = true;
            cargaDatosDeParametrosAutomaticosDeNivel2Cereados();
            tieneCentroCostoEntidad = false;
            activaBotonGuardaPadre = false;
            activaBotonGuardarGeneral = true;

        }

        if (selectedEntidad.getNivel() == 2 && mascaraNuevoOpcion.equals("S")) {
            swParAutomatico = true;
            swRadioSubNivel = true;
            cntParametroAutomaticoDeNivel2 = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(selectedEntidad);
            cargaDatosDeParametrosAutomaticosDeNivel2();
            if (cntEntidadesService.verificaEntidadCentroCosto(selectedEntidad)) {
                activaBotonGuardaPadre = false;
                activaBotonGuardarGeneral = true;
                tieneCentroCostoEntidad = true;
            }

        }

        if (selectedEntidad.getNivel() == 1 && mascaraNuevoOpcion.equals("N")) {
            swParAutomatico = true;
            swRadioSubNivel = false;
            cntParametroAutomaticoDeNivel2 = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(selectedEntidad);
            cargaDatosDeParametrosAutomaticosDeNivel2();

        }
        if (selectedEntidad.getNivel() == 1 && mascaraNuevoOpcion.equals("S")) {
            swRadioSubNivel = false;
        }
        if (!cntEntidadesService.verificaEntidadCentroCosto(selectedEntidad) && selectedEntidad.getNivel() == 1) {
            activaBotonGuardaPadre = false;
            activaBotonGuardarGeneral = true;
            tieneCentroCostoEntidad = true;
        }

        int nivelNuevoPlanCuenta = cntEntidadesService.obtieneNiveleCuentaSubAndPadre(selectedEntidad, mascaraNuevoOpcion);
        if (nivelNuevoPlanCuenta == 3) {
            tieneCentroDeCostoNivel3 = true;
            tieneCCMasivo = false;
        } else {
            tieneCentroDeCostoNivel3 = false;
            if (nivelNuevoPlanCuenta == 2) {
                botonCentroDeCostoHabilita = true;
                centroDeCostoHabilita = selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
            } else {
                if (nivelNuevoPlanCuenta == 1) {
                    botonCentroDeCostoHabilita = !selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                    centroDeCostoHabilita = selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                    botonIrACentroDeCosto = !selectedEntidad.getTieneCentroCosto().equals(EnumTieneCentroDeCosto.SI.getCodigo());
                } else {
                    botonCentroDeCostoHabilita = true;

                }
            }
        }
    }

    public void cargaDatosDeParametrosAutomaticosDeNivel2Cereados() {
        facDeCompra = EnumParametroAutomatico.NINGUNO.getCodigo();
        facDeVenta = EnumParametroAutomatico.NINGUNO.getCodigo();
        sinFactura = EnumParametroAutomatico.NINGUNO.getCodigo();
        noDeducible = EnumParametroAutomatico.NINGUNO.getCodigo();
        retencion = EnumTipoRetencion.SIN_RETENCION.getCodigo();
        grossingUp = EnumTipoRetencion.SIN_RETENCION.getCodigo();
        tipoMovimiento = EnumTipoMovimiento.AMBOS.getCodigo();
        ajuste = "SAJU";
    }

    public void activaCombo(ValueChangeEvent e) {
        if (mascaraNuevoOpcion.equals("N")) {
            activaTipoMovimiento = getCntEntidadesService().activaTipoMovimientoCombo(selectedEntidad);
        } else {
            if (mascaraNuevoOpcion.equals("S")) {
                activaTipoMovimiento = getCntEntidadesService().activaTipoMovimientoCombo(selectedEntidad);
            }
        }
    }

    public void activaFacturaDeCompra(ValueChangeEvent e) {
        if (facDeCompraHabilita) {
            facDeCompraHabilita = true;

        } else {
            facDeCompraHabilita = false;
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

    public void activaFacturaDeVenta(ValueChangeEvent e) {
        if (facDeVentaHabilita) {
            facDeVentaHabilita = true;

        } else {
            facDeVentaHabilita = false;
        }
    }

    public void activaSinFactura(ValueChangeEvent e) {
        if (sinfacturaHabilita) {
            sinfacturaHabilita = true;

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

    public void activaGrossing(ValueChangeEvent e) {
        if (grossingHabilita) {
            comboGrossinUpHabilita = false;



        } else {
            ParTipoRetencion parTipoRetencionGrossingUp = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo());
            comboGrossinUpHabilita = true;
            grossingUp = parTipoRetencionGrossingUp.getCodigo();
        }
    }

    public boolean isSwajuste() {
        return swajuste;
    }

    public void setSwajuste(boolean swajuste) {
        this.swajuste = swajuste;
    }

    public String getMensajeVerifica1() {
        return mensajeVerifica1;
    }

    public void setMensajeVerifica1(String mensajeVerifica1) {
        this.mensajeVerifica1 = mensajeVerifica1;
    }

    public String getMensajeVerifica2() {
        return mensajeVerifica2;
    }

    public void setMensajeVerifica2(String mensajeVerifica2) {
        this.mensajeVerifica2 = mensajeVerifica2;
    }

    public boolean verificaExistenciaCodigo(CntEntidad CntObjetos) {
        return getCntEntidadesService().verificaExistenciaMascara(CntObjetos);
    }

    public CntMascara getCntMascaras() {
        return cntMascaras;
    }

    public void setCntMascaras(CntMascara cntMascaras) {
        this.cntMascaras = cntMascaras;
    }

    public boolean isActivaCentrosCosto() {
        return activaCentrosCosto;
    }

    public void setActivaCentrosCosto(boolean activaCentrosCosto) {
        this.activaCentrosCosto = activaCentrosCosto;
    }

    public String adicionar1() {
        if (selectedEntidad != null) {
            setInSessionIdEntidad3(selectedEntidad.getIdEntidad());
            return "objetosAdicionar";
        } else {
            MessageUtils.addErrorMessage("Debe seleccionar una cuenta.");
        }
        return null;
    }

    public String modificar1() {
        setInSessionIdEntidad3(selectedEntidad.getIdEntidad());
        setInSessionIntAuxiliar(selectedEntidad.getNivel()); //Nivel cuenta para modificar
        return "objetosModificar";
    }

    public String cancelar() {
        return "planCuentas";
    }

    public String cancelarAsignacionCentroDeCosto() {
        return "planCuentasAdicionar";
    }

    public boolean isActivaAsignacionCentrosCosto() {
        return activaAsignacionCentrosCosto;
    }

    public void setActivaAsignacionCentrosCosto(boolean activaAsignacionCentrosCosto) {
        this.activaAsignacionCentrosCosto = activaAsignacionCentrosCosto;
    }

    public int getNumeroEspaciador() {
        return numeroEspaciador;
    }

    public void setNumeroEspaciador(int numeroEspaciador) {
        this.numeroEspaciador = numeroEspaciador;
    }

    public int getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(int movimiento) {
        this.movimiento = movimiento;
    }

    public String getAjuste() {
        return ajuste;
    }

    public boolean isActivaDebeHaber() {
        return activaDebeHaber;
    }

    public void setActivaDebeHaber(boolean activaDebeHaber) {
        this.activaDebeHaber = activaDebeHaber;
    }

    public void setAjuste(String ajuste) {
        this.ajuste = ajuste;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public boolean isActivaPanelAsigCentroCosto() {
        return activaPanelAsigCentroCosto;
    }

    public void setActivaPanelAsigCentroCosto(boolean activaPanelAsigCentroCosto) {
        this.activaPanelAsigCentroCosto = activaPanelAsigCentroCosto;
    }

    public boolean isSwnivel() {
        return swnivel;
    }

    public void setSwnivel(boolean swnivel) {
        this.swnivel = swnivel;
    }

    public boolean isSwsubnivel() {
        return swsubnivel;
    }

    public void setSwsubnivel(boolean swsubnivel) {
        this.swsubnivel = swsubnivel;
    }

    //// Codigo nurvo///////
    public void listaTodo() {
        if (eleccionTodos) {
            color = "para-elegidas";
            listaMomentanea = filteredCntObjetos;
            filteredCntObjetos = null;
        } else {
            filteredCntObjetos = listaMomentanea;
            listaMomentanea = null;
            color = "para-filas";
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getEleccionTodos() {
        return eleccionTodos;
    }

    public void setEleccionTodos(Boolean eleccionTodos) {
        this.eleccionTodos = eleccionTodos;
    }

    public List<CntEntidad> getListaMomentanea() {
        return listaMomentanea;
    }

    public void setListaMomentanea(List<CntEntidad> listaMomentanea) {
        this.listaMomentanea = listaMomentanea;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    //</editor-fold>
    public CntEntidad getSelectedObjetos() {
        return selectedEntidad;
    }

    public void setSelectedObjetos(CntEntidad selectedEntidad) {
        this.selectedEntidad = selectedEntidad;
    }

    public String getGruposNivelCombo() {
        return gruposNivelCombo;
    }

    public void setGruposNivelCombo(String gruposNivelCombo) {
        this.gruposNivelCombo = gruposNivelCombo;
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

    public CntEntidad getSelectedEntidad() {
        return selectedEntidad;
    }

    public void setSelectedEntidad(CntEntidad selectedEntidad) {
        this.selectedEntidad = selectedEntidad;
    }

    public String getFacDeCompra() {
        return facDeCompra;
    }

    public void setFacDeCompra(String facDeCompra) {
        this.facDeCompra = facDeCompra;
    }

    public String getSinFactura() {
        return sinFactura;
    }

    public void setSinFactura(String sinFactura) {
        this.sinFactura = sinFactura;
    }

    public String getFacDeVenta() {
        return facDeVenta;
    }

    public void setFacDeVenta(String facDeVenta) {
        this.facDeVenta = facDeVenta;
    }

    public String getRetencion() {
        return retencion;
    }

    public String getNoDeducible() {
        return noDeducible;
    }

    public void setNoDeducible(String noDeducible) {
        this.noDeducible = noDeducible;
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

    public Boolean getSwRetencion() {
        return swRetencion;
    }

    public void setSwRetencion(Boolean swRetencion) {
        this.swRetencion = swRetencion;
    }

    public List<ParAjustes> getParAjustesList() {
        return parAjustesList;
    }

    public void setParAjustesList(List<ParAjustes> parAjustesList) {
        this.parAjustesList = parAjustesList;
    }

    public Boolean getRenderNivel2AndNivel1() {
        return renderNivel2AndNivel1;
    }

    public void setRenderNivel2AndNivel1(Boolean renderNivel2AndNivel1) {
        this.renderNivel2AndNivel1 = renderNivel2AndNivel1;
    }

    public CntParametroAutomatico getCntParametroAutomatico() {
        return cntParametroAutomatico;
    }

    public void setCntParametroAutomatico(CntParametroAutomatico cntParametroAutomatico) {
        this.cntParametroAutomatico = cntParametroAutomatico;
    }

    public void activaTipoDeRetencion(ValueChangeEvent e) {
        if (!retencion.equals(EnumTipoRetencion.SIN_RETENCION.getCodigo())) {
            swRetencion = true;
            activaDebeHaber = false;
//            tipoDeRetencion = "DEB";
        } else {
            swRetencion = false;
            activaDebeHaber = true;
//            tipoDeRetencion = null;
        }
    }

    public String obtieneEnumTipoAjuste(String tipoAjuste) {
        if (tipoAjuste.equals("ANTERIOR")) {
            return EnumTipoAjuste.ANTERIOR.getCodigo();
        }
        return EnumTipoAjuste.ACTUAL.getCodigo();
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

    public Boolean getSwParAutomatico() {
        return swParAutomatico;
    }

    public void setSwParAutomatico(Boolean swParAutomatico) {
        this.swParAutomatico = swParAutomatico;
    }

    public Boolean getSwRadioSubNivel() {
        return swRadioSubNivel;
    }

    public void setSwRadioSubNivel(Boolean swRadioSubNivel) {
        this.swRadioSubNivel = swRadioSubNivel;
    }

    public List<CntParametroAutomatico> getListParametroAutomatico() {
        return listParametroAutomatico;
    }

    public void setListParametroAutomatico(List<CntParametroAutomatico> listParametroAutomatico) {
        this.listParametroAutomatico = listParametroAutomatico;
    }

    public Boolean getSwActivaBoton() {
        return swActivaBoton;
    }

    public void setSwActivaBoton(Boolean swActivaBoton) {
        this.swActivaBoton = swActivaBoton;
    }

    public List<CntEntidad> getListPadres() {
        return listPadres;
    }

    public void setListPadres(List<CntEntidad> listPadres) {
        this.listPadres = listPadres;
    }

    public List<CntNivel> getListaNivel() {
        return listaNivel;
    }

    public void setListaNivel(List<CntNivel> listaNivel) {
        this.listaNivel = listaNivel;
    }

    //Nuevos metodos y (Get,Set) Henrry
    public int getNumeroEspaciadorAdicionar() {
        return numeroEspaciadorAdicionar;
    }

    public void setNumeroEspaciadorAdicionar(int numeroEspaciadorAdicionar) {
        this.numeroEspaciadorAdicionar = numeroEspaciadorAdicionar;
    }

    public String getMascaraNivelPosicionUno() {
        return mascaraNivelPosicionUno;
    }

    public void setMascaraNivelPosicionUno(String mascaraNivelPosicionUno) {
        this.mascaraNivelPosicionUno = mascaraNivelPosicionUno;
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

    public String getMascaraSubNivelPosicionDos() {
        return mascaraSubNivelPosicionDos;
    }

    public void setMascaraSubNivelPosicionDos(String mascaraSubNivelPosicionDos) {
        this.mascaraSubNivelPosicionDos = mascaraSubNivelPosicionDos;
    }

    public String concatenaNuevaMascaraDatosVista(String mascaraParteUno, String cajaDetexto, String mascaraParteDos) {
        return mascaraParteUno + cajaDetexto + mascaraParteDos;
    }

    public String getNuevaMasacaraNivelVista() {
        return nuevaMasacaraNivelVista;
    }

    public void setNuevaMasacaraNivelVista(String nuevaMasacaraNivelVista) {
        this.nuevaMasacaraNivelVista = nuevaMasacaraNivelVista;
    }

    public String getNuevaMasacaraSubNivelVista() {
        return nuevaMasacaraSubNivelVista;
    }

    public void setNuevaMasacaraSubNivelVista(String nuevaMasacaraSubNivelVista) {
        this.nuevaMasacaraSubNivelVista = nuevaMasacaraSubNivelVista;
    }

    public Boolean getDesactivaRadioButtonSubNivel() {
        return desactivaRadioButtonSubNivel;
    }

    public void setDesactivaRadioButtonSubNivel(Boolean desactivaRadioButtonSubNivel) {
        this.desactivaRadioButtonSubNivel = desactivaRadioButtonSubNivel;
    }
    //fin metodos hnerry

    public List<ParTipoRetencion> getParRetencionesList() {
        return parRetencionesList;
    }

    public void setParRetencionesList(List<ParTipoRetencion> parRetencionesList) {
        this.parRetencionesList = parRetencionesList;
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

    public Boolean getFacDeCompraHabilita() {
        return facDeCompraHabilita;
    }

    public void setFacDeCompraHabilita(Boolean facDeCompraHabilita) {
        this.facDeCompraHabilita = facDeCompraHabilita;
    }

    public Boolean getFacDeVentaHabilita() {
        return facDeVentaHabilita;
    }

    public void setFacDeVentaHabilita(Boolean facDeVentaHabilita) {
        this.facDeVentaHabilita = facDeVentaHabilita;
    }

    public Boolean getGrossingHabilita() {
        return grossingHabilita;
    }

    public void setGrossingHabilita(Boolean grossingHabilita) {
        this.grossingHabilita = grossingHabilita;
    }

    public Boolean getRetencionHabilita() {
        return retencionHabilita;
    }

    public void setRetencionHabilita(Boolean retencionHabilita) {
        this.retencionHabilita = retencionHabilita;
    }

    public Boolean getSinfacturaHabilita() {
        return sinfacturaHabilita;
    }

    public void setSinfacturaHabilita(Boolean sinfacturaHabilita) {
        this.sinfacturaHabilita = sinfacturaHabilita;
    }

    public Boolean getNoDeducibleHabilita() {
        return noDeducibleHabilita;
    }

    public void setNoDeducibleHabilita(Boolean noDeducibleHabilita) {
        this.noDeducibleHabilita = noDeducibleHabilita;
    }

    public Boolean getGrossingUpActivar() {
        return grossingUpActivar;
    }

    public void setGrossingUpActivar(Boolean grossingUpActivar) {
        this.grossingUpActivar = grossingUpActivar;
    }

    public Boolean getRetencionActiva() {
        return retencionActiva;
    }

    public void setRetencionActiva(Boolean retencionActiva) {
        this.retencionActiva = retencionActiva;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }

    public Boolean getCombustibleHabilita() {
        return combustibleHabilita;
    }

    public void setCombustibleHabilita(Boolean combustibleHabilita) {
        this.combustibleHabilita = combustibleHabilita;
    }

    public String getTipoCalculoAutomatico() {
        return tipoCalculoAutomatico;
    }

    public void setTipoCalculoAutomatico(String tipoCalculoAutomatico) {
        this.tipoCalculoAutomatico = tipoCalculoAutomatico;
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

    public Boolean getBotonFacturaVentaHabilita() {
        return botonFacturaVentaHabilita;
    }

    public void setBotonFacturaVentaHabilita(Boolean botonFacturaVentaHabilita) {
        this.botonFacturaVentaHabilita = botonFacturaVentaHabilita;
    }

    public Boolean getBotonSinFacturaHabilita() {
        return botonSinFacturaHabilita;
    }

    public void setBotonSinFacturaHabilita(Boolean botonSinFacturaHabilita) {
        this.botonSinFacturaHabilita = botonSinFacturaHabilita;
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

    //desde aqui para Centro de Costo por Jonas
    public void activaCentroDeCosto(ValueChangeEvent e) {
        if (centroDeCostoHabilita) {
            botonIrACentroDeCosto = false;
        } else {
            botonIrACentroDeCosto = true;
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

    public void activaProyecto(ValueChangeEvent e) {
        if (proyectoHabilita) {
            botonIrAProyecto = false;
        } else {
            botonIrAProyecto = true;
        }
    }

    public String irAsignacionCentroDeCosto() {
        setInSessiontIdEntidadAsignacionCC(cntEntidad.getIdEntidad());
        return "asignacionCentroDeCosto";
    }

    public Boolean getBotonCentroDeCostoHabilita() {
        return botonCentroDeCostoHabilita;
    }

    public void setBotonCentroDeCostoHabilita(Boolean botonCentroDeCostoHabilita) {
        this.botonCentroDeCostoHabilita = botonCentroDeCostoHabilita;
    }

    public Boolean getCentroDeCostoHabilita() {
        return centroDeCostoHabilita;
    }

    public void setCentroDeCostoHabilita(Boolean centroDeCostoHabilita) {
        this.centroDeCostoHabilita = centroDeCostoHabilita;
    }

    public Boolean getBotonIrACentroDeCosto() {
        return botonIrACentroDeCosto;
    }

    public void setBotonIrACentroDeCosto(Boolean botonIrACentroDeCosto) {
        this.botonIrACentroDeCosto = botonIrACentroDeCosto;
    }
    //hasta aqui para Centro de Costo por Jonas

    //Get y Set para asignacion de centros de costo por Henrry Guzman.
    public void inicializaValoresAsignacionCentrosDeCosto() throws Exception {
        cntEntidad = new CntEntidad();
        cntEntidad.setDescripcion(descripcion);
        if (mascaraNuevoOpcion.equals("N")) {
            cntEntidad.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos));
        } else {
            cntEntidad.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos));
        }
    }

    public void inicializaValoresAsignacionAuxiliares() throws Exception {
    }

    public void inicializaValoresAsignacionProyectos() throws Exception {
    }

    public List<CntEntidad> listaCentrosDeCostoInicial() {
        try {
            listaCentrosDeCosto = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.CENTROS_COSTOS.getCodigo());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCentrosDeCosto;
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

    public void obtieneObjetoPlanCuentas(ValueChangeEvent e) {
    }

    public String insertaPuntosAutomatio(CntEntidad cntEntidad) {
        return cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad);
    }

    public List<CntEntidad> getListaCentrosDeCosto() {
        return listaCentrosDeCosto;
    }

    public void setListaCentrosDeCosto(List<CntEntidad> listaCentrosDeCosto) {
        this.listaCentrosDeCosto = listaCentrosDeCosto;
    }

    public List<CntEntidad> getListaCentrosDeCostoElegidaParaQuitarse() {
        return listaCentrosDeCostoElegidaParaQuitarse;
    }

    public void setListaCentrosDeCostoElegidaParaQuitarse(List<CntEntidad> listaCentrosDeCostoElegidaParaQuitarse) {
        this.listaCentrosDeCostoElegidaParaQuitarse = listaCentrosDeCostoElegidaParaQuitarse;
    }

    public List<CntConfiguracionCentrocosto> getListaCentrosDeCostoElegidaParaQuitarseAux() {
        return listaCentrosDeCostoElegidaParaQuitarseAux;
    }

    public void setListaCentrosDeCostoElegidaParaQuitarseAux(List<CntConfiguracionCentrocosto> listaCentrosDeCostoElegidaParaQuitarseAux) {
        this.listaCentrosDeCostoElegidaParaQuitarseAux = listaCentrosDeCostoElegidaParaQuitarseAux;
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

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

//  Desde aqui el codigo Jonas para guardar la Configuracion de Centro de Costos por Definicion
    public String guardarCntConfiguracionCentroCostoPorDefinicion() throws Exception {
        if (!listaCntConfiguracionCentrocosto.isEmpty()) {
            cntConfiguracionCentroCostoService.guardaCntConfiguracionCentroCostoDefinicion(listaCntConfiguracionCentrocosto, getLoginSession(), cntEntidad);
            setInSessionIdEntidad2(cntEntidad.getIdEntidad());
            return "planCuentasModificar";
        }
        MessageUtils.addErrorMessage("No se asigno correctamente los Centros de Costo");
        return null;
    }

    public String guardarCntConfiguracionCentroCostoPorDefinicionParaAdicionEntidad() throws Exception {
        if (!listaCntConfiguracionCentrocosto.isEmpty()) {
            cntConfiguracionCentroCostoService.guardaCntConfiguracionCentroCostoDefinicion(listaCntConfiguracionCentrocosto, getLoginSession(), cntEntidadDefinicionCentrosCosto);
            return "planCuentas";
        }
        MessageUtils.addErrorMessage("No se asigno correctamente los Centros de Costo");
        return null;
    }

    public String cancelaCntConfiguracionCentroCostoPorDefinicion() {
        setInSessionIdEntidad2(cntEntidad.getIdEntidad());
        return "planCuentasModificar";
    }

    public String cancelaCntConfiguracionCentroCostoAlternativa() {
        setInSessiontCodigoParRecetasDistribucionCC(null);
        return "listaDistribucionDeCentrosDeCosto";
    }

    public CntConfiguracionCentroCostoService getCntConfiguracionCentroCostoService() {
        return cntConfiguracionCentroCostoService;
    }

    public void setCntConfiguracionCentroCostoService(CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService) {
        this.cntConfiguracionCentroCostoService = cntConfiguracionCentroCostoService;
    }

    public Boolean getEsDefinicion() {
        return esDefinicion;
    }

    public void setEsDefinicion(Boolean esDefinicion) {
        this.esDefinicion = esDefinicion;
    }
//  Hasta aqui el codigo Jonas para guardar la Configuracion de Centro de Costos por Definicion

    public List<CntConfiguracionCentrocosto> getListaCntConfiguracionCentrocosto() {
        return listaCntConfiguracionCentrocosto;
    }

    public void setListaCntConfiguracionCentrocosto(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto) {
        this.listaCntConfiguracionCentrocosto = listaCntConfiguracionCentrocosto;
    }

    public CntConfiguracionCentrocosto getConfiguracionCentrocosto() {
        return configuracionCentrocosto;
    }

    public void setConfiguracionCentrocosto(CntConfiguracionCentrocosto configuracionCentrocosto) {
        this.configuracionCentrocosto = configuracionCentrocosto;
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

    public void modificaCntConfiguracionCentroCostoPorDefinicion() throws Exception {
        if (cntEntidadesService.validaSumaTotalProcentajeAlCienPorcientoParaModificar(listaCntConfiguracionCentrocosto)) {
            if (!listaCntConfiguracionCentrocosto.isEmpty()) {
                cntConfiguracionCentroCostoService.modificarCntConfiguracionCentroCostoDefinicion(listaCntConfiguracionCentrocosto, getLoginSession(), cntEntidad);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mensaje Error:", "La suma total de porcentajes no cumple 100%, no se puede modificar."));
        }

    }

    public String cancelarCntConfiguracionCentroCostoPorDefinicionParaAdiconEntidad() throws Exception {
        if (cntEntidadesService.verificaEntidadCentroCosto(selectedEntidad)) {
            listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
            listaCentrosDeCosto = new ArrayList<CntEntidad>();
            listaCntConfiguracionCentrocosto = cntEntidadesService.cargaListadoConfiguracionDesdeUnPlanCuentas(selectedEntidad);
            listaCentrosDeCosto = cntEntidadesService.filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(listaCntConfiguracionCentrocosto);
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
            actimaBotonModificacionAsignacion = true;
            esDefinicion = false;
        }
        return "planCuentas";
    }

    public void cancelarCntConfiguracionCentroCostoPorDefinicionModificacion() throws Exception {
        if (cntEntidadesService.verificaEntidadCentroCosto(selectedEntidad)) {
            listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
            listaCentrosDeCosto = new ArrayList<CntEntidad>();
            listaCntConfiguracionCentrocosto = cntEntidadesService.cargaListadoConfiguracionDesdeUnPlanCuentas(selectedEntidad);
            listaCentrosDeCosto = cntEntidadesService.filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(listaCntConfiguracionCentrocosto);
            sumaPorsentajeTotal = cntEntidadesService.SumaTotalProcentajeAlCienPorciento(listaCntConfiguracionCentrocosto);
            disponibilidad = new BigDecimal("100.0").subtract(sumaPorsentajeTotal);
            actimaBotonModificacionAsignacion = true;
            esDefinicion = false;
        }
    }

    public Boolean getActimaBotonModificacionAsignacion() {
        return actimaBotonModificacionAsignacion;
    }

    public void setActimaBotonModificacionAsignacion(Boolean actimaBotonModificacionAsignacion) {
        this.actimaBotonModificacionAsignacion = actimaBotonModificacionAsignacion;
    }

    public void saveCntEntidadParaAsignacionCC_action() throws Exception {
        cntEntidad = new CntEntidad();
        cntEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
        cntEntidad.setDescripcion(descripcion);
        cntEntidad.setIdEntidadPadre(super.getFromSessionIdEntidad3());
        if (mascaraNuevoOpcion == null) {
            MessageUtils.addInfoMessage("No se registró, ya que no selecciono ninguna opción");
        } else if (mascaraNuevoOpcion.equals("N")) {
            cntEntidad.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos));
        } else {
            cntEntidad.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos));
        }
        cntEntidad.setCntMascara(selectedEntidad.getCntMascara());
        cntEntidad.setNivel(cntEntidadesService.obtieneNiveleCuentaSubAndPadre(selectedEntidad, mascaraNuevoOpcion));
        ParTiposMovimiento parTiposMovimiento = (ParTiposMovimiento) parParametricasService.find(ParTiposMovimiento.class, tipoMovimiento);
        cntEntidad.setParTiposMovimiento(parTiposMovimiento);

        if (selectedEntidad.getNivel()
                == 1) {
            cntEntidad.setIdEntidadPadre(selectedEntidad.getIdEntidadPadre());
        } else {
            cntEntidad.setIdEntidadPadre(selectedEntidad.getIdEntidad());
        }

        super.setPersistValues(cntEntidad);

        if (cntEntidadesService.verificaExistenciaMascara(cntEntidad)) {
//            cntEntidad.setDescripcion(cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad));
            ParAjustes parAjustes = (ParAjustes) parParametricasService.find(ParAjustes.class, ajuste);
            ParTipoRetencion parTipoRetencion = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, retencion);
            ParTipoRetencion parTipoRetencionGrossing = (ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, grossingUp);
            cntParametroAutomatico = new CntParametroAutomatico();

            cntParametroAutomatico.setCntEntidad(cntEntidad);
            cntParametroAutomatico.setTipoCalculoAutomatico(tipoCalculoAutomatico);
            if (facDeCompraHabilita == true) {
                cntParametroAutomatico.setFacturaCompra(facDeCompra);

            } else {
                cntParametroAutomatico.setFacturaCompra(EnumParametroAutomatico.NINGUNO.getCodigo());
            }

            if (facDeCompraHabilita == true) {
                cntParametroAutomatico.setFacturaCompra(facDeCompra);
            } else {
                cntParametroAutomatico.setFacturaCompra(EnumParametroAutomatico.NINGUNO.getCodigo());
            }
            if (facDeVentaHabilita == true) {
                cntParametroAutomatico.setFacturaVenta(facDeVenta);
            } else {
                cntParametroAutomatico.setFacturaVenta(EnumParametroAutomatico.NINGUNO.getCodigo());
            }
            if (noDeducibleHabilita == true) {
                cntParametroAutomatico.setCreditoFiscalNoDeducible(noDeducible);
            } else {
                cntParametroAutomatico.setCreditoFiscalNoDeducible(EnumParametroAutomatico.NINGUNO.getCodigo());
            }
            if (sinfacturaHabilita == true) {
                if (sinfacturaDebe == true && sinfacturaHaber == true) {
                    cntParametroAutomatico.setSinFactura(EnumParametroAutomatico.AMBOS.getCodigo());

                } else {
                    if (sinfacturaDebe == true) {
                        cntParametroAutomatico.setSinFactura(EnumParametroAutomatico.DEBE.getCodigo());
                    }
                    if (sinfacturaHaber == true) {
                        cntParametroAutomatico.setSinFactura(EnumParametroAutomatico.HABER.getCodigo());
                    }
                }
            } else {
                cntParametroAutomatico.setSinFactura(EnumParametroAutomatico.NINGUNO.getCodigo());
            }

            if (retencionHabilita == true) {
                cntParametroAutomatico.setParTipoRetencion(parTipoRetencion);
            } else {
                cntParametroAutomatico.setParTipoRetencion((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo()));
            }
            if (grossingHabilita == true) {
                cntParametroAutomatico.setParTipoRetencionGrossingUp(parTipoRetencionGrossing);
            } else {
                cntParametroAutomatico.setParTipoRetencionGrossingUp((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, EnumTipoRetencion.SIN_RETENCION.getCodigo()));
            }
            cntParametroAutomatico.setTipoMovimiento(tipoMovimiento);
            cntParametroAutomatico.setParAjustes(parAjustes);
            setPersistValues(cntParametroAutomatico);
            cntEntidad = cntEntidadesService.persistCntObjetosConAsignacionPorDefinicion(cntEntidad, mascaraNuevoOpcion, cntParametroAutomatico);
            descripcion = "";
            mascaraNuevoOpcion = "";
            mascaraNuevo = "";
            mascaraNivel = "";
            mascaraSubNivel = "";
            activaTipoMovimiento = false;
            cargaDatosDeParametrosAutomaticosDeNivel2Cereados();
            swRetencion = false;
            cntEntidadDefinicionCentrosCosto = cntEntidadesService.find(CntEntidad.class, cntEntidad.getIdEntidad());            

        } else {
            MessageUtils.addInfoMessage("La cuenta " + mascaraNuevo + " ya existe");
        }
    }

    public void guardaCuentaYAsignaCentrosDeCosto() throws Exception {
        saveCntEntidadParaAsignacionCC_action();
        if (cntEntidadesService.verificaEntidadCentroCosto(cntEntidad)) {
            listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
            listaCentrosDeCosto = new ArrayList<CntEntidad>();
            listaCentrosDeCosto = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
            actimaBotonModificacionAsignacion = false;
            esDefinicion = true;
            centroDeCostoHabilita = false;
        }

    }

    public String irPlanCuentasParaAdicionarEntidad() {
        return "planCuentas";
    }

    public CntEntidad getCntEntidadDefinicionCentrosCosto() {
        return cntEntidadDefinicionCentrosCosto;
    }

    public void setCntEntidadDefinicionCentrosCosto(CntEntidad cntEntidadDefinicionCentrosCosto) {
        this.cntEntidadDefinicionCentrosCosto = cntEntidadDefinicionCentrosCosto;
    }

    public Boolean getTieneCentroCostoEntidad() {
        return tieneCentroCostoEntidad;
    }

    public void setTieneCentroCostoEntidad(Boolean tieneCentroCostoEntidad) {
        this.tieneCentroCostoEntidad = tieneCentroCostoEntidad;
    }

    public ParCuentasGenerales getParCuentasGenerales() {
        return parCuentasGenerales;
    }

    public void setParCuentasGenerales(ParCuentasGenerales parCuentasGenerales) {
        this.parCuentasGenerales = parCuentasGenerales;
    }

    //Fin Get y Set para asigancion de centros de Costo por Henrry Guzman.
    public Boolean getActivaBotonGuardar() {
        return activaBotonGuardar;
    }

    public void setActivaBotonGuardar(Boolean activaBotonGuardar) {
        this.activaBotonGuardar = activaBotonGuardar;
    }

    public ParCuentasGenerales getSelectParCuentasGenerales() {
        return selectParCuentasGenerales;
    }

    public void setSelectParCuentasGenerales(ParCuentasGenerales selectParCuentasGenerales) {
        this.selectParCuentasGenerales = selectParCuentasGenerales;
    }

    public void verificaValoresAdicionPlanCuenta(ValueChangeEvent e) {
        if (descripcion.isEmpty()) {
            muestraDialogo = true;
        } else {
            muestraDialogo = false;
        }
        codigo = concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos);
    }

    public String saveCntEntidadyCuentaGeneral_action() throws Exception {
        if (selectParCuentasGenerales != null) {
            cntEntidad = new CntEntidad();
            cntEntidad.setDescripcion(descripcion);
            cntEntidad.setIdEntidadPadre(super.getFromSessionIdEntidad3());
            cntEntidad.setTieneCentroCosto(EnumTieneCentroDeCosto.NO.getCodigo());
            cntEntidad.setTieneAuxiliar(EnumTieneAuxiliar.NO.getCodigo());
            if (mascaraNuevoOpcion == null) {
                MessageUtils.addInfoMessage("No se registró, ya que no selecciono ninguna opción");
                return null;
            } else if (mascaraNuevoOpcion.equals("N")) {
                cntEntidad.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos));
            } else {
                cntEntidad.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos));
            }
            cntEntidad.setCntMascara(selectedEntidad.getCntMascara());
            cntEntidad.setNivel(cntEntidadesService.obtieneNiveleCuentaSubAndPadre(selectedEntidad, mascaraNuevoOpcion));
            ParTiposMovimiento parTiposMovimiento = (ParTiposMovimiento) parParametricasService.find(ParTiposMovimiento.class, tipoMovimiento);
            cntEntidad.setParTiposMovimiento(parTiposMovimiento);

            if (selectedEntidad.getNivel() == 1) {
                cntEntidad.setIdEntidadPadre(selectedEntidad.getIdEntidadPadre());
            } else {
                if (selectedEntidad.getIdEntidadPadre() == 0L) {
                    cntEntidad.setIdEntidadPadre(selectedEntidad.getIdEntidadPadre());
                } else {
                    cntEntidad.setIdEntidadPadre(selectedEntidad.getIdEntidad());
                }
            }

            super.setPersistValues(cntEntidad);

            if (cntEntidadesService.verificaExistenciaMascara(cntEntidad)) {
                cntParametroAutomatico = new CntParametroAutomatico();
                cntEntidadesService.guardaCuentaGeneralYPlanDeCuentas(cntEntidad, mascaraNuevoOpcion, cntParametroAutomatico, selectParCuentasGenerales.getCodigo());
                descripcion = "";
                mascaraNuevoOpcion = "";
                mascaraNuevo = "";
                mascaraNivel = "";
                mascaraSubNivel = "";
                activaTipoMovimiento = false;
                cargaDatosDeParametrosAutomaticosDeNivel2Cereados();
                swRetencion = false;
                return "planCuentas";

            } else {
                MessageUtils.addInfoMessage("La cuenta " + mascaraNuevo + " ya existe");
                return null;
            }
        }
        MessageUtils.addInfoMessage("Debe selecionar una opción.");
        return null;
    }

    public Boolean getMuestraDialogo() {
        return muestraDialogo;
    }

    public void setMuestraDialogo(Boolean muestraDialogo) {
        this.muestraDialogo = muestraDialogo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void selecEvento(ValueChangeEvent e) {        
    }

    public Boolean getActivaBotonGuardaPadre() {
        return activaBotonGuardaPadre;
    }

    public void setActivaBotonGuardaPadre(Boolean activaBotonGuardaPadre) {
        this.activaBotonGuardaPadre = activaBotonGuardaPadre;
    }

    public Boolean getHabilitaDialogoCC() {
        return habilitaDialogoCC;
    }

    public void setHabilitaDialogoCC(Boolean habilitaDialogoCC) {
        this.habilitaDialogoCC = habilitaDialogoCC;
    }

    public Boolean getAuxiliarHabilita() {
        return auxiliarHabilita;
    }

    public void setAuxiliarHabilita(Boolean auxiliarHabilita) {
        this.auxiliarHabilita = auxiliarHabilita;
    }

    public Boolean getBotonIrAsignacionAuxiliar() {
        return botonIrAsignacionAuxiliar;
    }

    public void setBotonIrAsignacionAuxiliar(Boolean botonIrAsignacionAuxiliar) {
        this.botonIrAsignacionAuxiliar = botonIrAsignacionAuxiliar;
    }

    public List<CntAuxiliar> listaAuxiliaresPorAsignar() throws Exception {
        return listaSelecionadosParaAsignarSelec = cntAuxiliaresService.listaCntAuxiliares();
    }

    public CntAuxiliaresService getCntAuxiliaresService() {
        return cntAuxiliaresService;
    }

    public void setCntAuxiliaresService(CntAuxiliaresService cntAuxiliaresService) {
        this.cntAuxiliaresService = cntAuxiliaresService;
    }

    public List<CntAuxiliar> getSelectAuxiliarParaAsignacion() {
        return selectAuxiliarParaAsignacion;
    }

    public void setSelectAuxiliarParaAsignacion(List<CntAuxiliar> selectAuxiliarParaAsignacion) {
        this.selectAuxiliarParaAsignacion = selectAuxiliarParaAsignacion;
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

    public void limpiaListaDeAuxiliaresSeleccionadosModificar() {
        selectAuxiliarParaAsignacionModificacion = new ArrayList<CntAuxiliar>();
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

    public void limpiaListaDeCentrosDeCostoSeleccionados() {
        listaCntConfiguracionCentrocosto = new ArrayList<CntConfiguracionCentrocosto>();
    }

    public void validaValoresAntesDeGuardarPorcentajes() {
        if (!listaCntConfiguracionCentrocosto.isEmpty()) {
            if (!cntDistribucionCentroCostoService.verificaValoresListaCentroCostos(listaCntConfiguracionCentrocosto)) {
                MessageUtils.addErrorMessage("No se asigno correctamente los porcentajes a las cuentas");
            }
        } else {
            MessageUtils.addErrorMessage("No se asigno correctamente los Centros de Costo");
        }
    }

    public CntDistribucionCentroCostoService getCntDistribucionCentroCostoService() {
        return cntDistribucionCentroCostoService;
    }

    public void setCntDistribucionCentroCostoService(CntDistribucionCentroCostoService cntDistribucionCentroCostoService) {
        this.cntDistribucionCentroCostoService = cntDistribucionCentroCostoService;
    }

    public Boolean getBotonAuxiliarHabilita() {
        return botonAuxiliarHabilita;
    }

    public void setBotonAuxiliarHabilita(Boolean botonAuxiliarHabilita) {
        this.botonAuxiliarHabilita = botonAuxiliarHabilita;
    }

    public Boolean getBotonProyectoHabilita() {
        return botonProyectoHabilita;
    }

    public void setBotonProyectoHabilita(Boolean botonProyectoHabilita) {
        this.botonProyectoHabilita = botonProyectoHabilita;
    }

    public Boolean getProyectoHabilita() {
        return proyectoHabilita;
    }

    public void setProyectoHabilita(Boolean proyectoHabilita) {
        this.proyectoHabilita = proyectoHabilita;
    }

    public Boolean getBotonIrAAuxiliar() {
        return botonIrAAuxiliar;
    }

    public void setBotonIrAAuxiliar(Boolean botonIrAAuxiliar) {
        this.botonIrAAuxiliar = botonIrAAuxiliar;
    }

    public Boolean getBotonIrAProyecto() {
        return botonIrAProyecto;
    }

    public void setBotonIrAProyecto(Boolean botonIrAProyecto) {
        this.botonIrAProyecto = botonIrAProyecto;
    }

    public Boolean getHabilitaDialogoAuxiliar() {
        return habilitaDialogoAuxiliar;
    }

    public void setHabilitaDialogoAuxiliar(Boolean habilitaDialogoAuxiliar) {
        this.habilitaDialogoAuxiliar = habilitaDialogoAuxiliar;
    }

    public Boolean getHabilitaDialogoProyecto() {
        return habilitaDialogoProyecto;
    }

    public void setHabilitaDialogoProyecto(Boolean habilitaDialogoProyecto) {
        this.habilitaDialogoProyecto = habilitaDialogoProyecto;
    }

    public List<CntAuxiliar> getSelectAuxiliarParaAsignacionModificacion() {
        return selectAuxiliarParaAsignacionModificacion;
    }

    public void setSelectAuxiliarParaAsignacionModificacion(List<CntAuxiliar> selectAuxiliarParaAsignacionModificacion) {
        this.selectAuxiliarParaAsignacionModificacion = selectAuxiliarParaAsignacionModificacion;
    }

    public Boolean getActivaBotonGuardarGeneral() {
        return activaBotonGuardarGeneral;
    }

    public void setActivaBotonGuardarGeneral(Boolean activaBotonGuardarGeneral) {
        this.activaBotonGuardarGeneral = activaBotonGuardarGeneral;
    }

    public Boolean getTieneCCMasivo() {
        return tieneCCMasivo;
    }

    public void setTieneCCMasivo(Boolean tieneCCMasivo) {
        this.tieneCCMasivo = tieneCCMasivo;
    }

    public Boolean getTieneCentroDeCostoNivel3() {
        return tieneCentroDeCostoNivel3;
    }

    public void setTieneCentroDeCostoNivel3(Boolean tieneCentroDeCostoNivel3) {
        this.tieneCentroDeCostoNivel3 = tieneCentroDeCostoNivel3;
    }

    public Boolean getTieneCentroDeCostoNivel4() {
        return tieneCentroDeCostoNivel4;
    }

    public void setTieneCentroDeCostoNivel4(Boolean tieneCentroDeCostoNivel4) {
        this.tieneCentroDeCostoNivel4 = tieneCentroDeCostoNivel4;
    }

    public void verificaExistenciaDeCentrosDeCosto(ValueChangeEvent e) throws Exception {        
        if (!cntEntidadesService.verificaSiEnUnRangoDeCuentasTieneAsignadosCentrosDeCosto(selectedEntidad)) {
            MessageUtils.addErrorMessage("Una de las cuentas ya tiene asignado Centros de Costo no puede cambiar su valor.");
        }
    }
}