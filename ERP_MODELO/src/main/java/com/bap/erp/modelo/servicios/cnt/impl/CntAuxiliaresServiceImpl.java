package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliarPorCuenta;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import java.util.Collections;
import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntAuxiliaresServiceImpl extends GenericDaoImpl<CntAuxiliar> implements CntAuxiliaresService {

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntAuxiliar persistCntAuxiliares(CntAuxiliar cntAuxiliares) throws Exception {
        try {
            super.persist(cntAuxiliares);
        } catch (Exception e) {
            throw e;
        }
        return cntAuxiliares;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntAuxiliar mergeCntAuxiliares(CntAuxiliar cntAuxiliares) throws Exception {
        try {
            super.merge(cntAuxiliares);
        } catch (Exception e) {
            throw e;
        }
        return cntAuxiliares;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeCntAuxiliares(CntAuxiliar cntAuxiliares) throws Exception {
        try {
            super.remove(cntAuxiliares);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntAuxiliar> listaCntAuxiliares() {
        try {
            List<CntAuxiliar> lista = hibernateTemplate.find(
                    "select a from CntAuxiliar a where a.fechaBaja is null");
            return lista;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public String ultimo_numero_auxiliar() {
        List<CntAuxiliar> lista = hibernateTemplate.find(""
                + "select a "
                + "from CntAuxiliar a "
                + "where a.fechaBaja is null "
                + "order by a.numero DESC");
        return lista.isEmpty() ? "1" : Long.toString(lista.get(0).getNumero() + 1);

    }

    public String verificaValoresForm(CntAuxiliar cntAuxiliares) {
        CntAuxiliar auxiliarBase = find(CntAuxiliar.class, cntAuxiliares.getIdAuxiliar());
        if (cntAuxiliares.getSigla().equals(auxiliarBase.getSigla()) && cntAuxiliares.getNombre().equals(auxiliarBase.getNombre())) {
            return "OK";
        } else {
            if (verificaExistenciaDeSiglaModificar(cntAuxiliares.getSigla(), cntAuxiliares).equals("OK")) {
                if (verificaExistenciaDeNombreModificar(cntAuxiliares.getNombre(), cntAuxiliares).equals("OK")) {
                    return "OK";
                } else {
                    return "El nombre " + cntAuxiliares.getNombre() + "esta asignado a otro auxiliar";
                }
            } else {
                return "La sigla" + cntAuxiliares.getSigla() + " esta asignada a otro auxiliar";
            }
        }
    }

    public List<CntAuxiliarPorCuenta> listaCntAuxiliaresPorCuenta(CntEntidad cntEntidad) {
        List<CntAuxiliarPorCuenta> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntAuxiliarPorCuenta j "
                + "where j.fechaBaja is null "
                + "and j.cntEntidad.idEntidad = '" + cntEntidad.getIdEntidad() + "' ");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public CntAuxiliarPorCuenta encuentraAuxiliarPorCuentaPorDetalleYAuxiliar(CntDetalleComprobante cntDetalleComprobante, CntAuxiliar cntAuxiliar) {
        List<CntAuxiliarPorCuenta> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntAuxiliarPorCuenta j "
                + "where j.fechaBaja is null "
                + "and j.cntEntidad.idEntidad = '" + cntDetalleComprobante.getCntEntidad().getIdEntidad() + "' "
                + "and j.cntAuxiliar.idAuxiliar = '" + cntAuxiliar.getIdAuxiliar() + "' ");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public String verificaExistenciaDeSigla(String sigla) {
        if (!sigla.equals("")) {
            List<CntAuxiliar> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntAuxiliar h "
                    + "where h.fechaBaja is null "
                    + "and h.sigla = '" + sigla + "' ");
            if (!lista.isEmpty()) {
                return "La sigla " + sigla + " ya existe inserte otra.";
            } else {
                return "OK";
            }
        } else {
            return "El campo sigla es obligatorio.";
        }
    }

    public String verificaExistenciaDeSiglaModificar(String sigla, CntAuxiliar cntAuxiliar) {
        if (!sigla.equals("")) {
            List<CntAuxiliar> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntAuxiliar h "
                    + "where h.fechaBaja is null "
                    + "and h.sigla = '" + sigla + "' and h.idAuxiliar<>'" + cntAuxiliar.getIdAuxiliar() + "' ");
            if (!lista.isEmpty()) {
                return "La sigla " + sigla + " ya existe inserte otra.";
            } else {
                return "OK";
            }
        } else {
            return "El campo sigla es obligatorio.";
        }
    }

    public String verificaExistenciaDeNombre(String nombre) {
        if (!nombre.equals("")) {
            List<CntAuxiliar> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntAuxiliar h "
                    + "where h.fechaBaja is null "
                    + "and h.nombre = '" + nombre + "' ");
            if (!lista.isEmpty()) {
                return "El nombre " + nombre + " ya existe inserte otra.";
            } else {
                return "OK";
            }
        } else {
            return "El campo nombre es obligatorio.";
        }
    }

    public String verificaExistenciaDeNombreModificar(String nombre, CntAuxiliar cntAuxiliar) {
        if (!nombre.equals("")) {
            List<CntAuxiliar> lista = hibernateTemplate.find(""
                    + "select h "
                    + "from CntAuxiliar h "
                    + "where h.fechaBaja is null "
                    + "and h.nombre = '" + nombre + "' and h.idAuxiliar<>'" + cntAuxiliar.getIdAuxiliar() + "' ");
            if (!lista.isEmpty()) {
                return "El nombre " + nombre + " ya existe en otro auxiliar.";
            } else {
                return "OK";
            }
        } else {
            return "El campo nombre es obligatorio.";
        }
    }

    //verifica si una entidad esta en detalle comprobante para no ser eliminado
    public Boolean verificaAuxiliarDetalleCmmprobante(CntAuxiliar cntAuxiliar) {
        List<Long> listaAuxiliares = hibernateTemplate.find(""
                + "select c "
                + "from CntDetalleComprobante c "
                + "where c.idAuxiliar=" + cntAuxiliar.getIdAuxiliar() + " and c.fechaBaja is null");
        if (!listaAuxiliares.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public CntAuxiliar obtieneAuxiliar(Long idAuxiliar) throws Exception {
        List<CntAuxiliar> lista = hibernateTemplate.find(
                "select a "
                + "from CntAuxiliar "
                + "a where a.idAuxiliar = '" + idAuxiliar + "' "
                + "and a.fechaBaja is null");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }
}
