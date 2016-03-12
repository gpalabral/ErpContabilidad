package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntFacturacion;
import com.bap.erp.modelo.entidades.cnt.CntPojoFacturacion;
import com.bap.erp.modelo.enums.EnumEstado;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntFacturacionService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntFacturacionCliente {

    public static void main(String args[]) throws Exception {
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\ProyectoERP2015\\ERP-conta\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("//home//henrry//CONTA GIT//ERP-conta//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
        CntFacturacionService cntFacturacionService = (CntFacturacionService) context.getBean("cntFacturacionService");
        CntDetalleComprobanteService cntDetalleComprobanteService = (CntDetalleComprobanteService) context.getBean("cntDetalleComprobanteService");
        ParParametricasService parParametricasService = (ParParametricasService) context.getBean("parParametricasService");

//        CntSucursal
//        
//        /*Listado*/        
//        List<CntFacturacion> lista = cntFacturacionService.listaCntFacturacion();
//        for (CntFacturacion cntFacturacion : lista) {
//            System.out.println("los elementos de la lista son---- "+cntFacturacion.getCreditoFiscalTransitorio());
//        }
        /*
         * Persist
         */
//        CntFacturacion cntFacturacion = new CntFacturacion();
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 280L);
//        ParTipoFacturacion parTipoFacturacion = (ParTipoFacturacion) parParametricasService.find(ParTipoFacturacion.class, "MINT");
//        ParParametrosAutorizacion parParametrosAutorizacion = (ParParametrosAutorizacion)parParametricasService.find(ParParametrosAutorizacion.class, "FACT");
//        ParSucursal parSucursal = (ParSucursal) parParametricasService.find(ParSucursal.class, "SUC1");
//        cntFacturacion.setCntDetalleComprobante(cntDetalleComprobante);
//        cntFacturacion.setMonto(cntDetalleComprobante.getDebe());
//        cntFacturacion.setExcento(BigDecimal.ZERO);
//        cntFacturacion.setIce(BigDecimal.ZERO);
//        cntFacturacion.setIva(new BigDecimal("13"));
//        cntFacturacion.setCreditoFiscalTransitorio("N");
//        cntFacturacion.setFechaFactura(new Date());
//        cntFacturacion.setNroInicial(12L);
////        cntFacturacion.setNroFinal(15L);
//        cntFacturacion.setParTipoFacturacion(parTipoFacturacion);
//        cntFacturacion.setParParametrosAutorizacion(parParametrosAutorizacion);                
////        cntFacturacion.setEstado("PEND");
//        cntFacturacion.setLoginUsuario("hguzman");
//        cntFacturacion.setParSucursal(parSucursal);
//        cntFacturacion.setUsuarioAlta("TEST");
//        cntFacturacion.setFechaAlta(new Date());        
//        cntFacturacionService.persist(cntFacturacion);        
//        BigDecimal primero = new BigDecimal("120");
//        BigDecimal segundo = new BigDecimal("0");
//        System.out.println("el resultado es"+cntFacturacionService.divideDosBigDecimal(primero, segundo));  
//        Float iva = new Float("12.35");
//        BigDecimal resul = new BigDecimal(iva);
//        System.out.println("resulta "+resul);
//        CntFacturacion cntFacturacion = new CntFacturacion();
//        cntFacturacion.setMonto(new BigDecimal("200"));
//        cntFacturacion.setExcento(new BigDecimal("25"));
//        cntFacturacion.setIce(new BigDecimal("0"));
//        BigDecimal excento = new BigDecimal("25");
//        BigDecimal ice = new BigDecimal("2");
//
////        System.out.println("el detalle es "+cntDetalleComprobanteService.find(CntDetalleComprobante.class,155L).getCntEntidad().getIdEntidad());
////        
//        cntFacturacion.setCntDetalleComprobante(cntDetalleComprobanteService.find(CntDetalleComprobante.class, 155L));
//        cntFacturacion = cntFacturacionService.obtieneIVA(cntFacturacion, excento, ice);
//        System.out.println("el EXCENTO ES:::" + cntFacturacion.getExcento());
//        System.out.println("el ICE ES:::" + cntFacturacion.getIce());
//        System.out.println("el IVA ES:::" + cntFacturacion.getIva());
//        cntFacturacion.setParParametrosAutorizacion();
//        System.out.println("el detalle es "+cntDetalleComprobanteService.find(CntDetalleComprobante.class,155L).getCntEntidad().getIdEntidad());
//        
//        cntFacturacion.setCntDetalleComprobante(cntDetalleComprobanteService.find(CntDetalleComprobante.class, 155L));
//        cntFacturacion = cntFacturacionService.obtieneIVA(cntFacturacion, excento, ice);
//        System.out.println("el EXCENTO ES:::" + cntFacturacion.getExcento());
//        System.out.println("el ICE ES:::" + cntFacturacion.getIce());
//        System.out.println("el IVA ES:::" + cntFacturacion.getIva());
//        cntFacturacionService.guardaFacturaDeCompra(cntFacturacion);
//        
//        CntFacturacion cntFacturacion=new CntFacturacion();
//         cntFacturacion.setMonto(new BigDecimal("1254.56"));
//        cntFacturacion.setMontoSegMoneda(new BigDecimal("1254.56"));
//        cntFacturacion.setExcento(new BigDecimal("148.141"));
//        cntFacturacion.setExcentoSegMoneda(new BigDecimal("1498"));
//        cntFacturacion.setIce(new BigDecimal("18.96"));
//        cntFacturacion.setIceSegMoneda(new BigDecimal("18.96"));
//        cntFacturacion.setIva(new BigDecimal("6.98"));
//        cntFacturacion.setIvaSegMoneda(new BigDecimal("6.98"));
//        cntFacturacion.setMovimiento("VENT");
//        cntFacturacion.setCntDetalleComprobante((CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, 443L));
//        cntFacturacion.setLoginUsuario("usuario");
//        cntFacturacion.setFechaAlta(new Date());
//        cntFacturacion.setUsuarioAlta("usuario");
//        cntFacturacion.setParParametrosAutorizacion(null);
//        cntFacturacion.setParTipoFacturacion(null);
//        cntFacturacion.setEstado(EnumEstado.PENDIENTE.getCodigo());
//        cntFacturacion.setSucursal("SUCURSAL DOS");
//        cntFacturacion.setNroInicial(12L);
//        cntFacturacion.setNroAutorizacion("15852");
////        cntFacturacion.setNroAutorizacion("213854126");
//        cntFacturacion.setCodigoControl("as-ds-df-fd-fd");
//        cntFacturacion.setRazonSocial("");
//        
//        
//        ParSucursal parSucursal=parParametricasService.encuentraParSucursal(cntFacturacion.getSucursal());
//        System.out.println("*******************************numero deo aoutorizacion***********************************");
//        System.out.println("SUCURSAL AUTORIZACION"+parSucursal.getAutorizacion()+"");
//        
//        System.out.println(""+parParametricasService.encuentraParSucursalPorNumeroAutorizacion(cntFacturacion.getNroAutorizacion())+"");
//        
//        System.out.println("codigo de sucursal"+parSucursal.getCodigo()+"");
//        ParValor parValor=(ParValor)parParametricasService.findParValor(parSucursal.getCodigo());
//        System.out.println("el objeto seleccionado tiene como id "+parValor.getIdValor()+"");
//        
//        parValor.setValor(cntFacturacion.getNroAutorizacion());
//        parValor.setUsuarioModificacion("TEST");
//        parValor.setFechaModificacion(new Date());
//        parValor=parParametricasService.mergeParValor(parValor);
//        System.out.println("nuevo numero de autorizacion" +parValor.getValor()+"");
//        
//        
//        
//        System.out.println("verifica campos de facturacion"+cntFacturacionService.validaCamposFacturaVenta(cntFacturacion)+"/////////////////////////////");
//        
//        System.out.println("monto"+cntFacturacion.getExcento()+"");
////        System.out.println("es decimal"+cntFacturacionService.esDecimal(cntFacturacion.getExcento().toString()) +"es decimal////////////////////////////////////////////");
//        
//        
//        
//        
//
////        cntFacturacion.setCntDetalleComprobante(cntDetalleComprobanteService.find(CntDetalleComprobante.class,157L));
////       // System.out.println("Calcula iva e it"+cntFacturacionService.obtieneIvaIT(new BigDecimal("1254.56"))+"");
////        cntFacturacionService.obtieneValoresSegundaMonedaFacturacion(cntFacturacion);
//        // cntFacturacionService.guardaCntFacturacion(cntFacturacion,"crojas");
//  
////        try {
////            cntFacturacionService.guardaFacturaDeVenta(cntFacturacion, "UsuarioTest");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
////        CntFacturacion cntFacturacion = cntFacturacionService.find(CntFacturacion.class, 69L);
////        System.out.println("es verdad???"+cntFacturacionService.activaCodigoDeControl(cntFacturacion));
////        String codigoControl="1A-2";
////        System.out.println("furula el codigo:::"+cntFacturacionService.completaFormatoCodigoControl(codigoControl));
//        
////        BigDecimal diferencia;
////        
////        
////           diferencia=new BigDecimal("10.24").subtract(new BigDecimal("20.54"));
////        if(diferencia.signum() == -1){
////                diferencia=diferencia.multiply(new BigDecimal ("-1"));
////        }
////        System.out.println("el valor de diferencia"+diferencia+"");
//        
//        
//        CntDetalleComprobante cntDetalleComprobante=(CntDetalleComprobante)cntDetalleComprobanteService.find(CntDetalleComprobante.class,4920L);
//        
//        CntFacturacion cntFacturacion=cntFacturacionService.obtieneFacturaPorDetalleComprobante(cntDetalleComprobante);
////        CntFacturacion cntFacturacion = cntFacturacionService.find(CntFacturacion.class, 187L);
//        System.out.println("el detalle  es::: "+cntFacturacion.getIdFacturacion()+"/////////////////////////////////////");
////        System.out.println("el detalle  es::: "+(cntFacturacionService.encuentraCreditoFiscalPorCntFacturacion(cntFacturacion)).getDebe());
//        int periodo = 12;
//        SimpleDateFormat formato = new SimpleDateFormat("yyyy, MM, dd");
//        Date fechaActual = new Date();
//        int anio = fechaActual.getYear() + 1900;
//        int mes = periodo - 1;
//        Calendar calendario = Calendar.getInstance();
//        calendario.set(anio, mes, 1);
//        int ultimoDia = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
//        
//        Date fechaInicialConfigurada = formato.parse(anio+", "+periodo+", 01");
////        Date fechaInicialConfigurada2 = formato.parse("2015, 12, 01");
////        System.out.println("la fecha configurada es:::: "+fechaInicialConfigurada);
//        Date fechaFinalConfigurada = formato.parse(anio+", "+periodo+", "+ultimoDia);
//        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
//        String fechaInicial = formatoFecha.format(fechaInicialConfigurada);
//        String fechaFinal = formatoFecha.format(fechaFinalConfigurada);
//        System.out.println("el primer dia es:::: "+fechaInicial);
//        System.out.println("el ultimo dia es:::: "+fechaFinal);
        System.out.println("...compras  ....................***********************.......");
//        List<CntFacturacion> lista = cntFacturacionService.listaReporteCompraVenta("VENT", 1, 2015);
        List<CntFacturacion> lista = cntFacturacionService.listaReporteFacturaVentaMigrado("VENT", 1, 2015);
//        List<CntFacturacion> lista = cntFacturacionService.listaFacturaVentaAnulada("VENT", 7, 2015);
        for (CntFacturacion lista1 : lista) {
            System.out.println("las Facturas son::::" + lista1.getFechaFactura() + "..estado : " + lista1.getEstado()
                    + "..num : " + lista1.getNroInicial() + "..movimiento  : " + lista1.getMovimiento());
        }

//        System.out.println("el sig numero es:::" + cntFacturacionService.generaNumeroDeFactura(15L));
    }

}
