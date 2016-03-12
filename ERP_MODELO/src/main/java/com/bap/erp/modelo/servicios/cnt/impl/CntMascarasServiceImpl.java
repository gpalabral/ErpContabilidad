package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnf.ParTiposDatoNivel;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumTiposDatoNivel;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import com.bap.erp.modelo.servicios.cnt.CntNivelesService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CntMascarasServiceImpl extends GenericDaoImpl<CntMascara> implements CntMascarasService {

    private final static Log log = LogFactory.getLog(CntMascarasServiceImpl.class);
    @Autowired
    private CntNivelesService cntNivelesService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntMascara adicionarCntMascara(CntMascara cntMascaras, List<CntNivel> cntNivelesList) throws Exception {
        try {
            persist(cntMascaras);
            int nivel = cntNivelesList.size();
            for (CntNivel cntNivel : cntNivelesList) {
                cntNivel.setNivel(nivel);
                nivel--;
                cntNivel.setCntMascara(cntMascaras);
                cntNivel.setTipoMovimiento(cntMascaras.getGrupoNivel().getCodigo().equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo()) ? EnumGruposNivel.PLAN_CUENTAS.getCodigo() : EnumGruposNivel.CENTROS_COSTOS.getCodigo());
                cntNivelesService.persist(cntNivel);
            }
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
        return cntMascaras;
    }

    public CntMascara obtieneMascarasPorGrrupoNivel(String grupoNivel) {
        List<CntMascara> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntMascara j "
                + "where j.grupoNivel.codigo = '" + grupoNivel + "' "
                + "and j.fechaBaja is null");
        return !lista.isEmpty() ? lista.get(0) : null;
    }

    public String generaMascaraAutomatico(List<CntNivel> auxCntNivelList) {
        int mascara = 0;
        int mascaraAux = 9;
        int contador = 1;
        String generacionMascara = "";
        for (CntNivel cntNivelAux : auxCntNivelList) {
            for (int i = 1; i <= cntNivelAux.getTamanio(); i++) {
                mascara = mascara * 10 + mascaraAux;
            }
            if (contador == auxCntNivelList.size()) {
                generacionMascara = generacionMascara + Integer.toString(mascara);
                mascara = 0;
            } else {
                generacionMascara = generacionMascara + Integer.toString(mascara) + "-";
                mascara = 0;

            }
            contador++;
        }
        return generacionMascara;
    }

    public String generaMascaraAutomaticoCentros(List<CntNivel> auxCntNivelList) {
        String straux = "";
        String mascaraC;
        for (CntNivel cntNivelAux : auxCntNivelList) {
            for (int i = 1; i <= cntNivelAux.getTamanio(); i++) {
                if (cntNivelAux.getTipoNivel().getCodigo().equals(EnumTiposDatoNivel.CARACTER.getCodigo())) {
                    straux = straux + "&";
                }
                if (cntNivelAux.getTipoNivel().getCodigo().equals(EnumTiposDatoNivel.NUMERO.getCodigo())) {
                    straux = straux + "9";
                }
            }
            straux = straux + "-";
        }
        int len = straux.length();
        mascaraC = straux.substring(0, len - 1);
        return mascaraC;
    }

    public String validaCamposSinLlenarDelListado(List<CntNivel> cntNivelesList) {
        Boolean verifica = true;
        for (CntNivel cntNivel : cntNivelesList) {
            if (cntNivel.getDescripcion() == null || cntNivel.getDescripcion().isEmpty()) {
                verifica = false;
            }
        }
        return !verifica ? "Uno de los campos Descripcion no se lleno, verifique por favor." : "OK";
    }

    public Boolean verificaExistenciaDeMascara(String grupoNivel) {
        List<CntMascara> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntMascara h "
                + "where h.grupoNivel.codigo = '" + grupoNivel + "' "
                + "and h.fechaBaja is null");
        return lista.isEmpty();
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntMascara persistCntMascara(CntMascara cntMascara) throws Exception {
        persist(cntMascara);
        return cntMascara;
    }

    //Obtiene valor del tamanio de la mascara...
    public List<CntMascara> obtieneTananioMascara(String grupoNivel) throws Exception {
        try {
           List<CntMascara> lista = hibernateTemplate.find(""
                + "select a "
                + "from CntMascara a "
                + "where a.grupoNivel.codigo = '" + grupoNivel + "' "
                + "and a.fechaBaja is null");
            if (!lista.isEmpty()) {
                return lista;
            }
            return Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }
}
