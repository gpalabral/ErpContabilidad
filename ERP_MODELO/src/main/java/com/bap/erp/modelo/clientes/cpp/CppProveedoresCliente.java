package com.bap.erp.modelo.clientes.cpp;

import java.math.BigDecimal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CppProveedoresCliente {

    public static void main(String args[]) throws Exception {
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("home\\jonas\\NetBeansProjects\\Proyecto ERP\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
//                                                                          /home/conejo/GIT/ERP_CONTA_CxP/ERP-conta/ERP_APP/src/main/webapp/WEB-INF/applicationContext.xml
        ApplicationContext context = new FileSystemXmlApplicationContext("//home//conejo//GIT//ERP_CONTA_CxP//ERP-conta//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
//        CppProveedorService cppProveedorService = (CppProveedorService) context.getBean("cppProveedorService");
//        List<CppProveedor> lista = cppProveedorService.listaTodosLosProveedores();
//        for (CppProveedor cppProveedor : lista) {
//            System.out.println("los proveedores son::"+cppProveedor.getCppCliente().getNombre());
//        }
//        
        BigDecimal numero = new BigDecimal("-123");
        System.out.println("numero::: "+numero.negate());
        
    }
}