package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntTipoCambio;
import com.bap.erp.modelo.servicios.cnt.CntTipoCambioService;
import com.iknow.utils.ObjectUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import java.util.List;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class CntTipoCambioCliente {

    public static void main(String args[]) throws Exception {

//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("//home//conejo//GIT//ERP_CONTA_CxP//ERP-conta//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");

        CntTipoCambioService cntTipoCambioService = (CntTipoCambioService) context.getBean("cntTipoCambioService");
//        CntAuxiliaresService cntAuxiliaresService = (CntAuxiliaresService) context.getBean("cntAuxiliaresService");

        //persis tipo de cambio-------------------------------------------------
//        CntTipoCambio cliente = new CntTipoCambio();
//                   cliente.setIdTipoCambio(1L);
//        cliente.setFecha(new Date());
//        cliente.setTipoCambio(new BigDecimal(9.25));
//        cliente.setTipoUfv(new BigDecimal(9.00));
//        cliente.setFechaAlta(new Date());
//        cliente.setUsuarioAlta("TEST");
//        try {
//            cntTipoCambioService.persistCntTipoCambio(cliente);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ObjectUtils.printObjectState(cliente);
        //listado tipo de cambio-------------------------------------------
//
//        System.out.println("LISTA DE TIPO DE CAMBIO::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
//        List<CntTipoCambio> list = cntTipoCambioService.listaCntTipoCambio();
//        int a = 0;
//        for (CntTipoCambio cntTipoCambio : list) {
//            a++;
//            System.out.println("Numero de Listas<<<<<<" + a);
//            System.out.println("Tipo de Cambio<<<<<<" + cntTipoCambio.getTipoCambio());
//            System.out.println("Tipo UFV<<<<<<" + cntTipoCambio.getTipoUfv());
//            System.out.println("Usuario <<<<<<<<" + cntTipoCambio.getUsuarioAlta());
//        }
//        System.out.println("la fecha del primero es::::"+list.get(0).getFecha());
//        System.out.println("la fecha del ultimo es::::"+list.get(list.size()-1).getFecha());
//el ultimo registro de fecha, tipo de cambio y tipoufv-------------------------------------------                 
//        System.out.println("El ultimo registro es en fecha::::::" + cntTipoCambioService.ultimoCntTipoCambio().getFecha());
//        System.out.println("El ultimo registro de tipo de cambio es::::::" + cntTipoCambioService.ultimoCntTipoCambio().getTipoCambio());
//        System.out.println("El ultimo registro de tipoufv::::::" + cntTipoCambioService.ultimoCntTipoCambio().getTipoUfv());
//el ultimo registro de fecha, tipo de cambio y tipoufv discriminanado si el tipo de cambio y UFV tienen valores o no-------------------------------------------                 
//        System.out.println("El ultimo registro es en fecha::::::" + cntTipoCambioService.ultimoRegistroCntTipoCambio().getFecha());
//        System.out.println("El ultimo registro de tipo de cambio es::::::" + cntTipoCambioService.ultimoRegistroCntTipoCambio().getTipoCambio());
//        System.out.println("El ultimo registro de tipoufv::::::" + cntTipoCambioService.ultimoRegistroCntTipoCambio().getTipoUfv());
//la ultima fecha de registro sin tipo de cambio ni tipo ufv--------------------------------------
//        System.out.println("La primera fecha sin registro::::::" + cntTipoCambioService.ultimaFechaRegistroCntTipoCambio().getFecha());
//generar nuevas fechas a partir de la ultima fecha de sin tipo de cambio ni tipo ufv-------------
//        CntTipoCambio cntTipoCambioAux;
//        cntTipoCambioAux = cntTipoCambioService.ultimoRegistroCntTipoCambio();
//        System.out.println("fecha de la base de datos" + cntTipoCambioAux.getFecha());
//        Date fecha = cntTipoCambioService.generaSiguienteDia(cntTipoCambioAux.getFecha(), 1);
//        System.out.println("la fecha mas un dia es>>>>>>>>>>>>>>>>>> " + fecha);
//persist de las fechas con tipovalor y tipoUFV iguala cero----------------------------------------
//        System.out.println("persist tipo de cambio a dias faltantes::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
//        cntTipoCambioService.grabarFechas();
//contar el numero de regsitros que tienen valores de tipo de cambio y ufv igual a cero
//         System.out.println("Numero de dias faltantes:"+cntTipoCambioService.ultimoDiaRegistrado()+">>>>>>");
//realizar el primer registro en la base de datos cuando los registros sean cero
//         System.out.println("Numero de registros en la bd<---" + cntTipoCambioService.numeroRegistrosTipoCambio()+"--->");
//         cntTipoCambioService.grabarPrimerFecha();
//         
        //        int a = 0;
//        System.out.println("entro  a la lista de tipo cambio::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
//        for (CntTipoCambio cntTipoCambio : list) {
//            a++;
//            System.out.println("Numero de Listas<" + a + ">");
//            System.out.println("Fechas<<<<<<" + cntTipoCambio.getFecha());
//            System.out.println("Tipo de Cambio<<<<<<" + cntTipoCambio.getTipoCambio());
//            System.out.println("Tipo UFV<<<<<<" + cntTipoCambio.getTipoUfv());
//            System.out.println("Usuario <<<<<<<<" + cntTipoCambio.getUsuarioAlta());
//        }
        /*tipos de Cambio por fecha*/
//        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy, MM, dd");
//        Date date = sdf.parse("2013, 11, 01");
//        System.out.println("la fecha es :::"+date);
//        CntTipoCambio tipoES = cntTipoCambioService.devuelveCntTipoDeCambio(date);        
//        System.out.println("el tipo es "+tipoES.getTipoCambio().toString());

        /*dia siguiente*/
//        Date fecha = new Date();
//        long Cincominutos = 86400000;
//        System.out.println("la fecha actual es:::"+fecha);        
//        System.out.println("new Date "+new Date(fecha.getTime()+86400000L));
//        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
//        String fechaString = formato.format(new Date());
//        System.out.println("la fecha es:::: "+fechaString);        
//         Date fecha = new Date("" + mes + "/" + dias + "/2014");
        String fechaJ = "2015-01-27T04:00:00.000Z";        
        System.out.println("el tipo es"+cntTipoCambioService.devuelveCntTipoDeCambioWSParaCxP(fechaJ).getTipoCambio());
    }
}
