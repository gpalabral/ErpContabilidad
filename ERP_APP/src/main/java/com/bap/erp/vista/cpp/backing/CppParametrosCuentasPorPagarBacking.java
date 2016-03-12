package com.bap.erp.vista.cpp.backing;

import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import com.iknow.utils.ObjectUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "cppParametrosCuentasPorPagarBacking")
@ViewScoped
public class CppParametrosCuentasPorPagarBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    private List<String> parametrosList = new ArrayList<String>();
    private int periodoActualVista;
    private List<ParValor> listaParametricasSegunOpcion;
    private ParValor filteredParValor;
    private ParValor selectedParValor;
    private ParValor parValor;
    private Boolean activaModifica;
    private String codigo;
    private String descripcion;

    public CppParametrosCuentasPorPagarBacking() {
    }

    @PostConstruct
    void initcppParametrosCuentasPorPagarBacking() {
         selectedParValor = new ParValor();
        parValor = new ParValor();

    }

    public void cargaDatosLista() {
        if (parametrosList.isEmpty()) {
            parametrosList.add("1 -  BANCO");
            parametrosList.add("2 - CIUDAD");
            parametrosList.add("3 - LUGAR DE ENTREGA");
            parametrosList.add("4 - TIPO DE DOCUMENTO IDENTIDAD");
            listaParametricasSegunOpcion = parParametricasService.cargaListadoParametricas(1);
        }
    }

    public void actualizaParValor(ValueChangeEvent event) {
        parValor = new ParValor();
        selectedParValor = new ParValor();
        selectedParValor.setCodigo("");
        selectedParValor.setDescripcion("");
        codigo="";
        descripcion="";
        activaModifica = false;

    }

    public void actualizaParValorChano() {
        parValor = new ParValor();
        selectedParValor = new ParValor();
        activaModifica = false;
    }

    public void actualizaParValorDos(ValueChangeEvent event) {
        codigo = selectedParValor.getCodigo();
        descripcion = selectedParValor.getDescripcion();
        activaModifica = true;
           

    }

    public void actualizaListado(ValueChangeEvent event) {
        listaParametricasSegunOpcion = parParametricasService.cargaListadoParametricas(periodoActualVista);
    }

    public List<String> listadoSegunParametro() {
        cargaDatosLista();
        return parametrosList;
    }

    public void obtieneObjetoParValor(ValueChangeEvent e) {
    }

    public int extraeNumeroDeCadena(String cadena) {
        if (!cadena.equals("")) {
            String[] vector = cadena.split(" - ");
            int numero = Integer.parseInt(vector[0]);
            return numero;
        }
        return 0;
    }

    public String guardaParametros() throws Exception {
        parValor.setCodigo(codigo);
        parValor.setDescripcion(descripcion);
        if (parParametricasService.verificaExistenciaDeCodigo(parValor).equals("OK")) {            
            if (parParametricasService.verificaExistenciaDescripcion(parValor).equals("OK")) {                
                setPersistValues(parValor);
                parParametricasService.guardaParametros(parValor, periodoActualVista);
                listaParametricasSegunOpcion = parParametricasService.cargaListadoParametricas(periodoActualVista);
                parValor = new ParValor();
            } else {
                MessageUtils.addErrorMessage(parParametricasService.verificaExistenciaDescripcion(parValor));
                return null;
            }
        } else {
            MessageUtils.addErrorMessage("Debe llenar el campo Codigo");
            return null;
        }
//        return "parametricasCuentasPorCobrar";
        return null;
    }

    public String modificaParametros() throws Exception {                
    
        selectedParValor.setCodigo(codigo);
        selectedParValor.setDescripcion(descripcion);        
        if (parParametricasService.verificaExistenciaDeCodigo(selectedParValor).equals("OK")) {
            if (parParametricasService.verificaExistenciaDescripcion(selectedParValor).equals("OK")) {
                setMergeValues(selectedParValor);                                
                parParametricasService.modificaParParametros(selectedParValor, periodoActualVista);
                listaParametricasSegunOpcion = parParametricasService.cargaListadoParametricas(periodoActualVista);
                selectedParValor = new ParValor();
            } else {
                MessageUtils.addErrorMessage(parParametricasService.verificaExistenciaDescripcion(selectedParValor));
                return null;
            }
        } else {
            MessageUtils.addErrorMessage("Debe llenar el campo Codigo");
            return null;
        }
           return null;
    }

    ///////////////////////////////////////////////////////////////////////////////gets and sets 
    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public List<String> getParametrosList() {
        return parametrosList;
    }

    public void setParametrosList(List<String> parametrosList) {
        this.parametrosList = parametrosList;
    }

    public int getPeriodoActualVista() {
        return periodoActualVista;
    }

    
    public void setPeriodoActualVista(int periodoActualVista) {
        this.periodoActualVista = periodoActualVista;
    }

    public List<ParValor> getListaParametricasSegunOpcion() {
        return listaParametricasSegunOpcion;
    }

    public void setListaParametricasSegunOpcion(List<ParValor> listaParametricasSegunOpcion) {
        this.listaParametricasSegunOpcion = listaParametricasSegunOpcion;
    }

    public ParValor getFilteredParValor() {
        return filteredParValor;
    }

    public void setFilteredParValor(ParValor filteredParValor) {
        this.filteredParValor = filteredParValor;
    }

    public ParValor getSelectedParValor() {
        return selectedParValor;
    }

    public void setSelectedParValor(ParValor selectedParValor) {
        this.selectedParValor = selectedParValor;
    }

    public ParValor getParValor() {
        return parValor;
    }

    public void setParValor(ParValor parValor) {
        this.parValor = parValor;
    }

    public Boolean getActivaModifica() {
        return activaModifica;
    }

    public void setActivaModifica(Boolean activaModifica) {
        this.activaModifica = activaModifica;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
