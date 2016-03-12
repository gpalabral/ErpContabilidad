package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliarPorCuenta;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;

import java.util.List;

public interface CntAuxiliaresService extends GenericDao<CntAuxiliar>{

    CntAuxiliar persistCntAuxiliares(CntAuxiliar cntAuxiliares) throws Exception;
    
    CntAuxiliar mergeCntAuxiliares(CntAuxiliar cntAuxiliares) throws Exception;
    
    void removeCntAuxiliares(CntAuxiliar cntAuxiliares) throws Exception;
    
    List<CntAuxiliar> listaCntAuxiliares () throws Exception;
    
    String verificaValoresForm(CntAuxiliar cntAuxiliares);
    
    String ultimo_numero_auxiliar();
    
    List<CntAuxiliarPorCuenta> listaCntAuxiliaresPorCuenta(CntEntidad cntEntidad);
    
    CntAuxiliarPorCuenta encuentraAuxiliarPorCuentaPorDetalleYAuxiliar(CntDetalleComprobante cntDetalleComprobante, CntAuxiliar cntAuxiliar);
    
    String verificaExistenciaDeSigla(String sigla);
    
    String verificaExistenciaDeNombre(String nombre);
    
    Boolean verificaAuxiliarDetalleCmmprobante(CntAuxiliar cntAuxiliar);
    
    String verificaExistenciaDeSiglaModificar(String sigla, CntAuxiliar cntAuxiliar);
    
    String verificaExistenciaDeNombreModificar(String nombre, CntAuxiliar cntAuxiliar);
    
    CntAuxiliar obtieneAuxiliar(Long idAuxiliar) throws Exception;
  
  
}