package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.CntGestion;
import com.bap.erp.modelo.servicios.cnt.CntGestionesService;
import java.util.Collections;
import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntGestionesServiceImpl extends GenericDaoImpl<CntGestion> implements CntGestionesService {

    public List<CntGestion> listaCrmGestiones() {
        try {
            List<CntGestion> lista = hibernateTemplate.find(""
                    + "select c "
                    + "from CntGestion c "
                    + "where c.fechaBaja is null");
            return lista;
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntGestion adicionarCntGestiones(CntGestion cntGestiones) throws Exception {
        try {
            persist(cntGestiones);
        } catch (Exception e) {      
            throw new RuntimeException("adicionarCntGestionesException");
        }
        return cntGestiones;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntGestion modificarCntGestiones(CntGestion cntGestiones) throws Exception {
        try {
            merge(cntGestiones);
        } catch (Exception e) {
      
            throw new RuntimeException("actualizarCntGestionesException");
        }
        return cntGestiones;
    }
    
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void eliminarCrmGestiones(CntGestion cntGestiones) throws Exception {
        try {            
            remove(cntGestiones);
        } catch (Exception e) {      
            throw new RuntimeException("eliminarCntGestionesException");
        }
    }


  
}
