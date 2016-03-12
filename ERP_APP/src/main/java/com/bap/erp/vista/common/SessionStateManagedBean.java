package com.bap.erp.vista.common;

import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

@ManagedBean(name = "sessionState")
@SessionScoped
public class SessionStateManagedBean implements Serializable {

    private static Logger log = Logger.getLogger(SessionStateManagedBean.class);
    //Variable que almacena el login del usuario
    private String login;
    private String nombreUsuario;
    //Bandera que indica si el usuario esta autenticado
    private boolean authenticated;
    private final String pathTemplate = "/WEB-INF/facelets/templates/erpTemplate.xhtml";
    private Long idEntidad;
    private Long idEntidad3;
    private Long idEntidad2;
    private String stringAuxiliar;
    private String stringAuxiliar2;
    private String stringAuxiliar3;
    private String skinLayout = "customTheme";
    private boolean deleteable;
    private String dateFormat = "dd-MM-yyyy";
    private String dateTimeFormat = "dd-MM-yyyy hh:mm:ss";
    private Long idGestion = 1L;
//    private List<CntDetalleTx> cntDetalleTransaccionesListSession = new ArrayList<CntDetalleTx>();
//    private CntDetalleTx cntDetalleTx;
    private int intAuxiliar;
    private int intAuxiliar2;
    /*
     * creados para asig
     */
    private CntEntidad cntObjetos;
    private Long idEntidadSeleccion;
    private List<CntDetalleComprobante> cntDetalleComprobantesListSesion;
    private Long idComprobante;
    private Long idDetalleComprobante;
    private Long idCentroDeCosto;
    private Long idDetalleComprobanteModifica;
    private String tipoDeGrupoNivel;
    private Long idEntidadAsignacionCC;
    private String codigoParRecetasDistribucionCC;
    private Long idProyecto;
    private Long idDetalleComprobanteParaMayor;
    private Long idEntidadParaMayor;
    private Long idTipoProveedor;
    private Long idProveedor;
    private Long idComprobanteModifica;
    private String tipoUsuario;
    private Boolean activaDesactivaBotonesDetalle = false;
    private String scrollPos;
    private String valorBusqueda;
    private List<CntEntidad> listaDeCuentasFiltrada;
    private Long idPlanCuentasParametrizacion;
    private String tipoCuentaParametrizacion;
    private String tipoRetencionOGrossing;
    private Long idEntidadModificacionDetalle;
    private Long idEntidadBG;
    private Long idEntidadEERRR;
    private Long idAdmUsuario;
    private int valorIndice;
    private Date fechaInicio;
    private Date fechaFin;
    private int nivel;
    private Boolean ceros;
    private String tipoReporte;
    private String tipoMoneda;
    private Long idEntidadFacturacion;
    private Long idEntidadSumasySaldos;

    /**
     * Creates a new instance of SessionStateManagedBean
     */
    public SessionStateManagedBean() {
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @return the authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isDeleteable() {
        return deleteable;
    }

    public void setDeleteable(boolean deleteable) {
        this.deleteable = deleteable;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getPathTemplate() {
        return pathTemplate;
    }

    public String getSkinLayout() {
        return skinLayout;
    }

    public void setSkinLayout(String skinLayout) {
        this.skinLayout = skinLayout;
    }

    public String getVersion() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getInitParameter("VERSION");
    }

    public String getRelease() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getInitParameter("RELEASE");
    }

    /**
     * @return the dateFormat
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * @return the dateTimeFormat
     */
    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public boolean isAnyError() {
        if (FacesContext.getCurrentInstance().getMessageList().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the idEntidad3
     */
    public Long getIdEntidad3() {
        return idEntidad3;
    }

    /**
     * @param idEntidad3 the idEntidad3 to set
     */
    public void setIdEntidad3(Long idEntidad3) {
        this.idEntidad3 = idEntidad3;
    }

    /**
     * @return the idEntidad2
     */
    public Long getIdEntidad2() {
        return idEntidad2;
    }

    /**
     * @param idEntidad2 the idEntidad2 to set
     */
    public void setIdEntidad2(Long idEntidad2) {
        this.idEntidad2 = idEntidad2;
    }

    /**
     * @return the stringAuxiliar
     */
    public String getStringAuxiliar() {
        return stringAuxiliar;
    }

    /**
     * @param stringAuxiliar the stringAuxiliar to set
     */
    public void setStringAuxiliar(String stringAuxiliar) {
        this.stringAuxiliar = stringAuxiliar;
    }

    /**
     * @return the stringAuxiliar2
     */
    public String getStringAuxiliar2() {
        return stringAuxiliar2;
    }

    /**
     * @param stringAuxiliar2 the stringAuxiliar2 to set
     */
    public void setStringAuxiliar2(String stringAuxiliar2) {
        this.stringAuxiliar2 = stringAuxiliar2;
    }

    /**
     * @return the stringAuxiliar3
     */
    public String getStringAuxiliar3() {
        return stringAuxiliar3;
    }

    /**
     * @param stringAuxiliar3 the stringAuxiliar3 to set
     */
    public void setStringAuxiliar3(String stringAuxiliar3) {
        this.stringAuxiliar3 = stringAuxiliar3;
    }

    /**
     * @return the idGestion
     */
    public Long getIdGestion() {
        return idGestion;
    }

    /**
     * @param idGestion the idGestion to set
     */
    public void setIdGestion(Long idGestion) {
        this.idGestion = idGestion;
    }

    public CntEntidad getCntObjetos() {
        return cntObjetos;
    }

    public void setCntObjetos(CntEntidad cntObjetos) {
        this.cntObjetos = cntObjetos;
    }

    public int getIntAuxiliar() {
        return intAuxiliar;
    }

    public void setIntAuxiliar(int intAuxiliar) {
        this.intAuxiliar = intAuxiliar;
    }

    public int getIntAuxiliar2() {
        return intAuxiliar2;
    }

    public void setIntAuxiliar2(int intAuxiliar2) {
        this.intAuxiliar2 = intAuxiliar2;
    }

    public Long getIdEntidadSeleccion() {
        return idEntidadSeleccion;
    }

    public void setIdEntidadSeleccion(Long idEntidadSeleccion) {
        this.idEntidadSeleccion = idEntidadSeleccion;
    }

    public List<CntDetalleComprobante> getCntDetalleComprobantesListSesion() {
        return cntDetalleComprobantesListSesion;
    }

    public void setCntDetalleComprobantesListSesion(List<CntDetalleComprobante> cntDetalleComprobantesListSesion) {
        this.cntDetalleComprobantesListSesion = cntDetalleComprobantesListSesion;
    }

    public Long getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Long idComprobante) {
        this.idComprobante = idComprobante;
    }

    public Long getIdDetalleComprobante() {
        return idDetalleComprobante;
    }

    public void setIdDetalleComprobante(Long idDetalleComprobante) {
        this.idDetalleComprobante = idDetalleComprobante;
    }

    public Long getIdCentroDeCosto() {
        return idCentroDeCosto;
    }

    public void setIdCentroDeCosto(Long idCentroDeCosto) {
        this.idCentroDeCosto = idCentroDeCosto;
    }

    public Long getIdDetalleComprobanteModifica() {
        return idDetalleComprobanteModifica;
    }

    public void setIdDetalleComprobanteModifica(Long idDetalleComprobanteModifica) {
        this.idDetalleComprobanteModifica = idDetalleComprobanteModifica;
    }

    public String getTipoDeGrupoNivel() {
        return tipoDeGrupoNivel;
    }

    public void setTipoDeGrupoNivel(String tipoDeGrupoNivel) {
        this.tipoDeGrupoNivel = tipoDeGrupoNivel;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Long getIdEntidadAsignacionCC() {
        return idEntidadAsignacionCC;
    }

    public void setIdEntidadAsignacionCC(Long idEntidadAsignacionCC) {
        this.idEntidadAsignacionCC = idEntidadAsignacionCC;
    }

    public String getCodigoParRecetasDistribucionCC() {
        return codigoParRecetasDistribucionCC;
    }

    public void setCodigoParRecetasDistribucionCC(String codigoParRecetasDistribucionCC) {
        this.codigoParRecetasDistribucionCC = codigoParRecetasDistribucionCC;
    }

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Long getIdDetalleComprobanteParaMayor() {
        return idDetalleComprobanteParaMayor;
    }

    public void setIdDetalleComprobanteParaMayor(Long idDetalleComprobanteParaMayor) {
        this.idDetalleComprobanteParaMayor = idDetalleComprobanteParaMayor;
    }

    public Long getIdEntidadParaMayor() {
        return idEntidadParaMayor;
    }

    public void setIdEntidadParaMayor(Long idEntidadParaMayor) {
        this.idEntidadParaMayor = idEntidadParaMayor;
    }

    public Long getIdTipoProveedor() {
        return idTipoProveedor;
    }

    public void setIdTipoProveedor(Long idTipoProveedor) {
        this.idTipoProveedor = idTipoProveedor;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Long getIdComprobanteModifica() {
        return idComprobanteModifica;
    }

    public void setIdComprobanteModifica(Long idComprobanteModifica) {
        this.idComprobanteModifica = idComprobanteModifica;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        SessionStateManagedBean.log = log;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Boolean getActivaDesactivaBotonesDetalle() {
        return activaDesactivaBotonesDetalle;
    }

    public void setActivaDesactivaBotonesDetalle(Boolean activaDesactivaBotonesDetalle) {
        this.activaDesactivaBotonesDetalle = activaDesactivaBotonesDetalle;
    }

    public String getScrollPos() {
        return scrollPos;
    }

    public void setScrollPos(String scrollPos) {
        this.scrollPos = scrollPos;
    }

    public String getValorBusqueda() {
        return valorBusqueda;
    }

    public void setValorBusqueda(String valorBusqueda) {
        this.valorBusqueda = valorBusqueda;
    }

    public List<CntEntidad> getListaDeCuentasFiltrada() {
        return listaDeCuentasFiltrada;
    }

    public void setListaDeCuentasFiltrada(List<CntEntidad> listaDeCuentasFiltrada) {
        this.listaDeCuentasFiltrada = listaDeCuentasFiltrada;
    }

    public Long getIdPlanCuentasParametrizacion() {
        return idPlanCuentasParametrizacion;
    }

    public void setIdPlanCuentasParametrizacion(Long idPlanCuentasParametrizacion) {
        this.idPlanCuentasParametrizacion = idPlanCuentasParametrizacion;
    }

    public String getTipoCuentaParametrizacion() {
        return tipoCuentaParametrizacion;
    }

    public void setTipoCuentaParametrizacion(String tipoCuentaParametrizacion) {
        this.tipoCuentaParametrizacion = tipoCuentaParametrizacion;
    }

    public String getTipoRetencionOGrossing() {
        return tipoRetencionOGrossing;
    }

    public void setTipoRetencionOGrossing(String tipoRetencionOGrossing) {
        this.tipoRetencionOGrossing = tipoRetencionOGrossing;
    }

    public Long getIdEntidadModificacionDetalle() {
        return idEntidadModificacionDetalle;
    }

    public void setIdEntidadModificacionDetalle(Long idEntidadModificacionDetalle) {
        this.idEntidadModificacionDetalle = idEntidadModificacionDetalle;
    }

    public Long getIdEntidadEERRR() {
        return idEntidadEERRR;
    }

    public void setIdEntidadEERRR(Long idEntidadEERRR) {
        this.idEntidadEERRR = idEntidadEERRR;
    }

    public Long getIdEntidadBG() {
        return idEntidadBG;
    }

    public void setIdEntidadBG(Long idEntidadBG) {
        this.idEntidadBG = idEntidadBG;
    }

    public Long getIdAdmUsuario() {
        return idAdmUsuario;
    }

    public void setIdAdmUsuario(Long idAdmUsuario) {
        this.idAdmUsuario = idAdmUsuario;
    }

    public int getValorIndice() {
        return valorIndice;
    }

    public void setValorIndice(int valorIndice) {
        this.valorIndice = valorIndice;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Boolean getCeros() {
        return ceros;
    }

    public void setCeros(Boolean ceros) {
        this.ceros = ceros;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public Long getIdEntidadFacturacion() {
        return idEntidadFacturacion;
    }

    public void setIdEntidadFacturacion(Long idEntidadFacturacion) {
        this.idEntidadFacturacion = idEntidadFacturacion;
    }

    public Long getIdEntidadSumasySaldos() {
        return idEntidadSumasySaldos;
    }

    public void setIdEntidadSumasySaldos(Long idEntidadSumasySaldos) {
        this.idEntidadSumasySaldos = idEntidadSumasySaldos;
    }
}
