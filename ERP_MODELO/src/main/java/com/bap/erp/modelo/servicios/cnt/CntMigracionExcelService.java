package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.*;
import java.util.List;

public interface CntMigracionExcelService extends GenericDao<CntEntidad> {

    List<CntNivel> generaNivelConElRegistroDelExcel(String nivel)throws Exception;
    
    void guardaListaPlanCuentasConParametrosAutomaticos(CntMascara cntMascaras, List<CntEntidad> cntPlanCuentasList) throws Exception;

    void guardarCntMascarCntNivelesCntPlancuentasCntParametroAutomatico(CntMascara cntMascaras, List<CntNivel> cntNivelesList, List<CntEntidad> listaCntEntidad,List<CntDefinicionCuentasMigracion> listaSuperioresPadres) throws Exception;  

    void asignaPadresAPlanDeCuentas()throws Exception;
    
}
