package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico;
import java.util.List;

public interface CntParametroAutomaticoService extends GenericDao<CntParametroAutomatico> {

    CntParametroAutomatico persistCntParametroAutomatico(CntParametroAutomatico cntParametroAutomatico) throws Exception;

    CntParametroAutomatico mergeCntParametroAutomatico(CntParametroAutomatico cntParametroAutomatico) throws Exception;

    void removeCntParametroAutomatico(CntParametroAutomatico cntParametroAutomatico) throws Exception;

    List<CntParametroAutomatico> listaCntParametroAutomatico();

    List<CntParametroAutomatico> listaDeParametrosAutomaticosDeHijos(CntEntidad cntEntidad);

    CntParametroAutomatico obtieneObjetoDeParametroAutomatico(CntEntidad cntEntidad);

    void grabarParametrosHijosMasivo(CntEntidad cntEntidad, String usuarioLogin) throws Exception;

    CntParametroAutomatico obtieneParametrosAutomaticosDeNivel2(CntEntidad cntEntidad) throws Exception;

    void daDeBajaTodosLosParametrosAutomaticos(String usuarioLogin) throws Exception;
    
    void grabarParametrosHijosMasivoCondicionIndividual(CntEntidad cntEntidad, String usuarioLogin, String individuales) throws Exception;
    
    Boolean verificaRelacionCntEntidadConCntParametroAutomatico(CntEntidad cntEntidad) throws Exception;
    
    CntParametroAutomatico obtieneCntParametroAutomaticoPorCuenta(CntEntidad cntEntidad) throws Exception;
}