package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.servicios.cnt.CntCentrosCostoService;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntCentrosCostoServiceImpl extends GenericDaoImpl<CntEntidad> implements CntCentrosCostoService {

    @Autowired
    private CntMascarasService cntMascarasService;
    public static final String LETRAS_PARA_CENTROS_COSTO = "ABCDEEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public String generaCodigoNivelesSubAndPadre(CntEntidad cntCentrosCosto, String tipo) {
        int nivelObjeto, nivelMascaraAux, numero;
        String cadena, mascaraOficial;
        mascaraOficial = "";
        nivelMascaraAux = generaNivelCodigo(cntCentrosCosto.getMascaraGenerada());
        String[] arrayMascara = cntCentrosCosto.getMascaraGenerada().split("-");
        nivelObjeto = obtieneNiveleCuentaSubAndPadre(cntCentrosCosto, tipo);
        int contador = arrayMascara.length;
        for (int i = 0; i < arrayMascara.length; i++) {
            if (nivelObjeto == nivelMascaraAux) {
                if (esNumero(cntCentrosCosto, contador)) {
                    numero = Integer.parseInt(arrayMascara[i]);
                    numero++;
                    if (controlaGeneracionNumero(arrayMascara[i], numero)) {
                        arrayMascara[i] = generaCodigoConCeros(numero, arrayMascara[i].length());
                    } else {

                    }
                } else {
                    cadena = "";
                    for (int j = 0; j < arrayMascara[i].length(); j++) {
                        cadena = cadena + "&";
                    }
                    arrayMascara[i] = cadena;
                }
            }
            contador--;
            if (i == 0) {
                mascaraOficial = mascaraOficial + arrayMascara[i];
            } else {
                mascaraOficial = mascaraOficial + "-" + arrayMascara[i];
            }
            nivelMascaraAux = nivelMascaraAux - 1;
        }
        return mascaraOficial;
    }

    public boolean esNumero(CntEntidad cntCentrosCosto, int nivel) {
        try {
            List<CntNivel> list = hibernateTemplate.find(""
                    + "select h "
                    + "from CntNivel h "
                    + "where h.cntMascaras.idMascara = " + cntCentrosCosto.getCntMascara().getIdMascara() + " and h.tipoMovimiento='" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                    + "and h.nivel = " + nivel + "");
            if (list.get(0).getTipoNivel().getCodigo().equals("N")) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public int generaNivelCodigo(String codigo) {
        String[] arrayMascara = codigo.split("-");
        return arrayMascara.length;
    }

    @Override
    public int obtieneNiveleCuentaSubAndPadre(CntEntidad cntCentrosCosto, String tipo) {
        if (tipo.equals("N")) {
            return cntCentrosCosto.getNivel();
        } else {
            return cntCentrosCosto.getNivel() - 1;
        }
    }

    /**
     * Metodo controla: Controla el crecimiento de un numero Ej. 1-00-00-000 -->
     * 2-00-00-000 ......9-00-00-000 donde el 1 solo llega hasta el nueve
     *
     * @author Henrry Renán Guzmán Saramani
     */
    public boolean controlaGeneracionNumero(String subCodigo, int numero) {
        int valor = 0;
        for (int i = 1; i <= subCodigo.length(); i++) {
            valor = valor * 10 + 9;
        }
        return numero <= valor ? true : false;
    }

    @Override
    public String generaCodigoConCeros(int numero, int tamanio) {
        String codigo;
        int cont = 0;
        codigo = "";
        int numeroAux = numero;
        while (numeroAux > 0) {
            numeroAux = numeroAux / 10;
            cont++;
        }
        tamanio = tamanio - cont;
        for (int i = 1; i <= tamanio; i++) {
            codigo = codigo + "0";
        }
        codigo = codigo + Integer.toString(numero);
        return codigo;

    }

    @Override
    public int controlaLongitudNumero(CntEntidad cntCentrosCosto, String tipo) {
        int nivelObjeto1 = 0, numero1 = 0;
        int nivelMascaraAux;
        cntCentrosCosto.setMascaraGenerada(cntCentrosCosto.getMascaraGenerada() == null ? generaCodigoNiveleInicialCentroDeCosto() : cntCentrosCosto.getMascaraGenerada());
        String[] arrayMascara = cntCentrosCosto.getMascaraGenerada().split("-");
        cntCentrosCosto.setNivel(cntCentrosCosto.getNivel() == 0 ? arrayMascara.length : cntCentrosCosto.getNivel());
        nivelMascaraAux = arrayMascara.length;
        nivelObjeto1 = obtieneNiveleCuentaSubAndPadre(cntCentrosCosto, tipo);
        for (int i = 0; i < arrayMascara.length; i++) {
            if (nivelObjeto1 == nivelMascaraAux) {
                numero1 = arrayMascara[i].length();
            }
            nivelMascaraAux = nivelMascaraAux - 1;
        }
        return numero1;
    }

    @Override
    public String generaNumeroSiguiente(CntEntidad cntCentrosCosto, String tipo) {
        int nivelObjeto = 0;
        String cadena;
        int numero;
        int nivelMascaraAux;
        cntCentrosCosto.setMascaraGenerada(cntCentrosCosto.getMascaraGenerada() == null || cntCentrosCosto.getMascaraGenerada().equals("") ? generaCodigoNiveleInicialCentroDeCosto() : cntCentrosCosto.getMascaraGenerada());
        nivelMascaraAux = generaNivelCodigo(cntCentrosCosto.getMascaraGenerada());
        String[] arrayMascara = cntCentrosCosto.getMascaraGenerada().split("-");
        nivelObjeto = obtieneNiveleCuentaSubAndPadre(cntCentrosCosto, tipo);
        int contador = arrayMascara.length;
        for (int i = 0; i < arrayMascara.length; i++) {
            if (nivelObjeto == nivelMascaraAux) {
                if (esNumero(cntCentrosCosto, contador)) {
                    numero = Integer.parseInt(arrayMascara[i]);
                    numero++;
                } else {
                    cadena = "";
                    for (int j = 0; j < arrayMascara[i].length(); j++) {
//                        METODO
                        cadena = cadena + "&";
                    }
                    return cadena;
                }
                if (controlaGeneracionNumero(arrayMascara[i], numero)) {
                    return generaCodigoConCeros(numero, arrayMascara[i].length());
                } else {
                }
            }
            contador--;
            nivelMascaraAux = nivelMascaraAux - 1;
        }
        return "";
    }

    public String verificaExistenciaCodigo(String centroCosto) {
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.mascaraGenerada ='" + centroCosto + "' "
                + "and j.cntMascara.grupoNivel.codigo = '" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                + "and j.fechaBaja is null");
        return lista.size() > 0 ? "Codigo Existente" : "Codigo Disponible";
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad persistCntCentroCostoNivelAndSubNivel(CntEntidad cntCentrosCosto) throws Exception {
        try {
            CntMascara cntMascaras;
            if (cntCentrosCosto.getCntMascara() != null) {
                cntMascaras = (CntMascara) cntMascarasService.find(CntMascara.class, cntCentrosCosto.getCntMascara().getIdMascara());
            } else {
                cntMascaras = (CntMascara) cntMascarasService.obtieneMascarasPorGrrupoNivel(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
            }
            cntCentrosCosto.setCntMascara(cntMascaras);
            super.persist(cntCentrosCosto);
            return cntCentrosCosto;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad mergeCntCentroCostoNivelAndSubNivel(CntEntidad cntCentrosCosto) throws Exception {
        try {
            super.merge(cntCentrosCosto);
            return cntCentrosCosto;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntEntidad removeCntCentroCostoNivelAndSubNivel(CntEntidad cntCentrosCosto) throws Exception {
        try {
            super.remove(cntCentrosCosto);
            return cntCentrosCosto;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean verificaExistenciaMascara(CntEntidad cntCentrosCosto) {
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select j from CntEntidad j "
                + "where j.mascaraGenerada = '" + cntCentrosCosto.getMascaraGenerada() + "' "
                + "and j.cntMascara.grupoNivel.codigo = '" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                + "and j.fechaBaja is null");
        return lista.size() > 0 ? false : true;
    }

    @Override
    public String generaCodigoNiveleAndSubNivel(CntEntidad cntCentrosCosto, String tipo, String codigo) {
        int nivelObjeto1 = 0;
        String mascaraOficial = "";
        int nivelMascaraAux;
        nivelMascaraAux = generaNivelCodigo(cntCentrosCosto.getDescripcion());
        String[] arrayMascara = cntCentrosCosto.getDescripcion().split("-");
        int contador = arrayMascara.length;
        nivelObjeto1 = obtieneNiveleCuentaSubAndPadre(cntCentrosCosto, tipo);
        for (int i = 0; i < arrayMascara.length; i++) {
            if (nivelObjeto1 == nivelMascaraAux) {
                if (esNumero(cntCentrosCosto, contador)) {
                    if (controlaGeneracionNumero(arrayMascara[i], Integer.parseInt(codigo))) {
                        arrayMascara[i] = generaCodigoConCeros(Integer.parseInt(codigo), arrayMascara[i].length());
                    } else {
                    }
                } else {
                    arrayMascara[i] = generaCodigoConCerosString(codigo, arrayMascara[i].length() - codigo.length());
                }
            }
            if (i == 0) {
                mascaraOficial = mascaraOficial + arrayMascara[i];
            } else {
                mascaraOficial = mascaraOficial + "-" + arrayMascara[i];
            }
            contador--;
            nivelMascaraAux = nivelMascaraAux - 1;
        }
        return mascaraOficial;
    }

    public String generaCodigoNivelAndSubnivelfromCharacter(CntEntidad cntCentrosCosto, String tipo, String codigo) {
        String mascaraOficial = "";
        return mascaraOficial;
    }

    @Override
    public boolean verificaExistenciaMascaraNivelAndSubNivel(String centroCosto) {
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select j "
                + "from CntEntidad j "
                + "where j.mascaraGenerada = '" + centroCosto + "' "
                + "and j.cntMascara.grupoNivel.codigo = '" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                + "and j.fechaBaja is null");
        return lista.size() > 0 ? false : true;
    }

    @Override
    public boolean isCharacterCntCentroCosto(CntEntidad cntCentrosCosto, String tipo) {
        int nivelObjeto = 0;
        int nivelMascaraAux;
        nivelMascaraAux = generaNivelCodigo(cntCentrosCosto.getDescripcion());
        nivelObjeto = obtieneNiveleCuentaSubAndPadre(cntCentrosCosto, tipo);
        String[] tipoNivel = allDefinitionCharacterOrNumberCentroCosto(cntCentrosCosto);
        int posicion = 1;
        for (int i = nivelMascaraAux; i >= 1; i--) {
            if (i == nivelObjeto) {
                if (tipoNivel[posicion].equals("C")) {
                    return true;
                } else {
                    return false;
                }
            }
            posicion = posicion + 2;
        }
        return false;
    }

    @Override
    public String[] allDefinitionCharacterOrNumberCentroCosto(CntEntidad cntCentrosCosto) {
        String[] tipoNivel = new String[10];
        List<CntNivel> list = hibernateTemplate.find(""
                + "select h "
                + "from CntNivel h "
                + "where h.cntMascaras.idMascara = " + cntCentrosCosto.getCntMascara().getIdMascara() + " and h.tipoMovimiento='" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                + "order by h.nivel asc");
        int i = 1;
        for (CntNivel cntNivel : list) {
            tipoNivel[i] = cntNivel.getTipoNivel().getCodigo();
            i++;
            tipoNivel[i] = Integer.toString(cntNivel.getTamanio());
            i++;
        }
        return tipoNivel;
    }

    @Override
    public boolean activaTipoMovimientoCombo(CntEntidad cntCentrosCosto) {
        int nivelObjetoCuenta;
        nivelObjetoCuenta = 0;
        nivelObjetoCuenta = cntCentrosCosto.getNivel();
        if (nivelObjetoCuenta == 1) {
            return true;
        }
        return false;
    }

    @Override
    public String generaCodigoConCerosString(String codigo, int tamanio) {
        for (int i = 0; i < tamanio; i++) {
            codigo = codigo + "0";
        }
        return codigo;
    }

    public String generaCodigoNiveleInicial() {
        CntMascara cntMascara;
        cntMascara = (CntMascara) cntMascarasService.obtieneMascarasPorGrrupoNivel(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
        String[] arrayMascara = cntMascara.getMascara().split("-");
        String mascara = "";
        for (int i = 0; i < arrayMascara.length; i++) {
            if (i != 0) {
                mascara = mascara + "-" + convierteNueveCero(arrayMascara[i]);
            }
        }
        return mascara;
    }

    public String convierteNueveCero(String valor) {
        String valorConvertido = "";
        for (int i = 0; i < valor.length(); i++) {
            if (valor.charAt(i) == '9') {
                valorConvertido = valorConvertido + "0";
            }
        }
        if (!valorConvertido.equals("")) {
            return valorConvertido;
        } else {
            return valor;
        }
    }

    public String generaCodigoNiveleInicialCentroDeCosto() {
        CntMascara cntMascara;
        cntMascara = (CntMascara) cntMascarasService.obtieneMascarasPorGrrupoNivel(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
        String[] arrayMascara = cntMascara.getMascara().split("-");
        String mascara = "";
        for (int i = 0; i < arrayMascara.length; i++) {
            if (i != 0) {
                mascara = mascara + "-" + convierteNueveCero(arrayMascara[i]);
            } else {
                mascara = convierteNueveCero(arrayMascara[i]);
            }
        }
        return mascara;
    }

    public String[] obtieneMascaraSeparada(CntEntidad mascara, String tipoNivel) {
        String mascarasDivididasParaVista[] = new String[2];
        String mascaraUno = "";
        String mascaraDos = "";
        Boolean swUno;
        Boolean swDos;
        int posicionCambia;
        mascara.setMascaraGenerada(mascara.getMascaraGenerada() == null || mascara.getMascaraGenerada().equals("") ? generaCodigoNiveleInicialCentroDeCosto() : mascara.getMascaraGenerada());
        String[] arrayMascara = mascara.getMascaraGenerada().split("-");
        mascara.setNivel(mascara.getNivel() == 0 ? arrayMascara.length : mascara.getNivel());
        posicionCambia = tipoNivel.equals("N") ? (arrayMascara.length - mascara.getNivel()) : arrayMascara.length - (mascara.getNivel() - 1);
        if (posicionCambia > 0 && posicionCambia < arrayMascara.length) {
            swUno = true;
            swDos = false;
            for (int i = 0; i < arrayMascara.length; i++) {
                if (posicionCambia != i && swUno) {
                    mascaraUno = mascaraUno + arrayMascara[i] + "-";
                }
                if (posicionCambia != i && swDos) {
                    mascaraDos = mascaraDos + "-" + arrayMascara[i];
                }
                if (posicionCambia == i) {
                    swUno = false;
                    swDos = true;
                }
            }
            mascarasDivididasParaVista[0] = mascaraUno;
            mascarasDivididasParaVista[1] = mascaraDos;

        } else {
            if (posicionCambia == 0) {
                for (int i = 0; i < arrayMascara.length; i++) {
                    if (posicionCambia != i) {
                        mascaraDos = mascaraDos + "-" + arrayMascara[i];
                    }
                }
                mascarasDivididasParaVista[0] = "";
                mascarasDivididasParaVista[1] = mascaraDos;
            } else {
                if (posicionCambia == arrayMascara.length) {
                    for (int i = 0; i < arrayMascara.length; i++) {
                        if (posicionCambia != i) {
                            mascaraUno = mascaraUno + arrayMascara[i] + "-";
                        }
                    }
                    mascarasDivididasParaVista[0] = mascaraUno;
                    mascarasDivididasParaVista[1] = "";
                }
            }
        }
        return mascarasDivididasParaVista;
    }

    public String generaNumeroSiguienteAutomatico(CntEntidad mascara, String nivel) {
        List<CntEntidad> lista = new ArrayList<CntEntidad>();
        if (nivel.equals("N")) {
            lista = hibernateTemplate.find(""
                    + "select a "
                    + "from CntEntidad a where a.idEntidadPadre=" + mascara.getIdEntidadPadre() + " "
                    + "order by a.idEntidad DESC");
        } else {
            if (nivel.equals("S")) {
                lista = hibernateTemplate.find(""
                        + "select a "
                        + "from CntEntidad a where a.idEntidadPadre=" + mascara.getIdEntidad() + " "
                        + "order by a.idEntidad DESC");
            }
        }
        return !lista.isEmpty() ? generaNumeroSiguiente(lista.get(0), "N") : nivel.equals("N") ? generaNumeroSiguiente(mascara, "N") : generaNumeroSiguiente(mascara, "S");
    }

    public CntEntidad verificaSiEsPadre(CntEntidad centroDeCosto, String nivel, Long idPadre) {
        CntMascara cntMascara;
        cntMascara = (CntMascara) cntMascarasService.obtieneMascarasPorGrrupoNivel(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
        if (nivel.equals("N")) {
            if (centroDeCosto.getNivel() == cntMascara.getTamanioNivel()) {
                centroDeCosto.setIdEntidadPadre(0L);
            } else {
                centroDeCosto.setIdEntidadPadre(find(CntEntidad.class, idPadre).getIdEntidadPadre());
            }
        } else {
            centroDeCosto.setIdEntidadPadre(idPadre);
        }
        return centroDeCosto;
    }

    public Boolean verificaExistenciaCentrosDeCosto() {
        List<CntEntidad> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntEntidad h "
                + "where h.cntMascara.grupoNivel.codigo = '" + EnumGruposNivel.CENTROS_COSTOS.getCodigo() + "' "
                + "and h.fechaBaja is null");
        return lista.size() > 0 ? true : false;
    }

    public String[] construyeMascaraInicialNivelYSubNivel(String mascara, String nivel) {
        String mascaraGenerada = "";
        String mascarasDivididasParaVista[] = new String[3];
        String[] arrayMascara = mascara.split("-");
        for (int i = 0; i < arrayMascara.length; i++) {
            if (nivel.equals("N") && i == 0) {
                if (arrayMascara[i].matches("[0-9.]*")) {
                    mascarasDivididasParaVista[i] = adicionaCeros("1", arrayMascara[i]);
                } else {
                    mascarasDivididasParaVista[i] = adicionaA(arrayMascara[i]);
                }
                if (nivel.equals("N")) {
                    mascaraGenerada = mascaraGenerada + "-";
                }
            } else {

                mascaraGenerada = mascaraGenerada + arrayMascara[i];
                if (i < arrayMascara.length - 1) {
                    mascaraGenerada = mascaraGenerada + "-";
                }
            }
        }
        mascarasDivididasParaVista[2] = mascaraGenerada;
        return mascarasDivididasParaVista;
    }

    public String adicionaCeros(String valor, String longitud) {
        int tamano = longitud.length() - valor.length();
        String valorCeros = "";
        for (int i = 1; i <= tamano; i++) {
            valorCeros = valorCeros + "0";
        }
        return valorCeros + valor;
    }

    public String adicionaA(String valor) {
        String valorA = "";
        for (int i = 1; i <= valor.length(); i++) {
            valorA = valorA + "A";
        }
        return valorA;
    }

    public String[] obtieneMascaraSeparadaInicial(String mascara, String tipoNivel) {
        String mascarasDivididasParaVista[] = new String[2];
        String[] arrayMascara = mascara.split("-");
        for (int i = 0; i < arrayMascara.length; i++) {
            if (tipoNivel.equals("N")) {
            }
        }
        return mascarasDivididasParaVista;
    }

    public String convierteMascaraConCeros(String mascaraOriginal) {
        String mascaraConvertida = "";
        String[] arrayMascara = mascaraOriginal.split("-");
        for (int i = 0; i < arrayMascara.length; i++) {
            if (arrayMascara[i].matches("[0-9.]*")) {
                mascaraConvertida = mascaraConvertida + adicionaCerosNuevoNumero(arrayMascara[i]);
            } else {
                mascaraConvertida = mascaraConvertida + arrayMascara[i];
            }
            if (i < arrayMascara.length - 1) {
                mascaraConvertida = mascaraConvertida + "-";
            }
        }
        return mascaraConvertida;
    }

    public String adicionaCerosNuevoNumero(String valor) {
        String valorCeros = "";
        for (int i = 1; i <= valor.length(); i++) {
            valorCeros = valorCeros + "0";
        }
        return valorCeros;
    }

    public Boolean verificaMascara(String mascaraCC) {
        Boolean valor = true;
        String[] arrayMascara = mascaraCC.split("-");
        for (String cadena : arrayMascara) {
            for (int h = 0; h < cadena.length(); h++) {
                if (cadena.charAt(h) == '&') {                    
                    valor = false;
                }
            }
        }
        return valor;
    }
    
    public Boolean verificaMascaraVacio(String mascaraCC) {
        Boolean valor = true;
        String[] arrayMascara = mascaraCC.split("-");
        for (String cadena : arrayMascara) {
                if (cadena.isEmpty()) {
                    valor = false;
            }
        }
        return valor;
        
    }
}
