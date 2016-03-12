package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGenerales;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGeneralesNivel;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnf.ParCuentasGeneralesService;
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

@ManagedBean(name = "cntDefinicionesBacking")
@ViewScoped
public class CntDefinicionesBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @ManagedProperty(value = "#{parCuentasGeneralesService}")
    private ParCuentasGeneralesService parCuentasGeneralesService;
    @ManagedProperty(value = "#{cntMascarasService}")
    private CntMascarasService cntMascarasService;
    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    private ParCuentasGenerales parCuentasGenerales;
    private ParCuentasGeneralesNivel parCuentasGeneralesNivel;
    private List<ParCuentasGenerales> parCuentasGeneralesList;
    private List<ParCuentasGeneralesNivel> cuentasGeneralesNivelAuxList = new ArrayList<ParCuentasGeneralesNivel>();
    private List<Integer> listaCombo = new ArrayList<Integer>();
    private CntMascara cntMascaras;
    private Boolean habilitaSeleccionDeCuentas = true;

    public CntDefinicionesBacking() {
    }

    @PostConstruct
    void initCntDefinirCuentasBacking() {
        if (parParametricasService.verificaSiExistenCuentasGenerales()) {
            habilitaSeleccionDeCuentas = false;
        } else {
            parCuentasGeneralesNivel = new ParCuentasGeneralesNivel();
            parCuentasGeneralesNivel.setNivel(0);
            listaCombo.add(0);
            listaCombo.add(1);
            listaCombo.add(2);
            listaCombo.add(3);
            listaCombo.add(4);
            listaCombo.add(5);
            listaCombo.add(6);
            listaCombo.add(7);
            listaCombo.add(8);
            listaCombo.add(9);
        }
    }

    public String saveParCuentasGenerales_action() throws Exception {
        try {
            if (cuentasGeneralesNivelAuxList.size() <= 9) {
                if (!cntEntidadesService.verificaCuentasGeneralesListLleno(cuentasGeneralesNivelAuxList)) {
                    cntMascaras = (CntMascara) cntMascarasService.obtieneMascarasPorGrrupoNivel(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
                    if (cntMascaras != null) {
                        cntEntidadesService.persistCuentasGeneralesPasanObjetosList(cuentasGeneralesNivelAuxList, cntMascaras,getLoginSession());
                        MessageUtils.addInfoMessage("Se adiciono de forma correcta.");
                        return "planCuentas";
                    } else {
                        MessageUtils.addErrorMessage("Falta crear la Mascara para el Plan de Cuentas.");
                        return null;
                    }
                } else {
                    MessageUtils.addErrorMessage("No se puede adicionar de forma correcta, ya que existen niveles en CERO.");
                    return null;
                }
            } else {
                MessageUtils.addErrorMessage("No se puede adicionar mas de 9 registros.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ParCuentasGenerales> getParCuentasGeneralesList() {
        parCuentasGeneralesList = parParametricasService.parCuentasGeneralesList();
        return parCuentasGeneralesList;
    }

    public List<ParCuentasGeneralesNivel> cuentasGeneralesNivelList() {        
        if (cuentasGeneralesNivelAuxList.isEmpty()) {            
            parCuentasGeneralesList = parParametricasService.parCuentasGeneralesList();
            for (ParCuentasGenerales parCuentasGeneralesAux : parCuentasGeneralesList) {
                ParCuentasGeneralesNivel cuentasGeneralesNivel = new ParCuentasGeneralesNivel();
                cuentasGeneralesNivel.setCuentasGenerales(parCuentasGeneralesAux);
                cuentasGeneralesNivel.setNivel(Integer.parseInt(parCuentasGeneralesAux.getValor()));
//                super.setPersistValues(this);
                cuentasGeneralesNivelAuxList.add(cuentasGeneralesNivel);
            }
        }
        return cuentasGeneralesNivelAuxList;
    }

    public void setParCuentasGeneralesList(List<ParCuentasGenerales> parCuentasGeneralesList) {
        this.parCuentasGeneralesList = parCuentasGeneralesList;
    }

    public ParCuentasGenerales getParCuentasGenerales() {
        return parCuentasGenerales;
    }

    public void setParCuentasGenerales(ParCuentasGenerales parCuentasGenerales) {
        this.parCuentasGenerales = parCuentasGenerales;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public List<ParCuentasGeneralesNivel> getCuentasGeneralesNivelAuxList() {
        return cuentasGeneralesNivelAuxList;
    }

    public void setCuentasGeneralesNivelAuxList(List<ParCuentasGeneralesNivel> cuentasGeneralesNivelAuxList) {
        this.cuentasGeneralesNivelAuxList = cuentasGeneralesNivelAuxList;
    }

    public void controlaNivel(ValueChangeEvent event) {
        List<Integer> listaAux = new ArrayList<Integer>();
        listaAux.add(0);
        for (int i = 1; i <= 9; i++) {
            boolean sw = true;
            for (ParCuentasGeneralesNivel parCuentasGeneralesNivelAux : cuentasGeneralesNivelAuxList) {
                if (parCuentasGeneralesNivelAux.getNivel() == i) {
                    sw = false;
                }
            }
            if (sw) {
                listaAux.add(i);
            }
        }
        listaCombo = listaAux;
    }

    public ParCuentasGeneralesNivel getParCuentasGeneralesNivel() {
        return parCuentasGeneralesNivel;
    }

    public void setParCuentasGeneralesNivel(ParCuentasGeneralesNivel parCuentasGeneralesNivel) {
        this.parCuentasGeneralesNivel = parCuentasGeneralesNivel;
    }

    public ParCuentasGeneralesService getParCuentasGeneralesService() {
        return parCuentasGeneralesService;
    }

    public void setParCuentasGeneralesService(ParCuentasGeneralesService parCuentasGeneralesService) {
        this.parCuentasGeneralesService = parCuentasGeneralesService;
    }

    public CntMascara getCntMascaras() {
        return cntMascaras;
    }

    public void setCntMascaras(CntMascara cntMascaras) {
        this.cntMascaras = cntMascaras;
    }

    public CntMascarasService getCntMascarasService() {
        return cntMascarasService;
    }

    public void setCntMascarasService(CntMascarasService cntMascarasService) {
        this.cntMascarasService = cntMascarasService;
    }

    public List<Integer> getListaCombo() {
        return listaCombo;
    }

    public void setListaCombo(List<Integer> listaCombo) {
        this.listaCombo = listaCombo;
    }

    /**
     * @return the cntEntidadesService
     */
    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    /**
     * @param cntEntidadesService the cntEntidadesService to set
     */
    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public Boolean getHabilitaSeleccionDeCuentas() {
        return habilitaSeleccionDeCuentas;
    }

    public void setHabilitaSeleccionDeCuentas(Boolean habilitaSeleccionDeCuentas) {
        this.habilitaSeleccionDeCuentas = habilitaSeleccionDeCuentas;
    }
}
