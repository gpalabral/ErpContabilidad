package com.bap.erp.vista.client;

import com.bap.erp.modelo.entidades.cnf.ParAdministrador;
import com.bap.erp.modelo.servicios.cnf.CnfLoginService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.bap.erp.ws.WamsaAuthClient;
import java.io.File;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.log4j.Logger;

@ManagedBean(name = "admLoginBacking")
@ViewScoped
public class AdmLoginBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(AdmLoginBacking.class);

    @ManagedProperty(value = "#{cnfLoginService}")
    private CnfLoginService cnfLoginService;

    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;

    private String passNuevo = "";
    private String passConfirmado = "";
    private String passAntiguo = "";
    private File file;
    private ParAdministrador parAdministrador = new ParAdministrador();

    private String login;
    private String password;

    private WamsaAuthClient wamsaAuthClient=new WamsaAuthClient();

    @PostConstruct
    public void initAdmLoginBacking() {
        getLoginSession();
    }

    public String validarLogin() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
//            if (cnfLoginService.validaUsuario(getLogin(), getPassword())) {
            if (getWamsaAuthClient().login(login, password)) {

                
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                HttpSession session = request.getSession(true);
                getSessionManagedBean().setAuthenticated(true);
                session.setAttribute("auth", true);

//            admUsuarios = cPanelServicesClient.login(admUsuarios.getLogin(), admUsuarios.getPassword());
//            //Ponemos al usuario en modo de autenticacion
//            if (admUsuarios != null) {
//                setAuthenticationVariables(admUsuarios);
//            }
                MessageUtils.addInfoMessage("Bienvenido " + getLogin());
                setLoginSession(getLogin());
                setInSessionTipoUsuario(cnfLoginService.encuentraTipoUsuarioPorLogin(getLogin()));
//                log.info("Redirecting to Index page");
                getSessionManagedBean().setNombreUsuario("Administrador Wamsa");
                return "index";
            } else {
                MessageUtils.addErrorMessage("El usuario y la contraseña son incorrectos");
            }
//            if (cnfLoginService.validaUsuario(admUsuarios.getLogin(), admUsuarios.getPassword())) {
////            admUsuarios = cPanelServicesClient.login(admUsuarios.getLogin(), admUsuarios.getPassword());
////            //Ponemos al usuario en modo de autenticacion
////            if (admUsuarios != null) {
////                setAuthenticationVariables(admUsuarios);
////            }
//                MessageUtils.addInfoMessage("Bienvenido " + admUsuarios.getLogin());
//                setLoginSession(admUsuarios.getLogin());
////                log.info("Redirecting to Index page");
//                return "index";
//            }
        } catch (Exception e) {
            MessageUtils.addErrorMessage("Error al momento de logearse, verifique los datos introducidos.");
            log.error(e);
        }
        return null;
    }
    
     public String validarLoginTemporal() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        try {
            if (cnfLoginService.validaUsuario(getLogin(), getPassword())) {
                MessageUtils.addInfoMessage("Bienvenido " + getLogin());
                setLoginSession(getLogin());
                setInSessionTipoUsuario(cnfLoginService.encuentraTipoUsuarioPorLogin(getLogin()));
                getSessionManagedBean().setNombreUsuario("Administrador Wamsa");
                return "index";
            } else {
                MessageUtils.addErrorMessage("El usuario y la contraseña son incorrectos");
            }
        } catch (Exception e) {
            MessageUtils.addErrorMessage("Error al momento de logearse, verifique los datos introducidos.");
            log.error(e);
        }
        return null;
    }

//    private void setAuthenticationVariables(AdmUsuarios admUsuarios) {
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();;
//        HttpSession session = request.getSession(true);
//        if (admUsuarios.getEstadoUsuario().equals("VIG")) {
//            session.setAttribute("auth", true);
//            getSessionManagedBean().setLogin(admUsuarios.getLogin());
//            getSessionManagedBean().setAuthenticated(true);
//            AdmPersonas persona = admUsuarios.getAdmPersonas();
//            getSessionManagedBean().setNombreUsuario(persona.getNombre() + " " + persona.getApPaterno() + " " + persona.getApMaterno());
//        }
//    }
//
//    private void resetAuthenticationVariables() {
//        log.info("Terminando la session");
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//        HttpSession session = request.getSession(false);
//        session.setAttribute("auth", null);
//        getSessionManagedBean().setLogin(null);
//        getSessionManagedBean().setAuthenticated(false);
//        getSessionManagedBean().setNombreUsuario(null);
//        setSessionManagedBean(null);
//        admUsuarios = null;
//    }

    public Date getDateExact(Date d) {
        Calendar cal = Calendar.getInstance();
        Calendar dd = Calendar.getInstance();
        dd.setTime(d);
        cal.set(dd.get(Calendar.YEAR), dd.get(Calendar.MONTH), dd.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return cal.getTime();
    }

    public String logout() {
//        resetAuthenticationVariables();
        return "login";
    }

    public String cancela() {
        resetSessionVariables();
        return "index";
    }

    public String guardaContraseniaModificada() {
//        if (!cnfLoginService.validaUsuario(admUsuarios.getLogin(), passAntiguo)) {
//            MessageUtils.addErrorMessage("Error, el password actual no coincide");
//            return null;
//        }
//        if (cnfLoginService.encriptarContrasena(admUsuarios.getLogin(), passAntiguo).equals(cnfLoginService.encriptarContrasena(admUsuarios.getLogin(), passNuevo))) {
//            MessageUtils.addErrorMessage("Error, el nuevo password no puede ser similar al anterior");
//            return null;
//        }
//        try {
//            parAdministrador = cnfLoginService.findByUsuarioYPass(admUsuarios.getLogin(), passAntiguo);
//            if (parAdministrador != null) {
//                parAdministrador.setContrasenia(cnfLoginService.encriptarContrasena(admUsuarios.getLogin(), passNuevo));
//                parAdministrador.setFechaModificacion(new Date());
//                parAdministrador.setUsuarioModificacion(admUsuarios.getLogin());
//                cnfLoginService.mergeParAdministrador(parAdministrador);
//                MessageUtils.addInfoMessage("Se modifico la contraseña exitosamente");
//                return "login";
//            }
//        } catch (Exception e) {
//        }
        return null;
    }

    public CnfLoginService getCnfLoginService() {
        return cnfLoginService;
    }

    public void setCnfLoginService(CnfLoginService cnfLoginService) {
        this.cnfLoginService = cnfLoginService;
    }

    public String getPassNuevo() {
        return passNuevo;
    }

    public void setPassNuevo(String passNuevo) {
        this.passNuevo = passNuevo;
    }

    public String getPassConfirmado() {
        return passConfirmado;
    }

    public void setPassConfirmado(String passConfirmado) {
        this.passConfirmado = passConfirmado;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        AdmLoginBacking.log = log;
    }

    public String getPassAntiguo() {
        return passAntiguo;
    }

    public void setPassAntiguo(String passAntiguo) {
        this.passAntiguo = passAntiguo;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    //Codigo Henrry Guzman: Captcha nuevo
    public void check(ActionEvent e) {
//        MessageUtils.addInfoMessage("Su Codigo es correcto!");
//        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Your Code Is Correct !", null));
    }

    public ParAdministrador getParAdministrador() {
        return parAdministrador;
    }

    public void setParAdministrador(ParAdministrador parAdministrador) {
        this.parAdministrador = parAdministrador;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the wamsaAuthClient
     */
    public WamsaAuthClient getWamsaAuthClient() {
        return wamsaAuthClient;
    }

    /**
     * @param wamsaAuthClient the wamsaAuthClient to set
     */
    public void setWamsaAuthClient(WamsaAuthClient wamsaAuthClient) {
        this.wamsaAuthClient = wamsaAuthClient;
    }
}
