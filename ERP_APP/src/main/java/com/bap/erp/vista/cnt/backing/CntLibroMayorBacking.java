package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntLibroMayor;
import com.bap.erp.modelo.entidades.cnf.ParPlanCuentas;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.enums.EnumTipoMoneda;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import com.bap.erp.modelo.servicios.cnt.CntProyectoService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.bap.erp.vista.utils.ReportManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

@ManagedBean(name = "cntLibroMayorBacking")
@ViewScoped
public class CntLibroMayorBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntAuxiliaresService}")
    private CntAuxiliaresService cntAuxiliaresService;
    @ManagedProperty(value = "#{cntProyecto}")
    private CntProyectoService cntProyectoService;
    private CntEntidad cntEntidad;
    private CntEntidad cntEntidadSelecion = new CntEntidad();
    private CntEntidad cntEntidadSelecionDos = new CntEntidad();
    private CntDetalleComprobante cntDetalleComprobanteSelecion;
    private Date fechaInicial = new Date();
    private Date fechaFinal = new Date();
    private List<CntLibroMayor> listaDetalleComprobante = new ArrayList<CntLibroMayor>();
    private List<ParPlanCuentas> listaPlanCuentasRangos = new ArrayList<ParPlanCuentas>();
    private BigDecimal sumaSaldo;
    private List<CntComprobante> listaDeComprobantesPorParametros = new ArrayList<CntComprobante>();
    private List<CntDetalleComprobante> listaResultadoConsulta = new ArrayList<CntDetalleComprobante>();
    private List<String> listaDePaginasYComprobantes = new ArrayList<String>();
    private String tituloDeComprobante = "Lista de Transacciones";
    private String year = "Lista de Transacciones";
    private int comprobanteElegido = 0;
    private ParPlanCuentas entidadElegida;
    private int comprobanteFinal = 0;
    private Boolean limiteInicial = true;
    private Boolean limiteFinal = true;
    private BigDecimal sumaDebe = new BigDecimal("0.00");
    private BigDecimal sumaHaber = new BigDecimal("0.00");
    private BigDecimal sumaNetoDebe = new BigDecimal("0.00");
    private BigDecimal sumaNetoHaber = new BigDecimal("0.00");
    private BigDecimal sumaSaldoTotal = new BigDecimal("0.00");
    private CntDetalleComprobante cntDetalleComprobanteParaMayor;
    private Boolean deshabilitaBotonDeCuentas = true;
    private Boolean habilitaVolverAComprobante = false;
    private CntEntidad cntEntidadParaMayor;
    private CntEntidad cntEntidadMayor;
    private Boolean habilitaVolverAPlanDeCuentas = false;
    private Boolean habilitaReporte = false;
    private String moneda = EnumTipoMoneda.BOLIVIANOS.getCodigo();

    private List<CntEntidad> cntEntidadList;
    private List<CntEntidad> filteredcntEntidad;
    private CntEntidad selected = new CntEntidad();
    private String valorEntidad;
    private String color = null;
    private Boolean eleccionTodos = false;
    private List<CntEntidad> listaMomentanea = new ArrayList<CntEntidad>();
    private Boolean activaBotonSeleccion;
    private String tipoCuentaSelecionada;
    private Boolean habilitaVolverBG = false;
    private Boolean habilitaVolverEERR = false;
    private Boolean habilitaVolverSS = false;
    private CntEntidad cntEntidadMayorBG;
    private CntEntidad cntEntidadMayorEERR;
    private CntEntidad cntEntidadMayorSS;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoAnteriorDolar;
    private BigDecimal sumaDebeAux;
    private BigDecimal sumaHaberAux;
    private BigDecimal sumaDebeDolarAux;
    private BigDecimal sumaHaberDolarAux;
    private CntDetalleComprobante selectedDetalleComprobante;
    private CntLibroMayor cntLibroMayorSeleccion;

    public CntLibroMayorBacking() {
    }

    @PostConstruct
    void initCntLibroMayorBacking() {

        try {
            if (getFromSessionIdDetalleComprobanteParaMayor() != null) {
                cntDetalleComprobanteParaMayor = cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobanteParaMayor());
                cntEntidadSelecion = cntDetalleComprobanteParaMayor.getCntEntidad();
                cntEntidadSelecionDos = cntDetalleComprobanteParaMayor.getCntEntidad();
                deshabilitaBotonDeCuentas = false;
                habilitaVolverAComprobante = true;
                //            fechaInicial = cntDetalleComprobanteParaMayor.getCntComprobante().getFecha();
                //            fechaFinal = cntDetalleComprobanteParaMayor.getCntComprobante().getFecha();
                //            listaEntidadesPorRango();
                setInSessionIdDetalleComprobanteParaMayor(null);
            }
            if (getFromSessionIdEntidadParaMayor() != null) {
                cntEntidadParaMayor = cntEntidadesService.find(CntEntidad.class, getFromSessionIdEntidadParaMayor());
                cntEntidadSelecion = cntEntidadParaMayor;
                cntEntidadSelecionDos = cntEntidadParaMayor;
                deshabilitaBotonDeCuentas = false;
                habilitaVolverAPlanDeCuentas = true;
                fechaInicial = new Date("01/01/2014");
                fechaFinal = new Date("12/31/2014");
                listaEntidadesPorRango();
                setInSessionIdEntidadParaMayor(null);
            }
            //Aumentado por Jacqueline Carvajal para ver mayor según cuenta seleccionada 03/12/2014
            if (getFromSessionIdEntidad2() != null) {
                cntEntidadMayor = cntEntidadesService.find(CntEntidad.class, getFromSessionIdEntidad2());
                cntEntidadSelecion = cntEntidadMayor;
                cntEntidadSelecionDos = cntEntidadMayor;
                deshabilitaBotonDeCuentas = false;
                habilitaVolverAPlanDeCuentas = true;
                listaEntidadesPorRango();
                setInSessionIdEntidad2(null);
            }
            if (getFromSessionIdEntidadBG() != null) {
                cntEntidadMayorBG = cntEntidadesService.find(CntEntidad.class, getFromSessionIdEntidadBG());
                cntEntidadSelecion = cntEntidadMayorBG;
                cntEntidadSelecionDos = cntEntidadMayorBG;
                deshabilitaBotonDeCuentas = false;
                habilitaVolverAPlanDeCuentas = false;
                habilitaVolverBG = true;

                moneda = getFromSessionTipoMoneda();
                listaEntidadesPorRango();
                setInSessionIdEntidadBG(null);
            }
            if (getFromSessionIdEntidadEERRR() != null) {
                cntEntidadMayorEERR = cntEntidadesService.find(CntEntidad.class, getFromSessionIdEntidadEERRR());
                cntEntidadSelecion = cntEntidadMayorEERR;
                cntEntidadSelecionDos = cntEntidadMayorEERR;
                habilitaVolverBG = false;
                deshabilitaBotonDeCuentas = false;
                habilitaVolverAPlanDeCuentas = false;
                fechaInicial = getFromSessionFechaInicio();
                fechaFinal = getFromSessionFechaFin();
                moneda = getFromSessionTipoMoneda();
                listaEntidadesPorRango();

                if (super.getFromSessionIdDetalleComprobante() != null) {
                    cntLibroMayorSeleccion = cntDetalleComprobanteService.obtieneComprobanteXnumero(super.getFromSessionIdDetalleComprobante());
                    setInSessionIdDetalleComprobante(null);
                }

                setInSessionIdEntidadEERRR(null);
            }
            if (super.getFromSessionIdEntidadSumasySaldos() != null) {
                cntEntidadMayorSS = cntEntidadesService.find(CntEntidad.class, getFromSessionIdEntidadSumasySaldos());
                cntEntidadSelecion = cntEntidadMayorSS;
                cntEntidadSelecionDos = cntEntidadMayorSS;
                deshabilitaBotonDeCuentas = false;
                habilitaVolverAPlanDeCuentas = false;
                habilitaVolverBG = false;
                habilitaVolverSS = true;
                moneda = getFromSessionTipoMoneda();
                listaEntidadesPorRango();
                setInSessionIdEntidadSumasySaldos(null);
            }

            if (getFromSessionTipoReporte() != null) {
                if (getFromSessionTipoReporte().equals("BG")) {
                    habilitaVolverBG = true;
                    habilitaVolverEERR = false;
                    habilitaVolverSS = false;
                } else {
                    if (getFromSessionTipoReporte().equals("EERR")) {
                        habilitaVolverEERR = true;
                        habilitaVolverBG = false;
                        habilitaVolverSS = false;
                    }
                }
                if (getFromSessionTipoReporte().equals("SS")) {
                    habilitaVolverEERR = false;
                    habilitaVolverBG = false;
                    habilitaVolverSS = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<CntEntidad> cntObjetosPorGrupoNivelList() {
        try {
            cntEntidadList = cntEntidadesService.cntEntidadesPorGrupoNivelList(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
        } catch (Exception e) {
        }
        return cntEntidadList;
    }

    public void listaTodo() {
        if (eleccionTodos) {
            color = "para-elegidas";
            listaMomentanea = filteredcntEntidad;
            filteredcntEntidad = null;
        } else {
            filteredcntEntidad = listaMomentanea;
            listaMomentanea = null;
            color = "para-filas";
        }
    }

    public void obtieneObjetoPlanCuentas(ValueChangeEvent e) {
        activaBotonSeleccion = selected.getNivel() != 1;
    }

    public String insertaPuntosAutomatio(CntEntidad cntEntidad) {
        return cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad);
    }

    public String seleccionar() {
        if (selected.getIdEntidad() != null) {
            if (selected.getNivel() == 1) {
                setInSessionIdEntidadSeleccion(selected.getIdEntidad());
                adicionaValorPorBoton(tipoCuentaSelecionada, selected);
                return null;
            } else {
                MessageUtils.addErrorMessage("La cuenta debe ser de Ãºltimo nivel.");
            }
        } else {
            MessageUtils.addErrorMessage("No selecciono ninguna cuenta.");
        }
        return null;
    }

    public void cargaPrimeraCuenta(ValueChangeEvent e) {
        tipoCuentaSelecionada = "PCUENTA";
    }

    public void cargaSegundaCuenta(ValueChangeEvent e) {
        tipoCuentaSelecionada = "SCUENTA";
    }

    public void adicionaValorPorBoton(String tipoCuentaSelecionada, CntEntidad cntEntidad) {
        if (tipoCuentaSelecionada.equals("PCUENTA")) {
            cntEntidadSelecion = cntEntidad;
            cntEntidadSelecionDos = cntEntidad;
        } else if (tipoCuentaSelecionada.equals("SCUENTA")) {
            cntEntidadSelecionDos = cntEntidad;
        }
    }

    public void metodoSelecionaEntidad() {
        if (cntEntidad.getNivel() == 1) {
            cntEntidadSelecion = cntEntidad;
        } else {
            MessageUtils.addErrorMessage("La cuenta seleccionada debe ser de ultimo nivel");
        }
    }

    //metodo GusPal
    public List<CntLibroMayor> muestraLibroMayorDeCuentaLista() throws Exception {
        listaDetalleComprobante = new ArrayList<CntLibroMayor>();
        if (cntEntidadSelecion == null) {
            return listaDetalleComprobante;
        }
        if (listaDetalleComprobante.isEmpty()) {
            sumaSaldo = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadSelecion, fechaInicial);
//            listaDetalleComprobante = cntDetalleComprobanteService.listaLibroMayorSegunCuenta(cntEntidadSelecion, fechaInicial, fechaFinal);
            listaDetalleComprobante = cntDetalleComprobanteService.listaUnida(cntEntidadSelecion, fechaInicial, fechaFinal);

        }
        return listaDetalleComprobante;
    }

    public void muestraLibroMayorDeCuentaLista(ValueChangeEvent event) throws Exception {
        listaDetalleComprobante = new ArrayList<CntLibroMayor>();
        CntEntidad cntEntidadElegidaCombo = cntEntidadesService.find(CntEntidad.class, listaPlanCuentasRangos.get(comprobanteElegido - 1).getIdEntidad());
        tituloDeComprobante = "Cuenta " + listaPlanCuentasRangos.get(comprobanteElegido - 1).getMascaraGenerada() + " ";

        if (listaDetalleComprobante.isEmpty()) {
            sumaSaldo = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);
            listaDetalleComprobante = cntDetalleComprobanteService.listaUnida(cntEntidadElegidaCombo, fechaInicial, fechaFinal);
        }

        sumaDebeHaberNetoSaldo(listaDetalleComprobante);

        habilitaBotonesSiguienteAnterior();
    }

    public void limpiaEntidad() {
        cntEntidadSelecion = new CntEntidad();
        listaDetalleComprobante = new ArrayList<CntLibroMayor>();
        cntEntidadSelecionDos = new CntEntidad();
        listaPlanCuentasRangos = new ArrayList<ParPlanCuentas>();
        listaDePaginasYComprobantes = new ArrayList<String>();
    }

    public String cambiaFormatoMoneda(BigDecimal valor) {
        return cntDetalleComprobanteService.cambiaFormatoMoneda(valor);
    }

    public List<ParPlanCuentas> listaEntidadesPorRango() throws Exception {
        listaPlanCuentasRangos = new ArrayList<ParPlanCuentas>();
        CntEntidad cntEntidadDos = cntEntidadesService.cntEntidadPorMascara(cntEntidadSelecionDos.getMascaraGenerada());
        if (cntEntidadSelecion != null && cntEntidadDos != null && fechaInicial != null && fechaFinal != null) {
            if (cntEntidadesService.esLaCuentaInicialInferiorALaFinalLibroMayor(cntEntidadSelecion, cntEntidadDos)) {
                if (listaPlanCuentasRangos.isEmpty()) {
                    listaPlanCuentasRangos = cntEntidadesService.listaLasCuentasEnUnRango(cntEntidadSelecion, cntEntidadDos);
                }
                if (!listaPlanCuentasRangos.isEmpty()) {
                    comprobanteFinal = listaPlanCuentasRangos.size();
                    comprobanteElegido = 1;
                    tituloDeComprobante = "Cuenta " + listaPlanCuentasRangos.get(0).getMascaraGenerada() + " " + listaPlanCuentasRangos.get(0).getDescripcion() + " ";
                    CntEntidad cntEntidadElegidaCombo = cntEntidadesService.find(CntEntidad.class, listaPlanCuentasRangos.get(0).getIdEntidad());
                    sumaSaldo = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);
                    listaDetalleComprobante = cntDetalleComprobanteService.listaUnida(cntEntidadElegidaCombo, fechaInicial, fechaFinal);

                    sumas(cntEntidadElegidaCombo);
                    limiteFinal = false;
                } else {
                    listaResultadoConsulta = new ArrayList<CntDetalleComprobante>();
                    comprobanteFinal = 0;
                    comprobanteElegido = 0;
                    tituloDeComprobante = "Lista de Transacciones";
                    limiteFinal = true;
                }
                sumaDebeHaberNetoSaldo(listaDetalleComprobante);
                if (!listaDetalleComprobante.isEmpty()) {
                    habilitaReporte = true;
                }
                return listaPlanCuentasRangos;
            } else {
                MessageUtils.addErrorMessage("Verifique que el numero de la Cuenta Inicial sea menor al de la Cuenta Final");
            }
        } else {
            MessageUtils.addErrorMessage("Es necesario llenar todos los campos para realizar la búsqueda");
        }
        return listaPlanCuentasRangos;
    }

    private void sumas(CntEntidad cntEntidadElegidaCombo) {
        sumaDebeAux = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);
        sumaHaberAux = cntDetalleComprobanteService.sumaHaberParaLibroMayor(cntEntidadElegidaCombo, fechaInicial);
        saldoAnterior = sumaDebeAux.subtract(sumaHaberAux);
        sumaDebeDolarAux = cntDetalleComprobanteService.sumadebeDolarInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);
        sumaHaberDolarAux = cntDetalleComprobanteService.sumaHaberDolarParaLibroMayor(cntEntidadElegidaCombo, fechaInicial);
        saldoAnteriorDolar = sumaDebeDolarAux.subtract(sumaHaberDolarAux);
    }

    public void botonComprobanteSiguiente() throws Exception {
        Date fechaInicial = new Date();
        if (comprobanteElegido < comprobanteFinal) {
            comprobanteElegido++;
            tituloDeComprobante = "Comprobante " + listaPlanCuentasRangos.get(comprobanteElegido - 1).getMascaraGenerada() + " " + listaPlanCuentasRangos.get(comprobanteElegido - 1).getDescripcion() + " ";
            CntEntidad cntEntidadElegidaCombo = cntEntidadesService.find(CntEntidad.class, listaPlanCuentasRangos.get(0).getIdEntidad());
            sumaSaldo = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);
            listaDetalleComprobante = cntDetalleComprobanteService.listaUnida(cntEntidadElegidaCombo, fechaInicial, fechaFinal);

            sumaDebeHaberNetoSaldo(listaDetalleComprobante);
        }
        habilitaBotonesSiguienteAnterior();
        Date fechaFinal = new Date();
        Long tipo = (fechaFinal.getTime() - fechaInicial.getTime()) / 1000;
    }

    public void botonComprobanteAnterior() throws Exception {
        if (comprobanteElegido > 1) {
            comprobanteElegido--;
            tituloDeComprobante = "Cuenta " + listaPlanCuentasRangos.get(comprobanteElegido - 1).getMascaraGenerada() + " " + listaPlanCuentasRangos.get(comprobanteElegido - 1).getDescripcion() + " ";
            CntEntidad cntEntidadElegidaCombo = cntEntidadesService.find(CntEntidad.class, listaPlanCuentasRangos.get(0).getIdEntidad());
            sumaSaldo = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);
            listaDetalleComprobante = cntDetalleComprobanteService.listaUnida(cntEntidadElegidaCombo, fechaInicial, fechaFinal);

            sumaDebeHaberNetoSaldo(listaDetalleComprobante);
        }
        habilitaBotonesSiguienteAnterior();
    }

    public void botonComprobanteUltimo() throws Exception {
        if (comprobanteFinal > 1) {
            comprobanteElegido = comprobanteFinal;
            tituloDeComprobante = "Cuenta " + listaPlanCuentasRangos.get(comprobanteElegido - 1).getMascaraGenerada() + " " + listaPlanCuentasRangos.get(comprobanteElegido - 1).getDescripcion() + " ";
            CntEntidad cntEntidadElegidaCombo = cntEntidadesService.find(CntEntidad.class, listaPlanCuentasRangos.get(0).getIdEntidad());
            sumaSaldo = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);
            listaDetalleComprobante = cntDetalleComprobanteService.listaUnida(cntEntidadElegidaCombo, fechaInicial, fechaFinal);

            sumaDebeHaberNetoSaldo(listaDetalleComprobante);
        }
        habilitaBotonesSiguienteAnterior();

    }

    public void botonComprobanteInicial() throws Exception {
        if (comprobanteFinal >= 1) {
            comprobanteElegido = 1;
            tituloDeComprobante = "Cuenta " + listaPlanCuentasRangos.get(comprobanteElegido - 1).getMascaraGenerada() + " " + listaPlanCuentasRangos.get(comprobanteElegido - 1).getDescripcion() + " ";
            CntEntidad cntEntidadElegidaCombo = cntEntidadesService.find(CntEntidad.class, listaPlanCuentasRangos.get(0).getIdEntidad());
            sumaSaldo = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);
            listaDetalleComprobante = cntDetalleComprobanteService.listaUnida(cntEntidadElegidaCombo, fechaInicial, fechaFinal);

            sumaDebeHaberNetoSaldo(listaDetalleComprobante);

        }
        habilitaBotonesSiguienteAnterior();
    }

    public void sumaDebeHaberNetoSaldo(List<CntLibroMayor> listaLibroMayor) {
        if (!listaLibroMayor.isEmpty()) {
            sumaDebe = cntDetalleComprobanteService.sumaDebeComprobanteLibroMayor(listaLibroMayor);
            sumaHaber = cntDetalleComprobanteService.sumaHaberComprobanteLibroMayor(listaLibroMayor);
            sumaSaldoTotal = cntDetalleComprobanteService.sumaSaldoComprobanteLibroMayor(listaLibroMayor);
            if (sumaDebe.compareTo(sumaHaber) == 1) {
                sumaNetoDebe = sumaDebe.subtract(sumaHaber);
            } else {
                sumaNetoHaber = sumaHaber.subtract(sumaDebe);
            }
        } else {
            sumaDebe = new BigDecimal(0);
            sumaHaber = new BigDecimal(0);
            sumaNetoDebe = new BigDecimal(0);
            sumaNetoHaber = new BigDecimal(0);
            sumaSaldoTotal = new BigDecimal(0);
        }
    }

    public int extraeNumeroDeCadena(String cadena) {
        if (cadena.length() > 0) {
            String[] vector = cadena.split(" - ");
            int numero = Integer.parseInt(vector[1]);
            return numero;
        }
        return 0;
    }

    public void habilitaBotonesSiguienteAnterior() {
        if (comprobanteElegido == comprobanteFinal) {
            if (comprobanteElegido == 0) {
                limiteInicial = true;
                limiteFinal = true;
            }
            limiteFinal = true;
        } else {
            limiteFinal = false;
        }
        if (comprobanteElegido > 1) {
            limiteInicial = false;
        } else {
            limiteInicial = true;
        }
    }

    public void eligeComprobante(ValueChangeEvent event) throws Exception {
        if (comprobanteElegido > 0) {
            tituloDeComprobante = "Cuenta " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getNumero() + " " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getDescripcion() + " ";
        } else {
            comprobanteElegido = 0;
        }
        habilitaBotonesSiguienteAnterior();
    }

    public List<String> listaDePaginasYEntidades() {
        listaDePaginasYComprobantes.add("");
        if (comprobanteFinal > 0) {
            listaDePaginasYComprobantes = cntEntidadesService.listaDeNumerosDeEntidadesParaReporte(listaPlanCuentasRangos);
        }
        return listaDePaginasYComprobantes;
    }

    public String vuelveADetalleComprobante() {
        setInSessionIdDetalleComprobanteParaMayor(cntDetalleComprobanteParaMayor.getIdDetalleComprobante());
        return "comprobantesList";
    }

    public String vuelveAPlanDeCuentas() {
        setInSessionIdEntidadParaMayor(cntEntidadSelecion.getIdEntidad());
        return "planCuentas";
    }

    public String vuelveABalanceGral() {
        if (super.getFromSessionTipoReporte().equals("BG")) {
            setInSessionIdEntidadBG(cntEntidadSelecion.getIdEntidad());
            return "reporte_balanceGeneral";
        } else {
            if (super.getFromSessionTipoReporte().equals("EERR")) {
                setInSessionIdEntidadEERRR(cntEntidadSelecion.getIdEntidad());
                return "reporte_EERR";
            }
        }
        return null;
    }

    public String vuelveAEERR() {
        setInSessionIdEntidadEERRR(cntEntidadSelecion.getIdEntidad());
        return "reporte_EERR";
    }

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
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

    public CntEntidad getCntEntidadSelecion() {
        return cntEntidadSelecion;
    }

    public void setCntEntidadSelecion(CntEntidad cntEntidadSelecion) {
        this.cntEntidadSelecion = cntEntidadSelecion;
    }

    public CntDetalleComprobante getCntDetalleComprobanteSelecion() {
        return cntDetalleComprobanteSelecion;
    }

    public void setCntDetalleComprobanteSelecion(CntDetalleComprobante cntDetalleComprobanteSelecion) {
        this.cntDetalleComprobanteSelecion = cntDetalleComprobanteSelecion;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public List<CntLibroMayor> getListaDetalleComprobante() {
        return listaDetalleComprobante;
    }

    public void setListaDetalleComprobante(List<CntLibroMayor> listaDetalleComprobante) {
        this.listaDetalleComprobante = listaDetalleComprobante;
    }

    public CntEntidad getCntEntidadSelecionDos() {
        return cntEntidadSelecionDos;
    }

    public void setCntEntidadSelecionDos(CntEntidad cntEntidadSelecionDos) {
        this.cntEntidadSelecionDos = cntEntidadSelecionDos;
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public List<ParPlanCuentas> getListaPlanCuentasRangos() {
        return listaPlanCuentasRangos;
    }

    public void setListaPlanCuentasRangos(List<ParPlanCuentas> listaPlanCuentasRangos) {
        this.listaPlanCuentasRangos = listaPlanCuentasRangos;
    }

    public List<CntComprobante> getListaDeComprobantesPorParametros() {
        return listaDeComprobantesPorParametros;
    }

    public void setListaDeComprobantesPorParametros(List<CntComprobante> listaDeComprobantesPorParametros) {
        this.listaDeComprobantesPorParametros = listaDeComprobantesPorParametros;
    }

    public List<CntDetalleComprobante> getListaResultadoConsulta() {
        return listaResultadoConsulta;
    }

    public void setListaResultadoConsulta(List<CntDetalleComprobante> listaResultadoConsulta) {
        this.listaResultadoConsulta = listaResultadoConsulta;
    }

    public String getTituloDeComprobante() {
        return tituloDeComprobante;
    }

    public void setTituloDeComprobante(String tituloDeComprobante) {
        this.tituloDeComprobante = tituloDeComprobante;
    }

    public int getComprobanteElegido() {
        return comprobanteElegido;
    }

    public void setComprobanteElegido(int comprobanteElegido) {
        this.comprobanteElegido = comprobanteElegido;
    }

    public int getComprobanteFinal() {
        return comprobanteFinal;
    }

    public void setComprobanteFinal(int comprobanteFinal) {
        this.comprobanteFinal = comprobanteFinal;
    }

    public Boolean getLimiteInicial() {
        return limiteInicial;
    }

    public void setLimiteInicial(Boolean limiteInicial) {
        this.limiteInicial = limiteInicial;
    }

    public Boolean getLimiteFinal() {
        return limiteFinal;
    }

    public void setLimiteFinal(Boolean limiteFinal) {
        this.limiteFinal = limiteFinal;
    }

    public List<String> getListaDePaginasYComprobantes() {
        return listaDePaginasYComprobantes;
    }

    public void setListaDePaginasYComprobantes(List<String> listaDePaginasYComprobantes) {
        this.listaDePaginasYComprobantes = listaDePaginasYComprobantes;
    }

    public ParPlanCuentas getEntidadElegida() {
        return entidadElegida;
    }

    public void setEntidadElegida(ParPlanCuentas entidadElegida) {
        this.entidadElegida = entidadElegida;
    }

    public BigDecimal getSumaSaldo() {
        return sumaSaldo;
    }

    public void setSumaSaldo(BigDecimal sumaSaldo) {
        this.sumaSaldo = sumaSaldo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BigDecimal getSumaDebe() {
        return sumaDebe;
    }

    public void setSumaDebe(BigDecimal sumaDebe) {
        this.sumaDebe = sumaDebe;
    }

    public BigDecimal getSumaHaber() {
        return sumaHaber;
    }

    public void setSumaHaber(BigDecimal sumaHaber) {
        this.sumaHaber = sumaHaber;
    }

    public BigDecimal getSumaNetoDebe() {
        return sumaNetoDebe;
    }

    public void setSumaNetoDebe(BigDecimal sumaNetoDebe) {
        this.sumaNetoDebe = sumaNetoDebe;
    }

    public BigDecimal getSumaNetoHaber() {
        return sumaNetoHaber;
    }

    public void setSumaNetoHaber(BigDecimal sumaNetoHaber) {
        this.sumaNetoHaber = sumaNetoHaber;
    }

    public BigDecimal getSumaSaldoTotal() {
        return sumaSaldoTotal;
    }

    public void setSumaSaldoTotal(BigDecimal sumaSaldoTotal) {
        this.sumaSaldoTotal = sumaSaldoTotal;
    }

    public CntDetalleComprobante getCntDetalleComprobanteParaMayor() {
        return cntDetalleComprobanteParaMayor;
    }

    public void setCntDetalleComprobanteParaMayor(CntDetalleComprobante cntDetalleComprobanteParaMayor) {
        this.cntDetalleComprobanteParaMayor = cntDetalleComprobanteParaMayor;
    }

    public Boolean getDeshabilitaBotonDeCuentas() {
        return deshabilitaBotonDeCuentas;
    }

    public void setDeshabilitaBotonDeCuentas(Boolean deshabilitaBotonDeCuentas) {
        this.deshabilitaBotonDeCuentas = deshabilitaBotonDeCuentas;
    }

    public String reporteLibroMayor_action() {
        listaDetalleComprobante = new ArrayList<CntLibroMayor>();
        CntEntidad cntEntidadElegidaCombo = cntEntidadesService.find(CntEntidad.class, listaPlanCuentasRangos.get(comprobanteElegido - 1).getIdEntidad());
        sumaSaldo = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);

        try {
            Map parameters = new HashMap();//mapear
            parameters.put("sumaSaldo", sumaSaldo);
            parameters.put("sumaDebe", sumaDebe);
            parameters.put("sumaHaber", sumaHaber);
            parameters.put("sumaSaldoTotal", sumaSaldoTotal);
            parameters.put("sumaNetoDebe", sumaNetoDebe);
            parameters.put("sumaNetoHaber", sumaNetoHaber);

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);

            report.drawReport(FacesContext.getCurrentInstance(), cntDetalleComprobanteService.listaUnida(cntEntidadElegidaCombo, fechaInicial, fechaFinal), "reporte_libroMayor");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String reporteLibrosMayores_action() {
        listaDetalleComprobante = new ArrayList<CntLibroMayor>();
        CntEntidad cntEntidadElegidaCombo = cntEntidadesService.find(CntEntidad.class, listaPlanCuentasRangos.get(comprobanteElegido - 1).getIdEntidad());
        sumaSaldo = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);

        try {
            Map parameters = new HashMap();//mapear
            parameters.put("sumaSaldo", sumaSaldo);
            parameters.put("sumaDebe", sumaDebe);
            parameters.put("sumaHaber", sumaHaber);
            parameters.put("sumaSaldoTotal", sumaSaldoTotal);
            parameters.put("sumaNetoDebe", sumaNetoDebe);
            parameters.put("sumaNetoHaber", sumaNetoHaber);
            parameters.put("fechaInicial", fechaInicial);
            parameters.put("fechaFinal", fechaFinal);

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);
            report.drawReport(FacesContext.getCurrentInstance(), cntDetalleComprobanteService.listaUnida(cntEntidadElegidaCombo, fechaInicial, fechaFinal), "reporte_librosMayores");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="Metodo para reporte libro mayor en Sus y en Bs Jacqueline Cavajal  04/12/2014">
    public String reporteLibrosMayoresBsSUS_action() {
        listaDetalleComprobante = new ArrayList<CntLibroMayor>();
        CntEntidad cntEntidadElegidaCombo = cntEntidadesService.find(CntEntidad.class, listaPlanCuentasRangos.get(comprobanteElegido - 1).getIdEntidad());
        sumaSaldo = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidadElegidaCombo, fechaInicial);

        try {
            Map parameters = new HashMap();//mapear
            parameters.put("sumaSaldo", sumaSaldo);
            parameters.put("sumaDebe", sumaDebe);
            parameters.put("sumaHaber", sumaHaber);
            parameters.put("sumaSaldoTotal", sumaSaldoTotal);
            parameters.put("sumaNetoDebe", sumaNetoDebe);
            parameters.put("sumaNetoHaber", sumaNetoHaber);
            parameters.put("fechaInicial", fechaInicial);
            parameters.put("fechaFinal", fechaFinal);

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);
            if (moneda.equals(EnumTipoMoneda.BOLIVIANOS.getCodigo())) {
                report.drawReport(FacesContext.getCurrentInstance(), cntDetalleComprobanteService.listaUnida(cntEntidadElegidaCombo, fechaInicial, fechaFinal), "reporte_libroMayorBs");
            } else {
                if (moneda.equals(EnumTipoMoneda.DOLAR.getCodigo())) {
                    report.drawReport(FacesContext.getCurrentInstance(), cntDetalleComprobanteService.listaUnida(cntEntidadElegidaCombo, fechaInicial, fechaFinal), "reporte_libroMayorSUS");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //</editor-fold>
    public CntEntidad getCntEntidadParaMayor() {
        return cntEntidadParaMayor;
    }

    public void setCntEntidadParaMayor(CntEntidad cntEntidadParaMayor) {
        this.cntEntidadParaMayor = cntEntidadParaMayor;
    }

    public Boolean getHabilitaVolverAComprobante() {
        return habilitaVolverAComprobante;
    }

    public void setHabilitaVolverAComprobante(Boolean habilitaVolverAComprobante) {
        this.habilitaVolverAComprobante = habilitaVolverAComprobante;
    }

    public Boolean getHabilitaVolverAPlanDeCuentas() {
        return habilitaVolverAPlanDeCuentas;
    }

    public void setHabilitaVolverAPlanDeCuentas(Boolean habilitaVolverAPlanDeCuentas) {
        this.habilitaVolverAPlanDeCuentas = habilitaVolverAPlanDeCuentas;
    }

    public Boolean getHabilitaReporte() {
        return habilitaReporte;
    }

    public void setHabilitaReporte(Boolean habilitaReporte) {
        this.habilitaReporte = habilitaReporte;
    }

    public CntEntidad getCntEntidadMayor() {
        return cntEntidadMayor;
    }

    public void setCntEntidadMayor(CntEntidad cntEntidadMayor) {
        this.cntEntidadMayor = cntEntidadMayor;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public List<CntEntidad> getCntEntidadList() {
        return cntEntidadList;
    }

    public void setCntEntidadList(List<CntEntidad> cntEntidadList) {
        this.cntEntidadList = cntEntidadList;
    }

    public List<CntEntidad> getFilteredcntEntidad() {
        return filteredcntEntidad;
    }

    public void setFilteredcntEntidad(List<CntEntidad> filteredcntEntidad) {
        this.filteredcntEntidad = filteredcntEntidad;
    }

    public CntEntidad getSelected() {
        return selected;
    }

    public void setSelected(CntEntidad selected) {
        this.selected = selected;
    }

    public String getValorEntidad() {
        return valorEntidad;
    }

    public void setValorEntidad(String valorEntidad) {
        this.valorEntidad = valorEntidad;
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

    public Boolean getActivaBotonSeleccion() {
        return activaBotonSeleccion;
    }

    public void setActivaBotonSeleccion(Boolean activaBotonSeleccion) {
        this.activaBotonSeleccion = activaBotonSeleccion;
    }

    public String getTipoCuentaSelecionada() {
        return tipoCuentaSelecionada;
    }

    public void setTipoCuentaSelecionada(String tipoCuentaSelecionada) {
        this.tipoCuentaSelecionada = tipoCuentaSelecionada;
    }

    public Boolean getHabilitaVolverBG() {
        return habilitaVolverBG;
    }

    public void setHabilitaVolverBG(Boolean habilitaVolverBG) {
        this.habilitaVolverBG = habilitaVolverBG;
    }

    public Boolean getHabilitaVolverEERR() {
        return habilitaVolverEERR;
    }

    public void setHabilitaVolverEERR(Boolean habilitaVolverEERR) {
        this.habilitaVolverEERR = habilitaVolverEERR;
    }

    public CntEntidad getCntEntidadMayorBG() {
        return cntEntidadMayorBG;
    }

    public void setCntEntidadMayorBG(CntEntidad cntEntidadMayorBG) {
        this.cntEntidadMayorBG = cntEntidadMayorBG;
    }

    public CntEntidad getCntEntidadMayorEERR() {
        return cntEntidadMayorEERR;
    }

    public void setCntEntidadMayorEERR(CntEntidad cntEntidadMayorEERR) {
        this.cntEntidadMayorEERR = cntEntidadMayorEERR;
    }

    public BigDecimal getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public BigDecimal getSaldoAnteriorDolar() {
        return saldoAnteriorDolar;
    }

    public void setSaldoAnteriorDolar(BigDecimal saldoAnteriorDolar) {
        this.saldoAnteriorDolar = saldoAnteriorDolar;
    }

    public BigDecimal getSumaDebeAux() {
        return sumaDebeAux;
    }

    public void setSumaDebeAux(BigDecimal sumaDebeAux) {
        this.sumaDebeAux = sumaDebeAux;
    }

    public BigDecimal getSumaHaberAux() {
        return sumaHaberAux;
    }

    public void setSumaHaberAux(BigDecimal sumaHaberAux) {
        this.sumaHaberAux = sumaHaberAux;
    }

    public BigDecimal getSumaDebeDolarAux() {
        return sumaDebeDolarAux;
    }

    public void setSumaDebeDolarAux(BigDecimal sumaDebeDolarAux) {
        this.sumaDebeDolarAux = sumaDebeDolarAux;
    }

    public BigDecimal getSumaHaberDolarAux() {
        return sumaHaberDolarAux;
    }

    public void setSumaHaberDolarAux(BigDecimal sumaHaberDolarAux) {
        this.sumaHaberDolarAux = sumaHaberDolarAux;
    }

    public CntAuxiliaresService getCntAuxiliaresService() {
        return cntAuxiliaresService;
    }

    public void setCntAuxiliaresService(CntAuxiliaresService cntAuxiliaresService) {
        this.cntAuxiliaresService = cntAuxiliaresService;
    }

    public CntProyectoService getCntProyectoService() {
        return cntProyectoService;
    }

    public void setCntProyectoService(CntProyectoService cntProyectoService) {
        this.cntProyectoService = cntProyectoService;
    }

    public String nombreAuxiliar(Long idAuxiliar) throws Exception {
        if (idAuxiliar != null) {
            return cntAuxiliaresService.obtieneAuxiliar(idAuxiliar).getNombre();
        }
        return " ";
    }

    public String nombreProyecto(Long idProyecto) throws Exception {
        if (idProyecto != null) {
            return cntProyectoService.obtieneCntProyecto(idProyecto).getNombre();
        }
        return " ";
    }

    //PARA SELECCIONAR  
//<editor-fold defaultstate="collapsed" desc="métodos para ver comprobante de cuenta seleccionada en libro mayor J.C. 02/06/2015">
    public void obtieneObjeto(ValueChangeEvent event) {
        selectedDetalleComprobante = cntLibroMayorSeleccion.getIdDetalleComprobante();
    }

    public CntDetalleComprobante getSelectedDetalleComprobante() {
        return selectedDetalleComprobante;
    }

    public void setSelectedDetalleComprobante(CntDetalleComprobante selectedDetalleComprobante) {
        this.selectedDetalleComprobante = selectedDetalleComprobante;
    }

    public CntLibroMayor getCntLibroMayorSeleccion() {
        return cntLibroMayorSeleccion;
    }

    public void setCntLibroMayorSeleccion(CntLibroMayor cntLibroMayorSeleccion) {
        this.cntLibroMayorSeleccion = cntLibroMayorSeleccion;
    }

    public String verComprobante() {
        if (selectedDetalleComprobante != null) {
            setInSessionIdDetalleComprobante(selectedDetalleComprobante.getIdDetalleComprobante());
            setInSessionFechaInicio(fechaInicial);
            setInSessionFechaFin(fechaFinal);
            setInSessionTipoMoneda(moneda);
            return "imprimeComprobante";
        } else {
            MessageUtils.addErrorMessage("Debe Seleccionar una cuenta para ver comprobante ...");
            return null;
        }
    }
//</editor-fold>

    public CntEntidad getCntEntidadMayorSS() {
        return cntEntidadMayorSS;
    }

    public void setCntEntidadMayorSS(CntEntidad cntEntidadMayorSS) {
        this.cntEntidadMayorSS = cntEntidadMayorSS;
    }

    public Boolean getHabilitaVolverSS() {
        return habilitaVolverSS;
    }

    public void setHabilitaVolverSS(Boolean habilitaVolverSS) {
        this.habilitaVolverSS = habilitaVolverSS;
    }

    public String vuelveASumasSaldos() {
        System.out.println("...vuelve sumas y saldos    : " + cntEntidadSelecion.getIdEntidad());
        setInSessionIdEntidadSumasySaldos(cntEntidadSelecion.getIdEntidad());
        return "imprimeSumasSaldosComprobantes";

    }
    
     public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;

        Font font = wb.createFont();

        font.setFontHeightInPoints((short) 10);

        font.setFontName("Arial");

        font.setColor(IndexedColors.WHITE.getIndex());

        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        HSSFSheet sheet = wb.getSheetAt(0);

        CellStyle style = wb.createCellStyle();

        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_CENTER);

        style.setVerticalAlignment(CellStyle.VERTICAL_JUSTIFY);

        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());

        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < 10; i++) {  //CANTIDAD DE COLUMNAS DE DATATABLE
            sheet.autoSizeColumn(i);
            
        }

        int j = 0;
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (j == 0) {
                    System.out.println(".................{{{{{{{{{{{{{{"+cell.getStringCellValue().toUpperCase());
                    cell.setCellValue(cell.getStringCellValue().toUpperCase());
                    System.out.println(". style ::: ."+style);
                    cell.setCellStyle(style);
                }
            }
            j++;
        }
    }
}
