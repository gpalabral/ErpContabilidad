package com.bap.erp.modelo.entidades.cnt;

import com.bap.erp.modelo.entidades.cnf.ParCuentasGenerales;



/**
 *
 * @author Jonas
 */

public class CntDefinicionCuentasMigracion {
    
    private CntEntidad cntEntidad;
    private String codigoCuentaGeneral;

    public CntDefinicionCuentasMigracion(){        
    }
    
    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    public String getCodigoCuentaGeneral() {
        return codigoCuentaGeneral;
    }

    public void setCodigoCuentaGeneral(String codigoCuentaGeneral) {
        this.codigoCuentaGeneral = codigoCuentaGeneral;
    }
    
}
