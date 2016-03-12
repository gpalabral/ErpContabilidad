package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.commons.entities.mappings.CalculosTributarios;
import com.bap.erp.commons.entities.mappings.Comprobante;
import com.bap.erp.commons.entities.mappings.CuentaMonto;
import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDistribucionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import java.math.BigDecimal;
import com.bap.erp.modelo.enums.EnumEstado;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumMovimientoFactura;
import com.bap.erp.modelo.enums.EnumTipoComprobante;
import com.bap.erp.modelo.enums.EnumTipoModulo;
import com.bap.erp.modelo.enums.EnumTipoMoneda;
import com.bap.erp.modelo.enums.EnumTransaccionRealizada;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntDistribucionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntFacturacionService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.ParseException;
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

public class CntComprobantesServiceImpl extends GenericDaoImpl<CntComprobante> implements CntComprobantesService {

    @Autowired
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @Autowired
    private CntFacturacionService cntFacturacionService;
    @Autowired
    private CntDistribucionCentroCostoService cntDistribucionCentroCostoService;
    @Autowired
    private ParParametricasService parParametricasService;
    @Autowired
    private CntEntidadesService cntEntidadesService;
    @Autowired
    private CntTipoCambioService cntTipoCambioService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntComprobante persistCntComprobantes(CntComprobante cntComprobante) throws Exception {
        try {
            super.persist(cntComprobante);
        } catch (Exception e) {
            throw e;
        }
        return cntComprobante;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntComprobante mergeCntComprobantes(CntComprobante cntComprobante) throws Exception {
        try {
            super.merge(cntComprobante);
        } catch (Exception e) {
            throw e;
        }
        return cntComprobante;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeCntComprobantes(CntComprobante cntComprobante) throws Exception {
        try {
            super.remove(cntComprobante);
        } catch (Exception h) {
            throw h;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void deleteCntComprobantes(CntComprobante cntComprobante) throws Exception {
        try {
            super.delete(cntComprobante);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntComprobante> listaCntComprobantes() throws Exception {
        try {
            /*
             List<CntComprobante> lista = hibernateTemplate.find(""
             + "select j "
             + "from CntComprobante j "
             + "where j.idComprobante not in(select h.idAntecesor "
             + "from CntComprobante h "
             + "where h.idAntecesor is not null) "
             + "and j.fechaBaja is null "
             + "order by j.numero ASC");            
             if (!lista.isEmpty()) {
             return lista;
             }*/

            /**
             * *********
             */
            List lista = (List) hibernateTemplate.execute(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    String q = ""
                            + "select j "
                            + "from CntComprobante j "
                            + "where j.idComprobante not in(select h.idAntecesor "
                            + "from CntComprobante h "
                            + "where h.idAntecesor is not null) "
                            + "and j.fechaBaja is null "
                            + "order by j.numero ASC";
                    Query query = session.createQuery(q);
//                query.setMaxResults(100);
//                query.setFirstResult(1);
                    List s = query.list();
                    return s;
                }
            });
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception e) {
            throw e;
        }

        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntComprobante persistCntComprobanteYCntDetalleComprobante(CntComprobante cntComprobante, List<CntDetalleComprobante> listaCntDetalleComprobante) throws Exception {
        try {
            cntComprobante = persistCntComprobantes(cntComprobante);
            for (CntDetalleComprobante cntDetalleComprobante : listaCntDetalleComprobante) {
                cntDetalleComprobante.setCntComprobante(cntComprobante);
                cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return cntComprobante;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntComprobante mergeCntComprobanteYCntDetalleComprobanteConfirmado(CntComprobante cntComprobante, List<CntDetalleComprobante> listaCntDetalleComprobante, String loginUsuario) throws Exception {
        try {
            cntComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
            cntComprobante.setLoginUsuario(loginUsuario);
            cntComprobante = mergeCntComprobantes(cntComprobante);
            CntFacturacion cntFacturacion;
            List<CntDistribucionCentrocosto> listaDeDistribucionCentroCosto;
            for (CntDetalleComprobante cntDetalleComprobante : listaCntDetalleComprobante) {
                cntDetalleComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                cntDetalleComprobante.setLoginUsuario(loginUsuario);
                cntDetalleComprobante.setUsuarioAlta(cntComprobante.getUsuarioAlta());
                cntDetalleComprobante.setFechaAlta(cntComprobante.getFechaAlta());
                cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
                //aumentado para nueva cuenta
                if (cntDetalleComprobante.getIdPadre() == null) {
//                    cntFacturacion = cntDetalleComprobanteService.buscaFacturacionPorDetalleComprobantePadres(cntDetalleComprobante);
                    cntFacturacion = cntDetalleComprobante.getCntFacturacion();
                    if (cntFacturacion != null) {
                        cntFacturacion.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                        cntFacturacion.setUsuarioAlta(cntComprobante.getUsuarioAlta());
                        cntFacturacion.setFechaAlta(cntComprobante.getFechaAlta());
                        if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo())) {
                            cntFacturacion.setFechaFactura(cntComprobante.getFecha());
                        }
                        cntFacturacionService.mergeCntFacturacion(cntFacturacion);
                    }
                }
                listaDeDistribucionCentroCosto = cntDistribucionCentroCostoService.listaDistribucionCentroCostoPorDetalleComprobante(cntDetalleComprobante);
                for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaDeDistribucionCentroCosto) {
                    cntDistribucionCentrocosto.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                    cntDistribucionCentrocosto.setUsuarioAlta(cntComprobante.getUsuarioAlta());
                    cntDistribucionCentrocosto.setFechaAlta(cntComprobante.getFechaAlta());
                    cntDistribucionCentroCostoService.mergeCntDistribucionCentrocosto(cntDistribucionCentrocosto);
                }
            }
            cntDetalleComprobanteService.guardaCuentaAjusteMonetarioDiferenciaTipoCambio(listaCntDetalleComprobante, cntComprobante, loginUsuario);
        } catch (Exception ex) {
            throw ex;
        }
        return cntComprobante;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeCntComprobanteCntDetalleComprobanteCntFacturacion(CntComprobante cntComprobante) throws Exception {
        try {
            for (CntDetalleComprobante cntDetalleComprobante : obtieneDetalleComprobantePadres(cntComprobante)) {
                cntDetalleComprobante.setUsuarioBaja(cntComprobante.getUsuarioBaja());
                cntDetalleComprobante.setFechaBaja(cntComprobante.getFechaBaja());
                cntDetalleComprobanteService.removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
            }
            removeCntComprobantes(cntComprobante);
        } catch (Exception h) {
            throw h;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void deleteCntComprobanteCntDetalleComprobanteCntFacturacion(CntComprobante cntComprobante) throws Exception {
        try {
            for (CntDetalleComprobante cntDetalleComprobante : obtieneDetalleComprobantePadresParaDelete(cntComprobante)) {
                CntDetalleComprobante detalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobante.getIdDetalleComprobante());
                cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(detalleComprobante);
            }
            CntComprobante comprobante = (CntComprobante) find(CntComprobante.class, cntComprobante.getIdComprobante());
            deleteCntComprobantes(comprobante);
        } catch (Exception h) {
            throw h;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void mergeCntComprobanteAndCntDetalleComprobanteAndFacturaTotal(CntComprobante cntComprobante) throws Exception {
        CntComprobante comprobanteParaGuardar = find(CntComprobante.class, cntComprobante.getIdComprobante());
        comprobanteParaGuardar.setDescripcion(cntComprobante.getDescripcion());
        comprobanteParaGuardar.setGlosaComprobante(cntComprobante.getGlosaComprobante());
        comprobanteParaGuardar.setTipoMoneda(cntComprobante.getTipoMoneda());
        comprobanteParaGuardar.setUsuarioAlta(cntComprobante.getUsuarioAlta());
        comprobanteParaGuardar.setFechaAlta(cntComprobante.getFechaAlta());
        try {
            for (CntDetalleComprobante cntDetalleComprobante : obtieneDetalleComprobantePadres(comprobanteParaGuardar)) {
                cntDetalleComprobante.setUsuarioAlta(comprobanteParaGuardar.getUsuarioAlta());
                cntDetalleComprobante.setFechaAlta(comprobanteParaGuardar.getFechaAlta());
                cntDetalleComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
                cntDetalleComprobanteService.mergeCntDetalleComprobantesModificaTotal(cntDetalleComprobante);
//                cntDetalleComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
            }
            for (CntDetalleComprobante cntDetalleComprobante : obtieneTodosLosDetalleComprobante(comprobanteParaGuardar)) {
//                cntDetalleComprobante.setIdReplicado(null);
                cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
            }
            comprobanteParaGuardar.setEstado(EnumEstado.CONFIRMADO.getCodigo());
            mergeCntComprobantes(comprobanteParaGuardar);
            CntComprobante cntComprobanteAntecesor = (CntComprobante) find(CntComprobante.class, comprobanteParaGuardar.getIdAntecesor());
            for (CntDetalleComprobante detalleComprobante : obtieneDetalleComprobantePadresParaDelete(cntComprobanteAntecesor)) {
                cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(detalleComprobante);
            }
            cntComprobanteAntecesor.setUsuarioModificacion(comprobanteParaGuardar.getUsuarioAlta());
            cntComprobanteAntecesor.setFechaModificacion(comprobanteParaGuardar.getFechaAlta());
            cntComprobanteAntecesor.setUsuarioBaja(comprobanteParaGuardar.getUsuarioAlta());
            cntComprobanteAntecesor.setFechaBaja(comprobanteParaGuardar.getFechaAlta());
            removeCntComprobantes(cntComprobanteAntecesor);
        } catch (Exception h) {
            throw h;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void persistCntComprobanteAndCntDetalleComprobanteAndFacturaTotal(CntComprobante cntComprobante) throws Exception {
        CntComprobante cntComprobanteaux = (CntComprobante) cntComprobante.clone();
        cntComprobanteaux.setEstado(EnumEstado.CONFIRMADO.getCodigo());
        cntComprobanteaux.setIdAntecesor(cntComprobante.getIdComprobante());
        cntComprobanteaux.setIdComprobante(null);
        cntComprobanteaux.setFechaAlta(cntComprobante.getFechaModificacion());
        cntComprobanteaux.setUsuarioAlta(cntComprobante.getUsuarioModificacion());
        cntComprobanteaux.setFechaModificacion(null);
        cntComprobanteaux.setUsuarioModificacion(null);
        cntComprobanteaux = persistCntComprobantes(cntComprobanteaux);

        try {
            cntDetalleComprobanteService.cambiaDetalleComprobanteIdModificacion(cntComprobanteaux);
            CntComprobante cntComprobanteOriginal = find(CntComprobante.class, cntComprobante.getIdComprobante());
            cntComprobanteOriginal.setFechaBaja(cntComprobante.getFechaModificacion());
            cntComprobanteOriginal.setUsuarioBaja(cntComprobante.getUsuarioModificacion());
            cntComprobanteOriginal.setEstado(EnumEstado.CONFIRMADO.getCodigo());
            mergeCntComprobantes(cntComprobanteOriginal);
        } catch (Exception h) {
            throw h;
        }

    }

    public Long ultimoNumeroComprobante(CntComprobante cntComprobante) throws Exception {
        try {
            List<CntComprobante> lista = hibernateTemplate.find(""
                    + "select a "
                    + "from CntComprobante a "
                    + "where a.parTipoComprobante.codigo ='" + cntComprobante.getParTipoComprobante().getCodigo() + "' "
                    + "and a.fechaBaja is null "
                    + "and (a.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' or a.estado = '" + EnumEstado.ANULADO.getCodigo() + "') "
                    + "order by a.numero DESC");
            return lista.isEmpty() ? 1 : lista.get(0).getNumero() + 1;

        } catch (Exception e) {
            throw e;
        }
    }

    public Long obtieneUltimoNumeroComprobante(CntComprobante cntComprobante) throws Exception {
        try {
            List<CntComprobante> lista = hibernateTemplate.find(""
                    + "select a "
                    + "from CntComprobante a "
                    + "where a.parTipoComprobante.codigo ='" + cntComprobante.getParTipoComprobante().getCodigo() + "' "
                    + "and a.fechaBaja is null "
                    + "and (a.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' or a.estado = '" + EnumEstado.ANULADO.getCodigo() + "') "
                    + "order by a.numero DESC");
            return lista.isEmpty() ? 1 : lista.get(0).getNumero();

        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntComprobante mergeCntComprobanteYCntDetalleComprobante(CntComprobante cntComprobante, List<CntDetalleComprobante> listaCntDetalleComprobante, List<CntDetalleComprobante> listaCntDetalleComprobanteAQuitarse) throws Exception {
        try {
            cntComprobante = mergeCntComprobantes(cntComprobante);
            listaCntDetalleComprobanteAQuitarse = listaDeCuentasQueDebenDarseDeBaja(listaCntDetalleComprobante, cntComprobante);
            for (CntDetalleComprobante cntDetalleComprobanteQ : listaCntDetalleComprobanteAQuitarse) {
                cntDetalleComprobanteQ.setFechaBaja(new Date());
                cntDetalleComprobanteQ.setUsuarioBaja("SISTEMA");
                cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobanteQ);
            }
            for (CntDetalleComprobante cntDetalleComprobante : listaCntDetalleComprobante) {
                if (cntDetalleComprobante.getIdDetalleComprobante() == null) {
                    cntDetalleComprobante.setCntComprobante(cntComprobante);
                    cntDetalleComprobante = cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
                } else {
                    cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return cntComprobante;
    }

    private List<CntDetalleComprobante> listaDeCuentasQueDebenDarseDeBaja(List<CntDetalleComprobante> listaDeElegidos, CntComprobante cntComprobante) {
        List<CntDetalleComprobante> listaAQuitarse = new ArrayList<CntDetalleComprobante>();
        List<CntDetalleComprobante> listaActualesEnBD = cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante);
        for (CntDetalleComprobante cntDetalleComprobante1 : listaActualesEnBD) {
            boolean existe = false;
            for (CntDetalleComprobante cntDetalleComprobante : listaDeElegidos) {
                if (cntDetalleComprobante1.getIdDetalleComprobante() == cntDetalleComprobante.getIdDetalleComprobante()) {
                    existe = true;
                }
            }
            if (!existe) {
                listaAQuitarse.add(cntDetalleComprobante1);
            }
        }
        if (!listaAQuitarse.isEmpty()) {
            return listaAQuitarse;
        }
        return Collections.EMPTY_LIST;
    }

    public Boolean verificaExistenciaDePendientes() {
        List<CntComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntComprobante j "
                + "where j.fechaBaja is null "
                + "and j.estado = 'PEND'");
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntComprobante persistTemporalParaModificacion(CntComprobante cntComprobanteElegido, String usuarioLogeado) throws Exception {
        List<CntDetalleComprobante> listaDetalleComprobanteTemporal;
        listaDetalleComprobanteTemporal = cntDetalleComprobanteService.listaDeCuentasPorComprobanteParaRepilcados(cntComprobanteElegido);
        CntComprobante cntComprobanteNuevo;
        cntComprobanteNuevo = cntComprobanteElegido;
        cntComprobanteNuevo.setEstado(cntComprobanteElegido.getEstado());
        cntComprobanteNuevo.setUsuarioAlta(cntComprobanteElegido.getUsuarioAlta());
        cntComprobanteNuevo.setFechaAlta(cntComprobanteElegido.getFechaAlta());
        cntComprobanteNuevo.setIdAntecesor(cntComprobanteNuevo.getIdComprobante());
        cntComprobanteNuevo.setIdComprobante(null);
        persistCntComprobantes(cntComprobanteNuevo);
        CntDetalleComprobante detalleComprobante;
        CntFacturacion cntFacturacion;
        for (CntDetalleComprobante cntDetalleComprobante : listaDetalleComprobanteTemporal) {
            detalleComprobante = (CntDetalleComprobante) cntDetalleComprobante.clone();
            detalleComprobante.setCntComprobante(cntComprobanteNuevo);
            detalleComprobante.setIdDetalleComprobante(null);
            detalleComprobante.setUsuarioAlta(cntComprobanteElegido.getUsuarioAlta());
            detalleComprobante.setFechaAlta(cntComprobanteElegido.getFechaAlta());
            detalleComprobante = encuentraPadreParaAntecesor(detalleComprobante);
//            detalleComprobante = cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante);
            if (detalleComprobante.getIdPadre() == null) {
                if (cntDetalleComprobanteService.buscaFacturacionPorDetalleComprobantePadres(cntDetalleComprobante) != null) {
                    cntFacturacion = (CntFacturacion) cntDetalleComprobanteService.buscaFacturacionPorDetalleComprobantePadres(cntDetalleComprobante).clone();
                    cntFacturacion.setIdAntecesor(cntFacturacion.getIdFacturacion());
//                    cntFacturacion.setCntDetalleComprobante(detalleComprobante);
                    cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
                    cntFacturacion.setIdFacturacion(null);
                    cntFacturacion = cntFacturacionService.persistCntFacturacion(cntFacturacion);
                    detalleComprobante.setCntFacturacion(cntFacturacion);
                }
            }
            cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante);
        }
        return cntComprobanteNuevo;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntComprobante mergeAnuladoComprobanteDetalleFacturacion(CntComprobante cntComprobanteElegido, String usuarioLogeado) throws Exception {
        List<CntDetalleComprobante> listaDetalleComprobanteTemporal;
        listaDetalleComprobanteTemporal = cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobanteElegido);
        cntComprobanteElegido.setEstado(EnumEstado.ANULADO.getCodigo());
        cntComprobanteElegido.setUsuarioModificacion(usuarioLogeado);
        cntComprobanteElegido.setFechaModificacion(new Date());
        mergeCntComprobantes(cntComprobanteElegido);
        CntDetalleComprobante detalleComprobante;
        CntFacturacion cntFacturacion;
        for (CntDetalleComprobante cntDetalleComprobante : listaDetalleComprobanteTemporal) {
            detalleComprobante = (CntDetalleComprobante) cntDetalleComprobante.clone();
            detalleComprobante.setEstado(EnumEstado.ANULADO.getCodigo());
            detalleComprobante.setUsuarioModificacion(usuarioLogeado);
            detalleComprobante.setFechaModificacion(new Date());
            cntDetalleComprobanteService.mergeCntDetalleComprobantes(detalleComprobante);
            if (detalleComprobante.getIdPadre() == null) {
                if (cntDetalleComprobanteService.buscaFacturacionPorDetalleComprobantePadres(cntDetalleComprobante) != null) {
                    cntFacturacion = (CntFacturacion) cntDetalleComprobanteService.buscaFacturacionPorDetalleComprobantePadres(cntDetalleComprobante).clone();
                    cntFacturacion.setEstado(EnumEstado.ANULADO.getCodigo());
                    cntFacturacionService.mergeCntFacturacion(cntFacturacion);
                }
            }
        }
        return cntComprobanteElegido;
    }

    public CntDetalleComprobante encuentraPadreParaAntecesor(CntDetalleComprobante cntDetalleComprobanteClonado) {
        if (cntDetalleComprobanteClonado.getIdPadre() != null) {
            List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntDetalleComprobante j "
                    + "where j.fechaBaja is null "
                    + "and j.idAntecesor = '" + cntDetalleComprobanteClonado.getIdPadre() + "' "
                    + "and j.cntComprobante.idComprobante = '" + cntDetalleComprobanteClonado.getCntComprobante().getIdComprobante() + "' ");
            if (!lista.isEmpty()) {
                cntDetalleComprobanteClonado.setIdPadre(lista.get(0).getIdDetalleComprobante());
                return cntDetalleComprobanteClonado;
            }
        }
        return cntDetalleComprobanteClonado;
    }

    public String obtienePeriodoPorFecha(Date fecha) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        int month = 0;
        try {
            month = calendar.get(Calendar.MONTH) + 1;
            return Integer.toString(month);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntComprobante persistTemporalParaCopia(CntComprobante cntComprobanteElegido, String usuarioLogeado) throws Exception {
        try {
            List<CntDistribucionCentrocosto> listaDistribucionCentroDeCostos;
            CntDistribucionCentrocosto cntDistribucionCCAuxiliar;
            List<CntDetalleComprobante> listaDetalleComprobanteTemporal;
            listaDetalleComprobanteTemporal = cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobanteElegido);
            CntComprobante cntComprobanteNuevo;
            cntComprobanteNuevo = cntComprobanteElegido;
            cntComprobanteNuevo.setDescripcion(cntComprobanteElegido.getDescripcion() + " (Copia)");
            cntComprobanteNuevo.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntComprobanteNuevo.setIdComprobante(null);
            cntComprobanteNuevo.setUsuarioAlta(usuarioLogeado);
            cntComprobanteNuevo.setFechaAlta(new Date());
            cntComprobanteNuevo.setNumero(0L);
            cntComprobanteNuevo.setPeriodo("");
            persistCntComprobantes(cntComprobanteNuevo);
            CntDetalleComprobante detalleComprobante;
            CntFacturacion cntFacturacion;
            for (CntDetalleComprobante cntDetalleComprobante : listaDetalleComprobanteTemporal) {
                detalleComprobante = (CntDetalleComprobante) cntDetalleComprobante.clone();
                detalleComprobante.setCntComprobante(cntComprobanteNuevo);
                detalleComprobante.setIdDetalleComprobante(null);
                detalleComprobante.setUsuarioAlta(usuarioLogeado);
                detalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
                detalleComprobante.setFechaAlta(new Date());
                detalleComprobante.setIdAntecesor(cntDetalleComprobante.getIdDetalleComprobante());
                detalleComprobante = encuentraPadreParaAntecesor(detalleComprobante);
                if (encuentraFacturasPorDetalleComprobante(cntDetalleComprobante) != null) {
                    cntFacturacion = (CntFacturacion) encuentraFacturasPorDetalleComprobante(cntDetalleComprobante).clone();
//                    cntFacturacion.setCntDetalleComprobante(detalleComprobante);
                    cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
                    cntFacturacion.setNroInicial(null);
                    cntFacturacion.setNroFinal(null);
                    cntFacturacion.setFechaFactura(null);
                    cntFacturacion.setIdFacturacion(null);
                    cntFacturacionService.persistCntFacturacion(cntFacturacion);
                    detalleComprobante.setCntFacturacion(cntFacturacion);
                }
                detalleComprobante = cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante);
                listaDistribucionCentroDeCostos = encuentraCentroDeCostoPorDetalleComprobante(cntDetalleComprobante);
                if (!listaDistribucionCentroDeCostos.isEmpty()) {
                    for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaDistribucionCentroDeCostos) {
                        cntDistribucionCCAuxiliar = (CntDistribucionCentrocosto) cntDistribucionCentrocosto.clone();
                        cntDistribucionCCAuxiliar.setCntDetalleComprobante(detalleComprobante);
                        cntDistribucionCCAuxiliar.setEstado(EnumEstado.PENDIENTE.getCodigo());
                        cntDistribucionCCAuxiliar.setFechaAlta(detalleComprobante.getFechaAlta());
                        cntDistribucionCCAuxiliar.setUsuarioAlta(detalleComprobante.getUsuarioAlta());
                        cntDistribucionCCAuxiliar.setUsuarioModificacion(null);
                        cntDistribucionCCAuxiliar.setFechaModificacion(null);
                        cntDistribucionCCAuxiliar.setIdDistribucionCentrocosto(null);
                        cntDistribucionCentroCostoService.persistCntDistribucionCentrocosto(cntDistribucionCCAuxiliar);
                    }
                }
            }
            List<CntDetalleComprobante> listaDetalleComprobanteNueva = cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobanteNuevo);
            for (CntDetalleComprobante cntDetalleComprobante : listaDetalleComprobanteNueva) {
                cntDetalleComprobante.setIdAntecesor(null);
                cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
            }
            return cntComprobanteNuevo;
        } catch (Exception e) {
            throw e;
        }
    }

    public CntFacturacion encuentraFacturasPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
//        List<CntFacturacion> lista = hibernateTemplate.find(""
//                + "select j "
//                + "from CntFacturacion j "
//                + "where j.fechaBaja is null "
//                + "and j.cntDetalleComprobante.idDetalleComprobante = '" + cntDetalleComprobante.getIdDetalleComprobante() + "' "
//                + "and j.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' ");
//        if (!lista.isEmpty()) {
//            return lista.get(0);
//        }        
        return cntDetalleComprobante.getCntFacturacion();
    }

    public Boolean verificaComprobanteConFactura(CntComprobante cntComprobante) {
        int sw = 0;
        if (obtieneDetalleComprobante(cntComprobante) != null) {
            List<CntDetalleComprobante> lista = obtieneDetalleComprobante(cntComprobante);
            for (CntDetalleComprobante cntDetalleComprobante : lista) {
                if (cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo()) || cntDetalleComprobante.getTransaccionRealizada().equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo())) {
                    sw = 1;
                }
            }
        }
        return sw != 0;
    }

    public List<CntDetalleComprobante> obtieneDetalleComprobante(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find("select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.posicion is not null "
                + "and j.cntComprobante.idComprobante='" + cntComprobante.getIdComprobante() + "'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;

    }

    public CntComprobante persistTemporalParaRevertirComprobante(CntComprobante cntComprobanteElegido, String usuarioLogeado) throws Exception {
        CntComprobante cntComprobanteNuevo;
        List<CntDistribucionCentrocosto> listaDistribucionCentroDeCostos = new ArrayList<CntDistribucionCentrocosto>();
        List<CntDetalleComprobante> listaDetalleComprobanteTemporal;
        listaDetalleComprobanteTemporal = cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobanteElegido);
        cntComprobanteNuevo = cntComprobanteElegido;
        cntComprobanteNuevo.setDescripcion(cntComprobanteElegido.getDescripcion() + " (Revertido)");
        cntComprobanteNuevo.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntComprobanteNuevo.setIdComprobante(null);
        cntComprobanteNuevo.setUsuarioAlta(usuarioLogeado);
        cntComprobanteNuevo.setFechaAlta(new Date());
        cntComprobanteNuevo.setNumero(0L);
        persistCntComprobantes(cntComprobanteNuevo);
        CntDetalleComprobante detalleComprobante;

        for (CntDetalleComprobante cntDetalleComprobante : listaDetalleComprobanteTemporal) {
            detalleComprobante = (CntDetalleComprobante) cntDetalleComprobante.clone();
            if (detalleComprobante.getDebe() != null) {
                detalleComprobante.setHaber(detalleComprobante.getDebe());
                detalleComprobante.setDebe(null);
                detalleComprobante.setHaberDolar(detalleComprobante.getDebeDolar());
                detalleComprobante.setDebeDolar(null);
            } else if (detalleComprobante.getHaber() != null) {
                detalleComprobante.setDebe(detalleComprobante.getHaber());
                detalleComprobante.setHaber(null);
                detalleComprobante.setDebeDolar(detalleComprobante.getHaberDolar());
                detalleComprobante.setHaberDolar(null);
            }
            if (detalleComprobante.getDebe() == null && detalleComprobante.getHaber() == null) {
                if (detalleComprobante.getDebeDolar() != null) {
                    detalleComprobante.setHaberDolar(detalleComprobante.getDebeDolar());
                    detalleComprobante.setDebeDolar(null);

                } else if (detalleComprobante.getHaberDolar() != null) {
                    detalleComprobante.setDebeDolar(detalleComprobante.getHaberDolar());
                    detalleComprobante.setHaberDolar(null);
                }
            }
            if (detalleComprobante.getDebeDolar() == null && detalleComprobante.getHaberDolar() == null) {
                if (detalleComprobante.getDebe() != null) {
                    detalleComprobante.setHaber(detalleComprobante.getDebe());
                    detalleComprobante.setDebe(null);

                } else if (detalleComprobante.getHaber() != null) {
                    detalleComprobante.setDebe(detalleComprobante.getHaber());
                    detalleComprobante.setHaber(null);
                }
            }
            detalleComprobante.setCntComprobante(cntComprobanteNuevo);
            detalleComprobante.setIdDetalleComprobante(null);
            detalleComprobante.setUsuarioAlta(usuarioLogeado);
            detalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
            detalleComprobante.setFechaAlta(new Date());
            detalleComprobante.setIdAntecesor(cntDetalleComprobante.getIdDetalleComprobante());
            detalleComprobante = encuentraPadreParaAntecesor(detalleComprobante);
            detalleComprobante = cntDetalleComprobanteService.persistCntDetalleComprobantes(detalleComprobante);
            listaDistribucionCentroDeCostos = encuentraCentroDeCostoPorDetalleComprobante(cntDetalleComprobante);
            if (!listaDistribucionCentroDeCostos.isEmpty()) {
                for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaDistribucionCentroDeCostos) {
                    CntDistribucionCentrocosto cntDistribucionCentrocostoAuxiliar = (CntDistribucionCentrocosto) cntDistribucionCentrocosto.clone();
                    cntDistribucionCentrocostoAuxiliar.setIdDistribucionCentrocosto(null);
                    cntDistribucionCentrocostoAuxiliar.setCntDetalleComprobante(detalleComprobante);
                    cntDistribucionCentrocostoAuxiliar.setFechaAlta(new Date());
                    cntDistribucionCentrocostoAuxiliar.setEstado(EnumEstado.PENDIENTE.getCodigo());
                    cntDistribucionCentrocostoAuxiliar.setUsuarioAlta(detalleComprobante.getUsuarioAlta());
                    cntDistribucionCentrocostoAuxiliar.setUsuarioModificacion(null);
                    cntDistribucionCentrocostoAuxiliar.setFechaModificacion(null);
                    cntDistribucionCentrocostoAuxiliar.setIdDistribucionCentrocosto(null);
                    cntDistribucionCentroCostoService.persistCntDistribucionCentrocosto(cntDistribucionCentrocostoAuxiliar);
                }
            }

        }
        List<CntDetalleComprobante> listaDetalleComprobanteNueva = cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobanteNuevo);
        for (CntDetalleComprobante cntDetalleComprobante : listaDetalleComprobanteNueva) {
            cntDetalleComprobante.setIdAntecesor(null);
            cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
        }
        return cntComprobanteNuevo;
    }

    public void nuleaCampoDeIdReplicado(CntComprobante cntComprobante) throws Exception {
        cntComprobante = mergeCntComprobantes(cntComprobante);
        List<CntDetalleComprobante> listaDeDetallesComprobantes = cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante);
        CntFacturacion cntFacturacion;
        if (!listaDeDetallesComprobantes.isEmpty()) {
            for (CntDetalleComprobante cntDetalleComprobante : listaDeDetallesComprobantes) {
                cntDetalleComprobante = cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
                if (cntDetalleComprobante.getIdPadre() == null) {
                    cntFacturacion = cntDetalleComprobanteService.buscaFacturacionPorDetalleComprobantePadres(cntDetalleComprobante);
                    if (cntFacturacion != null) {
                        cntFacturacionService.mergeCntFacturacion(cntFacturacion);
                    }
                }
            }
        }

    }

    public boolean verificaExistenciaMascaraNivelAndSubNivel(String mascaraGenerada) {
        List<CntEntidad> lista = hibernateTemplate.find(
                "select o from CntEntidad o "
                + "where o.mascaraGenerada='" + mascaraGenerada + "' "
                + "and o.cntMascara.grupoNivel.codigo='" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                + "and o.fechaBaja is null");
        return lista.size() > 0 ? false : true;
    }

    public List<CntDetalleComprobante> obtieneDetalleComprobantePadres(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.fechaBaja is null "
                + "and h.idPadre is null "
                + "and h.cntComprobante.idComprobante='" + cntComprobante.getIdComprobante() + "'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntDetalleComprobante> obtieneDetalleComprobantePadresParaDelete(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.idPadre is null "
                + "and h.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "'");
        if (!lista.isEmpty()) {
            return lista;
        }

        return Collections.EMPTY_LIST;
    }

    public boolean existenFacturasIncompletas(CntComprobante cntComprobante) {
//        List<CntFacturacion> lista = hibernateTemplate.find(""
//                + "select j "
//                + "from CntFacturacion j "
//                + "where j.fechaBaja is null "
//                + "and j.cntDetalleComprobante.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante() + " "
//                + "and (j.fechaFactura is null or j.nroInicial is null)");        
        List<CntFacturacion> lista = hibernateTemplate.find(""
                + "select j.cntFacturacion "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntComprobante.idComprobante = " + cntComprobante.getIdComprobante() + " "
                + "and (j.cntFacturacion.fechaFactura is null or j.cntFacturacion.nroInicial is null)");
        return !lista.isEmpty();
    }

    public List<CntComprobante> listaComprobantesPendientesSinDetalleComprobante() throws Exception {
        try {
            List<CntComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntComprobante j "
                    + "where j.idComprobante not in(select h.cntComprobante.idComprobante "
                    + "from CntDetalleComprobante h) "
                    + "and j.fechaBaja is null");
            if (!lista.isEmpty()) {
                return lista;
            }
        } catch (Exception e) {
            throw e;
        }
        return Collections.EMPTY_LIST;
    }

    //Método para eliminar los comprobantes con estado pendiente que no tengan ningún 
    // Detalle Comprobante, esto para limpiar la base, y no tener datos basura.
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeComprobantePendientesSinDetalleComprobante() throws Exception {
        try {
            for (CntComprobante cntComprobante : listaComprobantesPendientesSinDetalleComprobante()) {
                deleteCntComprobantes(cntComprobante);
            }
        } catch (Exception h) {
            throw h;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeDetalleComprobantePendienteSinFactura(CntComprobante cntComprobante) throws Exception {
        try {
            for (CntDetalleComprobante cntDetalleComprobante : cntDetalleComprobanteService.listaDeDetalleComprobantesPadresPorComprobanteSinFechaBaja(cntComprobante)) {
                if (verificaTransaccionRealizadaSiEsAlgunTipoDeFactura(cntDetalleComprobante.getTransaccionRealizada())) {
                    if (!cntFacturacionService.verificaExistenciaDeFacturasDeUnDetalleComprobante(cntDetalleComprobante)) {
                        cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
                    }
                }
            }
        } catch (Exception h) {
            throw h;
        }
    }

    public Boolean verificaTransaccionRealizadaSiEsAlgunTipoDeFactura(String transaccionRealizada) {
        if (transaccionRealizada.equals(EnumTransaccionRealizada.FACTURA_VENTA.getCodigo())) {
            return true;
        } else {
            if (transaccionRealizada.equals(EnumTransaccionRealizada.FACTURA_COMPRA.getCodigo())) {
                return true;
            }
        }
        return false;
    }

    public List<CntDetalleComprobante> obtieneTodosLosDetalleComprobante(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find("select j "
                + "from CntDetalleComprobante j "
                + "where j.cntComprobante.idComprobante='" + cntComprobante.getIdComprobante() + "'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }

    public boolean verificaSiSeCreaOModifica(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> listaDetalleComprobanteConfirmado = hibernateTemplate.find(""
                + "select j "
                + "from CntDetalleComprobante j "
                + "where j.fechaBaja is null "
                + "and j.cntComprobante.idComprobante = '" + cntComprobante.getIdComprobante() + "' "
                + "and j.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "'");
        if (!listaDetalleComprobanteConfirmado.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<CntComprobante> listaComprobantesEnUnRango(Long nroComprobanteInicio, Long nroComprobanteFinal, String periodo, String anio, String tipoComprobante) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MM, dd");
        Date fechaInicialParaAnio = sdf.parse(anio + ", 01, 01");
        Date fechaFinalParaAnio = sdf.parse((Integer.parseInt(anio) + 1) + ", 01, 01");
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechaInicialParaAnioConsulta = formato.format(fechaInicialParaAnio);
        String fechaFinalParaAnioConsulta = formato.format(fechaFinalParaAnio);
        List<CntComprobante> listaDeComprobantes = hibernateTemplate.find(""
                + "select j "
                + "from CntComprobante j "
                + "where j.fechaBaja is null "
                + "and (j.numero >= '" + nroComprobanteInicio.toString() + "' and j.numero <= '" + nroComprobanteFinal.toString() + "') "
                + "and j.periodo = " + periodo + " "
                + "and (j.fecha >= '" + fechaInicialParaAnioConsulta + "' and j.fecha < '" + fechaFinalParaAnioConsulta + "') "
                + "and j.estado = '" + EnumEstado.CONFIRMADO.getCodigo() + "' "
                + "and j.parTipoComprobante.codigo = '" + tipoComprobante + "' "
                + "order by j.numero asc");
        if (!listaDeComprobantes.isEmpty()) {
            return listaDeComprobantes;
        }
        return Collections.EMPTY_LIST;
    }

//    public List<Long> listaDeNumerosDeComprobantes(List<CntComprobante> listaDeComprobantes) {
//        List<Long> lista = new ArrayList<Long>();
//        for (CntComprobante cntComprobante : listaDeComprobantes) {
//            lista.add(cntComprobante.getNumero());
//        }
//        if (!lista.isEmpty()) {
//            return lista;
//        }
//        return Collections.EMPTY_LIST;
//    }
//    public Integer devuelvePosicionEnLaLista(List<CntComprobante> listaDeComprobantes, Long idComprobante) {
//        int contador = new Integer(0);
//        for (CntComprobante cntComprobante : listaDeComprobantes) {
//            contador = contador + 1;
//            if (cntComprobante.getIdComprobante() == idComprobante) {
//                return contador;
//            }
//        }
//        return contador;
//    }
    public List<Integer> listaDeNumerosDeComprobantes(int limite) {
        List<Integer> listaDeNumerosDeComprobantes = new ArrayList<Integer>();
        for (int i = 1; i <= limite; i++) {
            listaDeNumerosDeComprobantes.add(i);
        }
        if (!listaDeNumerosDeComprobantes.isEmpty()) {
            return listaDeNumerosDeComprobantes;
        }
        return Collections.EMPTY_LIST;
    }

    public List<String> listaDeNumerosDeComprobantesParaReporte(List<CntComprobante> listaDeComprobantes) {
        List<String> listaDePaginas = new ArrayList<String>();
        int numero = 0;
        for (CntComprobante cntComprobante : listaDeComprobantes) {
            numero = numero + 1;
            listaDePaginas.add("Pag. - " + numero + " - Comprobante Nro " + cntComprobante.getNumero());
        }
        if (!listaDePaginas.isEmpty()) {
            return listaDePaginas;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntDetalleComprobante> obtieneDetalleComprobantePendientesParaUnComprobante(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.fechaBaja is null "
                + "and h.cntComprobante.idComprobante='" + cntComprobante.getIdComprobante() + "'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntDetalleComprobante> obtieneDetalleComprobanteHijosPorIdPadre(CntDetalleComprobante detalleComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.idPadre=" + detalleComprobante.getIdDetalleComprobante());
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void mergeCntComprobanteAndCntDetalleComprobanteAndFacturaAndDistribucionTotal(CntComprobante cntComprobante, String usuarioLogueado, Date fechaX) throws Exception {
        CntComprobante comprobanteParaGuardar = find(CntComprobante.class, cntComprobante.getIdComprobante());
        comprobanteParaGuardar.setUsuarioModificacion(usuarioLogueado);
        comprobanteParaGuardar.setFechaModificacion(fechaX);
        comprobanteParaGuardar.setUsuarioBaja(usuarioLogueado);
        comprobanteParaGuardar.setFechaBaja(fechaX);
        mergeCntComprobantes(comprobanteParaGuardar);
        CntComprobante cntComprobanteaux = (CntComprobante) cntComprobante.clone();
        cntComprobanteaux.setDescripcion(cntComprobante.getDescripcion());
        cntComprobanteaux.setGlosaComprobante(cntComprobante.getGlosaComprobante());
        cntComprobanteaux.setTipoMoneda(cntComprobante.getTipoMoneda());
        cntComprobanteaux.setEstado(EnumEstado.CONFIRMADO.getCodigo());
        cntComprobanteaux.setIdAntecesor(cntComprobante.getIdComprobante());
        cntComprobanteaux.setIdComprobante(null);
        cntComprobanteaux.setFechaAlta(fechaX);
        cntComprobanteaux.setUsuarioAlta(usuarioLogueado);
        cntComprobanteaux.setFechaModificacion(null);
        cntComprobanteaux.setUsuarioModificacion(null);
        cntComprobanteaux.setFechaBaja(null);
        cntComprobanteaux.setUsuarioBaja(null);
        persistCntComprobantes(cntComprobanteaux);
        try {
            for (CntDetalleComprobante cntDetalleComprobantePendiente : obtieneDetalleComprobantePendientesParaUnComprobante(comprobanteParaGuardar)) {
                if (cntDetalleComprobantePendiente.getIdPadre() == null && cntDetalleComprobantePendiente.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                    if (cntDetalleComprobantePendiente.getIdAntecesor() != null) {
                        CntDetalleComprobante detalleComprobantePadreAntecesor = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, cntDetalleComprobantePendiente.getIdAntecesor());
                        detalleComprobantePadreAntecesor.setFechaModificacion(fechaX);
                        detalleComprobantePadreAntecesor.setUsuarioModificacion(usuarioLogueado);
                        detalleComprobantePadreAntecesor.setFechaBaja(fechaX);
                        detalleComprobantePadreAntecesor.setUsuarioBaja(usuarioLogueado);
                        cntDetalleComprobanteService.mergeCntDetalleComprobantes(detalleComprobantePadreAntecesor);
                        for (CntDetalleComprobante detalleComprobanteHijosAntecesor : obtieneDetalleComprobanteHijosPorIdPadre(detalleComprobantePadreAntecesor)) {
                            detalleComprobanteHijosAntecesor.setFechaModificacion(fechaX);
                            detalleComprobanteHijosAntecesor.setUsuarioModificacion(usuarioLogueado);
                            detalleComprobanteHijosAntecesor.setFechaBaja(fechaX);
                            detalleComprobanteHijosAntecesor.setUsuarioBaja(usuarioLogueado);
                            cntDetalleComprobanteService.mergeCntDetalleComprobantes(detalleComprobanteHijosAntecesor);
                        }
                        //metodo factura
                        CntFacturacion cntFacturacionModificacion = cntFacturacionService.obtieneFacturaPorDetalleComprobanteParaDelete(cntDetalleComprobantePendiente);
                        if (cntFacturacionModificacion != null) {
                            cntFacturacionModificacion.setUsuarioAlta(usuarioLogueado);
                            cntFacturacionModificacion.setFechaAlta(fechaX);
                            cntFacturacionService.mergeCntFacturacionModificaTotal(cntFacturacionModificacion);
                        }
                        CntFacturacion cntFacturacionAntecesor = cntFacturacionService.obtieneFacturaPorDetalleComprobanteParaDelete(detalleComprobantePadreAntecesor);
                        if (cntFacturacionAntecesor != null) {
                            cntFacturacionAntecesor.setUsuarioModificacion(usuarioLogueado);
                            cntFacturacionAntecesor.setFechaModificacion(fechaX);
                            cntFacturacionAntecesor.setUsuarioBaja(usuarioLogueado);
                            cntFacturacionAntecesor.setFechaBaja(fechaX);
                            cntFacturacionService.mergeCntFacturacionModificaTotal(cntFacturacionAntecesor);
                        }
//                      metodo JONAS
                        cntDistribucionCentroCostoService.modificaDistribucionCentroDeCostoPorDetalleComprobante(cntDetalleComprobantePendiente, usuarioLogueado, fechaX);
                    }
                } else {
                    if (cntDetalleComprobantePendiente.getEstado().equals(EnumEstado.PENDIENTE.getCodigo())) {
                        cntDistribucionCentroCostoService.modificaDistribucionCentroDeCostoPorDetalleComprobante(cntDetalleComprobantePendiente, usuarioLogueado, fechaX);
                    }
                }
                cntDetalleComprobantePendiente.setCntComprobante(cntComprobanteaux);
                if (!cntDetalleComprobantePendiente.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo())) {
                    cntDetalleComprobantePendiente.setUsuarioAlta(usuarioLogueado);
                    cntDetalleComprobantePendiente.setFechaAlta(fechaX);
                    cntDetalleComprobantePendiente.setEstado(EnumEstado.CONFIRMADO.getCodigo());
//                    aumentado para cambiar las transacciones de acuerdo al cambio del tipo de Cambio
                }
                if (cntDetalleComprobantePendiente.getDebe() != null) {
                    cntDetalleComprobantePendiente.setDebeDolar((cntDetalleComprobantePendiente.getDebe().divide(cntComprobanteaux.getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
                } else if (cntDetalleComprobantePendiente.getHaber() != null) {
                    cntDetalleComprobantePendiente.setHaberDolar((cntDetalleComprobantePendiente.getHaber().divide(cntComprobanteaux.getTipoCambio(), 4, RoundingMode.CEILING)).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                CntFacturacion cntFacturacion = cntDetalleComprobantePendiente.getCntFacturacion();
                if (cntFacturacion != null) {
                    cntFacturacion.setFechaFactura(comprobanteParaGuardar.getFecha());
                    cntFacturacionService.mergeCntFacturacion(cntFacturacion);
                }
                cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobantePendiente);
            }
            if (cntDetalleComprobanteService.verificaCuentaDiferenciaDeCambio(cntComprobanteaux)) {
                cntDetalleComprobanteService.guardaCuentaAjusteMonetarioDiferenciaTipoCambioModifica(cntComprobanteaux);
            } else {
                cntDetalleComprobanteService.guardaCuentaAjusteMonetarioDiferenciaTipoCambio(cntDetalleComprobanteService.ordenaSegunPosicion(cntComprobanteaux), cntComprobanteaux, usuarioLogueado);
            }
        } catch (Exception h) {
            throw h;
        }
    }

    public void copiaUnComprobanteNVeces(CntComprobante cntComprobante, int n) {
        long idTemporalAnterior = 0;
        Date fechaInicial = new Date();
        long contadorNumero = 0L;
        try {
            contadorNumero = ultimoNumeroComprobante(cntComprobante);
        } catch (Exception ex) {
            Logger.getLogger(CntComprobantesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < n; i++) {
            int dias = (int) ((Math.random() * 27) + 1); //numeros del 1 al 30 
            int mes = (int) ((Math.random() * 11) + 1); //numeros del 1 al 12                    
            Date fecha = new Date("" + mes + "/" + dias + "/2014");
            CntComprobante cntComprobanteCopia = (CntComprobante) cntComprobante.clone();
            cntComprobanteCopia.setIdComprobante(null);
            cntComprobanteCopia.setNumero(contadorNumero);
            cntComprobanteCopia.setFecha(fecha);
            cntComprobanteCopia.setPeriodo(mes + "");
            cntComprobanteCopia.setFechaAlta(fecha);
            contadorNumero++;
            try {
                persistCntComprobantes(cntComprobanteCopia);
            } catch (Exception ex) {
                Logger.getLogger(CntComprobantesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<CntDetalleComprobante> listaDetallesComprobante = cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobante);
            for (CntDetalleComprobante cntDetalleComprobante : listaDetallesComprobante) {
                CntDetalleComprobante cntDetalleComprobanteCopia = (CntDetalleComprobante) cntDetalleComprobante.clone();
                cntDetalleComprobanteCopia.setIdDetalleComprobante(null);
                cntDetalleComprobanteCopia.setCntComprobante(cntComprobanteCopia);
                cntDetalleComprobanteCopia.setFechaAlta(fecha);
                if (cntDetalleComprobanteCopia.getIdPadre() != null) {
                    cntDetalleComprobanteCopia.setIdPadre(idTemporalAnterior);
                }
                try {
                    cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobanteCopia);
                    if (cntDetalleComprobanteCopia.getIdPadre() == null) {
                        idTemporalAnterior = cntDetalleComprobanteCopia.getIdDetalleComprobante();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CntComprobantesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        Date fechaFinal = new Date();
        Long tipo = (fechaFinal.getTime() - fechaInicial.getTime()) / 1000;
    }

    public boolean compruebaSiElPeriodoEstaHabilitado(CntComprobante cntComprobante) {
        Calendar fechaActual = Calendar.getInstance();
        Calendar fechaComprobante = Calendar.getInstance();
        fechaActual.setTime(new Date());
        fechaComprobante.setTime(cntComprobante.getFecha());
        int mesActual = fechaActual.get(Calendar.MONTH) + 1;
        int mesComprobante = fechaComprobante.get(Calendar.MONTH) + 1;
        if (mesActual == mesComprobante) {
            return true;
        } else {
            return periodoHabilitado(cntComprobante.getFecha());
        }
    }

    public boolean periodoHabilitado(Date fechaComprobante) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fechaInicialFormateada = formatter.parse(parParametricasService.findParValor("FIRC").getValor());
            Date fechaFinalFormateada = formatter.parse(parParametricasService.findParValor("FFRC").getValor());
            if (fechaComprobante.after(fechaInicialFormateada) && new Date().before(fechaFinalFormateada)) {
                return true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(CntComprobantesServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<CntDistribucionCentrocosto> encuentraCentroDeCostoPorDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        List<CntDistribucionCentrocosto> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntDistribucionCentrocosto j "
                + "where j.fechaBaja is null "
                + "and j.cntDetalleComprobante.idDetalleComprobante = '" + cntDetalleComprobante.getIdDetalleComprobante() + "' ");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public Boolean verificaDetalleComprobantePendientesPorComprobante(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.fechaBaja is null "
                + "and h.cntComprobante.idComprobante='" + cntComprobante.getIdComprobante() + "' and h.estado='" + EnumEstado.PENDIENTE.getCodigo() + "'");
        if (lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public Boolean verificaDetalleComprobanteConfirmadosPorComprobante(CntComprobante cntComprobante) {
        List<CntDetalleComprobante> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntDetalleComprobante h "
                + "where h.fechaBaja is null "
                + "and h.cntComprobante.idComprobante='" + cntComprobante.getIdComprobante() + "' and h.estado='" + EnumEstado.CONFIRMADO.getCodigo() + "'");
        if (lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<CntComprobante> listaComprobantesSegunMonto(BigDecimal monto, CntComprobante cntComprobante) {
        String consulta = "";
        if (cntComprobante != null) {
            consulta = "and j.numero >= " + cntComprobante.getNumero() + " ";
        }
        List<CntComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntComprobante j "
                + "where j.fechaBaja is null "
                + "and j.estado = 'CONF' "
                + "" + consulta + ""
                + "and j.totalComprobantes = " + monto + "");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntComprobante> listaComprobantesPorPalabra(String palabra, CntComprobante cntComprobante) {
        String consulta = "";
        if (cntComprobante != null) {
            consulta = "and j.numero >= " + cntComprobante.getNumero() + " ";
        }
        List<CntComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntComprobante j "
                + "where j.fechaBaja is null "
                + "and j.estado = 'CONF' "
                + "" + consulta + ""
                + "and j.descripcion like '%" + palabra + "%'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public Comprobante calculaExcentoIceDescuentoIvaXcxpWS(Comprobante comprobante) {
        try {
            CntFacturacion cntFacturacion = new CntFacturacion();
            BigDecimal excento, ice;
            excento = new BigDecimal(Float.toString(comprobante.getExcento()));
            ice = new BigDecimal(Float.toString(comprobante.getIce()));
            CntTipoCambio cntTipoCambio = cntTipoCambioService.find(CntTipoCambio.class, comprobante.getIdTipoDeCambio());
            List<CuentaMonto> listaDeCuentas = comprobante.getListaCuentas();
            BigDecimal totalIVA = new BigDecimal(BigInteger.ZERO);
            for (CuentaMonto cntCuentaMontoPojo : listaDeCuentas) {
                CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, cntCuentaMontoPojo.getIdCuentaMontoPojo());
                cntFacturacion.setMonto(new BigDecimal(Float.toString(cntCuentaMontoPojo.getMonto())));
                cntFacturacion.setExcento(excento);
                cntFacturacion.setIce(ice);
                cntFacturacion = cntFacturacionService.obtieneIVA(cntFacturacion, excento, ice, cntEntidad, cntTipoCambio.getTipoCambio());
                totalIVA = totalIVA.add(cntFacturacion.getIva());
            }
            comprobante.setIva(totalIVA.floatValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comprobante;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntComprobante guardaComprobanteCxP(Comprobante comprobante) throws Exception {
        try {
//            comprobante
            CntTipoCambio cntTipoCambio = cntTipoCambioService.find(CntTipoCambio.class, comprobante.getIdTipoDeCambio());
            ParTipoComprobante parTipoComprobante = (ParTipoComprobante) parParametricasService.find(ParTipoComprobante.class, "EGRE");
            CntComprobante cntComprobante = new CntComprobante();
            cntComprobante.setTipoCambio(cntTipoCambio.getTipoCambio());
            cntComprobante.setModulo(EnumTipoModulo.CUENTAS_POR_PAGAR.getCodigo());
            cntComprobante.setEstado(EnumEstado.CONFIRMADO.getCodigo());
            cntComprobante.setLoginUsuario(comprobante.getLoginUsuario());
            cntComprobante.setTotalComprobantes(new BigDecimal(Float.toString(comprobante.getMonto())));
            cntComprobante.setTotalComprobantesSegMoneda(new BigDecimal(Float.toString(comprobante.getMontoSegMoneda())));
            cntComprobante.setFecha(comprobante.getFechaComprobante());
            cntComprobante.setDescripcion(comprobante.getConcepto());
            cntComprobante.setParTipoComprobante(parTipoComprobante);
            cntComprobante.setPeriodo(obtienePeriodoPorFecha(comprobante.getFechaComprobante()));
            cntComprobante.setTipoMoneda(EnumTipoMoneda.AMBOS.getCodigo());
            cntComprobante.setGlosaComprobante(comprobante.getGlosa());
            cntComprobante.setNumero(ultimoNumeroComprobante(cntComprobante));
            cntComprobante.setUsuarioAlta(comprobante.getLoginUsuario());
            cntComprobante.setFechaAlta(new Date());
            cntComprobante = persistCntComprobantes(cntComprobante);
//            factura
            CntFacturacion cntFacturacion = new CntFacturacion();
            cntFacturacion.setCodigoControl(comprobante.getCodigoControl());
            cntFacturacion.setCreditoFiscalTransitorio(comprobante.getCreditoFiscalTransitorio());
            cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntFacturacion.setExcento(new BigDecimal(Float.toString(comprobante.getExcento())));
            cntFacturacion.setExcentoSegMoneda(new BigDecimal(Float.toString(comprobante.getExcentoSegMoneda())));
            cntFacturacion.setFechaFactura(comprobante.getFechaFactura());
            cntFacturacion.setIce(new BigDecimal(Float.toString(comprobante.getIce())));
            cntFacturacion.setIceSegMoneda(new BigDecimal(Float.toString(comprobante.getIceSegMoneda())));
//            cntFacturacion.setIdBanco(null);
//            cntFacturacion.setIdProveedorCliente(comprobante.getIdProveedorCliente()); //idProvCli
            cntFacturacion.setIva(new BigDecimal(Float.toString(comprobante.getIva())));
            cntFacturacion.setIvaSegMoneda(new BigDecimal(Float.toString(comprobante.getIvaSegMoneda())));
            cntFacturacion.setLoginUsuario(comprobante.getLoginUsuario());
            cntFacturacion.setMonto(new BigDecimal(Float.toString(comprobante.getMonto())));
            cntFacturacion.setMontoSegMoneda(new BigDecimal(Float.toString(comprobante.getMontoSegMoneda())));
            cntFacturacion.setMovimiento(EnumMovimientoFactura.FACTURA_VENTA.getCodigo());
            cntFacturacion.setNroAutorizacion(null);
//            detalle
            CntDetalleComprobante cntDetalleComprobante;
            long posicion = 1;
            for (CuentaMonto cuentaMonto : comprobante.getListaCuentas()) {
                cntDetalleComprobante = new CntDetalleComprobante();
                cntDetalleComprobante.setCntComprobante(cntComprobante);
                cntDetalleComprobante.setCntEntidad(cntEntidadesService.find(CntEntidad.class, cuentaMonto.getIdCuentaMontoPojo()));
                cntDetalleComprobante.setCntFacturacion(cntFacturacion);
                cntDetalleComprobante.setPosicion(posicion);//metodo para la posicion
                cntDetalleComprobante.setHaber(new BigDecimal(Float.toString(cuentaMonto.getMonto())));

//                cntDetalleComprobante
                posicion++;
                cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            }

            return cntComprobante;
        } catch (Exception e) {
            throw e;
        }
    }

    public CalculosTributarios calculosTributarios(CalculosTributarios calculosTributarios) {
        try {
            CntFacturacion cntFacturacion = new CntFacturacion();
            BigDecimal excento, ice;
            excento = new BigDecimal(Float.toString(calculosTributarios.getExcento()));
            ice = new BigDecimal(Float.toString(calculosTributarios.getIce()));
            CntTipoCambio cntTipoCambio = cntTipoCambioService.find(CntTipoCambio.class, calculosTributarios.getIdTipoDeCambio());
            List<CuentaMonto> listaDeCuentas = calculosTributarios.getListaCuentas();
            BigDecimal totalIVA = new BigDecimal(BigInteger.ZERO);
            for (CuentaMonto cntCuentaMontoPojo : listaDeCuentas) {
                CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, cntCuentaMontoPojo.getIdCuentaMontoPojo());
                cntFacturacion.setMonto(new BigDecimal(Float.toString(cntCuentaMontoPojo.getMonto())));
                cntFacturacion.setExcento(excento);
                cntFacturacion.setIce(ice);
                cntFacturacion = cntFacturacionService.obtieneIVA(cntFacturacion, excento, ice, cntEntidad, cntTipoCambio.getTipoCambio());
                totalIVA = totalIVA.add(cntFacturacion.getIva());
            }
            calculosTributarios.setIva(totalIVA.floatValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calculosTributarios;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaCntDetalleComprobante(CntComprobante cntComprobante, List<CntDetalleComprobante> listCntDetalleComprobante) throws Exception {
        try {
//            CntComprobante persistCntComprobantes = cntComprobantesService.persistCntComprobantes(cntComprobante);
//            cntComprobante = persistCntComprobantes(cntComprobante);
            super.persist(cntComprobante);
            for (CntDetalleComprobante cntDetalleComprobante : listCntDetalleComprobante) {
                if (cntDetalleComprobante.getIdPadre() == 7) {
                    cntDetalleComprobante.setIdPadre(cntComprobante.getIdComprobante());
                }
                cntDetalleComprobante.setCntComprobante(cntComprobante);
                cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);

            }
        } catch (Exception e) {
            throw e;
        }
    }

    public CntComprobante getObtieneCpbtePorNumeroCpbte(Long nroCpbte, String codigo) {
        String cod = null;
        if (codigo.equals("E")) {
            cod = EnumTipoComprobante.EGRESO.getCodigo();
        } else {
            if (codigo.equals("T")) {
                cod = EnumTipoComprobante.TRASPASO.getCodigo();
            } else {
                if (codigo.equals("I")) {
                    cod = EnumTipoComprobante.INGRESO.getCodigo();
                }
            }

        }// cierra esl

        List<CntComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntComprobante j "
                + "where j.fechaBaja is null "
                + "and j.estado = 'CONF' "
                + "and j.numero = " + nroCpbte + " "
                + "and j.parTipoComprobante.codigo = '" + cod + "' ");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }
}
