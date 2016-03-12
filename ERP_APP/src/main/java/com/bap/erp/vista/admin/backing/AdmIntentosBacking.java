/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.admin.backing;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "admIntentosBacking")
@ApplicationScoped
public class AdmIntentosBacking implements Serializable {
    
    private Map<String, Integer> usuariosFallidos = new HashMap<String, Integer>();    

    public Map<String, Integer> getUsuariosFallidos() {
        return usuariosFallidos;
    }

    public void setUsuariosFallidos(Map<String, Integer> usuariosFallidos) {
        this.usuariosFallidos = usuariosFallidos;
    }
       
}
