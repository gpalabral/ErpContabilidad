package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntAuxiliar;
import com.bap.erp.modelo.entidades.cnt.CntAuxiliarPorCuenta;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresPorCuentaService;
import com.bap.erp.modelo.servicios.cnt.CntAuxiliaresService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.iknow.utils.ObjectUtils;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntAuxiliaresPorCuentaCliente {

    public static void main(String args[]) throws Exception {
//F:\ERP Maven Project\APP\ERP_APP
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("//home//chano//Proyecto_ERP//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\BAP\\ERP\\GIT\\ERP-conta\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        CntAuxiliaresPorCuentaService cntAuxiliaresPorCuentaService = (CntAuxiliaresPorCuentaService) context.getBean("cntAuxiliaresPorCuentaService");
        CntEntidadesService cntEntidadesService = (CntEntidadesService) context.getBean("cntEntidadesService");
        CntAuxiliaresService cntAuxiliaresService = (CntAuxiliaresService) context.getBean("cntAuxiliaresService");

        CntEntidad cuenta = cntEntidadesService.find(CntEntidad.class, 8L);
//        CntAuxiliar auxiliar=cntAuxiliaresService.find(CntAuxiliar.class,1L);
//        CntAuxiliarPorCuenta porCuenta=new CntAuxiliarPorCuenta();
//        porCuenta.setCntAuxiliar(auxiliar);
//        porCuenta.setCntEntidad(cuenta);
//        porCuenta.setFechaAlta(new Date());
//        porCuenta.setUsuarioAlta("HENRRY- PRUEBA");
//        cntAuxiliaresPorCuentaService.persistCntAuxiliaresPorCuenta(porCuenta);

//        List<Long>ListaComprobantes=cntAuxiliaresPorCuentaService.listaAuxiliarDetalleComprobante(cuenta);
//        int cont=0;
//        if (!ListaComprobantes.isEmpty()) {
//            System.out.println("la lista esta cargada");
//            System.out.println("el tamaño de la lista es"+ListaComprobantes.size()+"");
//            
//        }else{
//        System.out.println("la lista esta vacia");
//        }
//        for (Long longAuxiliares : ListaComprobantes) {
//               System.out.println("la id de los auxiliares"+longAuxiliares.toString()+"<----------------------------------------"); 
//                        
//        }
//        System.out.println("la cuenta esta en detalle comprobante" + cntAuxiliaresPorCuentaService.comparaAuxiliarDetalleComprobanteEntidad(1L, cuenta) + "<-------------------------------");
        
        try {
            System.out.println("Verifica:"+cntAuxiliaresPorCuentaService.verificaRelacionCntEntidadConCntAuxiliarPorCuenta(cntEntidadesService.find(CntEntidad.class, 1L)));
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
