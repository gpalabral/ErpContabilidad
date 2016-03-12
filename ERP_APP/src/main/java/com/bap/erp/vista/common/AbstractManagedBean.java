package com.bap.erp.vista.common;

import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.vista.utils.FacesUtils;
import com.iknow.utils.ObjectUtils;
import java.io.*;
import java.util.Date;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.NoneScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@NoneScoped
public class AbstractManagedBean {
    
    @ManagedProperty(value = "#{sessionState}")
    private SessionStateManagedBean sessionManagedBean;

    /**
     * lo
     *
     * @return the sessionManagedBean
     */
    public SessionStateManagedBean getSessionManagedBean() {
        return sessionManagedBean;
    }

    /**
     * @param sessionManagedBean the sessionManagedBean to set
     */
    public void setSessionManagedBean(SessionStateManagedBean sessionManagedBean) {
        this.sessionManagedBean = sessionManagedBean;
    }
    
    public void setInSessionIdEntidad(Long idEntidad) {
        getSessionManagedBean().setIdEntidad(idEntidad);
    }
    
    public Long getFromSessionIdEntidad() {
        return getSessionManagedBean().getIdEntidad();
    }
    
    public void setInSessionIdEntidad2(Long idEntidad) {
        getSessionManagedBean().setIdEntidad2(idEntidad);
    }
    
    public Long getFromSessionIdEntidad2() {
        return getSessionManagedBean().getIdEntidad2();
    }
    
    public void setInSessionIdEntidad3(Long idEntidad) {
        getSessionManagedBean().setIdEntidad3(idEntidad);
    }
    
    public Long getFromSessionIdEntidad3() {
        return getSessionManagedBean().getIdEntidad3();
    }
    
    public void setInSessiontIdEntidadAsignacionCC(Long idEntidad) {
        getSessionManagedBean().setIdEntidadAsignacionCC(idEntidad);
    }
    
    public Long getFromSessionIdEntidadAsignacionCC() {
        return getSessionManagedBean().getIdEntidadAsignacionCC();
    }
    
    public void setInSessiontCodigoParRecetasDistribucionCC(String codigo) {
        getSessionManagedBean().setCodigoParRecetasDistribucionCC(codigo);
    }
    
    public String getFromSessionCodigoParRecetasDistribucionCC() {
        return getSessionManagedBean().getCodigoParRecetasDistribucionCC();
    }
    
    public String getInSessionStringAuxiliar() {
        return getSessionManagedBean().getStringAuxiliar();
    }
    
    public void setInSessionStringAuxiliar(String stringAuxiliar) {
        getSessionManagedBean().setStringAuxiliar(stringAuxiliar);
    }
    
    public String getInSessionStringAuxiliar2() {
        return getSessionManagedBean().getStringAuxiliar2();
    }
    
    public void setInSessionStringAuxiliar2(String stringAuxiliar) {
        getSessionManagedBean().setStringAuxiliar2(stringAuxiliar);
    }
    
    public String getInSessionStringAuxiliar3() {
        return getSessionManagedBean().getStringAuxiliar3();
    }
    
    public void setInSessionStringAuxiliar3(String stringAuxiliar) {
        getSessionManagedBean().setStringAuxiliar3(stringAuxiliar);
    }
    
    public void setInSessionSkinLayout(String skinLayotut) {
        getSessionManagedBean().setSkinLayout(skinLayotut);
    }
    
    public String getFromSessionSkinLayout() {
        return getSessionManagedBean().getSkinLayout();
    }
    
    public void setForRemove() {
        getSessionManagedBean().setDeleteable(true);
    }
    
    public boolean isForRemove() {
        return getSessionManagedBean().isDeleteable();
    }
    
    public void resetSessionVariables() {
        getSessionManagedBean().setIdEntidad(null);
        getSessionManagedBean().setDeleteable(false);
    }
    
    public void setPersistValues(Serializable entity) {
        entity = ObjectUtils.setValorAtributoObjectToField("fechaAlta", entity, new Date(), Date.class);
        entity = ObjectUtils.setValorAtributoObjectToField("usuarioAlta", entity, getSessionManagedBean().getLogin(), String.class);
    }
    
    public void setMergeValues(Serializable entity) {
        entity = ObjectUtils.setValorAtributoObjectToField("fechaModificacion", entity, new Date(), Date.class);
        entity = ObjectUtils.setValorAtributoObjectToField("usuarioModificacion", entity, getSessionManagedBean().getLogin(), String.class);
    }
    
    public void setRemoveValues(Serializable entity) {
        entity = ObjectUtils.setValorAtributoObjectToField("fechaBaja", entity, new Date(), Date.class);
        entity = ObjectUtils.setValorAtributoObjectToField("usuarioBaja", entity, getSessionManagedBean().getLogin(), String.class);
    }
    
    public String getDateFormat() {
        return getSessionManagedBean().getDateFormat();
    }
    
    public String getDateTimeFormat() {
        return getSessionManagedBean().getDateTimeFormat();
    }

    //<editor-fold defaultstate="collapsed" desc="Metodos para subir archivos a la base de datos">
    /**
     * Lectura del archivo fisico para convertirlo en byte y ponerlo en la base
     * de datos
     *
     * @param File
     * @return byte []
     * @version 1.0, 25/07/12
     * @author Cristian Cruz Morales
     */
    public byte[] devolverArchivo(File f) {
        try {
            FileInputStream in = new FileInputStream(f);
            byte[] bytes = new byte[(int) f.length()];
            in.read(bytes);
            in.close();
            return bytes;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Lectura del archivo fisico para devolver el tipo de archivo que se subio
     *
     * @param File
     * @return String
     * @version 1.0, 25/07/12
     * @author Cristian Cruz Morales
     */
    public String devolverTipoArchivo(File f) {
        return new MimetypesFileTypeMap().getContentType(f);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodos para descargar los archivos desde la base de datos">
    /**
     * Lectura de los byte's que se subio para convertirlos en un archivo fisico
     * en el servidor
     *
     * @param String nombre
     * @param byte[] bytes
     * @return String
     * @version 1.0, 25/07/12
     * @author Cristian Cruz Morales
     */
    public void crearArchivoFisico(String nombre, byte[] bytes) throws FileNotFoundException, IOException {
        File someFile = new File(nombre);
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }

    /**
     * Obtencion del archivo fisico en el servidor para poder descargarlo
     *
     * @param String tipoArchivo
     * @param String nombreArchivo
     * @param byte[] archivo
     * @return String
     * @version 1.0, 25/07/12
     * @author Cristian Cruz Morales
     */
    public void generaFile(String tipoArchivo, String nombreArchivo, byte[] archivo) {
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = (String) servletContext.getRealPath("/WEB-INF/uploaded/" + nombreArchivo);
            crearArchivoFisico(realPath, archivo);
            File ficheroXLS = new File(realPath);
            FacesContext ctx = FacesContext.getCurrentInstance();
            FileInputStream fis = new FileInputStream(ficheroXLS);
            byte[] bytes = new byte[1000];
            int read = 0;
            if (!ctx.getResponseComplete()) {
                String fileName = ficheroXLS.getName();
                String contentType = tipoArchivo;
                //String contentType = "application/pdf";
                HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                ServletOutputStream out = response.getOutputStream();
                while ((read = fis.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                ctx.responseComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //</editor-fold>    

    public Long getIdGestionSession() {
        return getSessionManagedBean().getIdGestion();
    }
    
    public void setIdGestionSession(Long idGestion) {
        getSessionManagedBean().setIdGestion(idGestion);
    }
    
    public String getLoginSession() {
        return getSessionManagedBean().getLogin();
    }
    
    public void setLoginSession(String login) {
        getSessionManagedBean().setLogin(login);
        FacesUtils.getHttpSession(true).setAttribute("auth", "true");
    }

//    public void setInSessionCntDetalleTransaccionesList(List<CntDetalleTx> lista) {
//        getSessionManagedBean().setCntDetalleTransaccionesListSession(lista);
//    }
//
//    public List<CntDetalleTx> getFromSessionCntDetalleTransaccionesList() {
//        return getSessionManagedBean().getCntDetalleTransaccionesListSession();
//    }
//
//    //////////////////
//    public CntDetalleTx getFromSessionCntDetalle() {
//        return getSessionManagedBean().getCntDetalleTx();
//    }
//
//    public void setInSessionCntDetalle(CntDetalleTx cntDetalleTx) {
//        getSessionManagedBean().setCntDetalleTx(cntDetalleTx);
//    }
//
    public CntEntidad getCntObjetosSession() {
        return getSessionManagedBean().getCntObjetos();
    }
    
    public void setCntObjetosSession(CntEntidad cntObjetos) {
        getSessionManagedBean().setCntObjetos(cntObjetos);
    }
    
    public void setInSessionIntAuxiliar(int intAuxiliar) {
        getSessionManagedBean().setIntAuxiliar(intAuxiliar);
    }
    
    public int getFromSessionIntAuxiliar() {
        return getSessionManagedBean().getIntAuxiliar();
    }
    
    public void setInSessionIntAuxiliar2(int intAuxiliar2) {
        getSessionManagedBean().setIntAuxiliar2(intAuxiliar2);
    }
    
    public int getFromSessionIntAuxiliar2() {
        return getSessionManagedBean().getIntAuxiliar2();
    }
    
    public void setInSessionIdEntidadSeleccion(Long idEntidad) {
        getSessionManagedBean().setIdEntidadSeleccion(idEntidad);
    }
    
    public Long getFromSessionIdEntidadSeleccion() {
        return getSessionManagedBean().getIdEntidadSeleccion();
    }
    
    public void setInSessionCntDetalleComprobantesListSesion(List<CntDetalleComprobante> cntDetalleComprobantesLista) {
        getSessionManagedBean().setCntDetalleComprobantesListSesion(cntDetalleComprobantesLista);
    }
    
    public List<CntDetalleComprobante> getFromSessionCntDetalleComprobantesListSesion() {
        return getSessionManagedBean().getCntDetalleComprobantesListSesion();
    }
    
    public void setInSessionIdComprobante(Long idComprobante) {
        getSessionManagedBean().setIdComprobante(idComprobante);
    }
    
    public Long getFromSessionIdComprobante() {
        return getSessionManagedBean().getIdComprobante();
    }
    
    public void setInSessionIdDetalleComprobante(Long idDetalleComprobante) {
        getSessionManagedBean().setIdDetalleComprobante(idDetalleComprobante);
    }
    
    public Long getFromSessionIdDetalleComprobante() {
        return getSessionManagedBean().getIdDetalleComprobante();
    }
    
    public void setInSessionIdDetalleComprobanteModifica(Long idDetalleComprobanteModifica) {
        getSessionManagedBean().setIdDetalleComprobanteModifica(idDetalleComprobanteModifica);
    }
    
    public Long getFromSessionIdDetalleComprobanteModifica() {
        return getSessionManagedBean().getIdDetalleComprobanteModifica();
    }
    
    public void setInSessionIdCentroDeCosto(Long idCentroDeCosto) {
        getSessionManagedBean().setIdCentroDeCosto(idCentroDeCosto);
    }
    
    public Long getFromSessionIdCentroDeCosto() {
        return getSessionManagedBean().getIdCentroDeCosto();
    }
    
    public String getFromSessionTipoDeGrupoNivel() {
        return getSessionManagedBean().getTipoDeGrupoNivel();
    }
    
    public void setInSessionTipoDeGrupoNivel(String tipoDeGrupoNivel) {
        getSessionManagedBean().setTipoDeGrupoNivel(tipoDeGrupoNivel);
    }
    
    public String getFromSessionNombreUsuario() {
        return getSessionManagedBean().getNombreUsuario();
    }
    
    public void setInSessionNombreUsuario(String nombreUsuario) {
        getSessionManagedBean().setNombreUsuario(nombreUsuario);
    }
    
    public void limpiarVariablesSession() {
        getSessionManagedBean().setIdCentroDeCosto(null);
        getSessionManagedBean().setIdComprobante(null);
        getSessionManagedBean().setIdDetalleComprobante(null);
        getSessionManagedBean().setIdDetalleComprobanteModifica(null);
        getSessionManagedBean().setIdEntidad(null);
        getSessionManagedBean().setIdEntidad2(null);
        getSessionManagedBean().setIdEntidad3(null);
        getSessionManagedBean().setIdEntidadAsignacionCC(null);
        getSessionManagedBean().setIdEntidadSeleccion(null);
        getSessionManagedBean().setTipoDeGrupoNivel(null);
        getSessionManagedBean().setIdProyecto(null);
        getSessionManagedBean().setActivaDesactivaBotonesDetalle(false);
        getSessionManagedBean().setListaDeCuentasFiltrada(null);
        getSessionManagedBean().setValorBusqueda(null);
        getSessionManagedBean().setIdEntidadBG(null);
        getSessionManagedBean().setIdEntidadEERRR(null);
        getSessionManagedBean().setFechaInicio(null);
        getSessionManagedBean().setFechaFin(null);
        getSessionManagedBean().setIdEntidadFacturacion(null);
    }
    
    public void setInSessionIdProyecto(Long idProyecto) {
        getSessionManagedBean().setIdProyecto(idProyecto);
    }
    
    public Long getFromSessionIdProyecto() {
        return getSessionManagedBean().getIdProyecto();
    }
    
    public void setInSessionIdDetalleComprobanteParaMayor(Long idDetalleComprobanteParaMayor) {
        getSessionManagedBean().setIdDetalleComprobanteParaMayor(idDetalleComprobanteParaMayor);
    }
    
    public Long getFromSessionIdDetalleComprobanteParaMayor() {
        return getSessionManagedBean().getIdDetalleComprobanteParaMayor();
    }
    
    public void setInSessionIdEntidadParaMayor(Long idEntidad) {
        getSessionManagedBean().setIdEntidadParaMayor(idEntidad);
    }
    
    public Long getFromSessionIdEntidadParaMayor() {
        return getSessionManagedBean().getIdEntidadParaMayor();
    }
    
    public void setInSessionIdTipoProveedor(Long idTipoProveedor) {
        getSessionManagedBean().setIdTipoProveedor(idTipoProveedor);
    }
    
    public Long getFromSessionIdTipoProveedor() {
        return getSessionManagedBean().getIdTipoProveedor();
    }
    
    public void setInSessionIdProveedor(Long idProveedor) {
        getSessionManagedBean().setIdProveedor(idProveedor);
    }
    
    public Long getFromSessionIdProveedor() {
        return getSessionManagedBean().getIdProveedor();
    }
    
    public void setInSessionIdComprobanteModifica(Long idComprobante) {
        getSessionManagedBean().setIdComprobanteModifica(idComprobante);
    }
    
    public Long getFromSessionIdComprobanteModifica() {
        return getSessionManagedBean().getIdComprobanteModifica();
    }
    
    public void setInSessionTipoUsuario(String tipoUsuario) {
        getSessionManagedBean().setTipoUsuario(tipoUsuario);
    }
    
    public String getFromSessionTipoUsuario() {
        return getSessionManagedBean().getTipoUsuario();
    }
    
    public void setInSessionActivaDesactivaBotonesDetalle(Boolean activaBotones) {
        getSessionManagedBean().setActivaDesactivaBotonesDetalle(activaBotones);
    }
    
    public Boolean getFromSessionActivaDesactivaBotonesDetalle() {
        return getSessionManagedBean().getActivaDesactivaBotonesDetalle();
    }
    
    public void setInSessionListaDeCuentasFiltrada(List<CntEntidad> listaDeCuentasFiltrada) {
        getSessionManagedBean().setListaDeCuentasFiltrada(listaDeCuentasFiltrada);
    }
    
    public List<CntEntidad> getFromSessionListaDeCuentasFiltrada() {
        return getSessionManagedBean().getListaDeCuentasFiltrada();
    }
    
    public void setInSessionValorBusqueda(String valorBusqueda) {
        getSessionManagedBean().setValorBusqueda(valorBusqueda);
    }
    
    public String getFromSessionValorBusqueda() {
        return getSessionManagedBean().getValorBusqueda();
    }
    
    public void setInSessionIdPlanCuentasParametrizacion(Long idPlanCuentas) {
        getSessionManagedBean().setIdPlanCuentasParametrizacion(idPlanCuentas);
    }
    
    public Long getFromSessionIdPlanCuentasParametrizacion() {
        return getSessionManagedBean().getIdPlanCuentasParametrizacion();
    }
    
    public void setInSessionTipoCuentaParametrizacion(String tipoCuenta) {
        getSessionManagedBean().setTipoCuentaParametrizacion(tipoCuenta);
    }
    
    public String getFromSessionTipoCuentaParametrizacion() {
        return getSessionManagedBean().getTipoCuentaParametrizacion();
    }
    
    public String getFromSessionTipoRetencionOGrossing() {
        return getSessionManagedBean().getTipoRetencionOGrossing();
    }
    
    public void setInSessionTipoRetencionOGrossing(String tipoRetencionOGrossing) {
        getSessionManagedBean().setTipoRetencionOGrossing(tipoRetencionOGrossing);
    }
    
    public Long getFromSessionIdEntidadModificacionDetalle() {
        return getSessionManagedBean().getIdEntidadModificacionDetalle();
    }
    
    public void setInSessionIdEntidadModificacionDetalle(Long idEntidadModificacionDetalle) {
        getSessionManagedBean().setIdEntidadModificacionDetalle(idEntidadModificacionDetalle);
    }
    
    public void setInSessionIdEntidadBG(Long idEntidadBG) {
        getSessionManagedBean().setIdEntidadBG(idEntidadBG);
    }
    
    public Long getFromSessionIdEntidadBG() {
        return getSessionManagedBean().getIdEntidadBG();
    }
    
    public void setInSessionIdEntidadEERRR(Long idEntidadEERRR) {
        getSessionManagedBean().setIdEntidadEERRR(idEntidadEERRR);
    }
    
    public Long getFromSessionIdEntidadEERRR() {
        return getSessionManagedBean().getIdEntidadEERRR();
    }
    
    public void setInSessionIdAdmUsuario(Long idAdmUsuario) {
        getSessionManagedBean().setIdAdmUsuario(idAdmUsuario);
    }
    
    public Long getFromSessionIdAdmUsuario() {
        return getSessionManagedBean().getIdAdmUsuario();
    }
    
    public void setInSessionValorIndice(int valorIndice) {
        getSessionManagedBean().setValorIndice(valorIndice);
    }
    
    public int getFromSessionValorIndice() {
        return getSessionManagedBean().getValorIndice();
    }
    
    public void setInSessionFechaInicio(Date fechaInicio) {
        getSessionManagedBean().setFechaInicio(fechaInicio);
    }
    
    public Date getFromSessionFechaInicio() {
        return getSessionManagedBean().getFechaInicio();
    }
    
    public void setInSessionFechaFin(Date fechaFin) {
        getSessionManagedBean().setFechaFin(fechaFin);
    }
    
    public Date getFromSessionFechaFin() {
        return getSessionManagedBean().getFechaFin();
    }
    
    public void setInSessionNivel(int nivel) {
        getSessionManagedBean().setNivel(nivel);
    }
    
    public int getFromSessionNivel() {
        return getSessionManagedBean().getNivel();
    }
    
    public void setInSessionCeros(Boolean ceros) {
        getSessionManagedBean().setCeros(ceros);
    }
    
    public Boolean getFromSessionCeros() {
        return getSessionManagedBean().getCeros();
    }
    
    public void setInSessionTipoReporte(String tipoReporte) {
        getSessionManagedBean().setTipoReporte(tipoReporte);
    }
    
    public String getFromSessionTipoReporte() {
        return getSessionManagedBean().getTipoReporte();
    }
    
    public void setInSessionTipoMoneda(String tipoMoneda) {
        getSessionManagedBean().setTipoMoneda(tipoMoneda);
    }
    
    public String getFromSessionTipoMoneda() {
        return getSessionManagedBean().getTipoMoneda();
    }
    
    public void setInSessionIdEntidadFacturacion(Long idEntidadFacturacion) {
        getSessionManagedBean().setIdEntidadFacturacion(idEntidadFacturacion);
    }
    
    public Long getFromSessionIdEntidadFacturacion() {
        return getSessionManagedBean().getIdEntidadFacturacion();
    }
    
    public void setInSessionIdEntidadSumasySaldos(Long idEntidadSumasySaldos) {
        getSessionManagedBean().setIdEntidadSumasySaldos(idEntidadSumasySaldos);
    }
    
    public Long getFromSessionIdEntidadSumasySaldos() {
        return getSessionManagedBean().getIdEntidadSumasySaldos();
    }
    
}
