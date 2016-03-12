package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntAuxiliaresCliente {

    public static void main(String args[]) throws Exception {
//F:\ERP Maven Project\APP\ERP_APP
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\GIT erpContaMerged\\ERP-conta\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        CntAuxiliaresService cntAuxiliaresService = (CntAuxiliaresService) context.getBean("cntAuxiliaresService");
        CntEntidadesService cntEntidadesService = (CntEntidadesService) context.getBean("cntEntidadesService");

//persis
        CntAuxiliar cliente = new CntAuxiliar();
//                        cliente.setIdAuxiliar(1L);
//                        cliente.setNumero(6);
//                        cliente.setSigla("ABIS");
//                        cliente.setNombre("TESTTTT");
//                        cliente.setDescripcion("Aplicacion");
//                        cliente.setFechaAlta(new Date());
//                        cliente.setUsuarioAlta("TEST");
//                        
//                        try{
//                            cntAuxiliaresService.persistCntAuxiliares(cliente);
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                         ObjectUtils.printObjectState(cliente);      

        //listado de auxiliares
//
//        System.out.println("LISTA DE AUXILIARES::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
//        List<CntAuxiliar> list = cntAuxiliaresService.listaCntAuxiliares();
//        for (CntAuxiliar cntAuxiliares : list) {
//            System.out.println("Auxiliares:::" + cntAuxiliares.getSigla());
//        }
        //fin de listado
//       
        /*
         * Eliminacion auxiliares
         */
//        CntGestion gestionPrueba = (CntGestion)cntGestionesService.find(CntGestion.class, 4L);
//        try {
//            cntGestionesService.eliminarCrmGestiones(gestionPrueba);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }               
        /*
         * eliminacion
         */
//        System.out.println("NUMERO: "+cntAuxiliaresService.ultimo_numero_auxiliar());
//                        Date fecha = new Date();
//                        System.out.println("la fecha en date es "+fecha.getDate());
//        CntEntidad cuenta = cntEntidadesService.find(CntEntidad.class, 904L);
//        List<CntAuxiliarPorCuenta> listaDeAuxiliaresPorCuenta = cntAuxiliaresService.listaCntAuxiliaresPorCuenta(cuenta);
//        for (CntAuxiliarPorCuenta cntAuxiliar : listaDeAuxiliaresPorCuenta) {
//            System.out.println("el aux es::::"+cntAuxiliar.getCntAuxiliar().getDescripcion());
//        }
//        System.out.println("RESULTADO SIGLA:" + cntAuxiliaresService.verificaExistenciaDeSigla("EFGH"));
//        System.out.println("RESULTADO NOMBRE:" + cntAuxiliaresService.verificaExistenciaDeNombre(""));
        System.out.println("                                    ..........................");
        System.out.println("...auxiliar es : " + cntAuxiliaresService.obtieneAuxiliar(2L).getNombre());
    }
}
