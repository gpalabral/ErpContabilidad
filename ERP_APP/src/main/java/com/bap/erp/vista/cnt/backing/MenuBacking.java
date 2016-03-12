package com.bap.erp.vista.cnt.backing;

import com.bap.erp.modelo.enums.EnumGruposNivel;
import com.bap.erp.modelo.enums.EnumTipoUsuarioLogin;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.vista.common.AbstractManagedBean;
import com.bap.erp.vista.utils.MessageUtils;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Henrry
 */
@ManagedBean(name = "menuBacking")
@ViewScoped
public class MenuBacking extends AbstractManagedBean implements Serializable {

    @ManagedProperty(value = "#{parParametricasService}")
    private ParParametricasService parParametricasService;

    public MenuBacking() {
    }

    @PostConstruct
    public void initMenuBacking() {
    }

    public String tipoDeCambio() {
        limpiarVariablesSession();
        return "tipoCambioList";
    }

    public String nivelesPlanCuentas() {
        limpiarVariablesSession();
        return "nivelesMascara";
    }

    public String definicionCuentas() {
        limpiarVariablesSession();
        return "definicionCuentas";
    }

    public String parametrosDeGestion() {
        limpiarVariablesSession();
        return "parametrosDeGestion";
    }

    public String parametrosGestion() {
        limpiarVariablesSession();
        return "parametrosGestion";
    }

    public String parametrosServidor() {
        limpiarVariablesSession();
        return "parametrosServidor";
    }
    
    public String parametrosActiveDirectory() {
        System.out.println("param Active Dir");
        limpiarVariablesSession();
        return "configuracionActiveDirectory";
    }

    public String auxiliares() {
        limpiarVariablesSession();
        return "auxiliares";
    }

    public String proyectos() {
        limpiarVariablesSession();
        return "proyectos";
    }

    public String entidadAdicionar() {
        limpiarVariablesSession();
        setInSessionTipoDeGrupoNivel(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
        return "planCuentas";
    }

    public String planDeCuentasParametrizado() {
        limpiarVariablesSession();
//        setInSessionTipoDeGrupoNivel(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
        return "planCuentasParametrizado";
    }

    public String planCuentasParametrizadoAjustes() {
        limpiarVariablesSession();
//        setInSessionTipoDeGrupoNivel(EnumGruposNivel.PLAN_CUENTAS.getCodigo());
        return "planCuentasParametrizadoAjustes";
    }

    public String centrosDeCosto() {
        limpiarVariablesSession();
        setInSessionTipoDeGrupoNivel(EnumGruposNivel.CENTROS_COSTOS.getCodigo());
        return "centrosDeCosto";
    }

    public String asignacionCentrosDeCosto() {
        limpiarVariablesSession();
        return "asignacionCentroDeCosto";
    }

    public String mascaraCentrosDeCosto() {
        limpiarVariablesSession();
        return "nivelesMascaraCentroCosto";
    }

    public String distribucionDeCentroDeCosto() {
        limpiarVariablesSession();
        return "distribucionDeCentroDeCosto";
    }

    public String empresa() {
        limpiarVariablesSession();
        return "empresa";
    }

    public String centroCosto() {
        limpiarVariablesSession();
        return "centrosDeCosto";
    }

    public String comprobantesList() {
//        setInSessionIdComprobante(null);
//        setInSessionIdDetalleComprobante(null);
        limpiarVariablesSession();
        if (parParametricasService.verificaParametrizacionParaContabilizacionAutomatica()) {
            return "comprobantesList";
        }
        MessageUtils.addErrorMessage("Debe llenar toda la Parametrizacion para contabilizacion Automatica");
        return "parametrosGestion";
    }

    public String comprobantesMigracion() {
        return "comprobantesMigracionForm";
    }

    public String datosEmpresa() {
        limpiarVariablesSession();
        return "datosEmpresa";
    }

    public String datosDeEmpresa() {
        limpiarVariablesSession();
        return "datosDeEmpresa";
    }

    public String facturaCompra() {
        limpiarVariablesSession();
        return "facturaCompra";
    }

    public String libroMayor() {
        limpiarVariablesSession();
        return "libro_Mayor";
    }

//    public String libroMayor() {
//        limpiarVariablesSession();
//        return "libroMayor";
//    }
    public String sumasSaldos() {
        limpiarVariablesSession();
        return "imprimeSumasSaldosComprobantes";
    }

    public String recetasCostos() {
        limpiarVariablesSession();
        return "listaDistribucionDeCentrosDeCosto";
    }

    public String reporteComprobantes() {
        limpiarVariablesSession();
        return "imprimeComprobante";
    }

    public String tipoProveedor() {
        limpiarVariablesSession();
        return "tipoProveedor";
    }

    public String parametricasCuentasPorPagar() {
        limpiarVariablesSession();
        return "parametricasCuentasPorPagar";
    }

    public String proveedoresList() {
        limpiarVariablesSession();
        return "proveedoresList";
    }

    public String cargaPlanCuentas() {
        limpiarVariablesSession();
        return "planCuentasMigracionForm";
    }

    public String configurarIpWebService() {
        limpiarVariablesSession();
        return "configuracionIP";
    }

    public String login() {
        limpiarVariablesSession();
        return "modificaPassword";
    }

    public String balanceGeneral() {
        limpiarVariablesSession();
        return "reporte_balanceGeneral";
    }

    public String estadoResultadosEERR() {
        limpiarVariablesSession();
        return "reporte_EERR";
    }
//    public String estadoResultadosEERR() {
//        limpiarVariablesSession();
//        return "reporteEERR";
//    }

    public String libroDeVentas() {
        limpiarVariablesSession();
        return "reporteLibroVentas";
    }

    public String libroDeCompras() {
        limpiarVariablesSession();
        return "reporteLibroCompras";
    }
    
    public String registroUsuario() {
        return "registroUsuarios";
    }
    
    public String listaUsuarios() {
        return "listaUsuarios";
    }
    
    public String cargaComprobantes() {
        limpiarVariablesSession();
        return "comprobantesDetallesMigracionForm";
    }
    
    public String cargaFacturas() {
        limpiarVariablesSession();
        return "facturaComprasVentasMigracionForm";
    }

    public Boolean esAdministrador() {
        if (getFromSessionTipoUsuario().equals(EnumTipoUsuarioLogin.ADMINISTRADOR.getCodigo())) {
            return true;
        }
        return false;
    }

    public ParParametricasService getParParametricasService() {
        return parParametricasService;
    }

    public void setParParametricasService(ParParametricasService parParametricasService) {
        this.parParametricasService = parParametricasService;
    }
}
