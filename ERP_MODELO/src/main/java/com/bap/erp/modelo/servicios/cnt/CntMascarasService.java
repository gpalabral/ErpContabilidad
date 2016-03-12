package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import java.util.List;

public interface CntMascarasService extends GenericDao<CntMascara> {

    CntMascara adicionarCntMascara(CntMascara cntMascaras, List<CntNivel> cntNivelesList) throws Exception;

    CntMascara obtieneMascarasPorGrrupoNivel(String grupoNivel);

    String generaMascaraAutomatico(List<CntNivel> auxCntNivelesList);

    String generaMascaraAutomaticoCentros(List<CntNivel> auxCntNivelesList);

    public String validaCamposSinLlenarDelListado(List<CntNivel> cntNivelesList);

    public Boolean verificaExistenciaDeMascara(String grupoNivel);
    
    CntMascara persistCntMascara(CntMascara cntMascara) throws Exception;
    
    public List<CntMascara> obtieneTananioMascara(String grupoNivel) throws Exception;
    
}
