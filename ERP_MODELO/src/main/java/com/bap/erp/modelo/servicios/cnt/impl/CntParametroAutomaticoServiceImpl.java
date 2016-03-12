package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico;
import com.bap.erp.modelo.enums.EnumConfirmacion;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntParametroAutomaticoService;
import com.iknow.utils.ObjectUtils;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntParametroAutomaticoServiceImpl extends GenericDaoImpl<CntParametroAutomatico> implements CntParametroAutomaticoService {

    @Autowired
    CntEntidadesService cntEntidadesService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntParametroAutomatico persistCntParametroAutomatico(CntParametroAutomatico cntParametroAutomatico) throws Exception {
        try {
            super.persist(cntParametroAutomatico);
        } catch (Exception e) {
            throw e;
        }
        return cntParametroAutomatico;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntParametroAutomatico mergeCntParametroAutomatico(CntParametroAutomatico cntParametroAutomatico) throws Exception {
        try {
            super.merge(cntParametroAutomatico);
        } catch (Exception e) {
            throw e;
        }
        return cntParametroAutomatico;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeCntParametroAutomatico(CntParametroAutomatico cntParametroAutomatico) throws Exception {
        try {
//            CntParametroAutomatico cntParametroAutomaticoBD = super.find(CntParametroAutomatico.class, cntParametroAutomatico.getIdParametroAutomatico());
            ObjectUtils.printObjectState(cntParametroAutomatico);
            super.remove(cntParametroAutomatico);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntParametroAutomatico> listaCntParametroAutomatico() {
        try {
            List<CntParametroAutomatico> lista = hibernateTemplate.find(""
                    + "select a "
                    + "from CntParametroAutomatico a "
                    + "where a.fechaBaja is null");
            return lista;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public CntParametroAutomatico obtieneObjetoDeParametroAutomatico(CntEntidad cntEntidad) {
        List<CntParametroAutomatico> list;
        list = hibernateTemplate.find(
                "select j "
                + "from CntParametroAutomatico j "
                + "where j.fechaBaja is null "
                + "and j.cntEntidad.idEntidad=" + cntEntidad.getIdEntidad() + "");
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public List<CntParametroAutomatico> listaDeParametrosAutomaticosDeHijos(CntEntidad cntEntidad) {
        List<CntParametroAutomatico> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntParametroAutomatico j "
                + "where j.fechaBaja is null "
                + "and j.cntEntidad.idEntidad IN "
                + "(select o.idEntidad "
                + "from CntEntidad o "
                + "where o.fechaBaja is null "
                + "and o.idEntidadPadre = " + cntEntidad.getIdEntidad() + ")");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public void grabarParametrosHijosMasivo(CntEntidad cntEntidad, String usuarioLogeado) throws Exception {
        CntParametroAutomatico cntParametroAutomaticoAux = obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (cntEntidad.getNivel() == 2) {
            if (!listaDeParametrosAutomaticosDeHijos(cntEntidad).isEmpty()) {
                List<CntParametroAutomatico> list = listaDeParametrosAutomaticosDeHijos(cntEntidad);
                for (CntParametroAutomatico cntParametroAutomatico : list) {
                    //Condicion para no modificar los parametros automaticos de cierta entidad
                    if (cntParametroAutomatico.getCntEntidad().getParametrosIndividuales().equals(EnumConfirmacion.NO.getCodigo())) {
                        cntParametroAutomatico.setTipoCalculoAutomatico(cntParametroAutomaticoAux.getTipoCalculoAutomatico());
                        cntParametroAutomatico.setTipoMovimiento(cntParametroAutomaticoAux.getTipoMovimiento());
                        cntParametroAutomatico.setFacturaCompra(cntParametroAutomaticoAux.getFacturaCompra());
                        cntParametroAutomatico.setCreditoFiscalNoDeducible(cntParametroAutomaticoAux.getCreditoFiscalNoDeducible());
                        cntParametroAutomatico.setFacturaVenta(cntParametroAutomaticoAux.getFacturaVenta());
                        cntParametroAutomatico.setSinFactura(cntParametroAutomaticoAux.getSinFactura());
                        cntParametroAutomatico.setParTipoRetencion(cntParametroAutomaticoAux.getParTipoRetencion());
                        cntParametroAutomatico.setParTipoRetencionGrossingUp(cntParametroAutomaticoAux.getParTipoRetencionGrossingUp());
                        cntParametroAutomatico.setParAjustes(cntParametroAutomaticoAux.getParAjustes());
                        cntParametroAutomatico.setFechaModificacion(cntParametroAutomaticoAux.getFechaModificacion());
                        cntParametroAutomatico.setUsuarioModificacion(cntParametroAutomaticoAux.getUsuarioModificacion());
                        mergeCntParametroAutomatico(cntParametroAutomatico);
                    }
                }
            }
        }
    }

    public CntParametroAutomatico obtieneParametrosAutomaticosDeNivel2(CntEntidad cntEntidad) throws Exception {
        if (cntEntidad.getNivel() == 2) {
            try {
                return obtieneObjetoDeParametroAutomatico(cntEntidad);
            } catch (Exception ex) {
                throw ex;
            }
        } else if (cntEntidad.getNivel() == 1) {
            try {
                return obtieneObjetoDeParametroAutomatico(cntEntidadesService.find(CntEntidad.class, cntEntidad.getIdEntidadPadre()));
            } catch (Exception ex) {
                throw ex;
            }
        }
        return null;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void daDeBajaTodosLosParametrosAutomaticos(String usuarioLogin) throws Exception {
        Date fechaBaja = new Date();
        try {
            List<CntParametroAutomatico> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from CntParametroAutomatico j "
                    + "where j.fechaBaja is null");
            for (CntParametroAutomatico cntParametroAutomatico : lista) {
                cntParametroAutomatico.setUsuarioBaja(usuarioLogin);
                cntParametroAutomatico.setFechaBaja(fechaBaja);
                mergeCntParametroAutomatico(cntParametroAutomatico);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void grabarParametrosHijosMasivoCondicionIndividual(CntEntidad cntEntidad, String usuarioLogin, String individuales) throws Exception {
        CntParametroAutomatico cntParametroAutomaticoAux = obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (cntEntidad.getNivel() == 2) {
            if (!listaDeParametrosAutomaticosDeHijos(cntEntidad).isEmpty()) {
                List<CntParametroAutomatico> list = listaDeParametrosAutomaticosDeHijos(cntEntidad);
                for (CntParametroAutomatico cntParametroAutomatico : list) {
                    //Condicion para no modificar los parametros automaticos de cierta entidad
                    cntParametroAutomatico.setTipoCalculoAutomatico(cntParametroAutomaticoAux.getTipoCalculoAutomatico());
                    cntParametroAutomatico.setTipoMovimiento(cntParametroAutomaticoAux.getTipoMovimiento());
                    cntParametroAutomatico.setFacturaCompra(cntParametroAutomaticoAux.getFacturaCompra());
                    cntParametroAutomatico.setCreditoFiscalNoDeducible(cntParametroAutomaticoAux.getCreditoFiscalNoDeducible());
                    cntParametroAutomatico.setFacturaVenta(cntParametroAutomaticoAux.getFacturaVenta());
                    cntParametroAutomatico.setSinFactura(cntParametroAutomaticoAux.getSinFactura());
                    cntParametroAutomatico.setParTipoRetencion(cntParametroAutomaticoAux.getParTipoRetencion());
                    cntParametroAutomatico.setParTipoRetencionGrossingUp(cntParametroAutomaticoAux.getParTipoRetencionGrossingUp());
                    cntParametroAutomatico.setParAjustes(cntParametroAutomaticoAux.getParAjustes());
                    cntParametroAutomatico.setFechaModificacion(cntParametroAutomaticoAux.getFechaModificacion());
                    cntParametroAutomatico.setUsuarioModificacion(cntParametroAutomaticoAux.getUsuarioModificacion());
                    cntParametroAutomatico = mergeCntParametroAutomatico(cntParametroAutomatico);
                    CntEntidad entidad = cntEntidadesService.find(CntEntidad.class, cntParametroAutomatico.getCntEntidad().getIdEntidad());
                    entidad.setParametrosIndividuales("N");
                    cntEntidadesService.mergeCntObjetos(cntEntidad);
                }
            }
        }
    }

    public Boolean verificaRelacionCntEntidadConCntParametroAutomatico(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntParametroAutomatico> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntParametroAutomatico h "
                    + "where h.cntEntidad.idEntidad=" + cntEntidad.getIdEntidad() + " and h.fechaBaja is null");
            return lista.isEmpty();
        } catch (Exception e) {
            throw e;
        }
    }

    public CntParametroAutomatico obtieneCntParametroAutomaticoPorCuenta(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntParametroAutomatico> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntParametroAutomatico h "
                    + "where h.cntEntidad.idEntidad=" + cntEntidad.getIdEntidad() + " "
                    + "and h.fechaBaja is null");
            return !lista.isEmpty() ? lista.get(0) : new CntParametroAutomatico();
        } catch (Exception e) {
            throw e;
        }
    }
}
