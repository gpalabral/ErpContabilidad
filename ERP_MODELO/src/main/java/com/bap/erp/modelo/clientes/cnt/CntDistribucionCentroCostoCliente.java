package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntConfiguracionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntDetalleComprobante;
import com.bap.erp.modelo.entidades.cnt.CntDistribucionCentrocosto;
import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.servicios.cnt.CntComprobantesService;
import com.bap.erp.modelo.servicios.cnt.CntConfiguracionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntDetalleComprobanteService;
import com.bap.erp.modelo.servicios.cnt.CntDistribucionCentroCostoService;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntDistribucionCentroCostoCliente {

    public static void main(String args[]) throws Exception {
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\BAP\\ERP\\GIT\\ERP-conta\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("//home//conejo//BAP//ERP-conta//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
//        CntComprobantesService cntComprobantesService = (CntComprobantesService) context.getBean("cntComprobantesService");
//        CntDetalleComprobanteService cntDetalleComprobanteService = (CntDetalleComprobanteService) context.getBean("cntDetalleComprobanteService");
        CntDistribucionCentroCostoService cntDistribucionCentroCostoService = (CntDistribucionCentroCostoService) context.getBean("cntDistribucionCentroCostoService");
//        CntEntidadesService cntEntidadesService = (CntEntidadesService) context.getBean("cntEntidadesService");

//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 5317L);
//
//        List<CntDistribucionCentrocosto>lista=cntDistribucionCentroCostoService.listaCombinadaCentrosDeCostoConDistribucionCentroCostoList(cntDetalleComprobante);
//        for (CntDistribucionCentrocosto cdc : lista) {
//            System.out.println("Codigo:"+cdc.getCntCentroDeCosto().getMascaraGenerada()+"CC:" + cdc.getCntCentroDeCosto().getDescripcion());            
//        }
//        List<CntDistribucionCentrocosto>lista= new ArrayList<CntDistribucionCentrocosto>();
//        CntDistribucionCentrocosto cntDistribucionCentrocosto = cntDistribucionCentroCostoService.find(CntDistribucionCentrocosto.class, 1L);
//        CntDistribucionCentrocosto cntDistribucionCentrocosto1 = cntDistribucionCentroCostoService.find(CntDistribucionCentrocosto.class, 2L);
//        lista.add(cntDistribucionCentrocosto);
//        lista.add(cntDistribucionCentrocosto1);                
//        System.out.println("el monto es:::"+cntDistribucionCentroCostoService.obtieneSumaDeMontosListaCntDistribucionCentroCosto(lista));
//        System.out.println("el porcentaje es:::"+cntDistribucionCentroCostoService.obtieneSumaDePorcentajesListaCntDistribucionCentroCosto(lista));
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 5910L);
//        List<CntDistribucionCentrocosto> cntDistribucionCentrocostoList = cntDistribucionCentroCostoService.listaCombinadaCentrosDeCostoConDistribucionCentroCostoList(cntDetalleComprobante, false, "0");    
//        for (CntDistribucionCentrocosto cntDistribucionCentrocosto : cntDistribucionCentrocostoList) {
//            System.out.println("la distribucion:::"+cntDistribucionCentrocosto.getCntCentroDeCosto().getIdEntidad());
//        }
//        List<CntDistribucionCentrocosto> listaFinal = cntDistribucionCentroCostoService.listaDistribucionCCParaModificacion(cntDetalleComprobante, cntDistribucionCentrocostoList);
//        for (CntDistribucionCentrocosto cntDistribucionCentrocosto : listaFinal) {
//            System.out.println("la distribucion es:::"+cntDistribucionCentrocosto.getPorcentaje());
//        }
//        CntDetalleComprobante cntDetalleComprobante = cntDetalleComprobanteService.find(CntDetalleComprobante.class, 6303L);
//        cntDistribucionCentroCostoService.modificaDistribucionCentroDeCostoPorDetalleComprobante(cntDetalleComprobante, "JonasTest", new Date());
//        try {
//            System.out.println("Verifica:" + cntDistribucionCentroCostoService.verificaRelacionCentroCostoConCntDistribucionCentrocosto(cntEntidadesService.find(CntEntidad.class, 1L)));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println("test");
        System.out.println("verifica:::: "+cntDistribucionCentroCostoService.existeUnComprobanteConCentroDeCosto());
    }
}
