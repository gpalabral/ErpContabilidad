package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntEntidad;
import com.bap.erp.modelo.entidades.cnt.CntParametroAutomatico;
import com.bap.erp.modelo.entidades.cnf.ParAjustes;
import com.bap.erp.modelo.entidades.cnf.ParTipoRetencion;
import com.bap.erp.modelo.entidades.cnf.ParValor;
import com.bap.erp.modelo.servicios.cnt.CntEntidadesService;
import com.bap.erp.modelo.servicios.cnt.CntParametroAutomaticoService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.iknow.utils.ObjectUtils;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntParametricoAutomaticoCliente {

    public static void main(String args[]) throws Exception {

//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\BAP\\ERP\\GIT\\ERP-conta\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        CntParametroAutomaticoService cntParametroAutomaticoService = (CntParametroAutomaticoService) context.getBean("cntParametroAutomaticoService");
        CntEntidadesService cntEntidadesService = (CntEntidadesService) context.getBean("cntEntidadesService");
        ParParametricasService parParametricasService = (ParParametricasService) context.getBean("parParametricasService");

//        System.out.println("Persist ParametrosAutomaticos");
//        CntParametroAutomatico parametro = cntParametroAutomaticoService.find(CntParametroAutomatico.class, 792L);
////        parametro.setIdParametroAutomatico(792L);
////        parametro.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, 6L));
//        parametro.setTipoMovimiento("DEB");
//        parametro.setFacturaCompra("DEB");
//        parametro.setFacturaVenta("DEB");
////        parametro.setParTipoRetencion((ParTipoRetencion)parParametricasService.find(ParTipoRetencion.class,"SRET"));
//        parametro.setParTipoRetencionGrossingUp((ParTipoRetencion) parParametricasService.find(ParTipoRetencion.class, "SRET"));
//        parametro.setParAjustes((ParAjustes) parParametricasService.find(ParAjustes.class, "AREI"));
//        parametro.setSinFactura("DEB");
//       // parametro.setFechaAlta(new Date());
//        //  parametro.setUsuarioAlta("TEST");
//        parametro.setFechaModificacion(new Date());
//        parametro.setUsuarioModificacion("TESTTODO");

//        try {
////           cntParametroAutomaticoService.persistCntParametroAutomatico(parametro);
//            cntParametroAutomaticoService.mergeCntParametroAutomatico(parametro);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//         ObjectUtils.printObjectState(parametro);
//
//
//        System.out.println("Persist ParametrosAutomaticos");
//        CntParametroAutomatico parametro = cntParametroAutomaticoService.find(CntParametroAutomatico.class, 792L);
////        parametro.setIdParametroAutomatico(792L);
////        parametro.setCntEntidad((CntEntidad) cntEntidadesService.find(CntEntidad.class, 6L));
//        parametro.setTipoMovimiento("DEB");
//        parametro.setFacturaCompra("DEB");
//        parametro.setFacturaVenta("DEB");
////        parametro.setParTipoRetencion((ParTipoRetencion)parParametricasService.find(ParTipoRetencion.class,"SRET"));
//        parametro.setParTipoRetencionGrossingUp((ParTipoRetencion)parParametricasService.find(ParTipoRetencion.class,"SRET"));
//        parametro.setParAjustes((ParAjustes) parParametricasService.find(ParAjustes.class, "AREI"));
//        parametro.setSinFactura("DEB");
//       // parametro.setFechaAlta(new Date());
//      //  parametro.setUsuarioAlta("TEST");
//        parametro.setFechaModificacion(new Date());
//        parametro.setUsuarioModificacion("TESTTODO");
//        
//       
//        try {
////           cntParametroAutomaticoService.persistCntParametroAutomatico(parametro);
//            cntParametroAutomaticoService.mergeCntParametroAutomatico(parametro);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//         ObjectUtils.printObjectState(parametro);
////
////        
////         
////         
////         
////         List<CntParametroAutomatico>lista=cntParametroAutomaticoService.listaCntParametroAutomatico();
////         for (CntParametroAutomatico cntParametroAutomatico : lista) {
////             System.out.println("idtabla"+cntParametroAutomatico.getIdParametroAutomatico()+"----------------");
////            
////        }
//
//            
//        
//        System.out.println("Merge MASIVO--------------->");
//        cntParametroAutomaticoService.grabarParametrosHijosMasivo((CntEntidad) cntEntidadesService.find(CntEntidad.class, 5L),"hguzman");
//        CntParametroAutomatico test;
//        CntEntidad entidad = cntEntidadesService.find(CntEntidad.class, 11L);
//        test = cntParametroAutomaticoService.obtieneParametrosAutomaticosDeNivel2(entidad);
//        System.out.println("prueba de Param "+test.getFacturaCompra());
//        
//        try {
//            System.out.println("Verifica:"+cntParametroAutomaticoService.verificaRelacionCntEntidadConCntParametroAutomatico(cntEntidadesService.find(CntEntidad.class, 528L)));
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            System.out.println("Obtiene Objeto::"+cntParametroAutomaticoService.obtieneCntParametroAutomaticoPorCuenta(cntEntidadesService.find(CntEntidad.class, 528L)));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }

}
