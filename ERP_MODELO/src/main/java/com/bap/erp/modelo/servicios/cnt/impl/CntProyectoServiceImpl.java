package com.bap.erp.modelo.servicios.cnt.impl;

import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import com.bap.erp.modelo.servicios.cnt.CntProyectoService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CntProyectoServiceImpl extends GenericDaoImpl<CntProyecto> implements CntProyectoService {

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntProyecto persistCntProyecto(CntProyecto cntProyecto) throws Exception {
        try {
            super.persist(cntProyecto);
        } catch (Exception e) {
            throw e;
        }
        return cntProyecto;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public CntProyecto mergeCntProyecto(CntProyecto cntProyecto) throws Exception {
        try {
            super.merge(cntProyecto);
        } catch (Exception e) {
            throw e;
        }
        return cntProyecto;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeCntProyectos(CntProyecto cntProyecto) throws Exception {
        try {
            CntProyecto cntProyectoBD = super.find(CntProyecto.class, cntProyecto.getIdProyecto());
            super.remove(cntProyecto);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CntProyecto> listaCntProyectosOrdenados() throws Exception {
        try {
            List<CntProyecto> lista = hibernateTemplate.find(""
                    + "select o "
                    + "from CntProyecto o "
                    + "where o.fechaBaja is null "
                    + "order by o.mascara ASC");
            return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }
    }

    public CntProyecto findCntProyectos(Long idProyecto) {
        List<CntProyecto> lista = hibernateTemplate.find(""
                + "select h "
                + "from CntProyecto h "
                + "where h.idProyecto=" + idProyecto);
        return !lista.isEmpty() ? lista.get(0) : null;
    }

    public int generaNivelCodigoCuentasGenerales(String codigo) {
        String[] arrayMascara = codigo.split("-");
        int contador;
        contador = 0;
        for (int i = 0; i < arrayMascara.length; i++) {
            contador++;
        }
        return contador;
    }

    public String generaEspaciosEnDescripcionNivelesSubAndPadre(CntProyecto cntProyecto) {
        String descripcionNueva = "";
        int limite = generaNivelCodigoCuentasGenerales(cntProyecto.getMascara()) - cntProyecto.getNivel();
        for (int i = 1; i <= limite; i++) {
            descripcionNueva = descripcionNueva + "....";
        }
        return descripcionNueva + cntProyecto.getNombre();
    }

    public String generaNumeroSiguienteAutomatico(CntProyecto cntProyecto, String nivel) throws Exception {
        List<CntProyecto> lista = new ArrayList<CntProyecto>();
        if (cntProyecto != null) {
            try {
                if (nivel.equals("N")) {
                    lista = hibernateTemplate.find(""
                            + "select h "
                            + "from CntProyecto h where h.idPadre=" + cntProyecto.getIdPadre() + " "
                            + "order by h.mascara DESC");
                } else {
                    if (nivel.equals("S")) {
                        lista = hibernateTemplate.find(""
                                + "select r "
                                + "from CntProyecto r where r.idPadre=" + cntProyecto.getIdProyecto() + " "
                                + "order by r.mascara DESC");
                    }
                }
                return !lista.isEmpty() ? generaNumeroSiguiente(lista.get(0), "N") : nivel.equals("N") ? generaNumeroSiguiente(cntProyecto, "N") : generaNumeroSiguiente(cntProyecto, "S");

            } catch (Exception e) {
                throw e;
            }
        } else {
            return "01";
        }

    }

    public String generaNumeroSiguiente(CntProyecto cntProyecto, String tipo) {
        int nivelObjetoCuenta = 0;
        int nivelMascaraAux;
        int numero;
        nivelMascaraAux = generaNivelCodigoCuentasGenerales(cntProyecto.getMascara());
        String[] arrayMascara = cntProyecto.getMascara().split("-");
        if (tipo.equals("N")) {
            nivelObjetoCuenta = cntProyecto.getNivel();
        } else {
            nivelObjetoCuenta = cntProyecto.getNivel() - 1;
        }
        for (int i = 0; i < arrayMascara.length; i++) {
            if (nivelObjetoCuenta == nivelMascaraAux) {
                numero = Integer.parseInt(arrayMascara[i]);
                numero++;
                if (controlaGeneracionNumero(arrayMascara[i], numero)) {
                    return generaCodigoConCeros(numero, arrayMascara[i].length());
                } else {
                    return "El numero es mayor al Tamaño no insista::";
                }
            }
            nivelMascaraAux = nivelMascaraAux - 1;
        }
        return "";

    }

    public boolean controlaGeneracionNumero(String subCodigo, int numero) {
        int valor = 0;
        for (int i = 1; i <= subCodigo.length(); i++) {
            valor = valor * 10 + 9;
        }
        return numero <= valor ? true : false;
    }

    public String generaCodigoConCeros(int numero, int tamanio) {
        int cont = 0;
        String codigo;
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

    public String[] obtieneMascaraSeparada(CntProyecto cntProyecto, String tipoNivel) {
        String mascarasDivididasParaVista[] = new String[2];
        String mascaraUno = "";
        String mascaraDos = "";
        Boolean swUno;
        Boolean swDos;
        int posicionCambia;
        String[] arrayMascara = cntProyecto.getMascara().split("-");
        posicionCambia = tipoNivel.equals("N") ? (arrayMascara.length - cntProyecto.getNivel()) : arrayMascara.length - (cntProyecto.getNivel() - 1);
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

    public int controlaLongitudNumero(CntProyecto cntProyecto, String tipo) {
        int nivelObjetoCuenta1 = 0, numero1 = 0;
        int nivelMascaraAux;
        nivelMascaraAux = generaNivelCodigoCuentasGenerales(cntProyecto.getMascara());
        String[] arrayMascara = cntProyecto.getMascara().split("-");
        if (tipo.equals("N")) {
            nivelObjetoCuenta1 = cntProyecto.getNivel();
        } else {
            nivelObjetoCuenta1 = cntProyecto.getNivel() - 1;
        }
        for (int i = 0; i < arrayMascara.length; i++) {
            if (nivelObjetoCuenta1 == nivelMascaraAux) {
                numero1 = arrayMascara[i].length();

            }
            nivelMascaraAux = nivelMascaraAux - 1;
        }
        return numero1;
    }

    public String datosRepetidos(CntProyecto cntProyecto) {
        List<CntProyecto> listaNombre = hibernateTemplate.find(""
                + "select j "
                + "from CntProyecto j "
                + "where j.fechaBaja is null "
                + "and j.nombre = '" + cntProyecto.getNombre() + "' "
                + "and j.nivel = " + cntProyecto.getNivel() + " ");
        if (!listaNombre.isEmpty()) {
            return "El nombre " + cntProyecto.getNombre() + " ya se encuentra registrado en nuestra Base de Datos";
        }
        List<CntProyecto> listaNivel = hibernateTemplate.find(""
                + "select j "
                + "from CntProyecto j "
                + "where j.fechaBaja is null "
                + "and j.mascara = '" + cntProyecto.getMascara() + "' "
                + "and j.nivel = " + cntProyecto.getNivel() + " ");
        if (!listaNivel.isEmpty()) {
            return "La Mascara " + cntProyecto.getMascara() + " ya se encuentra registrado en nuestra Base de Datos";
        }
        return "";
    }

    public Boolean verificaExistenciaDeProyectos(List<CntProyecto> listaProyectos, CntProyecto proyectoSeleccionado) {

        if (proyectoSeleccionado != null) {
            if (proyectoSeleccionado.getIdProyecto() != null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean verificaExistenciaDeProyectosParaCrear() {
        List<CntProyecto> listaProyectos = hibernateTemplate.find(""
                + "select h "
                + "from CntProyecto h "
                + "where h.fechaBaja is null");
        if (listaProyectos.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public String datosRepetidosModificacion(CntProyecto cntProyecto) {
        List<CntProyecto> listaNombre = hibernateTemplate.find(""
                + "select j "
                + "from CntProyecto j "
                + "where j.fechaBaja is null "
                + "and j.nombre = '" + cntProyecto.getNombre() + "' "
                + "and j.idProyecto <> '" + cntProyecto.getIdProyecto() + "'"
                + "and j.nivel = " + cntProyecto.getNivel() + " ");
        if (!listaNombre.isEmpty()) {
            return "El nombre " + cntProyecto.getNombre() + " ya se encuentra registrado en nuestra Base de Datos";
        }
        return "";
    }
    //verifica si una entidad esta en detalle comprobante para no ser eliminado

    public Boolean verificaProyectoDetalleComprobante(CntProyecto cntProyecto) {
        List<Long> listaProyectos = hibernateTemplate.find(""
                + "select c "
                + "from CntDetalleComprobante c "
                + "where c.idProyecto=" + cntProyecto.getIdProyecto() + " and c.fechaBaja is null");
        if (!listaProyectos.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    //lista hijos de 

    public List<CntProyecto> listaHijosProyecto(CntProyecto cntProyecto) throws Exception {
        try {
            List<CntProyecto> lista = hibernateTemplate.find(""
                    + "select c "
                    + "from CntProyecto c "
                    + "where c.idPadre=" + cntProyecto.getIdProyecto() + ""
                    + "and c.fechaBaja is null");
            return !lista.isEmpty() ? lista : Collections.EMPTY_LIST;
        } catch (Exception e) {
            throw e;
        }

    }

//    borrar padre e hijos de proyectos
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void removeProyectosMasHijos(CntProyecto cntProyecto) throws Exception {
        try {
            for (CntProyecto cntProyectohijos : listaHijosProyecto(cntProyecto)) {
                cntProyectohijos.setUsuarioBaja(cntProyecto.getUsuarioBaja());
                cntProyectohijos.setFechaBaja(cntProyecto.getFechaBaja());
                removeCntProyectos(cntProyectohijos);
            }
            cntProyecto.setUsuarioBaja(cntProyecto.getUsuarioBaja());
            cntProyecto.setFechaBaja(cntProyecto.getFechaBaja());
            removeCntProyectos(cntProyecto);
        } catch (Exception h) {
            throw h;
        }
    }

    //verifica si en la base existe un proyecto si esta vacia devuelve true
    public Boolean existeProyecto() {
        List<CntProyecto> listaProyectos = hibernateTemplate.find(""
                + "select c "
                + "from CntProyecto c "
                + "where c.idProyecto is not null and c.fechaBaja is null");
        if (!listaProyectos.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public CntProyecto obtieneCntProyecto(Long idProyecto) throws Exception {
        List<CntProyecto> lista = hibernateTemplate.find(
                "select p "
                + "from CntProyecto p "
                + "where p.idAuxiliar = '" + idProyecto + "' "
                + "and p.fechaBaja is null");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

}
