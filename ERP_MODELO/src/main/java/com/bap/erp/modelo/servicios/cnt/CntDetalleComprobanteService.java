package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.entidades.cnt.CntLibroMayor;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import com.bap.erp.modelo.pojo.PojoCntDetalleComprobanteSumasSaldos;
import com.bap.erp.modelo.pojo.PojoCntEntidadBGyEERR;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CntDetalleComprobanteService extends GenericDao<CntDetalleComprobante> {

    CntDetalleComprobante persistCntDetalleComprobantes(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    CntDetalleComprobante mergeCntDetalleComprobantes(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    void removeCntDetalleComprobantesCntFacturacion(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    void removeCntDetalleComprobantesCntFacturacionQuitar(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    void deleteCntDetalleComprobantesCntFacturacion(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    void mergeCntDetalleComprobantesModificaTotal(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    List<CntDetalleComprobante> listaCntDetalleComprobantes();

    List<CntDetalleComprobante> listaDeCuentasPorComprobante(CntComprobante cntComprobante);

    List<CntDetalleComprobante> listaDeCuentasPorComprobanteParaRepilcados(CntComprobante cntComprobante);

    BigDecimal sumaDebeComprobante(List<CntDetalleComprobante> listaDeCuentasDeComprobante);

    BigDecimal sumaHaberComprobante(List<CntDetalleComprobante> listaDeCuentasDeComprobante);

    List<CntDetalleComprobante> quitaCntDetalleComprobanteDeLista(List<CntDetalleComprobante> cntDetalleComprobanteList, CntDetalleComprobante cntDetalleComprobante);

    Boolean verificaExistenciaDePendientes(CntComprobante cntComprobante);

    CntDetalleComprobante ultimaPosicionDetalleComprobante();

    void guardaCreditoFiscalNoDeducible(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    CntDetalleComprobante obtienePosicionDetalleComprobantePorComprobante(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    List<CntDetalleComprobante> ordenaSegunPosicion(CntComprobante cntComprobante) throws Exception;

    List<CntDetalleComprobante> ordenaSegunPosicionDolar(CntComprobante cntComprobante) throws Exception;

    List<CntDetalleComprobante> listaHijosPorPadre(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    List<CntDetalleComprobante> listaHijosPorPadreParaDelete(CntDetalleComprobante cntDetalleComprobante);

    void aumentaPosicionAPadres(CntComprobante cntComprobante, long posicion) throws Exception;

    void reducePosicionAPadres(CntComprobante cntComprobante, long posicion) throws Exception;

    List<CntDetalleComprobante> obtieneHijosTambienPadresDeUnDetalleComprobante(CntDetalleComprobante cntDetalleComprobante);

    CntDetalleComprobante obtieneIdPadreDeUnDetalleComprobante(List<CntDetalleComprobante> listaCntDetalleComprobanteElegidaParaQuitarse);

    CntDetalleComprobante guardaRetencionGrossing(CntDetalleComprobante cntDetalleComprobante, CntEntidad cntEntidad, String tipo) throws Exception;

    BigDecimal convierteBolivianosDolar(BigDecimal valorBolivianos, BigDecimal tipoCambio);

    List<CntLibroMayor> listaLibroMayorSegunCuenta(CntEntidad cntEntidad, Date fechaInicial, Date fechaFinal) throws Exception;

    BigDecimal sumaSaldoInicialLibroMayor(CntEntidad cntEntidad, Date fechaInicial);

    String encuentraTipoComprobante(CntComprobante cntComprobante);

    String cambiaFormatoMoneda(BigDecimal valor);

    CntFacturacion buscaFacturacionPorDetalleComprobantePadres(CntDetalleComprobante cntDetalleComprobante);

    CntDetalleComprobante obtieneMontoOriginal(final CntDetalleComprobante cntDetalleComprobante) throws Exception;

    Boolean verificaDetalleComprobanteTieneFactura(CntDetalleComprobante cntDetalleComprobante);

    Boolean verificaSiExistenFacturasEnComprobante(CntComprobante cntComprobante);

    String tipoDeTransaccionPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante);

    CntDetalleComprobante persistCntDetalleComprobantesModifica(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    CntDetalleComprobante guardaRetencionGrossingModifica(CntDetalleComprobante cntDetalleComprobante, CntEntidad cntEntidad, String tipo) throws Exception;

    void restauraAnteriorCntDetalleComprobante(CntFacturacion cntFacturacion) throws Exception;

    CntDetalleComprobante guardaDetalleYFacturaPendientes(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    CntDetalleComprobante guardaDetalleComprobanteLimpiandoAnteriores(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    List<CntDetalleComprobante> listaDeDetalleComprobantesPadresPorComprobanteSinFechaBaja(CntComprobante cntComprobante) throws Exception;

    CntDetalleComprobante guardaDetalleComprobanteYDaDeBajaAlAntecesor(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    List<CntDetalleComprobante> obtieneListaDePendientesDetalleComprobantePadres(CntComprobante cntComprobante);

    void removePendientesDetalleComprobanteCancelar(CntComprobante cntComprobante) throws Exception;

    CntDetalleComprobante persistCntDetalleComprobantesModificaCreditoFiscalNoDeducible(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    void quitarCntDetalleComprobantes(List<CntDetalleComprobante> listaCntDetalleComprobanteElegidaParaQuitarse, CntComprobante cntComprobante) throws Exception;

    List<CntDetalleComprobante> listaDetalleComprobantesPorComprobanteTambienFechaBajaSoloPadres(CntComprobante cntComprobante);

    void cambiaEstadoAnuladoTodoElComprobanteMasLasTablasDependientes(CntComprobante cntComprobante) throws Exception;

    void cambiaEstadoAnuladoDetalleComprobanteMasLasTablasDependientesSoloHijos(CntDetalleComprobante detalleComprobante) throws Exception;

    void cambiaEstadoAnuladoFactura(CntDetalleComprobante detalleComprobante) throws Exception;

    void cambiaDetalleComprobanteIdModificacion(CntComprobante cntComprobante) throws Exception;

    void cambiaEstadoDependientesSoloHijosModificado(CntDetalleComprobante detalleComprobante) throws Exception;

    List<CntDetalleComprobante> listaDetalleComprobantesPorComprobanteReporte(Long nroComprobanteInicio, Long nroComprobanteFinal, String periodo, String anio, String tipoComprobante) throws Exception;

    List<CntDetalleComprobante> listaDetalleComprobantePorComprobanteEnOrden(CntComprobante cntComprobante) throws Exception;

    //listado que filtra el valor de dolar paa mostrar en la vista
    List<CntDetalleComprobante> listaDetalleComprobantePorComprobanteEnOrdenDolar(CntComprobante cntComprobante) throws Exception;

    List<CntNivel> listaDeNiveles() throws Exception;

    List<CntDetalleComprobante> detalleComprobantePorFechaList(Date fechaConsulta) throws Exception;

    //Metodo obtiene lista Detalle Comprobantes hasta la Fecha X, por suentas ordenas con sus valores SUMAS Y SALDOS
    List<PojoCntDetalleComprobanteSumasSaldos> obtieneListaSumasSaldosSoloDetalleComprobante(Date fechaConsulta) throws Exception;

    //Este metodo suma la columna insertada en el parametro tipoCampo de un Detalle Conprobante, 
    //pero solo los detalles comprobantes que se encuentren hasta la fecha introducida.  
    //Parametro de Entrada---> tipoCampo=(debe,haber,debeDolar,haberDolar) puede tener uno de estos valores.
    BigDecimal sumaCampoMonetarioDetalleComprobante(final CntEntidad cntEntidad, Date hastaFecha, final String tipoCampo);

    //Metodo devuelve Lsitado Con tadas las cuentas con sus valores Sumas y Saldos
    List<PojoCntDetalleComprobanteSumasSaldos> devuelveListaCompletaParaSumasSaldos(Date fechaConsulta, int nivel, Boolean ceros) throws Exception;

    /*Suma el total del CampoMonetario (debe, haber, debe_dolar o haber_dolar) segùn el comprobante introducido*/
    BigDecimal sumaCampoMonetarioPorComprobante(final CntComprobante cntComprobante, final String tipoCampoMonetario);

    BigDecimal obtieneSumasgeneralesporTipoDeColumna(String tipoColumna, Date fechaConsulta) throws Exception;

    List<PojoCntDetalleComprobanteSumasSaldos> obtieneListaCompletaParaSumasSaldos(Date fechaConsulta, int nivel, Boolean ceros) throws Exception;

    List<CntEntidad> cntEntidadesPorGrupoNivelList(int nivel) throws Exception;

    BigDecimal sumaDebeComprobanteLibroMayor(List<CntLibroMayor> lista);

    BigDecimal sumaHaberComprobanteLibroMayor(List<CntLibroMayor> lista);

    BigDecimal sumaSaldoComprobanteLibroMayor(List<CntLibroMayor> lista);

    void asignaAuxiliaresADetalleComprobante(CntDetalleComprobante cntDetalleComprobante, CntAuxiliar cntAuxiliar) throws Exception;

    void asignaProyectoADetalleComprobante(CntDetalleComprobante cntDetalleComprobante, CntProyecto cntProyecto) throws Exception;

    BigDecimal sumaDebeBolivianoDolarComprobante(List<CntDetalleComprobante> listaDeCuentasDeComprobante, String tipoMoneda);

    BigDecimal sumaHaberBolivianoDolarComprobante(List<CntDetalleComprobante> listaDeCuentasDeComprobante, String tipoMoneda);

    List<CntDetalleComprobante> obtieneListaDePendientesYQuitadosDetalleComprobantePadres(CntComprobante cntComprobante);

    void guardaPosicionesAnteriores(CntComprobante cntComprobante);

    List<CntDetalleComprobante> obtienePadresPorComprobante(CntComprobante cntComprobante);

    void reestablecePosicionesAnteriores(CntComprobante cntComprobante);

    void guardaCuentaAjusteMonetarioDiferenciaTipoCambio(List cntcntDetalleComprobantesList, CntComprobante cntComprobante, String loginUsuario) throws Exception;

    Boolean verificaCuentaDiferenciaDeCambio(CntComprobante cntComprobante);

    CntDetalleComprobante buscaDiferenciaTipoCambio(CntComprobante cntComprobante);

    void guardaCuentaAjusteMonetarioDiferenciaTipoCambioModifica(CntComprobante cntComprobante) throws Exception;

    CntFacturacion buscaNumeroFactura(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    Boolean verificaRelacionCntEntidadConCntDetalleComprobante(CntEntidad cntEntidad) throws Exception;

    List<PojoCntEntidadBGyEERR> listPlanCuentaParaBGyEERR(Date fecha, String tipoReporte);

    List<PojoCntEntidadBGyEERR> listaPlanCuentasParaBalanceGeneral(Date fecha, String tipoReporte);

    List<PojoCntEntidadBGyEERR> listaPlanCuentasParaBalanceGeneralCeros(Date fecha, int nivel, Boolean ceros, String tipoReporte) throws Exception;

    BigDecimal totalDetalleComprobantePorCuenta(CntEntidad cntEntidad, Date fecha);

    BigDecimal[] totalMontoAmbasMonedasDetalleComprobantePorCuenta(CntEntidad cntEntidad, Date fecha);

    BigDecimal[] totalMontoAmbasMonedasDetalleComprobantePorTipoCpbte(CntEntidad cntEntidad, Date fechaIni, Date fechaFin, String tipoCpbte);

    CntDetalleComprobante encuentraDetalleComprobantePorFacturacion(CntFacturacion cntFacturacion) throws Exception;

    BigDecimal[] obtieneMontoTotal(Date fecha, String tipoReporte);

    BigDecimal[] obtieneMontoTotalSus(Date fecha, String tipoReporte);

    BigDecimal[] obtieneMontoTotalEI(Date fecha, String tipoReporte);

    BigDecimal sumaHaberParaLibroMayor(CntEntidad cntEntidad, Date fechaInicial);

    BigDecimal sumaHaberDolarParaLibroMayor(CntEntidad cntEntidad, Date fechaInicial);

    BigDecimal sumadebeDolarInicialLibroMayor(CntEntidad cntEntidad, Date fechaInicial);

    Boolean verificaPeriodoyGestion(Date fecha);

    List<CntLibroMayor> listaResultados(List<CntLibroMayor> lista) throws Exception;

    List<CntLibroMayor> listaUnida(CntEntidad cntEntidad, Date fechaInicial, Date fechaFinal) throws Exception;

    BigDecimal[] obtieneMontoTotalEERR(Date fechaI, Date fechaF, String tipoReporte);

    BigDecimal[] obtieneMontoTotalSusEERR(Date fechaI, Date fechaF, String tipoReporte);

    List<PojoCntEntidadBGyEERR> listaPlanCuentasParaEERR(Date fechaI, Date fechaF, String tipoReporte);

    List<PojoCntEntidadBGyEERR> listaEERRctrlCeros(Date fechaI, Date fechaF, int nivel, Boolean ceros, String tipoReporte) throws Exception;

    BigDecimal[] totalMontoAmbasMonedasDetalleComprobantePorCuentaEERR(CntEntidad cntEntidad, Date fechaI, Date fechaF);

    BigDecimal sumaDebeDolarComprobanteLibroMayor(List<CntLibroMayor> lista);

    BigDecimal sumaHaberDolarComprobanteLibroMayor(List<CntLibroMayor> lista);

    BigDecimal sumaSaldoDolarComprobanteLibroMayor(List<CntLibroMayor> lista);

    CntLibroMayor obtieneComprobanteXnumero(Long idDetalleComprobante);

    List<PojoCntDetalleComprobanteSumasSaldos> obtieneListaFinal(Date fechaConsulta, int nivel, Boolean ceros) throws Exception;

    Date devuelveFecha(Date inicial);

    void guardaCntDetalleComprobante(CntComprobante cntComprobante, List<CntDetalleComprobante> listCntDetalleComprobante) throws Exception;

    BigDecimal[] obtieneMontoTotalDebeHaber(String tipoReporte);

    List<CntDetalleComprobante> listaproFechas(Date fi, Date f2);

    CntDetalleComprobante getObtieneDetalleConFacturaNullXCpbte(CntComprobante cntComprobante);

}
