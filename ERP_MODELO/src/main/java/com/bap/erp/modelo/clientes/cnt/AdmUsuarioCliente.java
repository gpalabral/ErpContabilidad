package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.admin.AdmUsuarios;
import com.bap.erp.modelo.admin.servicio.AdmUsuariosService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class AdmUsuarioCliente {

    //private final static Log log = LogFactory.getLog(CntEntidadesCliente.class);
    public static void main(String args[]) {

//        ApplicationContext context = new FileSystemXmlApplicationContext("//home//javier//Documentos//ERContabilidad//ERP-conta//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\GIT erpContaMerged\\ERP-conta\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("//media//henrry//267bd361-373b-4d96-9197-f3fbffb09c25//HENRRY//BAP//ERP//ERP CONTA//ERP-conta//ERP_APP//src//main//webapp//WEB-INF//applicationContext.xml");
        AdmUsuariosService admUsuariosService = (AdmUsuariosService) context.getBean("admUsuariosService");
        AdmUsuarios admUsuarios = new AdmUsuarios();
        try {
            System.out.println("CERIFICA:" + admUsuariosService.verificaUsuarioActiveDirectory(admUsuarios));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
