package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntProveedor;
import com.bap.erp.modelo.servicios.cnt.CntProveedorService;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntProveedorCliente {

    public static void main(String args[]) throws Exception {
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("//home//conejo//BAP//ERP-conta//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
                                                                            
        CntProveedorService cntProveedorService = (CntProveedorService) context.getBean("cntProveedorService");
        
        
        
        /*Listado*/        
//        List<CntProveedor> lista = cntProveedorService.listaCntProveedor();
//        for (CntProveedor cntProveedor : lista) {
//            System.out.println("los proveedores son "+cntProveedor.getRazonSocial());
//        }
        /*Razon Social por nit */         
//        CntProveedor prueba = cntProveedorService.generaProveedorPorNit(24681012L);
//        System.out.println("la razon social es:::"+prueba.getRazonSocial());
//       
//        CntProveedor prueba = cntProveedorService.generaProveedorPorRazonSocial("PRINCO");
//        System.out.println("la razon social es:::"+prueba.getRazonSocial());

   
        /*verifica si es nueva Autorización*/
//        CntProveedor cntProveedor = cntProveedorService.find(CntProveedor.class, 3L);
//        cntProveedor = cntProveedorService.cambiaAutorizacionSiEsNueva(cntProveedor, "123456789");
//        System.out.println("la nueva autorización es--- "+cntProveedor.getAutorizacion());
        System.out.println("genera proveedor"+cntProveedorService.generaProveedorPorNit(123456789L).getRazonSocial());
    }
}
