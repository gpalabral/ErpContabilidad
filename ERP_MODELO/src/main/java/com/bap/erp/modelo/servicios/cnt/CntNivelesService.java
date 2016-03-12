package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import java.util.List;

public interface CntNivelesService extends GenericDao<CntNivel> {

    CntNivel llenaCntNivelesProyecto();

    String verificaDatos(List<CntNivel> cntNivelesList)throws Exception;
    
    List<CntNivel> listaDeNivelesPlanCuentas(String tipocuenta);
    
    String muestraMascaraPlanCuentas(String mascaraTipoCuenta);
    
}
