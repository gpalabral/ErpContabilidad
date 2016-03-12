package com.bap.erp.modelo.clientes.cnt;

import com.bap.erp.modelo.entidades.cnt.CntMascara;
import com.bap.erp.modelo.entidades.cnt.CntNivel;
import com.bap.erp.modelo.entidades.cnf.ParGruposNivel;
import com.bap.erp.modelo.servicios.cnt.CntCentrosCostoService;
import com.bap.erp.modelo.servicios.cnt.CntMascarasService;
import com.bap.erp.modelo.servicios.cnt.CntNivelesService;
import com.bap.erp.modelo.servicios.cnf.ParParametricasService;
import com.iknow.utils.ObjectUtils;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CntMascarasClient {

    public static void main(String[] args) {

//        ApplicationContext context = new FileSystemXmlApplicationContext("C:\\GUS\\BAP\\ERP\\Codigo\\ERP_APP\\target\\ERP_APP-1.0\\WEB-INF\\applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("D:\\Proyecto ERP MAVEN\\ERP APLICACION\\ERP_APP\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("//home//henrry//Proyecto_ERP_MAVEN//ERP_APP//src//main//webapp//WEB-INF//applicationContext.xml");
        ApplicationContext context = new FileSystemXmlApplicationContext("//home//javier//Documentos//ERContabilidad//ERP-conta//ERP_APP//src//main//webapp/WEB-INF//applicationContext.xml");
        CntMascarasService cntMascarasService = (CntMascarasService) context.getBean("cntMascarasService");
//        CntCentrosCostoService centrosCostoService = (CntCentrosCostoService) context.getBean("centrosCostoService");
        CntNivelesService cntNivelesService = (CntNivelesService) context.getBean("cntNivelesService");

//        ParParametricasService parParametricasService = (ParParametricasService) context.getBean("parParametricasService");
//        ParGruposNivel parGruposNivel = (ParGruposNivel) parParametricasService.find(ParGruposNivel.class, "PCTA");
//
////        CntMascara cntMascaras = cntMascarasService.obtieneMascarasPorIdGestion(1L, EnumGruposNivel.PLAN_CUENTAS.getCodigo());
//        ObjectUtils.printObjectState(parGruposNivel);
//
//
//        CntMascara cntMascaras = new CntMascara();
//        cntMascaras.setGrupoNivel(parGruposNivel);
//        cntMascaras.setTamanioNivel(2);
//        cntMascaras.setMascara("X");
//        cntMascaras.setFechaAlta(new Date());
//        cntMascaras.setUsuarioAlta("SIS");
//        try {
////            cntMascarasService.adicionarCntMascara(cntMascaras);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        ObjectUtils.printObjectState(cntMascaras);
//        
        /*String nombre="Henrry";
         //        System.out.println("LETRA:"+centrosCostoService.generaCodigoNiveleInicial());
         System.out.println("Verifica:"+cntMascarasService.verificaExistenciaDeMascara("PCTA"));
  
        
         //        System.out.println("la lista de niveles es"+cntNivelesService.listaDeNivelesPlanCuentas()+"");
         List<CntNivel>nivel=cntNivelesService.listaDeNivelesPlanCuentas("CCOS");
         //        List<CntNivel>nivel=cntNivelesService.listaDeNivelesPlanCuentas("PCTA");
        
         for (CntNivel cntNivel : nivel) {
         System.out.println("el nivel------------------->"+cntNivel.getNivel()+"la descripcion----------------->"+cntNivel.getDescripcion()+"");           
         }
            
        
         System.out.println("la mascara del plan de duentas es------------------------>"+cntNivelesService.muestraMascaraPlanCuentas("CCOS")+"");
         */
        try {
            List<CntMascara> listaMas = cntMascarasService.obtieneTananioMascara("PCTA");
            for (CntMascara lista : listaMas) {
                System.out.println("Tamanio Nivel.- "+lista.getTamanioNivel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
