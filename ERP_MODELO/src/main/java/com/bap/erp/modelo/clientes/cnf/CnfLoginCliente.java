package com.bap.erp.modelo.clientes.cnf;

import com.bap.erp.modelo.servicios.cnf.CnfLoginService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CnfLoginCliente {

    public static void main(String args[]) {

//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("//home//jonas//Proyecto_ERP//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("//home//chano//Proyecto_ERP//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
        CnfLoginService cnfLoginService = (CnfLoginService) context.getBean("cnfLoginService");
//        String pass = cnfLoginService.encriptarContrasena("admin_wamsa", "Wamsa12345@");
//        System.out.println("Encriptado:"+cnfLoginService.encriptarContrasena("admin_wamsa", "Wamsa123456@"));
//        System.out.println("desencript"+cnfLoginService.desencriptaContrasena("admin_wamsa", "CslNfENkzqeQJ4SFR4Xi+g=="));
//        System.out.println("desencript"+pass);
//        if(cnfLoginService.validaUsuario("admin_wamsa", "Wamsa12345@")){
//            System.out.println("es verdad");
//        }else{
//            System.out.println("es falso");
//        }
        System.out.println("el tipo es "+cnfLoginService.encuentraTipoUsuarioPorLogin("admin_wamsa"));
        
    }
}
