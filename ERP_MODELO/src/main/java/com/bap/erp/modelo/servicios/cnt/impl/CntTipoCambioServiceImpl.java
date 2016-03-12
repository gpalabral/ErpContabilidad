package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import com.bap.erp.util.Utilitario;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntTipoCambioServiceImpl extends GenericDaoImpl<CntTipoCambio> implements CntTipoCambioService {

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntTipoCambio persistCntTipoCambio(CntTipoCambio cntTipoCambio) throws Exception {
        try {
            super.persist(cntTipoCambio);
        } catch (Exception e) {
            throw e;
        }
        return cntTipoCambio;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntTipoCambio mergeCntTipoCambio(CntTipoCambio cntTipoCambio) throws Exception {
        try {
            super.merge(cntTipoCambio);
        } catch (Exception e) {
            throw e;
        }
        return cntTipoCambio;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeCntTipoCambio(CntTipoCambio cntTipoCambio) throws Exception {
        try {
            CntTipoCambio cntTipoCambioBD = super.find(CntTipoCambio.class, cntTipoCambio.getIdTipoCambio());
            super.remove(cntTipoCambio);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntTipoCambio> listaCntTipoCambio() {
        try {
            List<CntTipoCambio> lista = hibernateTemplate.find(""
                    + "select a "
                    + "from CntTipoCambio a "
                    + "where a.fechaBaja is null order by a.idTipoCambio DESC");
            return lista;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public String verificaValoresForm(CntTipoCambio cntTipoCambio) {

//  return cntTipoCambio.getTipoCambio() != null ? "OK" : "el tipo de cambio es obligatorio";
        return "OK";
    }

//aumentado sacar la ultima registro con tipo de cambio y tipo ufv
    public CntTipoCambio ultimoCntTipoCambio() {
        List<CntTipoCambio> list;
        try {
            list = hibernateTemplate.find(
                    "select j "
                    + "from CntTipoCambio j "
                    + "where j.fechaBaja is null "
                    + "and j.tipoCambio <> 0 "
                    + "and j.tipoUfv <> 0 "
                    + "order by j.fecha desc");
            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
        }
        return null;
    }
    //sacar el ultimo registro de la tabla tipo de cambio   

    public CntTipoCambio ultimoRegistroCntTipoCambio() {
        List<CntTipoCambio> list;
        try {
            list = hibernateTemplate.find(
                    "select j "
                    + "from CntTipoCambio j "
                    + "where j.fechaBaja is null "
                    + "order by j.fecha desc");
            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
        }
        return null;
    }

    //sacara el ultima fecha sin tipo de cambio ni tipo ufv
    public CntTipoCambio ultimaFechaRegistroCntTipoCambio() {
        List<CntTipoCambio> list;

        try {
            list = hibernateTemplate.find(
                    "select j "
                    + "from CntTipoCambio j "
                    + "where j.fechaBaja is null "
                    + "and j.tipoCambio = 0 "
                    + "and j.tipoUfv = 0 "
                    + "order by j.fecha ASC");
            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
        }
        return null;
    }

    //genera la siguiente fecha dada una fecha
    public Date generaSiguienteDia(Date fecha, int dias) {
        Calendar calCalendario = Calendar.getInstance();
        calCalendario.setTimeInMillis(fecha.getTime());
        calCalendario.add(Calendar.DATE, dias);
        return new Timestamp(calCalendario.getTimeInMillis());
    }

    //genera el insert de fechas que faltan---------------
    public void grabarFechas() {
        List<CntTipoCambio> lista = new ArrayList<CntTipoCambio>();
        Date fechahoy = new Date();
        if (ultimoRegistroCntTipoCambio() != null) {
            Date fechasinvalores = ultimoRegistroCntTipoCambio().getFecha();
            if (fechasinvalores != null) {
                int cantidaddiferencia = Utilitario.differenceBetweenDates(fechahoy, fechasinvalores);
                if (cantidaddiferencia != 0) {
                    for (int i = 0; i < cantidaddiferencia; i++) {
                        fechasinvalores = generaSiguienteDia(fechasinvalores, 1);
                        CntTipoCambio nuevoObjeto = new CntTipoCambio();
                        nuevoObjeto.setFecha(fechasinvalores);
                        nuevoObjeto.setTipoCambio(new BigDecimal(0));
                        nuevoObjeto.setTipoUfv(new BigDecimal(0));
                        nuevoObjeto.setFechaAlta(new Date());
                        nuevoObjeto.setUsuarioAlta("SISTEMA");
                        try {
                            persistCntTipoCambio(nuevoObjeto);
                        } catch (Exception e) {
                        }
                        lista.add(nuevoObjeto);
                    }
                }
            }
        } else {
            CntTipoCambio nuevoObjeto = new CntTipoCambio();
            nuevoObjeto.setFecha(new Date());
            nuevoObjeto.setTipoCambio(new BigDecimal(0));
            nuevoObjeto.setTipoUfv(new BigDecimal(0));
            nuevoObjeto.setFechaAlta(new Date());
            nuevoObjeto.setUsuarioAlta("SISTEMA");
            try {
                persistCntTipoCambio(nuevoObjeto);
            } catch (Exception e) {
            }
        }
    }

//cuenta dias de tipo de registro
    public Long ultimoDiaRegistrado() {
        try {
            Long dias = (Long) hibernateTemplate.find(
                    "select count (*) "
                    + "from CntTipoCambio j "
                    + "where j.fechaBaja is null "
                    + "and (j.tipoCambio = 0 "
                    + "or j.tipoUfv = 0)").get(0);
            return dias;
        } catch (Exception e) {
        }
        return 0L;
    }

    public Long numeroRegistrosTipoCambio() {
        try {
            Long dias = (Long) hibernateTemplate.find(
                    "select count (*) "
                    + "from CntTipoCambio j "
                    + "where j.fechaBaja is null").get(0);
            return dias;
        } catch (Exception e) {
        }
        return 0L;
    }

    public void grabarPrimerFecha() {
        Long registros = numeroRegistrosTipoCambio();
        if (registros == 0) {
            CntTipoCambio nuevoObjeto = new CntTipoCambio();
            nuevoObjeto.setFecha(new Date());
            nuevoObjeto.setTipoCambio(new BigDecimal(0));
            nuevoObjeto.setTipoUfv(new BigDecimal(0));
            nuevoObjeto.setFechaAlta(new Date());
            nuevoObjeto.setUsuarioAlta("SISTEMA");
            try {
                persistCntTipoCambio(nuevoObjeto);
            } catch (Exception e) {
            }
        }
    }

    public CntTipoCambio devuelveCntTipoDeCambio(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechaString = formato.format(fecha);
        String fechaSiguienteDia = formato.format(fecha.getTime() + 86400000L);
        List<CntTipoCambio> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntTipoCambio j "
                + "where j.fechaBaja is null "
                + "and j.fecha >= '" + fechaString + "' "
                + "and j.fecha < '" + fechaSiguienteDia + "' ");
        return !lista.isEmpty() ? lista.get(0) : null;
    }

    public CntTipoCambio devuelveCntTipoDeCambioWSParaCxP(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        Date fechaDate = new Date(fecha.substring(3, 5) + "/" + fecha.substring(0, 2) + "/" + fecha.substring(6, 10));
        String fechaString = formato.format(fechaDate);
        String fechaSiguienteDia = formato.format(fechaDate.getTime() + 86400000L);
        List<CntTipoCambio> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntTipoCambio h "
                + "where h.fechaBaja is null "
                + "and h.fecha >= '" + fechaString + "' "
                + "and h.fecha < '" + fechaSiguienteDia + "' ");
        return !lista.isEmpty() ? lista.get(0) : new CntTipoCambio();
    }

    public CntTipoCambio ultimoCntTipoCambioRegistrado() throws Exception {
        List<CntTipoCambio> list;
        try {
            list = hibernateTemplate.find(
                    "select j "
                    + "from CntTipoCambio j "
                    + "where j.fechaBaja is null "
                    + "and j.fechaModificacion <> null "
                    + "and j.tipoCambio <> 0 "
                    + "order by j.fecha desc");
            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
        }
        return null;
    }
}
