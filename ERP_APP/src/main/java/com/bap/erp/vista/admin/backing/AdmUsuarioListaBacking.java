/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.admin.backing;

import com.bap.erp.modelo.admin.AdmUsuarios;
import com.bap.erp.modelo.admin.servicio.AdmUsuariosService;
import com.bap.erp.vista.common.AbstractManagedBean;
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
@ManagedBean(name = "admUsuarioListaBacking")
@ViewScoped
public class AdmUsuarioListaBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{admUsuariosService}")
    private AdmUsuariosService admUsuariosService;

    private AdmUsuarios admUsuario;

    private List<AdmUsuarios> listaUsuarios = new ArrayList<AdmUsuarios>();

    public AdmUsuarioListaBacking() {
    }

    @PostConstruct
    public void initAdmUsuarioListaBacking() {
        admUsuario = new AdmUsuarios();
        try {
            listaUsuarios = admUsuariosService.listaUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public AdmUsuariosService getAdmUsuariosService() {
        return admUsuariosService;
    }

    public void setAdmUsuariosService(AdmUsuariosService admUsuariosService) {
        this.admUsuariosService = admUsuariosService;
    }

    public List<AdmUsuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<AdmUsuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }


    public String irRegistraUsuario() {
        return "registroUsuarios";
    }

    public String irModificarUsuario(AdmUsuarios admUsuarios) {
        setInSessionIdAdmUsuario(admUsuarios.getIdUsuario());
        return "registroUsuarios";
    }

    public void seleccionaAdmUsuario(AdmUsuarios admUsuarios) {
        this.admUsuario = admUsuarios;
        org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('dialogoEliminacion').show()");
    }

    public void eliminarUsuario() {
        try {
//            admUsuario.setUsuarioBaja(getLoginSession());
            admUsuario.setUsuarioBaja("TEST");
            admUsuario.setFechaBaja(new Date());
            admUsuariosService.remove(admUsuario);
            listaUsuarios = admUsuariosService.listaUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AdmUsuarios getAdmUsuario() {
        return admUsuario;
    }

    public void setAdmUsuario(AdmUsuarios admUsuario) {
        this.admUsuario = admUsuario;
    }

}
