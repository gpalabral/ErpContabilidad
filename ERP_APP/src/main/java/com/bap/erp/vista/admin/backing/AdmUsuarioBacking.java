/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.admin.backing;

import com.bap.erp.modelo.admin.AdmUsuarios;
import com.bap.erp.modelo.admin.servicio.AdmUsuariosService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Henrry
 */
@ManagedBean(name = "admUsuariosBacking")
@ViewScoped
public class AdmUsuarioBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{admUsuariosService}")
    private AdmUsuariosService admUsuariosService;

    private AdmUsuarios admUsuario;

    public AdmUsuarioBacking() {
    }

    @PostConstruct
    public void initAdmUsuariosBacking() {
        admUsuario = new AdmUsuarios();
        if (getFromSessionIdAdmUsuario() != null) {
            admUsuario = admUsuariosService.find(AdmUsuarios.class, getFromSessionIdAdmUsuario());
            setInSessionIdAdmUsuario(null);
        }
    }

    public String guardaAdmUsuario() {
        try {
            if (admUsuariosService.encuentraUsuario(admUsuario, false).getIdUsuario() == null) {

//            admUsuario.setUsuarioAlta(getLoginSession());
                admUsuario.setUsuarioAlta("TEST");
                admUsuario.setFechaAlta(new Date());
                admUsuariosService.persist(admUsuario);
                return "listaUsuarios";
            } else {
                MessageUtils.addErrorMessage("El usuario ya existe.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public String modificaAdmUsuario() {
        try {
//            admUsuario.setUsuarioAlta(getLoginSession());
            admUsuario.setUsuarioModificacion("TEST");
            admUsuario.setFechaModificacion(new Date());
            admUsuariosService.merge(admUsuario);
            return "listaUsuarios";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public String cancelar() {

        return "listaUsuarios";

    }

    public AdmUsuariosService getAdmUsuariosService() {
        return admUsuariosService;
    }

    public void setAdmUsuariosService(AdmUsuariosService admUsuariosService) {
        this.admUsuariosService = admUsuariosService;
    }

    public AdmUsuarios getAdmUsuario() {
        return admUsuario;
    }

    public void setAdmUsuario(AdmUsuarios admUsuario) {
        this.admUsuario = admUsuario;
    }

}
