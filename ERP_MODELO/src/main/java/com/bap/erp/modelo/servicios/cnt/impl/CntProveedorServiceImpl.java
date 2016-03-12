package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntProveedor;
import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import com.bap.erp.modelo.servicios.cnt.CntProveedorService;
import java.util.Collections;
import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntProveedorServiceImpl extends GenericDaoImpl<CntProveedor> implements CntProveedorService {

  
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntProveedor persistCntProveedor(CntProveedor cntProveedor) throws Exception {
        try {
            super.persist(cntProveedor);
        } catch (Exception e) {
            throw e;
        }
        return cntProveedor;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntProveedor mergeCntProveedor(CntProveedor cntProveedor) throws Exception {
        try {
            super.merge(cntProveedor);
        } catch (Exception e) {
            throw e;
        }
        return cntProveedor;
    } 
    
    
    public List<CntProveedor> listaCntProveedor() throws Exception {
           List<CntProveedor> list = hibernateTemplate.find(""
                + "select j "
                + "from CntProveedor j "
                + "where j.fechaBaja is null ");
        if(!list.isEmpty()){
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    public CntProveedor generaProveedorPorNit(Long numeroNit) {        
         List<CntProveedor> list = hibernateTemplate.find(""
                + "select j "
                + "from CntProveedor j "
                + "where j.fechaBaja is null "
                 + "and j.nit = '"+numeroNit+"'");
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
    
     public CntProveedor generaProveedorPorRazonSocial(String razonSocial) {        
         List<CntProveedor> list = hibernateTemplate.find(""
                + "select j "
                + "from CntProveedor j "
                + "where j.fechaBaja is null "
                 + "and j.razonSocial = '"+razonSocial+"'");
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
    

    public CntProveedor cambiaAutorizacionSiEsNueva(CntProveedor cntProveedor, String autorizacion) {
        if(!cntProveedor.getAutorizacion().equals(autorizacion)){
            cntProveedor.setAutorizacion(autorizacion);            
        }
        return cntProveedor;
    }   
   
    
}
