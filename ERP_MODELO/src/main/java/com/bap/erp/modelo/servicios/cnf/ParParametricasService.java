package com.bap.erp.modelo.servicios.cnf;

import com.bap.erp.modelo.entidades.cnf.ParTipoRetencion;
import com.bap.erp.modelo.entidades.cnf.ParDatosEmpresa;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.entidades.cnf.ParEstadoProyecto;
import com.bap.erp.modelo.entidades.cnf.ParSegundaMoneda;
import com.bap.erp.modelo.entidades.cnf.ParLugarDeEntrega;
import com.bap.erp.modelo.entidades.cnf.ParTipoDocIdentidad;
import com.bap.erp.modelo.entidades.cnf.ParAjustes;
import com.bap.erp.modelo.entidades.cnf.ParTiposDatoNivel;
import com.bap.erp.modelo.entidades.cnf.ParCiudad;
import com.bap.erp.modelo.entidades.cnf.ParBanco;
import com.bap.erp.modelo.entidades.cnf.ParVarios;
import com.bap.erp.modelo.entidades.cnf.ParTipoFacturacion;
import com.bap.erp.modelo.entidades.cnf.ParRecetasDistribucionCentroCosto;
import com.bap.erp.modelo.entidades.cnf.ParSucursal;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGenerales;
import com.bap.erp.modelo.entidades.cnf.ParIpWebServiceWamsa;
import com.bap.erp.modelo.entidades.cnf.ParParametrosActiveDirectory;
import com.bap.erp.modelo.entidades.cnf.ParParametrosServidor;
import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntDefinicionCuentasMigracion;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.pojo.ParCuentasGeneralesPojo;
import com.bap.erp.modelo.wrapper.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface ParParametricasService {

    Serializable find(Class clazz, String id);

    public ParValor persistParValor(ParValor parValor) throws Exception;

    public ParValor mergeParValor(ParValor parValor) throws Exception;

    public ParValor removeParValor(ParValor parValor) throws Exception;

    List<ParCuentasGenerales> parCuentasGeneralesList();

    List<ParSegundaMoneda> getSegundaMonedaList();

    List<ParDatosEmpresa> listaDatosDeEmpresa();

    List<ParVarios> listaVarios();

    List<ParTiposDatoNivel> getParTiposDatoNivels();

    DatosEmpresaWrapper factoryEmpresa();

    ComprasYVentasWrapper factoryComprasYVentas();

    GestionContableWrapper factoryGestionContable();

    VariosWrapper factoryVarios();

    CuentasDeAjusteWrapper factoryCuentasDeAjuste();

    RetencionesIUEWrapper factoryRetencionesIUE();

    ParValor findParValor(String codigo);

    ParValor findParValorContextoVarios(String codigo);

    void modificarDatosEmpresa(DatosEmpresaWrapper datosEmpresaWrapper, GestionContableWrapper gestionContableWrapper, VariosWrapper variosWrapper, String usuarioModificacion) throws Exception;

    void modificaParametrosDeGestion(ComprasYVentasWrapper comprasYVentasWrapper, CuentasDeAjusteWrapper cuentasDeAjusteWrapper, RetencionesIUEWrapper retencionesIUEWrapper, String usuarioModificacion) throws Exception;

    List<ParAjustes> listaTiposDeAjuste(String tipoDeAjuste);

    List<ParTipoComprobante> listaTiposComprobantes();

    List<ParTipoRetencion> listaTipoRetencion();

    List<ParSucursal> listaTodasLasSucursal();

    List<ParTipoFacturacion> listaLosTiposDeFactura();

    String encuentraNumeroAutorizacionSucursal(ParSucursal parSucursal);

    ParSucursal encuentraParSucursal(String descripcion);

    boolean encuentraParSucursalPorNumeroAutorizacion(String numeroAutorizacion);

    ParValor encuentraParvalorParSucursal(ParSucursal parSucuarsal);

    List<ParRecetasDistribucionCentroCosto> listaDistribucionCentroCostos();

    Long ultimoRegistroRecetaParValor();

    String verificaValoresFormDistribucion(ParValor parValor);

    List<CntConfiguracionCentrocosto> listaConfiguracionCentroCostoPorReceta(String codigoReceta);

    boolean validaNombreRepetido(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto);

    ParValor encuentraParvalorCuentaGeneralPorCodigo(String codigo);

    List<ParCuentasGenerales> listaCuentasGenerales();

    Boolean verificaExistenciaDeCuentaEnDefiniconDeCuentas(String valorNivel);

    List<ParCuentasGeneralesPojo> listaCuentasGeneralesPojo();

    List<ParEstadoProyecto> listaEstadosDeProyectos();

    boolean verificaSiExistenCuentasGenerales();

    List<CntConfiguracionCentrocosto> listaDistribucionCentroDeCostoReceta(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto);

    ParRecetasDistribucionCentroCosto findParRecetasDistribucionCentroCosto(String codigo);

    Boolean verificaRecetaDetalleComprobante(CntConfiguracionCentrocosto cntConfiguracionCentrocosto);

    void eliminaRecetaCentroCostos(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto);

    Boolean verificaRecetaDetalleComprobante(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto);

    //parametros de cuentas por pagar
//    List<EnumParametrosCuentasPorPagar> listaDeContextosParametros();

    List<ParValor> listadoBancoParValor();

    List<ParValor> listadoCiudadParValor();

    List<ParValor> listadoLugarEntregaParValor();

    List<ParValor> listadoParTipoDocIdentidadParValor();

    List<ParBanco> listadoBanco();

    List<ParCiudad> listadoCiudad();

    List<ParLugarDeEntrega> listadoLugarEntrega();

    List<ParTipoDocIdentidad> listadoParTipoDocIdentidad();

    List<ParTipoDocIdentidad> listaTiposDocumentosDeIdentidad();

    List<ParValor> cargaListadoParametricas(int valor);

    void guardaParametros(ParValor parValor, int numero) throws Exception;

    String verificaExistenciaDescripcion(ParValor parValor);

    String verificaExistenciaDeCodigo(ParValor parValor);

    void modificaParParametros(ParValor parValor, int numero);

    List<CntDefinicionCuentasMigracion> listaDefinicionCuentas(List<CntEntidad> lista);

    List<ParCuentasGenerales> listaCuentasGeneralesParametricas();

    void guardaDefinicionDeCuentasMigracion(List<CntDefinicionCuentasMigracion> listaCuentasDefinicionMigracion);

    Boolean validaListaDefinicionCuentas(List<CntDefinicionCuentasMigracion> listaCuentasDefinicionMigracion);

    Boolean validaCuentasRepetidas(List<CntDefinicionCuentasMigracion> listaCuentasDefinicionMigracion);

    ParCuentasGenerales findByCodigo(String codigo);

    List<ParCuentasGenerales> listaCuentasNoElegidas(List<CntDefinicionCuentasMigracion> listaElegidos);

    ParIpWebServiceWamsa findParValorContextoWebService(String codigo);

    ParValor findParValorContextoWebServices(String codigo);

    Boolean validaNumeroIp(String ip) throws Exception;

    Boolean verificaParametrizacionParaContabilizacionAutomatica();
    
    Boolean verificaSiUnaCuentaEstaEnParametrosDeGestion(CntEntidad cntEntidad);
    
//    Boolean verificaSiFaltaLlenarParametrosDeGestion();
    
    Boolean estaHabilitadoCCenDatosDeEmpresa();
    
    List<ParCuentasGenerales> listaTodasLasCuentasGenerales();
    
    ParParametrosServidor findParametrosServidor(String codigo)throws Exception;
    
    void modificaParametrosServidor(String urlAutenticacion)throws Exception;
    
    String devuelveMascaraPorTipoCuenta(String codigo);
    
    List<ParValor> getParametrosActiDirList();
    
    ParParametrosActiveDirectory findParametrosActiveDirectory(String codigo)throws Exception;
    
    void modificaParametrosActiveDirectory(String host, String dominio)throws Exception;
    
    Boolean verificaPeriodoYAnioActual(Date fechaActual)throws Exception;
}
