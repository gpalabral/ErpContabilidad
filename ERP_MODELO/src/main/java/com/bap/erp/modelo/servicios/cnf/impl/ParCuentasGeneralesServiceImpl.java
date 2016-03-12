package com.bap.erp.modelo.servicios.cnf.impl;


import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGenerales;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGeneralesNivel;
import com.bap.erp.modelo.servicios.cnf.ParCuentasGeneralesService;
import java.util.List;


public class ParCuentasGeneralesServiceImpl extends GenericDaoImpl<ParCuentasGeneralesNivel> implements ParCuentasGeneralesService {

    
    @Override
    public String generaCodigoCuentasGenrales(String mascara, Integer nivel) {
        String colores = "9-99-999";
        String nivelAux = Integer.toString(nivel);
        String[] arrayMascara = mascara.split("-");
        String mascaraCuentaGeneral = "";
        for (int i = 0; i < arrayMascara.length; i++) {
            String s = arrayMascara[i].replace('9', '0');
            if (mascaraCuentaGeneral.equals("")) {
                mascaraCuentaGeneral = mascaraCuentaGeneral + Integer.toString(nivel);
            } else {
                mascaraCuentaGeneral = mascaraCuentaGeneral + "-" + s;
            }
        }
        return mascaraCuentaGeneral;
    }

    @Override
    public int generaNivelCodigoCuentasGenerales(String codigo) {
        String[] arrayMascara = codigo.split("-");
        int contador = 0;
        for (int i = 0; i < arrayMascara.length; i++) {
            contador++;
        }
        return contador;
    }  
}
