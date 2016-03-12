package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnf.ParGruposNivel;
import com.bap.erp.modelo.entidades.cnf.ParTiposDatoNivel;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumTiposDatoNivel;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import com.bap.erp.modelo.servicios.cnt.CntNivelesService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.iknow.utils.ObjectUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntNivelesServiceImpl extends GenericDaoImpl<CntNivel> implements CntNivelesService {

    @Autowired
    private ParParametricasService parParametricasService;
    @Autowired
    private CntMascarasService cntMascarasService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void persistCntNiveles(CntMascara cntMascaras, List<CntNivel> cntNivelesList) throws Exception {
        try {
            //TODO Cambiar las constantes por enum
            ParGruposNivel parGruposNivel = (ParGruposNivel) parParametricasService.find(ParGruposNivel.class, EnumGruposNivel.PLAN_CUENTAS.getCodigo());
            cntMascaras.setGrupoNivel(parGruposNivel);
            cntMascarasService.persist(cntMascaras);
            for (CntNivel cntNiveles : cntNivelesList) {
                cntNiveles.setCntMascara(cntMascaras);                
                super.persist(cntNiveles);
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public CntNivel llenaCntNivelesProyecto() {
        ParTiposDatoNivel parTiposDatoNivel = (ParTiposDatoNivel) parParametricasService.find(ParTiposDatoNivel.class, EnumTiposDatoNivel.CARACTER.getCodigo());
        CntNivel cntNiveles = new CntNivel();
        cntNiveles.setNivel(1);
        cntNiveles.setDescripcion("");
        cntNiveles.setTamanio(1);
        cntNiveles.setTipoNivel(parTiposDatoNivel);
        cntNiveles.setUsuarioAlta("TES");
        cntNiveles.setFechaAlta(new Date());
        return cntNiveles;
    }

    public String verificaDatos(List<CntNivel> cntNivelesList) throws Exception {
        String msg = "";
        try {
            for (CntNivel cntNiveles : cntNivelesList) {
                if (cntNiveles.getDescripcion().isEmpty()) {
                    msg = "Falta descripción";
                    break;
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return msg;
    }

    public List<CntNivel> listaDeNivelesPlanCuentas(String tipocuenta) {
        List<CntNivel> lista = hibernateTemplate.find(""
                + "select c "
                + "from CntNivel c "
                + "where c.tipoMovimiento='"+tipocuenta+"' order by c.nivel DESC");
        
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.emptyList();
    }
    
    //muestra la mascara de el plan de cuentas
    public String muestraMascaraPlanCuentas(String mascaraTipoCuenta){
    List<CntMascara>lista=hibernateTemplate.find(""
            + "select c "
            + "from CntMascara c "
            + "where c.grupoNivel='"+mascaraTipoCuenta+"' and c.fechaBaja is null");
       if(!lista.isEmpty()){
        return lista.get(0).getMascara();
       }
     return "";
    }
    
}
