package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico;
import com.bap.erp.modelo.enums.EnumAjuste;
import com.bap.erp.modelo.enums.EnumEstado;
import com.bap.erp.modelo.enums.EnumIconosPrimeFaces;
import com.bap.erp.modelo.enums.EnumParametroAutomatico;
import com.bap.erp.modelo.enums.EnumTipoAjuste;
import com.bap.erp.modelo.enums.EnumTipoMovimiento;
import com.bap.erp.modelo.enums.EnumTipoRetencion;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntParametroAutomaticoService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.iknow.utils.ObjectUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "cntFormularioDetalleComprobanteBacking")
@ViewScoped
public class CntFormularioDetalleComprobanteBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{cntEntidadesService}")
    private CntEntidadesService cntEntidadesService;
    @ManagedProperty(value = "#{cntDetalleComprobanteService}")
    private CntDetalleComprobanteService cntDetalleComprobanteService;
    @ManagedProperty(value = "#{cntComprobantesService}")
    private CntComprobantesService cntComprobantesService;
    @ManagedProperty(value = "#{cntParametroAutomaticoService}")
    private CntParametroAutomaticoService cntParametroAutomaticoService;
    private CntEntidad cntEntidad;
    private CntDetalleComprobante cntDetalleComprobante;
    private CntComprobante cntComprobante;
    private Boolean activaBotonGuardar;
    private Boolean swDebe;
    private Boolean swHaber;
    private Boolean pulsoSwDebe;
    private Boolean pulsoSwHaber;
    private Boolean activaFacturaDeCompra;
    private Boolean activaFacturaDeVenta;
    private Boolean activaSinFactura;
    private Boolean activaRetencion;
    private String nombreRetencion;
    private Boolean activaRetencionGrossingUp;
    private String nombreRetencionGrossingUp;
    private Boolean activaCreditoFiscalNoDeducible;
    private Boolean activaAjuste;
    private String nombreAjuste;

    public CntFormularioDetalleComprobanteBacking() {
    }

    @PostConstruct
    void initCntFormularioDetalleComprobanteBacking() {
        if (getFromSessionIdDetalleComprobante() != null) {
            cntDetalleComprobante = (CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, getFromSessionIdDetalleComprobante());
            cntEntidad = cntEntidadesService.find(CntEntidad.class, cntDetalleComprobante.getCntEntidad().getIdEntidad());
            CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
            swDebe = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.DEBE.getCodigo());
            swHaber = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.HABER.getCodigo());
        } else {
            cntDetalleComprobante = new CntDetalleComprobante();
        }
        if (getFromSessionIdComprobante() != null) {
            cntComprobante = (CntComprobante) cntComprobantesService.find(CntComprobante.class, getFromSessionIdComprobante());
        }
        if (getFromSessionIdEntidadSeleccion() != null) {
            cntEntidad = cntEntidadesService.find(CntEntidad.class, getFromSessionIdEntidadSeleccion());
            CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
            swDebe = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.DEBE.getCodigo());
            swHaber = pa.getTipoMovimiento().equals(EnumTipoMovimiento.AMBOS.getCodigo()) ? true : pa.getTipoMovimiento().equals(EnumTipoMovimiento.HABER.getCodigo());
            setInSessionIdEntidadSeleccion(null);
        }
        activaBotonGuardar = true;
    }

    public void bloqueaOpcionDebe(ValueChangeEvent e) throws Exception {
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaFacturaCompra();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaFacturaVenta();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaSinFactura();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaRetenciones();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaGrossingUp();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaCreditoFiscalNoDeducible();
        pulsoSwDebe = true;
        pulsoSwHaber = false;
        activaAjuste();

        if (cntDetalleComprobante.getDebe() != null) {
            swHaber = false;
            activaBotonGuardar = false;
            cntDetalleComprobante.setHaber(null);
        } else {
            swHaber = true;
            activaBotonGuardar = true;
            cntDetalleComprobante.setHaber(null);
        }
        swDebe = true;

    }

    public void bloqueaOpcionHaber(ValueChangeEvent e) throws Exception {
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaFacturaCompra();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaFacturaVenta();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaSinFactura();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaRetenciones();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaGrossingUp();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaCreditoFiscalNoDeducible();
        pulsoSwDebe = false;
        pulsoSwHaber = true;
        activaAjuste();
        if (cntDetalleComprobante.getHaber() != null) {
            swDebe = false;
            activaBotonGuardar = false;
            cntDetalleComprobante.setDebe(null);
        } else {
            swDebe = true;
            activaBotonGuardar = true;
            cntDetalleComprobante.setDebe(null);
        }
        swHaber = true;

    }

    public void activaFacturaCompra() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getFacturaCompra().equals(EnumParametroAutomatico.DEBE.getCodigo())) {
                activaFacturaDeCompra = true;
            } else {
                activaFacturaDeCompra = false;
            }
        } else {
            if (pulsoSwHaber) {
                if (pa.getFacturaCompra().equals(EnumParametroAutomatico.HABER.getCodigo())) {
                    activaFacturaDeCompra = true;
                } else {
                    activaFacturaDeCompra = false;
                }
            }
        }
    }

    public void activaCreditoFiscalNoDeducible() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getCreditoFiscalNoDeducible().equals(EnumParametroAutomatico.DEBE.getCodigo())) {
                activaCreditoFiscalNoDeducible = true;
                // tipoMovimiento = pa.getTipoMovimiento();                
            } else {
                activaCreditoFiscalNoDeducible = false;
            }
        } else {
            if (pulsoSwHaber) {
                if (pa.getCreditoFiscalNoDeducible().equals(EnumParametroAutomatico.HABER.getCodigo())) {
                    activaCreditoFiscalNoDeducible = true;
                } else {
                    activaCreditoFiscalNoDeducible = false;
                }
            }
        }
    }

    public void activaFacturaVenta() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getFacturaVenta().equals(EnumParametroAutomatico.DEBE.getCodigo())) {
                activaFacturaDeVenta = true;
            } else {
                activaFacturaDeVenta = false;
            }
        } else {
            if (pulsoSwHaber) {
                if (pa.getFacturaVenta().equals(EnumParametroAutomatico.HABER.getCodigo())) {
                    activaFacturaDeVenta = true;
                } else {
                    activaFacturaDeVenta = false;
                }
            }
        }
    }

    public void activaSinFactura() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getSinFactura().equals(EnumParametroAutomatico.NINGUNO.getCodigo())) {
                activaSinFactura = false;
            } else {
                activaSinFactura = true;
            }
        } else {
            if (pulsoSwHaber) {
                if (pa.getSinFactura().equals(EnumParametroAutomatico.NINGUNO.getCodigo())) {
                    activaSinFactura = false;
                } else {
                    activaSinFactura = true;
                }
            }
        }
    }

    public void activaRetenciones() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            activaRetencion = false;
        } else {
            if (pulsoSwHaber) {
//                if (pa.getParTipoRetencion().getCodigo().equals(EnumTipoRetencion.SIN_RETENCION.getCodigo())) {
//                    activaRetencion = false;
//                } else {
//                    activaRetencion = true;
//                    nombreRetencion = pa.getParTipoRetencion().getDescripcion();
//                }
            }
        }
    }

    public void activaGrossingUp() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getParTipoRetencionGrossingUp().getCodigo().equals(EnumTipoRetencion.SIN_RETENCION.getCodigo())) {
                activaRetencionGrossingUp = false;
            } else {
                activaRetencionGrossingUp = true;
//                nombreRetencionGrossingUp = pa.getParTipoRetencion().getDescripcion();
            }
        } else {
            if (pulsoSwHaber) {
                activaRetencionGrossingUp = false;
            }
        }
    }

    public void activaAjuste() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
        if (pulsoSwDebe) {
            if (pa.getParAjustes().getCodigo().equals(EnumAjuste.SIN_AJUSTE.getCodigo())) {
                activaAjuste = false;
            } else {
                activaAjuste = true;
                nombreAjuste = pa.getParAjustes().getDescripcion();
            }
        } else {
            if (pulsoSwHaber) {
                if (pa.getParAjustes().getCodigo().equals(EnumAjuste.SIN_AJUSTE.getCodigo())) {
                    activaAjuste = false;
                } else {
                    activaAjuste = true;
                    nombreAjuste = pa.getParAjustes().getDescripcion();
                }
            }
        }
    }

//    public String irRetencionActivada() throws Exception {
//        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);
//
//        if (pa.getParTipoRetencion().getCodigo().equals(EnumTipoRetencion.BIENES.getCodigo())) {
//            return "";
//        } else {
//            if (pa.getParTipoRetencion().getCodigo().equals(EnumTipoRetencion.SERVICIOS.getCodigo())) {
//                return "";
//            } else {
//                if (pa.getParTipoRetencion().getCodigo().equals(EnumTipoRetencion.BIENES.getCodigo())) {
//                    return "";
//                } else {
//                    if (pa.getParTipoRetencion().getCodigo().equals(EnumTipoRetencion.RC_IVA.getCodigo())) {
//                        return "";
//                    } else {
//                        if (pa.getParTipoRetencion().getCodigo().equals(EnumTipoRetencion.ALQUILERES.getCodigo())) {
//                            return "";
//                        } else {
//                            if (pa.getParTipoRetencion().getCodigo().equals(EnumTipoRetencion.REMESAS_AL_EXTERIOR.getCodigo())) {
//                                return "";
//                            } else {
//                                if (pa.getParTipoRetencion().getCodigo().equals(EnumTipoRetencion.IUE_IT_IND_EXPORTADOR.getCodigo())) {
//                                    return "";
//                                } else {
//                                    return null;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
    public String irRetencionActivadaGrossingUp() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);

        if (pa.getParTipoRetencionGrossingUp().getCodigo().equals(EnumTipoRetencion.BIENES.getCodigo())) {
            return "";
        } else {
            if (pa.getParTipoRetencionGrossingUp().getCodigo().equals(EnumTipoRetencion.SERVICIOS.getCodigo())) {
                return "";
            } else {
                if (pa.getParTipoRetencionGrossingUp().getCodigo().equals(EnumTipoRetencion.BIENES.getCodigo())) {
                    return "";
                } else {
                    if (pa.getParTipoRetencionGrossingUp().getCodigo().equals(EnumTipoRetencion.RC_IVA.getCodigo())) {
                        return "";
                    } else {
                        if (pa.getParTipoRetencionGrossingUp().getCodigo().equals(EnumTipoRetencion.ALQUILERES.getCodigo())) {
                            return "";
                        } else {
                            if (pa.getParTipoRetencionGrossingUp().getCodigo().equals(EnumTipoRetencion.REMESAS_AL_EXTERIOR.getCodigo())) {
                                return "";
                            } else {
                                if (pa.getParTipoRetencionGrossingUp().getCodigo().equals(EnumTipoRetencion.IUE_IT_IND_EXPORTADOR.getCodigo())) {
                                    return "";
                                } else {
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String irAjustes() throws Exception {
        CntParametroAutomatico pa = cntParametroAutomaticoService.obtieneObjetoDeParametroAutomatico(cntEntidad);

        if (pa.getParAjustes().getCodigo().equals(EnumAjuste.AJUSTE_RESULTADO_POR_EXPOSICION_INFLACION.getCodigo())) {
            return "";
        } else {
            if (pa.getParAjustes().getCodigo().equals(EnumAjuste.AJUSTE_INFLACION_Y_TENENCIA_DE_BIENES.getCodigo())) {
                return "";
            } else {
                if (pa.getParAjustes().getCodigo().equals(EnumAjuste.DIFERENCIA_DE_CAMBIO.getCodigo())) {
                    return "";
                } else {
                    if (pa.getParAjustes().getCodigo().equals(EnumAjuste.CORRECCION_MONETARIA.getCodigo())) {
                        return "";
                    } else {
                        if (pa.getParAjustes().getCodigo().equals(EnumAjuste.AJUSTE_DE_CAPITAL.getCodigo())) {
                            return "";
                        } else {
                            if (pa.getParAjustes().getCodigo().equals(EnumAjuste.AJUSTE_GLOBAL_DEL_PATRIMONIO.getCodigo())) {
                                return "";
                            } else {
                                if (pa.getParAjustes().getCodigo().equals(EnumAjuste.AJUSTE_RESERVA_PATRIMONIAL.getCodigo())) {
                                    return "";
                                } else {
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public String irFacturaCompra() {
        return "facturaCompra";
    }

    public String irCreditoFiscalNoDecucible() {
        return "";
    }

    public String irFacturaVenta() {
        cntDetalleComprobante.setPosicion(cntDetalleComprobanteService.ultimaPosicionDetalleComprobante().getPosicion()+1);
        cntDetalleComprobante.setLoginUsuario("hguzman");
        cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntDetalleComprobante.setCntComprobante(cntComprobante);
        cntDetalleComprobante.setCntEntidad(cntEntidad);
        setPersistValues(cntDetalleComprobante);
        try {
            cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());
            return "facturaVenta";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String adicionaDeatlleComprobanteConEstado() {
        cntDetalleComprobante.setLoginUsuario("hguzman");
        cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntDetalleComprobante.setCntComprobante(cntComprobante);
        cntDetalleComprobante.setCntEntidad(cntEntidad);
        setPersistValues(cntDetalleComprobante);        
        try {
            cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            return "formularioComprobante";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String adicionaDetalleComprobanteFacturaVenta() {
        cntDetalleComprobante.setLoginUsuario("JonasTeeeest");
        cntDetalleComprobante.setEstado(EnumEstado.PENDIENTE.getCodigo());
        cntDetalleComprobante.setCntComprobante(cntComprobante);
        cntDetalleComprobante.setCntEntidad(cntEntidad);
        setPersistValues(cntDetalleComprobante);        
        try {
            cntDetalleComprobanteService.persistCntDetalleComprobantes(cntDetalleComprobante);
            super.setInSessionIdDetalleComprobante(cntDetalleComprobante.getIdDetalleComprobante());            
            return "facturaCompra";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String modificarDeatlleComprobanteConEstado() {
        cntDetalleComprobante.setLoginUsuario("hguzman");
        cntDetalleComprobante.setCntComprobante(cntComprobante);
        cntDetalleComprobante.setCntEntidad(cntEntidad);
        setMergeValues(cntDetalleComprobante);        
        try {
            cntDetalleComprobanteService.mergeCntDetalleComprobantes(cntDetalleComprobante);
            return "formularioComprobante";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEditable() {
        if (cntDetalleComprobante == null) {
            return false;
        } else if (cntDetalleComprobante.getIdDetalleComprobante() == null) {
            return false;
        } else {
            return true;
        }
    }

    public List<CntEntidad> completaDescripcionCuenta(String descripcion) {
        List<CntEntidad> listaDeSugerencias;
        listaDeSugerencias = cntEntidadesService.listaCuentasDeUltimoNivelPorDescripcion(descripcion);
        return listaDeSugerencias;
    }

    public void actualizaDato(ValueChangeEvent e) {
        
    }

    public String irAtras() {
        return "formularioComprobante";
    }

    public String irPlanCuentas() {
        return "planCuentas";
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

    public CntEntidad getCntEntidad() {
        return cntEntidad;
    }

    public void setCntEntidad(CntEntidad cntEntidad) {
        this.cntEntidad = cntEntidad;
    }

    public CntDetalleComprobante getCntDetalleComprobante() {
        return cntDetalleComprobante;
    }

    public void setCntDetalleComprobante(CntDetalleComprobante cntDetalleComprobante) {
        this.cntDetalleComprobante = cntDetalleComprobante;
    }

    public CntEntidadesService getCntEntidadesService() {
        return cntEntidadesService;
    }

    public void setCntEntidadesService(CntEntidadesService cntEntidadesService) {
        this.cntEntidadesService = cntEntidadesService;
    }

    public CntDetalleComprobanteService getCntDetalleComprobanteService() {
        return cntDetalleComprobanteService;
    }

    public void setCntDetalleComprobanteService(CntDetalleComprobanteService cntDetalleComprobanteService) {
        this.cntDetalleComprobanteService = cntDetalleComprobanteService;
    }

    public CntComprobantesService getCntComprobantesService() {
        return cntComprobantesService;
    }

    public void setCntComprobantesService(CntComprobantesService cntComprobantesService) {
        this.cntComprobantesService = cntComprobantesService;
    }

    public CntComprobante getCntComprobante() {
        return cntComprobante;
    }

    public void setCntComprobante(CntComprobante cntComprobante) {
        this.cntComprobante = cntComprobante;
    }

    public CntParametroAutomaticoService getCntParametroAutomaticoService() {
        return cntParametroAutomaticoService;
    }

    public void setCntParametroAutomaticoService(CntParametroAutomaticoService cntParametroAutomaticoService) {
        this.cntParametroAutomaticoService = cntParametroAutomaticoService;
    }

    public Boolean getSwDebe() {
        return swDebe;
    }

    public void setSwDebe(Boolean swDebe) {
        this.swDebe = swDebe;
    }

    public Boolean getSwHaber() {
        return swHaber;
    }

    public void setSwHaber(Boolean swHaber) {
        this.swHaber = swHaber;
    }

    public Boolean getPulsoSwDebe() {
        return pulsoSwDebe;
    }

    public void setPulsoSwDebe(Boolean pulsoSwDebe) {
        this.pulsoSwDebe = pulsoSwDebe;
    }

    public Boolean getPulsoSwHaber() {
        return pulsoSwHaber;
    }

    public void setPulsoSwHaber(Boolean pulsoSwHaber) {
        this.pulsoSwHaber = pulsoSwHaber;
    }

    public Boolean getActivaFacturaDeCompra() {
        return activaFacturaDeCompra;
    }

    public void setActivaFacturaDeCompra(Boolean activaFacturaDeCompra) {
        this.activaFacturaDeCompra = activaFacturaDeCompra;
    }

    public Boolean getActivaFacturaDeVenta() {
        return activaFacturaDeVenta;
    }

    public void setActivaFacturaDeVenta(Boolean activaFacturaDeVenta) {
        this.activaFacturaDeVenta = activaFacturaDeVenta;
    }

    public Boolean getActivaSinFactura() {
        return activaSinFactura;
    }

    public void setActivaSinFactura(Boolean activaSinFactura) {
        this.activaSinFactura = activaSinFactura;
    }

    public Boolean getActivaBotonGuardar() {
        return activaBotonGuardar;
    }

    public void setActivaBotonGuardar(Boolean activaBotonGuardar) {
        this.activaBotonGuardar = activaBotonGuardar;
    }

    public Boolean getActivaRetencion() {
        return activaRetencion;
    }

    public void setActivaRetencion(Boolean activaRetencion) {
        this.activaRetencion = activaRetencion;
    }

    public String getNombreRetencion() {
        return nombreRetencion;
    }

    public void setNombreRetencion(String nombreRetencion) {
        this.nombreRetencion = nombreRetencion;
    }

    public Boolean getActivaRetencionGrossingUp() {
        return activaRetencionGrossingUp;
    }

    public void setActivaRetencionGrossingUp(Boolean activaRetencionGrossingUp) {
        this.activaRetencionGrossingUp = activaRetencionGrossingUp;
    }

    public String getNombreRetencionGrossingUp() {
        return nombreRetencionGrossingUp;
    }

    public void setNombreRetencionGrossingUp(String nombreRetencionGrossingUp) {
        this.nombreRetencionGrossingUp = nombreRetencionGrossingUp;
    }

    public Boolean getActivaCreditoFiscalNoDeducible() {
        return activaCreditoFiscalNoDeducible;
    }

    public void setActivaCreditoFiscalNoDeducible(Boolean activaCreditoFiscalNoDeducible) {
        this.activaCreditoFiscalNoDeducible = activaCreditoFiscalNoDeducible;
    }

    public Boolean getActivaAjuste() {
        return activaAjuste;
    }

    public void setActivaAjuste(Boolean activaAjuste) {
        this.activaAjuste = activaAjuste;
    }

    public String getNombreAjuste() {
        return nombreAjuste;
    }

    public void setNombreAjuste(String nombreAjuste) {
        this.nombreAjuste = nombreAjuste;
    }
}
