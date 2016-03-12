package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnf.ParParametrosGestion;
import com.bap.erp.modelo.entidades.cnf.ParParametrosServidor;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "cntParametrosServidorBacking")
@ViewScoped
public class CntParametrosServidorBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    private String urlAutenticacion = ""  ;    
    
    public CntParametrosServidorBacking() {
    }

    @PostConstruct
    public void initCntParametrosServidorBacking() {               
        try {
            
        urlAutenticacion = parParametricasService.findParametrosServidor("HOST").getValor();        
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public String modificaParametrosServidor(){
        try {
            if (urlAutenticacion.equals("")) {
                MessageUtils.addErrorMessage("La Url de Autentificacion es obligatoria");
            } else{
                parParametricasService.modificaParametrosServidor(urlAutenticacion);
                return "paginaEnBlanco";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String cancelar() {
        return "paginaEnBlanco";
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }            

    public String getUrlAutenticacion() {
        return urlAutenticacion;
    }

    public void setUrlAutenticacion(String urlAutenticacion) {
        this.urlAutenticacion = urlAutenticacion;
    }
   
    
}
