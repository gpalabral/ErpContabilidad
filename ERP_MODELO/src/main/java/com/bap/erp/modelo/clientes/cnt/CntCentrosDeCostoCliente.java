package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.servicios.cnt.CntCentrosCostoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntCentrosDeCostoCliente {

    public static void main(String args[]) throws Exception {

        
        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("//home//henrry//Proyecto_ERP_MAVEN//ERP_APP//src//main//webapp//WEB-INF//applicationContext.xml");
        CntCentrosCostoService cntCentrosCostoService = (CntCentrosCostoService) context.getBean("cntCentrosCostoService");
        System.out.println("MASCARA:"+cntCentrosCostoService.convierteMascaraConCeros("9-&&-999"));

    }
    
}
