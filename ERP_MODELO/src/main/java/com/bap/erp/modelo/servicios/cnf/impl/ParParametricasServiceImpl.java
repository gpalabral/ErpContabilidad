package com.bap.erp.modelo.servicios.cnf.impl;

import com.bap.erp.modelo.entidades.cnf.ParDatosEmpresa;
import com.bap.erp.modelo.entidades.cnf.ParTipoRetencion;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.entidades.cnf.ParGestionContable;
import com.bap.erp.modelo.entidades.cnf.ParEstadoProyecto;
import com.bap.erp.modelo.entidades.cnf.ParSegundaMoneda;
import com.bap.erp.modelo.entidades.cnf.ParLugarDeEntrega;
import com.bap.erp.modelo.entidades.cnf.ParTipoDocIdentidad;
import com.bap.erp.modelo.entidades.cnf.ParAjustes;
import com.bap.erp.modelo.entidades.cnf.ParTiposDatoNivel;
import com.bap.erp.modelo.entidades.cnf.ParCiudad;
import com.bap.erp.modelo.entidades.cnf.ParCuentasDeAjuste;
import com.bap.erp.modelo.entidades.cnf.ParBanco;
import com.bap.erp.modelo.entidades.cnf.ParVarios;
import com.bap.erp.modelo.entidades.cnf.ParTipoFacturacion;
import com.bap.erp.modelo.entidades.cnf.ParRecetasDistribucionCentroCosto;
import com.bap.erp.modelo.entidades.cnf.ParTipoComprobante;
import com.bap.erp.modelo.entidades.cnf.ParSucursal;
import com.bap.erp.modelo.entidades.cnf.ParComprasYVentas;
import com.bap.erp.modelo.entidades.cnf.ParCuentasGenerales;
import com.bap.erp.modelo.entidades.cnf.ParRetencionIue;
import com.bap.erp.modelo.GenericDaoImpl;
import com.bap.erp.modelo.entidades.cnf.ParIpWebServiceWamsa;
import com.bap.erp.modelo.entidades.cnf.ParParametrosActiveDirectory;
import com.bap.erp.modelo.entidades.cnf.ParParametrosServidor;
import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntDefinicionCuentasMigracion;
import com.bap.erp.modelo.entidades.cnt.CntDistribucionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.enums.EnumEmpresa_DatosEmpresa;
import com.bap.erp.modelo.enums.EnumEmpresa_GestionContable;
import com.bap.erp.modelo.enums.EnumEmpresa_Varios;
import com.bap.erp.modelo.enums.EnumTipoAjuste;
import com.bap.erp.modelo.pojo.ParCuentasGeneralesPojo;
import com.bap.erp.modelo.servicios.cnt.CntConfiguracionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.impl.CntMascarasServiceImpl;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.wrapper.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ParParametricasServiceImpl extends GenericDaoImpl<ParValor> implements ParParametricasService {

    private final static Log log = LogFactory.getLog(CntMascarasServiceImpl.class);
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private CntEntidadesService cntEntidadesService;
    @Autowired
    private CntConfiguracionCentroCostoService cntConfiguracionCentroCostoService;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public ParValor persistParValor(ParValor parValor) throws Exception {
        try {
            super.persist(parValor);
            return parValor;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public ParValor mergeParValor(ParValor parValor) throws Exception {
        try {
            super.merge(parValor);
            return parValor;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public ParValor removeParValor(ParValor parValor) throws Exception {
        try {
            super.remove(parValor);
            return parValor;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Serializable find(Class clazz, String id) {
        Serializable o = (Serializable) hibernateTemplate.get(clazz, id);
        if (o != null) {
            return o;
        } else {
            throw new RuntimeException("No se encuentra un valor con la clave: " + id + " para la entidad: " + clazz);
        }
    }

    @Override
    public List<ParCuentasGenerales> parCuentasGeneralesList() {
        try {
            List<ParCuentasGenerales> lista = hibernateTemplate.find(""
                    + "select o from ParCuentasGenerales o");
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();

    }

    @Override
    public List<ParSegundaMoneda> getSegundaMonedaList() {
        List<ParSegundaMoneda> lista = hibernateTemplate.find(""
                + "select o "
                + "from ParSegundaMoneda o");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.emptyList();
    }

    public List<ParDatosEmpresa> listaDatosDeEmpresa() {
        List<ParDatosEmpresa> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParDatosEmpresa j");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.emptyList();
    }

    public List<ParVarios> listaVarios() {
        List<ParVarios> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParVarios j");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.emptyList();
    }

    @Override
    public List<ParTiposDatoNivel> getParTiposDatoNivels() {
        try {
            List<ParTiposDatoNivel> lista = hibernateTemplate.find(""
                    + "select o "
                    + "from ParTiposDatoNivel o");
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public DatosEmpresaWrapper factoryEmpresa() {
        List<ParDatosEmpresa> lista = hibernateTemplate.find(""
                + "select o from ParDatosEmpresa o");
        DatosEmpresaWrapper datosEmpresaWrapper = new DatosEmpresaWrapper();
        for (ParDatosEmpresa parDatosEmpresa : lista) {
            if (parDatosEmpresa.getCodigo().equals(EnumEmpresa_DatosEmpresa.RAZON_SOCIAL.getCodigo())) {
                datosEmpresaWrapper.setRazonSocial(parDatosEmpresa.getValor());
            }
            if (parDatosEmpresa.getCodigo().equals(EnumEmpresa_DatosEmpresa.SUBTITULO.getCodigo())) {
                datosEmpresaWrapper.setSubtitulo(parDatosEmpresa.getValor());
            }
            if (parDatosEmpresa.getCodigo().equals(EnumEmpresa_DatosEmpresa.NUMERO_DE_IDENTIFICACIÓN_TRIBUTARIA.getCodigo())) {
                datosEmpresaWrapper.setNit(parDatosEmpresa.getValor());
            }
            if (parDatosEmpresa.getCodigo().equals(EnumEmpresa_DatosEmpresa.GESTION_CONTABLE.getCodigo())) {
                datosEmpresaWrapper.setGestion(parDatosEmpresa.getValor());
            }
            if (parDatosEmpresa.getCodigo().equals(EnumEmpresa_DatosEmpresa.AUTORIZACION.getCodigo())) {
                datosEmpresaWrapper.setAutorizacion(parDatosEmpresa.getValor());
            }
            if (parDatosEmpresa.getCodigo().equals(EnumEmpresa_DatosEmpresa.DIRECCIÓN.getCodigo())) {
                datosEmpresaWrapper.setDireccion(parDatosEmpresa.getValor());
            }
        }
        return datosEmpresaWrapper;

    }

    public ComprasYVentasWrapper factoryComprasYVentas() {
        List<ParComprasYVentas> lista = hibernateTemplate.find(""
                + "select j from ParComprasYVentas j");
        ComprasYVentasWrapper comprasYVentasWrapper = new ComprasYVentasWrapper();
        for (ParComprasYVentas parComprasYVentas : lista) {
            try {
                if (parComprasYVentas.getCodigo().equals("IVA")) {
                    comprasYVentasWrapper.setPorcentajeIVA(Float.parseFloat(parComprasYVentas.getValor()));
                } else if (parComprasYVentas.getCodigo().equals("IT")) {
                    comprasYVentasWrapper.setPorcentajeIT(Float.parseFloat(parComprasYVentas.getValor()));
                } else if (parComprasYVentas.getCodigo().equals("CDBF")) {
                    comprasYVentasWrapper.setDebitoFiscal(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parComprasYVentas.getValor())));
                } else if (parComprasYVentas.getCodigo().equals("CCRF")) {
                    comprasYVentasWrapper.setCreditoFiscal(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parComprasYVentas.getValor())));
                } else if (parComprasYVentas.getCodigo().equals("CCFD")) {
                    comprasYVentasWrapper.setCreditoFiscalNoDeducible(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parComprasYVentas.getValor())));
                } else if (parComprasYVentas.getCodigo().equals("CCFT")) {
                    comprasYVentasWrapper.setCreditoFiscalTransitorio(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parComprasYVentas.getValor())));
                } else if (parComprasYVentas.getCodigo().equals("CITP")) {
                    comprasYVentasWrapper.setImpuestoTransaccion(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parComprasYVentas.getValor())));
                } else if (parComprasYVentas.getCodigo().equals("CITG")) {
                    comprasYVentasWrapper.setGastoImpuestoTransaccion(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parComprasYVentas.getValor())));
                } else if (parComprasYVentas.getCodigo().equals("CDFT")) {
                    comprasYVentasWrapper.setDebitoFiscalTransitorio(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parComprasYVentas.getValor())));
                }
                if (parComprasYVentas.getCodigo().equals("OPIT")) {
                    if (parComprasYVentas.getValor().equals("1")) {
                        comprasYVentasWrapper.setObligacionPagoIT(true);
                    } else {
                        comprasYVentasWrapper.setObligacionPagoIT(false);

                    }
                }
            } catch (Exception ex) {
            }

        }
        return comprasYVentasWrapper;
    }

    public CuentasDeAjusteWrapper factoryCuentasDeAjuste() {
        List<ParCuentasDeAjuste> lista = hibernateTemplate.find("select h from ParCuentasDeAjuste h");
        CuentasDeAjusteWrapper cuentasDeAjusteWrapper = new CuentasDeAjusteWrapper();
        for (ParCuentasDeAjuste parCuentasDeAjuste : lista) {
            try {
                if (parCuentasDeAjuste.getCodigo().equals("AREI")) {
                    cuentasDeAjusteWrapper.setInflacionResulPorExposicionInflacion(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parCuentasDeAjuste.getValor())));
                } else if (parCuentasDeAjuste.getCodigo().equals("ACM3")) {
                    cuentasDeAjusteWrapper.setAjusteCorreccionMonetaria(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parCuentasDeAjuste.getValor())));
                } else if (parCuentasDeAjuste.getCodigo().equals("DCA3")) {
                    cuentasDeAjusteWrapper.setDiferenciaCambio(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parCuentasDeAjuste.getValor())));
                } else if (parCuentasDeAjuste.getCodigo().equals("ACAP")) {
                    cuentasDeAjusteWrapper.setAjusteCapital(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parCuentasDeAjuste.getValor())));
                } else if (parCuentasDeAjuste.getCodigo().equals("AJRP")) {
                    cuentasDeAjusteWrapper.setAjusteReservasPatrimoniales(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parCuentasDeAjuste.getValor())));
                }
            } catch (Exception e) {
            }
        }
        return cuentasDeAjusteWrapper;
    }

    public RetencionesIUEWrapper factoryRetencionesIUE() {
        List<ParRetencionIue> lista = hibernateTemplate.find("select h from ParRetencionIue h");
        RetencionesIUEWrapper retencionesIUEWrapper = new RetencionesIUEWrapper();
        for (ParRetencionIue parRetencionIue : lista) {
            try {
                if (parRetencionIue.getCodigo().equals("CRBI")) {
                    retencionesIUEWrapper.setPorcentajeRetencionesBienes(Float.parseFloat(parRetencionIue.getValor()));
                } else if (parRetencionIue.getCodigo().equals("CRSV")) {
                    retencionesIUEWrapper.setPorcentajeRetencionesServicios(Float.parseFloat(parRetencionIue.getValor()));
                } else if (parRetencionIue.getCodigo().equals("CTRS")) {
                    retencionesIUEWrapper.setCuentaIUERetencionesServicios(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parRetencionIue.getValor())));
                } else if (parRetencionIue.getCodigo().equals("CTRB")) {
                    retencionesIUEWrapper.setCuentaIUERetencionesBienes(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parRetencionIue.getValor())));
                } else if (parRetencionIue.getCodigo().equals("CRIT")) {
                    retencionesIUEWrapper.setCuentaITRetenciones(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parRetencionIue.getValor())));
                } else if (parRetencionIue.getCodigo().equals("CRCI")) {
                    retencionesIUEWrapper.setCuentaRetencionesRCIVA(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parRetencionIue.getValor())));
                } else if (parRetencionIue.getCodigo().equals("CRAE")) {
                    retencionesIUEWrapper.setCuentaRetencionesExterior(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parRetencionIue.getValor())));
                } else if (parRetencionIue.getCodigo().equals("RSIE")) {
                    retencionesIUEWrapper.setRetencionIUEITSectorIndustrialExporta(Float.parseFloat(parRetencionIue.getValor()));
                } else if (parRetencionIue.getCodigo().equals("CRSI")) {
                    retencionesIUEWrapper.setCuentaRetencionIUEITSectorIndustrial(cntEntidadesService.find(CntEntidad.class, Long.parseLong(parRetencionIue.getValor())));
                }
            } catch (Exception e) {
            }
        }
        return retencionesIUEWrapper;
    }

    public GestionContableWrapper factoryGestionContable() {
        List<ParGestionContable> lista = hibernateTemplate.find(""
                + "select h from ParGestionContable h");
        GestionContableWrapper gestionContableWrapper = new GestionContableWrapper();
        for (ParGestionContable parGestionContable : lista) {
            try {
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.CIUDAD.getCodigo())) {
                    gestionContableWrapper.setCiudad(parGestionContable.getValor());
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.INICIO_GESTIÓN_FISCAL.getCodigo())) {
                    gestionContableWrapper.setInicioGestionFiscal(Integer.parseInt(parGestionContable.getValor()));
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.PERIODO_ACTUAL.getCodigo())) {
                    gestionContableWrapper.setPeriodoActual(Integer.parseInt(parGestionContable.getValor()));
//                gestionContableWrapper.setPeriodoActual(new java.sql.Date(utilDate.parGestionContable.getValor()));
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.TIPO_DE_CAMBIO_INICIAL.getCodigo())) {
                    gestionContableWrapper.setTipoCambioInicial(Float.parseFloat(parGestionContable.getValor()));
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.DEFINICION_DE_CUENTAS.getCodigo())) {
                    gestionContableWrapper.setDefinicionCuentas((CntEntidad) cntEntidadesService.find(CntEntidad.class, Long.parseLong(parGestionContable.getValor())));
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.NORMA_CONTABLE_3.getCodigo())) {
                    gestionContableWrapper.setNormaContable3(parGestionContable.getValor());
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.AJUSTES_REI_SOBRE_LA_MISMA_CUENTA.getCodigo())) {
                    gestionContableWrapper.setAjustesREI(parGestionContable.getValor().equals("1") ? true : false);
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.MODO_DE_PROCESO_DE_AJUSTES.getCodigo())) {
                    gestionContableWrapper.setModoProcesosAjustes(parGestionContable.getValor());
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.TIPO_DE_MONEDA.getCodigo())) {
                    gestionContableWrapper.setTipoMoneda(parGestionContable.getValor());
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.FECHA_INICIAL_REGISTRO_COMPROBANTE.getCodigo())) {
                    String[] formateada = parGestionContable.getValor().split("/");
                    gestionContableWrapper.setFechaLimiteInicial(new Date(formateada[1] + "/" + formateada[0] + "/" + formateada[2]));
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.FECHA_FINAL_REGISTRO_COMPROBANTE.getCodigo())) {
                    String[] formateada = parGestionContable.getValor().split("/");
                    gestionContableWrapper.setFechaLimiteFinal(new Date(formateada[1] + "/" + formateada[0] + "/" + formateada[2]));
                }
                if (parGestionContable.getCodigo().equals(EnumEmpresa_GestionContable.ANIO_ACTUAL.getCodigo())) {
                    gestionContableWrapper.setAnioActual(Integer.parseInt(parGestionContable.getValor()));
//                gestionContableWrapper.setPeriodoActual(new java.sql.Date(utilDate.parGestionContable.getValor()));
                }

            } catch (Exception e) {
            }

        }
        return gestionContableWrapper;

    }

    public VariosWrapper factoryVarios() {
        List<ParVarios> lista = hibernateTemplate.find("select h from ParVarios h");
        VariosWrapper variosWrapper = new VariosWrapper();
        for (ParVarios parVarios : lista) {
            try {
                if (parVarios.getCodigo().equals(EnumEmpresa_Varios.EJECUTA_PRESUPUESTO_FISCAL.getCodigo())) {
                    variosWrapper.setEjecutaPresupuestoFiscal(parVarios.getValor().equals("1") ? true : false);
                }
                if (parVarios.getCodigo().equals(EnumEmpresa_Varios.CENTROS_DE_COSTO.getCodigo())) {
                    variosWrapper.setCentroDeCostos(parVarios.getValor().equals("1") ? true : false);
                }
                if (parVarios.getCodigo().equals(EnumEmpresa_Varios.CUENTAS_COORPORATIVAS.getCodigo())) {
                    variosWrapper.setCuentasCoorporativas(parVarios.getValor().equals("1") ? true : false);
                }
                if (parVarios.getCodigo().equals(EnumEmpresa_Varios.LONGITUD.getCodigo())) {
                    variosWrapper.setLongitud(Integer.parseInt(parVarios.getValor()));
                }
                if (parVarios.getCodigo().equals(EnumEmpresa_Varios.NUEVO_SISTEMA_DE_FACTURACION.getCodigo())) {
                    variosWrapper.setNuevoSistemaFacturacion(parVarios.getValor().equals("1") ? true : false);
                }
                if (parVarios.getCodigo().equals(EnumEmpresa_Varios.CODIGO_DE_CONTROL.getCodigo())) {
                    variosWrapper.setCodigoControl(parVarios.getValor().equals("1") ? true : false);
                }
                if (parVarios.getCodigo().equals(EnumEmpresa_Varios.FACTURACIÓN_COMPUTARIZADA.getCodigo())) {
                    variosWrapper.setFacturacionComputarizada(parVarios.getValor().equals("1") ? true : false);
                }
                if (parVarios.getCodigo().equals(EnumEmpresa_Varios.PROYECTO.getCodigo())) {
                    variosWrapper.setProyectos(parVarios.getValor().equals("1") ? true : false);
                }
                if (parVarios.getCodigo().equals(EnumEmpresa_Varios.AUXILIAR.getCodigo())) {
                    variosWrapper.setAuxiliares(parVarios.getValor().equals("1") ? true : false);
                }
            } catch (Exception e) {
            }

        }
        return variosWrapper;
    }

    public ParValor findParValor(String codigo) {
        List<ParValor> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParValor j where j.codigo='" + codigo + "'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return new ParValor();
    }

    public ParValor findParValorContextoVarios(String codigo) {
        List<ParValor> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParValor j where j.codigo='" + codigo + "' and j.contexto='VARIOS'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return new ParValor();
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void modificarDatosEmpresa(DatosEmpresaWrapper datosEmpresaWrapper, GestionContableWrapper gestionContableWrapper, VariosWrapper variosWrapper, String usuarioModificacion) throws Exception {
        try {

            ParValor razonSocial, subtitulo, gestion, nit, autorizacion, direccion;

            razonSocial = findParValor("RSOC");
            //***************************
//            ObjectUtils.printObjectState(razonSocial);
            //***************************
            if (datosEmpresaWrapper.getRazonSocial() != null) {
                razonSocial.setValor(datosEmpresaWrapper.getRazonSocial());
                razonSocial.setUsuarioModificacion(usuarioModificacion);
                razonSocial.setFechaModificacion(new Date());
                mergeParValor(razonSocial);
            }

            subtitulo = findParValor("SUBT");
            //***************************
//            ObjectUtils.printObjectState(subtitulo);
            //***************************

            if (datosEmpresaWrapper.getSubtitulo() != null) {
                subtitulo.setValor(datosEmpresaWrapper.getSubtitulo());
                subtitulo.setUsuarioModificacion(usuarioModificacion);
                subtitulo.setFechaModificacion(new Date());
                mergeParValor(subtitulo);
            }
            nit = findParValor("NIT");
            //***************************
//            ObjectUtils.printObjectState(nit);
            //***************************
            if (datosEmpresaWrapper.getNit() != null) {
                nit.setValor(datosEmpresaWrapper.getNit());
                nit.setUsuarioModificacion(usuarioModificacion);
                nit.setFechaModificacion(new Date());
                mergeParValor(nit);
            }
            gestion = findParValor("GEST");
            //***************************
//            ObjectUtils.printObjectState(gestion);
            //***************************
            if (datosEmpresaWrapper.getGestion() != null) {
                gestion.setValor(datosEmpresaWrapper.getGestion());
                gestion.setUsuarioModificacion(usuarioModificacion);
                gestion.setFechaModificacion(new Date());
                mergeParValor(gestion);
            }

            autorizacion = findParValor("AUTO");
            //***************************
//            ObjectUtils.printObjectState(autorizacion);
            //***************************
            if (datosEmpresaWrapper.getAutorizacion() != null) {

                autorizacion.setValor(datosEmpresaWrapper.getAutorizacion());
                autorizacion.setUsuarioModificacion(usuarioModificacion);
                autorizacion.setFechaModificacion(new Date());
                mergeParValor(autorizacion);
            }

            direccion = findParValor("DIR");
            //***************************
//            ObjectUtils.printObjectState(direccion);
            //***************************
            if (datosEmpresaWrapper.getDireccion() != null) {
                direccion.setValor(datosEmpresaWrapper.getDireccion());
                direccion.setUsuarioModificacion(usuarioModificacion);
                direccion.setFechaModificacion(new Date());
                mergeParValor(direccion);
            }
            ParValor ciudad, inicioGestionFiscal, periodoActual, tipoCambioInicial, definicionCuentas, normaContable3, ajustesREI, modoProcesosAjustes, tipoMoneda, fechaInicialRegistroComprobante, fechaFinalRegistroComprobante, anioActual;

            ciudad = findParValor("CIUD");
            //***************************
//            ObjectUtils.printObjectState(ciudad);
            //***************************
            ciudad.setValor(gestionContableWrapper.getCiudad());
            ciudad.setUsuarioModificacion(usuarioModificacion);
            ciudad.setFechaModificacion(new Date());
            mergeParValor(ciudad);

            inicioGestionFiscal = findParValor("IGFI");
            //***************************
//            ObjectUtils.printObjectState(inicioGestionFiscal);
            //***************************
            inicioGestionFiscal.setValor(Integer.toString(gestionContableWrapper.getInicioGestionFiscal()));
            inicioGestionFiscal.setUsuarioModificacion(usuarioModificacion);
            inicioGestionFiscal.setFechaModificacion(new Date());
            mergeParValor(inicioGestionFiscal);

            periodoActual = findParValor("PACT");
            //***************************
//            ObjectUtils.printObjectState(periodoActual);
            //***************************
            if (gestionContableWrapper.getPeriodoActual() != null) {
                periodoActual.setValor(gestionContableWrapper.getPeriodoActual().toString());
                periodoActual.setUsuarioModificacion(usuarioModificacion);
                periodoActual.setFechaModificacion(new Date());
                mergeParValor(periodoActual);
            }
            anioActual = findParValor("AACT");
            //***************************
//            ObjectUtils.printObjectState(anioActual);
            //***************************
            if (gestionContableWrapper.getAnioActual()!= null) {
                anioActual.setValor(gestionContableWrapper.getAnioActual().toString());
                anioActual.setUsuarioModificacion(usuarioModificacion);
                anioActual.setFechaModificacion(new Date());
                mergeParValor(anioActual);
            }

            tipoCambioInicial = findParValor("TCOF");
            //***************************
//            ObjectUtils.printObjectState(tipoCambioInicial);
            //***************************
            tipoCambioInicial.setValor(Float.toHexString(gestionContableWrapper.getTipoCambioInicial()));
            tipoCambioInicial.setUsuarioModificacion(usuarioModificacion);
            tipoCambioInicial.setFechaModificacion(new Date());
            mergeParValor(tipoCambioInicial);

            definicionCuentas = findParValor("DCTA");
            //***************************
//            ObjectUtils.printObjectState(definicionCuentas);
            //***************************
            if (gestionContableWrapper.getDefinicionCuentas() != null) {
                if (gestionContableWrapper.getDefinicionCuentas().getIdEntidad() != null) {
                    definicionCuentas.setValor(Long.toString(gestionContableWrapper.getDefinicionCuentas().getIdEntidad()));
                    definicionCuentas.setUsuarioModificacion(usuarioModificacion);
                    definicionCuentas.setFechaModificacion(new Date());
                    mergeParValor(definicionCuentas);
                }
            }

            normaContable3 = findParValor("NCON");
            //***************************
//            ObjectUtils.printObjectState(normaContable3);
            //***************************
            if (gestionContableWrapper.getNormaContable3() != null) {
                normaContable3.setValor(gestionContableWrapper.getNormaContable3());
                normaContable3.setUsuarioModificacion(usuarioModificacion);
                normaContable3.setFechaModificacion(new Date());
                mergeParValor(normaContable3);
            }
            ajustesREI = findParValor("ARSC");
            //***************************
//            ObjectUtils.printObjectState(ajustesREI);
            //***************************
            if (gestionContableWrapper.getAjustesREI() != null) {
                ajustesREI.setValor(Integer.toString(Boolean.valueOf(gestionContableWrapper.getAjustesREI()).compareTo(false)));
                ajustesREI.setUsuarioModificacion(usuarioModificacion);
                ajustesREI.setFechaModificacion(new Date());
                mergeParValor(ajustesREI);
            }
            modoProcesosAjustes = findParValor("MPDA");
            //***************************
//            ObjectUtils.printObjectState(modoProcesosAjustes);
            //***************************
            if (gestionContableWrapper.getModoProcesosAjustes() != null) {
                modoProcesosAjustes.setValor(gestionContableWrapper.getModoProcesosAjustes());
                modoProcesosAjustes.setUsuarioModificacion(usuarioModificacion);
                modoProcesosAjustes.setFechaModificacion(new Date());
                mergeParValor(modoProcesosAjustes);
            }
            tipoMoneda = findParValor("TMON");
            //***************************
//            ObjectUtils.printObjectState(tipoMoneda);
            //***************************
            if (gestionContableWrapper.getTipoMoneda() != null) {
                tipoMoneda.setValor(gestionContableWrapper.getTipoMoneda());
                tipoMoneda.setUsuarioModificacion(usuarioModificacion);
                tipoMoneda.setFechaModificacion(new Date());
                mergeParValor(tipoMoneda);
            }

            fechaInicialRegistroComprobante = findParValor("FIRC");
            if (gestionContableWrapper.getFechaLimiteInicial() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaInicialRegistroComprobante.setValor(sdf.format(gestionContableWrapper.getFechaLimiteInicial()));
                fechaInicialRegistroComprobante.setUsuarioModificacion(usuarioModificacion);
                fechaInicialRegistroComprobante.setFechaModificacion(new Date());
                mergeParValor(fechaInicialRegistroComprobante);
            }

            fechaFinalRegistroComprobante = findParValor("FFRC");
            //***************************
//            ObjectUtils.printObjectState(tipoMoneda);
            //***************************
            if (gestionContableWrapper.getFechaLimiteFinal() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaFinalRegistroComprobante.setValor(sdf.format(gestionContableWrapper.getFechaLimiteFinal()));
                fechaFinalRegistroComprobante.setUsuarioModificacion(usuarioModificacion);
                fechaFinalRegistroComprobante.setFechaModificacion(new Date());
                mergeParValor(fechaFinalRegistroComprobante);
            }

            ParValor ejecutaPresupuestoFiscal, centroDeCostos, cuentasCoorporativas, longitud, nuevoSistemaFacturacion, facturacionComputarizada, codigoControl, proyectos, auxiliares;

            ejecutaPresupuestoFiscal = findParValor("EPF");
            //***************************
//            ObjectUtils.printObjectState(ejecutaPresupuestoFiscal);
            //***************************
            if (variosWrapper.getEjecutaPresupuestoFiscal() != null) {
                ejecutaPresupuestoFiscal.setValor(Integer.toString(Boolean.valueOf(variosWrapper.getEjecutaPresupuestoFiscal()).compareTo(false)));
                ejecutaPresupuestoFiscal.setUsuarioModificacion(usuarioModificacion);
                ejecutaPresupuestoFiscal.setFechaModificacion(new Date());
                mergeParValor(ejecutaPresupuestoFiscal);
            }

            centroDeCostos = findParValorContextoVarios("CCOS");
            //***************************
//            ObjectUtils.printObjectState(centroDeCostos);
            //***************************
            if (variosWrapper.getCentroDeCostos() != null) {
                centroDeCostos.setValor(Integer.toString(variosWrapper.getCentroDeCostos() ? 1 : 0));
                centroDeCostos.setUsuarioModificacion(usuarioModificacion);
                centroDeCostos.setFechaModificacion(new Date());
                mergeParValor(centroDeCostos);
            }
            cuentasCoorporativas = findParValor("CTAC");
            //***************************
//            ObjectUtils.printObjectState(cuentasCoorporativas);
            //***************************
            if (variosWrapper.getCuentasCoorporativas() != null) {
                cuentasCoorporativas.setValor(Integer.toString(Boolean.valueOf(variosWrapper.getCuentasCoorporativas()).compareTo(false)));
                cuentasCoorporativas.setUsuarioModificacion(usuarioModificacion);
                cuentasCoorporativas.setFechaModificacion(new Date());
                mergeParValor(cuentasCoorporativas);
            }
            longitud = findParValor("LONG");
            //***************************
//            ObjectUtils.printObjectState(longitud);
            //***************************
            if (variosWrapper.getLongitud() != null) {
                longitud.setValor(Integer.toString(variosWrapper.getLongitud()));
                longitud.setUsuarioModificacion(usuarioModificacion);
                longitud.setFechaModificacion(new Date());
                mergeParValor(longitud);
            }
            nuevoSistemaFacturacion = findParValor("NSDF");
            //***************************
//            ObjectUtils.printObjectState(nuevoSistemaFacturacion);
            //***************************
            if (variosWrapper.getNuevoSistemaFacturacion() != null) {
                nuevoSistemaFacturacion.setValor(Integer.toString(Boolean.valueOf(variosWrapper.getNuevoSistemaFacturacion()).compareTo(false)));
                nuevoSistemaFacturacion.setUsuarioModificacion(usuarioModificacion);
                nuevoSistemaFacturacion.setFechaModificacion(new Date());
                mergeParValor(nuevoSistemaFacturacion);
            }
            facturacionComputarizada = findParValor("FCOM");
            //***************************
//            ObjectUtils.printObjectState(facturacionComputarizada);
            //***************************
            if (variosWrapper.getFacturacionComputarizada() != null) {
                facturacionComputarizada.setValor(Integer.toString(Boolean.valueOf(variosWrapper.getFacturacionComputarizada()).compareTo(false)));
                facturacionComputarizada.setUsuarioModificacion(usuarioModificacion);
                facturacionComputarizada.setFechaModificacion(new Date());
                mergeParValor(facturacionComputarizada);
            }
            codigoControl = findParValor("CCOD");
            //***************************
//            ObjectUtils.printObjectState(codigoControl);
            //***************************
//            codigoControl.setValor(Integer.toString(Boolean.valueOf(variosWrapper.getCodigoControl()).compareTo(false)));
            if (variosWrapper.getCodigoControl() != null) {
                codigoControl.setValor(Integer.toString(variosWrapper.getCodigoControl() ? 1 : 0));
                codigoControl.setUsuarioModificacion(usuarioModificacion);
                codigoControl.setFechaModificacion(new Date());
                mergeParValor(codigoControl);
            }
            proyectos = findParValorContextoVarios("PROY");
            //***************************
//            ObjectUtils.printObjectState(proyectos);
            //***************************
            if (variosWrapper.getProyectos() != null) {
                proyectos.setValor(Integer.toString(variosWrapper.getProyectos() ? 1 : 0));
                proyectos.setUsuarioModificacion(usuarioModificacion);
                proyectos.setFechaModificacion(new Date());
                mergeParValor(proyectos);
            }
            auxiliares = findParValor("AUXI");
            //***************************
//            ObjectUtils.printObjectState(auxiliares);
            //***************************
            if (variosWrapper.getAuxiliares() != null) {
                auxiliares.setValor(Integer.toString(variosWrapper.getAuxiliares() ? 1 : 0));
                auxiliares.setUsuarioModificacion(usuarioModificacion);
                auxiliares.setFechaModificacion(new Date());
                mergeParValor(auxiliares);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void modificaParametrosDeGestion(ComprasYVentasWrapper comprasYVentasWrapper, CuentasDeAjusteWrapper cuentasDeAjusteWrapper, RetencionesIUEWrapper retencionesIUEWrapper, String usuarioModificacion) throws Exception {
        try {
            ParValor it, opit, iva, cdbf, ccrf, ccfd, ccft, citp, citg, cdft;
            //Cargando los datos de Compras y Ventas
            if (comprasYVentasWrapper.getPorcentajeIT() != null) {
                it = findParValor("IT");
                it.setValor(comprasYVentasWrapper.getPorcentajeIT().toString());
                it.setUsuarioModificacion(usuarioModificacion);
                it.setFechaModificacion(new Date());
                mergeParValor(it);
            }

            if (comprasYVentasWrapper.getObligacionPagoIT() != null) {
                opit = findParValor("OPIT");
                opit.setValor(Integer.toString(Boolean.valueOf(comprasYVentasWrapper.getObligacionPagoIT()).compareTo(false)));
                opit.setUsuarioModificacion(usuarioModificacion);
                opit.setFechaModificacion(new Date());
                mergeParValor(opit);
            }

            if (comprasYVentasWrapper.getPorcentajeIVA() != null) {
                iva = findParValor("IVA");
                iva.setValor(comprasYVentasWrapper.getPorcentajeIVA().toString());
                iva.setUsuarioModificacion(usuarioModificacion);
                iva.setFechaModificacion(new Date());
                mergeParValor(iva);
            }

            if (comprasYVentasWrapper.getDebitoFiscal() != null) {
                cdbf = findParValor("CDBF");
                cdbf.setValor(comprasYVentasWrapper.getDebitoFiscal().getIdEntidad().toString());
                cdbf.setUsuarioModificacion(usuarioModificacion);
                cdbf.setFechaModificacion(new Date());
                mergeParValor(cdbf);
            }

            if (comprasYVentasWrapper.getCreditoFiscal().getIdEntidad() != null) {
                ccrf = findParValor("CCRF");
                ccrf.setValor(comprasYVentasWrapper.getCreditoFiscal().getIdEntidad().toString());
                ccrf.setUsuarioModificacion(usuarioModificacion);
                ccrf.setFechaModificacion(new Date());
                mergeParValor(ccrf);
            }

            if (comprasYVentasWrapper.getCreditoFiscalNoDeducible().getIdEntidad() != null) {
                ccfd = findParValor("CCFD");
                ccfd.setValor(comprasYVentasWrapper.getCreditoFiscalNoDeducible().getIdEntidad().toString());
                ccfd.setUsuarioModificacion(usuarioModificacion);
                ccfd.setFechaModificacion(new Date());
                mergeParValor(ccfd);
            }

            if (comprasYVentasWrapper.getCreditoFiscalTransitorio().getIdEntidad() != null) {
                ccft = findParValor("CCFT");
                ccft.setValor(comprasYVentasWrapper.getCreditoFiscalTransitorio().getIdEntidad().toString());
                ccft.setUsuarioModificacion(usuarioModificacion);
                ccft.setFechaModificacion(new Date());
                mergeParValor(ccft);
            }

            if (comprasYVentasWrapper.getImpuestoTransaccion().getIdEntidad() != null) {
                citp = findParValor("CITP");
                citp.setValor(comprasYVentasWrapper.getImpuestoTransaccion().getIdEntidad().toString());
                citp.setUsuarioModificacion(usuarioModificacion);
                citp.setFechaModificacion(new Date());
                mergeParValor(citp);
            }

            if (comprasYVentasWrapper.getGastoImpuestoTransaccion().getIdEntidad() != null) {
                citg = findParValor("CITG");
                citg.setValor(comprasYVentasWrapper.getGastoImpuestoTransaccion().getIdEntidad().toString());
                citg.setUsuarioModificacion(usuarioModificacion);
                citg.setFechaModificacion(new Date());
                mergeParValor(citg);
            }

            if (comprasYVentasWrapper.getDebitoFiscalTransitorio().getIdEntidad() != null) {
                cdft = findParValor("CDFT");
                cdft.setValor(comprasYVentasWrapper.getDebitoFiscalTransitorio().getIdEntidad().toString());
                cdft.setUsuarioModificacion(usuarioModificacion);
                cdft.setFechaModificacion(new Date());
                mergeParValor(cdft);
            }

            //Cargando los datos de Cuentas de Ajuste
            ParValor arei, acm3, dca3, acap, ajrp;

            if (cuentasDeAjusteWrapper.getInflacionResulPorExposicionInflacion().getIdEntidad() != null) {
                arei = findParValor("AREI");
                arei.setValor(cuentasDeAjusteWrapper.getInflacionResulPorExposicionInflacion().getIdEntidad().toString());
                arei.setUsuarioModificacion(usuarioModificacion);
                arei.setFechaModificacion(new Date());
                mergeParValor(arei);
            }

            if (cuentasDeAjusteWrapper.getAjusteCorreccionMonetaria().getIdEntidad() != null) {
                acm3 = findParValor("ACM3");
                acm3.setValor(cuentasDeAjusteWrapper.getAjusteCorreccionMonetaria().getIdEntidad().toString());
                acm3.setUsuarioModificacion(usuarioModificacion);
                acm3.setFechaModificacion(new Date());
                mergeParValor(acm3);
            }

            if (cuentasDeAjusteWrapper.getDiferenciaCambio().getIdEntidad() != null) {
                dca3 = findParValor("DCA3");
                dca3.setValor(cuentasDeAjusteWrapper.getDiferenciaCambio().getIdEntidad().toString());
                dca3.setUsuarioModificacion(usuarioModificacion);
                dca3.setFechaModificacion(new Date());
                mergeParValor(dca3);
            }

            if (cuentasDeAjusteWrapper.getAjusteCapital().getIdEntidad() != null) {
                acap = findParValor("ACAP");
                acap.setValor(cuentasDeAjusteWrapper.getAjusteCapital().getIdEntidad().toString());
                acap.setUsuarioModificacion(usuarioModificacion);
                acap.setFechaModificacion(new Date());
                mergeParValor(acap);
            }

            if (cuentasDeAjusteWrapper.getAjusteReservasPatrimoniales().getIdEntidad() != null) {
                ajrp = findParValor("AJRP");
                ajrp.setValor(cuentasDeAjusteWrapper.getAjusteReservasPatrimoniales().getIdEntidad().toString());
                ajrp.setUsuarioModificacion(usuarioModificacion);
                ajrp.setFechaModificacion(new Date());
                mergeParValor(ajrp);
            }

            //Cargando los datos de Retenciones I.U.
            ParValor crbi, crsv, ctrs, ctrb, crit, crci, crae, rsie, crsi;

            if (retencionesIUEWrapper.getPorcentajeRetencionesBienes() != null) {
                crbi = findParValor("CRBI");
                crbi.setValor(retencionesIUEWrapper.getPorcentajeRetencionesBienes().toString());
                crbi.setUsuarioModificacion(usuarioModificacion);
                crbi.setFechaModificacion(new Date());
                mergeParValor(crbi);
            }

            if (retencionesIUEWrapper.getPorcentajeRetencionesServicios() != null) {
                crsv = findParValor("CRSV");
                crsv.setValor(retencionesIUEWrapper.getPorcentajeRetencionesServicios().toString());
                crsv.setUsuarioModificacion(usuarioModificacion);
                crsv.setFechaModificacion(new Date());
                mergeParValor(crsv);
            }

            if (retencionesIUEWrapper.getCuentaIUERetencionesServicios().getIdEntidad() != null) {
                ctrs = findParValor("CTRS");
                ctrs.setValor(retencionesIUEWrapper.getCuentaIUERetencionesServicios().getIdEntidad().toString());
                ctrs.setUsuarioModificacion(usuarioModificacion);
                ctrs.setFechaModificacion(new Date());
                mergeParValor(ctrs);
            }

            if (retencionesIUEWrapper.getCuentaIUERetencionesBienes().getIdEntidad() != null) {
                ctrb = findParValor("CTRB");
                ctrb.setValor(retencionesIUEWrapper.getCuentaIUERetencionesBienes().getIdEntidad().toString());
                ctrb.setUsuarioModificacion(usuarioModificacion);
                ctrb.setFechaModificacion(new Date());
                mergeParValor(ctrb);
            }

            if (retencionesIUEWrapper.getCuentaITRetenciones().getIdEntidad() != null) {
                crit = findParValor("CRIT");
                crit.setValor(retencionesIUEWrapper.getCuentaITRetenciones().getIdEntidad().toString());
                crit.setUsuarioModificacion(usuarioModificacion);
                crit.setFechaModificacion(new Date());
                mergeParValor(crit);
            }

            if (retencionesIUEWrapper.getCuentaRetencionesRCIVA().getIdEntidad() != null) {
                crci = findParValor("CRCI");
                crci.setValor(retencionesIUEWrapper.getCuentaRetencionesRCIVA().getIdEntidad().toString());
                crci.setUsuarioModificacion(usuarioModificacion);
                crci.setFechaModificacion(new Date());
                mergeParValor(crci);
            }

            if (retencionesIUEWrapper.getCuentaRetencionesExterior().getIdEntidad() != null) {
                crae = findParValor("CRAE");
                crae.setValor(retencionesIUEWrapper.getCuentaRetencionesExterior().getIdEntidad().toString());
                crae.setUsuarioModificacion(usuarioModificacion);
                crae.setFechaModificacion(new Date());
                mergeParValor(crae);
            }

            if (retencionesIUEWrapper.getRetencionIUEITSectorIndustrialExporta() != null) {
                rsie = findParValor("RSIE");
                rsie.setValor(retencionesIUEWrapper.getRetencionIUEITSectorIndustrialExporta().toString());
                rsie.setUsuarioModificacion(usuarioModificacion);
                rsie.setFechaModificacion(new Date());
                mergeParValor(rsie);
            }

            if (retencionesIUEWrapper.getCuentaRetencionIUEITSectorIndustrial().getIdEntidad() != null) {
                crsi = findParValor("CRSI");
                crsi.setValor(retencionesIUEWrapper.getCuentaRetencionIUEITSectorIndustrial().getIdEntidad().toString());
                crsi.setUsuarioModificacion(usuarioModificacion);
                crsi.setFechaModificacion(new Date());
                mergeParValor(crsi);
            }

        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
//            throw e;
        }
    }

    public List<ParAjustes> listaTiposDeAjuste(String tipoDeAjuste) {
        String consulta = "";
        if (EnumTipoAjuste.ACTUAL.getCodigo().equals(tipoDeAjuste)) {
            consulta = "where j.tipoDato = '" + EnumTipoAjuste.ACTUAL.getCodigo() + "' or j.tipoDato = '" + EnumTipoAjuste.AMBOS.getCodigo() + "' ";
        } else {
            consulta = "where j.tipoDato = '" + EnumTipoAjuste.ANTERIOR.getCodigo() + "' or j.tipoDato = '" + EnumTipoAjuste.AMBOS.getCodigo() + "' ";
        }
        List<ParAjustes> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParAjustes j "
                + "" + consulta + "");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParTipoComprobante> listaTiposComprobantes() {
        List<ParTipoComprobante> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParTipoComprobante j");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParSucursal> listaTodasLasSucursal() {
        List<ParSucursal> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParSucursal j");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParTipoRetencion> listaTipoRetencion() {
        List<ParTipoRetencion> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParTipoRetencion j where j.codigo!='SRET'");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.emptyList();
    }

    public List<ParTipoFacturacion> listaLosTiposDeFactura() {
        List<ParTipoFacturacion> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParTipoFacturacion j ");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.emptyList();
    }

    public String encuentraNumeroAutorizacionSucursal(ParSucursal parSucursal) {
        List<ParSucursal> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParSucursal j"
                + "where j..descripcion='" + parSucursal.getDescripcion() + "'");
        if (!lista.isEmpty()) {
            return lista.get(0).getAutorizacion();
        }
        return null;

    }

    public ParSucursal encuentraParSucursal(String descripcion) {
        List<ParSucursal> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParSucursal j "
                + "where j.descripcion='" + descripcion + "'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public boolean encuentraParSucursalPorNumeroAutorizacion(String numeroAutorizacion) {
        List<ParSucursal> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParSucursal j "
                + "where j.autorizacion='" + numeroAutorizacion + "'");
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public ParValor encuentraParvalorParSucursal(ParSucursal parSucuarsal) {
        List<ParValor> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParValor j "
                + "where j.contexto='SUCURSAL' and j.codigo='" + parSucuarsal.getCodigo() + "'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public List<ParRecetasDistribucionCentroCosto> listaDistribucionCentroCostos() {
        List<ParRecetasDistribucionCentroCosto> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParRecetasDistribucionCentroCosto j "
                + "where j.fechaBaja is null");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.emptyList();
    }

    //encuentra ultimo numero de recetas de distribucion de centro de costos
    public Long ultimoRegistroRecetaParValor() {
        List<ParRecetasDistribucionCentroCosto> list = hibernateTemplate.find(
                "select j "
                + "from ParRecetasDistribucionCentroCosto j "
                + "where j.fechaBaja is null "
                + "order by j.codigo desc");
        if (!list.isEmpty()) {
            return (Long.parseLong(list.get(0).getCodigo()) + 1L);
        }
        return 1L;
    }

    public String verificaValoresFormDistribucion(ParValor parValor) {

        return "OK";
    }

    public List<CntConfiguracionCentrocosto> listaConfiguracionCentroCostoPorReceta(String codigoReceta) {
        try {
            List<CntConfiguracionCentrocosto> lista = hibernateTemplate.find(""
                    + "select o "
                    + "from CntConfiguracionCentrocosto o where o.fechaBaja is null and o.codigoDistribucionCentroCosto='" + Long.parseLong(codigoReceta) + "'");
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    //valida nombre receta

    public boolean validaNombreRepetido(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) {
        List<ParRecetasDistribucionCentroCosto> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParRecetasDistribucionCentroCosto j "
                + "where j.fechaBaja is null and  j.descripcion like'" + parRecetasDistribucionCentroCosto.getDescripcion() + "'");
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public ParValor encuentraParvalorCuentaGeneralPorCodigo(String codigo) {
        List<ParValor> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParValor j "
                + "where j.contexto='CUENTA_GENERAL' and j.codigo='" + codigo + "'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    //lista cuentas generales sin asignar 
    public List<ParCuentasGenerales> listaCuentasGenerales() {
        try {
            List<ParCuentasGenerales> lista = hibernateTemplate.find(""
                    + "select o "
                    + "from ParCuentasGenerales o where o.valor='0'");
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Boolean verificaExistenciaDeCuentaEnDefiniconDeCuentas(String valorNivel) {
        if (!valorNivel.isEmpty() || !valorNivel.equals("")) {
            List<ParCuentasGenerales> lista = hibernateTemplate.find(""
                    + "select o "
                    + "from ParCuentasGenerales o "
                    + "where o.valor='" + valorNivel + "'");
            return !lista.isEmpty() ? false : true;
        } else {
            return false;
        }
    }

    public List<ParCuentasGeneralesPojo> listaCuentasGeneralesPojo() {
        List<ParCuentasGeneralesPojo> listaPojo = new ArrayList<ParCuentasGeneralesPojo>();
        ParCuentasGeneralesPojo parCuentasGeneralesPojo = new ParCuentasGeneralesPojo();
        for (ParCuentasGenerales parCuentasGenerales : listaCuentasGenerales()) {
            ParCuentasGeneralesPojo parCuentasGeneralesPojoAux = (ParCuentasGeneralesPojo) parCuentasGeneralesPojo.clone();
            parCuentasGeneralesPojoAux.setParCuentasGenerales(parCuentasGenerales);
            parCuentasGeneralesPojoAux.setSeleccionado(Boolean.FALSE);
            listaPojo.add(parCuentasGeneralesPojoAux);
        }
        return listaPojo;
    }

    public List<ParEstadoProyecto> listaEstadosDeProyectos() {
        List<ParEstadoProyecto> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParEstadoProyecto j ");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public boolean verificaSiExistenCuentasGenerales() {
        List<ParCuentasGenerales> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParCuentasGenerales j "
                + "where j.valor <> 0");
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<CntConfiguracionCentrocosto> listaDistribucionCentroDeCostoReceta(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) {
        List<CntConfiguracionCentrocosto> lista = hibernateTemplate.find(""
                + "select c "
                + "from CntConfiguracionCentrocosto c "
                + "where fechaBaja is null "
                + "and codigoDistribucionCentroCosto=" + parRecetasDistribucionCentroCosto.getCodigo() + "");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public ParRecetasDistribucionCentroCosto findParRecetasDistribucionCentroCosto(String codigo) {
        List<ParRecetasDistribucionCentroCosto> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParRecetasDistribucionCentroCosto j where j.codigo='" + codigo + "'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return new ParRecetasDistribucionCentroCosto();
    }

    public Boolean verificaRecetaDetalleComprobante(CntConfiguracionCentrocosto cntConfiguracionCentrocosto) {
        List<CntDistribucionCentrocosto> lista = hibernateTemplate.find(""
                + "select c "
                + "from CntDistribucionCentrocosto c "
                + "where c.idConfiguracionCentrocosto='" + cntConfiguracionCentrocosto.getIdConfiguracionCentrocosto() + "' "
                + "and c.fechaBaja is null");
        if (!lista.isEmpty()) {
            return true;
        }
        return false;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void eliminaRecetaCentroCostos(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) {
        int sw = 0;
        List<CntConfiguracionCentrocosto> listaHijosReceta = listaDistribucionCentroDeCostoReceta(parRecetasDistribucionCentroCosto);
        for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : listaHijosReceta) {
            if (!verificaRecetaDetalleComprobante(cntConfiguracionCentrocosto)) {
                sw = 1;
                cntConfiguracionCentrocosto.setFechaBaja(parRecetasDistribucionCentroCosto.getFechaBaja());
                cntConfiguracionCentrocosto.setUsuarioBaja(parRecetasDistribucionCentroCosto.getUsuarioBaja());
                try {
                    cntConfiguracionCentroCostoService.removeCntConfiguracionCentroCosto(cntConfiguracionCentrocosto);
                } catch (Exception ex) {
                }
            }
        }
        if (sw == 1) {
            ParValor parValor = findParValor(parRecetasDistribucionCentroCosto.getCodigo());
            parValor.setFechaBaja(parRecetasDistribucionCentroCosto.getFechaBaja());
            parValor.setUsuarioBaja(parRecetasDistribucionCentroCosto.getUsuarioBaja());
            try {
                removeParValor(parValor);
            } catch (Exception ex) {
                Logger.getLogger(ParParametricasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Boolean verificaRecetaDetalleComprobante(ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto) {
        int sw = 0;
        List<CntConfiguracionCentrocosto> listaHijosReceta = listaDistribucionCentroDeCostoReceta(parRecetasDistribucionCentroCosto);
        for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : listaHijosReceta) {
            if (verificaRecetaDetalleComprobante(cntConfiguracionCentrocosto)) {
                sw = 1;
            }
        }
        if (sw == 1) {
            return true;
        } else {
            return false;
        }
    }
//aumentado para parametros de cuentas por pagar    

    public List<ParValor> listadoBancoParValor() {
        List<ParValor> lista = hibernateTemplate.find(""
                + "select c"
                + " from ParValor c "
                + ""
                + "where c.contexto='BANCO' and fechaBaja is null");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParCiudad> listadoCiudad() {
        List<ParCiudad> lista = hibernateTemplate.find("select c from ParCiudad c");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParBanco> listadoBanco() {
        List<ParBanco> lista = hibernateTemplate.find("select c from ParBanco c");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParLugarDeEntrega> listadoLugarEntrega() {
        List<ParLugarDeEntrega> lista = hibernateTemplate.find("select c from ParLugarDeEntrega c");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParTipoDocIdentidad> listadoParTipoDocIdentidad() {
        List<ParTipoDocIdentidad> lista = hibernateTemplate.find("select c from ParTipoDocIdentidad c");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParTipoDocIdentidad> listaTiposDocumentosDeIdentidad() {
        List<ParTipoDocIdentidad> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParTipoDocIdentidad j ");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParValor> cargaListadoParametricas(int valor) {
        List<ParValor> lista = null;
        switch (valor) {
            case 1:
                lista = listadoBancoParValor();
                break;
            case 2:
                lista = listadoCiudadParValor();
                break;
            case 3:
                lista = listadoLugarEntregaParValor();
                break;
            case 4:
                lista = listadoParTipoDocIdentidadParValor();
                break;
            default:
                break;
        }
        return lista;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaParametros(ParValor parValor, int numero) throws Exception {
        ParValor parValorNuevo = new ParValor();
        switch (numero) {
            case 1:
                parValorNuevo.setContexto("BANCO");
                break;
            case 2:
                parValorNuevo.setContexto("CIUDAD");
                break;
            case 3:
                parValorNuevo.setContexto("LUGAR_DE_ENTREGA");
                break;
            case 4:
                parValorNuevo.setContexto("TIPO_DOC_IDENTIDAD");
                break;
            default:
                break;
        }
        parValorNuevo.setCodigo(parValor.getCodigo());
        parValorNuevo.setDescripcion(parValor.getDescripcion());
        parValorNuevo.setUsuarioAlta(parValor.getUsuarioAlta());
        parValorNuevo.setFechaAlta(parValor.getFechaAlta());
        persistParValor(parValorNuevo);
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void modificaParParametros(ParValor parValor, int numero) {
        merge(parValor);
    }

    public String verificaExistenciaDeCodigo(ParValor parValor) {
        if (!parValor.getCodigo().equals("")) {
            List<ParValor> lista = hibernateTemplate.find(""
                    + "select c "
                    + "from ParValor c "
                    + "where c.fechaBaja is null "
                    + "and c.codigo = '" + parValor.getCodigo() + "' ");
            if (!lista.isEmpty()) {
                return "El codigo " + parValor.getCodigo() + " ya existe.";
            } else {
                return "OK";
            }
        } else {
            return "Debe llenar El codigo.";
        }
    }

    public String verificaExistenciaDescripcion(ParValor parValor) {
        if (!parValor.getCodigo().equals("")) {
            List<ParValor> lista = hibernateTemplate.find(""
                    + "select c "
                    + "from ParValor c "
                    + "where c.fechaBaja is null "
                    + "and c.descripcion = '" + parValor.getDescripcion() + "' ");
            if (!lista.isEmpty()) {
                return "El codigo " + parValor.getDescripcion() + " ya existe.";
            } else {
                return "OK";
            }
        } else {
            return "Debe llenar La Descripcion.";
        }
    }

    public List<ParValor> listadoCiudadParValor() {
        List<ParValor> lista = hibernateTemplate.find(""
                + "select c"
                + " from ParValor c "
                + "where c.contexto='CIUDAD' and fechaBaja is null");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParValor> listadoLugarEntregaParValor() {
        List<ParValor> lista = hibernateTemplate.find(""
                + "select c "
                + "from ParValor c "
                + "where c.contexto='LUGAR_DE_ENTREGA' and fechaBaja is null");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParValor> listadoParTipoDocIdentidadParValor() {
        List<ParValor> lista = hibernateTemplate.find(""
                + "select c "
                + "from ParValor c "
                + "where c.contexto='TIPO_DOC_IDENTIDAD' and fechaBaja is null");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public List<CntDefinicionCuentasMigracion> listaDefinicionCuentas(List<CntEntidad> lista) {
        List<CntDefinicionCuentasMigracion> listaDefinicion = new ArrayList<CntDefinicionCuentasMigracion>();
        for (CntEntidad cntEntidad : lista) {
            CntDefinicionCuentasMigracion cntDefinicionCuentasMigracion = new CntDefinicionCuentasMigracion();
            cntDefinicionCuentasMigracion.setCntEntidad(cntEntidad);
            cntDefinicionCuentasMigracion.setCodigoCuentaGeneral("--SELECCIONAR--");
            listaDefinicion.add(cntDefinicionCuentasMigracion);
        }
        if (!listaDefinicion.isEmpty()) {
            return listaDefinicion;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ParCuentasGenerales> listaCuentasGeneralesParametricas() {
        List<ParCuentasGenerales> lista;
        ParCuentasGenerales vacia = new ParCuentasGenerales();
        vacia.setCodigo(null);
        vacia.setDescripcion("--SELECCIONAR--");
        lista = hibernateTemplate.find(""
                + "select j "
                + "from ParCuentasGenerales j ");
        lista.add(0, vacia);
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public void guardaDefinicionDeCuentasMigracion(List<CntDefinicionCuentasMigracion> listaCuentasDefinicionMigracion) {
        ParValor cuenta;
        for (CntDefinicionCuentasMigracion cntDefinicionCuentasMigracion : listaCuentasDefinicionMigracion) {
            cuenta = findParValor(cntDefinicionCuentasMigracion.getCodigoCuentaGeneral());
            cuenta.setValor(cntDefinicionCuentasMigracion.getCntEntidad().getMascaraGenerada().substring(0, 1));
            try {
                mergeParValor(cuenta);
            } catch (Exception ex) {
                Logger.getLogger(ParParametricasServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Boolean validaListaDefinicionCuentas(List<CntDefinicionCuentasMigracion> listaCuentasDefinicionMigracion) {
        for (CntDefinicionCuentasMigracion cntDefinicionCuentasMigracion : listaCuentasDefinicionMigracion) {
            if (cntDefinicionCuentasMigracion.getCodigoCuentaGeneral().equals("x")) {
                return false;
            }
        }
        return true;
    }

    public Boolean validaCuentasRepetidas(List<CntDefinicionCuentasMigracion> listaCuentasDefinicionMigracion) {
        int contador;
        for (CntDefinicionCuentasMigracion cntDefinicionCuentasMigracion : listaCuentasDefinicionMigracion) {
            contador = 0;
            for (CntDefinicionCuentasMigracion cntDefinicionCuentasMigracion1 : listaCuentasDefinicionMigracion) {
                if (cntDefinicionCuentasMigracion.getCodigoCuentaGeneral().equals(cntDefinicionCuentasMigracion1.getCodigoCuentaGeneral())) {
                    contador++;
                }
            }
            if (contador > 1) {
                return false;
            }
        }
        return true;
    }

    public ParCuentasGenerales findByCodigo(String codigo) {
        List<ParCuentasGenerales> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParCuentasGenerales j "
                + "where j.codigo = '" + codigo + "'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public List<ParCuentasGenerales> listaCuentasNoElegidas(List<CntDefinicionCuentasMigracion> listaElegidos) {
        List<ParCuentasGenerales> listaResultante = new ArrayList<ParCuentasGenerales>();
        for (ParCuentasGenerales parCuentasGenerales : listaCuentasGeneralesParametricas()) {
            if (!existeEnLista(listaElegidos, parCuentasGenerales)) {
                listaResultante.add(parCuentasGenerales);
            }
        }
        return listaResultante;
    }

    public Boolean existeEnLista(List<CntDefinicionCuentasMigracion> listaElegidos, ParCuentasGenerales cuenta) {
        for (CntDefinicionCuentasMigracion cntDefinicionCuentasMigracion : listaElegidos) {
            if (cntDefinicionCuentasMigracion.getCodigoCuentaGeneral().equals(cuenta.getCodigo())) {
                return true;
            }
        }
        return false;
    }

    public ParIpWebServiceWamsa findParValorContextoWebService(String codigo) {
        List<ParIpWebServiceWamsa> lista = hibernateTemplate.find(" "
                + "select j "
                + "from ParIpWebServiceWamsa j "
                + "where j.codigo ='" + codigo + "'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public ParValor findParValorContextoWebServices(String codigo) {
        List<ParValor> lista = hibernateTemplate.find(" "
                + "select j "
                + "from ParValor j "
                + "where j.codigo ='" + codigo + "' and j.contexto='IP_WEB_SERVICE_WAMSA'");
        if (!lista.isEmpty()) {
            return lista.get(0);
        }
        return null;
    }

    public Boolean validaNumeroIp(String ip) throws Exception {
        try {
            if (ip == null || ip.trim().length() < 7) {
                return false;
            }
            String[] temp = ip.split("\\.");
            if (temp.length != 4) {
                return false;
            }
            for (String oct : temp) {
                if (!isNumeric(oct)) {
                    return false;
                }
            }
            int[] dir = new int[]{
                Integer.parseInt(temp[0]),
                Integer.parseInt(temp[1]),
                Integer.parseInt(temp[2]),
                Integer.parseInt(temp[3])
            };
            if (dir[0] > 254 || dir[0] <= 0
                    || dir[1] > 254 || dir[1] < 0
                    || dir[2] > 254 || dir[2] < 0
                    || dir[3] > 254 || dir[3] <= 0) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            throw new Exception(
                    "Error en el metodo: esIPv4( String )\n - " + ex.getMessage());
        }
    }

    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;

        }
    }

    public Boolean verificaParametrizacionParaContabilizacionAutomatica() {
        List<ParComprasYVentas> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParComprasYVentas j "
                + "where j.valor = null");
        if (lista.isEmpty()) {
            List<ParCuentasDeAjuste> lista2 = hibernateTemplate.find(""
                    + "select o "
                    + "from ParCuentasDeAjuste o "
                    + "where o.valor = null");
            if (lista2.isEmpty()) {
                List<ParRetencionIue> lista3 = hibernateTemplate.find(""
                        + "select n "
                        + "from ParRetencionIue n "
                        + "where n.valor = null");
                if (lista3.isEmpty()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean verificaSiUnaCuentaEstaEnParametrosDeGestion(CntEntidad cntEntidad) {
        List<ParComprasYVentas> listaComprasYVentas = hibernateTemplate.find(""
                + "select j "
                + "from ParComprasYVentas j "
                + "where j.tipoDato = 'ID' "
                + "and j.valor = '" + cntEntidad.getIdEntidad() + "'");
        List<ParCuentasDeAjuste> listaCuentasDeAjuste = hibernateTemplate.find(""
                + "select j "
                + "from ParCuentasDeAjuste j "
                + "where j.tipoDato = 'ID' "
                + "and j.valor = '" + cntEntidad.getIdEntidad() + "'");
        List<ParRetencionIue> listaRetencionIue = hibernateTemplate.find(""
                + "select j "
                + "from ParRetencionIue j "
                + "where j.tipoDato = 'ID' "
                + "and j.valor = '" + cntEntidad.getIdEntidad() + "'");
        if (!listaComprasYVentas.isEmpty() || !listaCuentasDeAjuste.isEmpty() || !listaRetencionIue.isEmpty()) {
            return true;
        }
        return false;
    }

//    public Boolean verificaSiFaltaLlenarParametrosDeGestion() {
//        List<ParComprasYVentas> listaComprasYVentas = hibernateTemplate.find(""
//                + "select j "
//                + "from ParComprasYVentas j "
//                + "where j.tipoDato = 'ID' "
//                + "and j.valor is null");        
//        List<ParCuentasDeAjuste> listaCuentasDeAjuste = hibernateTemplate.find(""
//                + "select j "
//                + "from ParCuentasDeAjuste j "
//                + "where j.tipoDato = 'ID' "
//                + "and j.valor is null");        
//        List<ParRetencionIue> listaRetencionIue = hibernateTemplate.find(""
//                + "select j "
//                + "from ParRetencionIue j "
//                + "where j.tipoDato = 'ID' "
//                + "and j.valor is null ");                
//        if(!listaComprasYVentas.isEmpty() || !listaCuentasDeAjuste.isEmpty() || !listaRetencionIue.isEmpty()){
//            return true;
//        }
//        return false;
//    }
    public Boolean estaHabilitadoCCenDatosDeEmpresa() {
        List<ParVarios> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParVarios j "
                + "where j.codigo = '" + EnumEmpresa_Varios.CENTROS_DE_COSTO.getCodigo() + "'");
        return lista.get(0).getValor().equals("1");
    }

    public List<ParCuentasGenerales> listaTodasLasCuentasGenerales() {
        List<ParCuentasGenerales> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParCuentasGenerales j");
        if (!lista.isEmpty()) {
            return lista;
        }
        return Collections.EMPTY_LIST;
    }

    public String devuelveMascaraPorTipoCuenta(String codigo) {
        List<ParCuentasGenerales> lista = hibernateTemplate.find(""
                + "select j "
                + "from ParCuentasGenerales j "
                + "where j.codigo = '" + codigo + "'");
        if (!lista.isEmpty()) {
            return lista.get(0).getValor();
        }
        return "";
    }

    public ParParametrosServidor findParametrosServidor(String codigo) throws Exception {
        try {
            List<ParParametrosServidor> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from ParParametrosServidor j "
                    + "where j.codigo = '" + codigo + "'");
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return new ParParametrosServidor();
    }

    public void modificaParametrosServidor(String urlAutenticacion) throws Exception {
        try {
            ParValor parUrlAutenticacion = findParValor("HOST");
            parUrlAutenticacion.setValor(urlAutenticacion);
            mergeParValor(parUrlAutenticacion);
        } catch (Exception e) {
            throw e;
        }
    }  
        

    public ParTipoComprobante parTipoComprobante(String codigo) throws Exception {
        try {
            List<ParTipoComprobante> lista = hibernateTemplate.find(""
                    + "select j "
                    + "from ParTipoComprobante j "
                    + "where j.codigo = '" + codigo + "'");
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return new ParTipoComprobante();
    }

    public List<ParValor> getParametrosActiDirList() {
        String query = "select h "
                + "from ParValor h "
                + "where h.contexto='ACTIVEDIRECTORY' and h.fechaBaja is null";
        return hibernateTemplate.find(query);
    }

    public ParParametrosActiveDirectory findParametrosActiveDirectory(String codigo) throws Exception {
        try {
            return (ParParametrosActiveDirectory) find(ParParametrosActiveDirectory.class, codigo);
        } catch (Exception e) {
            throw e;
        }
    }

    public void modificaParametrosActiveDirectory(String host, String dominio) throws Exception {
        try {
            System.out.println("entra a metodo de modificacion!!!!");
            ParValor parActiveDirectoryHost = findParValor("HOST");
            parActiveDirectoryHost.setValor(host);
            mergeParValor(parActiveDirectoryHost);
            ParValor parActiveDirectoryDominio = findParValor("DOM");
            parActiveDirectoryDominio.setValor(dominio);
            mergeParValor(parActiveDirectoryDominio);
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean verificaPeriodoYAnioActual(Date fechaActual) throws Exception {
        ParGestionContable periodoActual = (ParGestionContable) find(ParGestionContable.class, "PACT");
        ParGestionContable anioActual = (ParGestionContable) find(ParGestionContable.class, "AACT");
        System.out.println("periodoActual:::: " + periodoActual.getValor());
        System.out.println("anioActual:::: " + anioActual.getValor());
        int mes = fechaActual.getMonth() + 1;
        System.out.println("periodoFecha::" + mes);
        int anio = fechaActual.getYear() + 1900;
        System.out.println("anioFecha::" + anio);
        return mes == Integer.parseInt(periodoActual.getValor()) && anio == Integer.parseInt(anioActual.getValor());
    }
}
