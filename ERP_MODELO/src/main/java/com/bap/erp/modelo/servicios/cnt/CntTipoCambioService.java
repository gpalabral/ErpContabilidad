package com.bap.erp.modelo.servicios.cnt;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import java.util.Date;
import java.util.List;

public interface CntTipoCambioService extends GenericDao<CntTipoCambio> {

    CntTipoCambio persistCntTipoCambio(CntTipoCambio cntTipoCambio) throws Exception;

    CntTipoCambio mergeCntTipoCambio(CntTipoCambio cntTipoCambio) throws Exception;

    void removeCntTipoCambio(CntTipoCambio cntTipoCambio) throws Exception;

    List<CntTipoCambio> listaCntTipoCambio() throws Exception;

    String verificaValoresForm(CntTipoCambio cntTipoCambio);

    CntTipoCambio ultimoCntTipoCambio();

    CntTipoCambio ultimoRegistroCntTipoCambio();

    CntTipoCambio ultimaFechaRegistroCntTipoCambio();

    Date generaSiguienteDia(Date fecha, int dias);

    void grabarFechas();

    Long ultimoDiaRegistrado();

    Long numeroRegistrosTipoCambio();

    void grabarPrimerFecha();

    CntTipoCambio devuelveCntTipoDeCambio(Date fecha);

    CntTipoCambio devuelveCntTipoDeCambioWSParaCxP(String fecha);
    
    CntTipoCambio ultimoCntTipoCambioRegistrado() throws Exception;

}
