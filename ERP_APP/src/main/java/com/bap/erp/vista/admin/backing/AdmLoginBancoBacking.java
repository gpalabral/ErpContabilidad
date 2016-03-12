/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.admin.backing;

import com.bap.erp.modelo.admin.AdmUsuarios;
import com.bap.erp.modelo.admin.servicio.AdmUsuariosService;
import com.bap.erp.modelo.enums.EnumEstadosUsuario;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.FacesUtils;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "admLoginBancoBacking")
@ViewScoped
public class AdmLoginBancoBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{admUsuariosService}")
    private AdmUsuariosService admUsuariosService;
    @ManagedProperty(value = "#{admIntentosBacking}")
    private AdmIntentosBacking admIntentosBacking;
    private Integer veces = 0;

    private AdmUsuarios admUsuarios;

    @PostConstruct
    public void initAdmLoginBacking() {
        System.out.println("el ip es-------------- " + FacesUtils.getServletRequest().getRemoteAddr());
        System.out.println("el remote host es -----" + FacesUtils.getServletRequest().getRemoteHost());
        admUsuarios = new AdmUsuarios();
    }

//    public String validarLogin() {
//        try {
//            AdmUsuarios usuarioEncontradoPorLogin = admUsuariosService.encuentraUsuarioLogueo(admUsuarios);
//            if (admUsuariosService.verificaExistenciaUsuarioBD(admUsuarios)) {
//                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//                HttpSession session = request.getSession(true);
//                getSessionManagedBean().setAuthenticated(true);
//                session.setAttribute("auth", true);
//                setLoginSession(admUsuarios.getLogin());
//                getSessionManagedBean().setTipoUsuario(usuarioEncontradoPorLogin.getTipoUsuario());
//                getSessionManagedBean().setNombreUsuario(admUsuarios.getLogin());
//                MessageUtils.addInfoMessage("Bienvenido " + admUsuarios.getLogin());
//                return "index";
//            } else {
////                AdmUsuarios usuarioEncontradoPorLogin = admUsuariosService.encuentraUsuarioLogueo(admUsuarios);
//                System.out.println("USUARIO:" + usuarioEncontradoPorLogin);
//                if (usuarioEncontradoPorLogin.getIdUsuario() != null) {
//                    if (!usuarioEncontradoPorLogin.getEstadoUsuario().equals(EnumEstadosUsuario.BLOQUEADO.getEstado())) {
//
//                        if (admIntentosBacking.getUsuariosFallidos().get(usuarioEncontradoPorLogin.getLogin()) != null) {
//                            veces = admIntentosBacking.getUsuariosFallidos().get(usuarioEncontradoPorLogin.getLogin());
//                            veces++;
//                            admIntentosBacking.getUsuariosFallidos().put(usuarioEncontradoPorLogin.getLogin(), veces);
//                        } else {
//                            admIntentosBacking.getUsuariosFallidos().put(usuarioEncontradoPorLogin.getLogin(), veces);
//                        }
//                        if (veces >= 3) {
//                            if (usuarioEncontradoPorLogin.getEstadoUsuario().equals("VIG")) {
//                                usuarioEncontradoPorLogin.setEstadoUsuario(EnumEstadosUsuario.BLOQUEADO.getEstado());
//                                usuarioEncontradoPorLogin.setFechaModificacion(new Date());
//                                usuarioEncontradoPorLogin.setUsuarioModificacion("SISTEMA");
//                                admUsuariosService.mergeAdmUsuarios(usuarioEncontradoPorLogin);
//                            }
//                            MessageUtils.addErrorMessage("login", "Esta cuenta esta bloqueada por exceder el número de intentos fallidos");
//                        } else {
//                            MessageUtils.addErrorMessage("login", "Usuario invalido, aseguresé de que el usuario y la contraseña sean correctos");
//                        }
//                    } else {
//                        MessageUtils.addErrorMessage("login", "Esta cuenta esta bloqueada por exceder el número de intentos fallidos");
//                    }
//                } else {
//                    MessageUtils.addErrorMessage("login", "Usuario invalido, aseguresé de que el usuario y la contraseña sean correctos");
//                }
//
//            }
//
//        } catch (Exception e) {
//            MessageUtils.addErrorMessage("login", "Error del SISTEMA al momento de logearse");
//            e.printStackTrace();
//        }
//        return null;
//    }
    public String validarLogin() {
        try {

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession(true);
            getSessionManagedBean().setAuthenticated(true);
            session.setAttribute("auth", true);
            setLoginSession(admUsuarios.getLogin());
            getSessionManagedBean().setNombreUsuario(admUsuarios.getLogin());
            MessageUtils.addInfoMessage("Bienvenido " + admUsuarios.getLogin());

            return "index";

        } catch (Exception e) {
            MessageUtils.addErrorMessage("login", "Error del SISTEMA al momento de logearse");
            e.printStackTrace();
        }
        return null;
    }

    public AdmUsuariosService getAdmUsuariosService() {
        return admUsuariosService;
    }

    public void setAdmUsuariosService(AdmUsuariosService admUsuariosService) {
        this.admUsuariosService = admUsuariosService;
    }

    public AdmUsuarios getAdmUsuarios() {
        return admUsuarios;
    }

    public void setAdmUsuarios(AdmUsuarios admUsuarios) {
        this.admUsuarios = admUsuarios;
    }

    public AdmIntentosBacking getAdmIntentosBacking() {
        return admIntentosBacking;
    }

    public void setAdmIntentosBacking(AdmIntentosBacking admIntentosBacking) {
        this.admIntentosBacking = admIntentosBacking;
    }

}
