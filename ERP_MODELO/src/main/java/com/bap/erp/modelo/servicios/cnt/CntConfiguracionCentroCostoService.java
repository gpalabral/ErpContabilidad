/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnf.ParRecetasDistribucionCentroCosto;
import java.util.List;

public interface CntConfiguracionCentroCostoService extends GenericDao<CntConfiguracionCentrocosto> {

    List<CntConfiguracionCentrocosto> listaConbinadaCentrosDeCostoConConfiguracionDeCentrosDeCostoList(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    CntConfiguracionCentrocosto persistCntConfiguracionCentroCosto(CntConfiguracionCentrocosto cntConfiguracionCentroCosto) throws Exception;

    CntConfiguracionCentrocosto mergeCntConfiguracionCentroCosto(CntConfiguracionCentrocosto cntConfiguracionCentroCosto) throws Exception;

    CntConfiguracionCentrocosto removeCntConfiguracionCentroCosto(CntConfiguracionCentrocosto cntConfiguracionCentroCosto) throws Exception;

    void guardaCntConfiguracionCentroCostoDefinicion(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentroCosto, String usuario, CntEntidad cuentaElegida) throws Exception;

    void guardaCntConfiguracionCentroCostoAlternativa(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentroCosto, String usuario, ParRecetasDistribucionCentroCosto recetasDistribucionCentroCosto) throws Exception;

    List<CntConfiguracionCentrocosto> obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidadParaDefinicion(CntEntidad cntEntidad) throws Exception;

    void modificarCntConfiguracionCentroCostoDefinicion(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentroCosto, String usuario, CntEntidad cuentaElegida) throws Exception;

    List<CntConfiguracionCentrocosto> listaCentrosDeCostoPorDefinicion(CntEntidad cuenta) throws Exception;

    List<CntConfiguracionCentrocosto> listaCentrosDeCostoAlternativa(String codigoReceta) throws Exception;

    List<CntConfiguracionCentrocosto> cargaListadoConfiguracionParaRecetasDesdeUnPlanCuentas(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) throws Exception;

    Boolean verificaExistenciaDeConfiguracionCentrosDeCostoParaRecetaDeUnCntEntidad(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) throws Exception;

    CntConfiguracionCentrocosto obtieneListaConfiguracionCentrosDeCostoParaRecetaDesdeCntEntidad(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto, Long idCentroCosto) throws Exception;

    void modificarCntConfiguracionCentroCostoAlternativa(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentroCosto, String usuario, ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) throws Exception;

    List<CntConfiguracionCentrocosto> obtieneListaConfiguracionCentrosDeCostoDesdeCntEntidadParaAlternativa(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) throws Exception;
}
