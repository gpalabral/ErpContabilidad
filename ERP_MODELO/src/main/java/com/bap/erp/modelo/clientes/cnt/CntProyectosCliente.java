package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntProyecto;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntProyectoService;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntProyectosCliente {

    public static void main(String args[]) throws Exception {
//F:\ERP Maven Project\APP\ERP_APP
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("//home//chano//Proyecto_ERP//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
        CntProyectoService cntProyectoService = (CntProyectoService) context.getBean("cntProyectoService");


//        for (CntProyecto cntProyecto : cntProyectoService.listaCntProyectosOrdenados()) {
//            System.out.println("Mascar:" + cntProyecto.getMascara());
//        }

        CntProyecto cntProyecto = cntProyectoService.find(CntProyecto.class,42L);
//        try {
//            String mascaraNivelPosicionUno = cntProyectoService.obtieneMascaraSeparada(cntProyecto, "N")[0];
//            String mascaraNivelPosicionDos = cntProyectoService.obtieneMascaraSeparada(cntProyecto, "N")[1];
//
//            System.out.println(mascaraNivelPosicionUno + "" + cntProyectoService.generaNumeroSiguienteAutomatico(cntProyecto, "N") + "" + mascaraNivelPosicionDos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        System.out.println("proyecto pertenece a detalle comprobante"+cntProyectoService.verificaProyectoDetalleCmmprobante(cntProyecto) +"**********************************************");
//
//        cntProyecto.setFechaBaja(new Date());
//        cntProyecto.setUsuarioBaja("TEST");
//        cntProyectoService.removeCntProyectos(cntProyecto);
        
        
//        List<CntProyecto>proyectos=cntProyectoService.listaHijosProyecto(cntProyecto);
//        for (CntProyecto cntProyecto1 : proyectos) {
//            System.out.println("la lista de h ijos es "+cntProyecto1.getIdProyecto()+"*******************************************");
//        }
//        cntProyectoService.removeProyectosMasHijos(cntProyecto);
        
        System.out.println("la base esta vacia"+cntProyectoService.existeProyecto()+"-------------------****************************----------------");
        
        
        

    }
}
