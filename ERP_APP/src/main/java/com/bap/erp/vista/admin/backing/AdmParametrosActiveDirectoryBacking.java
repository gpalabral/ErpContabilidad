package com.bap.erp.vista.admin.backing;

import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "admParametrosActiveDirectoryBacking")
@ViewScoped
public class AdmParametrosActiveDirectoryBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    private String host = "";
    private String dominio = "";

    public AdmParametrosActiveDirectoryBacking() {
    }

    @PostConstruct
    public void initCntParametrosServidorBacking() {
        try {            
            host = parParametricasService.findParametrosActiveDirectory("HOST").getValor();
            dominio = parParametricasService.findParametrosActiveDirectory("DOM").getValor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String modificaParametrosServidor() {
        try {
            if (host.equals("")) {
                MessageUtils.addErrorMessage("La direccion Ip es Obligatoria");
            } else if (dominio.equals("")) {
                MessageUtils.addErrorMessage("El dominio es Obligatorio");
            } else {
                parParametricasService.modificaParametrosActiveDirectory(host,dominio);
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

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


}
