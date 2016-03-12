package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliarPorCuenta;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresPorCuentaService;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntAuxiliaresPorCuentaServiceImpl extends GenericDaoImpl<CntAuxiliarPorCuenta> implements CntAuxiliaresPorCuentaService {

    @Autowired
    CntEntidadesService cntEntidadesService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntAuxiliarPorCuenta findauxiliarPorCuenta(Long id) {
        List<CntAuxiliarPorCuenta> lista = hibernateTemplate.find("select c from CntAuxiliarPorCuenta c where c.fechaBaja is null  and c.cntAuxiliar.idAuxiliar='" + id + "'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return new CntAuxiliarPorCuenta();
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntAuxiliarPorCuenta findAuxiliarPorCuentaPorAuxiliarYEntidad(Long id, CntEntidad cntEntidad) {
        List<CntAuxiliarPorCuenta> lista = hibernateTemplate.find("select c from CntAuxiliarPorCuenta c where c.fechaBaja is null  and c.cntEntidad.idEntidad='" + cntEntidad.getIdEntidad() + "'  and c.cntAuxiliar.idAuxiliar='" + id + "'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return new CntAuxiliarPorCuenta();
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntAuxiliarPorCuenta persistCntAuxiliaresPorCuenta(CntAuxiliarPorCuenta cntAuxiliarPorCuenta) throws Exception {
        try {
            super.persist(cntAuxiliarPorCuenta);
        } catch (Exception e) {
            throw e;
        }
        return cntAuxiliarPorCuenta;
    }

    public CntAuxiliarPorCuenta mergeCntAuxiliaresPorCuenta(CntAuxiliarPorCuenta cntAuxiliarPorCuenta) throws Exception {
        try {
            super.merge(cntAuxiliarPorCuenta);
        } catch (Exception e) {
            throw e;
        }
        return cntAuxiliarPorCuenta;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void persistCntAuxiliaresPorCuentaListaSeleccionada(CntEntidad entidad, List<CntAuxiliar> listAuxiliaresseleccionados) throws Exception {
        try {
            for (CntAuxiliar cntAuxiliar : listAuxiliaresseleccionados) {
                CntAuxiliarPorCuenta porCuenta = new CntAuxiliarPorCuenta();
                porCuenta.setCntAuxiliar(cntAuxiliar);
                porCuenta.setCntEntidad(entidad);
                porCuenta.setFechaAlta(entidad.getFechaAlta());
                porCuenta.setUsuarioAlta(entidad.getUsuarioAlta());
                persistCntAuxiliaresPorCuenta(porCuenta);
            }


        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void mergeCntAuxiliaresPorCuentaListaSeleccionada(CntEntidad entidad, List<CntAuxiliar> listAuxiliaresSeleccionados) throws Exception {
        int sw;
        int cont;
        int sw1;
        try {
            List<CntAuxiliar> cntAuxiliarAsignadoCuenta = cntEntidadesService.listaDeAuxiliaresPorEntidad(entidad);
            if (!listAuxiliaresSeleccionados.isEmpty()) {
                for (CntAuxiliar cntAuxiliarListSeleccion : listAuxiliaresSeleccionados) {
                    cont = 0;
                    sw = 0;
                    while (cont < cntAuxiliarAsignadoCuenta.size()) {
                        if (cntAuxiliarListSeleccion.getIdAuxiliar().equals(cntAuxiliarAsignadoCuenta.get(cont).getIdAuxiliar())) {
                            sw = 1;
                            cont = cntAuxiliarAsignadoCuenta.size();
                        }
                        cont = cont + 1;
                    }
                    if (sw == 0) {
                        CntAuxiliarPorCuenta cntAuxiliarPorCuentaNueva = new CntAuxiliarPorCuenta();
                        cntAuxiliarPorCuentaNueva.setCntEntidad(entidad);
                        cntAuxiliarPorCuentaNueva.setCntAuxiliar(cntAuxiliarListSeleccion);
                        cntAuxiliarPorCuentaNueva.setFechaAlta(entidad.getFechaModificacion());
                        cntAuxiliarPorCuentaNueva.setUsuarioAlta(entidad.getUsuarioModificacion());
                        persistCntAuxiliaresPorCuenta(cntAuxiliarPorCuentaNueva);
                    }
                }
                List<CntAuxiliar> cntAuxiliarAsignadoCuentaDos = cntEntidadesService.listaDeAuxiliaresPorEntidad(entidad);
                for (CntAuxiliar cntAuxiliarCuentaSeleccionada : cntAuxiliarAsignadoCuentaDos) {
                    sw1 = 0;
                    for (CntAuxiliar cntAuxiliarNuevaSeleccion : listAuxiliaresSeleccionados) {
                        if (cntAuxiliarCuentaSeleccionada.getIdAuxiliar().equals(cntAuxiliarNuevaSeleccion.getIdAuxiliar())) {
                            sw1 = 1;
                        }
                    }
                    if (sw1 == 0) {
                        CntAuxiliarPorCuenta cntAuxiliarPorCuentaModificar = findauxiliarPorCuenta(cntAuxiliarCuentaSeleccionada.getIdAuxiliar());
                        cntAuxiliarPorCuentaModificar.setFechaBaja(entidad.getFechaModificacion());
                        cntAuxiliarPorCuentaModificar.setUsuarioBaja(entidad.getUsuarioModificacion());
                        mergeCntAuxiliaresPorCuenta(cntAuxiliarPorCuentaModificar);
                    }
                }
            } else {
                bajaListaAuxiliaresPorEntidad(entidad);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void bajaListaAuxiliaresPorEntidad(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntAuxiliar> cntAuxiliarAsignadoCuenta = cntEntidadesService.listaDeAuxiliaresPorEntidad(cntEntidad);
            for (CntAuxiliar cntAuxiliar : cntAuxiliarAsignadoCuenta) {
                CntAuxiliarPorCuenta cntAuxiliarPorCuentaModificar = findAuxiliarPorCuentaPorAuxiliarYEntidad(cntAuxiliar.getIdAuxiliar(), cntEntidad);
                cntAuxiliarPorCuentaModificar.setFechaBaja(cntEntidad.getFechaModificacion());
                cntAuxiliarPorCuentaModificar.setUsuarioBaja(cntEntidad.getUsuarioModificacion());
                mergeCntAuxiliaresPorCuenta(cntAuxiliarPorCuentaModificar);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Long> listaAuxiliarDetalleComprobante(CntEntidad cntEntidad) {
        List<Long> lista = hibernateTemplate.find(""
                + "select  distinct c.idAuxiliar "
                + "from CntDetalleComprobante c "
                + "where c.cntEntidad.idEntidad=" + cntEntidad.getIdEntidad() + " "
                + "and c.fechaBaja is null and idAuxiliar is not null");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }
//compara si un auxiliar esta en detalle comprobante y pertenece a una cuenta para que no se modifique

    public Boolean comparaAuxiliarDetalleComprobanteEntidad(Long idAuxiliarAsignadoEntidad, CntEntidad cntEntidad) throws Exception {
        int cont = 0;
        List<Long> listaAuxiliarEntidad = listaAuxiliarDetalleComprobante(cntEntidad);
        if (!listaAuxiliarEntidad.isEmpty()) {
            while (cont < listaAuxiliarEntidad.size()) {
                if (listaAuxiliarEntidad.get(cont).equals(idAuxiliarAsignadoEntidad)) {
                    cont = listaAuxiliarEntidad.size() + 1;
                    return true;
                } else {
                    cont = cont + 1;
                }
            }
        }
        return false;
    }
    
    public Boolean verificaRelacionCntEntidadConCntAuxiliarPorCuenta(CntEntidad cntEntidad) throws Exception {
        try {
            List<CntAuxiliarPorCuenta> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntAuxiliarPorCuenta h "
                    + "where h.cntEntidad.idEntidad=" + cntEntidad.getIdEntidad() + " and h.fechaBaja is null");
            return !lista.isEmpty();
        } catch (Exception e) {
            throw e;
        }
    }
}
