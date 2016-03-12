package com.bap.erp.vista.cnf.backing;

import com.bap.erp.vista.cnt.backing.*;
import com.bap.erp.vista.common.AbstractManagedBean;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

@ManagedBean(name = "cnfLoginBacking")
@ViewScoped
public class CnfLoginBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CntAuxiliaresBacking.class);
    String login="";
    String password="";

    public CnfLoginBacking() {
    }

    @PostConstruct
    public void initCntAuxiliarBacking() {

    }
    
    public String validarLogin(){
        
        return null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

}
