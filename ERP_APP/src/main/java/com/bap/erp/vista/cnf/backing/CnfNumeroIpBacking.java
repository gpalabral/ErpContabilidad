package com.bap.erp.vista.cnf.backing;

import com.bap.erp.modelo.entidades.cnf.ParIpWebServiceWamsa;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.servicios.cnf.CnfLoginService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import com.bap.erp.vista.client.CPanelServicesClient;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.iknow.utils.ObjectUtils;
import java.io.Serializable;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import net.sf.rubycollect4j.iter.FindAllIterable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@ManagedBean(name = "cnfNumeroIpBacking")
@ViewScoped
public class CnfNumeroIpBacking extends AbstractManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(CnfNumeroIpBacking.class);
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    @Autowired
    private CPanelServicesClient cPanelServicesClient = new CPanelServicesClient();
    private ParValor parValor;
    private ParIpWebServiceWamsa parIpWebServiceWamsa;
    private String numeroUno = "";
    private String numeroDos = "";
    private String numeroTres = "";
    private String numeroCuatro = "";
    private String numeros[];
    private Boolean editable;
    private String numeroIpVista;
    private Boolean habilitaModificar;
    private Boolean valorHabilita = true;
    private Boolean pruebaConexion;
    private String mensaje;
    private String nombreBoton;

    public CnfNumeroIpBacking() {
    }

    @PostConstruct
    public void initCnfNumeroIpBacking() {
        parValor = parParametricasService.findParValorContextoWebServices("ACT");
        if (!parValor.getDescripcion().equals("0")) {
            numeros = (parValor.getDescripcion()).split("[.]");
            numeroUno = numeros[0];
            numeroDos = numeros[1];
            numeroTres = numeros[2];
            numeroCuatro = numeros[3];
            editable = false;
            habilitaModificar = true;
        } else {
            editable = true;
            habilitaModificar = false;
        }
    }

    public String guardaDireccionIP() throws Exception {
        numeroIpVista = numeroUno + "." + numeroDos + "." + numeroTres + "." + numeroCuatro;
        if (parParametricasService.validaNumeroIp(numeroIpVista)) {
            setMergeValues(parValor);
            parValor.setDescripcion(numeroIpVista);
            int numero = 1;
            parParametricasService.modificaParParametros(parValor, numero);
            MessageUtils.addInfoMessage("El numero Ip fue registrado...");
            return "OK";
        } else {
            MessageUtils.addErrorMessage("El numero no es valido.");
            return null;

        }

    }

    public void habilitaModificar(ValueChangeEvent e) {
        if (valorHabilita) {
            habilitaModificar = false;
        } else {
            habilitaModificar = true;
        }
    }

    public String editaNumeroIP() throws Exception {
        numeroIpVista = numeroUno + "." + numeroDos + "." + numeroTres + "." + numeroCuatro;
        if (parParametricasService.validaNumeroIp(numeroIpVista)) {
            setMergeValues(parValor);
            parValor.setDescripcion(numeroIpVista);
            int numero = 1;
            parParametricasService.modificaParParametros(parValor, numero);
            MessageUtils.addInfoMessage("El numero Ip fue modificado...");
            return "OK";
        } else {
            MessageUtils.addErrorMessage("El numero no es valido.");
            return null;

        }
    }

    public String limpiaIP() {
        numeroUno = "";
        numeroDos = "";
        numeroTres = " ";
        numeroCuatro = "";
        return "configuraIP";
        
    }

    public void verificaConeccion(ValueChangeEvent e) {
    }
//gets and set

    public ParIpWebServiceWamsa getParIpWebServiceWamsa() {
        return parIpWebServiceWamsa;
    }

    public void setParIpWebServiceWamsa(ParIpWebServiceWamsa parIpWebServiceWamsa) {
        this.parIpWebServiceWamsa = parIpWebServiceWamsa;
    }

    public ParValor getParValor() {
        return parValor;
    }

    public void setParValor(ParValor parValor) {
        this.parValor = parValor;
    }

    public String getNumeroUno() {
        return numeroUno;
    }

    public void setNumeroUno(String numeroUno) {
        this.numeroUno = numeroUno;
    }

    public String getNumeroDos() {
        return numeroDos;
    }

    public void setNumeroDos(String numeroDos) {
        this.numeroDos = numeroDos;
    }

    public String getNumeroTres() {
        return numeroTres;
    }

    public void setNumeroTres(String numeroTres) {
        this.numeroTres = numeroTres;
    }

    public String getNumeroCuatro() {
        return numeroCuatro;
    }

    public void setNumeroCuatro(String numeroCuatro) {
        this.numeroCuatro = numeroCuatro;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        CnfNumeroIpBacking.log = log;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public String[] getNumeros() {
        return numeros;
    }

    public void setNumeros(String[] numeros) {
        this.numeros = numeros;
    }

    public String getNumeroIpVista() {
        return numeroIpVista;
    }

    public void setNumeroIpVista(String numeroIpVista) {
        this.numeroIpVista = numeroIpVista;
    }

    public Boolean getHabilitaModificar() {
        return habilitaModificar;
    }

    public void setHabilitaModificar(Boolean habilitaModificar) {
        this.habilitaModificar = habilitaModificar;
    }

    public Boolean getValorHabilita() {
        return valorHabilita;
    }

    public void setValorHabilita(Boolean valorHabilita) {
        this.valorHabilita = valorHabilita;
    }

    public String verificaConexionWS() {
        try {
            pruebaConexion = cPanelServicesClient.verificaConexion(numeroUno + "." + numeroDos + "." + numeroTres + "." + numeroCuatro);
//            MessageUtils.addInfoMessage("Conexion con el servidor de WAMSA correcta.");
            mensaje = "Conexion con el servidor de WAMSA correcta.";
            valorHabilita = false;
           nombreBoton="CANCELAR";
           return mensaje;
        } catch (Exception e) {
            pruebaConexion = false;
//            MessageUtils.addErrorMessage("El IP: " + numeroUno + "." + numeroDos + "." + numeroTres + "." + numeroCuatro + " es incorrecto, no existe conexion con el servidor de WAMSA.");
            mensaje = "El IP introducido es incorrecto, no existe conexion con el servidor de WAMSA.";
            nombreBoton="VOLVER";
            e.printStackTrace();
            valorHabilita = true;
            return mensaje;
        }

    }

    public CPanelServicesClient getcPanelServicesClient() {
        return cPanelServicesClient;
    }

    public void setcPanelServicesClient(CPanelServicesClient cPanelServicesClient) {
        this.cPanelServicesClient = cPanelServicesClient;
    }

    public Boolean getPruebaConexion() {
        return pruebaConexion;
    }

    public void setPruebaConexion(Boolean pruebaConexion) {
        this.pruebaConexion = pruebaConexion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreBoton() {
        return nombreBoton;
    }

    public void setNombreBoton(String nombreBoton) {
        this.nombreBoton = nombreBoton;
    }
    
    
}
