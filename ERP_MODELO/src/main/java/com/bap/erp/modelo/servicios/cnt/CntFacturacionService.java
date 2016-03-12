package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.entidades.cnt.CntPojoFacturacion;
import java.math.BigDecimal;
import java.util.List;

public interface CntFacturacionService extends GenericDao<CntFacturacion> {

    CntFacturacion persistCntFacturacion(CntFacturacion cntFacturacion) throws Exception;

    CntFacturacion mergeCntFacturacion(CntFacturacion cntFacturacion) throws Exception;

    void removeCntFacturacion(CntFacturacion cntFacturacion) throws Exception;

    void deleteCntFacturacion(CntFacturacion cntFacturacion) throws Exception;

    void mergeCntFacturacionModificaTotal(CntFacturacion cntFacturacion) throws Exception;

    List<CntFacturacion> listaCntFacturacion() throws Exception;

//    CntFacturacion obtieneIVA(CntFacturacion cntFacturacion, BigDecimal excento, BigDecimal ice) throws Exception;
    CntFacturacion obtieneIVA(CntFacturacion cntFacturacion, BigDecimal excento, BigDecimal ice, CntEntidad cntEntidad, BigDecimal tipoCambio) throws Exception;

    BigDecimal divideDosBigDecimal(BigDecimal dividendo, BigDecimal divisor);

//    void guardaFacturaDeCompra(CntFacturacion cntFacturacion) throws Exception;
//    CntFacturacion modificaFacturaDeCompra(CntFacturacion cntFacturacion) throws Exception;
    BigDecimal obtieneIvaIT(BigDecimal monto);

//    void guardaFacturaDeVenta(CntFacturacion cntFacturacion) throws Exception;
    CntFacturacion obtieneValoresSegundaMonedaFacturacion(CntFacturacion cntFacturacion, BigDecimal tipoDeCambio) throws Exception;

    BigDecimal convierteBolivianosDolar(BigDecimal valorBolivianos, BigDecimal tipoCambio);

    boolean activaCodigoDeControl(CntFacturacion cntFacturacion);

    boolean validaFormatoCodigoControl(String codigoControl);

    String completaFormatoCodigoControl(String codigoControl);

    void eliminaFacturasConEstadoPendientesAndModificacion(List<CntDetalleComprobante> listaCntDetalleComprobante) throws Exception;

//    void cargaProveedor(CntFacturacion cntFacturacion) throws Exception;
//    String[] validaDatosDeFacturaDeCompra(CntFacturacion cntFacturacion);
//    String[] validaCamposFacturaVenta(CntFacturacion cntFacturacion);
    boolean esDecimal(String cad);

    CntFacturacion obtieneFacturaPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante);

    CntFacturacion obtieneFacturaPorDetalleComprobanteParaDelete(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    CntFacturacion obtieneFacturaPorDetalleComprobanteParaDeleteFacturaVenta(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    CntDetalleComprobante encuentraCreditoFiscalPorCntFacturacion(CntFacturacion cntFacturacion) throws Exception;

    List<CntDetalleComprobante> encuentraHijosDetalleComprobantePorFacturacion(CntFacturacion cntFacturacion) throws Exception;

//    CntFacturacion persistCntDetalleComprobantesFacturaCompraModifica(CntFacturacion cntFacturacion) throws Exception;
//    void guardaFacturaDeVentaModifica(CntFacturacion cntFacturacion) throws Exception;
    Boolean verificaTipoDeFactura(CntDetalleComprobante cntDetalleComprobante);

    Boolean verificaExistenciaDeFacturasDeUnDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) throws Exception;

    CntDetalleComprobante encuentraDetalleComprobanteAntecesor(CntFacturacion cntFacturacion) throws Exception;

    void cambiaEstadoFacturaModificacion(CntDetalleComprobante detalleComprobante) throws Exception;

    List<CntFacturacion> listaReporteCompraVenta(String movimiento, int periodo, int anio) throws Exception;
    
    String[] validaDatosDeFacturaDeCompra(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion);

    String[] validaCamposFacturaVenta(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion);

    CntFacturacion modificaFacturaDeCompra(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion) throws Exception;

    void guardaFacturaDeVentaModifica(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion, Long idDetalleComprobante) throws Exception;

    void guardaFacturaDeCompra(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion, Long idDetalleComprobante) throws Exception;

    void guardaFacturaDeVenta(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion, Long idDetalleComprobante) throws Exception;

    Long generaNumeroDeFactura(Long idDosificacion) throws Exception;

    Boolean validaNumeroFactura(CntFacturacion cntFacturacion) throws Exception;
    List<CntFacturacion> listaFacturaVentaAnulada(String movimiento, int periodo, int anio) throws Exception;
   
    List<CntFacturacion> listaReporteFacturaVentaMigrado(String movimiento, int periodo, int anio) throws Exception;

}
