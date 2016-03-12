/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bap.erp.vista.utils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author Henrry Guzman
 */
public class TiempoInactividadServletListener implements HttpSessionAttributeListener {

    public void attributeAdded(HttpSessionBindingEvent event) {
        HttpSession sess = event.getSession();
        sess.setMaxInactiveInterval(30 * 60);
    }

    public void attributeRemoved(HttpSessionBindingEvent event) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void attributeReplaced(HttpSessionBindingEvent event) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
