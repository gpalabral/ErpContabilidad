package com.bap.erp.vista.cnt.backing;

import com.bap.comp.CompPlanEntidadesBacking;
import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "cntEntidadBacking")
@ViewScoped
public class CntEntidadBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{planEntBacking}")
    private CompPlanEntidadesBacking compPlanEntidadesBacking;
    private String tipoCuenta = EnumGruposNivel.PLAN_CUENTAS.getCodigo();

    public CntEntidadBacking() {
    }

    @PostConstruct
    void initCntEntidadBacking() {
    }

    public String adicionar() {
        if (tipoCuenta.equals(EnumGruposNivel.PLAN_CUENTAS.getCodigo())) {            
//            if (compPlanEntidadesBacking.getEntidadSel() != null) {
            if(true){
//                setInSessionIdEntidad(compPlanEntidadesBacking.getEntidadSel().getIdEntidad());
                setInSessionIdEntidad(2L);
                return "entidadAdicionar";
            } else {
                MessageUtils.addInfoMessage("Debe seleccionar una cuenta");
            }            
        } else if (tipoCuenta.equals(EnumGruposNivel.CENTROS_COSTOS.getCodigo())) {
            return "entidadCentroDeCostoAdicionar";
        }
        return null;
    }

    public CompPlanEntidadesBacking getCompPlanEntidadesBacking() {
        return compPlanEntidadesBacking;
    }

    public void setCompPlanEntidadesBacking(CompPlanEntidadesBacking compPlanEntidadesBacking) {
        this.compPlanEntidadesBacking = compPlanEntidadesBacking;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
}
