package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntGestion;
import java.util.List;

public interface CntGestionesService extends GenericDao<CntGestion>{
    
    List<CntGestion> listaCrmGestiones();
    
    CntGestion adicionarCntGestiones(CntGestion cntGestiones) throws Exception;
    
    CntGestion modificarCntGestiones(CntGestion cntGestiones) throws Exception;
    
    void eliminarCrmGestiones(CntGestion cntGestiones) throws Exception;
    
    //chano
    
//    CrmGestiones persistCrmGestiones(CrmGestiones crmGestiones) throws Exception;
//    
//    CrmGestiones mergeCrmGestiones(CrmGestiones crmGestiones) throws Exception;
//
//    void removeCrmGestiones(CrmGestiones crmGestiones) throws Exception;
    
    
    //fin chano    
}
