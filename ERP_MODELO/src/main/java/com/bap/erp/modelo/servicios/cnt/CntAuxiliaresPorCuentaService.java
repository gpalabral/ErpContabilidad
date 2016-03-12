package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliarPorCuenta;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import java.util.List;

public interface CntAuxiliaresPorCuentaService extends GenericDao<CntAuxiliarPorCuenta> {

    CntAuxiliarPorCuenta findauxiliarPorCuenta(Long id);
    
      public CntAuxiliarPorCuenta findAuxiliarPorCuentaPorAuxiliarYEntidad(Long id, CntEntidad cntEntidad);

    CntAuxiliarPorCuenta persistCntAuxiliaresPorCuenta(CntAuxiliarPorCuenta cntAuxiliarPorCuenta) throws Exception;

    CntAuxiliarPorCuenta mergeCntAuxiliaresPorCuenta(CntAuxiliarPorCuenta cntAuxiliarPorCuenta) throws Exception;

    void persistCntAuxiliaresPorCuentaListaSeleccionada(CntEntidad entidad, List<CntAuxiliar> listAuxiliaresseleccionados) throws Exception;

    void mergeCntAuxiliaresPorCuentaListaSeleccionada(CntEntidad entidad, List<CntAuxiliar> listAuxiliaresSeleccionados) throws Exception;
    
    void bajaListaAuxiliaresPorEntidad(CntEntidad cntEntidad)throws Exception;
    //carga una lista de auxilires de una cuenta de detalle comprobantes
     public List<Long>listaAuxiliarDetalleComprobante(CntEntidad cntEntidad);
     //verifica si un auxiliar esta asignado a una cuenta que esta registrada en detalle comprobante
     Boolean comparaAuxiliarDetalleComprobanteEntidad(Long idAuxiliarAsinadoEntidad, CntEntidad cntEntidad) throws Exception;
     
     Boolean verificaRelacionCntEntidadConCntAuxiliarPorCuenta(CntEntidad cntEntidad) throws Exception;
    
}