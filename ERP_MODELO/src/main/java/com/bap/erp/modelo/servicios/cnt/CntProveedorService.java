package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntProveedor;
import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import java.util.List;

public interface CntProveedorService extends GenericDao<CntProveedor> {
    
   CntProveedor persistCntProveedor(CntProveedor cntProveedor) throws Exception; 
    
    CntProveedor mergeCntProveedor(CntProveedor cntProveedor) throws Exception;

    List<CntProveedor> listaCntProveedor() throws Exception;

    CntProveedor generaProveedorPorNit(Long numeroNit);
    
    CntProveedor cambiaAutorizacionSiEsNueva(CntProveedor cntProveedor, String autorizacion);
    
    CntProveedor generaProveedorPorRazonSocial(String razonSocial);
        
}