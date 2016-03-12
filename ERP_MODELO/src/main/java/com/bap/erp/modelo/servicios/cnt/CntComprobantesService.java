package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.commons.entities.mappings.CalculosTributarios;
import com.bap.erp.commons.entities.mappings.Comprobante;
import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDistribucionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CntComprobantesService extends GenericDao<CntComprobante> {

    CntComprobante persistCntComprobantes(CntComprobante cntComprobante) throws Exception;

    CntComprobante mergeCntComprobantes(CntComprobante cntComprobante) throws Exception;

    void deleteCntComprobantes(CntComprobante cntComprobante) throws Exception;

    List<CntComprobante> listaCntComprobantes() throws Exception;

    CntComprobante persistCntComprobanteYCntDetalleComprobante(CntComprobante cntComprobante, List<CntDetalleComprobante> listaCntDetalleComprobante) throws Exception;

    CntComprobante mergeCntComprobanteYCntDetalleComprobanteConfirmado(CntComprobante cntComprobante, List<CntDetalleComprobante> listaCntDetalleComprobante, String loginUsuario) throws Exception;

    void removeCntComprobanteCntDetalleComprobanteCntFacturacion(CntComprobante cntComprobante) throws Exception;

    void deleteCntComprobanteCntDetalleComprobanteCntFacturacion(CntComprobante cntComprobante) throws Exception;

    void mergeCntComprobanteAndCntDetalleComprobanteAndFacturaTotal(CntComprobante cntComprobante) throws Exception;

    Long ultimoNumeroComprobante(CntComprobante cntComprobante) throws Exception;
    
    Long obtieneUltimoNumeroComprobante(CntComprobante cntComprobante) throws Exception;

    CntComprobante mergeCntComprobanteYCntDetalleComprobante(CntComprobante cntComprobante, List<CntDetalleComprobante> listaCntDetalleComprobante, List<CntDetalleComprobante> listaCntDetalleComprobanteAQuitarse) throws Exception;

    Boolean verificaExistenciaDePendientes();

    CntComprobante persistTemporalParaModificacion(CntComprobante cntComprobanteElegido, String usuarioLogeado) throws Exception;

    CntComprobante mergeAnuladoComprobanteDetalleFacturacion(CntComprobante cntComprobanteElegido, String usuarioLogeado) throws Exception;

    String obtienePeriodoPorFecha(Date fecha) throws Exception;

    CntComprobante persistTemporalParaCopia(CntComprobante cntComprobanteElegido, String usuarioLogeado) throws Exception;

    CntFacturacion encuentraFacturasPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante);

    Boolean verificaComprobanteConFactura(CntComprobante cntComprobante);

    List<CntDetalleComprobante> obtieneDetalleComprobante(CntComprobante cntComprobante);

    CntComprobante persistTemporalParaRevertirComprobante(CntComprobante cntComprobanteElegido, String usuarioLogeado) throws Exception;

    boolean verificaExistenciaMascaraNivelAndSubNivel(String mascaraGenerada);

    List<CntDetalleComprobante> obtieneDetalleComprobantePadres(CntComprobante cntComprobante);

    List<CntDetalleComprobante> obtieneDetalleComprobantePadresParaDelete(CntComprobante cntComprobante);

    boolean existenFacturasIncompletas(CntComprobante cntComprobante);

    List<CntComprobante> listaComprobantesPendientesSinDetalleComprobante() throws Exception;

    void removeComprobantePendientesSinDetalleComprobante() throws Exception;

    void removeDetalleComprobantePendienteSinFactura(CntComprobante cntComprobante) throws Exception;

    List<CntDetalleComprobante> obtieneTodosLosDetalleComprobante(CntComprobante cntComprobante);

    void persistCntComprobanteAndCntDetalleComprobanteAndFacturaTotal(CntComprobante cntComprobante) throws Exception;

    boolean verificaSiSeCreaOModifica(CntComprobante cntComprobante);

    List<CntComprobante> listaComprobantesEnUnRango(Long nroComprobanteInicio, Long nroComprobanteFinal, String periodo, String anio, String tipoComprobante) throws Exception;

//    List<Long> listaDeNumerosDeComprobantes(List<CntComprobante> listaDeComprobantes);
    
//    Integer devuelvePosicionEnLaLista(List<CntComprobante> listaDeComprobantes, Long idComprobante);
    
    List<Integer> listaDeNumerosDeComprobantes(int limite);

    List<String> listaDeNumerosDeComprobantesParaReporte(List<CntComprobante> listaDeComprobantes);

    void mergeCntComprobanteAndCntDetalleComprobanteAndFacturaAndDistribucionTotal(CntComprobante cntComprobante, String usuarioLogueado, Date fechaX) throws Exception;
    
    void copiaUnComprobanteNVeces(CntComprobante cntComprobante, int n);
    
    boolean compruebaSiElPeriodoEstaHabilitado(CntComprobante cntComprobante);
    
    boolean periodoHabilitado(Date fechaComprobante);
    
    List<CntDistribucionCentrocosto> encuentraCentroDeCostoPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante);
    
    Boolean verificaDetalleComprobantePendientesPorComprobante(CntComprobante cntComprobante);
    
    Boolean verificaDetalleComprobanteConfirmadosPorComprobante(CntComprobante cntComprobante);
    
    List<CntComprobante> listaComprobantesSegunMonto(BigDecimal monto, CntComprobante cntComprobante);
    
    List<CntComprobante> listaComprobantesPorPalabra(String palabra, CntComprobante cntComprobante);
    
    Comprobante calculaExcentoIceDescuentoIvaXcxpWS(Comprobante comprobante);
    
    CalculosTributarios calculosTributarios(CalculosTributarios calculosTributarios);
    
    CntComprobante guardaComprobanteCxP(Comprobante comprobante) throws Exception;
    
    void guardaCntDetalleComprobante(CntComprobante cntComprobante, List<CntDetalleComprobante> listCntDetalleComprobante) throws Exception;
    
    CntComprobante getObtieneCpbtePorNumeroCpbte(Long nroCpbte, String codigo);
    
}
