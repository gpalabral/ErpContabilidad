package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnf.ParGruposNivel;
import com.bap.erp.modelo.entidades.cnf.ParTiposDatoNivel;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.enums.EnumTiposDatoNivel;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import com.bap.erp.modelo.servicios.cnt.CntNivelesService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "cntNivelesMascarBacking")
@ViewScoped
public class CntNivelesMascarBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntNivelesService}")
    private CntNivelesService cntNivelesService;
    @ManagedProperty(value = "#{cntMascarasService}")
    private CntMascarasService cntMascarasService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    private CntNivel cntNiveles;
    private List<CntNivel> auxCntNivelesList = new ArrayList<CntNivel>();
    private int tamanioNumerosList[] = {2, 3, 4, 5, 6, 7, 8, 9};
    private int nivel = 0;
    private int numerosNiveles = 0;
    private String generacionMascara = "";
    private int mascara = 0;
    private int mascaraAux = 9;
    private CntMascara cntMascaras;
    private int contador;
    private CntNivel selectedNiveles;
    private List<CntNivel> filteredCntNiveles;
    private int numeroObjetosNivel;
    private String gruposNivelCombo = "PCTA";
    ///// codigo nuevo
    Boolean swActivaOpciones = false;
    private String mascaraC;
    private String tipo;
    private List<ParTiposDatoNivel> parTiposDatoNivelList = new ArrayList<ParTiposDatoNivel>();
    private int nivelModifica;
    private int tamanioModifica;
    private String descripcionModifica;
    private Boolean habilitaSeleccionDeCuentas = true;

    public CntNivelesMascarBacking() {
    }

    @PostConstruct
    void initcntNivelesBacking() {
        if (super.getFromSessionIdEntidad() != null) {            
            cntNiveles = (CntNivel) cntNivelesService.find(CntNivel.class, super.getFromSessionIdEntidad());
        } else {
            cntMascaras = new CntMascara();
            cntNiveles = new CntNivel();
            cntNiveles.setTamanio(1);
        }
        if (!cntMascarasService.verificaExistenciaDeMascara("PCTA")) {
            habilitaSeleccionDeCuentas = false;
            numeroObjetosNivel = (cntNivelesService.listaDeNivelesPlanCuentas("PCTA")).size();
            auxCntNivelesList = cntNivelesService.listaDeNivelesPlanCuentas("PCTA");
            generacionMascara = cntNivelesService.muestraMascaraPlanCuentas("PCTA");
        }
    }

    public void setSelectRowCntNiveles(CntNivel value) {
        super.setInSessionIdEntidad(value.getIdNivel());
    }

    public String saveCntNiveles_action() {
        if (cntMascarasService.validaCamposSinLlenarDelListado(auxCntNivelesList).equals("OK")) {
            if (gruposNivelCombo != null) {

                cntMascaras.setTamanioNivel(numerosNiveles);
                if (gruposNivelCombo.equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo())) {
                    cntMascaras.setMascara(generacionMascara);
                }
                if (gruposNivelCombo.equals(EnumGruposNivel.CENTROS_COSTOS.getCodigo())) {
                    cntMascaras.setMascara(mascaraC);
                }
                super.setPersistValues(cntMascaras);
                try {
                    ParGruposNivel parGruposNivel = (ParGruposNivel) parParametricasService.find(ParGruposNivel.class, gruposNivelCombo);
                    cntMascaras.setGrupoNivel(parGruposNivel);
                    if (cntMascarasService.verificaExistenciaDeMascara(parGruposNivel.getCodigo())) {
                        cntMascarasService.adicionarCntMascara(cntMascaras, auxCntNivelesList);
                        MessageUtils.addInfoMessage("Se registro niveles para:" + gruposNivelCombo + " de forma correcta.");
                        if (gruposNivelCombo.equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo()) || gruposNivelCombo.equals(EnumGruposNivel.CENTROS_COSTOS.getCodigo())) {
                            return "definicionCuentas";
                        } else {
                            return "objetos";
                        }

                    } else {
                        MessageUtils.addErrorMessage("Ya se registro la mascara para Plan de Cuentas.");
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MessageUtils.addErrorMessage("Ocurrio un error al registrar la mascara y los niveles.");
                }
            } else {
                MessageUtils.addInfoMessage("Seleccione un de las opciones.");
            }
        } else {
            MessageUtils.addErrorMessage(cntMascarasService.validaCamposSinLlenarDelListado(auxCntNivelesList));
        }
        return null;
    }

    public void seleccionarNivel(ActionEvent event) {
        tipo = selectedNiveles.getTipoNivel().getCodigo();
        nivelModifica = selectedNiveles.getNivel();
        descripcionModifica = selectedNiveles.getDescripcion();
        tamanioModifica = selectedNiveles.getTamanio();
    }

    public String saveCntNivelesCentroCostos_action() {
        if (gruposNivelCombo != null) {
            MessageUtils.addInfoMessage("Esta creando Niveles para:" + gruposNivelCombo);
            cntMascaras.setTamanioNivel(numerosNiveles);
            if (gruposNivelCombo.equals(EnumGruposNivel.CENTROS_COSTOS.getCodigo())) {
                cntMascaras.setMascara(mascaraC);
            }
            super.setPersistValues(cntMascaras);
            try {
                ParGruposNivel parGruposNivel = (ParGruposNivel) parParametricasService.find(ParGruposNivel.class, gruposNivelCombo);
                cntMascaras.setGrupoNivel(parGruposNivel);
                cntMascarasService.adicionarCntMascara(cntMascaras, auxCntNivelesList);
                MessageUtils.addInfoMessage("Se registro los datos de forma correcta !!");
                if (gruposNivelCombo.equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo()) || gruposNivelCombo.equals(EnumGruposNivel.CENTROS_COSTOS.getCodigo())) {
                    return "definicionCuentas";
                } else {
                    return "objetos";
                }
            } catch (Exception e) {
                e.printStackTrace();
                MessageUtils.addErrorMessage("Ocurrio un error al registrar la mascara y los niveles.");
            }
        } else {
            MessageUtils.addInfoMessage("Seleccione un de las opciones.");
        }
        return null;
    }

    public void agregarNivel() {
        auxCntNivelesList = new ArrayList<CntNivel>();
        nivel = 0;
        numerosNiveles = 0;
        for (int i = 1; i <= numeroObjetosNivel; i++) {
            nivel++;
            cntNiveles = new CntNivel();
            cntNiveles.setNivel(nivel);
            cntNiveles.setTamanio(1);
            ParTiposDatoNivel parTiposDatoNivel = (ParTiposDatoNivel) parParametricasService.find(ParTiposDatoNivel.class, "N");
            cntNiveles.setTipoNivel(parTiposDatoNivel);
            super.setPersistValues(cntNiveles);
            auxCntNivelesList.add(cntNiveles);
            generaMascaraAutomatico();
            cntNiveles = new CntNivel();
            cntNiveles.setTamanio(1);
            numerosNiveles++;
        }

    }

    public void agregarNivel(ValueChangeEvent e) {
        auxCntNivelesList = new ArrayList<CntNivel>();
        nivel = 0;
        numerosNiveles = 0;
        for (int i = 1; i <= numeroObjetosNivel; i++) {
            nivel++;
            cntNiveles.setNivel(nivel);
            cntNiveles.setTamanio(1);
            cntNiveles.setDescripcion("");
            ParTiposDatoNivel parTiposDatoNivel = (ParTiposDatoNivel) parParametricasService.find(ParTiposDatoNivel.class, "N");
            cntNiveles.setTipoNivel(parTiposDatoNivel);
            super.setPersistValues(cntNiveles);
            auxCntNivelesList.add(cntNiveles);
            if (gruposNivelCombo.equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo())) {
                generaMascaraAutomatico();
            } else {
                swActivaOpciones = true;
                generaMascaraAutomaticoCentros();
            }
            cntNiveles = new CntNivel();
            cntNiveles.setTamanio(1);
            numerosNiveles++;
        }
    }

    public String generaMascaraAutomatico() {
        mascara = 0;
        mascaraAux = 9;
        contador = 1;
        generacionMascara = "";
        for (CntNivel cntNivelesAux : auxCntNivelesList) {
            for (int i = 1; i <= cntNivelesAux.getTamanio(); i++) {
                mascara = mascara * 10 + mascaraAux;
            }
            if (contador == auxCntNivelesList.size()) {
                generacionMascara = generacionMascara + Integer.toString(mascara);
                mascara = 0;
            } else {
                generacionMascara = generacionMascara + Integer.toString(mascara) + "-";
                mascara = 0;

            }
            contador++;
        }
        return generacionMascara;
    }

    public boolean isActivaTamanio() {
        if (auxCntNivelesList.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean isActivaCombo() {
        if (auxCntNivelesList.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean activaTamanioList(CntNivel cntNiveles) {
        if (cntNiveles.getNivel() == 1) {
            return false;
        }
        return true;
    }

    /**
     * *********** Get y Set ***********
     */
    public List<CntNivel> getAuxCntNivelesList() {
        return auxCntNivelesList;
    }

    public void setAuxCntNivelesList(List<CntNivel> auxCntNivelesList) {
        this.auxCntNivelesList = auxCntNivelesList;
    }

    public CntNivel getCntNiveles() {
        return cntNiveles;
    }

    public void setCntNiveles(CntNivel cntNiveles) {
        this.cntNiveles = cntNiveles;
    }

    public int[] getTamanioNumerosList() {
        return tamanioNumerosList;
    }

    public void setTamanioNumerosList(int[] tamanioNumerosList) {
        this.tamanioNumerosList = tamanioNumerosList;
    }

    public CntNivelesService getCntNivelesService() {
        return cntNivelesService;
    }

    public void setCntNivelesService(CntNivelesService cntNivelesService) {
        this.cntNivelesService = cntNivelesService;
    }

    public int getNumerosNiveles() {
        return numerosNiveles;
    }

    public void setNumerosNiveles(int numerosNiveles) {
        this.numerosNiveles = numerosNiveles;
    }

    public String getGeneracionMascara() {
        return generacionMascara;
    }

    public void setGeneracionMascara(String generacionMascara) {
        this.generacionMascara = generacionMascara;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public CntMascara getCntMascaras() {
        return cntMascaras;
    }

    public void setCntMascaras(CntMascara cntMascaras) {
        this.cntMascaras = cntMascaras;
    }

    public CntNivel getSelectedNiveles() {
        return selectedNiveles;
    }

    public void setSelectedNiveles(CntNivel selectedNiveles) {
        this.selectedNiveles = selectedNiveles;
    }

    public List<CntNivel> getFilteredCntNiveles() {
        return filteredCntNiveles;
    }

    public void setFilteredCntNiveles(List<CntNivel> filteredCntNiveles) {
        this.filteredCntNiveles = filteredCntNiveles;
    }

    public String editaCntNivelList_action() {
        selectedNiveles.setDescripcion(descripcionModifica);
        selectedNiveles.setNivel(nivelModifica);
        selectedNiveles.setTamanio(tamanioModifica);
        generaMascaraAutomatico();
        return null;
    }

    public int getNumeroObjetosNivel() {
        if (numeroObjetosNivel == 0) {
            numeroObjetosNivel = 2;
            agregarNivel();
        }
        return numeroObjetosNivel;
    }

    public void setNumeroObjetosNivel(int numeroObjetosNivel) {
        this.numeroObjetosNivel = numeroObjetosNivel;
    }

    public void cierrraDialogo(ValueChangeEvent e) {
        cntNiveles = new CntNivel();

    }

    public CntMascarasService getCntMascarasService() {
        return cntMascarasService;
    }

    public void setCntMascarasService(CntMascarasService cntMascarasService) {
        this.cntMascarasService = cntMascarasService;
    }

    public String getGruposNivelCombo() {
        return gruposNivelCombo;
    }

    public void setGruposNivelCombo(String gruposNivelCombo) {
        this.gruposNivelCombo = gruposNivelCombo;
    }

    public Boolean getSwActivaOpciones() {
        return swActivaOpciones;
    }

    public void setSwActivaOpciones(Boolean swActivaOpciones) {
        this.swActivaOpciones = swActivaOpciones;
    }

    //codigo henrry para centros costo combinacion total..
    public void activaOpciones(ValueChangeEvent e) {
        if (gruposNivelCombo.equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo())) {
            swActivaOpciones = false;
        } else {
            generaMascaraAutomaticoCentros();
            swActivaOpciones = true;
        }
    }

    public void activaOpcionesCentrodeCostos(ValueChangeEvent e) {
        if (gruposNivelCombo.equals(EnumGruposNivel.CENTROS_COSTOS.getCodigo())) {
            swActivaOpciones = true;
            generaMascaraAutomaticoCentros();
        }
    }

    public String editaNivelesCentrosCosto() {
        ParTiposDatoNivel parTiposDatoNivel = (ParTiposDatoNivel) parParametricasService.find(ParTiposDatoNivel.class, tipo);
        selectedNiveles.setTipoNivel(parTiposDatoNivel);
        generaMascaraAutomaticoCentros();
        return null;
    }

    public String generaMascaraAutomaticoCentros() {
        String straux = "";
        for (CntNivel cntNivelesAux : auxCntNivelesList) {
            for (int i = 1; i <= cntNivelesAux.getTamanio(); i++) {
                if (cntNivelesAux.getTipoNivel().getCodigo().equals(EnumTiposDatoNivel.CARACTER.getCodigo())) {
                    straux = straux + "&";
                }
                if (cntNivelesAux.getTipoNivel().getCodigo().equals(EnumTiposDatoNivel.NUMERO.getCodigo())) {
                    straux = straux + "9";
                }
            }
            straux = straux + "-";
        }
        int len = straux.length();
        mascaraC = straux.substring(0, len - 1);
        return mascaraC;
    }

    public String getMascaraC() {
        return mascaraC;
    }

    public void setMascaraC(String mascaraC) {
        this.mascaraC = mascaraC;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<ParTiposDatoNivel> getParTiposDatoNivelList() {
        try {
            parTiposDatoNivelList = parParametricasService.getParTiposDatoNivels();

        } catch (Exception e) {
            e.getStackTrace();
        }
        return parTiposDatoNivelList;
    }

    public void setParTiposDatoNivelList(List<ParTiposDatoNivel> parTiposDatoNivelList) {
        this.parTiposDatoNivelList = parTiposDatoNivelList;
    }

    public String iconoGuardar() {
        return EnumIconosPrimeFaces.GUARDAR.getCodigo();
    }

    public String iconoModificar() {
        return EnumIconosPrimeFaces.MODIFICAR.getCodigo();
    }

    public String iconoEliminar() {
        return EnumIconosPrimeFaces.ELIMINAR.getCodigo();
    }

    public String iconoAtras() {
        return EnumIconosPrimeFaces.ATRAS.getCodigo();
    }

    public String iconoIrOtraVentana() {
        return EnumIconosPrimeFaces.IR_OTRA_VENTANA.getCodigo();
    }

    public int getTamanioModifica() {
        return tamanioModifica;
    }

    public void setTamanioModifica(int tamanioModifica) {
        this.tamanioModifica = tamanioModifica;
    }

    public String getDescripcionModifica() {
        return descripcionModifica;
    }

    public void setDescripcionModifica(String descripcionModifica) {
        this.descripcionModifica = descripcionModifica;
    }

    public int getNivelModifica() {
        return nivelModifica;
    }

    public void setNivelModifica(int nivelModifica) {
        this.nivelModifica = nivelModifica;
    }

    public Boolean getHabilitaSeleccionDeCuentas() {
        return habilitaSeleccionDeCuentas;
    }

    public void setHabilitaSeleccionDeCuentas(Boolean habilitaSeleccionDeCuentas) {
        this.habilitaSeleccionDeCuentas = habilitaSeleccionDeCuentas;
    }
}
