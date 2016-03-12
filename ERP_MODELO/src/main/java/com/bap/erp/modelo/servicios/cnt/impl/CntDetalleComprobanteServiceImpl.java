package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import static com.bap.erp.commons.utils.NumberUtils.redondeaFloat;
import com.bap.erp.modelo.entidades.cnf.ParComprasYVentas;
import com.bap.erp.modelo.entidades.cnf.ParCuentasDeAjuste;
import com.bap.erp.modelo.entidades.cnf.ParGestionContable;
import com.bap.erp.modelo.entidades.cnf.ParRetencionIue;
import com.bap.erp.modelo.entidades.cnt.*;
import com.bap.erp.modelo.enums.*;
import com.bap.erp.modelo.pojo.PojoCntDetalleComprobanteSumasSaldos;
import com.bap.erp.modelo.pojo.PojoCntEntidadBGyEERR;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntDistribucionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntFacturacionService;
import com.bap.erp.modelo.servicios.cnt.CntParametroAutomaticoService;
import com.iknow.utils.ObjectUtils;
//import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntDetalleComprobanteServiceImpl extends GenericDaoImpl<CntDetalleComprobante> implements CntDetalleComprobanteService {

    @Autowired
    private ParParametricasService parParametricasService;
    @Autowired
    private CntParametroAutomaticoService parametroAutomaticoService;
    @Autowired
    private CntEntidadesService cntEntidadesService;
    @Autowired
    private CntFacturacionService cntFacturacionService;
    @Autowired
    private CntComprobantesService cntComprobantesService;
    @Autowired
    private CntDistribucionCentroCostoService cntDistribucionCentrocostoService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDetalleComprobante persistCntDetalleComprobantes(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
            super.persist(cntDetalleComprobante);
        } catch (Exception e) {
            throw e;
        }
        return cntDetalleComprobante;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDetalleComprobante mergeCntDetalleComprobantes(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
            super.merge(cntDetalleComprobante);
        } catch (Exception e) {
            throw e;
        }
        return cntDetalleComprobante;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeCntDetalleComprobantesCntFacturacion(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
            CntFacturacion cntFacturacion = cntFacturacionService.obtieneFacturaPorDetalleComprobante(cntDetalleComprobante);
            if (cntFacturacion != null) {
                cntFacturacion.setUsuarioModificacion(cntDetalleComprobante.getUsuarioModificacion());
                cntFacturacion.setFechaModificacion(cntDetalleComprobante.getFechaModificacion());
                cntFacturacion.setUsuarioBaja(cntDetalleComprobante.getUsuarioModificacion());
                cntFacturacion.setFechaBaja(cntDetalleComprobante.getFechaModificacion());
                cntFacturacionService.removeCntFacturacion(cntFacturacion);
            }
            /*ENTRABA DISTRIBUCION*/
            List<CntDetalleComprobante> listaAux = listaHijosPorPadre(cntDetalleComprobante);
            for (CntDetalleComprobante detalleComprobanteHijos : listaAux) {
                /**
                 * ENTRABA PARA LOS HIJOS
                 */
//                detalleComprobanteHijos.setCntFacturacion(null);
//                mergeCntDetalleComprobantes(cntDetalleComprobante);
                detalleComprobanteHijos.setUsuarioModificacion(cntDetalleComprobante.getUsuarioModificacion());
                detalleComprobanteHijos.setFechaModificacion(cntDetalleComprobante.getFechaModificacion());
                detalleComprobanteHijos.setUsuarioBaja(cntDetalleComprobante.getUsuarioModificacion());
                detalleComprobanteHijos.setFechaBaja(cntDetalleComprobante.getFechaModificacion());
                super.remove(detalleComprobanteHijos);

            }
            if (find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getDebe() != null) {
                cntDetalleComprobante.setDebe(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getDebe());
            } else {
                if (find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getHaber() != null) {
                    cntDetalleComprobante.setHaber(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getHaber());
                }
            }
            cntDetalleComprobante.setUsuarioBaja(cntDetalleComprobante.getUsuarioModificacion());
            cntDetalleComprobante.setFechaBaja(cntDetalleComprobante.getFechaModificacion());
            super.remove(cntDetalleComprobante);
        } catch (Exception h) {
            throw h;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeCntDetalleComprobantesCntFacturacionQuitar(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
            CntFacturacion cntFacturacion = cntFacturacionService.obtieneFacturaPorDetalleComprobante(cntDetalleComprobante);
            if (cntFacturacion != null) {
                cntFacturacion.setEstado(EnumEstado.QUITADO_CONFIRMADO.getCodigo());
                cntFacturacion.setUsuarioBaja(cntDetalleComprobante.getUsuarioBaja());
                cntFacturacion.setFechaBaja(cntDetalleComprobante.getFechaBaja());
                cntFacturacionService.removeCntFacturacion(cntFacturacion);
            }
            for (CntDetalleComprobante detalleComprobanteHijos : listaHijosPorPadre(cntDetalleComprobante)) {
                detalleComprobanteHijos.setEstado(EnumEstado.QUITADO_CONFIRMADO.getCodigo());
                detalleComprobanteHijos.setUsuarioBaja(cntDetalleComprobante.getUsuarioBaja());
                detalleComprobanteHijos.setFechaBaja(cntDetalleComprobante.getFechaBaja());
                super.remove(detalleComprobanteHijos);
            }
            if (find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getDebe() != null) {
                cntDetalleComprobante.setDebe(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getDebe());
            } else {
                if (find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getHaber() != null) {
                    cntDetalleComprobante.setHaber(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getHaber());
                }
            }
            cntDetalleComprobante.setUsuarioBaja(cntDetalleComprobante.getUsuarioBaja());
            cntDetalleComprobante.setFechaBaja(cntDetalleComprobante.getFechaBaja());
            super.remove(cntDetalleComprobante);
        } catch (Exception h) {
            throw h;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void deleteCntDetalleComprobantesCntFacturacion(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        CntDetalleComprobante detalleComprobante = find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante());
        try {
//            CntFacturacion cntFacturacion = cntFacturacionService.obtieneFacturaPorDetalleComprobanteParaDelete(detalleComprobante);
            CntFacturacion cntFacturacion = detalleComprobante.getCntFacturacion();
            if (cntFacturacion != null) {
                for (CntDetalleComprobante detalleComprobanteHijosAnterior : listaHijosPorPadreParaDelete(detalleComprobante)) {
                    detalleComprobanteHijosAnterior.setCntFacturacion(null);
                    mergeCntDetalleComprobantes(detalleComprobanteHijosAnterior);
                }
                detalleComprobante.setCntFacturacion(null);
                mergeCntDetalleComprobantes(detalleComprobante);
                CntFacturacion facturaParaEliminar = cntFacturacionService.find(CntFacturacion.class, cntFacturacion.getIdFacturacion());
                cntFacturacionService.deleteCntFacturacion(facturaParaEliminar);
            }
            List<CntDistribucionCentrocosto> listaCntDistribucionCC = cntDistribucionCentrocostoService.listaDistribucionCentroCostoPorDetalleComprobante(detalleComprobante);
            if (!listaCntDistribucionCC.isEmpty()) {
                for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaCntDistribucionCC) {
                    cntDistribucionCentrocostoService.deleteCntDistribucionCentroCosto(cntDistribucionCentrocosto);
                }
            }
            for (CntDetalleComprobante detalleComprobanteHijos : listaHijosPorPadreParaDelete(detalleComprobante)) {
                listaCntDistribucionCC = cntDistribucionCentrocostoService.listaDistribucionCentroCostoPorDetalleComprobante(detalleComprobanteHijos);
                if (!listaCntDistribucionCC.isEmpty()) {
                    for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaCntDistribucionCC) {
                        cntDistribucionCentrocostoService.deleteCntDistribucionCentroCosto(cntDistribucionCentrocosto);
                    }
                }
                detalleComprobanteHijos.setCntFacturacion(null);
//                mergeCntDetalleComprobantes(cntDetalleComprobante);
                super.delete(detalleComprobanteHijos);
            }
            super.delete(detalleComprobante);
        } catch (Exception h) {
            throw h;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void mergeCntDetalleComprobantesModificaTotal(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
            CntFacturacion cntFacturacion = cntFacturacionService.obtieneFacturaPorDetalleComprobanteParaDelete(cntDetalleComprobante);
            if (cntFacturacion != null) {
                if (cntFacturacion.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo())) {
                    cntFacturacion.setUsuarioModificacion(cntDetalleComprobante.getUsuarioModificacion());
                    cntFacturacion.setFechaModificacion(cntDetalleComprobante.getFechaModificacion());
                    cntFacturacion.setUsuarioBaja(cntDetalleComprobante.getUsuarioModificacion());
                    cntFacturacion.setFechaBaja(cntDetalleComprobante.getFechaModificacion());
                } else if (cntFacturacion.getEstado().equals(EnumEstado.QUITADO_CONFIRMADO.getCodigo())) {
                    cntFacturacion.setFechaModificacion(null);
                    cntFacturacion.setUsuarioModificacion(null);
                    cntFacturacion.setFechaBaja(null);
                    cntFacturacion.setUsuarioBaja(null);
                } else {
                    cntFacturacion.setUsuarioAlta(cntDetalleComprobante.getUsuarioModificacion());
                    cntFacturacion.setFechaAlta(cntDetalleComprobante.getFechaModificacion());
                }
                cntFacturacionService.mergeCntFacturacionModificaTotal(cntFacturacion);
            }
            for (CntDetalleComprobante detalleComprobanteHijos : listaHijosPorPadreParaDelete(cntDetalleComprobante)) {
                if (detalleComprobanteHijos.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo())) {
                    detalleComprobanteHijos.setUsuarioModificacion(cntDetalleComprobante.getUsuarioModificacion());
                    detalleComprobanteHijos.setFechaModificacion(cntDetalleComprobante.getFechaModificacion());
                    detalleComprobanteHijos.setUsuarioBaja(cntDetalleComprobante.getUsuarioModificacion());
                    detalleComprobanteHijos.setFechaBaja(cntDetalleComprobante.getFechaModificacion());
                } else if (detalleComprobanteHijos.getEstado().equals(EnumEstado.QUITADO_CONFIRMADO.getCodigo())) {
                    detalleComprobanteHijos.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                    detalleComprobanteHijos.setUsuarioModificacion(null);
                    detalleComprobanteHijos.setFechaModificacion(null);
                    detalleComprobanteHijos.setUsuarioBaja(null);
                    detalleComprobanteHijos.setFechaBaja(null);
                } else {
                    detalleComprobanteHijos.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                    detalleComprobanteHijos.setUsuarioAlta(cntDetalleComprobante.getUsuarioModificacion());
                    detalleComprobanteHijos.setFechaAlta(cntDetalleComprobante.getFechaModificacion());
                }
                detalleComprobanteHijos.setCntComprobante(cntDetalleComprobante.getCntComprobante());
                super.merge(detalleComprobanteHijos);
            }
            super.merge(cntDetalleComprobante);
        } catch (Exception h) {
            throw h;
        }
    }

    public List<CntDetalleComprobante> listaCntDetalleComprobantes() {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntDetalleComprobante> listaDeCuentasPorComprobante(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntDetalleComprobante> listaDeCuentasPorComprobanteParaRepilcados(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public BigDecimal sumaDebeComprobante(List<CntDetalleComprobante> listaDeCuentasDeComprobante) {
        BigDecimal resultadoDebe = new BigDecimal(BigInteger.ZERO);
        for (CntDetalleComprobante cntDetalleComprobante : listaDeCuentasDeComprobante) {
            if (cntDetalleComprobante.getDebe() != null) {
                resultadoDebe = resultadoDebe.add(cntDetalleComprobante.getDebe());
            }
        }
        return resultadoDebe;
    }

    public BigDecimal sumaHaberComprobante(List<CntDetalleComprobante> listaDeCuentasDeComprobante) {
        BigDecimal resultadoHaber = new BigDecimal(BigInteger.ZERO);
        for (CntDetalleComprobante cntDetalleComprobante : listaDeCuentasDeComprobante) {
            if (cntDetalleComprobante.getHaber() != null) {
                resultadoHaber = resultadoHaber.add(cntDetalleComprobante.getHaber());
            }
        }
        return resultadoHaber;
    }

    public List<CntDetalleComprobante> quitaCntDetalleComprobanteDeLista(List<CntDetalleComprobante> cntDetalleComprobanteList, CntDetalleComprobante cntDetalleComprobante) {
        List<CntDetalleComprobante> listaActualizada = new ArrayList<CntDetalleComprobante>();
        for (CntDetalleComprobante cntDetalleComprobante1 : cntDetalleComprobanteList) {
            if (cntDetalleComprobante.getIdDetalleComprobante() != cntDetalleComprobante.getIdDetalleComprobante()) {
                listaActualizada.add(cntDetalleComprobante1);
            }
        }
        if (!listaActualizada.isEmpty()) {
            return listaActualizada;
        }
        return Collections.EMPTY_LIST;
    }

    public Boolean verificaExistenciaDePendientes(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.estado = '" + EnumEstado.PENDIENTE.getCodigo() + "' "
                + "and j.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante());
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public CntDetalleComprobante ultimaPosicionDetalleComprobante() {
        List<CntDetalleComprobante> list;
        try {
            list = hibernateTemplate.find(
                    "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.fechaBaja is null "
                    + "and j.posicion is not null "
                    + "order by j.posicion DESC");
            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaCreditoFiscalNoDeducible(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        BigDecimal montoTotal = cntDetalleComprobante.getDebe();
        cntDetalleComprobante = obtieneMontoDebeDetalleComprobante(cntDetalleComprobante);
        try {
            cntDetalleComprobante = mergeCntDetalleComprobantes(cntDetalleComprobante);
        } catch (Exception ex) {
            throw ex;
        }
        CntDetalleComprobante cntDetalleComprobanteCreditoFiscalNoDeducible = (CntDetalleComprobante) cntDetalleComprobante.clone();
        cntDetalleComprobanteCreditoFiscalNoDeducible.setDebe((montoTotal.subtract(cntDetalleComprobante.getDebe()).setScale(2, BigDecimal.ROUND_HALF_UP)));
        cntDetalleComprobanteCreditoFiscalNoDeducible.setDebeDolar(convierteBolivianosDolar(cntDetalleComprobanteCreditoFiscalNoDeducible.getDebe(), cntDetalleComprobante.getCntComprobante().getTipoCambio()));
        Long idCreditoFiscalNoDeducible = Long.parseLong(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "CCFD")).getValor());
        cntDetalleComprobanteCreditoFiscalNoDeducible.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, idCreditoFiscalNoDeducible));
        cntDetalleComprobanteCreditoFiscalNoDeducible.setIdPadre(cntDetalleComprobanteCreditoFiscalNoDeducible.getIdDetalleComprobante());
        cntDetalleComprobanteCreditoFiscalNoDeducible.setIdDetalleComprobante(null);
        cntDetalleComprobanteCreditoFiscalNoDeducible.setTransaccionRealizada(null);
        cntDetalleComprobanteCreditoFiscalNoDeducible.setPosicion(null);
        cntDetalleComprobanteCreditoFiscalNoDeducible.setIdAntecesor(null);
        persistCntDetalleComprobantes(cntDetalleComprobanteCreditoFiscalNoDeducible);
    }

    public CntDetalleComprobante obtieneMontoDebeDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        BigDecimal montoParcial = cntDetalleComprobante.getDebe();
        BigDecimal iva = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IVA")).getValor()).multiply(new BigDecimal("0.01"));
        iva = iva.setScale(5, BigDecimal.ROUND_HALF_UP);
        cntDetalleComprobante.setDebe((cntDetalleComprobante.getDebe().multiply(new BigDecimal("1").subtract(iva))).setScale(2, BigDecimal.ROUND_HALF_UP));
        cntDetalleComprobante.setDebeDolar(convierteBolivianosDolar(cntDetalleComprobante.getDebe(), cntDetalleComprobante.getCntComprobante().getTipoCambio()));
        return cntDetalleComprobante;
    }

    public BigDecimal convierteBolivianosDolar(BigDecimal valorBolivianos, BigDecimal tipoCambio) {
        return ((valorBolivianos.divide(tipoCambio, 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public CntDetalleComprobante obtienePosicionDetalleComprobantePorComprobante(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        if (cntDetalleComprobante.getPosicion() != null) {
            try {
                aumentaPosicionAPadres(cntDetalleComprobante.getCntComprobante(), cntDetalleComprobante.getPosicion());
            } catch (Exception e) {
                throw e;
            }
        } else {
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.fechaBaja is null "
                    + "and j.cntComprobante.idComprobante = " + cntDetalleComprobante.getCntComprobante().getIdComprobante() + " "
                    + "order by j.posicion DESC");
            if (!lista.isEmpty()) {
                cntDetalleComprobante.setPosicion(lista.get(0).getPosicion() + 1);
            } else {
                cntDetalleComprobante.setPosicion(1L);
            }
        }
        return cntDetalleComprobante;
    }

    public List<CntDetalleComprobante> ordenaSegunPosicion(CntComprobante cntComprobante) throws Exception {
        try {
            CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "DCA3")).getValor()));
            List<CntDetalleComprobante> listaFinal = new ArrayList<CntDetalleComprobante>();
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "' "
                    + "and j.fechaBaja is null and j.cntEntidad.idEntidad<>'" + cntEntidad.getIdEntidad() + "' "
                    + "and j.idPadre is null order by j.posicion ASC ");
            if (!lista.isEmpty()) {
                for (CntDetalleComprobante cntDetalleComprobante : lista) {
                    listaFinal.add(cntDetalleComprobante);
                    if (!listaHijosPorPadre(cntDetalleComprobante).isEmpty()) {
                        listaFinal.addAll(listaHijosPorPadre(cntDetalleComprobante));
                    }
                }
                return listaFinal;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

//    ordenaSegunPosicionDolar
    public List<CntDetalleComprobante> ordenaSegunPosicionDolar(CntComprobante cntComprobante) throws Exception {
        try {

            CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "DCA3")).getValor()));
            List<CntDetalleComprobante> listaFinal = new ArrayList<CntDetalleComprobante>();
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "' "
                    + "and j.fechaBaja is null "
                    + "and j.idPadre is null order by j.posicion ASC ");
            if (!lista.isEmpty()) {
                for (CntDetalleComprobante cntDetalleComprobante : lista) {
                    listaFinal.add(cntDetalleComprobante);
                    if (!listaHijosPorPadre(cntDetalleComprobante).isEmpty()) {
                        listaFinal.addAll(listaHijosPorPadre(cntDetalleComprobante));
                    }
                }
                return listaFinal;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntDetalleComprobante> listaHijosPorPadre(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.fechaBaja is null "
                    + "and j.idPadre = '" + cntDetalleComprobante.getIdDetalleComprobante() + "'");
            if (!lista.isEmpty()) {
                return lista;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntDetalleComprobante> listaHijosPorPadreParaDelete(CntDetalleComprobante cntDetalleComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.idPadre = '" + cntDetalleComprobante.getIdDetalleComprobante() + "'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void aumentaPosicionAPadres(CntComprobante cntComprobante, long posicion) throws Exception {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "'"
                + "and j.posicion >= '" + posicion + "' ");
        if (!lista.isEmpty()) {
            for (CntDetalleComprobante cntDetalleComprobante : lista) {
                cntDetalleComprobante.setPosicion(cntDetalleComprobante.getPosicion() + 1);
                mergeCntDetalleComprobantes(cntDetalleComprobante);
            }
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void reducePosicionAPadres(CntComprobante cntComprobante, long posicion) throws Exception {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.fechaBaja is null "
                + "and h.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "' "
                + "and h.posicion >= '" + posicion + "' "
                + "and h.idPadre is null");
        if (!lista.isEmpty()) {
            for (CntDetalleComprobante cntDetalleComprobante : lista) {
                cntDetalleComprobante.setPosicion(cntDetalleComprobante.getPosicion() - 1);
                mergeCntDetalleComprobantes(cntDetalleComprobante);
            }
        }
    }

    public List<CntDetalleComprobante> obtieneHijosTambienPadresDeUnDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        List<CntDetalleComprobante> lista;

        if (cntDetalleComprobante.getIdPadre() != null) {
            lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.fechaBaja is null and j.cntComprobante.idComprobante=" + cntDetalleComprobante.getCntComprobante().getIdComprobante() + " "
                    + "and (j.idPadre = " + cntDetalleComprobante.getIdPadre() + " or j.idDetalleComprobante=" + cntDetalleComprobante.getIdPadre() + ")");
        } else {
            lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.fechaBaja is null and j.cntComprobante.idComprobante=" + cntDetalleComprobante.getCntComprobante().getIdComprobante() + " "
                    + "and (j.idPadre = " + cntDetalleComprobante.getIdDetalleComprobante() + " or j.idDetalleComprobante=" + cntDetalleComprobante.getIdDetalleComprobante() + ")");
        }
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public CntDetalleComprobante obtieneIdPadreDeUnDetalleComprobante(List<CntDetalleComprobante> listaCntDetalleComprobanteElegidaParaQuitarse) {
        if (listaCntDetalleComprobanteElegidaParaQuitarse != null) {
            int contador = 0;
            for (CntDetalleComprobante cntDetalleComprobante : listaCntDetalleComprobanteElegidaParaQuitarse) {
                if (cntDetalleComprobante.getIdPadre() == null) {
                    contador++;
                }
            }
            if (contador < 2) {
                if (listaCntDetalleComprobanteElegidaParaQuitarse.get(0).getIdPadre() != null) {
                    return (CntDetalleComprobante) find(CntDetalleComprobante.class, listaCntDetalleComprobanteElegidaParaQuitarse.get(0).getIdPadre());
                } else {
                    return listaCntDetalleComprobanteElegidaParaQuitarse.get(0);
                }
            }
        }
        return null;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDetalleComprobante guardaRetencionGrossing(CntDetalleComprobante cntDetalleComprobante, CntEntidad cntEntidad, String tipo) throws Exception {
        String valorCodigoRetencionGrossing = "";
        BigDecimal primerValor = new BigDecimal(BigInteger.ZERO);
        BigDecimal segundoValor = new BigDecimal(BigInteger.ZERO);
        BigDecimal porcentaje1 = new BigDecimal("1");
        BigDecimal porcentaje = new BigDecimal("100");
        BigDecimal montoGasto = new BigDecimal(BigInteger.ZERO);
        BigDecimal porcentajesDivide = new BigDecimal("0.01");
        BigDecimal montoPagar = new BigDecimal(BigInteger.ZERO);
        BigDecimal montoIue = new BigDecimal(BigInteger.ZERO);
        BigDecimal montoIT = new BigDecimal(BigInteger.ZERO);
        CntEntidad entidadIue = new CntEntidad();
        CntEntidad entidadIT = new CntEntidad();
        int valor = 0;
        CntParametroAutomatico parametroAutomatico = (CntParametroAutomatico) parametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntDetalleComprobante.getCntEntidad());
        if (tipo.equals(EnumRetencionGrossing.RETENCION.getCodigo())) {
            valorCodigoRetencionGrossing = parametroAutomatico.getParTipoRetencion().getCodigo();
        } else {
            valorCodigoRetencionGrossing = parametroAutomatico.getParTipoRetencionGrossingUp().getCodigo();
        }
        if (valorCodigoRetencionGrossing.equals(EnumTipoRetencion.BIENES.getCodigo())) {
            primerValor = new BigDecimal(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CRBI")).getValor());
            segundoValor = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IT")).getValor());
            entidadIue = (CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CTRB")).getValor()));
            entidadIT = (CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CRIT")).getValor()));
            valor = 1;
        }
        if (valorCodigoRetencionGrossing.equals(EnumTipoRetencion.SERVICIOS.getCodigo())) {
            primerValor = new BigDecimal(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CRSV")).getValor());
            segundoValor = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IT")).getValor());
            entidadIue = (CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CTRS")).getValor()));
            entidadIT = (CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CRIT")).getValor()));
            valor = 2;
        }
        if (valorCodigoRetencionGrossing.equals(EnumTipoRetencion.RC_IVA.getCodigo())) {
            primerValor = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IVA")).getValor());
            segundoValor = new BigDecimal("0");
            entidadIue = (CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CRCI")).getValor()));
            valor = 3;
        }
        if (valorCodigoRetencionGrossing.equals(EnumTipoRetencion.ALQUILERES.getCodigo())) {
            primerValor = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IVA")).getValor());
            segundoValor = new BigDecimal(((ParComprasYVentas) parParametricasService.find(ParComprasYVentas.class, "IT")).getValor());
            entidadIue = (CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CRCI")).getValor()));
            entidadIT = (CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CRIT")).getValor()));
            valor = 4;
        }
        if (valorCodigoRetencionGrossing.equals(EnumTipoRetencion.REMESAS_AL_EXTERIOR.getCodigo())) {
            primerValor = new BigDecimal(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CRSV")).getValor());
            segundoValor = new BigDecimal("0");
            entidadIue = (CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CRAE")).getValor()));
            valor = 5;
        }
        if (valorCodigoRetencionGrossing.equals(EnumTipoRetencion.IUE_IT_IND_EXPORTADOR.getCodigo())) {
            primerValor = new BigDecimal(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "RSIE")).getValor());
            segundoValor = new BigDecimal("0");
            entidadIue = (CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParRetencionIue) parParametricasService.find(ParRetencionIue.class, "CRSI")).getValor()));
            valor = 6;
        }
        if (tipo.equals(EnumRetencionGrossing.RETENCION.getCodigo())) {
            switch (valor) {
                case 1:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.RETENCION_BIENES.getCodigo());
                    break;
                case 2:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.RETENCION_SERVICIOS.getCodigo());
                    break;
                case 3:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.RETENCION_RC_IVA.getCodigo());
                    break;
                case 4:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.RETENCION_ALQUILERES.getCodigo());
                    break;
                case 5:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.RETENCION_REMESAS_AL_EXTERIOR.getCodigo());
                    break;
                case 6:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.RETENCION_IUE_IT_IND_EXPORTADOR.getCodigo());
                    break;

                default:
                    break;
            }
            montoGasto = cntDetalleComprobante.getDebe();
            montoPagar = (cntDetalleComprobante.getDebe().multiply(porcentaje1.subtract((primerValor.add(segundoValor)).divide(porcentaje)))).setScale(2, BigDecimal.ROUND_HALF_UP);
            montoIue = ((cntDetalleComprobante.getDebe().subtract(cntDetalleComprobante.getDebe().multiply(porcentaje1.subtract((primerValor).divide(porcentaje))))).setScale(2, BigDecimal.ROUND_HALF_UP));
            montoIT = ((cntDetalleComprobante.getDebe().subtract(cntDetalleComprobante.getDebe().multiply(porcentaje1.subtract((segundoValor).divide(porcentaje))))).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (tipo.equals(EnumRetencionGrossing.GROSSING.getCodigo())) {
            switch (valor) {
                case 1:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.GROSSING_BIENES.getCodigo());
                    break;
                case 2:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.GROSSING_SERVICIOS.getCodigo());
                    break;
                case 3:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.GROSSING_RC_IVA.getCodigo());
                    break;
                case 4:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.GROSSING_ALQUILERES.getCodigo());
                    break;
                case 5:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.GROSSING_REMESAS_AL_EXTERIOR.getCodigo());
                    break;
                case 6:
                    cntDetalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.GROSSING_IUE_IT_IND_EXPORTADOR.getCodigo());
                    break;
                default:
                    break;
            }
            montoGasto = (cntDetalleComprobante.getDebe().divide(porcentaje1.subtract((primerValor.add(segundoValor)).multiply(porcentajesDivide)), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP);
            montoPagar = cntDetalleComprobante.getDebe();
            montoIue = ((montoGasto.subtract(montoGasto.multiply(porcentaje1.subtract((primerValor).divide(porcentaje))))).setScale(2, BigDecimal.ROUND_HALF_UP));
            montoIT = ((montoGasto.subtract(montoGasto.multiply(porcentaje1.subtract((segundoValor).divide(porcentaje))))).setScale(2, BigDecimal.ROUND_HALF_UP));
            montoGasto = montoPagar.add(montoIT).add(montoIue);
        }
        cntDetalleComprobante.setDebe(montoGasto);
        cntDetalleComprobante.setHaber(null);
        cntDetalleComprobante.setDebeDolar((montoGasto.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
        cntDetalleComprobante.setHaberDolar(null);
        try {
            persistCntDetalleComprobantes(cntDetalleComprobante);
            cntDetalleComprobante = persistCntDetalleComprobantes(cntDetalleComprobante);
        } catch (Exception ex) {
            throw ex;
        }
        CntDetalleComprobante detalleComprobantePagar;
        detalleComprobantePagar = (CntDetalleComprobante) cntDetalleComprobante.clone();
        detalleComprobantePagar.setIdDetalleComprobante(null);
        detalleComprobantePagar.setCntEntidad(cntEntidad);
        detalleComprobantePagar.setDebe(null);
        detalleComprobantePagar.setHaber(montoPagar);
        detalleComprobantePagar.setDebeDolar(null);
        detalleComprobantePagar.setHaberDolar((montoPagar.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
        detalleComprobantePagar.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
        detalleComprobantePagar.setPosicion(null);
        detalleComprobantePagar.setIdAntecesor(null);
        detalleComprobantePagar.setTransaccionRealizada(null);
        try {
            persistCntDetalleComprobantes(detalleComprobantePagar);
        } catch (Exception ex) {
            throw ex;
        }
        CntDetalleComprobante detalleComprobanteIue;
        detalleComprobanteIue = (CntDetalleComprobante) cntDetalleComprobante.clone();
        detalleComprobanteIue.setIdDetalleComprobante(null);
        detalleComprobanteIue.setCntEntidad(entidadIue);
        detalleComprobanteIue.setDebe(null);
        detalleComprobanteIue.setHaber(montoIue);
        detalleComprobanteIue.setDebeDolar(null);
        detalleComprobanteIue.setHaberDolar((montoIue.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
        detalleComprobanteIue.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
        detalleComprobanteIue.setPosicion(null);
        detalleComprobanteIue.setIdAntecesor(null);
        detalleComprobanteIue.setTransaccionRealizada(null);
        try {
            persistCntDetalleComprobantes(detalleComprobanteIue);
        } catch (Exception ex) {
            throw ex;
        }

        if (entidadIT.getIdEntidad() != null) {
            CntDetalleComprobante detalleComprobanteIt;
            detalleComprobanteIt = (CntDetalleComprobante) cntDetalleComprobante.clone();
            detalleComprobanteIt.setIdDetalleComprobante(null);
            detalleComprobanteIt.setCntEntidad(entidadIT);
            detalleComprobanteIt.setDebe(null);
            detalleComprobanteIt.setHaber(montoIT);
            detalleComprobanteIt.setDebeDolar(null);
            detalleComprobanteIt.setHaberDolar((montoIT.divide(cntDetalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
            detalleComprobanteIt.setIdPadre(cntDetalleComprobante.getIdDetalleComprobante());
            detalleComprobanteIt.setPosicion(null);
            detalleComprobanteIt.setIdAntecesor(null);
            detalleComprobanteIt.setTransaccionRealizada(null);
            try {
                persistCntDetalleComprobantes(detalleComprobanteIt);
            } catch (Exception ex) {
                throw ex;
            }
        }
        return cntDetalleComprobante;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDetalleComprobante guardaRetencionGrossingModifica(CntDetalleComprobante cntDetalleComprobante, CntEntidad cntEntidad, String tipo) throws Exception {
        CntDetalleComprobante detalleComprobante = (CntDetalleComprobante) cntDetalleComprobante.clone();
        detalleComprobante.setIdAntecesor(cntDetalleComprobante.getIdDetalleComprobante());
        detalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
        detalleComprobante.setUsuarioBaja(null);
        detalleComprobante.setFechaBaja(null);
        detalleComprobante.setDebeDolar(null);
        detalleComprobante.setHaberDolar(null);
        detalleComprobante.setFechaModificacion(null);
        detalleComprobante.setUsuarioModificacion(null);
        detalleComprobante.setUsuarioAlta(cntDetalleComprobante.getUsuarioModificacion());
        detalleComprobante.setFechaAlta(cntDetalleComprobante.getFechaModificacion());
        detalleComprobante.setIdDetalleComprobante(null);
        guardaRetencionGrossing(detalleComprobante, cntEntidad, tipo);
        if (cntDetalleComprobante.getIdAntecesor() != null && cntDetalleComprobante.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
            detalleComprobante.setIdAntecesor(cntDetalleComprobante.getIdAntecesor());
            mergeCntDetalleComprobantes(detalleComprobante);
            cntDetalleComprobante = find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante());
            deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
        } else {
            if (cntDetalleComprobante.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
                detalleComprobante.setIdAntecesor(null);
                detalleComprobante = mergeCntDetalleComprobantes(detalleComprobante);
            } else {
                cntDetalleComprobante.setGlosa(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getGlosa());
                cntDetalleComprobante.setEstado(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getEstado());
                cntDetalleComprobante.setLoginUsuario(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getLoginUsuario());
                cntDetalleComprobante.setTransaccionRealizada(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getTransaccionRealizada());
                removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
            }
        }
        CntComprobante comprobante = cntComprobantesService.find(CntComprobante.class, cntDetalleComprobante.getCntComprobante().getIdComprobante());
        comprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntComprobantesService.merge(comprobante);
        return detalleComprobante;
    }

//    public List<CntLibroMayor> listaLibroMayorSegunCuenta(CntEntidad cntEntidad, Date fechaInicial, Date fechaFinal) {
//        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
//        String fechaInicialConsulta = formato.format(fechaInicial);
//        String fechaFinalConsulta = formato.format(fechaFinal.getTime() + 86400000L);
//        List<CntLibroMayor> lista = (List<CntLibroMayor>) hibernateTemplate.find(""
//                + "select j "
//                + "from CntLibroMayor j "
//                + "where j.identidad = '" + cntEntidad.getIdEntidad() + "' "
//                + "and j.fecha >= '" + fechaInicialConsulta + "' "
//                + "and j.fecha <= '" + fechaFinalConsulta + "' "
//                + "order by j.mascaraGenerada, j.fecha ASC");
//        BigDecimal sumaSaldo = sumaSaldoInicialLibroMayor(cntEntidad, fechaInicial);
//        for (CntLibroMayor cntLibroMayor : lista) {
//            if (cntLibroMayor.getDebe() == null) {
//                cntLibroMayor.setDebe(new BigDecimal(0));
//            }
//            if (cntLibroMayor.getHaber() == null) {
//                cntLibroMayor.setHaber(new BigDecimal(0));
//            }
//            cntLibroMayor.setSaldo(sumaSaldo.add(cntLibroMayor.getDebe()).subtract(cntLibroMayor.getHaber()));
//            sumaSaldo = cntLibroMayor.getSaldo();
//        }
//        if (!lista.isEmpty()) {
//            return lista;
//        }
//        return Collections.EMPTY_LIST;
//    }
    public List<CntLibroMayor> listaLibroMayorSegunCuenta(CntEntidad cntEntidad, Date fechaInicial, Date fechaFinal) throws Exception {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
            String fechaInicialConsulta = formato.format(fechaInicial);
            String fechaFinalConsulta = formato.format(fechaFinal.getTime() + 86400000L);
            List<CntLibroMayor> listaF = new ArrayList<CntLibroMayor>();
            List<CntLibroMayor> lista = (List<CntLibroMayor>) hibernateTemplate.find(""
                    + "select j "
                    + "from CntLibroMayor j "
                    + "where j.identidad = '" + cntEntidad.getIdEntidad() + "' "
                    + "and j.fecha >= '" + fechaInicialConsulta + "' "
                    + "and j.fecha <= '" + fechaFinalConsulta + "' "
                    + "order by j.mascaraGenerada, j.fecha ASC");
            BigDecimal sumaSaldo = sumaSaldoInicialLibroMayor(cntEntidad, fechaInicial);
            BigDecimal sumaHaber = sumaHaberParaLibroMayor(cntEntidad, fechaInicial);
            BigDecimal saldoInicial = sumaSaldo.subtract(sumaHaber);
            BigDecimal sumaDebeDolar = sumadebeDolarInicialLibroMayor(cntEntidad, fechaInicial);
            BigDecimal sumaHaberDolar = sumaHaberDolarParaLibroMayor(cntEntidad, fechaInicial);
            BigDecimal saldoInicialDolar = sumaDebeDolar.subtract(sumaHaberDolar);
            List<CntLibroMayor> listaInicial = listaInicial(saldoInicial, saldoInicialDolar);
            for (CntLibroMayor cntLibroMayor : lista) {
                if (cntLibroMayor.getDebe() == null) {
                    cntLibroMayor.setDebe(new BigDecimal(0));
                }
                if (cntLibroMayor.getHaber() == null) {
                    cntLibroMayor.setHaber(new BigDecimal(0));
                }

                if (cntLibroMayor.getDebeDolar() == null) {
                    cntLibroMayor.setDebeDolar(new BigDecimal(0));
                }
                if (cntLibroMayor.getHaberDolar() == null) {
                    cntLibroMayor.setHaberDolar(new BigDecimal(0));
                }
                cntLibroMayor.setSaldo(saldoInicial.add(cntLibroMayor.getDebe()).subtract(cntLibroMayor.getHaber()));
                saldoInicial = cntLibroMayor.getSaldo();
                cntLibroMayor.setSaldoDolar(saldoInicialDolar.add(cntLibroMayor.getDebeDolar()).subtract(cntLibroMayor.getHaberDolar()));
                saldoInicialDolar = cntLibroMayor.getSaldoDolar();
            }
            if (!lista.isEmpty()) {
                listaF.addAll(listaInicial);
                listaF.addAll(lista);
                return listaF;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntLibroMayor> listaInicial(BigDecimal saldoInicial, BigDecimal saldoInicialDolar) throws Exception {
        List<CntLibroMayor> liInicial = new ArrayList<CntLibroMayor>();
        CntLibroMayor cntLib = new CntLibroMayor();
        cntLib.setGlosa("SALDO INICIAL ");
        cntLib.setSaldo(saldoInicial);
        cntLib.setSaldoDolar(saldoInicialDolar);
        liInicial.add(cntLib);
        return liInicial;
    }

    public BigDecimal sumaSaldoInicialLibroMayor(final CntEntidad cntEntidad, Date fechaInicial) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(fechaInicial);
//        c.roll(Calendar.DAY_OF_YEAR, -1);
//        fechaInicial = c.getTime();//Restamos 1 dia
        fechaInicial = devuelveFecha(fechaInicial);
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        final String fechaInicialConsulta = formato.format(fechaInicial);

        BigDecimal suma = (BigDecimal) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String q = ""
                        + "select sum(j.debe) as suma "
                        + "from CntLibroMayor j "
                        + "where j.identidad = '" + cntEntidad.getIdEntidad() + "' "
                        + "and j.fecha <= '" + fechaInicialConsulta + "' ";
                Query query = session.createQuery(q);
                BigDecimal s = (BigDecimal) query.uniqueResult();
                return s;
            }
        });
        if (suma == null) {
            suma = new BigDecimal(0);
        }
        return suma;
    }

    public String encuentraTipoComprobante(CntComprobante cntComprobante) {
        String remitente = "";
        if (cntComprobante.getParTipoComprobante().getCodigo().equals(EnumTipoComprobante.EGRESO.getCodigo())) {
            remitente = "Pagado A:";
        } else {
            if (cntComprobante.getParTipoComprobante().getCodigo().equals(EnumTipoComprobante.INGRESO.getCodigo())) {
                remitente = "Recibido De::";
            } else {
                if (cntComprobante.getParTipoComprobante().getCodigo().equals(EnumTipoComprobante.TRASPASO.getCodigo())) {
                    remitente = "Concepto:";
                }
            }
        }
        return remitente;
    }

    public String cambiaFormatoMoneda(BigDecimal valor) {
        if (valor == null) {
            valor = new BigDecimal(BigInteger.ZERO);
        }
        Locale locale = new Locale("en", "US");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(valor).substring(1, nf.format(valor).length());
    }

    public CntFacturacion buscaFacturacionPorDetalleComprobantePadres(CntDetalleComprobante cntDetalleComprobante) {
        List<CntFacturacion> lista = hibernateTemplate.find(""
                //                + "select j "
                //                + "from CntFacturacion j "
                //                + "where j.fechaBaja is null "
                //                + "and j.cntDetalleComprobante.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante());
                + "select j.cntFacturacion "
                + "from CntDetalleComprobante j "
                + "where j.cntFacturacion.fechaBaja is null "
                + "and j.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante());
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public CntDetalleComprobante obtieneMontoOriginal(final CntDetalleComprobante cntDetalleComprobante) throws Exception {
        try {
            if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo()) || cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.CREDITO_FISCAL_NO_DEDUCIBLE.getCodigo())) {
                BigDecimal sumaDebe = (BigDecimal) hibernateTemplate.execute(new HibernateCallback() {
                    public Object doInHibernate(Session session) throws HibernateException, SQLException {
                        String q = ""
                                + "select sum(j.debe) as suma "
                                + "from CntDetalleComprobante j "
                                + "where j.fechaBaja is null "
                                + "and j.idPadre = '" + cntDetalleComprobante.getIdDetalleComprobante() + "' ";
                        Query query = session.createQuery(q);
                        BigDecimal s = (BigDecimal) query.uniqueResult();
                        return s.add(cntDetalleComprobante.getDebe());
                    }
                });
                if (sumaDebe != null) {
                    cntDetalleComprobante.setDebe(sumaDebe);
                }
                return cntDetalleComprobante;
            } else {
                if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo())) {
                    List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                            + "select j "
                            + "from CntDetalleComprobante j "
                            + "where j.fechaBaja is null "
                            + "and j.idPadre = '" + cntDetalleComprobante.getIdDetalleComprobante() + "'");
                    if (!lista.isEmpty()) {
                        cntDetalleComprobante.setHaber(lista.get(0).getHaber().add(cntDetalleComprobante.getHaber()));
                    }
                    return cntDetalleComprobante;
                } else {
                    if (tipoTransaccionGrossing(cntDetalleComprobante)) {
                        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                                + "select j "
                                + "from CntDetalleComprobante j "
                                + "where j.fechaBaja is null "
                                + "and j.idPadre = '" + cntDetalleComprobante.getIdDetalleComprobante() + "'");
                        if (!lista.isEmpty()) {
                            cntDetalleComprobante.setDebe(lista.get(0).getHaber());
                        }
                        return cntDetalleComprobante;
                    } else {
                        if (tipoTransaccionGrossing(cntDetalleComprobante) || cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.SIN_FACTURA.getCodigo()) || cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.NINGUNO.getCodigo())) {
                            return cntDetalleComprobante;
                        }
                    }
                }
            }
            return cntDetalleComprobante;
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean verificaDetalleComprobanteTieneFactura(CntDetalleComprobante cntDetalleComprobante) {
        List<CntFacturacion> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntFacturacion j "
                + "where j.fechaBaja is null "
                + "and j.cntDetalleComprobante.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante() + "");
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public Boolean verificaSiExistenFacturasEnComprobante(CntComprobante cntComprobante) {
        List<CntFacturacion> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntFacturacion j "
                + "where j.fechaBaja is null "
                + "and j.cntDetalleComprobante.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "' ");
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public String tipoDeTransaccionPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        if (cntDetalleComprobante.getTransaccionRealizada() != null && cntDetalleComprobante.getIdPadre() == null) {
            if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.CREDITO_FISCAL_NO_DEDUCIBLE.getCodigo())) {
                return "Credito Fiscal No Deducible";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo())) {
                return "Factura de Compra";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo())) {
                return "Factura de Venta";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_ALQUILERES.getCodigo())) {
                return "Grossing Alquileres";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_BIENES.getCodigo())) {
                return "Grossing Bienes";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_IUE_IT_IND_EXPORTADOR.getCodigo())) {
                return "Grossing IUE-IT Ind. Exportador";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_RC_IVA.getCodigo())) {
                return "Grossing RC-IVA";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_REMESAS_AL_EXTERIOR.getCodigo())) {
                return "Grossing Remesas al Exterior";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_SERVICIOS.getCodigo())) {
                return "Grossing Servicios";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.NINGUNO.getCodigo())) {
                return "Ninguno";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_ALQUILERES.getCodigo())) {
                return "Retención Alquileres";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_BIENES.getCodigo())) {
                return "Retención Bienes";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_IUE_IT_IND_EXPORTADOR.getCodigo())) {
                return "Retención IUE-IT Ind. Exportador";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_RC_IVA.getCodigo())) {
                return "Retención RC-IVA";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_REMESAS_AL_EXTERIOR.getCodigo())) {
                return "Retención Remesas al Exterior";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_SERVICIOS.getCodigo())) {
                return "Retención Servicios";
            } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.SIN_FACTURA.getCodigo())) {
                return "Sin Factura";
            }
        }
        return "";
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDetalleComprobante persistCntDetalleComprobantesModifica(CntDetalleComprobante cntDetalleComprobante) throws Exception {

        CntDetalleComprobante detalleComprobante = (CntDetalleComprobante) cntDetalleComprobante.clone();
        //buscamos el detalle comprobante original para que no se modifique nada
        CntDetalleComprobante detalleOriginal = find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante());
        //para no perder los datos del detallecomprobante anterior   
        if (detalleOriginal.getDebe() != null) {
            cntDetalleComprobante.setDebe(detalleOriginal.getDebe());
            cntDetalleComprobante.setDebeDolar(detalleOriginal.getDebeDolar());
//            cntDetalleComprobante.setDebeDolar((detalleOriginal.getDebe().divide(detalleOriginal.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
            cntDetalleComprobante.setHaber(null);
            cntDetalleComprobante.setHaberDolar(null);
        }
        if (detalleOriginal.getHaber() != null) {
//            cntDetalleComprobante.setHaberDolar((detalleOriginal.getHaber().divide(detalleOriginal.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
            cntDetalleComprobante.setHaber(detalleOriginal.getHaber());
            cntDetalleComprobante.setHaberDolar(detalleOriginal.getHaberDolar());
//            cntDetalleComprobante.setDebeDolar((detalleOriginal.getDebe().divide(detalleOriginal.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
            cntDetalleComprobante.setDebe(null);
            cntDetalleComprobante.setDebeDolar(null);

        }
        //el detalleComprobante ya esta cargado con sus datos actuales
        try {
            detalleComprobante.setIdDetalleComprobante(null);
            detalleComprobante.setIdAntecesor(cntDetalleComprobante.getIdDetalleComprobante());
            detalleComprobante.setUsuarioBaja(null);
            detalleComprobante.setFechaBaja(null);
            detalleComprobante.setUsuarioModificacion(null);
            detalleComprobante.setFechaModificacion(null);
            if (detalleComprobante.getDebe() != null) {
                detalleComprobante.setDebeDolar((detalleComprobante.getDebe().divide(detalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            if (detalleComprobante.getHaber() != null) {
                detalleComprobante.setHaberDolar((detalleComprobante.getHaber().divide(detalleComprobante.getCntComprobante().getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            detalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            detalleComprobante.setUsuarioAlta(cntDetalleComprobante.getUsuarioModificacion());
            detalleComprobante.setFechaAlta(cntDetalleComprobante.getFechaModificacion());
            CntParametroAutomatico pa = parametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntDetalleComprobante.getCntEntidad());
            detalleComprobante.setTransaccionRealizada(pa.getTipoCalculoAutomatico().equals(EnumTipoCalculoAutomatico.NINGUNO.getCodigo()) ? EnumTransaccionRealizada.NINGUNO.getCodigo() : EnumTransaccionRealizada.SIN_FACTURA.getCodigo());
            detalleComprobante = persistCntDetalleComprobantes(detalleComprobante);
            if (cntDetalleComprobante.getIdAntecesor() != null && cntDetalleComprobante.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                detalleComprobante.setIdAntecesor(cntDetalleComprobante.getIdAntecesor());
                mergeCntDetalleComprobantes(detalleComprobante);
                CntDetalleComprobante cntDetalleComprobanteAEliminar = find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante());
                deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAEliminar);
            } else {
                cntDetalleComprobante.setGlosa(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getGlosa());
                removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
            }
            CntComprobante comprobante = cntComprobantesService.find(CntComprobante.class, detalleComprobante.getCntComprobante().getIdComprobante());
            comprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntComprobantesService.mergeCntComprobantes(comprobante);
            CntDetalleComprobante cntDetalleComprobanteAEliminar = find(CntDetalleComprobante.class, detalleComprobante.getIdAntecesor());
            if (cntDetalleComprobanteAEliminar.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAEliminar);
                detalleComprobante.setIdAntecesor(null);
                mergeCntDetalleComprobantes(detalleComprobante);
            }
        } catch (Exception h) {
            throw h;
        }
        return detalleComprobante;
    }

    public Boolean tipoTransaccionGrossing(CntDetalleComprobante cntDetalleComprobante) {
        if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_ALQUILERES.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_BIENES.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_IUE_IT_IND_EXPORTADOR.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_RC_IVA.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_REMESAS_AL_EXTERIOR.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.GROSSING_SERVICIOS.getCodigo())) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean tipoTransaccionRetenciones(CntDetalleComprobante cntDetalleComprobante) {
        if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_ALQUILERES.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_BIENES.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_IUE_IT_IND_EXPORTADOR.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_RC_IVA.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_REMESAS_AL_EXTERIOR.getCodigo())) {
            return true;
        } else if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.RETENCION_SERVICIOS.getCodigo())) {
            return true;
        } else {
            return false;
        }
    }

    public void restauraAnteriorCntDetalleComprobante(CntFacturacion cntFacturacion) throws Exception {
        CntDetalleComprobante cntDetalleComprobante = encuentraDetalleComprobantePorFacturacion(cntFacturacion);
//        CntDetalleComprobante cntDetalleComprobante = find(CntDetalleComprobante.class, cntFacturacion.getCntDetalleComprobante().getIdDetalleComprobante());
        List<CntDetalleComprobante> listaDeComprobanteCompleto = obtieneHijosTambienPadresDeUnDetalleComprobante(cntDetalleComprobante);
        CntDetalleComprobante comprobanteOriginal;
        for (CntDetalleComprobante cntDetalleComprobante1 : listaDeComprobanteCompleto) {
            comprobanteOriginal = find(CntDetalleComprobante.class, cntDetalleComprobante1.getIdAntecesor());
            cntDetalleComprobante1.setDebe(comprobanteOriginal.getDebe());
            cntDetalleComprobante1.setHaber(comprobanteOriginal.getHaber());
            cntDetalleComprobante1.setDebeDolar(comprobanteOriginal.getDebeDolar());
            cntDetalleComprobante1.setHaberDolar(comprobanteOriginal.getHaberDolar());
            cntDetalleComprobante1.setGlosa(comprobanteOriginal.getGlosa());
            try {
                mergeCntDetalleComprobantes(cntDetalleComprobante1);
            } catch (Exception ex) {
                throw ex;
            }
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDetalleComprobante guardaDetalleYFacturaPendientes(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        CntDetalleComprobante detalleComprobanteClon = (CntDetalleComprobante) cntDetalleComprobante.clone();
        detalleComprobanteClon.setIdDetalleComprobante(null);
        detalleComprobanteClon.setIdAntecesor(cntDetalleComprobante.getIdDetalleComprobante());
        detalleComprobanteClon.setEstado(EnumEstado.PENDIENTE.getCodigo());
        detalleComprobanteClon = persistCntDetalleComprobantes(detalleComprobanteClon);

        if (cntDetalleComprobante.getIdAntecesor() != null && cntDetalleComprobante.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
            detalleComprobanteClon.setIdAntecesor(cntDetalleComprobante.getIdAntecesor());
            mergeCntDetalleComprobantes(detalleComprobanteClon);
            deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
        } else {
            removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
        }
        CntFacturacion cntFacturacion = (CntFacturacion) cntFacturacionService.obtieneFacturaPorDetalleComprobante((CntDetalleComprobante) find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor())).clone();
        cntFacturacion.setIdAntecesor(cntFacturacion.getIdFacturacion());
        cntFacturacion.setIdFacturacion(null);
        cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntFacturacionService.persistCntFacturacion(cntFacturacion);
        return cntDetalleComprobante;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDetalleComprobante guardaDetalleComprobanteLimpiandoAnteriores(CntDetalleComprobante cntDetalleComprobante) throws Exception {

        CntDetalleComprobante detalleComprobanteClon = (CntDetalleComprobante) cntDetalleComprobante.clone();
        detalleComprobanteClon.setIdDetalleComprobante(null);
        detalleComprobanteClon.setIdAntecesor(cntDetalleComprobante.getIdDetalleComprobante());
        detalleComprobanteClon.setEstado(EnumEstado.PENDIENTE.getCodigo());
        detalleComprobanteClon.setUsuarioBaja(null);
        detalleComprobanteClon.setFechaBaja(null);
        detalleComprobanteClon = persistCntDetalleComprobantes(detalleComprobanteClon);
        return detalleComprobanteClon;
    }

    public List<CntDetalleComprobante> listaDeDetalleComprobantesPadresPorComprobanteSinFechaBaja(CntComprobante cntComprobante) throws Exception {
        try {
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "' "
                    + "and j.idPadre is null");
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception h) {
            throw h;
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDetalleComprobante guardaDetalleComprobanteYDaDeBajaAlAntecesor(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        persistCntDetalleComprobantes(cntDetalleComprobante);
        CntDetalleComprobante cntDetalleComprobanteAntecesor = find(CntDetalleComprobante.class, cntDetalleComprobante.getIdAntecesor());
        removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAntecesor);
        return cntDetalleComprobante;
    }

    public List<CntDetalleComprobante> obtieneListaDePendientesDetalleComprobantePadres(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.fechaBaja is null "
                + "and h.idPadre is null "
                + "and h.estado = '" + EnumEstado.PENDIENTE.getCodigo() + "' "
                + "and h.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante());
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntDetalleComprobante> obtieneListaDePendientesYQuitadosDetalleComprobantePadres(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where ((h.fechaBaja is null "
                + "and h.estado = '" + EnumEstado.PENDIENTE.getCodigo() + "') or h.estado = '" + EnumEstado.QUITADO_CONFIRMADO.getCodigo() + "' )"
                + "and h.idPadre is null "
                + "and h.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante());
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removePendientesDetalleComprobanteCancelar(CntComprobante cntComprobante) throws Exception {
        try {
            CntDetalleComprobante cdc;
            Boolean sw = true;
            for (CntDetalleComprobante detalleComprobante : obtieneListaDePendientesYQuitadosDetalleComprobantePadres(cntComprobante)) {
                if (detalleComprobante.getIdAntecesor() != null && detalleComprobante.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                    cdc = find(CntDetalleComprobante.class, detalleComprobante.getIdAntecesor());
                    cdc.setUsuarioModificacion(null);
                    cdc.setFechaModificacion(null);
                    cdc.setUsuarioBaja(null);
                    cdc.setFechaBaja(null);
                    List<CntDistribucionCentrocosto> listaCntDistribucionCC = cntDistribucionCentrocostoService.listaDistribucionCCPorDetalleComprobanteConFechaBaja(cdc);
                    if (!listaCntDistribucionCC.isEmpty()) {
                        for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaCntDistribucionCC) {
                            cntDistribucionCentrocosto.setUsuarioModificacion(null);
                            cntDistribucionCentrocosto.setFechaModificacion(null);
                            cntDistribucionCentrocosto.setUsuarioBaja(null);
                            cntDistribucionCentrocosto.setFechaBaja(null);
                            cntDistribucionCentrocostoService.mergeCntDistribucionCentrocosto(cntDistribucionCentrocosto);
                        }
                    }
                    List<CntDetalleComprobante> listaDeDependientesDeDetalle = listaHijosPorPadreParaDelete(cdc);
                    for (CntDetalleComprobante cntDetalleComprobante : listaDeDependientesDeDetalle) {
                        listaCntDistribucionCC = cntDistribucionCentrocostoService.listaDistribucionCCPorDetalleComprobanteConFechaBaja(cntDetalleComprobante);
                        if (!listaCntDistribucionCC.isEmpty()) {
                            for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaCntDistribucionCC) {
                                cntDistribucionCentrocosto.setUsuarioModificacion(null);
                                cntDistribucionCentrocosto.setFechaModificacion(null);
                                cntDistribucionCentrocosto.setUsuarioBaja(null);
                                cntDistribucionCentrocosto.setFechaBaja(null);
                                cntDistribucionCentrocostoService.mergeCntDistribucionCentrocosto(cntDistribucionCentrocosto);
                            }
                        }
                    }
                    mergeCntDetalleComprobantesModificaTotal(cdc);
                    sw = false;
                    CntDetalleComprobante detalle = find(CntDetalleComprobante.class, detalleComprobante.getIdDetalleComprobante());
                    deleteCntDetalleComprobantesCntFacturacion(detalle);
                } else if (detalleComprobante.getEstado().equals(EnumEstado.QUITADO_CONFIRMADO.getCodigo())) {
                    detalleComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                    detalleComprobante.setUsuarioModificacion(null);
                    detalleComprobante.setFechaModificacion(null);
                    detalleComprobante.setUsuarioBaja(null);
                    detalleComprobante.setFechaBaja(null);
                    mergeCntDetalleComprobantesModificaTotal(detalleComprobante);
                } else {
                    CntDetalleComprobante detalle = find(CntDetalleComprobante.class, detalleComprobante.getIdDetalleComprobante());
                    deleteCntDetalleComprobantesCntFacturacion(detalle);
                }
            }
            CntComprobante comprobante = cntComprobantesService.find(CntComprobante.class, cntComprobante.getIdComprobante());
            if (!cntComprobantesService.verificaDetalleComprobanteConfirmadosPorComprobante(cntComprobante)) {
                if (sw && comprobante.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                    cntComprobantesService.deleteCntComprobantes(comprobante);
                } else {
                    comprobante.setUsuarioModificacion(null);
                    comprobante.setFechaModificacion(null);
                    comprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                    cntComprobantesService.mergeCntComprobantes(comprobante);
                }
            } else if (cntComprobantesService.verificaDetalleComprobanteConfirmadosPorComprobante(cntComprobante)) {
                cntComprobantesService.deleteCntComprobantes(comprobante);
            } else {
                comprobante.setUsuarioModificacion(null);
                comprobante.setFechaModificacion(null);
                comprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                cntComprobantesService.mergeCntComprobantes(comprobante);
            }
            reestablecePosicionesAnteriores(cntComprobante);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntDetalleComprobante persistCntDetalleComprobantesModificaCreditoFiscalNoDeducible(CntDetalleComprobante cntDetalleComprobante) throws Exception {
        CntDetalleComprobante cntDetalleComprobanteAuxiliar = (CntDetalleComprobante) cntDetalleComprobante.clone();
        try {
            cntDetalleComprobanteAuxiliar.setIdDetalleComprobante(null);
            cntDetalleComprobanteAuxiliar.setUsuarioBaja(null);
            cntDetalleComprobanteAuxiliar.setFechaBaja(null);
            cntDetalleComprobanteAuxiliar.setUsuarioModificacion(null);
            cntDetalleComprobanteAuxiliar.setFechaModificacion(null);
            cntDetalleComprobanteAuxiliar.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntDetalleComprobanteAuxiliar.setTransaccionRealizada(EnumTransaccionRealizada.CREDITO_FISCAL_NO_DEDUCIBLE.getCodigo());
            cntDetalleComprobanteAuxiliar.setUsuarioAlta(cntDetalleComprobante.getUsuarioModificacion());
            cntDetalleComprobanteAuxiliar.setFechaAlta(cntDetalleComprobante.getFechaModificacion());
            cntDetalleComprobanteAuxiliar = persistCntDetalleComprobantes(cntDetalleComprobanteAuxiliar);
            cntDetalleComprobante.setGlosa(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getGlosa());
            cntDetalleComprobante.setDebe(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getDebe());
            mergeCntDetalleComprobantes(cntDetalleComprobante);
            guardaCreditoFiscalNoDeducible(cntDetalleComprobanteAuxiliar);
            if (cntDetalleComprobante.getIdAntecesor() != null && cntDetalleComprobante.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                cntDetalleComprobanteAuxiliar.setIdAntecesor(cntDetalleComprobante.getIdDetalleComprobante());
                mergeCntDetalleComprobantes(cntDetalleComprobanteAuxiliar);
                CntDetalleComprobante cntDetalleComprobanteAEliminar = find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante());
                cntDetalleComprobante.setGlosa(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getGlosa());
                cntDetalleComprobante.setDebe(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getDebe());
                mergeCntDetalleComprobantes(cntDetalleComprobante);
                deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobanteAEliminar);
            } else {

                if (cntDetalleComprobante.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                    deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
                } else {
                    cntDetalleComprobanteAuxiliar.setIdAntecesor(cntDetalleComprobante.getIdDetalleComprobante());
                    mergeCntDetalleComprobantes(cntDetalleComprobanteAuxiliar);
                    removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
                }
                if (find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()) != null) {
                    cntDetalleComprobante.setGlosa(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getGlosa());
                    cntDetalleComprobante.setDebe(find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante()).getDebe());
                    mergeCntDetalleComprobantes(cntDetalleComprobante);
                }

            }
            CntComprobante comprobante = cntComprobantesService.find(CntComprobante.class, cntDetalleComprobante.getCntComprobante().getIdComprobante());
            comprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntComprobantesService.merge(comprobante);
        } catch (Exception h) {
            throw h;
        }
        return cntDetalleComprobanteAuxiliar;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void quitarCntDetalleComprobantes(List<CntDetalleComprobante> listaCntDetalleComprobanteElegidaParaQuitarse, CntComprobante cntComprobante) throws Exception {
        for (CntDetalleComprobante cntDetalleComprobante1 : listaCntDetalleComprobanteElegidaParaQuitarse) {
            if (cntDetalleComprobante1.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo()) && cntDetalleComprobante1.getIdPadre() == null) {
                CntDetalleComprobante detalleComprobante = find(CntDetalleComprobante.class, cntDetalleComprobante1.getIdDetalleComprobante());
                detalleComprobante.setEstado(EnumEstado.QUITADO_CONFIRMADO.getCodigo());
                detalleComprobante.setUsuarioBaja(cntComprobante.getUsuarioBaja());
                detalleComprobante.setFechaBaja(cntComprobante.getFechaBaja());
                removeCntDetalleComprobantesCntFacturacionQuitar(detalleComprobante);
                reducePosicionAPadres(cntComprobante, detalleComprobante.getPosicion());
            } else {
                if (cntDetalleComprobante1.getEstado().equals(EnumEstado.PENDIENTE.getCodigo()) && cntDetalleComprobante1.getIdPadre() == null) {
                    CntDetalleComprobante detalleComprobante = find(CntDetalleComprobante.class, cntDetalleComprobante1.getIdDetalleComprobante());
                    deleteCntDetalleComprobantesCntFacturacion(detalleComprobante);
                    reducePosicionAPadres(cntComprobante, detalleComprobante.getPosicion());
                }
            }
        }
    }

    public List<CntDetalleComprobante> listaDetalleComprobantesPorComprobanteTambienFechaBajaSoloPadres(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.idPadre is null "
                + "and h.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante());
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void cambiaEstadoAnuladoTodoElComprobanteMasLasTablasDependientes(CntComprobante cntComprobante) throws Exception {
        try {
            for (CntDetalleComprobante detalleComprobante : listaDetalleComprobantesPorComprobanteTambienFechaBajaSoloPadres(cntComprobante)) {
                CntDetalleComprobante cdc = find(CntDetalleComprobante.class, detalleComprobante.getIdDetalleComprobante());
                cdc.setUsuarioModificacion(cntComprobante.getUsuarioModificacion());
                cdc.setFechaModificacion(cntComprobante.getFechaModificacion());
                cambiaEstadoAnuladoFactura(cdc);
                cambiaEstadoAnuladoDetalleComprobanteMasLasTablasDependientesSoloHijos(cdc);
            }
            cntComprobante.setEstado(EnumEstado.ANULADO.getCodigo());
            cntComprobantesService.mergeCntComprobantes(cntComprobante);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void cambiaEstadoAnuladoDetalleComprobanteMasLasTablasDependientesSoloHijos(CntDetalleComprobante detalleComprobante) throws Exception {
        try {
            for (CntDetalleComprobante cntDetalleComprobante : listaHijosPorPadreParaDelete(detalleComprobante)) {
                CntDetalleComprobante cdc = find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante());
                cdc.setEstado(EnumEstado.ANULADO.getCodigo());
                cdc.setUsuarioModificacion(detalleComprobante.getUsuarioModificacion());
                cdc.setFechaModificacion(detalleComprobante.getFechaModificacion());
                mergeCntDetalleComprobantes(cdc);
            }
            CntDetalleComprobante cdc = find(CntDetalleComprobante.class, detalleComprobante.getIdDetalleComprobante());
            cdc.setEstado(EnumEstado.ANULADO.getCodigo());
            mergeCntDetalleComprobantes(cdc);
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void cambiaEstadoAnuladoFactura(CntDetalleComprobante detalleComprobante) throws Exception {
        try {
            CntFacturacion cntFacturacion = (CntFacturacion) cntFacturacionService.obtieneFacturaPorDetalleComprobante((CntDetalleComprobante) find(CntDetalleComprobante.class, detalleComprobante.getIdDetalleComprobante()));
            if (cntFacturacion != null) {
                cntFacturacion.setNit(0L);
                cntFacturacion.setRazonSocial(EnumEstado.ANULADO.toString());
                cntFacturacion.setMonto(BigDecimal.ZERO);
                cntFacturacion.setMontoSegMoneda(BigDecimal.ZERO);
                cntFacturacion.setExcento(BigDecimal.ZERO);
                cntFacturacion.setExcentoSegMoneda(BigDecimal.ZERO);
                cntFacturacion.setIce(BigDecimal.ZERO);
                cntFacturacion.setIceSegMoneda(BigDecimal.ZERO);
                cntFacturacion.setIva(BigDecimal.ZERO);
                cntFacturacion.setIvaSegMoneda(BigDecimal.ZERO);
                cntFacturacion.setEstado(EnumEstado.ANULADO.getCodigo());
                cntFacturacion.setUsuarioModificacion(detalleComprobante.getUsuarioModificacion());
                cntFacturacion.setFechaModificacion(detalleComprobante.getFechaModificacion());
                cntFacturacionService.mergeCntFacturacion(cntFacturacion);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void cambiaDetalleComprobanteIdModificacion(CntComprobante cntComprobante) throws Exception {
        try {
            CntComprobante cntComprobanteOriginal = (CntComprobante) cntComprobantesService.find(CntComprobante.class, cntComprobante.getIdAntecesor());
            for (CntDetalleComprobante cntDetalleComprobanteList : cntComprobantesService.obtieneDetalleComprobantePadresParaDelete(cntComprobanteOriginal)) {
                CntDetalleComprobante detalleComprobante = find(CntDetalleComprobante.class, cntDetalleComprobanteList.getIdDetalleComprobante());
                CntFacturacion cntFacturacionAux = cntFacturacionService.obtieneFacturaPorDetalleComprobante(detalleComprobante);
                if (cntFacturacionAux != null) {
                    cntFacturacionService.cambiaEstadoFacturaModificacion(detalleComprobante);
                }
                cntDistribucionCentrocostoService.cambiaEstadoDistribucionCentrocostoModificacion(detalleComprobante);
                detalleComprobante.setCntComprobante(cntComprobante);
                detalleComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                cambiaEstadoDependientesSoloHijosModificado(detalleComprobante);
                mergeCntDetalleComprobantes(detalleComprobante);
            }
        } catch (Exception e) {
            throw e;

        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void cambiaEstadoDependientesSoloHijosModificado(CntDetalleComprobante detalleComprobante) throws Exception {
        try {
            for (CntDetalleComprobante cntDetalleComprobante : listaHijosPorPadreParaDelete(detalleComprobante)) {
                CntDetalleComprobante cdc = find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante());
                cdc.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                cdc.setUsuarioModificacion(detalleComprobante.getUsuarioModificacion());
                cdc.setFechaModificacion(detalleComprobante.getFechaModificacion());
                cdc.setCntComprobante(detalleComprobante.getCntComprobante());
                mergeCntDetalleComprobantes(cdc);
                cntDistribucionCentrocostoService.cambiaEstadoDistribucionCentrocostoModificacion(cdc);
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntDetalleComprobante> listaDetalleComprobantesPorComprobanteReporte(Long nroComprobanteInicio, Long nroComprobanteFinal, String periodo, String anio, String tipoComprobante) throws Exception {
        try {
            List<CntDetalleComprobante> listaDeDetallesComprobantesFinal = new ArrayList<CntDetalleComprobante>();
            List<CntComprobante> listaDeComprobantes = cntComprobantesService.listaComprobantesEnUnRango(nroComprobanteInicio, nroComprobanteFinal, periodo, anio, tipoComprobante);
            if (!listaDeComprobantes.isEmpty()) {
                List<CntDetalleComprobante> listaDeCntDetallesComprobantes;
                CntDetalleComprobante detalleComprobanteParaTitulo = new CntDetalleComprobante();
                CntDetalleComprobante test2;
                for (CntComprobante cntComprobante : listaDeComprobantes) {
                    detalleComprobanteParaTitulo.setGlosa("COMPROBANTE NRO " + cntComprobante.getNumero() + " ''" + cntComprobante.getDescripcion() + "'' ");
                    test2 = (CntDetalleComprobante) detalleComprobanteParaTitulo.clone();
                    listaDeDetallesComprobantesFinal.add(test2);
                    listaDeCntDetallesComprobantes = listaDetalleComprobantePorComprobanteEnOrden(cntComprobante);
                    listaDeDetallesComprobantesFinal.addAll(listaDeCntDetallesComprobantes);
                }
            }
            if (!listaDeDetallesComprobantesFinal.isEmpty()) {
                return listaDeDetallesComprobantesFinal;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception ex) {
            throw ex;
        }
    }
//modificado para listar sin la cuenta de dolar

    public List<CntDetalleComprobante> listaDetalleComprobantePorComprobanteEnOrden(CntComprobante cntComprobante) throws Exception {
        if (parParametricasService.find(ParCuentasDeAjuste.class, "DCA3") == null) {

        }
        CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "DCA3")).getValor()));
        List<CntDetalleComprobante> listaFinal = new ArrayList<CntDetalleComprobante>();
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null and j.cntEntidad.idEntidad<>'" + cntEntidad.getIdEntidad() + "' "
                + "and j.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "' "
                + "and j.idPadre = null "
                + "order by j.posicion ASC");
        if (!lista.isEmpty()) {
            for (CntDetalleComprobante cntDetalleComprobante : lista) {
                listaFinal.add(cntDetalleComprobante);
                listaFinal.addAll(listaHijosPorPadre(cntDetalleComprobante));
            }
        }
        if (!listaFinal.isEmpty()) {
            return listaFinal;
        }
        return Collections.EMPTY_LIST;

    }
    //aumentado para filtrar la diferencia de tipo de cambio

    public List<CntDetalleComprobante> listaDetalleComprobantePorComprobanteEnOrdenDolar(CntComprobante cntComprobante) throws Exception {
//        CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "DCA3")).getValor()));
        List<CntDetalleComprobante> listaFinal = new ArrayList<CntDetalleComprobante>();
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "' "
                + "and j.idPadre = null "
                + "order by j.posicion ASC");
        if (!lista.isEmpty()) {
            for (CntDetalleComprobante cntDetalleComprobante : lista) {
                listaFinal.add(cntDetalleComprobante);
                listaFinal.addAll(listaHijosPorPadre(cntDetalleComprobante));
            }
        }
        if (!listaFinal.isEmpty()) {
            return listaFinal;
        }
        return Collections.EMPTY_LIST;

    }

    public List<CntDetalleComprobante> detalleComprobantePorFechaList(Date fechaConsulta) throws Exception {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
            String fechaParaConsulta = formato.format(fechaConsulta.getTime() + 86400000L);
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntDetalleComprobante h "
                    //                    + "where h.fechaAlta <= '" + fechaParaConsulta + "' "
                    + "where h.cntComprobante.fecha <= '" + fechaParaConsulta + "' "
                    + "and h.fechaBaja is null "
                    + "and h.cntComprobante.fechaBaja is null "
                    + "and h.estado='" + EnumEstado.CONFIRMADO.getCodigo() + "' "
                    + "order by h.cntEntidad.mascaraGenerada ASC");
            return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<PojoCntDetalleComprobanteSumasSaldos> obtieneListaSumasSaldosSoloDetalleComprobante(Date fechaConsulta) throws Exception {
        List<PojoCntDetalleComprobanteSumasSaldos> listaSumasSaldos = new ArrayList<PojoCntDetalleComprobanteSumasSaldos>();
        List<CntDetalleComprobante> listaDetalleComrobanteOriginal = detalleComprobantePorFechaList(fechaConsulta);
        PojoCntDetalleComprobanteSumasSaldos sumasSaldos;
        if (!listaDetalleComrobanteOriginal.isEmpty()) {
            Long valor = listaDetalleComrobanteOriginal.get(0).getCntEntidad().getIdEntidad();
            sumasSaldos = new PojoCntDetalleComprobanteSumasSaldos();
            sumasSaldos.setIdEntidad(listaDetalleComrobanteOriginal.get(0).getCntEntidad());
            sumasSaldos = calculaValoresMonetarios(sumasSaldos, listaDetalleComrobanteOriginal.get(0), fechaConsulta);
            listaSumasSaldos.add(sumasSaldos);

            for (CntDetalleComprobante cdc : listaDetalleComrobanteOriginal) {

                if (cdc.getCntEntidad().getIdEntidad() != valor) {
                    valor = cdc.getCntEntidad().getIdEntidad();
                    sumasSaldos = new PojoCntDetalleComprobanteSumasSaldos();
                    sumasSaldos.setIdEntidad(cdc.getCntEntidad());
                    sumasSaldos = (PojoCntDetalleComprobanteSumasSaldos) calculaValoresMonetarios(sumasSaldos, cdc, fechaConsulta).clone();
                    listaSumasSaldos.add(sumasSaldos);
                }
            }
        }
        return listaSumasSaldos;
    }

    public PojoCntDetalleComprobanteSumasSaldos calculaValoresMonetarios(PojoCntDetalleComprobanteSumasSaldos sumasSaldosPojo, CntDetalleComprobante cntDetalleComprobante, Date fechaConsulta) {
        sumasSaldosPojo.setIdEntidad(cntDetalleComprobante.getCntEntidad());
        sumasSaldosPojo.setSumaDebe(sumaCampoMonetarioDetalleComprobante(cntDetalleComprobante.getCntEntidad(), fechaConsulta, "debe"));
        sumasSaldosPojo.setSumaHaber(sumaCampoMonetarioDetalleComprobante(cntDetalleComprobante.getCntEntidad(), fechaConsulta, "haber"));
        sumasSaldosPojo.setSumaDebeDolar(sumaCampoMonetarioDetalleComprobante(cntDetalleComprobante.getCntEntidad(), fechaConsulta, "debeDolar"));
        sumasSaldosPojo.setSumaHaberDolar(sumaCampoMonetarioDetalleComprobante(cntDetalleComprobante.getCntEntidad(), fechaConsulta, "haberDolar"));
        if (sumasSaldosPojo.getSumaDebe().compareTo(sumasSaldosPojo.getSumaHaber()) == 1) {
            sumasSaldosPojo.setDeudor(sumasSaldosPojo.getSumaDebe().subtract(sumasSaldosPojo.getSumaHaber()));
            sumasSaldosPojo.setAcreedor(new BigDecimal(0.00));
        } else {
            sumasSaldosPojo.setAcreedor(sumasSaldosPojo.getSumaHaber().subtract(sumasSaldosPojo.getSumaDebe()));
            sumasSaldosPojo.setDeudor(new BigDecimal(0.00));
        }
        if (sumasSaldosPojo.getSumaDebeDolar().compareTo(sumasSaldosPojo.getSumaHaberDolar()) == 1) {
            sumasSaldosPojo.setDeudorDolar(sumasSaldosPojo.getSumaDebeDolar().subtract(sumasSaldosPojo.getSumaHaberDolar()));
            sumasSaldosPojo.setAcreedorDolar(new BigDecimal(0.00));
        } else {
            sumasSaldosPojo.setAcreedorDolar(sumasSaldosPojo.getSumaHaberDolar().subtract(sumasSaldosPojo.getSumaDebeDolar()));
            sumasSaldosPojo.setDeudorDolar(new BigDecimal(0.00));
        }
        return sumasSaldosPojo;
    }

    //Lista todos los niveles
    public List<CntNivel> listaDeNiveles() throws Exception {
        try {
            List<CntNivel> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntNivel j "
                    + "where j.tipoMovimiento = 'PCTA' ");
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception h) {
            throw h;
        }
        return Collections.EMPTY_LIST;
    }

    public BigDecimal sumaCampoMonetarioDetalleComprobante(final CntEntidad cntEntidad, Date hastaFecha, final String tipoCampo) {
        Calendar c = Calendar.getInstance();
        c.setTime(hastaFecha);
        c.roll(Calendar.DAY_OF_YEAR, 1);
        hastaFecha = c.getTime();//Aumentamos 1 dia
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        final String fechaConsulta = formato.format(hastaFecha);

        BigDecimal sumaDebe = (BigDecimal) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String q = ""
                        + "select sum(j." + tipoCampo + ") as suma "
                        + "from CntDetalleComprobante j "
                        + "where j.cntEntidad.idEntidad = " + cntEntidad.getIdEntidad() + " "
                        + "and j.cntComprobante.fecha <= '" + fechaConsulta + "' "
                        + "and j.cntComprobante.fechaBaja is null "
                        + "and j.fechaBaja is null and j.estado='" + EnumEstado.CONFIRMADO.getCodigo() + "'";

                Query query = session.createQuery(q);
                BigDecimal s = (BigDecimal) query.uniqueResult();
                return s;
            }
        });

        if (sumaDebe == null) {
            sumaDebe = new BigDecimal("0.00");
        }
        return sumaDebe;
    }

    public List<PojoCntDetalleComprobanteSumasSaldos> devuelveListaCompletaParaSumasSaldos(Date fechaConsulta, int nivel, Boolean ceros) throws Exception {
        PojoCntDetalleComprobanteSumasSaldos sumasSaldos;
        Boolean sw = true;
        int posicion = 0;
        List<PojoCntDetalleComprobanteSumasSaldos> listaOriginalSumasSaldos = new ArrayList<PojoCntDetalleComprobanteSumasSaldos>();
        try {
            List<CntEntidad> listaPlanCuentasCompleto = cntEntidadesPorGrupoNivelList(nivel);
            List<PojoCntDetalleComprobanteSumasSaldos> listaPojoCntDetalleComprobanteSumasSaldos = obtieneListaSumasSaldosSoloDetalleComprobante(fechaConsulta);
            int cantidadTotalLista = listaPojoCntDetalleComprobanteSumasSaldos.size();
            if (cantidadTotalLista != 0) {
                if (!ceros && nivel == 1) {
                    return listaPojoCntDetalleComprobanteSumasSaldos;
                } else {
                    for (CntEntidad entidad : listaPlanCuentasCompleto) {
                        if (sw && entidad.getMascaraGenerada().equals(listaPojoCntDetalleComprobanteSumasSaldos.get(posicion).getIdEntidad().getMascaraGenerada())) {
                            listaOriginalSumasSaldos.add(listaPojoCntDetalleComprobanteSumasSaldos.get(posicion));
                            posicion++;
                            if (posicion == cantidadTotalLista) {
                                sw = false;
                                posicion--;
                            }
                        } else {
                            sumasSaldos = new PojoCntDetalleComprobanteSumasSaldos();
                            sumasSaldos.setIdEntidad(entidad);
                            sumasSaldos.setSumaDebe(BigDecimal.ZERO);
                            sumasSaldos.setSumaHaber(BigDecimal.ZERO);
                            sumasSaldos.setSumaDebeDolar(BigDecimal.ZERO);
                            sumasSaldos.setSumaHaberDolar(BigDecimal.ZERO);
                            sumasSaldos.setDeudor(BigDecimal.ZERO);
                            sumasSaldos.setAcreedor(BigDecimal.ZERO);
                            sumasSaldos.setDeudorDolar(BigDecimal.ZERO);
                            sumasSaldos.setAcreedorDolar(BigDecimal.ZERO);
                            listaOriginalSumasSaldos.add(sumasSaldos);
                        }
                    }
                    return listaOriginalSumasSaldos;
                }
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    public BigDecimal sumaCampoMonetarioPorComprobante(final CntComprobante cntComprobante, final String tipoCampoMonetario) {
        BigDecimal sumaCampoMonetario = (BigDecimal) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String q = ""
                        + "select sum(j." + tipoCampoMonetario + ") as suma "
                        + "from CntDetalleComprobante j "
                        + "where j.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante() + " "
                        + "and j.fechaBaja is null and j.estado='" + EnumEstado.CONFIRMADO.getCodigo() + "'";
                Query query = session.createQuery(q);
                BigDecimal s = (BigDecimal) query.uniqueResult();
                return s;
            }
        });
        if (sumaCampoMonetario == null) {
            sumaCampoMonetario = new BigDecimal("0.00");
        }
        return sumaCampoMonetario;
    }

    public BigDecimal obtieneSumasgeneralesporTipoDeColumna(String tipoColumna, Date fechaConsulta) throws Exception {
        BigDecimal resultadoSuma = new BigDecimal("0.00");
        try {
            List<PojoCntDetalleComprobanteSumasSaldos> comprobanteSumasSaldoses = obtieneListaSumasSaldosSoloDetalleComprobante(fechaConsulta);
            for (PojoCntDetalleComprobanteSumasSaldos pojoCntDetalleComprobanteSumasSaldos : comprobanteSumasSaldoses) {
                if (tipoColumna.equals(EnumCampoMonetario.DEBE.getCodigo())) {
                    resultadoSuma = resultadoSuma.add(pojoCntDetalleComprobanteSumasSaldos.getSumaDebe());
                } else {
                    if (tipoColumna.equals(EnumCampoMonetario.HABER.getCodigo())) {
                        resultadoSuma = resultadoSuma.add(pojoCntDetalleComprobanteSumasSaldos.getSumaHaber());
                    } else {
                        if (tipoColumna.equals(EnumCampoMonetario.DEBE_DOLAR.getCodigo())) {
                            resultadoSuma = resultadoSuma.add(pojoCntDetalleComprobanteSumasSaldos.getSumaDebeDolar());
                        } else {
                            if (tipoColumna.equals(EnumCampoMonetario.HABER_DOLAR.getCodigo())) {
                                resultadoSuma = resultadoSuma.add(pojoCntDetalleComprobanteSumasSaldos.getSumaDebeDolar());
                            } else {
                                if (tipoColumna.equals(EnumCampoMonetario.DEUDOR.getCodigo())) {
                                    resultadoSuma = resultadoSuma.add(pojoCntDetalleComprobanteSumasSaldos.getDeudor());
                                } else {
                                    if (tipoColumna.equals(EnumCampoMonetario.ACREEDOR.getCodigo())) {
                                        resultadoSuma = resultadoSuma.add(pojoCntDetalleComprobanteSumasSaldos.getAcreedor());
                                    } else {
                                        if (tipoColumna.equals(EnumCampoMonetario.DEUDOR_DOLAR.getCodigo())) {
                                            resultadoSuma = resultadoSuma.add(pojoCntDetalleComprobanteSumasSaldos.getDeudorDolar());
                                        } else {
                                            if (tipoColumna.equals(EnumCampoMonetario.ACREEDOR_DOLAR.getCodigo())) {
                                                resultadoSuma = resultadoSuma.add(pojoCntDetalleComprobanteSumasSaldos.getAcreedorDolar());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return resultadoSuma;
    }

    public List<PojoCntDetalleComprobanteSumasSaldos> obtieneListaCompletaParaSumasSaldos(Date fechaConsulta, int nivel, Boolean ceros) throws Exception {
        BigDecimal sumaBloqueDebe = new BigDecimal("0.00");
        BigDecimal sumaBloqueHaber = new BigDecimal("0.00");
        BigDecimal sumaBloqueDebeDolar = new BigDecimal("0.00");
        BigDecimal sumaBloqueHaberDolar = new BigDecimal("0.00");
        BigDecimal sumaBloqueDeudor = new BigDecimal("0.00");
        BigDecimal sumaBloqueAcreedor = new BigDecimal("0.00");
        BigDecimal sumaBloqueDeudorDolar = new BigDecimal("0.00");
        BigDecimal sumaBloqueAcreedorDolar = new BigDecimal("0.00");
        Boolean sw = true;
        PojoCntDetalleComprobanteSumasSaldos comprobanteSumasSaldos;
        CntEntidad cntEntidadUno = new CntEntidad();
        CntEntidad cntEntidadDos;
        List<PojoCntDetalleComprobanteSumasSaldos> auxListaSumasSaldos = new ArrayList<PojoCntDetalleComprobanteSumasSaldos>();
        List<PojoCntDetalleComprobanteSumasSaldos> listaSumasSaldos = devuelveListaCompletaParaSumasSaldos(fechaConsulta, nivel, ceros);
        if (nivel != 1) {
            for (PojoCntDetalleComprobanteSumasSaldos sumasSaldos : listaSumasSaldos) {
                if (sumasSaldos.getIdEntidad().getNivel() < nivel) {
                    sumaBloqueDebe = sumaBloqueDebe.add(sumasSaldos.getSumaDebe());
                    sumaBloqueHaber = sumaBloqueHaber.add(sumasSaldos.getSumaHaber());
                    sumaBloqueDebeDolar = sumaBloqueDebeDolar.add(sumasSaldos.getSumaDebeDolar());
                    sumaBloqueHaberDolar = sumaBloqueHaberDolar.add(sumasSaldos.getSumaHaberDolar());
                    sumaBloqueDeudor = sumaBloqueDeudor.add(sumasSaldos.getDeudor());
                    sumaBloqueAcreedor = sumaBloqueAcreedor.add(sumasSaldos.getAcreedor());
                    sumaBloqueDeudorDolar = sumaBloqueDeudorDolar.add(sumasSaldos.getDeudorDolar());
                    sumaBloqueAcreedorDolar = sumaBloqueAcreedorDolar.add(sumasSaldos.getAcreedorDolar());
                }
                if (sumasSaldos.getIdEntidad().getNivel() == nivel) {
                    if (sw) {
                        cntEntidadUno = sumasSaldos.getIdEntidad();
                        sw = false;
                    } else {
                        cntEntidadDos = new CntEntidad();
                        if (cntEntidadUno.getIdEntidad() != null) {
                            cntEntidadDos = cntEntidadUno;
                            comprobanteSumasSaldos = new PojoCntDetalleComprobanteSumasSaldos();
                            comprobanteSumasSaldos.setIdEntidad(cntEntidadDos);
                            comprobanteSumasSaldos.setSumaDebe(sumaBloqueDebe);
                            comprobanteSumasSaldos.setSumaHaber(sumaBloqueHaber);
                            comprobanteSumasSaldos.setSumaDebeDolar(sumaBloqueDebeDolar);
                            comprobanteSumasSaldos.setSumaHaberDolar(sumaBloqueHaberDolar);
                            comprobanteSumasSaldos.setDeudor(sumaBloqueDeudor);
                            comprobanteSumasSaldos.setAcreedor(sumaBloqueAcreedor);
                            comprobanteSumasSaldos.setDeudorDolar(sumaBloqueDeudorDolar);
                            comprobanteSumasSaldos.setAcreedorDolar(sumaBloqueAcreedorDolar);
                            if (ceros) {
                                auxListaSumasSaldos.add(comprobanteSumasSaldos);
                            } else {
                                if (comprobanteSumasSaldos.getSumaDebe().equals(new BigDecimal("0.00"))) {
                                    if (!comprobanteSumasSaldos.getSumaHaber().equals(new BigDecimal("0.00"))) {
                                        auxListaSumasSaldos.add(comprobanteSumasSaldos);
                                    }
                                } else {
                                    auxListaSumasSaldos.add(comprobanteSumasSaldos);
                                }
                            }
                            sumaBloqueDebe = new BigDecimal("0.00");
                            sumaBloqueHaber = new BigDecimal("0.00");
                            sumaBloqueDebeDolar = new BigDecimal("0.00");
                            sumaBloqueHaberDolar = new BigDecimal("0.00");
                            sumaBloqueDeudor = new BigDecimal("0.00");
                            sumaBloqueAcreedor = new BigDecimal("0.00");
                            sumaBloqueDeudorDolar = new BigDecimal("0.00");
                            sumaBloqueAcreedorDolar = new BigDecimal("0.00");
                            cntEntidadUno = sumasSaldos.getIdEntidad();
                        }
                    }
                }
            }
            cntEntidadDos = cntEntidadUno;
            comprobanteSumasSaldos = new PojoCntDetalleComprobanteSumasSaldos();
            comprobanteSumasSaldos.setIdEntidad(cntEntidadDos);
            comprobanteSumasSaldos.setSumaDebe(sumaBloqueDebe);
            comprobanteSumasSaldos.setSumaHaber(sumaBloqueHaber);
            comprobanteSumasSaldos.setSumaDebeDolar(sumaBloqueDebeDolar);
            comprobanteSumasSaldos.setSumaHaberDolar(sumaBloqueHaberDolar);
            comprobanteSumasSaldos.setDeudor(sumaBloqueDeudor);
            comprobanteSumasSaldos.setAcreedor(sumaBloqueAcreedor);
            comprobanteSumasSaldos.setDeudorDolar(sumaBloqueDeudorDolar);
            comprobanteSumasSaldos.setAcreedorDolar(sumaBloqueAcreedorDolar);
            if (ceros) {
                auxListaSumasSaldos.add(comprobanteSumasSaldos);
            } else {
                if (comprobanteSumasSaldos.getSumaDebe().equals(new BigDecimal("0.00"))) {
                    if (!comprobanteSumasSaldos.getSumaHaber().equals(new BigDecimal("0.00"))) {
                        auxListaSumasSaldos.add(comprobanteSumasSaldos);
                    }
                } else {
                    auxListaSumasSaldos.add(comprobanteSumasSaldos);
                }
            }
            return auxListaSumasSaldos;
        } else {
            return listaSumasSaldos;
        }
    }

    public List<CntEntidad> cntEntidadesPorGrupoNivelList(int nivel) throws Exception {
        try {
            List<CntEntidad> lista = hibernateTemplate.find(""
                    + "select o "
                    + "from CntEntidad o "
                    + "where o.cntMascara.grupoNivel.codigo = '" + EnumGruposNivel.PLAN_CUENTAS.getCodigo() + "' "
                    + "and o.nivel<=" + nivel + " and o.fechaBaja is null "
                    + "order by o.mascaraGenerada ASC");
            return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    public BigDecimal sumaDebeComprobanteLibroMayor(List<CntLibroMayor> lista) {
        BigDecimal sumaDebe = new BigDecimal("0.00");
        for (CntLibroMayor cntLibroMayor : lista) {
            if (cntLibroMayor.getDebe() != null && cntLibroMayor.getIdDetalleComprobante() != null) {
                sumaDebe = sumaDebe.add(cntLibroMayor.getDebe());
            }
        }
        return sumaDebe;
    }

    public BigDecimal sumaHaberComprobanteLibroMayor(List<CntLibroMayor> lista) {
        BigDecimal sumaHaber = new BigDecimal("0.00");
        for (CntLibroMayor cntLibroMayor : lista) {
            if (cntLibroMayor.getHaber() != null && cntLibroMayor.getIdDetalleComprobante() != null) {
                sumaHaber = sumaHaber.add(cntLibroMayor.getHaber());
            }
        }
        return sumaHaber;
    }

    public BigDecimal sumaSaldoComprobanteLibroMayor(List<CntLibroMayor> lista) {
        BigDecimal sumaSaldo = new BigDecimal("0.00");
        for (CntLibroMayor cntLibroMayor : lista) {
            if (cntLibroMayor.getSaldo() != null && cntLibroMayor.getIdDetalleComprobante() != null) {
                sumaSaldo = sumaSaldo.add(cntLibroMayor.getSaldo());
            }
        }
        return sumaSaldo;
    }

    public void asignaAuxiliaresADetalleComprobante(CntDetalleComprobante cntDetalleComprobante, CntAuxiliar cntAuxiliar) throws Exception {
        cntDetalleComprobante.setIdAuxiliar(cntAuxiliar.getIdAuxiliar());
        mergeCntDetalleComprobantes(cntDetalleComprobante);
        List<CntDetalleComprobante> listaHijos = listaHijosPorPadre(cntDetalleComprobante);
        for (CntDetalleComprobante cntDetalleComprobante1 : listaHijos) {
            if (cntDetalleComprobante1.getCntEntidad().getTieneAuxiliar().equals(EnumConfirmacion.SI.getCodigo())) {
                cntDetalleComprobante1.setIdAuxiliar(cntAuxiliar.getIdAuxiliar());
                mergeCntDetalleComprobantes(cntDetalleComprobante1);
            }
        }
    }

    public void asignaProyectoADetalleComprobante(CntDetalleComprobante cntDetalleComprobante, CntProyecto cntProyecto) throws Exception {
        cntDetalleComprobante.setIdProyecto(cntProyecto.getIdProyecto());
        mergeCntDetalleComprobantes(cntDetalleComprobante);
        List<CntDetalleComprobante> listaHijos = listaHijosPorPadre(cntDetalleComprobante);
        for (CntDetalleComprobante cntDetalleComprobante1 : listaHijos) {
            cntDetalleComprobante1.setIdProyecto(cntProyecto.getIdProyecto());
            mergeCntDetalleComprobantes(cntDetalleComprobante1);
        }
    }

    public BigDecimal sumaDebeBolivianoDolarComprobante(List<CntDetalleComprobante> listaDeCuentasDeComprobante, String tipoMoneda) {
        BigDecimal resultadoDebe = new BigDecimal(BigInteger.ZERO);
        for (CntDetalleComprobante cntDetalleComprobante : listaDeCuentasDeComprobante) {
            if (tipoMoneda.equals(EnumTipoMoneda.BOLIVIANOS.getCodigo())) {
                if (cntDetalleComprobante.getDebe() != null) {
                    resultadoDebe = resultadoDebe.add(cntDetalleComprobante.getDebe());
                }
            } else {
                if (cntDetalleComprobante.getDebeDolar() != null) {
                    resultadoDebe = resultadoDebe.add(cntDetalleComprobante.getDebeDolar());
                }
            }
        }
        return resultadoDebe;
    }

    public BigDecimal sumaHaberBolivianoDolarComprobante(List<CntDetalleComprobante> listaDeCuentasDeComprobante, String tipoMoneda) {
        BigDecimal resultadoHaber = new BigDecimal(BigInteger.ZERO);
        for (CntDetalleComprobante cntDetalleComprobante : listaDeCuentasDeComprobante) {
            if (tipoMoneda.equals(EnumTipoMoneda.BOLIVIANOS.getCodigo())) {
                if (cntDetalleComprobante.getHaber() != null) {
                    resultadoHaber = resultadoHaber.add(cntDetalleComprobante.getHaber());
                }
            } else {
                if (cntDetalleComprobante.getHaberDolar() != null) {
                    resultadoHaber = resultadoHaber.add(cntDetalleComprobante.getHaberDolar());
                }
            }
        }
        return resultadoHaber;
    }

    public void guardaPosicionesAnteriores(CntComprobante cntComprobante) {
        try {
            List<CntDetalleComprobante> listaDetallesComprobantes = obtienePadresPorComprobante(cntComprobante);
            for (CntDetalleComprobante cntDetalleComprobante : listaDetallesComprobantes) {
                cntDetalleComprobante.setPosicionAnterior(cntDetalleComprobante.getPosicion());
                mergeCntDetalleComprobantes(cntDetalleComprobante);
            }
        } catch (Exception ex) {
            Logger.getLogger(CntDetalleComprobanteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<CntDetalleComprobante> obtienePadresPorComprobante(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante() + " "
                + "and j.idPadre is null");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void reestablecePosicionesAnteriores(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.idPadre is null "
                + "and j.posicionAnterior is not null ");
        if (!lista.isEmpty()) {
            for (CntDetalleComprobante cntDetalleComprobante : lista) {
                cntDetalleComprobante.setPosicion(cntDetalleComprobante.getPosicionAnterior());
                cntDetalleComprobante.setPosicionAnterior(null);
                try {
                    mergeCntDetalleComprobantes(cntDetalleComprobante);
                } catch (Exception ex) {
                    Logger.getLogger(CntDetalleComprobanteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//    diferencia de tipo de cambioy ajuste moneda
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaCuentaAjusteMonetarioDiferenciaTipoCambio(List cntcntDetalleComprobantesList, CntComprobante cntComprobante, String loginUsuario) throws Exception {
        String tipoMoneda;
        CntDetalleComprobante detalleComprobante = new CntDetalleComprobante();
        detalleComprobante.setIdDetalleComprobante(null);
        if (cntComprobante.getTipoMoneda().equals(EnumTipoMoneda.BOLIVIANOS.getCodigo()) || cntComprobante.getTipoMoneda().equals(EnumTipoMoneda.AMBOS.getCodigo())) {
            detalleComprobante.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "DCA3")).getValor())));
            tipoMoneda = EnumTipoMoneda.DOLAR.getCodigo();

        } else {
            detalleComprobante.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "ACM3")).getValor())));
            tipoMoneda = EnumTipoMoneda.BOLIVIANOS.getCodigo();
        }
        BigDecimal sumaDebeBolivianoDolar = sumaDebeBolivianoDolarComprobante(cntcntDetalleComprobantesList, tipoMoneda);
        BigDecimal sumaHaberBolivianoDolar = sumaHaberBolivianoDolarComprobante(cntcntDetalleComprobantesList, tipoMoneda);
        detalleComprobante.setDebe(null);
        detalleComprobante.setHaber(null);
        detalleComprobante.setFechaAlta(new Date());
        detalleComprobante.setUsuarioAlta(loginUsuario);
        detalleComprobante.setLoginUsuario(loginUsuario);
        BigDecimal resultadoDiferencia = sumaDebeBolivianoDolar.subtract(sumaHaberBolivianoDolar);
        //desbloquear para verificar las operaciones de diferencia de tipo de cambio
        if (resultadoDiferencia.signum() < 0) {
            detalleComprobante.setDebeDolar(sumaHaberBolivianoDolar.subtract(sumaDebeBolivianoDolar));
            detalleComprobante.setDebe(new BigDecimal(BigInteger.ZERO));
            detalleComprobante.setHaberDolar(null);
        } else {
            detalleComprobante.setDebeDolar(null);
            detalleComprobante.setHaberDolar(sumaDebeBolivianoDolar.subtract(sumaHaberBolivianoDolar));
            detalleComprobante.setHaber(new BigDecimal(BigInteger.ZERO));
        }
        detalleComprobante.setIdPadre(null);
        detalleComprobante.setCntComprobante(cntComprobante);
        detalleComprobante.setPosicion(obtienePosicionDetalleComprobantePorComprobante(cntComprobante));
        detalleComprobante.setTransaccionRealizada(EnumTransaccionRealizada.NINGUNO.getCodigo());
        detalleComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
        detalleComprobante.setGlosa("DIFERENCIA TIPO CAMBIO");
        try {
            if (resultadoDiferencia.compareTo(BigDecimal.ZERO) == 0) {
            } else {
                persistCntDetalleComprobantes(detalleComprobante);
            }
        } catch (Exception ex) {
            throw ex;
        }

    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaCuentaAjusteMonetarioDiferenciaTipoCambioModifica(CntComprobante cntComprobante) throws Exception {
        String tipoMoneda;
        List<CntDetalleComprobante> cntDetalleComprobantesList = new ArrayList<CntDetalleComprobante>();
        cntDetalleComprobantesList = ordenaSegunPosicion(cntComprobante);
        ObjectUtils.printObjectState(cntComprobante);
        CntDetalleComprobante detalleComprobantedifTipoCambio = buscaDiferenciaTipoCambio(cntComprobante);
        ObjectUtils.printObjectState(detalleComprobantedifTipoCambio);
        if (cntComprobante.getTipoMoneda().equals(EnumTipoMoneda.BOLIVIANOS.getCodigo()) || cntComprobante.getTipoMoneda().equals(EnumTipoMoneda.AMBOS.getCodigo())) {
            CntEntidad cntEntidaddos = (CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "DCA3")).getValor()));
            ObjectUtils.printObjectState(cntEntidaddos);
            detalleComprobantedifTipoCambio.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "DCA3")).getValor())));
            tipoMoneda = EnumTipoMoneda.DOLAR.getCodigo();
        } else {
            detalleComprobantedifTipoCambio.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "ACM3")).getValor())));
            tipoMoneda = EnumTipoMoneda.BOLIVIANOS.getCodigo();
        }
        BigDecimal sumaDebeBolivianoDolar = sumaDebeBolivianoDolarComprobante(cntDetalleComprobantesList, tipoMoneda);
        BigDecimal sumaHaberBolivianoDolar = sumaHaberBolivianoDolarComprobante(cntDetalleComprobantesList, tipoMoneda);
        detalleComprobantedifTipoCambio.setDebe(null);
        detalleComprobantedifTipoCambio.setHaber(null);
        detalleComprobantedifTipoCambio.setFechaAlta(new Date());
        detalleComprobantedifTipoCambio.setUsuarioAlta(cntComprobante.getLoginUsuario());
        detalleComprobantedifTipoCambio.setLoginUsuario(cntComprobante.getLoginUsuario());
        BigDecimal resultadoDiferencia = sumaDebeBolivianoDolar.subtract(sumaHaberBolivianoDolar);
        if (resultadoDiferencia.signum() < 0) {
            detalleComprobantedifTipoCambio.setDebeDolar(sumaHaberBolivianoDolar.subtract(sumaDebeBolivianoDolar));
            detalleComprobantedifTipoCambio.setHaberDolar(null);
        } else {
            detalleComprobantedifTipoCambio.setDebeDolar(null);
            detalleComprobantedifTipoCambio.setHaberDolar(sumaDebeBolivianoDolar.subtract(sumaHaberBolivianoDolar));
        }
        detalleComprobantedifTipoCambio.setIdPadre(null);
        detalleComprobantedifTipoCambio.setCntComprobante(cntComprobante);
        detalleComprobantedifTipoCambio.setPosicion(obtienePosicionDetalleComprobantePorComprobante(cntComprobante));
        detalleComprobantedifTipoCambio.setTransaccionRealizada(EnumTransaccionRealizada.NINGUNO.getCodigo());
        detalleComprobantedifTipoCambio.setEstado(EnumEstado.CONFIRMADO.getCodigo());
        detalleComprobantedifTipoCambio.setGlosa("DIFERENCIA TIPO CAMBIO");
        try {
            if (resultadoDiferencia.compareTo(BigDecimal.ZERO) == 0) {
                detalleComprobantedifTipoCambio.setFechaBaja(new Date());
                detalleComprobantedifTipoCambio.setUsuarioBaja(cntComprobante.getLoginUsuario());
            } else {
                detalleComprobantedifTipoCambio.setPosicion(obtienePosicionDetalleComprobantePorComprobante(cntComprobante) - 1L);
                mergeCntDetalleComprobantes(detalleComprobantedifTipoCambio);
            }
        } catch (Exception ex) {
            throw ex;
        }

    }

    public Long obtienePosicionDetalleComprobantePorComprobante(CntComprobante cntComprobante) throws Exception {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante() + " "
                + "order by j.posicion DESC");
        if (!lista.isEmpty()) {
            return (lista.get(0).getPosicion() + 1);
        } else {
            return null;
        }
    }

    //devuelve "true" si en comprobante esta registrado diferencia de tipo de cambio
    public Boolean verificaCuentaDiferenciaDeCambio(CntComprobante cntComprobante) {
        int sw = 0;
        List<CntDetalleComprobante> lista = listaDeCuentasPorComprobante(cntComprobante);
        CntEntidad cntEntidadDiferenciaTipoCambio = ((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "DCA3")).getValor())));
        for (CntDetalleComprobante cntDetalleComprobante : lista) {
            if (cntDetalleComprobante.getCntEntidad().getIdEntidad().equals(cntEntidadDiferenciaTipoCambio.getIdEntidad())) {
                sw = 1;
            }
        }
        if (sw == 1) {
            return true;

        } else {
            return false;
        }
    }

    //busca en el comprobante la cuenta con diferencia de tipo de cambio registrada en un comprobante
    public CntDetalleComprobante buscaDiferenciaTipoCambio(CntComprobante cntComprobante) {
        CntEntidad cntEntidadDiferenciaTipoCambio = ((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(((ParCuentasDeAjuste) parParametricasService.find(ParCuentasDeAjuste.class, "DCA3")).getValor())));
        List<CntDetalleComprobante> list = hibernateTemplate.find(""
                + "select c "
                + "from CntDetalleComprobante c "
                + "where c.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante() + ""
                + "and c.cntEntidad.idEntidad=" + cntEntidadDiferenciaTipoCambio.getIdEntidad() + " "
                + "and c.fechaBaja is null");
        if (!list.isEmpty()) {
            return (list.get(0));
        } else {
            return null;
        }
    }

    public CntFacturacion buscaNumeroFactura(CntDetalleComprobante cntDetalleComprobante) throws Exception {
//        List<CntFacturacion> lista = hibernateTemplate.find(""
//                + "select j "
//                + "from CntFacturacion j "
//                + "where j.fechaBaja is null "
//                + "and j.cntDetalleComprobante.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante() + "");
        List<CntFacturacion> lista = hibernateTemplate.find(""
                + "select j.cntFacturacion "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntFacturacion.fechaBaja is null "
                + "and j.idDetalleComprobante = " + cntDetalleComprobante.getIdDetalleComprobante() + "");
        if (!lista.isEmpty()) {
            return (lista.get(0));
        } else {
            return null;
        }
    }

    public Boolean verificaRelacionCntEntidadConCntDetalleComprobante(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntDetalleComprobante h "
                    + "where h.cntEntidad.idEntidad=" + cntEntidad.getIdEntidad() + " and h.fechaBaja is null");
            return !lista.isEmpty();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<PojoCntEntidadBGyEERR> listPlanCuentaParaBGyEERR(Date fecha, String tipoReporte) {
        BigDecimal total = new BigDecimal(0.00);
        BigDecimal totalSegMoneda = new BigDecimal(0.00);
        BigDecimal[] resultados = new BigDecimal[2];
        String valorCuenta = "";
        List<PojoCntEntidadBGyEERR> listaPojoCntEntidadBDyEERR = new ArrayList<PojoCntEntidadBGyEERR>();
        List<CntEntidad> listaPlanDeCuentasInverso = cntEntidadesService.listaPlanCuentasDescendente(tipoReporte);
        PojoCntEntidadBGyEERR cuenta;
//        int contador = 1;
        if (tipoReporte.equals("BG")) {
            valorCuenta = parParametricasService.devuelveMascaraPorTipoCuenta("ACT");
//            contador = 2;            
        } else {
            valorCuenta = parParametricasService.devuelveMascaraPorTipoCuenta("EGR");
        }
        for (CntEntidad cntEntidad : listaPlanDeCuentasInverso) {
            cuenta = new PojoCntEntidadBGyEERR();
            cuenta.setIdEntidad(cntEntidad);
            if (cntEntidad.getNivel() == 1) {
                resultados = totalMontoAmbasMonedasDetalleComprobantePorCuenta(cntEntidad, fecha);
            } else {
                resultados = montoAmbasMonedasCuentasPorNivel(listaPojoCntEntidadBDyEERR, cntEntidad.getIdEntidad());
            }
            if (cntEntidad.getNivel() != 1 || valorCuenta.equals(cuenta.getIdEntidad().getMascaraGenerada().substring(0, 1))) {
                cuenta.setMontoMonedaUno(resultados[0]);
                cuenta.setMontoMonedaDos(resultados[1]);
            } else {
                cuenta.setMontoMonedaUno(resultados[0].negate());
                cuenta.setMontoMonedaDos(resultados[1].negate());
            }
            if (cuenta.getIdEntidad().getIdEntidadPadre() == 0L) {
//                if(contador > 0) {
                if (valorCuenta.equals(cuenta.getIdEntidad().getMascaraGenerada().substring(0, 1))) {
                    total = total.subtract(cuenta.getMontoMonedaUno());
                    totalSegMoneda = totalSegMoneda.subtract(cuenta.getMontoMonedaDos());
//                    contador--;

                } else {

                    total = total.add(cuenta.getMontoMonedaUno());
                    totalSegMoneda = totalSegMoneda.add(cuenta.getMontoMonedaDos());

                }
            }
            listaPojoCntEntidadBDyEERR.add(0, cuenta);
        }
        cuenta = new PojoCntEntidadBGyEERR();
        cuenta.setIdEntidad(null);
        cuenta.setMontoMonedaUno(total);
        cuenta.setMontoMonedaDos(totalSegMoneda);
        listaPojoCntEntidadBDyEERR.add(0, cuenta);
        return listaPojoCntEntidadBDyEERR;
    }

    public BigDecimal[] totalMontoAmbasMonedasDetalleComprobantePorCuenta(CntEntidad cntEntidad, Date fecha) {
        BigDecimal resultado = new BigDecimal(0.00);
        BigDecimal resultadoHaber = new BigDecimal(0.00);
        BigDecimal resultadoSegMoneda = new BigDecimal(0.00);
        BigDecimal resultadoHaberSegMoneda = new BigDecimal(0.00);
        BigDecimal[] resultados = new BigDecimal[2];
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechaParaConsulta = formato.format(fecha.getTime() + 86400000L);
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntEntidad.idEntidad = " + cntEntidad.getIdEntidad() + " "
                + "and j.cntComprobante.fecha <= '" + fechaParaConsulta + "' "
                + "and j.cntComprobante.fechaBaja is null "
                + "and j.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' "
                + "and j.cntComprobante.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' ");
        for (CntDetalleComprobante cntDetalleComprobante : lista) {
//            if (cntDetalleComprobante.getHaber() == null) {
            if (cntDetalleComprobante.getHaber().compareTo(new BigDecimal(0.00)) == 0) {
                resultado = resultado.add(cntDetalleComprobante.getDebe());
                resultadoSegMoneda = resultadoSegMoneda.add(cntDetalleComprobante.getDebeDolar());
            } else {
                if (cntDetalleComprobante.getHaber().compareTo(new BigDecimal(0.00)) == 1) {
                    resultadoHaber = resultadoHaber.add(cntDetalleComprobante.getHaber());
                    resultadoHaberSegMoneda = resultadoHaberSegMoneda.add(cntDetalleComprobante.getHaberDolar());
//                resultado = resultado.subtract(cntDetalleComprobante.getHaber());
//                resultadoSegMoneda = resultadoSegMoneda.subtract(cntDetalleComprobante.getHaberDolar());
                }
            }
        }
        resultados[0] = resultado.subtract(resultadoHaber);
        resultados[1] = resultadoSegMoneda.subtract(resultadoHaberSegMoneda);
//        resultados[0] = resultado;
//        resultados[1] = resultadoSegMoneda;
        return resultados;
    }

    public BigDecimal totalDetalleComprobantePorCuenta(CntEntidad cntEntidad, Date fecha) {
        BigDecimal resultado = new BigDecimal(0.00);
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechaParaConsulta = formato.format(fecha.getTime() + 86400000L);
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntEntidad.idEntidad = " + cntEntidad.getIdEntidad() + " "
                + "and j.cntComprobante.fecha <= '" + fechaParaConsulta + "' "
                + "and j.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' "
                + "and j.cntComprobante.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' ");
        for (CntDetalleComprobante cntDetalleComprobante : lista) {
            if (cntDetalleComprobante.getHaber() == null) {
                resultado = resultado.add(cntDetalleComprobante.getDebe());
            } else {
                resultado = resultado.subtract(cntDetalleComprobante.getHaber());
            }
        }
        return resultado;
    }

    BigDecimal montoCuentasPorNivel(List<PojoCntEntidadBGyEERR> listaCuentas, long idEntidad) {
        BigDecimal resultado = new BigDecimal(BigInteger.ZERO);
        for (PojoCntEntidadBGyEERR listaCuenta : listaCuentas) {
            if (listaCuenta.getIdEntidad().getIdEntidadPadre() == idEntidad) {
                resultado = resultado.add(listaCuenta.getMontoMonedaUno());
            }
        }
        return resultado;
    }

    BigDecimal[] montoAmbasMonedasCuentasPorNivel(List<PojoCntEntidadBGyEERR> listaCuentas, long idEntidad) {
        BigDecimal[] resultadoAmbasMonedas = new BigDecimal[2];
        BigDecimal resultado = new BigDecimal(BigInteger.ZERO);
        BigDecimal resultadoSegMoneda = new BigDecimal(BigInteger.ZERO);
        for (PojoCntEntidadBGyEERR listaCuenta : listaCuentas) {
            if (listaCuenta.getIdEntidad().getIdEntidadPadre() == idEntidad) {
                resultado = resultado.add(listaCuenta.getMontoMonedaUno());
                resultadoSegMoneda = resultadoSegMoneda.add(listaCuenta.getMontoMonedaDos());
            }
        }
        resultadoAmbasMonedas[0] = resultado;
        resultadoAmbasMonedas[1] = resultadoSegMoneda;
        return resultadoAmbasMonedas;
    }

    public CntDetalleComprobante encuentraDetalleComprobantePorFacturacion(CntFacturacion cntFacturacion) throws Exception {

        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntFacturacion.idFacturacion = " + cntFacturacion.getIdFacturacion() + "");
        if (lista.isEmpty()) {
            return new CntDetalleComprobante();
        }
        return lista.get(0);
    }
//  REVISANDO BALANCE GENERAL PARA QUE CUMPLA LA SIGUIENTE FORMULA  ***** SALDO = SALDO + DEBE - HABER  ********

    public List<PojoCntEntidadBGyEERR> listaPlanCuentasParaBalanceGeneral(Date fecha, String tipoReporte) {
        List<PojoCntEntidadBGyEERR> listaPojoCntEntidadBDyEERR = new ArrayList<PojoCntEntidadBGyEERR>();
        List<CntEntidad> listaPlanDeCuentasInverso = cntEntidadesService.listaPlanCuentasDescendente(tipoReporte);
        PojoCntEntidadBGyEERR cuenta;
        BigDecimal[] resultados = new BigDecimal[2];
        BigDecimal total = new BigDecimal(0.00);
        BigDecimal totalSegMoneda = new BigDecimal(0.00);
        String valorCuenta = "";

        Boolean fechaVerificado = verificaPeriodoyGestion(fecha);
        if (fechaVerificado) {
            if (tipoReporte.equals("BG")) {
                valorCuenta = parParametricasService.devuelveMascaraPorTipoCuenta("ACT");
            } else {
                valorCuenta = parParametricasService.devuelveMascaraPorTipoCuenta("EGR");
            }
            for (CntEntidad cntEntidad : listaPlanDeCuentasInverso) {
                cuenta = new PojoCntEntidadBGyEERR();
                cuenta.setIdEntidad(cntEntidad);
                cuenta.setDescripcion(insertaPuntosAutomatio(cntEntidad));
                if (cntEntidad.getNivel() == 1) {
                    resultados = totalMontoAmbasMonedasDetalleComprobantePorCuenta(cntEntidad, fecha);
                } else {
                    resultados = montoAmbasMonedasCuentasPorNivel(listaPojoCntEntidadBDyEERR, cntEntidad.getIdEntidad());
                }
                if (cntEntidad.getNivel() != 1 || valorCuenta.equals(cuenta.getIdEntidad().getMascaraGenerada().substring(0, 1))) {
                    cuenta.setMontoMonedaUno(resultados[0]);
                    cuenta.setMontoMonedaDos(resultados[1]);
                } else {
                    cuenta.setMontoMonedaUno(resultados[0].negate());
                    cuenta.setMontoMonedaDos(resultados[1].negate());
                }
                if (cntEntidad.getNivel() == 1) {
                    cuenta.setNivel1bs(resultados[0]);
                    cuenta.setNivel1sus(resultados[1]);
                }
                if (cntEntidad.getNivel() == 2) {
                    cuenta.setNivel2bs(resultados[0]);
                    cuenta.setNivel2sus(resultados[1]);
                }
                if (cntEntidad.getNivel() == 3) {
                    cuenta.setNivel3bs(resultados[0]);
                    cuenta.setNivel3sus(resultados[1]);
                }
                if (cntEntidad.getNivel() == 4) {
                    cuenta.setNivel4bs(resultados[0]);
                    cuenta.setNivel4sus(resultados[1]);
                }
                if (cntEntidad.getNivel() == 5) {
                    cuenta.setNivel5bs(resultados[0]);
                    cuenta.setNivel5sus(resultados[1]);
                }
                if (cntEntidad.getNivel() == 6) {
                    cuenta.setNivel6bs(resultados[0]);
                    cuenta.setNivel6sus(resultados[1]);
                }

                if (cuenta.getIdEntidad().getIdEntidadPadre() == 0L) {
                    if (valorCuenta.equals(cuenta.getIdEntidad().getMascaraGenerada().substring(0, 1))) {
                        total = total.subtract(cuenta.getMontoMonedaUno());
                        totalSegMoneda = totalSegMoneda.subtract(cuenta.getMontoMonedaDos());
                    } else {
                        total = total.add(cuenta.getMontoMonedaUno());
                        totalSegMoneda = totalSegMoneda.add(cuenta.getMontoMonedaDos());
                    }
                }
                listaPojoCntEntidadBDyEERR.add(0, cuenta);
            }
            cuenta = new PojoCntEntidadBGyEERR();
//        cuenta.setIdEntidad(null);
            cuenta.setMontoMonedaUno(total);
            cuenta.setMontoMonedaDos(totalSegMoneda);
            listaPojoCntEntidadBDyEERR.add(0, cuenta);

            return listaPojoCntEntidadBDyEERR;
        }
        return Collections.EMPTY_LIST;
    }

    public List<PojoCntEntidadBGyEERR> listaPlanCuentasParaBalanceGeneralCeros(Date fecha, int nivel, Boolean ceros, String tipoReporte) throws Exception {
        try {

            List<PojoCntEntidadBGyEERR> lista = new ArrayList<PojoCntEntidadBGyEERR>();
            List<PojoCntEntidadBGyEERR> listaConCeros = new ArrayList<PojoCntEntidadBGyEERR>();
            List<PojoCntEntidadBGyEERR> listaO = listaPlanCuentasParaBalanceGeneral(fecha, tipoReporte);
            PojoCntEntidadBGyEERR pojo;
            if (ceros) {
                for (PojoCntEntidadBGyEERR pojoCntEntidad : listaO) {
                    pojo = new PojoCntEntidadBGyEERR();
                    if (pojoCntEntidad.getIdEntidad() != null) {
                        if (pojoCntEntidad.getIdEntidad().getNivel() >= nivel) {
                            if (!pojoCntEntidad.getMontoMonedaUno().equals(new BigDecimal(0.00))) {
                                pojo.setIdEntidad(pojoCntEntidad.getIdEntidad());
//                                pojo.setDescripcion(pojoCntEntidad.getDescripcion());
                                pojo.setDescripcion(insertaPuntosAutomatio(pojo.getIdEntidad()));
                                pojo.setValorIndex(listaO.indexOf(pojoCntEntidad));
                                pojo.setMontoMonedaUno(pojoCntEntidad.getMontoMonedaUno());
                                pojo.setMontoMonedaDos(pojoCntEntidad.getMontoMonedaDos());
                                pojo.setNivel1bs(pojoCntEntidad.getNivel1bs());
                                pojo.setNivel1sus(pojoCntEntidad.getNivel1sus());
                                pojo.setNivel2bs(pojoCntEntidad.getNivel2bs());
                                pojo.setNivel2sus(pojoCntEntidad.getNivel2sus());
                                pojo.setNivel3bs(pojoCntEntidad.getNivel3bs());
                                pojo.setNivel3sus(pojoCntEntidad.getNivel3sus());
                                pojo.setNivel4bs(pojoCntEntidad.getNivel4bs());
                                pojo.setNivel4sus(pojoCntEntidad.getNivel4sus());
                                pojo.setNivel5bs(pojoCntEntidad.getNivel5bs());
                                pojo.setNivel5sus(pojoCntEntidad.getNivel5sus());
                                pojo.setNivel6bs(pojoCntEntidad.getNivel6bs());
                                pojo.setNivel6sus(pojoCntEntidad.getNivel6sus());

                                lista.add(pojo);
                            }
                        }
                    }
                }
            } else {
                for (PojoCntEntidadBGyEERR cc : listaO) {
                    pojo = new PojoCntEntidadBGyEERR();
                    if (cc.getIdEntidad() != null) {
                        if (cc.getIdEntidad().getNivel() >= nivel) {
                            pojo.setIdEntidad(cc.getIdEntidad());
                            pojo.setDescripcion(cc.getDescripcion());
                            pojo.setValorIndex(listaO.indexOf(cc));
                            pojo.setMontoMonedaUno(cc.getMontoMonedaUno());
                            pojo.setMontoMonedaDos(cc.getMontoMonedaDos());
                            pojo.setNivel1bs(cc.getNivel1bs());
                            pojo.setNivel1sus(cc.getNivel1sus());
                            pojo.setNivel2bs(cc.getNivel2bs());
                            pojo.setNivel2sus(cc.getNivel2sus());
                            pojo.setNivel3bs(cc.getNivel3bs());
                            pojo.setNivel3sus(cc.getNivel3sus());
                            pojo.setNivel4bs(cc.getNivel4bs());
                            pojo.setNivel4sus(cc.getNivel4sus());
                            pojo.setNivel5bs(cc.getNivel5bs());
                            pojo.setNivel5sus(cc.getNivel5sus());
                            pojo.setNivel6bs(cc.getNivel6bs());
                            pojo.setNivel6sus(cc.getNivel6sus());

                            listaConCeros.add(pojo);
                        }
                    }
                }
                return listaConCeros;
            }
            return lista;

        } catch (Exception e) {
            throw e;
        }
    }

    public String insertaPuntosAutomatio(CntEntidad cntEntidad) {
        return cntEntidadesService.generaEspaciosEnDescripcionNivelesSubAndPadre(cntEntidad);
    }

    public BigDecimal[] totalMontoAmbasMonedasDetalleComprobantePorTipoCpbte(CntEntidad cntEntidad, Date fechaIni, Date fechaFin, String tipoCpbte) {
        BigDecimal resultado = new BigDecimal(0.00);
        BigDecimal resultadoSegMoneda = new BigDecimal(0.00);
        BigDecimal[] resultados = new BigDecimal[2];
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechaParaConsultaIni = formato.format(fechaIni.getTime() + 86400000L);
        String fechaParaConsultaFin = formato.format(fechaFin.getTime() + 86400000L);
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntEntidad.idEntidad = " + cntEntidad.getIdEntidad() + " "
                + "and j.cntComprobante.fecha >= '" + fechaParaConsultaIni + "' "
                + "and j.cntComprobante.fecha <= '" + fechaParaConsultaFin + "' "
                + "and j.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' "
                + "and j.cntComprobante.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' "
                + "and j.cntComprobante.parTipoComprobante.codigo like '" + tipoCpbte + "'");
        for (CntDetalleComprobante cntDetalleComprobante : lista) {
            if (cntDetalleComprobante.getHaber() == null) {
                resultado = resultado.add(cntDetalleComprobante.getDebe());
                resultadoSegMoneda = resultadoSegMoneda.add(cntDetalleComprobante.getDebeDolar());
            } else {
                resultado = resultado.subtract(cntDetalleComprobante.getHaber());
                resultadoSegMoneda = resultadoSegMoneda.subtract(cntDetalleComprobante.getHaberDolar());

            }
        }
        resultados[0] = resultado;
        resultados[1] = resultadoSegMoneda;
        return resultados;
    }

    public BigDecimal[] obtieneMontoTotal(Date fecha, String tipoReporte) {
        List<PojoCntEntidadBGyEERR> lista = listPlanCuentaParaBGyEERR(fecha, tipoReporte);

        BigDecimal[] resultados = new BigDecimal[3];
        BigDecimal resulActivo = new BigDecimal(0.00);
        BigDecimal resulPasivo = new BigDecimal(0.00);
        BigDecimal resulPatrimonio = new BigDecimal(0.00);
        lista.remove(0);
        for (PojoCntEntidadBGyEERR l : lista) {
            if (l.getIdEntidad().getIdEntidadPadre().equals(0L)) {
                if (l.getIdEntidad().getDescripcion().equals("ACTIVO")) {
                    resulActivo = l.getMontoMonedaUno();
                }
                if (l.getIdEntidad().getDescripcion().equals("PASIVO")) {
                    resulPasivo = l.getMontoMonedaUno();
                }
                if (l.getIdEntidad().getDescripcion().equals("PATRIMONIO")) {
                    resulPatrimonio = l.getMontoMonedaUno();
                }
            }
        }
        resultados[0] = resulActivo;
        resultados[1] = resulPasivo;
        resultados[2] = resulPatrimonio;
        return resultados;

    }

    public BigDecimal[] obtieneMontoTotalSus(Date fecha, String tipoReporte) {
        List<PojoCntEntidadBGyEERR> lista = listPlanCuentaParaBGyEERR(fecha, tipoReporte);

        BigDecimal[] resultados = new BigDecimal[3];
        BigDecimal resulActivo = new BigDecimal(0.00);
        BigDecimal resulPasivo = new BigDecimal(0.00);
        BigDecimal resulPatrimonio = new BigDecimal(0.00);
        lista.remove(0);
        for (PojoCntEntidadBGyEERR l : lista) {
            if (l.getIdEntidad().getIdEntidadPadre().equals(0L)) {
                if (l.getIdEntidad().getDescripcion().equals("ACTIVO")) {
                    resulActivo = l.getMontoMonedaDos();
                }
                if (l.getIdEntidad().getDescripcion().equals("PASIVO")) {
                    resulPasivo = l.getMontoMonedaDos();
                }
                if (l.getIdEntidad().getDescripcion().equals("PATRIMONIO")) {
                    resulPatrimonio = l.getMontoMonedaDos();
                }
            }
        }
        resultados[0] = resulActivo;
        resultados[1] = resulPasivo;
        resultados[2] = resulPatrimonio;
        return resultados;

    }

    public BigDecimal[] obtieneMontoTotalEI(Date fecha, String tipoReporte) {
        List<PojoCntEntidadBGyEERR> lista = listPlanCuentaParaBGyEERR(fecha, tipoReporte);

        BigDecimal[] resultados = new BigDecimal[2];
        BigDecimal resulEgreso = new BigDecimal(0.00);
        BigDecimal resulIngreso = new BigDecimal(0.00);

        lista.remove(0);
        for (PojoCntEntidadBGyEERR l : lista) {
            if (l.getIdEntidad().getIdEntidadPadre().equals(0L)) {
                if (l.getIdEntidad().getDescripcion().equals("EGRESO")) {
                    resulEgreso = l.getMontoMonedaUno();
                }
                if (l.getIdEntidad().getDescripcion().equals("INGRESO")) {
                    resulIngreso = l.getMontoMonedaUno();
                }
            }
        }
        resultados[0] = resulEgreso;
        resultados[1] = resulIngreso;
        return resultados;
    }

    public BigDecimal sumaHaberParaLibroMayor(final CntEntidad cntEntidad, Date fechaInicial) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(fechaInicial);
//        c.roll(Calendar.DAY_OF_YEAR, -1);
//        fechaInicial = c.getTime();//Restamos 1 dia
        fechaInicial = devuelveFecha(fechaInicial);
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        final String fechaInicialConsulta = formato.format(fechaInicial);
        BigDecimal suma = (BigDecimal) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String q = ""
                        + "select sum(j.haber) as suma "
                        + "from CntLibroMayor j "
                        + "where j.identidad = '" + cntEntidad.getIdEntidad() + "' "
                        + "and j.fecha <= '" + fechaInicialConsulta + "' ";
                Query query = session.createQuery(q);
                BigDecimal s = (BigDecimal) query.uniqueResult();
                return s;
            }
        });
        if (suma == null) {
            suma = new BigDecimal(0);
        }
        return suma;
    }

    public BigDecimal sumadebeDolarInicialLibroMayor(final CntEntidad cntEntidad, Date fechaInicial) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(fechaInicial);
//        c.roll(Calendar.DAY_OF_YEAR, -1);
//        fechaInicial = c.getTime();//Restamos 1 dia
        fechaInicial = devuelveFecha(fechaInicial);
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        final String fechaInicialConsulta = formato.format(fechaInicial);
        BigDecimal suma = (BigDecimal) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String q = ""
                        + "select sum(j.debeDolar) as suma "
                        + "from CntLibroMayor j "
                        + "where j.identidad = '" + cntEntidad.getIdEntidad() + "' "
                        + "and j.fecha <= '" + fechaInicialConsulta + "' ";
                Query query = session.createQuery(q);
                BigDecimal s = (BigDecimal) query.uniqueResult();
                return s;
            }
        });
        if (suma == null) {
            suma = new BigDecimal(0);
        }
        return suma;
    }

    public BigDecimal sumaHaberDolarParaLibroMayor(final CntEntidad cntEntidad, Date fechaInicial) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(fechaInicial);
//        c.roll(Calendar.DAY_OF_YEAR, -1);
//        fechaInicial = c.getTime();//Restamos 1 dia
        fechaInicial = devuelveFecha(fechaInicial);
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        final String fechaInicialConsulta = formato.format(fechaInicial);
        BigDecimal suma = (BigDecimal) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                String q = ""
                        + "select sum(j.haberDolar) as suma "
                        + "from CntLibroMayor j "
                        + "where j.identidad = '" + cntEntidad.getIdEntidad() + "' "
                        + "and j.fecha <= '" + fechaInicialConsulta + "' ";
                Query query = session.createQuery(q);
                BigDecimal s = (BigDecimal) query.uniqueResult();
                return s;
            }
        });
        if (suma == null) {
            suma = new BigDecimal(0);
        }
        return suma;
    }

    public Boolean verificaPeriodoyGestion(Date fecha) {
        boolean existe = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        int periodo = calendar.get(Calendar.MONTH) + 1;
        int gestion = calendar.get(calendar.YEAR);
        List<ParGestionContable> lista = hibernateTemplate.find(
                "select p "
                + "from ParGestionContable p "
                + "where p.codigo = '" + EnumEmpresa_GestionContable.PERIODO_ACTUAL.getCodigo() + "' ");
//                + "and p.valor = '" + Integer.toString(periodo) + "'");
        int peri = Integer.parseInt(lista.get(0).getValor());
        System.out.println("..peri es ::: " + peri + "..periodos :::: " + periodo);

        List<ParGestionContable> listad = hibernateTemplate.find(
                "select p "
                + "from ParGestionContable p "
                + "where p.codigo = '" + EnumEmpresa_GestionContable.ANIO_ACTUAL.getCodigo() + "' "
                + "and p.valor = '" + Integer.toString(gestion) + "'");

//        if (!lista.isEmpty()) {
//            if (!listad.isEmpty()) {
//                existe = true;
//            }
//        }
        if (periodo <= peri) {
            System.out.println("...por menor ::::  ");
            if (!listad.isEmpty()) {
                existe = true;
            }
        }

        return existe;
    }

    public List<CntLibroMayor> listaResultados(List<CntLibroMayor> lista) throws Exception {
        try {
            List<CntLibroMayor> listar = new ArrayList<CntLibroMayor>();
            if (!lista.isEmpty()) {
                CntLibroMayor c = new CntLibroMayor();
                c.setGlosa("TOTALES :");
                c.setDebe(sumaDebeComprobanteLibroMayor(lista));
                c.setHaber(sumaHaberComprobanteLibroMayor(lista));
                c.setDebeDolar(sumaDebeDolarComprobanteLibroMayor(lista));
                c.setHaberDolar(sumaHaberDolarComprobanteLibroMayor(lista));
                CntLibroMayor c1 = new CntLibroMayor();
                c1.setGlosa("NETO :");
                if (c.getDebe().compareTo(c.getHaber()) == 1) {
                    c1.setDebe(c.getDebe().subtract(c.getHaber()));
                    c1.setDebeDolar(c.getDebeDolar().subtract(c.getHaberDolar()));
                } else {
                    c1.setHaber(c.getHaber().subtract(c.getDebe()));
                    c1.setHaberDolar(c.getHaberDolar().subtract(c.getDebeDolar()));
                }

                CntLibroMayor c2 = new CntLibroMayor();
                c2.setGlosa("SALDO FINAL :");
                c2.setDebe(c1.getDebe());
                c2.setHaber(c1.getHaber());
                c2.setDebeDolar(c1.getDebeDolar());
                c2.setHaberDolar(c1.getHaberDolar());
                listar.add(0, c);
                listar.add(1, c1);
                listar.add(2, c2);

                return listar;
            }

            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw (e);
        }
    }

    public List<CntLibroMayor> listaUnida(CntEntidad cntEntidad, Date fechaInicial, Date fechaFinal) throws Exception {
        try {
            List<CntLibroMayor> listafinal = new ArrayList<CntLibroMayor>();
            List<CntLibroMayor> listau = listaLibroMayorSegunCuenta(cntEntidad, fechaInicial, fechaFinal);
            List<CntLibroMayor> listad = listaResultados(listau);
            if (!listau.isEmpty()) {
                listafinal.addAll(listau);
                listafinal.addAll(listad);
                return listafinal;
            }

        } catch (Exception e) {
        }
        return Collections.EMPTY_LIST;
    }

    public BigDecimal[] obtieneMontoTotalEERR(Date fechaI, Date fechaF, String tipoReporte) {
        List<PojoCntEntidadBGyEERR> lista = listaPlanCuentasParaEERR(fechaI, fechaF, tipoReporte);

        BigDecimal[] resultados = new BigDecimal[2];
        BigDecimal resulEgreso = new BigDecimal(0.00);
        BigDecimal resulIngreso = new BigDecimal(0.00);
        lista.remove(0);
        for (PojoCntEntidadBGyEERR l : lista) {
            if (l.getIdEntidad().getIdEntidadPadre().equals(0L)) {
                if (l.getIdEntidad().getDescripcion().equals("EGRESO")) {
                    resulEgreso = l.getMontoMonedaUno();
                }
                if (l.getIdEntidad().getDescripcion().equals("INGRESO")) {
                    resulIngreso = l.getMontoMonedaUno();
                }
            }
        }
        resultados[0] = resulEgreso;
        resultados[1] = resulIngreso;
        return resultados;

    }

    public BigDecimal[] obtieneMontoTotalSusEERR(Date fechaI, Date fechaF, String tipoReporte) {
        List<PojoCntEntidadBGyEERR> lista = listaPlanCuentasParaEERR(fechaI, fechaF, tipoReporte);

        BigDecimal[] resultados = new BigDecimal[2];
        BigDecimal resulEgreso = new BigDecimal(0.00);
        BigDecimal resulIngreso = new BigDecimal(0.00);

        lista.remove(0);
        for (PojoCntEntidadBGyEERR l : lista) {
            if (l.getIdEntidad().getIdEntidadPadre().equals(0L)) {
                if (l.getIdEntidad().getDescripcion().equals("EGRESO")) {
                    resulEgreso = l.getMontoMonedaDos();
                }
                if (l.getIdEntidad().getDescripcion().equals("INGRESO")) {
                    resulIngreso = l.getMontoMonedaDos();
                }
            }
        }
        resultados[0] = resulEgreso;
        resultados[1] = resulIngreso;

        return resultados;
    }

    public BigDecimal[] totalMontoAmbasMonedasDetalleComprobantePorCuentaEERR(CntEntidad cntEntidad, Date fechaI, Date fechaF) {
        BigDecimal resultado = new BigDecimal(0.00);
        BigDecimal resultadoSegMoneda = new BigDecimal(0.00);
        BigDecimal[] resultados = new BigDecimal[2];
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechaParaConsultaI = formato.format(fechaI.getTime() + 86400000L);
        String fechaParaConsultaF = formato.format(fechaF.getTime() + 86400000L);

        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntEntidad.idEntidad = " + cntEntidad.getIdEntidad() + " "
                + "and j.cntComprobante.fecha >= '" + fechaParaConsultaI + "' "
                + "and j.cntComprobante.fecha <= '" + fechaParaConsultaF + "' "
                + "and j.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' "
                + "and j.cntComprobante.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' ");
        for (CntDetalleComprobante cntDetalleComprobante : lista) {
            if (cntDetalleComprobante.getHaber() == null && cntDetalleComprobante.getHaberDolar() == null) {
                if (cntDetalleComprobante.getDebe() != null) {
                    resultado = resultado.add(cntDetalleComprobante.getDebe());
                }
                resultadoSegMoneda = resultadoSegMoneda.add(cntDetalleComprobante.getDebeDolar());
            } else {
                if (cntDetalleComprobante.getHaber() != null) {
                    resultado = resultado.subtract(cntDetalleComprobante.getHaber());
                }
                resultadoSegMoneda = resultadoSegMoneda.subtract(cntDetalleComprobante.getHaberDolar());
            }
        }
        resultados[0] = resultado;
        resultados[1] = resultadoSegMoneda;
        return resultados;
    }

    public List<PojoCntEntidadBGyEERR> listaPlanCuentasParaEERR(Date fechaI, Date fechaF, String tipoReporte) {
        List<PojoCntEntidadBGyEERR> listaPojoCntEntidadBDyEERR = new ArrayList<PojoCntEntidadBGyEERR>();
        List<CntEntidad> listaPlanDeCuentasInverso = cntEntidadesService.listaPlanCuentasDescendente(tipoReporte);
        PojoCntEntidadBGyEERR cuenta;
        BigDecimal[] resultados = new BigDecimal[2];
        BigDecimal total = new BigDecimal(0.00);
        BigDecimal totalSegMoneda = new BigDecimal(0.00);
        String valorCuenta = "";

        Boolean fechaVerificadoF = verificaPeriodoyGestion(fechaF);
        if (fechaVerificadoF) {
            if (tipoReporte.equals("BG")) {
                valorCuenta = parParametricasService.devuelveMascaraPorTipoCuenta("ACT");
            } else {
                valorCuenta = parParametricasService.devuelveMascaraPorTipoCuenta("EGR");
            }
            for (CntEntidad cntEntidad : listaPlanDeCuentasInverso) {
                cuenta = new PojoCntEntidadBGyEERR();
                cuenta.setIdEntidad(cntEntidad);
                cuenta.setDescripcion(insertaPuntosAutomatio(cntEntidad));
                if (cntEntidad.getNivel() == 1) {
                    resultados = totalMontoAmbasMonedasDetalleComprobantePorCuentaEERR(cntEntidad, fechaI, fechaF);
                } else {
                    resultados = montoAmbasMonedasCuentasPorNivel(listaPojoCntEntidadBDyEERR, cntEntidad.getIdEntidad());
                }
                if (cntEntidad.getNivel() != 1 || valorCuenta.equals(cuenta.getIdEntidad().getMascaraGenerada().substring(0, 1))) {
                    cuenta.setMontoMonedaUno(resultados[0]);
                    cuenta.setMontoMonedaDos(resultados[1]);
                } else {
                    cuenta.setMontoMonedaUno(resultados[0].negate());
                    cuenta.setMontoMonedaDos(resultados[1].negate());
                }
                if (cntEntidad.getNivel() == 1) {
                    cuenta.setNivel1bs(resultados[0]);
                    cuenta.setNivel1sus(resultados[1]);
                }
                if (cntEntidad.getNivel() == 2) {
                    cuenta.setNivel2bs(resultados[0]);
                    cuenta.setNivel2sus(resultados[1]);
                }
                if (cntEntidad.getNivel() == 3) {
                    cuenta.setNivel3bs(resultados[0]);
                    cuenta.setNivel3sus(resultados[1]);
                }
                if (cntEntidad.getNivel() == 4) {
                    cuenta.setNivel4bs(resultados[0]);
                    cuenta.setNivel4sus(resultados[1]);
                }
                if (cntEntidad.getNivel() == 5) {
                    cuenta.setNivel5bs(resultados[0]);
                    cuenta.setNivel5sus(resultados[1]);
                }
                if (cntEntidad.getNivel() == 6) {
                    cuenta.setNivel6bs(resultados[0]);
                    cuenta.setNivel6sus(resultados[1]);
                }

                if (cuenta.getIdEntidad().getIdEntidadPadre() == 0L) {
                    if (valorCuenta.equals(cuenta.getIdEntidad().getMascaraGenerada().substring(0, 1))) {
                        total = total.subtract(cuenta.getMontoMonedaUno());
                        totalSegMoneda = totalSegMoneda.subtract(cuenta.getMontoMonedaDos());
                    } else {
                        total = total.add(cuenta.getMontoMonedaUno());
                        totalSegMoneda = totalSegMoneda.add(cuenta.getMontoMonedaDos());
                    }
                }
                listaPojoCntEntidadBDyEERR.add(0, cuenta);
            }
            cuenta = new PojoCntEntidadBGyEERR();
            cuenta.setMontoMonedaUno(total);
            cuenta.setMontoMonedaDos(totalSegMoneda);
            listaPojoCntEntidadBDyEERR.add(0, cuenta);

            return listaPojoCntEntidadBDyEERR;
        }
        return Collections.EMPTY_LIST;
    }

    public List<PojoCntEntidadBGyEERR> listaEERRctrlCeros(Date fechaI, Date fechaF, int nivel, Boolean ceros, String tipoReporte) throws Exception {
        try {

            List<PojoCntEntidadBGyEERR> lista = new ArrayList<PojoCntEntidadBGyEERR>();
            List<PojoCntEntidadBGyEERR> listaConCeros = new ArrayList<PojoCntEntidadBGyEERR>();
            List<PojoCntEntidadBGyEERR> listaO = listaPlanCuentasParaEERR(fechaI, fechaF, tipoReporte);
            PojoCntEntidadBGyEERR pojo;
            if (ceros) {
                for (PojoCntEntidadBGyEERR pojoCntEntidad : listaO) {
                    pojo = new PojoCntEntidadBGyEERR();
                    if (pojoCntEntidad.getIdEntidad() != null) {
                        if (pojoCntEntidad.getIdEntidad().getNivel() >= nivel) {
                            if (!pojoCntEntidad.getMontoMonedaUno().equals(new BigDecimal(0.00))) {
                                pojo.setIdEntidad(pojoCntEntidad.getIdEntidad());
//                                pojo.setDescripcion(pojoCntEntidad.getDescripcion());
                                pojo.setDescripcion(insertaPuntosAutomatio(pojo.getIdEntidad()));
                                pojo.setValorIndex(listaO.indexOf(pojoCntEntidad));
                                pojo.setMontoMonedaUno(pojoCntEntidad.getMontoMonedaUno());
                                pojo.setMontoMonedaDos(pojoCntEntidad.getMontoMonedaDos());
                                pojo.setNivel1bs(pojoCntEntidad.getNivel1bs());
                                pojo.setNivel1sus(pojoCntEntidad.getNivel1sus());
                                pojo.setNivel2bs(pojoCntEntidad.getNivel2bs());
                                pojo.setNivel2sus(pojoCntEntidad.getNivel2sus());
                                pojo.setNivel3bs(pojoCntEntidad.getNivel3bs());
                                pojo.setNivel3sus(pojoCntEntidad.getNivel3sus());
                                pojo.setNivel4bs(pojoCntEntidad.getNivel4bs());
                                pojo.setNivel4sus(pojoCntEntidad.getNivel4sus());
                                pojo.setNivel5bs(pojoCntEntidad.getNivel5bs());
                                pojo.setNivel5sus(pojoCntEntidad.getNivel5sus());
                                pojo.setNivel6bs(pojoCntEntidad.getNivel6bs());
                                pojo.setNivel6sus(pojoCntEntidad.getNivel6sus());

                                lista.add(pojo);
                            }
                        }
                    }
                }
            } else {
                for (PojoCntEntidadBGyEERR cc : listaO) {
                    pojo = new PojoCntEntidadBGyEERR();
                    if (cc.getIdEntidad() != null) {
                        if (cc.getIdEntidad().getNivel() >= nivel) {
                            pojo.setIdEntidad(cc.getIdEntidad());
//                            pojo.setDescripcion(cc.getDescripcion());
                            pojo.setDescripcion(insertaPuntosAutomatio(cc.getIdEntidad()));
                            pojo.setValorIndex(listaO.indexOf(cc));
                            pojo.setMontoMonedaUno(cc.getMontoMonedaUno());
                            pojo.setMontoMonedaDos(cc.getMontoMonedaDos());
                            pojo.setNivel1bs(cc.getNivel1bs());
                            pojo.setNivel1sus(cc.getNivel1sus());
                            pojo.setNivel2bs(cc.getNivel2bs());
                            pojo.setNivel2sus(cc.getNivel2sus());
                            pojo.setNivel3bs(cc.getNivel3bs());
                            pojo.setNivel3sus(cc.getNivel3sus());
                            pojo.setNivel4bs(cc.getNivel4bs());
                            pojo.setNivel4sus(cc.getNivel4sus());
                            pojo.setNivel5bs(cc.getNivel5bs());
                            pojo.setNivel5sus(cc.getNivel5sus());
                            pojo.setNivel6bs(cc.getNivel6bs());
                            pojo.setNivel6sus(cc.getNivel6sus());

                            listaConCeros.add(pojo);
                        }
                    }
                }
                return listaConCeros;
            }
            return lista;

        } catch (Exception e) {
            throw e;
        }
    }

    public BigDecimal sumaDebeDolarComprobanteLibroMayor(List<CntLibroMayor> lista) {
        BigDecimal sumaDebe = new BigDecimal("0.00");
        for (CntLibroMayor cntLibroMayor : lista) {
            if (cntLibroMayor.getDebe() != null && cntLibroMayor.getIdDetalleComprobante() != null) {
                sumaDebe = sumaDebe.add(cntLibroMayor.getDebeDolar());
            }
        }
        return sumaDebe;
    }

    public BigDecimal sumaHaberDolarComprobanteLibroMayor(List<CntLibroMayor> lista) {
        BigDecimal sumaHaber = new BigDecimal("0.00");
        for (CntLibroMayor cntLibroMayor : lista) {
            if (cntLibroMayor.getHaber() != null && cntLibroMayor.getIdDetalleComprobante() != null) {
                sumaHaber = sumaHaber.add(cntLibroMayor.getHaberDolar());
            }
        }
        return sumaHaber;
    }

    public BigDecimal sumaSaldoDolarComprobanteLibroMayor(List<CntLibroMayor> lista) {
        BigDecimal sumaSaldo = new BigDecimal("0.00");
        for (CntLibroMayor cntLibroMayor : lista) {
            if (cntLibroMayor.getSaldo() != null && cntLibroMayor.getIdDetalleComprobante() != null) {
                sumaSaldo = sumaSaldo.add(cntLibroMayor.getSaldoDolar());
            }
        }
        return sumaSaldo;
    }

    public CntLibroMayor obtieneComprobanteXnumero(Long idDetalleComprobante) {
        List<CntLibroMayor> lista = hibernateTemplate.find(""
                + "select l "
                + "from CntLibroMayor l "
                + "where l.idDetalleComprobante.idDetalleComprobante = '" + idDetalleComprobante + "' ");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return new CntLibroMayor();
    }

    public List<PojoCntDetalleComprobanteSumasSaldos> obtieneListaFinal(Date fechaConsulta, int nivel, Boolean ceros) throws Exception {
        List<PojoCntDetalleComprobanteSumasSaldos> listaSS = obtieneListaCompletaParaSumasSaldos(fechaConsulta, nivel, ceros);
        List<PojoCntDetalleComprobanteSumasSaldos> listaSSF = new ArrayList<PojoCntDetalleComprobanteSumasSaldos>();
        PojoCntDetalleComprobanteSumasSaldos pojo;
        for (PojoCntDetalleComprobanteSumasSaldos lss : listaSS) {
            pojo = new PojoCntDetalleComprobanteSumasSaldos();
            pojo = lss;
            pojo.setValorIndex(listaSS.indexOf(lss));
            pojo.setDescripcion(insertaPuntosAutomatio(lss.getIdEntidad()));

            listaSSF.add(pojo);
        }
        return listaSSF;

    }

    public Date devuelveFecha(Date inicial) {
        Calendar c = Calendar.getInstance();
        c.setTime(inicial);
        c.roll(Calendar.DAY_OF_YEAR, -1);
        inicial = c.getTime();//
        GregorianCalendar date1 = new GregorianCalendar();
        date1.setTime(inicial);
        int mes = date1.get(Calendar.MONTH);
        mes = mes + 1;
        if (mes == 12 && date1.get(Calendar.DAY_OF_MONTH) == 31) {
            c.roll(Calendar.YEAR, -1);
            inicial = c.getTime();
        }
        return inicial;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaCntDetalleComprobante(CntComprobante cntComprobante, List<CntDetalleComprobante> listCntDetalleComprobante) throws Exception {
        try {
//            CntComprobante persistCntComprobantes = cntComprobantesService.persistCntComprobantes(cntComprobante);
            BigDecimal sumDebeB = new BigDecimal("0");
            BigDecimal sumHaberB = new BigDecimal("0");
            BigDecimal sumDebeS = new BigDecimal("0");
            BigDecimal sumHaberS = new BigDecimal("0");
            Long idDetalleComprobante = null;
            for (CntDetalleComprobante cntDetalleComprobante : listCntDetalleComprobante) {
                sumDebeB = sumDebeB.add(cntDetalleComprobante.getDebe());
                sumHaberB = sumHaberB.add(cntDetalleComprobante.getHaber());
                sumDebeS = sumDebeS.add(cntDetalleComprobante.getDebeDolar());
                sumHaberS = sumHaberS.add(cntDetalleComprobante.getHaberDolar());
                cntDetalleComprobante.setCntComprobante(cntComprobante);

            }
//            System.out.println("SUMA DEBE BS: " + sumDebeB);
//            System.out.println("SUMA HABER BS: " + sumHaberB);
//            System.out.println("SUMA DEBE SUS: " + sumDebeS);
//            System.out.println("SUMA HABER SUS: " + sumHaberS);
            Float sumDB = redondeaFloat(sumDebeB.floatValue(), 2);
            Float sumHB = redondeaFloat(sumHaberB.floatValue(), 2);
            Float sumDS = redondeaFloat(sumDebeS.floatValue(), 2);
            Float sumHS = redondeaFloat(sumHaberS.floatValue(), 2);
//            System.out.println("SUMA DEBE BS FLOAT: " + sumDB);
//            System.out.println("SUMA HABER BS FLOAT: " + sumHB);
//            System.out.println("SUMA DEBE SUS FLOAT: " + sumDS);
//            System.out.println("SUMA HABER SUS FLOAT: " + sumHS);
            if (sumDB.equals(sumHB) && sumDS.equals(sumHS)) {
                cntComprobante.setTotalComprobantes(BigDecimal.valueOf(sumDB));
                cntComprobante.setTotalComprobantesSegMoneda(BigDecimal.valueOf(sumDS));
                cntComprobante = cntComprobantesService.persistCntComprobantes(cntComprobante);
                System.out.println("-------------------COMPROBANTE-------------------------- ");
                System.out.println("ID_COMP: " + cntComprobante.getIdComprobante()
                        + " ID_ANTECESOR: " + cntComprobante.getIdAntecesor()
                        + " GLOSA_COMP: " + cntComprobante.getGlosaComprobante()
                        + " TIPO_CAMBIO: " + cntComprobante.getTipoCambio()
                        + " TIPO_MONEDA: " + cntComprobante.getTipoMoneda()
                        + " NUMERO: " + cntComprobante.getNumero()
                        + " PERIODO: " + cntComprobante.getPeriodo()
                        + " TIPO_COMP: " + cntComprobante.getParTipoComprobante().getCodigo()
                        + " DESCRIPCION: " + cntComprobante.getDescripcion()
                        + " FECHA: " + cntComprobante.getFecha()
                        + " TOTAL_COMP:" + cntComprobante.getTotalComprobantes()
                        + " TOTAL_COMP_SM: " + cntComprobante.getTotalComprobantesSegMoneda()
                        + " ESTADO: " + cntComprobante.getEstado()
                        + " MODULO: " + cntComprobante.getModulo()
                        + " MOTIVO_ANULADO: " + cntComprobante.getMotivoAnulacion());
                for (CntDetalleComprobante cntDetalleComprobante : listCntDetalleComprobante) {
                    cntDetalleComprobante.setCntComprobante(cntComprobante);
                    CntDetalleComprobante cntComprobantepersit = persistCntDetalleComprobantes(cntDetalleComprobante);
                    if (cntDetalleComprobante.getIdPadre() != null) {
                        cntDetalleComprobante.setIdPadre(idDetalleComprobante);
                    } else {
                        idDetalleComprobante = cntComprobantepersit.getIdDetalleComprobante();
                    }
                    System.out.println("-------------------DETALLE COMPROBANTE-------------------------- ");
                    System.out.println("ID_COMP: " + cntDetalleComprobante.getCntComprobante().getNumero()
                            + " ID_ENTIDAD---  : " + cntDetalleComprobante.getCntEntidad()
                            + " ID_ENTIDAD: " + cntDetalleComprobante.getCntEntidad().getMascaraGenerada()
                            + " ID_PADRE: " + cntDetalleComprobante.getIdPadre()
                            + " ID_ANTECESOR: " + cntDetalleComprobante.getIdAntecesor()
                            + " ID_AUXILIAR: " + cntDetalleComprobante.getIdAuxiliar()
                            + " ID_PROYECTO: " + cntDetalleComprobante.getIdProyecto()
                            + " POSICION: " + cntDetalleComprobante.getPosicion()
                            + " POSICION_ANTERIOR: " + cntDetalleComprobante.getPosicionAnterior()
                            + " DEBO_BS: " + cntDetalleComprobante.getDebe()
                            + " HABER_BS:" + cntDetalleComprobante.getHaber()
                            + " DEBE_SUS: " + cntDetalleComprobante.getDebeDolar()
                            + " HABER_SUS: " + cntDetalleComprobante.getHaberDolar()
                            + " GLOSA: " + cntDetalleComprobante.getGlosa()
                            + " ESTADO: " + cntDetalleComprobante.getEstado()
                            + " TRANS_REALIZADA: " + cntDetalleComprobante.getTransaccionRealizada()
                            + " NRO_CHEQUE: " + cntDetalleComprobante.getNumeroCheque()
                    );
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public BigDecimal[] obtieneMontoTotalDebeHaber(String tipoReporte) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<CntDetalleComprobante> listaproFechas(Date fi, Date f2) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechainicio = formato.format(fi.getTime() + 86400000L);

        String fechafin = formato.format(f2.getTime() + 86400000L);

        List<CntDetalleComprobante> lista = hibernateTemplate.find(
                "select d from CntDetalleComprobante d where d.cntComprobante.fecha >= '" + fechainicio + "' "
                + "and d.cntComprobante.fecha >= '" + fechafin + "'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public CntDetalleComprobante getObtieneDetalleConFacturaNullXCpbte(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> listac = hibernateTemplate.find(
                "select d "
                + "from CntDetalleComprobante d "
                + "where d.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante() + " "
                + "and d.cntComprobante.fechaBaja is null "
                + "and d.fechaBaja is null and d.cntFacturacion is null");
        if (!listac.isEmpty()) {
            return listac.get(0);
        }
        return null;
    }

}
