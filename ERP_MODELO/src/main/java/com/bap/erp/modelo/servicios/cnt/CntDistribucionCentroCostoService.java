/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDistribucionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HENRRY
 */
public interface CntDistribucionCentroCostoService extends GenericDao<CntDistribucionCentrocosto> {

    List<CntDistribucionCentrocosto> listaCombinadaCentrosDeCostoConDistribucionCentroCostoList(CntDetalleComprobante cntDetalleComprobante, Boolean porDefinicion, String codigoReceta) throws Exception;

    Boolean verificaSumatoriaDeProcentajesYMontosParaDistribucionCC(List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList) throws Exception;

    List<CntDistribucionCentrocosto> obtieneAutomaticamenteElProcentajesYMontosParaDistribucionCC(List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList, BigDecimal montoTotal) throws Exception;

    BigDecimal obtieneMontoDesdeElPorcentaje(BigDecimal montoTotal, BigDecimal porcentaje);

    BigDecimal obtienePorcentajeDesdeElMonto(BigDecimal montoTotal, BigDecimal monto);

    void guardaListaDeCntDistribucionCentroCosto(List<CntDistribucionCentrocosto> cntDistribucionCentrocostosList, String UsuarioLogueado) throws Exception;

    CntDistribucionCentrocosto persistCntDistribucionCentrocosto(CntDistribucionCentrocosto cntDistribucionCentrocosto) throws Exception;

    CntDistribucionCentrocosto mergeCntDistribucionCentrocosto(CntDistribucionCentrocosto cntDistribucionCentrocosto) throws Exception;

    BigDecimal obtieneSumaDeMontosListaCntDistribucionCentroCosto(List<CntDistribucionCentrocosto> listaCntDistribucionCentrocostos);

    BigDecimal obtieneSumaDePorcentajesListaCntDistribucionCentroCosto(List<CntDistribucionCentrocosto> listaCntDistribucionCentrocostos);

    List<CntDistribucionCentrocosto> obtieneListaDistribucionCentrocostoPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante);

    void cambiaEstadoDistribucionCentrocostoModificacion(CntDetalleComprobante detalleComprobante) throws Exception;

    BigDecimal encuentraRestoDistribucionCentroDeCostos(List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList, BigDecimal montoTotal);

    BigDecimal encuentraRestoPorcentajeDistribucionCentroDeCostos(List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList, BigDecimal montoTotal);

    List<CntDistribucionCentrocosto> listaDistribucionCentroCostoPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    List<CntDistribucionCentrocosto> listaDistribucionCCParaModificacion(CntDetalleComprobante cntDetalleComprobante, List<CntDistribucionCentrocosto> listaOriginal) throws Exception;

    void removeCntDistribucionCentroCosto(CntDistribucionCentrocosto cntDistribucionCentrocosto) throws Exception;

    void deleteCntDistribucionCentroCosto(CntDistribucionCentrocosto cntDistribucionCentrocosto) throws Exception;
    
    List<CntDistribucionCentrocosto> listaDistribucionCCPorDetalleComprobanteConFechaBaja(CntDetalleComprobante cntDetalleComprobante);        
    
    void modificaDistribucionCentroDeCostoPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante, String usuarioLogueado, Date fechaAlta) throws Exception;
    
    void modificaFechaYUsuarioDistribucionPorDistribucion(CntDistribucionCentrocosto cntDistribucionCentrocosto, String usuarioLogueado, Date fechaAlta) throws Exception;
    
//retorna true si la lista es completa y no posee valores null en el campo porcentaje
    Boolean verificaValoresListaCentroCostos(List<CntConfiguracionCentrocosto> listaCntConfiguracionCentrocosto);
    
    Boolean verificaRelacionCentroCostoConCntDistribucionCentrocosto(CntEntidad cntEntidad) throws Exception;
    
    Boolean existeUnComprobanteConCentroDeCosto();
}
