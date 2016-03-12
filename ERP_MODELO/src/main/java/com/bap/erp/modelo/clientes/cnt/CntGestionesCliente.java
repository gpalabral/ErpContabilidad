package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntGestion;
import com.bap.erp.modelo.servicios.cnt.CntGestionesService;
import com.iknow.utils.ObjectUtils;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntGestionesCliente {

    public static void main(String args[]) {

        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        CntGestionesService cntGestionesService = (CntGestionesService) context.getBean("cntGestionesService");
        
        /*Persist CntGestion*/
        CntGestion gestionPrueba = new CntGestion();
        gestionPrueba.setNombreGestion("GESTION 3");
        gestionPrueba.setPeriodoInicial("MAYO");
        gestionPrueba.setPeriodoFinal("ABRIL");
        gestionPrueba.setEstadoGestion("ABI");
        gestionPrueba.setFechaAlta(new Date());
        gestionPrueba.setUsuarioAlta("TEST");
        try {
            cntGestionesService.adicionarCntGestiones(gestionPrueba);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectUtils.printObjectState(gestionPrueba);        
        /*persist*/
        
        /*Modificacion CntGestion*/
//        CntGestion gestionPrueba = (CntGestion)cntGestionesService.find(CntGestion.class, 3L);
//        gestionPrueba.setNombreGestion("GESTION 4");
//        gestionPrueba.setFechaModificacion(new Date());
//        gestionPrueba.setUsuarioModificacion("TEST_MODIFICADO");
//        try {
//            cntGestionesService.modificarCntGestiones(gestionPrueba);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ObjectUtils.printObjectState(gestionPrueba);        
        /*modificación*/
        
        /*Eliminacion CntGestion*/
//        CntGestion gestionPrueba = (CntGestion)cntGestionesService.find(CntGestion.class, 4L);
//        try {
//            cntGestionesService.eliminarCrmGestiones(gestionPrueba);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }               
        /*eliminacion*/
        
        /* Listado cntGestiones*/
//        List<CntGestion> list = cntGestionesService.listaCrmGestiones();
//        for (CntGestion cntGestiones : list) {
//            System.out.println("gestiones:::" + cntGestiones.getNombreGestion());
//        }
        /*listado*/
    }
}
