package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.enums.EnumEstado;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.enums.EnumTipoComprobante;
import com.bap.erp.modelo.enums.EnumTipoModulo;
import com.bap.erp.modelo.enums.EnumTipoMoneda;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author Jonas
 */
@ManagedBean(name = "cntComprobantesBacking")
@ViewScoped
public class CntComprobantesBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntTipoCambioService}")
    private CntTipoCambioService cntTipoCambioService;
    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;
    private List<CntComprobante> listaDeComprobantes;
    private CntComprobante cntComprobanteElegido;
    private List<CntDetalleComprobante> listaDeCuentasPorComprobante;
    private CntDetalleComprobante cntDetalleComprobanteElegido;
    private Boolean muestraMensajeSiExistePendientes;
    private Boolean activaBotonCopiarComprobanteConfirmado = true;
    private Boolean activaBotonRevertirComprobanteConfirmado = true;
    private String cambiaNombreBoton;
    private Boolean activaBotonAnularComprobante;
    private Boolean activaBotonModifica;
    private Boolean activaBotonElimina;
    private String colorEstado;
    private List<CntDetalleComprobante> listaDeDetallesElegidos = new ArrayList<CntDetalleComprobante>();
    private CntDetalleComprobante cntDetalleComprobanteDeMayor;
    private List<CntComprobante> listaComprobantesFiltrados;
    private String filtroDescripcion;

    //Variables busqueda
    private String tipoBuqueda = "PAL";
    private String palabraBuqueda = "";

    private boolean loadedDetalle = false;

    public CntComprobantesBacking() {
    }

    @PostConstruct
    void initCntComprobantesBacking() {

        try {
            if (getFromSessionIdDetalleComprobanteParaMayor() != null) {
                cntDetalleComprobanteDeMayor = cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobanteParaMayor());
                cntComprobanteElegido = cntDetalleComprobanteDeMayor.getCntComprobante();
                listaDeDetallesElegidos.add(cntDetalleComprobanteDeMayor);
                cntDetalleComprobanteElegido = cntDetalleComprobanteDeMayor;
                setInSessionIdDetalleComprobanteParaMayor(null);
            }
            if (getFromSessionIdComprobante() != null) {
                cntComprobanteElegido = (CntComprobante) cntComprobantesService.find(CntComprobante.class, getFromSessionIdComprobante());
            } else {
                cntComprobantesService.removeComprobantePendientesSinDetalleComprobante();
            }
            muestraMensajeSiExistePendientes = cntComprobantesService.verificaExistenciaDePendientes();
            cambiaNombreBoton = "Modificar";
            activaBotonAnularComprobante = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<CntComprobante> cntComprobantesList() throws Exception {
        if (listaDeComprobantes == null || listaDeComprobantes.isEmpty()) {
            listaDeComprobantes = cntComprobantesService.listaCntComprobantes();
        }
        return listaDeComprobantes;
    }

    public List<CntDetalleComprobante> generaCuentasPorComprobante() {
        try {
            if (cntComprobanteElegido != null && loadedDetalle == false) {
                listaDeCuentasPorComprobante = cntDetalleComprobanteService.ordenaSegunPosicion(cntComprobanteElegido);
                loadedDetalle = true;
            }
            return listaDeCuentasPorComprobante;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.EMPTY_LIST;
    }

    public String cambiaColorEstado(String estado) {
        if (estado.equals(EnumEstado.PENDIENTE.getCodigo())) {
            colorEstado = "#E80B21";
        }
        if (estado.equals(EnumEstado.MODIFICANDO.getCodigo())) {
            colorEstado = "#E80B21";
        }
        if (estado.equals(EnumEstado.PROCESO_ANULACION.getCodigo())) {
            colorEstado = "#E80B21";
        }
        if (estado.equals(EnumEstado.ANULADO.getCodigo())) {
            colorEstado = "#9FA0A9";
        }
        if (estado.equals(EnumEstado.CONFIRMADO.getCodigo())) {
            colorEstado = "#01FB72";
        }
        if (estado.equals(EnumEstado.REVERTIDO.getCodigo())) {
            colorEstado = "#E80B21";
        }
        if (estado.equals(EnumEstado.COPIANDO.getCodigo())) {
            colorEstado = "#E80B21";
        }

        return colorEstado;
    }

    public String cambiaColorEstadoCC(String estado) {
        if (estado.equals(EnumEstado.CONFIRMADO.getCodigo())) {
            colorEstado = "#009999";
        } else {
            if (estado.equals(EnumEstado.ANULADO.getCodigo())) {
                colorEstado = "#D0332E";
            } else {
                colorEstado = "#E1E463";
            }
        }
        return colorEstado;
    }

    public Boolean cambiaColorPendientes(String estado) {

        if (estado.equals(EnumEstado.PENDIENTE.getCodigo())) {
            return true;
        }
        return false;

    }

    public Boolean cambiaColorModificados(String estado) {

        if (estado.equals(EnumEstado.MODIFICANDO.getCodigo())) {
            return true;
        }
        return false;

    }

    public Boolean cambiaColorProcesoAnulado(String estado) {
        if (estado.equals(EnumEstado.PROCESO_ANULACION.getCodigo())) {
            return true;
        }
        return false;
    }

    public Boolean cambiaColorAnulado(String estado) {
        if (estado.equals(EnumEstado.ANULADO.getCodigo())) {
            return true;
        }
        return false;
    }

    public String adicionarComprobante() {
        CntComprobante cntComprobanteNuevo = new CntComprobante();
//        if (cntTipoCambioService.devuelveCntTipoDeCambio(new Date()) != null && cntTipoCambioService.devuelveCntTipoDeCambio(new Date()).getFechaModificacion() != null) {
        try {
            cntComprobanteNuevo.setTipoCambio(cntTipoCambioService.ultimoCntTipoCambioRegistrado().getTipoCambio());
            cntComprobanteNuevo.setLoginUsuario(getLoginSession());
            cntComprobanteNuevo.setDescripcion("");
            cntComprobanteNuevo.setEstado(EnumEstado.PENDIENTE.getCodigo());
            cntComprobanteNuevo.setFecha(cntTipoCambioService.ultimoCntTipoCambioRegistrado().getFecha());
            cntComprobanteNuevo.setGlosaComprobante("");
            cntComprobanteNuevo.setTipoMoneda(EnumTipoMoneda.AMBOS.getCodigo());
            cntComprobanteNuevo.setNumero(0L);
            cntComprobanteNuevo.setParTipoComprobante((ParTipoComprobante) parParametricasService.find(ParTipoComprobante.class, EnumTipoComprobante.EGRESO.getCodigo()));
            cntComprobanteNuevo.setPeriodo("");
            cntComprobanteNuevo.setTotalComprobantes(new BigDecimal("0"));
            cntComprobanteNuevo.setTotalComprobantesSegMoneda(new BigDecimal("0"));
            cntComprobanteNuevo.setModulo(EnumTipoModulo.CONTABILIDAD.getCodigo());
            setPersistValues(cntComprobanteNuevo);
            cntComprobanteNuevo = cntComprobantesService.persistCntComprobantes(cntComprobanteNuevo);
            setInSessionIdComprobante(cntComprobanteNuevo.getIdComprobante());
            setInSessionIdComprobanteModifica(null);
            if (cntTipoCambioService.devuelveCntTipoDeCambio(new Date()) == null || cntTipoCambioService.devuelveCntTipoDeCambio(new Date()).getFechaModificacion() == null) {
                MessageUtils.addErrorMessage("Advertencia, el registro de tipos de cambio no esta actualizado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        } else {
//            MessageUtils.addErrorMessage("Debe registrar el tipo de cambio de hoy.");
//            return null;
//        }
//        return "formularioComprobante";
        return "detalleComprobante";
    }

    public String adicionarDetalleComprobante(String tipoComprobante) {
        CntComprobante cntComprobanteNuevo = new CntComprobante();
        cntComprobanteNuevo.setTipoCambio(cntTipoCambioService.ultimoRegistroCntTipoCambio().getTipoCambio());
        cntComprobanteNuevo.setLoginUsuario(getLoginSession());
        cntComprobanteNuevo.setDescripcion("");
        cntComprobanteNuevo.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntComprobanteNuevo.setFecha(cntTipoCambioService.ultimoRegistroCntTipoCambio().getFecha());
        cntComprobanteNuevo.setGlosaComprobante("");
        cntComprobanteNuevo.setTipoMoneda(EnumTipoMoneda.AMBOS.getCodigo());
        cntComprobanteNuevo.setNumero(0L);
        cntComprobanteNuevo.setParTipoComprobante((ParTipoComprobante) parParametricasService.find(ParTipoComprobante.class, EnumTipoComprobante.EGRESO.getCodigo()));
        cntComprobanteNuevo.setPeriodo("");
        cntComprobanteNuevo.setTotalComprobantes(new BigDecimal("0"));
        cntComprobanteNuevo.setTotalComprobantesSegMoneda(new BigDecimal("0"));
        cntComprobanteNuevo.setModulo(EnumTipoModulo.CONTABILIDAD.getCodigo());
        cntComprobanteNuevo.getParTipoComprobante().setCodigo(tipoComprobante);
        setPersistValues(cntComprobanteNuevo);
        try {
            cntComprobanteNuevo = cntComprobantesService.persistCntComprobantes(cntComprobanteNuevo);
            setInSessionIdComprobante(cntComprobanteNuevo.getIdComprobante());
            setInSessionIdComprobanteModifica(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cntTipoCambioService.devuelveCntTipoDeCambio(new Date()) == null || cntTipoCambioService.devuelveCntTipoDeCambio(new Date()).getFechaModificacion() == null) {
            MessageUtils.addErrorMessage("Advertencia, el registro de tipos de cambio no esta actualizado");
        }
        return "detalleComprobante";
    }

    public String modificarComprobante() {

        if (cntComprobanteElegido != null) {
            setInSessionIdComprobante(cntComprobanteElegido.getIdComprobante());
            setInSessionIdComprobanteModifica(cntComprobanteElegido.getIdComprobante());
            setInSessionIdDetalleComprobante(null);
            cntDetalleComprobanteService.guardaPosicionesAnteriores(cntComprobanteElegido);
            if (cambiaNombreBoton.equals("MODIFICAR")) {
                //ver si el comprobante tiene cuentas de ajuste monetario y diferencia de tipo de cmabio
                if (cntDetalleComprobanteService.verificaCuentaDiferenciaDeCambio(cntComprobanteElegido)) {
                    //buscar el comprobante con entidad diferencia de tipo de cambio
                    CntDetalleComprobante cntDetalleComprobanteDifTipoCambio = cntDetalleComprobanteService.buscaDiferenciaTipoCambio(cntComprobanteElegido);
                    CntDetalleComprobante cntDetalleComprobanteModificadoDifTipoCambio;
                    cntDetalleComprobanteModificadoDifTipoCambio = (CntDetalleComprobante) cntDetalleComprobanteDifTipoCambio.clone();
                    cntDetalleComprobanteModificadoDifTipoCambio.setIdDetalleComprobante(null);
                    cntDetalleComprobanteModificadoDifTipoCambio.setIdAntecesor(cntDetalleComprobanteDifTipoCambio.getIdDetalleComprobante());
                    cntDetalleComprobanteModificadoDifTipoCambio.setDebeDolar(null);
                    cntDetalleComprobanteModificadoDifTipoCambio.setHaberDolar(null);
                    cntDetalleComprobanteModificadoDifTipoCambio.setEstado("PEND");
                    cntDetalleComprobanteModificadoDifTipoCambio.setPosicion(0L);
                    setPersistValues(cntDetalleComprobanteModificadoDifTipoCambio);
                    setRemoveValues(cntDetalleComprobanteDifTipoCambio);
                    try {
                        cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobanteModificadoDifTipoCambio);
                        cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobanteDifTipoCambio);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            if (cntTipoCambioService.devuelveCntTipoDeCambio(new Date()) == null) {
                MessageUtils.addErrorMessage("Advertencia, el registro de tipos de cambio no esta actualizado");
            }
            return "detalleComprobante";
        }
        MessageUtils.addErrorMessage("Debe Seleccionar un Comprobante para realizar la modificacion");
        return "comprobantesList";
    }

    public String revertirComprobante() {
        if (cntTipoCambioService.devuelveCntTipoDeCambio(new Date()) != null) {
            if (cntComprobanteElegido != null) {
                CntComprobante cntComprobanteNuevo;
                try {
                    if (cntComprobanteElegido.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo())) {
                        cntComprobanteNuevo = cntComprobantesService.persistTemporalParaRevertirComprobante(cntComprobanteElegido, getLoginSession());
                        setInSessionIdComprobante(cntComprobanteNuevo.getIdComprobante());
                    } else {
                        setInSessionIdComprobante(cntComprobanteElegido.getIdComprobante());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "formularioComprobante";
            }
            MessageUtils.addErrorMessage("Debe Seleccionar un Comprobante con estado Confirmado para Revertir");
            return "comprobantesList";

        } else {
            MessageUtils.addErrorMessage("Debe registrar el tipo de cambio de hoy.");
            return null;
        }
    }

    public String copiaComprobante() {
        if (cntTipoCambioService.devuelveCntTipoDeCambio(new Date()) != null) {
            if (cntComprobanteElegido != null) {
                CntComprobante cntComprobanteNuevo;
                try {
                    if (cntComprobanteElegido.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo())) {
                        cntComprobanteNuevo = cntComprobantesService.persistTemporalParaCopia(cntComprobanteElegido, getLoginSession());
                        setInSessionIdComprobante(cntComprobanteNuevo.getIdComprobante());
                    } else {
                        setInSessionIdComprobante(cntComprobanteElegido.getIdComprobante());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "detalleComprobante";
            }
            MessageUtils.addErrorMessage("Debe seleccionar un Comprobante para realizar la copia");
            return "comprobantesList";
        } else {
            MessageUtils.addErrorMessage("Debe registrar el tipo de cambio de hoy.");
            return null;
        }
    }

    public String anularComprobante() {
        System.out.println("entro a ANULACION:::::::");
//        if (cntTipoCambioService.devuelveCntTipoDeCambio(new Date()) != null) {
            if (cntComprobanteElegido != null) {
                if (cntComprobanteElegido.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo()) || cntComprobanteElegido.getEstado().equals(EnumEstado.PROCESO_ANULACION.getCodigo())) {
                    cntComprobanteElegido.setModulo(EnumTipoModulo.CONTABILIDAD.getCodigo());
                    //Esta opcion se la comento para tener la opcion de seguir trabajar con las transacciones
//                    cntComprobanteElegido.setEstado(EnumEstado.PROCESO_ANULACION.getCodigo());
                    setMergeValues(cntComprobanteElegido);
                    try {
                        cntComprobantesService.mergeCntComprobantes(cntComprobanteElegido);
                        setInSessionIdComprobante(cntComprobanteElegido.getIdComprobante());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Esta opcion se la comento para tener la opcion de seguir trabajar con las transacciones
//                    return "formularioComprobante";
                    return "detalleComprobanteAnulacion";
                }
            }
            MessageUtils.addErrorMessage("Debe Seleccionar un Comprobante para realizar la modificacion");
            return "comprobantesList";
//        } else {
//            MessageUtils.addErrorMessage("Debe registrar el tipo de cambio de hoy.");
//            return null;
//        }
    }

    public String eliminarComprobante() {
        if (cntComprobanteElegido != null) {
            setInSessionIdComprobante(cntComprobanteElegido.getIdComprobante());
            return "formularioComprobanteElimina";
        } else {
            MessageUtils.addErrorMessage("Debe Seleccionar un Comprobante para realizar la eliminacion");
            return "comprobantesList";
        }

    }

    public void cambiaNombreBotonModificarSoloPendientesCompletar(ValueChangeEvent e) {
        try {
            loadedDetalle = false;
            if (cntComprobanteElegido.getEstado().equals(EnumEstado.PENDIENTE.getCodigo()) || cntComprobanteElegido.getEstado().equals(EnumEstado.REVERTIDO.getCodigo())) {
                cambiaNombreBoton = "Completar";
            } else {
                cambiaNombreBoton = "Modificar";
                //Nombre:removeDetalleComprobantePendienteSinFactura, metodo que elimina detalle comprobante de tipo factura que no tiene relacion con la tabla facturacion.
                cntComprobantesService.removeDetalleComprobantePendienteSinFactura(cntComprobanteElegido);
            }
            activaBotonAnularComprobante = cntComprobanteElegido.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo()) ? false : !cntComprobanteElegido.getEstado().equals(EnumEstado.PROCESO_ANULACION.getCodigo());
            activaBotonModifica = cntComprobanteElegido.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo()) ? true : cntComprobanteElegido.getEstado().equals(EnumEstado.ANULADO.getCodigo()) ? true : cntComprobanteElegido.getEstado().equals(EnumEstado.PROCESO_ANULACION.getCodigo());
            activaBotonElimina = cntComprobanteElegido.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo()) ? true : cntComprobanteElegido.getEstado().equals(EnumEstado.ANULADO.getCodigo()) ? true : cntComprobanteElegido.getEstado().equals(EnumEstado.PROCESO_ANULACION.getCodigo());
            if (cntComprobanteElegido.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo()) && cntComprobanteElegido.getModulo().equals(EnumTipoModulo.CONTABILIDAD.getCodigo())) {
                activaBotonCopiarComprobanteConfirmado = false;
                activaBotonAnularComprobante = false;
                activaBotonModifica = false;
                activaBotonRevertirComprobanteConfirmado = cntComprobantesService.verificaComprobanteConFactura(cntComprobanteElegido);
            } else {
                activaBotonCopiarComprobanteConfirmado = true;
                activaBotonAnularComprobante = true;
            }
            if (!activaBotonModifica && (cntComprobanteElegido.getEstado().equals(EnumEstado.CONFIRMADO.getCodigo()) || cntComprobanteElegido.getEstado().equals(EnumEstado.REVERTIDO.getCodigo()))) {
                activaBotonModifica = !parParametricasService.verificaPeriodoYAnioActual(cntComprobanteElegido.getFecha());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String verMayorDetalleElegido() {
        if (listaDeDetallesElegidos.isEmpty()) {
            MessageUtils.addErrorMessage("Debe Seleccionar una Transaccion para ver su Mayor de esa cuenta");
            return null;
        } else {
            setInSessionIdDetalleComprobanteParaMayor(listaDeDetallesElegidos.get(0).getIdDetalleComprobante());
//            return "libroMayor";
            return "libro_Mayor";
        }
    }

    public Boolean cuentaAuxiliar(BigDecimal debe, BigDecimal haber) {

        if (debe.compareTo(BigDecimal.ZERO) == 0 && haber.compareTo(BigDecimal.ZERO) == 0) {
            return false;
        }
        return true;
    }

    public void cargaListaFiltrada() throws Exception {
        listaComprobantesFiltrados = cntComprobantesService.listaCntComprobantes();
    }

    public void realizaBusqueda() {
        List<CntComprobante> lista;
        if (tipoBuqueda.equals("MON")) {
            BigDecimal monto = new BigDecimal(palabraBuqueda);
            lista = cntComprobantesService.listaComprobantesSegunMonto(monto, cntComprobanteElegido);
        } else {
            lista = cntComprobantesService.listaComprobantesPorPalabra(palabraBuqueda, cntComprobanteElegido);
        }
    }

    public String iconoRegistra() {
        return EnumIconosPrimeFaces.GUARDAR.getCodigo();
    }

    public String iconoEdita() {
        return EnumIconosPrimeFaces.MODIFICAR.getCodigo();
    }

    public String iconoElimina() {
        return EnumIconosPrimeFaces.ELIMINAR.getCodigo();
    }

    public String iconoAtras() {
        return EnumIconosPrimeFaces.ATRAS.getCodigo();
    }

    public CntComprobantesService getCntComprobantesService() {
        return cntComprobantesService;
    }

    public void setCntComprobantesService(CntComprobantesService cntComprobantesService) {
        this.cntComprobantesService = cntComprobantesService;
    }

    public List<CntComprobante> getListaDeComprobantes() {
        return listaDeComprobantes;
    }

    public void setListaDeComprobantes(List<CntComprobante> listaDeComprobantes) {
        this.listaDeComprobantes = listaDeComprobantes;
    }

    public CntComprobante getCntComprobanteElegido() {
        return cntComprobanteElegido;
    }

    public void setCntComprobanteElegido(CntComprobante cntComprobanteElegido) {
        this.cntComprobanteElegido = cntComprobanteElegido;
    }

    public List<CntDetalleComprobante> getListaDeCuentasPorComprobante() {
        return listaDeCuentasPorComprobante;
    }

    public void setListaDeCuentasPorComprobante(List<CntDetalleComprobante> listaDeCuentasPorComprobante) {
        this.listaDeCuentasPorComprobante = listaDeCuentasPorComprobante;
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public CntDetalleComprobante getCntDetalleComprobanteElegido() {
        if (listaDeDetallesElegidos != null && !listaDeDetallesElegidos.isEmpty() && listaDeDetallesElegidos.size() == 1) {
            cntDetalleComprobanteElegido = listaDeDetallesElegidos.get(0);
        } else {
            cntDetalleComprobanteElegido = new CntDetalleComprobante();
        }
        return cntDetalleComprobanteElegido;
    }

    public void setCntDetalleComprobanteElegido(CntDetalleComprobante cntDetalleComprobanteElegido) {
        this.cntDetalleComprobanteElegido = cntDetalleComprobanteElegido;
    }

    public CntTipoCambioService getCntTipoCambioService() {
        return cntTipoCambioService;
    }

    public void setCntTipoCambioService(CntTipoCambioService cntTipoCambioService) {
        this.cntTipoCambioService = cntTipoCambioService;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }

    public Boolean getMuestraMensajeSiExistePendientes() {
        return muestraMensajeSiExistePendientes;
    }

    public void setMuestraMensajeSiExistePendientes(Boolean muestraMensajeSiExistePendientes) {
        this.muestraMensajeSiExistePendientes = muestraMensajeSiExistePendientes;
    }

    public String getCambiaNombreBoton() {
        return cambiaNombreBoton;
    }

    public void setCambiaNombreBoton(String cambiaNombreBoton) {
        this.cambiaNombreBoton = cambiaNombreBoton;
    }

    public Boolean getActivaBotonAnularComprobante() {
        return activaBotonAnularComprobante;
    }

    public void setActivaBotonAnularComprobante(Boolean activaBotonAnularComprobante) {
        this.activaBotonAnularComprobante = activaBotonAnularComprobante;
    }

    public Boolean getActivaBotonCopiarComprobanteConfirmado() {
        return activaBotonCopiarComprobanteConfirmado;
    }

    public void setActivaBotonCopiarComprobanteConfirmado(Boolean activaBotonCopiarComprobanteConfirmado) {
        this.activaBotonCopiarComprobanteConfirmado = activaBotonCopiarComprobanteConfirmado;
    }

    public Boolean getActivaBotonModifica() {
        return activaBotonModifica;
    }

    public void setActivaBotonModifica(Boolean activaBotonModifica) {
        this.activaBotonModifica = activaBotonModifica;
    }

    public Boolean getActivaBotonElimina() {
        return activaBotonElimina;
    }

    public void setActivaBotonElimina(Boolean activaBotonElimina) {
        this.activaBotonElimina = activaBotonElimina;
    }

    public Boolean getActivaBotonRevertirComprobanteConfirmado() {
        return activaBotonRevertirComprobanteConfirmado;
    }

    public void setActivaBotonRevertirComprobanteConfirmado(Boolean activaBotonRevertirComprobanteConfirmado) {
        this.activaBotonRevertirComprobanteConfirmado = activaBotonRevertirComprobanteConfirmado;
    }

    public String getColorEstado() {
        return colorEstado;
    }

    public void setColorEstado(String colorEstado) {
        this.colorEstado = colorEstado;
    }

    public List<CntDetalleComprobante> getListaDeDetallesElegidos() {
        return listaDeDetallesElegidos;
    }

    public void setListaDeDetallesElegidos(List<CntDetalleComprobante> listaDeDetallesElegidos) {
        this.listaDeDetallesElegidos = listaDeDetallesElegidos;
    }

    public CntDetalleComprobante getCntDetalleComprobanteDeMayor() {
        return cntDetalleComprobanteDeMayor;
    }

    public void setCntDetalleComprobanteDeMayor(CntDetalleComprobante cntDetalleComprobanteDeMayor) {
        this.cntDetalleComprobanteDeMayor = cntDetalleComprobanteDeMayor;
    }

    public String muestraNumeroComprobante(CntComprobante cntComprobante) {
        return cntComprobante.getDescripcion().isEmpty() ? "-" : cntComprobante.getNumero().toString();
    }

    public List<CntComprobante> getListaComprobantesFiltrados() {
        return listaComprobantesFiltrados;
    }

    public void setListaComprobantesFiltrados(List<CntComprobante> listaComprobantesFiltrados) {
        this.listaComprobantesFiltrados = listaComprobantesFiltrados;
    }

    public String getFiltroDescripcion() {
        return filtroDescripcion;
    }

    public void setFiltroDescripcion(String filtroDescripcion) {
        this.filtroDescripcion = filtroDescripcion;
    }

    public String getTipoBuqueda() {
        return tipoBuqueda;
    }

    public void setTipoBuqueda(String tipoBuqueda) {
        this.tipoBuqueda = tipoBuqueda;
    }

    public String getPalabraBuqueda() {
        return palabraBuqueda;
    }

    public void setPalabraBuqueda(String palabraBuqueda) {
        this.palabraBuqueda = palabraBuqueda;
    }

    /**
     * @return the loadedDetalle
     */
    public boolean isLoadedDetalle() {
        return loadedDetalle;
    }

    /**
     * @param loadedDetalle the loadedDetalle to set
     */
    public void setLoadedDetalle(boolean loadedDetalle) {
        this.loadedDetalle = loadedDetalle;
    }

}
