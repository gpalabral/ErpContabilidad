package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import com.bap.erp.modelo.entidades.cnf.ParEstadoProyecto;
import com.bap.erp.modelo.servicios.cnt.CntProyectoService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.iknow.utils.ObjectUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import org.apache.log4j.Logger;

@ManagedBean(name = "cntProyectosAdicionBacking")
@ViewScoped
public class CntProyectosAdicionBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntProyectosAdicionBacking.class);
    @ManagedProperty(value = "#{cntProyectoService}")
    private CntProyectoService cntProyectoService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    private CntProyecto cntProyecto = new CntProyecto();
    private List<ParEstadoProyecto> listaDeEstadosDeProyecto = new ArrayList<ParEstadoProyecto>();
    private CntProyecto cntProyectoElegido;
    private boolean eligeCntProyecto = false;
    private boolean habilitaEstado;
    private String mascaraNuevoOpcion = "";
    private String mascaraNivelPosicionUno = "";
    private String mascaraNivelPosicionDos = "";
    private String mascaraSubNivelPosicionUno = "";
    private String mascaraSubNivelPosicionDos = "";
    private String nivelIn;
    private String subNivelIn;
    private String mensajeVerifica1;
    private String mensajeVerifica2;
    private int longitudNivel;
    private int longitudSubNivel;
    private Boolean desactivaRadioButtonSubNivel = true;
    private Boolean desactivaProyectoInicio = true;
    private String mascaraNivel;
    private String mascaraSubNivel;

    public CntProyectosAdicionBacking() {
    }

    @PostConstruct
    public void initCntProyectosBacking(){
        try {
            if (getFromSessionIdProyecto() != null) {
                cntProyectoElegido = cntProyectoService.find(CntProyecto.class, getFromSessionIdProyecto());
                desactivaRadioButtonSubNivel = cntProyectoElegido.getNivel() == 1 ? false : true;
                habilitaEstado = cntProyectoElegido.getNivel() == 1 ? false : true;
                mascaraNuevoOpcion = "N";

                mascaraNivelPosicionUno = cntProyectoService.obtieneMascaraSeparada(cntProyectoElegido, "N")[0];
                mascaraNivelPosicionDos = cntProyectoService.obtieneMascaraSeparada(cntProyectoElegido, "N")[1];
                nivelIn = cntProyectoService.generaNumeroSiguienteAutomatico(cntProyectoElegido, "N");
                longitudNivel = cntProyectoService.controlaLongitudNumero(cntProyectoElegido, "N");

                mascaraSubNivelPosicionUno = cntProyectoService.obtieneMascaraSeparada(cntProyectoElegido, "S")[0];
                mascaraSubNivelPosicionDos = cntProyectoService.obtieneMascaraSeparada(cntProyectoElegido, "S")[1];
                subNivelIn = cntProyectoService.generaNumeroSiguienteAutomatico(cntProyectoElegido, "S");
                longitudSubNivel = cntProyectoService.controlaLongitudNumero(cntProyectoElegido, "S");
                eligeCntProyecto = true;
            } else {
                if (cntProyectoService.verificaExistenciaDeProyectosParaCrear()) {
                    cntProyectoElegido = new CntProyecto();
                    nivelIn = "01";
                    mascaraNivelPosicionDos = "-000";
                    longitudNivel = nivelIn.length() + 1;
                    mascaraNuevoOpcion = "N";
                    desactivaRadioButtonSubNivel = false;
                    desactivaProyectoInicio = false;
                    habilitaEstado = mascaraNuevoOpcion.equals("S") ? false : true;

                }
            }
            cntProyecto.setEstado("PROY");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

    public String guardaProyecto() throws Exception {
        try {
            if (!cntProyectoService.existeProyecto()) {
                if (cntProyectoElegido.getNivel() == 2) {
                    if (mascaraNuevoOpcion.equals("N")) {                        
                        cntProyecto.setMascara(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos));
                        cntProyecto.setIdPadre(0L);
                        cntProyecto.setNivel(2);
                    } else {                        
                        cntProyecto.setIdPadre(cntProyectoElegido.getIdProyecto());
                        cntProyecto.setMascara(concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos));
                        cntProyecto.setNivel(1);
                    }
                } else {                    
                    cntProyecto.setIdPadre(cntProyectoElegido.getIdPadre());
                    cntProyecto.setMascara(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraSubNivelPosicionDos));
                    cntProyecto.setNivel(1);
                }
            } else {
                cntProyecto.setMascara(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos));
                cntProyecto.setIdPadre(0L);
                cntProyecto.setNivel(2);
            }
            if (!cntProyectoService.datosRepetidos(cntProyecto).equals("")) {
                MessageUtils.addErrorMessage(cntProyectoService.datosRepetidos(cntProyecto));
                return null;
            }
            super.setPersistValues(cntProyecto);
            cntProyectoService.persistCntProyecto(cntProyecto);
        } catch (Exception e) {
            MessageUtils.addErrorMessage("No se pudo guardar el Proyecto");
            return null;
        }
        return "proyectos";
    }

    public String concatenaNuevaMascaraDatosVista(String mascaraParteUno, String cajaDetexto, String mascaraParteDos) {
        return mascaraParteUno + cajaDetexto + mascaraParteDos;
    }

    public String cancelaGuardaProyecto() {
        limpiarVariablesSession();
        return "proyectos";
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        CntProyectosAdicionBacking.log = log;
    }

    public CntProyectoService getCntProyectoService() {
        return cntProyectoService;
    }

    public void setCntProyectoService(CntProyectoService cntProyectoService) {
        this.cntProyectoService = cntProyectoService;
    }

    public CntProyecto getCntProyecto() {
        return cntProyecto;
    }

    public void setCntProyecto(CntProyecto cntProyecto) {
        this.cntProyecto = cntProyecto;
    }

    public List<ParEstadoProyecto> getListaDeEstadosDeProyecto() {
        if (listaDeEstadosDeProyecto.isEmpty()) {
            listaDeEstadosDeProyecto = parParametricasService.listaEstadosDeProyectos();
        }
        return listaDeEstadosDeProyecto;
    }

    public void activaCombillo(ValueChangeEvent e) {
        habilitaEstado = mascaraNuevoOpcion.equals("S") ? false : true;
    }

    public void generaPruebaNivel(ValueChangeEvent e) {
    }

    public void generaPruebaSubNivel(ValueChangeEvent e) {
    }

    public boolean esEntero(String cad) {
        for (int i = 0; i < cad.length(); i++) {
            if (!Character.isDigit(cad.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public void setListaDeEstadosDeProyecto(List<ParEstadoProyecto> listaDeEstadosDeProyecto) {
        this.listaDeEstadosDeProyecto = listaDeEstadosDeProyecto;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public CntProyecto getCntProyectoElegido() {
        return cntProyectoElegido;
    }

    public void setCntProyectoElegido(CntProyecto cntProyectoElegido) {
        this.cntProyectoElegido = cntProyectoElegido;
    }

    public boolean isEligeCntProyecto() {
        return eligeCntProyecto;
    }

    public void setEligeCntProyecto(boolean eligeCntProyecto) {
        this.eligeCntProyecto = eligeCntProyecto;
    }

    public String getMascaraNuevoOpcion() {
        return mascaraNuevoOpcion;
    }

    public void setMascaraNuevoOpcion(String mascaraNuevoOpcion) {
        this.mascaraNuevoOpcion = mascaraNuevoOpcion;
    }

    public String getMascaraNivelPosicionUno() {
        return mascaraNivelPosicionUno;
    }

    public void setMascaraNivelPosicionUno(String mascaraNivelPosicionUno) {
        this.mascaraNivelPosicionUno = mascaraNivelPosicionUno;
    }

    public String getMascaraNivelPosicionDos() {
        return mascaraNivelPosicionDos;
    }

    public void setMascaraNivelPosicionDos(String mascaraNivelPosicionDos) {
        this.mascaraNivelPosicionDos = mascaraNivelPosicionDos;
    }

    public String getMascaraSubNivelPosicionUno() {
        return mascaraSubNivelPosicionUno;
    }

    public void setMascaraSubNivelPosicionUno(String mascaraSubNivelPosicionUno) {
        this.mascaraSubNivelPosicionUno = mascaraSubNivelPosicionUno;
    }

    public String getMascaraSubNivelPosicionDos() {
        return mascaraSubNivelPosicionDos;
    }

    public void setMascaraSubNivelPosicionDos(String mascaraSubNivelPosicionDos) {
        this.mascaraSubNivelPosicionDos = mascaraSubNivelPosicionDos;
    }

    public String getNivelIn() {
        return nivelIn;
    }

    public void setNivelIn(String nivelIn) {
        this.nivelIn = nivelIn;
    }

    public String getSubNivelIn() {
        return subNivelIn;
    }

    public void setSubNivelIn(String subNivelIn) {
        this.subNivelIn = subNivelIn;
    }

    public String getMensajeVerifica1() {
        return mensajeVerifica1;
    }

    public void setMensajeVerifica1(String mensajeVerifica1) {
        this.mensajeVerifica1 = mensajeVerifica1;
    }

    public String getMensajeVerifica2() {
        return mensajeVerifica2;
    }

    public void setMensajeVerifica2(String mensajeVerifica2) {
        this.mensajeVerifica2 = mensajeVerifica2;
    }

    public int getLongitudNivel() {
        return longitudNivel;
    }

    public void setLongitudNivel(int longitudNivel) {
        this.longitudNivel = longitudNivel;
    }

    public int getLongitudSubNivel() {
        return longitudSubNivel;
    }

    public void setLongitudSubNivel(int longitudSubNivel) {
        this.longitudSubNivel = longitudSubNivel;
    }

    public Boolean getDesactivaRadioButtonSubNivel() {
        return desactivaRadioButtonSubNivel;
    }

    public void setDesactivaRadioButtonSubNivel(Boolean desactivaRadioButtonSubNivel) {
        this.desactivaRadioButtonSubNivel = desactivaRadioButtonSubNivel;
    }

    public String getMascaraNivel() {
        return mascaraNivel;
    }

    public void setMascaraNivel(String mascaraNivel) {
        this.mascaraNivel = mascaraNivel;
    }

    public String getMascaraSubNivel() {
        return mascaraSubNivel;
    }

    public void setMascaraSubNivel(String mascaraSubNivel) {
        this.mascaraSubNivel = mascaraSubNivel;
    }

    public boolean isHabilitaEstado() {
        return habilitaEstado;
    }

    public void setHabilitaEstado(boolean habilitaEstado) {
        this.habilitaEstado = habilitaEstado;
    }

    public Boolean getDesactivaProyectoInicio() {
        return desactivaProyectoInicio;
    }

    public void setDesactivaProyectoInicio(Boolean desactivaProyectoInicio) {
        this.desactivaProyectoInicio = desactivaProyectoInicio;
    }
}
