package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.servicios.cnt.CntCentrosCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.iknow.utils.ObjectUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "cntCentrosDeCostoAdicionarBacking")
@ViewScoped
public class CntCentrosDeCostoAdicionarBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntCentrosCostoService}")
    private CntCentrosCostoService cntCentrosCostoService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{cntMascarasService}")
    private CntMascarasService cntMascarasService;
    private CntEntidad centrosCosto;
    private CntEntidad centrosCostoNuevo;
    private String mascaraNuevoOpcion;
    private String mascaraNivelPosicionUno = "";
    private String mascaraNivelPosicionDos = "";
    private String nivelIn;
    private int longitudNivel;
    private int longitudSubNivel;
    private String mensajeVerifica1;
    private String mensajeVerifica2;
    private Boolean desactivaRadioButtonSubNivel = true;
    private String mascaraSubNivelPosicionUno = "";
    private String mascaraSubNivelPosicionDos = "";
    private String subNivelIn;

    public CntCentrosDeCostoAdicionarBacking() {
    }

    @PostConstruct
    void initCntCentrosDeCostoAdicionarBacking() {
        if (getFromSessionIdCentroDeCosto() != null) {
            centrosCosto = new CntEntidad();
            centrosCostoNuevo = (CntEntidad) cntCentrosCostoService.find(CntEntidad.class, getFromSessionIdCentroDeCosto());
            cargaDatosParaNivelAndSubNivel();
        } else {
            centrosCosto = new CntEntidad();
            centrosCostoNuevo = new CntEntidad();

            cargaDatosParaNivelAndSubNivel();

        }
    }

    public void cargaDatosParaNivelAndSubNivel() {
        try {

            if (cntCentrosCostoService.verificaExistenciaCentrosDeCosto()) {
                mascaraNivelPosicionUno = cntCentrosCostoService.obtieneMascaraSeparada(centrosCostoNuevo, "N")[0];
                mascaraNivelPosicionDos = cntCentrosCostoService.obtieneMascaraSeparada(centrosCostoNuevo, "N")[1];
                longitudNivel = cntCentrosCostoService.controlaLongitudNumero(centrosCostoNuevo, "N");
                nivelIn = cntCentrosCostoService.generaNumeroSiguienteAutomatico(centrosCostoNuevo, "N");
                mensajeVerifica1 = cntCentrosCostoService.verificaExistenciaCodigo(mascaraNivelPosicionUno + nivelIn + mascaraNivelPosicionDos);
                if (centrosCostoNuevo.getNivel() == 1) {
                    desactivaRadioButtonSubNivel = false;
                } else {
                    mascaraSubNivelPosicionUno = cntCentrosCostoService.obtieneMascaraSeparada(centrosCostoNuevo, "S")[0];
                    mascaraSubNivelPosicionDos = cntCentrosCostoService.obtieneMascaraSeparada(centrosCostoNuevo, "S")[1];
                    longitudSubNivel = cntCentrosCostoService.controlaLongitudNumero(centrosCostoNuevo, "S");
                    subNivelIn = cntCentrosCostoService.generaNumeroSiguienteAutomatico(centrosCostoNuevo, "S");
                    mensajeVerifica2 = cntCentrosCostoService.verificaExistenciaCodigo(mascaraSubNivelPosicionUno + subNivelIn + mascaraSubNivelPosicionDos);
                }
            } else {
                String mascaraOriginal = cntMascarasService.obtieneMascarasPorGrrupoNivel(EnumGruposNivel.CENTROS_COSTOS.getCodigo()).getMascara();
                mascaraNivelPosicionDos = cntCentrosCostoService.construyeMascaraInicialNivelYSubNivel(cntCentrosCostoService.convierteMascaraConCeros(mascaraOriginal), "N")[2];
                nivelIn = cntCentrosCostoService.construyeMascaraInicialNivelYSubNivel(cntCentrosCostoService.convierteMascaraConCeros(mascaraOriginal), "N")[0];
                longitudNivel = nivelIn.length() + 1;
                mensajeVerifica1 = cntCentrosCostoService.verificaExistenciaCodigo(mascaraNivelPosicionUno + nivelIn + mascaraNivelPosicionDos);
                desactivaRadioButtonSubNivel = false;

            }
            mascaraNuevoOpcion = "N";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String saveCntCentrosCosto_action() throws Exception {
        centrosCosto.setParametrosIndividuales("N");
        centrosCosto.setIdEntidadPadre(0L);
        if (mascaraNuevoOpcion == null) {
            MessageUtils.addErrorMessage("No se registró, ya que no seleccionó ninguna opción");
            return null;
        } else if (mascaraNuevoOpcion.equals("N")) {
//            if (nivelIn.length() == longitudNivel) { REVISAR PARA HENRRY
            centrosCosto.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos));
//            } else {
//                MessageUtils.addErrorMessage("El código de Nivel no tiene el formato requerido");
//                return null;
//            }
            if (centrosCostoNuevo.getIdEntidad() != null) {
                centrosCosto.setIdEntidadPadre(centrosCostoNuevo.getIdEntidadPadre());
            }
        } else {
            if (subNivelIn.length() == longitudSubNivel) {
                centrosCosto.setMascaraGenerada(concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos));
            } else {
                MessageUtils.addErrorMessage("El código de Subnivel no tiene el formato requerido");
                return null;
            }
            if (centrosCostoNuevo.getIdEntidad() != null) {
                centrosCosto.setIdEntidadPadre(centrosCostoNuevo.getIdEntidad());
            }
        }
        centrosCosto.setCntMascara(centrosCostoNuevo.getCntMascara());

        if (centrosCostoNuevo.getIdEntidad() != null) {
            centrosCosto.setNivel(cntCentrosCostoService.obtieneNiveleCuentaSubAndPadre(centrosCostoNuevo, mascaraNuevoOpcion));
        } else {
            centrosCosto.setNivel(cntMascarasService.obtieneMascarasPorGrrupoNivel(EnumGruposNivel.CENTROS_COSTOS.getCodigo()).getTamanioNivel());
        }
        centrosCosto.setTipo(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
        centrosCosto.setTieneAuxiliar("N");
        centrosCosto.setTieneCentroCosto("N");
        centrosCosto.setHabilitaCentroCosto("N");
        if (centrosCosto.getIdEntidad() != null) {
            centrosCosto = cntCentrosCostoService.verificaSiEsPadre(centrosCosto, mascaraNuevoOpcion, super.getFromSessionIdCentroDeCosto());
        }
        super.setPersistValues(centrosCosto);
        if (cntCentrosCostoService.verificaExistenciaMascara(centrosCosto)) {
//            if (cntCentrosCostoService.verificaMascara(centrosCosto.getMascaraGenerada())) {
            if (cntCentrosCostoService.verificaMascaraVacio(centrosCosto.getMascaraGenerada())) {
                cntCentrosCostoService.persistCntCentroCostoNivelAndSubNivel(centrosCosto);
                return "centrosDeCosto";
            } else {
                MessageUtils.addErrorMessage("La cuenta tiene un valor vacio  " + centrosCosto.getMascaraGenerada() + ", verifique porfavor.");
                return null;
            }
//            } else {
//                MessageUtils.addErrorMessage("La cuenta tiene un valor no valido, cambie el caracter (&) por uno valido.");
//                return null;
//            }
        } else {
            MessageUtils.addErrorMessage("La cuenta " + centrosCosto.getMascaraGenerada() + " ya existe");
        }
        return null;
    }

    public void generaPruebaNivel(ValueChangeEvent e) {
        if (cntCentrosCostoService.verificaExistenciaMascaraNivelAndSubNivel(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos))) {
            MessageUtils.addErrorMessage("El código " + concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos) + " ya existe inserte otro número.");
        } else {
            MessageUtils.addInfoMessage("El código " + concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos) + " no existe puede registrar sin problemas.");
        }
        mensajeVerifica1 = cntCentrosCostoService.verificaExistenciaCodigo(concatenaNuevaMascaraDatosVista(mascaraNivelPosicionUno, nivelIn, mascaraNivelPosicionDos));
    }

    public void generaPruebaSubNivel(ValueChangeEvent e) {
        if (cntCentrosCostoService.verificaExistenciaMascaraNivelAndSubNivel(concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos))) {
            MessageUtils.addErrorMessage("El código " + concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos) + " ya existe inserte otro número.");
        } else {
            MessageUtils.addInfoMessage("El código " + concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos) + " no existe puede registrar sin problemas.");
        }
        mensajeVerifica2 = cntCentrosCostoService.verificaExistenciaCodigo(concatenaNuevaMascaraDatosVista(mascaraSubNivelPosicionUno, subNivelIn, mascaraSubNivelPosicionDos));

    }

    public String cancelarCntCentrosCosto_action() {
        setInSessionIdCentroDeCosto(null);
        return "centrosDeCosto";
    }

    public String iconoRegistra() {
        return EnumIconosPrimeFaces.GUARDAR.getCodigo();
    }

    public String iconoEdita() {
        return EnumIconosPrimeFaces.MODIFICAR.getCodigo();
    }

    public String iconoElimina() {
        return EnumIconosPrimeFaces.ELIMINAR.getCodigo();
    }

    public String iconoAtras() {
        return EnumIconosPrimeFaces.ATRAS.getCodigo();
    }

    public String concatenaNuevaMascaraDatosVista(String mascaraParteUno, String cajaDetexto, String mascaraParteDos) {
        return mascaraParteUno + cajaDetexto + mascaraParteDos;
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public CntEntidad getCentrosCosto() {
        return centrosCosto;
    }

    public void setCentrosCosto(CntEntidad centrosCosto) {
        this.centrosCosto = centrosCosto;
    }

    public String getMascaraNuevoOpcion() {
        return mascaraNuevoOpcion;
    }

    public void setMascaraNuevoOpcion(String mascaraNuevoOpcion) {
        this.mascaraNuevoOpcion = mascaraNuevoOpcion;
    }

    public CntEntidad getCentrosCostoNuevo() {
        return centrosCostoNuevo;
    }

    public void setCentrosCostoNuevo(CntEntidad centrosCostoNuevo) {
        this.centrosCostoNuevo = centrosCostoNuevo;
    }

    public String getMascaraNivelPosicionUno() {
        return mascaraNivelPosicionUno;
    }

    public void setMascaraNivelPosicionUno(String mascaraNivelPosicionUno) {
        this.mascaraNivelPosicionUno = mascaraNivelPosicionUno;
    }

    public String getNivelIn() {
        return nivelIn;
    }

    public void setNivelIn(String nivelIn) {
        this.nivelIn = nivelIn;
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

    public Boolean getDesactivaRadioButtonSubNivel() {
        return desactivaRadioButtonSubNivel;
    }

    public void setDesactivaRadioButtonSubNivel(Boolean desactivaRadioButtonSubNivel) {
        this.desactivaRadioButtonSubNivel = desactivaRadioButtonSubNivel;
    }

    public String getMascaraSubNivelPosicionUno() {
        return mascaraSubNivelPosicionUno;
    }

    public void setMascaraSubNivelPosicionUno(String mascaraSubNivelPosicionUno) {
        this.mascaraSubNivelPosicionUno = mascaraSubNivelPosicionUno;
    }

    public String getMascaraNivelPosicionDos() {
        return mascaraNivelPosicionDos;
    }

    public void setMascaraNivelPosicionDos(String mascaraNivelPosicionDos) {
        this.mascaraNivelPosicionDos = mascaraNivelPosicionDos;
    }

    public String getMascaraSubNivelPosicionDos() {
        return mascaraSubNivelPosicionDos;
    }

    public void setMascaraSubNivelPosicionDos(String mascaraSubNivelPosicionDos) {
        this.mascaraSubNivelPosicionDos = mascaraSubNivelPosicionDos;
    }

    public String getSubNivelIn() {
        return subNivelIn;
    }

    public void setSubNivelIn(String subNivelIn) {
        this.subNivelIn = subNivelIn;
    }

    public CntCentrosCostoService getCntCentrosCostoService() {
        return cntCentrosCostoService;
    }

    public void setCntCentrosCostoService(CntCentrosCostoService cntCentrosCostoService) {
        this.cntCentrosCostoService = cntCentrosCostoService;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public CntMascarasService getCntMascarasService() {
        return cntMascarasService;
    }

    public void setCntMascarasService(CntMascarasService cntMascarasService) {
        this.cntMascarasService = cntMascarasService;
    }
}
