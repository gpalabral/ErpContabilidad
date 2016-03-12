package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.commons.entities.mappings.EntidadArbolPojo;
import com.bap.erp.commons.entities.mappings.EntidadPojo;
import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGeneralesNivel;
import com.bap.erp.modelo.entidades.cnf.ParPlanCuentas;
import com.bap.erp.modelo.entidades.cnt.*;
import com.bap.erp.modelo.pojo.CntEntidadPojo;
import java.math.BigDecimal;
import java.util.List;

public interface CntEntidadesService extends GenericDao<CntEntidad> {

    String eliminaCaracterDescripcion(CntEntidad cntObjetos);

    String generaCodigoNiveleAndSubNivel(CntEntidad cntObjetos, String tipo, int numero);

    String verificaExistenciaCodigo(String cuenta);

    int obtieneNiveleCuentaSubAndPadre(CntEntidad cntObjetos, String tipo);

    boolean verificaExistenciaMascara(CntEntidad cntObjetos);

    String generaEspaciosEnDescripcionNivelesSubAndPadre(CntEntidad cntObjetos);

    CntEntidad persistCntObjetosConAsignacionPorDefinicion(CntEntidad cntEntidad, String tipo, CntParametroAutomatico cntParametroAutomatico) throws Exception;

    CntEntidad persistCntObjetosConAsignacionPorDefinicionConAsignacionAuxiliares(CntEntidad cntEntidad, String tipo, CntParametroAutomatico cntParametroAutomatico, List<CntAuxiliar> selectAuxiliarParaAsignacion, List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto) throws Exception;

    CntEntidad persistCntObjetos(CntEntidad cntEntidad) throws Exception;

    CntEntidad mergeCntObjetos(CntEntidad cntObjetos) throws Exception;

    CntEntidad removeCntObjetos(CntEntidad cntEntidad) throws Exception;

    CntEntidad mergeCntEntidadAndCntParametroAutomatico(CntEntidad cntEntidad, CntParametroAutomatico cntParametroAutomatico) throws Exception;

    boolean verificaExistenciaMascaraNivelAndSubNivel(String cuenta);

    boolean activaTipoMovimientoCombo(CntEntidad cntObjetos);

    String generaCodigoNivelesSubAndPadre(CntEntidad getIdObjeto, String tipo);

    String generaNumeroSiguiente(CntEntidad cntObjetos, String tipo);

    boolean activaTipoMovimiento(CntEntidad cntObjetos, String tipo, String mascaraNuevo);

    int controlaLongitudNumero(CntEntidad cntObjetos, String tipo);

    List<CntEntidad> obtieneHijosPorCntObjetos(CntEntidad cntObjetos);

    List<CntEntidad> obtieneHermanosPorCntEntidad(CntEntidad cntEntidad);

    List<CntEntidad> cntEntidadesPorGrupoNivelList(String grupoNivel) throws Exception;

    boolean verificaCuentasGeneralesListLleno(List<ParCuentasGeneralesNivel> cntPlanCuentasList);

    void persistCuentasGeneralesPasanObjetosList(List<ParCuentasGeneralesNivel> cntPlanCuentasList, CntMascara cntMascaras, String usuarioLogeado) throws Exception;

    List<CntEntidad> listaSuperiorGrupoDeObjetoSeleccionado(CntEntidad cntEntidad);

    boolean esNivel1oNivel2(CntEntidad cntEntidad);

    String devuelveTipoMovimiento(int tipoMovimiento);

    String[] obtieneMascaraSeparada(CntEntidad cntEntidad, String tipoNivel);

    CntNivel obtienedescripcionNivel(int value);

    String generaNumeroSiguienteAutomatico(CntEntidad cntEntidad, String nivel);

    void persistMasivoCuentasNivelDosAndUnoBaseMigrada(String nombreUsuarioLogeado) throws Exception;

    List<CntEntidad> listaCuentasDeUltimoNivelPorDescripcion(String descripcion);

    List<CntEntidad> listaCuentasParaRetencionesAndGrossingUp();

    List<CntEntidad> cntEntidadesParaCentrosDeCostoSoloNivelUnoList() throws Exception;

    List<CntEntidad> cntEntidadesParaCentrosDeCostoSoloNivelUnoFiltraList() throws Exception;

    Boolean verfificaExistenciaDeCentroDeCostoAntesDeAdicionar(CntEntidad centrosDeCostoElegidaParaQuitarseAux, List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto);

    Boolean verificaEntidadCentroCosto(CntEntidad cntEntidad) throws Exception;

    List<CntConfiguracionCentrocosto> llevaTodosLosCentrosDeCostoNivelUnoASiguienteListaConfiguracion() throws Exception;

    List<CntEntidad> filtraObjetosExistenteDelListadoConfiguracionParaGenerarListaCentroCosto(List<CntConfiguracionCentrocosto> configuracionCentrocostosList) throws Exception;

    Boolean validaSumaTotalProcentajeAlCienPorciento(List<CntConfiguracionCentrocosto> configuracionCentrocostosList) throws Exception;

    BigDecimal SumaTotalProcentajeAlCienPorciento(List<CntConfiguracionCentrocosto> configuracionCentrocostosList) throws Exception;

    List<CntEntidad> listaPadresDeCentroDeCosto(CntEntidad centroDeCostoHiijo) throws Exception;

    Boolean verificaExistenciaDeConfiguracionCentrosDeCostoDeUnCntEntidad(CntEntidad cntEntidad) throws Exception;

    CntConfiguracionCentrocosto obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidad(CntEntidad cntEntidad, Long idCentroCosto) throws Exception;

    List<CntConfiguracionCentrocosto> cargaListadoConfiguracionDesdeUnPlanCuentas(CntEntidad cntEntidad) throws Exception;

    Boolean validaSumaTotalProcentajeAlCienPorcientoParaModificar(List<CntConfiguracionCentrocosto> configuracionCentrocostosList) throws Exception;

    Boolean verificaEntidadPadre(CntEntidad cntEntidad) throws Exception;

    Boolean verificaEntidadConCentroCosto(CntEntidad cntEntidad) throws Exception;

    void guardaCuentaGeneral(CntEntidad cntEntidad, String codigoCuenta) throws Exception;

    void guardaCuentaGeneralYPlanDeCuentas(CntEntidad cntObjetos, String tipo, CntParametroAutomatico cntParametroAutomatico, String codigoCuenta) throws Exception;

    List<ParPlanCuentas> listaLasCuentasEnUnRango(CntEntidad cuentaInicial, CntEntidad cuentaFinal) throws Exception;

    CntEntidad cntEntidadPorMascara(String cuentaInicial) throws Exception;

    List<String> listaDeNumerosDeEntidadesParaReporte(List<ParPlanCuentas> listaDeParPlanCuentas);

    Boolean verificaEntidadConAuxiliar(CntEntidad cntEntidad) throws Exception;

    Boolean verificaEntidadConProyecto(CntEntidad cntEntidad) throws Exception;

    Boolean verificaAuxiliarEntidades() throws Exception;

    //verifica si una entidad del plan de cuentas tiene centro de costos
    Boolean verificaCentroCostosEntidades() throws Exception;

    //verifica si un proyecto tiene asignado proyectos para bloquear el boton en radio de datos de empresa
    Boolean verificaProyectoDetalleComprobante() throws Exception;

    List<CntAuxiliar> listaDeAuxiliaresPorEntidad(CntEntidad cntEntidad) throws Exception;

    //modificar auxiliar
    CntEntidad mergeCntObjetosConAsignacionPorDefinicionConAsignacionAuxiliares(CntEntidad cntEntidad, String tipo, CntParametroAutomatico cntParametroAutomatico, List<CntAuxiliar> selectAuxiliarParaAsignacion, List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto) throws Exception;

    //verifica si una entidad tiene asignado un auxiliar
    Boolean verificaEntidadTieneAuxiliarAsignado(CntEntidad cntEntidad) throws Exception;

    Boolean verificaEntidadTieneParametrosAutomaticos() throws Exception;

    Boolean habilitaEliminacionCentroCosto(CntEntidad cntEntidad);

    String[] verificaYEliminaCentrosDeCosto(CntEntidad cntEntidad) throws Exception;

    void eliminaCntEntidadConDescendencia(CntEntidad cntEntidad) throws Exception;

    List<CntEntidad> listaUltimosDescendientes(CntEntidad cntEntidad);

    List<CntEntidad> listaTodosLosDescendientes(CntEntidad cntEntidad);

    Boolean habilitaEliminacionCentroCostoConDistribucion(CntEntidad cntEntidad);

    Boolean modificaMasivoPlanDeCuentasSoloCampoTieneCentrosDeCosto(CntEntidad entidadSeleccionada) throws Exception;

    Boolean verificaSiEnUnRangoDeCuentasTieneAsignadosCentrosDeCosto(CntEntidad cntEntidad);

    String generaMascaraConElRegistroDelExcel(String mascara);

    String convierteDigitosNueve(String parteCodigo);

    void saveCntMascarCntNivelesCntPlancuentasCntParametroAutomatico(CntMascara cntMascaras, List<CntNivel> cntNivelesList, List<CntEntidad> listaCntEntidad, String UsuarioLogueado) throws Exception;

    void persistCntEntidadListas(List<CntEntidad> listaCntEntidad, CntMascara cntMascara) throws Exception;

    void cambiafechabaja() throws Exception;

    List<CntParametroAutomatico> listaDeParametrosautomaticosmodificar();

    int generaNivelPorMascara(String mascara);

    long obtieneIdPadreParaBDMigrada(CntEntidad cntEntidad);

    List<CntEntidadPojo> listaCuentasConParametrosAutomaticos();

    List<CntEntidadPojo> listaCuentasConParametrosAutomaticosPorDescripcion(String descripcion);

    List<CntPojoParametrizacionAjustesPlanCuentas> cargaListaParaParametrizacionAjustes();

    void eliminaPlanDeCuentas(CntEntidad cntEntidad) throws Exception;

    CntEntidad eliminaCuentaConSuParametrosAutomatico(CntEntidad cntEntidad) throws Exception;

    List<CntEntidad> listaPlanCuentasDescendente(String tipoReporte);

    Boolean esLaCuentaInicialInferiorALaFinalLibroMayor(CntEntidad cuentaInicial, CntEntidad cuentaFinal);

    List<EntidadArbolPojo> planDeCuentas() throws Exception;

    public List<CntEntidad> listaPlanDeCuentasPorMascara(String tipo) throws Exception;

    public List<CntEntidad> listaPlanDeCuentasPorNivel(int nivel) throws Exception;
    
    public List<CntEntidad> listaPlanDeCuentasPoridEntidad(String tipo, Long idEntidad) throws Exception;

    public List<EntidadPojo> getListaEntidadPojoByEntidad(CntEntidad cntEntidad, Long idEntidad) throws Exception;
    
    public List<EntidadArbolPojo> getListaEntidadArbolPojo() throws Exception;
    
    public List<EntidadPojo> getListaEntidadPojoByEntidadRecursivo(CntEntidad cntEntidad) throws Exception;
    
    public List<CntEntidad> listaPlanDeCuentasRecursivo(String tipo, int nivel) throws Exception;
    
    public List<EntidadArbolPojo> getListaEntidadArbolPojoRecursivo() throws Exception;
    
    List<CntEntidad> obtieneHermanosPorCntEntidadCC(CntEntidad cntEntidad);

    List<CntEntidad> obtieneHijosPorCntObjetosCC(CntEntidad cntEntidad);
    
    Boolean comparaEntidadConDetalleComprobante(CntEntidad cntEntidad, CntDetalleComprobante detalleComprobante);
}
