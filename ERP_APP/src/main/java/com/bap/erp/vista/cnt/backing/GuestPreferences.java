/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.cnt.backing;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class GuestPreferences implements Serializable {

//    private String theme = "south-street"; //default
//    private String theme = "hot-sneaks"; //default
    private String theme = "cupertino"; //default
//    private String theme = "pepper-grinder"; //default
//    private String theme = "bluesky"; //default

    public String getTheme() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.containsKey("theme")) {
            theme = params.get("theme");
        }

        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
