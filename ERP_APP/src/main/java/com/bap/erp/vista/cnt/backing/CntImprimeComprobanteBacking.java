package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumCampoMonetario;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.enums.EnumTipoComprobante;
import com.bap.erp.modelo.enums.EnumTipoMoneda;
import com.bap.erp.modelo.servicios.cnt.*;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.bap.erp.vista.utils.ReportManager;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import com.bap.erp.util.Utilitario;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

@ManagedBean(name = "cntImprimeComprobanteBacking")
@ViewScoped
public class CntImprimeComprobanteBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    private CntComprobante cntComprobante;
    private List<ParTipoComprobante> listaTipoComprobante;
    private ParTipoComprobante parTipoComprobante = new ParTipoComprobante();
    private Long inicioImpresion = 1L;
    private Long finImpresion = 1L;
    private String periodo = new Date().getMonth() + 1 + "";
    private String anio = new Date().getYear() + 1900 + "";
    private String tipo = EnumTipoComprobante.EGRESO.getCodigo();
    private String moneda = EnumTipoMoneda.BOLIVIANOS.getCodigo();
    private Boolean opcionImpresion = true;
    private List<CntDetalleComprobante> listaResultadoConsulta = new ArrayList<CntDetalleComprobante>();
    private CntDetalleComprobante cntDetalleComprobanteElegido;
    private int comprobanteElegido = 0;
    private int comprobanteFinal = 0;
    private List<CntComprobante> listaDeComprobantesPorParametros = new ArrayList<CntComprobante>();
    private String tituloDeComprobante = "Lista de Transacciones";
    private Boolean limiteInicial = true;
    private Boolean limiteFinal = true;
    private List<String> listaDePaginasYComprobantes = new ArrayList<String>();
    private String comprobanteElegidoCadena = "0";
    private BigDecimal debe = new BigDecimal(0);
    private BigDecimal haber = new BigDecimal(0);
    private BigDecimal debeDolar = new BigDecimal(0);
    private BigDecimal haberDolar = new BigDecimal(0);
    SimpleDateFormat formateador = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
    private Utilitario util = new Utilitario();
    private Boolean habilitaReporte = true;
    CntDetalleComprobante cntDetalleComprobante;
    NumberFormat formatter = new DecimalFormat("#,#00.00");
    private String debes;
    private String habers;
    private String debeDolars;
    private String haberDolars;
    private Boolean habilitaVolver = false;
    private Boolean habilitaCancelar = false;

    public CntImprimeComprobanteBacking() {
    }

    @PostConstruct
    void initCntImprimeComprobanteBacking() {
        if (super.getFromSessionIdComprobante() != null) {
            cntComprobante = cntComprobantesService.find(CntComprobante.class, super.getFromSessionIdComprobante());
            periodo = cntComprobante.getPeriodo();
            finImpresion = inicioImpresion = cntComprobante.getNumero();
            listaDetallesComprobantesPorConsulta();
            tipo = cntComprobante.getParTipoComprobante().getCodigo();
            habilitaCancelar = true;
        }
        if (super.getFromSessionIdDetalleComprobante() != null) {
            cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, super.getFromSessionIdDetalleComprobante());
            inicioImpresion = cntDetalleComprobante.getCntComprobante().getNumero();
            finImpresion = inicioImpresion;
            periodo = cntDetalleComprobante.getCntComprobante().getPeriodo();
            moneda = super.getFromSessionTipoMoneda();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(cntDetalleComprobante.getCntComprobante().getFechaAlta());
            int gestion = calendar.get(calendar.YEAR);
            anio = String.valueOf(gestion);
            tipo = cntDetalleComprobante.getCntComprobante().getParTipoComprobante().getCodigo();
            listaDetallesComprobantesPorConsulta();
            habilitaVolver = true;
            setInSessionIdDetalleComprobante(null);
        }
    }

    public String cancelaImpresionDeComprobantes() {
        limpiarVariablesSession();
        return "comprobantesList";
    }

    public List<CntDetalleComprobante> listaDetallesComprobantesPorConsulta() {
        System.out.println(".------------------..............");
        try {
            if (inicioImpresion != null && finImpresion != null && !periodo.equals("") && !anio.equals("")) {
                if (finImpresion < inicioImpresion) {
                    MessageUtils.addErrorMessage("El rango de Comprobantes no se encuentra correctamente introducido");
                } else {
                    listaDeComprobantesPorParametros = cntComprobantesService.listaComprobantesEnUnRango(inicioImpresion, finImpresion, periodo, anio, tipo);
                    if (!listaDeComprobantesPorParametros.isEmpty()) {
                        listaResultadoConsulta = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(listaDeComprobantesPorParametros.get(0));
                        comprobanteFinal = listaDeComprobantesPorParametros.size();
                        comprobanteElegido = 1;
                        tituloDeComprobante = "Comprobante Nro " + listaDeComprobantesPorParametros.get(0).getNumero() + " " + listaDeComprobantesPorParametros.get(0).getDescripcion() + " ";
                        sumaCampoMonetario(listaDeComprobantesPorParametros.get(0));
                        limiteFinal = false;
                    } else {
                        listaResultadoConsulta = new ArrayList<CntDetalleComprobante>();
                        comprobanteFinal = 0;
                        comprobanteElegido = 0;
                        tituloDeComprobante = "Lista de Transacciones";
                        limiteFinal = true;
                        debe = new BigDecimal(0);
                        haber = new BigDecimal(0);
                        debeDolar = new BigDecimal(0);
                        haberDolar = new BigDecimal(0);
                    }
                }
            } else {
                MessageUtils.addErrorMessage("Es necesario llenar todos los campos para realizar la búsqueda");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        habilitaReporte = listaResultadoConsulta.isEmpty();
        System.out.println("...lista resultado consulta es : " + listaResultadoConsulta.size());
        return listaResultadoConsulta;
    }

    public void botonComprobanteSiguiente() throws Exception {
        if (comprobanteElegido < comprobanteFinal) {
            comprobanteElegido++;
            listaResultadoConsulta = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
            tituloDeComprobante = "Comprobante Nro " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getNumero() + " " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getDescripcion() + " ";
            sumaCampoMonetario(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
        }
        habilitaBotonesSiguienteAnterior();
    }

    public void botonComprobanteAnterior() throws Exception {
        if (comprobanteElegido > 1) {
            comprobanteElegido--;
            listaResultadoConsulta = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
            tituloDeComprobante = "Comprobante Nro " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getNumero() + " " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getDescripcion() + " ";
            sumaCampoMonetario(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
        }
        habilitaBotonesSiguienteAnterior();
    }

    public void botonComprobanteUltimo() throws Exception {
        if (comprobanteFinal > 1) {
            comprobanteElegido = comprobanteFinal;
            listaResultadoConsulta = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
            tituloDeComprobante = "Comprobante Nro " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getNumero() + " " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getDescripcion() + " ";
            sumaCampoMonetario(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
        }
        habilitaBotonesSiguienteAnterior();

    }

    public void botonComprobanteInicial() throws Exception {
        if (comprobanteFinal >= 1) {
            comprobanteElegido = 1;
            listaResultadoConsulta = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
            tituloDeComprobante = "Comprobante Nro " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getNumero() + " " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getDescripcion() + " ";
            sumaCampoMonetario(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
        }
        habilitaBotonesSiguienteAnterior();
    }

    public void eligeComprobante(ValueChangeEvent event) throws Exception {

        if (comprobanteElegido > 0) {
            listaResultadoConsulta = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
            tituloDeComprobante = "Comprobante Nro " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getNumero() + " " + listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getDescripcion() + " ";
        } else {
            comprobanteElegido = 0;
        }
        habilitaBotonesSiguienteAnterior();
    }

    public void cargaListadoPorMoneda(ValueChangeEvent event) throws Exception {
        if (moneda.equals(EnumTipoMoneda.BOLIVIANOS.getCodigo())) {

            listaResultadoConsulta = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
        } else {

            listaResultadoConsulta = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrdenDolar(listaDeComprobantesPorParametros.get(comprobanteElegido - 1));
        }

    }

    public List<String> listaDePaginasYComprobantes() {
        listaDePaginasYComprobantes.add("");
        if (comprobanteFinal > 0) {
            listaDePaginasYComprobantes = cntComprobantesService.listaDeNumerosDeComprobantesParaReporte(listaDeComprobantesPorParametros);
        }
        return listaDePaginasYComprobantes;
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

    public void sumaCampoMonetario(CntComprobante comprobanteIntroducido) {
        debe = cntDetalleComprobanteService.sumaCampoMonetarioPorComprobante(comprobanteIntroducido, EnumCampoMonetario.DEBE.getCodigo());
        haber = cntDetalleComprobanteService.sumaCampoMonetarioPorComprobante(comprobanteIntroducido, EnumCampoMonetario.HABER.getCodigo());
        debeDolar = cntDetalleComprobanteService.sumaCampoMonetarioPorComprobante(comprobanteIntroducido, EnumCampoMonetario.DEBE_DOLAR.getCodigo());
        haberDolar = cntDetalleComprobanteService.sumaCampoMonetarioPorComprobante(comprobanteIntroducido, EnumCampoMonetario.HABER_DOLAR.getCodigo());
        debes = formatter.format(debe);
        habers = formatter.format(haber);
        debeDolars = formatter.format(debeDolar);
        haberDolars = formatter.format(haberDolar);
    }

    public List<ParTipoComprobante> listaTiposComprobantes() {
        return parParametricasService.listaTiposComprobantes();
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

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public Long getInicioImpresion() {
        return inicioImpresion;
    }

    public void setInicioImpresion(Long inicioImpresion) {
        this.inicioImpresion = inicioImpresion;
    }

    public Long getFinImpresion() {
        return finImpresion;
    }

    public void setFinImpresion(Long finImpresion) {
        this.finImpresion = finImpresion;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public List<ParTipoComprobante> getListaTipoComprobante() {
        return listaTipoComprobante;
    }

    public void setListaTipoComprobante(List<ParTipoComprobante> listaTipoComprobante) {
        this.listaTipoComprobante = listaTipoComprobante;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Boolean getOpcionImpresion() {
        return opcionImpresion;
    }

    public void setOpcionImpresion(Boolean opcionImpresion) {
        this.opcionImpresion = opcionImpresion;
    }

    public ParTipoComprobante getParTipoComprobante() {
        return parTipoComprobante;
    }

    public void setParTipoComprobante(ParTipoComprobante parTipoComprobante) {
        this.parTipoComprobante = parTipoComprobante;
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public List<CntDetalleComprobante> getListaResultadoConsulta() {
        return listaResultadoConsulta;
    }

    public void setListaResultadoConsulta(List<CntDetalleComprobante> listaResultadoConsulta) {
        this.listaResultadoConsulta = listaResultadoConsulta;
    }

    public CntDetalleComprobante getCntDetalleComprobanteElegido() {
        return cntDetalleComprobanteElegido;
    }

    public void setCntDetalleComprobanteElegido(CntDetalleComprobante cntDetalleComprobanteElegido) {
        this.cntDetalleComprobanteElegido = cntDetalleComprobanteElegido;
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

    public List<CntComprobante> getListaDeComprobantesPorParametros() {
        return listaDeComprobantesPorParametros;
    }

    public void setListaDeComprobantesPorParametros(List<CntComprobante> listaDeComprobantesPorParametros) {
        this.listaDeComprobantesPorParametros = listaDeComprobantesPorParametros;
    }

    public String getTituloDeComprobante() {
        return tituloDeComprobante;
    }

    public void setTituloDeComprobante(String tituloDeComprobante) {
        this.tituloDeComprobante = tituloDeComprobante;
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

    public String getComprobanteElegidoCadena() {
        return comprobanteElegidoCadena;
    }

    public void setComprobanteElegidoCadena(String comprobanteElegidoCadena) {
        this.comprobanteElegidoCadena = comprobanteElegidoCadena;
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public BigDecimal getDebeDolar() {
        return debeDolar;
    }

    public void setDebeDolar(BigDecimal debeDolar) {
        this.debeDolar = debeDolar;
    }

    public BigDecimal getHaberDolar() {
        return haberDolar;
    }

    public void setHaberDolar(BigDecimal haberDolar) {
        this.haberDolar = haberDolar;
    }

    public String reporteLibroMayor_action() {

        try {

            Map parameters = new HashMap();//mapear
            parameters.put("tipoUsuario", "GUS");

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);
            report.drawReport(FacesContext.getCurrentInstance(), cntComprobantesService.listaComprobantesEnUnRango(1L, 500L, "3", "2014", "EGRE"), "libroMayor");//nombre jasper

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String reporteComprobantes_action() {
        String tipoCbte = listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getParTipoComprobante().getDescripcion();
        BigDecimal tipoCambio = listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getTipoCambio();
        String fecha = formateador.format(listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getFechaAlta());
        String numeroLiteralD = util.Convertir(debe.toString(), true);
        String numeroLiteralDSus = util.Convertir(debeDolar.toString(), true);
        try {
            Map parameters = new HashMap();//mapear
            parameters.put("moneda", moneda);
            parameters.put("tipoCbte", tipoCbte);
            parameters.put("tipoCambio", tipoCambio);
            parameters.put("fecha", fecha);
            parameters.put("numeroLiteralD", numeroLiteralD);
            parameters.put("numeroLiteralDSus", numeroLiteralDSus);

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);
            if (moneda.equals(EnumTipoMoneda.BOLIVIANOS.getCodigo())) {
                report.drawReport(FacesContext.getCurrentInstance(), cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(listaDeComprobantesPorParametros.get(comprobanteElegido - 1)), "reporte_comprobantes");//nombre jasper
            } else {
                report.drawReport(FacesContext.getCurrentInstance(), cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrdenDolar(listaDeComprobantesPorParametros.get(comprobanteElegido - 1)), "reporte_comprobantes");//nombre jasper

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String reporteComprobantesEIT_action() {
        String tipoCbte = listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getParTipoComprobante().getDescripcion();
        BigDecimal tipoCambio = listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getTipoCambio();
        String fecha = formateador.format(listaDeComprobantesPorParametros.get(comprobanteElegido - 1).getFechaAlta());
        String numeroLiteralD = util.Convertir(debe.toString(), true);
        String numeroLiteralDSus = util.Convertir(debeDolar.toString(), true);
        try {
            Map parameters = new HashMap();//mapear
            parameters.put("moneda", moneda);
            parameters.put("tipoCbte", tipoCbte);
            parameters.put("tipoCambio", tipoCambio);
            parameters.put("fecha", fecha);
            parameters.put("numeroLiteralD", numeroLiteralD);
            parameters.put("numeroLiteralDSus", numeroLiteralDSus);

            ReportManager report = new ReportManager();
            report.setReportParam(parameters);

            report.drawReport(FacesContext.getCurrentInstance(), cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(listaDeComprobantesPorParametros.get(comprobanteElegido - 1)), "reporte_comprobantesEgreso");//nombre jasper

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean getHabilitaReporte() {
        return habilitaReporte;
    }

    public void setHabilitaReporte(Boolean habilitaReporte) {
        this.habilitaReporte = habilitaReporte;
    }

    public CntDetalleComprobante getCntDetalleComprobante() {
        return cntDetalleComprobante;
    }

    public void setCntDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        this.cntDetalleComprobante = cntDetalleComprobante;
    }

    public String vuelveALibroMayor() {
        setInSessionIdEntidadEERRR(cntDetalleComprobante.getCntEntidad().getIdEntidad());
        setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
        return "libro_Mayor";
    }

    public String insertaPuntosAutomatio(CntEntidad cntEntidad) {
        return cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad);
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public Utilitario getUtil() {
        return util;
    }

    public void setUtil(Utilitario util) {
        this.util = util;
    }

    public String getDebes() {
        return debes;
    }

    public void setDebes(String debes) {
        this.debes = debes;
    }

    public String getHabers() {
        return habers;
    }

    public void setHabers(String habers) {
        this.habers = habers;
    }

    public String getDebeDolars() {
        return debeDolars;
    }

    public void setDebeDolars(String debeDolars) {
        this.debeDolars = debeDolars;
    }

    public String getHaberDolars() {
        return haberDolars;
    }

    public void setHaberDolars(String haberDolars) {
        this.haberDolars = haberDolars;
    }

    public Boolean getHabilitaVolver() {
        return habilitaVolver;
    }

    public void setHabilitaVolver(Boolean habilitaVolver) {
        this.habilitaVolver = habilitaVolver;
    }

    public Boolean getHabilitaCancelar() {
        return habilitaCancelar;
    }

    public void setHabilitaCancelar(Boolean habilitaCancelar) {
        this.habilitaCancelar = habilitaCancelar;
    }
}
