package com.bap.erp.modelo.servicios.cnt.impl;

import antlr.Utils;
import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.*;
import com.bap.erp.modelo.entidades.cnf.ParComprasYVentas;
import com.bap.erp.modelo.entidades.cnf.ParCuentasDeAjuste;
import com.bap.erp.modelo.entidades.cnf.ParSucursal;
import com.bap.erp.modelo.entidades.cnf.ParTipoFacturacion;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.enums.*;
import com.bap.erp.modelo.servicios.cnt.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;

public class CntFacturacionServiceImpl extends GenericDaoImpl<CntFacturacion> implements CntFacturacionService {

    @Autowired
    private CntProveedorService cntProveedorService;
    @Autowired
    private CntParametroAutomaticoService cntParametroAutomaticoService;
    @Autowired
    private ParParametricasService parParametricasService;
    @Autowired
    private CntEntidadesService cntEntidadesService;
    @Autowired
    private CntComprobantesService cntComprobantesService;
    @Autowired
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @Autowired
    private CntParametroAutomaticoService parametroAutomaticoService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntFacturacion persistCntFacturacion(CntFacturacion cntFacturacion) throws Exception {
        try {
            super.persist(cntFacturacion);
        } catch (Exception e) {
            throw e;
        }
        return cntFacturacion;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntFacturacion mergeCntFacturacion(CntFacturacion cntFacturacion) throws Exception {
        try {
            super.merge(cntFacturacion);
        } catch (Exception e) {
            throw e;
        }
        return cntFacturacion;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeCntFacturacion(CntFacturacion cntFacturacion) throws Exception {
        try {
            super.remove(cntFacturacion);
        } catch (Exception h) {
            throw h;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void deleteCntFacturacion(CntFacturacion cntFacturacion) throws Exception {
        try {
            super.delete(cntFacturacion);
        } catch (Exception h) {
            throw h;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void mergeCntFacturacionModificaTotal(CntFacturacion cntFacturacion) throws Exception {
        try {
            cntFacturacion.setEstado(EnumEstado.CONFIRMADO.getCodigo());
            super.merge(cntFacturacion);
        } catch (Exception h) {
            throw h;
        }
    }

    public List<CntFacturacion> listaCntFacturacion() throws Exception {
        List<CntFacturacion> list = hibernateTemplate.find(""
                + "select j "
                + "from CntFacturacion j "
                + "where j.fechaBaja is null ");
        if (!list.isEmpty()) {
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    public CntFacturacion obtieneIVA(CntFacturacion cntFacturacion, BigDecimal excento, BigDecimal ice, CntEntidad cntEntidad, BigDecimal tipoCambio) throws Exception {
        System.out.println("el OBTIENE IVA-------------------");
        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion);
        BigDecimal montoParcial = cntFacturacion.getMonto();
        CntParametroAutomatico cntParametroAutomatico;
        if (cntEntidad == null) {
            cntParametroAutomatico = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntDetalleComprobante.getCntEntidad());
//            cntParametroAutomatico = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntFacturacion.getCntDetalleComprobante().getCntEntidad());
        } else {
            cntParametroAutomatico = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        }
        BigDecimal iva = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IVA")).getValor()).multiply(new BigDecimal("0.01"));
        iva = iva.setScale(5, BigDecimal.ROUND_HALF_UP);
        if (!(cntFacturacion.getExcento().compareTo(excento) == 0)) {
            if ((excento.add(ice)).compareTo(cntFacturacion.getMonto()) == 1) {
                cntFacturacion.setExcento(cntFacturacion.getMonto().subtract(ice).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                cntFacturacion.setExcento(excento.setScale(5, BigDecimal.ROUND_HALF_UP));
            }
        } else if (!(cntFacturacion.getIce().compareTo(ice) == 0)) {
            if ((ice.add(excento)).compareTo(cntFacturacion.getMonto()) == 1) {
                cntFacturacion.setIce(cntFacturacion.getMonto().subtract(excento).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                cntFacturacion.setIce(ice.setScale(5, BigDecimal.ROUND_HALF_UP));
            }
        }
        montoParcial = montoParcial.subtract(cntFacturacion.getExcento());
        montoParcial = montoParcial.subtract(cntFacturacion.getIce());
        if (cntParametroAutomatico.getFacturaCompra().equals(EnumFacturaCompraCombustible.CON_COMBUSTIBLE.getCodigo())) {
            System.out.println("por con combustible::::");
            montoParcial = (montoParcial.multiply(new BigDecimal("0.7").setScale(2, BigDecimal.ROUND_HALF_UP)));
        }
        System.out.println("pasa por el IVA:::");
        cntFacturacion.setIva((montoParcial.subtract(montoParcial.multiply(new BigDecimal("1").subtract(iva))).setScale(2, BigDecimal.ROUND_HALF_UP)));
        if (cntEntidad == null) {
            obtieneValoresSegundaMonedaFacturacion(cntFacturacion, null);
        } else {
            obtieneValoresSegundaMonedaFacturacion(cntFacturacion, tipoCambio);
        }
        return cntFacturacion;
    }
//    public CntFacturacion obtieneIVA(CntFacturacion cntFacturacion, BigDecimal excento, BigDecimal ice) throws Exception {
//        BigDecimal montoParcial = cntFacturacion.getMonto();
//        CntParametroAutomatico cntParametroAutomatico = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntFacturacion.getCntDetalleComprobante().getCntEntidad());
//        BigDecimal iva = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IVA")).getValor()).multiply(new BigDecimal("0.01"));
//        iva = iva.setScale(5, BigDecimal.ROUND_HALF_UP);
//        if (!(cntFacturacion.getExcento().compareTo(excento) == 0)) {
//            if ((excento.add(ice)).compareTo(cntFacturacion.getMonto()) == 1) {
//                cntFacturacion.setExcento(cntFacturacion.getMonto().subtract(ice).setScale(2, BigDecimal.ROUND_HALF_UP));
//            } else {
//                cntFacturacion.setExcento(excento.setScale(5, BigDecimal.ROUND_HALF_UP));
//            }
//        } else if (!(cntFacturacion.getIce().compareTo(ice) == 0)) {
//            if ((ice.add(excento)).compareTo(cntFacturacion.getMonto()) == 1) {
//                cntFacturacion.setIce(cntFacturacion.getMonto().subtract(excento).setScale(2, BigDecimal.ROUND_HALF_UP));
//            } else {
//                cntFacturacion.setIce(ice.setScale(5, BigDecimal.ROUND_HALF_UP));
//            }
//        }
//        montoParcial = montoParcial.subtract(cntFacturacion.getExcento());
//        montoParcial = montoParcial.subtract(cntFacturacion.getIce());
//        if (cntParametroAutomatico.getFacturaCompra().equals(EnumFacturaCompraCombustible.CON_COMBUSTIBLE.getCodigo())) {
//            montoParcial = (montoParcial.multiply(new BigDecimal("0.7").setScale(2, BigDecimal.ROUND_HALF_UP)));
//        }
//        cntFacturacion.setIva((montoParcial.subtract(montoParcial.multiply(new BigDecimal("1").subtract(iva))).setScale(2, BigDecimal.ROUND_HALF_UP)));
//        obtieneValoresSegundaMonedaFacturacion(cntFacturacion);
//        return cntFacturacion;
//    }

    public BigDecimal divideDosBigDecimal(BigDecimal dividendo, BigDecimal divisor) {
        if (divisor.compareTo(new BigDecimal(BigInteger.ZERO)) == 1) {
            Float resultado = dividendo.floatValue() / divisor.floatValue();
            return new BigDecimal(resultado.toString());
        }
        return new BigDecimal(BigInteger.ZERO);
    }

//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
//    public void guardaFacturaDeCompra(CntFacturacion cntFacturacion) throws Exception {
//        cntFacturacion.setMovimiento(EnumMovimientoFactura.FACTURA_COMPRA.getCodigo());
//        if (cntFacturacion.getCreditoFiscalTransitorio().equals("N")) {
//            cargaProveedor(cntFacturacion);
//        } else {
//            ParTipoFacturacion parTipoFacturacion = (ParTipoFacturacion) parParametricasService.find(ParTipoFacturacion.class, "MINT");
//            cntFacturacion.setRazonSocial("");
//            cntFacturacion.setNit(0L);
//            cntFacturacion.setNroAutorizacion("");
//            cntFacturacion.setParTipoFacturacion(parTipoFacturacion);
//        }
//        cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
//        if (cntFacturacion.getIdFacturacion() != null) {
//            cntFacturacion.setIdAntecesor(cntFacturacion.getIdFacturacion());
//            CntFacturacion cntFacturacionOriginal = find(CntFacturacion.class, cntFacturacion.getIdFacturacion());
//            CntDetalleComprobante cntDetalleComprobanteOriginal = cntFacturacionOriginal.getCntDetalleComprobante();
//            cntDetalleComprobanteOriginal.setFechaModificacion(cntFacturacion.getFechaAlta());
//            cntDetalleComprobanteOriginal.setUsuarioModificacion(cntFacturacion.getUsuarioAlta());
//            cntDetalleComprobanteService.removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteOriginal);
//            cntFacturacion.setIdFacturacion(null);
//            cntFacturacion = persistCntFacturacion(cntFacturacion);
//            if (cntFacturacionOriginal.getIdAntecesor() != null && cntFacturacionOriginal.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
//                cntFacturacion.setIdAntecesor(cntFacturacionOriginal.getIdAntecesor());
//                mergeCntFacturacion(cntFacturacion);
//                deleteCntFacturacion(cntFacturacionOriginal);
//            } else {
//                if (cntFacturacionOriginal.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
//                    deleteCntFacturacion(cntFacturacionOriginal);
//                } else {
//                    removeCntFacturacion(cntFacturacionOriginal);
//                }
//            }
//        } else {
//            cntFacturacion = persistCntFacturacion(cntFacturacion);
//        }
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
//        cntDetalleComprobante.setDebe(cntFacturacion.getMonto().subtract(cntFacturacion.getIva()));
//        cntDetalleComprobante.setDebeDolar(convierteBolivianosDolar(cntDetalleComprobante.getDebe(), cntDetalleComprobante.getCntComprobante().getTipoCambio()));
//        cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo());
//        cntDetalleComprobante.setFechaAlta(cntFacturacion.getFechaAlta());
//        cntDetalleComprobante.setUsuarioAlta(cntFacturacion.getUsuarioAlta());
//        try {
//            if (cntDetalleComprobante.getIdAntecesor() != null) {
//                CntDetalleComprobante cntDetalleComprobanteAnterior = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor());
//                if (cntDetalleComprobanteAnterior.getIdAntecesor() != null && cntDetalleComprobanteAnterior.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
//                    cntDetalleComprobante.setIdAntecesor(cntDetalleComprobanteAnterior.getIdAntecesor());
//                    cntDetalleComprobante = cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
//                    cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
//                } else {
//                    if (cntDetalleComprobanteAnterior.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
//                        cntDetalleComprobante.setIdAntecesor(null);
//                        cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
//                    } else {
//                        cntDetalleComprobanteService.removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
//                    }
//                }
//            } else {
//                cntDetalleComprobante = cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
//            }
//        } catch (Exception ex) {
//            throw ex;
//        }
//        CntDetalleComprobante detalleComprobanteCreditoFiscal;
//        detalleComprobanteCreditoFiscal = (CntDetalleComprobante) cntDetalleComprobante.clone();
//        detalleComprobanteCreditoFiscal.setTransaccionRealizada(null);
//        detalleComprobanteCreditoFiscal.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
//        detalleComprobanteCreditoFiscal.setPosicion(null);
//        detalleComprobanteCreditoFiscal.setIdDetalleComprobante(null);
//        detalleComprobanteCreditoFiscal.setIdAntecesor(null);
//        if (cntFacturacion.getCreditoFiscalTransitorio().equals("N")) {            
//            Long idDebitoFiscal = Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CCRF")).getValor()); //Parametrica de Credito Fiscal
////            Long idDebitoFiscal = Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CDBF")).getValor()); //Parametrica de Debito Fiscal
//            detalleComprobanteCreditoFiscal.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, idDebitoFiscal));
//        } else {
//            Long idCreditoFiscalTransitorio = Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CCFT")).getValor());
//            detalleComprobanteCreditoFiscal.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, idCreditoFiscalTransitorio));
//        }
//        detalleComprobanteCreditoFiscal.setDebe(cntFacturacion.getIva());
//        detalleComprobanteCreditoFiscal.setDebeDolar(cntFacturacion.getIvaSegMoneda());
//        cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobanteCreditoFiscal);
//        CntComprobante comprobante = cntComprobantesService.find(CntComprobante.class, detalleComprobanteCreditoFiscal.getCntComprobante().getIdComprobante());
//        comprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
//        cntComprobantesService.merge(comprobante);
//    }
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntFacturacion modificaFacturaDeCompra(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion) throws Exception {
        CntDetalleComprobante detalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion).clone();
//        CntDetalleComprobante detalleComprobante = (CntDetalleComprobante) cntFacturacion.getCntDetalleComprobante().clone();
        detalleComprobante.setDebe(cntFacturacion.getMonto().subtract(cntFacturacion.getIva()));
        detalleComprobante.setDebeDolar(convierteBolivianosDolar(detalleComprobante.getDebe(), detalleComprobante.getCntComprobante().getTipoCambio()));
        detalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo());
        detalleComprobante.setIdDetalleComprobante(null);
//        detalleComprobante.setIdAntecesor(cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
        detalleComprobante.setIdAntecesor(detalleComprobante.getIdDetalleComprobante());
        detalleComprobante.setUsuarioBaja(null);
        detalleComprobante.setFechaBaja(null);
        detalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
        detalleComprobante.setUsuarioAlta(cntFacturacion.getUsuarioModificacion());
        detalleComprobante.setFechaAlta(cntFacturacion.getFechaModificacion());
//        CntParametroAutomatico pa = parametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntFacturacion.getCntDetalleComprobante().getCntEntidad());
        CntParametroAutomatico pa = parametroAutomaticoService.obtieneObjetoDeParametroAutomatico(detalleComprobante.getCntEntidad());
        detalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo());
        try {
            detalleComprobante = cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante);
        } catch (Exception e) {
        }
        CntDetalleComprobante detalleComprobanteCreditoFiscal;
        detalleComprobanteCreditoFiscal = (CntDetalleComprobante) detalleComprobante.clone();
        detalleComprobanteCreditoFiscal.setTransaccionRealizada(null);
        detalleComprobanteCreditoFiscal.setIdPadre(detalleComprobante.getIdDetalleComprobante());
        detalleComprobanteCreditoFiscal.setPosicion(null);
        detalleComprobanteCreditoFiscal.setIdDetalleComprobante(null);
        detalleComprobanteCreditoFiscal.setUsuarioAlta(cntFacturacion.getUsuarioModificacion());
        detalleComprobanteCreditoFiscal.setFechaAlta(cntFacturacion.getFechaModificacion());
        if (cntFacturacion.getCreditoFiscalTransitorio().equals("N")) {
            Long idDebitoFiscal = Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CDBF")).getValor());
            detalleComprobanteCreditoFiscal.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, idDebitoFiscal));
        } else {
            Long idCreditoFiscalTransitorio = Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CCFT")).getValor());
            detalleComprobanteCreditoFiscal.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, idCreditoFiscalTransitorio));
        }
        detalleComprobanteCreditoFiscal.setDebe(cntFacturacion.getIva());
        detalleComprobanteCreditoFiscal.setDebeDolar(cntFacturacion.getIvaSegMoneda());
        cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobanteCreditoFiscal);
        if (cntFacturacion.getCreditoFiscalTransitorio().equals("N")) {
            cntFacturacion.setIdProveedorCliente(cargaProveedor(cntFacturacion, nit, razonSocial, numeroAutorizacion).getIdProveedor());
        }
        cntFacturacion.setMovimiento(EnumMovimientoFactura.FACTURA_COMPRA.getCodigo());
        if (cntFacturacion.getCreditoFiscalTransitorio().equals("S")) {
            ParTipoFacturacion parTipoFacturacion = (ParTipoFacturacion) parParametricasService.find(ParTipoFacturacion.class, "MINT");
            cntFacturacion.setIdProveedorCliente(null);
            cntFacturacion.setNroAutorizacion("");
            cntFacturacion.setParTipoFacturacion(parTipoFacturacion);
        }
        cntFacturacion.setIdAntecesor(cntFacturacion.getIdFacturacion());
        cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntFacturacion.setUsuarioAlta(cntFacturacion.getUsuarioModificacion());
        cntFacturacion.setFechaAlta(cntFacturacion.getFechaModificacion());
        cntFacturacion = persistCntFacturacion(cntFacturacion);
//        cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobanteCreditoFiscal);
        return cntFacturacion;
    }

    public BigDecimal obtieneIvaIT(BigDecimal monto) {
        BigDecimal iva = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IVA")).getValor());
        iva = iva.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal porcentaje1 = new BigDecimal("1");
        BigDecimal porcentaje = new BigDecimal("100");
        return (monto.subtract(monto.multiply(porcentaje1.subtract(iva.divide(porcentaje))))).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public CntFacturacion obtieneValoresSegundaMonedaFacturacion(CntFacturacion cntFacturacion, BigDecimal tipoDeCambio) throws Exception {
        try {
            if (tipoDeCambio == null) {
                CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion);
//            CntDetalleComprobante cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
                tipoDeCambio = cntDetalleComprobante.getCntComprobante().getTipoCambio();
            }
            cntFacturacion.setMontoSegMoneda((cntFacturacion.getMonto().divide(tipoDeCambio, 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
            cntFacturacion.setIceSegMoneda((cntFacturacion.getIce().divide(tipoDeCambio, 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
            cntFacturacion.setExcentoSegMoneda((cntFacturacion.getExcento().divide(tipoDeCambio, 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
            cntFacturacion.setIvaSegMoneda((cntFacturacion.getIva().divide(tipoDeCambio, 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
            return cntFacturacion;
        } catch (Exception e) {
            throw e;
        }
    }

    public BigDecimal convierteBolivianosDolar(BigDecimal valorBolivianos, BigDecimal tipoCambio) {
        return ((valorBolivianos.divide(tipoCambio, 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

////    guardado de factura de venta====================================================================================================
//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
//    public void guardaFacturaDeVenta(CntFacturacion cntFacturacion) throws Exception {
//        int sw = 0;
//        BigDecimal resultadoMontoIVA = new BigDecimal(BigInteger.ZERO);
//        BigDecimal resultadoMontoIVADolar = new BigDecimal(BigInteger.ZERO);
//        BigDecimal resultadoMontoIT = new BigDecimal(BigInteger.ZERO);
//        BigDecimal resultadoMontoITDolar = new BigDecimal(BigInteger.ZERO);
//        BigDecimal resultadoMonto = new BigDecimal(BigInteger.ZERO);
//        BigDecimal resultadoMontoDolar = new BigDecimal(BigInteger.ZERO);
//        BigDecimal monto = new BigDecimal(BigInteger.ZERO);
//        BigDecimal porcentaje1 = new BigDecimal("1");
//        BigDecimal porcentaje = new BigDecimal("100");
//        CntDetalleComprobante cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
//        BigDecimal iva = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IVA")).getValor());
//        iva = iva.setScale(2, BigDecimal.ROUND_HALF_UP);
//        BigDecimal it = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IT")).getValor());
//        it = it.setScale(2, BigDecimal.ROUND_HALF_UP);
//        if ( != null && cntFacturacion.getNit() != null) {
//            if (cntProveedorService.generaProveedorPorRazonSocial(cntFacturacion.getRazonSocial()) == null) {
//                CntProveedor cntProveedor = new CntProveedor();
//                cntProveedor.setRazonSocial(cntFacturacion.getRazonSocial());
//                cntProveedor.setNit(cntFacturacion.getNit());
//                cntProveedor.setUsuarioAlta(cntFacturacion.getLoginUsuario());
//                cntProveedor.setFechaAlta(new Date());
//                cntProveedor.setAutorizacion(cntFacturacion.getNroAutorizacion());
//                cntProveedorService.persistCntProveedor(cntProveedor);
//            }
//        }
//
//        if (cntFacturacion.getNroAutorizacion() != null) {
//            if (parParametricasService.encuentraParSucursalPorNumeroAutorizacion(cntFacturacion.getNroAutorizacion()) == false) {
//                ParSucursal parSucursal = parParametricasService.encuentraParSucursal(cntFacturacion.getSucursal());
//                ParValor parValor = (ParValor) parParametricasService.findParValor(parSucursal.getCodigo());
//                parValor.setValor(cntFacturacion.getNroAutorizacion());
//                parValor.setFechaModificacion(new Date());
//                parValor.setUsuarioModificacion(cntFacturacion.getLoginUsuario());
//                try {
//                    parParametricasService.mergeParValor(parValor);
//                } catch (Exception ex) {
//                    throw ex;
//                }
//            }
//        }
//        cntFacturacion.setMovimiento(EnumMovimientoFactura.FACTURA_VENTA.getCodigo());
//        cntFacturacion.setCntDetalleComprobante(cntDetalleComprobante);
//        cntFacturacion.setLoginUsuario(cntFacturacion.getLoginUsuario());
//        cntFacturacion.setFechaAlta(new Date());
//        cntFacturacion.setUsuarioAlta(cntFacturacion.getLoginUsuario());
//        cntFacturacion.setParParametrosAutorizacion(null);
//        cntFacturacion.setParTipoFacturacion(null);
//        if (sw == 1) {
//            monto = cntFacturacion.getMonto();
//        } else {
//            if (cntDetalleComprobante.getDebe() != null) {
//                monto = cntDetalleComprobante.getDebe();
//            }
//            if (cntDetalleComprobante.getHaber() != null) {
//                monto = cntDetalleComprobante.getHaber();
//            }
//        }
//        cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
//        cntFacturacion = persistCntFacturacion(cntFacturacion);
//        monto = monto.subtract(cntFacturacion.getExcento().add(cntFacturacion.getIce()));
//        resultadoMonto = (monto.multiply(porcentaje1.subtract(iva.divide(porcentaje)))).setScale(2, BigDecimal.ROUND_HALF_UP);
//        resultadoMontoIVA = (monto.subtract(monto.multiply(porcentaje1.subtract(iva.divide(porcentaje))))).setScale(2, BigDecimal.ROUND_HALF_UP);
//        resultadoMontoIT = (monto.subtract(monto.multiply(porcentaje1.subtract(it.divide(porcentaje))))).setScale(2, BigDecimal.ROUND_HALF_UP);
//        resultadoMontoDolar = (resultadoMonto.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);
//        resultadoMontoIVADolar = (resultadoMontoIVA.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);
//        resultadoMontoITDolar = (resultadoMontoIT.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);
//        cntDetalleComprobante.setHaber((monto.multiply(porcentaje1.subtract(iva.divide(porcentaje)))).setScale(2, BigDecimal.ROUND_HALF_UP));
//        cntDetalleComprobante.setHaberDolar((resultadoMonto.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
//        cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo());
//        try {
//            if (cntDetalleComprobante.getIdAntecesor() != null) {
//                CntDetalleComprobante cntDetalleComprobanteAnterior = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor());
//                if (cntDetalleComprobanteAnterior.getIdAntecesor() != null && cntDetalleComprobanteAnterior.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
//                    cntDetalleComprobante.setIdAntecesor(cntDetalleComprobanteAnterior.getIdAntecesor());
//                    cntDetalleComprobante = cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
//                    cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
//                } else {
//                    if (cntDetalleComprobanteAnterior.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
//                        cntDetalleComprobante.setIdAntecesor(null);
//                        cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
//                    } else {
//                        cntDetalleComprobanteService.removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
//                    }
//                }
//            } else {
//                cntDetalleComprobante = cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
//            }
//        } catch (Exception ex) {
//            throw ex;
//        }
//        cntDetalleComprobante.setHaber((monto.multiply(porcentaje1.subtract(iva.divide(porcentaje)))).setScale(2, BigDecimal.ROUND_HALF_UP));
//        cntDetalleComprobante.setHaberDolar((resultadoMonto.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
//        List<CntDetalleComprobante> lista = encuentraHijosDetalleComprobantePorFacturacion(cntFacturacion);
//        CntDetalleComprobante detalleComprobante;
//        detalleComprobante = (CntDetalleComprobante) cntDetalleComprobante.clone();
//        detalleComprobante.setIdDetalleComprobante(null);
//        detalleComprobante.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CDBF")).getValor())));
//        detalleComprobante.setDebe(null);
//        detalleComprobante.setHaber(resultadoMontoIVA);
//        detalleComprobante.setFechaAlta(new Date());
//        detalleComprobante.setUsuarioAlta(cntFacturacion.getLoginUsuario());
//        detalleComprobante.setLoginUsuario(cntFacturacion.getLoginUsuario());
//        detalleComprobante.setDebeDolar(null);
//        detalleComprobante.setHaberDolar((resultadoMontoIVA.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
//        detalleComprobante.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
//        detalleComprobante.setPosicion(null);
//        detalleComprobante.setIdAntecesor(null);
//        detalleComprobante.setTransaccionRealizada(null);
//        try {
//            cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante);
//        } catch (Exception ex) {
//            throw ex;
//        }
//        CntDetalleComprobante detalleComprobante1;
//        detalleComprobante1 = (CntDetalleComprobante) cntDetalleComprobante.clone();
//        detalleComprobante1.setIdDetalleComprobante(null);
//        detalleComprobante1.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CITP")).getValor())));
//        detalleComprobante1.setDebe(resultadoMontoIT);
//        detalleComprobante1.setHaber(null);
//        detalleComprobante1.setFechaAlta(new Date());
//        detalleComprobante1.setUsuarioAlta(cntFacturacion.getLoginUsuario());
//        detalleComprobante1.setLoginUsuario(cntFacturacion.getLoginUsuario());
//        detalleComprobante1.setDebeDolar((resultadoMontoIT.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
//        detalleComprobante1.setHaberDolar(null);
//        detalleComprobante1.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
//        detalleComprobante1.setPosicion(null);
//        detalleComprobante1.setIdAntecesor(null);
//        detalleComprobante1.setTransaccionRealizada(null);
//        try {
//            cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante1);
//        } catch (Exception ex) {
//            throw ex;
//        }
//        CntDetalleComprobante detalleComprobante2;
//        detalleComprobante2 = (CntDetalleComprobante) cntDetalleComprobante.clone();
//        detalleComprobante2.setIdDetalleComprobante(null);
//        detalleComprobante2.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CITG")).getValor())));
//        detalleComprobante2.setDebe(null);
//        detalleComprobante2.setHaber(resultadoMontoIT);
//        detalleComprobante2.setFechaAlta(new Date());
//        detalleComprobante2.setUsuarioAlta(cntFacturacion.getLoginUsuario());
//        detalleComprobante2.setLoginUsuario(cntFacturacion.getLoginUsuario());
//        detalleComprobante2.setDebeDolar(null);
//        detalleComprobante2.setHaberDolar((resultadoMontoIT.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
//        detalleComprobante2.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
//        detalleComprobante2.setPosicion(null);
//        detalleComprobante2.setIdAntecesor(null);
//        detalleComprobante2.setTransaccionRealizada(null);
//        try {
//            cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante2);
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//  fin de guardado de factura de venta============================================================================================
    public boolean activaCodigoDeControl(CntFacturacion cntFacturacion) {
        if (cntFacturacion.getNroAutorizacion() != null) {
            if (cntFacturacion.getNroAutorizacion().length() > 3) {
                if (cntFacturacion.getNroAutorizacion().substring(3, 4).equals("4") || cntFacturacion.getNroAutorizacion().substring(3, 4).equals("6")) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validaFormatoCodigoControl(String codigoControl) {
        String[] vecCodigoControl;
        if (!codigoControl.equals("")) {
            vecCodigoControl = codigoControl.split("-");
            for (int i = 0; i < vecCodigoControl.length; i++) {
                if (vecCodigoControl[i].length() != 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public String completaFormatoCodigoControl(String codigoControl) {
        if (!codigoControl.equals("")) {
            if (validaFormatoCodigoControl(codigoControl)) {
                codigoControl = codigoControl + "-";
            }
        }
        return codigoControl;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void eliminaFacturasConEstadoPendientesAndModificacion(List<CntDetalleComprobante> listaCntDetalleComprobante) throws Exception {
        try {
            for (CntDetalleComprobante cntDetalleComprobante : listaCntDetalleComprobante) {
                if (!cntDetalleComprobante.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo())) {
                    if (cntDetalleComprobante.getIdPadre() == null) {
                        if (cntDetalleComprobanteService.buscaFacturacionPorDetalleComprobantePadres(cntDetalleComprobante) != null) {
                            deleteCntFacturacion(cntDetalleComprobanteService.buscaFacturacionPorDetalleComprobantePadres(cntDetalleComprobante));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean existeNit(Long nit) {
        List<CntProveedor> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntProveedor j "
                + "where j.fechaBaja is null "
                + "and j.nit = '" + nit + "' ");
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

//    public void cargaProveedor(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion) throws Exception {
//        if (cntFacturacion.getParParametrosAutorizacion().getCodigo().equals(EnumParametrosDeAutorizacion.FACTURA.getCodigo()) && nit != null) {
//            if (existeNit(nit)) {
//                CntProveedor cntProveedor = cntProveedorService.generaProveedorPorNit(nit);
//                if (!cntProveedor.getAutorizacion().equals(cntFacturacion.getNroAutorizacion())) {
//                    cntProveedor.setAutorizacion(numeroAutorizacion);
//                    cntProveedorService.mergeCntProveedor(cntProveedor);
//                }
//            } else {
//                CntProveedor cntProveedor = new CntProveedor();
//                cntProveedor.setRazonSocial(razonSocial);
//                cntProveedor.setNit(nit);
//                cntProveedor.setAutorizacion(numeroAutorizacion);
//                cntProveedor.setUsuarioAlta(cntFacturacion.getUsuarioAlta());
//                cntProveedor.setFechaAlta(cntFacturacion.getFechaAlta());
//                cntProveedorService.persistCntProveedor(cntProveedor);
//            }
//        }
//    }
    public CntProveedor cargaProveedor(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion) throws Exception {
        CntProveedor cntProveedor = new CntProveedor();
        if (nit != null) {
            System.out.println("primera");
            if (existeNit(nit)) {
                System.out.println("merge");
                cntProveedor = cntProveedorService.generaProveedorPorNit(nit);
                if (!cntProveedor.getAutorizacion().equals(cntFacturacion.getNroAutorizacion())) {
                    cntProveedor.setAutorizacion(numeroAutorizacion);
                    cntProveedorService.mergeCntProveedor(cntProveedor);
//                    return cntProveedor;
                }
            } else {
                System.out.println("persist");
                cntProveedor.setRazonSocial(razonSocial);
                cntProveedor.setNit(nit);
                cntProveedor.setAutorizacion(numeroAutorizacion);
                cntProveedor.setUsuarioAlta(cntFacturacion.getUsuarioAlta());
                cntProveedor.setFechaAlta(cntFacturacion.getFechaAlta());
                cntProveedorService.persistCntProveedor(cntProveedor);
//                return cntProveedor;
            }
            return cntProveedor;
        }
        return null;
    }

//    public String[] validaDatosDeFacturaDeCompra(CntFacturacion cntFacturacion) {
//        String[] colores = new String[6];
//        String mensaje = "";
//        if (cntFacturacion.getNit() == null || cntFacturacion.getNit() < 0) {
//            colores[0] = "red";
//            mensaje = mensaje + "Nit, ";
//        } else {
//            colores[0] = null;
//        }
//        if (cntFacturacion.getRazonSocial().equals("") || cntFacturacion.getRazonSocial() == null) {
//            colores[1] = "red";
//            mensaje = mensaje + "Razon Social, ";
//        } else {
//            colores[1] = null;
//        }
//        if (cntFacturacion.getNroAutorizacion().equals("") || cntFacturacion.getNroAutorizacion() == null) {
//            colores[2] = "red";
//            mensaje = mensaje + "Autorización, ";
//        } else {
//            colores[2] = null;
//        }
//        if (!activaCodigoDeControl(cntFacturacion)) {
//            if (cntFacturacion.getCodigoControl().equals("") || cntFacturacion.getRazonSocial() == null) {
//                colores[3] = "red";
//                mensaje = mensaje + "Código de Control, ";
//            } else {
//                colores[3] = null;
//            }
//        }
//        if (cntFacturacion.getNroInicial() == null || cntFacturacion.getNroInicial() < 0) {
//            colores[4] = "red";
//            mensaje = mensaje + "Nª de Factura, ";
//        } else {
//            colores[4] = null;
//        }
//        colores[5] = mensaje;
//        return colores;
//    }
//    public String[] validaCamposFacturaVenta(CntFacturacion cntFacturacion) {
//        String[] colores = new String[4];
//        String mensaje = "";
//
//        if (cntFacturacion.getNit() == null || cntFacturacion.getNit() < 0 || cntFacturacion.getNit().toString().equals("")) {
//            colores[0] = "red";
//            mensaje = mensaje + "Nit, ";
//        } else {
//            colores[0] = null;
//        }
//        if (cntFacturacion.getRazonSocial().equals("") || cntFacturacion.getNit() == null) {
//            colores[1] = "red";
//            mensaje = mensaje + "Razon Social, ";
//        } else {
//            colores[1] = null;
//        }
//        if (cntFacturacion.getNroAutorizacion().equals("") || cntFacturacion.getNit() == null) {
//            colores[2] = "red";
//            mensaje = mensaje + "Numero de Autorizacion, ";
//        } else {
//            colores[2] = null;
//        }
//        colores[3] = mensaje;
//        return colores;
//    }
    public boolean esDecimal(String cad) {
        boolean hayPunto = false;
        StringBuffer parteEntera = new StringBuffer();
        StringBuffer parteDecimal = new StringBuffer();
        int i = 0, posicionDelPunto;

        for (i = 0; i < cad.length(); i++) {
            if (cad.charAt(i) == '.') {
                hayPunto = true;
            }
        }
        if (hayPunto) {
            posicionDelPunto = cad.indexOf('.');
        } else {
            return false;
        }
        if (posicionDelPunto == cad.length() - 1 || posicionDelPunto == 0) {
            return false;
        }

        for (i = 0; i < posicionDelPunto; i++) {
            parteEntera.append(cad.charAt(i));
        }
        for (i = 0; i < parteEntera.length(); i++) {
            if (!Character.isDigit(parteEntera.charAt(i))) {
                return false;
            }
        }

        for (i = posicionDelPunto + 1; i < cad.length(); i++) {
            parteDecimal.append(cad.charAt(i));
        }
        for (i = 0; i < parteDecimal.length(); i++) {
            if (!Character.isDigit(parteDecimal.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public CntFacturacion obtieneFacturaPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
//        List<CntFacturacion> lista = hibernateTemplate.find(""
//                + "select h "
//                + "from CntFacturacion h "
//                + "where h.cntDetalleComprobante.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante());
//        if (!lista.isEmpty()) {
//            return lista.get(0);
//        }
//        modificado para CPP
        if (cntDetalleComprobante.getCntFacturacion() != null) {
            return cntDetalleComprobante.getCntFacturacion();
        }
        return null;
    }

    public CntFacturacion obtieneFacturaPorDetalleComprobanteParaDelete(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
//            List<CntFacturacion> lista = hibernateTemplate.find(""
//                    + "select h from "
//                    + "CntFacturacion h "
//                    + "where h.cntDetalleComprobante.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante());
//            if (!lista.isEmpty()) {
//                return lista.get(0);
//            }
            //        modificado para CPP
            if (cntDetalleComprobante.getCntFacturacion() != null) {
                return cntDetalleComprobante.getCntFacturacion();
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public CntFacturacion obtieneFacturaPorDetalleComprobanteParaDeleteFacturaVenta(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
            List<CntFacturacion> lista = hibernateTemplate.find(""
                    + "select h from "
                    + "CntFacturacion h "
                    + "where h.cntDetalleComprobante.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante() + "ORDER BY idFacturacion DESC");
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public CntDetalleComprobante encuentraCreditoFiscalPorCntFacturacion(CntFacturacion cntFacturacion) throws Exception {
        try {
            CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion);
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.fechaBaja is null "
                    + "and j.idPadre = " + cntDetalleComprobante.getIdDetalleComprobante() + " ");
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    public List<CntDetalleComprobante> encuentraHijosDetalleComprobantePorFacturacion(CntFacturacion cntFacturacion) throws Exception {
        try {
            CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion);
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.fechaBaja is null "
                    + "and j.idPadre = " + cntDetalleComprobante.getIdDetalleComprobante() + " "
                    + "and j.estado = '" + EnumEstado.PENDIENTE.getCodigo() + "' ORDER BY idDetalleComprobante ASC");
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
//    public CntFacturacion persistCntDetalleComprobantesFacturaCompraModifica(CntFacturacion cntFacturacion) throws Exception {
//        CntFacturacion facturacion = (CntFacturacion) cntFacturacion.clone();
//        try {
//            facturacion = modificaFacturaDeCompra(facturacion);
//            if (facturacion.getCntDetalleComprobante().getIdAntecesor() != null && facturacion.getCntDetalleComprobante().getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
//                facturacion.getCntDetalleComprobante().setIdAntecesor(cntFacturacion.getCntDetalleComprobante().getIdAntecesor());
//                cntDetalleComprobanteService.mergeCntDetalleComprobantes(facturacion.getCntDetalleComprobante());
//                cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntFacturacion.getCntDetalleComprobante());
//            } else {
//                cntFacturacion.getCntDetalleComprobante().setGlosa(cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante()).getGlosa());
//                cntDetalleComprobanteService.removeCntDetalleComprobantesCntFacturacion(cntFacturacion.getCntDetalleComprobante());
//            }
//        } catch (Exception h) {
//            throw h;
//        }
//        return facturacion;
//    }
//    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
//    public void guardaFacturaDeVentaModifica(CntFacturacion cntFacturacion) throws Exception {
//        CntDetalleComprobante cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
//        CntDetalleComprobante cntDetalleComprobanteAntecesor = encuentraDetalleComprobanteAntecesor(cntFacturacion);
//        cntDetalleComprobanteAntecesor.setFechaModificacion(cntFacturacion.getFechaModificacion());
//        cntDetalleComprobanteAntecesor.setUsuarioModificacion(cntFacturacion.getUsuarioModificacion());
//        cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
//        CntFacturacion cntFacturacionModificar = (CntFacturacion) cntFacturacion.clone();
//        cntFacturacionModificar.setFechaModificacion(null);
//        cntFacturacionModificar.setUsuarioModificacion(null);
//        cntFacturacionModificar.setIdFacturacion(null);
//        cntFacturacionModificar.setCntDetalleComprobante(cntDetalleComprobante);
//        cntFacturacionModificar.setEstado(EnumEstado.PENDIENTE.getCodigo());
//        guardaFacturaDeVenta(cntFacturacionModificar);
//        if (cntFacturacion.getIdAntecesor() != null && cntFacturacion.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
//            cntFacturacionModificar.setIdAntecesor(cntFacturacion.getIdAntecesor());
//            mergeCntFacturacion(cntFacturacionModificar);
//        } else {
//            cntFacturacionModificar.setIdAntecesor(cntFacturacion.getIdFacturacion());
//            mergeCntFacturacion(cntFacturacionModificar);
//        }
//        CntComprobante comprobante = cntComprobantesService.find(CntComprobante.class, cntDetalleComprobante.getCntComprobante().getIdComprobante());
//        comprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
//        cntComprobantesService.mergeCntComprobantes(comprobante);
//    }
    public Boolean verificaTipoDeFactura(CntDetalleComprobante cntDetalleComprobante) {
        if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.CREDITO_FISCAL_NO_DEDUCIBLE.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo())) {
            return true;
        }
        return false;
    }

    public Boolean verificaExistenciaDeFacturasDeUnDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
//            List<CntFacturacion> lista = hibernateTemplate.find(""
//                    + "select h from "
//                    + "CntFacturacion h "
//                    + "where h.cntDetalleComprobante.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante());
            List<CntFacturacion> lista = hibernateTemplate.find(""
                    + "select j.cntFacturacion from "
                    + "CntDetalleComprobante j "
                    + "where j.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante());
            if (!lista.isEmpty()) {
                return true;
            }
        } catch (Exception h) {
            throw h;
        }
        return false;
    }

    public CntDetalleComprobante encuentraDetalleComprobanteAntecesor(CntFacturacion cntFacturacion) throws Exception {
        try {
            CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion);
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.fechaBaja is null "
                    + "and j.idDetalleComprobante = " + cntDetalleComprobante.getIdAntecesor() + " ");
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (Exception c) {
            throw c;
        }
        return null;
    }
    //aumentado para confirmar factura en el modificado

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void cambiaEstadoFacturaModificacion(CntDetalleComprobante detalleComprobante) throws Exception {
        try {
//            CntFacturacion cntFacturacion = obtieneFacturaPorDetalleComprobante((CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, detalleComprobante.getIdDetalleComprobante()));
            CntFacturacion cntFacturacion = detalleComprobante.getCntFacturacion();
            if (cntFacturacion != null) {
                cntFacturacion.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                cntFacturacion.setUsuarioModificacion(detalleComprobante.getUsuarioModificacion());
                cntFacturacion.setFechaModificacion(detalleComprobante.getFechaModificacion());
                mergeCntFacturacion(cntFacturacion);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public String[] validaDatosDeFacturaDeCompra(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion) {
        String[] colores = new String[6];
        String mensaje = "";
        if (nit == null || nit < 0) {
            colores[0] = "red";
            mensaje = mensaje + "Nit, ";
        } else {
            colores[0] = null;
        }
        if (razonSocial.equals("")) {
            colores[1] = "red";
            mensaje = mensaje + "Razon Social, ";
        } else {
            colores[1] = null;
        }
        if (numeroAutorizacion.equals("")) {
            colores[2] = "red";
            mensaje = mensaje + "Autorización, ";
        } else {
            colores[2] = null;
        }
        if (!activaCodigoDeControl(cntFacturacion)) {
            if (cntFacturacion.getCodigoControl().equals("") || razonSocial == null) {
                colores[3] = "red";
                mensaje = mensaje + "Código de Control, ";
            } else {
                colores[3] = null;
            }
        }
        if (cntFacturacion.getNroInicial() == null || cntFacturacion.getNroInicial() < 0) {
            colores[4] = "red";
            mensaje = mensaje + "Nª de Factura, ";
        } else {
            colores[4] = null;
        }
        colores[5] = mensaje;
        return colores;
    }

    public String[] validaCamposFacturaVenta(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion) {
        String[] colores = new String[4];
        String mensaje = "";

        if (nit == null || nit < 0 || nit.toString().equals("")) {
            colores[0] = "red";
            mensaje = mensaje + "Nit, ";
        } else {
            colores[0] = null;
        }
        if (razonSocial.equals("")) {
            colores[1] = "red";
            mensaje = mensaje + "Razon Social, ";
        } else {
            colores[1] = null;
        }
        if (cntFacturacion.getNroAutorizacion().equals("")) {
            colores[2] = "red";
            mensaje = mensaje + "Numero de Autorizacion, ";
        } else {
            colores[2] = null;
        }
        colores[3] = mensaje;
        return colores;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaFacturaDeCompra(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion, Long idDetalleComprobante) throws Exception {
        System.out.println("guardado FACTURA DE COMPRA-----------------");
        cntFacturacion.setMovimiento(EnumMovimientoFactura.FACTURA_COMPRA.getCodigo());
        if (cntFacturacion.getCreditoFiscalTransitorio().equals("N")) {
            cntFacturacion.setIdProveedorCliente(cargaProveedor(cntFacturacion, nit, razonSocial, numeroAutorizacion).getIdProveedor());
            cntFacturacion.setNit(nit);
            cntFacturacion.setRazonSocial(razonSocial);
        } else {
            ParTipoFacturacion parTipoFacturacion = (ParTipoFacturacion) parParametricasService.find(ParTipoFacturacion.class, "MINT");
            razonSocial = "";
            nit = 0L;
            cntFacturacion.setNroAutorizacion("");
            cntFacturacion.setParTipoFacturacion(parTipoFacturacion);
        }
        cntFacturacion.setNit(nit);
        cntFacturacion.setRazonSocial(razonSocial);
        cntFacturacion.setNroAutorizacion(numeroAutorizacion);
        cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
        if (cntFacturacion.getIdFacturacion() != null) {
            cntFacturacion.setIdAntecesor(cntFacturacion.getIdFacturacion());
            CntFacturacion cntFacturacionOriginal = find(CntFacturacion.class, cntFacturacion.getIdFacturacion());
            CntDetalleComprobante cntDetalleComprobanteAux = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacionOriginal);
//            CntDetalleComprobante cntDetalleComprobanteOriginal = cntFacturacionOriginal.getCntDetalleComprobante();
            CntDetalleComprobante cntDetalleComprobanteOriginal = cntDetalleComprobanteAux;
            cntDetalleComprobanteOriginal.setFechaModificacion(cntFacturacion.getFechaAlta());
            cntDetalleComprobanteOriginal.setUsuarioModificacion(cntFacturacion.getUsuarioAlta());
            cntFacturacion.setIdFacturacion(null);
            cntFacturacion = persistCntFacturacion(cntFacturacion);
            if (cntFacturacionOriginal.getIdAntecesor() != null && cntFacturacionOriginal.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                cntFacturacion.setIdAntecesor(cntFacturacionOriginal.getIdAntecesor());
                mergeCntFacturacion(cntFacturacion);
                cntDetalleComprobanteOriginal.setCntFacturacion(null);
                deleteCntFacturacion(cntFacturacionOriginal);
            } else {
                if (cntFacturacionOriginal.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                    cntDetalleComprobanteOriginal.setCntFacturacion(null);
                    deleteCntFacturacion(cntFacturacionOriginal);
                } else {
                    removeCntFacturacion(cntFacturacionOriginal);
                }
            }
            cntDetalleComprobanteService.removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteOriginal);
        } else {
            cntFacturacion = persistCntFacturacion(cntFacturacion);
        }
//        encuentra por el id de detalle cambio para cpp
        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, idDetalleComprobante);
        cntDetalleComprobante.setCntFacturacion(cntFacturacion);
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion); cpp
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
        cntDetalleComprobante.setDebe(cntFacturacion.getMonto().subtract(cntFacturacion.getIva()));
        cntDetalleComprobante.setDebeDolar(convierteBolivianosDolar(cntDetalleComprobante.getDebe(), cntDetalleComprobante.getCntComprobante().getTipoCambio()));
        cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo());
        cntDetalleComprobante.setFechaAlta(cntFacturacion.getFechaAlta());
        cntDetalleComprobante.setUsuarioAlta(cntFacturacion.getUsuarioAlta());
        try {
//            MODELO PARA PRIMERAS MODIFICACIONES
            if (cntDetalleComprobante.getIdAntecesor() != null) {
                CntDetalleComprobante cntDetalleComprobanteAnterior = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor());
                if (cntDetalleComprobanteAnterior.getIdAntecesor() != null && cntDetalleComprobanteAnterior.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                    cntDetalleComprobante.setIdAntecesor(cntDetalleComprobanteAnterior.getIdAntecesor());
                    cntDetalleComprobante = cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
                    cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
                } else {
                    if (cntDetalleComprobanteAnterior.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                        cntDetalleComprobante.setIdAntecesor(null);
                        cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
                    } else {
                        cntDetalleComprobanteService.removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
                    }
                }
            } else {
                cntDetalleComprobante = cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
            }
        } catch (Exception ex) {
            throw ex;
        }
        CntDetalleComprobante detalleComprobanteCreditoFiscal;
        detalleComprobanteCreditoFiscal = (CntDetalleComprobante) cntDetalleComprobante.clone();
//        se setea un valor nulo en facturacion para no crear la relacion 
        detalleComprobanteCreditoFiscal.setCntFacturacion(null);
        detalleComprobanteCreditoFiscal.setTransaccionRealizada(null);
        detalleComprobanteCreditoFiscal.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
        detalleComprobanteCreditoFiscal.setPosicion(null);
        detalleComprobanteCreditoFiscal.setIdDetalleComprobante(null);
        detalleComprobanteCreditoFiscal.setIdAntecesor(null);
        if (cntFacturacion.getCreditoFiscalTransitorio().equals("N")) {
            Long idDebitoFiscal = Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CCRF")).getValor()); //Parametrica de Credito Fiscal
//            Long idDebitoFiscal = Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CDBF")).getValor()); //Parametrica de Debito Fiscal
            detalleComprobanteCreditoFiscal.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, idDebitoFiscal));
        } else {
            Long idCreditoFiscalTransitorio = Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CCFT")).getValor());
            detalleComprobanteCreditoFiscal.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, idCreditoFiscalTransitorio));
        }
        detalleComprobanteCreditoFiscal.setDebe(cntFacturacion.getIva());
        detalleComprobanteCreditoFiscal.setDebeDolar(cntFacturacion.getIvaSegMoneda());
        cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobanteCreditoFiscal);
        CntComprobante comprobante = cntComprobantesService.find(CntComprobante.class, detalleComprobanteCreditoFiscal.getCntComprobante().getIdComprobante());
        comprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntComprobantesService.merge(comprobante);
    }

    //    guardado de factura de venta====================================================================================================
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaFacturaDeVenta(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion, Long idDetalleComprobante) throws Exception {
        int sw = 0;
        BigDecimal resultadoMontoIVA = new BigDecimal(BigInteger.ZERO);
        BigDecimal resultadoMontoIVADolar = new BigDecimal(BigInteger.ZERO);
        BigDecimal resultadoMontoIT = new BigDecimal(BigInteger.ZERO);
        BigDecimal resultadoMontoITDolar = new BigDecimal(BigInteger.ZERO);
        BigDecimal resultadoMonto = new BigDecimal(BigInteger.ZERO);
        BigDecimal resultadoMontoDolar = new BigDecimal(BigInteger.ZERO);
        BigDecimal monto = new BigDecimal(BigInteger.ZERO);
        BigDecimal porcentaje1 = new BigDecimal("1");
        BigDecimal porcentaje = new BigDecimal("100");
//        CntDetalleComprobante cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion);
        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, idDetalleComprobante);
        BigDecimal iva = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IVA")).getValor());
        iva = iva.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal it = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IT")).getValor());
        it = it.setScale(2, BigDecimal.ROUND_HALF_UP);
        if (razonSocial != null && nit != null) {
            if (cntProveedorService.generaProveedorPorRazonSocial(razonSocial) == null) {
                CntProveedor cntProveedor = new CntProveedor();
                cntProveedor.setRazonSocial(razonSocial);
                cntProveedor.setNit(nit);
                cntProveedor.setUsuarioAlta(cntFacturacion.getLoginUsuario());
                cntProveedor.setFechaAlta(new Date());
                cntProveedor.setAutorizacion(numeroAutorizacion);
                cntProveedor = cntProveedorService.persistCntProveedor(cntProveedor);
                cntFacturacion.setIdProveedorCliente(cntProveedor.getIdProveedor());
            }
        }

        if (cntFacturacion.getNroAutorizacion() != null) {
            if (parParametricasService.encuentraParSucursalPorNumeroAutorizacion(cntFacturacion.getNroAutorizacion()) == false) {
                ParSucursal parSucursal = parParametricasService.encuentraParSucursal(cntFacturacion.getSucursal());
                ParValor parValor = (ParValor) parParametricasService.findParValor(parSucursal.getCodigo());
                parValor.setValor(cntFacturacion.getNroAutorizacion());
                parValor.setFechaModificacion(new Date());
                parValor.setUsuarioModificacion(cntFacturacion.getLoginUsuario());
                try {
                    parParametricasService.mergeParValor(parValor);
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
        cntFacturacion.setMovimiento(EnumMovimientoFactura.FACTURA_VENTA.getCodigo());
//        cntFacturacion.setCntDetalleComprobante(cntDetalleComprobante);
        cntFacturacion.setNit(nit);
        cntFacturacion.setRazonSocial(razonSocial);
        cntFacturacion.setNroAutorizacion(numeroAutorizacion);
        cntFacturacion.setLoginUsuario(cntFacturacion.getLoginUsuario());
        cntFacturacion.setFechaAlta(new Date());
        cntFacturacion.setUsuarioAlta(cntFacturacion.getLoginUsuario());
        cntFacturacion.setParParametrosAutorizacion(null);
        cntFacturacion.setParTipoFacturacion(null);
        if (sw == 1) {
            monto = cntFacturacion.getMonto();
        } else {
            if (cntDetalleComprobante.getDebe() != null) {
                monto = cntDetalleComprobante.getDebe();
            }
            if (cntDetalleComprobante.getHaber() != null) {
                monto = cntDetalleComprobante.getHaber();
            }
        }
        cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntFacturacion = persistCntFacturacion(cntFacturacion);
        monto = monto.subtract(cntFacturacion.getExcento().add(cntFacturacion.getIce()));
        resultadoMonto = (monto.multiply(porcentaje1.subtract(iva.divide(porcentaje)))).setScale(2, BigDecimal.ROUND_HALF_UP);
        resultadoMontoIVA = (monto.subtract(monto.multiply(porcentaje1.subtract(iva.divide(porcentaje))))).setScale(2, BigDecimal.ROUND_HALF_UP);
        resultadoMontoIT = (monto.subtract(monto.multiply(porcentaje1.subtract(it.divide(porcentaje))))).setScale(2, BigDecimal.ROUND_HALF_UP);
        resultadoMontoDolar = (resultadoMonto.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);
        resultadoMontoIVADolar = (resultadoMontoIVA.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);
        resultadoMontoITDolar = (resultadoMontoIT.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);
        cntDetalleComprobante.setHaber((monto.multiply(porcentaje1.subtract(iva.divide(porcentaje)))).setScale(2, BigDecimal.ROUND_HALF_UP));
        cntDetalleComprobante.setHaberDolar((resultadoMonto.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
        cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo());
        cntDetalleComprobante.setCntFacturacion(cntFacturacion);
        try {
            if (cntDetalleComprobante.getIdAntecesor() != null) {
                CntDetalleComprobante cntDetalleComprobanteAnterior = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor());
                if (cntDetalleComprobanteAnterior.getIdAntecesor() != null && cntDetalleComprobanteAnterior.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                    cntDetalleComprobante.setIdAntecesor(cntDetalleComprobanteAnterior.getIdAntecesor());
                    cntDetalleComprobante = cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
                    cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
                } else {
                    if (cntDetalleComprobanteAnterior.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                        cntDetalleComprobante.setIdAntecesor(null);
                        cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
                    } else {
                        cntDetalleComprobanteService.removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAnterior);
                    }
                }
            } else {
                cntDetalleComprobante = cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
            }
        } catch (Exception ex) {
            throw ex;
        }
        cntDetalleComprobante.setHaber((monto.multiply(porcentaje1.subtract(iva.divide(porcentaje)))).setScale(2, BigDecimal.ROUND_HALF_UP));
        cntDetalleComprobante.setHaberDolar((resultadoMonto.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
        List<CntDetalleComprobante> lista = encuentraHijosDetalleComprobantePorFacturacion(cntFacturacion);
        CntDetalleComprobante detalleComprobante;
        detalleComprobante = (CntDetalleComprobante) cntDetalleComprobante.clone();
        detalleComprobante.setIdDetalleComprobante(null);
        detalleComprobante.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CDBF")).getValor())));
        detalleComprobante.setDebe(null);
        detalleComprobante.setHaber(resultadoMontoIVA);
        detalleComprobante.setFechaAlta(new Date());
        detalleComprobante.setUsuarioAlta(cntFacturacion.getLoginUsuario());
        detalleComprobante.setLoginUsuario(cntFacturacion.getLoginUsuario());
        detalleComprobante.setDebeDolar(null);
        detalleComprobante.setHaberDolar((resultadoMontoIVA.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
        detalleComprobante.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
        detalleComprobante.setPosicion(null);
        detalleComprobante.setIdAntecesor(null);
        detalleComprobante.setCntFacturacion(null);
        detalleComprobante.setTransaccionRealizada(null);
        try {
            cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante);
        } catch (Exception ex) {
            throw ex;
        }
        CntDetalleComprobante detalleComprobante1;
        detalleComprobante1 = (CntDetalleComprobante) cntDetalleComprobante.clone();
        detalleComprobante1.setIdDetalleComprobante(null);
//        detalleComprobante1.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CITP")).getValor())));
        detalleComprobante1.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CITG")).getValor())));
        detalleComprobante1.setDebe(resultadoMontoIT);
        detalleComprobante1.setHaber(null);
        detalleComprobante1.setFechaAlta(new Date());
        detalleComprobante1.setUsuarioAlta(cntFacturacion.getLoginUsuario());
        detalleComprobante1.setLoginUsuario(cntFacturacion.getLoginUsuario());
        detalleComprobante1.setDebeDolar((resultadoMontoIT.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
        detalleComprobante1.setHaberDolar(null);
        detalleComprobante1.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
        detalleComprobante1.setPosicion(null);
        detalleComprobante1.setIdAntecesor(null);
        detalleComprobante1.setCntFacturacion(null);
        detalleComprobante1.setTransaccionRealizada(null);
        try {
            cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante1);
        } catch (Exception ex) {
            throw ex;
        }
        CntDetalleComprobante detalleComprobante2;
        detalleComprobante2 = (CntDetalleComprobante) cntDetalleComprobante.clone();
        detalleComprobante2.setIdDetalleComprobante(null);
//        detalleComprobante2.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CITG")).getValor())));
        detalleComprobante2.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CITP")).getValor())));
        detalleComprobante2.setDebe(null);
        detalleComprobante2.setHaber(resultadoMontoIT);
        detalleComprobante2.setFechaAlta(new Date());
        detalleComprobante2.setUsuarioAlta(cntFacturacion.getLoginUsuario());
        detalleComprobante2.setLoginUsuario(cntFacturacion.getLoginUsuario());
        detalleComprobante2.setDebeDolar(null);
        detalleComprobante2.setHaberDolar((resultadoMontoIT.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
        detalleComprobante2.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
        detalleComprobante2.setPosicion(null);
        detalleComprobante2.setIdAntecesor(null);
        detalleComprobante2.setCntFacturacion(null);
        detalleComprobante2.setTransaccionRealizada(null);
        try {
            cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante2);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaFacturaDeVentaModifica(CntFacturacion cntFacturacion, Long nit, String razonSocial, String numeroAutorizacion, Long idDetallleComprobante) throws Exception {
        try {

//        CntDetalleComprobante cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(cntFacturacion);
            CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, idDetallleComprobante);
//            CntDetalleComprobante cntDetalleComprobanteAntecesor = encuentraDetalleComprobanteAntecesor(cntFacturacion);
            CntDetalleComprobante cntDetalleComprobanteAntecesor = cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor());
            if (cntDetalleComprobanteAntecesor != null) {
                cntDetalleComprobanteAntecesor.setFechaModificacion(cntFacturacion.getFechaModificacion());
                cntDetalleComprobanteAntecesor.setUsuarioModificacion(cntFacturacion.getUsuarioModificacion());
                cntDetalleComprobanteAntecesor.setFechaBaja(cntFacturacion.getFechaModificacion());
                cntDetalleComprobanteAntecesor.setUsuarioBaja(cntFacturacion.getUsuarioModificacion());
                cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobanteAntecesor);
            }
            cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
            CntFacturacion cntFacturacionModificar = (CntFacturacion) cntFacturacion.clone();
            cntFacturacionModificar.setFechaModificacion(null);
            cntFacturacionModificar.setUsuarioModificacion(null);
            cntFacturacionModificar.setIdFacturacion(null);
//        cntFacturacionModificar.setCntDetalleComprobante(cntDetalleComprobante);
            cntFacturacionModificar.setEstado(EnumEstado.PENDIENTE.getCodigo());
            guardaFacturaDeVenta(cntFacturacionModificar, nit, razonSocial, numeroAutorizacion, idDetallleComprobante);
            if (cntFacturacion.getIdAntecesor() != null && cntFacturacion.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                cntFacturacionModificar.setIdAntecesor(cntFacturacion.getIdAntecesor());
                mergeCntFacturacion(cntFacturacionModificar);
            } else {
                cntFacturacionModificar.setIdAntecesor(cntFacturacion.getIdFacturacion());
                mergeCntFacturacion(cntFacturacionModificar);
            }
            CntComprobante comprobante = cntComprobantesService.find(CntComprobante.class, cntDetalleComprobante.getCntComprobante().getIdComprobante());
            comprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntComprobantesService.mergeCntComprobantes(comprobante);

        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntFacturacion> listaReporteCompraVenta(String movimiento, int periodo, int anio) throws Exception {
        System.out.println("...movimiento ::"+movimiento+"..periodo :::"+periodo+"..anio :: "+anio);
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy, MM, dd");
            int mes = periodo - 1;
            Calendar calendario = Calendar.getInstance();
            calendario.set(anio, mes, 1);
            int ultimoDia = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date fechaInicialConfigurada = formato.parse(anio + ", " + periodo + ", 01");
            Date fechaFinalConfigurada = formato.parse(anio + ", " + periodo + ", " + ultimoDia);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
            String fechaInicial = formatoFecha.format(fechaInicialConfigurada);
            String fechaFinal = formatoFecha.format(fechaFinalConfigurada);
//            List<CntFacturacion> listaFacturas = hibernateTemplate.find(""
//                    + "select j "
//                    + "from CntFacturacion j "
//                    + "where j.fechaBaja is null "
//                    + "and j.estado = 'CONF' "
//                    + "and j.movimiento = '" + movimiento + "' "
//                    + "and j.fechaFactura >= '" + fechaInicial + "' and j.fechaFactura <= '" + fechaFinal + "' "
//                    + "order by j.fechaFactura asc"
//            );  
            System.out.println("...fecha inicial :::"+fechaInicial+"..fecha final :: "+fechaFinal);
            String consultaExtra = "and j.cntFacturacion.fechaFactura >= '" + fechaInicial + "' and j.cntFacturacion.fechaFactura <= '" + fechaFinal + "' ";
            if (movimiento.equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo())) {
                consultaExtra = "and j.cntComprobante.fecha >= '" + fechaInicial + "' and j.cntComprobante.fecha <= '" + fechaFinal + "' ";
            }
            List<CntFacturacion> listaFacturas = hibernateTemplate.find(""
                    + "select j.cntFacturacion "
                    + "from CntDetalleComprobante j "
                    + "where j.cntComprobante.fechaBaja is null "
                    + "and (j.cntFacturacion.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' "
                    + "or j.cntFacturacion.estado = '" + EnumEstado.ANULADO.getCodigo() + "' )"
                    + "and j.fechaBaja is null "
                    + "and j.cntFacturacion.fechaBaja is null "
                    + "and j.cntFacturacion.movimiento = '" + movimiento + "' "
                    + "and j.idPadre = null "
                    + consultaExtra
                    + "order by j.cntFacturacion.fechaFactura asc"
            );
            if (!listaFacturas.isEmpty()) {
                return listaFacturas;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    public Long generaNumeroDeFactura(Long idDosificacion) throws Exception {
        try {
            Long numero = 0L;
            List<CntFacturacion> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntFacturacion j "
                    + "where j.fechaBaja is null "
                    + "and j.idDosificacion = " + idDosificacion + " "
                    + "and j.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' "
                    + "order by j.nroInicial desc");
            if (!lista.isEmpty()) {
                numero = lista.get(0).getNroInicial() + 1;
            }
            return numero;
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean validaNumeroFactura(CntFacturacion cntFacturacion) throws Exception {
        System.out.println("...nro factura es : " + cntFacturacion.getNroInicial());
        List<CntFacturacion> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntFacturacion j "
                + "where j.fechaBaja is null "
                + "and j.nroInicial = " + cntFacturacion.getNroInicial() + " "
                //                    + "and j.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' "
                + "order by j.nroInicial desc");

        if (!lista.isEmpty()) {
            System.out.println("...retornando por distinto true");
            return true;
        }
        System.out.println("...retornando por false");
        return false;
    }

    public List<CntFacturacion> listaFacturaVentaAnulada(String movimiento, int periodo, int anio) throws Exception {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy, MM, dd");
        int mes = periodo - 1;
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio, mes, 1);
        int ultimoDia = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date fechaInicialConfigurada = formato.parse(anio + ", " + periodo + ", 01");
        Date fechaFinalConfigurada = formato.parse(anio + ", " + periodo + ", " + ultimoDia);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
        String fechaInicial = formatoFecha.format(fechaInicialConfigurada);
        String fechaFinal = formatoFecha.format(fechaFinalConfigurada);

        System.out.println("..fei :" + fechaInicial + "..fecha fin : " + fechaFinal);
        CntFacturacion j;

        List<CntFacturacion> listaFacturas = hibernateTemplate.find(""
                + "select j "
                + "from CntFacturacion j "
                + "where j.fechaBaja is null "
                + "and j.estado = '" + EnumEstado.ANULADO.getCodigo() + "' "
                + "and j.movimiento = '" + movimiento + "' "
                + "and j.fechaFactura >= '" + fechaInicial + "' and j.fechaFactura <= '" + fechaFinal + "' "
                + "and j.monto"
                + "and j.idFacturacion NOT IN (select c.cntFacturacion.idFacturacion "
                + "from CntDetalleComprobante c "
                + "where c.fechaBaja is null ) "
                + "order by j.fechaFactura asc");
        System.out.println("...lista es : " + listaFacturas.size());
        if (!listaFacturas.isEmpty()) {
            return listaFacturas;
        } else {
            return Collections.EMPTY_LIST;
        }

    }

    public List<CntFacturacion> listaReporteFacturaVentaMigrado(String movimiento, int periodo, int anio) throws Exception {
        System.out.println("...movimiento ::"+movimiento+"..periodo :::"+periodo+"..anio :: "+anio);
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy, MM, dd");
            int mes = periodo - 1;
            Calendar calendario = Calendar.getInstance();
            calendario.set(anio, mes, 1);
            int ultimoDia = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date fechaInicialConfigurada = formato.parse(anio + ", " + periodo + ", 01");
            Date fechaFinalConfigurada = formato.parse(anio + ", " + periodo + ", " + ultimoDia);
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
            String fechaInicial = formatoFecha.format(fechaInicialConfigurada);
            String fechaFinal = formatoFecha.format(fechaFinalConfigurada);

            System.out.println("...fecha inicial :::"+fechaInicial+"..fecha final :: "+fechaFinal);
            String consultaExtra = "and j.cntFacturacion.fechaFactura >= '" + fechaInicial + "' and j.cntFacturacion.fechaFactura <= '" + fechaFinal + "' ";
            
            List<CntFacturacion> listaFacturas = hibernateTemplate.find(""
                    + "select j "
                    + "from CntFacturacion j "
                    + "where j.fechaBaja is null "
                    + "and j.estado = 'CONF' "
                    + "and j.movimiento = '" + movimiento + "' "
                    + "and j.fechaFactura >= '" + fechaInicial + "' and j.fechaFactura <= '" + fechaFinal + "' "
                    + "order by j.fechaFactura asc"
            );  
            
            if (!listaFacturas.isEmpty()) {
                return listaFacturas;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

}
