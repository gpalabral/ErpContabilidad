package com.bap.erp.modelo.clientes.cnf;

import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import java.util.Date;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ParParametricasCliente {

    public static void main(String args[]) {

        ApplicationContext context = new FileSystemXmlApplicationContext("//home//conejo//Develpoment//Projects//ERP-conta//ERP_APP//src//main//webapp//WEB-INF//applicationContext.xml");

        ParParametricasService parParametricasService = (ParParametricasService) context.getBean("parParametricasService");
        CntEntidadesService cntEntidadesService = (CntEntidadesService) context.getBean("cntEntidadesService");

//        ParTiposMovimiento parTipoMovimiento = (ParTiposMovimiento) parParametricasService.find(ParTiposMovimiento.class, "TXNI");
//        ObjectUtils.printObjectState(parTipoMovimiento);

        /*
         * Listado Datos empresa
         */
//        List<ParDatosEmpresa> list = parParametricasService.listaDatosDeEmpresa();
//        for (ParDatosEmpresa parDatosEmpresa : list) {
//            System.out.println("los Datos empresa son:::"+parDatosEmpresa.getDescripcion());
//        }
        /*
         * listado
         */
//        System.out.println("CUENTA: "+cuentasGeneralesService.generaCodigoCuentasGenrales("9-99-999",1));
//        DatosEmpresaWrapper datosEmpresaWrapper = parParametricasService.factoryEmpresa();
//        datosEmpresaWrapper.setRazonSocial("ZTE BOLIVIA S.R.L.");
//        
//        try {
//            parParametricasService.modifiacarDatosEmpresa(datosEmpresaWrapper);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("Convierte: "+Integer.toString(Boolean.valueOf(false).compareTo( false )));
//        System.out.println("Razon:"+datosEmpresaWrapper.getRazonSocial());
//        System.out.println("Dir:"+datosEmpresaWrapper.getDireccion());
//        System.out.println("Nit:"+datosEmpresaWrapper.getNit());
//         VariosWrapper variosWrapper=parParametricasService.factoryVarios();
////        System.out.print("Centro de costos:"+variosWrapper.getCentroDeCostos());
//        System.out.println("Centro de costos:" + variosWrapper.getCentroDeCostos());
//        System.out.println("Facturacion Computarizada:"+variosWrapper.getFacturacionComputarizada());
//        System.out.println("Cuentas Coorporativas:"+variosWrapper.getCuentasCoorporativas());
//        GestionContableWrapper gestionContableWrapper = parParametricasService.factoryGestionContable();
//        System.out.println("Ciudad" + gestionContableWrapper.getCiudad());
//        System.out.println("Inicio gestion fiscal" + gestionContableWrapper.getInicioGestionFiscal());
//        System.out.println("Tipo de cambio Oficial" + gestionContableWrapper.getTipoCambioInicial());
//        System.out.println("Norma contable" + gestionContableWrapper.getNormaContable3());
//        System.out.println("Periodo actual" + gestionContableWrapper.getPeriodoActual());
//        ComprasYVentasWrapper comprasYVentasWrapper = parParametricasService.factoryComprasYVentas();
//        System.out.println("iva------<" + comprasYVentasWrapper.getPorcentajeIVA());
//        System.out.println("it------>" + comprasYVentasWrapper.getPorcentajeIT());
//        System.out.println("pagoit------->" + comprasYVentasWrapper.getObligacionPagoIT());
//        System.out.println("debitofiscal----->" + comprasYVentasWrapper.getDebitoFiscal());
//        System.out.println("impuestos a la transacciones---->" + comprasYVentasWrapper.getImpuestoTransaccion());
//        CuentasDeAjusteWrapper cuentasDeAjusteWrapper = parParametricasService.factoryCuentasDeAjuste();
//        System.out.println("Diferencia de cambio---->" + cuentasDeAjusteWrapper.getDiferenciaCambio());
//        System.out.println("Ajuste por correlacion monetaria---->" + cuentasDeAjusteWrapper.getAjusteCorreccionMonetaria());
//        System.out.println("Ajuste de reservas patrimoniales---->" + cuentasDeAjusteWrapper.getAjusteReservasPatrimoniales());
//        RetencionesIUEWrapper retencionesIUEWrapper= parParametricasService.factoryRetencionesIUE();
//        System.out.println("Porcentaje de Bienes---->" + retencionesIUEWrapper.getPorcentajeRetencionesBienes());
//        System.out.println("Porcentaje de Retenciones en Servicios---->" + retencionesIUEWrapper.getPorcentajeRetencionesServicios());
//        System.out.println("Retenciones IUE IT Sector industrial Exporta ---->" + retencionesIUEWrapper.getRetencionIUEITSectorIndustrialExporta());
//        System.out.println("Retenciones IUE-ITSector industrial---->" + retencionesIUEWrapper.getCuentaRetencionIUEITSectorIndustrial());
//        
//        List<ParTipoComprobante> lista = parParametricasService.listaTiposComprobantes();
//        for (ParTipoComprobante parTipoComprobante : lista) {
//            System.out.println("los compro son:::"+parTipoComprobante.getDescripcion());
//        }
//        List<ParTipoFacturacion> lista = parParametricasService.listaLosTiposDeFactura();
//        for (ParTipoFacturacion parTipoRetencion : lista) {
//            System.out.println("Los campos de la vista son" +parTipoRetencion.getDescripcion()+"**********" +parTipoRetencion.getCodigo()+"");
//            
//        }
//        
//         ParTipoRetencion parTipoRetencion=(ParTipoRetencion)parParametricasService.find(ParTipoRetencion.class,"SRET");
//         System.out.println("Los campos de la vista son" +parTipoRetencion.getDescripcion());
//           
//       
//        System.out.println("ultimo numero "+parParametricasService.ultimoRegistroRecetaParValor());
//        System.out.println("Existe: "+parParametricasService.verificaExistenciaDeCuentaEnDefiniconDeCuentas("9"));
//        List<ParCuentasGenerales> listaCuentas=parParametricasService.listaCuentasGenerales();
//        for (ParCuentasGenerales parCuentasGenerales : listaCuentas) {
//            System.out.println("los codigos de las cuentas por asignar son"+parCuentasGenerales.getCodigo()+"///////////////////////////");
//        }
//        List<ParCuentasGeneralesPojo> listaCuentasPojo=parParametricasService.listaCuentasGeneralesPojo();
//        for (ParCuentasGeneralesPojo parCuentasGeneralesPojo : listaCuentasPojo) {
//            System.out.println("los codigos de las cuentas por asignar son"+parCuentasGeneralesPojo.getParCuentasGenerales().getDescripcion()+"///////////////////////////");
//            System.out.println("valor boolean"+parCuentasGeneralesPojo.getSeleccionado()+"///////////////////////////");
//        }
//        
//        System.out.println("Existe: "+parParametricasService.verificaExistenciaDeCuentaEnDefiniconDeCuentas(""));
//        List<ParEstadoProyecto> lista = parParametricasService.listaEstadosDeProyectos();
//        for (ParEstadoProyecto parEstadoProyecto : lista) {
//            System.out.println("los estados son:::"+parEstadoProyecto.getDescripcion());
//        }
//        System.out.println("tiene datos:::" + parParametricasService.verificaSiExistenCuentasGenerales());
//        ParRecetasDistribucionCentroCosto parRecetasDistribucionCentroCosto = parParametricasService.findParRecetasDistribucionCentroCosto("1");
//        List<CntConfiguracionCentrocosto> lista = parParametricasService.listaDistribucionCentroDeCostoReceta(parRecetasDistribucionCentroCosto);
//        for (CntConfiguracionCentrocosto cntConfiguracionCentrocosto : lista) {
//            System.out.println("el id es" + cntConfiguracionCentrocosto.getIdConfiguracionCentrocosto() + "");
//            System.out.println("la receta esta en un detalle comprobante" + parParametricasService.verificaRecetaDetalleComprobante(cntConfiguracionCentrocosto) + "");
//        }
//        List<EnumParametrosCuentasPorPagar>lista=parParametricasService.listaDeContextosParametros();
//        for (EnumParametrosCuentasPorPagar enumParametrosCuentasPorPagar : lista) {
//            System.out.println("el contexto es"+enumParametrosCuentasPorPagar.name()+"");
//        }
//        List<ParCiudad>lista=parParametricasService.listadoCiudad();
//        for (ParCiudad parCiudad : lista) {
//            System.out.println(" El nombre de la ciudad es "+parCiudad.getCodigo()+"-------------"+parCiudad.getDescripcion());
//            
//        }
//        
//        ParCuentasGenerales cta = parParametricasService.findByCodigo("ACT");
//        System.out.println("la cuenta es:::"+cta.getDescripcion());
//        CntDefinicionCuentasMigracion obj = new CntDefinicionCuentasMigracion();
//        obj.setCodigoCuentaGeneral("ACT");    
//        List<CntDefinicionCuentasMigracion> lista = new ArrayList<CntDefinicionCuentasMigracion>();
//        lista.add(obj);
//        for (ParCuentasGenerales parame : parParametricasService.listaCuentasNoElegidas(lista)) {
//            System.out.println("las cuentas son:::"+parame.getDescripcion());
//            
//        }
//            ParIpWebServiceWamsa parIpWebServiceWamsa=parParametricasService.findParValorContextoWebService("ACT");
//            System.out.println("los datos del ip son"+parIpWebServiceWamsa.getDireccionIp());
//            ParValor parValor=parParametricasService.findParValorContextoWebServices("ACT");
//            System.out.println("el id es ::"+parValor.getContexto());
//            String ip="254.245.12";
//            
//            System.out.println("comprobancion de numero ip"+parParametricasService.validaNumeroIp(ip));
//        CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, 0L);
//        if(parParametricasService.verificaSiUnaCuentaEstaEnParametrosDeGestion(cntEntidad)){
//            System.out.println("se encuentra");
//        }else{
//            System.out.println("no se encuentra");
//        }
//        System.out.println("varios");
//        List<ParVarios> lista = parParametricasService.listaVarios();
//        for (ParVarios lista1 : lista) {
//            System.out.println("son" + lista1.getDescripcion() + "esta en "+lista1.getValor());
//        }
        try {

            System.out.println("periodo act " + parParametricasService.verificaPeriodoYAnioActual(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
