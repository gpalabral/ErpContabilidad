package com.bap.erp.modelo.servicios.cnf;

import com.bap.erp.modelo.GenericDao;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGeneralesNivel;

public interface ParCuentasGeneralesService extends GenericDao<ParCuentasGeneralesNivel>{
    
    String generaCodigoCuentasGenrales(String mascara,Integer nivel);
    
    public int generaNivelCodigoCuentasGenerales(String codigo);    
}
