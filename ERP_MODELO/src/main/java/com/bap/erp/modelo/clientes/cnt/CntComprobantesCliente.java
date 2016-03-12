package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntFacturacionService;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import java.util.Calendar;
import java.util.Date;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntComprobantesCliente {

    public static void main(String args[]) throws Exception {
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("home\\jonas\\NetBeansProjects\\Proyecto ERP\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("//home//henrry//CONTA GIT//ERP-conta//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
        CntComprobantesService cntComprobantesService = (CntComprobantesService) context.getBean("cntComprobantesService");
        CntTipoCambioService cntTipoCambioService = (CntTipoCambioService) context.getBean("cntTipoCambioService");
        ParParametricasService parParametricasService = (ParParametricasService) context.getBean("parParametricasService");
        CntDetalleComprobanteService cntDetalleComprobanteService = (CntDetalleComprobanteService) context.getBean("cntDetalleComprobanteService");
        CntFacturacionService cntFacturacionService = (CntFacturacionService) context.getBean("cntFacturacionService");
        /*
         * Persist
         */
//        CntComprobante cntComprobante = new CntComprobante();
//        cntComprobante.setPeriodo("2");
//        cntComprobante.setParTipoComprobante((ParTipoComprobante) parParametricasService.find(ParTipoComprobante.class, "TRAS"));
//        cntComprobante.setDescripcion("Comprobante de traspaso");
//        cntComprobante.setTotalComprobantes(BigDecimal.ZERO);
//        cntComprobante.setFechaAlta(new Date());
//        cntComprobante.setUsuarioAlta("TEST");
//        cntComprobante.setFecha(new Date());
//        cntComprobante.setNumero(1L);
//        cntComprobantesService.persist(cntComprobante);
        /*
         * Merge
         */
//       CntComprobante cntComprobante = cntComprobantesService.find(CntComprobante.class, 1L);
//       cntComprobante.setPeriodo("3");
//       cntComprobantesService .merge(cntComprobante);
        /*
         * Listado
         */
//        try {
//            List<CntComprobante> lista = cntComprobantesService.listaCntComprobantes();
//            for (CntComprobante cntComprobantes : lista) {
//                System.out.println("comprobantes" + cntComprobantes.getIdComprobante());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        System.out.println("NUMERO:"+cntComprobantesService.ultimoNumeroComprobante((CntComprobante)cntComprobantesService.find(CntComprobante.class, 42L)));
//        System.out.println("VERIFICA:"+cntComprobantesService.verificaExistenciaDePendietes());
//        try {
//            System.out.println("FECHA MES:" + cntComprobantesService.obtienePeriodoPorFecha(new Date()));
//        } catch (Exception e) {
//        }
//
//        BigDecimal monto = new BigDecimal("500");
//        Locale locale = new Locale("en", "US");
//        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
//        System.out.println("El monto a pagar es de: " + nf.format(monto));
//        System.out.println("MONTO: " + nf.format(monto).substring(1, nf.format(monto).length()));
//        CntComprobante cntComprobante = cntComprobantesService.find(CntComprobante.class, 865L);
//        List<CntDetalleComprobante>lista=cntComprobantesService.obtieneDetalleComprobante(cntComprobante);
//        for (CntDetalleComprobante cntDetalleComprobante : lista) {
//                System.out.println("detalles comprobantes" + cntDetalleComprobante.getIdDetalleComprobante());
//            }
//        
//        System.out.println("obtiene detalles comprobantes"+cntComprobantesService.obtieneDetalleComprobante(cntComprobante)+"");
//        System.out.println("muestra si el comprobante tiene factura"+cntComprobantesService.verificaComprobanteConFactura(cntComprobante)+"");
//        try {
////            cntComprobantesService.mergeCntComprobanteAndCntDetalleComprobanteAndFacturaTotal(cntComprobante);
//            System.out.println("RETORNA:"+cntFacturacionService.obtieneFacturaPorDetalleComprobanteParaDelete(cntDetalleComprobanteService.find(CntDetalleComprobante.class,3670L)));
//            
//
//        } catch (Exception h) {
//            h.printStackTrace();
//        }
//
//        for (CntComprobante comprobante : cntComprobantesService.listaComprobantesPendientesSinDetalleComprobante()) {
//            System.out.println("ID:"+comprobante.getIdComprobante());
//        }
//        
//        cntComprobantesService.removeComprobantePendientesSinDetalleComprobante();
//
//
//        cntComprobantesService.persistTemporalParaCopia(cntComprobante, "JONAS");
//        cntComprobantesService.nuleaCampoDeIdReplicado(cntComprobante);
//        CntComprobante test = cntComprobantesService.find(CntComprobante.class, 995L);
//        List<CntDetalleComprobante> lista = cntComprobantesService.obtieneDetalleComprobantePadresParaDelete(test);
//        for (CntDetalleComprobante cntDetalleComprobante : lista) {
//            System.out.println("es::: "+cntDetalleComprobante.getIdDetalleComprobante());
//        }
//        CntComprobante cntComprobante = cntComprobantesService.find(CntComprobante.class, 1154L);
//        System.out.println("se modifica??? "+cntComprobantesService.verificaSiSeCreaOModifica(cntComprobante));
//        String mensaje = "Pag. - 100 - Comprobante Nro 23";
//        String[] vector = new String[3];
//        vector = mensaje.split(" - ");        
//        int numero = Integer.parseInt(vector[1]);
//        System.out.println("El numero es::: "+numero);
//        System.out.println("Inserta N cantidad de Comprobantes");
//        CntComprobante cntComprobante = cntComprobantesService.find(CntComprobante.class, 7326L);
//        cntComprobantesService.copiaUnComprobanteNVeces(cntComprobante, 200);
//        System.out.println("..................................................................................................");
//        Date fechaInicial = new Date("01/01/2015");
////        Date fechaActual = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(fechaInicial);
//        int mes = calendar.get(Calendar.MONTH) + 1;
//        int año = calendar.get(calendar.YEAR);
//        System.out.println("...mes es : " + mes + "..año  : " + año);
//        if (fechaActual.after(fechaInicial) && fechaActual.before(fechaFinal)) {
//            System.out.println("esta en el rango");
//        } else {
//            System.out.println("no");
//        }
//        System.out.println("esta habilitado----- "+cntComprobantesService.periodoHabilitado(fechaActual));
//        String test = "01/22/2014";
//        String[] divi = test.split("/");        
//        Date fecha = new Date("01/02/2014");
//        System.out.println("la fecha es "+fecha);
//        System.out.println("el nombre es:::"+EnumCuentaGeneral.PATRIMONIO.name());
        //nivel 3
//        String cadena = "1-11-00-1-0-1";
//        String[] mascaraVec = cadena.split("-");        
//        String queda="";
//        for (int i = 0; i < ((mascaraVec.length+1)-1); i++) {
//            queda = queda+mascaraVec[i]+"-";
//        }
//        System.out.println("lo que queda es::"+queda);
//        CntDetalleComprobante j = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 7516L);
//        List<CntDistribucionCentrocosto> lista = cntComprobantesService.encuentraCentroDeCostoPorDetalleComprobante(j);
//        for (CntDistribucionCentrocosto cntDistribucionCentrocosto : lista) {
//            System.out.println("son: "+cntDistribucionCentrocosto.getIdDistribucionCentrocosto());  
//        }
//        BigDecimal monto = new BigDecimal(200);
//        List<CntComprobante> lista = cntComprobantesService.listaComprobantesSegunMonto(monto);
//        for (CntComprobante lista1 : lista) {
//            System.out.println("el comprobante es:::"+lista1.getIdComprobante());
//        }
//        Comprobante cntComprobantePojo = new Comprobante();
//        cntComprobantePojo.setExcento(10.2F);
//        cntComprobantePojo.setIce(10F);
//        cntComprobantePojo.setIdTipoDeCambio(Long.MIN_VALUE);
//        List<CuentaMonto> listaCuentas = new ArrayList<CuentaMonto>();
//        CuentaMonto cntCuentaMontoPojo = new CuentaMonto();
//        cntCuentaMontoPojo.setIdCuentaMontoPojo(54L);
//        cntCuentaMontoPojo.setMonto(12.3F);
////        CuentaMonto cntCuentaMontoPojo2 = new CuentaMonto();
////        cntCuentaMontoPojo2.setIdCuentaMontoPojo(55L);
////        cntCuentaMontoPojo2.setMonto(10.1F);
//        listaCuentas.add(cntCuentaMontoPojo);
////        listaCuentas.add(cntCuentaMontoPojo2);
//        cntComprobantePojo.setListaCuentas(listaCuentas);
//        cntComprobantePojo.setIdTipoDeCambio(1L);
//
//        cntComprobantePojo = cntComprobantesService.calculaExcentoIceDescuentoIvaXcxpWS(cntComprobantePojo);
//        System.out.println("excento" + cntComprobantePojo.getExcento());
        System.out.println("..........***********************************...............");
        CntComprobante cnt = cntComprobantesService.getObtieneCpbtePorNumeroCpbte(1L,"E");

        System.out.println("....cnt numero : " + cnt);
    }
}
