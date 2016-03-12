package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.pojo.PojoCntEntidadBGyEERR;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.bap.erp.modelo.servicios.cnt.CntFacturacionService;
import com.bap.erp.modelo.entidades.cnt.CntComprobante;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntDetalleComprobantesCliente {

    public static void main(String args[]) throws Exception {

        ApplicationContext context = new FileSystemXmlApplicationContext("//home//henrry//CONTA GIT//ERP-conta//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("//home//CONTA GIT//esktop//applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\GIT erpContaMerged\\ERP-conta\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");

        CntDetalleComprobanteService cntDetalleComprobanteService = (CntDetalleComprobanteService) context.getBean("cntDetalleComprobanteService");
        CntComprobantesService cntComprobantesService = (CntComprobantesService) context.getBean("cntComprobantesService");
        CntEntidadesService cntEntidadesService = (CntEntidadesService) context.getBean("cntEntidadesService");
        CntTipoCambioService cntTipoCambioService = (CntTipoCambioService) context.getBean("cntTipoCambioService");
        ParParametricasService parParametricasService = (ParParametricasService) context.getBean("parParametricasService");
        CntFacturacionService cntFacturacionService = (CntFacturacionService) context.getBean("cntFacturacionService");
        //<editor-fold defaultstate="collapsed" desc="otras pruebas  ">
        /*
         * Persist
         */
//        CntDetalleComprobante cntDetalleComprobante = new CntDetalleComprobante();
//        cntDetalleComprobante.setCntComprobante(cntComprobantesService.find(CntComprobante.class, 1L));
//        cntDetalleComprobante.setCntEntidad(cntEntidadesService.find(CntEntidad.class, 1L));
//        cntDetalleComprobante.setDebe(BigDecimal.ZERO);
//        cntDetalleComprobante.setHaber(BigDecimal.ZERO);
//        cntDetalleComprobante.setGlosa("Detalle de Comprobante");
//        cntDetalleComprobante.setFechaAlta(new Date());
//        cntDetalleComprobante.setUsuarioAlta("SISTEMA");
//        cntDetalleComprobanteService.persist(cntDetalleComprobante);

        /*
         * Merge
         */
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 1L);
//        cntDetalleComprobante.setGlosa("Glosa modificada");
//        cntDetalleComprobanteService.merge(cntDetalleComprobante);
//        /*Listado*/
//        List<CntDetalleComprobante> lista = cntDetalleComprobanteService.listaCntDetalleComprobantes();
//        for (CntDetalleComprobante cntDetalleComprobante : lista) {
//            System.out.println("el comprobante es:::"+cntDetalleComprobante.getGlosa());
//        }
//        /*Listado de cuentas por Comprobante*/
//        List<CntDetalleComprobante> lista = cntDetalleComprobanteService.listaDeCuentasPorComprobante(cntComprobantesService.find(CntComprobante.class, 1L));
//        for (CntDetalleComprobante cntDetalleComprobante : lista) {
//            System.out.println("la cuenta es:::"+cntDetalleComprobante.getCntEntidad().getDescripcion());
//        }
        /*
         * persist masivo
         */
//        CntComprobante cntComprobante = new CntComprobante();
//        cntComprobante.setCntTipoCambio(cntTipoCambioService.find(CntTipoCambio.class, 1L));
//        cntComprobante.setPeriodo("2");
//        cntComprobante.setFecha(new Date());
//        cntComprobante.setNumero(13L);
//        cntComprobante.setParTipoComprobante((ParTipoComprobante)parParametricasService.find(ParTipoComprobante.class, "TRAS"));
//        cntComprobante.setDescripcion("Comprobante de traspaso");
//        cntComprobante.setTotalComprobantes(BigDecimal.ZERO);
//        cntComprobante.setFechaAlta(new Date());
//        cntComprobante.setUsuarioAlta("SISTEMA2");
//        /*detalle comprobante*/
//        CntDetalleComprobante cntDetalleComprobante = new CntDetalleComprobante();
//        cntDetalleComprobante.setCntEntidad(cntEntidadesService.find(CntEntidad.class, 1L));
//        cntDetalleComprobante.setDebe(BigDecimal.ZERO);
//        cntDetalleComprobante.setHaber(BigDecimal.ZERO);
//        cntDetalleComprobante.setGlosa("Detalle de Comprobante uno");
//        cntDetalleComprobante.setFechaAlta(new Date());
//        cntDetalleComprobante.setUsuarioAlta("SISTEMA2");
//
//        CntDetalleComprobante cntDetalleComprobante2 = new CntDetalleComprobante();
//        cntDetalleComprobante2.setCntEntidad(cntEntidadesService.find(CntEntidad.class, 2L));
//        cntDetalleComprobante2.setDebe(BigDecimal.ZERO);
//        cntDetalleComprobante2.setHaber(BigDecimal.ZERO);
//        cntDetalleComprobante2.setGlosa("Detalle de Comprobante prueba dos");
//        cntDetalleComprobante2.setFechaAlta(new Date());
//        cntDetalleComprobante2.setUsuarioAlta("SISTEMA2");
//
//        List<CntDetalleComprobante> lista =  new ArrayList<CntDetalleComprobante>();
//        lista.add(cntDetalleComprobante);
//        lista.add(cntDetalleComprobante2);
//
//        cntComprobantesService.persistCntComprobanteYCntDetalleComprobante(cntComprobante, lista);
//        System.out.println("la suma debe es:::"+cntDetalleComprobanteService.sumaDebeComprobante(lista));
//        System.out.println("Posicion:" + cntDetalleComprobanteService.obtienePosicionDetalleComprobantePorComprobante((CntComprobante) cntComprobantesService.find(CntComprobante.class, 200L)));
//        CntComprobante cntComprobante = cntComprobantesService.find(CntComprobante.class, 199L);
//        List<CntDetalleComprobante> lista = cntDetalleComprobanteService.ordenaSegunPosicion(cntComprobante);
//        for (CntDetalleComprobante cntDetalleComprobante : lista) {
//            System.out.println("la detalle es:::" + cntDetalleComprobante.getPosicion());
//        }
//        CntComprobante cntComprobante = cntComprobantesService.find(CntComprobante.class, 202L);
//        cntDetalleComprobanteService.asignaNuevaPosicionAPadres(cntComprobante, 4L);
//
//        List<CntDetalleComprobante> lista = cntDetalleComprobanteService.obtieneHijosTambienPadresDeUnDetalleComprobante((CntDetalleComprobante) cntDetalleComprobanteService.find(CntDetalleComprobante.class, 499L));
//        for (CntDetalleComprobante cntDetalleComprobante : lista) {
//            System.out.println("id="+cntDetalleComprobante.getIdDetalleComprobante()+"    Cuenta:"+cntDetalleComprobante.getCntEntidad().getMascaraGenerada());
//        }
//        System.out.println("dolares"+cntDetalleComprobanteService.convierteBolivianosDolar(new BigDecimal("40385.76"), new BigDecimal("6.98")) +"");
//        System.out.println("Cuenta: "+cntDetalleComprobanteService.obtieneIdPadreDeUnDetalleComprobante(lista).getCntEntidad().getMascaraGenerada());
//        System.out.println("..........................***************************************...............................");
////
//        System.out.println("...debe bs. :" + cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cuenta, inicial));
//        System.out.println("...haber bs. :" + cntDetalleComprobanteService.sumaHaberParaLibroMayor(cuenta, inicial));
//
//        System.out.println("...debe dolar... : " + cntDetalleComprobanteService.sumadebeDolarInicialLibroMayor(cuenta, inicial));
//        System.out.println("...haber dolar ... : " + cntDetalleComprobanteService.sumaHaberDolarParaLibroMayor(cuenta, inicial));
//        List<CntLibroMayor> lista = cntDetalleComprobanteService.listaLibroMayorSegunCuenta(cuenta, inicial, fin);
////
//        List<CntLibroMayor> lista = cntDetalleComprobanteService.listaUnida(cntEntidad, inicial, fin);
//        System.out.println("LISTA:::------------- " + lista.size());
//        for (Object x : lista) {
//            CntLibroMayor c = (CntLibroMayor) x;
//            System.out.println("mascara.." + c.getIdentidad() + "-..glosa : " + c.getGlosa() + "..debe..  " + c.getDebe() + " hber :  " + c.getHaber() + " saldo::: " + c.getSaldo()
//                    + "..debedolar : " + c.getDebeDolar() + "..haber dolar : " + c.getHaberDolar() + "..saldo dolar : " + c.getSaldoDolar());
//        }
//        System.out.println("...sumasaldo :  " + cntDetalleComprobanteService.sumaSaldoComprobanteLibroMayor(lista));
//        System.out.println("                                             lista para adicionar ");
//        List<CntLibroMayor> listar = cntDetalleComprobanteService.listaResultados(lista);
//        for (CntLibroMayor l : listar) {
//            System.out.println("..glosa : " + l.getGlosa() + "..debe : " + l.getDebe() + "..haber.." + l.getHaber() + "..glosa des " + l.getDescripcion());
//        }
//        /**
//         * ************SUMA PARA EL LIBRO MAYOR***************
//         */
//        System.out.println("...............SUMA LIBRO MAYOR SALDO INICIAL  ");
////        SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MM, dd");
////        Date inicial = sdf.parse("2015, 05, 21");
//        CntEntidad cta = cntEntidadesService.find(CntEntidad.class, 57L);
//        BigDecimal suma = cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cta, inicial);
//        System.out.println("...saldo inicial : " + suma);
//        System.out.println("...suma haber : " + cntDetalleComprobanteService.sumaHaberParaLibroMayor(cta, inicial));
//        System.out.println("...suma debedol : " + cntDetalleComprobanteService.sumadebeDolarInicialLibroMayor(cta, inicial));
//        System.out.println("...suma haber : " + cntDetalleComprobanteService.sumaHaberDolarParaLibroMayor(cta, inicial));
//        System.out.println("FACTUAR:"+cntDetalleComprobanteService.buscaFacturacionPorDetalleComprobantePadres((CntDetalleComprobante)cntDetalleComprobanteService.find(CntDetalleComprobante.class,1031L)));
//        CntDetalleComprobante cntDetalleComprobante=cntDetalleComprobanteService.obtieneMontoOriginal((CntDetalleComprobante)cntDetalleComprobanteService.find(CntDetalleComprobante.class, 1440L));
//        System.out.println("MONTO ORIGINAL:"+cntDetalleComprobante.getDebe()+"   :  "+cntDetalleComprobante.getHaber());
//        System.out.println("entro al cliente");
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 4740L);
//
//        System.out.println("el id del detalle comprobante"+cntDetalleComprobante.getIdDetalleComprobante()+"");
//        System.out.println("el id del facturacion"+cntDetalleComprobante.getCntEntidad().getIdEntidad()+"" );
//        cntDetalleComprobanteService.removeCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
//        cntDetalleComprobanteService.deleteCntDetalleComprobantesCntFacturacion(cntDetalleComprobante);
//        for (CntDetalleComprobante cdc : cntDetalleComprobanteService.obtieneListaDePendientesDetalleComprobantePadres(cntComprobantesService.find(CntComprobante.class, 1174L))) {
//            System.out.println("ELEMENTO :"+cdc.getIdDetalleComprobante());
//        }
//        CntComprobante cc=cntComprobantesService.find(CntComprobante.class,1253L);
//        cc.setFechaModificacion(new Date());
//        cc.setUsuarioModificacion("HENRRY");
////        cntDetalleComprobanteService.reducePosicionAPadres(cc,1);
//
////        cntDetalleComprobanteService.cambiaEstadoAnuladoTodoElComprobanteMasLasTablasDependientes(cc);
//
//
//        cntComprobantesService.persistCntComprobanteAndCntDetalleComprobanteAndFacturaTotal(cc);
//        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy, MM, dd");
//        Date inicial = sdf.parse("2014, 01, 01");
//        Date fin = sdf.parse("2014, 12, 31");
//        String anio = "2014";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MM, dd");
//            Date inicialParaAnio = sdf.parse(anio + ", 01, 01");
//            int sigAnio = Integer.parseInt(anio)+1;
//            Date finalParaAnio = sdf.parse(sigAnio+ ", 01, 01");
//        System.out.println("fecha inicial---> "+inicialParaAnio);
//        System.out.println("fecha final----> "+finalParaAnio);
//        System.out.println("......................------------------------------.................................");
//        CntComprobante compro = cntComprobantesService.find(CntComprobante.class, 45L);
////        List<CntDetalleComprobante> lista = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(compro);
//        List<CntDetalleComprobante> listac = cntDetalleComprobanteService.listaDetalleComprobantePorComprobanteEnOrden(compro);
//        for (CntDetalleComprobante cntDetalleComprobante : listac) {
//            System.out.println("numero..-->" + cntDetalleComprobante.getPosicion() + "...cuenta..." + cntDetalleComprobante.getCntEntidad().getMascaraGenerada() + cntDetalleComprobante.getCntEntidad().getDescripcion()
//                    + "..glosa..-->" + cntDetalleComprobante.getGlosa() + "..debe.BS.-->" + cntDetalleComprobante.getDebe() + "..debe SUS..-->" + cntDetalleComprobante.getDebeDolar() + "..haber BS.." + cntDetalleComprobante.getHaber()
//                    + "...haber SUS..-->" + cntDetalleComprobante.getHaberDolar() + "..elaborado por...-->" + cntDetalleComprobante.getCntComprobante().getUsuarioAlta() + "..num,,,," + cntDetalleComprobante.getCntComprobante().getFechaAlta()
//                    + "..num  " + cntDetalleComprobante.getCntComprobante().getNumero());
//        }
//        List<CntDetalleComprobante> lista = cntDetalleComprobanteService.listaDetalleComprobantesPorComprobanteReporte(2L, 3L, "2", "2014", "EGRE");
//        for (CntDetalleComprobante cntDetalleComprobante : lista) {
//            System.out.println("los numeros"+cntDetalleComprobante.getPosicion()+"los detalles son:::"+cntDetalleComprobante.getGlosa());
//        }
//    }
//        List<CntNivel> lista = cntDetalleComprobanteService.listaDeNiveles();
//        for (CntNivel cntNivel : lista) {
//            System.out.println("Descripcion es" + cntNivel.getDescripcion() + "--------movimiento------------" + cntNivel.getTipoMovimiento() + "---------------");
//        }
//        CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, 6L);
//        System.out.println("ID:" + cntEntidad.getIdEntidad() + " ENTIDAD: " + cntEntidad.getMascaraGenerada() + " " + cntEntidad.getDescripcion());
//
//        System.out.println("Suma Debe:" + cntDetalleComprobanteService.sumaCampoMonetarioDetalleComprobante(cntEntidad, new Date(), "debe"));
//        System.out.println("Suma Heber:" + cntDetalleComprobanteService.sumaCampoMonetarioDetalleComprobante(cntEntidad, new Date(), "haber"));
//        for (CntDetalleComprobante cdc : cntDetalleComprobanteService.detalleComprobantePorFechaList(new Date())) {
//            System.out.println("CODIGO:"+cdc.getCntEntidad().getIdEntidad());
//        }
//        for (PojoCntDetalleComprobanteSumasSaldos pcdcss : cntDetalleComprobanteService.obtieneListaSumasSaldosSoloDetalleComprobante(new Date())) {
////            System.out.println("idEntidad A: " + listaIterator.next().getCntEntidad().getIdEntidad());
//            System.out.print(" cuenta: " + pcdcss.getIdEntidad().getMascaraGenerada());
//            System.out.print(" debe: " + pcdcss.getSumaDebe());
//            System.out.print(" haber: " + pcdcss.getSumaHaber());
//            System.out.print(" debeDolar: " + pcdcss.getSumaDebeDolar());
//            System.out.print(" haberDolar: " + pcdcss.getSumaHaberDolar());
//            System.out.print(" deudor: " + pcdcss.getDeudor());
//            System.out.print(" acreedor: " + pcdcss.getAcreedor());
//            System.out.print(" deudorDolar: " + pcdcss.getDeudorDolar());
//            System.out.print(" acreedorDolar: " + pcdcss.getAcreedorDolar());
//            System.out.println("");
//            System.out.println("-----------------------------------------------------");
//        }
//        System.out.println("................................................................................................................");
//        CntEntidad cuenta = cntEntidadesService.find(CntEntidad.class, 51L);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MM, dd");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy, MM, dd");
//        Date inicial = sdf.parse("2015, 02, 04");
//        Date fin = sdf2.parse("2015, 04, 30");
//
//        System.out.println("    CUENTA  |   DEBE    |   HABER   |   DEBE DOLAR  |   HABER DOLAR |   DEUDOR  |   ACREEDOR    |   DEUDOR DOLAR    |   ACREEDOR DOLAR  |");
////        List<PojoCntDetalleComprobanteSumasSaldos> listas = cntDetalleComprobanteService.obtieneListaCompletaParaSumasSaldos(fin, 1, true);
//        List<PojoCntDetalleComprobanteSumasSaldos> listas = cntDetalleComprobanteService.obtieneListaFinal(fin, 1, true);
//        System.out.println("....");
//        for (PojoCntDetalleComprobanteSumasSaldos pcdcss : listas) {
//            System.out.println("..index  " + pcdcss.getValorIndex());
//            System.out.print(pcdcss.getIdEntidad().getMascaraGenerada());
//            System.out.print("  | " + pcdcss.getSumaDebe());
//            System.out.print("  | " + pcdcss.getSumaHaber());
//            System.out.print("  | " + pcdcss.getSumaDebeDolar());
//            System.out.print("  | " + pcdcss.getSumaHaberDolar());
//            System.out.print("  | " + pcdcss.getDeudor());
//            System.out.print("  | " + pcdcss.getAcreedor());
//            System.out.print("  | " + pcdcss.getDeudorDolar());
//            System.out.print("  | " + pcdcss.getAcreedorDolar());
//            System.out.println("");
//        }
//        for (CntEntidad entidad : cntDetalleComprobanteService.cntEntidadesPorGrupoNivelList(6)) {
//            System.out.println(entidad.getMascaraGenerada());
//        }
//        CntComprobante compro = cntComprobantesService.find(CntComprobante.class, 1800L);
//        List<CntDetalleComprobante> lista = cntDetalleComprobanteService.obtieneListaDePendientesYQuitadosDetalleComprobantePadres(compro);
//        for (CntDetalleComprobante cntDetalleComprobante : lista) {
//            System.out.println("detalle comprob "+cntDetalleComprobante.getEstado());
//        }
//        CntComprobante compro = cntComprobantesService.find(CntComprobante.class, 1816L);
//        cntDetalleComprobanteService.guardaPosicionesAnteriores(compro);
//        List<CntDetalleComprobante> lista = cntDetalleComprobanteService.obtienePadresPorComprobante(compro);
//        for (CntDetalleComprobante cntDetalleComprobante : lista) {
//            System.out.println("la pos es "+cntDetalleComprobante.getPosicionAnterior());
//        }
//        try {
//            System.out.println("Verifica:"+cntDetalleComprobanteService.verificaRelacionCntEntidadConCntDetalleComprobante(cntEntidadesService.find(CntEntidad.class, 1L)));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println("............................................................................");
//        CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, 47L);
//        System.out.println("el monto es: "+cntDetalleComprobanteService.totalDetalleComprobantePorCuenta(cntEntidad, new Date()));
//        BigDecimal dolar = new BigDecimal(6.87);
//        System.out.println(".......................EERR ..................----------------------------------------");
//        System.out.println("....cu3nta mascara  : " + parParametricasService.devuelveMascaraPorTipoCuenta("pas"));
//
//        System.out.println("..................******************************----------------- ");
////        BigDecimal[] resultados = new BigDecimal[3];
////        resultados = cntDetalleComprobanteService.obtieneMontoTotal(new Date(), "BG");
//
////        System.out.println("...resul es :: " + resultados[0] +"...--"+resultados[1]+"...resu : "+resultados[2]);
//        System.out.println("................................................................................................................");
//
////        CntEntidad cuenta = cntEntidadesService.find(CntEntidad.class, 51L);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MM, dd");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy, MM, dd");
//        Date inicial = sdf.parse("2015, 02, 04");
//        Date fin = sdf2.parse("2015, 07, 31");
////
//        System.out.println("..***/////////////------------------------------------****************.fecha Inicial : " + inicial + "..fin  : " + fin);
////        List<PojoCntEntidadBGyEERR> listap = cntDetalleComprobanteService.listaEERRctrlCeros(inicial, fin, 1, false, "EERR");
//        List<PojoCntEntidadBGyEERR> listap = cntDetalleComprobanteService.listaPlanCuentasParaBalanceGeneralCeros(fin, 1, false, "EERR");
////        List<PojoCntEntidadBGyEERR> listap = cntDetalleComprobanteService.listaPlanCuentasParaBalanceGeneral(fin, "BG");
//
//        System.out.println("...size : " + listap.size());
//
//        System.out.println("................................................................................................................");
//        for (PojoCntEntidadBGyEERR lista1 : listap) {
//            if (lista1.getIdEntidad() != null) {
//                System.out.println("  las cuentas son::: " + lista1.getIdEntidad().getMascaraGenerada() +"..descripcion : "+lista1.getDescripcion()+ " monto " + lista1.getMontoMonedaUno() + "  dolares " + lista1.getMontoMonedaDos()
//                        + " nivel1 bs: " + lista1.getNivel1bs()
//                        + " nivel2 bs: " + lista1.getNivel2bs()
//                        + " nivel3 bs: " + lista1.getNivel3bs()
//                        + " nivel4 bs: " + lista1.getNivel4bs()
//                        + " nivel5 bs: " + lista1.getNivel5bs()
//                        + " nivel6 bs: " + lista1.getNivel6bs()
//                );
//
//            } else {
//                System.out.println(" monto else :  " + lista1.getMontoMonedaUno() + " en dolares else : " + lista1.getMontoMonedaDos() + "..identidad --> " + lista1.getIdEntidad());
//            }
//        }
//        CntEntidad enti = cntEntidadesService.find(CntEntidad.class,51L);
//        BigDecimal[] resultados = new BigDecimal[2];
//        resultados = cntDetalleComprobanteService.totalMontoAmbasMonedasDetalleComprobantePorCuenta(enti, fin);
//        
//        
//        System.out.println("...total monedas :::::resul 0 :: "+resultados[0]+"..resul 1 :::"+resultados[1] );
//        
        
                
//        BigDecimal[] resultados = new BigDecimal[2];
//        resultados = cntDetalleComprobanteService.obtieneMontoTotalSusEERR(inicial, fin, "EERR");
//        System.out.println("...egreso es " + resultados[0] + "..ingreso : " + resultados[1]);
//        System.out.println("...resta es   : " + resultados[1].subtract(resultados[0]));
//        System.out.println(".................................................");
//        System.out.println("cntDetalleComprobanteService:"+cntDetalleComprobanteService);
//
//        System.out.println("prueba:"+cntDetalleComprobanteService.listaCntDetalleComprobantes().size());
//
//        CntDetalleComprobante test = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 86L);
//        System.out.println("etest");
//        System.out.println("el id es::"+test);
//        System.out.println("el id es::"+test.getIdDetalleComprobante());
//        Date fecha = new Date("03/26/2014");
//        System.out.println("la fecha es::: "+fecha);
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 103L);
//        try {
//            List<CntDetalleComprobante> lista = cntDetalleComprobanteService.listaHijosPorPadre(cntDetalleComprobante);
//
//        } catch (Exception e) {
//        }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="pruebas">
        System.out.println("********************************.....monto es ****************************************** ");
//        CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, 11L);
////        Date f1 = new Date("2015/05/05");
////        Date f2 = new Date("2015/07/31");
//
//        BigDecimal[] results = new BigDecimal[2];
////        results = cntDetalleComprobanteService.obtieneMontoTotalEI(f2, "EERR");
//        results = cntDetalleComprobanteService.obtieneMontoTotalEI(f2, "BG");
//
//        System.out.println(".....monto es  " + results[0] + " .. r1  " + results[1]);
////
//        Date f1 = new Date("2015/04/30");
//        System.out.println("..monto sus : "+cntDetalleComprobanteService.obtieneMontoTotalSus(f1, "EERR"));
//        CntComprobante cbte = cntComprobantesService.find(CntComprobante.class, 182L);
//
//        System.out.println("...debe es : " + cntDetalleComprobanteService.sumaCampoMonetarioPorComprobante(cbte, EnumCampoMonetario.DEBE.getCodigo()));
//        BigDecimal debe = cntDetalleComprobanteService.sumaCampoMonetarioPorComprobante(cbte, EnumCampoMonetario.DEBE.getCodigo());
//
//        NumberFormat formatter = new DecimalFormat("#,#00.00");
////         formatter.format(debe);
//        String numero = formatter.format(debe);
//        System.out.println("...numero es : " + numero);
//        CntDetalleComprobante det = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 856L);
//        System.out.println("..lib may es --  : " + cntDetalleComprobanteService.obtieneComprobanteXnumero(det.getIdDetalleComprobante()).getGlosa());

////        List<PojoCntDetalleComprobanteSumasSaldos> devuelveListaCompletaParaSumasSaldos
////        System.out.println("...........................***---***---***......................................................");
////        Date f1 = new Date("2015/04/30");
////
////        List<PojoCntDetalleComprobanteSumasSaldos> lista = cntDetalleComprobanteService.devuelveListaCompletaParaSumasSaldos(f1, 1, true);
////        System.out.println("..lista es1 : " + lista.size());
////        for (PojoCntDetalleComprobanteSumasSaldos l1 : lista) {
////            System.out.println("..nivel " + l1.getIdEntidad().getNivel() + "..mas : " + l1.getIdEntidad().getMascaraGenerada() + "..debe : " + l1.getSumaDebe() + "..haber : " + l1.getSumaHaber());
////
////        }
        System.out.println("......................****..................devuelve.........................------------------------------------------");

//        List<PojoCntDetalleComprobanteSumasSaldos> lista2 = cntDetalleComprobanteService.obtieneListaCompletaParaSumasSaldos(f1, 1, true);
//        List<PojoCntDetalleComprobanteSumasSaldos> lista2 = cntDetalleComprobanteService.obtieneListaSumasSaldosSoloDetalleComprobante(fin);
//        List<PojoCntDetalleComprobanteSumasSaldos> lista2 = cntDetalleComprobanteService.devuelveListaCompletaParaSumasSaldos(fin, 1, false);
//        List<PojoCntDetalleComprobanteSumasSaldos> lista2 = cntDetalleComprobanteService.obtieneListaSumasSaldosSoloDetalleComprobante(fin);
//        System.out.println("..lista es2 : " + lista2.size());
//        for (PojoCntDetalleComprobanteSumasSaldos l2 : lista2) {
//            System.out.println("indice : " + l2.getValorIndex() + "..nivel " + l2.getIdEntidad().getNivel() + "..mas : " + l2.getIdEntidad().getMascaraGenerada() + "..debe : " + l2.getSumaDebe() + "..haber : " + l2.getSumaHaber());
//
//        }
////
//        List<CntDetalleComprobante> listad = cntDetalleComprobanteService.detalleComprobantePorFechaList(fin);
//        for (CntDetalleComprobante d : listad) {
//            System.out.println("..d " + d.getCntEntidad().getMascaraGenerada() + "  debe " + d.getDebe() + "..haber : " + d.getHaber());
//
//        }
//
//        CntFacturacion fac = cntFacturacionService.find(CntFacturacion.class, 42L);
//        System.out.println("...cntdetalle es  : " + cntDetalleComprobanteService.encuentraDetalleComprobantePorFacturacion(fac));
//</editor-fold>
        System.out.println("................................................................");
         Date f1 = new Date("2015/09/26");
//          Date f2 = new Date("2015/07/30");
//        List<CntDetalleComprobante> listapor = cntDetalleComprobanteService.listaproFechas(f1, f2);
//        System.out.println("lista es : "+listapor.size());
//<editor-fold defaultstate="collapsed" desc="pruebass">
//
//        System.out.println("......../////////*******************************************mayores.....................");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy, MM, dd");
//        Date inicial = sdf.parse("2015, 04, 11");
//        Date fin = sdf.parse("2015, 04, 31");
//
//        CntEntidad cntEntidad = cntEntidadesService.find(CntEntidad.class, 291L);
////        System.out.println("...inicial  " + inicial + "..fin  : " + fin);
//        System.out.println(" *************************************************");
//        System.out.println("..saldo inicial es   : " + cntDetalleComprobanteService.sumaSaldoInicialLibroMayor(cntEntidad, inicial));
//        System.out.println(" *************************************************");
//        Calendar c = Calendar.getInstance();
//        c.setTime(inicial);
//        c.roll(Calendar.DAY_OF_YEAR, -1);
//        inicial = c.getTime();//
//        GregorianCalendar date1 = new GregorianCalendar();
//        date1.setTime(inicial);
//        int mes = date1.get(Calendar.MONTH);
//        mes = mes + 1;
//        System.out.println("..mes : " + mes);
//        if (mes == 12 && date1.get(Calendar.DAY_OF_MONTH) == 31) {
//            c.roll(Calendar.YEAR, -1);
//            inicial = c.getTime();
//            System.out.println("....res: " + inicial);
//        } else {
//            System.out.println("..inicial : " + inicial);
//        }
//        System.out.println("...devuelve fecha es : " + cntDetalleComprobanteService.devuelveFecha(inicial));
//        System.out.println("-----saldo inicial");
//</editor-fold>
        
//        System.out.println("..............."+cntDetalleComprobanteService.verificaPeriodoyGestion(f1));
         CntComprobante cpbte = cntComprobantesService.find(CntComprobante.class, 870L);
         System.out.println("........"+cntDetalleComprobanteService.getObtieneDetalleConFacturaNullXCpbte(cpbte));
         System.out.println("........"+cntDetalleComprobanteService.getObtieneDetalleConFacturaNullXCpbte(cpbte).getIdDetalleComprobante());
         
         
    }
}
